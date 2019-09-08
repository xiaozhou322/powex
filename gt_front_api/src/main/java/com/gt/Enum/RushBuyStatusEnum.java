package com.gt.Enum;

public class RushBuyStatusEnum {
	public static final int WAITCHECK = 0;//
    public static final int SUCCCHECK = 1;//
    public static final int NOCHECK = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == WAITCHECK){
			name = "待抽签";
		}else if(value == SUCCCHECK){
			name = "中签";
		}else if(value == NOCHECK){
			name = "未中签";
		}
		return name;
	}
    
}
