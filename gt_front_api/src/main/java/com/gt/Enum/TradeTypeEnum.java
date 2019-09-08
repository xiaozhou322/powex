package com.gt.Enum;

public class TradeTypeEnum {
    public static final int BUY = 1;//
    public static final int SELL = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BUY){
			name = "买+卖";
		}else if(value == SELL){
			name = "买+卖";
		}
		return name;
	}
    
}
