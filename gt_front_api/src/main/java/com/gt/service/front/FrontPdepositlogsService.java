package com.gt.service.front;

import java.util.List;

import com.gt.entity.Pdepositlogs;

/**
 * 保证金操作记录表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPdepositlogsService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pdepositlogs instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pdepositlogs findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pdepositlogs instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pdepositlogs> list(int firstResult, int maxResults, String filter,boolean isFY);
}
