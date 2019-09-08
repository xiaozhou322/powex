package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fintrolinfo;

public interface IntrolinfoService {

	public Fintrolinfo findById(int id);

	public void saveObj(Fintrolinfo obj);

	public void deleteObj(int id);

	public void updateObj(Fintrolinfo obj);

	public List<Fintrolinfo> findByProperty(String name, Object value);

	public List<Fintrolinfo> findAll();

	public List<Fintrolinfo> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public List<Map> getAllIntrol(int firstResult, int maxResults, String filter,boolean isFY);
}