package com.gt.Enum;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class QuestionTypeEnum {
    public static final int COIN_RECHARGE = 1;
    public static final int COIN_WITHDRAW = 2;
    public static final int CNY_RECHARGE = 3;
    public static final int CNY_WITHDRAW = 4;
    public static final int OTHERS = 5;
    
    public static String getEnumString(int value) {
    	
    	
		String name = "";
		switch (value) {
		case COIN_RECHARGE:
			name = "充值问题";
			break;
		case COIN_WITHDRAW:
			name = "提现问题";
			break;
		case CNY_RECHARGE:
			name = "业务问题";
			break;
		case CNY_WITHDRAW:
			name = "绑定问题";
			break;
		case OTHERS:
			name = "其他问题";
			break;
		}
		return name;
	}
    
public static String getEnumString(int value,HttpServletRequest request) {
    	
    	
		String name = "";
		switch (value) {
		case COIN_RECHARGE:
			name = "充值问题";
			name = getLocaleMessage(request,null,"enum.question."+value);
			break;
		case COIN_WITHDRAW:
			name = "提现问题";
			name = getLocaleMessage(request,null,"enum.question."+value);
			break;
		case CNY_RECHARGE:
			name = "业务问题";
			name = getLocaleMessage(request,null,"enum.question."+value);
			break;
		case CNY_WITHDRAW:
			name = "绑定问题";
			name = getLocaleMessage(request,null,"enum.question."+value);
			break;
		case OTHERS:
			name = "其他问题";
			name = getLocaleMessage(request,null,"enum.question."+value);
			break;
		}
		return name;
	}
    
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
	    WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	    return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
	}
}
