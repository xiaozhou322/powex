package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fsubscription;
import com.gt.entity.Fsubscriptionlog;
import com.gt.entity.Fvirtualwallet;

public interface SubscriptionLogService {

	public Fsubscriptionlog findById(int id);

	public void saveObj(Fsubscriptionlog obj);

	public void deleteObj(int id);

	public void updateObj(Fsubscriptionlog obj);

	public List<Fsubscriptionlog> findByProperty(String name, Object value);

	public List<Fsubscriptionlog> findAll();
	
	public void updateChargeLog1(List<Fvirtualwallet> fvirtualwallets,List<Fintrolinfo> fintrolinfos,Fsubscriptionlog fsubscriptionlog);

	public List<Fsubscriptionlog> list(int firstResult, int maxResults,String filter,boolean isFY);
	
	public void updateChargeLog(Fsubscriptionlog fsubscriptionlog,Fvirtualwallet w,Fvirtualwallet v);
	
	public void updateChargeLog(Fsubscriptionlog fsubscriptionlog,Fvirtualwallet w1,Fvirtualwallet v1,Fvirtualwallet v);
	
	public void updateSendFrozen(Fvirtualwallet fvirtualwallet,Fsubscriptionlog fsubscriptionlog,Fintrolinfo info);
	
	public void updateChargeLog(Fsubscriptionlog fsubscriptionlog,Fsubscription sub);
}
