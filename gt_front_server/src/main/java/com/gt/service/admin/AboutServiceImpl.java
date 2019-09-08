package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FaboutDAO;
import com.gt.entity.Fabout;

@Service("aboutService")
public class AboutServiceImpl implements AboutService{
	@Autowired
	private FaboutDAO aboutDAO;

	public Fabout findById(int id) {
		return this.aboutDAO.findById(id);
	}

	public void saveObj(Fabout obj) {
		this.aboutDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fabout obj = this.aboutDAO.findById(id);
		this.aboutDAO.delete(obj);
	}

	public void updateObj(Fabout obj) {
		this.aboutDAO.attachDirty(obj);
	}

	public List<Fabout> findByProperty(String name, Object value) {
		return this.aboutDAO.findByProperty(name, value);
	}

	public List<Fabout> findAll() {
		return this.aboutDAO.findAll();
	}

	public List<Fabout> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.aboutDAO.list(firstResult, maxResults, filter,isFY);
	}
}