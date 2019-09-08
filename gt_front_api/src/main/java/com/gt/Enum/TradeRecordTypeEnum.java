package com.gt.Enum;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class TradeRecordTypeEnum {
    public static final int CNY_RECHARGE = 1;//人民币充值
    public static final int CNY_WITHDRAW = 2;//人民币提现
    public static final int BTC_RECHARGE = 3;//虚拟币充值
    public static final int BTC_WITHDRAW = 4;//虚拟币提现
    public static final int USDT_RECHARGE = 5;//USDT购买
    public static final int USDT_WITHDRAW = 6;//USDT兑换
    
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case CNY_RECHARGE:
			name = "CNY充值";
			break;
		case CNY_WITHDRAW:
			name = "CNY提现";
			break;
		case BTC_RECHARGE:
			name = "充值";
			break;
		case BTC_WITHDRAW:
			name = "提现";
			break;
		case USDT_RECHARGE:
			name = "USDT充值";
			break;
		case USDT_WITHDRAW:
			name = "USDT提现";
			break;
		}
		return name;
	}
    
    public static String getEnumString(int value,HttpServletRequest request) {
		String name = "";
		switch (value) {
		case CNY_RECHARGE:
			name = "CNY充值";
			name = getLocaleMessage(request,null,"enum.record.cny_recharge");
			break;
		case CNY_WITHDRAW:
			name = "CNY提现";
			name = getLocaleMessage(request,null,"enum.record.cny_withdraw");
			break;
		case BTC_RECHARGE:
			name = "充值";
			name = getLocaleMessage(request,null,"enum.record.btc_recharge");
			break;
		case BTC_WITHDRAW:
			name = "提现";
			name = getLocaleMessage(request,null,"enum.record.btc_withdraw");
			break;
		case USDT_RECHARGE:
			name = "USDT充值";
			name = getLocaleMessage(request,null,"enum.record.usdt_recharge");
			break;
		case USDT_WITHDRAW:
			name = "USDT提现";
			name = getLocaleMessage(request,null,"enum.record.usdt_withdraw");
			break;
		}
		return name;
	}
    
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
	    WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	    return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
	}
    
}
