package com.gt.controller;

import java.util.HashMap;

import net.sf.json.JSONObject;


public class APIConstant {
	public static HashMap<String, Integer> actionMap = new HashMap<String, Integer>() ;
	static {
		actionMap.put("Invalidate", 0);

		int i = 101 ;
		actionMap.put("depth", i++);
		actionMap.put("kline", i++);
		actionMap.put("market", i++);
		actionMap.put("trades", i++);
		actionMap.put("tickers", i++);
		
		i=201 ;
		actionMap.put("cancel_entrust", i++);
		actionMap.put("trade", i++);
		actionMap.put("entrust", i++);
		actionMap.put("order", i++);
		actionMap.put("lastentrust", i++);
		actionMap.put("userinfo", i++);
		
		i=301 ;
		actionMap.put("withdraw", i++);
		actionMap.put("cancel_withdraw", i++);
		actionMap.put("withdraw_record", i++);
		
	};


	public synchronized static Integer getInteger(String action) {

		try {
			Integer actionInteger = actionMap.get(action);

			if (actionInteger == null) {
				actionInteger = 0;
			}

			return actionInteger;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int getInt(String param) {
		int ret = 0;
		try {
			ret = Integer.parseInt(param.trim());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return ret;
	}

	public static double getDouble(String param) {
		double ret = 0;
		try {
			ret = Double.parseDouble(param.trim());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return ret;
	}

	public static String getUnknownError(Exception e) {
		if(e != null){
			e.printStackTrace();
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(FAPIController.CODE, -10001);
		jsonObject.accumulate(FAPIController.MESSAGE, "网络错误，请稍后重试");

		return jsonObject.toString();
	}
}