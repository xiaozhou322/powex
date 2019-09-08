package com.gt.Enum;

public class BalanceearningTypeEnum {
    public static final int COIN = 1;//正常
    public static final int SCORE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == COIN){
			name = "虚拟币";
		}else if(value == SCORE){
			name = "商城积分";
		}
		return name;
	}
    
}
