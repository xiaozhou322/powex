package com.gt.service.front;

public interface FrontCacheService {
	
	public double getLatestDealPrize(int id);
	
	public double getTotal(int id);
	
	public double get24Price(int id);
	
	public String getJsonString(int id ,int key);
	
	public String getIndexJsonString(int id ,int key);
	
	public double getLowest(int id);
	
	public double getHighest(int id);
	
	public double get24Total(int id);
	
	public double getHighestBuyPrize(int id);
	
	public double getLowestSellPrize(int id);
	
	public double getRates(int vid,boolean isbuy,int level);
	
	public String getBuyDepthMap(int symbol);
	
	public String getSellDepthMap(int symbol);
	
	public String getEntrustSuccessMap(int symbol);
}
