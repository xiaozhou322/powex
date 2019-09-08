package com.gt.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FreleaselogDAO;
import com.gt.entity.Freleaselog;

@Service("releaseLogService")
public class ReleaseLogServiceImpl implements ReleaseLogService {
	@Autowired
	private FreleaselogDAO freleaselogDAO;
	@Autowired
	private HttpServletRequest request;

	public Freleaselog findById(int id) {
		return this.freleaselogDAO.findById(id);
	}

	public void saveObj(Freleaselog obj) {
		this.freleaselogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Freleaselog obj = this.freleaselogDAO.findById(id);
		this.freleaselogDAO.delete(obj);
	}

	public void updateObj(Freleaselog obj) {
		this.freleaselogDAO.attachDirty(obj);
	}

	public List<Freleaselog> findByProperty(String name, Object value) {
		return this.freleaselogDAO.findByProperty(name, value);
	}

	public List<Freleaselog> findAll() {
		return this.freleaselogDAO.findAll();
	}
	
	public List<Freleaselog> list(int firstResult, int maxResults,
			String filter) {
		List<Freleaselog> all = this.freleaselogDAO.list(firstResult, maxResults, filter,true);
		for (Freleaselog frelease : all) {
			frelease.getFuser().getFemail();
			frelease.getFvirtualcointype().getfShortName();
		}
		return all;
	}
	
	public List<Freleaselog> simplelist(int firstResult, int maxResults,
			String filter) {
		List<Freleaselog> all = this.freleaselogDAO.list(firstResult, maxResults, filter,false);
		return all;
	}
}
