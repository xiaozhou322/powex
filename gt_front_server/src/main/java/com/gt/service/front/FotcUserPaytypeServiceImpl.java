package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FotcUserPaytypeDAO;
import com.gt.entity.FotcUserPaytype;


/**
 * otc用户支付类型service
 * @author zhouyong
 *
 */
@Service("fotcUserPaytypeService")
public class FotcUserPaytypeServiceImpl implements FotcUserPaytypeService {
	@Autowired
	private FotcUserPaytypeDAO otcUserPaytypeDAO;
	
	public List<FotcUserPaytype> findAllPayType(String key, Object value){
		return otcUserPaytypeDAO.findAllPayType(key, value);
	}
	
	public FotcUserPaytype queryById(Integer id) {
		return otcUserPaytypeDAO.queryById(id);
	}
	
	public Integer saveObj(FotcUserPaytype obj) {
		return otcUserPaytypeDAO.save(obj);
	}
	
	public void updateObj(FotcUserPaytype obj) {
		otcUserPaytypeDAO.update(obj);
	}
	
	public boolean queryisBindPayType(Integer fid) {
		return otcUserPaytypeDAO.queryisBindPayType(fid);
	}
	
	public List<FotcUserPaytype> findAllPayTypeById(int fid) {
		return otcUserPaytypeDAO.findAllPayTypeById(fid);
	}
	
	public FotcUserPaytype queryisBindType(Integer fid, Integer payType) {
		return otcUserPaytypeDAO.queryisBindType(fid, payType);
	}
	
	public List<FotcUserPaytype> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.otcUserPaytypeDAO.list(firstResult, maxResults, filter,isFY);
	}
	
}
