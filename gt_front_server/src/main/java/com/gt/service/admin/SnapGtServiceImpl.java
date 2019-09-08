package com.gt.service.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FassetgtDAO;
import com.gt.entity.Fassetgt;

@Service("snapGtService")
public class SnapGtServiceImpl implements SnapGtService {
	@Autowired
	private FassetgtDAO fassetgtDAO;
	@Autowired
	private HttpServletRequest request;

	public Fassetgt findById(int id) {
		return this.fassetgtDAO.findById(id);
	}

	public void saveObj(Fassetgt obj) {
		this.fassetgtDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fassetgt obj = this.fassetgtDAO.findById(id);
		this.fassetgtDAO.delete(obj);
	}

	public void updateObj(Fassetgt obj) {
		this.fassetgtDAO.attachDirty(obj);
	}

	public List<Fassetgt> findByProperty(String name, Object value) {
		return this.fassetgtDAO.findByProperty(name, value);
	}

	public List<Fassetgt> findAll() {
		return this.fassetgtDAO.findAll();
	}
	
	public List<Fassetgt> list(int firstResult, int maxResults,
			String filter) {
		List<Fassetgt> all = this.fassetgtDAO.list(firstResult, maxResults, filter);
		for (Fassetgt fassetgt : all) {
			fassetgt.getFuser().getFemail();
		}
		return all;
	}
	
	public List<Fassetgt> getAssetGtByDate(int first_result,int max_result,Date endDate) {
		return this.fassetgtDAO.getAssetGtByDate(first_result, max_result,endDate);
	}
}
