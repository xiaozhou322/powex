package com.gt.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
* @ClassName: ApplicationContextUtil  
* @Description: 手动注入类  
* @author Ryan  
* @date 2018年11月21日
* @version   
*
 */
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
    
    public final static Object getBean(String beanName, Class<?> requiredType) {
    		return applicationContext.getBean(beanName, requiredType);
    }
}
