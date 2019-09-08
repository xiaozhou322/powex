package com.gt.Enum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * otc订单操作日志类型数据字典
 * @author zhouyong
 *
 */
public class OtcOrderLogsTypeEnum {

	/**用户手动取消**/
	public static final int hand_cancel = 1;
	/**系统自动取消**/
	public static final int auto_cancel = 2;
	/**异常订单申诉失败**/
	public static final int appeal_failed = 3;
	/**异常订单申诉成功**/
	public static final int appeal_success = 4;
	/**管理员手动取消**/
	public static final int manager_cancel = 5;
	
	
	private static Map<Integer,String> dataMap = new LinkedHashMap<Integer, String>();
	
	static{
		dataMap.put(-10, "全部");
		dataMap.put(hand_cancel, "用户手动取消");
		dataMap.put(auto_cancel, "系统自动取消");
		dataMap.put(appeal_failed, "异常订单申诉失败");
		dataMap.put(appeal_success, "异常订单申诉成功");
		dataMap.put(manager_cancel, "管理员手动取消");
	}
	
	public static Map<Integer,String> getAll(){
		return dataMap;
	}
	
	public static String getDescription(Integer key){
		return dataMap.get(key);
	}
}
