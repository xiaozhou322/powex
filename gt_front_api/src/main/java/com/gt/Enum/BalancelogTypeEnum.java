package com.gt.Enum;

public class BalancelogTypeEnum {
    public static final int COIN = 1;//正常
    public static final int CNY = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == COIN){
			name = "虚拟币";
		}else if(value == CNY){
			name = "人民币";
		}
		return name;
	}
    
}
