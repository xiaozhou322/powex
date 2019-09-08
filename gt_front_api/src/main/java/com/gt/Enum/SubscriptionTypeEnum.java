package com.gt.Enum;

public class SubscriptionTypeEnum {
	public static final int RMB = 1;//
    public static final int COIN = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case RMB:
			name = "人民币认购";
			break;
		case COIN:
			name = "虚拟币换购";
			break;
		}
		return name;
	}
    
}
