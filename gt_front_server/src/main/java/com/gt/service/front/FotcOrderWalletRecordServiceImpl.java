package com.gt.service.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gt.dao.FotcOrderWalletRecordDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.FotcOrderWalletRecord;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.Utils;


/**
 * otc钱包记录service
 * @author zhouyong
 *
 */
@Service("fotcOrderWalletRecordService")
public class FotcOrderWalletRecordServiceImpl implements FotcOrderWalletRecordService{
	private static final Logger logger = LoggerFactory.getLogger(FotcOrderWalletRecordService.class);
	
	@Autowired
	private FotcOrderWalletRecordDAO fotcOrderWalletRecordDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	

	public List<FotcOrderWalletRecord> findAllOrder(Integer userId){
		return fotcOrderWalletRecordDAO.findAllOrder(userId);
	}
	
	public FotcOrderWalletRecord queryById(Integer adId) {
		return fotcOrderWalletRecordDAO.queryById(adId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveObj(Integer changeType, double rewardNum, Fvirtualcointype coinType, Fuser user, Integer orderId) {
		
		FotcOrderWalletRecord forderWalletRecord=new FotcOrderWalletRecord();
		forderWalletRecord.setChange_type(changeType);
		forderWalletRecord.setFvirtualcointype(coinType);
		forderWalletRecord.setUser(user);
		forderWalletRecord.setReward_num(rewardNum);
		forderWalletRecord.setBdelete(0);
		forderWalletRecord.setRemark("");
		forderWalletRecord.setOrder_id(orderId);
		forderWalletRecord.setCreate_time(Utils.getTimestamp());
		
		List<Fvirtualwallet> list=virtualwalletDAO.findByFuid(forderWalletRecord.getUser().getFid());
		//StringBuffer  detail=new StringBuffer();
		List ls=new ArrayList();
		for(Fvirtualwallet fvirtualwallet:list){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("type", fvirtualwallet.getFvirtualcointype().getFid());
			map.put("total", fvirtualwallet.getFtotal());
			map.put("frozen", fvirtualwallet.getFfrozen());
			ls.add(map);
		}
		System.out.println(JSON.toJSONString(ls));
		forderWalletRecord.setWallet_detil(JSON.toJSONString(ls).replace("\"", "'"));
		//保存订单记录
		try{
			fotcOrderWalletRecordDAO.save(forderWalletRecord);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

}
