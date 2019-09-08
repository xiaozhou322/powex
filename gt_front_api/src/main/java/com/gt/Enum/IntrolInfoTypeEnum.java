package com.gt.Enum;

import java.util.HashMap;
import java.util.Map;

public class IntrolInfoTypeEnum {
	public static final int INTROL_REG = 0;//注册认证奖励
    public static final int INTROL_INTROL = 1;//推荐奖励
    public static final int INTROL_RELEASE = 2;//锁仓释放
    public static final int INTROL_GT_BONUS = 3;//GT持币分红，待取消
    public static final int INTROL_GT_GAIN = 4;//GT锁仓利息
    public static final int INTROL_TRADE = 5;//交易挖矿收益
    public static final int INTROL_TRADE_INTROL = 6;//交易挖矿推荐人奖励
    public static final int INTROL_AIRDROP = 7;//空投
    public static final int INTROL_CANDY = 9;//糖果
    public static final int INTROL_RED_ENVELOPE = 10;//红包
    public static final int INTROL_TRADE_FEE = 11;//手续费返佣
    public static final int INTROL_RECHARHE = 12;//充值奖励
    public static final int INTROL_SUB = 13;//众筹奖励
    public static final int INTROL_LOTTERY = 14;//抽奖活动
    public static final int ETH10000= 15;//抽奖活动
    public static Map<Integer,String> getEnumMap(){
    	Map<Integer,String> map =new HashMap<Integer,String>();
    	map.put(INTROL_REG, "注册认证奖励");
    	map.put(INTROL_INTROL, "推荐奖励");
    	map.put(INTROL_RELEASE, "锁仓释放");
    	map.put(INTROL_GT_BONUS,  "GT分红");
    	map.put(INTROL_GT_GAIN,  "锁仓利息");
    	map.put(INTROL_TRADE, "挖矿收益");
    	map.put(INTROL_TRADE_INTROL, "挖矿推荐奖励");
    	map.put(INTROL_AIRDROP, "空投");
    	map.put(INTROL_CANDY,"糖果");
    	map.put(INTROL_RED_ENVELOPE,  "红包");
    	map.put(INTROL_TRADE_FEE, "手续费返佣");
    	map.put(INTROL_RECHARHE, "充值奖励");
    	map.put(INTROL_SUB, "众筹奖励");
    	map.put(INTROL_LOTTERY, "抽奖");
    	map.put(ETH10000, "万币抽奖");
    	return map;
    }
    
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == INTROL_REG){
			name = "注册认证奖励";
		}else if(value == INTROL_INTROL){
			name = "推荐奖励";
		}else if(value == INTROL_RELEASE){
			name = "锁仓释放";
		}else if(value == INTROL_GT_BONUS){
			name = "GT分红";
		}else if(value == INTROL_GT_GAIN){
			name = "锁仓利息";
		}else if(value == INTROL_TRADE){
			name = "挖矿收益";
		}else if(value == INTROL_TRADE_INTROL){
			name = "挖矿推荐奖励";
		}else if(value == INTROL_AIRDROP){
			name = "空投";
		}else if(value == INTROL_CANDY){
			name = "糖果";
		}else if(value == INTROL_RED_ENVELOPE){
			name = "红包";
		}else if(value == INTROL_TRADE_FEE){
			name = "手续费返佣";
		}else if(value == INTROL_RECHARHE){
			name = "充值奖励";
		}else if(value == INTROL_SUB){
			name = "众筹奖励";
		}else if(value == INTROL_LOTTERY){
			name = "抽奖";
		}else if(value == ETH10000){
			name = "万币抽奖";
		}
		return name;
	}
    
}
