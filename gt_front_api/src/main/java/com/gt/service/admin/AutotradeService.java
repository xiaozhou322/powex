package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fautotrade;

public interface AutotradeService {

	public Fautotrade findById(int id);

	public void saveObj(Fautotrade obj);

	public void deleteObj(int id);

	public void updateObj(Fautotrade obj);

	public List<Fautotrade> findByProperty(String name, Object value);

	public List<Fautotrade> findAll();

	public List<Fautotrade> list(int firstResult, int maxResults, String filter,boolean isFY);
}
