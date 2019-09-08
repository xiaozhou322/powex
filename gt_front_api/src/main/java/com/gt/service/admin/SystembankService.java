package com.gt.service.admin;

import java.util.List;
import com.gt.entity.Systembankinfo;

public interface SystembankService {

	public Systembankinfo findById(int id);

	public void saveObj(Systembankinfo obj);

	public void deleteObj(int id);
	
	public void updateObj(Systembankinfo obj);

	public List<Systembankinfo> findByProperty(String name, Object value);

	public List<Systembankinfo> findAll();

	public List<Systembankinfo> list(int firstResult, int maxResults, String filter,boolean isFY);
}
