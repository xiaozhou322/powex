package com.gt.Enum;

/**
 * 是否是订单超时等待申诉状态
 * @author zhouyong
 *
 */
public class OtcPendAppealEnum {
	public static final int yes = 1001 ;//
	public static final int no = 1002 ;//
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case yes:
			name = "是" ;
			break;
		case no:
			name = "否" ;
			break;
		
		
		}
		return name;
	}
}
