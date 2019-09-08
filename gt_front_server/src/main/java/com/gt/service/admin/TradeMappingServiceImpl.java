package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FtrademappingDAO;
import com.gt.dao.PtrademappingDAO;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Ptrademapping;

@Service("tradeMappingService")
public class TradeMappingServiceImpl implements TradeMappingService {
	@Autowired
	private FtrademappingDAO trademappingDAO;
	@Autowired
	private PtrademappingDAO ptrademappingDAO;

	public Ftrademapping findById(int id) {
		Ftrademapping ftrademapping = this.trademappingDAO.findById(id);
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1() != null){
			ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname();
		}
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype2() != null){
			ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname();
		}
		return ftrademapping;
	}

	public int saveObj(Ftrademapping obj) {
		this.trademappingDAO.save(obj);
		return obj.getFid();
	}

	public void deleteObj(int id) {
		Ftrademapping obj = this.trademappingDAO.findById(id);
		this.trademappingDAO.delete(obj);
	}

	public void updateObj(Ftrademapping obj) {
		this.trademappingDAO.attachDirty(obj);
	}

	public List<Ftrademapping> findByProperty(String name, Object value) {
		return this.trademappingDAO.findByProperty(name, value);
	}

	public List<Ftrademapping> findAll() {
		return this.trademappingDAO.findAll();
	}

	public List<Ftrademapping> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Ftrademapping> lists = this.trademappingDAO.list(firstResult, maxResults, filter,isFY);
		for (Ftrademapping ftrademapping : lists) {
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1() != null){
				ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname();
			}
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype2() != null){
				ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname();
			}
		}
		return lists;
	}
	
	public void updateTrademappings(Ftrademapping obj, Ptrademapping obj2) {
		this.trademappingDAO.attachDirty(obj);
		
		this.ptrademappingDAO.attachDirty(obj2);
	}
}