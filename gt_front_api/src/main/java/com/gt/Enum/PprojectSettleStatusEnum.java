package com.gt.Enum;

/**
 * 项目方收益结算枚举
 * @author zhouyong
 *
 */
public class PprojectSettleStatusEnum {
	
    public static final int WAIT_SETTLE = 0;        //待结算
    public static final int HAS_SETTLE = 1;       //已结算
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == WAIT_SETTLE){
			name = "待结算";
		}else if(value == HAS_SETTLE){
			name = "已结算";
		}
		return name;
	}
    
}
