package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositlogs;
import com.gt.entity.Ptrademapping;

/**
 * 保证金表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPdepositService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pdeposit instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pdeposit findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pdeposit instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pdeposit> list(int firstResult, int maxResults, String filter,boolean isFY);

	public void saveAll(Pdeposit pdeposit, Fvirtualwallet fvirtualwallet,
			Pdepositlogs pdepositlog,List<Ptrademapping> ptrademappingList, Pcointype pcointype);
}
