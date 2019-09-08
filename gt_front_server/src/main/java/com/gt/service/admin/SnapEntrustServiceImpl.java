package com.gt.service.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FentrustsnapDAO;
import com.gt.entity.Fentrustsnap;

@Service("snapEntrustService")
public class SnapEntrustServiceImpl implements SnapEntrustService {
	@Autowired
	private FentrustsnapDAO fentrustsnapDAO;
	@Autowired
	private HttpServletRequest request;

	public Fentrustsnap findById(int id) {
		return this.fentrustsnapDAO.findById(id);
	}

	public void saveObj(Fentrustsnap obj) {
		this.fentrustsnapDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fentrustsnap obj = this.fentrustsnapDAO.findById(id);
		this.fentrustsnapDAO.delete(obj);
	}

	public void updateObj(Fentrustsnap obj) {
		this.fentrustsnapDAO.attachDirty(obj);
	}

	public List<Fentrustsnap> findByProperty(String name, Object value) {
		return this.fentrustsnapDAO.findByProperty(name, value);
	}

	public List<Fentrustsnap> findAll() {
		return this.fentrustsnapDAO.findAll();
	}
	
	public List<Fentrustsnap> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		List<Fentrustsnap> all = this.fentrustsnapDAO.list(firstResult, maxResults, filter,isFY);
		for (Fentrustsnap fentrustsnap : all) {
			fentrustsnap.getFuser().getFemail();
			if(fentrustsnap.getFintroUser()!=null){
				fentrustsnap.getFintroUser().getFemail();
			}
		}
		return all;
	}
	
	public List<Fentrustsnap> getSnaptByDate(int first_result,int max_result,Date endDate) {
		return this.fentrustsnapDAO.getSnaptByDate(first_result, max_result,endDate);
	}
	
	public List<Map> listIntro(Date calcday) {
		return this.fentrustsnapDAO.listIntro(calcday);
	}
}
