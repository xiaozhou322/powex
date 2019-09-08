package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fwebbaseinfo;

public interface WebBaseInfoService {

	public Fwebbaseinfo findById(int id);

	public void saveObj(Fwebbaseinfo obj);

	public void deleteObj(int id);

	public void updateObj(Fwebbaseinfo obj);

	public List<Fwebbaseinfo> findByProperty(String name, Object value);

	public List<Fwebbaseinfo> findAll();
}
