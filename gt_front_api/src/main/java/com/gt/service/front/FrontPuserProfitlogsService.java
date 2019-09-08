package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fvirtualwallet;
import com.gt.entity.PuserProfitlogs;

/**
 * 用户挖矿收益记录表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
public interface FrontPuserProfitlogsService {
	
	/**
	 * 保存记录
	 * @param instance
	 */
	public void save(PuserProfitlogs instance);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public PuserProfitlogs findById(java.lang.Integer id);
	
	/**
	 * 修改
	 * @param instance
	 */
	public void update(PuserProfitlogs instance);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<PuserProfitlogs> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 获取用户买入的挖矿奖励
	 * @param yesterdayStr
	 * @return
	 */
	public List<Map<String, Object>> getUserBuyProfitlogs(String yesterdayStr);
	
	
	/**
	 * 获取用户卖出的挖矿奖励
	 * @param yesterdayStr
	 * @return
	 */
	public List<Map<String, Object>> getUserSellProfitlogs(String yesterdayStr);
	
	/**
	 * 
	* @Title: updateUserProfit  
	* @Description: 发放用户交易收益 
	* @author Ryan
	* @param @param walletInfo
	* @param @param puserProfitlogs  
	* @return void
	* @throws
	 */
	public void updateUserProfit(Fvirtualwallet walletInfo,PuserProfitlogs puserProfitlogs);
	
}
