package com.gt.service.front;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.gt.entity.FactiveCoinLogs;
import com.gt.entity.FapproCoinLogs;
import com.gt.entity.FconvertCoinLogs;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pproduct;

public interface FvirtualWalletService {
	
	public Fvirtualwallet findById(int id);
	
	public Fvirtualwallet findFvirtualwallet(int fuser,int vid);

	public List<Fvirtualwallet> findByTwoProperty(String propertyName1, int value1,String propertyName2, int value2);
	
	public List<Fvirtualwallet> findByProperty(String propertyName1, Object value1);
	
	public void attachDirty(Fvirtualwallet instance);
	
	public void updateObj(Fvirtualwallet obj);
	
	public List<Map> getTotalQty();
	
	public BigDecimal getTotalQty(int vid);

	public List<Fvirtualwallet> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 币种兑换保存接口
	 * @param fvirtualwalletFrom
	 * @param fvirtualwalletTo
	 * @param convertAmount1
	 * @param convertAmount2
	 * @param fuser
	 */
	public void updateConvertCoinSubmit(Fvirtualwallet fvirtualwalletFrom, Fvirtualwallet fvirtualwalletTo,
				double convertAmount1, double convertAmount2, Fuser fuser, Pproduct pproduct, Integer convertType);
	
	/**
	 * 修改钱包并修改币种激活流水
	 * @param instance
	 */
	public void updateTradeActiveCoin(FactiveCoinLogs activeLogs, Fvirtualwallet fvirtualwallet,
				Fvirtualwallet fvirtualwalletPow, double consumePowNum);
	
	
	/**
	 * 分页查询币种兑换记录
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FconvertCoinLogs> convertCoinLogsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 保存币种激活流水记录
	 * @param instance
	 */
	public Integer saveActiveCoinLogs(FactiveCoinLogs instance);
	
	/**
	 * 修改币种激活流水记录
	 * @param instance
	 */
	public void updateActiveCoinLogs(FactiveCoinLogs instance);
	
	
	/**
	 * 分页查询币种待激活记录
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FactiveCoinLogs> queryActiveCoinLogsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 根据id查询币种激活流水
	 * @param id
	 * @return
	 */
	public FactiveCoinLogs findActiveCoinLogsById(int id);
	
	
	/**
	 * 币种拨付操作
	 * @param instance
	 */
	public void updateApproCoinLogs(double amount, Fvirtualwallet fvirtualwallet, Pproduct pproduct, Fuser fuser);
	
	
	/**
	 * 分页查询币种拨付流水记录
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FapproCoinLogs> queryApproCoinLogsList(int firstResult, int maxResults, String filter,boolean isFY);
}
