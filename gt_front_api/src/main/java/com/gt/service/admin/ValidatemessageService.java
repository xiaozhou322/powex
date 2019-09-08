package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fvalidatemessage;

public interface ValidatemessageService {

	public Fvalidatemessage findById(int id);

	public void saveObj(Fvalidatemessage obj);

	public void deleteObj(int id);

	public void updateObj(Fvalidatemessage obj);

	public List<Fvalidatemessage> findByProperty(String name, Object value);

	public List<Fvalidatemessage> findAll();

	public List<Fvalidatemessage> list(int firstResult, int maxResults,String filter,boolean isFY);
}
