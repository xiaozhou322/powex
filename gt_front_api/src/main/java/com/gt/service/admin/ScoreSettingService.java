package com.gt.service.admin;

import java.util.List;

import com.gt.entity.FscoreSetting;

public interface ScoreSettingService {

	public FscoreSetting findById(int id);

	public void saveObj(FscoreSetting obj);

	public void deleteObj(int id);

	public void updateObj(FscoreSetting obj);

	public List<FscoreSetting> findByProperty(String name, Object value);

	public List<FscoreSetting> findAll();

	public List<FscoreSetting> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
}