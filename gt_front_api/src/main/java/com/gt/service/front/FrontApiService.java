package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fuser;

public interface FrontApiService {
	
	public int findTradeRecordllCount(Fuser fuser);
	
	public List<Object[]> findTradeRecordAll(Fuser fuser,int firstResult,int maxResult,boolean isFY);
}
