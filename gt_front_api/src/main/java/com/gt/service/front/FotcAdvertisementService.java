package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcAdvertisement;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.PageBean;

import net.sf.json.JSONObject;


/**
 * 承兑商广告service
 * @author zhouyong
 *
 */
public interface FotcAdvertisementService {
	
	public void saveSell(FotcAdvertisement obj,Fvirtualwallet fvirtualwallet1);
	
	
	public void saveBuy(FotcAdvertisement obj);
	
	
	public PageBean<FotcAdvertisement> findAdvertisementByPage(int fid, int pageSize, int pageIndex);
	
	
	public List<FotcAdvertisement> queryOtcAdvertisementByPage(FotcAdvertisement obj, int firstResult,int maxResult);
	

	public int queryOtcAdvertisementCount(FotcAdvertisement obj);

	
	public FotcAdvertisement queryById(Integer adId);
	

	public List<FotcAdvertisement> list(int firstResult, int maxResults, String filter, boolean isFY);

	
	public JSONObject updateAdvertisementDown(int id);
	
	
	public void updateObj(FotcAdvertisement ad);
	
	
	public JSONObject updateAdvertisementOnline(FotcAdvertisement obj);
	
}