package com.gt.Enum;

public class VirtualCoinTradeTypeEnum {
    public static final int BUY = 1;//买
    public static final int SELL = 2;//卖
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BUY){
			name = "买入";
		}else if(value == SELL){
			name = "卖出";
		}
		return name;
	}
    
}
