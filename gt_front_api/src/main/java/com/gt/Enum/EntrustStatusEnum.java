package com.gt.Enum;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class EntrustStatusEnum {
    public static final int Going = 1;//未成交
    public static final int PartDeal = 2 ;//部分成交
    public static final int AllDeal = 3;//完全成交
    public static final int Cancel = 4;//撤销
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == Going){
			name = "未成交";
		}else if(value == PartDeal){
			name = "部分成交";
		}else if(value == AllDeal){
			name = "完全成交";
		}else if(value == Cancel){
			name = "用户撤销";
		}
		return name;
	}
    
    public static String getEnumString(int value,HttpServletRequest request) {
		String name = "";
		if(value == Going){
			name = "未成交";
			name = getLocaleMessage(request,null,"enum.entrust.status."+value);
		}else if(value == PartDeal){
			name = "部分成交";
			name = getLocaleMessage(request,null,"enum.entrust.status."+value);
		}else if(value == AllDeal){
			name = "完全成交";
			name = getLocaleMessage(request,null,"enum.entrust.status."+value);
		}else if(value == Cancel){
			name = "用户撤销";
			name = getLocaleMessage(request,null,"enum.entrust.status."+value);
		}
		return name;
	}
    
    public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
	    WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	    return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
	}
}
