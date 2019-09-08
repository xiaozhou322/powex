package com.gt.service.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.service.front.FrontCacheService;
import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;

@Service("frontCacheService")
public class FrontCacheServiceImpl implements FrontCacheService {
	
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	public double getLatestDealPrize(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLatestDealPrize(id)));
	}
	
	public double getTotal(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getTotalDeal24(id)));
	}
	
	public double get24Price(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getStart24Price(id)));
	}
	
	public String getJsonString(int id ,int key){
		return redisCacheUtil.getCacheString(RedisKey.getJsonString(id, key));
	}
	
	public String getIndexJsonString(int id ,int key){
		return redisCacheUtil.getCacheString(RedisKey.getIndexJsonString(id, key));
	}
	
	public double getLowest(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLowestPrize24(id)));
	}
	
	public double getHighest(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getHighestPrize24(id)));
	}
	
	public double get24Total(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getTotalRMB24(id)));
	}
	
	public double getHighestBuyPrize(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getHighestBuyPrize(id)));
	}
	
	public double getLowestSellPrize(int id){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getLowestSellPrize(id)));
	}
	
	public double getRates(int vid,boolean isbuy,int level){
		return Double.valueOf(redisCacheUtil.getCacheStringToDouble(RedisKey.getRates(vid, isbuy, level)));
	}
	
	public String getBuyDepthMap(int symbol){
		return redisCacheUtil.getCacheString(RedisKey.getBuyDepth(symbol));
	}
	
	public String getSellDepthMap(int symbol){
		return redisCacheUtil.getCacheString(RedisKey.getSellDepth(symbol));
	}

	@Override
	public String getEntrustSuccessMap(int symbol) {
		return redisCacheUtil.getCacheString(RedisKey.getEntrustSuccess(symbol));
	}
}
