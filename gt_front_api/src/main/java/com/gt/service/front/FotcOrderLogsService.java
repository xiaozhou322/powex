package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcOrderLogs;

/**
 * otc订单操作日志Service
 * @author zhouyong
 *
 */
public interface FotcOrderLogsService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(FotcOrderLogs instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public FotcOrderLogs findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(FotcOrderLogs instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FotcOrderLogs> list(int firstResult, int maxResults, String filter,boolean isFY);
	
}
