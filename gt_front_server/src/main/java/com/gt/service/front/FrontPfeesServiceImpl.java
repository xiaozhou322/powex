package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PfeesDAO;
import com.gt.entity.Pfees;



@Service("frontPfeesService")
public class FrontPfeesServiceImpl implements FrontPfeesService {
	@Autowired
	private PfeesDAO pfeesDAO;
	
	public void save(Pfees instance) {
		pfeesDAO.save(instance);
	}
	
	public Pfees findById(java.lang.Integer id) {
		return pfeesDAO.findById(id);
	}
	
	
	public void update(Pfees instance) {
		pfeesDAO.attachDirty(instance);
	}
	
	public List<Pfees> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pfeesDAO.list(firstResult, maxResults, filter, isFY);
	}
}
