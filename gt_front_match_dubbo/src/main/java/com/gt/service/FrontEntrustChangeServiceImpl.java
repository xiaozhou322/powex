package com.gt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.auto.AutoDealMaking;
import com.gt.auto.AutoDealingOneDayData;
import com.gt.auto.KlinePeriodData;
import com.gt.auto.RealTimeData;
import com.gt.entity.Fentrust;
import com.gt.entity.Ftrademapping;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FtradeMappingService;
import com.gt.util.Utils;

@Service("frontEntrustChangeService")
public class FrontEntrustChangeServiceImpl implements FrontEntrustChangeService{
	
	@Autowired
	private RealTimeData realTimeData;
	
	@Autowired
	private KlinePeriodData klinePeriodData ;
	
	@Autowired
	private AutoDealMaking autoDealMaking ;
	
	@Autowired
	private AutoDealingOneDayData autoDealingOneDayData ;
	
	@Autowired
	private FtradeMappingService ftradeMappingService;
	
	public void addEntrustLimitBuyMap(int symbol,Fentrust fentrust){
		
		realTimeData.addEntrustLimitBuyMap(symbol, fentrust);	
	}
	
	public void addEntrustBuyMap(int symbol,Fentrust fentrust){
		
		realTimeData.addEntrustBuyMap(symbol, fentrust);
	}
	
	public void addEntrustLimitSellMap(int symbol,Fentrust fentrust){
		
		realTimeData.addEntrustLimitSellMap(symbol, fentrust);
	}
	
	public void addEntrustSellMap(int symbol,Fentrust fentrust){
		
		realTimeData.addEntrustSellMap(symbol, fentrust);
	}
	
	public void removeEntrustLimitBuyMap(int symbol,Fentrust fentrust){

		realTimeData.removeEntrustLimitBuyMap(symbol, fentrust);
	}
	
	public void removeEntrustBuyMap(int symbol,Fentrust fentrust){

		realTimeData.removeEntrustBuyMap(symbol, fentrust);
	}
	
	public void removeEntrustLimitSellMap(int symbol,Fentrust fentrust){

		realTimeData.removeEntrustLimitSellMap(symbol, fentrust);
	}

	public void removeEntrustSellMap(int symbol,Fentrust fentrust){	
		
		realTimeData.removeEntrustSellMap(symbol, fentrust);
	}

	public void initPeriodMap(int symbol) {
		
		klinePeriodData.initPeriodMap(symbol);
		klinePeriodData.initPeriods(symbol);
		
		Ftrademapping ftrademapping = ftradeMappingService.findFtrademapping2(symbol);
		
		realTimeData.initOneMarket(ftrademapping);
		
		long time = Utils.getTimestamp().getTime();
		autoDealingOneDayData.calcOneMarket(ftrademapping, time);
		autoDealMaking.startNewMarket(symbol);

		
		
		
	}
}
