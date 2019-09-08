package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PchargeWithdrawStatisticsDAO;
import com.gt.dao.PcwTotalStatisticsDAO;
import com.gt.dao.PdataStatisticsDAO;
import com.gt.dao.PentrustStatisticsDAO;
import com.gt.dao.PprofitlogsDAO;
import com.gt.dao.PtradeStatisticsDAO;
import com.gt.entity.Pcapitaldetails;
import com.gt.entity.PchargeWithdrawStatistics;
import com.gt.entity.PcwTotalStatistics;
import com.gt.entity.PdataStatistics;
import com.gt.entity.Pdepositlogs;
import com.gt.entity.PentrustStatistics;
import com.gt.entity.Pprofitlogs;
import com.gt.entity.PtradeStatistics;



@Service("frontPstatisticsDataService")
public class FrontPstatisticsDataServiceImpl implements FrontPstatisticsDataService {
	@Autowired
	private PdataStatisticsDAO pdataStatisticsDAO;
	@Autowired
	private PtradeStatisticsDAO ptradeStatisticsDAO;
	@Autowired
	private PentrustStatisticsDAO pentrustStatisticsDAO;
	@Autowired
	private PchargeWithdrawStatisticsDAO pchargeWithdrawStatisticsDAO;
	@Autowired
	private PcwTotalStatisticsDAO pcwTotalStatisticsDAO;
	
	@Autowired
	private PprofitlogsDAO pprofitlogsDAO;
	
	public void savePentrustStatistics(PentrustStatistics instance) {
		pentrustStatisticsDAO.save(instance);
	}
	
	public void savePtradeStatistics(PtradeStatistics instance) {
		ptradeStatisticsDAO.save(instance);
	}
	
	public void savePchargeWithdrawStatistics(PchargeWithdrawStatistics instance) {
		pchargeWithdrawStatisticsDAO.save(instance);
	}
	
	public void savePprofitlogs(Pprofitlogs pprofitlogs) {
		pprofitlogsDAO.save(pprofitlogs);
	}
	
	public void savePdataStatistics(PdataStatistics pdataStatistics) {
		pdataStatisticsDAO.save(pdataStatistics);
	}
	
	public void savePcwTotalStatistics(PcwTotalStatistics pcwTotalStatistics) {
		pcwTotalStatisticsDAO.save(pcwTotalStatistics);
	}
	
	public List<PdataStatistics> dataStatisticsList(int firstResult, int maxResults, String filter,boolean isFY) {
		return pdataStatisticsDAO.list(firstResult, maxResults, filter, isFY);
	}


	public List<PtradeStatistics> tradeStatisticsList(int firstResult, int maxResults, String filter, boolean isFY) {
		List<PtradeStatistics> tradeStatisticsList = ptradeStatisticsDAO.list(firstResult, maxResults, filter, isFY);
		for (PtradeStatistics tradeStatistics : tradeStatisticsList) {
			tradeStatistics.getTrademappingStr();
		}
		return tradeStatisticsList;
	}


	public List<PentrustStatistics> entrustStatisticsList(int firstResult, int maxResults, String filter, boolean isFY) {
		List<PentrustStatistics> entrustStatisticsList =  pentrustStatisticsDAO.list(firstResult, maxResults, filter, isFY);
		for (PentrustStatistics entrustStatistics : entrustStatisticsList) {
			entrustStatistics.getTrademappingStr();
		}
		return entrustStatisticsList;
	}


	public List<PchargeWithdrawStatistics> chargeWithdrawStatisticsList(int firstResult, int maxResults, String filter,
			boolean isFY) {
		return pchargeWithdrawStatisticsDAO.list(firstResult, maxResults, filter, isFY);
	}


	public List<Pcapitaldetails> capitaldetailsList(int firstResult, int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pdepositlogs> depositlogsList(int firstResult, int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return null;
	}

	public PcwTotalStatistics findCwTotalStatisticsByParam(String filter) {
		return pcwTotalStatisticsDAO.findCwTotalStatisticsByParam(filter);
	}


	@Override
	public List<PcwTotalStatistics> pcwStatisticslist(int i, int j,
			String filter, boolean b) {
		return pcwTotalStatisticsDAO.list(i, j, filter, b);
	}

	public void updatePcwTotalStatistics(PcwTotalStatistics instance) {
		pcwTotalStatisticsDAO.attachDirty(instance);
	}

	@Override
	public PdataStatistics findPdataStatisticsByParam(String filter) {
		return pdataStatisticsDAO.findPdataStatisticsByParam(filter);
	}

	@Override
	public void updatePdataStatistics(PdataStatistics instance) {
		pdataStatisticsDAO.attachDirty(instance);
	}
	
	@Override
	public Map<Integer, Object> getEntrustSQLValue1(String sqlfilter) {
		return pentrustStatisticsDAO.getEntrustSQLValue1(sqlfilter);
	}

	@Override
	public Map<Integer, Object> getTradeSQLValue1(String sqlfilter) {
		return ptradeStatisticsDAO.getTradeSQLValue1(sqlfilter);
	}

	@Override
	public Map<Integer, Object> getChargeWithdrawSQLValue1(String sqlfilter) {
		return pchargeWithdrawStatisticsDAO.getChargeWithdrawSQLValue1(sqlfilter);
	}

}
