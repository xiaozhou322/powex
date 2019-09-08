package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcAdvertisement;
import com.gt.entity.FotcOrder;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.PageBean;

import net.sf.json.JSONObject;


/**
 * otc订单service
 * @author zhouyong
 *
 */
public interface FotcOrderService {
	
	public PageBean<FotcOrder> findOrderByPage(int fid, int pageSize, int pageIndex);
	
	
	public List<FotcOrder> findAllOrder(Integer userId);
	
	
	public FotcOrder queryById(Integer adId);
	
	
	/**
	 * 第三方机构用户保存订单
	 * @param order
	 * @param otcWallet
	 * @param ad
	 * @return
	 * @throws Exception
	 */
	public Integer save3rdOtcObj(FotcOrder order,Fvirtualwallet otcWallet);
	
	
	public void updateObj(FotcOrder order);
	
	
	/**
	 * 修改订单和广告
	 * @param order
	 * @param ad
	 * @throws Exception
	 */
	public Boolean updateOrderAndAd(FotcOrder order,FotcAdvertisement ad,Fvirtualwallet otcWallet);
	
	
	public void updateCancelOrder(FotcOrder order, FotcAdvertisement ad, Fvirtualwallet organWallet, Integer logsType);
	

	/**
	 * 承兑商放币
	 * @param order
	 */
	public void updateOtcWallet(FotcOrder order,Fvirtualwallet organWallet,
			Fvirtualwallet acceptWallet,FotcAdvertisement ad, Integer logsType);
	
	
	public List<FotcOrder> list(int firstResult, int maxResults,String filter,boolean isFY);
	
	//生成备注号
	public String getRemark();
	
	
	public String getOrderIdByTime();
	
	
	public List<FotcOrder> findByProperty(String propertyName, Object value);
	
	
	/**
	 * 普通用户保存订单
	 * @param otcwallet
	 * @param ad
	 * @param order
	 * @return
	 */
	public Integer saveUserOtcObj(FotcOrder order,FotcAdvertisement ad, Fvirtualwallet otcwallet);
	
	
	/**
	 * 订单状态变更回调
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public String updateOrderStatusCallback(Integer orderId);
	
	
	public String queryAcceptAvgResptime(Integer fid);
	
	
	public FotcOrder queryOrderByOrginOrderId(String orginOrderId);
	
	
	//查询广告在进行中的订单
	public List<FotcOrder> findByProperty1(String propertyName, int value);
	
	
	/**
	 * 订单成功
	 * @param orderId
	 */
	public JSONObject updateSucess(Integer orderId, Integer logsType);
	
	
	/**
	 * 订单失败
	 * @param orderId
	 */
	public JSONObject updateFailedOrder(Integer orderId, Integer logsType);
	

}
