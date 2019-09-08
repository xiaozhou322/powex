package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PdepositcointypeDAO;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositcointype;



@Service("frontPdepositcointypeService")
public class FrontPdepositcointypeServiceImpl implements FrontPdepositcointypeService {
	
	@Autowired
	private PdepositcointypeDAO pdepositcointypeDAO;
	
	public Pdepositcointype findById(int id) {
		return pdepositcointypeDAO.findById(id);
	}

	public List<Pdepositcointype> findByProperty(String propertyName,
			Object value) {
		return pdepositcointypeDAO.findByProperty(propertyName,value);
	}

	public List<Pdepositcointype> findByParam(String filter) {
		return pdepositcointypeDAO.findByParam(filter);
	}
	
	public List<Pdepositcointype> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pdepositcointypeDAO.list(firstResult, maxResults, filter, isFY);
	}
	
	public void update(Pdepositcointype instance) {
		pdepositcointypeDAO.attachDirty(instance);
	}
	
}
