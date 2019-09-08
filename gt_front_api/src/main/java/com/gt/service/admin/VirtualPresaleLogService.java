package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fvirtualpresalelog;
import com.gt.entity.Fvirtualwallet;

public interface VirtualPresaleLogService {

	public Fvirtualpresalelog findById(int id);

	public void saveObj(Fvirtualpresalelog obj);

	public void deleteObj(int id);

	public void updateObj(Fvirtualpresalelog obj);

	public List<Fvirtualpresalelog> findByProperty(String name, Object value);

	public List<Fvirtualpresalelog> findAll();

	public List<Fvirtualpresalelog> list(int firstResult, int maxResults,String filter,boolean isFY);
	
	public void updateVirtualPresaleLog(Fvirtualwallet virtualwallet,Fvirtualpresalelog virtualoperationlog);

	public void updateVirtualPresaleLog(Fvirtualwallet virtualwallet,Fvirtualpresalelog virtualoperationlog,Fintrolinfo info);
}
