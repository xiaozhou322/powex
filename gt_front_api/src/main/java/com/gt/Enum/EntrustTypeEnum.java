package com.gt.Enum;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class EntrustTypeEnum {
    public static final int BUY = 0;//买
    public static final int SELL = 1 ;//卖
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BUY){
			name = "买入";
		}else if(value == SELL){
			name = "卖出";
		}

		return name;
	}
    
    public static String getEnumString(int value,HttpServletRequest request) {
		String name = "";
		if(value == BUY){
			name = "买入";
			name = getLocaleMessage(request,null,"enum.entrust."+value);
		}else if(value == SELL){
			name = "卖出";
			name = getLocaleMessage(request,null,"enum.entrust."+value);
		}

		return name;
	}
    
    public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
	    WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	    return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
	}
}
