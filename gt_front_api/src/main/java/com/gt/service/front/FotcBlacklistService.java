package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcBlacklist;

/**
 * otc黑名单用户Service
 * @author zhouyong
 *
 */
public interface FotcBlacklistService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(FotcBlacklist instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public FotcBlacklist findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(FotcBlacklist instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FotcBlacklist> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 根据用户id查询黑名单信息
	 * @param userId
	 * @return
	 */
	public FotcBlacklist findByUserId(java.lang.Integer userId);
	
}
