package com.gt.Enum;

/**
 * 项目方币种状态枚举
 * @author zhouyong
 *
 */
public class PCoinTypeStatusEnum {
	public static final int NORMAL = 1;//正常
    public static final int PAUSE = 2;//禁用
    public static final int RISK = 3;//风险
    public static final int HIDE = 4;//隐藏
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL){
			name = "正常";
		}else if(value == PAUSE){
			name = "暂停";
		}else if(value == RISK){
			name = "风险";
		}else if(value == HIDE){
			name = "隐藏";
		}
		return name;
	}
    
}
