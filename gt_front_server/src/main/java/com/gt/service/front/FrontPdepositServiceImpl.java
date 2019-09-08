package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.PcointypeDAO;
import com.gt.dao.PdepositDAO;
import com.gt.dao.PdepositlogsDAO;
import com.gt.dao.PtrademappingDAO;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositlogs;
import com.gt.entity.Ptrademapping;



@Service("frontPdepositService")
public class FrontPdepositServiceImpl implements FrontPdepositService {
	
	@Autowired
	private PdepositDAO pdepositDAO;
	@Autowired
	private PdepositlogsDAO pdepositlogsDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO;
	@Autowired
	private PtrademappingDAO ptrademappingDAO;
	@Autowired
	private PcointypeDAO pcointypeDAO;
	
	public void save(Pdeposit instance) {
		pdepositDAO.save(instance);
	}
	
	public Pdeposit findById(java.lang.Integer id) {
		return pdepositDAO.findById(id);
	}
	
	
	public void update(Pdeposit instance) {
		pdepositDAO.attachDirty(instance);
	}
	
	public List<Pdeposit> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pdepositDAO.list(firstResult, maxResults, filter, isFY);
	}

	public void saveAll(Pdeposit pdeposit, Fvirtualwallet fvirtualwallet,
			Pdepositlogs pdepositlog,List<Ptrademapping> ptrademappingList, Pcointype pcointype) {
		pdepositDAO.save(pdeposit);
		pdepositlogsDAO.save(pdepositlog);
		fvirtualwalletDAO.attachDirty(fvirtualwallet);
		for (Ptrademapping ptrademapping : ptrademappingList) {
			ptrademappingDAO.attachDirty(ptrademapping);
		}
		pcointype.setDepositId(pdeposit.getId());
		pcointypeDAO.attachDirty(pcointype);
	}
}
