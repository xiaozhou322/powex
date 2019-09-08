package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fabout;

public interface AboutService {

	public Fabout findById(int id);

	public void saveObj(Fabout obj);

	public void deleteObj(int id);

	public void updateObj(Fabout obj);

	public List<Fabout> findByProperty(String name, Object value);

	public List<Fabout> findAll();

	public List<Fabout> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}