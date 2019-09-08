package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fvirtualoperationlog;
import com.gt.entity.Fvirtualwallet;

public interface VirtualOperationLogService {

	public Fvirtualoperationlog findById(int id);

	public void saveObj(Fvirtualoperationlog obj);

	public void deleteObj(int id);

	public void updateObj(Fvirtualoperationlog obj);

	public List<Fvirtualoperationlog> findByProperty(String name, Object value);

	public List<Fvirtualoperationlog> findAll();

	public List<Fvirtualoperationlog> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	public void updateVirtualOperationLog(Fvirtualwallet virtualwallet,Fvirtualoperationlog virtualoperationlog);

	public void updateVirtualOperationLog(Fvirtualwallet virtualwallet,Fvirtualoperationlog virtualoperationlog,Fintrolinfo info);
}
