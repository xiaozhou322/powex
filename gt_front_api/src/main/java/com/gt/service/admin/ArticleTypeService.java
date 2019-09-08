package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Farticletype;

public interface ArticleTypeService {

	public Farticletype findById(int id);

	public void saveObj(Farticletype obj);

	public void deleteObj(int id);

	public void updateObj(Farticletype obj);

	public List<Farticletype> findByProperty(String name, Object value);

	public List<Farticletype> findAll();

	public List<Farticletype> list(int firstResult, int maxResults, String filter,boolean isFY);
}
