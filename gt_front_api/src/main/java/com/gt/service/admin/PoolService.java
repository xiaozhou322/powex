package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fpool;
import com.gt.entity.Fvirtualcointype;

public interface PoolService {

	public Fpool findById(int id);

	public void saveObj(Fpool obj);

	public void deleteObj(int id);

	public void updateObj(Fpool obj);

	public List<Fpool> findByProperty(String name, Object value);

	public List<Fpool> findAll();
	
	public List list(int firstResult, int maxResults, String filter,boolean isFY);
	
	public Fpool getOneFpool(Fvirtualcointype fvirtualcointype);

}