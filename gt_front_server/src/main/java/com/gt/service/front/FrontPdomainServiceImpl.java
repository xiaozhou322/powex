package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PdomainDAO;
import com.gt.entity.Pdomain;



@Service("frontPdomainService")
public class FrontPdomainServiceImpl implements FrontPdomainService {
	
	@Autowired
	private PdomainDAO pdomainDAO;
	
	public void save(Pdomain instance) {
		pdomainDAO.save(instance);
	}
	
	public Pdomain findById(java.lang.Integer id) {
		return pdomainDAO.findById(id);
	}

	public List<Pdomain> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		return pdomainDAO.list(firstResult, maxResults, filter, isFY);
	}
	
	public Pdomain findByProperty(String propertyName, Object value) {
		return pdomainDAO.findByProperty(propertyName, value);
	}

	public void update(Pdomain instance) {
		pdomainDAO.attachDirty(instance);
	}
	
	public Pdomain findByProjectId(java.lang.Integer projectId) {
		return pdomainDAO.findByProjectId(projectId);
	}
}
