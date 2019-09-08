package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fentrustlog;

public interface FentrustlogService {
	
	public void save(Fentrustlog fentrustlog);
	
	public List<Fentrustlog> entrustlogList(int firstResult, int maxResults, String filter,boolean isFY);
	
}
