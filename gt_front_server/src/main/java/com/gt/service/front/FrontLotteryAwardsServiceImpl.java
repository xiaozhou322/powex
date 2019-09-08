package com.gt.service.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.LotteryAwardsDAO;
import com.gt.entity.LotteryAwardsModel;

@Service("frontLotteryAwardsService")
public class FrontLotteryAwardsServiceImpl implements FrontLotteryAwardsService {
	
	private static final Logger log = LoggerFactory.getLogger(FrontLotteryAwardsServiceImpl.class);

	@Autowired
	private LotteryAwardsDAO lotteryAwardsDAO;
	
	
	public void save(LotteryAwardsModel lotteryAwardsModel) {
		// TODO Auto-generated method stub
		lotteryAwardsDAO.attachDirty(lotteryAwardsModel);
	}

	public void update(LotteryAwardsModel lotteryAwardsModel) {
		// TODO Auto-generated method stub
		lotteryAwardsDAO.attachDirty(lotteryAwardsModel);
	}

	public LotteryAwardsModel findById(int id) {
		return lotteryAwardsDAO.findById(id);
		// TODO Auto-generated method stub
		
	}
	public List<LotteryAwardsModel> getlist(int firstResult,
			int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return lotteryAwardsDAO.list(firstResult, maxResults, filter, isFY);
	}

	public List<LotteryAwardsModel> findByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return this.lotteryAwardsDAO.findByProperty(propertyName, value);
	}

	public List<LotteryAwardsModel> findByTwoProperty(String propertyName1,
			Object value1, String propertyName2, Object value2) {
		// TODO Auto-generated method stub
		return this.lotteryAwardsDAO.findByTwoProperty(propertyName1, value1, propertyName2, value2);
	}


}
