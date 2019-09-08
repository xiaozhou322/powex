package com.gt.Enum;

public class RemittanceTypeEnum {//汇款币种：人民币、USDT、atm
    public static final int Type1 = 0;//法币人民币
    public static final int Type2 = 2;//ATM机
    public static final int Type3 = 3;//网上银行
    public static final int Type4 = 4;//法币USDT
   
    public static String getEnumString(int value) {
		String name = "";
		if(value == Type1){
			name = "法币-人民币";
		}else if(value == Type2){
			name = "网银直充";
		}else if(value == Type3){
			name = "支付宝转账";
		}else if(value == Type4){
			name = "法币-USDT";
		}
		return name;
	}
}
