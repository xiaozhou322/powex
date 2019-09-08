package com.gt.sdk;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.controller.BaseController;
import com.gt.controller.front.FrontUserJsonController;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.FuserFaceID;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.UserService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserFaceIDService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.HttpClientUtils;
import com.gt.util.Utils;

import net.sf.json.JSONObject;

/**
 * FaceID实名认证接口
 * @author zhouyong
 *
 */
@Controller
public class FaceIDCertification extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontUserJsonController.class);
	
	private final static String api_key = "v5iKL5PaGDB1-Lt48hrVEPYRsKO5Ub3y";
	private final static String api_secret = "xdkE0Ng0hKqGBmbU9W0XcZgQo_Kvr1Bw";
	
	@Autowired
	private FrontUserFaceIDService frontUserFaceIDService;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private UserService userService ;
	/**
	 * 移动端网页身份识别接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user/faceValidateReq")
	public String faceValidateReq(HttpServletRequest request) {
		
		JSONObject jsonObject = new JSONObject() ;
		
		String userId = request.getParameter("userId");
		
		//获得一个用于实名验证的 token（token唯一且只能使用一次）
		String getTokenUrl = "https://api.megvii.com/faceid/lite/get_token";
		//用户完成或取消验证后网页跳转的目标URL。（回调方法为Post）
		String return_url = Constant.faceid_returnUrl;
		//用户完成验证、取消验证、或验证超时后，由FaceID服务器请求客户服务器的URL。（推荐为HTTPS页面，如果为HTTP则用户需要通过签名自行校验数据可信性，回调方法为Post）
		String notify_url = Constant.faceid_notifyUrl;
		//客户业务流水号，该号必须唯一。
//		String biz_no = String.valueOf(new Date().getTime());
		String biz_no = String.valueOf(userId);
		//本参数影响验证流程中是否存在身份证拍摄环节：如果为“1”，则可选择包含身份证拍摄；如果为“0”，验证流程中将没有身份证拍摄。
		String comparison_type = "1";
		//传递参数“0”，“1”，“2”或“3”，表示获取用户身份证信息的方法。
		//0：不拍摄身份证，而是通过 idcard_name / idcard_number 参数传入；
		//1：仅拍摄身份证人像面，可获取人像面所有信息；
		//2： 拍摄身份证人像面和身份证国徽面，可获取身份证所有信息；
		//3：不拍摄身份证，但会要求用户在界面上输入身份证号和姓名。
		String idcard_mode = "2";
		
		try {
			com.alibaba.fastjson.JSONObject jsonParam = new com.alibaba.fastjson.JSONObject();
			jsonParam.put("getTokenUrl", getTokenUrl);
			jsonParam.put("api_key", Constant.faceid_apiKey);
			jsonParam.put("api_secret", Constant.faceid_apiSecret);
			jsonParam.put("return_url", return_url);
			jsonParam.put("notify_url", notify_url);
			jsonParam.put("biz_no", biz_no);
			jsonParam.put("comparison_type", comparison_type);
			jsonParam.put("idcard_mode", idcard_mode);
            
            String proxyServiceUrl = "https://faceid.gbcax.com/user/faceValidate.html";            
            String str = HttpClientUtils.sendPost(proxyServiceUrl, jsonParam);

			com.alibaba.fastjson.JSONObject jsonResult = com.alibaba.fastjson.JSONObject.parseObject(str);
            
			//获取token
			String token = jsonResult.getString("token");
			if(StringUtils.isBlank(token)) {
				jsonObject.accumulate("code", "-10001");
				jsonObject.accumulate("msg", "调用API异常");
				return jsonObject.toString();
			}
			
			//保存userFaceID记录
			FuserFaceID userFaceId = new FuserFaceID();
			userFaceId.setUserId(Integer.valueOf(biz_no));
			userFaceId.setToken(token);
			userFaceId.setExpiredTime(Timestamp.valueOf(jsonResult.getString("expired_time")));
			userFaceId.setStatus(0);  // 0 
			userFaceId.setCreateTime(Utils.getTimestamp());
			userFaceId.setUpdateTime(Utils.getTimestamp());
			frontUserFaceIDService.addUserFaceID(userFaceId);
			
			//转跳到第三方身份验证页面
			String faceValidateUrl = "https://api.megvii.com/faceid/lite/do";
			
			jsonObject.accumulate("code", "0");
			jsonObject.accumulate("faceValidateUrl", faceValidateUrl+"?token="+token);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("调用API异常");
			jsonObject.accumulate("code", "-10001");
			jsonObject.accumulate("msg", "调用API异常");
			return jsonObject.toString();
		}
		
		return jsonObject.toString();
	}
	
	

	/**
	 * 移动端网页身份识别接口
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/user/faceValidate")
	public ModelAndView faceValidate(HttpServletRequest request) {
		
		String errviewname = null;
		if(this.isMobile(request)) {
			errviewname="mobile/error" ;
 	    }else {
 	    	errviewname="front/error" ;
 	    }
		ModelAndView modelAndView = new ModelAndView() ;
		
		//获得一个用于实名验证的 token（token唯一且只能使用一次）
		String getTokenUrl = "https://api.megvii.com/faceid/lite/get_token";
		//用户完成或取消验证后网页跳转的目标URL。（回调方法为Post）
		String return_url = "https://www.baidu.com";
		//用户完成验证、取消验证、或验证超时后，由FaceID服务器请求客户服务器的URL。（推荐为HTTPS页面，如果为HTTP则用户需要通过签名自行校验数据可信性，回调方法为Post）
		String notify_url = "http://test168.free.idcfengye.com/user/faceValidateCallback.html";
		//客户业务流水号，该号必须唯一。
		String biz_no = String.valueOf(new Date().getTime());
		//本参数影响验证流程中是否存在身份证拍摄环节：如果为“1”，则可选择包含身份证拍摄；如果为“0”，验证流程中将没有身份证拍摄。
		String comparison_type = "1";
		//传递参数“0”，“1”，“2”或“3”，表示获取用户身份证信息的方法。
		//0：不拍摄身份证，而是通过 idcard_name / idcard_number 参数传入；
		//1：仅拍摄身份证人像面，可获取人像面所有信息；
		//2： 拍摄身份证人像面和身份证国徽面，可获取身份证所有信息；
		//3：不拍摄身份证，但会要求用户在界面上输入身份证号和姓名。
		String idcard_mode = "2";
		
		try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("api_key", api_key);
            params.put("api_secret", api_secret);
            params.put("return_url", return_url);
            params.put("notify_url", notify_url);
            params.put("biz_no", biz_no);
            params.put("comparison_type", comparison_type);
            params.put("idcard_mode", idcard_mode);
            
			//获取token
			String token = this.getToken(getTokenUrl, params);
			if(StringUtils.isBlank(token)) {
				modelAndView.addObject("errCode", "-10001");
				modelAndView.addObject("errMsg", "调用API异常");
				modelAndView.setViewName(errviewname);
				return modelAndView;
			}
			//转跳到第三方身份验证页面
			String faceValidateUrl = "https://api.megvii.com/faceid/lite/do";
			
			modelAndView.setViewName("redirect:"+faceValidateUrl + "?token=" + token);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("调用API异常");
			modelAndView.addObject("errCode", "-10001");
			modelAndView.addObject("errMsg", "调用API异常");
			modelAndView.setViewName(errviewname);
			return modelAndView;
		}
		
		return modelAndView;
	}*/
	
	
	/**
	 * 身份认证结果回调
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/faceValidateCallback", produces=JsonEncode)
	public void faceValidateCallback(HttpServletRequest request, @RequestBody String data) {
		
		JSONObject jsonObject = new JSONObject() ;
		
		if(StringUtils.isNotBlank(data)) {
			com.alibaba.fastjson.JSONObject jsonParam = com.alibaba.fastjson.JSONObject.parseObject(data);
			
			String biz_no = jsonParam.getString("biz_no");
			Integer status = jsonParam.getIntValue("status");
			String statusValue = jsonParam.getString("status_value");
			//更新状态
			FuserFaceID userFaceID = frontUserFaceIDService.findByUserId(Integer.valueOf(biz_no));
			if(null != userFaceID && userFaceID.getStatus() != 102) {
				userFaceID.setStatus(status);
				userFaceID.setStatusValue(statusValue);
				userFaceID.setUpdateTime(Utils.getTimestamp());
				frontUserFaceIDService.updateUserFaceID(userFaceID);				
				
				if(102 == status) {
					//更新user的key2认证字段
					Fuser fuser = frontUserService.findById(Integer.valueOf(biz_no));
					fuser.setFpostImgValidate(true);
					fuser.setFhasImgValidate(true);
					
					//实名认证奖励
					
					Fuser fintrolUser = null;
					Fintrolinfo introlInfo = null;
					Fvirtualwallet fvirtualwallet = null;
					Fintrolinfo introlInfoIntro = null;
					Fvirtualwallet fvirtualwalletIntro = null;
					Fscore fscore = fuser.getFscore();
					
					String[] auditSendCoin = frontConstantMapService.getString("kyc2SendCoin").split("#");
					String[] introlSendCoin = frontConstantMapService.getString("introlSendCoin").split("#");
					int coinID = Integer.parseInt(auditSendCoin[0]);
					double coinQty = Double.valueOf(auditSendCoin[1]);
					int coinIDIntro = Integer.parseInt(introlSendCoin[0]);
					double coinQtyIntro = Double.valueOf(introlSendCoin[1]);
					
					int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"KYC2认证奖励");
					//kyc2认证奖励只奖励一次，同时需要检测fscore的设定
					if (sendcount==0 && !fscore.isFissend()) {
						fscore.setFissend(true);
						
						if(fuser.getfIntroUser_id() != null){
							fintrolUser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid());
							fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
						}
						//如果设置了KYC2认证奖励，且币种存在的话，则进行奖励
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
							introlInfo.setFtitle("KYC2认证奖励");
						}
						//如果存在邀请人，且邀请奖励存在的话
						fvirtualcointype=frontVirtualCoinService.findById(coinIDIntro);
						if (coinIDIntro>0 && coinQtyIntro>0 && null!=fintrolUser && null!=fvirtualcointype){
							fvirtualwalletIntro = this.frontUserService.findVirtualWalletByUser(fintrolUser.getFid(), coinIDIntro);
							fvirtualwalletIntro.setFtotal(fvirtualwalletIntro.getFtotal()+coinQtyIntro);
							introlInfoIntro = new Fintrolinfo();
							introlInfoIntro.setFcreatetime(Utils.getTimestamp());
							introlInfoIntro.setFiscny(false);
							introlInfoIntro.setFqty(coinQtyIntro);
							introlInfoIntro.setFuser(fintrolUser);
							introlInfoIntro.setFname(fvirtualwalletIntro.getFvirtualcointype().getFname());
							introlInfoIntro.setFtype(IntrolInfoTypeEnum.INTROL_INTROL);
							introlInfoIntro.setFtitle("推荐用户注册获得奖励"+coinQtyIntro+"个"+fvirtualwalletIntro.getFvirtualcointype().getFname());
						}
					}
					
					this.userService.updateObj(fuser, fscore, fintrolUser, fvirtualwallet, introlInfo, fvirtualwalletIntro, introlInfoIntro);
					//更新session
					this.SetSession(fuser,request) ;
				}
			}
		}
		
	}
	
	
	/**
	 * 身份认证结果回调（页面回调）
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/faceValidatePageCallback")
	public ModelAndView faceValidatePageCallback(HttpServletRequest request) {
		String errviewname = null;
		if(this.isMobile(request)) {
			errviewname="mobile/error/faceIdFinish" ;
 	    }else {
 	    	errviewname="front/error/faceIdFinish" ;
 	    }
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		modelAndView.setViewName(errviewname);

		return modelAndView;
	}
	
}
