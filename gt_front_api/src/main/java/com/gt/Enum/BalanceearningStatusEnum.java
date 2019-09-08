package com.gt.Enum;

public class BalanceearningStatusEnum {
    public static final int WAIT_VALUE = 1;//转入
    public static final int SEND_VALUE = 2;//转出
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == WAIT_VALUE){
			name = "等待发放";
		}else if(value == SEND_VALUE){
			name = "已发放";
		}
		return name;
	}
    
}
