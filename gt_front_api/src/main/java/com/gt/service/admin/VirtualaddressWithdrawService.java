package com.gt.service.admin;

import java.util.List;

import com.gt.entity.FvirtualaddressWithdraw;

public interface VirtualaddressWithdrawService {

	public FvirtualaddressWithdraw findById(int id);

	public void saveObj(FvirtualaddressWithdraw obj);

	public void deleteObj(int id);

	public void updateObj(FvirtualaddressWithdraw obj);

	public List<FvirtualaddressWithdraw> findByProperty(String name, Object value);

	public List<FvirtualaddressWithdraw> findAll();

	public List<FvirtualaddressWithdraw> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
