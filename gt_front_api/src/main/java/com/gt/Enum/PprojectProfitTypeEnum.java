package com.gt.Enum;

/**
 * 项目方收益类型枚举
 * @author zhouyong
 *
 */
public class PprojectProfitTypeEnum {
	
    public static final int BUY_COMMISSION = 1;        //买入返佣
    public static final int SELL_COMMISSION = 2;       //卖出返佣
    public static final int BUY_FEES = 3;              //买入手续费
    public static final int SELL_FEES = 4;             //卖出手续费
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BUY_COMMISSION){
			name = "买入返佣";
		}else if(value == SELL_COMMISSION){
			name = "卖出返佣";
		}else if(value == BUY_FEES){
			name = "买入手续费";
		}else if(value == SELL_FEES){
			name = "卖出手续费";
		}
		return name;
	}
    
}
