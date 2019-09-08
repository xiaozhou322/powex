package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.Psystemconfig;

/**
 * 项目方系统配置表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
public interface FrontPsystemconfigService {
	public List<Psystemconfig> list(int firstResult, int maxResults, String filter,boolean isFY);

	public void save(Psystemconfig psystemconfig);

	public void update(Psystemconfig psystemconfig);
	
	public Psystemconfig findByProjectId(String filter);
	
	public Map<String, Object> findAllMap(String filter);
}
