package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PdepositlogsDAO;
import com.gt.entity.Pdepositlogs;



@Service("frontPdepositlogsService")
public class FrontPdepositlogsServiceImpl implements FrontPdepositlogsService {
	
	@Autowired
	private PdepositlogsDAO pdepositlogsDAO;
	
	public void save(Pdepositlogs instance) {
		pdepositlogsDAO.save(instance);
	}
	
	public Pdepositlogs findById(java.lang.Integer id) {
		return pdepositlogsDAO.findById(id);
	}
	
	
	public void update(Pdepositlogs instance) {
		pdepositlogsDAO.attachDirty(instance);
	}
	
	public List<Pdepositlogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pdepositlogsDAO.list(firstResult, maxResults, filter, isFY);
	}
}
