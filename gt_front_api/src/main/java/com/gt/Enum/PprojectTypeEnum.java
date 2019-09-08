package com.gt.Enum;

/**
 * 项目方类型枚举
 * @author zhouyong
 *
 */
public class PprojectTypeEnum {
    public static final int PROJECT = 1;//项目方
    public static final int COMMUNITY = 2;//社群
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == PROJECT){
			name = "项目方";
		}else if(value == COMMUNITY){
			name = "社群";
		}
		return name;
	}
    
}
