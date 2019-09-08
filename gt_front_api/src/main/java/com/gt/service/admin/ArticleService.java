package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Farticle;

public interface ArticleService {

	public Farticle findById(int id);

	public void saveObj(Farticle obj);

	public void deleteObj(int id);

	public void updateObj(Farticle obj);

	public List<Farticle> findByProperty(String name, Object value);

	public List<Farticle> findAll();

	public List<Farticle> list(int firstResult, int maxResults,
			String filter,boolean isFY);

}