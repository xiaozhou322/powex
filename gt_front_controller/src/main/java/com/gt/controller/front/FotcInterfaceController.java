package com.gt.controller.front;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.Enum.OtcOrderTypeEnum;
import com.gt.Enum.OtcPendAppealEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fapi;
import com.gt.entity.FotcAdvertisement;
import com.gt.entity.FotcInstitutionsinfo;
import com.gt.entity.FotcOrder;
import com.gt.entity.FotcOrderModel;
import com.gt.entity.FotcUserPaytype;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.ApiService;
import com.gt.service.front.FotcAdvertisementService;
import com.gt.service.front.FotcInstitutionsinfoService;
import com.gt.service.front.FotcOrderService;
import com.gt.service.front.FotcUserPaytypeService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.UtilsService;
import com.gt.util.Base64Utils;
import com.gt.util.Constant;
import com.gt.util.DateHelper;
import com.gt.util.PaginUtil;
import com.gt.util.Utils;

import net.sf.json.JSONObject;
/**
 * 
 * 
 * 广告操作
 * @author admin
 *
 */
@Controller
@RequestMapping("/otc")
public class FotcInterfaceController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(FotcInterfaceController.class);
	
	public final static String CODE = "code" ;
	public final static String MESSAGE = "msg" ;
	public final static String TIME = "time" ;
	public final static String DATA = "data" ;
	public final static String ORDERID = "orderId" ;
	public final static int maxResults = Constant.AppRecordPerPage ;
	
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private ApiService apiService ;
	
	@Autowired
	private FotcAdvertisementService advertisementService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FvirtualWalletService virtualWalletService ;
	
	@Autowired
	private FotcOrderService otcorderService ;
	@Autowired
	private FotcUserPaytypeService otcuserpaytypeService ;
	@Autowired
	private FotcInstitutionsinfoService otcinstitutionsinfoService;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontConstantMapService constantMap ;
	
	private Integer countdown1;    //等待接单倒计时
	private Integer countdown2;    //等待付款倒计时
	private Integer countdown3;    //付款超时申诉倒计时
	private Integer countdown4;    //等待确认倒计时
	private Integer countdown5;    //确认超时申诉倒计时
	private Integer countdown6;    //等待二次确认倒计时
	
	
	public void init() {
		//默认等待时间都是1200秒
		countdown1 = 300;
		countdown2 = 1200;
		countdown3 = 1800;
		countdown4 = 1200;
		countdown5 = 3600;
		countdown6 = 600;
		try {
			if (constantMap.get("countdown1")!=null){
				countdown1 = Integer.valueOf(constantMap.getString("countdown1"));
			}
			if (constantMap.get("countdown2")!=null){
				countdown2 = Integer.valueOf(constantMap.getString("countdown2"));
			}
			if (constantMap.get("countdown3")!=null){
				countdown3 = Integer.valueOf(constantMap.getString("countdown3"));
			}
			if (constantMap.get("countdown4")!=null){
				countdown4 = Integer.valueOf(constantMap.getString("countdown4"));
			}
			if (constantMap.get("countdown5")!=null){
				countdown5 = Integer.valueOf(constantMap.getString("countdown5"));
			}
			if (constantMap.get("countdown6")!=null){
				countdown6 = Integer.valueOf(constantMap.getString("countdown6"));
			}
		}catch(Exception e){
			e.printStackTrace();
			//配置异常默认等待时间1200秒
			countdown1 = 300;
			countdown2 = 1200;
			countdown3 = 1800;
			countdown4 = 1200;
			countdown5 = 3600;
			countdown6 = 600;
		}
	}
	
	
	@RequestMapping(value="/otcApi",produces=JsonEncode)
	public ModelAndView appApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String action
			) throws Exception {
		request.setCharacterEncoding("UTF-8") ;
		ModelAndView modelAndView = new ModelAndView();
		String returnUrl = request.getParameter("return_url");       //返回地址
		
		modelAndView.addObject("returnUrl", returnUrl) ;
		Integer actionInteger = APIConstant.getInteger(action) ;
		String errviewname = null;
		if(this.isMobile(request)) {
			errviewname="mobile/newagent/payerror" ;
 	    }else {
 	    	errviewname="front/otcorder/payerror" ;
 	    }
		Fapi fapi = null ;
		Fuser fuser = null ;
		if(actionInteger / 100 < 2){
			String api_key = request.getParameter("partnerId") ;
			String sign = request.getParameter("sign") ;
			List<Fapi> fapis = this.utilsService.list(0, 0, " where fpartner=? ", false, Fapi.class,api_key) ;
			if(fapis.size() == 1 ){
				fapi = fapis.get(0) ;
				if(fapi.isFistrade()==false){
					modelAndView.addObject("errCode", "-10001");
					modelAndView.addObject("errMsg", "无交易权限");
					modelAndView.setViewName(errviewname);
					return modelAndView;
				}
				
				if((fapi.isFiswithdraw()==false && actionInteger / 100==3)){
					modelAndView.addObject("errCode", "-10002");
					modelAndView.addObject("errMsg", "无提现权限");
					modelAndView.setViewName(errviewname);
					return modelAndView;
				}
				
				String ip = getIpAddr(request) ;
				if("*".equals(fapi.getFip()) == false && fapi.getFip().indexOf(ip)==-1){
					modelAndView.addObject("errCode", "-10003");
					modelAndView.addObject("errMsg", "IP无权限");
					modelAndView.setViewName(errviewname);
					return modelAndView;
				}
				
				fuser = fapi.getFuser() ;
				
				if(fuser.getFstatus() != 1) {
					modelAndView.addObject("errCode", "-10009");
					modelAndView.addObject("errMsg", "用户被禁用");
					modelAndView.setViewName(errviewname);
					return modelAndView;
				}
				if(!fuser.isFisorganization()) {
					modelAndView.addObject("errCode", "-10011");
					modelAndView.addObject("errMsg", "用户不是机构商");
					modelAndView.setViewName(errviewname);
					return modelAndView;
				}
			}else{
				modelAndView.addObject("errCode", "-10004");
				modelAndView.addObject("errMsg", "api_key错误");
				modelAndView.setViewName(errviewname);
				return modelAndView;
			}
			
			
			Map<String, String[]> params = request.getParameterMap() ;
			TreeSet<String> paramTreeSet = new TreeSet<String>() ;
			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String key = entry.getKey() ;
				String[] values = entry.getValue() ;
				if(values.length!=1){
					modelAndView.addObject("errCode", "-10005");
					modelAndView.addObject("errMsg", "参数错误");
					modelAndView.setViewName(errviewname);
					return modelAndView;
				}
				if("sign".equals(key) == false && "action".equals(key) == false && "return_url".equals(key) == false){
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
				modelAndView.addObject("errCode", "-10006");
				modelAndView.addObject("errMsg", "验签失败");
				modelAndView.setViewName(errviewname);
				return modelAndView;
			}
		}

		switch (actionInteger) {
		case 0 :
			{
				modelAndView.addObject("errCode", "-10007");
				modelAndView.addObject("errMsg", "API不存在");
				modelAndView.setViewName(errviewname);
			}
			break ;
			
		default:
			try {
				//第三方直接跳转到OTC平台   若session为空则赋值一个session
				Object session_user = getSession(request).getAttribute("third_user");
				 if(null == session_user) {
					 super.CleanSession(request);
					 getSession(request).setAttribute("third_user", fuser);
					 getSession(request).setAttribute("isThird", true);
					 getSession(request).setMaxInactiveInterval(1200);
				 }
				Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class,Fapi.class,Fuser.class) ;
				modelAndView = (ModelAndView)method.invoke(this, request,fapi,fuser) ;
			} catch (Exception e) {
				modelAndView.addObject("errCode", "-10008");
				modelAndView.addObject("errMsg", APIConstant.getUnknownError(e));
				modelAndView.setViewName(errviewname);
			}
			break ;	
		}
		
		return modelAndView ;
	}
	
	
	/**
	 * 第三方下单接口
	 * @param request
	 * @param fapi
	 * @param fuser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView otcPay(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
			ModelAndView modelview = new ModelAndView();
			String orderNo = request.getParameter("orderNo");           //订单号
			String coinType = request.getParameter("coinType");         //币种简称
			String orderType = request.getParameter("orderType");       //1:买入  2：卖出
			String totalCount = request.getParameter("totalCount");     //订单数量
			String payType = request.getParameter("payType");           //支付类型(1:银行卡  2：微信  3：支付宝)
			String name = request.getParameter("name");                 //帐户名(微信，支付宝昵称)
			String bankAccount = request.getParameter("bankAccount");   //账号
			String bank = request.getParameter("bank");                 //所属银行
			String bankBranch = request.getParameter("bankBranch");     //银行分行
			String qrCode = request.getParameter("qrCode");             //二维码地址
			String phone = request.getParameter("phone");               //手机号
			String remark = request.getParameter("remark");             //备注
			String pageUrl = request.getParameter("pageUrl");           //页面回调地址
			String serverUrl = request.getParameter("serverUrl");       //服务端回调地址
			String returnUrl = request.getParameter("return_url");       //返回地址
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", orderNo);
			paramMap.put("coinType", coinType);
			paramMap.put("orderType", orderType);
			paramMap.put("totalCount", totalCount);
			paramMap.put("payType", payType);
			paramMap.put("name", name);
			paramMap.put("bankAccount", bankAccount);
			paramMap.put("bank", bank);
			paramMap.put("bankBranch", bankBranch);
			paramMap.put("qrCode", qrCode);
			paramMap.put("phone", phone);
			paramMap.put("remark", remark);
			paramMap.put("userId", fuser.getFid());
			paramMap.put("pageUrl", pageUrl);
			paramMap.put("serverUrl", serverUrl);
			paramMap.put("returnUrl", returnUrl);
			
			logger.info("otc订单接口请求报文：{}", paramMap);
			String errviewname = null;
			if(this.isMobile(request)) {
				errviewname="mobile/newagent/payerror" ;
	 	    }else {
	 	    	errviewname="front/otcorder/payerror" ;
	 	    }
			modelview.addObject("returnUrl", returnUrl) ;
			if(StringUtils.isNotBlank(remark)) {
				Date date3rd = new Date(Long.valueOf(remark));
				if(DateHelper.getDifferSecond(new Date(), date3rd)/1000 > 60) {
					modelview.addObject("errCode", "-10016");
					modelview.addObject("errMsg", "订单请求超时");
					modelview.setViewName(errviewname);
					return modelview;
				}
			}
			
			String base64Encode = Base64Utils.encodeStr(JSON.toJSONString(paramMap));
			
//			request.setAttribute("orderParam", base64Encode);
			modelview.setViewName("redirect:/otc/adPage.html?orderParam="+URLEncoder.encode(base64Encode, "UTF-8"));
			return modelview;
	}
	
	
	/**
	 * 第三方查询订单详情接口
	 * @param request
	 * @param fapi
	 * @param fuser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView otcOrderDetail(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		ModelAndView modelview = new ModelAndView();
		
		String organOrderId = request.getParameter("orgOrderId");        //机构订单号
		String otcOrderId = request.getParameter("otcOrderId");            //otc平台订单号
		logger.info("otc订单详情接口请求报文：{organOrderId,otcOrderId}", organOrderId+","+otcOrderId);
		
		modelview.setViewName("redirect:/otc/orderRedirect.html?organOrderId="+organOrderId);
		return modelview;
	}
	
	
	/**
	 * 对外接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adList", method = { RequestMethod.POST, RequestMethod.GET },produces={JsonEncode})
	public ModelAndView adList(HttpServletRequest request,HttpServletResponse response,
			/*@ModelAttribute(value = "orderParam")String  orderParam,*/
			/*@ModelAttribute(value = "RSAOrderParam")String  RSAOrderParam,
			@ModelAttribute(value = "MD5OrderParam")String  MD5OrderParam,*/
			RedirectAttributes attr) throws Exception{
		
		ModelAndView modelview = new ModelAndView();
		
		//验签
		/*if (!MD5SignHelper.isRightSign(RSAOrderParam, RSA_main.MD5_KEY, MD5OrderParam)) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单签名异常");
        }*/
		//RSA解密
		/*String rsaDecr = RSAUtil.decryptByPrivateKey(RSAOrderParam, RSA_main.RSA_PRIVATE);
		Map<Object, Object> orderMap = UrlMapExchangeUtil.urlParamsToMap(rsaDecr);*/
		
		/*logger.info("otc接口请求报文：{}", orderMap);
		System.out.println("otc接口请求报文："+orderMap);*/
		
//		OtcOrderModel orderModel = JSON.parseObject(orderParam, OtcOrderModel.class);
		//数据校验 TODO
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", /*"1533803156348"*/Utils.getTimestamp().getTime());
		paramMap.put("coinType", "BTC");
		paramMap.put("orderType", "1");
		paramMap.put("totalCount", "2");
		paramMap.put("payType", "1");
		paramMap.put("name", "叶孤城");
		paramMap.put("bankAccount", "888888888888888");
		paramMap.put("bank", "中国银行");
		paramMap.put("bankBranch", "深圳市科技园支行");
		paramMap.put("qrCode", "http://192.168.0.108:8080/upload/download/201807191644594891096c48b65e44b4999596aaa00005454.png.html");
		paramMap.put("phone", "17880612013");
		paramMap.put("remark", "购买BTC");
		paramMap.put("userId", "107671");
		paramMap.put("pageUrl", "http://192.168.0.158:8480/exchange/success.html");
		paramMap.put("serverUrl", "http://192.168.0.158:8480/account/updateOrderStatus.html");
		paramMap.put("returnUrl", "http://baidu.com");
		
		String orderParam = JSON.toJSONString(paramMap);
		
		String base64Encode = Base64Utils.encodeStr(orderParam);
		
		modelview.setViewName("redirect:/otc/adPage.html?orderParam="+URLEncoder.encode(base64Encode, "UTF-8"));
		return modelview;
	}
	
	/**
	 * 广告列表
	 * @param request
	 * @param coinId
	 * @param count
	 * @param orderType
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/adPage")
	public ModelAndView adPage(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		String orderParam = request.getParameter("orderParam");
		
		String base64Decode = Base64Utils.decodeStr(orderParam);
		
		FotcOrderModel orderModel = JSON.parseObject(base64Decode, FotcOrderModel.class);
		
		String errviewname = null;
		if(this.isMobile(request)) {
			errviewname="mobile/newagent/payerror" ;
 	    }else {
 	    	errviewname="front/otcorder/payerror" ;
 	    }
		
		modelAndView.addObject("returnUrl", orderModel.getReturnUrl()) ;
		//获取在线用户
		HttpSession session = getSession(request) ;
		ServletContext application = session.getServletContext();
        List<Integer> onlineUserList = (List<Integer>) application.getAttribute("onlineUserList");
		if(null == onlineUserList || onlineUserList.size() == 0) {
			modelAndView.addObject("errCode", "-10016");
			modelAndView.addObject("errMsg", "暂无在线币商的广告");
			modelAndView.setViewName(errviewname);
			return modelAndView;
		}
		StringBuffer userList = new StringBuffer() ;
		for (Integer userId : onlineUserList) {
			if(userList.length()>0){
				userList.append(",") ;
			}
			userList.append(userId) ;
		}
		
		List<Fvirtualcointype> coinTypeList = virtualCoinService.findByProperty("fShortName", orderModel.getCoinType());
		
		Fvirtualcointype coin = coinTypeList.get(0);
		
		FotcAdvertisement otcad = new FotcAdvertisement();
		Fvirtualcointype cointype = new Fvirtualcointype();
		cointype.setFid(coin.getFid());
		otcad.setFvirtualcointype(cointype);
		otcad.setRepertory_count(Double.valueOf(orderModel.getTotalCount()));
		otcad.setAd_type(Integer.valueOf(orderModel.getOrderType()));
		otcad.setOnlineUserList(userList.toString());
		
		int totalCount = advertisementService.queryOtcAdvertisementCount(otcad);
		int totalPage = totalCount/maxResults +(totalCount%maxResults==0?0:1) ;
		currentPage = currentPage<1?1:currentPage ;
		currentPage = currentPage>totalPage?totalCount:currentPage ;
		List<FotcAdvertisement> adList = advertisementService.queryOtcAdvertisementByPage(otcad, (currentPage-1)*maxResults, maxResults);
		
		//获取机构商钱包信息
		List<Fvirtualwallet> fvirtualwallets = virtualWalletService.findByTwoProperty("fuser.fid", orderModel.getUserId(), "fvirtualcointype.fid",coin.getFid());
		Fvirtualwallet fvirtualwallet = fvirtualwallets.get(0);
		if(otcad.getAd_type() == OtcOrderTypeEnum.buy_order && fvirtualwallet.getFtotal() < otcad.getRepertory_count()) {
			modelAndView.addObject("errCode", "-10012");
			modelAndView.addObject("errMsg", "机构出售币数超过实际持有币数");
			modelAndView.setViewName(errviewname);
			return modelAndView;
		}
		//机构请求币数超过广告出售币数
		if(otcad.getAd_type() == OtcOrderTypeEnum.sell_order && adList.size() == 0) {
			modelAndView.addObject("errCode", "-10010");
			modelAndView.addObject("errMsg", "机构求购币数超过广告出售币数");
			modelAndView.setViewName(errviewname);
			return modelAndView;
		}
		//机构请求币数超过广告出售币数
		if(otcad.getAd_type() == OtcOrderTypeEnum.buy_order && adList.size() == 0) {
			modelAndView.addObject("errCode", "-10013");
			modelAndView.addObject("errMsg", "机构出售币数超过广告求购币数");
			modelAndView.setViewName(errviewname);
			return modelAndView;
		}
		
		
		for (FotcAdvertisement ad : adList) {
			//查询承兑商绑定的支付方式
			List<FotcUserPaytype> paytypeList = otcuserpaytypeService.findAllPayType("fuser.fid", ad.getUser().getFid());
			ad.setPaytypeList(paytypeList);
			
			String respTimeStr = otcorderService.queryAcceptAvgResptime(ad.getUser().getFid());
			ad.setRespTimeStr(respTimeStr);
		}
	        
		Collections.shuffle(adList); // 随机排序
		
		String base64Encode = Base64Utils.encodeStr(JSON.toJSONString(orderModel));
		String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/otc/adPage.html?orderParam="+URLEncoder.encode(base64Encode, "UTF-8")+"&") ;
		
		modelAndView.addObject("currentPage", currentPage) ;
		modelAndView.addObject("totalPage", totalPage) ;
		modelAndView.addObject("pagin",pagin) ;
		modelAndView.addObject("adList", adList) ;
		modelAndView.addObject("coinId", coin.getFid()) ;
		modelAndView.addObject("orderModelJson", JSON.toJSONString(orderModel)) ;
		
		modelAndView.setViewName("front/otcorder/adList") ;
		
		if(this.isMobile(request)) {
 	    	modelAndView.setViewName("mobile/newagent/adList") ;
 	    }
		
		return modelAndView ;
	}
	
	
	
	/**
	 * 用户下单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/placeOrder",method = RequestMethod.POST)
	public String placeOrder(HttpServletRequest request) {
			Integer coinId = Integer.valueOf(request.getParameter("coinId"));
		 	String orderModelJson = request.getParameter("orderModelJson");
		 	Integer adId = Integer.valueOf(request.getParameter("adId"));
			JSONObject jsonObject = new JSONObject() ;
			
			FotcOrderModel orderModel = JSON.parseObject(orderModelJson, FotcOrderModel.class);
			
			FotcAdvertisement ad = advertisementService.queryById(adId);
			double totalCount = Double.valueOf(orderModel.getTotalCount());
			double totalPrice = ad.getPrice()*totalCount;
			Fuser fuser = null;
			init();
			
			if(ad.getRepertory_count()<totalCount){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"超出可购买或出售数量"));
				return jsonObject.toString() ;
			}
			if(totalPrice>ad.getOrder_limit_max()){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"超出购买或出售限额"));
				return jsonObject.toString() ;
				
			}
			if(totalPrice<ad.getOrder_limit_min()){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"低于购买或出售限额"));
				return jsonObject.toString() ;
			}
			Fvirtualwallet organWallet = null;
			try {
				FotcOrder otcOrder = otcorderService.queryOrderByOrginOrderId(orderModel.getOrderNo());
				if(null != otcOrder) {
					if(otcOrder.getOrderStatus() >= 0) {
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"订单不能重复下单"));
						return jsonObject.toString() ;
					} else {
						//状态变更回调
						String retStr = otcorderService.updateOrderStatusCallback(otcOrder.getId());
						JSONObject  myJson = JSONObject.fromObject(retStr);
						if(myJson.getString("code").equals("0")) {
							otcOrder.setFotcAdvertisement(ad);
							otcOrder.setUnitPrice(ad.getPrice());
							otcOrder.setTotalPrice(otcOrder.getUnitPrice()*otcOrder.getAmount());
							if(countdown1 > 0) {
								otcOrder.setOrderStatus(OtcOrderStatusEnum.notreceived);
							} else {
								otcOrder.setOrderStatus(OtcOrderStatusEnum.Payable);   //待支付
							}
							otcOrder.setUpdateTime(Utils.getTimestamp());
							
							//修改广告冻结数量
							ad.setFreeze_count(ad.getFreeze_count()+otcOrder.getAmount());
							ad.setRepertory_count(ad.getRepertory_count()-otcOrder.getAmount());
							ad.setUpdate_time(Utils.getTimestamp());
							
							if(otcOrder.getOrderType() == OtcOrderTypeEnum.buy_order) {
								//获取机构商钱包信息
								organWallet = frontUserService.findVirtualWalletByUser(orderModel.getUserId(), coinId);
								if(null != organWallet && organWallet.getFtotal()<otcOrder.getAmount()){
									//资金不足
									jsonObject.accumulate("code", -1) ;
									jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.notenough")) ;
									return jsonObject.toString() ;
								}
								organWallet.setFtotal(organWallet.getFtotal()-otcOrder.getAmount());
								organWallet.setFfrozen(organWallet.getFfrozen()+otcOrder.getAmount());
							}
							
							Boolean retResult = otcorderService.updateOrderAndAd(otcOrder, ad, organWallet);
							if(!retResult) {
								jsonObject.accumulate("code", -1) ;
								jsonObject.accumulate("msg", "广告币不足，下单失败");
								return jsonObject.toString() ;
							}
							
							jsonObject.accumulate("code", 1) ;
							jsonObject.accumulate("organOrderId", otcOrder.getUserOrderId()) ;
							jsonObject.accumulate("msg", "操作成功");
						} else {
							jsonObject.accumulate("code", -1) ;
							jsonObject.accumulate("msg", "创建订单失败");
							return jsonObject.toString() ;
						}
					}
				} else {
					FotcOrder order = new FotcOrder();
					
					order.setFotcAdvertisement(ad);
					
					order.setAmount(Double.valueOf(orderModel.getTotalCount()));
					
					fuser = this.frontUserService.findById(orderModel.getUserId());
					order.setFuser(fuser);
					Fvirtualcointype cointype = new Fvirtualcointype();
					cointype.setFid(coinId);
					order.setFvirtualcointype(cointype);
					
					order.setUnitPrice(ad.getPrice());
					order.setTotalPrice(order.getUnitPrice()*order.getAmount());
					
//					order.setOrderStatus(OrderStatus.WaitingPay);   //待支付
					order.setOrderStatus(OtcOrderStatusEnum.noeffected);   //未生效
					order.setOrderType(Integer.valueOf(orderModel.getOrderType()));
					order.setUserOrderId(orderModel.getOrderNo());
					
					order.setCreateTime(Utils.getTimestamp());
					order.setUpdateTime(Utils.getTimestamp());
					order.setRemark(otcorderService.getRemark());
					order.setPhone(orderModel.getPhone());
					order.setPageUrl(orderModel.getPageUrl());
					order.setServerUrl(orderModel.getServerUrl());
					order.setReturnUrl(orderModel.getReturnUrl());
					order.setIs_third(1);      //第三方订单
					order.setIs_callback_success(0);   //回调状态
					order.setOvertime(1002);     //未超时
					
					//若为求购订单, 则保存支付方式
					if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
						 order.setPayType(Integer.valueOf(orderModel.getPayType()));
						 order.setRealName(orderModel.getName());
						 order.setPaymentAccount(orderModel.getBankAccount());
						 order.setQrCode(orderModel.getQrCode());
						 order.setBank(orderModel.getBank());
						 order.setBankBranch(orderModel.getBankBranch());
					}
					
					//保存订单
					Integer orderId = otcorderService.save3rdOtcObj(order,organWallet);
					if(orderId <= 0) {
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", "广告可用币不足，创建订单失败");
						return jsonObject.toString() ;
					}
					//状态变更回调
					String retStr = otcorderService.updateOrderStatusCallback(orderId);
//				  	String retStr = "{\'code\':\'101\',\'msg\':\'回调机构商服务端异常\'}";
				  //String retStr = "{\'code\':\'0\',\'msg\':\'成功\'}";
					JSONObject  myJson = JSONObject.fromObject(retStr);
					//查询订单
					FotcOrder otcorder = otcorderService.queryById(orderId);
					if(myJson.getString("code").equals("0")) {
						if(countdown1 > 0) {
							otcorder.setOrderStatus(OtcOrderStatusEnum.notreceived);   //未接单
						} else {
							otcorder.setOrderStatus(OtcOrderStatusEnum.Payable);   //待支付
						}
						otcorder.setUpdateTime(Utils.getTimestamp());
						
						//修改广告冻结数量
						ad.setFreeze_count(ad.getFreeze_count()+order.getAmount());
						ad.setRepertory_count(ad.getRepertory_count()-order.getAmount());
						ad.setUpdate_time(Utils.getTimestamp());
						
						if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
							//获取机构商钱包信息
							organWallet = frontUserService.findVirtualWalletByUser(orderModel.getUserId(), coinId);
							if(null != organWallet && organWallet.getFtotal()<order.getAmount()){
								//资金不足
								jsonObject.accumulate("code", -1) ;
								jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.notenough")) ;
								return jsonObject.toString() ;
							}
							organWallet.setFtotal(organWallet.getFtotal()-order.getAmount());
							organWallet.setFfrozen(organWallet.getFfrozen()+order.getAmount());
						}
						
						Boolean retResult = otcorderService.updateOrderAndAd(otcorder, ad, organWallet);
						if(!retResult) {
							jsonObject.accumulate("code", -1) ;
							jsonObject.accumulate("msg", "广告币不足，下单失败");
							return jsonObject.toString() ;
						}
						
						jsonObject.accumulate("code", 1) ;
						jsonObject.accumulate("organOrderId", order.getUserOrderId()) ;
						jsonObject.accumulate("msg", "操作成功");
					} else {
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", "创建订单失败");
						return jsonObject.toString() ;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "操作失败");
				return jsonObject.toString() ;
			}
			
			
			if(null!=fuser) {
				super.CleanSession(request);
				getSession(request).setAttribute("third_user", fuser);
				getSession(request).setAttribute("isThird", true);
				getSession(request).setMaxInactiveInterval(1200);
			}
			return jsonObject.toString() ;
		}
	
	
	/**
	 * 承兑商查询用户订单详情
	 * @param request
	 * @param model
	 * @param modelAndView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/orderRedirect")
	public ModelAndView orderRedirect(
			HttpServletRequest request, ModelAndView modelAndView
			) throws Exception{
		
		//获取等待时间
		init();
		
		String organOrderId = request.getParameter("organOrderId");
		
		//查询订单
		FotcOrder order = otcorderService.queryOrderByOrginOrderId(organOrderId);
		if(null != order) {
			//查询广告
			FotcAdvertisement ad = advertisementService.queryById(order.getFotcAdvertisement().getId());
			
			Fuser fuser = order.getFuser();
			
			modelAndView.addObject("ad", ad) ;
			modelAndView.addObject("order", order) ;
			modelAndView.addObject("fuser", fuser) ;
			modelAndView.addObject("returnUrl", order.getReturnUrl()) ;
			modelAndView.addObject("isCallSucc",order.getIs_callback_success());
			
			if(order.getOrderStatus()!=null&&order.getOrderStatus()==OtcOrderStatusEnum.notreceived){			
				modelAndView.addObject("countdown", countdown1) ;
				
			}else if(order.getOrderStatus()!=null&&order.getOrderStatus()==OtcOrderStatusEnum.Payable){
				if(order.getOvertime()!=null&&order.getOvertime()==OtcPendAppealEnum.yes){
					modelAndView.addObject("countdown", countdown3) ;
				}else if(order.getOvertime()!=null&&order.getOvertime()==OtcPendAppealEnum.no){
					modelAndView.addObject("countdown", countdown2) ;
				}
				
			}else if(order.getOrderStatus()!=null&&order.getOrderStatus()==OtcOrderStatusEnum.Paid){
				if(order.getOvertime()!=null&&order.getOvertime()==OtcPendAppealEnum.yes){
					modelAndView.addObject("countdown", countdown5) ;
				}else if(order.getOvertime()!=null&&order.getOvertime()==OtcPendAppealEnum.no){
					modelAndView.addObject("countdown", countdown4) ;
				}
				
			}else if(order.getOrderStatus()!=null&&order.getOrderStatus()==OtcOrderStatusEnum.Recognitionreceipt){
				modelAndView.addObject("countdown", countdown6) ;
				
			}else{
				modelAndView.addObject("countdown", 0) ;
			}
			List<FotcUserPaytype> paytypeList = null;
			//买单(用户或广告商)
			if((fuser.isFismerchant() && order.getOrderType() == OtcOrderTypeEnum.buy_order) || 
					(!fuser.isFismerchant() && order.getOrderType() == OtcOrderTypeEnum.sell_order)) {
				if(!fuser.isFismerchant()) {
					//查询用户绑定的支付方式
					paytypeList = otcuserpaytypeService.findAllPayType("fuser.fid", ad.getUser().getFid());
				} else {
					//查询用户绑定的支付方式
					paytypeList = otcuserpaytypeService.findAllPayType("fuser.fid", order.getFuser().getFid());
				}
				modelAndView.setViewName("front/newagent/userBuyOrder") ;
				if(this.isMobile(request)) {
		 	    	modelAndView.setViewName("mobile/newagent/userBuyOrder") ;
		 	    }
			} else {   //卖单(用户或广告商)
				//查询用户绑定的支付方式
				modelAndView.setViewName("front/newagent/userSellOrder") ;
				if(this.isMobile(request)) {
		 	    	modelAndView.setViewName("mobile/newagent/userSellOrder") ;
		 	    }
			}
			modelAndView.addObject("paytypeList", paytypeList) ;
			
		}
		
		return modelAndView;
	}
	
	
	
	/**
	 * 页面通知
	 * @param request
	 * @param attr
	 * @param orderId
	 * @param coinName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/pageJump", method = { RequestMethod.POST, RequestMethod.GET })
	public String pageJump(HttpServletRequest request,RedirectAttributes attr,
			@RequestParam(required=true)Integer orderId) throws Exception {
		
		//查询订单
		FotcOrder order = otcorderService.queryById(orderId);
		
		//查询api
		Fapi fapi = apiService.findFapiByUserId(order.getFuser().getFid());
				
//		order.setOrderStatus(OrderStatus.success);
//		order.setUpdateTime(Utils.getTimestamp());
//		otcorderService.updateObj(order);
//		
//		order = otcorderService.queryById(orderId);
		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("partnerId", fapi.getFpartner());
//		resultMap.put("type", order.getUserOrderId());
		
//		//拼接参数
//		String paramStr = MD5SignHelper.getParamStr(resultMap);
//
//	    //MD5加密
//	    String sign = MD5SignHelper.getSign(paramStr, fapi.getFsecret());
//	    
//	    resultMap.put("sign", sign);
//	    
//	    attr.addAttribute("result", JSON.toJSONString(resultMap));
	    attr.addAttribute("type", order.getOrderType());

	    String pageUrl = null;
	    if(StringUtils.isNotBlank(order.getPageUrl())) {
	    	pageUrl = order.getPageUrl();
	    } else {
	    	FotcInstitutionsinfo instInfo = otcinstitutionsinfoService.findByUserId(order.getFuser().getFid());
	    	pageUrl = instInfo.getPage_callback_url();
	    }
	    
//		return "redirect:http://192.168.0.158:8480/order/index.html";
		return "redirect:"+pageUrl;
	}
	
	
	
	
	/*public static void main(String[] args) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", Utils.getTimestamp().getTime());
		paramMap.put("coinType", "BTC");
		paramMap.put("orderType", "1");
		paramMap.put("totalCount", "2");
		paramMap.put("payType", "1");
		paramMap.put("name", "叶孤城");
		paramMap.put("bankAccount", "888888888888888");
		paramMap.put("bank", "中国银行");
		paramMap.put("bankBranch", "深圳市科技园支行");
		paramMap.put("qrCode", "http://192.168.0.108:8080/upload/download/201807191644594891096c48b65e44b4999596aaa00005454.png.html");
		paramMap.put("phone", "17880612013");
		paramMap.put("remark", "");
		paramMap.put("partnerId", "994275c0-01da-4f62-82a1-29690c08fcc2");
		paramMap.put("pageUrl", "http://192.168.0.108:8081/exchange/success.html");
		paramMap.put("serverUrl", "http://192.168.0.108:8081/account/updateOrderStatus.html");
		
		//传递参数
		String paramStr = MD5SignHelper.getParamStr(paramMap);

	    //对RSA密文后边追加MD5密钥后进行MD5加密，
	     String MD5OrderParam = MD5SignHelper.getSign(paramStr, RSA_main.MD5_KEY);
	     paramMap.put("action", "otcPay");
	     paramMap.put("sign", MD5OrderParam);
	     HttpClientUtils httppost = new HttpClientUtils();
	     httppost.post("http://192.168.0.114:8081/otc/otcApi.html", paramMap);
		 String orderParam = JSON.toJSONString(paramMap);
		 System.out.println(orderParam);
	}*/
}
