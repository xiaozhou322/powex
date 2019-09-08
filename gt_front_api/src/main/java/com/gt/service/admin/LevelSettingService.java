package com.gt.service.admin;

import java.util.List;

import com.gt.entity.FlevelSetting;


public interface LevelSettingService {

	public FlevelSetting findById(int id);

	public void saveObj(FlevelSetting obj);

	public void deleteObj(int id);

	public void updateObj(FlevelSetting obj);

	public List<FlevelSetting> findByProperty(String name, Object value);

	public List<FlevelSetting> findAll();

	public List<FlevelSetting> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}