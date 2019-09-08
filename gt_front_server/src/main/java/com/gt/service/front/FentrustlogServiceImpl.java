package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FentrustlogDAO;
import com.gt.entity.Fentrustlog;

@Service("fentrustlogService")
public class FentrustlogServiceImpl implements FentrustlogService {

	@Autowired
	private FentrustlogDAO fentrustlogDAO;
	
	public void save(Fentrustlog fentrustlog) {
		fentrustlogDAO.save(fentrustlog);
	}

	
	public List<Fentrustlog> entrustlogList(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fentrustlog> all = this.fentrustlogDAO.entrustlogList(firstResult, maxResults, filter,isFY);
		for (Fentrustlog fentrustlog : all) {
			if(fentrustlog.getFtrademapping() != null){
				fentrustlog.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getFname();
				fentrustlog.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
			}
		}
		return all;
	}
}
