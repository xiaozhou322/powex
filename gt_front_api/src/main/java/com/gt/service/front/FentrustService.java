package com.gt.service.front;

import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Fvirtualwallet;

public interface FentrustService {
	
	public void attachDirty(Fentrust fentrust);
	
	public Fentrust findById(int id);

	public void saveTradeDealMaking(Fentrustlog buyLog, Fentrustlog sellLog,
			Fentrust buy, Fentrust sell, Fvirtualwallet fsellVirtualwallet,
			Fvirtualwallet fbuyVirtualwallet, Fvirtualwallet fbuyWallet,
			Fvirtualwallet fsellWallet);
}
