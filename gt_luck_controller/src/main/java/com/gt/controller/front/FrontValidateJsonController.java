package com.gt.controller.front;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gt.Enum.LogTypeEnum;
import com.gt.Enum.MessageTypeEnum;
import com.gt.Enum.SendMailTypeEnum;
import com.gt.comm.MultipleValues;
import com.gt.controller.BaseController;
import com.gt.entity.Emailvalidate;
import com.gt.entity.Fuser;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontValidateMapService;
import com.gt.service.front.FrontValidateService;
import com.gt.util.Constant;
import com.gt.util.Utils;


@Controller
public class FrontValidateJsonController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private FrontValidateMapService frontValidateMapService ;
	
	//通过邮箱找回登录密码
	@ResponseBody
	@RequestMapping(value = "/validate/sendEmail",produces=JsonEncode)
	public String queryEmail(
			HttpServletRequest request,
			@RequestParam(required=true)String imgcode,
			@RequestParam(required=true)String idcardno,
			@RequestParam(required=true)String email
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		idcardno = idcardno.toLowerCase();
		if(vcodeValidate(request, imgcode) == false ){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.verinfo")) ;
			return jsonObject.toString() ;
		}
		
		if(!email.matches(Constant.EmailReg)){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.emailformaterror")) ;
			return jsonObject.toString() ;
		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("femail", email) ;
		if(fusers.size()==1){
			Fuser fuser = fusers.get(0) ;
			if(fuser.getFisMailValidate()){
				//验证身份证号码
				if(fuser.getFhasRealValidate() == true ){
					if(idcardno.trim().equals(fuser.getFidentityNo()) == false ){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect")) ;
						return jsonObject.toString() ;
					}
				}
				
				
				String ip = getIpAddr(request) ;
				Emailvalidate ev = this.frontValidateMapService.getMailMap(ip+"_"+SendMailTypeEnum.FindPassword) ;
				if(ev==null || Utils.getTimestamp().getTime()-ev.getFcreateTime().getTime()>5*60*1000L){
					boolean flag = false ;
					try {
						String lcal = RequestContextUtils.getLocale(request).toString();
						String mas=getLocaleMessage(request,null,"mail.title.findpass");
						flag = this.frontValidateService.saveSendFindPasswordMail(ip,fuser,email, lcal,mas) ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(flag){
						jsonObject.accumulate("code", 0) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.resetpwdmail")) ;
						return jsonObject.toString() ;
					}else{
						jsonObject.accumulate("code", -4) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror")) ;
						return jsonObject.toString() ;
					}
				}else{
					jsonObject.accumulate("code", -4) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.reqfreq")) ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate("code", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.novalemail")) ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.noaccount")) ;
			return jsonObject.toString() ;
		}
		
	}
	
	
	//通过手机找回登录密码
	@ResponseBody
	@RequestMapping(value = "/validate/resetPhoneValidation",produces=JsonEncode)
	public String resetPhoneValidation(
			HttpServletRequest request,
			@RequestParam(required=true)String phone,
			@RequestParam(required=true)String msgcode,
			@RequestParam(required=true)String idcardno
			) throws Exception{
		String areaCode = "86" ;
		idcardno = idcardno.toLowerCase();
		JSONObject jsonObject = new JSONObject() ;
		
		if(!phone.matches(Constant.PhoneReg)){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.phoneformaterror")) ;
			return jsonObject.toString() ;
		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
		
		if(fusers.size()==1){
			Fuser fuser = fusers.get(0) ;
			
			//短信验证码
			boolean validate = validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.FIND_PASSWORD, msgcode) ;
			if(validate == false ){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.smserror")) ;
				return jsonObject.toString() ;
			}
			
			if(fuser.isFisTelephoneBind()){
				//验证身份证号码
				if(fuser.getFhasRealValidate() == true ){
					if(idcardno.trim().equals(fuser.getFidentityNo()) == false ){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.idincorrect")) ;
						return jsonObject.toString() ;
					}
				}
				
				//往session放数据
				MultipleValues values = new MultipleValues() ;
				values.setValue1(fuser.getFid()) ;
				values.setValue2(Utils.getTimestamp()) ;
				request.getSession().setAttribute("resetPasswordByPhone", values) ;
				
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.FIND_PASSWORD);
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.jumpmodif")) ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate("code", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.novalphone")) ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.noaccount")) ;
			return jsonObject.toString() ;
		}
		
	}
	
	
	//邮件重置密码最后一步
	@ResponseBody
	@RequestMapping(value = "/validate/resetPassword",produces=JsonEncode)
	public String resetPassword(
			HttpServletRequest request,
			@RequestParam(required=true)String newPassword,
			@RequestParam(required=true)String newPassword2,
			@RequestParam(required=true)int fid,
			@RequestParam(required=true)int ev_id,
			@RequestParam(required=true)String newuuid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		boolean flag = false ;
		try {
			flag = this.frontValidateService.canSendFindPwdMsg(fid, ev_id, newuuid) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!flag){
			jsonObject.accumulate("code", -6) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.resetpageinvalid")) ;
			return jsonObject.toString() ;
		}
		
		//密码
		if(newPassword.length()<6){
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.pwdlens")) ;
			return jsonObject.toString() ;
		}
		
		if(!newPassword.equals(newPassword2)){
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.pwdtwodif")) ;
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(fid) ;
		
		if(Utils.MD5(newPassword,fuser.getSalt()).equals(fuser.getFtradePassword())){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.loginsametran")) ;
			return jsonObject.toString() ;
		}
		
		boolean updateFlag = false ;
		fuser.setFloginPassword(Utils.MD5(newPassword,fuser.getSalt())) ;
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFUser(fuser,LogTypeEnum.User_RESET_PWD,ip) ;
			
			updateFlag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(updateFlag){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.loginpwdresetsucc")) ;
			return jsonObject.toString() ;
		}else{
			jsonObject.accumulate("code", -5) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror")) ;
			return jsonObject.toString() ;
		}
		
	}
	
	
	//手机重置密码最后一步
	@ResponseBody
	@RequestMapping(value = "/validate/resetPasswordPhone",produces=JsonEncode)
	public String resetPasswordPhone(
			HttpServletRequest request,
			@RequestParam(required=true)String newPassword,
			@RequestParam(required=true)String newPassword2
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		boolean isValidate = false ;
		Fuser fuser = null ;
		Object resetPasswordByPhone = request.getSession().getAttribute("resetPasswordByPhone") ;
		if(resetPasswordByPhone!=null ){
			MultipleValues values = (MultipleValues)resetPasswordByPhone ;
			Integer fuserid = (Integer)values.getValue1() ;
			Timestamp time = (Timestamp)values.getValue2() ;
			
			if(Utils.timeMinus(Utils.getTimestamp(), time)<300){
				fuser = this.frontUserService.findById(fuserid) ;
				isValidate = true ;
			}
		}
		
		if(!isValidate){
			jsonObject.accumulate("code", -6) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.resetpageinvalid")) ;
			return jsonObject.toString() ;
		}
		
		//密码
		if(newPassword.length()<6){
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.pwdlens")) ;
			return jsonObject.toString() ;
		}
		
		if(!newPassword.equals(newPassword2)){
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.pwdtwodif")) ;
			return jsonObject.toString() ;
		}
		
		
		if(Utils.MD5(newPassword,fuser.getSalt()).equals(fuser.getFtradePassword())){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.loginsametran")) ;
			return jsonObject.toString() ;
		}
		
		boolean updateFlag = false ;
		fuser.setFloginPassword(Utils.MD5(newPassword,fuser.getSalt())) ;
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFUser(fuser,LogTypeEnum.User_RESET_PWD,ip) ;
			
			updateFlag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(updateFlag){
			request.getSession().removeAttribute("resetPasswordByPhone") ;
			
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.validate.loginpwdresetsucc")) ;
			return jsonObject.toString() ;
		}else{
			jsonObject.accumulate("code", -5) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror")) ;
			return jsonObject.toString() ;
		}
		
	}
	
	//绑定邮箱
	@ResponseBody
	@RequestMapping("/validate/postValidateMail")
	public String postMail(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") String email
			) throws Exception{
		JSONObject js = new JSONObject();
		if(GetSession(request) == null){
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation"));
			return js.toString();
		}
		
		//邮箱注册
		boolean isExists = this.frontUserService.isEmailExists(email) ;
		if(isExists){
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.emailexist"));
			return js.toString();
		}
		
		if(!email.matches(Constant.EmailReg)){
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.user.emailformaterror"));
			return js.toString();
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(fuser.getFisMailValidate()){
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.validate.emailbind"));
			return js.toString();
		}
		
		boolean flag = false ;
		try {
			String lcal = RequestContextUtils.getLocale(request).toString();
			String msg=getLocaleMessage(request,null,"mail.title.bind");
			flag = this.frontUserService.saveValidateEmail(fuser,email,true,msg,lcal) ;
		} catch (Exception e) {
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
			return js.toString();
		}
		
		if(flag){
			js.accumulate("code", 0);
			js.accumulate("msg", getLocaleMessage(request,null,"json.validate.mailsendsuc"));
			return js.toString();
		}else{
			js.accumulate("code", -1);
			js.accumulate("msg", getLocaleMessage(request,null,"json.validate.sendhalfhour"));
			return js.toString();
		}
	}
	
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
        WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
        return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
    }
	
}
