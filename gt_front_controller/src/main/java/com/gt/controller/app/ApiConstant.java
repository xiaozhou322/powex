package com.gt.controller.app;

import java.util.HashMap;

import net.sf.json.JSONObject;

public class ApiConstant {
	public static HashMap<String, Integer> actionMap = new HashMap<String, Integer>();
	static {
		int i = 0;
		actionMap.put("Invalidate", i++);// 无效

		actionMap.put("UserLogin", i++);// 登录
		actionMap.put("UserRegister", i++); // 注册
		actionMap.put("SendMessageCode", i++);
		actionMap.put("SendMailCode", i++);
		actionMap.put("FindLoginPassword", i++);

		actionMap.put("GetCoinListInfo", i++);// 每个币种的基础信息
		actionMap.put("GetVersion", i++);// 获取服务端最小版本号
		actionMap.put("GetMarketData", i++);// 行情
		actionMap.put("GetNews", i++);// 新闻
		actionMap.put("GetMyCenter", i++);// 个人中心
		actionMap.put("GetAbout", i++);// 个人中心
		actionMap.put("GetMarketDepth", i++);// 市场深度
		actionMap.put("RechargeBanks", i++);
		
		actionMap.put("AfterWeiXinLogin", i++);
		actionMap.put("AfterQQLogin", i++);
		
		actionMap.put("article", i++);		
		actionMap.put("aboutindex", i++);
		actionMap.put("GetEntrustlogs", i++); //最新成交记录
		// 需要token
		i = 201;
		actionMap.put("GetAccountInfo", i++); // 账号信息，用户信息+交易账号+放款账号信息
		actionMap.put("GetBtcRechargeListRecord", i++);// 虚拟币充值，返回充值地址和记录
		actionMap.put("GetEntrustInfo", i++);// 委托订单
		actionMap.put("CancelEntrust", i++);// 取消订单
		actionMap.put("BtcTradeSubmit", i++);// 下单
		actionMap.put("TradePassword", i++);// 交易密码
		actionMap.put("GetIntrolinfo", i++);// 提成明细
		actionMap.put("GetIntrolDetail", i++);

		actionMap.put("ViewValidateIdentity", i++);// 查看认证信息

		actionMap.put("ValidateIdentity", i++);
		actionMap.put("ValidateIdentity2", i++);
		
		actionMap.put("RechargeCny", i++);

		actionMap.put("bindPhone", i++);
		actionMap.put("UnbindPhone", i++);
		actionMap.put("ChangebindPhone", i++);

		actionMap.put("GetWithdrawBankList", i++);
		actionMap.put("SetWithdrawCnyBankInfo", i++);
		actionMap.put("WithDrawCny", i++);
		actionMap.put("GetWithdrawBtcAddress", i++);
		actionMap.put("SetWithdrawBtcAddr", i++);
		actionMap.put("WithdrawBtcSubmit", i++);
		actionMap.put("changePassword", i++);
		actionMap.put("GetAllRecords", i++);
		actionMap.put("GetSettingLogs", i++);
		actionMap.put("GetLoginLogs", i++);
		actionMap.put("PostQuestion", i++);
		actionMap.put("DelQuestion", i++);
		actionMap.put("ListQuestions", i++);
		actionMap.put("GeRechargeBankList", i++);
		actionMap.put("GetQuestionTypeList", i++);
		actionMap.put("getSecurity", i++);
		actionMap.put("googleAuth", i++);
		actionMap.put("getBindGoogle", i++);
		actionMap.put("bindMail", i++);
		
		actionMap.put("GetScoreLogs", i++);
		actionMap.put("getScoreRule", i++);
		actionMap.put("getLevelRule", i++);
		
		actionMap.put("getCoinAddress", i++);
		actionMap.put("GetVirtualCoinAddress", i++);
		actionMap.put("DetelCoinAddress", i++);
		actionMap.put("DeleteBankAddress", i++);
		actionMap.put("ValidateKyc", i++);
		actionMap.put("usdtManual", i++);
		actionMap.put("rechargeUsdt", i++);
		actionMap.put("rechargeUsdtSubmit", i++);
		actionMap.put("updateOutAddress", i++);
		actionMap.put("withdrawUsdtSubmit", i++);
		actionMap.put("exchangeUsdtSubmit", i++);
		actionMap.put("withdrawUsdt", i++);
		actionMap.put("rechargeBtc", i++);
		actionMap.put("getVirtualAddress", i++);
		actionMap.put("cancelRechargeUsdtSubmit", i++);
		actionMap.put("recordUsdt", i++);
		actionMap.put("usermessage", i++);
		actionMap.put("readMessage", i++);
		actionMap.put("cancelWithdrawusdt", i++);
		actionMap.put("faceValidateReq", i++);
		actionMap.put("changePassword2", i++);
		actionMap.put("drawAccountsSubmit", i++);
		actionMap.put("checkTradePwd", i++);
		actionMap.put("GetDrawaccountsCoinlist", i++);
		actionMap.put("GetAccountsByCoin", i++);
		
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
		}
		return ret;
	}

	public static double getDouble(String param) {
		double ret = 0;
		try {
			ret = Double.parseDouble(param.trim());
		} catch (Exception e) {
		}
		return ret;
	}

	public static String getUnknownError(Exception e) {
		if (e != null) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, -10000);
		jsonObject.accumulate(APP_API_Controller.Value, "网络错误，请稍后重试");

		return jsonObject.toString();
	}
}