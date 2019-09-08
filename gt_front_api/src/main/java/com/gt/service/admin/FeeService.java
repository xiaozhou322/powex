package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Ffees;

public interface FeeService {

	public Ffees findById(int id);

	public void saveObj(Ffees obj);

	public void deleteObj(int id);

	public void updateObj(Ffees obj);

	public List<Ffees> findByProperty(String name, Object value);

	public List<Ffees> findAll();
	
	public List<Ffees> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public Ffees findFfee(int tradeMappingID,int level);
	
}