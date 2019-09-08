package com.gt.service.front;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FarticleDAO;
import com.gt.dao.PadDAO;
import com.gt.entity.Pad;
import com.gt.entity.Farticle;



@Service("frontPadService")
public class FrontPadServiceImpl implements FrontPadService {
	
	@Autowired
	private PadDAO padDAO;
	@Autowired
	private FarticleDAO farticleDAO;
	
	public void save(Pad instance) {
		padDAO.save(instance);
	}
	
	public Pad findById(java.lang.Integer id) {
		return padDAO.findById(id);
	}
	
	
	public void update(Pad instance) {
		padDAO.attachDirty(instance);
	}
	
	public List<Pad> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return padDAO.list(firstResult, maxResults, filter, isFY);
	}
	
	public void updateAudit(Pad pad, Farticle article) {
		padDAO.attachDirty(pad);
		farticleDAO.save(article);
	}
}
