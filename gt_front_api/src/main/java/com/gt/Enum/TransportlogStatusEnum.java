package com.gt.Enum;

public class TransportlogStatusEnum {
    public static final int NORMAL_VALUE = 2;//正常
    public static final int AUDIT_VALUE = 1;//禁用
    public static final int CANCEL_VALUE = 3;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL_VALUE){
			name = "待审核";
		}else if(value == AUDIT_VALUE){
			name = "已审核";
		}else if(value == CANCEL_VALUE){
			name = "已取消";
		}
		return name;
	}
    
}
