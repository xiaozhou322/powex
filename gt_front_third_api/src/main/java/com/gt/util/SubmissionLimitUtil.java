package com.gt.util;

import com.gt.util.redis.RedisCacheUtil;

/**
 * 
* @ClassName: SubmissionLimitUtil  
* @Description: 控制访问频繁工具类  
* @author Ryan  
* @date 2018年12月12日
* @version   
*
 */
public class SubmissionLimitUtil {
	
	private static RedisCacheUtil redisCacheUtil = (RedisCacheUtil) ApplicationContextUtil.getBean("redisCacheUtil");
	
	//过期时间60s
	private final static int EXPIRATION 	= 60;
	//标志位
	private final static String DATA 		= "X";
	//访问限制
	private final static int LIMIT 			= 100;
	
	public static boolean orderSubmissionLimit(String key){
		
		String flag = redisCacheUtil.getCacheString(key+DATA);
		int num = redisCacheUtil.getCacheInteger(key);
		
		if(flag == null){
			redisCacheUtil.setCacheObject(key+DATA, DATA, EXPIRATION);
			redisCacheUtil.setCacheObject(key, "1", EXPIRATION);
			return true;
		}else{
			if(num > LIMIT){
				return false;
			}else{
				redisCacheUtil.setCacheObject(key, num+1+"", EXPIRATION);
				return true;
			}
		}
	}
}
