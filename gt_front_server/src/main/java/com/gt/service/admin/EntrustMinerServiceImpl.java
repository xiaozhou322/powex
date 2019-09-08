package com.gt.service.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FentrustminerDAO;
import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fentrustminer;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fvirtualwallet;

@Service("entrustMinerService")
public class EntrustMinerServiceImpl implements EntrustMinerService {
	@Autowired
	private FentrustminerDAO fentrustminerDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;
	@Autowired
	private HttpServletRequest request;

	public Fentrustminer findById(int id) {
		return this.fentrustminerDAO.findById(id);
	}

	public void saveObj(Fentrustminer obj) {
		this.fentrustminerDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fentrustminer obj = this.fentrustminerDAO.findById(id);
		this.fentrustminerDAO.delete(obj);
	}

	public void updateObj(Fentrustminer obj) {
		this.fentrustminerDAO.attachDirty(obj);
	}

	public List<Fentrustminer> findByProperty(String name, Object value) {
		return this.fentrustminerDAO.findByProperty(name, value);
	}

	public List<Fentrustminer> findAll() {
		return this.fentrustminerDAO.findAll();
	}
	
	public List<Fentrustminer> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		List<Fentrustminer> all = this.fentrustminerDAO.list(firstResult, maxResults, filter,isFY);
		for (Fentrustminer fentrustminer : all) {
			fentrustminer.getFuser().getFemail();
			fentrustminer.getFcoin().getFname();
			if(fentrustminer.getFintroUser()!=null){
				fentrustminer.getFintroUser().getFemail();
			}
		}
		return all;
	}
	
	public List<Fentrustminer> getSnaptByDate(int first_result,int max_result,Date endDate) {
		return this.fentrustminerDAO.getSnaptByDate(first_result, max_result,endDate);
	}
	
	public void updateMiner(Fvirtualwallet fvirtualwallet,Fintrolinfo introlInfo,
			Fentrustminer fentrustminer){
		try {
			if(fvirtualwallet != null){
				this.virtualwalletDAO.attachDirty(fvirtualwallet) ;
			}
			if(introlInfo != null){
				this.introlinfoDAO.save(introlInfo);
			}
			if(fentrustminer != null){
				this.fentrustminerDAO.attachDirty(fentrustminer);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
