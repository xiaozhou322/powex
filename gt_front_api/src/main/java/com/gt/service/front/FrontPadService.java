package com.gt.service.front;

import java.util.List;

import com.gt.entity.Farticle;
import com.gt.entity.Pad;

/**
 * 项目方公告表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:33
 */
public interface FrontPadService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pad instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pad findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pad instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pad> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 
	* @Title: updateAudit  
	* @Description: 审核 
	* @author Ryan
	* @param @param pad
	* @param @param article  
	* @return void
	* @throws
	 */
	public void updateAudit(Pad pad, Farticle article);
}
