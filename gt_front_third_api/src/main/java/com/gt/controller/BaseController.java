package com.gt.controller;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.CountLimitTypeEnum;
import com.gt.Enum.MessageTypeEnum;
import com.gt.Enum.PprojectTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.Enum.UserStatusEnum;
import com.gt.comm.MessageValidate;
import com.gt.entity.Emailvalidate;
import com.gt.entity.Fadmin;
import com.gt.entity.Flimittrade;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pdomain;
import com.gt.entity.Pproject;
import com.gt.entity.Psystemconfig;
import com.gt.result.JsonResult;
import com.gt.result.ResultCode;
import com.gt.service.admin.LimittradeService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPprojectService;
import com.gt.service.front.FrontPsystemconfigService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontValidateMapService;
import com.gt.service.front.FrontValidateService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.Device;
import com.gt.util.GoogleAuth;
import com.gt.util.NetworkUtil;
import com.gt.util.OSSPostObject;
import com.gt.util.Utils;

public class BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	private static final String CLASS_NAME = BaseController.class.getSimpleName();

	public static final String JsonEncode = "application/json;charset=UTF-8" ;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontValidateMapService frontValidateMapService ;
	@Autowired
	private LimittradeService limittradeService;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontPsystemconfigService frontPsystemconfigService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontPprojectService frontPprojectService;

	
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
	
	public Fuser GetSecondLoginSession(HttpServletRequest request){
		HttpSession session = getSession(request) ;
		return (Fuser) session.getAttribute("second_login_user") ;
	}
	
	public void SetSecondLoginSession(HttpServletRequest request,Fuser fuser){
		HttpSession session = getSession(request) ;
		session.setAttribute("second_login_user", fuser) ;
	}
	public void RemoveSecondLoginSession(HttpServletRequest request){
		HttpSession session = getSession(request) ;
		session.setAttribute("second_login_user", null) ;
	}
	
	public void SetSession(Fuser fuser,HttpServletRequest request){
		HttpSession session = getSession(request) ;
		session.setAttribute("login_user", fuser) ;
		LinkedHashMap<Fvirtualcointype,Fvirtualwallet> loguser_wallets =this.frontUserService.findVirtualWallet(fuser.getFid());
		Fvirtualwallet wallet = this.frontUserService.findWalletByUser(fuser.getFid());
		Fvirtualwallet usdtwallet = this.frontUserService.findUSDTWalletByUser(fuser.getFid());
		session.setAttribute("wallet",wallet );
		session.setAttribute("usdtwallet",usdtwallet );
		session.setAttribute("loguser_wallets",loguser_wallets );
		
		double totalCapital = 0F ;
		totalCapital+=((wallet.getFtotal()+wallet.getFfrozen())/6.5);
		totalCapital+=usdtwallet.getFtotal()+usdtwallet.getFfrozen();
		Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapService.get("tradeMappings");
		if(loguser_wallets!=null){
			for (Map.Entry<Fvirtualcointype, Fvirtualwallet> entry : loguser_wallets.entrySet()) {
				if(entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_CNY_VALUE || entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_USDT_VALUE) continue;
				try {
					Integer trade=0;
					if(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid())!=null){
						trade=tradeMappings.get(entry.getValue().getFvirtualcointype().getFid());
					}
					totalCapital += ( entry.getValue().getFfrozen()+entry.getValue().getFtotal() )* this.frontCacheService.getLatestDealPrize(trade) ;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		session.setAttribute("totalCapitalTrade", Utils.getDouble(totalCapital,2) );
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
	
	public boolean isNeedTradePassword(HttpServletRequest request){
		if(GetSession(request) == null) return true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String key = GetSession(request).getFid()+"trade" ;
		Object obj = getSession(request).getAttribute(key) ;
		
		if(obj==null){
			return true ;
		}else{
			try {
				double hour = Double.valueOf(this.frontConstantMapService.getString("tradePasswordHour"));
				double lastHour = Utils.getDouble((sdf.parse(obj.toString()).getTime()-new Date().getTime())/1000/60/60, 2);
				if(lastHour >= hour){
					getSession(request).removeAttribute(key);
					return true ;
				}else{
					return false ;
				}
			} catch (ParseException e) {
				return false ;
			}
		}
	}
	
	
	public void setNoNeedPassword(HttpServletRequest request){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String key = GetSession(request).getFid()+"trade" ;
			getSession(request).setAttribute(key,sdf.format(new Date())) ;
		} catch (Exception e) {}
	}
	
	
	//for menu
	@ModelAttribute
	public void menuSelect(HttpServletRequest request){
		//banner菜单
		String uri = request.getRequestURI() ;
		String selectMenu = null ;
		if(uri.startsWith("/trade")
				||uri.startsWith("/user")
				||uri.startsWith("/coinage")
				||uri.startsWith("/balance")
				||uri.startsWith("/question")){
			selectMenu = "trade";
		}else if(uri.startsWith("/service")){
			selectMenu = "service" ;
		}else if(uri.startsWith("/financial")
				||uri.startsWith("/introl")
				||uri.startsWith("/push/")
				||uri.startsWith("/shop/sell")
				||uri.startsWith("/shop/myorder")
				||uri.startsWith("/account")){
			selectMenu = "financial" ;
		}else if(uri.startsWith("/about/")){
			selectMenu = "about" ;
		}else if(uri.startsWith("/crowd/")){
			selectMenu = "crowd" ;
		}else if(uri.startsWith("/market.html")){
			selectMenu = "market" ;
		}else if(uri.startsWith("/exchange/")){
			selectMenu = "exchange" ;
		}else{
			selectMenu = "index";
		}
		request.setAttribute("selectMenu", selectMenu) ;
		//左侧菜单
		if(uri.startsWith("/trade")
				||uri.startsWith("/user")
				||uri.startsWith("/shop/")
				||uri.startsWith("/divide/")
				||uri.startsWith("/crowd/")
				||uri.startsWith("/balance/")
				||uri.startsWith("/introl/mydivide")
				||uri.startsWith("/account")
				||uri.startsWith("/financial")
				||uri.startsWith("/coinage/")
				||uri.startsWith("/free/")//融资融币
				||uri.startsWith("/question")){
			String leftMenu = null ;
			int selectGroup = 1 ;
			
			if(uri.startsWith("/question/questionColumn")){
				leftMenu = "questionColumn";
				selectGroup = 4 ;
			}else if(uri.startsWith("/question/question")){
				leftMenu = "question";
				selectGroup = 4 ;
			}else if(uri.startsWith("/user/apply")){
				leftMenu = "apply";
				selectGroup = 4 ;
			}if(uri.startsWith("/question/message")){
				leftMenu = "message";
				selectGroup = 4 ;
			}else if(uri.startsWith("/user/")){
				leftMenu = "userinfo" ;
				selectGroup = 3 ;
			}else if(uri.startsWith("/user/security")){
				leftMenu = "security" ;
				selectGroup = 3 ;
			}else if(uri.startsWith("/user/api")){
				leftMenu = "api" ;
				selectGroup = 3 ;
			}else if(uri.startsWith("/account/record")){
				leftMenu = "record" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/financial/accountalipay") 
					||uri.startsWith("/financial/accountbank")
					||uri.startsWith("/financial/accountcoin")){
				leftMenu = "accountAdd" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/crowd/mylogs") 
					||uri.startsWith("/crowd/logs")){
				leftMenu = "mylogs" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/deduct")){
				leftMenu = "record" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/rechargeCny")
					||uri.startsWith("/account/proxyCode")
					||uri.startsWith("/account/payCode")){
				leftMenu = "recharge" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/withdrawCny")){
				leftMenu = "withdraw" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/rechargeBtc")){
				leftMenu = "recharge" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/withdrawBtc")){
				leftMenu = "withdraw" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/btcTransport")){
				leftMenu = "btcTransport" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/trade/entrust")){
				leftMenu = "entrust" ;
				selectGroup = 1 ;
			}else if(uri.startsWith("/divide/")){
				leftMenu = "divide" ;
				selectGroup = 6 ;
			}else if(uri.startsWith("/financial/")){
				leftMenu = "financial" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/introl/mydivide")){
				leftMenu = "introl" ;
				selectGroup = 2 ;
			}
			request.setAttribute("selectGroup", selectGroup) ;
			request.setAttribute("leftMenu", leftMenu) ;
		}
	}
	
	/*@ModelAttribute
	public void addConstant(HttpServletRequest request){//此方法会在每个controller前执行
		//HttpSession session = getSession(request)  ;
		//前端常量
		request.setAttribute("constant", frontConstantMapService.getMap()) ;
		String ossURL = OSSPostObject.URL;
		if(Constant.IS_OPEN_OSS.equals("false")){
			ossURL = "";
		}
		request.setAttribute("oss_url", ossURL) ;
		//判断是否是项目方域名
		if(getReqDomain(request)!=null){
			
			request.setAttribute("isprojectUrl", true) ;
			Pproject pproject = frontPprojectService.findByProperty("projectId.fid", getReqDomain(request).getProjectId().getFid());
			request.setAttribute("project", pproject) ;
			//request.setAttribute("projectname", pproject.getLogoUrl()) ;
		}else{
			
			request.setAttribute("isprojectUrl", false) ;
		}
		StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();   	
		
		if(tempContextUrl.startsWith(Constant.Domain.toLowerCase())){
			request.setAttribute("isMainDomain", true) ;
		}else{
			request.setAttribute("isMainDomain", false) ;
		}
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
		
		if(this.frontCacheService.isBalckUser(GetSession(request).getFid())){
			this.CleanSession(request) ;
			return ;
		}

		
	}*/
	
/*	public Map<String, Object> setRealTimeData(int id){
		Map<String, Object> map = new HashMap<String, Object>() ;

		map.put("BuyMap", this.realTimeData.getBuyDepthMap(id,10) ) ;
		map.put("SellMap", this.realTimeData.getSellDepthMap(id,10) ) ;
		map.put("BuySuccessMap", this.realTimeData.getEntrustSuccessMap(id,10)) ;
		
		map.put("LatestDealPrize", this.realTimeData.getLatestDealPrize(id)) ;
		map.put("LowestSellPrize", this.realTimeData.getLowestSellPrize(id)) ;
		map.put("HighestBuyPrize", this.realTimeData.getHighestBuyPrize(id)) ;
		
		map.put("OneDayLowest", this.oneDayData.getLowest(id)) ;
		map.put("OneDayHighest", this.oneDayData.getHighest(id)) ;
		map.put("OneDayTotal", this.oneDayData.getTotal(id)) ;
		return map ;
	}*/
	

	public boolean validateMessageCode(Fuser fuser,String areaCode,String phone,int type,String code){
		boolean match = true ;
		MessageValidate messageValidate = this.frontValidateMapService.getMessageMap(fuser.getFid()+"_"+type) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(/*!messageValidate.getAreaCode().equals(areaCode)
					||*/!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+type);
			}
		}
		
		return match ;
	}
	
	
	
	public boolean validateMessageCode(String ip,String areaCode,String phone,int type,String code){
		if(type!=MessageTypeEnum.REG_CODE&&type!=MessageTypeEnum.FIND_PASSWORD&&type!=MessageTypeEnum.loginTwo){
			System.out.println("调用方法错误");
			return false ;
		}
		
		String key1 = ip+"message_"+type ;
		String key2 = ip+"_"+phone+"_"+type ;
		boolean match = true ;
		MessageValidate messageValidate = this.frontValidateMapService.getMessageMap(key2) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(!messageValidate.getAreaCode().equals(areaCode)
					||!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
				this.frontValidateMapService.removeMessageMap(key1);
				this.frontValidateMapService.removeMessageMap(key2);
			}
		}
		
		return match ;
	}

	
	public boolean validateMailCode(String ip ,String mail,int type,String code){
		String key2 = ip+"_"+mail+"_"+type ;
		boolean match = true ;
		Emailvalidate emailvalidate = this.frontValidateMapService.getMailMap(key2) ;
		if(emailvalidate==null){
			match = false ;
		}else{
			if(!emailvalidate.getFmail().equals(mail)
					||!emailvalidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMailMap(key1);
				this.frontValidateMapService.removeMailMap(key2);
			}
		}
		
		return match ;
	}
	

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
				 ip = ip.substring(0, ip.lastIndexOf(",")).trim();
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
		public static  MessageValidate getMessageValidate(String areaCode,String phone){
			MessageValidate messageValidate2 = new MessageValidate() ;
			messageValidate2.setAreaCode(areaCode) ;
			messageValidate2.setCode(Utils.randomInteger(6)) ;
			messageValidate2.setPhone(phone) ;
			messageValidate2.setCreateTime(Utils.getTimestamp()) ;
			return messageValidate2;
		}
		
		//功能的安全检测，主要检测谷歌验证码，短信验证码，交易密码，48小时安全提现
		//检测通过返回ok，否则返回json格式的错误信息
		//参数			说明
		//isHumanCheck	是否进行人机验证，重要操作需要
		//is48Safe		是否进行48小时安全检测，重要操作需要
		//isNeedTrade	是否需要校验交易密码，为true则tradePwd必填
		//tradePwd		交易密码
		//isNeedCode	是否交易手机验证码、谷歌验证码，为true则phoneCode、totpCode根据情况必填
		//phoneCode		手机验证码
		//totpCode		谷歌验证码
		//msgType		手机验证码类型
		//logType		日志类型，可以为0
		//logDesc		日志说明，可以为空
		//特别说明：为了实现统一安全检测，所以需要约定好相应提交的请求参数名称
		//交易密码	
		/*public  String actionSecurityCheck(HttpServletRequest request,Fuser fuser,Boolean isHumanCheck,Boolean is48Safe,Boolean isNeedTrade,String tradePwd,Boolean isNeedCode,String phoneCode,String totpCode,Integer msgType,Integer logType,String logDesc) {
			
			JSONObject jsonObject = new JSONObject() ;
			//人机验证
			if(isHumanCheck){
				String retCode;
				try {
					retCode = new AliyunCheck().checkAliyunNVCMode(request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					retCode ="100";
				}
				if(!(retCode.equals("100") || retCode.equals("200"))) {
					jsonObject.accumulate("code", retCode) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.humancheck")) ;
					return jsonObject.toString() ;
				}
			}
			//人机验证
			
			//强制性安全检测，避免异常账户状态导致用户财产损失
			if(fuser.getFregType()==0){
				//注册类型为手机的账户，必须要绑定手机号码
				if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.ntp")) ;
					return jsonObject.toString() ;
				}
			}else{
				//对注册类型为邮箱的账户，必须要绑定邮箱地址
				if(null==fuser.getFemail() || fuser.getFemail().equals("")){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.nex")) ;
					return jsonObject.toString() ;
				}
			}
			//强制性安全检测，避免异常账户状态导致用户财产损失
			
			//48小时安全检测
			if(is48Safe) {
				try {
					if (frontUserService.isSafeTime(fuser.getFid())){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.safetime")) ;
						return jsonObject.toString() ;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.safetime")) ;
					return jsonObject.toString() ;
				}
			}
			//48小时安全检测
			
			//交易密码，手机验证码，谷歌验证码都需要进行次数检测
			String ip = getIpAddr(request);
			
			//需要校验交易密码
			if(isNeedTrade) {
				if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
					jsonObject.accumulate("code", -4) ;
					jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.user.settranfirst")) ;
					return jsonObject.toString() ;
				}
				
				int trade_limit = frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				if(trade_limit<=0){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent")) ;
					this.frontUserService.updateUserLog(fuser, ip, logType, logDesc+"：频繁密码验证");
					return jsonObject.toString() ;
				}
				
				try {
					if(!fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt()))){
						jsonObject.accumulate("code", -1) ;
						Object[] params = new Object[]{trade_limit};
						jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentpwd"));
						frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
						return jsonObject.toString() ;
					}else if(trade_limit<Constant.ErrorCountLimit){
						frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObject.accumulate("code", -1) ;
					Object[] params = new Object[]{trade_limit};
					jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentpwd"));
					frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					return jsonObject.toString() ; 
				}
			}
			//需要校验交易密码
			
			//需要进行验证码检测
			if(isNeedCode) {
				//必须绑定在手机和谷歌之中绑定一个
				if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.bindinfo")) ;
					return jsonObject.toString() ;
				}
				int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
				int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				
				if(fuser.getFgoogleBind()){
					if(google_limit<=0){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent")) ;
						this.frontUserService.updateUserLog(fuser, ip,logType, logDesc+"：频繁谷歌验证");
						return jsonObject.toString() ;
					}
					
					boolean googleValidate = GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator()) ;
					if(!googleValidate){
						jsonObject.accumulate("code", -1) ;
						Object[] params = new Object[]{google_limit};
						jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentgcode"));
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
						return jsonObject.toString() ;
					}else if(google_limit<Constant.ErrorCountLimit){
						this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					}
				}
				
				if(fuser.isFisTelephoneBind()){
					if(mobil_limit<=0){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent")) ;
						this.frontUserService.updateUserLog(fuser, ip, logType, logDesc+"：频繁手机验证");
						return jsonObject.toString() ;
					}
					
					boolean mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(),msgType, phoneCode) ;
					if(!mobilValidate){
						jsonObject.accumulate("code", -1) ;
						Object[] params = new Object[]{mobil_limit};
						jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentmcode"));
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
						return jsonObject.toString() ;
					}else if(mobil_limit<Constant.ErrorCountLimit){
						this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
					}
				}
			}
			
			//需要进行验证码检测
			
			return "ok";
		}*/
		
		
		
		
		
		
		
		private Set<Integer> blackUser = new HashSet<Integer>();

		public void addBlackUser(int uid) {
			synchronized (blackUser) {
				blackUser.add(uid);
			}
		}

		public void removeBlackUser(int uid) {
			synchronized (blackUser) {
				blackUser.remove(uid);
			}
		}

		public boolean isBalckUser(int uid) {
			synchronized (blackUser) {
				return blackUser.contains(uid);
			}
		}

		private Map<String, Integer> black = new HashMap<String, Integer>();

		public boolean black(String ip, Integer type) {
			synchronized (this.black) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String key = ip + "_" + type + "_"
						+ sdf.format(Utils.getTimestamp());
				Integer count = this.black.get(key);
				if (count == null) {
					count = 0;
				}
				if (count > 20) {
					return false;
				}
				this.black.put(key, count + 1);
				return true;
			}
		}

		private static Map<String, Long> appSessionMap = new HashMap<String, Long>();

		public synchronized String putAppSession(HttpSession session, Fuser fuser) {
			String loginToken = session.getId() + "_"
					+ Utils.getTimestamp().getTime() + "_" + fuser.getFid();
			this.appSessionMap.put(loginToken, Utils.getTimestamp().getTime());
			return loginToken;
		}

		public boolean isAppLogin(String key, boolean update) {
			Long l = this.appSessionMap.get(key);
			if (l == null) {
				return false;
			} else {
				 Timestamp time = new Timestamp(l) ;
				 if(Utils.getTimestamp().getTime() - time.getTime() <30*3600*1000L ){
					 if(update==true){
						 this.appSessionMap.put(key, Utils.getTimestamp().getTime()) ;
					 }
					 return true ;
				 }else{
					 return false ;
				 }
				//return true;
			}
		}

		public Fuser getAppFuser(String key) {
			Fuser fuser = null;
			try {
				String split[] = key.split("_");
				if (split.length == 3) {
					fuser = this.frontUserService.findById(Integer
							.parseInt(split[2]));
				}
			} catch (Exception e) {
			}

			return fuser;

		}
		public Map<String, Object> setRealTimeData(int id){
			
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(id) ;
			int count1 = ftrademapping.getFcount1() ;
			int count2 = ftrademapping.getFcount2() ;
			Map<String, Object> map = new HashMap<String, Object>() ;
			String sellEntrustsString = this.frontCacheService.getSellDepthMap(id);
			List<String> sellEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(sellEntrustsString, String.class);
			String buyEntrustsString = this.frontCacheService.getBuyDepthMap(id);
			List<String> buyEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(buyEntrustsString, String.class);
			
			String successEntrustsString = this.frontCacheService.getEntrustSuccessMap(id);
			List<String> successEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(successEntrustsString, String.class);
			
			JSONArray successArray = new JSONArray() ;
			JSONArray sellArray = new JSONArray() ;
			JSONArray buyArray = new JSONArray() ;
			if (sellEntrustsList != null) {
				for (int i = 0; i < sellEntrustsList.size() && i < 20; i++){
					String fentrustString = sellEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					JSONObject item = new JSONObject() ;
					item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[0]), count1)+"") ;
					item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[1]), count2)+"") ;
					sellArray.add(item) ;
				}
			}
			
			if (buyEntrustsList != null) {
				for (int i = 0; i < buyEntrustsList.size() && i < 20 ; i++) {
					String fentrustString = buyEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					JSONObject item = new JSONObject() ;
					item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[0]), count1)+"") ;
					item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[1]), count2)+"") ;
					buyArray.add(item) ;
				}
			}
			
			
			if (successEntrustsList != null) {
				for (int i = 0; i < successEntrustsList.size() && i < 20 ; i++) {
					String fentrustString = successEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					JSONObject item = new JSONObject() ;
					item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[0]), count1)+"") ;
					item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[1]), count2)+"") ;
					successArray.add(item) ;
				}
			}
			map.put("BuyMap", buyArray ) ;
			map.put("SellMap", sellArray ) ;
			map.put("BuySuccessMap", successArray) ;
			
			map.put("LatestDealPrize", this.frontCacheService.getLatestDealPrize(id)) ;
			map.put("LowestSellPrize", this.frontCacheService.getLowestSellPrize(id)) ;
			map.put("HighestBuyPrize", this.frontCacheService.getHighestBuyPrize(id)) ;
			
			map.put("OneDayLowest", this.frontCacheService.getLowest(id)) ;
			map.put("OneDayHighest", this.frontCacheService.getHighest(id)) ;
			map.put("OneDayTotal", this.frontCacheService.getTotal(id)) ;
			return map ;
		}
		
		
		
		/**
		 * 从request获取域名
		 * @param request
		 * @return
		 */
		public Pdomain getReqDomain(HttpServletRequest request) {
			//从缓存获取项目方域名列表
			List<Pdomain> pdomainList = (List<Pdomain>) frontConstantMapService.get("pdomainList");
			String reqDomain = NetworkUtil.getDomain(request);
			Pdomain pdomain = null;
			if(null != pdomainList && pdomainList.size() > 0) {
				//List转Map
				Map<String, Pdomain> maps = Maps.uniqueIndex(pdomainList, new Function<Pdomain, String>() {
					@Override
					public String apply(Pdomain pdomain) {
						return pdomain.getCompleteDomain();
					}
				});
				
				pdomain = maps.get(reqDomain);
			}
			
			return pdomain;
		}
		
		
		/**
		 * 根据域名获取市场列表
		 * @param request
		 * @return
		 */
		public List<Ftrademapping> getTrademappingListByDomain(HttpServletRequest request) {
			
			//1.获取域名
			Pdomain pdomain = getReqDomain(request);
			//2.根据域名获取项目方对应的市场
			List<Ftrademapping> ftrademappings = new ArrayList<Ftrademapping>();
			if(null!=pdomain){
				
				String filter = "where fprojectId = " + pdomain.getProjectId().getFid();
				List<Ftrademapping> projectTrademappings = ftradeMappingService.list(0, 0, filter, false);
				
				ftrademappings.addAll(projectTrademappings);
				
				filter = "where projectId.fid = " + pdomain.getProjectId().getFid();
				
				List<Psystemconfig> psystemconfigs = frontPsystemconfigService.list(0, 0, filter, false);
				if(psystemconfigs != null && psystemconfigs.size() > 0){
					for(Psystemconfig psc : psystemconfigs){
						String fidString = psc.getFvalue();
						if(StringUtils.isNotBlank(fidString)){
							String[] fids = fidString.split(",");
							for(String s : fids){
								Ftrademapping tp = ftradeMappingService.findFtrademapping2(Integer.valueOf(s));
								ftrademappings.add(tp);
							}
						}
					}
				}
			}else{
				ftrademappings = this.utilsService.list1(0, 0, " where fstatus=? ", false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
			}
			
			return ftrademappings;
		}
		
		
		//项目方公共校验接口
		protected String virifyProject(Fuser fuser) {
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			
			Pproject pproject = null;
			List<Pproject> pprojectList = frontPprojectService.list(0, 0, filter+"", false);
			if(null != pprojectList && pprojectList.size() > 0) {
				pproject = pprojectList.get(0);
				if(pproject.getType() == PprojectTypeEnum.COMMUNITY) {
					return new JsonResult(ResultCode.FAIL, "账户无权限操作。").toString();
				}
			} else {
				return new JsonResult(ResultCode.FAIL, "您不是项目方账户。").toString();
			}
			
			return null;
		}

}
