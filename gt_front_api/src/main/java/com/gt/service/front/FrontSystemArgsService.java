package com.gt.service.front;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gt.entity.Fwebbaseinfo;

@Service
public interface FrontSystemArgsService {

	/**
	 * 根据key查询系统配置
	 * @param key
	 * @return
	 */
	public String getSystemArgs(String key);
	
	/**
	 * 查询所有系统配置
	 * @return
	 */
	public Map<String, Object> findAllMap();
	
	/**
	 * 根据id查询网站版权信息
	 * @param id
	 * @return
	 */
	public Fwebbaseinfo findFwebbaseinfoById(int id);
	
}
