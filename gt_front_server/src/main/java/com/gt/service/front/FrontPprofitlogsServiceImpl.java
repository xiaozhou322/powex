package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.PprofitlogsDAO;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pprofitlogs;



@Service("frontPprofitlogsService")
public class FrontPprofitlogsServiceImpl implements FrontPprofitlogsService {
	@Autowired
	private PprofitlogsDAO pprofitlogsDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	
	public void save(Pprofitlogs instance) {
		pprofitlogsDAO.save(instance);
	}
	
	public Pprofitlogs findById(java.lang.Integer id) {
		return pprofitlogsDAO.findById(id);
	}
	
	
	public void update(Pprofitlogs instance) {
		pprofitlogsDAO.attachDirty(instance);
	}
	
	public List<Pprofitlogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pprofitlogs> profitlogsList = pprofitlogsDAO.list(firstResult, maxResults, filter, isFY);
		for (Pprofitlogs profitlogs : profitlogsList) {
			profitlogs.getTrademappingStr();
		}
		return profitlogsList;
	}

	@Override
	public List<Map> getAssetListByParams(int projectId) {
		return pprofitlogsDAO.getAssetListByParams(projectId);
	}

	@Override
	public List<Map> getMonthServiceCharge(int status, int projectId) {
		return pprofitlogsDAO.getMonthServiceCharge(status, projectId);
	}

	@Override
	public List<Integer> getAllProfitCoinType(String tableName,String fieldName,String filter) {
		return pprofitlogsDAO.getAllProfitCoinType(tableName, fieldName, filter);
	}
	
	
	public void updateProfitlogsSettle(Fvirtualwallet wallet, Pprofitlogs profitlogs) {
		fvirtualwalletDAO.attachDirty(wallet);
		pprofitlogsDAO.attachDirty(profitlogs);
	}
}
