package com.gt.service.front;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.dao.FentrustDAO;
import com.gt.dao.FentrustlogDAO;
import com.gt.dao.FentrustplanDAO;
import com.gt.dao.FfeesDAO;
import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FtrademappingDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.UtilsDAO;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Fentrustplan;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.DataOperationUtil;
import com.gt.util.Utils;

@Service("frontTradeService")
public class FrontTradeServiceImpl implements FrontTradeService {
	private static final Logger log = LoggerFactory
			.getLogger(FrontTradeServiceImpl.class);

	@Autowired
	private FentrustDAO fentrustDAO;
	@Autowired
	private FentrustlogDAO fentrustlogDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO;
	@Autowired
	private FentrustplanDAO fentrustplanDAO;
	@Autowired
	private FfeesDAO ffeesDAO;
	@Autowired
	private FintrolinfoDAO fintrolinfoDAO;
	@Autowired
	private FtrademappingDAO ftrademappingDAO ;
	@Autowired
	private UtilsDAO utilsDAO ;
	@Autowired
	private FuserDAO fuserDAO ;
//	@Autowired
//    private RabbitTemplate rabbitTemplate;
	@Autowired
	private FrontEntrustChangeService frontEntrustChangeService;

	//手续费率
	private Map<String, Double> rates = new HashMap<String, Double>() ;
	
	public void putRates(String key,double value){
		synchronized (this.rates) {
			this.rates.put(key, value) ;
		}
	}
	public double getRates(int vid,boolean isbuy,int level){
		String key = vid+"_"+(isbuy?"buy":"sell")+"_"+level ;
		synchronized (this.rates) {
			return this.rates.get(key) ;
		}
	}
	
		
	public Fentrust findFentrustById(int id) {
		return this.fentrustDAO.findById(id);
	}

	public List<Fentrustlog> findFentrustLogByFentrust(Fentrust fentrust) {
		return this.fentrustlogDAO.findByProperty("fentrust.fid",
				fentrust.getFid());
	}

	// 最新成交记录
	public List<Fentrust> findLatestSuccessDeal(int coinTypeId,
			int fentrustType, int count) {
		return this.fentrustDAO.findLatestSuccessDeal(coinTypeId, fentrustType,
				count);
	}

	public List<Fentrust> findAllGoingFentrust(int coinTypeId,
			int fentrustType, boolean isLimit) {
		return this.fentrustDAO.findAllGoingFentrust(coinTypeId, fentrustType,
				isLimit);
	}

	// 获得24小时内的成交记录
	public List<Fentrustlog> findLatestSuccessDeal24(int coinTypeId, int hour) {
		List<Fentrustlog> list = this.fentrustlogDAO.findLatestSuccessDeal24(coinTypeId, 24);
		if(list == null || list.size() == 0){
			return null;
		}
		return list;
	}

	public Fentrustlog findLatestDeal(int coinTypeId) {
		Fentrustlog fentrust = this.fentrustDAO.findLatestDeal(coinTypeId);
		if(fentrust == null) return null;
		return fentrust;
	}

	// 委托记录
	public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int first_result, int max_result, String order,
			int entrust_status[], Date beginDate, Date endDate){
		List<Fentrust> list = this.fentrustDAO.getFentrustHistory(fuid,
				fvirtualCoinTypeId, entrust_type, first_result, max_result,
				order, entrust_status, beginDate, endDate);
		return list;
	}

	// 计划委托
	public List<Fentrustplan> findEntrustPlan(int type, int status[]) {
		List<Fentrustplan> list = this.fentrustplanDAO.findEntrustPlan(type,
				status);

		return list;
	}

	// 委托买入
	public Fentrust updateEntrustBuy(int tradeMappingID, double tradeAmount,
			double tradeCnyPrice, Fuser fuser, boolean fisLimit, Integer fsourceId,
			double commissionRate, double feesRate) {

		try {
			Ftrademapping mapping = this.ftrademappingDAO.findById(tradeMappingID);

			double ffeeRate = this.ffeesDAO.findFfee(tradeMappingID,
					fuser.getFscore().getFlevel()).getFbuyfee();
			double ffee = 0F;
			//System.out.println("service start");
			// 买入总价格
			double totalTradePrice = 0F;
			if (fisLimit) {
				totalTradePrice = tradeCnyPrice;
				ffee = 0 ;
			} else {
				//总手续费人民币
				totalTradePrice = tradeAmount * tradeCnyPrice;
				ffee = tradeAmount * ffeeRate;
			}
			Fvirtualwallet fwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype1().getFid());
			if(fwallet.getFtotal() < (totalTradePrice)){
				throw new RuntimeException();
			}
			//计算社群返佣
			double commissionProfit = Utils.getDouble(ffee*commissionRate ,6) ;
			//计算项目方手续费收益
			double feesProfit = DataOperationUtil.mul(DataOperationUtil.mul(ffee, feesRate), (1-commissionRate));
			log.info(">>>>>>ffee:<<<<<"+ffee);
			log.info(">>>>>>commissionRate:<<<<<"+commissionRate);
			log.info(">>>>>>feesRate:<<<<<"+feesRate);
			log.info(">>>>>>feesProfit:<<<<<"+feesProfit);
			feesProfit = Utils.getDouble(feesProfit ,6) ;
			log.info(">>>>>>feesProfit:<<<<<"+feesProfit);
			log.info(">>>>>>commissionProfit:<<<<<"+commissionProfit);
			
			/*fwallet.setFtotal(fwallet.getFtotal() - totalTradePrice);
			fwallet.setFfrozen(fwallet.getFfrozen()
					+ totalTradePrice);
			fwallet.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualwalletDAO.attachDirty(fwallet);*/

			
			//System.out.println("337:Fentrust");
			Fentrust fentrust = new Fentrust();
			
			if (fisLimit) {
				fentrust.setFcount(0F);
				fentrust.setFleftCount(0F);
				fentrust.setFprize(0);
			} else {
				fentrust.setFcount(tradeAmount);
				fentrust.setFleftCount(tradeAmount);
				fentrust.setFprize(tradeCnyPrice);
			}
			//System.out.println("349:Fentrust setting");
			fentrust.setFamount(totalTradePrice);
			fentrust.setFfees(ffee);
			fentrust.setFleftfees(ffee);
			fentrust.setFcreateTime(Utils.getTimestamp());
			fentrust.setFentrustType(EntrustTypeEnum.BUY);
			fentrust.setFisLimit(fisLimit);
			fentrust.setFlastUpdatTime(Utils.getTimestamp());
			fentrust.setFstatus(EntrustStatusEnum.Going);
			fentrust.setFsuccessAmount(0F);
			fentrust.setFhasSubscription(false);
			fentrust.setFuser(fuser);
			fentrust.setFtrademapping(mapping) ;
			fentrust.setFsource(fsourceId);
			fentrust.setFcommissionProfit(commissionProfit);
			fentrust.setFfeesProfit(feesProfit);
			this.fentrustDAO.save(fentrust);
			//System.out.println("363:Fentrust save");
			String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=? AND ftotal>=?" ;
			int count = this.utilsDAO.executeHQL(hql, - totalTradePrice,+ totalTradePrice,fuser.getFid(),mapping.getFvirtualcointypeByFvirtualcointype1().getFid(),totalTradePrice) ;
			if(count<=0){
				throw new RuntimeException() ;
			}
			//加入MQ买单队列
			/*fentrust = this.findFentrustById(fentrust.getFid()) ;
			String symbolAndIdStr = String.valueOf(tradeMappingID) + "," + String.valueOf(fentrust.getFid());
			if (fisLimit) {
				frontEntrustChangeService.addEntrustLimitBuyMap(tradeMappingID, fentrust);
//				rabbitTemplate.convertAndSend("entrust.limit.buy.add", symbolAndIdStr);
			} else {
				frontEntrustChangeService.addEntrustBuyMap(tradeMappingID, fentrust);
//				rabbitTemplate.convertAndSend("entrust.buy.add", symbolAndIdStr);
			}*/
			
			return fentrust ;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new RuntimeException();
		}

	}

	// 委托卖出
	public Fentrust updateEntrustSell(int tradeMappingID, double tradeAmount,
			double tradeCnyPrice, Fuser fuser, boolean fisLimit, Integer fsourceId,
			double commissionRate, double feesRate) {

		try {
			Ftrademapping mapping = this.ftrademappingDAO.findById(tradeMappingID);
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype2().getFid());
			if (fvirtualwallet.getFtotal() < tradeAmount) {
				throw new RuntimeException();
			}

			/*fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() - tradeAmount);
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() + tradeAmount);
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);*/
			
			

			double ffeeRate = this.ffeesDAO.findFfee(tradeMappingID,
					fuser.getFscore().getFlevel()).getFfee();
			Fentrust fentrust = new Fentrust();
			
			//总手续费人民币
			double ffee = 0 ;
			if (fisLimit) {
				fentrust.setFamount(0F);
				fentrust.setFfees(ffee);
				fentrust.setFleftfees(ffee);
			} else {
				ffee = tradeAmount*tradeCnyPrice * ffeeRate;
				fentrust.setFamount(tradeAmount* tradeCnyPrice);
				fentrust.setFfees(ffee);
				fentrust.setFleftfees(ffee);
			}
			
			//计算社群返佣
			double commissionProfit = Utils.getDouble(ffee*commissionRate ,6) ;
			//计算项目方手续费收益
			double feesProfit = DataOperationUtil.mul(DataOperationUtil.mul(ffee, feesRate), (1-commissionRate));
			log.info(">>>>>>ffee:<<<<<"+ffee);
			log.info(">>>>>>commissionRate:<<<<<"+commissionRate);
			log.info(">>>>>>feesRate:<<<<<"+feesRate);
			log.info(">>>>>>feesProfit:<<<<<"+feesProfit);
			feesProfit = Utils.getDouble(feesProfit ,6) ;
			log.info(">>>>>>feesProfit:<<<<<"+feesProfit);
			log.info(">>>>>>commissionProfit:<<<<<"+commissionProfit);
			
			fentrust.setFcount(tradeAmount);
			fentrust.setFleftCount(tradeAmount);
			fentrust.setFcreateTime(Utils.getTimestamp());
			fentrust.setFentrustType(EntrustTypeEnum.SELL);
			fentrust.setFisLimit(fisLimit);
			fentrust.setFlastUpdatTime(Utils.getTimestamp());
			fentrust.setFprize(tradeCnyPrice);
			fentrust.setFstatus(EntrustStatusEnum.Going);
			fentrust.setFsuccessAmount(0F);
			fentrust.setFuser(fuser);
			fentrust.setFhasSubscription(false);
			fentrust.setFtrademapping(mapping) ;
			fentrust.setFsource(fsourceId);
			fentrust.setFcommissionProfit(commissionProfit);
			fentrust.setFfeesProfit(feesProfit);
			this.fentrustDAO.save(fentrust);

			String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=? AND ftotal>=?" ;
			int count = this.utilsDAO.executeHQL(hql, - tradeAmount,+ tradeAmount,fuser.getFid(),mapping.getFvirtualcointypeByFvirtualcointype2().getFid(),tradeAmount) ;
			if(count<=0){
				throw new RuntimeException() ;
			}
			
			//加入MQ卖单队列
			/*fentrust = this.findFentrustById(fentrust.getFid()) ;
			String symbolAndIdStr = String.valueOf(tradeMappingID) + "," + String.valueOf(fentrust.getFid());
			if (fisLimit) {
				frontEntrustChangeService.addEntrustLimitSellMap(tradeMappingID, fentrust);
//				rabbitTemplate.convertAndSend("entrust.limit.sell.add", symbolAndIdStr);
			} else {
				frontEntrustChangeService.addEntrustSellMap(tradeMappingID, fentrust);
//				rabbitTemplate.convertAndSend("entrust.sell.add", symbolAndIdStr);
			}*/
			
			return fentrust ;
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	// 委托记录
	public List<Fentrust> findFentrustHistory(int firstResult, int maxResults,
			String filter, boolean isFY) {
		List<Fentrust> list = this.fentrustDAO.list(firstResult, maxResults, filter, isFY);
		return list;
	}
	
	// 委托记录
	public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int first_result, int max_result, String order,
			int entrust_status[]) {
		List<Fentrust> list = this.fentrustDAO.getFentrustHistory(fuid,
				fvirtualCoinTypeId, entrust_type, first_result, max_result,
				order, entrust_status);
		for (Fentrust fentrust : list) {
			Ftrademapping ftrademapping = fentrust.getFtrademapping() ;
			ftrademapping.getFcount1();
			ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname() ;
			ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname() ;
		}
		return list;
	}

	public int findFentrustHistoryCount(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int entrust_status[]) {
		return this.fentrustDAO.getFentrustHistoryCount(fuid,
				fvirtualCoinTypeId, entrust_type, entrust_status);
	}

	public List<Fentrustplan> findFentrustplan(int fuser, int fvirtualcointype,
			int[] fstatus, int firtResult, int maxResult, String order) {
		return this.fentrustplanDAO.findFentrustplan(fuser, fvirtualcointype,
				fstatus, firtResult, maxResult, order);
	}

	public Fentrustplan findFentrustplanById(int id) {
		return this.fentrustplanDAO.findById(id);
	}

	public long findFentrustplanCount(int fuser, int fvirtualcointype,
			int[] fstatus) {
		return this.fentrustplanDAO.findFentrustplanCount(fuser,
				fvirtualcointype, fstatus);
	}



	public void updateCancelFentrust(Fentrust fentrust, Fuser fuser) {

		try {
			fentrust.setFlastUpdatTime(Utils.getTimestamp());
			fentrust.setFstatus(EntrustStatusEnum.Cancel);
			this.fentrustDAO.attachDirty(fentrust);

			if (fentrust.getFentrustType() == EntrustTypeEnum.BUY) {
				// 买
				Fvirtualwallet fwallet =this.fvirtualwalletDAO
						.findVirtualWallet(fuser.getFid(), fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getFid());
				double leftAmount = fentrust.getFamount()
						- fentrust.getFsuccessAmount();

				fwallet.setFtotal(fwallet.getFtotal() + leftAmount);
				fwallet.setFfrozen(fwallet.getFfrozen() - leftAmount);
				fwallet.setFlastUpdateTime(Utils.getTimestamp());
				this.fvirtualwalletDAO.attachDirty(fwallet);

				//加入MQ取消买单队列
				/*fentrust = this.findFentrustById(fentrust.getFid()) ;
				String symbolAndIdStr = String.valueOf(fentrust.getFtrademapping().getFid()) + "," + String.valueOf(fentrust.getFid());
				if (fentrust.isFisLimit()) {
					frontEntrustChangeService.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//					rabbitTemplate.convertAndSend("entrust.limit.buy.remove", symbolAndIdStr);
				} else {
					frontEntrustChangeService.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//					rabbitTemplate.convertAndSend("entrust.buy.remove", symbolAndIdStr);
				}*/
			} else {
				// 卖
				Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO
						.findVirtualWallet(fuser.getFid(), fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFid());
				double leftCount = fentrust.getFleftCount();
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() + leftCount);
				fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() - leftCount);
				fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet);

				//加入MQ取消卖单队列
				/*fentrust = this.findFentrustById(fentrust.getFid()) ;
				String symbolAndIdStr = String.valueOf(fentrust.getFtrademapping().getFid()) + "," + String.valueOf(fentrust.getFid());
				if (fentrust.isFisLimit()) {
					frontEntrustChangeService.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//					rabbitTemplate.convertAndSend("entrust.limit.sell.remove", symbolAndIdStr);
				} else {
					frontEntrustChangeService.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//					rabbitTemplate.convertAndSend("entrust.sell.remove", symbolAndIdStr);
				}*/
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public List<Fentrust> findFentrustByParam(int firstResult, int maxResults,
			String filter, boolean isFY) {
		return this.fentrustDAO.findByParam(firstResult, maxResults, filter,
				isFY, Fentrust.class);
	}

	public int findFentrustByParamCount(String filter) {
		return this.fentrustDAO.findByParamCount(filter, Fentrust.class);
	}
	
	public void updateFeeLog(Fentrust entrust,Fvirtualwallet fvirtualwallet) {
		try {
			this.fentrustDAO.attachDirty(entrust);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	
	public List<Fentrust> findFentrustsByParam(int firstResult, int maxResults, String filter,boolean isFY){
		return this.fentrustDAO.findByParam(firstResult, maxResults, filter, isFY, Fentrust.class) ;
	}
	

	public void updateFentrust(Fentrust fentrust){
		try {
			this.fentrustDAO.attachDirty(fentrust) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCoinFentrust(Fentrust fentrust,List<Fvirtualwallet> fvirtualwallets,List<Fintrolinfo> fintrolinfos){
		try {
			this.fentrustDAO.attachDirty(fentrust) ;
			for (Fintrolinfo fintrolinfo : fintrolinfos) {
				this.fintrolinfoDAO.save(fintrolinfo);
			}
			for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
