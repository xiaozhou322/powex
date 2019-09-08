package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FmessageDAO;
import com.gt.dao.FvirtualpresalelogDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fvirtualpresalelog;
import com.gt.entity.Fvirtualwallet;

@Service("virtualPresaleLogService")
public class VirtualPresaleLogServiceImpl implements VirtualPresaleLogService {
	@Autowired
	private FvirtualpresalelogDAO virtualpresalelogDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDao;
	@Autowired
	private FmessageDAO messageDAO;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;

	public Fvirtualpresalelog findById(int id) {
		Fvirtualpresalelog operationLog = this.virtualpresalelogDAO.findById(id);;
		return operationLog;
	}

	public void saveObj(Fvirtualpresalelog obj) {
		this.virtualpresalelogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fvirtualpresalelog obj = this.virtualpresalelogDAO.findById(id);
		this.virtualpresalelogDAO.delete(obj);
	}

	public void updateObj(Fvirtualpresalelog obj) {
		this.virtualpresalelogDAO.attachDirty(obj);
	}

	public List<Fvirtualpresalelog> findByProperty(String name, Object value) {
		return this.virtualpresalelogDAO.findByProperty(name, value);
	}

	public List<Fvirtualpresalelog> findAll() {
		return this.virtualpresalelogDAO.findAll();
	}

	public List<Fvirtualpresalelog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fvirtualpresalelog> all = this.virtualpresalelogDAO.list(firstResult, maxResults, filter,isFY);
		for (Fvirtualpresalelog foperationlog : all) {
			foperationlog.getFuser().getFemail();
			if(foperationlog.getFcreator() != null){
				foperationlog.getFcreator().getFname();
			}
			foperationlog.getFvirtualcointype().getFname();
		}
		return all;
	}
	
	public void updateVirtualPresaleLog(Fvirtualwallet virtualwallet,Fvirtualpresalelog virtualoperationlog) {
		try {
			this.virtualwalletDao.attachDirty(virtualwallet);
			this.virtualpresalelogDAO.attachDirty(virtualoperationlog);
		} catch (Exception e) {
			throw new  RuntimeException();
		}
	}

	public void updateVirtualPresaleLog(Fvirtualwallet virtualwallet,Fvirtualpresalelog virtualoperationlog,Fintrolinfo info) {
		try {
			this.virtualwalletDao.attachDirty(virtualwallet);
			this.introlinfoDAO.save(info);
			this.virtualpresalelogDAO.attachDirty(virtualoperationlog);
		} catch (Exception e) {
			throw new  RuntimeException();
		}
	}
}
