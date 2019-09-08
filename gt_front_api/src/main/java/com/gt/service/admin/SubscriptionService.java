package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Fsubscription;
import com.gt.entity.Fsubscriptionlog;
import com.gt.entity.Fvirtualwallet;

public interface SubscriptionService {

	public Fsubscription findById(int id);

	public void saveObj(Fsubscription obj);

	public void deleteObj(int id);

	public void updateObj(Fsubscription obj);

	public List<Fsubscription> findByProperty(String name, Object value);

	public List<Fsubscription> findAll();
	
	public void updateSubscription(Fvirtualwallet fvirtualwallet1,Fvirtualwallet fvirtualwallet,Fsubscriptionlog fsubscriptionlog,
			Fsubscription fsubscription);

	public List<Fsubscription> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
