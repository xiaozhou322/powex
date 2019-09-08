package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FdrawaccountsDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fdrawaccounts;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.Utils;



@Service("frontDrawAccountsService")
public class FrontDrawAccountsServiceImpl implements FrontDrawAccountsService {
	
	@Autowired
	private FdrawaccountsDAO fdrawaccountsDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	
	public void updateDrawAccountsSubmit(Fvirtualwallet fvirtualwalletFrom, 
			Fvirtualwallet fvirtualwalletTo, Fvirtualcointype fvirtualcointype, double amount, Integer type) {
		fvirtualwalletFrom.setFtotal(fvirtualwalletFrom.getFtotal()-amount) ;
//		fvirtualwalletFrom.setFfrozen(fvirtualwalletFrom.getFfrozen()+amount) ;
		fvirtualwalletFrom.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.fvirtualwalletDAO.attachDirty(fvirtualwalletFrom) ;
		
		fvirtualwalletTo.setFtotal(fvirtualwalletTo.getFtotal()+amount) ;
		fvirtualwalletTo.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.fvirtualwalletDAO.attachDirty(fvirtualwalletTo) ;
		
		//保存划账记录
		Fdrawaccounts fdrawaccounts = new Fdrawaccounts();
		fdrawaccounts.setFuserFrom(fvirtualwalletFrom.getFuser());
		fdrawaccounts.setFuserTo(fvirtualwalletTo.getFuser());
		fdrawaccounts.setFamount(amount);
		fdrawaccounts.setFcreateTime(Utils.getTimestamp());
		fdrawaccounts.setFcointype(fvirtualcointype);
		fdrawaccounts.setFtype(type);
		
		fdrawaccountsDAO.save(fdrawaccounts);
	}
	
	public Fdrawaccounts findById(java.lang.Integer id) {
		return fdrawaccountsDAO.findById(id);
	}
	
	
	public void update(Fdrawaccounts instance) {
		fdrawaccountsDAO.attachDirty(instance);
	}
	
	public List<Fdrawaccounts> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fdrawaccounts> drawaccountsList = fdrawaccountsDAO.list(firstResult, maxResults, filter, isFY);
		
		for (Fdrawaccounts drawaccounts : drawaccountsList) {
			if(null != drawaccounts.getFuserFrom()) {
				drawaccounts.getFuserFrom().getFrealName();
			}
			if(null != drawaccounts.getFuserTo()) {
				drawaccounts.getFuserTo().getFrealName();
			}
			if(null != drawaccounts.getFcointype()) {
				drawaccounts.getFcointype().getfShortName();
			}
		}
		return drawaccountsList;
	}
	
	public List<Map<String, Object>> queryDrawaccountsStatisticsList(int firstResult, int maxResults, String filter,boolean isFY, int type) {
		return this.fdrawaccountsDAO.queryDrawaccountsStatisticsList(firstResult, maxResults, filter, isFY, type);
	}
	
	public int getDrawaccountsStatisticsCount(String filter, int type) {
		return this.fdrawaccountsDAO.getDrawaccountsStatisticsCount(filter, type);
	}
	
}
