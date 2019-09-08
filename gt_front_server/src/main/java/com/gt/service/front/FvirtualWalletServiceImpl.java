package com.gt.service.front;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FactiveCoinLogsDAO;
import com.gt.dao.FapproCoinLogsDAO;
import com.gt.dao.FconvertCoinLogsDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.PproductDAO;
import com.gt.entity.FactiveCoinLogs;
import com.gt.entity.FapproCoinLogs;
import com.gt.entity.FconvertCoinLogs;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pproduct;
import com.gt.util.Utils;

@Service("fvirtualWalletService")
public class FvirtualWalletServiceImpl implements FvirtualWalletService {
	private static final Logger log = LoggerFactory.getLogger(FvirtualWalletServiceImpl.class);
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	
	@Autowired
	private FconvertCoinLogsDAO fconvertCoinLogsDAO;
	@Autowired
	private FactiveCoinLogsDAO factiveCoinLogsDAO;
	@Autowired
	private FapproCoinLogsDAO fapproCoinLogsDAO;
	@Autowired
	private PproductDAO pproductDAO;
	
	public Fvirtualwallet findFvirtualwallet(int fuser,int vid){
		List<Fvirtualwallet> fvirtualwallets = this.fvirtualwalletDAO.findByParam(0, 0, " where fuser.fid=? and fvirtualcointype.fid=? ", false,Fvirtualwallet.class,fuser,vid) ;
		if(fvirtualwallets.size()==1){
			return fvirtualwallets.get(0) ;
		}else{
			log.error("Fvirtualwallet fuser="+fuser+",vid="+vid+" exists "+fvirtualwallets.size());
			return null ;
		}
		
	}

	public List<Fvirtualwallet> findByTwoProperty(String propertyName1, int value1,
			String propertyName2, int value2) {
		return fvirtualwalletDAO.findByTwoProperty(propertyName1, value1, propertyName2, value2);
	}
	
	
	public List<Fvirtualwallet> findByProperty(String propertyName1, Object value1) {
		// TODO Auto-generated method stub
		return fvirtualwalletDAO.findByProperty(propertyName1, value1);
	}
	
	public void attachDirty(Fvirtualwallet instance){
		fvirtualwalletDAO.attachDirty(instance);
	}
	
	public void updateObj(Fvirtualwallet obj) {
		this.fvirtualwalletDAO.attachDirty(obj);
	}
	
	public List<Map> getTotalQty() {
		return this.fvirtualwalletDAO.getTotalQty();
	}
	
	public BigDecimal getTotalQty(int vid) {
		return this.fvirtualwalletDAO.getTotalQty(vid);
	}
	
	public List<Fvirtualwallet> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fvirtualwallet> all = this.fvirtualwalletDAO.list(firstResult, maxResults, filter,isFY);
		for (Fvirtualwallet fvirtualwallet : all) {
			fvirtualwallet.getFuser().getFlastLoginTime();
			if(fvirtualwallet.getFvirtualcointype() != null){
				fvirtualwallet.getFvirtualcointype().getFname();
			}
		}
		return all;
	}
	
	public Fvirtualwallet findById(int id) {
		return this.fvirtualwalletDAO.findById(id);
	}

	
	public synchronized void updateConvertCoinSubmit(Fvirtualwallet fvirtualwalletFrom, Fvirtualwallet fvirtualwalletTo,
			double convertAmount1, double convertAmount2, Fuser fuser, Pproduct pproduct, Integer convertType) {
		fvirtualwalletFrom.setFtotal(fvirtualwalletFrom.getFtotal()-convertAmount1) ;
		fvirtualwalletFrom.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.fvirtualwalletDAO.attachDirty(fvirtualwalletFrom) ;
		
		if(1 == convertType) {
			fvirtualwalletTo.setFlocked(fvirtualwalletTo.getFlocked()+convertAmount2) ;
			fvirtualwalletTo.setFlastUpdateTime(Utils.getTimestamp()) ;
			this.fvirtualwalletDAO.attachDirty(fvirtualwalletTo) ;
		} else if(2 == convertType) {
			fvirtualwalletTo.setFtotal(fvirtualwalletTo.getFtotal()+convertAmount2) ;
			fvirtualwalletTo.setFlastUpdateTime(Utils.getTimestamp()) ;
			this.fvirtualwalletDAO.attachDirty(fvirtualwalletTo) ;
		}
		
		//保存兑换流水记录
		FconvertCoinLogs convertCoinLogs = new FconvertCoinLogs();
		convertCoinLogs.setFuser(fuser);
		convertCoinLogs.setConvertCointype1(fvirtualwalletFrom.getFvirtualcointype());
		convertCoinLogs.setConvertCointype2(fvirtualwalletTo.getFvirtualcointype());
		convertCoinLogs.setConvertAmount1(convertAmount1);
		convertCoinLogs.setConvertAmount2(convertAmount2);
		convertCoinLogs.setCreateTime(Utils.getTimestamp());
		
		fconvertCoinLogsDAO.save(convertCoinLogs);
		
		//修改产品信息
		if(1 == convertType) {
			pproduct.setConvertAvailableAmount(pproduct.getConvertAvailableAmount()-convertAmount2);
			pproduct.setProAvailableAmount(pproduct.getProAvailableAmount()+convertAmount2);
		} else if(2 == convertType) {
			pproduct.setConvertAvailableAmount(pproduct.getConvertAvailableAmount()+convertAmount2);
			pproduct.setProAvailableAmount(pproduct.getProAvailableAmount()-convertAmount2);
		}
		pproductDAO.attachDirty(pproduct);
		
	}

	
	public void updateTradeActiveCoin(FactiveCoinLogs activeLogs, Fvirtualwallet fvirtualwallet,
			Fvirtualwallet fvirtualwalletPow, double consumePowNum) {
		//修改钱包记录
		fvirtualwallet.setFlocked(fvirtualwallet.getFlocked()-activeLogs.getActiveAmount());
		fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+activeLogs.getActiveAmount()) ;
		fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
		
		//扣除单次解锁手续费：为解锁数量的10%等价值的平台币（POW）
		fvirtualwalletPow.setFtotal(fvirtualwallet.getFtotal()-consumePowNum) ;
		fvirtualwalletPow.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
		
		//修改币种激活流水
		this.factiveCoinLogsDAO.attachDirty(activeLogs);;
		
	}

	
	public List<FconvertCoinLogs> convertCoinLogsList(int firstResult, int maxResults, String filter, boolean isFY) {
		List<FconvertCoinLogs> convertCoinLogsList = fconvertCoinLogsDAO.list(firstResult, maxResults, filter, isFY);
		for (FconvertCoinLogs convertCoinLogs : convertCoinLogsList) {
			if(null != convertCoinLogs.getFuser()) {
				convertCoinLogs.getFuser().getFrealName();
			}
		}
		return convertCoinLogsList;
	}

	
	public Integer saveActiveCoinLogs(FactiveCoinLogs instance) {
		return factiveCoinLogsDAO.save(instance);
	}

	
	public List<FactiveCoinLogs> queryActiveCoinLogsList(int firstResult, int maxResults, String filter, boolean isFY) {
		List<FactiveCoinLogs> activeCoinLogsList = factiveCoinLogsDAO.list(firstResult, maxResults, filter, isFY);
		for (FactiveCoinLogs activeCoinLogs : activeCoinLogsList) {
			if(null != activeCoinLogs.getFuser()) {
				activeCoinLogs.getFuser().getFrealName();
			}
		}
		return activeCoinLogsList;
	}

	
	public void updateActiveCoinLogs(FactiveCoinLogs instance) {
		factiveCoinLogsDAO.attachDirty(instance);
	}
	
	
	public FactiveCoinLogs findActiveCoinLogsById(int id) {
		return factiveCoinLogsDAO.findById(id);
	}

	
	public void updateApproCoinLogs(double amount, Fvirtualwallet fvirtualwallet, Pproduct pproduct, Fuser fuser) {
		//修改钱包记录
		fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+amount) ;
		fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
		
		//修改产品信息
		pproduct.setConvertAvailableAmount(pproduct.getConvertAvailableAmount()-amount);
		pproduct.setProAvailableAmount(pproduct.getProAvailableAmount()+amount);
		pproductDAO.attachDirty(pproduct);
		
		//保存币种拨付流水
		FapproCoinLogs approCoinLogs = new FapproCoinLogs();
		approCoinLogs.setFuser(fuser);
		approCoinLogs.setCoinType(pproduct.getCoinType());
		approCoinLogs.setAmount(amount);
		approCoinLogs.setCreateTime(Utils.getTimestamp());
		fapproCoinLogsDAO.save(approCoinLogs);
	}

	
	public List<FapproCoinLogs> queryApproCoinLogsList(int firstResult, int maxResults, String filter, boolean isFY) {
		List<FapproCoinLogs> approCoinLogsList = fapproCoinLogsDAO.list(firstResult, maxResults, filter, isFY);
		for (FapproCoinLogs approCoinLogs : approCoinLogsList) {
			if(null != approCoinLogs.getFuser()) {
				approCoinLogs.getFuser().getFrealName();
			}
		}
		return approCoinLogsList;
	}

}
