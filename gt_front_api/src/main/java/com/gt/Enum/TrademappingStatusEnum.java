package com.gt.Enum;

public class TrademappingStatusEnum {
	public static final int ACTIVE = 1;//正常显示
    public static final int FOBBID = 2;//禁用，不能交易，不能显示
    public static final int PAUSE = 3;//停牌，不能交易，可以显示
    public static final int HIDDEN = 4;//隐藏主站列表显示，项目方专区可以显示，仅可以使用ID方法，允许交易
    public static final int PAUSE_HIDDEN = 7;//停牌隐藏，项目方专区可以显示，不允许交易
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case ACTIVE:
			name = "正常";
			break;
		case FOBBID:
			name = "禁用";
			break;
		case PAUSE:
			name = "停牌";
			break;
		case HIDDEN:
			name = "隐藏";
			break;
		case PAUSE_HIDDEN:
			name = "停牌隐藏";
			break;
		}
		return name;
	}
    
}
