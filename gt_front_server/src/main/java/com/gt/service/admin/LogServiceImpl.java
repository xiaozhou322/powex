package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FlogDAO;
import com.gt.entity.Flog;

@Service("logService")
public class LogServiceImpl implements LogService{
	@Autowired
	private FlogDAO logDAO;

	public Flog findById(int id) {
		return this.logDAO.findById(id);
	}

	public void saveObj(Flog obj) {
		this.logDAO.save(obj);
	}

	public void deleteObj(int id) {
		Flog obj = this.logDAO.findById(id);
		this.logDAO.delete(obj);
	}

	public void updateObj(Flog obj) {
		this.logDAO.attachDirty(obj);
	}

	public List<Flog> findByProperty(String name, Object value) {
		return this.logDAO.findByProperty(name, value);
	}

	public List<Flog> findAll() {
		return this.logDAO.findAll();
	}

	public List<Flog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.logDAO.list(firstResult, maxResults, filter,isFY);
	}
}