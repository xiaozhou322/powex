package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.UtilsDAO;
import com.gt.entity.Farticle;
import com.gt.entity.Fasset;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;

@Service("utilsService")
public class UtilsServiceImpl implements UtilsService {

	@Autowired
	private UtilsDAO utilsDAO ;
	
	public List list(int firstResult, int maxResults, String filter, boolean isFY,Class c,Object... param){
		return this.utilsDAO.findByParam(firstResult, maxResults, filter, isFY, c,param) ;
	}
	public List list(int firstResult, int maxResults, String filter, boolean isFY,Class c){
		return this.utilsDAO.findByParam(firstResult, maxResults, filter, isFY, c) ;
	}
	public List list_admin(int firstResult, int maxResults, String filter, boolean isFY,Class c){
		List<Fasset> all = (List<Fasset>)this.utilsDAO.findByParam(firstResult, maxResults, filter, isFY, c) ;
		for (Fasset fasset : all) {
			if(fasset.getFuser() != null) fasset.getFuser().getFnickName();
		}
		return all;
	}
	public int count(String filter, Class c,Object... param){
		return this.utilsDAO.findByParamCount(filter, c,param) ;
	}
	public double sum(String filter, String field, Class c,Object... param){
		return this.utilsDAO.sum(filter, field, c,param) ;
	}
	public int min(String filter, String field, Class c,Object... param){
		return this.utilsDAO.min(filter, field, c,param) ;
	}
	public int max(String filter, String field, Class c,Object... param){
		return this.utilsDAO.max(filter, field, c,param) ;
	}
	
	public List<Ftrademapping> list1(int firstResult, int maxResults, String filter, boolean isFY,Class c,Object... param){
		List<Ftrademapping> list = this.utilsDAO.findByParam(firstResult, maxResults, filter, isFY, c,param) ;
		for (Ftrademapping ftrademapping : list) {
			ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname() ;
			ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname() ;
		}
		return list ;
	}
	
	
	public List<Fvirtualcaptualoperation> list3(int firstResult, int maxResults, String filter, boolean isFY,Class c){
		List<Fvirtualcaptualoperation> list= this.utilsDAO.findByParam(firstResult, maxResults, filter, isFY, c) ;
		for (Fvirtualcaptualoperation fvirtualcaptualoperation : list) {
			Fuser fuser = fvirtualcaptualoperation.getFuser() ;
			if(fuser!=null ){
				fuser.getFnickName();
			}
			
			Fvirtualcointype fvirtualcointype = fvirtualcaptualoperation.getFvirtualcointype() ;
			if(fvirtualcointype!=null ){
				fvirtualcointype.getFname() ;
			}
		}
		return list ;
	}
	public List<Farticle> list4(int firstResult, int maxResults, String filter, boolean isFY){
		List<Farticle> list = this.utilsDAO.findByParam(firstResult, maxResults, filter, isFY, Farticle.class) ;
		for (Farticle farticle : list) {
			farticle.getFarticletype().getFname() ;
		}
		return list ;
	}
	
	public List<Fvirtualwallet> list5(int firstResult, int maxResults, String filter, boolean isFY,Class c,Object... param){
		List<Fvirtualwallet> list = this.utilsDAO.findByParam(firstResult, maxResults, filter, isFY, c,param) ;
		for (Fvirtualwallet fvirtualwallet : list) {
			fvirtualwallet.getFvirtualcointype().getFname() ;
		}
		return list ;
	}
	
	public List findHQL(int firstResult, int maxResults, String filter,boolean isFY,Class c,Object... param) {
		return this.utilsDAO.findHQL(firstResult, maxResults, filter, isFY, c, param) ;
	}
	public int executeHQL(String hql, double ftotal, double ffrozen,int fack_uid, int fack_vid) {
		return utilsDAO.executeHQL(hql, ftotal, ffrozen, fack_uid, fack_vid);
	}
}
