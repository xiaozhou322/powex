package com.gt.service.front;

import java.util.List;

import com.gt.entity.Farticle;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualwallet;

public interface UtilsService {

	public List list(int firstResult, int maxResults, String filter, boolean isFY,Class c,Object... param);
	
	public List list(int firstResult, int maxResults, String filter, boolean isFY,Class c);
	
	public List list_admin(int firstResult, int maxResults, String filter, boolean isFY,Class c);
	
	public int count(String filter, Class c,Object... param);
	
	public double sum(String filter, String field, Class c,Object... param);
	
	public int min(String filter, String field, Class c,Object... param);
	
	public int max(String filter, String field, Class c,Object... param);
	
	public List<Ftrademapping> list1(int firstResult, int maxResults, String filter, boolean isFY,Class c,Object... param);
	
	
	public List<Fvirtualcaptualoperation> list3(int firstResult, int maxResults, String filter, boolean isFY,Class c);
	
	public List<Farticle> list4(int firstResult, int maxResults, String filter, boolean isFY);
	
	public List<Fvirtualwallet> list5(int firstResult, int maxResults, String filter, boolean isFY,Class c,Object... param);
	
	public List findHQL(int firstResult, int maxResults, String filter,boolean isFY,Class c,Object... param);

	public int executeHQL(String hql, double ftotal, double ffrozen,int fack_uid, int fack_vid);
	
}
