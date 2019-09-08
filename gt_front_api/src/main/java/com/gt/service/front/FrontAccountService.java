package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcaptualoperation;

/**
 * 会员资金记录接口
 * @author zhouyong
 *
 */
public interface FrontAccountService {
	
	/**
	 * 保存资金记录信息
	 * @param fcapitaloperation
	 */
	public Integer addFcapitaloperation(Fcapitaloperation fcapitaloperation);
	
	/**
	 * 查询资金记录列表
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Fcapitaloperation> findCapitalList(int firstResult, int maxResults,String filter,boolean isFY);
	
	/**
	 * 根据条件查询资金记录条数
	 * @param param
	 * @return
	 */
	public int findCapitalCount(Map<String, Object> param);
	
	/**
	 * 根据id查询资金记录信息
	 * @param id
	 * @return
	 */
	public Fcapitaloperation findCapitalOperationById(int id);
	
	/**
	 * 根据备注查询资金记录信息
	 * @param remark
	 * @return
	 */
	public Fcapitaloperation findCapitalOperationByRemark(String remark);
	
	/**
	 * 更新资金记录信息
	 * @param fcapitaloperation
	 */
	public void updateCapitalOperation(Fcapitaloperation fcapitaloperation);
	
	public void updateSaveCapitalOperation(Fcapitaloperation fcapitaloperation);
	
	/**
	 * 更新人民币提现信息
	 * @param withdrawBanlance
	 * @param fuser
	 * @param fbankinfoWithdraw
	 * @return
	 */
	public boolean updateWithdrawCNY(double withdrawBanlance,Fuser fuser,FbankinfoWithdraw fbankinfoWithdraw);
	
	/**
	 * 更新美元提现信息
	 * @param withdrawBanlance
	 * @param fuser
	 * @param fbankinfoWithdraw
	 * @return
	 */
	public int updateWithdrawUSDT(double withdrawBanlance,Fuser fuser,FbankinfoWithdraw fbankinfoWithdraw);
	
	/**
	 * 根据id查询积分信息
	 * @param id
	 * @return
	 */
	public Fscore findFscoreById(int id);
	
	/**
	 * 更新取消人民币提现信息
	 * @param fcapitaloperation
	 * @param fuser
	 */
	public void updateCancelWithdrawCny(Fcapitaloperation fcapitaloperation,Fuser fuser);
	
	/**
	 * 更新取消美元提现信息
	 * @param fcapitaloperation
	 * @param fuser
	 */
	public void updateCancelWithdrawUsdt(Fcapitaloperation fcapitaloperation,Fuser fuser);
	
	/**
	 * 更新取消比特币提现信息
	 * @param fvirtualcaptualoperation
	 * @param fuser
	 */
	public void updateCancelWithdrawBtc(Fvirtualcaptualoperation fvirtualcaptualoperation,Fuser fuser);
	
	/**
	 * 查询会员今日人民币提现次数
	 * @param fuser
	 * @return
	 */
	public int getTodayCnyWithdrawTimes(Fuser fuser);
	
	/**
	 * 查询会员今日虚拟币提现次数
	 * @param fuser
	 * @return
	 */
	public int getTodayVirtualCoinWithdrawTimes(Fuser fuser);
	
	
}
