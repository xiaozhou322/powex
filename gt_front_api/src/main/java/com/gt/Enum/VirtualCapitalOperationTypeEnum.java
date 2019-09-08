package com.gt.Enum;

//资金操作类型
public class VirtualCapitalOperationTypeEnum {
	public static final int COIN_IN = 1 ;
	public static final int COIN_OUT = 2 ;
	public static final int COIN_TRANSFER = 3 ;
	public static String getEnumString(int value) {
		String name = "";
		if(value == COIN_IN){
			name = "虚拟币充值";
		}else if(value == COIN_OUT){
			name = "虚拟币提现";
		}else if(value == COIN_TRANSFER){
			name = "虚拟币转账";
		}
		return name;
	}
}
