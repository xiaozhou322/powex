package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Flog;

public interface LogService {

	public Flog findById(int id);

	public void saveObj(Flog obj);

	public void deleteObj(int id);

	public void updateObj(Flog obj);

	public List<Flog> findByProperty(String name, Object value);

	public List<Flog> findAll();

	public List<Flog> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}