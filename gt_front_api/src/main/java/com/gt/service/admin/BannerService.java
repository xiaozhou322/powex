package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fbanner;

public interface BannerService {

	public Fbanner findById(int id);

	public void saveObj(Fbanner obj);

	public void deleteObj(int id);

	public void updateObj(Fbanner obj);

	public List<Fbanner> findByProperty(String name, Object value);

	public List<Fbanner> findAll();

	public List<Fbanner> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
