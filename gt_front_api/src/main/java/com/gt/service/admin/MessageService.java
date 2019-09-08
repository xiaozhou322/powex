package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fmessage;

public interface MessageService {

	public Fmessage findById(int id);

	public void saveObj(Fmessage obj);

	public void deleteObj(int id);

	public void updateObj(Fmessage obj);

	public List<Fmessage> findByProperty(String name, Object value);

	public List<Fmessage> findAll();

	public List<Fmessage> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}