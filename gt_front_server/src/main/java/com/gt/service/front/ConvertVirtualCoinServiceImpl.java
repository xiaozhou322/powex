package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.ConvertVirtualCoinDAO;
import com.gt.entity.ConvertVirtualCoin;

@Service("convertVirtualCoinService")
public class ConvertVirtualCoinServiceImpl implements ConvertVirtualCoinService{

	@Autowired
	private ConvertVirtualCoinDAO convertVirtualCoinDAO;
	
	@Override
	public void save(ConvertVirtualCoin instance) {
		// TODO Auto-generated method stub
		convertVirtualCoinDAO.save(instance);
	}

	@Override
	public ConvertVirtualCoin findById(Integer id) {
		// TODO Auto-generated method stub
		return convertVirtualCoinDAO.findById(id);
	}

	@Override
	public ConvertVirtualCoin findByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void update(ConvertVirtualCoin instance) {
		// TODO Auto-generated method stub
		convertVirtualCoinDAO.attachDirty(instance);
	}

	@Override
	public List<ConvertVirtualCoin> list(int firstResult, int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return convertVirtualCoinDAO.list(firstResult, maxResults, filter, isFY);
	}

}
