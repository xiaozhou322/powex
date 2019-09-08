package com.gt.service.front;

import java.util.List;

import com.gt.entity.Ftrademapping;
import com.gt.entity.Pcointype;
import com.gt.entity.Ptrademapping;

/**
 * 项目方交易市场表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPtrademappingService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Ptrademapping instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Ptrademapping findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Ptrademapping instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Ptrademapping> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 审核
	 * @param instance
	 */
	public void updateAudit(Ptrademapping ptrademapping,Ftrademapping ftrademapping, int i);
	
	
	/**
	 * 根据平台市场id查找项目方市场信息
	 * @param coinId
	 * @return
	 */
	public Ptrademapping findByTrademappingId(Integer trademappingId);
}
