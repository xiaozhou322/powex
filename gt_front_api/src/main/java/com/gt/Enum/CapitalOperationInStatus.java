package com.gt.Enum;

public class CapitalOperationInStatus {
	public static final int NoGiven = 1 ;//建立了充值，未补全充值信息
	public static final int WaitForComing = 2 ;//等待银行到账
	public static final int Come = 3 ;//到账
	public static final int Invalidate = 4 ;//失效
	public static final int SystemCancel = 5 ;//系统取消
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case NoGiven:
			name = "尚未付款" ;
			break;
		case WaitForComing:
			name = "等待银行到账" ;	
			break;
		case Come:
			name = "已经到账" ;
			break;
		case Invalidate:
			name = "用户撤销" ;
			break;
		case SystemCancel:
			name = "充值失败" ;
			break;
		}
		return name;
	}
}
