package com.gt.controller.front;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.OtcPayTypeEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FotcUserPaytype;
import com.gt.entity.Fuser;
import com.gt.service.front.FotcUserPaytypeService;
import com.gt.util.Constant;
import com.gt.util.OSSPostObject;
import com.gt.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FotcUserPaytypeController extends BaseController {
	@Autowired
	private FotcUserPaytypeService fotcuserpaytypeService;


	/**
	 * 录入支付方式
	 * 
	 * @param request
	 * @param fotcuserpaytype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/savepaytype")
	public String bindPayType(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int openweixin,
			@RequestParam(required = false, defaultValue = "0") int openalipay,
			@RequestParam(required = false, defaultValue = "0") int openbank,
			@RequestParam(required = false, defaultValue = "0") int paytype/*,
			@RequestParam Fotcuserpaytype queryisBindBank,
			@RequestParam Fotcuserpaytype queryisBindAlipy,
			@RequestParam Fotcuserpaytype queryisBindWeiXin*/) {
		String pic1Url = request.getParameter("pic1Url"); // 微信收款码
		String pic2Url = request.getParameter("pic2Url"); // 支付宝图片

		String fbankperson = request.getParameter("fbankperson"); // 开户名
		String fbankname = request.getParameter("fbankname"); // 开户银行
		String fbanknumber = request.getParameter("fbanknumber"); // 账号
		String fbankaddress = request.getParameter("fbankaddress");// 开户地址
		String fbankothers = request.getParameter("fbankothers"); // 分行名称
		String weixinname = request.getParameter("weixinname"); // 微信昵称
		String weixinid = request.getParameter("weixinid"); // 微信号
		String aliname = request.getParameter("aliname"); // 支付宝姓名
		String aliid = request.getParameter("aliid"); // 支付宝账号
		String paytypebank_id = request.getParameter("paytypebank_id"); // 银行id
		String paytypealipay_id = request.getParameter("paytypealipay_id"); // 支付宝id
		String paytypeweixin_id = request.getParameter("paytypeweixin_id"); // 微信id

		JSONObject jsonObject = new JSONObject();
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
		FotcUserPaytype fotcuserpaytype = new FotcUserPaytype();
		fotcuserpaytype.setFuser(fuser);
		fotcuserpaytype.setPayType(paytype);
		fotcuserpaytype.setCreateTime(Utils.getTimestamp());
		
		if (paytype == OtcPayTypeEnum.UnionPay) {
			if(!fuser.getFrealName().equals(fbankperson) && !fuser.isFismerchant() ) {
				jsonObject.accumulate("code", 300);
				jsonObject.accumulate("msg", getLocaleMessage(request, null, "银行卡账户名称必须是实名认证姓名"));
				return jsonObject.toString();
			}
			fotcuserpaytype.setRealName(fbankperson);
			fotcuserpaytype.setBank(fbankname);
			fotcuserpaytype.setBankBranch(fbankothers);
			fotcuserpaytype.setPaymentAccount(fbanknumber);
			fotcuserpaytype.setRemark(fbankaddress);
			fotcuserpaytype.setStatus(openbank);
			if(StringUtils.isBlank(paytypebank_id)) {
				fotcuserpaytypeService.saveObj(fotcuserpaytype);
			} else {
				FotcUserPaytype userpaytype = fotcuserpaytypeService.queryById(Integer.valueOf(paytypebank_id));
				fotcuserpaytype.setVersion(userpaytype.getVersion());
				fotcuserpaytype.setId(Integer.valueOf(paytypebank_id));
				fotcuserpaytype.setUpdateTime(Utils.getTimestamp());
				fotcuserpaytypeService.updateObj(fotcuserpaytype);
			}
		}
		if (paytype == OtcPayTypeEnum.WeiXinPay) {
			fotcuserpaytype.setRealName(weixinname);
			fotcuserpaytype.setPaymentAccount(weixinid);
			fotcuserpaytype.setStatus(openweixin);
			fotcuserpaytype.setQrCode(pic1Url);
			if(StringUtils.isBlank(paytypeweixin_id)) {
				fotcuserpaytypeService.saveObj(fotcuserpaytype);
			} else {
				FotcUserPaytype userpaytype = fotcuserpaytypeService.queryById(Integer.valueOf(paytypeweixin_id));
				fotcuserpaytype.setVersion(userpaytype.getVersion());
				fotcuserpaytype.setId(Integer.valueOf(paytypeweixin_id));
				fotcuserpaytype.setUpdateTime(Utils.getTimestamp());
				fotcuserpaytypeService.updateObj(fotcuserpaytype);
			}
		}
		if (paytype == OtcPayTypeEnum.AliPay) {
			fotcuserpaytype.setRealName(aliname);
			fotcuserpaytype.setPaymentAccount(aliid);
			fotcuserpaytype.setStatus(openalipay);
			fotcuserpaytype.setQrCode(pic2Url);
			if(StringUtils.isBlank(paytypealipay_id)) {
				fotcuserpaytypeService.saveObj(fotcuserpaytype);
			} else {
				FotcUserPaytype userpaytype = fotcuserpaytypeService.queryById(Integer.valueOf(paytypealipay_id));
				fotcuserpaytype.setVersion(userpaytype.getVersion());
				fotcuserpaytype.setId(Integer.valueOf(paytypealipay_id));
				fotcuserpaytype.setUpdateTime(Utils.getTimestamp());
				fotcuserpaytypeService.updateObj(fotcuserpaytype);
			}
		}
		
		jsonObject.accumulate("code", 1);
		jsonObject.accumulate("msg", getLocaleMessage(request, null, "保存成功"));
		return jsonObject.toString();
	}

	/**
	 * 查看支付方式
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/paytypeList")
	public ModelAndView paytypeList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("front/user/pay_account");
		if(this.isMobile(request)) {
 	    	modelAndView.setViewName("mobile/user/pay_account") ;
 	    }
		Fuser fuser = GetSession(request);
		Integer fid = null; 
		String realname = null;
		if(fuser != null) {
			fid = fuser.getFid();
			realname = fuser.getFrealName();
		}
		FotcUserPaytype queryisBindBank = fotcuserpaytypeService.queryisBindType(fid, OtcPayTypeEnum.UnionPay);
		FotcUserPaytype queryisBindWeiXin = fotcuserpaytypeService.queryisBindType(fid, OtcPayTypeEnum.WeiXinPay);
		FotcUserPaytype queryisBindAlipy = fotcuserpaytypeService.queryisBindType(fid, OtcPayTypeEnum.AliPay);
		
		/*List<Fotcuserpaytype> payTypeList = fotcuserpaytypeService.findAllPayTypeById(GetSession(request).getFid());
		modelAndView.addObject("payTypeList", payTypeList);*/
		modelAndView.addObject("queryisBindBank", queryisBindBank);
		modelAndView.addObject("queryisBindWeiXin", queryisBindWeiXin);
		modelAndView.addObject("queryisBindAlipy", queryisBindAlipy);
		modelAndView.addObject("realname", realname);
		modelAndView.addObject("ismerchant", fuser.isFismerchant());
		return modelAndView;
	}

	
	
	@ResponseBody
	@RequestMapping(value="/user/uploadPayway",produces="text/html;charset=UTF-8")
	public String uploadPayway(
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
		 /*if(fuser.isFpostImgValidate()){
	        return "";
	    }
        if(fuser.isFhasImgValidate()){
        	return "";
        }*/
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

}
