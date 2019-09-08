package com.gt.Enum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 产品周期数据字典
 * @author zhouyong
 *
 */
public class ProductPeriodEnum {

	/**3个月**/
	public static final int month_3 = 1;
	/**6个月**/
	public static final int month_6 = 2;
	/**12个月**/
	public static final int month_12 = 3;
	/**18个月**/
	public static final int month_18 = 4;
	
	
	private static Map<Integer,String> dataMap = new LinkedHashMap<Integer, String>();
	
	private static Map<Integer,Integer> dataValueMap = new LinkedHashMap<Integer, Integer>();
	
	static{
		dataMap.put(month_3, "3个月");
		dataMap.put(month_6, "6个月");
		dataMap.put(month_12, "12个月");
		dataMap.put(month_18, "18个月");
	}
	
	static{
		dataValueMap.put(month_3, 90);
		dataValueMap.put(month_6, 180);
		dataValueMap.put(month_12, 360);
		dataValueMap.put(month_18, 540);
	}
	
	public static Map<Integer,String> getAll(){
		return dataMap;
	}
	
	public static String getDescription(Integer key){
		return dataMap.get(key);
	}
	
	
	public static Map<Integer,Integer> getDataValueMap(){
		return dataValueMap;
	}
}
