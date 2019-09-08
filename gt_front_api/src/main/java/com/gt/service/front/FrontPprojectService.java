package com.gt.service.front;

import java.util.List;

import com.gt.entity.Pdomain;
import com.gt.entity.Pfees;
import com.gt.entity.Pproject;

/**
 * 项目表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPprojectService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pproject instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pproject findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pproject instance);
	
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pproject> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	public Pproject findByProperty(String propertyName, Object value);
}
