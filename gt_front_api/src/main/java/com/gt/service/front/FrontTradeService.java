package com.gt.service.front;

import java.util.Date;
import java.util.List;

import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Fentrustplan;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;

public interface FrontTradeService {
		
	public Fentrust findFentrustById(int id);

	public List<Fentrustlog> findFentrustLogByFentrust(Fentrust fentrust);

	// 最新成交记录
	public List<Fentrust> findLatestSuccessDeal(int coinTypeId,
			int fentrustType, int count);

	public List<Fentrust> findAllGoingFentrust(int coinTypeId,
			int fentrustType, boolean isLimit);

	// 获得24小时内的成交记录
	public List<Fentrustlog> findLatestSuccessDeal24(int coinTypeId, int hour);

	public Fentrustlog findLatestDeal(int coinTypeId);

	// 委托记录
	public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int first_result, int max_result, String order,
			int entrust_status[], Date beginDate, Date endDate);

	// 计划委托
	public List<Fentrustplan> findEntrustPlan(int type, int status[]);

	// 委托买入
	public Fentrust updateEntrustBuy(int tradeMappingID, double tradeAmount,
			double tradeCnyPrice, Fuser fuser, boolean fisLimit, Integer fsourceId,
			double commissionRate, double feesRate);

	// 委托卖出
	public Fentrust updateEntrustSell(int tradeMappingID, double tradeAmount,
			double tradeCnyPrice, Fuser fuser, boolean fisLimit, Integer fsourceId,
			double commissionRate, double feesRate);

	// 委托记录
	public List<Fentrust> findFentrustHistory(int firstResult, int maxResults,
			String filter, boolean isFY);
	
	// 委托记录
	public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int first_result, int max_result, String order,
			int entrust_status[]);

	public int findFentrustHistoryCount(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int entrust_status[]);

	public List<Fentrustplan> findFentrustplan(int fuser, int fvirtualcointype,
			int[] fstatus, int firtResult, int maxResult, String order);

	public Fentrustplan findFentrustplanById(int id);

	public long findFentrustplanCount(int fuser, int fvirtualcointype,
			int[] fstatus);


	public void updateCancelFentrust(Fentrust fentrust, Fuser fuser);

	public List<Fentrust> findFentrustByParam(int firstResult, int maxResults,
			String filter, boolean isFY);

	public int findFentrustByParamCount(String filter);
	
	public void updateFeeLog(Fentrust entrust,Fvirtualwallet fvirtualwallet);

	
	public List<Fentrust> findFentrustsByParam(int firstResult, int maxResults, String filter,boolean isFY);
	

	public void updateFentrust(Fentrust fentrust);
	
	public void updateCoinFentrust(Fentrust fentrust,List<Fvirtualwallet> fvirtualwallets,List<Fintrolinfo> fintrolinfos);

}
