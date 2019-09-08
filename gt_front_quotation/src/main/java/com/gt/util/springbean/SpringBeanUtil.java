package com.gt.util.springbean;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gt.service.front.FrontUserService;


@Service/**项目启动时扫描到此类使用了注解，加入到spring的容器**/
public class SpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取applicationContext对象
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据bean的id来查找对象
	 * @param id
	 * @return
	 */
	public static Object getBeanById(String id) {
		return applicationContext.getBean(id);
	}
	
	/**
	 * 根据bean的name来查找对象
	 * @param id
	 * @return
	 */
    public static Object getBeanByName(String beanName) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanName);
    }
    
	/**
	 * 根据bean的class来查找所有的对象(包括子类)
	 * @param c
	 * @return
	 */
	public static Map<?, ?> getBeansByClass(Class<?> c) {
		return applicationContext.getBeansOfType(c);
	}
	
	/**   
	 * @Title:  getThreadPoolTaskExecutor   
	 * @Description: 获取线程池 
	 */
	/*public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return (ThreadPoolTaskExecutor)applicationContext.getBean("taskExecutor");
	}*/
	
	public static FrontUserService getFrontUserService() {
		return (FrontUserService)applicationContext.getBean("frontUserService");
	}

}

