package com.gt.Enum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OTC订单申诉原因
 * @author zhouyong
 *
 */
public class OtcAppealReasonEnum {
	public static final int notreceived = 1 ;//买家申诉——已付款忘点我已付款
	public static final int Payable = 2 ;//买家申诉——已付款卖家未确认
	public static final int Paid = 3 ;//卖家申诉——买家未付款	
	public static final int Recognitionreceipt = 4 ;//卖家申诉——未收到款，错点确认收款
	
	
	private static Map<Integer,String> dataMap = new LinkedHashMap<Integer, String>();
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case notreceived:
			name = "买家申诉——已付款忘点我已付款" ;
			break;
		case Payable:
			name = "买家申诉——已付款卖家未确认" ;
			break;
		case Paid:
			name = "卖家申诉——买家未付款" ;	
			break;
		case Recognitionreceipt:
			name = "卖家申诉——未收到款，错点确认收款" ;
			break;
		}
		return name;
	}
	
	static{
		dataMap.put(notreceived, "买家申诉——已付款忘点我已付款");
		dataMap.put(Payable, "买家申诉——已付款卖家未确认");
		dataMap.put(Paid, "卖家申诉——买家未付款");
		dataMap.put(Recognitionreceipt, "卖家申诉——未收到款，错点确认收款");
	}
	
	
	public static Map<Integer,String> getAll(){
		return dataMap;
	}
	
	public static String getDescription(Integer key){
		return dataMap.get(key);
	}
}
