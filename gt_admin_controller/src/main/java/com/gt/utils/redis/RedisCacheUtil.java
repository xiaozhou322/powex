package com.gt.utils.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * redis 基本操作工具类
 * 
 * @author cgd
 * 
 * @param <T>
 */
@Service("redisCacheUtil")
public class RedisCacheUtil<T> {

	@Autowired
	@Qualifier("jedisTemplate")
	public RedisTemplate redisTemplate;

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * 
	 * @param key
	 *            缓存的键值
	 * @param value
	 *            缓存的值
	 * @return 缓存的对象
	 */
	public <T>ValueOperations<String, T> setCacheObject(String key, T value) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		operation.set(key, value);
		return operation;
	}

	/**
	 * 缓存基本的对象并设置缓存有效时间（秒），Integer、String、实体类等
	 * 
	 * @param key
	 * @param value
	 * @param time
	 * @return
	 */
	public <T> ValueOperations<String, T> setCacheObject(String key, T value,
			long timeout) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		operation.set(key, value, timeout, TimeUnit.SECONDS);
		return operation;
	}

	/**
	 * 获得缓存的基本对象。
	 * 
	 * @param key
	 *            缓存键值
	 * @param operation
	 * @return 缓存键值对应的数据
	 */
	public <T> T getCacheObject(String key) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}
	
	public String getCacheString(String key) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		Object object = redisTemplate.opsForValue().get(key);
		if(null == object){
			return null;
		}else{
			return object.toString();
		}
	}
	
	public String getCacheStringToDouble(String key) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		Object object = redisTemplate.opsForValue().get(key);
		if(null == object){
			return "0";
		}else{
			return object.toString();
		}
	}
	/**
	 * 检测key对应缓存是否存在
	 * 
	 * @param key
	 * @return
	 */
	public Boolean hasCache(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 缓存List数据
	 * 
	 * @param key
	 *            缓存的键值
	 * @param dataList
	 *            待缓存的List数据
	 * @return 缓存的对象
	 */
	public <T> ListOperations<String, T> setCacheList(String key,
			List<T> dataList) {
		ListOperations listOperation = redisTemplate.opsForList();
		if (null != dataList) {
			int size = dataList.size();
			for (int i = 0; i < size; i++) {
				listOperation.rightPush(key, dataList.get(i));
			}
		}
		return listOperation;
	}

	/**
	 * 获得缓存的list对象
	 * 
	 * @param key
	 *            缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public <T> List<T> getCacheList(String key) {
		List<T> dataList = new ArrayList<T>();
		ListOperations<String, T> listOperation = redisTemplate.opsForList();
		Long size = listOperation.size(key);
		for (int i = 0; i < size; i++) {
			dataList.add((T) listOperation.leftPop(key));
		}

		return dataList;
	}

	/**
	 * 缓存Set
	 * 
	 * @param key
	 *            缓存键值
	 * @param dataSet
	 *            缓存的数据
	 * @return 缓存数据的对象
	 */
	public <T> BoundSetOperations<String, T> setCacheSet(String key,
			Set<T> dataSet) {
		BoundSetOperations<String, T> setOperation = redisTemplate
				.boundSetOps(key);
		Iterator<T> it = dataSet.iterator();
		while (it.hasNext()) {
			setOperation.add(it.next());
		}
		return setOperation;
	}

	/**
	 * 获得缓存的set
	 * 
	 * @param key
	 * @param operation
	 * @return
	 */
	public Set<T> getCacheSet(String key) {
		Set<T> dataSet = new HashSet<T>();
		BoundSetOperations<String, T> operation = redisTemplate
				.boundSetOps(key);
		Long size = operation.size();
		for (int i = 0; i < size; i++) {
			dataSet.add(operation.pop());
		}
		return dataSet;
	}

	/**
	 * 缓存Map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public <T> HashOperations<String, String, T> setCacheMap(String key,
			Map<String, T> dataMap) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		if (null != dataMap) {
			for (Map.Entry<String, T> entry : dataMap.entrySet()) {
				hashOperations.put(key, entry.getKey(), entry.getValue());
			}
		}
		return hashOperations;
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 * @param hashOperation
	 * @return
	 */
	public <T> Map<String, T> getCacheMap(String key) {
		Map<String, T> map = redisTemplate.opsForHash().entries(key);
		return map;
	}

	/**
	 * 缓存Map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public <T> HashOperations<String, Integer, T> setCacheIntegerMap(
			String key, Map<Integer, T> dataMap) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		if (null != dataMap) {
			for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
				hashOperations.put(key, entry.getKey(), entry.getValue());
			}
		}
		return hashOperations;
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 * @return
	 */
	public <T> Map<Integer, T> getCacheIntegerMap(String key) {
		Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
		return map;
	}

	/**
	 * 删除指定的缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public void deleteCache(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 删除指定的缓存对象集合
	 * 
	 * @param key
	 * @return
	 */
	public void deleteCache(Collection keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 异步延时删除指定的缓存对象(毫秒)
	 * 
	 * @param key
	 * @return
	 */
	public void deleteCacheDelay(final String key, final long timeout) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(timeout);
					redisTemplate.delete(key);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 设置缓存过期时间(秒)
	 * 
	 * @param key
	 * @return
	 */
	public void expireCache(String key, int timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

}
