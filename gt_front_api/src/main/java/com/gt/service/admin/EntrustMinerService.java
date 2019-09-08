package com.gt.service.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.entity.Fentrustminer;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fvirtualwallet;

public interface EntrustMinerService {

	public Fentrustminer findById(int id);

	public void saveObj(Fentrustminer obj);

	public void deleteObj(int id);

	public void updateObj(Fentrustminer obj);

	public List<Fentrustminer> findByProperty(String name, Object value);

	public List<Fentrustminer> findAll();
	
	public List<Fentrustminer> list(int firstResult, int maxResults, String filter, boolean isFY);
	
	public List<Fentrustminer> getSnaptByDate(int first_result,int max_result,Date endDate);
	
	public void updateMiner(Fvirtualwallet fvirtualwallet,Fintrolinfo introlInfo,Fentrustminer fentrustminer);
}
