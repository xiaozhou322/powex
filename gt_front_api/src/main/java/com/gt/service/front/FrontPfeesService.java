package com.gt.service.front;

import java.util.List;

import com.gt.entity.Pfees;

/**
 * 项目方手续费率表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPfeesService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pfees instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pfees findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pfees instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pfees> list(int firstResult, int maxResults, String filter,boolean isFY);
}
