package com.gt.Enum;

public class NperStatusEnum {

	/**
	 * 未开启
	 */
	public static final int NO_START = 1;
	
	/**
	 * 开启
	 */
	public static final int START_ING = 2;
	
	/**
	 * 摇奖
	 */
	public static final int DRAW_LOTTERY = 3;
	
	/**
	 * 结束
	 */
	public static final int END = 4;
	
	public static String getEnumString(int value) {
		String name = "";
		if(value == NO_START){
			name = "未开启";
		}else if(value == START_ING){
			name = "已开启";
		}else if(value == DRAW_LOTTERY){
			name = "摇奖中";
		}else if(value == END){
			name = "已结束";
		}
		return name;
	}
	
}
