package com.gt.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {  
    private static ApplicationContext applicationContext;  
    
    
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  

    public void setApplicationContext(ApplicationContext applicationContext) {  
        ApplicationContextUtil.applicationContext = applicationContext;  
    }  

    public static Object getBean(String beanName) {  
        return applicationContext.getBean(beanName);  
    }  
}
