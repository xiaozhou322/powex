package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fcountlimit;

public interface CountLimitService {

	public Fcountlimit findById(int id);

	public void saveObj(Fcountlimit obj);

	public void deleteObj(int id);

	public void updateObj(Fcountlimit obj);

	public List<Fcountlimit> findByProperty(String name, Object value);

	public List<Fcountlimit> findAll();

	public List<Fcountlimit> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
