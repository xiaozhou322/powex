package com.gt.Enum;

public class SharePlanStatusEnum {
    public static final int SAVE_VALUE = 1;//
    public static final int AUDITED_VALUE = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == SAVE_VALUE){
			name = "暂存";
		}else if(value == AUDITED_VALUE){
			name = "已审核";
		}
		return name;
	}
    
}
