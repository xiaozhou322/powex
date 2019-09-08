package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PprojectDAO;
import com.gt.entity.Pdomain;
import com.gt.entity.Pproject;



@Service("frontPprojectService")
public class FrontPprojectServiceImpl implements FrontPprojectService {
	@Autowired
	private PprojectDAO pprojectDAO;
	
	public void save(Pproject instance) {
		pprojectDAO.save(instance);
	}
	
	public Pproject findById(java.lang.Integer id) {
		return pprojectDAO.findById(id);
	}
	
	
	public void update(Pproject instance) {
		pprojectDAO.attachDirty(instance);
	}
	
	
	public List<Pproject> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pprojectDAO.list(firstResult, maxResults, filter, isFY);
	}

	
	public Pproject findByProperty(String propertyName, Object value) {
		return pprojectDAO.findByProperty(propertyName, value);
	}
}
