package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pproduct;

/**
 * 权证产品service
 * @author zhouyong
 *
 */
public interface FrontPproductService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pproduct instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pproduct findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pproduct instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pproduct> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 根据条件币种信息
	 * @param propertyName1
	 * @param value1
	 * @param propertyName2
	 * @param value2
	 * @return
	 */
	public List<Pproduct> findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2);
	
	/**
	 * 审核币种信息
	 * @return
	 */
	public void updateAudit(Pproduct product, Fvirtualcointype fvirtualcointype, Integer type);
	
	/**
	 * 根据兑换币种查找项目方产品信息
	 * @param coinId
	 * @return
	 */
	public Pproduct findByCoinId(Integer coinId);
}