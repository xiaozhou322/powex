package com.gt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.NperDAO;
import com.gt.entity.Nper;
import com.gt.service.front.NperService;

@Service
public class NperServiceImpl implements NperService {

	@Autowired
	private NperDAO nperDAO;
	
	@Override
	public Nper findById(int id) {
		return nperDAO.findById(id);
	}

	@Override
	public void saveObj(Nper obj) {
		nperDAO.save(obj);

	}

	@Override
	public void deleteObj(Nper obj) {
		nperDAO.delete(obj);

	}

	@Override
	public void updateObj(Nper obj) {
		nperDAO.attachDirty(obj);

	}

	@Override
	public List<Nper> findByProperty(String name, Object value) {
		return nperDAO.findByProperty(name, value);
	}

	@Override
	public List<Nper> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return nperDAO.list(firstResult, maxResults, filter, isFY);
	}
	
	@Override
	public int getAllCount(String tableName,String filter) {
		return nperDAO.getAllCount(tableName, filter);
	}

	@Override
	public int sendPrize(Integer id) {
		return nperDAO.sendPrize(id);
	}

	public List<Nper> findByStatus(int status1,int status2,int selectType) {
		return nperDAO.findByStatus(status1, status2, selectType);
	}
	
	public List<Nper> findByStatus(int status, int selectType) {
		return nperDAO.findByStatus(status, selectType);
	}
}
