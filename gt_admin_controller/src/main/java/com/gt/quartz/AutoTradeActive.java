package com.gt.quartz;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.ActiveStatusEnum;
import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.entity.FactiveCoinLogs;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pproduct;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPproductService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;


/**
 * 用户币种兑换交易激活定时器
 * @author zhouyong
 *
 */
public class AutoTradeActive {
	private static final Logger log = LoggerFactory.getLogger(AutoTradeActive.class);
	
	
	DecimalFormat df = new DecimalFormat("0.000000");
	
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FvirtualWalletService virtualWalletService;	
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontPproductService pproductService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private TradeMappingService tradeMappingService ;
	@Autowired
	private FrontCacheService frontCacheService;
	
	
	public void work() {
		synchronized (this) {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>开始用户币种兑换交易解锁定时任务<<<<<<<<<<<<<<<<<<<<<<<<<<");
			try{
				String yesterdayStr = Utils.getYesterday();
				//TODO 获取所有权证产品
				String filter = " where auditStatus = " + AuditStatusEnum.auditPass;
				
				List<Pproduct> productList = pproductService.list(0, 0, filter, false);
				
				this.saveTradeActiveCoin(productList, yesterdayStr);
				
			}catch (Exception e) {
				e.printStackTrace() ;
				log.error("用户币种兑换交易解锁异常", e.getMessage());
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>结束用户币种兑换交易解锁定时任务<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
	
	
	/**
	 * 计算交易挖矿激活币种
	 * @param systemconfigList
	 * @param yesterdayStr
	 */
	private void saveTradeActiveCoin(List<Pproduct> productList, String yesterdayStr) {
		try {
			//获取平台币种信息
			List<Fvirtualcointype> coinTypeList = frontVirtualCoinService.findByProperty("fShortName", "POW");
			Fvirtualcointype powCoinType = null;
			if(null != coinTypeList && coinTypeList.size() > 0) {
				powCoinType = coinTypeList.get(0);
			}
			
			//遍历产品
			for (int i = 0; i < productList.size(); i++) {
				Pproduct pproduct = productList.get(i);
				
				//产品对应的币种
				Fvirtualcointype cointype = pproduct.getCoinType();
				//产品的兑换币种
				Fvirtualcointype convertCointype = pproduct.getConvertCointype();
				
				//获取买入交易量激活比例 配置
				double tradeActiveRatio = Double.valueOf(frontConstantMapService.getString("tradeActiveRatio"));
				//获取用户锁仓币激活比例 配置
				double lockedActiveRatio = Double.valueOf(frontConstantMapService.getString("lockedActiveRatio"));
				
				//统计查询所有用户前一日购买的权证产品的交易量
				String sqlfilter = "select b.FUs_fId, sum(a.fCount) from fentrustlog a "
						+ "  left join fentrust b on a.FEn_fId = b.fid "
						+ "  left join ftrademapping c on a.ftrademapping = c.fid "
						+ "  where c.fvirtualcointype2 = " + cointype.getFid()
						+ "  and a.fEntrustType = "+ EntrustTypeEnum.BUY
						+ "  and DATE_FORMAT(a.fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' "
						+ "  group by b.FUs_fId ";
				
				Map<Integer, Object> userTradeAmountMap = adminService.getSQLValue1(sqlfilter);
				
				//遍历用户
				for (Integer userId : userTradeAmountMap.keySet()) {
					
					Fuser fuser = this.frontUserService.findById(userId);
					
					//获取用户前一日买入量
					double usertradeAmount = Double.valueOf(String.valueOf(userTradeAmountMap.get(userId))) ;
					
					//根据比例计算用户可激活币种数量
					double canUserActiveAmount = Utils.getDouble(usertradeAmount*tradeActiveRatio, 4);
					
					//获取用户钱包
					Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(userId, cointype.getFid()) ;
					//计算最大可激活数量
					double maxActiveAmount = Utils.getDouble(fvirtualwallet.getFlocked()*lockedActiveRatio,4);
					
					//用户真正可激活数量
					double activeAmount = (canUserActiveAmount < maxActiveAmount)?canUserActiveAmount:maxActiveAmount;
					
					//计算需要消耗激活币值10%等价值的pow数量
//					double consumePowNum = this.calcPOWAmount(activeAmount, cointype, powCoinType);
					
					//保存币种待激活流水
					FactiveCoinLogs activeCoinLogs = new FactiveCoinLogs();
					activeCoinLogs.setStatisticalDate(yesterdayStr);
					activeCoinLogs.setActiveAmount(activeAmount);
					activeCoinLogs.setFuser(fuser);
					activeCoinLogs.setStatus(ActiveStatusEnum.wait_active);
					activeCoinLogs.setCoinType(cointype);
					activeCoinLogs.setCreateTime(Utils.getTimestamp());
					activeCoinLogs.setUpdateTime(Utils.getTimestamp());
					Integer activeCoinlogsId = virtualWalletService.saveActiveCoinLogs(activeCoinLogs);
					
					//激活操作
					activeCoinLogs = virtualWalletService.findActiveCoinLogsById(activeCoinlogsId);
					this.activeCointype(userId, cointype, powCoinType, activeCoinLogs);
					
				} 
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 计算等价值的POW币数量
	 * @param activeAmount
	 * @param proCointype
	 * @param powCoinType
	 * @return
	 */
	private Double calcPOWAmount(double activeAmount, Fvirtualcointype proCointype, Fvirtualcointype powCoinType) {
		//获取USDT币种
		List<Fvirtualcointype> usdtCoinTypeList = frontVirtualCoinService.findByProperty("fShortName", "USDT");
		Fvirtualcointype usdtCoinType = null;
		if(null != usdtCoinTypeList && usdtCoinTypeList.size() > 0) {
			usdtCoinType = usdtCoinTypeList.get(0);
		}
		
		//查询权证产品币种兑USDT的交易市场
		String filter = " where fvirtualcointypeByFvirtualcointype1.fid="+usdtCoinType.getFid()
					   +" and fvirtualcointypeByFvirtualcointype2.fid="+proCointype.getFid();
		List<Ftrademapping> fbUsdtTrademappings = tradeMappingService.list(0, 0, filter, false);
		double realPrice = 0d;
		if(null != fbUsdtTrademappings && fbUsdtTrademappings.size() > 0) {
			Ftrademapping fbUsdtMapping = fbUsdtTrademappings.get(0);
			//获取市场24小时最低价
			double oneDayLowest = Utils.getDouble(this.frontCacheService.getLowest(fbUsdtMapping.getFid()), fbUsdtMapping.getFcount1());
			//获取市场24小时最高价
			double oneDayHighest = Utils.getDouble(this.frontCacheService.getHighest(fbUsdtMapping.getFid()), fbUsdtMapping.getFcount1());
			//获取市场最新价
			double latestDealPrice = Utils.getDouble(this.frontCacheService.getLatestDealPrize(fbUsdtMapping.getFid()), fbUsdtMapping.getFcount1());
			//计算24小时平均价
			double avgOneDayPrice = (oneDayLowest + oneDayHighest)/2;
			//计算真实价格
			if(0 == avgOneDayPrice) {
				realPrice = latestDealPrice;
			} else {
				realPrice = (latestDealPrice >= avgOneDayPrice)?avgOneDayPrice:latestDealPrice;
			}
		}
		
		//查询POW兑USDT的交易市场
		filter = " where fvirtualcointypeByFvirtualcointype1.fid="+usdtCoinType.getFid()
					   +" and fvirtualcointypeByFvirtualcointype2.fid="+powCoinType.getFid();
		List<Ftrademapping> powUsdtTrademappings = tradeMappingService.list(0, 0, filter, false);
		double powRealPrice = 0d;
		Ftrademapping powUsdtMapping = null;
		if(null != powUsdtTrademappings && powUsdtTrademappings.size() > 0) {
			powUsdtMapping = powUsdtTrademappings.get(0);
			//获取市场24小时最低价
			double powOneDayLowest = Utils.getDouble(this.frontCacheService.getLowest(powUsdtMapping.getFid()), powUsdtMapping.getFcount1());
			//获取市场24小时最高价
			double powOneDayHighest = Utils.getDouble(this.frontCacheService.getHighest(powUsdtMapping.getFid()), powUsdtMapping.getFcount1());
			//获取市场最新价
			double powLatestDealPrice = Utils.getDouble(this.frontCacheService.getLatestDealPrize(powUsdtMapping.getFid()), powUsdtMapping.getFcount1());
			//计算24小时平均价
			double powAvgOneDayPrice = (powOneDayLowest + powOneDayHighest)/2;
			//计算POW真实价格
			if(0 == powAvgOneDayPrice) {
				powRealPrice = powLatestDealPrice;
			} else {
				powRealPrice = (powLatestDealPrice >= powAvgOneDayPrice)?powAvgOneDayPrice:powLatestDealPrice;
			}
		}
		
		double powAmount = Utils.getDouble(realPrice*activeAmount*0.1/powRealPrice, powUsdtMapping.getFcount2());
		
		return powAmount;
	}
	
	
	
	/**
	 * 激活操作
	 * @param userId
	 * @param proCointype
	 * @param powCoinType
	 * @param activeCoinLogs
	 */
	private void activeCointype(Integer userId, Fvirtualcointype proCointype,
			Fvirtualcointype powCoinType, FactiveCoinLogs activeCoinLogs) {
		//获取用户钱包POW余额
		Fvirtualwallet fvirtualwalletPow = this.frontUserService.findVirtualWalletByUser(userId, powCoinType.getFid()) ;
		
		//1.查询前天是否有待激活记录， 若有则优先激活前天的记录，若无则激活昨天的记录
		String filter = " where fuser.fid = " + userId
					   +" and status = " + ActiveStatusEnum.wait_active
					   +" and coinType.fid = " + proCointype.getFid()
					   +" and statisticalDate = " + Utils.getBeforeYesterday();
		List<FactiveCoinLogs> activeCoinLogsList = virtualWalletService.queryActiveCoinLogsList(0, 0, filter, false);
		
		//2.优先激活前天的记录
		if(null != activeCoinLogsList && activeCoinLogsList.size() > 0) {
			FactiveCoinLogs activeLogs = activeCoinLogsList.get(0);
			//更新激活流水表和钱包表
			this.updateActiveLogsAndWallet(activeLogs, userId, proCointype, powCoinType, 1);
		}
		//3.激活昨天的记录
		this.updateActiveLogsAndWallet(activeCoinLogs, userId, proCointype, powCoinType, 2);
	}
	
	
	/**
	 * 更新激活流水表和钱包表
	 * @param activeLogs
	 * @param userId
	 * @param proCointype
	 * @param powCoinType
	 * @param type     1：前天的记录     2：昨天的记录
	 */
	private void updateActiveLogsAndWallet(FactiveCoinLogs activeLogs,Integer userId,
			Fvirtualcointype proCointype,Fvirtualcointype powCoinType, Integer type) {
		
		//1.获取用户钱包POW余额
		Fvirtualwallet fvirtualwalletPow = this.frontUserService.findVirtualWalletByUser(userId, powCoinType.getFid()) ;
		
		//2.判断钱包POW余额是否充足， 若充足则激活，不足则置为放弃激活
		double consumePowNum = this.calcPOWAmount(activeLogs.getActiveAmount(), proCointype, powCoinType);
		
		if(fvirtualwalletPow.getFtotal() >= consumePowNum) {
			activeLogs.setStatus(ActiveStatusEnum.has_active);
			activeLogs.setUpdateTime(Utils.getTimestamp());
			
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(userId, proCointype.getFid()) ;
			//TODO 修改钱包记录和币种激活流水
			virtualWalletService.updateTradeActiveCoin(activeLogs, fvirtualwallet, fvirtualwalletPow, consumePowNum);
			
		} else {
			if(1 == type) {   //若钱包POW币不足且为前天的记录，则放弃激活
				activeLogs.setStatus(ActiveStatusEnum.no_active);    //放弃激活
				activeLogs.setUpdateTime(Utils.getTimestamp());
				virtualWalletService.updateActiveCoinLogs(activeLogs);
			}
		}
	}
	
}
