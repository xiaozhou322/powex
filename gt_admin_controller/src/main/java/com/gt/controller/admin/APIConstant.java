package com.gt.controller.admin;

import java.util.HashMap;

import net.sf.json.JSONObject;


public class APIConstant {
	public static HashMap<String, Integer> actionMap = new HashMap<String, Integer>() ;
	static {
		actionMap.put("Invalidate", 0);

		int i = 101 ;
		//actionMap.put("coolist", i++);
		
		i=201 ;
		actionMap.put("coolist", i++);	//提现列表
		actionMap.put("coodetail", i++);//提现详情
		
		i=301 ;
		actionMap.put("coollock", i++);//提现锁定
		actionMap.put("coolunlock", i++);//提现锁定
		actionMap.put("coolaudit", i++);//提现审核
		
		actionMap.put("reg", i++);//创建用户
		actionMap.put("kyc1", i++);//kyc1认证
		actionMap.put("kyc2", i++);//kyc2认证
		actionMap.put("tradepwd", i++);//设置交易密码
		actionMap.put("google", i++);//设置谷歌认证
		actionMap.put("recharge", i++);//充值
		actionMap.put("luckydraw", i++);//摇奖操作
		actionMap.put("bindphone", i++);//绑定手机
		actionMap.put("bindemail", i++);//绑定邮箱
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