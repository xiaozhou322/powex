package com.gt.service.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PsystemconfigDAO;
import com.gt.entity.Psystemconfig;



@Service("frontPsystemconfigService")
public class FrontPsystemconfigServiceImpl implements FrontPsystemconfigService {
	
	@Autowired
	private PsystemconfigDAO psystemconfigDAO;
	
	public List<Psystemconfig> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		return psystemconfigDAO.list(firstResult, maxResults, filter, isFY);
	}

	public void save(Psystemconfig psystemconfig) {
		psystemconfigDAO.save(psystemconfig);
	}

	public void update(Psystemconfig psystemconfig) {
		psystemconfigDAO.attachDirty(psystemconfig);
	}
	
	public Psystemconfig findByProjectId(String filter) {
		return psystemconfigDAO.findByProjectId(filter);
	}
	
	public Map<String, Object> findAllMap(String filter){
		Map<String, Object> map = new HashMap<String, Object>() ;
		List<Psystemconfig> list = this.psystemconfigDAO.findAll(filter) ;
		for (Psystemconfig psystemconfig : list) {
			map.put(psystemconfig.getFkey(), psystemconfig.getFvalue()) ;
		}
		return map ;
	}
	
}
