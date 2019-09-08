package com.gt.auto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Ffees;
import com.gt.entity.Ftrademapping;
import com.gt.service.TradeDealMakingServiceImpl;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;

public class AutoDealMaking {
	private static final Logger log = LoggerFactory .getLogger(AutoDealMaking.class);
	
	@Autowired
	private RealTimeData realTimeData;
	@Autowired
	private FrontTradeService frontTradeService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private TradeDealMakingServiceImpl tradeDealMakingService;
	
	public void init() {
		//gai
		List<Ffees> ffees = this.utilsService.list(0, 0, "", false, Ffees.class) ;
		for (Ffees fee : ffees) {
			this.tradeDealMakingService.putRates(fee.getFtrademapping().getFid()+"_buy_"+fee.getFlevel(), fee.getFbuyfee()) ;
			this.tradeDealMakingService.putRates(fee.getFtrademapping().getFid()+"_sell_"+fee.getFlevel() ,fee.getFfee()) ;
		}
		
		while(this.realTimeData.getMisInit() == false ){
			try {
				Thread.sleep(10L) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		new Work().run() ;
		for(Integer mid :matchMap.keySet()) {
			System.out.println("交易市场："+mid+" 撮合引擎启动成功");
		}
	}

	private static Map<Integer, Long> matchMap = new HashMap<Integer, Long>();
	
	public void startNewMarket(int id) {
		//如果市场的撮合引擎已经开启，则不再启动新线程处理
		if(matchMap.containsKey(Integer.valueOf(id))) {
			System.out.println("交易市场："+id+" 撮合引擎已经启动，请不要重复启动");
			return;
		}
		
		List<Ftrademapping> alls = AutoDealMaking.this.utilsService.list1(0, 0, "where fid="+id+" and fstatus="+TrademappingStatusEnum.ACTIVE, false,Ftrademapping.class);
		if (alls != null && alls.size() >0) {
			final Ftrademapping ftrademappingnew = alls.get(0);
			List<Ffees> ffees = this.utilsService.list(0, 0, "where ftrademapping.fid="+ftrademappingnew.getFid(), false, Ffees.class) ;
			for (Ffees fee : ffees) {
				this.tradeDealMakingService.putRates(fee.getFtrademapping().getFid()+"_buy_"+fee.getFlevel(), fee.getFbuyfee()) ;
				this.tradeDealMakingService.putRates(fee.getFtrademapping().getFid()+"_sell_"+fee.getFlevel() ,fee.getFfee()) ;
			}
			System.out.println("市场ID："+ftrademappingnew.getFid()+"撮合引擎启动");
			try {
				Thread thread = new Thread(new Runnable() {
					public void run() {
						while(true ){
							try {
								try {
									limitBuyDealMaking(ftrademappingnew) ;
								} catch (Exception e) {
									e.printStackTrace();
								}
								try {
									limitSellDealMaking(ftrademappingnew) ;
								} catch (Exception e) {
									e.printStackTrace();
								}
								try {
									dealMaking(ftrademappingnew);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								try {
									limitBuySellDealMaking(ftrademappingnew);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Thread.sleep(1L) ;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
					}
				}) ;
				matchMap.put(ftrademappingnew.getFid(),Long.valueOf(thread.getId()));
				thread.setPriority(Thread.MAX_PRIORITY) ;
				thread.setName("MATCHENGINE_TP"+ftrademappingnew.getFid());
				thread.start() ;
				
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}
		
		
	}
	
	class Work  {
		public void run() {
				try {
					String filter = "where fstatus="+TrademappingStatusEnum.ACTIVE;
					//gai
					List<Ftrademapping> alls = AutoDealMaking.this.utilsService.list1(0, 0, filter, false,Ftrademapping.class);
					if (alls != null && alls.size() >0) {
						for (final Ftrademapping ftrademapping : alls) {
							System.out.println("市场ID："+ftrademapping.getFid()+"撮合引擎启动");
							try {
								Thread thread = new Thread(new Runnable() {
									public void run() {
										while(true ){
											try {
												try {
													limitBuyDealMaking(ftrademapping) ;
												} catch (Exception e) {
													e.printStackTrace();
												}
												try {
													limitSellDealMaking(ftrademapping) ;
												} catch (Exception e) {
													e.printStackTrace();
												}
												try {
													dealMaking(ftrademapping);
												} catch (Exception e) {
													e.printStackTrace();
												}
												
												try {
													limitBuySellDealMaking(ftrademapping);
												} catch (Exception e) {
													e.printStackTrace();
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
											
											try {
												Thread.sleep(1L) ;
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										}
										
									}
								}) ;
								matchMap.put(ftrademapping.getFid(),Long.valueOf(thread.getId()));
								thread.setPriority(Thread.MAX_PRIORITY) ;
								thread.start() ;
							} catch (Exception e) {
								 e.printStackTrace();
							}
						}
					}

				} catch (Exception e) {
					 e.printStackTrace() ;
				}
				
		}
	}

		private void limitSellDealMaking(Ftrademapping ftrademapping) {
			int id = ftrademapping.getFid() ;
			boolean rehandle = false;

			Fentrust[] sellLimitFentrusts = realTimeData
					.getEntrustLimitSellMap(id,1) ;
			Fentrust[] buyFentrusts = realTimeData.getEntrustBuyMap(id,1) ;

			if (sellLimitFentrusts.length > 0 && buyFentrusts.length > 0) {

				first: for (int i = 0; i < sellLimitFentrusts.length; i++) {
					Fentrust sell = (Fentrust) sellLimitFentrusts[i];

					if (sell.getFstatus() == EntrustStatusEnum.AllDeal
							|| sell.getFstatus() == EntrustStatusEnum.Cancel) {
						realTimeData.removeEntrustLimitSellMap(id, sell);
						continue;
					}

					for (int j = 0; j < buyFentrusts.length; j++) {
						Fentrust buy = (Fentrust) buyFentrusts[j];

						if (buy.getFstatus() == EntrustStatusEnum.AllDeal
								|| buy.getFstatus() == EntrustStatusEnum.Cancel) {
							realTimeData.removeEntrustBuyMap(id, buy);
							continue;
						}

						boolean isbuy =false;
						boolean issell =false;
						if(buy.getFid().intValue() > sell.getFid().intValue()){
							isbuy = true;
						}else if(buy.getFid().intValue() < sell.getFid().intValue()){
							issell = true;
						}else{
							issell = true;
						}

						double sellPrize = buy.getFprize();
						double sellCount = buy.getFleftCount();
						sellCount = sellCount > sell.getFleftCount() ? sell
								.getFleftCount() : sellCount;

						Fentrustlog buyFentrustlog = new Fentrustlog();
						buyFentrustlog.setFamount(sellCount * sellPrize);
						buyFentrustlog.setFcount(sellCount);
						buyFentrustlog.setFcreateTime(Utils.getTimestamp());
						buyFentrustlog.setFprize(sellPrize);
						buyFentrustlog.setIsactive(isbuy);
						buyFentrustlog.setFentrust(buy);
						buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
						buyFentrustlog.setFtrademapping(ftrademapping) ;
						//添加委托单来源
						buyFentrustlog.setFsource(buy.getFsource());

						Fentrustlog sellFentrustlog = new Fentrustlog();
						sellFentrustlog.setFamount(sellCount * sellPrize);
						sellFentrustlog.setFcount(sellCount);
						sellFentrustlog.setFcreateTime(Utils.getTimestamp());
						sellFentrustlog.setFprize(sellPrize);
						sellFentrustlog.setIsactive(issell);
						sellFentrustlog.setFentrust(sell);
						sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
						sellFentrustlog.setFtrademapping(ftrademapping) ;
						//添加委托单来源
						sellFentrustlog.setFsource(sell.getFsource());

						buy = frontTradeService.findFentrustById(buy.getFid());
						sell = frontTradeService.findFentrustById(sell.getFid());
						boolean ret = false;
						try {
							tradeDealMakingService.updateDealMaking(ftrademapping,buy, sell,
									buyFentrustlog, sellFentrustlog, id);
							ret = true;
						} catch (Exception e) {
						}

						if (ret) {
							realTimeData.addEntrustSuccessMap(id,
									sellFentrustlog);
							realTimeData.addEntrustSuccessMap(id,
									buyFentrustlog);
							

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (buy.isFisLimit()) {
									realTimeData.addEntrustLimitBuyMap(id,
											buy);
								} else {
									realTimeData.addEntrustBuyMap(id, buy);
								}
							} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (buy.isFisLimit()) {
									realTimeData.removeEntrustLimitBuyMap(
											id, buy);
								} else {
									realTimeData.removeEntrustBuyMap(id,
											buy);
								}

							}

							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (sell.isFisLimit()) {
									realTimeData.addEntrustLimitSellMap(
											id, sell);
								} else {
									realTimeData.addEntrustSellMap(id,
											sell);
								}
							} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (sell.isFisLimit()) {
									realTimeData
											.removeEntrustLimitSellMap(id, sell);
								} else {
									realTimeData.removeEntrustSellMap(id,
											sell);
								}

							}

							rehandle = true;
							
						} else {
							buy = frontTradeService.findFentrustById(buy
									.getFid());
							sell = frontTradeService.findFentrustById(sell
									.getFid());

							if (buy == null || sell == null) {
								log.error("buy or sell null;");
								continue;
							}

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustBuyMap(id, buy);
							} else {
								realTimeData.removeEntrustBuyMap(id, buy);
							}
							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustLimitSellMap(id,
										sell);
							} else {
								realTimeData.removeEntrustLimitSellMap(id,
										sell);
							}
						}

						rehandle = true;

						break first;
					}
				}

				if (rehandle) {
					limitSellDealMaking(ftrademapping);
				}
			}

		}

		private void limitBuyDealMaking(Ftrademapping ftrademapping) {
			int id = ftrademapping.getFid() ;
			boolean rehandle = false;
			
			Fentrust[] buyLimitFentrusts = realTimeData
					.getEntrustLimitBuyMap(id,1) ;
			Fentrust[] sellFentrusts = realTimeData.getEntrustSellMap(id,1) ;
			if (buyLimitFentrusts.length > 0 && sellFentrusts.length > 0) {

				first: for (int i = 0; i < buyLimitFentrusts.length; i++) {
					Fentrust buy = (Fentrust) buyLimitFentrusts[i];

					if (buy.getFstatus() == EntrustStatusEnum.AllDeal
							|| buy.getFstatus() == EntrustStatusEnum.Cancel) {
						realTimeData.removeEntrustLimitBuyMap(id, buy);
						continue;
					}

					for (int j = 0; j < sellFentrusts.length; j++) {
						Fentrust sell = (Fentrust) sellFentrusts[j];

						if (sell.getFstatus() == EntrustStatusEnum.AllDeal
								|| sell.getFstatus() == EntrustStatusEnum.Cancel) {
							realTimeData.removeEntrustSellMap(id, sell);
							continue;
						}

						boolean isbuy =false;
						boolean issell =false;
						if(buy.getFid().intValue() > sell.getFid().intValue()){
							isbuy = true;
						}else if(buy.getFid().intValue() < sell.getFid().intValue()){
							issell = true;
						}else{
							issell = true;
						}

						double buyPrize = sell.getFprize();
						double buyCount = (buy.getFamount() - buy
								.getFsuccessAmount()) / buyPrize;
						buyCount = buyCount > sell.getFleftCount() ? sell
								.getFleftCount() : buyCount;

						Fentrustlog buyFentrustlog = new Fentrustlog();
						buyFentrustlog.setFamount(buyCount * buyPrize);
						buyFentrustlog.setFcount(buyCount);
						buyFentrustlog.setFcreateTime(Utils.getTimestamp());
						buyFentrustlog.setFprize(buyPrize);
						buyFentrustlog.setIsactive(isbuy);
						buyFentrustlog.setFentrust(buy);
						buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
						buyFentrustlog.setFtrademapping(ftrademapping) ;
						//添加委托单来源
						buyFentrustlog.setFsource(buy.getFsource());

						Fentrustlog sellFentrustlog = new Fentrustlog();
						sellFentrustlog.setFamount(buyCount * buyPrize);
						sellFentrustlog.setFcount(buyCount);
						sellFentrustlog.setFcreateTime(Utils.getTimestamp());
						sellFentrustlog.setFprize(buyPrize);
						sellFentrustlog.setIsactive(issell);
						sellFentrustlog.setFentrust(sell);
						sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
						sellFentrustlog.setFtrademapping(ftrademapping) ;
						//添加委托单来源
						sellFentrustlog.setFsource(sell.getFsource());

						buy = frontTradeService.findFentrustById(buy.getFid());
						sell = frontTradeService.findFentrustById(sell.getFid());
						boolean ret = false;
						try {
							tradeDealMakingService.updateDealMaking(ftrademapping,buy, sell,
									buyFentrustlog, sellFentrustlog, id);
							ret = true;
						} catch (Exception e) {
						}

						if (ret) {
							realTimeData.addEntrustSuccessMap(id,
									sellFentrustlog);
							realTimeData.addEntrustSuccessMap(id,
									buyFentrustlog);
							

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (buy.isFisLimit()) {
									realTimeData.addEntrustLimitBuyMap(id,
											buy);
								} else {
									realTimeData.addEntrustBuyMap(id, buy);
								}
							} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (buy.isFisLimit()) {
									realTimeData.removeEntrustLimitBuyMap(
											id, buy);
								} else {
									realTimeData.removeEntrustBuyMap(id,
											buy);
								}

							}

							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (sell.isFisLimit()) {
									realTimeData.addEntrustLimitSellMap(
											id, sell);
								} else {
									realTimeData.addEntrustSellMap(id,
											sell);
								}
							} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (sell.isFisLimit()) {
									realTimeData
											.removeEntrustLimitSellMap(id, sell);
								} else {
									realTimeData.removeEntrustSellMap(id,
											sell);
								}

							}

							rehandle = true;
							
						} else {
							buy = frontTradeService.findFentrustById(buy
									.getFid());
							sell = frontTradeService.findFentrustById(sell
									.getFid());

							if (buy == null || sell == null) {
								log.error("buy or sell null;");
								continue;
							}

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData
										.addEntrustLimitBuyMap(id, buy);
							} else {
								realTimeData.removeEntrustLimitBuyMap(id,
										buy);
							}
							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustSellMap(id, sell);
							} else {
								realTimeData
										.removeEntrustSellMap(id, sell);
							}
						}

						rehandle = true;

						break first;
					}
				}

				if (rehandle) {
					limitBuyDealMaking(ftrademapping);
				}
			}

		}

		private void dealMaking(Ftrademapping ftrademapping) {
			int id = ftrademapping.getFid() ;
			boolean rehandle = false;

			Fentrust[] buyFentrusts = realTimeData.getEntrustBuyMap(id,Integer.MAX_VALUE) ;
			Fentrust[] sellFentrusts = realTimeData.getEntrustSellMap(id,Integer.MAX_VALUE) ;
			if (buyFentrusts.length > 0 && sellFentrusts.length > 0) {

				first: for (int i = 0; i < buyFentrusts.length; i++) {
					Fentrust buy = (Fentrust) buyFentrusts[i];

					if (buy.getFstatus() == EntrustStatusEnum.AllDeal
							|| buy.getFstatus() == EntrustStatusEnum.Cancel) {
						realTimeData.removeEntrustBuyMap(id, buy);
						continue;
					}

					second: for (int j = 0; j < sellFentrusts.length; j++) {
						Fentrust sell = (Fentrust) sellFentrusts[j];

						if (sell.getFstatus() == EntrustStatusEnum.AllDeal
								|| sell.getFstatus() == EntrustStatusEnum.Cancel) {
							realTimeData.removeEntrustSellMap(id, sell);
							continue;
						}

						if (buy.getFprize() < sell.getFprize()) {
							continue;
						}
						
						
						/*********************************************************/
						/*********************************************************/
						// 解决当卖单进入时本可以成交，但是此时又进入卖单，导致卖单以低价成交的bug
						/*
						 * 当买单的时间比卖单新时，需要判断是否有比卖单旧的价格可以成交，
						 * 当买单比卖单旧时，需要判断是否有比买单旧的价格可以成交
						 */
						int buyTime = buy.getFid() ;
						int sellTime = sell.getFid() ;
						if (buyTime  >= sellTime ) {
							// 当买单的时间比卖单新时，需要判断是否有比卖单旧的价格可以成交，
							boolean hasOld = false;
							for (int x = i + 1; x < buyFentrusts.length; x++) {

								Fentrust tmp = (Fentrust) buyFentrusts[x];
								int tmpTime = tmp.getFid() ;
								if (tmp.getFprize() >= sell.getFprize()
										&& tmpTime < sellTime) {
									hasOld = true;
								}
								break;
							}
							if (hasOld == true) {
								break second;
							}

						} else {
							// 当买单比卖单旧时，需要判断是否有比买单旧的价格可以成交
							boolean hasNew = false;
							for (int x = j + 1; x < sellFentrusts.length; x++) {

								Fentrust tmp = (Fentrust) sellFentrusts[x];
								int tmpTime = tmp.getFid() ;
								if (buy.getFprize() >= tmp.getFprize()
										&& tmpTime < buyTime) {
									hasNew = true;
								}
								break;
							}
							if (hasNew == true) {
								continue second;
							}
						}
						/*********************************************************/
						/*********************************************************/

						double buyCount = buy.getFleftCount();
						buyCount = buyCount > sell.getFleftCount() ? sell
								.getFleftCount() : buyCount;
						double buyPrize = 0d;
						if (buy.getFid().intValue() < sell.getFid().intValue()) {
							buyPrize = buy.getFprize();
						} else {
							buyPrize = sell.getFprize();
						}

						if (buyCount > buy.getFleftCount()
								|| buyCount > sell.getFleftCount()
								|| buyPrize > buy.getFprize()) {
							log.error("dealmaking error!");
							return;
						}
						
						boolean isbuy =false;
						boolean issell =false;
						if(buy.getFid().intValue() > sell.getFid().intValue()){
							isbuy = true;
						}else if(buy.getFid().intValue() < sell.getFid().intValue()){
							issell = true;
						}else{
							issell = true;
						}
						
						Fentrustlog buyFentrustlog = new Fentrustlog();
						buyFentrustlog.setFamount(buyCount * buyPrize);
						buyFentrustlog.setFcount(buyCount);
						buyFentrustlog.setFcreateTime(Utils.getTimestamp());
						buyFentrustlog.setFprize(buyPrize);
						buyFentrustlog.setIsactive(isbuy);
						buyFentrustlog.setFentrust(buy);
						buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
						buyFentrustlog.setFtrademapping(ftrademapping) ;
						//添加委托单来源
						buyFentrustlog.setFsource(buy.getFsource());

						Fentrustlog sellFentrustlog = new Fentrustlog();
						sellFentrustlog.setFamount(buyCount * buyPrize);
						sellFentrustlog.setFcount(buyCount);
						sellFentrustlog.setFcreateTime(Utils.getTimestamp());
						sellFentrustlog.setFprize(buyPrize);
						sellFentrustlog.setIsactive(issell);
						sellFentrustlog.setFentrust(sell);
						sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
						sellFentrustlog.setFtrademapping(ftrademapping) ;
						//添加委托单来源
						sellFentrustlog.setFsource(sell.getFsource());

						buy = frontTradeService.findFentrustById(buy.getFid());
						sell = frontTradeService.findFentrustById(sell.getFid());
						boolean ret = false;
						try {
							tradeDealMakingService.updateDealMaking(ftrademapping,buy, sell,
									buyFentrustlog, sellFentrustlog, id);
							ret = true;
						} catch (Exception e) {
						}

						if (ret) {
							realTimeData.addEntrustSuccessMap(id,
									sellFentrustlog);
							realTimeData.addEntrustSuccessMap(id,
									buyFentrustlog);
							
							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (buy.isFisLimit()) {
									realTimeData.addEntrustLimitBuyMap(id,
											buy);
								} else {
									realTimeData.addEntrustBuyMap(id, buy);
								}
							} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (buy.isFisLimit()) {
									realTimeData.removeEntrustLimitBuyMap(
											id, buy);
								} else {
									realTimeData.removeEntrustBuyMap(id,
											buy);
								}

							}

							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (sell.isFisLimit()) {
									realTimeData.addEntrustLimitSellMap(
											id, sell);
								} else {
									realTimeData.addEntrustSellMap(id,
											sell);
								}
							} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (sell.isFisLimit()) {
									realTimeData
											.removeEntrustLimitSellMap(id, sell);
								} else {
									realTimeData.removeEntrustSellMap(id,
											sell);
								}

							}

							rehandle = true;
						} else {
							buy = frontTradeService.findFentrustById(buy
									.getFid());
							sell = frontTradeService.findFentrustById(sell
									.getFid());

							if (buy == null || sell == null) {
								log.error("buy or sell null;");
								continue;
							}

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustBuyMap(id, buy);
							} else {
								realTimeData.removeEntrustBuyMap(id, buy);
							}
							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustSellMap(id, sell);
							} else {
								realTimeData
										.removeEntrustSellMap(id, sell);
							}
						}

						break first;
					}
				}
				if (rehandle) {
					dealMaking(ftrademapping);
				}

			}
	}

		private void limitBuySellDealMaking(Ftrademapping ftrademapping) {

			int id = ftrademapping.getFid() ;
			boolean rehandle = false;
			Fentrust[] buyLimitFentrusts = realTimeData
					.getEntrustLimitBuyMap(id,1) ;
			Fentrust[] sellLimitFentrusts = realTimeData.getEntrustLimitSellMap(id,1) ;
			
			
			Fentrust[] buys = realTimeData
					.getEntrustBuyMap(id,1) ;
			Fentrust[] sells = realTimeData.getEntrustSellMap(id,1) ;
			
			
			if ( buys.length==0&&sells.length==0&& buyLimitFentrusts.length > 0 && sellLimitFentrusts.length > 0) {

					Fentrust buy = (Fentrust) buyLimitFentrusts[0];
					Fentrust sell = (Fentrust) sellLimitFentrusts[0];
					

					if (buy.getFstatus() == EntrustStatusEnum.AllDeal
							|| buy.getFstatus() == EntrustStatusEnum.Cancel
							) {
						realTimeData.removeEntrustLimitBuyMap(id, buy);
						return;
					}
					if (sell.getFstatus() == EntrustStatusEnum.AllDeal
							|| sell.getFstatus() == EntrustStatusEnum.Cancel
							) {
						realTimeData.removeEntrustLimitSellMap(id, sell);
						return;
					}

					double buyPrize = this.realTimeData.getLatestDealPrize(id);
					double buyCount = (buy.getFamount() - buy
							.getFsuccessAmount()) / buyPrize;
					buyCount = buyCount > sell.getFleftCount() ? sell
							.getFleftCount() : buyCount;
					
					boolean isbuy =false;
					boolean issell =false;
					if(buy.getFid().intValue() > sell.getFid().intValue()){
						isbuy = true;
					}else if(buy.getFid().intValue() < sell.getFid().intValue()){
						issell = true;
					}else{
						issell = true;
					}

					Fentrustlog buyFentrustlog = new Fentrustlog();
					buyFentrustlog.setFamount(buyCount * buyPrize);
					buyFentrustlog.setFcount(buyCount);
					buyFentrustlog.setFcreateTime(Utils.getTimestamp());
					buyFentrustlog.setFprize(buyPrize);
					buyFentrustlog.setIsactive(isbuy);
					buyFentrustlog.setFentrust(buy);
					buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
					buyFentrustlog.setFtrademapping(ftrademapping) ;
					//添加委托单来源
					buyFentrustlog.setFsource(buy.getFsource());

					Fentrustlog sellFentrustlog = new Fentrustlog();
					sellFentrustlog.setFamount(buyCount * buyPrize);
					sellFentrustlog.setFcount(buyCount);
					sellFentrustlog.setFcreateTime(Utils.getTimestamp());
					sellFentrustlog.setFprize(buyPrize);
					sellFentrustlog.setIsactive(issell);
					sellFentrustlog.setFentrust(sell);
					sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
					sellFentrustlog.setFtrademapping(ftrademapping) ;
					//添加委托单来源
					sellFentrustlog.setFsource(sell.getFsource());

					buy = frontTradeService.findFentrustById(buy.getFid());
					sell = frontTradeService.findFentrustById(sell.getFid());
					boolean ret = false;
					try {
						tradeDealMakingService.updateDealMaking(ftrademapping,buy, sell,
								buyFentrustlog, sellFentrustlog, id);
						ret = true;
					} catch (Exception e) {
					}

					if (ret) {
						realTimeData.addEntrustSuccessMap(id,
								sellFentrustlog);
						realTimeData.addEntrustSuccessMap(id,
								buyFentrustlog);
						

						if (buy.getFstatus() == EntrustStatusEnum.Going
								|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
							if (buy.isFisLimit()) {
								realTimeData.addEntrustLimitBuyMap(id,
										buy);
							} else {
								realTimeData.addEntrustBuyMap(id, buy);
							}
						} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
							if (buy.isFisLimit()) {
								realTimeData.removeEntrustLimitBuyMap(
										id, buy);
							} else {
								realTimeData.removeEntrustBuyMap(id,
										buy);
							}

						}

						if (sell.getFstatus() == EntrustStatusEnum.Going
								|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
							if (sell.isFisLimit()) {
								realTimeData.addEntrustLimitSellMap(
										id, sell);
							} else {
								realTimeData.addEntrustSellMap(id,
										sell);
							}
						} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
							if (sell.isFisLimit()) {
								realTimeData
										.removeEntrustLimitSellMap(id, sell);
							} else {
								realTimeData.removeEntrustSellMap(id,
										sell);
							}

						}

						rehandle = true;
						
					} else {
						buy = frontTradeService.findFentrustById(buy
								.getFid());
						sell = frontTradeService.findFentrustById(sell
								.getFid());

						if (buy == null || sell == null) {
							log.error("buy or sell null;");
							return ;
						}

						if (buy.getFstatus() == EntrustStatusEnum.Going
								|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
							realTimeData
									.addEntrustLimitBuyMap(id, buy);
						} else {
							realTimeData.removeEntrustLimitBuyMap(id,
									buy);
						}
						if (sell.getFstatus() == EntrustStatusEnum.Going
								|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
							realTimeData.addEntrustSellMap(id, sell);
						} else {
							realTimeData
									.removeEntrustSellMap(id, sell);
						}
					}

			}

		}
		
}
