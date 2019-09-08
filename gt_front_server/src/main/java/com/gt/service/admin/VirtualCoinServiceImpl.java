package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.PCoinTypeStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.PcointypeDAO;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pcointype;


@Service("virtualCoinService")
public class VirtualCoinServiceImpl implements VirtualCoinService {
	@Autowired
	private FvirtualcointypeDAO virtualcointypeDAO;
	@Autowired
	private PcointypeDAO pcointypeDAO;

	public Fvirtualcointype findById(int id) {
		return this.virtualcointypeDAO.findById(id);
	}

	public int saveObj(Fvirtualcointype obj) {
		this.virtualcointypeDAO.save(obj);
		return obj.getFid();
	}

	public void deleteObj(int id) {
		Fvirtualcointype obj = this.virtualcointypeDAO.findById(id);
		this.virtualcointypeDAO.delete(obj);
	}

	public void updateObj(Fvirtualcointype obj) {
		this.virtualcointypeDAO.attachDirty(obj);
	}

	public List<Fvirtualcointype> findByProperty(String name, Object value) {
		return this.virtualcointypeDAO.findByProperty(name, value);
	}

	//selectType 0是=号，1是<>号
	public List<Fvirtualcointype> findAll(int coinType,int selectType) {
		return this.virtualcointypeDAO.findAll(coinType,selectType);
	}
	
	public List<Fvirtualcointype> findAll(int coinType1,int coinType2,int selectType) {
		return this.virtualcointypeDAO.findAll(coinType1,coinType2,selectType);
	}
	
	public List<Fvirtualcointype> findAll() {
		return this.virtualcointypeDAO.findAll();
	}

	public List<Fvirtualcointype> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fvirtualcointype> all = this.virtualcointypeDAO.list(firstResult, maxResults, filter,isFY);
		return all;
	}
	
	public boolean updateCoinType(Fvirtualcointype virtualcointype,String password) throws RuntimeException{
		String result = "";
		try {
			result = this.virtualcointypeDAO.startCoinType(virtualcointype.getFid());
			if(!result.equals("SUCCESS")){
				throw new RuntimeException(result) ;
			}
			Pcointype pcointype = pcointypeDAO.findByCoinId(virtualcointype.getFid());
			if(pcointype != null) {
				pcointype.setStatus(PCoinTypeStatusEnum.NORMAL);
				pcointypeDAO.attachDirty(pcointype);
			}
		} catch (Exception e) {
			throw new RuntimeException(result) ;
		}
		this.virtualcointypeDAO.attachDirty(virtualcointype);
		return true;
	}
	
	public List<Fvirtualcointype> findAllNormal(int i) {
		// TODO Auto-generated method stub
		return this.virtualcointypeDAO.findAllNormal(i);
	}
}