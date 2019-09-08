package com.gt.Enum;


/**
 * OTC订单申诉处理状态
 * @author zhouyong
 *
 */
public class OtcAppealStatusEnum {
	public static final int Notprocessed = 1001 ;//未处理
	public static final int fail = 1002 ;//申诉失败	
	public static final int success = 1003 ;//申诉成功
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case Notprocessed:
			name = "未处理" ;
			break;
		case success:
			name = "申诉成功" ;
			break;
		case fail:
			name = "申诉失败" ;	
			break;
		
		}
		return name;
	}
}
