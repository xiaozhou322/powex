package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Ftradehistory;

public interface TradehistoryService {

	public Ftradehistory findById(int id);
	
	public void updateUser(String sql);

	public void saveObj(Ftradehistory obj);

	public void deleteObj(int id);

	public void updateObj(Ftradehistory obj);

	public List<Ftradehistory> findByProperty(String name, Object value);

	public List<Ftradehistory> findAll();

	public List<Ftradehistory> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}