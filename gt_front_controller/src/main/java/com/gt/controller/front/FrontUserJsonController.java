package com.gt.controller.front;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.HtmlUtils;

import com.gt.Enum.BankInfoStatusEnum;
import com.gt.Enum.BankInfoWithdrawStatusEnum;
import com.gt.Enum.BankTypeEnum;
import com.gt.Enum.CountLimitTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.Enum.MessageStatusEnum;
import com.gt.Enum.MessageTypeEnum;
import com.gt.Enum.RegTypeEnum;
import com.gt.Enum.SendMailTypeEnum;
import com.gt.Enum.UserStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.comm.MessageValidate;
import com.gt.controller.BaseController;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fmessage;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.WalletMessage;
import com.gt.sdk.AliyunCheck;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.MessageService;
import com.gt.service.admin.SystemArgsService;
import com.gt.service.admin.UserService;
import com.gt.service.front.FrontAccountService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontValidateMapService;
import com.gt.service.front.FrontValidateService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.Device;
import com.gt.util.GoogleAuth;
import com.gt.util.IDCardVerifyUtil;
import com.gt.util.OSSPostObject;
import com.gt.util.Utils;
import com.gt.util.wallet.WalletUtil;

import net.sf.json.JSONObject;

@Controller
public class FrontUserJsonController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrontUserJsonController.class);

	private static final String CLASS_NAME = FrontUserJsonController.class.getSimpleName();

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontValidateMapService frontValidateMapService ;
	@Autowired
	private FvirtualWalletService fvirtualWalletService;	
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService ;
	/** 邮箱或者和手机号码是否重复是否重复
	 * @param type:0手机，1邮箱
	 * @Return 0重复，1正常
	 * */
	@ResponseBody
	@RequestMapping(value="/user/reg/chcekregname")
	public String chcekregname(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") String name,
			@RequestParam(required=false,defaultValue="1") int type,
			@RequestParam(required=false,defaultValue="0") int random
			) throws Exception{		
		JSONObject jsonObject = new JSONObject() ;

		if(type==0){
			//手机账号
			boolean flag = this.frontUserService.isTelephoneExists(name) ;
			if(flag){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phonewexist"));
			}else{
				jsonObject.accumulate("code", 0) ;
			}
		}else{
			//邮箱账号
			boolean flag = this.frontUserService.isEmailExists(name) ;
			if(flag){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.emailwexist"));
			}else{
				jsonObject.accumulate("code", 0) ;
			}
		}
		
		
		return jsonObject.toString() ;
	
	}

	
    /**
     * 二次登录验证
     * @param request
     * @param random 随机数
     * @param loginName 登录名
     * @param codeType 验证码类型
     * @param code 验证码
     * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping(value="/user/codelogin",produces=JsonEncode)
	public String codelogin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false,defaultValue="0") int random,
			@RequestParam(required=false,defaultValue="0") int codeType,//0手机、1邮箱,2谷歌
			@RequestParam(required=false,defaultValue=" ") String code

			) throws Exception{
		String areaCode = "86" ;
		JSONObject jsonObject = new JSONObject() ;
		//用户名密码登录成功时将用户名放入session；二次验证时从session中获取用户名；
		//String loginName=(String) getSession(request).getAttribute("login_name") ;
		Fuser fu= GetSecondLoginSession(request);
		
		if(fu==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"操作异常，请先登录再验证")) ;
			return jsonObject.toString() ;
		}
		String loginName=fu.getFloginName();
		int longLogin = -1;//0是手机，1是邮箱
		if(loginName.matches(Constant.PhoneReg) == true  ){
			longLogin = 0 ;
		}else{
			if(EmailValidator.getInstance().isValid(loginName) == true){
				longLogin = 1 ;
			}
		}
		
		if(longLogin == -1){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.usernameerror")) ;
			return jsonObject.toString() ;
		}
		
		Fuser fusers = this.frontUserService.findById(fu.getFid());
		if(fusers == null){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.noaccount")) ;
			return jsonObject.toString() ;
		}

		String ip = getIpAddr(request) ;
		int limitedCount = 0;
		boolean mobilValidate=false;
		if(codeType==0){
			limitedCount=this.frontValidateService.getLimitCount(ip,CountLimitTypeEnum.TELEPHONE) ;
			System.out.println(fusers.getFtelephone());
			 mobilValidate = validateMessageCode(getIpAddr(request),areaCode,fusers.getFtelephone(), MessageTypeEnum.loginTwo, code) ;
		}else if(codeType==1){
			 mobilValidate = validateMailCode(getIpAddr(request), fusers.getFemail(), SendMailTypeEnum.RegMail, code);
			limitedCount=this.frontValidateService.getLimitCount(ip,CountLimitTypeEnum.REG_MAIL) ;
		}else if(codeType==2){
			limitedCount=this.frontValidateService.getLimitCount(ip,CountLimitTypeEnum.GOOGLE) ;			
			 mobilValidate = GoogleAuth.auth(Long.parseLong(code),fusers.getFgoogleAuthenticator()) ;			
		}
		if(!mobilValidate){			
			if(codeType==0){
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			}else if(codeType==1){
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.REG_MAIL) ;				 
			}else if(codeType==2){
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			}
			this.frontValidateService.updateLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{limitedCount};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"验证码错误"));

			return jsonObject.toString() ;
		}
		if(limitedCount>0){			
				String isCanlogin = frontConstantMapService.get("isCanlogin").toString().trim();
				if(!isCanlogin.equals("1")){
					boolean isCanLogin = false;
					String[] canLoginUsers = frontConstantMapService.get("canLoginUsers").toString().split("#");
					for(int i=0;i<canLoginUsers.length;i++){
						if(canLoginUsers[i].equals(String.valueOf(fusers.getFid()))){
							isCanLogin = true;
							break;
						}
					}
					if(!isCanLogin){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.stopaccess")) ;
						return jsonObject.toString() ;
					}
				}
				
				if(fusers.getFstatus()==UserStatusEnum.NORMAL_VALUE){
					this.frontValidateService.deleteCountLimite(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
					if(fusers.getFopenSecondValidate()){
						SetSession(fusers,request) ;
						//针对IOS手机进行存储Cookie操作，避免重复登录
						if (Device.isIOSDevice(request)){
							String md5Token=this.putKeySession(getSession(request), fusers);
							Cookie token = new Cookie(Utils.getMD5_32_xx(ip), md5Token);
							token.setMaxAge(24 * 60 * 60);// 设置存在时间为24
							token.setPath("/");//设置作用域
							response.addCookie(token);
						}
						jsonObject.accumulate("code", 0) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.logsucc")) ;
						return jsonObject.toString() ;
					//SetSecondLoginSession(request,fuser);
					}else{
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"该账号未开启二次验证")) ;
						return jsonObject.toString() ;
					}
				}else{
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.accountfrozen")) ;
					return jsonObject.toString() ;
				}
			
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent")) ;
			return jsonObject.toString() ;
		}
		
	 }
	/* @Param　regType:0手机，1email
	 * @Return 1正常，-2名字重复，-4邮箱格式不对，-5客户端你没打开cookie
	 * */
	@ResponseBody
	@RequestMapping(value="/user/reg/index",produces=JsonEncode)
	public String regIndex(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int random,
			@RequestParam(required=false,defaultValue="0") String password,
			@RequestParam(required=false,defaultValue="0") String regName,
			@RequestParam(required=false,defaultValue="0") int regType,//0手机、1邮箱
			/*@RequestParam(required=false,defaultValue="0") String invite_code,*/
			@RequestParam(required=false,defaultValue="0") String ecode,
			@RequestParam(required=true,defaultValue="0") String phoneCode
			) throws Exception{
		String areaCode = "86" ;
		
		JSONObject jsonObject = new JSONObject() ;
		
		//人机验证
		boolean result = new AliyunCheck().checkAliyunICMode(request);
		if(!result) {
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"验证失败")) ;
			return jsonObject.toString() ;
		}
		
		String phone = HtmlUtils.htmlEscape(regName);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		String isOpenReg = frontConstantMapService.get("isOpenReg").toString().trim();
		if(!isOpenReg.equals("1")){
			jsonObject.accumulate("code", -888) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.suspendreg")) ;
			return jsonObject.toString() ;
		}
		
		password = HtmlUtils.htmlEscape(password.trim());
		if(password == null || password.length() <6){
			jsonObject.accumulate("code", -11) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.smslenw")) ;
			return jsonObject.toString() ;
		}
		//邮箱
		if(regType==0){
			//手机注册
			
			boolean flag1 = this.frontUserService.isTelephoneExists(regName) ;
			if(flag1){
				jsonObject.accumulate("code", -22) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phonebeenreg")) ;
				return jsonObject.toString() ;
			}
			
			if(!phone.matches(Constant.PhoneReg)){
				jsonObject.accumulate("code", -22) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneformaterror")) ;
				return jsonObject.toString() ;
			}
			
			boolean mobilValidate = validateMessageCode(getIpAddr(request),areaCode,phone, MessageTypeEnum.REG_CODE, phoneCode) ;
			if(!mobilValidate){
				jsonObject.accumulate("code", -20) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.smserror")) ;
				return jsonObject.toString() ;
			}
			
		}else{
			//邮箱注册
			boolean flag = this.frontUserService.isEmailExists(regName) ;
			if(flag){
				jsonObject.accumulate("code", -12) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.emailexist")) ;
				return jsonObject.toString() ;
			}
			
			boolean mailValidate = validateMailCode(getIpAddr(request), phone, SendMailTypeEnum.RegMail, ecode);
			if(!mailValidate){
				jsonObject.accumulate("code", -10) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.emailcodeerror")) ;
				return jsonObject.toString() ;
			}
			
			if(!EmailValidator.getInstance().isValid(regName)){
				jsonObject.accumulate("code", -12) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.emailformaterror")) ;
				return jsonObject.toString() ;
			}
			
		}
		
		
		//推广
		Fuser intro = null ;
		
		try {
			String intro_user = request.getParameter("intro_user") ;
			if(intro_user!=null && !"".equals(intro_user.trim())){
				List<Fuser> introList = this.frontUserService.findUserByProperty("fuserNo", intro_user) ;
				if(introList!=null&&introList.size()>0){
					intro=introList.get(0);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(intro==null){
			String isMustIntrol = frontConstantMapService.get("isMustIntrol").toString().trim();
			if(isMustIntrol.equals("1")){
				jsonObject.accumulate("code", -200) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.invitcode")) ;
				return jsonObject.toString();
			}
		}
		
		
		Fuser fuser = new Fuser() ;
		if(intro!=null){
			fuser.setfIntroUser_id(intro) ;
		}
		fuser.setFintrolUserNo(null);
		
	/*	try {
			String regfrom = request.getParameter("regfrom");
			if(regfrom!=null && "agon".equals(regfrom.trim())){
				fuser.setFregfrom("agon");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		
		if(regType == 0){
			//手机注册
			fuser.setFregType(RegTypeEnum.TEL_VALUE);
			fuser.setFtelephone(phone);
			fuser.setFareaCode(areaCode);
			fuser.setFisTelephoneBind(true);
			
			fuser.setFnickName(phone) ;
			fuser.setFloginName(phone) ;
		}else{
			fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
			fuser.setFemail(regName) ;
			fuser.setFisMailValidate(true) ;
			fuser.setFnickName(regName.split("@")[0]) ;
			fuser.setFloginName(regName) ;
		}
		
		fuser.setSalt(Utils.getUUID());
		fuser.setFregisterTime(Utils.getTimestamp()) ;
		fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt())) ;
		fuser.setFtradePassword(null) ;
		String ip = getIpAddr(request) ;
		
		fuser.setFregIp(ip);
		fuser.setFlastLoginIp(ip);
		fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
		fuser.setFlastLoginTime(Utils.getTimestamp()) ;
		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
		//是否拥有APP权限
		fuser.setFgrade(0);
		if(Constant.openSecondValidate.equals("true")){
			fuser.setFopenSecondValidate(true);
		}else{
			fuser.setFopenSecondValidate(false);
		}
		
		
		Fuser saveUser = null ;
		try {
			saveUser = this.frontUserService.saveRegister(fuser) ;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(regType==0){
				String key1 = ip+"message_"+MessageTypeEnum.REG_CODE ;
				String key2 = ip+"_"+phone+"_"+MessageTypeEnum.REG_CODE ;
				this.frontValidateMapService.removeMessageMap(key1);
				this.frontValidateMapService.removeMessageMap(key2);
			}else{
				String key1 = ip+"mail_"+SendMailTypeEnum.RegMail ;
				String key2 = ip+"_"+phone+"_"+SendMailTypeEnum.RegMail ;
				this.frontValidateMapService.removeMailMap(key1);
				this.frontValidateMapService.removeMailMap(key2);
			}
		}
		
		
		if(saveUser!=null){
			this.SetSession(saveUser,request) ;

			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.succreg")) ;
			return jsonObject.toString() ;
		
		}else{
			jsonObject.accumulate("code", -10) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror")) ;
			return jsonObject.toString() ;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/user/login/index",produces=JsonEncode)
	public String loginIndex(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false,defaultValue="0")int random,
			@RequestParam(required=true) String loginName,
			@RequestParam(required=true) String password
			) throws Exception {
		//System.out.println("loginName="+loginName+"\t password="+password);
		JSONObject jsonObject = new JSONObject() ;
		
		//行为式验证码验证
//		boolean result = verifyCaptcha(request);
		boolean result = new AliyunCheck().checkAliyunICMode(request);
		if(!result) {
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"验证失败")) ;
			return jsonObject.toString() ;
		}
		
		int longLogin = -1;//0是手机，1是邮箱
		if(loginName.matches(Constant.PhoneReg)){
			longLogin = 0 ;
		}else{
			if(EmailValidator.getInstance().isValid(loginName)){
				longLogin = 1 ;
			}
		}
		
		if(longLogin == -1){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.usernameerror")) ;
			return jsonObject.toString() ;
		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("floginName", loginName);
		if(fusers == null || fusers.size() != 1){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.noaccount")) ;
			return jsonObject.toString() ;
		}

		Fuser fuser = new Fuser() ;
		fuser.setFloginName(loginName);
		fuser.setFloginPassword(password) ;
		fuser.setSalt(fusers.get(0).getSalt());
		String ip = getIpAddr(request) ;
		int limitedCount = this.frontValidateService.getLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
		if(limitedCount>0){
			
			fuser = this.frontUserService.updateCheckLogin(fuser, ip,longLogin==1);
			if(fuser!=null){
				System.out.println(frontConstantMapService.get("isCanlogin"));
				String isCanlogin = frontConstantMapService.get("isCanlogin").toString().trim();
				if(!isCanlogin.equals("1")){
					boolean isCanLogin = false;
					String[] canLoginUsers = frontConstantMapService.get("canLoginUsers").toString().split("#");
					for(int i=0;i<canLoginUsers.length;i++){
						if(canLoginUsers[i].equals(String.valueOf(fuser.getFid()))){
							isCanLogin = true;
							break;
						}
					}
					if(!isCanLogin){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.stopaccess")) ;
						this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_LOGIN, "登录异常：平台不允许该用户访问");
						return jsonObject.toString() ;
					}
				}
				
				if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
					this.frontValidateService.deleteCountLimite(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
					if(fuser.getFopenSecondValidate()){
						//SetSecondLoginSession(request,fuser);
						//用户名密码登录成功时将用户名放入session；二次验证时从session中获取用户名；
						SetSecondLoginSession(request,fuser);
						//getSession(request).setAttribute("login_name", loginName) ;
						if(fuser.getFgoogleBind()){
							jsonObject.accumulate("ValidateType",2) ;
						}else if(fuser.isFisTelephoneBind()&&longLogin==0){
							jsonObject.accumulate("ValidateType",0) ;
						}else if(fuser.getFisMailValidate()&&longLogin==1){
							jsonObject.accumulate("ValidateType",1) ;
						}else{
							jsonObject.accumulate("code", -1) ;
							jsonObject.accumulate("msg", "数据异常") ;
							return jsonObject.toString() ;
						}
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.logsucc")) ;
						jsonObject.accumulate("code", 102) ;
						return jsonObject.toString() ;
					}else{
						SetSession(fuser,request) ;
						//针对IOS手机进行存储Cookie操作，避免重复登录
						if (Device.isIOSDevice(request)){
							String md5Token=this.putKeySession(getSession(request), fuser);
							Cookie token = new Cookie(Utils.getMD5_32_xx(ip), md5Token);
							token.setMaxAge(24 * 60 * 60);// 设置存在时间为24
							token.setPath("/");//设置作用域
							response.addCookie(token);
						}
						jsonObject.accumulate("code", 101) ;
						
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.logsucc")) ;
						return jsonObject.toString() ;
					}
				}else{
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.accountfrozen")) ;
					this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_LOGIN, "登录异常：账户冻结");
					return jsonObject.toString() ;
				}
			}else{
				//错误的用户名或密码
				if(limitedCount==Constant.ErrorCountLimit){
					jsonObject.accumulate("code", -2) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.errorpwdfg")) ;
				}else{
					jsonObject.accumulate("code", -2) ;
					Object[] param = new Object[]{limitedCount};
					jsonObject.accumulate("msg", getLocaleMessage(request,param,"json.user.errorpwdfg")) ;
					this.frontUserService.updateUserLog(fusers.get(0), ip, LogTypeEnum.User_LOGIN, "登录异常：账密错误超过"+limitedCount+"次");
				}
				this.frontValidateService.updateLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_LOGIN, "登录异常：频繁登录操作");
			return jsonObject.toString() ;
		}
	}
	
	/**
	 * 设置虚拟币提现地址
	 * @param phoneCode 手机验证码
	 * @param symbol 币种类型
	 * @param withdrawAddr 虚拟币提币地址
	 * @param withdrawBtcPass 交易密码
	 * @param totpCode 谷歌验证码
	 * @param remark 备注
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/modifyWithdrawBtcAddr")
	public String modifyWithdrawBtcAddr(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)String withdrawAddr,
			@RequestParam(required=true)String withdrawBtcPass,
			@RequestParam(required=false,defaultValue="REMARK")String withdrawRemark
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
	
		withdrawAddr = HtmlUtils.htmlEscape(withdrawAddr.trim());
		withdrawRemark = HtmlUtils.htmlEscape(withdrawRemark.trim());
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		String code=actionSecurityCheck(request, fuser, true, false, true, withdrawBtcPass, true, phoneCode, totpCode, MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT,LogTypeEnum.User_BTC, "修改提币地址异常","msg");
		if(!code.equals("ok")){
			return code;
		}
		
		
		if(withdrawRemark.length() >100){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.user.remarklong")) ;
			return jsonObject.toString() ;
		}
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				|| !fvirtualcointype.isFIsWithDraw()){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.user.nocoin")) ;
			return jsonObject.toString() ;
		}
		
		//提币地址不能为本人在站内的充值地址
		Fvirtualaddress fvaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype);
		if(null!=fvaddress) {
			if(!fvirtualcointype.isFisBts()) {
				if(fvaddress.getFadderess().toLowerCase().equals(withdrawAddr.toLowerCase())) {
					jsonObject.accumulate("code", -4) ;
					jsonObject.accumulate("msg",getLocaleMessage(request,null,"收币地址不能为本人站内充值地址")) ;
					return jsonObject.toString() ;
				}
			}else {
				if(fvirtualcointype.getMainAddr().toLowerCase().equals(withdrawAddr.toLowerCase()) && fvaddress.getFadderess().toLowerCase().equals(withdrawRemark.toLowerCase())) {
					jsonObject.accumulate("code", -4) ;
					jsonObject.accumulate("msg",getLocaleMessage(request,null,"收币地址不能为本人站内充值地址")) ;
					return jsonObject.toString() ;
				}
			}
		}
		
		//钱包核验地址，确保正确
		/*WalletMessage wmsg = new WalletMessage();
		wmsg.setIP(fvirtualcointype.getFip());
		wmsg.setPORT(fvirtualcointype.getFport());
		wmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
		wmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
		wmsg.setISERC20(false);
		wmsg.setCONTRACT("");
		wmsg.setDECIMALS(fvirtualcointype.getFdecimals());
		WalletUtil wallet =  WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), wmsg);
		
		try{
			if(!wallet.validateAddress(withdrawAddr)){
				jsonObject.accumulate("code", -4) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.user.errorwithdrawaddr")) ;
				return jsonObject.toString() ;
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg","Info:"+getLocaleMessage(request,null,"json.user.errorwithdrawaddr")) ;
			return jsonObject.toString() ;
		}*/
		
		
		
		String ip = getIpAddr(request) ;
		
		FvirtualaddressWithdraw fvirtualaddressWithdraw = new FvirtualaddressWithdraw();
		fvirtualaddressWithdraw.setFadderess(withdrawAddr) ;
		fvirtualaddressWithdraw.setFcreateTime(Utils.getTimestamp());
		fvirtualaddressWithdraw.setFremark(withdrawRemark);
		fvirtualaddressWithdraw.setFuser(fuser);
		fvirtualaddressWithdraw.setFvirtualcointype(fvirtualcointype);
		try {
			this.frontVirtualCoinService.updateFvirtualaddressWithdraw(fvirtualaddressWithdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.success")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "修改提币地址成功："+withdrawAddr);
		} catch (Exception e) {
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.networkanomaly")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "修改提币地址失败");
		}finally{
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
		}
 		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/user/detelCoinAddress")
	public String detelCoinAddress(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		String code=actionSecurityCheck(request, fuser, false, false, false, "", false, "", "", MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT,LogTypeEnum.User_BTC, "删除提币地址成功","msg");
		if(!code.equals("ok")){
			return code;
		}
		
		FvirtualaddressWithdraw virtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(fid);
		if(virtualaddressWithdraw == null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.norecord")) ;
			return jsonObject.toString() ;
		}
		if(fuser.getFid() != virtualaddressWithdraw.getFuser().getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString() ;
		}
		//成功
		try {
			this.frontVirtualCoinService.updateDelFvirtualaddressWithdraw(virtualaddressWithdraw);
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success")) ;
			this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_BTC, "删除提币地址成功："+virtualaddressWithdraw.getFadderess());
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
			this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_BTC, "删除提币地址失败");
		}
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping(value="/user/validatePhone",produces={JsonEncode})
	public String validatePhone(
			HttpServletRequest request,
			@RequestParam(required=true)int isUpdate,//0是绑定手机，1是换手机号码
			@RequestParam(required=true)String areaCode,
			@RequestParam(required=true)String imgcode,
			@RequestParam(required=true)String phone,
			@RequestParam(required=true)String oldcode,
			@RequestParam(required=true)String newcode,
			@RequestParam(required=false,defaultValue="0")String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		
		areaCode = areaCode.replace("+", "");
		if(!phone.matches("^\\d{10,14}$")){//手機格式不對
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneformaterror"));
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(isUpdate ==0){
			if(fuser.isFisTelephoneBind()){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.boundphone"));
				return jsonObject.toString() ;
			}else{
				if (fuser.getFregType()==0){
					//注册类型为手机用户只能进行解绑操作
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.nbp")) ;
					return jsonObject.toString() ;
						
				}
			}
		}else{
			if(!fuser.isFisTelephoneBind()){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.nobindphone"));
				return jsonObject.toString() ;
			}
		}

		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(google_limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent"));
			return jsonObject.toString() ;
		}
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent"));
			return jsonObject.toString() ;
		}
		//绑定时不需要验证解绑手机验证码，
		String code=actionSecurityCheck(request, fuser, true, false, false, null, false, null, null,  MessageTypeEnum.JIEBANG_MOBILE, LogTypeEnum.User_BIND_PHONE, "解绑手机验证码错误", "msg");		
		if(!code.equals("ok")){
			return code;
		}
	
		if(fuser.getFgoogleBind()){
			boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
			if(!googleAuth){
				//谷歌驗證失敗
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				jsonObject.accumulate("code", -1) ;
				Object[] params = new Object[]{google_limit};
				jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentgcode"));

				return jsonObject.toString() ;
			}else{
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
			}
		}
		
		if(isUpdate ==1){
			if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.JIEBANG_MOBILE, oldcode)){
				//手機驗證錯誤
				 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate("code", -1) ;
				Object[] params = new Object[]{tel_limit};
				jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentmcode"));

				return jsonObject.toString() ;
			}else{
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			}
		}
			
			if(validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.BANGDING_MOBILE, newcode)){
				//判斷手機是否被綁定了
				List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
				if(fusers.size()>0){//手機號碼已經被綁定了
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneexist"));
					return jsonObject.toString() ;
				}
				
				
				if(vcodeValidate(request, imgcode) == false ){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.verinfo"));
					return jsonObject.toString() ;
				}
				
				fuser.setFareaCode(areaCode) ;
				fuser.setFtelephone(phone) ;
				if(fuser.getFregType() == RegTypeEnum.TEL_VALUE){
					fuser.setFloginName(phone);
				}
				fuser.setFisTelephoneBind(true) ;
				fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
				try {
					this.frontUserService.updateFUser(fuser,LogTypeEnum.User_BIND_PHONE,ip) ;
				} catch (Exception e) {
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
					return jsonObject.toString() ;
				}finally{
					//成功
					this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.BANGDING_MOBILE);
					this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.JIEBANG_MOBILE);
				}
				

				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.bindsucc"));
				return jsonObject.toString() ;
			}else{
				//手機驗證錯誤
				 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate("code", -1) ;

				Object[] params = new Object[]{tel_limit};
				jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentmcode"));

				return jsonObject.toString() ;
			}
		
	}

	
	@ResponseBody
	@RequestMapping("/user/modifyPwd")
	public String modifyPwd(
			HttpServletRequest request,
			@RequestParam(required=true) String newPwd,
			@RequestParam(required=false,defaultValue="0") String originPwd,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")int pwdType,
			@RequestParam(required=true) String reNewPwd,
			@RequestParam(required=false,defaultValue="0")String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
	Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
	
		
		if(fuser.getFregType()==0){
			//注册类型为手机的账户，必须要绑定手机号码
			if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
				jsonObject.accumulate("code", -10) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.ntp")) ;
				return jsonObject.toString() ;
			}
		}else{
			//对注册类型为邮箱的账户，必须要绑定邮箱地址
			if(null==fuser.getFemail() || fuser.getFemail().equals("")){
				jsonObject.accumulate("code", -10) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.nex")) ;
				return jsonObject.toString() ;
			}
		}
		
		if(!newPwd.equals(reNewPwd)){
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.pwdtwodif")) ;
			return jsonObject.toString() ;//两次输入密码不一样
		}
		
		
		
			if(pwdType==0){
				String newPass = Utils.MD5(newPwd,fuser.getSalt());
				if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0 && fuser.getFtradePassword().equals(newPass)){
					jsonObject.accumulate("code", -6) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.loginsametran")) ;
					return jsonObject.toString() ;
				}
				//修改登陆密码
				if(!fuser.getFloginPassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
					jsonObject.accumulate("code", -5) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.oldloginerror")) ;
					return jsonObject.toString() ;//原始密码错误
				}
				
				 String code=actionSecurityCheck(request, fuser, true, false, false, null, true, phoneCode, totpCode, MessageTypeEnum.CHANGE_LOGINPWD, LogTypeEnum.User_UPDATE_LOGIN_PWD, "修改密码异常", "msg");		
					if(!code.equals("ok")){
						return code;
					}
			}else{
				String newPass = Utils.MD5(newPwd,fuser.getSalt());
				if(fuser.getFloginPassword().equals(newPass)){
					jsonObject.accumulate("code", -6) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.transamelogin")) ;
					return jsonObject.toString() ;
				}
				//修改交易密码
				if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
					if(!fuser.getFtradePassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
						jsonObject.accumulate("code", -5) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.oldtranerror")) ;
						return jsonObject.toString() ;//原始密码错误
					}
				}
				 String code=actionSecurityCheck(request, fuser, true, false, false, null, true, phoneCode, totpCode, MessageTypeEnum.CHANGE_TRADEPWD, LogTypeEnum.User_UPDATE_TRADE_PWD, "修改密码异常", "msg");		
					if(!code.equals("ok")){
						return code;
					}
			}
			
			if(pwdType==0){
				//修改交易密码
				if(fuser.getFloginPassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
					jsonObject.accumulate("code", -10) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.newloginsame")) ;
					return jsonObject.toString() ;
				}
			}else{
				//修改交易密码
				if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0
						&&fuser.getFtradePassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
					jsonObject.accumulate("code", -10) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.newtransame")) ;
					return jsonObject.toString() ;
				}
			}
		
		String ip = getIpAddr(request) ;
		
		try {
			if(pwdType==0){
				//修改登陆密码
				fuser.setFloginPassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				this.frontUserService.updateFUser(fuser,LogTypeEnum.User_UPDATE_LOGIN_PWD,ip) ;
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
			}else{
				
				//修改交易密码
			
				int logType=0;
				if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
					logType = LogTypeEnum.User_UPDATE_TRADE_PWD;
				}else{
					logType = LogTypeEnum.User_SET_TRADE_PWD;
					
					//设置交易密码奖励发放
					int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"设置交易密码奖励");
					if (sendcount==0 ) {
						Fintrolinfo introlInfo = null;
						Fvirtualwallet fvirtualwallet = null;
						String[] auditSendCoin = frontConstantMapService.getString("tradePassWordSendCoin").split("#");
						int coinID = Integer.parseInt(auditSendCoin[0]);
						double coinQty = Double.valueOf(auditSendCoin[1]);
						Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinID);
						if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
							fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid());
							fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
							introlInfo = new Fintrolinfo();
							introlInfo.setFcreatetime(Utils.getTimestamp());
							introlInfo.setFiscny(false);
							introlInfo.setFqty(coinQty);
							introlInfo.setFuser(fuser);
							introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_CANDY);
							introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
							introlInfo.setFtitle("设置交易密码奖励");
						}
						this.userService.updateObj(null, introlInfo, fvirtualwallet);
					}
										
				}
				fuser.setFtradePassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				
				this.frontUserService.updateFUser(fuser,logType,ip) ;
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
			}
		} catch (Exception e) {
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
			if(pwdType==0){
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_UPDATE_LOGIN_PWD, "密码操作失败");
			}else{
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_UPDATE_TRADE_PWD, "密码操作失败");
			}
			return jsonObject.toString() ;
		}finally{
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_LOGINPWD);
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_TRADEPWD);
		}
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success")) ;
		return jsonObject.toString() ;
	}
	
	
	/*
	 * 查看谷歌密匙
	 * */
	@ResponseBody
	@RequestMapping(value="/user/getGoogleTotpKey")
	public String getGoogleTotpKey(
			HttpServletRequest request,
			@RequestParam(required=true) String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
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
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo")+"(error code:nex)") ;
				return jsonObject.toString() ;
			}
		}
		
		String ip = getIpAddr(request) ;
		int limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
		if(limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent")) ;
			return jsonObject.toString() ;
		}else{
			if(fuser.getFgoogleBind()){
				if(GoogleAuth.auth(Long.parseLong(totpCode), fuser.getFgoogleAuthenticator())){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					
					jsonObject.accumulate("qecode", fuser.getFgoogleurl()) ;
					jsonObject.accumulate("code", 0) ;
					jsonObject.accumulate("totpKey", fuser.getFgoogleAuthenticator()) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.succver")) ;
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate("code", -1) ;
					
					Object[] params = new Object[]{limit};
					jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentgcode"));

					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.nogoogle")) ;
				return jsonObject.toString() ;
			}
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/user/validateIdentity",produces={JsonEncode})
	public String validateIdentity(
			HttpServletRequest request,
			@RequestParam(required=true)String identityNo,
			@RequestParam(required=true,defaultValue="0")int identityType,
			@RequestParam(required=true)String address,
			@RequestParam(required=true)String realName
			) throws Exception {
		LOGGER.info(CLASS_NAME + " validateIdentity,identityNo:{},realName:{}", identityNo, realName);
		JSONObject js = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		realName = HtmlUtils.htmlEscape(realName);
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(fuser.getFregType()==0){
			//注册类型为手机的账户，必须要绑定手机号码
			if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
				jsonObject.accumulate("code", 1) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.ntp")) ;
				return jsonObject.toString() ;
			}
		}else{
			//对注册类型为邮箱的账户，必须要绑定邮箱地址
			if(null==fuser.getFemail() || fuser.getFemail().equals("")){
				jsonObject.accumulate("code", 1) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo")+"(error code:nex)") ;
				return jsonObject.toString() ;
			}
		}
		
		if(Utils.isNumeric(address)==false) {
			jsonObject.accumulate("code", 1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect"));
			return jsonObject.toString();
		}
		
		if(fuser.getFpostRealValidate()){
			jsonObject.accumulate("code", 1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.patientaudit"));
			return jsonObject.toString();
		}
		identityNo = identityNo.toLowerCase();
		//把中国身份证进行特别校验
		if (address.equals("86")){
			 String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
		                "3", "2" };  
		        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
		                "9", "10", "5", "8", "4", "2" };  
				if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
					jsonObject.accumulate("code", 1);
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idlenerror"));
					LOGGER.info(CLASS_NAME + " validateIdentity,身份证号码长度应该为15位或18位");
					return jsonObject.toString();
				}
				
				String Ai = "";
		        if (identityNo.length() == 18) {  
		            Ai = identityNo.substring(0, 17);  
		        } else if (identityNo.length() == 15) {  
		            Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);  
		        }
				LOGGER.info(CLASS_NAME + " validateIdentity,Ai:{}", Ai);
		        if (Utils.isNumeric(Ai) == false) {  
		            jsonObject.accumulate("code", 1);
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect"));
					return jsonObject.toString();
		        }
		        // ================ 出生年月是否有效 ================  
		        String strYear = Ai.substring(6, 10);// 年份  
		        String strMonth = Ai.substring(10, 12);// 月份  
		        String strDay = Ai.substring(12, 14);// 月份  
		        if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {  
		        	jsonObject.accumulate("code", 1);
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect"));
					return jsonObject.toString();
		        }  
		      //===============提取注册最大年龄限制，默认是60，最高不超过75，最低不低于50；===============
		        GregorianCalendar gc = new GregorianCalendar();  
		        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		        
		        long mrYear = 60;
		        String maxRegYear = frontConstantMapService.getString("maxRegYear");
		        if(null!=maxRegYear && !maxRegYear.equals("")) {
		        	try {
		        		mrYear = Long.parseLong(maxRegYear);
		        	}catch (NumberFormatException e) {
		        		mrYear = 60;
			        }
		        	if(mrYear>75) {mrYear=75;}
		        	if(mrYear<50) {mrYear=50;}
		        }
		        try {
		            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > mrYear  
		                    || (gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) <18) {
		            	jsonObject.accumulate("code", 1);
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.yearrange"));
						return jsonObject.toString();
		            }
		        } catch (NumberFormatException e) {
		            e.printStackTrace();
		        }
		        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
		        	jsonObject.accumulate("code", 1);
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect"));
					return jsonObject.toString();
		        }
		        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
		        	jsonObject.accumulate("code", 1);
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect"));
					return jsonObject.toString();
		        }  
		        // =====================(end)=====================  
		  
		        // ================ 地区码时候有效 ================  
		        Hashtable h = Utils.getAreaCode();
		        if (h.get(Ai.substring(0, 2)) == null) {
		        	jsonObject.accumulate("code", 1);
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect"));
					return jsonObject.toString();
		        }
		        // ==============================================
		  
		        // ================ 判断最后一位的值 ================  
		        int TotalmulAiWi = 0;
		        for (int i = 0; i < 17; i++) {
		            TotalmulAiWi = TotalmulAiWi
		                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
		                    * Integer.parseInt(Wi[i]);
		        }
		        int modValue = TotalmulAiWi % 11;
		        String strVerifyCode = ValCodeArr[modValue];
		        Ai = Ai + strVerifyCode;
		        
		        if (identityNo.length() == 18) {
		            if (Ai.equals(identityNo) == false) {
		            	jsonObject.accumulate("code", 1);
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect"));
						return jsonObject.toString();
		            }
		        } else {
		            return "";  
		        }
		}
			if (realName.trim().length() > 32) {
				jsonObject.accumulate("code", 1);
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.realnameill"));
				return jsonObject.toString();
			}
			
			String sql = "where fidentityNo='"+identityNo+"'";
			int count = this.adminService.getAllCount("Fuser", sql);
			LOGGER.info(CLASS_NAME + " validateIdentity,从数据库中查询身份证号，返回结果集count:{}", count);
			if(count >0){
				jsonObject.accumulate("code", 1);
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idexist"));
				return jsonObject.toString();
			}
		
		//是否通过KYC认证，避免国外用户提交KYC1，未经验证的情况
		boolean isTrue =false;
		
		if(Constant.IS_APPKEY.equals("true") && address.equals("86")){
			IDCardVerifyUtil verifyUtil = new IDCardVerifyUtil();
			isTrue = verifyUtil.isRealPerson(realName, identityNo);
			LOGGER.info(CLASS_NAME + " validateIdentity,调verifyUtil.isRealPerson进行实名认证，返回结果isTrue:{}", isTrue);
			if(!isTrue){
				jsonObject.accumulate("code", 1);
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.nameidincorrect"));
				return jsonObject.toString();
			}
		}
		
		fuser.setFidentityType(identityType) ;
		fuser.setFidentityNo(identityNo) ;
		fuser.setFrealName(realName) ;
		fuser.setFpostRealValidate(true) ;
		fuser.setFareaCode(address);
		fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
		if(isTrue){
			//通过国内实名认证则进行标记
			fuser.setFhasRealValidate(true);
			fuser.setFhasRealValidateTime(Utils.getTimestamp());
			fuser.setFisValid(true);
		}
		String ip = getIpAddr(request) ;
		try {
			LOGGER.info(CLASS_NAME + " validateIdentity,修改用户信息,实名认证成功");
			this.frontUserService.updateFUser(fuser, LogTypeEnum.User_CERT,ip) ;
			if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
				getSession(request).setAttribute("login_user", fuser) ;
			}
			if(isTrue){
				//KYC1认证奖励，只奖励中国大陆用户
				int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"KYC1认证奖励");
				if (sendcount==0 ) {
					Fintrolinfo introlInfo = null;
					Fvirtualwallet fvirtualwallet = null;
					String[] auditSendCoin = frontConstantMapService.getString("kyc1SendCoin").split("#");
					int coinID = Integer.parseInt(auditSendCoin[0]);
					double coinQty = Double.valueOf(auditSendCoin[1]);
					//如果设置了KYC1认证奖励，且币种存在的话，则进行奖励
					Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinID);
					if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid());
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(coinQty);
						introlInfo.setFuser(fuser);
						introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_REG);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						introlInfo.setFtitle("KYC1认证奖励");
					}
					this.userService.updateObj(null, introlInfo, fvirtualwallet);
				}
							
				LOGGER.info(CLASS_NAME + " validateIdentity,修改用户钱包信息成功，增加了奖励金额");
			}
			this.SetSession(fuser,request) ;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			js.accumulate("code", 1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.idexist"));
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_CERT, "实名认证失败：身份证号码重复"+identityNo);
			return js.toString();
		}
		if(isTrue){
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.idsucc"));
		}else{
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.idupload"));
		}
		//实名认证奖励
		
		
		
		js.accumulate("code", 0);
		return js.toString();
	}
	
	
	

	@ResponseBody
	@RequestMapping(value="user/validateKyc",produces={JsonEncode})
	public String validateKyc(
			HttpServletRequest request,
			@RequestParam(required=true)String identityUrlOn,
			@RequestParam(required=true)String identityUrlOff,
			@RequestParam(required=true)String identityHoldUrl
		) throws Exception{
        JSONObject js = new JSONObject();
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        
        if(fuser.getFregType()==0){
			//注册类型为手机的账户，必须要绑定手机号码
			if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
				js.accumulate("code", -1) ;
				js.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.ntp")) ;
				return js.toString() ;
			}
		}else{
			//对注册类型为邮箱的账户，必须要绑定邮箱地址
			if(null==fuser.getFemail() || fuser.getFemail().equals("")){
				js.accumulate("code", -1) ;
				js.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo.nex")) ;
				return js.toString() ;
			}
		}
        
        if(fuser.isFpostImgValidate()){
        	js.accumulate("code", -1);
        	js.accumulate("msg", getLocaleMessage(request,null,"json.user.uppatient"));
        	return js.toString();
        }
        if(fuser.isFhasImgValidate()){
        	js.accumulate("code", -1);
        	js.accumulate("msg", getLocaleMessage(request,null,"json.user.reviewednoupload"));
        	return js.toString();
        }
        identityUrlOn = HtmlUtils.htmlEscape(identityUrlOn);
        identityUrlOff = HtmlUtils.htmlEscape(identityUrlOff);
        identityHoldUrl = HtmlUtils.htmlEscape(identityHoldUrl);
        fuser.setFpostImgValidate(true);
        fuser.setFpostImgValidateTime(Utils.getTimestamp());
        fuser.setfIdentityPath(identityUrlOn);
        fuser.setfIdentityPath2(identityUrlOff);
        fuser.setfIdentityPath3(identityHoldUrl);
        
		try {
			this.frontUserService.updateFuser(fuser);
		} catch (Exception e) {
			e.printStackTrace();
			js.accumulate("code", -1);
        	js.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror"));
        	this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_CERT, "提交KYC2申请失败");
        	return js.toString();
		}
		js.accumulate("msg", getLocaleMessage(request,null,"json.user.uploadedpatient"));
		this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_CERT, "提交KYC2申请成功");
		js.accumulate("code", 0);
		return js.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value="common/upload",produces={"text/html;charset=UTF-8"})
	public String upload(
			HttpServletRequest request
		) throws Exception{
		
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request ;
		MultipartFile multipartFile = mRequest.getFile("file");
		InputStream inputStream = multipartFile.getInputStream() ;
		String realName = multipartFile.getOriginalFilename() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(mRequest).getFid());
		if(realName!=null && realName.trim().toLowerCase().endsWith("jsp")){
			return "" ;
		}
		 if(fuser.isFpostImgValidate()){
	        return "";
	    }
        if(fuser.isFhasImgValidate()){
        	return "";
        }
		double size = multipartFile.getSize()/1000d;
		if(size > 5120d){
			return "";
		}
		String[] nameSplit = realName.split("\\.") ;
		String ext = nameSplit[nameSplit.length-1] ;
		if(ext!=null && !ext.trim().toLowerCase().endsWith("jpg")
				&& !ext.trim().toLowerCase().endsWith("bmp")
				 && !ext.trim().toLowerCase().endsWith("png")
				 && !ext.trim().toLowerCase().endsWith("jpeg")
				 && !ext.trim().toLowerCase().endsWith("gif")){
			return "";
		}
		
		String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
		String fileName = Utils.getRandomImageName()+"."+ext;
		Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
		JSONObject resultJson = new JSONObject() ;
		resultJson.accumulate("code",0) ;
		if(Constant.IS_OPEN_OSS.equals("false")){
			resultJson.accumulate("resultUrl","/"+Constant.uploadPicDirectory+"/"+fileName) ;
		}else{
			resultJson.accumulate("resultUrl",OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName) ;
		}
		
		return resultJson.toString();
	}
	
	//短信发送，验证码发送
	@ResponseBody
	@RequestMapping(value="/user/sendMsg")
	public String sendMsg(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		String areaCode = "86" ;
		String phone = request.getParameter("phone") ;
	/*	String vcode = request.getParameter("vcode") ;*/
		LOGGER.info(CLASS_NAME + " sendMsg,入参明细phone:{},type:{}", phone,  type);
		//注册类型免登陆可以发送
		JSONObject jsonObject = new JSONObject() ;
		LOGGER.info(CLASS_NAME + " sendMsg,获取请求用户对象GetSession(request)=" + GetSession(request));
		if(type != MessageTypeEnum.REG_CODE &&type != MessageTypeEnum.FIND_PASSWORD && GetSession(request)==null){
			LOGGER.info(CLASS_NAME + " sendMsg,校验不通过");
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}
		
		if(type<MessageTypeEnum.BEGIN || type>MessageTypeEnum.END){
			LOGGER.info(CLASS_NAME + " sendMsg,非法提交");
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}
		
		//注册需要验证码
		/*if(type == MessageTypeEnum.REG_CODE||type == MessageTypeEnum.FIND_PASSWORD){
			if(vcodeValidate(request, vcode) == false ){
				LOGGER.info(CLASS_NAME + " sendMsg,图片验证码错误");
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.verinfo"));
				return jsonObject.toString() ;
			}
		}*/
		
		String ip = getIpAddr(request) ;
		LOGGER.info(CLASS_NAME + " sendMsg,根据ip查询是否存在限制ip:{}", ip);
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(tel_limit<=0){
			LOGGER.info(CLASS_NAME + " sendMsg,此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent"));
			return jsonObject.toString() ;
		}
		
		Fuser fuser = null ;
		if(type !=MessageTypeEnum.REG_CODE){
			if(type == MessageTypeEnum.FIND_PASSWORD){
				List<Fuser> fusers = this.utilsService.list(0, 0, " where ftelephone=? ", false, Fuser.class,new Object[]{phone}) ;
				if(fusers.size()==1){
					fuser = fusers.get(0) ;
				}else{
					LOGGER.info(CLASS_NAME + " sendMsg,手机号码错误");
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneerror"));
					return jsonObject.toString() ;
				}
			}else{
				fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
				if(type == MessageTypeEnum.BANGDING_MOBILE){
//					if(fuser.isFisTelephoneBind()){
//						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "您已绑定手机");
//						return jsonObject.toString() ;
//					}
					if(phone == null || phone.trim().length() ==0){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneempty"));
						return jsonObject.toString() ;
					}
					if(!phone.matches("^\\d{10,14}$")){//手機格式不對
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneformaterror"));
						return jsonObject.toString() ;
					}
					List<Fuser> fusers = this.utilsService.list(0, 0, " where ftelephone=? ", false, Fuser.class,new Object[]{phone}) ;
					if(fusers.size() >0){
						LOGGER.info(CLASS_NAME + " sendMsg,手机号码已存在");
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneexist"));
						return jsonObject.toString() ;
					}
				}else{
					if(!fuser.isFisTelephoneBind()){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.nobindphone"));
						return jsonObject.toString() ;
					}else{
						phone = fuser.getFtelephone() ;
					}
				}
		}
		}else{
			boolean flag = this.frontUserService.isTelephoneExists(phone) ;
			LOGGER.info(CLASS_NAME + " sendMsg,isTelephoneExists从数据库查询手机号是否存在，返回flag:{}", flag);
			if(flag){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneexist"));
				return jsonObject.toString() ;
			}
		}
		
		Boolean flag = false;
		if(MessageTypeEnum.REG_CODE == type ){
			LOGGER.info(CLASS_NAME + " sendMsg,注册发送验证码");
			//注册
			MessageValidate messageValidate2=getMessageValidate(areaCode, phone);
			Object[] params = new Object[]{messageValidate2.getCode()};
			String mas=getLocaleMessage(request,params,"smstpl.enum."+type);
			flag = frontValidateService.saveSendMessageNologin(messageValidate2,getIpAddr(request),mas, type,0) ;
		}else if(MessageTypeEnum.BANGDING_MOBILE == type ){
			LOGGER.info(CLASS_NAME + " sendMsg,绑定手机发送验证码");
			MessageValidate messageValidate2=getMessageValidate(areaCode, phone);
			Object[] params = new Object[]{messageValidate2.getCode()};
			String mas=getLocaleMessage(request,params,"smstpl.enum."+type);
			flag = frontValidateService.saveSendMessageHaslogin(messageValidate2,fuser,fuser.getFid(),mas, type,0) ;
		}else{
			LOGGER.info(CLASS_NAME + " sendMsg,其它类型发送验证码");
			MessageValidate messageValidate2=getMessageValidate(fuser.getFareaCode(), fuser.getFtelephone());
			Object[] params = new Object[]{messageValidate2.getCode()};
			String mas=getLocaleMessage(request,params,"smstpl.enum."+type);
			flag = frontValidateService.saveSendMessageHaslogin(messageValidate2,fuser,fuser.getFid(), mas, type,0) ;
		}
		LOGGER.info(CLASS_NAME + " sendMsg,调BaseController.SendMessage()完成，flag:{}", flag);
		if(flag){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.codesent"));
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.sendfreq"));
		}
		LOGGER.info(CLASS_NAME + " sendMsg,返回值resultStr:{}", jsonObject.toString());
		return jsonObject.toString() ;
	
	}
	
	
	//短信发送，验证码发送
	@ResponseBody
	@RequestMapping(value="/user/sendMsgTwo")
	public String sendMsgTwo(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		String areaCode = "86" ;
		Fuser fu= GetSecondLoginSession(request);		
		if(fu==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"操作异常，请先登录再验证")) ;
			return jsonObject.toString() ;
		}
		Fuser fuser = this.frontUserService.findById(fu.getFid());
		if(fuser==null){			
				jsonObject.accumulate("code", 1001) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.noaccount")) ;
				return jsonObject.toString() ;			
		}
		Boolean flag = false;
		String phone=fuser.getFtelephone();
		

		LOGGER.info(CLASS_NAME + " sendMsg,入参明细phone:{},vcode:{},type:{}", fuser.getFloginName(), type);
		//注册类型免登陆可以发送
		
		if(type<MessageTypeEnum.BEGIN || type>MessageTypeEnum.END){
			LOGGER.info(CLASS_NAME + " sendMsg,非法提交");
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}
		
		if(type != MessageTypeEnum.loginTwo ){
			LOGGER.info(CLASS_NAME + " sendMsg,校验不通过");
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}else{
			String ip = getIpAddr(request) ;
			LOGGER.info(CLASS_NAME + " sendMsg,根据ip查询是否存在限制ip:{}", ip);
			int tel_limit = this.frontValidateService.getLimitCount(getIpAddr(request), CountLimitTypeEnum.TELEPHONE) ;
			if(tel_limit<=0){
				LOGGER.info(CLASS_NAME + " sendMsg,此ip操作频繁，请2小时后再试!");
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent"));
				return jsonObject.toString() ;
			}
			LOGGER.info(CLASS_NAME + " sendMsg,二次验证发送验证码");
			//注册
			MessageValidate messageValidate2=getMessageValidate(areaCode, phone);
			Object[] params = new Object[]{messageValidate2.getCode()};
			String mas=getLocaleMessage(request,params,"smstpl.enum."+type);
			flag = frontValidateService.saveSendMessageNologin(messageValidate2,getIpAddr(request),mas, type,0) ;
		}
				
		LOGGER.info(CLASS_NAME + " sendMsg,调BaseController.SendMessage()完成，flag:{}", flag);
		if(flag){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.codesent"));
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.sendfreq"));
		}
		LOGGER.info(CLASS_NAME + " sendMsg,返回值resultStr:{}", jsonObject.toString());
		return jsonObject.toString() ;
	
	}
	
	//发送邮件验证码
	@ResponseBody
	@RequestMapping(value="/user/sendMailCode")
	public String sendMailCode(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		String email = request.getParameter("email") ;
		String vcode = request.getParameter("vcode") ;
		
		//注册类型免登陆可以发送
		JSONObject jsonObject = new JSONObject() ;
		
		if(type != SendMailTypeEnum.RegMail && GetSession(request)==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}
		
		if(type<SendMailTypeEnum.BEGIN || type>SendMailTypeEnum.END){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}
		
		//注册需要验证码
		/*if(type == SendMailTypeEnum.RegMail){
			if(vcodeValidate(request, vcode) == false ){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.verinfo"));
				return jsonObject.toString() ;
			}
		}*/
		
		String ip = getIpAddr(request) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.REG_MAIL) ;
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent"));
			return jsonObject.toString() ;
		}
		String msg=getLocaleMessage(request,null,"mail.title.reg");
		String lcal = RequestContextUtils.getLocale(request).toString();
		frontValidateService.saveSendMailNologin(msg, lcal, getIpAddr(request), email, type,0);
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.codesent"));
		return jsonObject.toString() ;
		
	}
	
	//发送邮件验证码
	@ResponseBody
	@RequestMapping(value="/user/sendMailCodeTwo")
	public String sendMailCodeTwo(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fu= GetSecondLoginSession(request);
		if(fu==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"操作异常，请先登录再验证")) ;
			return jsonObject.toString() ;
		}
		Fuser fuser = this.frontUserService.findById(fu.getFid());
		if(fuser==null){			
				jsonObject.accumulate("code", 1001) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.noaccount")) ;
				return jsonObject.toString() ;			
		}		
		//注册类型免登陆可以发送
	    String email=fuser.getFemail();
		if(type<SendMailTypeEnum.BEGIN || type>SendMailTypeEnum.END){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}
		if(type !=  SendMailTypeEnum.RegMail){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.illsubmit"));
			return jsonObject.toString() ;
		}else{
			String ip = getIpAddr(request) ;
			int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.REG_MAIL) ;
			if(tel_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent"));
				return jsonObject.toString() ;
			}
			
			String msg=getLocaleMessage(request,null,"mail.title.reg");
			String lcal = RequestContextUtils.getLocale(request).toString();
			frontValidateService.saveSendMailNologin(msg, lcal, ip, email, type,0);
			
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.codesent"));
			return jsonObject.toString() ;
		}
		
	}
	
	
	/*
	 * 添加谷歌设备
	 * */
	@ResponseBody
	@RequestMapping(value="/user/googleAuth",produces={JsonEncode})
	public String googleAuth(
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(fuser.getFgoogleBind()){
			//已经绑定机器了，属于非法提交
			jsonObject.accumulate("code", -1) ;
			return jsonObject.toString() ;
		}
		
		Map<String, String> map = GoogleAuth.genSecret(fuser.getFloginName()) ;
		String totpKey = map.get("secret") ;
		String qecode = map.get("url") ;
		
		fuser.setFgoogleAuthenticator(totpKey) ;
		fuser.setFgoogleurl(qecode) ;
		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.frontUserService.updateFUser(fuser,-1,null) ;
		if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
			getSession(request).setAttribute("login_user", fuser) ;
		}
		
		jsonObject.accumulate("qecode", qecode) ;
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("totpKey", totpKey) ;
				
		return jsonObject.toString() ;
	}
	
	/*
	 * 添加设备认证
	 * */
	@ResponseBody
	@RequestMapping(value="/user/validateAuthenticator",produces={JsonEncode})
	public String validateAuthenticator(
			HttpServletRequest request,
			@RequestParam(required=true)String code,
			@RequestParam(required=true)String totpKey
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		//行为式验证码验证--无痕验证
		String retCode = new AliyunCheck().checkAliyunNVCMode(request);
		if(!(retCode.equals("100") || retCode.equals("200"))) {
			jsonObject.accumulate("code", retCode) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"验证失败")) ;
			return jsonObject.toString() ;
		}
		
		String ip = getIpAddr(request) ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

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
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo")+"(error code:nex)") ;
				return jsonObject.toString() ;
			}
		}
		
		boolean b_status = fuser.getFgoogleBind()==false
							&& totpKey.equals(fuser.getFgoogleAuthenticator())
							&& !totpKey.trim().equals("");
		
		if(!b_status){
			//非法提交
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.repeatbindgoogle")) ;
			return jsonObject.toString() ;
		}
		
		int limitedCount = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		if(limitedCount>0){
			boolean auth = GoogleAuth.auth(Long.parseLong(code), fuser.getFgoogleAuthenticator()) ;
			if(auth){
				jsonObject.accumulate("code", 0) ;//0成功，-1，错误
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.bindsucc")) ;
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				
				fuser.setFgoogleBind(true) ;
				fuser.setFgoogleValidate(false) ;
				this.frontUserService.updateFUser(fuser,LogTypeEnum.User_BIND_GOOGLE,ip) ;
				
				//谷歌认证奖励
				int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"谷歌认证奖励");
				if (sendcount==0 ) {
					Fintrolinfo introlInfo = null;
					Fvirtualwallet fvirtualwallet = null;
					String[] auditSendCoin = frontConstantMapService.getString("googleSendCoin").split("#");
					int coinID = Integer.parseInt(auditSendCoin[0]);
					double coinQty = Double.valueOf(auditSendCoin[1]);
					//如果设置了谷歌认证奖励，且币种存在的话，则进行奖励
					Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinID);
					if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid());
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(coinQty);
						introlInfo.setFuser(fuser);
						introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_CANDY);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						introlInfo.setFtitle("谷歌认证奖励");
					}
					this.userService.updateObj(null, introlInfo, fvirtualwallet);
				}
				
				
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
				return jsonObject.toString() ;
			}else{
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				jsonObject.accumulate("code", -1) ;
				Object[] params = new Object[]{limitedCount};
				jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentgcode"));
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_UPDATE_GOOGLE, "更新设备失败");
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.ipfrequent")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_UPDATE_GOOGLE, "更新设备异常：频繁谷歌验证");
			return jsonObject.toString() ;
		}
		
	}
	

	

	
	/**
	 * 设置提现银行卡信息
	 * param account 银行卡号
     * param phoneCode 手机验证码
     * param totpCode 谷歌验证码
     * param openBankType 银行类型
     * param address 分行名称
     * param prov 银行所属省份
     * param city 银行所属城市
     * param dist 银行所属区局
     * param payeeAddr 开户姓名
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/user/updateOutAddress")
	public String updateOutAddress(
			HttpServletRequest request,
			@RequestParam(required=true)String account,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int openBankType,
			@RequestParam(required=true)String address,
			@RequestParam(required=true)String prov,
			@RequestParam(required=true)String city,
			@RequestParam(required=true,defaultValue="0")String dist,
			@RequestParam(required=true)String payeeAddr//开户姓名
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
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
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo")+"(error code:nex)") ;
				return jsonObject.toString() ;
			}
		}
	String code=actionSecurityCheck(request, fuser, false, false, false, null, true, phoneCode, totpCode, MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, LogTypeEnum.User_CNY, "新增银行卡异常", "msg");
		
		if(!code.equals("ok")){
			return code;
		}
		
		address = HtmlUtils.htmlEscape(address);
		account = HtmlUtils.htmlEscape(account);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		totpCode = HtmlUtils.htmlEscape(totpCode);
		prov = HtmlUtils.htmlEscape(prov);
		city = HtmlUtils.htmlEscape(city);
		dist = HtmlUtils.htmlEscape(dist);
		payeeAddr = fuser.getFrealName();
		
		String last = prov+city;
		if(!dist.equals("0")){
			last = last+dist;
		}
		
		if(account.length() < 10){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.bankaccill")) ;
			return jsonObject.toString();
		}
		
		if(address.length() > 300){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.addrtolong")) ;
			return jsonObject.toString();
		}
		
		if(last.length() > 50){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString();
		}
		
		if(!fuser.getFrealName().equals(payeeAddr)){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "银行卡账号名必须与您的实名认证姓名一致") ;
			return jsonObject.toString();
		}
		
		String bankName = BankTypeEnum.getEnumString(openBankType);
		if(bankName == null || bankName.trim().length() ==0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.bankmistake")) ;
			return jsonObject.toString();
		}
		
		int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fbankType="+openBankType+" and fbankNumber='"+account+"' and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
		if(count>0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.bankcardexist")) ;
			return jsonObject.toString();
		}
		
		String ip = getIpAddr(request) ;
		
		
	
		
		//成功
		try {
			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
			fbankinfoWithdraw.setFbankNumber(account) ;
			fbankinfoWithdraw.setFbankType(openBankType) ;
			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
			fbankinfoWithdraw.setFname(bankName) ;
			fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			fbankinfoWithdraw.setFaddress(last);
			fbankinfoWithdraw.setFothers(address);
			fbankinfoWithdraw.setFuser(fuser);
			this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_CNY, "新增银行卡成功");
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_CNY, "新增银行卡失败");
		}finally{
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
		}
		
		return jsonObject.toString() ;
	}
	
	


	@ResponseBody
	@RequestMapping("/user/deleteBankAddress")
	public String deleteBankAddress(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
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
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo")+"(error code:nex)") ;
				return jsonObject.toString() ;
			}
		}
		
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(fid);
		if(fbankinfoWithdraw == null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.norecord")) ;
			return jsonObject.toString() ;
		}
		if(fuser.getFid() != fbankinfoWithdraw.getFuser().getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString() ;
		}
		//成功
		try {
			this.frontUserService.updateDelBankInfoWithdraw(fbankinfoWithdraw);
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success")) ;
			this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_CNY, "删除银行卡成功");
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
			this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_CNY, "删除银行卡失败");
		}
		
		return jsonObject.toString() ;
	}
	
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
        WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
        return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
    }
	
	@ResponseBody
	@RequestMapping("/user/readMessage")
	public String readMessage(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception {
		JSONObject jsonObject = new JSONObject();
		int userid = GetSession(request).getFid() ;
		
		Fmessage msg = this.messageService.findById(id);
		if(msg != null && msg.getFreceiver().getFid() == userid){
			if(msg.getFstatus() == MessageStatusEnum.NOREAD_VALUE){
				msg.setFstatus(MessageStatusEnum.READ_VALUE);
				this.messageService.updateObj(msg);
			}
		}

		jsonObject.accumulate("title", msg.getFtitle());
		jsonObject.accumulate("content", msg.getFcontent());
		return jsonObject.toString();
	}
	
	/**
	 * OTC--承兑商上线
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/user/online")
	public String online(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = getSession(request) ;
		 ServletContext application = session.getServletContext();
		 session.setAttribute("online",true);
        // 取得登录的用户名
         Fuser fuser = (Fuser) session.getAttribute("login_user");

        // 从在线列表中删除用户名
       /* List onlineUserList = (List) application.getAttribute("onlineUserList");
        if(onlineUserList==null){
        	onlineUserList=new ArrayList();
        }
        if(!onlineUserList.contains(Integer.valueOf(fuser.getFid()))){
              onlineUserList.add(Integer.valueOf(fuser.getFid()));
             application.setAttribute("onlineUserList",onlineUserList);
        }*/
        
        //从redis添加用户
        this.frontUserService.addOTCOnlineUserMap(fuser.getFid());
        jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "online");
		return jsonObject.toString() ;
	}
	
	
	/**
	 * OTC--承兑商下线
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/user/offline")
	public String outline(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = getSession(request) ;
		 ServletContext application = session.getServletContext();
		 session.setAttribute("online", false);
        // 取得登录的用户名
         Fuser fuser = (Fuser) session.getAttribute("login_user");

        // 从在线列表中删除用户名
        /*List onlineUserList = (List) application.getAttribute("onlineUserList");
        if(onlineUserList!=null&&onlineUserList.contains(fuser.getFid())){	        	
        	 onlineUserList.remove(Integer.valueOf(fuser.getFid()));
             application.setAttribute("onlineUserList",onlineUserList);
        }*/
        //从 redis移除用户 
        this.frontUserService.removeOTCOnlineUserMap(fuser.getFid());
        jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "offline");
		return jsonObject.toString() ;
	}
}
