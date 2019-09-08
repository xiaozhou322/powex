package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTransferStatusEnum;
import com.gt.dao.FvirtualcaptualoperationDAO;
import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.SystemArgsService;
import com.gt.util.wallet.WalletUtil;

@Service("virtualCapitaloperationService")
public class VirtualCapitaloperationServiceImpl implements
		VirtualCapitaloperationService {
	@Autowired
	private FvirtualcaptualoperationDAO virtualcaptualoperationDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;

	public Fvirtualcaptualoperation findById(int id) {
		Fvirtualcaptualoperation info = this.virtualcaptualoperationDAO.findById(id);
		info.getFuser().getFnickName();
		info.getFvirtualcointype().getFname();
		return info;
	}

	public void saveObj(Fvirtualcaptualoperation obj) {
		this.virtualcaptualoperationDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fvirtualcaptualoperation obj = this.virtualcaptualoperationDAO
				.findById(id);
		this.virtualcaptualoperationDAO.delete(obj);
	}

	public void updateObj(Fvirtualcaptualoperation obj) {
		this.virtualcaptualoperationDAO.attachDirty(obj);
	}

	public List<Fvirtualcaptualoperation> findByProperty(String name,
			Object value) {
		return this.virtualcaptualoperationDAO.findByProperty(name, value);
	}

	public List<Fvirtualcaptualoperation> findAll() {
		return this.virtualcaptualoperationDAO.findAll();
	}

	public List<Fvirtualcaptualoperation> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		List<Fvirtualcaptualoperation> all = this.virtualcaptualoperationDAO
				.list(firstResult, maxResults, filter, isFY);
		for (Fvirtualcaptualoperation virtualcaptualoperation : all) {
			if(virtualcaptualoperation.getFuser() != null){
				virtualcaptualoperation.getFuser().getFemail();
			}
			if(virtualcaptualoperation.getFvirtualcointype() != null){
				virtualcaptualoperation.getFvirtualcointype().getfShortName();
			}
			
		}
		return all;
	}

	public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet, WalletUtil walletUtil, Fvirtualcointype coin) throws RuntimeException {
		if(virtualcaptualoperation.getFtradeUniqueNumber() != null
				&& virtualcaptualoperation.getFtradeUniqueNumber().trim().length()>0){
			return;
		}
		int status = virtualcaptualoperation.getFstatus();
		if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
			return;
		}
		virtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
		if(coin.isFisautosend()){
			virtualcaptualoperation.setFisaudit(false);
		}else{
			virtualcaptualoperation.setFisaudit(true);
		}
		
		try {
			this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
			this.virtualwalletDAO.attachDirty(virtualwallet);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("提现失败");
		}
		
	}
	
	/*public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet,String encryptPasswors, Fvirtualcointype coin) throws RuntimeException {
		if(virtualcaptualoperation.getFtradeUniqueNumber() != null
				&& virtualcaptualoperation.getFtradeUniqueNumber().trim().length()>0){
			return;
		}
		int status = virtualcaptualoperation.getFstatus();
		if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
			return;
		}
		virtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
		if(coin.isFisautosend()){
			virtualcaptualoperation.setFisaudit(false);
		}else{
			virtualcaptualoperation.setFisaudit(true);
		}
		
		String address = virtualcaptualoperation.getWithdraw_virtual_address();
		double amount = virtualcaptualoperation.getFamount();
		
		try {
			//String txid = walletUtil.transfer(coin.getMainAddr(), address, amount, coin.getFpushMinPrice(),virtualcaptualoperation.getFischarge());
			//远程提币失败则直接处理为已审核，待添加交易ID
			if(txid != null && !"".equals(txid)){
				virtualcaptualoperation.setFtradeUniqueNumber(txid);
			}
			String transferOrderId = "";
			JSONObject walletType = WalletAPI.getWalletType();
			Map<String, Object> params = new HashMap<String, Object>();
			String coinType = coin.getfShortName();
			Long parentId = coin.getParentCid();
			params.put("amount", amount);
			if(0 != parentId){
				coinType = this.fvirtualcointypeDAO.findById(parentId.intValue()).getfShortName();
				params.put("contract", coin.getFcontract());
				params.put("amount", amount*Math.pow(10,coin.getFdecimals()));
			}
			params.put("walletPassword", encryptPasswors);
			params.put("address", address);
			params.put("txfee", coin.getFpushMinPrice());
			params.put("comment", virtualcaptualoperation.getFischarge());
			params.put("walletTypeId", walletType.get(coinType));
			JSONObject result = WalletAPI.main("transfer", params );
			if(null != result && 200 == result.getInt("code")){
				transferOrderId =  result.get("data")+"";
			}
			if(transferOrderId != null && !"".equals(transferOrderId)){
				virtualcaptualoperation.setfTransferOrderId(transferOrderId);
			}
			this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
			this.virtualwalletDAO.attachDirty(virtualwallet);
		} catch (Exception e1) {
			throw new RuntimeException("发送失败");
		}
		
	}*/
	
	//重新区块链转账，用于处理转账失败的提现订单
	public void updateCapitalRedo(Fvirtualcaptualoperation virtualcaptualoperation,
			WalletUtil walletUtil,Fvirtualcointype coin) throws RuntimeException {
		if(virtualcaptualoperation.getFtradeUniqueNumber() != null
				&& virtualcaptualoperation.getFtradeUniqueNumber().trim().length()>0){
			return;
		}
		
		int status = virtualcaptualoperation.getFstatus();
		if (status != VirtualCapitalOperationOutStatusEnum.OperationSuccess) {
			return;
		}
		
		if(null!=virtualcaptualoperation.getFtradeUniqueNumber() && !virtualcaptualoperation.getFtradeUniqueNumber().equals(null)){
			return;
		}
		
		virtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
		if(coin.isFisautosend()){
			virtualcaptualoperation.setFisaudit(false);
		}else{
			virtualcaptualoperation.setFisaudit(true);
		}
		
		try {
			this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
		} catch (Exception e1) {
			throw new RuntimeException("重复提币失败");
		}
		
	}
	
	public void updateCapitalTransfer(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet,Fvirtualwallet virtualwalletskr,String tradeUniqueId) throws RuntimeException {
		if(virtualcaptualoperation.getFtradeUniqueNumber() != null
				&& virtualcaptualoperation.getFtradeUniqueNumber().trim().length()>0){
			return;
		}
		/*if(virtualcaptualoperation.getfTransferOrderId() != null
				&& virtualcaptualoperation.getfTransferOrderId().trim().length()>0){
			return;
		}*/
		int status = virtualcaptualoperation.getFstatus();
		if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
			return;
		}
		virtualcaptualoperation.setFstatus(VirtualCapitalOperationTransferStatusEnum.OperationSuccess);
		virtualcaptualoperation.setFisaudit(true);
		virtualcaptualoperation.setFtradeUniqueNumber(tradeUniqueId);
		try {
			this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
			this.virtualwalletDAO.attachDirty(virtualwallet);
			this.virtualwalletDAO.attachDirty(virtualwalletskr);
		} catch (Exception e1) {
			throw new RuntimeException("转账失败");
		}
		
		
	}
	
	public void updateCapital(Fvirtualcaptualoperation virtualcaptualoperation,
			Fvirtualwallet virtualwallet) throws RuntimeException {
		try {
			this.virtualcaptualoperationDAO.attachDirty(virtualcaptualoperation);
			this.virtualwalletDAO.attachDirty(virtualwallet);
		} catch (Exception e1) {
			throw new RuntimeException();
		}
	}
	
	public List<Map> getTotalAmount(int type,String fstatus,boolean isToday) {
		return this.virtualcaptualoperationDAO.getTotalQty(type, fstatus,isToday);
	}
	
	public List getTotalGroup(String filter) {
		return this.virtualcaptualoperationDAO.getTotalGroup(filter);
	}
	
	public void updateCharge(Fvirtualcaptualoperation v,Fvirtualwallet w) {
		try {
			this.virtualcaptualoperationDAO.attachDirty(v);
			this.virtualwalletDAO.attachDirty(w);
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}
}
