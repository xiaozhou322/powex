package com.gt.Enum;

public class CoinTypeEnum {
    public static final int FB_CNY_VALUE = 0;//法币-人民币
    public static final int FB_COIN_VALUE = 1;//法币-虚拟币
    public static final int COIN_VALUE = 2;//普通虚拟币
    public static final int FB_USDT_VALUE = 4;//法币-USDT
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == FB_CNY_VALUE){
			name = "法币-人民币";
		}else if(value == FB_COIN_VALUE){
			name = "法币-虚拟币";
		}else if(value == COIN_VALUE){
			name = "普通虚拟币";
		}else if(value == FB_USDT_VALUE){
			name = "USDT";
		}
		return name;
	}
    
}
