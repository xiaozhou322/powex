package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fsecurity;

public interface SecurityService {

	public Fsecurity findById(int id);

	public void saveObj(Fsecurity obj);

	public void deleteObj(int id);

	public void updateObj(Fsecurity obj);

	public List<Fsecurity> findByProperty(String name, Object value);

	public List<Fsecurity> findAll();

	public List<Fsecurity> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
