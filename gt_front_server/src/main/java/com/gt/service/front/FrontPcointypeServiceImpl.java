package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.PcointypeDAO;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pcointype;



@Service("frontPcointypeService")
public class FrontPcointypeServiceImpl implements FrontPcointypeService {
	
	@Autowired
	private PcointypeDAO pcointypeDAO;
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO;
	
	public void save(Pcointype instance) {
		pcointypeDAO.save(instance);
	}

	public Pcointype findById(Integer id) {
		return pcointypeDAO.findById(id);
	}

	public void update(Pcointype instance) {
		pcointypeDAO.attachDirty(instance);
	}

	public List<Pcointype> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pcointypeDAO.list(firstResult, maxResults, filter, isFY);
	}
	
	public List findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2) {
		return this.pcointypeDAO.findByTwoProperty(propertyName1,value1,propertyName2, value2);
	}
	
	public void updateAudit(Pcointype pcointype, Fvirtualcointype fvirtualcointype, Integer type) {
		if(type == 1) {
			fvirtualcointypeDAO.save(fvirtualcointype);
			pcointype.setCoinId(fvirtualcointype.getFid());
			pcointypeDAO.attachDirty(pcointype);
		} else if(type == 2) {
			pcointypeDAO.attachDirty(pcointype);
			fvirtualcointypeDAO.attachDirty(fvirtualcointype);
		}
	}
	
	
	public Pcointype findByCoinId(Integer coinId) {
		return pcointypeDAO.findByCoinId(coinId);
	}
}
