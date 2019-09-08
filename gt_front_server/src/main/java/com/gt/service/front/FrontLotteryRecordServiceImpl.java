package com.gt.service.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.LotteryRecordDAO;
import com.gt.entity.LotteryLogModel;
import com.gt.entity.LotteryRecordModel;

@Service("frontLotteryRecordService")
public class FrontLotteryRecordServiceImpl implements FrontLotteryRecordService {
	
	private static final Logger log = LoggerFactory.getLogger(FrontLotteryRecordServiceImpl.class);

	@Autowired
	private LotteryRecordDAO lotteryRecordDAO;
	
	
	public void save(LotteryRecordModel lotteryRecordModel) {
		lotteryRecordDAO.attachDirty(lotteryRecordModel);
		
	}

	public void update(LotteryRecordModel lotteryRecordModel) {
		// TODO Auto-generated method stub
		lotteryRecordDAO.attachDirty(lotteryRecordModel);
	}

	public LotteryRecordModel findById(int id) {
		return lotteryRecordDAO.findById(id);
		
	}

	public List<LotteryRecordModel> getlist(int firstResult,
			int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return lotteryRecordDAO.list(firstResult, maxResults, filter, isFY);
	}

	public List<LotteryRecordModel> findByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return lotteryRecordDAO.findByProperty(propertyName, value);
	}


}
