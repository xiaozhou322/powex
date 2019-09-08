package com.gt.Enum;

import java.util.HashMap;
import java.util.Map;


public class FactivityWayEnum {
    public static final int lottery = 1;//抽奖
    public static final int Transa_rank = 2;//交易排名
    public static final int recharge = 3;//充值奖励
    public static final int Airdrop = 4;//空投
    public static final int red_pocket = 5;//红包
    public static Map<Integer,String> getEnumMap(){
    	Map<Integer,String> map =new HashMap<Integer,String>();
    	map.put(0, "请选择");
    	map.put(lottery, "抽奖");
    	map.put(Transa_rank,"交易排名");
    	map.put(recharge, "充值奖励");
    	map.put(Airdrop,"空投");
    	map.put(red_pocket,"红包");
    	
    	return map;
    } 
       
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case lottery:
			name = "抽奖";
			break;
		case Transa_rank:
			name = "交易排名";
			break;
		case recharge:
			name = "充值奖励";
			break;
		case Airdrop:
			name = "空投";
			break;
		case red_pocket:
			name = "红包";
			break;
		}
		return name;
	}
    
}
