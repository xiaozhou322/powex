package com.gt.service.front;

import java.util.List;

import com.gt.entity.Pad;
import com.gt.entity.Pcapitaldetails;

/**
 * 资产明细
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
public interface FrontPcapitaldetailsService {
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pcapitaldetails instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pcapitaldetails findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pcapitaldetails instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pcapitaldetails> list(int firstResult, int maxResults, String filter,boolean isFY);
}
