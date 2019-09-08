package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fadmin;

public interface AdminService {

	public Fadmin findById(int id);

	public void saveObj(Fadmin obj);

	public void deleteObj(int id);

	public void updateObj(Fadmin obj);

	public List<Fadmin> findByProperty(String name, Object value);

	public List<Fadmin> findAll();

	public List<Fadmin> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public void updateAdminlog(Fadmin admin,String ip,int type);
	
/*	public boolean updateDatabase(String ip,String port,String dataBase,String name,String password,String dir,String fileName){
		boolean flag = this.fadminDAO.backDatabase(ip,port,dataBase,name,password,dir,fileName);
		return flag;
	}*/
	
	public List<Fadmin> login(Fadmin fadmin);
	
	public int getAllCount(String tableName,String filter);
	
	public double getSQLValue(String filter);
	
	public Map getSQLValue1(String filter);
	
	public double getSQLValue2(String filter);
	
	public void updateSQL(String filter);
	
	public void updateAdminlog(Fadmin admin,String ip,int type,String description);
	
	public Double getAllSum(String tableName,String fieldName,String filter);
}