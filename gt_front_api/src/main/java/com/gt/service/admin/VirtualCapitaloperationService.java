package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.wallet.WalletUtil;

public interface VirtualCapitaloperationService {

	public Fvirtualcaptualoperation findById(int id);

	public void saveObj(Fvirtualcaptualoperation obj);

	public void deleteObj(int id);

	public void updateObj(Fvirtualcaptualoperation obj);

	public List<Fvirtualcaptualoperation> findByProperty(String name,
			Object value);

	public List<Fvirtualcaptualoperation> findAll();

	public List<Fvirtualcaptualoperation> list(int firstResult, int maxResults,
			String filter, boolean isFY);

	public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet, WalletUtil walletUtil, Fvirtualcointype coin);
	
	//public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,Fvirtualwallet virtualwallet,String encryptPasswors, Fvirtualcointype coin);
	
	//重新区块链转账，用于处理转账失败的提现订单
	public void updateCapitalRedo(Fvirtualcaptualoperation virtualcaptualoperation,
			WalletUtil walletUtil,Fvirtualcointype coin);
	
	public void updateCapitalTransfer(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet,Fvirtualwallet virtualwalletskr,String tradeUniqueId);
	
	public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet);
	
	public List<Map> getTotalAmount(int type,String fstatus,boolean isToday);
	
	public List getTotalGroup(String filter);
	
	public void updateCharge(Fvirtualcaptualoperation v,Fvirtualwallet w);
}
