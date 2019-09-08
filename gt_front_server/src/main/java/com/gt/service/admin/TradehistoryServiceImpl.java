package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FtradehistoryDAO;
import com.gt.entity.Ftradehistory;

@Service("tradehistoryService")
public class TradehistoryServiceImpl implements TradehistoryService {
	@Autowired
	private FtradehistoryDAO tradehistoryDAO;

	public Ftradehistory findById(int id) {
		return this.tradehistoryDAO.findById(id);
	}
	
	public void updateUser(String sql){
		this.tradehistoryDAO.updateUser(sql);
	}

	public void saveObj(Ftradehistory obj) {
		this.tradehistoryDAO.save(obj);
	}

	public void deleteObj(int id) {
		Ftradehistory obj = this.tradehistoryDAO.findById(id);
		this.tradehistoryDAO.delete(obj);
	}

	public void updateObj(Ftradehistory obj) {
		this.tradehistoryDAO.attachDirty(obj);
	}

	public List<Ftradehistory> findByProperty(String name, Object value) {
		return this.tradehistoryDAO.findByProperty(name, value);
	}

	public List<Ftradehistory> findAll() {
		return this.tradehistoryDAO.findAll();
	}

	public List<Ftradehistory> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Ftradehistory> lists = this.tradehistoryDAO.list(firstResult, maxResults, filter,isFY);
		for (Ftradehistory ftradehistory : lists) {
			if(ftradehistory.getFtrademapping().getFvirtualcointypeByFvirtualcointype1() != null){
				ftradehistory.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getFname();
			}
			if(ftradehistory.getFtrademapping().getFvirtualcointypeByFvirtualcointype2() != null){
				ftradehistory.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
			}
		}
		return lists;
	}
}