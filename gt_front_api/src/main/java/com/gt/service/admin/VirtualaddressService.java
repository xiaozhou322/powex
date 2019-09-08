package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fvirtualaddress;

public interface VirtualaddressService {

	public Fvirtualaddress findById(int id);

	public void saveObj(Fvirtualaddress obj);

	public void deleteObj(int id);

	public void updateObj(Fvirtualaddress obj);

	public List<Fvirtualaddress> findByProperty(String name, Object value);

	public List<Fvirtualaddress> findAll();

	public List<Fvirtualaddress> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}