package com.gt.Enum;

import java.util.HashMap;
import java.util.Map;


public class FactivityStatusEnum {
	public static final int Not_by = -1;//未通过
	public static final int Not_verify = 0;//未审核;
    public static final int Not_Open = 1;//未开启
    public static final int open = 2;//已开启
    public static final int end = 3;//已结束
    public static final int close = 4;//已取消

    public static Map<Integer,String> getEnumMap(){
    	Map<Integer,String> map =new HashMap<Integer,String>();
    	map.put(null, "请选择");
    	map.put(Not_verify, "未审核");
    	map.put(Not_by, "未通过");
    	map.put(Not_Open, "未开启");
    	map.put(open, "已开启");
    	map.put(end, "已结束");
    	map.put(close,  "已取消");

    	
    	return map;
    }
       
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case Not_by:
			name = "未通过";
			break;
		case Not_verify:
			name = "未审核";
			break;
		case Not_Open:
			name = "未开启";
			break;
		case open:
			name = "已开启";
			break;
		case end:
			name = "已结束";
			break;
		case close:
			name = "已取消";
			break;
		}
		return name;
	}
    
}
