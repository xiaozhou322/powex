package com.gt.Enum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 广告类型数据字典
 * @author zhouyong
 *
 */
public class OtcOrderTypeEnum {

	/**出售**/
	public static final int sell_order = 1;
	/**购买**/
	public static final int buy_order = 2;
	
	
	private static Map<Integer,String> dataMap = new LinkedHashMap<Integer, String>();
	
	static{
		dataMap.put(-10, "全部");
		dataMap.put(sell_order, "出售");
		dataMap.put(buy_order, "购买");
	}
	
	public static Map<Integer,String> getAll(){
		return dataMap;
	}
	
	public static String getDescription(Integer key){
		return dataMap.get(key);
	}
}
