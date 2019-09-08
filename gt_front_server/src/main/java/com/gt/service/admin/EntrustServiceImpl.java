package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FentrustDAO;
import com.gt.entity.Fentrust;

@Service("entrustService")
public class EntrustServiceImpl implements EntrustService{
	@Autowired
	private FentrustDAO entrustDAO;

	public Fentrust findById(int id) {
		return this.entrustDAO.findById(id);
	}

	public void saveObj(Fentrust obj) {
		this.entrustDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fentrust obj = this.entrustDAO.findById(id);
		this.entrustDAO.delete(obj);
	}

	public void updateObj(Fentrust obj) {
		this.entrustDAO.attachDirty(obj);
	}

	public List<Fentrust> findByProperty(String name, Object value) {
		return this.entrustDAO.findByProperty(name, value);
	}

	public List<Fentrust> findAll() {
		return this.entrustDAO.findAll();
	}

	public List<Fentrust> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fentrust> all = this.entrustDAO.list(firstResult, maxResults, filter,isFY);
		for (Fentrust fentrust : all) {
			if(fentrust.getFuser() != null){
				fentrust.getFuser().getFnickName();
			}
			if(fentrust.getFtrademapping() != null){
				fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getFname();
				fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
			}
		}
		return all;
	}
	
	public List<Map> getTotalQty(int fentrustType) {
		return this.entrustDAO.getTotalQty(fentrustType);
	}
	
	public List<Map> getMemberQty(String querytype,String queryday,int firstResult, int maxResults) {
		return this.entrustDAO.getMemberQty(querytype, queryday, firstResult, maxResults);
	}
	
	public List<Map> getTotalQty(int fentrustType,boolean isToady) {
		return this.entrustDAO.getTotalQty(fentrustType,isToady);
	}
	
	public List<Map> getMemberMarket(int querytype,String queryday,int firstResult, int maxResults) {
		return this.entrustDAO.getMemberMarket(querytype, queryday, firstResult, maxResults);
	}
	
	public List<Map> getMemberMarket(int querytype,String queryday,String queryday1,int firstResult, int maxResults) {
		return this.entrustDAO.getMemberMarket(querytype, queryday,queryday1, firstResult, maxResults);
	}
}