package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FadminDAO;
import com.gt.dao.FlogDAO;
import com.gt.entity.Fadmin;
import com.gt.entity.Flog;
import com.gt.util.Utils;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private FadminDAO fadminDAO;
	@Autowired
	private FlogDAO flogDAO;

	public Fadmin findById(int id) {
		return this.fadminDAO.findById(id);
	}

	public void saveObj(Fadmin obj) {
		this.fadminDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fadmin obj = this.fadminDAO.findById(id);
		this.fadminDAO.delete(obj);
	}

	public void updateObj(Fadmin obj) {
		this.fadminDAO.attachDirty(obj);
	}

	public List<Fadmin> findByProperty(String name, Object value) {
		List<Fadmin> all = this.fadminDAO.findByProperty(name, value);
		for (Fadmin fadmin : all) {
			if(fadmin.getFrole() != null){
				fadmin.getFrole().getFname();
			}
		}
		return all;
	}

	public List<Fadmin> findAll() {
		return this.fadminDAO.findAll();
	}

	public List<Fadmin> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fadmin> list = this.fadminDAO.list(firstResult, maxResults, filter,isFY);
		for (Fadmin fadmin : list) {
		     if(fadmin.getFrole() != null){
		    	 fadmin.getFrole().getFname();
		     }
		}
		return list;
	}
	
	public void updateAdminlog(Fadmin admin,String ip,int type) {
		Flog flog = new Flog() ;
		flog.setFcreateTime(Utils.getTimestamp()) ;
		flog.setFkey1(String.valueOf(admin.getFid())) ;
		flog.setFkey2(admin.getFname()) ;
		flog.setFkey3(ip) ;
		flog.setFtype(type) ;
		this.flogDAO.save(flog) ;
	}
	
/*	public boolean updateDatabase(String ip,String port,String dataBase,String name,String password,String dir,String fileName){
		boolean flag = this.fadminDAO.backDatabase(ip,port,dataBase,name,password,dir,fileName);
		return flag;
	}*/
	
	public List<Fadmin> login(Fadmin fadmin) {
		return this.fadminDAO.login(fadmin) ;
	}
	
	public int getAllCount(String tableName,String filter){
		return this.fadminDAO.getAllCount(tableName,filter);
	}
	
	public double getSQLValue(String filter){
		return this.fadminDAO.getSQLValue(filter);
	}
	
	public Map getSQLValue1(String filter){
		return this.fadminDAO.getSQLValue1(filter);
	}
	
	public double getSQLValue2(String filter){
		return this.fadminDAO.getSQLValue2(filter);
	}
	
	public void updateSQL(String filter){
		this.fadminDAO.updateSQL(filter);
	}
	
	public void updateAdminlog(Fadmin admin,String ip,int type,String description) {
		Flog flog = new Flog() ;
		flog.setFcreateTime(Utils.getTimestamp()) ;
		flog.setFkey1(String.valueOf(admin.getFid())) ;
		flog.setFkey2(admin.getFname()) ;
		flog.setFkey3(ip) ;
		flog.setFkey4(description) ;
		flog.setFtype(type) ;
		this.flogDAO.save(flog) ;
	}

	public Double getAllSum(String tableName,String fieldName,String filter){
		return this.fadminDAO.getAllSum(tableName,fieldName,filter);
	}
}