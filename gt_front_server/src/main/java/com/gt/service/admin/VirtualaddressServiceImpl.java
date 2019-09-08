package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FvirtualaddressDAO;
import com.gt.entity.Fvirtualaddress;

@Service("virtualaddressService")
public class VirtualaddressServiceImpl implements VirtualaddressService {
	@Autowired
	private FvirtualaddressDAO virtualaddressDAO;

	public Fvirtualaddress findById(int id) {
		return this.virtualaddressDAO.findById(id);
	}

	public void saveObj(Fvirtualaddress obj) {
		this.virtualaddressDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fvirtualaddress obj = this.virtualaddressDAO.findById(id);
		this.virtualaddressDAO.delete(obj);
	}

	public void updateObj(Fvirtualaddress obj) {
		this.virtualaddressDAO.attachDirty(obj);
	}

	public List<Fvirtualaddress> findByProperty(String name, Object value) {
		return this.virtualaddressDAO.findByProperty(name, value);
	}

	public List<Fvirtualaddress> findAll() {
		return this.virtualaddressDAO.findAll();
	}

	public List<Fvirtualaddress> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fvirtualaddress> lists = this.virtualaddressDAO.list(firstResult, maxResults, filter,isFY);
		for (Fvirtualaddress fvirtualaddress : lists) {
			if(fvirtualaddress.getFuser() != null){
				fvirtualaddress.getFuser().getFnickName();
			}
		}
		
		return lists;
	}
}