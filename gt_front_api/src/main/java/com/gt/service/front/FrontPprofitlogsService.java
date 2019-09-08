package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pprofitlogs;

/**
 * 收益记录表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
public interface FrontPprofitlogsService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(Pprofitlogs instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public Pprofitlogs findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(Pprofitlogs instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pprofitlogs> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 根据参数获取资产明细
	 * @param projectId
	 */
	public List<Map> getAssetListByParams(int projectId);
	
	/**
	 * 
	* @Title: getMonthServiceCharge  
	* @Description: 根据项目方id和状态获取当月收益 
	* @param @return  
	* @return List<Map>
	* @throws
	 */
	public List<Map> getMonthServiceCharge(int status,int projectId);
	
	/**
	 * 根据项目方id查询所有收益的币种
	 * @param projectId
	 * @return
	 */
	public List<Integer> getAllProfitCoinType(String tableName,String fieldName,String filter);
	
	
	/**
	 * 项目方收益结算
	 * @param wallet
	 * @param profitlogs
	 */
	public void updateProfitlogsSettle(Fvirtualwallet wallet, Pprofitlogs profitlogs);
}
