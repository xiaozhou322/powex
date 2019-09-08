package com.gt.service.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gt.entity.Fentrustsnap;

public interface SnapEntrustService {

	public Fentrustsnap findById(int id);

	public void saveObj(Fentrustsnap obj);

	public void deleteObj(int id);

	public void updateObj(Fentrustsnap obj);

	public List<Fentrustsnap> findByProperty(String name, Object value);

	public List<Fentrustsnap> findAll();
	
	public List<Fentrustsnap> list(int firstResult, int maxResults,
			String filter, boolean isFY);
	
	public List<Fentrustsnap> getSnaptByDate(int first_result,int max_result,Date endDate);
	
	public List<Map> listIntro(Date calcday);
}
