package com.gt.Enum;

//资金操作类型
public class CapitalOperationTypeEnum {
	public static final int RMB_IN = 1 ;
	public static final int RMB_OUT = 2 ;
	public static final int USDT_IN = 3 ;
	public static final int USDT_OUT = 4 ;
	public static final int AGENT_USDT_IN = 5 ;
	public static final int AGENT_USDT_OUT = 6 ;
	public static final int AGENT_TRANS_USDT_IN = 7 ;
	public static final int AGENT_TRANS_USDT_OUT = 8 ;
	public static String getEnumString(int value) {
		String name = "";
		if(value == RMB_IN){
			name = "人民币充值";
		}else if(value == RMB_OUT){
			name = "人民币提现";
		}else if(value == USDT_IN){
			name = "USDT购买";
		}else if(value == USDT_OUT){
			name = "USDT兑换";
		}else if(value == AGENT_USDT_IN){
			name = "出售USDT";
		}else if(value == AGENT_USDT_OUT){
			name = "购买USDT";
		}else if(value == AGENT_TRANS_USDT_IN){
			name = "转入USDT";
		}else if(value == AGENT_TRANS_USDT_OUT){
			name = "转出USDT";
		}
		return name;
	}
}
