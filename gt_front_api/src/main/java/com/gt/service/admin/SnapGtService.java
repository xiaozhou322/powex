package com.gt.service.admin;

import java.util.Date;
import java.util.List;
import com.gt.entity.Fassetgt;

public interface SnapGtService {

	public Fassetgt findById(int id);

	public void saveObj(Fassetgt obj);

	public void deleteObj(int id);

	public void updateObj(Fassetgt obj);

	public List<Fassetgt> findByProperty(String name, Object value);

	public List<Fassetgt> findAll();
	
	public List<Fassetgt> list(int firstResult, int maxResults,String filter);
	
	public List<Fassetgt> getAssetGtByDate(int first_result,int max_result,Date endDate);
}
