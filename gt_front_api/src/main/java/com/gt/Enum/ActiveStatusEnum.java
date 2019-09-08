package com.gt.Enum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 币种兑换激活状态数据字典
 * @author zhouyong
 *
 */
public class ActiveStatusEnum {

	/**待激活**/
	public static final int wait_active = 1;
	/**已激活**/
	public static final int has_active = 2;
	/**放弃激活**/
	public static final int no_active = 3;
	
	
	private static Map<Integer,String> dataMap = new LinkedHashMap<Integer, String>();
	
	static{
		dataMap.put(wait_active, "待激活");
		dataMap.put(has_active, "已激活");
		dataMap.put(no_active, "放弃激活");
	}
	
	public static Map<Integer,String> getAll(){
		return dataMap;
	}
	
	public static String getDescription(Integer key){
		return dataMap.get(key);
	}
}
