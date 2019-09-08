package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.PuserProfitlogsDAO;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.PuserProfitlogs;



@Service("frontPuserProfitlogsService")
public class FrontPuserProfitlogsServiceImpl implements FrontPuserProfitlogsService {
	@Autowired
	private PuserProfitlogsDAO puserProfitlogsDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO;
	
	public void save(PuserProfitlogs instance) {
		puserProfitlogsDAO.save(instance);
	}
	
	public PuserProfitlogs findById(java.lang.Integer id) {
		return puserProfitlogsDAO.findById(id);
	}
	
	
	public void update(PuserProfitlogs instance) {
		puserProfitlogsDAO.attachDirty(instance);
	}
	
	public List<PuserProfitlogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<PuserProfitlogs> userProfitlogsList = puserProfitlogsDAO.list(firstResult, maxResults, filter, isFY);
		for (PuserProfitlogs userProfitlogs : userProfitlogsList) {
			userProfitlogs.getTrademappingStr();
		}
		return userProfitlogsList;
	}

	
	public List<Map<String, Object>> getUserBuyProfitlogs(String yesterdayStr){
		return puserProfitlogsDAO.getUserBuyProfitlogs(yesterdayStr);
	}
	
	public List<Map<String, Object>> getUserSellProfitlogs(String yesterdayStr){
		return puserProfitlogsDAO.getUserSellProfitlogs(yesterdayStr);
	}

	@Override
	public void updateUserProfit(Fvirtualwallet walletInfo,PuserProfitlogs puserProfitlogs) {
		puserProfitlogsDAO.attachDirty(puserProfitlogs);
		fvirtualwalletDAO.attachDirty(walletInfo);
	}
	
}
