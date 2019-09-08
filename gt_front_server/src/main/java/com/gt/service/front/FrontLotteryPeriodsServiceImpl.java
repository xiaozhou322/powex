package com.gt.service.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.LotteryPeriodsDAO;
import com.gt.entity.LotteryPeriodsModel;

@Service("frontLotteryPeriodsService")
public class FrontLotteryPeriodsServiceImpl implements FrontLotteryPeriodsService {
	
	private static final Logger log = LoggerFactory.getLogger(FrontLotteryPeriodsServiceImpl.class);

	@Autowired
	private LotteryPeriodsDAO lotteryPeriodsDAO;
	
	
	
	public void save(LotteryPeriodsModel lotteryPeriodsModel) {
		// TODO Auto-generated method stub
		lotteryPeriodsDAO.attachDirty(lotteryPeriodsModel);
	}

	
	public void update(LotteryPeriodsModel lotteryPeriodsModel) {
		// TODO Auto-generated method stub
		lotteryPeriodsDAO.attachDirty(lotteryPeriodsModel);
	}

	
	public LotteryPeriodsModel findById(int id) {
		return lotteryPeriodsDAO.findById(id);
		// TODO Auto-generated method stub
		
	}

	
	public List<LotteryPeriodsModel> getlist(int firstResult,
			int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return lotteryPeriodsDAO.list(firstResult, maxResults, filter, isFY);
	}

	
	public List<LotteryPeriodsModel> findByTwoProperty(String propertyName1,
			Object value1, String propertyName2, Object value2) {
		// TODO Auto-generated method stub
		return lotteryPeriodsDAO.findByTwoProperty(propertyName1, value1, propertyName2, value2);
	}


}
