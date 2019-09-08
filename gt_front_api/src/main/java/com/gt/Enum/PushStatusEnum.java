package com.gt.Enum;

public class PushStatusEnum {
    public static final int WAIT = 1;
    public static final int BUY_CANCEL = 2;
    public static final int SELL_CANCEL = 3;
    public static final int SUCCESS = 4;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == WAIT){
			name = "等待买家确认";
		}else if(value == BUY_CANCEL){
			name = "买家取消";
		}else if(value == SELL_CANCEL){
			name = "卖家撤消";
		}else if(value == SUCCESS){
			name = "交易成功";
		}
		return name;
	}
    
}
