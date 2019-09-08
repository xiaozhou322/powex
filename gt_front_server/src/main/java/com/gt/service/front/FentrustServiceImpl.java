package com.gt.service.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FentrustDAO;
import com.gt.dao.FentrustlogDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.UtilsDAO;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Fvirtualwallet;

@Service("fentrustService")
public class FentrustServiceImpl implements FentrustService {
	
	private static final Logger log = LoggerFactory.getLogger(FentrustServiceImpl.class);
	
	@Autowired
	private FentrustDAO fentrustDAO;
	@Autowired
	private FentrustlogDAO fentrustlogDAO;
	@Autowired
	private UtilsDAO utilsDAO;
	
	public void attachDirty(Fentrust fentrust) {
		fentrustDAO.attachDirty(fentrust);
	}

	
	public Fentrust findById(int id) {
		return fentrustDAO.findById(id);
	}

	
	public void saveTradeDealMaking(Fentrustlog buyLog, Fentrustlog sellLog,
			Fentrust buy, Fentrust sell, Fvirtualwallet w1,
			Fvirtualwallet w2, Fvirtualwallet w3,
			Fvirtualwallet w4) {
		try {
			fentrustlogDAO.save(buyLog);
			fentrustlogDAO.save(sellLog);
			fentrustDAO.attachDirty(buy);
			fentrustDAO.attachDirty(sell);
			
			String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=?" ;
			int count = this.utilsDAO.executeHQL(hql, w1.getFtotal(),w1.getFfrozen(),w1.getFack_uid(),w1.getFack_vid()) ;
					
			if(w2.getFack_id().equals(w1.getFack_id()) == false ){
				count = this.utilsDAO.executeHQL(hql, w2.getFtotal(),w2.getFfrozen(),w2.getFack_uid(),w2.getFack_vid()) ;
			}

			if(w3.getFack_id().equals(w1.getFack_id()) == false &&w3.getFack_id().equals(w2.getFack_id()) == false ){
				count = this.utilsDAO.executeHQL(hql, w3.getFtotal(),w3.getFfrozen(),w3.getFack_uid(),w3.getFack_vid()) ;
			}

			if(w4.getFack_id().equals(w1.getFack_id()) == false &&w4.getFack_id().equals(w2.getFack_id()) == false &&w4.getFack_id().equals(w3.getFack_id()) == false ){
				count = this.utilsDAO.executeHQL(hql, w4.getFtotal(),w4.getFfrozen(),w4.getFack_uid(),w4.getFack_vid()) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FentrustServiceImpl saveTradeDealMaking failed", e);
		}
	}

}
