package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FconvertcointypeDAO;
import com.gt.entity.Fconvertcointype;


@Service("fconvertcointypeService")
public class FconvertcointypeServiceImpl implements FconvertcointypeService {
	@Autowired
	private FconvertcointypeDAO fconvertcointypeDAO;

	public Fconvertcointype findById(int id) {
		return this.fconvertcointypeDAO.findById(id);
	}

	public int saveObj(Fconvertcointype obj) {
		this.fconvertcointypeDAO.save(obj);
		return obj.getFid();
	}

	public void deleteObj(int id) {
		Fconvertcointype obj = this.fconvertcointypeDAO.findById(id);
		this.fconvertcointypeDAO.delete(obj);
	}

	public void updateObj(Fconvertcointype obj) {
		this.fconvertcointypeDAO.attachDirty(obj);
	}

	public List<Fconvertcointype> findByProperty(String name, Object value) {
		return this.fconvertcointypeDAO.findByProperty(name, value);
	}

	//selectType 0是=号，1是<>号
	public List<Fconvertcointype> findAll(int coinType,int selectType) {
		return this.fconvertcointypeDAO.findAll(coinType,selectType);
	}
	
	public List<Fconvertcointype> findAll(int coinType1,int coinType2,int selectType) {
		return this.fconvertcointypeDAO.findAll(coinType1,coinType2,selectType);
	}
	
	public List<Fconvertcointype> findAll() {
		return this.fconvertcointypeDAO.findAll();
	}

	public List<Fconvertcointype> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fconvertcointype> all = this.fconvertcointypeDAO.list(firstResult, maxResults, filter,isFY);
		return all;
	}
	
	public List<Fconvertcointype> findAllNormal(int i) {
		// TODO Auto-generated method stub
		return this.fconvertcointypeDAO.findAllNormal(i);
	}
}