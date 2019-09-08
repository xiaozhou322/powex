package com.gt.service.front;

import java.util.List;

import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositcointype;

/**
 * 保证金币种表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPdepositcointypeService {
	
	public Pdepositcointype findById(int id);
	
	public List<Pdepositcointype> findByProperty(String propertyName, Object value);

	public List<Pdepositcointype> findByParam(String filter);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pdepositcointype> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pdepositcointype instance);
}
