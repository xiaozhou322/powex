package com.gt.Enum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付方式数据字典
 * @author zhouyong
 *
 */
public class OtcPayTypeEnum {

	/**网银**/
	public static final int UnionPay = 1;
	/**微信**/
	public static final int WeiXinPay = 2;
	/**支付宝**/
	public static final int AliPay = 3;
	
	
	private static Map<Integer,String> dataMap = new LinkedHashMap<Integer, String>();
	
	static{
		dataMap.put(UnionPay, "网银");
		dataMap.put(WeiXinPay, "微信");
		dataMap.put(AliPay, "支付宝");
	}
	
	public static Map<Integer,String> getAll(){
		return dataMap;
	}
	
	public static String getDescription(Integer key){
		return dataMap.get(key);
	}
}
