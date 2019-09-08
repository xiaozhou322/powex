package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.Pcapitaldetails;
import com.gt.entity.PchargeWithdrawStatistics;
import com.gt.entity.PcwTotalStatistics;
import com.gt.entity.PdataStatistics;
import com.gt.entity.Pdepositlogs;
import com.gt.entity.PentrustStatistics;
import com.gt.entity.Pprofitlogs;
import com.gt.entity.Pproject;
import com.gt.entity.PtradeStatistics;

/**
 * 数据统计服务
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
public interface FrontPstatisticsDataService {
	
	/**
	 * 数据中心分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<PdataStatistics> dataStatisticsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 交易数据统计分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<PtradeStatistics> tradeStatisticsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	
	/**
	 * 委托数据统计分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<PentrustStatistics> entrustStatisticsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 充提币统计分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<PchargeWithdrawStatistics> chargeWithdrawStatisticsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	
	/**
	 * 资金明细统计分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pcapitaldetails> capitaldetailsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 收益记录统计分页查询
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Pdepositlogs> depositlogsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 保存委托单统计数据
	 * @param instance
	 */
	public void savePentrustStatistics(PentrustStatistics instance);
	
	/**
	 * 保存成交单统计数据
	 * @param instance
	 */
	public void savePtradeStatistics(PtradeStatistics instance);
	
	/**
	 * 保存充提币统计数据
	 * @param instance
	 */
	public void savePchargeWithdrawStatistics(PchargeWithdrawStatistics instance);
	
	/**
	 * 保存收益记录统计数据
	 * @param instance
	 */
	public void savePprofitlogs(Pprofitlogs pprofitlogs);
	
	/**
	 * 保存数据中心记录表
	 * @param pdataStatistics
	 */
	public void savePdataStatistics(PdataStatistics pdataStatistics);
	
	/**
	 * 按条件查询交易/挂单统计数据
	 * @param filter
	 * @return
	 */
	public PdataStatistics findPdataStatisticsByParam(String filter);
	
	/**
	 * 修改交易/挂单统计数据
	 * @param instance
	 */
	public void updatePdataStatistics(PdataStatistics instance);
	
	/**
	 * 保存冲提币统计数据
	 * @param pcwTotalStatistics
	 */
	public void savePcwTotalStatistics(PcwTotalStatistics pcwTotalStatistics);
	
	/**
	 * 按条件查询冲提统计数据
	 * @param filter
	 * @return
	 */
	public PcwTotalStatistics findCwTotalStatisticsByParam(String filter);
	
	
	/**
	 * 修改冲提统计数据
	 * @param instance
	 */
	public void updatePcwTotalStatistics(PcwTotalStatistics instance);
	
	/**
	 * 冲提统计数据分页查询
	 * @param i
	 * @param j
	 * @param filter
	 * @param b
	 * @return
	 */
	public List<PcwTotalStatistics> pcwStatisticslist(int i, int j,String filter, boolean b);
	
	/**
	 * 按条件查询挂单周统计曲线
	 * @param filter
	 * @return
	 */
	public Map<Integer, Object> getEntrustSQLValue1(String sqlfilter);


	/**
	 * 按条件查询交易额周统计曲线
	 * @param filter
	 * @return
	 */
	public Map<Integer, Object> getTradeSQLValue1(String sqlfilter);


	/**
	 * 按条件查询充提币周统计曲线
	 * @param filter
	 * @return
	 */
	public Map<Integer, Object> getChargeWithdrawSQLValue1(String sqlfilter);

}
