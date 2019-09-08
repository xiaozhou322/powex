package com.gt.util.redis;
/**
 * 
* @ClassName: RedisCacheExpiringTime  
* @Description: redis缓存过期时间，单位秒 
* @author Ryan  
* @date 2018年7月24日
* @version   
*
 */
public class RedisExpiringTime {
	
	public final static int COMMONS = 30*24*60*60;		//公共有效期30天
	
	public final static int MARKET = 24*60*60;		//行情缓存有效时间1天
}
