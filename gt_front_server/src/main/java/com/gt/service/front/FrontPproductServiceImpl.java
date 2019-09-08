package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.PproductDAO;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pproduct;



@Service("frontPproductService")
public class FrontPproductServiceImpl implements FrontPproductService {
	
	@Autowired
	private PproductDAO pproductDAO;
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO;
	
	public void save(Pproduct instance) {
		pproductDAO.save(instance);
	}

	public Pproduct findById(Integer id) {
		return pproductDAO.findById(id);
	}

	public void update(Pproduct instance) {
		pproductDAO.attachDirty(instance);
	}

	public List<Pproduct> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pproductDAO.list(firstResult, maxResults, filter, isFY);
	}
	
	public List findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2) {
		return this.pproductDAO.findByTwoProperty(propertyName1,value1,propertyName2, value2);
	}
	
	public void updateAudit(Pproduct product, Fvirtualcointype fvirtualcointype, Integer type) {
		if(type == 1) {
			fvirtualcointypeDAO.save(fvirtualcointype);
			product.setCoinType(fvirtualcointype);
			pproductDAO.attachDirty(product);
		} else if(type == 2) {
			pproductDAO.attachDirty(product);
			fvirtualcointypeDAO.attachDirty(fvirtualcointype);
		}
	}
	
	
	public Pproduct findByCoinId(Integer coinId) {
		return pproductDAO.findByCoinId(coinId);
	}

}
