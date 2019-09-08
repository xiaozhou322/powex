package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Frole;

public interface RoleService {

	public Frole findById(int id);

	public void saveObj(Frole obj);

	public void deleteObj(int id);

	public void updateObj(Frole obj);

	public List<Frole> findByProperty(String name, Object value);

	public List<Frole> findAll();

	public List<Frole> list(int firstResult, int maxResults,String filter,boolean isFY);
}
