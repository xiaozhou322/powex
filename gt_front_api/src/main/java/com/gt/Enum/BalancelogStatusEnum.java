package com.gt.Enum;


public class BalancelogStatusEnum {
    public static final int BALANCE_NOEFFECT_VALUE = 1;//未生效
    public static final int BALANCE_WAIT_VALUE = 2;//等待收益
    public static final int BALANCE_RECEIVE_VALUE = 3;//已收益
    public static final int BALANCE_SENDING_VALUE = 4;//已收益
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BALANCE_NOEFFECT_VALUE){
			name = "未生效";
		}else if(value == BALANCE_WAIT_VALUE){
			name = "等待收益";
		}else if(value == BALANCE_RECEIVE_VALUE){
			name = "已收益";
		}else if(value == BALANCE_SENDING_VALUE){
			name = "发放收益中";
		}
		return name;
	}
    
}
