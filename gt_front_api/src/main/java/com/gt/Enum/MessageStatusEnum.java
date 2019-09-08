package com.gt.Enum;

public class MessageStatusEnum {
    public static final int NOREAD_VALUE = 1;//
    public static final int READ_VALUE = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NOREAD_VALUE){
			name = "未读";
		}else if(value == READ_VALUE){
			name = "已读";
		}
		return name;
	}
    
}
