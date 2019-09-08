package com.gt.service.front;

public interface FrontMarketJsonService {
	
	public String marketRefresh(int symbol) throws Exception;
	
	public String marketRefresh2(int symbol) throws Exception;
	
	public String marketRefreshrose() throws Exception;
	
	public String period(int step,int symbol) throws Exception;
	
	public String depth(int symbol,int step) throws Exception;
	
	public String trade_json(int id) throws Exception;
	
	//websocket获取K线List数据
	public String period2(String step,int symbol,int num, Long time) throws Exception;
	//websocket获取最新K线数据
	public String lastPeriod(int symbol, String step, String type);
}
