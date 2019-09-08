package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FlevelSettingDAO;
import com.gt.entity.FlevelSetting;

@Service("levelSettingService")
public class LevelSettingServiceImpl implements LevelSettingService{
	@Autowired
	private FlevelSettingDAO levelSettingDAO;

	public FlevelSetting findById(int id) {
		return this.levelSettingDAO.findById(id);
	}

	public void saveObj(FlevelSetting obj) {
		this.levelSettingDAO.save(obj);
	}

	public void deleteObj(int id) {
		FlevelSetting obj = this.levelSettingDAO.findById(id);
		this.levelSettingDAO.delete(obj);
	}

	public void updateObj(FlevelSetting obj) {
		this.levelSettingDAO.attachDirty(obj);
	}

	public List<FlevelSetting> findByProperty(String name, Object value) {
		return this.levelSettingDAO.findByProperty(name, value);
	}

	public List<FlevelSetting> findAll() {
		return this.levelSettingDAO.findAll();
	}

	public List<FlevelSetting> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.levelSettingDAO.list(firstResult, maxResults, filter,isFY);
	}
}