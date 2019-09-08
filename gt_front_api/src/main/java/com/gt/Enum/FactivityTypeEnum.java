package com.gt.Enum;


public class FactivityTypeEnum {
    public static final int disposable = 1;//一次性
    public static final int periodicity = 2;//周期性

    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case disposable:
			name = "一次性";
			break;
		case periodicity:
			name = "周期性";
			break;
		}
		return name;
	}
    
}
