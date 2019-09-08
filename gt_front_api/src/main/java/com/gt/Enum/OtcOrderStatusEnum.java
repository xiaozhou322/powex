package com.gt.Enum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OTC订单状态
 * @author zhouyong
 *
 */
public class OtcOrderStatusEnum {
	public static final int noeffected = -1 ;//未生效
	public static final int notreceived = 101 ;//未接单
	public static final int Payable = 102 ;//待支付
	public static final int Paid = 103 ;//已付款 
	
	public static final int Recognitionreceipt = 104 ;//已确认收款
	public static final int ExceptionOrder = 105 ;//异常订单
	public static final int failOrder = 106 ;//失败
	public static final int success = 107 ;//成功
	

	private static Map<Integer,String> dataMap = new LinkedHashMap<Integer, String>();
	static{
		dataMap.put(-10, "全部");
		dataMap.put(-1, "未生效");
		dataMap.put(101, "未接单");
		dataMap.put(102, "待付款");
		dataMap.put(103, "已付款");
		dataMap.put(104, "已确认收款");
		dataMap.put(105, "异常订单");
		dataMap.put(106, "失败");
		dataMap.put(107, "成功");
	
	}
	public static Map<Integer,String> getAll(){
		return dataMap;
	}
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case noeffected:
			name = "未生效" ;
			break;
		case notreceived:
			name = "未接单" ;
			break;
		case Payable:
			name = "待支付" ;
			break;
		case Paid:
			name = "已付款" ;	
			break;
		case Recognitionreceipt:
			name = "已确认收款" ;
			break;
		case ExceptionOrder:
			name = "异常订单  " ;
			break;
		
	    case failOrder:
		name = "失败" ;
		break;

        case success:
	   name = "成功" ;
	    break;
		}
		return name;
	}
}
