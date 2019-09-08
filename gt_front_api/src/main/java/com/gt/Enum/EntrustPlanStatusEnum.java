package com.gt.Enum;

public class EntrustPlanStatusEnum {
    public static final int Not_entrust = 1;//未委托
    public static final int Entrust = 2 ;//已委托
    public static final int Cancel = 3;//取消
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == Not_entrust){
			name = "未委托";
		}else if(value == Entrust){
			name = "已委托";
		}else if(value == Cancel){
			name = "用户撤销";
		}
		return name;
	}
    
}
