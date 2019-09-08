package com.gt.Enum;

public class SharePlanTypeEnum {
    public static final int NORMAL = 1;
    public static final int HANDING = 2;
    public static final int SCORE = 3;
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case NORMAL:
			name = "赠送人民币";
			break;
		case HANDING:
			name = "赠送虚拟币";
			break;
		case SCORE:
			name = "赠送积分";
			break;	
		}
		return name;
	}
    
}
