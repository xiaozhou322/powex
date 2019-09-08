package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fconvertcointype;


public interface FconvertcointypeService {

	public Fconvertcointype findById(int id);

	public int saveObj(Fconvertcointype obj);

	public void deleteObj(int id);

	public void updateObj(Fconvertcointype obj);

	public List<Fconvertcointype> findByProperty(String name, Object value);

	//selectType 0是=号，1是<>号
	public List<Fconvertcointype> findAll(int coinType,int selectType);
	
	public List<Fconvertcointype> findAll(int coinType1,int coinType2,int selectType);
	
	public List<Fconvertcointype> findAll();

	public List<Fconvertcointype> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	public List<Fconvertcointype> findAllNormal(int i);
}