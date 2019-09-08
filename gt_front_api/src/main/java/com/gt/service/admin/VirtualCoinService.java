package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fvirtualcointype;


public interface VirtualCoinService {

	public Fvirtualcointype findById(int id);

	public int saveObj(Fvirtualcointype obj);

	public void deleteObj(int id);

	public void updateObj(Fvirtualcointype obj);

	public List<Fvirtualcointype> findByProperty(String name, Object value);

	//selectType 0是=号，1是<>号
	public List<Fvirtualcointype> findAll(int coinType,int selectType);
	
	public List<Fvirtualcointype> findAll(int coinType1,int coinType2,int selectType);
	
	public List<Fvirtualcointype> findAll();

	public List<Fvirtualcointype> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public boolean updateCoinType(Fvirtualcointype virtualcointype,String password);
	
	public List<Fvirtualcointype> findAllNormal(int i);
}