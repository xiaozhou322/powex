package com.gt.Enum;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class QuestionStatusEnum {
    public static final int NOT_SOLVED = 1;//未解决
    public static final int SOLVED = 2;//解决
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NOT_SOLVED){
			name = "未解决";
		}else if(value == SOLVED){
			name = "解决";
		}
		return name;
	}
    
    public static String getEnumString(int value,HttpServletRequest request) {
		String name = "";
		if(value == NOT_SOLVED){
			name = "未解决";
			name = getLocaleMessage(request,null,"enum.question.status."+value);
		}else if(value == SOLVED){
			name = "解决";
			name = getLocaleMessage(request,null,"enum.question.status."+value);
		}
		return name;
	}
    
    public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
	    WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	    return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
	}
    
}
