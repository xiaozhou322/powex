package com.gt.service.front;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.util.Utils;
import com.gt.util.common.MapUtil;
import com.gt.util.common.PeriodVo;
import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;
import com.gt.websocket.Global;
import com.gt.websocket.MyWebSocketServerHandler;

import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("frontMarketJsonService")
public class FrontMarketJsonServiceImpl implements FrontMarketJsonService{
	
	@Autowired
	private FrontConstantMapService constantMapService;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private RedisCacheUtil redisCacheUtil;	
	
	//交易中心
	public String marketRefresh(int symbol) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
//		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		List<Ftrademapping> ftrademappings = (List<Ftrademapping>) constantMapService.get("tradeMappingss");
		Ftrademapping ftrademapping = null;
		for (Ftrademapping trademapping : ftrademappings) {
			if(trademapping.getFid().intValue() == symbol) {
				ftrademapping = trademapping;
			}
		}
		if(ftrademapping == null ){
			return null ;
		}
		
		//加权参数
		Double adjustnum = 1d;
		if (constantMapService.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(constantMapService.getString("adjustvalue"));
		}
		
		if(adjustnum<0.75){
			adjustnum= 0.75;
		}
		if (adjustnum>1.85){
			adjustnum =1.85;
		}
		
		/*jsonObject.accumulate("p_new", Utils.getDouble(this.realTimeData.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("high", Utils.getDouble(this.oneDayData.getHighest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("low", Utils.getDouble(this.oneDayData.getLowest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("vol", Utils.getDouble(this.oneDayData.getTotal(ftrademapping.getFid())*adjustnum, ftrademapping.getFcount2()));
		jsonObject.accumulate("buy1", Utils.getDouble(this.realTimeData.getHighestBuyPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("sell1", Utils.getDouble(this.realTimeData.getLowestSellPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));*/
		jsonObject.accumulate("p_new", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("high", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getHighestPrize24(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("low", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLowestPrize24(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("vol", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getTotalDeal24(ftrademapping.getFid())))*adjustnum, ftrademapping.getFcount2()));
		jsonObject.accumulate("buy1", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getHighestBuyPrize(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("sell1", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLowestSellPrize(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("pbits", ftrademapping.getFcount1());
		jsonObject.accumulate("vbits", ftrademapping.getFcount2());
		
		//Object[] buyEntrusts = null ;
		//Object[] sellEntrusts = null ;
		/*Object[] successEntrusts = this.realTimeData.getEntrustSuccessMap(ftrademapping.getFid(),5) ;
		buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(),5) ;
		sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(),5) ;*/
		String successEntrustsString = redisCacheUtil.getCacheString(RedisKey.getEntrustSuccess(ftrademapping.getFid()));
		List<String> successEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(successEntrustsString, String.class);
		String sellEntrustsString = redisCacheUtil.getCacheString(RedisKey.getSellDepth(ftrademapping.getFid()));
		List<String> sellEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(sellEntrustsString, String.class);
		String buyEntrustsString = redisCacheUtil.getCacheString(RedisKey.getBuyDepth(ftrademapping.getFid()));
		List<String> buyEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(buyEntrustsString, String.class);
		
		JSONArray sellDepthList = new JSONArray() ;
		if(sellEntrustsList != null){
			for (int i = 0; i < sellEntrustsList.size() && i<5; i++) {
				//Fentrust fentrust = (Fentrust) sellEntrusts[i] ;
				String fentrustString = sellEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				JSONObject js1 = new JSONObject();
				js1.accumulate("id",i+1) ;
				//js1.accumulate("price",fentrust.getFprize()) ;
				js1.accumulate("price",Double.valueOf(fentrustParam[0])) ;
				//js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
				js1.accumulate("amount",Utils.getDoubleS(Double.valueOf(fentrustParam[1]), ftrademapping.getFcount2())) ;
				sellDepthList.add(js1);
			}
		}
		jsonObject.accumulate("sells", sellDepthList);
		
		JSONArray buyDepthList = new JSONArray() ;
		if(buyEntrustsList != null){
			for (int i = 0; i < buyEntrustsList.size() && i<5; i++) {
				JSONObject js1 = new JSONObject();
				//Fentrust fentrust = (Fentrust) buyEntrusts[i] ;
				String fentrustString = buyEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				
				js1.accumulate("id",i+1) ;
				//js1.accumulate("price",fentrust.getFprize()) ;
				js1.accumulate("price",Double.valueOf(fentrustParam[0])) ;
				//js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
				js1.accumulate("amount",Utils.getDoubleS(Double.valueOf(fentrustParam[1]), ftrademapping.getFcount2())) ;
				
				buyDepthList.add(js1);
			}
		}
		jsonObject.accumulate("buys", buyDepthList);
		
		JSONArray recentDealList = new JSONArray() ;
		if(successEntrustsList != null){
			for (int i = 0; i < successEntrustsList.size() && i<5; i++) {
				JSONObject js1 = new JSONObject();
				//Fentrustlog fentrust = (Fentrustlog) successEntrusts[i] ;
				String fentrustString = successEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				
				js1.accumulate("id",i+1) ;
				//js1.accumulate("time",String.valueOf(new SimpleDateFormat("HH:mm:ss").format(fentrust.getFcreateTime()))) ;
				js1.accumulate("time",String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new Timestamp(Long.valueOf(fentrustParam[0]))))) ;
				//js1.accumulate("price",String.valueOf(fentrust.getFprize())) ;
				js1.accumulate("price",fentrustParam[2]) ;
				//js1.accumulate("amount",Utils.getDoubleS(fentrust.getFcount, ftrademapping.getFcount2()))
				js1.accumulate("amount",Utils.getDoubleS(Double.valueOf(fentrustParam[3]), ftrademapping.getFcount2())) ;
				String en_type="";
				String cn_type = "";
				//fentrust.getfEntrustType
				if(Integer.valueOf(fentrustParam[4]) ==0){
					en_type="bid";
					cn_type="买入";
				}else{
					en_type="ask";
					cn_type="卖出";
				}
				js1.accumulate("en_type",en_type) ;
				js1.accumulate("type",cn_type) ;
				recentDealList.add(js1);
			}
		}
		jsonObject.accumulate("trades", recentDealList);
		
		return jsonObject.toString() ;
	}
	
	public String marketRefresh2(int symbol){
		JSONObject jsonObject = new JSONObject() ;
//		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		List<Ftrademapping> ftrademappings = (List<Ftrademapping>) constantMapService.get("tradeMappingss");
		Ftrademapping ftrademapping = null;
		for (Ftrademapping trademapping : ftrademappings) {
			if(trademapping.getFid().intValue() == symbol) {
				ftrademapping = trademapping;
			}
		}
		if(ftrademapping == null ){
			return null ;
		}
		//加权参数
		Double adjustnum = 1d;
		
		/*jsonObject.accumulate("p_new", Utils.getDouble(this.realTimeData.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("high", Utils.getDouble(this.oneDayData.getHighest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("low", Utils.getDouble(this.oneDayData.getLowest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("vol", Utils.getDouble(this.oneDayData.getTotal(ftrademapping.getFid())*adjustnum, ftrademapping.getFcount2()));
		jsonObject.accumulate("buy1", Utils.getDouble(this.realTimeData.getHighestBuyPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("sell1", Utils.getDouble(this.realTimeData.getLowestSellPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));*/
		jsonObject.accumulate("p_new", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("high", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getHighestPrize24(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("low", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLowestPrize24(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("vol", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getTotalDeal24(ftrademapping.getFid())))*adjustnum, ftrademapping.getFcount2()));
		jsonObject.accumulate("buy1", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getHighestBuyPrize(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("sell1", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLowestSellPrize(ftrademapping.getFid()))), ftrademapping.getFcount1()));
		jsonObject.accumulate("pbits", ftrademapping.getFcount1());
		jsonObject.accumulate("vbits", ftrademapping.getFcount2());
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==1){
			//jsonObject.accumulate("fbprice",  Utils.getDouble(this.realTimeData.getLatestDealPrize(13), 2));
			jsonObject.accumulate("fbprice",  Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(1))), 2));
		}else if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==3){
			//jsonObject.accumulate("fbprice",  Utils.getDouble(this.realTimeData.getLatestDealPrize(14), 2));
			jsonObject.accumulate("fbprice",  Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(2))), 2));
		}else{
			jsonObject.accumulate("fbprice",  1);
		}
		jsonObject.accumulate("usdtrate",  this.constantMapService.get("USDT_PRICE").toString());
		
		//double s = this.oneDayData.get24Price(ftrademapping.getFid());
		double s = Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getStart24Price(ftrademapping.getFid())));

		List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMapService.get("tradehistory");
		for (Ftradehistory ftradehistory : ftradehistorys) {
			if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
				s= ftradehistory.getFprice();
				break;
			}
		}
		
		
		double last = 0d;
		try {
			//last = Utils.getDouble((this.realTimeData.getLatestDealPrize(ftrademapping.getFid())-s)/s*100, 2);
			last = Utils.getDouble((Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(ftrademapping.getFid())))-s)/s*100, 2);
		} catch (Exception e) {}
		jsonObject.accumulate("rose", last);
		
		//Object[] buyEntrusts = null ;
		//Object[] sellEntrusts = null ;
		/*Object[] successEntrusts = this.realTimeData.getEntrustSuccessMap(ftrademapping.getFid(),60) ;
			buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(),20) ;
			sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(),20) ;*/
		String successEntrustsString = redisCacheUtil.getCacheString(RedisKey.getEntrustSuccess(ftrademapping.getFid()));
		List<String> successEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(successEntrustsString, String.class);
		String sellEntrustsString = redisCacheUtil.getCacheString(RedisKey.getSellDepth(ftrademapping.getFid()));
		List<String> sellEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(sellEntrustsString, String.class);
		String buyEntrustsString = redisCacheUtil.getCacheString(RedisKey.getBuyDepth(ftrademapping.getFid()));
		List<String> buyEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(buyEntrustsString, String.class);
		
		JSONArray sellDepthList = new JSONArray() ;
		if(sellEntrustsList != null){
			for (int i = 0; i < sellEntrustsList.size() && i<20; i++) {
				//Fentrust fentrust = (Fentrust) sellEntrusts[i] ;
				String fentrustString = sellEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				JSONObject js1 = new JSONObject();
				
				js1.accumulate("id",i+1) ;
				//js1.accumulate("price",fentrust.getFprize()) ;
				js1.accumulate("price",Double.valueOf(fentrustParam[0])) ;
				//js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
				js1.accumulate("amount",Utils.getDoubleS(Double.valueOf(fentrustParam[1]), ftrademapping.getFcount2())) ;
				sellDepthList.add(js1);
			}
		}
		jsonObject.accumulate("sells", sellDepthList);
		
		JSONArray buyDepthList = new JSONArray() ;
		if(buyEntrustsList != null){
			for (int i = 0; i < buyEntrustsList.size() && i<20; i++) {
				JSONObject js1 = new JSONObject();
				//Fentrust fentrust = (Fentrust) buyEntrusts[i] ;
				String fentrustString = buyEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				
				js1.accumulate("id",i+1) ;
				//js1.accumulate("price",fentrust.getFprize()) ;
				js1.accumulate("price",Double.valueOf(fentrustParam[0])) ;
				//js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
				js1.accumulate("amount",Utils.getDoubleS(Double.valueOf(fentrustParam[1]), ftrademapping.getFcount2())) ;
				
				buyDepthList.add(js1);
			}
		}
		jsonObject.accumulate("buys", buyDepthList);
		
		JSONArray recentDealList = new JSONArray() ;
		if(successEntrustsList != null){
			for (int i = 0; i < successEntrustsList.size() && i<20; i++) {
				JSONObject js1 = new JSONObject();
				//Fentrustlog fentrust = (Fentrustlog) successEntrusts[i] ;
				String fentrustString = successEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				
				js1.accumulate("id",i+1) ;
				//js1.accumulate("time",String.valueOf(new SimpleDateFormat("HH:mm:ss").format(fentrust.getFcreateTime()))) ;
				js1.accumulate("time",String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new Timestamp(Long.valueOf(fentrustParam[0]))))) ;
				//js1.accumulate("price",String.valueOf(fentrust.getFprize())) ;
				js1.accumulate("price",String.valueOf(fentrustParam[2])) ;
				//js1.accumulate("amount",Utils.getDoubleS(fentrust.getFcount(), ftrademapping.getFcount2())) ;
				js1.accumulate("amount",Utils.getDoubleS(Double.valueOf(fentrustParam[3]), ftrademapping.getFcount2())) ;
				String en_type="";
				String cn_type = "";
				//fentrust.getfEntrustType()
				if(Integer.valueOf(fentrustParam[4]) ==0){
					en_type="bid";
					cn_type="买入";
				}else{
					en_type="ask";
					cn_type="卖出";
				}
				js1.accumulate("en_type",en_type) ;
				js1.accumulate("type",cn_type) ;
				recentDealList.add(js1);
			}
		}
		jsonObject.accumulate("trades", recentDealList);
		
		JSONArray jsonArray = new JSONArray() ;
//		List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMapping();
//		List<Ftrademapping> ftrademappings = (List<Ftrademapping>) constantMapService.get("tradeMappingss");
		for(Ftrademapping tm : ftrademappings){
			if(tm == null ){
				continue ;
			}
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.accumulate("symbol", tm.getFid());
			jsonObj.accumulate("p_new", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(tm.getFid()))), tm.getFcount1()));
			jsonObj.accumulate("pbits", tm.getFcount1());
			jsonObj.accumulate("vbits", tm.getFcount2());
			double p = Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getStart24Price(tm.getFid())));

			List<Ftradehistory> ths = (List<Ftradehistory>)constantMapService.get("tradehistory");
			for (Ftradehistory th : ths) {
				if(th.getFtrademapping().getFid().intValue() == tm.getFid().intValue()){
					p= th.getFprice();
					break;
				}
			}
			
			
			double lastRose = 0d;
			try {
				//last = Utils.getDouble((this.realTimeData.getLatestDealPrize(ftrademapping.getFid())-s)/s*100, 2);
				lastRose = Utils.getDouble((Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(tm.getFid())))-s)/s*100, 2);
			} catch (Exception e) {}
			jsonObj.accumulate("rose", last);
			jsonArray.add(jsonObj);
		}
		jsonObject.accumulate("roses", jsonArray);
		return jsonObject.toString() ;
	}
	
	public String marketRefreshrose(){
		JSONArray jsonArray = new JSONArray() ;
		//for(Integer symbol : symbols){
//			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMapping();
			List<Ftrademapping> ftrademappings = (List<Ftrademapping>) constantMapService.get("tradeMappingss");
			for(Ftrademapping ftrademapping : ftrademappings){
				if(ftrademapping == null ){
					continue ;
				}
				
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.accumulate("symbol", ftrademapping.getFid());
				jsonObject.accumulate("p_new", Utils.getDouble(Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(ftrademapping.getFid()))), ftrademapping.getFcount1()));
				jsonObject.accumulate("pbits", ftrademapping.getFcount1());
				jsonObject.accumulate("vbits", ftrademapping.getFcount2());
				double s = Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getStart24Price(ftrademapping.getFid())));

				List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMapService.get("tradehistory");
				for (Ftradehistory ftradehistory : ftradehistorys) {
					if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
						s= ftradehistory.getFprice();
						break;
					}
				}
				
				
				double last = 0d;
				try {
					//last = Utils.getDouble((this.realTimeData.getLatestDealPrize(ftrademapping.getFid())-s)/s*100, 2);
					last = Utils.getDouble((Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(ftrademapping.getFid())))-s)/s*100, 2);
				} catch (Exception e) {}
				jsonObject.accumulate("rose", last);
				jsonArray.add(jsonObject);
			}
		//}
		
		return jsonArray.toString() ;
	}
	
	public String period(int step,int symbol){
		
		int key = 0 ;
		switch (step) {
		case 60:
			key = 0 ;
			break;
		case 60*3:
			key = 1 ;
			break;
		case 60*5:
			key = 2 ;
			break;
		case 60*15:
			key = 3 ;
			break;
		case 60*30:
			key = 4 ;
			break;
		case 60*60:
			key = 5 ;
			break;
		case 60*60*2:
			key = 6 ;
			break;
		case 60*60*4:
			key = 7 ;
			break;
		case 60*60*6:
			key = 8 ;
			break;
		case 60*60*12:
			key = 9 ;
			break;
		case 60*60*24:
			key = 10 ;
			break;
		case 60*60*24*3:
			key = 11 ;
			break;
		case 60*60*24*7:
			key = 12 ;
			break;
		}
		long l= System.currentTimeMillis() ;
		String ret = null;
		try {
			//String allperiod = this.klinePeriodData.getJsonString(symbol, key) ;
			String allperiod = redisCacheUtil.getCacheString(RedisKey.getJsonString(symbol, key));
			ret =allperiod;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret ;
	}
	
	public String depth(int symbol,int step) throws Exception{
		
		long l= System.currentTimeMillis() ;
		JSONObject jsonObject = new JSONObject() ;
		
		//Fentrust[] buy = null ;
		//Fentrust[] sell = null ;
		//buy = this.realTimeData.getBuyDepthMap(symbol,10);
		//sell = this.realTimeData.getSellDepthMap(symbol,10);
		String sellEntrustsString = redisCacheUtil.getCacheString(RedisKey.getSellDepth(symbol));
		List<String> sellEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(sellEntrustsString, String.class);
		String buyEntrustsString = redisCacheUtil.getCacheString(RedisKey.getBuyDepth(symbol));
		List<String> buyEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(buyEntrustsString, String.class);
		
		List<List<BigDecimal>> buyList = new ArrayList<List<BigDecimal>>() ;
		List<List<BigDecimal>> sellList = new ArrayList<List<BigDecimal>>() ;
		if(buyEntrustsList != null){
			for (String fentrustString:buyEntrustsList) {
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				List<BigDecimal> list = new ArrayList<BigDecimal>() ;
				
				//list.add(new BigDecimal(String.valueOf(fentrust.getFprize())).setScale(6, BigDecimal.ROUND_DOWN)) ;
				list.add(new BigDecimal(fentrustParam[0]).setScale(6, BigDecimal.ROUND_DOWN)) ;
				//list.add(new BigDecimal(String.valueOf(fentrust.getFleftCount())).setScale(4, BigDecimal.ROUND_DOWN)) ;
				list.add(new BigDecimal(fentrustParam[1]).setScale(4, BigDecimal.ROUND_DOWN)) ;
				buyList.add(list) ;
			}
		}
		if(sellEntrustsList != null){
			for (String fentrustString:sellEntrustsList) {
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				List<BigDecimal> list = new ArrayList<BigDecimal>() ;
				
				list.add(new BigDecimal(fentrustParam[0]).setScale(6, BigDecimal.ROUND_DOWN)) ;
				list.add(new BigDecimal(fentrustParam[1]).setScale(4, BigDecimal.ROUND_DOWN)) ;
				sellList.add(list) ;
			}
		}
		JSONObject askBidJson = new JSONObject() ;
		askBidJson.accumulate("bids", buyList) ;
		askBidJson.accumulate("asks", sellList) ;
		askBidJson.accumulate("date", Utils.getTimestamp().getTime()/1000L) ;
		jsonObject.accumulate("depth", askBidJson) ;
		
		JSONObject periodJson = new JSONObject() ;
		periodJson.accumulate("marketFrom", symbol);
		periodJson.accumulate("coinVol", symbol);
		periodJson.accumulate("type", step);
		//periodJson.accumulate("data",toArr( this.latestKlinePeroid.getPeriod(symbol, step/60),symbol));
		periodJson.accumulate("data",toArr(redisCacheUtil.getCacheString(RedisKey.getPeriod(symbol, step/60)),symbol));
		jsonObject.accumulate("period", periodJson) ;		
		
		String  ret = jsonObject.toString() ;
		return ret ;
	}
	/*private String toArr(List<Fperiod> list,int symbol){
		StringBuffer stringBuffer = new StringBuffer() ;
		
		//加权参数
		Double adjustnum = 1d;
		if (constantMap.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(constantMap.getString("adjustvalue"));
		}
		if(adjustnum<0.75){
			adjustnum= 0.75;
		}
		if (adjustnum>1.85){
			adjustnum =1.85;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String nowday = sdf.format(now);
		if (nowday.equals("2018-01-12")){
			adjustnum = 1d;
		}
		
		Ftrademapping mapping = this.ftradeMappingService.findFtrademapping(symbol);
		stringBuffer.append("[") ;
		if(list != null && list.size() >0){
			for (int i=list.size()-1;i<list.size();i++) {
				Fperiod fperiod = list.get(i) ;
				boolean isopen = Utils.openTrade(mapping, Utils.getTimestamp());
				if(isopen){
					stringBuffer.append("["+(fperiod.getFtime().getTime())
							+","+new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFliang()*adjustnum)).setScale(4, BigDecimal.ROUND_HALF_UP)+"]") ;
					if(i!=list.size()-1){
						stringBuffer.append(",") ;
					}
				}
			}
		}
		stringBuffer.append("]") ;
		return stringBuffer.toString() ;
	}*/
	
	private String toArr(String fperiodString,int symbol){
		StringBuffer stringBuffer = new StringBuffer() ;
		
		//加权参数
		Double adjustnum = 1d;
		if (constantMapService.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(constantMapService.getString("adjustvalue"));
		}
		if(adjustnum<0.75){
			adjustnum= 0.75;
		}
		if (adjustnum>1.85){
			adjustnum =1.85;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String nowday = sdf.format(now);
		if (nowday.equals("2018-01-12")){
			adjustnum = 1d;
		}
		
//		Ftrademapping mapping = this.ftradeMappingService.findFtrademapping(symbol);
		List<Ftrademapping> ftrademappings = (List<Ftrademapping>) constantMapService.get("tradeMappingss");
		Ftrademapping mapping = null;
		for (Ftrademapping trademapping : ftrademappings) {
			if(trademapping.getFid().intValue() == symbol) {
				mapping = trademapping;
			}
		}
		
		List<String> periodStringList = com.alibaba.fastjson.JSONObject.parseArray(fperiodString, String.class);
				
		stringBuffer.append("[") ;
		if(periodStringList != null && periodStringList.size() >0){
			for (int i=periodStringList.size()-1;i<periodStringList.size();i++) {
				//Fperiod fperiod = list.get(i) ;
				String[] periodParam = periodStringList.get(i).substring(1, periodStringList.get(i).length()-1).split(",");
				boolean isopen = Utils.openTrade(mapping, Utils.getTimestamp());
				if(isopen){
					stringBuffer.append("["+periodParam[0]
							+","+new BigDecimal(periodParam[1]).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(periodParam[2]).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(periodParam[3]).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(periodParam[4]).setScale(6, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(Double.valueOf(periodParam[5])*adjustnum)).setScale(4, BigDecimal.ROUND_HALF_UP)+"]") ;
					if(i!=periodStringList.size()-1){
						stringBuffer.append(",") ;
					}
				}
			}
		}
		stringBuffer.append("]") ;
		return stringBuffer.toString() ;
	}

	public String trade_json(int id) throws Exception{
		
		StringBuffer content = new StringBuffer() ;
		content.append(
				//"chart_1h = {symbol:\"BTC_CNY\",symbol_view:\"CNY/CNY\",ask:1.2,time_line:"+this.klinePeriodData.getIndexJsonString(id, 5) +"};"
				"chart_1h = {symbol:\"BTC_CNY\",symbol_view:\"CNY/CNY\",ask:1.2,time_line:"+ redisCacheUtil.getCacheString(RedisKey.getIndexJsonString(id, 5)) +"};"
				) ;
		content.append(
				//"chart_5m = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 2) +"};" 
				"chart_5m = {time_line:"+ redisCacheUtil.getCacheString(RedisKey.getIndexJsonString(id, 2)) +"};" 
				) ;
		content.append(
				//"chart_15m = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 3) +"};"
				"chart_15m = {time_line:"+ redisCacheUtil.getCacheString(RedisKey.getIndexJsonString(id, 3)) +"};"
				) ;
		content.append(
				//"chart_30m = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 4) +"};"
				"chart_30m = {time_line:"+ redisCacheUtil.getCacheString(RedisKey.getIndexJsonString(id, 4)) +"};"
				) ;
		content.append(
				//"chart_8h = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 8) +"};"
				"chart_8h = {time_line:"+ redisCacheUtil.getCacheString(RedisKey.getIndexJsonString(id, 8)) +"};"
				) ;
		content.append(
				//"chart_1d = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 10) +"};"
				"chart_1d = {time_line:"+ redisCacheUtil.getCacheString(RedisKey.getIndexJsonString(id, 10)) +"};"
				) ;
		return content.toString() ;
	}
	
	private long getDayCount(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//快照�?始日�? �?2017-12-27，最高季�?200�?
		String startday = "2018-01-12";
		long dcount = 0;
		try {
			Date startdate = sdf.parse(startday);
			Date now = new Date();
			dcount=(now.getTime()-startdate.getTime())/(24*60*60*1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dcount;
	}
	
	public void sendMarketFresh2(String msg){
		String message = marketRefresh2(Integer.valueOf(msg));
		List<ChannelHandlerContext> handlerContexts = MapUtil.findValueByLikeKey("symbol="+msg, Global.pushCtxMap);
		for(ChannelHandlerContext ctx : handlerContexts){
			MyWebSocketServerHandler.push(ctx, message);
		}
	}
	
	public void sendDepth(String msg){
		try {
			List<String> keyList = MapUtil.findKeysByLikeString("symbol="+msg, Global.pushCtxMap);
			for(String key : keyList){
				ChannelHandlerContext ctx = Global.pushCtxMap.get(key);
				String[] params = key.split(":");
				String message = depth(Integer.valueOf(params[2]), Integer.valueOf(params[3]));
				MyWebSocketServerHandler.push(ctx, message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String period2(String step,int symbol,int num, Long time){
		
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		int key = 0 ;
		
		if("M1".equals(step)) {
			key = 0 ;
		} else if("M5".equals(step)) {
			key = 2 ;
		} else if("M15".equals(step)) {
			key = 3 ;
		} else if("M30".equals(step)) {
			key = 4 ;
		} else if("H1".equals(step)) {
			key = 5 ;
		} else if("D1".equals(step)) {
			key = 10 ;
		} else if("1W".equals(step)) {
			key = 12 ;
		}
		
		long l= System.currentTimeMillis() ;
//		String ret = null;
		try {
			//加权参数
			/*Double adjustnum = 1d;
			if (constantMapService.get("adjustvalue")!=null){
				adjustnum = Double.valueOf(constantMapService.getString("adjustvalue"));
			}
			if(adjustnum<0.75){
				adjustnum= 0.75;
			}
			if (adjustnum>1.85){
				adjustnum =1.85;
			}*/
			//String allperiod = this.klinePeriodData.getJsonString(symbol, key) ;
			String allperiod = redisCacheUtil.getCacheString(RedisKey.getJsonString(symbol, key));
			List<String> allperiodList = com.alibaba.fastjson.JSONObject.parseArray(allperiod, String.class);
			
			List<PeriodVo> periodVoList = new ArrayList<PeriodVo>();
			if(allperiodList != null && allperiodList.size() >0){
				for (int i=0;i<allperiodList.size();i++) {
					String[] periodParam = allperiodList.get(i).substring(1, allperiodList.get(i).length()-1).split(",");
					
					if(Long.valueOf(periodParam[0]) <= time*1000) {
						PeriodVo periodVo = new PeriodVo();
						periodVo.setTime(Long.valueOf(periodParam[0]));
						periodVo.setOpen(new BigDecimal(periodParam[1]).setScale(6, BigDecimal.ROUND_HALF_UP));
						periodVo.setHigh(new BigDecimal(periodParam[2]).setScale(6, BigDecimal.ROUND_HALF_UP));
						periodVo.setLow(new BigDecimal(periodParam[3]).setScale(6, BigDecimal.ROUND_HALF_UP));
						periodVo.setClose(new BigDecimal(periodParam[4]).setScale(6, BigDecimal.ROUND_HALF_UP));
//						periodVo.setVolume(new BigDecimal(String.valueOf(Double.valueOf(periodParam[5])*adjustnum)).setScale(4, BigDecimal.ROUND_HALF_UP));
						periodVo.setVolume(new BigDecimal(String.valueOf(Double.valueOf(periodParam[5]))).setScale(4, BigDecimal.ROUND_HALF_UP));
						
						periodVoList.add(periodVo);
					}
				}
			}
			if(periodVoList.size() >= num) {
				periodVoList = periodVoList.subList(periodVoList.size()-1-num, periodVoList.size()-1);
			}
			
			/*periodVoList = periodVoList.parallelStream()
					.filter(v -> v.getTime() <= time*1000)
					.collect(Collectors.toList())
					.subList(periodVoList.size()-1-num, periodVoList.size()-1);*/
			
			jsonObject.put("data", periodVoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toJSONString() ;
	}
	
	
	public String lastPeriod(int symbol, String step, String type){
		
		int key = 0;
		if("M1".equals(step)) {
			key = 60 ;
		} else if("M5".equals(step)) {
			key = 60*5 ;
		} else if("M15".equals(step)) {
			key = 60*15 ;
		} else if("M30".equals(step)) {
			key = 60*30 ;
		} else if("H1".equals(step)) {
			key = 60*60 ;
		} else if("D1".equals(step)) {
			key = 60*60*24 ;
		} else if("1W".equals(step)) {
			key = 60*60*24*7 ;
		}
		
		String lastPeriod = getLastPeriod(redisCacheUtil.getCacheString(RedisKey.getPeriod(symbol, key/60)), symbol, type);
		return lastPeriod ;
	}
	
	
	private String getLastPeriod(String lastPeriod,int symbol, String type){
		
		//加权参数
		Double adjustnum = 1d;
		if (constantMapService.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(constantMapService.getString("adjustvalue"));
		}
		if(adjustnum<0.75){
			adjustnum= 0.75;
		}
		if (adjustnum>1.85){
			adjustnum =1.85;
		}
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String nowday = sdf.format(now);
		if (nowday.equals("2018-01-12")){
			adjustnum = 1d;
		}*/
		
//		Ftrademapping mapping = this.ftradeMappingService.findFtrademapping(symbol);
		List<Ftrademapping> ftrademappings = (List<Ftrademapping>) constantMapService.get("tradeMappingss");
		Ftrademapping mapping = null;
		for (Ftrademapping trademapping : ftrademappings) {
			if(trademapping.getFid().intValue() == symbol) {
				mapping = trademapping;
			}
		}
		
		List<String> periodStringList = com.alibaba.fastjson.JSONObject.parseArray(lastPeriod, String.class);
				
		PeriodVo periodVo = new PeriodVo();
		if(periodStringList != null && periodStringList.size() >0){
			for (int i=periodStringList.size()-1;i<periodStringList.size();i++) {
				//Fperiod fperiod = list.get(i) ;
				String[] periodParam = periodStringList.get(i).substring(1, periodStringList.get(i).length()-1).split(",");
				boolean isopen = Utils.openTrade(mapping, Utils.getTimestamp());
				if(isopen){
					periodVo.setTime(Long.valueOf(periodParam[0]));
					periodVo.setOpen(new BigDecimal(periodParam[1]).setScale(6, BigDecimal.ROUND_HALF_UP));
					periodVo.setHigh(new BigDecimal(periodParam[2]).setScale(6, BigDecimal.ROUND_HALF_UP));
					periodVo.setLow(new BigDecimal(periodParam[3]).setScale(6, BigDecimal.ROUND_HALF_UP));
					
					double latestPrice = Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(mapping.getFid())));
					if(0 == latestPrice) {
						periodVo.setClose(new BigDecimal(periodParam[4]).setScale(6, BigDecimal.ROUND_HALF_UP));
					} else {
						periodVo.setClose(new BigDecimal(Utils.getDouble(latestPrice, mapping.getFcount1())));
					}
					
					periodVo.setVolume(new BigDecimal(String.valueOf(Double.valueOf(periodParam[5])*adjustnum)).setScale(4, BigDecimal.ROUND_HALF_UP));
					periodVo.setType(type);
				}
			}
		}
		return com.alibaba.fastjson.JSON.toJSONString(periodVo) ;
	}

}
