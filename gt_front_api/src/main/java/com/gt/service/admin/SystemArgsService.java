package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fsystemargs;

public interface SystemArgsService {

	public Fsystemargs findById(int id);

	public void saveObj(Fsystemargs obj);

	public void deleteObj(int id);

	public void updateObj(Fsystemargs obj);

	public List<Fsystemargs> findByProperty(String name, Object value);

	public List<Fsystemargs> findAll();

	public List<Fsystemargs> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public String getValue(String key);
}