package com.gt.service.admin;

import java.util.List;
import com.gt.entity.Fadmin;
import com.gt.entity.Foperationlog;

public interface OperationLogService {

	public Foperationlog findById(int id);

	public void saveObj(Foperationlog obj);

	public void deleteObj(int id);

	public void updateObj(Foperationlog obj);

	public List<Foperationlog> findByProperty(String name, Object value);

	public List<Foperationlog> findAll();

	public List<Foperationlog> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public boolean updateOperationLog(int operationId,Fadmin auditor);
}
