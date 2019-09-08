package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FmessageDAO;
import com.gt.dao.FvirtualoperationlogDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fvirtualoperationlog;
import com.gt.entity.Fvirtualwallet;

@Service("virtualOperationLogService")
public class VirtualOperationLogServiceImpl implements VirtualOperationLogService {
	@Autowired
	private FvirtualoperationlogDAO virtualoperationlogDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDao;
	@Autowired
	private FmessageDAO messageDAO;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;

	public Fvirtualoperationlog findById(int id) {
		Fvirtualoperationlog operationLog = this.virtualoperationlogDAO.findById(id);;
		return operationLog;
	}

	public void saveObj(Fvirtualoperationlog obj) {
		this.virtualoperationlogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fvirtualoperationlog obj = this.virtualoperationlogDAO.findById(id);
		this.virtualoperationlogDAO.delete(obj);
	}

	public void updateObj(Fvirtualoperationlog obj) {
		this.virtualoperationlogDAO.attachDirty(obj);
	}

	public List<Fvirtualoperationlog> findByProperty(String name, Object value) {
		return this.virtualoperationlogDAO.findByProperty(name, value);
	}

	public List<Fvirtualoperationlog> findAll() {
		return this.virtualoperationlogDAO.findAll();
	}

	public List<Fvirtualoperationlog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fvirtualoperationlog> all = this.virtualoperationlogDAO.list(firstResult, maxResults, filter,isFY);
		for (Fvirtualoperationlog foperationlog : all) {
			foperationlog.getFuser().getFemail();
			if(foperationlog.getFcreator() != null){
				foperationlog.getFcreator().getFname();
			}
			foperationlog.getFvirtualcointype().getFname();
		}
		return all;
	}
	
	public void updateVirtualOperationLog(Fvirtualwallet virtualwallet,Fvirtualoperationlog virtualoperationlog) {
		try {
			this.virtualwalletDao.attachDirty(virtualwallet);
			this.virtualoperationlogDAO.attachDirty(virtualoperationlog);
		} catch (Exception e) {
			throw new  RuntimeException();
		}
	}

	public void updateVirtualOperationLog(Fvirtualwallet virtualwallet,Fvirtualoperationlog virtualoperationlog,Fintrolinfo info) {
		try {
			this.virtualwalletDao.attachDirty(virtualwallet);
			this.introlinfoDAO.save(info);
			this.virtualoperationlogDAO.attachDirty(virtualoperationlog);
		} catch (Exception e) {
			throw new  RuntimeException();
		}
	}
}
