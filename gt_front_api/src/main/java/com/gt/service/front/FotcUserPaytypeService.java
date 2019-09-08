package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcUserPaytype;


/**
 * otc用户支付类型service
 * @author zhouyong
 *
 */
public interface FotcUserPaytypeService {
	
	public List<FotcUserPaytype> findAllPayType(String key, Object value);
	
	
	public FotcUserPaytype queryById(Integer id);
	
	
	public Integer saveObj(FotcUserPaytype obj);
	
	
	public void updateObj(FotcUserPaytype obj);
	
	
	public boolean queryisBindPayType(Integer fid);
	
	
	public List<FotcUserPaytype> findAllPayTypeById(int fid);
	
	
	public FotcUserPaytype queryisBindType(Integer fid, Integer payType);
	
	
	public List<FotcUserPaytype> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
}
