package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;

public interface CapitaloperationService {

	public Fcapitaloperation findById(int id);

	public void saveObj(Fcapitaloperation obj);

	public void deleteObj(int id);

	public void updateObj(Fcapitaloperation obj);

	public List<Fcapitaloperation> findByProperty(String name, Object value);

	public List<Fcapitaloperation> findAll();

	public List<Fcapitaloperation> list(int firstResult, int maxResults,
			String filter,boolean isFY);

	public void updateCapital(Fcapitaloperation capitaloperation,Fvirtualwallet fvirtualwallet,boolean isRecharge);
	
	public void updateCapital(Fcapitaloperation fcapitaloperation,Fvirtualwallet fvirtualwallet,Fscore fscore,Fintrolinfo info);
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday);
	
	public Map getTotalAmountIn(int type,String fstatus,boolean isToday);
	
	public List getTotalGroup(String filter);
	
	public List getTotalAmountForReport(String filter);
	
	public List getTotalOperationlog(String filter);
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday,boolean isFee);

	public Double getTotalAmountForUser(Fuser fuser);

}