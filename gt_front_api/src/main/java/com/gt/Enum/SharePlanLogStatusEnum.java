package com.gt.Enum;

public class SharePlanLogStatusEnum {
    public static final int NOSEND = 1;//初始化
    public static final int HASSEND = 2;//已发送
    public static final int LOST = 3;//已发送
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NOSEND){
			name = "未领取";
		}else if(value == HASSEND){
			name = "已领取";
		}else if(value == LOST){
			name = "失效";
		}
		return name;
	}
}
