package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fwithdrawfees;

public interface WithdrawFeesService {

	public Fwithdrawfees findById(int id);

	public void saveObj(Fwithdrawfees obj);

	public void deleteObj(int id);

	public void updateObj(Fwithdrawfees obj);

	public List<Fwithdrawfees> findByProperty(String name, Object value);

	public List<Fwithdrawfees> findAll();
	
	public List<Fwithdrawfees> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public Fwithdrawfees findFfee(int virtualCoinType,int level);
}