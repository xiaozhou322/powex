package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fusersetting;

public interface UsersettingService {

	public Fusersetting findById(int id);

	public void saveObj(Fusersetting obj);

	public void deleteObj(int id);

	public void updateObj(Fusersetting obj);

	public List<Fusersetting> findByProperty(String name, Object value);

	public List<Fusersetting> findAll();

	public List<Fusersetting> list(int firstResult, int maxResults, String filter,boolean isFY);
}
