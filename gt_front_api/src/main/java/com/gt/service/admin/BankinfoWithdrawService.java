package com.gt.service.admin;

import java.util.List;

import com.gt.entity.FbankinfoWithdraw;

public interface BankinfoWithdrawService {

	public FbankinfoWithdraw findById(int id);

	public void saveObj(FbankinfoWithdraw obj);

	public void deleteObj(int id);

	public void updateObj(FbankinfoWithdraw obj);

	public List<FbankinfoWithdraw> findByProperty(String name, Object value);

	public List<FbankinfoWithdraw> findAll();

	public List<FbankinfoWithdraw> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
