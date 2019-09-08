package com.gt.controller.front;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.controller.BaseController;
import com.gt.entity.Fapi;
import com.gt.entity.FotcOrder;
import com.gt.entity.Fuser;
import com.gt.service.front.ApiService;
import com.gt.service.front.FotcOrderService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/otc")
public class FotcApiController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(FotcApiController.class);
	
	public final static String CODE = "code" ;
	public final static String MESSAGE = "msg" ;
	public final static String TIME = "time" ;
	public final static String DATA = "data" ;
	
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private ApiService apiService ;
	@Autowired
	private FotcOrderService otcorderService ;

	@ResponseBody
	@RequestMapping(value="/apiServer",produces=JsonEncode)
	public String appApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String action
			) throws Exception {
		request.setCharacterEncoding("UTF-8") ;
		JSONObject jsonObject = new JSONObject() ;
		
		Integer actionInteger = APIConstant.getInteger(action) ;
		
		Fapi fapi = null ;
		Fuser fuser = null ;
		if(actionInteger / 100 < 2){
			String api_key = request.getParameter("partnerId") ;
			String sign = request.getParameter("sign") ;
			List<Fapi> fapis = this.utilsService.list(0, 0, " where fpartner=? ", false, Fapi.class,api_key) ;
			if(fapis.size() == 1 ){
				fapi = fapis.get(0) ;
				if(fapi.isFistrade()==false){
					jsonObject.accumulate(CODE , -10001) ;
					jsonObject.accumulate(MESSAGE, "无交易权限") ;
					return jsonObject.toString() ;
				}
				
				if((fapi.isFiswithdraw()==false && actionInteger / 100==3)){
					jsonObject.accumulate(CODE , -10002) ;
					jsonObject.accumulate(MESSAGE, "无提现权限") ;
					return jsonObject.toString() ;
				}
				
				String ip = getIpAddr(request) ;
				if("*".equals(fapi.getFip()) == false && fapi.getFip().indexOf(ip)==-1){
					jsonObject.accumulate(CODE , -10003) ;
					jsonObject.accumulate(MESSAGE, "IP无权限") ;
					return jsonObject.toString() ;
				}
				
				fuser = fapi.getFuser() ;
				
				if(fuser.getFstatus() != 1) {
					jsonObject.accumulate(CODE , -10009) ;
					jsonObject.accumulate(MESSAGE, "用户被禁用") ;
					return jsonObject.toString() ;
				}
				if(!fuser.isFisorganization()) {
					jsonObject.accumulate(CODE , -10011) ;
					jsonObject.accumulate(MESSAGE, "用户不是机构商") ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate(CODE , -10004) ;
				jsonObject.accumulate(MESSAGE, "api_key错误") ;
				return jsonObject.toString() ;
			}
			
			
			Map<String, String[]> params = request.getParameterMap() ;
			TreeSet<String> paramTreeSet = new TreeSet<String>() ;
			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String key = entry.getKey() ;
				String[] values = entry.getValue() ;
				if(values.length!=1){
					jsonObject.accumulate(CODE , -10005) ;
					jsonObject.accumulate(MESSAGE, "参数错误") ;
					return jsonObject.toString() ;
				}
				if("sign".equals(key) == false &&"action".equals(key) == false){
					paramTreeSet.add(key+"="+values[0]) ;
				}
			}
			
			StringBuffer paramString = new StringBuffer() ;
			for (String p : paramTreeSet) {
				if(paramString.length()>0){
					paramString.append("&") ;
				}
				paramString.append(p) ;
			}
			paramString.append("&secret_key="+fapi.getFsecret()) ;
			String md5 = Utils.getMD5_32_xx(paramString.toString()).toUpperCase() ;
			
			if(md5.equals(sign) == false ){
				jsonObject.accumulate(CODE , -10006) ;
				jsonObject.accumulate(MESSAGE, "验签失败") ;
				return jsonObject.toString() ;
			}
		}

		String ret = "" ;
		switch (actionInteger) {
		case 0 :
			{
				jsonObject.accumulate(CODE , -10007) ;
				jsonObject.accumulate(MESSAGE, "API不存在") ;
				ret = jsonObject.toString() ;
			}
			break ;
			
		default:
			try {
				Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class,Fapi.class,Fuser.class) ;
				ret = (String)method.invoke(this, request,fapi,fuser) ;
			} catch (Exception e) {
				ret = APIConstant.getUnknownError(e) ;
			}
			break ;	
		}
		
		return ret ;
	}
	
	
	/**
	 * 第三方查询订单接口
	 * @param request
	 * @param fapi
	 * @param fuser
	 * @return
	 * @throws Exception
	 */
	public String otcOrder(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		
		String organOrderId = request.getParameter("orgOrderId");        //机构订单号
		logger.info("otc订单详情接口请求报文：{organOrderId,otcOrderId}", organOrderId);
		//查询订单
		FotcOrder otcOrder = otcorderService.queryOrderByOrginOrderId(organOrderId);
		if(null != otcOrder) {
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("otcOrderId", otcOrder.getId());
			orderMap.put("orgOrderId", otcOrder.getUserOrderId());
			orderMap.put("orderType", otcOrder.getOrderType());
			orderMap.put("unitPrice", otcOrder.getUnitPrice());
			orderMap.put("amount", otcOrder.getAmount());
			orderMap.put("totalPrice", otcOrder.getTotalPrice());
			orderMap.put("orderStatus", otcOrder.getOrderStatus());
			orderMap.put("orderStatusDesc", otcOrder.getOrderStatusDesc());
			orderMap.put("appealStatus", otcOrder.getAppeal_status());
			if(null != otcOrder.getAppeal_status()) {
				orderMap.put("appealStatusDesc", otcOrder.getAppeal_statusDesc());
			} else {
				orderMap.put("appealStatusDesc", "");
			}
			
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "成功") ;
			jsonObject.accumulate(DATA, orderMap) ;
		} else {
			jsonObject.accumulate(CODE, -101) ;
			jsonObject.accumulate(MESSAGE, "订单不存在") ;
		}
		
		return jsonObject.toString() ;
	}
	
	
	
	/*public static void main(String[] args) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("organOrderId", "20180820184503189");
		paramMap.put("partnerId", "90e0ed99-6268-4506-827e-d471db601adc");
		
		//传递参数
		String paramStr = MD5SignHelper.getParamStr(paramMap);

	    //对RSA密文后边追加MD5密钥后进行MD5加密，
	     String MD5OrderParam = MD5SignHelper.getSign(paramStr, RSA_main.MD5_KEY);
	     paramMap.put("action", "otcOrder");
	     paramMap.put("sign", MD5OrderParam);
	     HttpClientUtils httppost = new HttpClientUtils();
	     httppost.post("http://192.168.0.114:8081/otc/apiServer.html", paramMap);
		 String orderParam = JSON.toJSONString(paramMap);
		 System.out.println(orderParam);
	}*/
}
