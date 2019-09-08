package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Nper;

public interface NperService {

	public Nper findById(int id);

	public void saveObj(Nper obj);

	public void deleteObj(Nper obj);

	public void updateObj(Nper obj);

	public List<Nper> findByProperty(String name, Object value);

	public List<Nper> list(int firstResult, int maxResults, String filter,boolean isFY);
	
	public int getAllCount(String tableName,String filter);
	
	public int sendPrize(Integer id);
	
	public List<Nper> findByStatus(int status1,int status2,int selectType);
	
	public List<Nper> findByStatus(int status,int selectType);
	
}
