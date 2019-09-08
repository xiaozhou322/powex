package com.gt.Enum;

public class AutotradeStatusEnum {
    public static final int NORMAL = 1;//
    public static final int FORBIN = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL){
			name = "生效";
		}else if(value == FORBIN){
			name = "失效";
		}
		return name;
	}
    
}
