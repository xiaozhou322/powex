package com.gt.quartz;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.PprojectProfitTypeEnum;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pdomain;
import com.gt.entity.Pprofitlogs;
import com.gt.entity.PuserProfitlogs;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPdomainService;
import com.gt.service.front.FrontPstatisticsDataService;
import com.gt.service.front.FrontPuserProfitlogsService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Utils;


/**
 * 项目方收益记录统计定时任务
 * @author zhouyong
 *
 */
public class AutoPprofitStatistic {
	private static final Logger log = LoggerFactory.getLogger(AutoPprofitStatistic.class);
	
	
	DecimalFormat df = new DecimalFormat("0.000000");
	
	@Autowired
	private AdminService adminService ;
	@Autowired
	private TradeMappingService tradeMappingService ;
	@Autowired
	private FrontPstatisticsDataService pstatisticsDataService ;
	@Autowired
	private FrontPdomainService pdomainService ;
	@Autowired
	private FrontPuserProfitlogsService frontPuserProfitlogsService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontCacheService frontCacheService;
	
	
	
	
	public void work() {
		synchronized (this) {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行项目方收益记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
			try{
				String yesterdayStr = Utils.getYesterday();
				//查询所有项目方域名信息
				String filter = " where auditStatus = "+ AuditStatusEnum.auditPass;
				List<Pdomain> pdomainList = pdomainService.list(0, 0, filter, false);
				if(null != pdomainList && pdomainList.size() > 0) {
					//保存项目方社区买入/卖出返佣
					this.saveBuySellCommission(pdomainList, yesterdayStr);
					
					//保存项目方手续费收益 
					this.saveBuySellFees(pdomainList, yesterdayStr);
				}
				
				//计算用户挖矿奖励
				this.saveUserProfitlogs(yesterdayStr);
				
			}catch (Exception e) {
				e.printStackTrace() ;
				log.error("项目方收益记录统计异常", e.getMessage());
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>结束项目方收益记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
	
	
	/**
	 * 计算项目方社区买入/卖出返佣
	 * @param pdomainList
	 * @param yesterdayStr
	 */
	private void saveBuySellCommission(List<Pdomain> pdomainList, String yesterdayStr) {
		try {
			for (int i = 0; i < pdomainList.size(); i++) {
				Pdomain pdomain = pdomainList.get(i);
				//根据域名id查询所有需要返佣的交易市场和买入手续费
				String sqlfilter = "select ftrademapping, sum(fcommissionProfit) from fentrust"
						+ "  where fsource=" + pdomain.getProjectId().getFid()
						+ "  and fEntrustType = "+ EntrustTypeEnum.BUY
						+"   and fStatus = "+ EntrustStatusEnum.AllDeal
						+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' "
						+ "  and ftrademapping not in "
						+ "  (select fid from ftrademapping where fprojectId= "+pdomain.getProjectId().getFid()+") "
					    + "  group by ftrademapping ";
				
				Map<Integer, Object> buyMap = adminService.getSQLValue1(sqlfilter);
				
				
				//根据域名id查询所有需要返佣的交易市场和卖出手续费
				sqlfilter = "select ftrademapping, sum(fcommissionProfit) from fentrust"
						+ "  where fsource=" + pdomain.getProjectId().getFid()
						+ "  and fEntrustType = "+ EntrustTypeEnum.SELL
						+"   and fStatus = "+ EntrustStatusEnum.AllDeal
						+"   and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' "
						+ "  and ftrademapping not in "
						+ "  (select fid from ftrademapping where fprojectId= "+pdomain.getProjectId().getFid()+") "
					    + "  group by ftrademapping ";
				
				Map<Integer, Object> sellMap = adminService.getSQLValue1(sqlfilter);
				
				for (Entry<Integer, Object> m : buyMap.entrySet()) {
					Ftrademapping trademapping = tradeMappingService.findById(m.getKey());
					
					//买入返佣
					BigDecimal buyCommission = new BigDecimal(df.format(m.getValue()));
					//保存数据
					Pprofitlogs buyProfitlogs = new Pprofitlogs();
					buyProfitlogs.setProjectId(pdomain.getProjectId());
					buyProfitlogs.setStatisticalDate(yesterdayStr);
					buyProfitlogs.setTrademappingId(trademapping);
					buyProfitlogs.setCointypeId(trademapping.getFvirtualcointypeByFvirtualcointype2());
					buyProfitlogs.setAmount(buyCommission);
					buyProfitlogs.setProfitType(PprojectProfitTypeEnum.BUY_COMMISSION);  //收益类型  1：买入返佣  2：卖出返佣  3：买入手续费 4：卖出手续费
					buyProfitlogs.setStatus(0);  //结算状态  0：未结算  1：已结算
					buyProfitlogs.setCreateTime(Utils.getTimestamp());
					buyProfitlogs.setUpdateTime(Utils.getTimestamp());
					
					pstatisticsDataService.savePprofitlogs(buyProfitlogs);
				}
				
				for (Entry<Integer, Object> m : sellMap.entrySet()) {
					Ftrademapping trademapping = tradeMappingService.findById(m.getKey());
					
					//卖出返佣
					BigDecimal sellCommission = new BigDecimal(df.format(m.getValue()));
					//保存数据
					Pprofitlogs sellProfitlogs = new Pprofitlogs();
					sellProfitlogs.setProjectId(pdomain.getProjectId());
					sellProfitlogs.setStatisticalDate(yesterdayStr);
					sellProfitlogs.setTrademappingId(trademapping);
					sellProfitlogs.setCointypeId(trademapping.getFvirtualcointypeByFvirtualcointype1());
					sellProfitlogs.setAmount(sellCommission);
					sellProfitlogs.setProfitType(PprojectProfitTypeEnum.SELL_COMMISSION);  //收益类型  1：买入返佣  2：卖出返佣  3：买入手续费 4：卖出手续费
					sellProfitlogs.setStatus(0);  //结算状态  0：未结算  1：已结算
					sellProfitlogs.setCreateTime(Utils.getTimestamp());
					sellProfitlogs.setUpdateTime(Utils.getTimestamp());
					
					pstatisticsDataService.savePprofitlogs(sellProfitlogs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 计算项目方的手续费收益
	 * @param pdomainList
	 * @param yesterdayStr
	 */
	private void saveBuySellFees(List<Pdomain> pdomainList, String yesterdayStr) {
		try {
			for (int i = 0; i < pdomainList.size(); i++) {
				Pdomain pdomain = pdomainList.get(i);
				//根据域名id查询所有自己专区和平台的交易市场和买入手续费
				String sqlfilter = "select ftrademapping, sum(ffeesProfit) from fentrust"
						+ "  where fEntrustType = "+ EntrustTypeEnum.BUY
						+"   and fStatus = "+ EntrustStatusEnum.AllDeal
						+"   and  DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' "
						+ "  and ftrademapping in "
						+ "  (select fid from ftrademapping where fprojectId= "+pdomain.getProjectId().getFid()+") "
						+ "  group by ftrademapping ";
				
				Map<Integer, Object> buyMap = adminService.getSQLValue1(sqlfilter);
				
				
				//根据域名id查询所有自己专区和平台的交易市场和买入手续费
				sqlfilter = "select ftrademapping, sum(ffeesProfit) from fentrust"
						+ "  where fEntrustType = "+ EntrustTypeEnum.SELL
						+"   and fStatus = "+ EntrustStatusEnum.AllDeal
						+"   and  DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' "
						+ "  and ftrademapping in "
						+ "  (select fid from ftrademapping where fprojectId= "+pdomain.getProjectId().getFid()+") "
						+ "  group by ftrademapping ";
				
				Map<Integer, Object> sellMap = adminService.getSQLValue1(sqlfilter);
				
				
				for (Entry<Integer, Object> m : buyMap.entrySet()) {
					Ftrademapping trademapping = tradeMappingService.findById(m.getKey());
					
					//买入手续费
					BigDecimal buyFees = new BigDecimal(df.format(m.getValue()));
					//保存数据
					Pprofitlogs buyProfitlogs = new Pprofitlogs();
					buyProfitlogs.setProjectId(pdomain.getProjectId());
					buyProfitlogs.setStatisticalDate(yesterdayStr);
					buyProfitlogs.setTrademappingId(trademapping);
					buyProfitlogs.setCointypeId(trademapping.getFvirtualcointypeByFvirtualcointype2());
					buyProfitlogs.setAmount(buyFees);
					buyProfitlogs.setProfitType(PprojectProfitTypeEnum.BUY_FEES);  //收益类型  1：买入返佣  2：卖出返佣  3：买入手续费 4：卖出手续费
					buyProfitlogs.setStatus(0);  //结算状态  0：未结算  1：已结算
					buyProfitlogs.setCreateTime(Utils.getTimestamp());
					buyProfitlogs.setUpdateTime(Utils.getTimestamp());
					
					pstatisticsDataService.savePprofitlogs(buyProfitlogs);
				}
				
				for (Entry<Integer, Object> m : sellMap.entrySet()) {
					Ftrademapping trademapping = tradeMappingService.findById(m.getKey());
					
					//卖出手续费
					BigDecimal sellFees = new BigDecimal(df.format(m.getValue()));
					
					//保存数据
					Pprofitlogs sellProfitlogs = new Pprofitlogs();
					sellProfitlogs.setProjectId(pdomain.getProjectId());
					sellProfitlogs.setStatisticalDate(yesterdayStr);
					sellProfitlogs.setTrademappingId(trademapping);
					sellProfitlogs.setCointypeId(trademapping.getFvirtualcointypeByFvirtualcointype1());
					sellProfitlogs.setAmount(sellFees);
					sellProfitlogs.setProfitType(PprojectProfitTypeEnum.SELL_FEES);  //收益类型  1：买入返佣  2：卖出返佣  3：买入手续费 4：卖出手续费
					sellProfitlogs.setStatus(0);  //结算状态  0：未结算  1：已结算
					sellProfitlogs.setCreateTime(Utils.getTimestamp());
					sellProfitlogs.setUpdateTime(Utils.getTimestamp());
					
					pstatisticsDataService.savePprofitlogs(sellProfitlogs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 计算用户挖矿奖励
	 * @param pdomainList
	 * @param yesterdayStr
	 */
	private void saveUserProfitlogs(String yesterdayStr) {
		try {
			Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapService.get("tradeMappings");
			//获取配置的奖励币种
			String[] tradeRewardCoin = frontConstantMapService.getString("introlSendCoin").split("#");
			int rewardCoinId = 0;
			if(null != tradeRewardCoin) {
				rewardCoinId = Integer.parseInt(tradeRewardCoin[0]);
			}
			//奖励币种
			Fvirtualcointype rewardCointype = frontVirtualCoinService.findById(rewardCoinId);
			
			//买入挖矿奖励
			List<Map<String, Object>> buyMapList = frontPuserProfitlogsService.getUserBuyProfitlogs(yesterdayStr);
			for (Map<String, Object> map : buyMapList) {
				Integer userId = (Integer)map.get("userId");
				Integer tradeMappingId = (Integer)map.get("tradeMappingId");
				Integer coinTypeId = (Integer)map.get("coinTypeId");
				String  amount = map.get("amount").toString();
				
				Fuser fuser = frontUserService.findById(userId);
				Ftrademapping trademapping = tradeMappingService.findById(tradeMappingId);
				Fvirtualcointype fvirtualcointype = frontVirtualCoinService.findById(coinTypeId);
				PuserProfitlogs userProfitlogs = new PuserProfitlogs();
				userProfitlogs.setUser(fuser);
				userProfitlogs.setTrademappingId(trademapping);
				userProfitlogs.setCointype(fvirtualcointype);
				userProfitlogs.setAmount(new BigDecimal(amount));
				userProfitlogs.setStatisticalDate(yesterdayStr);
				userProfitlogs.setCreateTime(Utils.getTimestamp());
				userProfitlogs.setUpdateTime(Utils.getTimestamp());
				userProfitlogs.setStatus(0);   //结算状态  0：未结算  1：已结算
				userProfitlogs.setFbCointype(trademapping.getFvirtualcointypeByFvirtualcointype1());
				
				//计算美元估值
				userProfitlogs = calcUsdtValuation(userProfitlogs, tradeMappings, trademapping, rewardCointype);
				
				frontPuserProfitlogsService.save(userProfitlogs);
	        }
			
			//卖出挖矿奖励
			List<Map<String, Object>> sellMapList = frontPuserProfitlogsService.getUserSellProfitlogs(yesterdayStr);
			for (Map<String, Object> map : sellMapList) {
				Integer userId = (Integer)map.get("userId");
				Integer tradeMappingId = (Integer)map.get("tradeMappingId");
				Integer coinTypeId = (Integer)map.get("coinTypeId");
				String  amount = map.get("amount").toString();
				
				Fuser fuser = frontUserService.findById(userId);
				Ftrademapping trademapping = tradeMappingService.findById(tradeMappingId);
				Fvirtualcointype fvirtualcointype = frontVirtualCoinService.findById(coinTypeId);
				PuserProfitlogs userProfitlogs = new PuserProfitlogs();
				userProfitlogs.setUser(fuser);
				userProfitlogs.setTrademappingId(trademapping);
				userProfitlogs.setCointype(fvirtualcointype);
				userProfitlogs.setAmount(new BigDecimal(amount));
				userProfitlogs.setStatisticalDate(yesterdayStr);
				userProfitlogs.setCreateTime(Utils.getTimestamp());
				userProfitlogs.setUpdateTime(Utils.getTimestamp());
				userProfitlogs.setStatus(0);   //结算状态  0：未结算  1：已结算
				userProfitlogs.setFbCointype(trademapping.getFvirtualcointypeByFvirtualcointype1());
				
				//计算美元估值
				userProfitlogs = calcUsdtValuation(userProfitlogs, tradeMappings, trademapping, rewardCointype);
				
				frontPuserProfitlogsService.save(userProfitlogs);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//计算美元估值
	private PuserProfitlogs calcUsdtValuation(PuserProfitlogs userProfitlogs,Map<Integer,Integer> tradeMappings,Ftrademapping trademapping, Fvirtualcointype rewardCointype) {
		List<Fvirtualcointype> coinTypeList = frontVirtualCoinService.findByProperty("fShortName", "USDT");
		Fvirtualcointype usdtCoinType = null;
		if(null != coinTypeList && coinTypeList.size() > 0) {
			usdtCoinType = coinTypeList.get(0);
		}
		//判断法币是否是USDT
		if(usdtCoinType.getFid() == userProfitlogs.getFbCointype().getFid()) {
			userProfitlogs.setUsdtValuation(userProfitlogs.getAmount());
		} else {
			if(tradeMappings!=null&&tradeMappings.containsKey(trademapping.getFvirtualcointypeByFvirtualcointype2().getFid())){
				double curPrice = Utils.getDouble(this.frontCacheService.getLatestDealPrize(trademapping.getFid()), trademapping.getFcount1());
				
				//查询法币兑USDT的交易市场
				String filter = " where fvirtualcointypeByFvirtualcointype1.fid="+usdtCoinType.getFid()
							   +" and fvirtualcointypeByFvirtualcointype2.fid="+userProfitlogs.getFbCointype().getFid();
				List<Ftrademapping> fbUsdtTrademappings = tradeMappingService.list(0, 0, filter, false);
				if(null != fbUsdtTrademappings && fbUsdtTrademappings.size() > 0) {
					Ftrademapping fbUsdtMapping = fbUsdtTrademappings.get(0);
					double fbUsdtPrice = Utils.getDouble(this.frontCacheService.getLatestDealPrize(fbUsdtMapping.getFid()), fbUsdtMapping.getFcount1());
					String usdtValuation = df.format(userProfitlogs.getAmount().
							multiply(new BigDecimal(curPrice).multiply(new BigDecimal(fbUsdtPrice))));
					userProfitlogs.setUsdtValuation(new BigDecimal(usdtValuation));
				}
				
			}
		}
		
		userProfitlogs.setRewardAmount(userProfitlogs.getUsdtValuation());
		userProfitlogs.setRewardCointype(rewardCointype);
		
		return userProfitlogs;
	}
}
