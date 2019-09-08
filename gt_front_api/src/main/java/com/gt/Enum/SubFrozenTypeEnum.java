package com.gt.Enum;

public class SubFrozenTypeEnum {
    public static final int NO_VALUE = 0;//正常
    public static final int DAY_VALUE = 1;//禁用
    public static final int MON_VALUE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NO_VALUE){
			name = "限时众筹";
		}else if(value == DAY_VALUE){
			name = "按天解冻";
		}else if(value == MON_VALUE){
			name = "按月解冻";
		}
		return name;
	}
    
}
