package com.gt.service.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.Enum.OtcAppealReasonEnum;
import com.gt.Enum.OtcOrderLogsTypeEnum;
import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.Enum.OtcOrderTypeEnum;
import com.gt.dao.FadminDAO;
import com.gt.dao.FotcAdvertisementDAO;
import com.gt.dao.FotcBlacklistDAO;
import com.gt.dao.FotcInstitutionsinfoDAO;
import com.gt.dao.FotcOrderDAO;
import com.gt.dao.FotcOrderLogsDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fapi;
import com.gt.entity.FotcAdvertisement;
import com.gt.entity.FotcBlacklist;
import com.gt.entity.FotcInstitutionsinfo;
import com.gt.entity.FotcOrder;
import com.gt.entity.FotcOrderLogs;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.HttpClientUtils;
import com.gt.util.MD5SignHelper;
import com.gt.util.PageBean;
import com.gt.util.Utils;

import net.sf.json.JSONObject;


/**
 * otc订单service
 * @author zhouyong
 *
 */
@Service("fotcOrderService")
public class FotcOrderServiceImpl implements FotcOrderService{
	private static final Logger logger = LoggerFactory.getLogger(FotcOrderService.class);
	
	@Autowired
	private FotcOrderDAO fotcOrderDAO;
	@Autowired
	private FotcAdvertisementDAO fotcAdvertisementDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	@Autowired
	private ApiService apiService ;
	@Autowired
	private FotcInstitutionsinfoDAO fotcInstitutionsinfoDAO;
	@Autowired
	private FotcOrderWalletRecordService fotcOrderWalletRecordServiceImpl;
	@Autowired
	protected FrontUserService frontUserService ;
	@Autowired
	private FotcOrderLogsDAO otcOrderLogsDAO;
	@Autowired
	private FotcBlacklistDAO otcBlacklistDAO;
	@Autowired
	private FadminDAO fadminDAO;
	@Autowired
	private FrontConstantMapService frontConstantMapServiceImpl ;
	
	
	public PageBean<FotcOrder> findOrderByPage(int fid, int pageSize, int pageIndex){
		PageBean<FotcOrder>  pageBean=new PageBean<FotcOrder>(pageSize,pageIndex,fotcOrderDAO.getOrderCount(fid).intValue());
		int offset=(pageBean.getPageIndex()-1)*pageBean.getPageSize();
		pageBean.setList(fotcOrderDAO.findAllOrder(fid, pageSize, offset));
		return pageBean;
	}
	public List<FotcOrder> findAllOrder(Integer userId){
		return fotcOrderDAO.findAllOrder(userId);
	}
	
	public FotcOrder queryById(Integer adId) {
		return fotcOrderDAO.queryById(adId);
	}
	
	/**
	 * 第三方机构用户保存订单
	 * @param order
	 * @param otcWallet
	 * @param ad
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer save3rdOtcObj(FotcOrder order,Fvirtualwallet otcWallet) {
		int retOrderId = -1;
		//同步锁保证订单不能同时创建   广告可用数量不能为负数
		 FotcAdvertisement ad = fotcAdvertisementDAO.queryById(order.getFotcAdvertisement().getId());
		 if(ad.getRepertory_count() >= order.getAmount()) {
			//保存订单记录
			retOrderId = fotcOrderDAO.save(order);
		 }
		
		return retOrderId;
	}
	
	
	public void updateObj(FotcOrder order) {
		fotcOrderDAO.update(order);
	}
	
	
	/**
	 * 修改订单和广告
	 * @param order
	 * @param ad
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public synchronized Boolean updateOrderAndAd(FotcOrder order,FotcAdvertisement ad,Fvirtualwallet otcWallet) {
		 Boolean retResult = false;
		 //同步锁保证订单不能同时创建   广告可用数量不能为负数
		 FotcAdvertisement adotc = fotcAdvertisementDAO.queryById(order.getFotcAdvertisement().getId());
		 if(adotc.getRepertory_count() >= order.getAmount()) {
			//若为求购订单，则冻结钱包金额
			if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
				this.virtualwalletDAO.attachDirty(otcWallet);
				//用户出售冻结  (1:冻结,2:解冻,3:出账，4入账,)
				fotcOrderWalletRecordServiceImpl.saveObj(1, order.getAmount(), ad.getFvirtualcointype(), order.getFuser(), order.getId());
			}
			
			this.updateObj(order);
			
			fotcAdvertisementDAO.merge(ad);
			
			retResult = true;
		 }
		
		return retResult;
		
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCancelOrder(FotcOrder order, FotcAdvertisement ad, Fvirtualwallet organWallet, Integer logsType) {
		this.updateObj(order);
		
		fotcAdvertisementDAO.update(ad);
		//承兑商求购即用户出售解冻订单
		if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
			this.virtualwalletDAO.attachDirty(organWallet);
			//用户出售冻结  (1:冻结,2:解冻,3:出账，4入账,)
			fotcOrderWalletRecordServiceImpl.saveObj(2, order.getAmount(), ad.getFvirtualcointype(), order.getFuser(), order.getId());
		}
		
		if(logsType == OtcOrderLogsTypeEnum.hand_cancel
				|| logsType == OtcOrderLogsTypeEnum.auto_cancel
				|| logsType == OtcOrderLogsTypeEnum.appeal_failed
				|| logsType == OtcOrderLogsTypeEnum.manager_cancel) {
			
			//记录失败订单的操作日志
			FotcOrderLogs otcOrderLogs = new FotcOrderLogs();
			otcOrderLogs.setOrderId(order);
			//普通用户购买(即广告类型为出售)
			if(OtcOrderTypeEnum.sell_order == order.getOrderType()) {
				otcOrderLogs.setBuyUser(order.getFuser());
				otcOrderLogs.setSellUser(order.getFotcAdvertisement().getUser());
			} else {  //普通用户出售(即广告类型为求购)
				otcOrderLogs.setBuyUser(order.getFotcAdvertisement().getUser());
				otcOrderLogs.setSellUser(order.getFuser());
			}
			otcOrderLogs.setOrderType(order.getOrderType());
			otcOrderLogs.setAppealReason(order.getAppeal_reason());
			otcOrderLogs.setAppealUser(order.getAppeal_user());
			otcOrderLogs.setType(logsType);
			if(OtcOrderTypeEnum.sell_order == order.getOrderType()
					&& null != order.getAppeal_reason()
					&& OtcAppealReasonEnum.Payable == order.getAppeal_reason()) {
				//用户购买————用户投诉承兑商(已付款卖家未确认)
				otcOrderLogs.setComplainSucc(1);
			} else if(OtcOrderTypeEnum.sell_order == order.getOrderType() 
					&& null != order.getAppeal_reason()
					&& OtcAppealReasonEnum.Paid == order.getAppeal_reason()) {
				//用户出售————承兑商投诉用户(买家未付款)
				otcOrderLogs.setComplainSucc(1);
			} else {
				otcOrderLogs.setComplainSucc(0);
			}
			otcOrderLogs.setCreateTime(Utils.getTimestamp());
			
			otcOrderLogsDAO.save(otcOrderLogs);
			
			//若用户被投诉总次数大于10次则列入OTC用户黑名单
			this.virifyBlackUser(order, logsType);
		}
	}
	

	/**
	 * 承兑商放币
	 * @param order
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateOtcWallet(FotcOrder order,Fvirtualwallet organWallet, Fvirtualwallet acceptWallet,FotcAdvertisement ad, Integer logsType) {
		
		virtualwalletDAO.attachDirty(organWallet);
		
		virtualwalletDAO.attachDirty(acceptWallet);
		
		fotcAdvertisementDAO.update(ad);
		
		fotcOrderDAO.update(order);
		
		//用户出售
		if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
			//机构商出账  (1:冻结,2:解冻,3:出账，4入账,)
			fotcOrderWalletRecordServiceImpl.saveObj(3, order.getAmount(), ad.getFvirtualcointype(), order.getFuser(), order.getId());
			//承兑商入账  (1:冻结,2:解冻,3:出账，4入账,)
			fotcOrderWalletRecordServiceImpl.saveObj(4, order.getAmount(), ad.getFvirtualcointype(), ad.getUser(), order.getId());
		} else {
			//机构商入账  (1:冻结,2:解冻,3:出账，4入账,)
			fotcOrderWalletRecordServiceImpl.saveObj(4, order.getAmount(), ad.getFvirtualcointype(), order.getFuser(), order.getId());
			//承兑商出账  (1:冻结,2:解冻,3:出账，4入账,)
			fotcOrderWalletRecordServiceImpl.saveObj(3, order.getAmount(), ad.getFvirtualcointype(), ad.getUser(), order.getId());
		}
		
		
		if(logsType == OtcOrderLogsTypeEnum.appeal_success) {
			//记录成功订单的操作日志
			FotcOrderLogs otcOrderLogs = new FotcOrderLogs();
			otcOrderLogs.setOrderId(order);
			//普通用户购买(即广告类型为出售)
			if(OtcOrderTypeEnum.sell_order == order.getOrderType()) {
				otcOrderLogs.setBuyUser(order.getFuser());
				otcOrderLogs.setSellUser(order.getFotcAdvertisement().getUser());
			} else {  //普通用户出售(即广告类型为求购)
				otcOrderLogs.setBuyUser(order.getFotcAdvertisement().getUser());
				otcOrderLogs.setSellUser(order.getFuser());
			}
			otcOrderLogs.setOrderType(order.getOrderType());
			otcOrderLogs.setAppealReason(order.getAppeal_reason());
			otcOrderLogs.setAppealUser(order.getAppeal_user());
			otcOrderLogs.setType(logsType);
			if(OtcOrderTypeEnum.buy_order == order.getOrderType()
					&& null != order.getAppeal_reason()
					&& OtcAppealReasonEnum.Paid == order.getAppeal_reason()) {
				//用户出售————用户投诉承兑商(买家未付款)
				otcOrderLogs.setComplainSucc(1);
			} else if(OtcOrderTypeEnum.buy_order == order.getOrderType() 
					&& null != order.getAppeal_reason()
					&& OtcAppealReasonEnum.Payable == order.getAppeal_reason()) {
				//用户出售————承兑商投诉用户(已付款卖家未确认)
				otcOrderLogs.setComplainSucc(1);
			} else {
				otcOrderLogs.setComplainSucc(0);
			}
			otcOrderLogs.setCreateTime(Utils.getTimestamp());
			
			otcOrderLogsDAO.save(otcOrderLogs);
			
			//若用户被投诉总次数大于10次则列入OTC用户黑名单
			this.virifyBlackUser(order, logsType);
		}
		
	}
	
	
	
	public List<FotcOrder> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fotcOrderDAO.list(firstResult, maxResults, filter,isFY);
	}
	
	//生成备注号
	public String getRemark() {
	       
        String result="";
        Random random=new Random();
        for(int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
       return result;
    }
	
	public String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
	
	public List<FotcOrder> findByProperty(String propertyName, Object value) {
		
		return this.fotcOrderDAO.findByProperty(propertyName, value);
	}
	
	
	/**
	 * 普通用户保存订单
	 * @param otcwallet
	 * @param ad
	 * @param order
	 * @return
	 */
	public synchronized Integer saveUserOtcObj(FotcOrder order,FotcAdvertisement ad, Fvirtualwallet otcwallet) {
		 int retOrderId = -1;
		 //同步锁保证订单不能同时创建    广告可用数量不能为负数
		 FotcAdvertisement otcAd = fotcAdvertisementDAO.queryById(order.getFotcAdvertisement().getId());
		 if(otcAd.getRepertory_count() >= order.getAmount()) {
			//保存订单记录
			retOrderId = fotcOrderDAO.save(order);
			
			//承兑商求购即用户出售冻结订单
			if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
				this.virtualwalletDAO.attachDirty(otcwallet);
				//用户出售冻结  (1:冻结,2:解冻,3:出账，4入账,)
				fotcOrderWalletRecordServiceImpl.saveObj(1, order.getAmount(), ad.getFvirtualcointype(), order.getFuser(), retOrderId);
			}
			
			fotcAdvertisementDAO.merge(ad);
		 }
		
		return retOrderId;
	}
	
	
	/**
	 * 订单状态变更回调
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Async
	public String updateOrderStatusCallback(Integer orderId) {
		
		JSONObject jsonObject = new JSONObject() ;
		
		HttpClientUtils httpclient = new HttpClientUtils();
		
		//查询订单
		FotcOrder order = this.queryById(orderId);
		//查询api
		Fapi fapi = apiService.findFapiByUserId(order.getFuser().getFid());
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("partnerId", fapi.getFpartner());
		paramMap.put("otcOrderId", order.getId().toString());
		paramMap.put("orderId", order.getUserOrderId());
		paramMap.put("status", order.getOrderStatus().toString());
		
		//拼接参数
		String paramStr = MD5SignHelper.getParamStr(paramMap);

	    //MD5加密
	    String sign = MD5SignHelper.getSign(paramStr, fapi.getFsecret());
	    
	    paramMap.put("sign", sign);
		 
	    String serverUrl = null;
	    if(StringUtils.isNotBlank(order.getServerUrl())) {
	    	serverUrl = order.getServerUrl();
	    } else {
	    	FotcInstitutionsinfo instInfo = fotcInstitutionsinfoDAO.findByUserId(order.getFuser().getFid());
	    	if(null != instInfo) {
	    		serverUrl = instInfo.getServer_callback_url();
	    	}
	    }
	    
	    if(StringUtils.isNotBlank(serverUrl)) {
	    	//调用机构商接口，订单状态变更回调    TODO
	    	String result = httpclient.sendPost(serverUrl, paramMap);
	    	
	    	logger.info("回调机构商服务返回", result);
	    	if(null == result) {
    			jsonObject.accumulate("code", "101");
    			jsonObject.accumulate("msg", "回调机构商服务端异常");
    			return jsonObject.toString();
    		} else {
    			com.alibaba.fastjson.JSONObject jsonResult = com.alibaba.fastjson.JSONObject.parseObject(result);
    			if("1".equals(jsonResult.get("code").toString())){
	    			//成功、
	    			if(order.getOrderStatus() == OtcOrderStatusEnum.success ||
	    					order.getOrderStatus() == OtcOrderStatusEnum.failOrder) {
	    				order.setIs_callback_success(1);
	    				order.setUpdateTime(Utils.getTimestamp());
	    				this.updateObj(order);
	    			}
	    			jsonObject.accumulate("code", "0");
	    			jsonObject.accumulate("msg", jsonResult.get("massage"));
	    			return jsonObject.toString();
	    		}else {
	    			jsonObject.accumulate("code", "102");
	    			jsonObject.accumulate("msg", jsonResult.get("massage").toString());
	    			return jsonObject.toString();
	    		}
    		}
	    }
	    jsonObject.accumulate("code", "103");
		jsonObject.accumulate("msg", "回调地址不存在");
		return jsonObject.toString();
	}
	
	
	public String queryAcceptAvgResptime(Integer fid) {
		return fotcOrderDAO.queryAcceptAvgResptime(fid);
	}
	
	public FotcOrder queryOrderByOrginOrderId(String orginOrderId) {
		return fotcOrderDAO.queryOrderByOrginOrderId(orginOrderId);
	}
	//查询广告真在进行中的订单
	public List<FotcOrder> findByProperty1(String propertyName, int value) {
		return this.fotcOrderDAO.findByProperty1(propertyName, value);
	}
	
	/**
	 * 订单成功
	 * @param orderId
	 */
	public JSONObject updateSucess(Integer orderId, Integer logsType) {
		JSONObject jsonObject = new JSONObject() ;
		try {
			 //查询订单
			FotcOrder order = this.queryById(orderId);
			 if(OtcOrderStatusEnum.failOrder == order.getOrderStatus()) {
				//订单已失败
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg","订单已失败") ;
				 return jsonObject ;
			 }
			 if(OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 jsonObject.accumulate("code", -1) ;
				 jsonObject.accumulate("msg","订单已成功") ;
				 return jsonObject ;
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
					 jsonObject.accumulate("msg","用户冻结金额不足") ;
					 return jsonObject ;
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
					 jsonObject.accumulate("msg","承兑商冻结金额不足") ;
					 return jsonObject ;
				 }
				 //广告商冻结数量减少
				 acceptWallet.setFfrozen(acceptWallet.getFfrozen() - order.getAmount());
				 acceptWallet.setFlastUpdateTime(Utils.getTimestamp());
				 
				 //用户总数增加
				 organWallet.setFtotal(organWallet.getFtotal() + order.getAmount());
				 organWallet.setFlastUpdateTime(Utils.getTimestamp());
			 }
			 
			 //获取订单的广告信息
			 FotcAdvertisement ad = fotcAdvertisementDAO.queryById(order.getFotcAdvertisement().getId());
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
			 this.updateOtcWallet(order,organWallet,acceptWallet,ad, logsType);
			 
			 jsonObject.accumulate("code", 1) ;
			 jsonObject.accumulate("msg","操作成功") ;
			 return jsonObject ;
			 
		} catch (Exception e) {
			 e.printStackTrace();
			 jsonObject.accumulate("code", -1) ;
			 jsonObject.accumulate("msg","操作失败") ;
			 return jsonObject ;
		}
	}
	
	
	
	/**
	 * 订单失败
	 * @param orderId
	 */
	public JSONObject updateFailedOrder(Integer orderId, Integer logsType) {
		JSONObject jsonObject = new JSONObject();
		try {
			 //查询订单
			FotcOrder order = this.queryById(orderId);
			 if(OtcOrderStatusEnum.failOrder == order.getOrderStatus()) {
				 //订单已失败
				 jsonObject.accumulate("code", "-1");
				 jsonObject.accumulate("msg", "订单已失败");
				 return jsonObject;
			 }
			 if(OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 //订单已成功
				 jsonObject.accumulate("code", "-1");
				 jsonObject.accumulate("msg", "订单已成功");
				 return jsonObject;
			 }
			 Fvirtualwallet organWallet = null;
			 if(order.getOrderType() == OtcOrderTypeEnum.buy_order) {
				 //获取用户钱包信息
				 organWallet = frontUserService.findVirtualWalletByUser(order.getFuser().getFid(), order.getFvirtualcointype().getFid());
				 if(organWallet.getFfrozen()<order.getAmount()){
					 //机构商冻结资金不足
					 jsonObject.accumulate("code", "-1");
					 jsonObject.accumulate("msg", "机构商冻结资金不足");
					 return jsonObject;
				 }
				 //解冻钱包
				 organWallet.setFtotal(organWallet.getFtotal()+order.getAmount());
				 organWallet.setFfrozen(organWallet.getFfrozen()-order.getAmount());
			 }
			 FotcAdvertisement ad = fotcAdvertisementDAO.queryById(order.getFotcAdvertisement().getId());
			 if(ad.getFreeze_count() < order.getAmount()) {
				 //广告冻结金额不足
				 jsonObject.accumulate("code", "-1");
				 jsonObject.accumulate("msg", "广告冻结金额不足");
				 return jsonObject;
			 }
			 
			 //广告解冻数量
			 ad.setFreeze_count(ad.getFreeze_count()-order.getAmount());
			 ad.setRepertory_count(ad.getRepertory_count()+order.getAmount());
			 ad.setUpdate_time(Utils.getTimestamp());
			 
			 //修改订单状态
			 order.setOrderStatus(OtcOrderStatusEnum.failOrder);
			 order.setUpdateTime(Utils.getTimestamp());
			 
			 //系统取消订单
			 this.updateCancelOrder(order, ad, organWallet, logsType);
			 jsonObject.accumulate("code", "1");
			 jsonObject.accumulate("msg", "操作成功");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	

	/**
	 * 判断是否满足加入OTC黑名单的条件
	 * @param order
	 * @param logsType
	 */
	private void virifyBlackUser(FotcOrder order, Integer logsType) {
		//若用户被投诉总次数大于10次则列入OTC用户黑名单
		if(logsType == OtcOrderLogsTypeEnum.appeal_failed
				|| logsType == OtcOrderLogsTypeEnum.appeal_success) {
			StringBuffer filter = new StringBuffer();
			filter.append(" where (buyUser.fid = " + order.getFuser().getFid());
			filter.append(" or sellUser.fid = " + order.getFuser().getFid() +")");
			filter.append(" and complainSucc = 1 ");
			filter.append(" and type in ( "+ OtcOrderLogsTypeEnum.appeal_failed +","+ OtcOrderLogsTypeEnum.appeal_success + " )");
			filter.append(" and appealReason in ( " + OtcAppealReasonEnum.Payable +","+OtcAppealReasonEnum.Paid + " )");
			int totalCount = fadminDAO.getAllCount("FotcOrderLogs", filter+"");
			
			//从缓存获取OTC允许用户申诉失败总次数
			String otcAppealFailTimes = frontConstantMapServiceImpl.getString("otcAppealFailTimes");
			int times = (StringUtils.isNotBlank(otcAppealFailTimes))?Integer.valueOf(otcAppealFailTimes):10;
			if(totalCount >= times) {
				//判断用户是否是黑名单用户
				FotcBlacklist otcBlacklist = otcBlacklistDAO.findByUserId(order.getFuser().getFid());
				if(null == otcBlacklist) {
					otcBlacklist = new FotcBlacklist();
					otcBlacklist.setUserId(order.getFuser());
					otcBlacklist.setCreateTime(Utils.getTimestamp());
					otcBlacklistDAO.save(otcBlacklist);
				}
			}
		}
	}
}
