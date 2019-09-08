package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.AppDao;
import com.gt.entity.Fuser;

@Service("frontApiService")
public class FrontApiServiceImpl implements FrontApiService{
	@Autowired
	private AppDao appDao ;
	
	public int findTradeRecordllCount(Fuser fuser){
		return this.appDao.findTradeRecordAllCount(fuser) ;
	}
	
	public List<Object[]> findTradeRecordAll(Fuser fuser,int firstResult,int maxResult,boolean isFY){
		return this.appDao.findTradeRecordAll( fuser, firstResult, maxResult, isFY) ;
	}
}
