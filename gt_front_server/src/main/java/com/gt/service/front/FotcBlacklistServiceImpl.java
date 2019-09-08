package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FotcBlacklistDAO;
import com.gt.entity.FotcBlacklist;



@Service("fotcBlacklistService")
public class FotcBlacklistServiceImpl implements FotcBlacklistService {
	
	@Autowired
	private FotcBlacklistDAO otcBlacklistDAO;
	
	public void save(FotcBlacklist instance) {
		otcBlacklistDAO.save(instance);
	}
	
	public FotcBlacklist findById(java.lang.Integer id) {
		return otcBlacklistDAO.findById(id);
	}
	
	
	public void update(FotcBlacklist instance) {
		otcBlacklistDAO.attachDirty(instance);
	}
	
	public List<FotcBlacklist> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FotcBlacklist> fotcBlackList = otcBlacklistDAO.list(firstResult, maxResults, filter, isFY);
		
		for (FotcBlacklist otcBlacklist : fotcBlackList) {
			if(null != otcBlacklist.getUserId()) {
				otcBlacklist.getUserId().getFrealName();
			}
		}
		return fotcBlackList;
	}

	public FotcBlacklist findByUserId(Integer userId) {
		return otcBlacklistDAO.findByUserId(userId);
	}
	
}
