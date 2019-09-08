package com.gt.service.admin;

import java.util.Date;
import java.util.List;

import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fmessage;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;

public interface UserService {

	public Fuser findById(int id);

	public void saveObj(Fuser obj);

	public void deleteObj(int id);

	public void updateObj(Fuser obj);
	
	public void updateObj(Fuser obj,Fmessage message);
	
	public void updateObj(Fuser obj,Fscore fscore,Fuser fintrolUser,Fvirtualwallet fvirtualwallet,
			Fintrolinfo introlinfo,Fvirtualwallet fvirtualwalletIntro,Fintrolinfo introlinfoIntro);
	
	public void updateObj(Fuser obj,Fscore fscore,Fuser fintrolUser,Fvirtualwallet fvirtualwallet,Fintrolinfo introlinfo);
	
	public void updateObj(Fuser obj,Fintrolinfo introlinfo,Fvirtualwallet fvirtualwallet);

	public List<Fuser> findByProperty(String name, Object value);

	public List<Fuser> findAll();

	public List<Fuser> list(int firstResult, int maxResults, String filter,
			boolean isFY);
	
	public List<Fuser> simpleList(int firstResult, int maxResults, String filter,
			boolean isFY);

	public List findByDate(String propertyName, Date value);

	public List getUserGroup(String filter);

	public List<Fuser> listUserForAudit(int firstResult, int maxResults,
			String filter, boolean isFY);
	
	public List getUser(int type);
	
	public void updateUser(Fvirtualwallet fvirtualwallet,Fscore fscore);
}