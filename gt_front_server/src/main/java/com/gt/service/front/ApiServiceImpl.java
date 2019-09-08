package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.UtilsDAO;
import com.gt.entity.Fapi;

@Service("apiService")
public class ApiServiceImpl implements ApiService {
	
	@Autowired
	private UtilsDAO utilsDAO ;
	
	public Fapi findFapi(String api_key){
		StringBuffer filter = new StringBuffer(" where fpartner='"+api_key+"'") ;
		List<Fapi> fapis = this.utilsDAO.findByParam(0, 0, filter.toString(), false, Fapi.class) ;
		if(fapis.size()==1){
			return fapis.get(0) ;
		}
		return null ;
	}
	
	
	public Fapi findFapiByUserId(Integer userId){
		StringBuffer filter = new StringBuffer(" where fuser.fid='"+userId+"'") ;
		List<Fapi> fapis = this.utilsDAO.findByParam(0, 0, filter.toString(), false, Fapi.class) ;
		if(fapis.size() >=1){
			return fapis.get(0) ;
		}
		return null ;
	}
}
