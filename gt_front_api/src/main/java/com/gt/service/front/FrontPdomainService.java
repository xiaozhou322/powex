package com.gt.service.front;

import java.util.List;

import com.gt.entity.Pdomain;

/**
 * 项目方域名表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPdomainService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pdomain instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pdomain findById(java.lang.Integer id);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pdomain> list(int firstResult, int maxResults,String filter,boolean isFY);
	
	
	/**
	 * 根据完整查询记录
	 * @param completeDomain
	 * @return
	 */
	public Pdomain findByProperty(String propertyName, Object value);

	/**
	 * 修改记录
	 * @return
	 */
	public void update(Pdomain pdomain);
	
	public Pdomain findByProjectId(java.lang.Integer projectId);
}
