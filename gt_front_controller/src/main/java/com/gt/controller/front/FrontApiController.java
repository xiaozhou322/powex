package com.gt.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gt.Enum.CountLimitTypeEnum;
import com.gt.Enum.MessageTypeEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fapi;
import com.gt.entity.Fuser;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontValidateService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.GoogleAuth;
import com.gt.util.Utils;

@Controller 
public class FrontApiController extends BaseController {
	
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	
	
	
	@ResponseBody
	@RequestMapping(value="/json/cancelApi",produces=JsonEncode)
	public String cancelApi(
			HttpServletRequest request,
			@RequestParam(required=true )int id
			) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		
		Fapi fapi = this.frontUserService.findFapiById(id) ;
		if(fapi != null && fapi.getFuser().getFid() == GetSession(request).getFid()){
			try {
				this.frontUserService.deleteFapi(fapi) ;
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.question.delsuc")) ;
				return jsonObject.toString() ;
			} catch (Exception e) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString() ;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/json/addApi",produces=JsonEncode)
	public String addApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String tradePwd,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=false,defaultValue="0")int type,
			@RequestParam(required=false,defaultValue="")String fip
			)
			throws Exception {
		JSONObject jsonObject = new JSONObject();
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

		if(fuser.getFtradePassword()==null){
			//没有设置交易密码
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.user.settranfirst")) ;
			return jsonObject.toString() ;
		}
		
		if(!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()){
			//没有绑定谷歌或者手机
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo")) ;
			return jsonObject.toString() ;
		}
		
		String ip = getIpAddr(request) ;
		if(fuser.getFtradePassword()!=null){
			int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			if(trade_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.ipfrequent")) ;
				return jsonObject.toString() ;
			}else{
				boolean flag = fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt())) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
					Object[] params = new Object[]{trade_limit};
					jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentpwd"));

					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					return jsonObject.toString() ;
				}else if(trade_limit<Constant.ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				}
			}
		}
		
		if(fuser.getFgoogleBind()){
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.ipfrequent")) ;
				return jsonObject.toString() ;
			}else{
				boolean flag = GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator()) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
					Object[] params = new Object[]{google_limit};
					jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentgcode"));

					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else if(google_limit<Constant.ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
		}
		
		if(fuser.isFisTelephoneBind()){
			int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(tel_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.ipfrequent")) ;
				return jsonObject.toString() ;
			}else{
				boolean flag = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.API_CODE, phoneCode) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
					Object[] params = new Object[]{tel_limit};
					jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.incorrentmcode"));

					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					return jsonObject.toString() ;
				}else if(tel_limit<Constant.ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" ", Fapi.class) ;
		if(count>0){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.api.limit")) ;
			return jsonObject.toString();
		}
		
		boolean istrade = type!=1 ;
		boolean iswithdraw = type!=0 ;
		Fapi fapi = this.frontUserService.addFapi(fuser,String.valueOf(fuser.getFid()),istrade,iswithdraw,fip) ;
		jsonObject.accumulate("code", 0);
		jsonObject.accumulate("secret", fapi.getFsecret());
		jsonObject.accumulate("partner", fapi.getFpartner());
		jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.api.addsucc"));
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/user/api")
	public ModelAndView api(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		List<Fapi> list = this.utilsService.list(0, 0, " where  fuser.fid="+fuser.getFid()+" order by fid desc ", false, Fapi.class) ;
		modelAndView.addObject("list", list) ;
		
		int apinum=1;
		int apiCount =list.size();
		boolean isBindTelephone = fuser.isFisTelephoneBind();
		boolean isBindGoogle = fuser.getFgoogleBind();
		
		modelAndView.addObject("apinum", apinum) ;
		modelAndView.addObject("apiCount", apiCount) ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
		modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		modelAndView.setViewName("front/user/api") ;
		return modelAndView ;
	}
	
	@RequestMapping(value="/api_doc")
	public ModelAndView api_doc(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front/user/api_doc") ;
		return modelAndView ;
	}
	
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
        WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
        return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
    }
	
}
