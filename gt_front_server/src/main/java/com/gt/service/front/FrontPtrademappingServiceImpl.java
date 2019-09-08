package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FfeesDAO;
import com.gt.dao.FtrademappingDAO;
import com.gt.dao.PtrademappingDAO;
import com.gt.entity.Ffees;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Ptrademapping;
import com.gt.util.Constant;



@Service("frontPtrademappingService")
public class FrontPtrademappingServiceImpl implements FrontPtrademappingService {
	@Autowired
	private PtrademappingDAO ptrademappingDAO;
	@Autowired
	private FtrademappingDAO ftrademappingDAO;
	
	@Autowired
	private FfeesDAO feesDAO;
	
	public void save(Ptrademapping instance) {
		ptrademappingDAO.save(instance);
	}
	
	public Ptrademapping findById(java.lang.Integer id) {
		return ptrademappingDAO.findById(id);
	}
	
	
	public void update(Ptrademapping instance) {
		ptrademappingDAO.attachDirty(instance);
	}
	
	public List<Ptrademapping> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return ptrademappingDAO.list(firstResult, maxResults, filter, isFY);
	}
	
	public void updateAudit(Ptrademapping ptrademapping, Ftrademapping ftrademapping, int type) {
		if(type == 1) {
			ftrademappingDAO.save(ftrademapping);
			ptrademapping.setTradeMappingId(ftrademapping.getFid());
			ptrademappingDAO.attachDirty(ptrademapping);
			
			for(int i=1;i<=Constant.VIP;i++){
				Ffees fees = new Ffees();
				fees.setFlevel(i);
				fees.setFtrademapping(ftrademapping);
				//设置默认交易费
				/*
				 * level 1,0.001
				 * level 2,0.0008
				 * level 3,0.0006
				 * level 4,0.0004
				 * level 5,0.0002
				 * level 6,0
				 * 
				 */
				fees.setFfee((6-i)*0.0002);
				fees.setFbuyfee((6-i)*0.0002);
				this.feesDAO.save(fees);
			}
		}else if(type == 2) {
			ftrademappingDAO.attachDirty(ftrademapping);
			ptrademappingDAO.attachDirty(ptrademapping);
		}
	}
	
	
	/**
	 * 根据平台市场id查找项目方市场信息
	 * @param coinId
	 * @return
	 */
	public Ptrademapping findByTrademappingId(Integer trademappingId) {
		return this.ptrademappingDAO.findByTrademappingId(trademappingId);
	}
}
