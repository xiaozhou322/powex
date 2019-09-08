package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FscoreSettingDAO;
import com.gt.entity.FscoreSetting;

@Service("scoreSettingService")
public class ScoreSettingServiceImpl implements ScoreSettingService {
	@Autowired
	private FscoreSettingDAO scoreSettingDAO;

	public FscoreSetting findById(int id) {
		return this.scoreSettingDAO.findById(id);
	}

	public void saveObj(FscoreSetting obj) {
		this.scoreSettingDAO.save(obj);
	}

	public void deleteObj(int id) {
		FscoreSetting obj = this.scoreSettingDAO.findById(id);
		this.scoreSettingDAO.delete(obj);
	}

	public void updateObj(FscoreSetting obj) {
		this.scoreSettingDAO.attachDirty(obj);
	}

	public List<FscoreSetting> findByProperty(String name, Object value) {
		return this.scoreSettingDAO.findByProperty(name, value);
	}

	public List<FscoreSetting> findAll() {
		return this.scoreSettingDAO.findAll();
	}

	public List<FscoreSetting> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.scoreSettingDAO.list(firstResult, maxResults, filter,isFY);
	}
}