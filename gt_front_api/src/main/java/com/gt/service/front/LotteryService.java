package com.gt.service.front;

import java.util.List;

import com.gt.entity.Lottery;
import com.gt.entity.Nper;

public interface LotteryService {
	
	public void createTable(String tableName);

	public Lottery findById(int id, String tableName);

	public void saveObj(Lottery obj, String tableName);

	public void deleteObj(Lottery obj, String tableName);

	public void updateObj(Lottery obj, String tableName);

	public List<Lottery> findByProperty(String name, Object value, String tableName);
	
	public List<Lottery> findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2, String tableName);

	public List<Lottery> list(int firstResult, int maxResults, String filter,boolean isFY, String tableName);

	public int getAllCount(String filter, String tableName);

	public List<String> queryList(int firstResult, int maxResults, String hql, boolean isFY, String tableName);
	
	public Long currentLotteryNumber(String nper, String tableName);

	public Long maxLotteryNo(String nper, String tableName);

	public String saveNewNper(Nper nper, int uid);
	
}
