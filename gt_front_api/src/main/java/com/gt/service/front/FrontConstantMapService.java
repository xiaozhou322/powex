package com.gt.service.front;

import java.util.Map;

public interface FrontConstantMapService {
	
	public Map<String, Object> getMap();
	
	public Object get(String key);
	
	public String getString(String key);
	
	public void put(String key,Object value);
	
	
	/**
	 * 对比redis缓存的内存map
	 * @param key
	 * @param version
	 */
	public void putRedisMap(String key, Integer version);
	
	public Integer getRedisMap(String key);
}
