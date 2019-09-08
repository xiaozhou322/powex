package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fvirtualaddress;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pcointype;

/**
 * 项目方币种表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPcointypeService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pcointype instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pcointype findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pcointype instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pcointype> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 根据条件币种信息
	 * @param propertyName1
	 * @param value1
	 * @param propertyName2
	 * @param value2
	 * @return
	 */
	public List<Pcointype> findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2);
	
	/**
	 * 审核币种信息
	 * @return
	 */
	public void updateAudit(Pcointype pcointype, Fvirtualcointype fvirtualcointype, Integer type);
	
	/**
	 * 根据平台虚拟币id查找项目方币种信息
	 * @param coinId
	 * @return
	 */
	public Pcointype findByCoinId(Integer coinId);
}
;