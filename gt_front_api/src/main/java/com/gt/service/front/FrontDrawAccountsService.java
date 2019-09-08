package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fdrawaccounts;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;

/**
 * 划账记录service
 * @author zhouyong
 *
 */
public interface FrontDrawAccountsService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void updateDrawAccountsSubmit(Fvirtualwallet fvirtualwalletFrom, 
			Fvirtualwallet fvirtualwalletTo, Fvirtualcointype fvirtualcointype, double amount, Integer type);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Fdrawaccounts findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Fdrawaccounts instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Fdrawaccounts> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 查询划转统计数据list
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Map<String, Object>> queryDrawaccountsStatisticsList(int firstResult, int maxResults,
								String filter,boolean isFY, int type);
	
	
	/**
	 * 查询划转统计数据count
	 * @param filter
	 * @param type
	 * @return
	 */
	public int getDrawaccountsStatisticsCount(String filter, int type);
}
