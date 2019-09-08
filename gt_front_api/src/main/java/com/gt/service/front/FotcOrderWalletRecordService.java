package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcOrderWalletRecord;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;


/**
 * otc钱包记录service
 * @author zhouyong
 *
 */
public interface FotcOrderWalletRecordService {
	
	public List<FotcOrderWalletRecord> findAllOrder(Integer userId);
	
	
	public FotcOrderWalletRecord queryById(Integer adId);
	
	
	public void saveObj(Integer changeType, double rewardNum, Fvirtualcointype coinType,
						Fuser user, Integer orderId);
	

}
