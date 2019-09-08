package com.gt.service.front;

import java.util.List;

import com.gt.entity.ConvertVirtualCoin;

public interface ConvertVirtualCoinService {

	public void save(ConvertVirtualCoin instance);
	
	public ConvertVirtualCoin findById(Integer id);
	
	public ConvertVirtualCoin findByOrderId(String orderId);
	
	public void update(ConvertVirtualCoin instance);
	
	public List<ConvertVirtualCoin> list(int firstResult, int maxResults, String filter,boolean isFY);
}
