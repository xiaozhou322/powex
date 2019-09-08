package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fentrust;

public interface EntrustService {

	public Fentrust findById(int id);

	public void saveObj(Fentrust obj);

	public void deleteObj(int id);

	public void updateObj(Fentrust obj);

	public List<Fentrust> findByProperty(String name, Object value);

	public List<Fentrust> findAll();

	public List<Fentrust> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public List<Map> getTotalQty(int fentrustType);
	
	public List<Map> getMemberQty(String querytype,String queryday,int firstResult, int maxResults);
	
	public List<Map> getTotalQty(int fentrustType,boolean isToady);
	
	public List<Map> getMemberMarket(int querytype,String queryday,int firstResult, int maxResults);
	
	public List<Map> getMemberMarket(int querytype,String queryday,String queryday1,int firstResult, int maxResults);
}