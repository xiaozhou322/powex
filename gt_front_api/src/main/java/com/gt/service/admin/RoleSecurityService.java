package com.gt.service.admin;

import java.util.List;

import com.gt.entity.FroleSecurity;

public interface RoleSecurityService {

	public FroleSecurity findById(int id);

	public void saveObj(FroleSecurity obj);

	public void deleteObj(int id);

	public void updateObj(FroleSecurity obj);

	public List<FroleSecurity> findByProperty(String name, Object value);

	public List<FroleSecurity> findAll();

	public List<FroleSecurity> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public void deleteByRoleId(int roleId);
}
