package com.gt.Enum;

public class BalanceRecTypeEnum {
    public static final int CNY = 0;//正常
    public static final int XMB = 1;//禁用
    public static final int GWQ = 3;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == CNY){
			name = "CNY";
		}else if(value == XMB){
			name = "XMB";
		}else if(value == GWQ){
			name = "GWQ";
		}
		return name;
	}
    
}
