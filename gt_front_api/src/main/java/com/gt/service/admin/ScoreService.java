package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fscore;

public interface ScoreService {

	public Fscore findById(int id);

	public void saveObj(Fscore obj);

	public void deleteObj(int id);

	public void updateObj(Fscore obj);
	
	public void updateObj1(Fscore obj,Fscore obj1);

	public List<Fscore> findByProperty(String name, Object value);

	public List<Fscore> findAll();
	
	public void updateData(String sql);
}
