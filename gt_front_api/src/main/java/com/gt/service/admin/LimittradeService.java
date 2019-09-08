package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Flimittrade;

public interface LimittradeService {

	public Flimittrade findById(int id);

	public void saveObj(Flimittrade obj);

	public void deleteObj(int id);

	public void updateObj(Flimittrade obj);

	public List<Flimittrade> findByProperty(String name, Object value);

	public List<Flimittrade> findAll();

	public List<Flimittrade> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}