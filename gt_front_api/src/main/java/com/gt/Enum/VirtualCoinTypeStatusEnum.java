package com.gt.Enum;

public class VirtualCoinTypeStatusEnum {
    public static final int Normal = 1;//正常
    public static final int Abnormal = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == Normal){
			name = "正常";
		}else if(value == Abnormal){
			name = "禁用";
		}
		return name;
	}
    
}
