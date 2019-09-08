package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Ftrademapping;
import com.gt.entity.Ptrademapping;

public interface TradeMappingService {

	public Ftrademapping findById(int id);

	public int saveObj(Ftrademapping obj);

	public void deleteObj(int id);

	public void updateObj(Ftrademapping obj);
	
	public void updateTrademappings(Ftrademapping obj, Ptrademapping obj2);

	public List<Ftrademapping> findByProperty(String name, Object value);

	public List<Ftrademapping> findAll();

	public List<Ftrademapping> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}