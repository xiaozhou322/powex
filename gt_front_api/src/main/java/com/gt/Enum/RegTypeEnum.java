package com.gt.Enum;

public class RegTypeEnum {
    public static final int EMAIL_VALUE = 1;//正常
    public static final int TEL_VALUE = 0;//禁用
    public static final int WX_VALUE = 2;//禁用
    public static final int QQ_VALUE = 3;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == EMAIL_VALUE){
			name = "邮箱注册";
		}else if(value == TEL_VALUE){
			name = "手机注册";
		}else if(value == WX_VALUE){
			name = "微信注册";
		}else if(value == QQ_VALUE){
			name = "QQ注册";
		}
		return name;
	}
    
}
