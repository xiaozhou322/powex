package com.gt.auto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;



public class OneDayData {
	
	private Map<Integer, Double> lowestPrize24 = new HashMap<Integer, Double>() ;
	private Map<Integer, Double> highestPrize24 = new HashMap<Integer, Double>() ;
	private Map<Integer, Double> totalDeal24 = new HashMap<Integer, Double>() ;
	private Map<Integer, Double> start24Price = new HashMap<Integer, Double>() ;
	private Map<Integer, Double> totalRMB24 = new HashMap<Integer, Double>() ;
	
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	public double get24Price(int id) {
		Double f = this.start24Price.get(id) ;
		
		if(f==null){
			return 0F ;
		}else{
			return f ;
		}
	}
	
	public double get24Total(int id) {
		Double f = this.totalRMB24.get(id) ;
		
		if(f==null){
			return 0F ;
		}else{
			return f ;
		}
	}
	
	public double getLowest(int id){
		Double f = this.lowestPrize24.get(id) ;
		
		if(f==null){
			return 0F ;
		}else{
			return f ;
		}
	}
	public double getHighest(int id){
		Double f = this.highestPrize24.get(id) ;
		
		if(f==null){
			return 0F ;
		}else{
			return f ;
		}
	}
	public double getTotal(int id){
		Double f = this.totalDeal24.get(id) ;
		
		if(f==null){
			return 0F ;
		}else{
			return f ;
		}
	}
	
	public synchronized void putLowest(int id,double f){
		this.lowestPrize24.put(id, f) ;
		redisCacheUtil.setCacheObject(RedisKey.getLowestPrize24(id), f+"");
	}
	
	public synchronized void putHighest(int id,double f){
		this.highestPrize24.put(id, f) ;
		redisCacheUtil.setCacheObject(RedisKey.getHighestPrize24(id), f+"");
	}
	
	public synchronized void putTotal(int id,double f){
		this.totalDeal24.put(id, f) ;
		redisCacheUtil.setCacheObject(RedisKey.getTotalDeal24(id), f+"");
	}
	public synchronized void put24Price(int id,double f){
		this.start24Price.put(id, f) ;
		redisCacheUtil.setCacheObject(RedisKey.getStart24Price(id), f+"");
	}
	public synchronized void put24Total(int id,double f){
		this.totalRMB24.put(id, f) ;
		redisCacheUtil.setCacheObject(RedisKey.getTotalRMB24(id), f+"");
	}
	
	
}
