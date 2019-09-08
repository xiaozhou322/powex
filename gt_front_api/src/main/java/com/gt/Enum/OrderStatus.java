package com.gt.Enum;

public class OrderStatus {
	public static final int WaitingPay = 1 ;//建立了订单
	public static final int HavePayed = 2 ;//已付款
	public static final int ConfirmMoney = 3 ;//确认收款，订单完成
	public static final int Invalidate = 4 ;//失效
	public static final int SystemCancel = 5 ;//系统取消
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case WaitingPay:
			name = "待付款" ;
			break;
		case HavePayed:
			name = "已付款" ;	
			break;
		case ConfirmMoney:
			name = "交易成功" ;
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
