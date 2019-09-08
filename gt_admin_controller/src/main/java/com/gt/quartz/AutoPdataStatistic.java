package com.gt.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.PchargeWithdrawStatistics;
import com.gt.entity.PcwTotalStatistics;
import com.gt.entity.PdataStatistics;
import com.gt.entity.Pdomain;
import com.gt.entity.PentrustStatistics;
import com.gt.entity.PtradeStatistics;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontPdomainService;
import com.gt.service.front.FrontPstatisticsDataService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;


/**
 * 项目方数据统计定时任务
 * @author zhouyong
 *
 */
public class AutoPdataStatistic {
	private static final Logger log = LoggerFactory.getLogger(AutoPdataStatistic.class);
	
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private TradeMappingService tradeMappingService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FrontPstatisticsDataService pstatisticsDataService ;
	@Autowired
	private FrontPdomainService pdomainService ;
	
	/**
	 * 数据统计定时任务入口
	 */
	public void work() {
		synchronized (this) {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行项目方数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
			try{
				String yesterdayStr = Utils.getYesterday();
				//项目方成交单记录统计
				this.tradeStatistic(yesterdayStr);
				//项目方委托单记录统计
				this.entrustStatistic(yesterdayStr);
				//充提币记录统计
				this.chargeWithdrawStatistic(yesterdayStr);
				
				//查询所有项目方域名信息
				String filter = " where auditStatus = "+ AuditStatusEnum.auditPass;
				List<Pdomain> pdomainList = pdomainService.list(0, 0, filter, false);
				if(null != pdomainList && pdomainList.size() > 0) {
					//项目方委托单/成交单统计汇总
					tradeEntrustTotalStatistic(pdomainList, yesterdayStr);
				}
				
			}catch (Exception e) {
				e.printStackTrace() ;
				log.error("项目方成交单记录统计异常", e.getMessage());
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>结束项目方数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
	
	
	
	
	/**
	 * 项目方成交单记录统计
	 */
	private void tradeStatistic(String yesterdayStr) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行项目方成交单记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
		try{
			//查询所有项目方的市场
			String filter = "where fprojectId > 0";
			List<Ftrademapping> tradeMappingList = tradeMappingService.list(0, 0, filter, false);
			
			if(null != tradeMappingList && tradeMappingList.size() > 0) {
				for (int i = 0; i < tradeMappingList.size(); i++) {
					Ftrademapping tradeMapping = tradeMappingList.get(i);
					
					//买入成交笔数
					String sqlfilter = "select count(a.fid) from fentrustlog a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY 
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double buyNum = adminService.getSQLValue(sqlfilter);
					//卖出成交笔数
					sqlfilter = "select count(a.fid) from fentrustlog a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double sellNum = adminService.getSQLValue(sqlfilter);
					//买入成交额
					sqlfilter = "select sum(a.fAmount) from fentrustlog a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double buyAmount = adminService.getSQLValue(sqlfilter);
					//卖出成交额
					sqlfilter = "select sum(a.fAmount) from fentrustlog a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double sellAmount = adminService.getSQLValue(sqlfilter);
					//成交总额
					double totalAmount = buyAmount + sellAmount;
					
					//保存统计记录
					PtradeStatistics ptradeStatistics = new PtradeStatistics();
					Fuser fuser = frontUserService.findById(tradeMapping.getFprojectId());
					ptradeStatistics.setProjectId(fuser);
					ptradeStatistics.setTrademappingId(tradeMapping);
					ptradeStatistics.setStatisticalDate(yesterdayStr);
					ptradeStatistics.setBuyNum((int)buyNum);
					ptradeStatistics.setSellNum((int)sellNum);
					ptradeStatistics.setBuyAmount(buyAmount);
					ptradeStatistics.setSellAmount(sellAmount);
					ptradeStatistics.setTotalAmount(totalAmount);
					ptradeStatistics.setCreateTime(Utils.getTimestamp());
					ptradeStatistics.setUpdateTime(Utils.getTimestamp());
					pstatisticsDataService.savePtradeStatistics(ptradeStatistics);
				}
			}
		}catch (Exception e) {
			e.printStackTrace() ;
			log.error("项目方成交单记录统计异常", e.getMessage());
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>结束项目方成交单记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
	
	/**
	 * 项目方委托单记录统计
	 * @param yesterdayStr
	 */
	private void entrustStatistic(String yesterdayStr) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行项目方委托单记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
		try{
			//查询所有项目方的市场
			String filter = "where fprojectId > 0";
			List<Ftrademapping> tradeMappingList = tradeMappingService.list(0, 0, filter, false);
			
			if(null != tradeMappingList && tradeMappingList.size() > 0) {
				for (int i = 0; i < tradeMappingList.size(); i++) {
					Ftrademapping tradeMapping = tradeMappingList.get(i);
					//委托人数
					String sqlfilter = "select COUNT(DISTINCT(FUs_fId)) FROM fentrust a"
							+ "  where a.fStatus = "+ EntrustStatusEnum.Going
							+ "  and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double buyEntrustPeople = adminService.getSQLValue(sqlfilter);
					//买入委托笔数
					sqlfilter = "select count(a.fid) FROM fentrust a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY 
							+"   and a.fStatus = "+ EntrustStatusEnum.Going
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double buyEntrustNum = adminService.getSQLValue(sqlfilter);
					//卖出委托笔数
					sqlfilter = "select count(a.fid) from fentrust a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL
							+"   and a.fStatus = "+ EntrustStatusEnum.Going
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double sellEntrustNum = adminService.getSQLValue(sqlfilter);
					//买入挂单金额
					sqlfilter = "select sum(a.fAmount) from fentrust a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY
							+"   and a.fStatus = "+ EntrustStatusEnum.Going
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double buyEntrustAmount = adminService.getSQLValue(sqlfilter);
					//卖出挂单金额
					sqlfilter = "select sum(a.fAmount) from fentrust a"
							+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL
							+"   and a.fStatus = "+ EntrustStatusEnum.Going
							+"   and a.ftrademapping = "+ tradeMapping.getFid()
							+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double sellEntrustAmount = adminService.getSQLValue(sqlfilter);
					//挂单总额
					double totalAmount = buyEntrustAmount + sellEntrustAmount;
					
					//保存统计记录
					PentrustStatistics pentrustStatistics = new PentrustStatistics();
					Fuser fuser = frontUserService.findById(tradeMapping.getFprojectId());
					pentrustStatistics.setProjectId(fuser);
					pentrustStatistics.setTrademappingId(tradeMapping);
					pentrustStatistics.setStatisticalDate(yesterdayStr);
					pentrustStatistics.setMandatorNum((int)buyEntrustPeople);
					pentrustStatistics.setBuyNum((int)buyEntrustNum);
					pentrustStatistics.setSellNum((int)sellEntrustNum);
					pentrustStatistics.setBuyEntrustAmount(buyEntrustAmount);
					pentrustStatistics.setSellEntrustAmount(sellEntrustAmount);
					pentrustStatistics.setEntrustAmount(totalAmount);
					pentrustStatistics.setCreateTime(Utils.getTimestamp());
					pentrustStatistics.setUpdateTime(Utils.getTimestamp());
					pstatisticsDataService.savePentrustStatistics(pentrustStatistics);
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace() ;
			log.error("项目方委托单记录统计异常", e.getMessage());
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>结束项目方委托单记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
	
	
	/**
	 * 充提币记录统计
	 * @param yesterdayStr
	 */
	private void chargeWithdrawStatistic(String yesterdayStr) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行充提币记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
		try{
			//查询所有项目方的市场
			String filter = "where fprojectId > 0";
			List<Fvirtualcointype> cointypeList = virtualCoinService.list(0, 0, filter, false);
			
			if(null != cointypeList && cointypeList.size() > 0) {
				for (int i = 0; i < cointypeList.size(); i++) {
					Fvirtualcointype cointype = cointypeList.get(i);
					
					//冲币人数
					String sqlfilter = "select COUNT(DISTINCT(FUs_fId2)) FROM fvirtualcaptualoperation a"
							+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_IN 
							+ "  and a.fVi_fId2 = "+ cointype.getFid()
							+"   and DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double chargeNum = adminService.getSQLValue(sqlfilter);
					//提币人数
					sqlfilter = "select COUNT(DISTINCT(FUs_fId2)) FROM fvirtualcaptualoperation a"
							+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_OUT
							+ "  and a.fVi_fId2 = "+ cointype.getFid()
							+"   and DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double withdrawNum = adminService.getSQLValue(sqlfilter);
					//冲币数量
					sqlfilter = "select SUM(a.fAmount + a.ffees) FROM fvirtualcaptualoperation a"
							+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_IN 
							+"   and a.fStatus = "+ VirtualCapitalOperationInStatusEnum.SUCCESS
							+"   and a.fVi_fId2 = "+ cointype.getFid()
							+"   and DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double chargeAmount = adminService.getSQLValue(sqlfilter);
					//提币数量
					sqlfilter = "select SUM(a.fAmount + a.ffees) from fvirtualcaptualoperation a"
							+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_OUT
							+"   and a.fStatus = "+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
							+"   and a.fVi_fId2 = "+ cointype.getFid()
							+"   and DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double withdrawAmount = adminService.getSQLValue(sqlfilter);
					//待审核数量  ？？
					sqlfilter = "select SUM(a.fAmount + a.ffees) from fvirtualcaptualoperation a"
							+"   where a.fStatus = "+ EntrustStatusEnum.Going
							+"   and a.fVi_fId2 = "+ cointype.getFid()
							+"   and DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
					double buyEntrustAmount = adminService.getSQLValue(sqlfilter);
					
					//保存统计记录
					PchargeWithdrawStatistics cwStatistics = new PchargeWithdrawStatistics();
					Fuser fuser = frontUserService.findById(cointype.getFprojectId());
					cwStatistics.setProjectId(fuser);
					cwStatistics.setCointypeId(cointype);
					cwStatistics.setStatisticalDate(yesterdayStr);
					cwStatistics.setChargeNum((int)chargeNum);
					cwStatistics.setWithdrawNum((int)withdrawNum);
					cwStatistics.setChargeAmount(chargeAmount);
					cwStatistics.setWithdrawAmount(withdrawAmount);
					cwStatistics.setAuditAmount(buyEntrustAmount);
					cwStatistics.setCreateTime(Utils.getTimestamp());
					cwStatistics.setUpdateTime(Utils.getTimestamp());
					pstatisticsDataService.savePchargeWithdrawStatistics(cwStatistics);
					
					
					//冲提币汇总统计
					//按币种和项目方id查询统计数据
					sqlfilter = " where projectId.fid = " + fuser.getFid() + " and cointypeId.fid =" + cointype.getFid();
					PcwTotalStatistics cwTotalStatistics = pstatisticsDataService.findCwTotalStatisticsByParam(sqlfilter);
					if(null == cwTotalStatistics) {
						cwTotalStatistics = new PcwTotalStatistics();
						cwTotalStatistics.setProjectId(fuser);
						cwTotalStatistics.setCointypeId(cointype);
						cwTotalStatistics.setChargeNum(cwStatistics.getChargeNum());
						cwTotalStatistics.setWithdrawNum(cwStatistics.getWithdrawNum());
						cwTotalStatistics.setChargeAmount(cwStatistics.getChargeAmount());
						cwTotalStatistics.setWithdrawAmount(cwStatistics.getWithdrawAmount());
						cwTotalStatistics.setAuditAmount(cwStatistics.getAuditAmount());
						cwTotalStatistics.setCreateTime(Utils.getTimestamp());
						cwTotalStatistics.setUpdateTime(Utils.getTimestamp());
						pstatisticsDataService.savePcwTotalStatistics(cwTotalStatistics);
					} else {
						cwTotalStatistics.setChargeNum(cwTotalStatistics.getChargeNum()+cwStatistics.getChargeNum());
						cwTotalStatistics.setWithdrawNum(cwTotalStatistics.getWithdrawNum()+cwStatistics.getWithdrawNum());
						cwTotalStatistics.setChargeAmount(cwTotalStatistics.getChargeAmount()+cwStatistics.getChargeAmount());
						cwTotalStatistics.setWithdrawAmount(cwTotalStatistics.getWithdrawAmount()+cwStatistics.getWithdrawAmount());
						cwTotalStatistics.setAuditAmount(cwTotalStatistics.getAuditAmount()+cwStatistics.getAuditAmount());
						cwTotalStatistics.setUpdateTime(Utils.getTimestamp());
						pstatisticsDataService.updatePcwTotalStatistics(cwTotalStatistics);
					}
					
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace() ;
			log.error("充提币记录统计异常", e.getMessage());
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>结束充提币记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
	
	/**
	 * 项目方委托单/成交单统计汇总
	 */
	private void tradeEntrustTotalStatistic(List<Pdomain> pdomainList, String yesterdayStr) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行项目方委托单/成交单统计汇总<<<<<<<<<<<<<<<<<<<<<<<<<<");
		try{
			for (int i = 0; i < pdomainList.size(); i++) {
				Pdomain pdomain = pdomainList.get(i);
				//买入成交笔数
				String sqlfilter = "select COUNT(a.fId) from fentrustlog a"
						+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY
						+"   and a.fsource = "+ pdomain.getProjectId().getFid()
						+"   and DATE_FORMAT(fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
				double buyTradeAmount = adminService.getSQLValue(sqlfilter);
				//卖出成交笔数
				sqlfilter = "select COUNT(a.fId) from fentrustlog a"
						+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL
						+"   and a.fsource = "+ pdomain.getProjectId().getFid()
						+"   and DATE_FORMAT(fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
				double sellTradeAmount = adminService.getSQLValue(sqlfilter);
				//成交总笔数
				double totalTradeAmount = buyTradeAmount + sellTradeAmount;
				
				
				//买入挂单笔数
				sqlfilter = "select COUNT(a.fId) from fentrust a"
						+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY
						+"   and a.fStatus = "+ EntrustStatusEnum.Going
						+"   and a.fsource = "+ pdomain.getProjectId().getFid()
						+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
				double buyEntrustAmount = adminService.getSQLValue(sqlfilter);
				//卖出挂单笔数
				sqlfilter = "select COUNT(a.fId) from fentrust a"
						+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL
						+"   and a.fStatus = "+ EntrustStatusEnum.Going
						+"   and a.fsource = "+ pdomain.getProjectId().getFid()
						+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
				double sellEntrustAmount = adminService.getSQLValue(sqlfilter);
				//挂单总笔数
				double totalEntrustAmount = buyEntrustAmount + sellEntrustAmount;
				
				
				//冲币总笔数
				sqlfilter = "select COUNT(a.fId) FROM fvirtualcaptualoperation a"
						+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_IN 
						+ "  and a.fVi_fId2 in (select b.fId from fvirtualcointype b where b.fprojectId ="+ pdomain.getProjectId().getFid()+")"
						+ "  and DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
				double chargeAmount = adminService.getSQLValue(sqlfilter);
				//提币总笔数
				sqlfilter = "select COUNT(a.fId) FROM fvirtualcaptualoperation a"
						+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_OUT
						+ "  and a.fVi_fId2 in (select b.fId from fvirtualcointype b where b.fprojectId ="+ pdomain.getProjectId().getFid()+")"
						+ "  and DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
				double withdrawAmount = adminService.getSQLValue(sqlfilter);
				
				//冲提总数
				double chargeWithdrawAmount = chargeAmount + withdrawAmount;
				
				
				Fuser fuser = frontUserService.findById(pdomain.getProjectId().getFid());
				//冲提币汇总统计
				//按币种和项目方id查询统计数据
				sqlfilter = " where projectId.fid = " + fuser.getFid();
				PdataStatistics pdataStatistics = pstatisticsDataService.findPdataStatisticsByParam(sqlfilter);
				if(null == pdataStatistics) {
					pdataStatistics = new PdataStatistics();
					pdataStatistics.setProjectId(fuser);
					pdataStatistics.setStatisticalDate(yesterdayStr);
					pdataStatistics.setTradeBuyAmount(buyTradeAmount);
					pdataStatistics.setTradeSellAmount(sellTradeAmount);
					pdataStatistics.setTradeTotalAmount(totalTradeAmount);
					pdataStatistics.setEntrustBuyAmount(buyEntrustAmount);
					pdataStatistics.setEntrustSellAmount(sellEntrustAmount);
					pdataStatistics.setEntrustTotalAmount(totalEntrustAmount);
					pdataStatistics.setChargeNum(chargeAmount);
					pdataStatistics.setWithdrawNum(withdrawAmount);
					pdataStatistics.setChargeWithdrawNum(chargeWithdrawAmount);
					pdataStatistics.setCreateTime(Utils.getTimestamp());
					pdataStatistics.setUpdateTime(Utils.getTimestamp());
					pstatisticsDataService.savePdataStatistics(pdataStatistics);
				} else {
					pdataStatistics.setTradeBuyAmount(pdataStatistics.getTradeBuyAmount()+buyTradeAmount);
					pdataStatistics.setTradeSellAmount(pdataStatistics.getTradeSellAmount()+sellTradeAmount);
					pdataStatistics.setTradeTotalAmount(pdataStatistics.getTradeTotalAmount()+totalTradeAmount);
					pdataStatistics.setEntrustBuyAmount(pdataStatistics.getEntrustBuyAmount()+buyEntrustAmount);
					pdataStatistics.setEntrustSellAmount(pdataStatistics.getEntrustSellAmount()+sellEntrustAmount);
					pdataStatistics.setEntrustTotalAmount(pdataStatistics.getEntrustTotalAmount()+totalEntrustAmount);
					pdataStatistics.setChargeNum(pdataStatistics.getChargeNum()+chargeAmount);
					pdataStatistics.setWithdrawNum(pdataStatistics.getWithdrawNum()+withdrawAmount);
					pdataStatistics.setChargeWithdrawNum(pdataStatistics.getChargeWithdrawNum()+chargeWithdrawAmount);
					pdataStatistics.setUpdateTime(Utils.getTimestamp());
					pstatisticsDataService.updatePdataStatistics(pdataStatistics);
				}
			}
		}catch (Exception e) {
			e.printStackTrace() ;
			log.error("项项目方委托单/成交单统计汇总异常", e.getMessage());
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>结束项目方委托单/成交单统计汇总<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
}
