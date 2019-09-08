package com.gt.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gt.Enum.UserStatusEnum;
import com.gt.entity.Fadmin;
import com.gt.entity.Flimittrade;
import com.gt.entity.Fuser;
import com.gt.service.admin.LimittradeService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontValidateService;
import com.gt.util.Constant;
import com.gt.util.Device;
import com.gt.util.GoogleAuth;
import com.gt.util.OSSPostObject;

public class BaseAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAdminController.class);

	private static final String CLASS_NAME = BaseAdminController.class.getSimpleName();

	public static final String JsonEncode = "application/json;charset=UTF-8" ;

	/*@Autowired
	private ConstantMap constantMap ;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private FrontUserService frontUserService ;
	/*@Autowired
	private ValidateMap validateMap ;*/
	/*@Autowired
	private TaskList taskList ;*/
	@Autowired
	private LimittradeService limittradeService;
	
	public static final int maxResults = Constant.RecordPerPage ;
	
	public HttpSession getSession(HttpServletRequest request){
		return request.getSession() ;
	}
	
	public void setAdminSession(HttpServletRequest request,Fadmin fadmin){
		getSession(request).setAttribute("login_admin", fadmin) ;
		this.CleanSession(request) ;
	}
	
	public void removeAdminSession(HttpServletRequest request){
		getSession(request).removeAttribute("login_admin") ;
	}
	
	//获得管理员session
	public Fadmin getAdminSession(HttpServletRequest request){
		Object session = getSession(request).getAttribute("login_admin") ;
		Fadmin fadmin = null ;
		if(session!=null){
			fadmin = (Fadmin)session ;
		}
		return fadmin ;
	}
	
	//获得session中的用户
	public Fuser GetSession(HttpServletRequest request){
		Fuser fuser = null ;
		HttpSession session = getSession(request) ;
		Object session_user = session.getAttribute("login_user") ;
		if(session_user!=null){
			fuser = (Fuser)session_user;
			if(fuser.getFstatus() != UserStatusEnum.NORMAL_VALUE) return null;
		}
		return fuser ;
	}
	
	public void CleanSession(HttpServletRequest request){
		try {
			HttpSession session = getSession(request) ;
			String key = GetSession(request).getFid()+"trade" ;
			getSession(request).removeAttribute(key);
			session.setAttribute("login_user", null) ;
			session.setAttribute("wallet", null) ;
			session.setAttribute("loguser_wallets", null) ;
		} catch (Exception e) {}
	}
	
	@ModelAttribute
	public void addConstant(HttpServletRequest request){//此方法会在每个controller前执行

		//前端常量
		//request.setAttribute("constant", constantMap.getMap()) ;
		request.setAttribute("constant", frontConstantMapService.getMap()) ;
		String ossURL = OSSPostObject.URL;
		if(Constant.IS_OPEN_OSS.equals("false")){
			ossURL = "";
		}
		request.setAttribute("oss_url", ossURL) ;

		
//		
//		String uri = request.getRequestURI() ;
//		if(uri.startsWith("/trade/")){
//			//最新成交价格
//			List<Fvirtualcointype> realTimePrize = new ArrayList<Fvirtualcointype>() ;
//			List<Fvirtualcointype> fvirtualcointypes = (List)this.constantMap.get("virtualCoinType");
//			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
//				fvirtualcointype.setLastDealPrize(this.realTimeData.getLatestDealPrize(fvirtualcointype.getFid())) ;
//				fvirtualcointype.setHigestBuyPrize(this.realTimeData.getHighestBuyPrize(fvirtualcointype.getFid())) ;
//				fvirtualcointype.setLowestSellPrize(this.realTimeData.getLowestSellPrize(fvirtualcointype.getFid())) ;
//				realTimePrize.add(fvirtualcointype) ;
//				
//			}
//			request.setAttribute("realTimePrize", realTimePrize) ;
//		}
		
		if(GetSession(request)==null) return ;//用户没登陆不需执行以下内容
		

		
	}
	

	//发送短信验证码，已登录用户
	/*public boolean SendMessage(HttpServletRequest request,Fuser fuser,int fuserid,String areaCode,String phone,int type,int lang){
		LOGGER.info(CLASS_NAME + " SendMessage 已登录用户发送验证码,fuserid:{},phone:{},type:{}", fuserid, phone, type);
		boolean canSend = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(fuserid+"_"+type) ;
		LOGGER.info(CLASS_NAME + " SendMessage 根据用户id+短信类型从validateMap查找是否存在,返回结果集messageValidate:{}", new Gson().toJson(messageValidate));
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			LOGGER.info(CLASS_NAME + " SendMessage 距离上一次操作时间,小于120s");
			canSend = false ;
		}
		
		if(canSend){
			MessageValidate messageValidate2 = new MessageValidate() ;
			messageValidate2.setAreaCode(areaCode) ;
			messageValidate2.setCode(Utils.randomInteger(6)) ;
			messageValidate2.setPhone(phone) ;
			messageValidate2.setCreateTime(Utils.getTimestamp()) ;
			this.validateMap.putMessageMap(fuserid + "_" + type, messageValidate2) ;
			LOGGER.info(CLASS_NAME + " SendMessage 将验证信息存储到validateMap中，key:{},value:{}", fuserid+"_"+type, new Gson().toJson(messageValidate2));
			Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
//			fvalidatemessage.setFcontent(this.constantMap.getString(ConstantKeys.VALIDATE_MESSAGE_CONTENT).replace("#code#", messageValidate2.getCode())) ;
//			fvalidatemessage.setFcontent(messageValidate2.getCode()) ;
			Object[] params = new Object[]{messageValidate2.getCode()};
			fvalidatemessage.setFcontent(getLocaleMessage(request,params,"smstpl.enum."+type)) ;
			fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
			fvalidatemessage.setFphone(phone) ;
			fvalidatemessage.setFlang(lang);
			fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
			LOGGER.info(CLASS_NAME + " SendMessage 将短信发送信息存储到fvalidatemessage表,fvalidatemessage:{}", new Gson().toJson(fvalidatemessage));
			fvalidatemessage.setFuser(fuser) ;
			this.frontValidateService.addFvalidateMessage(fvalidatemessage) ;

			LOGGER.info(CLASS_NAME + " SendMessage 将短信发送记录主键id存储到taskList,id:{}", fvalidatemessage.getFid());
			this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
		}
		return canSend ;
	}*/
	
	/*public boolean validateMessageCode(Fuser fuser,String areaCode,String phone,int type,String code){
		boolean match = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(fuser.getFid()+"_"+type) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(!messageValidate.getAreaCode().equals(areaCode)
					||!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMessageMap(fuser.getFid()+"_"+type);
			}
		}
		
		return match ;
	}*/
	
	
	//发送短信验证码，未登录用户
	/*public boolean SendMessage(HttpServletRequest request,String ip,String areaCode,String phone,int type,int lang){
		LOGGER.info(CLASS_NAME + "SendMessage 未登录用户发送短信验证码,参数ip:{},areaCode:{},phone:{},type:{}", ip, areaCode, phone, type);
		String key1 = ip+"_"+type ;
		String key2 = ip+"_"+phone+"_"+type ;
		LOGGER.info(CLASS_NAME + "SendMessage key1:{},key2:{}", key1,key2);
		//限制ip120秒发送
		MessageValidate messageValidate = this.validateMap.getMessageMap(key1) ;
		LOGGER.info(CLASS_NAME + "SendMessage 根据ip地址+验证码类型，从数据库中查询限制信息,messageValidate:{}", new Gson().toJson(messageValidate));
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			LOGGER.info(CLASS_NAME + " SendMessage 验证码限制，此类型:{}距离上一次发送低于120s", key1);
			return false ;
		}
		
		
		messageValidate = this.validateMap.getMessageMap(key2) ;
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			LOGGER.info(CLASS_NAME + " SendMessage 验证码限制，此类型:{}距离上一次发送低于120s", key2);
			return false ;
		}
		
		MessageValidate messageValidate2 = new MessageValidate() ;
		messageValidate2.setAreaCode(areaCode) ;
		messageValidate2.setCode(Utils.randomInteger(6)) ;
		messageValidate2.setPhone(phone) ;
		messageValidate2.setCreateTime(Utils.getTimestamp()) ;
		this.validateMap.putMessageMap(key2, messageValidate2) ;
		this.validateMap.putMessageMap(key1, messageValidate2) ;
		Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
//		fvalidatemessage.setFcontent(messageValidate2.getCode()) ;
		Object[] params = new Object[]{messageValidate2.getCode()};
		fvalidatemessage.setFcontent(getLocaleMessage(request,params,"smstpl.enum."+type)) ;
		fvalidatemessage.setFlang(lang);
		fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
		fvalidatemessage.setFphone(phone) ;
		fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
		this.frontValidateService.addFvalidateMessage(fvalidatemessage) ;
		
		this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
		return true ;
	}*/
	
	/*public boolean validateMessageCode(String ip,String areaCode,String phone,int type,String code){
		if(type!=MessageTypeEnum.REG_CODE&&type!=MessageTypeEnum.FIND_PASSWORD){
			System.out.println("调用方法错误");
			return false ;
		}
		
//		String key1 = ip+"message_"+type ;
		String key2 = ip+"_"+phone+"_"+type ;
		boolean match = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(key2) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(!messageValidate.getAreaCode().equals(areaCode)
					||!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMessageMap(key1);
//				this.validateMap.removeMessageMap(key2);
			}
		}
		
		return match ;
	}*/
	
	//发送邮件验证码，未登录用户
	/*public boolean SendMail(HttpServletRequest request,String ip,String mail,int type,int lang){
		String key1 = ip+"mail_"+type ;
		String key2 = ip+"_"+mail+"_"+type ;
		//限制ip120秒发送
		Emailvalidate mailValidate = this.validateMap.getMailMap(key1) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		
		mailValidate = this.validateMap.getMailMap(key2) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		Emailvalidate mailValidate2 = new Emailvalidate() ;
		mailValidate2.setCode(Utils.randomInteger(6)) ;
		mailValidate2.setFcreateTime(Utils.getTimestamp()) ;
		mailValidate2.setFmail(mail) ;
		
		this.validateMap.putMailMap(key1, mailValidate2) ;
		this.validateMap.putMailMap(key2, mailValidate2) ;
		
		Fvalidateemail fvalidateemail = new Fvalidateemail() ;
		fvalidateemail.setEmail(mail) ;
		fvalidateemail.setFlang(lang);
		String regmailContent = null ;
		String lcal = RequestContextUtils.getLocale(request).toString();
		String regmailkey  = ConstantKeys.regmailContent;
		if(lcal.equals("zh_CN")){
			regmailkey=regmailkey+"_CN";
		}
		//regmailContent = this.constantMap.getString(regmailkey).replace("#code#", mailValidate2.getCode()) ;
		regmailContent = this.frontConstantMapService.getString(regmailkey).replace("#code#", mailValidate2.getCode());
		
		fvalidateemail.setFcontent(regmailContent) ;
		fvalidateemail.setFcreateTime(Utils.getTimestamp()) ;
		fvalidateemail.setFstatus(ValidateMailStatusEnum.Not_send) ;
		String webnamekey = ConstantKeys.WEB_NAME;
		if(lcal.equals("zh_CN")){
			webnamekey=webnamekey+"_CN";
		}
		//fvalidateemail.setFtitle(this.constantMap.getString(webnamekey)+getLocaleMessage(request,null,"mail.title.reg"));
		fvalidateemail.setFtitle(this.frontConstantMapService.getString(webnamekey)+getLocaleMessage(request,null,"mail.title.reg"));
		this.frontValidateService.addFvalidateemail(fvalidateemail) ;
		
		this.taskList.returnMailList(fvalidateemail.getFid()) ;
		
		return true ;
	}
	
	public boolean validateMailCode(String ip ,String mail,int type,String code){
		String key2 = ip+"_"+mail+"_"+type ;
		boolean match = true ;
		Emailvalidate emailvalidate = this.validateMap.getMailMap(key2) ;
		if(emailvalidate==null){
			match = false ;
		}else{
			if(!emailvalidate.getFmail().equals(mail)
					||!emailvalidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMailMap(key1);
//				this.validateMap.removeMailMap(key2);
			}
		}
		
		return match ;
	}*/
	

	public int totalPage(int totalCount,int maxResults){
		return totalCount/maxResults + (totalCount%maxResults==0?0:1) ;
	}

	
	public Flimittrade isLimitTrade(int vid) {
		Flimittrade flimittrade = null;
		String filter = "where ftrademapping.fid="+vid;
		List<Flimittrade> flimittrades = this.limittradeService.list(0, 0, filter, false);
		if(flimittrades != null && flimittrades.size() >0){
			flimittrade = flimittrades.get(0);
		}
		return flimittrade;
	}
	
	
	//图片验证码
	public boolean vcodeValidate(HttpServletRequest request,String vcode){
		Object session_code = request.getSession().getAttribute("checkcode") ;
		if(session_code==null || !vcode.equalsIgnoreCase((String)session_code)){
			return false ;
		}else{
			getSession(request).removeAttribute("checkcode") ;
			return true ;
		}
	}
	
	public boolean isSuper(HttpServletRequest request){
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		if (null !=admin && admin.getFid()==8){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = null;
		 try {
			 ip = request.getHeader("X-Forwarded-For");
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("X-Real-IP");
			 }
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("http_client_ip");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getRemoteAddr();  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("Proxy-Client-IP");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("WL-Proxy-Client-IP");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
			 }  
			 // 如果是多级代理，那么取第一个ip为客户ip   
			 if (ip != null && ip.indexOf(",") != -1) {  
			  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
			 }
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip = request.getRemoteAddr(); 
		}finally{
			//检测IP是否合法有效，不合法IP直接标识为非法登录
			 if (!InetAddressValidator.getInstance().isValid(ip)){
				 System.out.println("非法入侵："+ip+"");
				 ip = "Illegal IP:"+ip;
			 }
		 }
		 return ip;
	}
	
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
	    WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	    return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
	}
	
	//判断是否移动端
	public boolean isMobile(HttpServletRequest request)
	{
		boolean isMobile = false;

			if(Device.isMobileDevice(request))
			{
				isMobile =  true;
			}
			else
			{
				isMobile =  false;
			}

		return isMobile;
	}
	
	public String gAuth(HttpServletRequest request) {
		String gcode = null;
		if(null!=request.getParameter("gcode")){
			gcode = request.getParameter("gcode").toString();
		}
		if (null==gcode || gcode.equals("") || gcode.length()!=6){
			return "该项操作需要提交谷歌验证码";
		}
		
		Fadmin fadmin = getAdminSession(request);
		int userid = fadmin.getFuserid();
		if(userid != 0){
			boolean flag = false ;
			try{
			Fuser fuser = this.frontUserService.findById(userid);
				if(fuser.getFgoogleBind()){
					flag = GoogleAuth.auth(Long.parseLong(gcode.trim()),fuser.getFgoogleAuthenticator()) ;
				}else {
					return "账户尚未绑定谷歌认证码";
				}
			}catch(Exception e){
				e.printStackTrace() ;
				flag = false ;
			}
			if(flag==false){
				return "谷歌验证失败";
			}else {
				return "ok";
			}
		}else {
			return "未绑定实际操作账户";
		}
	}
}
