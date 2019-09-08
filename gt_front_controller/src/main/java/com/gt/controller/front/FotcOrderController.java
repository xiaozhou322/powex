package com.gt.controller.front;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.CountLimitTypeEnum;
import com.gt.Enum.OtcAppealReasonEnum;
import com.gt.Enum.OtcAppealStatusEnum;
import com.gt.Enum.OtcOrderLogsTypeEnum;
import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.Enum.OtcOrderTypeEnum;
import com.gt.Enum.OtcPendAppealEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FotcAdvertisement;
import com.gt.entity.FotcBlacklist;
import com.gt.entity.FotcOrder;
import com.gt.entity.FotcUserPaytype;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FotcAdvertisementService;
import com.gt.service.front.FotcBlacklistService;
import com.gt.service.front.FotcOrderService;
import com.gt.service.front.FotcUserPaytypeService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Constant;
import com.gt.util.DateUtil;
import com.gt.util.PaginUtil;
import com.gt.util.Utils;
import com.gt.util.XlsExport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author jqy
 *
 */
@Controller
@RequestMapping("/order")
public class FotcOrderController extends BaseController {
	private static final String ERROR_CODE = "code" ;
	private int numPerPage = Utils.getNumPerPage();
	@Autowired
	private FotcOrderService otcOrderService;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FotcAdvertisementService advertisementService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FotcUserPaytypeService otcuserpaytypeService ;
	@Autowired
	private FrontConstantMapService constantMap ;
	@Autowired
	private FotcBlacklistService otcBlacklistService ;
	
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
	
	/**
	 * 下单接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/placeOrder", method=RequestMethod.POST,produces={JsonEncode})
	public String placeOrder(HttpServletRequest request) throws Exception{
		double usdt_num = Double.valueOf(request.getParameter("usdt_num"));  //数量
	 	double price = Double.valueOf(request.getParameter("price"));    //单价
		int ad_id = Integer.valueOf(request.getParameter("ad_id"));     //广告id
		Integer orderType = Integer.valueOf(request.getParameter("orderType"));    //订单类型
		double cny_num = Double.valueOf(request.getParameter("cny_num"));      //总价
		String pay_pwd = request.getParameter("pay_pwd");     //交易密码
		/*double fees = Double.valueOf(request.getParameter("free"));*/
		
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		//要求用户完成KYC2，才能进行出售操作
		if(orderType==OtcOrderTypeEnum.buy_order && !fuser.isFhasImgValidate()) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"请先完成KYC2认证，才能进行出售操作"));
			return jsonObject.toString() ;
		}
		
		FotcAdvertisement ad = advertisementService.queryById(ad_id);
		
		init();
		
		//判断用户是否是黑名单用户
		FotcBlacklist otcBlacklist = otcBlacklistService.findByUserId(fuser.getFid());
		if(null != otcBlacklist) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"您已被列入OTC黑名单用户"));
			return jsonObject.toString() ;
		}
		
		//判断用户是否当日手动取消次数超限(只有用户买入才有手动取消)
		StringBuffer filter = new StringBuffer();
		filter.append(" where buyUser.fid = " + fuser.getFid());
		filter.append(" and orderType = " + OtcOrderTypeEnum.sell_order);
		filter.append(" and type = " + OtcOrderLogsTypeEnum.hand_cancel);
		filter.append(" and DATE_FORMAT(createTime,'%Y-%m-%d') = '" + DateUtil.nowDateForStrYMD() +"' ");
		int totalCount = this.adminService.getAllCount("FotcOrderLogs", filter+"");
		//从缓存获取OTC允许用户申诉失败总次数
		String otcHandCancelTimes = frontConstantMapService.getString("otcHandCancelTimes");
		int times = (StringUtils.isNotBlank(otcHandCancelTimes))?Integer.valueOf(otcHandCancelTimes):5;
		if(totalCount >= times) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"您当日取消已达"+times+"次，请明日再试"));
			return jsonObject.toString() ;
		}
		
		if(fuser.isFismerchant())
		{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"承兑商不能购买或出售"));
			return jsonObject.toString() ;
		}
		if(fuser.getFid()==ad.getUser().getFid())
		{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"您不能和自己交易"));
			return jsonObject.toString() ;
		}
		if(ad.getRepertory_count()<usdt_num){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"超出可购买或出售数量"));
			return jsonObject.toString() ;
		}
		if(cny_num>ad.getOrder_limit_max()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"超出购买或出售限额"));
			return jsonObject.toString() ;
			
		}
		if(cny_num<ad.getOrder_limit_min()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"低于购买或出售限额"));
			return jsonObject.toString() ;
		}
		
		Fvirtualwallet fvirtualwallet = null;
		if(orderType == OtcOrderTypeEnum.buy_order) {   //出售订单
			//获取用户钱包信息
			fvirtualwallet = frontUserService.findVirtualWalletByUser(fuser.getFid(), ad.getFvirtualcointype().getFid());
			if(fvirtualwallet.getFtotal()<usdt_num){
				//资金不足
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"钱包余额不足")) ;
				return jsonObject.toString() ;
			}
			if(fuser.getFtradePassword()==null){
				//没有设置交易密码
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg",getLocaleMessage(request,null,"没有设置交易密码")) ;
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
					boolean flag = fuser.getFtradePassword().equals(Utils.MD5(pay_pwd,fuser.getSalt())) ;
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
		}
		
		try{
			FotcOrder order = new FotcOrder();
			
			order.setFotcAdvertisement(ad);
			order.setAmount(usdt_num);
			order.setFuser(fuser);
			
			Fvirtualcointype cointype = this.virtualCoinService.findById(ad.getFvirtualcointype().getFid());
			order.setFvirtualcointype(cointype);
			
			order.setUnitPrice(ad.getPrice());
			order.setTotalPrice(cny_num);
			
			if(countdown1 > 0) {
				order.setOrderStatus(OtcOrderStatusEnum.notreceived);   //待接单
			} else {
				order.setOrderStatus(OtcOrderStatusEnum.Payable);   //待支付
			}
			order.setOrderType(orderType);      //订单类型  1：购买   2：出售
			order.setUserOrderId(otcOrderService.getOrderIdByTime());
			
			order.setCreateTime(Utils.getTimestamp());
			order.setUpdateTime(Utils.getTimestamp());
			order.setRemark(otcOrderService.getRemark());
			order.setPhone(fuser.getFtelephone());
			order.setIs_third(0);      //非第三方订单
			order.setOvertime(1002);     //未超时
			order.setIs_callback_success(0);   //回调状态
			
			 if(OtcOrderTypeEnum.buy_order == order.getOrderType()) {   //出售订单
				//冻结钱包
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-order.getAmount());
				fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+order.getAmount());
			 }
			
			//修改广告冻结数量
			ad.setFreeze_count(ad.getFreeze_count()+order.getAmount());
			ad.setRepertory_count(ad.getRepertory_count()-order.getAmount());
			ad.setUpdate_time(Utils.getTimestamp());
			
			//保存订单记录
			int retOrderId = otcOrderService.saveUserOtcObj(order, ad, fvirtualwallet);
			
			if(retOrderId <= 0) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "广告可用币不足，创建订单失败");
				return jsonObject.toString() ;
			}
			
			jsonObject.accumulate("code", 1) ;
			jsonObject.accumulate("orderId", retOrderId) ;
			jsonObject.accumulate("msg", "操作成功");
			return jsonObject.toString() ;
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "操作失败");
			return jsonObject.toString() ;
		}
		
	}
	
	
	
	/**
	 * 广告商接单接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/receiveOrder", method= {RequestMethod.GET,RequestMethod.POST},produces={JsonEncode})
	public String receiveOrder(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId) throws Exception{
		 JSONObject jsonObject = new JSONObject() ;
		 try {
			 //查询订单
			 FotcOrder order = otcOrderService.queryById(orderId);
			 FotcAdvertisement ad = advertisementService.queryById(order.getFotcAdvertisement().getId());
			 
			 Fuser fuser =GetSession(request);
			 
			 if(!fuser.isFismerchant()) {
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"只有承兑商才能接单")) ;
				 return jsonObject.toString() ;
			 }
			 
			//防止操作他人订单
			 if(fuser.getFid()!=order.getFotcAdvertisement().getUser().getFid()) {
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
				 return jsonObject.toString() ;
			 }
			 
			 if(OtcOrderStatusEnum.notreceived != order.getOrderStatus()) {
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg", getLocaleMessage(request,null,"不是未接单订单"));
				 return jsonObject.toString() ;
			 }
			 
			 //待支付
			 order.setOrderStatus(OtcOrderStatusEnum.Payable);
			 order.setUpdateTime(Utils.getTimestamp());
			 
			 otcOrderService.updateObj(order);
			 
			 //第三方订单回调
			 if(order.getFuser().isFisorganization() && order.getIs_third() == 1) {
				 //状态变更回调
				 otcOrderService.updateOrderStatusCallback(orderId);
			 }
			 
			 jsonObject.accumulate("code", 1) ;
			 jsonObject.accumulate("msg", "操作成功");
			 return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "操作失败");
			return jsonObject.toString() ;
		}
	}
	
	
	/**
	 * 付款和收款接口
	 * @param request
	 * @param orderId
	 * @param orderStatus
	 * @param payType
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/receiveOrPayOrder")
	public String receiveOrPayOrder(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId,
			@RequestParam(required=true)Integer orderStatus,
			@RequestParam(required=false)Integer payType) throws Exception{
		 JSONObject jsonObject = new JSONObject() ;
		 try {
			 
			 init();
			 
			 //查询订单
			 FotcOrder order = otcOrderService.queryById(orderId);
			 
//			 Fuser fuser=null;
//			 if(order.getIs_third()==1) {
//				 //防止机构商操作第三方订单
//				 Object session_user = getSession(request).getAttribute("third_user");
//				 if(null!=session_user) {
//					 fuser=(Fuser) session_user;
//				 }else {
//					 jsonObject.accumulate("code", -1) ;
//					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"不允许机构商直接操作第三方订单")) ;
//					 return jsonObject.toString() ;
//				 }
//			 }else {
//				 fuser =GetSession(request);
//			 }
			 
			//防止操作他人下单订单
//			 if(fuser.getFid()!=order.getFuser().getFid()) {
//				 jsonObject.accumulate("code", -1) ;
//				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
//				 return jsonObject.toString() ;
//			 }
			 
			 if(OtcOrderStatusEnum.failOrder == order.getOrderStatus()) {
				 //订单已失败
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单已失败")) ;
				 return jsonObject.toString() ;
			 }
			 if(OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 //订单已成功
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单已成功，请勿重复确认")) ;
				 return jsonObject.toString() ;
			 }
			 if(OtcOrderStatusEnum.ExceptionOrder == order.getOrderStatus()) {
				 //订单异常
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单异常")) ;
				 return jsonObject.toString() ;
			 }
			 
			 if(OtcOrderStatusEnum.Recognitionreceipt == order.getOrderStatus()) {
				 //订单已确认
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"请勿重复确认")) ;
				 return jsonObject.toString() ;
			 }
			 
			 //只有订单状态未待付款和已付款，才走付款收款流程
			 if(OtcOrderStatusEnum.Payable != order.getOrderStatus() && OtcOrderStatusEnum.Paid != order.getOrderStatus()) {
				 //订单已确认
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单状态不符，请勿非法操作")) ;
				 return jsonObject.toString() ;
			 }
			 
			 //确认付款
			 if(OtcOrderStatusEnum.Paid == orderStatus){
				 if(null != payType) {
					 //查询选中的支付方式
					 FotcUserPaytype paytype = otcuserpaytypeService.queryById(payType);
					 order.setPayType(paytype.getPayType());
					 order.setRealName(paytype.getRealName());
					 order.setPaymentAccount(paytype.getPaymentAccount());
					 order.setQrCode(paytype.getQrCode());
					 order.setBank(paytype.getBank());
					 order.setBankBranch(paytype.getBankBranch());
				 }
				 order.setOrderStatus(orderStatus);
				 order.setUpdateTime(Utils.getTimestamp());
				 otcOrderService.updateObj(order);
				 
				 //查询订单
				 order = otcOrderService.queryById(orderId);
				 
				 jsonObject.accumulate("code", 1) ;
				 jsonObject.accumulate("payType", order.getPayType()) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"操作成功")) ;
			 }else if(OtcOrderStatusEnum.Recognitionreceipt == orderStatus) {
				 //确认收款流程
				 //获取用户钱包信息
				 Fvirtualwallet organWallet = frontUserService.findVirtualWalletByUser(order.getFuser().getFid(), order.getFvirtualcointype().getFid());
				
				 //获取广告商钱包信息
				 Fvirtualwallet acceptWallet = frontUserService.findVirtualWalletByUser(order.getFotcAdvertisement().getUser().getFid(), order.getFvirtualcointype().getFid());
				 if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {     
					 //用户出售
					 if(null != organWallet && organWallet.getFfrozen()<order.getAmount()){
						//用户冻结金额不足
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg",getLocaleMessage(request,null,"用户冻结金额不足")) ;
						return jsonObject.toString() ;
					 }
					 
				 } else {     
					 //用户购买
					 if(acceptWallet.getFfrozen()<order.getAmount()){
						 //承兑商冻结金额不足
						 jsonObject.accumulate("code", -1) ;
						 jsonObject.accumulate("msg",getLocaleMessage(request,null,"承兑商冻结金额不足")) ;
						 return jsonObject.toString() ;
					 }
				 }
				 if(countdown6 > 0) {
					 order.setOrderStatus(orderStatus);
					 order.setUpdateTime(Utils.getTimestamp());
					 otcOrderService.updateObj(order);
					 
					 jsonObject.accumulate("code", 1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"操作成功")) ;
				 } else {
					 jsonObject = otcOrderService.updateSucess(order.getId(), 0);
				 }
			 }
			 //第三方订单回调
			 if(order.getFuser().isFisorganization() && order.getIs_third() == 1) {
				 //状态变更回调
				 otcOrderService.updateOrderStatusCallback(orderId);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "操作失败");
			return jsonObject.toString() ;
		}
		 return jsonObject.toString() ;
	}
	
	
	
	/**
	 * 订单成功
	 * @param request
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/sucessOrder", method=RequestMethod.POST,produces={JsonEncode})
	public String sucessOrder(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		 try {
			 //查询订单
			 FotcOrder order = otcOrderService.queryById(orderId);
			 
			 /*Fuser fuser=null;
			 if(order.getIs_third()==1) {
				 //防止机构商操作第三方订单
				 Object session_user = getSession(request).getAttribute("third_user");
				 if(null!=session_user) {
					 fuser=(Fuser) session_user;
				 }else {
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"不允许机构商直接操作第三方订单")) ;
					 return jsonObject.toString() ;
				 }
			 }else {
				 fuser =GetSession(request);
			 }
			 
			//防止操作他人下单订单
			 if(fuser.getFid()!=order.getFuser().getFid()) {
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
				 return jsonObject.toString() ;
			 }*/
			 
			 if(OtcOrderStatusEnum.failOrder == order.getOrderStatus()) {
				 //订单已失败
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单已失败")) ;
				 return jsonObject.toString() ;
			 }
			 if(OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 //订单已成功
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"请勿重复确认")) ;
				 return jsonObject.toString() ;
			 }
			 
			 if(OtcOrderStatusEnum.Recognitionreceipt != order.getOrderStatus()) {
				 //订单已成功
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单状态不符，请勿非法操作")) ;
				 return jsonObject.toString() ;
			 }
			 
			 //获取用户钱包信息
			 Fvirtualwallet organWallet = frontUserService.findVirtualWalletByUser(order.getFuser().getFid(), order.getFvirtualcointype().getFid());
			
			 //获取广告商钱包信息
			 Fvirtualwallet acceptWallet = frontUserService.findVirtualWalletByUser(order.getFotcAdvertisement().getUser().getFid(), order.getFvirtualcointype().getFid());
			 if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {     
				 //用户出售
				 if(null != organWallet && organWallet.getFfrozen()<order.getAmount()){
					//用户冻结金额不足
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg",getLocaleMessage(request,null,"用户冻结金额不足")) ;
					return jsonObject.toString() ;
				 }
				//用户冻结数量减少
				organWallet.setFfrozen(organWallet.getFfrozen()-order.getAmount());
				organWallet.setFlastUpdateTime(Utils.getTimestamp());
				
				//广告商总数增加
				acceptWallet.setFtotal(acceptWallet.getFtotal()+order.getAmount());
				acceptWallet.setFlastUpdateTime(Utils.getTimestamp());
				
			 } else {
				 //用户购买
				 if(acceptWallet.getFfrozen()<order.getAmount()){
					 //承兑商冻结金额不足
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"承兑商冻结金额不足")) ;
					 return jsonObject.toString() ;
				 }
				 //广告商冻结数量减少
				 acceptWallet.setFfrozen(acceptWallet.getFfrozen() - order.getAmount());
				 acceptWallet.setFlastUpdateTime(Utils.getTimestamp());
				 
				 //用户总数增加
				 organWallet.setFtotal(organWallet.getFtotal() + order.getAmount());
				 organWallet.setFlastUpdateTime(Utils.getTimestamp());
			 }
			 
			 //获取订单的广告信息
			 FotcAdvertisement ad=advertisementService.queryById(order.getFotcAdvertisement().getId());
			 //在广告的冻结数量中减去该订单的订购数量
			 ad.setFreeze_count(ad.getFreeze_count()-order.getAmount());
			 ad.setUpdate_time(Utils.getTimestamp());
			 //判断广告的剩余求购数量和冻结是否为0，为0则下架该广告
			 if(ad.getRepertory_count()<=0 && ad.getFreeze_count()<=0){
				 ad.setStatus(1);
			 }
			 //修改订单状态
			 order.setOrderStatus(OtcOrderStatusEnum.success);
			 order.setUpdateTime(Utils.getTimestamp());
			 //修改otc钱包
			 otcOrderService.updateOtcWallet(order,organWallet,acceptWallet,ad, 0);
			 
			//第三方订单回调
			 if(order.getFuser().isFisorganization() && order.getIs_third() == 1) {
				 //状态变更回调
				 otcOrderService.updateOrderStatusCallback(orderId);
			 }
			 
			 jsonObject.accumulate("code", 1) ;
			 jsonObject.accumulate("msg",getLocaleMessage(request,null,"操作成功")) ;
			 
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "操作失败");
			return jsonObject.toString() ;
		}
		return jsonObject.toString() ;
	}
	
	
	/**
	 * 订单失败
	 * @param request
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/failedOrder", method=RequestMethod.POST,produces={JsonEncode})
	public String failedOrder(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId) throws Exception{
		 JSONObject jsonObject = new JSONObject() ;
		 try {
			 //查询订单
			 FotcOrder order = otcOrderService.queryById(orderId);

			/* Fuser fuser=null;
			 if(order.getIs_third()==1) {
				 //防止机构商操作第三方订单
				 Object session_user = getSession(request).getAttribute("third_user");
				 if(null!=session_user) {
					 fuser=(Fuser) session_user;
				 }else {
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"不允许机构商直接操作第三方订单")) ;
					 return jsonObject.toString() ;
				 }
			 }else {
				 fuser =GetSession(request);
			 }
			 
			//防止操作他人下单订单
			 if(fuser.getFid()!=order.getFuser().getFid()) {
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
				 return jsonObject.toString() ;
			 }*/
			 
			 Fuser fuser=null;
			 fuser =GetSession(request);
			 
			 //待接单阶段，广告方才允许取消
			 if(OtcOrderStatusEnum.notreceived == order.getOrderStatus()) {
				 if(fuser.getFid()!=order.getFotcAdvertisement().getUser().getFid()) {
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
					 return jsonObject.toString() ;
				 }
			 }
			 
			//待付款阶段，买方才允许取消
			 if(OtcOrderStatusEnum.Payable == order.getOrderStatus()) {
				 
				 if (order.getOrderType()==OtcOrderTypeEnum.sell_order) {
					 //如果是买单只允许用户取消
					 if(fuser.getFid()!=order.getFuser().getFid()) {
						 jsonObject.accumulate("code", -1) ;
						 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
						 return jsonObject.toString() ;
					 }
				 }else {
					 //如果是卖单，则只允许挂单的广告方取消
					 if(fuser.getFid()!=order.getFotcAdvertisement().getUser().getFid()) {
						 jsonObject.accumulate("code", -1) ;
						 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
						 return jsonObject.toString() ;
					 }
				 }
			 }
			 
			 if(OtcOrderStatusEnum.failOrder == order.getOrderStatus()) {
				 //订单已失败
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单已失败")) ;
				 return jsonObject.toString() ;
			 }
			 if(OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 //订单已成功
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单已成功")) ;
				 return jsonObject.toString() ;
			 }
			 if((OtcOrderStatusEnum.notreceived != order.getOrderStatus() && OtcOrderStatusEnum.Payable != order.getOrderStatus()) || null!=order.getAppeal_status()) {
				 //订单为待付款阶段，且订单不能异常状态
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单状态不符或者正处于申诉阶段，请勿非法操作")) ;
				 return jsonObject.toString() ;
			 }
			 Fvirtualwallet organWallet = null;
			 if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
				 //获取用户钱包信息
				 organWallet = frontUserService.findVirtualWalletByUser(order.getFuser().getFid(), order.getFvirtualcointype().getFid());
				 if(organWallet.getFfrozen()<order.getAmount()){
					 //机构商冻结资金不足
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg","机构商冻结金额不足") ;
					 return jsonObject.toString() ;
				 }
				 //解冻钱包
				 organWallet.setFtotal(organWallet.getFtotal()+order.getAmount());
				 organWallet.setFfrozen(organWallet.getFfrozen()-order.getAmount());
			 }
			 FotcAdvertisement ad = advertisementService.queryById(order.getFotcAdvertisement().getId());
			 if(ad.getFreeze_count() < order.getAmount()) {
				 //广告冻结金额不足
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg","广告冻结金额不足") ;
				 return jsonObject.toString() ;
			 }
			 //广告解冻数量
			 ad.setFreeze_count(ad.getFreeze_count()-order.getAmount());
			 ad.setRepertory_count(ad.getRepertory_count()+order.getAmount());
			 ad.setUpdate_time(Utils.getTimestamp());
			 
			 //修改订单状态
			 order.setOrderStatus(OtcOrderStatusEnum.failOrder);
			 order.setUpdateTime(Utils.getTimestamp());
			 
			 //用户手动取消订单
			 otcOrderService.updateCancelOrder(order, ad, organWallet, OtcOrderLogsTypeEnum.hand_cancel);
			 
			//第三方订单回调
			 if(order.getFuser().isFisorganization() && order.getIs_third() == 1) {
				 //状态变更回调
				 otcOrderService.updateOrderStatusCallback(orderId);
			 }
			 
			 jsonObject.accumulate("code", 1) ;
			 jsonObject.accumulate("msg",getLocaleMessage(request,null,"操作成功")) ;
			 
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "操作失败");
			return jsonObject.toString() ;
		}
		return jsonObject.toString() ;
	}
	
	
	/**
	 * 申诉
	 * @param request
	 * @param orderId
	 * @param payType
	 * @param orderDesc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/appeal")
	public String appeal(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId) throws Exception{
		 JSONObject jsonObject = new JSONObject() ;
		 FotcOrder fotcorder = otcOrderService.queryById(orderId);
		 if(fotcorder!=null){
			 
			 Fuser fuser=GetSession(request);
			 if (null==fuser) {
				 Object session_user = getSession(request).getAttribute("third_user");
				 if(null!=session_user) {
					 fuser=(Fuser) session_user;
				 }
			 }
			 
			 /*if(fotcorder.getIs_third()==1 && !fuser.isFismerchant()) {
				//非承兑商要防止机构方操作第三方订单
				 if(null==getSession(request).getAttribute("third_user")) {
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"不允许机构商直接操作第三方订单")) ;
					 return jsonObject.toString() ;
				 }
			 }
			 
			 //防止操作他人下单订单，检查当前用户是否下单用户或者广告主
			 if(fuser.getFid()!=fotcorder.getFuser().getFid() && fuser.getFid()!=fotcorder.getFotcAdvertisement().getUser().getFid()) {
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"恶意操作，已被系统记录")) ;
				 return jsonObject.toString() ;
			 }*/
			 
			//判断是买家操作：买入订单操作人为下单人或者卖出订单操作人为广告商
			 Boolean isBuy=(fotcorder.getOrderType()==1&&fotcorder.getFuser().getFid()==fuser.getFid())||(fotcorder.getOrderType()==2&&fotcorder.getFotcAdvertisement().getUser().getFid()==fuser.getFid());
			//判断是卖家操作：买入订单操作人为广告商或者卖出订单操作人为下单人
			 Boolean isSell=(fotcorder.getOrderType()==1&&fotcorder.getFotcAdvertisement().getUser().getFid()==fuser.getFid())||(fotcorder.getOrderType()==2&&fotcorder.getFuser().getFid()==fuser.getFid());
			 if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.notreceived){
					
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg",getLocaleMessage(request,null,"未接单订单不能申诉")) ;
				 return jsonObject.toString() ;
				}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.Payable){
                  if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.yes){
                	//买家进行申诉
   					if(isBuy){
   						fotcorder.setAppeal_reason(OtcAppealReasonEnum.notreceived);
   						fotcorder.setAppeal_status(OtcAppealStatusEnum.Notprocessed);
   						fotcorder.setAppeal_user(fuser);
   						fotcorder.setOrderStatus(OtcOrderStatusEnum.ExceptionOrder);
   					//卖出订单	
   					}else {
   						 jsonObject.accumulate("code", -1) ;
						 jsonObject.accumulate("msg",getLocaleMessage(request,null,"未付款订单卖家不能申诉")) ;
						 return jsonObject.toString() ;
   					}
				}else if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.no){
						 jsonObject.accumulate("code", -1) ;
						 jsonObject.accumulate("msg",getLocaleMessage(request,null,"未付款订单没有超时，不能申诉")) ;
						 return jsonObject.toString() ;
				}
					
				}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.Paid){
                 if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.yes){
                	//买入订单
 					if(isBuy){
 						fotcorder.setAppeal_reason(OtcAppealReasonEnum.Payable);
 						fotcorder.setAppeal_status(OtcAppealStatusEnum.Notprocessed);
 						fotcorder.setAppeal_user(fuser);
 						fotcorder.setOrderStatus(OtcOrderStatusEnum.ExceptionOrder);
 					//卖出订单	
 					}else if(isSell){
 						fotcorder.setAppeal_reason(OtcAppealReasonEnum.Paid);
 						fotcorder.setAppeal_status(OtcAppealStatusEnum.Notprocessed);
 						fotcorder.setAppeal_user(fuser);
 						fotcorder.setOrderStatus(OtcOrderStatusEnum.ExceptionOrder);
 					}
					}else if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.no){
						 jsonObject.accumulate("code", -1) ;
						 jsonObject.accumulate("msg",getLocaleMessage(request,null,"未确认订单没有超时，不能申诉")) ;
						 return jsonObject.toString() ;
					}
					
				}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.Recognitionreceipt){
					 if(isSell){
						fotcorder.setAppeal_reason(OtcAppealReasonEnum.Recognitionreceipt);
						fotcorder.setAppeal_status(OtcAppealStatusEnum.Notprocessed);
						fotcorder.setAppeal_user(fuser);
						fotcorder.setOrderStatus(OtcOrderStatusEnum.ExceptionOrder);
					    }else{
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg",getLocaleMessage(request,null,"卖家已初次确认收款订单买家不能申诉")) ;
						return jsonObject.toString() ;
						}
					
					
					
				}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.ExceptionOrder){
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单正在申诉中，请勿重复申诉")) ;
					 return jsonObject.toString() ;
					
				}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.failOrder){
	
					 jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单已失败不能申诉")) ;
					 return jsonObject.toString() ;
               }else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.success){
	
            	     jsonObject.accumulate("code", -1) ;
					 jsonObject.accumulate("msg",getLocaleMessage(request,null,"订单成功不能申诉")) ;
					 return jsonObject.toString() ;
               }
			 
			 otcOrderService.updateObj(fotcorder);
			 jsonObject.accumulate("code", 1) ;
			 jsonObject.accumulate("msg",getLocaleMessage(request,null,"申诉成功")) ;
		 }else{
			 jsonObject.accumulate("code", -1) ;
			 jsonObject.accumulate("msg",getLocaleMessage(request,null,"没有该订单信息")) ;
		 }
		 
		//第三方订单回调
		 if(fotcorder.getFuser().isFisorganization() && fotcorder.getIs_third() == 1) {
			 //状态变更回调
			 otcOrderService.updateOrderStatusCallback(orderId);
		 }
		 
		return jsonObject.toString() ;
	}
	
	
	/**
	 * 订单列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orderList")
	public ModelAndView orderList(HttpServletRequest request)throws Exception{
		Fuser user=GetSession(request);
		ModelAndView modelAndView = new ModelAndView("front/newagent/order_list") ;
		if(this.isMobile(request)) {
 	    	modelAndView.setViewName("mobile/newagent/order_list") ;
 	    }
		
		String status=request.getParameter("status");
		
		String id = request.getParameter("id");
		String orderStatus = request.getParameter("orderStatus");
		String ad_type = request.getParameter("ad_type");
		String remark = request.getParameter("remark");
		String amount_type = request.getParameter("amount_type");

		if(id != null && id.trim().length() >0){
			
 			modelAndView.addObject("id", id);
 			
		}
		if(orderStatus != null && orderStatus.trim().length() >0&&!"-10".equals(orderStatus)){
			
 			modelAndView.addObject("orderStatus", orderStatus);
 			
		}
		if(ad_type != null && ad_type.trim().length() >0&&!"-10".equals(ad_type)){
			
 			modelAndView.addObject("ad_type", ad_type);
 			
		}

		if(remark != null && remark.trim().length() >0){
			
			modelAndView.addObject("remark", remark);
			
		}

		if(StringUtils.isNotBlank(amount_type)){
			modelAndView.addObject("amount_type", amount_type);	
		}
      if(status!=null&&!"".equals(status)){
    	  modelAndView.addObject("status",Integer.parseInt(status)) ;
      }else{
    	  modelAndView.addObject("status", 0) ; 
      }
      if(user!=null&&user.isFismerchant()){
    	  modelAndView.addObject("isMerchant", true) ; 
      }else{
    	  modelAndView.addObject("isMerchant", false) ; 
      }
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		
		Map<Integer,String> orderStatusList=OtcOrderStatusEnum.getAll();	
		Map<Integer,String> orderType=OtcOrderTypeEnum.getAll();
		modelAndView.addObject("numPerPage", numPerPage);	
		modelAndView.addObject("orderStatusList", orderStatusList);
		modelAndView.addObject("orderType", orderType);
		modelAndView.addObject("amountTypeMap", allType);

		return modelAndView;
	}	
	
	
	//
	@ResponseBody
	@RequestMapping(value="/orderList1",produces=JsonEncode)
	public String orderList1(HttpServletRequest request) throws Exception {
		int currentPage = 1;
		try {
			String orderField = request.getParameter("orderField");
		String amount_type = request.getParameter("amount_type");

		String url=" /order/orderPageList.html?1=1";
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front/newagent/order_pagelist") ;
		if(this.isMobile(request)) {
 	    	modelAndView.setViewName("mobile/newagent/order_pagelist") ;
 	    }
		String id = request.getParameter("id");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderStatus = request.getParameter("orderStatus");
		String ad_type = request.getParameter("ad_type");
		String remark = request.getParameter("remark");
		
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(id != null && id.trim().length() >0){
			filter.append("and  id = "+id+"  \n");
 			modelAndView.addObject("id", id);
 			url+="&id="+id;
		}
		if(orderStatus != null && orderStatus.trim().length() >0&&!"-10".equals(orderStatus)){
			filter.append("and  orderStatus = "+Integer.parseInt(orderStatus)+"  \n");
 			modelAndView.addObject("orderStatus", orderStatus);
 			url+="&orderStatus="+orderStatus;
		}
		if(ad_type != null && ad_type.trim().length() >0&&!"-10".equals(ad_type)){
			filter.append("and  orderType = "+Integer.parseInt(ad_type)+"  \n");
 			modelAndView.addObject("ad_type", ad_type);
 			url+="&ad_type="+ad_type;
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
			url+="&startDate="+startDate;
		}
		if(remark != null && remark.trim().length() >0){
			filter.append("and  remark='"+remark+"' \n");
			modelAndView.addObject("remark", remark);
			url+="&remark="+remark;
		}
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
			url+="&endDate = "+endDate;
		}
		if(request.getParameter("currentPage") != null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
	
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			
			if(request.getParameter("pageNum") != null){
				currentPage = Integer.parseInt(request.getParameter("pageNum"));
			}


				filter.append("  and (fuser.fid="+GetSession(request).getFid()+" OR fotcAdvertisement.user.fid="+GetSession(request).getFid()+")");

				filter.append(" and orderStatus in ("+OtcOrderStatusEnum.notreceived+","
						+OtcOrderStatusEnum.Payable+","+OtcOrderStatusEnum.Paid+","
						+OtcOrderStatusEnum.Recognitionreceipt+","+OtcOrderStatusEnum.ExceptionOrder+")");


			if(StringUtils.isNotBlank(amount_type)){
				filter.append(" and fvirtualcointype.fid="+Integer.valueOf(amount_type));	
			}
			if(orderField != null && orderField.trim().length() >0){
				filter.append(" order by "+orderField+"\n");
			}else{
				filter.append(" order by id, updateTime desc \n");
			}
			List<FotcOrder> list = this.otcOrderService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			
			JSONObject jsonObject = new JSONObject() ;
			
			JSONArray jsonArray = new JSONArray() ;
						
			for (FotcOrder fotcorder : list) {
				JSONObject fvirwalletObj = new JSONObject() ;
				Fuser user=fotcorder.getFuser();
				Fvirtualcointype fvirtualcointype=fotcorder.getFvirtualcointype();
				FotcAdvertisement fotcAdvertisement=fotcorder.getFotcAdvertisement();
				fvirwalletObj.accumulate("id", fotcorder.getId()) ;
				fvirwalletObj.accumulate("user_id", fotcorder.getFuser().getFid()) ;
				fvirwalletObj.accumulate("user_name", fotcorder.getFuser().getFrealName()) ;
				fvirwalletObj.accumulate("order_user_id", fotcorder.getFuser().getFid()) ;
				fvirwalletObj.accumulate("coin_type", fvirtualcointype.getfShortName()) ;
				if(GetSession(request).isFismerchant()){
				fvirwalletObj.accumulate("order_type", fotcAdvertisement.getAd_type()==1 ? "出售" : "求购") ;
				}else{
					fvirwalletObj.accumulate("order_type", fotcAdvertisement.getAd_type()==1 ? "买入" : "卖出") ;	
				}
				fvirwalletObj.accumulate("unit_price", fotcorder.getUnitPrice()) ;
				fvirwalletObj.accumulate("amount", fotcorder.getAmount()) ;
				fvirwalletObj.accumulate("total_price", fotcorder.getTotalPrice()) ;
				fvirwalletObj.accumulate("order_statusDesc", OtcOrderStatusEnum.getEnumString(fotcorder.getOrderStatus())) ;
				fvirwalletObj.accumulate("create_time", Utils.dateFormat(fotcorder.getCreateTime())) ;
				//承兑商出售，用户购买，并且用户已打款的订单，承兑商显示确认收到款按钮
				if("1".equals(fotcAdvertisement.getAd_type().toString())&&"1".equals(fotcorder.getOrderType().toString())&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.Paid){
					fvirwalletObj.accumulate("receiptsbuttunShow", true) ;
					fvirwalletObj.accumulate("paybuttunShow", false) ;
				}else if("2".equals(fotcAdvertisement.getAd_type().toString())&&"2".equals(fotcorder.getOrderType().toString())&&(fotcorder.getOrderStatus()==OtcOrderStatusEnum.Payable)){
					fvirwalletObj.accumulate("receiptsbuttunShow", false) ;
					fvirwalletObj.accumulate("paybuttunShow", true) ;
				}else{
					fvirwalletObj.accumulate("receiptsbuttunShow", false) ;
					fvirwalletObj.accumulate("paybuttunShow", false) ;
				}
				if(fotcorder.getUpdateTime()!=null&&!"".equals(fotcorder.getUpdateTime())){
					fvirwalletObj.accumulate("update_time", Utils.dateFormat(fotcorder.getUpdateTime())) ;
				}else{
					fvirwalletObj.accumulate("update_time", "") ;
				}
				if(null!=fotcorder.getFuser().getFregfrom() && !fotcorder.getFuser().getFregfrom().equals("")) {
					fvirwalletObj.accumulate("remark", fotcorder.getRemark()+"(APP)") ;
				}else {
					fvirwalletObj.accumulate("remark", fotcorder.getRemark()) ;
				}
				fvirwalletObj.accumulate("userName", user.getFrealName()) ;
				fvirwalletObj.accumulate("order_status", fotcorder.getOrderStatus()) ;
				
				if(fuser.getFid() == user.getFid()) {
					fvirwalletObj.accumulate("isAgent", false) ;  //是否为承兑商
				} else {
					fvirwalletObj.accumulate("isAgent", true) ;  //是否为承兑商
				}
				
				jsonArray.add(fvirwalletObj) ;
			}
			
			jsonObject.accumulate("orderList", jsonArray) ;
			jsonObject.accumulate("success", true) ;
			return jsonObject.toString() ;
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate("success", false) ;
			jsonObject.accumulate(ERROR_CODE, 10002) ;//系统错误
			return jsonObject.toString();
		}
		
	}
	//
	@ResponseBody
	@RequestMapping(value = "/allList", produces = JsonEncode)
	public String allList(HttpServletRequest request) throws Exception {
		try {
			StringBuffer filter = new StringBuffer();
			filter.append(" where 1=1 and (fuser.fid="
					+ GetSession(request).getFid()
					+ " OR fotcAdvertisement.user.fid="
					+ GetSession(request).getFid() + ")");
			filter.append(" and orderStatus in ("+OtcOrderStatusEnum.notreceived+","
					+OtcOrderStatusEnum.Paid+","+OtcOrderStatusEnum.Recognitionreceipt+")");
			int num = this.adminService.getAllCount("FotcOrder", filter + "");
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("count", num);
			jsonObject.accumulate("success", true);
			return jsonObject.toString();
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("success", false);
			jsonObject.accumulate(ERROR_CODE, 10002);// 系统错误
			return jsonObject.toString();
		}

	}	
	
	//机构商和普通用户查询订单列表，用户id为订单中的用户id
	@ResponseBody
	@RequestMapping(value="/orderPageList",produces=JsonEncode)
	public ModelAndView orderPageList(HttpServletRequest request) throws Exception {
		int currentPage = 1;
		int numPerPage=10;
		if(this.isMobile(request)){
			numPerPage = maxResults;
		}
		String orderField = request.getParameter("orderField");
		String amount_type = request.getParameter("amount_type");

		String url=" /order/orderPageList.html?1=1";
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front/newagent/order_pagelist") ;
		
		String id = request.getParameter("id");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderStatus = request.getParameter("orderStatus");
		String ad_type = request.getParameter("ad_type");
		String remark = request.getParameter("remark");
		
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(id != null && id.trim().length() >0){
			filter.append("and  id = "+id+"  \n");
 			modelAndView.addObject("id", id);
 			url+="&id="+id;
		}
		if(orderStatus != null && orderStatus.trim().length() >0&&!"-10".equals(orderStatus)){
			filter.append("and  orderStatus = "+Integer.parseInt(orderStatus)+"  \n");
 			modelAndView.addObject("orderStatus", orderStatus);
 			url+="&orderStatus="+orderStatus;
		}
		if(ad_type != null && ad_type.trim().length() >0&&!"-10".equals(ad_type)){
			filter.append("and  orderType = "+Integer.parseInt(ad_type)+"  \n");
 			modelAndView.addObject("ad_type", ad_type);
 			url+="&ad_type="+ad_type;
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
			url+="&startDate="+startDate;
		}
		if(remark != null && remark.trim().length() >0){
			filter.append("and  remark='"+remark+"' \n");
			modelAndView.addObject("remark", remark);
			url+="&remark="+remark;
		}
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
			url+="&endDate="+endDate;
		}
		if(request.getParameter("currentPage") != null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		filter.append("  and (fuser.fid="+GetSession(request).getFid()+" OR fotcAdvertisement.user.fid="+GetSession(request).getFid()+")");


		filter.append(" and orderStatus in ("+OtcOrderStatusEnum.failOrder+","+OtcOrderStatusEnum.success+","+OtcOrderStatusEnum.ExceptionOrder+")");

		if(StringUtils.isNotBlank(amount_type)){
			filter.append(" and fvirtualcointype.fid="+Integer.valueOf(amount_type));	
			modelAndView.addObject("amount_type", amount_type);	
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append(" order by "+orderField+"\n");
		}else{
			filter.append(" order by updateTime desc \n");
		}
	
		List<FotcOrder> list = this.otcOrderService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		Map<Integer,String> orderStatusList=OtcOrderStatusEnum.getAll();	
		Map<Integer,String> orderType=OtcOrderTypeEnum.getAll();
	    modelAndView.addObject("FotcorderList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("orderStatusList", orderStatusList);
		modelAndView.addObject("orderType", orderType);
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		modelAndView.addObject("amountTypeMap", allType);
		modelAndView.addObject("amount_type", amount_type);
		int totalCount=this.adminService.getAllCount("FotcOrder", filter+"");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcOrder", filter+""));
		int totalPage = totalCount/numPerPage +(totalCount%numPerPage==0?0:1) ;
//		currentPage = currentPage<1?1:currentPage ;
//		currentPage = currentPage>totalPage?totalCount:currentPage ;
				

		String pagin  = PaginUtil.generatePagin(totalPage, currentPage,  url+"&") ;
		
		modelAndView.addObject("pagin", pagin);
		modelAndView.addObject("isMerchant", GetSession(request).isFismerchant());
		
		if(this.isMobile(request))
		{
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.setViewName("mobile/newagent/order_pagelist") ;
			if(currentPage>1)//ajax 加载
			{
				modelAndView.setViewName("mobile/newagent/order_pagelist_ajax") ;
			}
		}
        return modelAndView;
	}
		
	
	
	/**
	 * 承兑商查询用户订单详情
	 * @param request
	 * @param model
	 * @param modelAndView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/orderDetail")
	public ModelAndView orderDetail(
			HttpServletRequest request, ModelAndView modelAndView
			) throws Exception{
		
		if(null == GetSession(request)) {
			modelAndView.setViewName("front/user/sub_user_login") ;
			return modelAndView;
		}
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		//获取等待时间
		init();
				
		Integer orderId = Integer.valueOf(request.getParameter("orderId"));
		
		//查询订单
		FotcOrder order = otcOrderService.queryById(orderId);
		Fuser forderuser = this.frontUserService.findById(order.getFuser().getFid());
		//查询广告
		FotcAdvertisement ad = advertisementService.queryById(order.getFotcAdvertisement().getId());
		
		modelAndView.addObject("ad", ad) ;
		modelAndView.addObject("order", order) ;
		modelAndView.addObject("returnUrl", order.getReturnUrl()) ;
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("forderuser", forderuser) ;
		
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
		
		return modelAndView;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/queryRealValidate", method=RequestMethod.POST,produces={JsonEncode})
	public String queryRealValidate(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Fuser login_user = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(!((Fuser)login_user).getFpostRealValidate()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"请先进行实名认证"));
			return jsonObject.toString() ;
		}
		jsonObject.accumulate("code", 0);
		return jsonObject.toString();
	}
	
	/**
	 * 查询用户是否绑定支付方式
	 * @param request
	 * @param adId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/queryPayWays", method=RequestMethod.POST,produces={JsonEncode})
	public String queryUserPayWays(HttpServletRequest request,
			@RequestParam(required=true)Integer adId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		//查询承兑商绑定的支付方式
		List<FotcUserPaytype> paytypeList = otcuserpaytypeService.findAllPayType("fuser.fid", fuser.getFid());
		if(paytypeList.size() == 0) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"请先绑定支付方式"));
			return jsonObject.toString() ;
		}
		jsonObject.accumulate("code", 0);
		return jsonObject.toString();
	}
	
	/**
	 * 查询用户OTC钱包虚拟币数量
	 * @param request
	 * @param adId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/queryUserWallet", method=RequestMethod.POST,produces={JsonEncode})
	public String queryUserWallet(HttpServletRequest request,
			@RequestParam(required=true)Integer adId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		//查询广告
		FotcAdvertisement ad = advertisementService.queryById(adId);
		//获取用户钱包信息
		Fvirtualwallet fvirtualwallet = frontUserService.findVirtualWalletByUser(fuser.getFid(), ad.getFvirtualcointype().getFid());
		if(null == fvirtualwallet) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"用户没有"+ad.getFvirtualcointype().getfShortName()+ "钱包"));
			return jsonObject.toString() ;
		}
		jsonObject.accumulate("code", 0);
		BigDecimal d = new BigDecimal(fvirtualwallet.getFtotal());
		jsonObject.accumulate("totalCoinNum", d.setScale(2, RoundingMode.HALF_DOWN).doubleValue());
		return jsonObject.toString();
	}
	
	/**
	 * 查询otc订单
	 * @param request
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/queryOtcOrder", method=RequestMethod.POST,produces={JsonEncode})
	public String queryOtcOrder(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId) throws Exception {
		JSONObject jo = new JSONObject();
		//查询订单
		try {
			FotcOrder order = otcOrderService.queryById(orderId);
			jo.accumulate("code", 0);
			jo.accumulate("orderStatus", order.getOrderStatus());
			jo.accumulate("overtime", order.getOvertime());
		} catch (Exception e) {
			e.printStackTrace();
			jo.accumulate("code", -1);
		}
		
		return jo.toString();
	}
	
	/**
	 * 查询otc订单
	 * @param request
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/selectcOrder", method=RequestMethod.POST,produces={JsonEncode})
	public String selectcOrder(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId) throws Exception {
		JSONObject jo = new JSONObject();
		//查询订单
		FotcOrder order = otcOrderService.queryById(orderId);
		
		jo.accumulate("id",order.getId());
		
		jo.accumulate("remark",order.getRemark());	
		jo.accumulate("orderType",OtcOrderTypeEnum.getDescription(order.getOrderType()));	
		jo.accumulate("adUser",order.getFotcAdvertisement().getUser().getFid());	
		jo.accumulate("cointype",order.getFvirtualcointype().getfShortName());	
		
		jo.accumulate("amount",order.getAmount());	
		jo.accumulate("unitPrice",order.getUnitPrice());
		jo.accumulate("totalPrice",order.getTotalPrice());
		jo.accumulate("orderStatus",OtcOrderStatusEnum.getEnumString(order.getOrderStatus()));
		jo.accumulate("updateTime", Utils.dateFormat(order.getUpdateTime()));
      
		jo.accumulate("code", 0);
	
		return jo.toString();
	}
	
	
	
	
	private static enum ExportFiled {
		订单号,交易类型,币种类型,交易数量,交易单价,交易总价,订单状态,创建时间,更新时间;
	}

	//查询订单数据
	private List<FotcOrder> getOrderList(HttpServletRequest request) {
		String f_orderId = request.getParameter("f_orderId");
		String f_remark = request.getParameter("f_remark");
		String f_startDate = request.getParameter("f_startDate");
		String f_endDate = request.getParameter("f_endDate");
		String f_orderStatus = request.getParameter("f_orderStatus");
		String f_adType = request.getParameter("f_adType");
		String f_coinType = request.getParameter("f_coinType");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append("  and (fuser.fid="+GetSession(request).getFid()+" OR fotcAdvertisement.user.fid="+GetSession(request).getFid()+")");
		if (StringUtils.isNotBlank(f_orderId)) {
			filter.append(" and id =" + Integer.parseInt(f_orderId) + " \n");
		}

		if (StringUtils.isNotBlank(f_remark)) {
			filter.append(" and remark =" + f_remark + " \n");
		}
		
		if (StringUtils.isNotBlank(f_startDate)) {
			filter.append(" and  DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%s') >= '"+f_startDate+"' \n");
		}
		
		if (StringUtils.isNotBlank(f_endDate)) {
			filter.append(" and  DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%s') <= '"+f_endDate+"' \n");
		}
		
		if (StringUtils.isNotBlank(f_orderStatus) && -10 != Integer.valueOf(f_orderStatus)) {
			filter.append(" and orderStatus =" + Integer.parseInt(f_orderStatus) + " \n");
		}
		
		if (StringUtils.isNotBlank(f_adType) && -10 != Integer.valueOf(f_adType)) {
			filter.append(" and orderType =" + Integer.parseInt(f_adType) + " \n");
		}
		
		if (StringUtils.isNotBlank(f_coinType)) {
			filter.append(" and fvirtualcointype.fid =" + Integer.parseInt(f_coinType) + " \n");
		}

		List<FotcOrder> list = this.otcOrderService.list(0, 0, filter + "", false);
		return list;
	}
	
	
	@RequestMapping("/orderExport")
	public String orderExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=orderList.xls");
		
		String isMerchant = request.getParameter("f_isMerchant");
		
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

	//	订单号,交易类型,币种类型,交易数量,交易单价,交易总价,订单状态,创建时间,更新时间;
		List<FotcOrder> orderList = this.getOrderList(request);
		for (FotcOrder order : orderList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 订单号:
					e.setCell(filed.ordinal(), order.getId());
					break;
				case 交易类型:
					if(isMerchant.equals("true")) {
						e.setCell(filed.ordinal(), order.getOrderType() == 1?"出售":"求购 ");
					} else {
						e.setCell(filed.ordinal(), order.getOrderType() == 1?"买入":"卖出");
					}
					break;
				case 币种类型:
					e.setCell(filed.ordinal(), order.getFvirtualcointype().getfShortName());
					break;
				case 交易数量:
					e.setCell(filed.ordinal(), order.getAmount());
					break;
				case 交易单价:
					e.setCell(filed.ordinal(), order.getUnitPrice());
					break;
				case 交易总价:
					e.setCell(filed.ordinal(), order.getTotalPrice());
					break;
				case 订单状态:
					e.setCell(filed.ordinal(), order.getOrderStatusDesc());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(), Utils.dateFormat(order.getCreateTime()));
					break;
				case 更新时间:
					e.setCell(filed.ordinal(), Utils.dateFormat(order.getUpdateTime()));
					break;
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		return null;
	}
}
