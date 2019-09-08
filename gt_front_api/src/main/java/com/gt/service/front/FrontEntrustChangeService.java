package com.gt.service.front;

import com.gt.entity.Fentrust;

public interface FrontEntrustChangeService {
	
	public void addEntrustLimitBuyMap(int symbol,Fentrust fentrust);
	
	public void addEntrustBuyMap(int symbol,Fentrust fentrust);
	
	public void addEntrustLimitSellMap(int symbol,Fentrust fentrust);
	
	public void addEntrustSellMap(int symbol,Fentrust fentrust);
	
	public void removeEntrustLimitBuyMap(int symbol,Fentrust fentrust);
	
	public void removeEntrustBuyMap(int symbol,Fentrust fentrust);
	
	public void removeEntrustLimitSellMap(int symbol,Fentrust fentrust);

	public void removeEntrustSellMap(int symbol,Fentrust fentrust);
	
	public void initPeriodMap(int symbol);
}
