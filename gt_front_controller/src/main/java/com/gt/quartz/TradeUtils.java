package com.gt.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.entity.Fentrust;
import com.gt.entity.Flimittrade;
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.service.admin.EntrustService;
import com.gt.service.admin.LimittradeService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.TradehistoryService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontTradeService;	
import com.gt.util.Utils;
import com.gt.utils.ApplicationContextUtil;

public class TradeUtils {
	
	private TradehistoryService 		tradehistoryService 		= (TradehistoryService)ApplicationContextUtil.getBean("tradehistoryService");
	private FrontCacheService 			frontCacheService 			= (FrontCacheService)ApplicationContextUtil.getBean("frontCacheService");
	private LimittradeService 			limittradeService 			= (LimittradeService)ApplicationContextUtil.getBean("limittradeService");
	private FrontConstantMapService 	map 						= (FrontConstantMapService)ApplicationContextUtil.getBean("frontConstantMapService");
	private FrontTradeService 			frontTradeService 			= (FrontTradeService)ApplicationContextUtil.getBean("frontTradeService");
	private EntrustService 				entrustService 				= (EntrustService)ApplicationContextUtil.getBean("entrustService");
	private TradeMappingService 		tradeMappingService 		= (TradeMappingService)ApplicationContextUtil.getBean("tradeMappingService");
	private FrontEntrustChangeService 	frontEntrustChangeService 	= (FrontEntrustChangeService)ApplicationContextUtil.getBean("frontEntrustChangeService");
	
	public void work() {
		String sql = "where fstatus="+TrademappingStatusEnum.ACTIVE;
		List<Ftrademapping> mappings = this.tradeMappingService.list(0, 0, sql, false);
		for (Ftrademapping ftrademapping : mappings) {
			double price = Utils.getDouble(this.frontCacheService.getLatestDealPrize(ftrademapping.getFid()),ftrademapping.getFcount1());
			Ftradehistory tradehistory = new Ftradehistory();
			tradehistory.setFdate(new Date());
			tradehistory.setFprice(price);
			tradehistory.setFtotal(this.frontCacheService.get24Total(ftrademapping.getFid()));
			tradehistory.setFtrademapping(ftrademapping);
			this.tradehistoryService.saveObj(tradehistory);
		}
		
		List<Flimittrade> trades = this.limittradeService.list(0, 0, "", false);
		for (Flimittrade flimittrade : trades) {
			Ftrademapping ftrademapping = this.tradeMappingService.findById(flimittrade.getFtrademapping().getFid()) ;
			try {
				double price = Utils.getDouble(this.frontCacheService.getLatestDealPrize(ftrademapping.getFid()),ftrademapping.getFcount1());
				flimittrade.setFdownprice(price);
				flimittrade.setFupprice(price);
				this.limittradeService.updateObj(flimittrade);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				//限价交易0点自动撤单
				String filter = "where (fstatus="+EntrustStatusEnum.Going+" or fstatus="+EntrustStatusEnum.PartDeal+") and ftrademapping.fid="+ftrademapping.getFid();
				List<Fentrust> fentrustList = this.entrustService.list(0, 0, filter, false);
				for (Fentrust fentrust : fentrustList) {
					if(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal){
						boolean flag = false ;
						try {
							this.frontTradeService.updateCancelFentrust(fentrust, fentrust.getFuser()) ;
							if (fentrust.getFentrustType() == EntrustTypeEnum.BUY) {
								if (fentrust.isFisLimit()) {
									frontEntrustChangeService.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//								rabbitTemplate.convertAndSend("entrust.limit.buy.remove", symbolAndIdStr);
								} else {
									frontEntrustChangeService.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//								rabbitTemplate.convertAndSend("entrust.buy.remove", symbolAndIdStr);
								}
							} else {
								if (fentrust.isFisLimit()) {
									frontEntrustChangeService.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//									rabbitTemplate.convertAndSend("entrust.limit.sell.remove", symbolAndIdStr);
								} else {
									frontEntrustChangeService.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//									rabbitTemplate.convertAndSend("entrust.sell.remove", symbolAndIdStr);
								}
							}
							
							flag = true ;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
//		try {
//			while(true){
//				//限价交易0点自动撤单
//				String filter = "where (fstatus="+EntrustStatusEnum.Going+" or fstatus="+EntrustStatusEnum.PartDeal+")";
//				List<Fentrust> fentrust = this.entrustService.list(0, 0, filter, false);
//				if(fentrust == null || fentrust.size() ==0) break;
//				for (Fentrust fentrust2 : fentrust) {
//					if(fentrust2.getFstatus()==EntrustStatusEnum.Going || fentrust2.getFstatus()==EntrustStatusEnum.PartDeal){
//						boolean flag = false ;
//						try {
//							this.frontTradeService.updateCancelFentrust(fentrust2, fentrust2.getFuser()) ;
//							flag = true ;
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						if(flag==true){
//							if(fentrust2.getFentrustType()==EntrustTypeEnum.BUY){
//								//买
//								if(fentrust2.isFisLimit()){
//									this.realTimeData.removeEntrustLimitBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}else{
//									this.realTimeData.removeEntrustBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}
//							}else{
//								//卖
//								if(fentrust2.isFisLimit()){
//									this.realTimeData.removeEntrustLimitSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}else{
//									this.realTimeData.removeEntrustSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}
//								
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		
		{
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = sdf.format(c.getTime());
			String xx = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+key+"'";
			List<Ftradehistory> ftradehistorys = this.tradehistoryService.list(0, 0, xx, false);
			map.put("tradehistory", ftradehistorys);
		}
		
	}
}