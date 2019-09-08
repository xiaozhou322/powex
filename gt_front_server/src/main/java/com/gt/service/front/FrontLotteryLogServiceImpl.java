package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import com.gt.dao.LotteryLogDAO;
import com.gt.entity.LotteryLogModel;

@Service("frontLotteryLogService")
public class FrontLotteryLogServiceImpl implements FrontLotteryLogService {
	
	private static final Logger log = LoggerFactory.getLogger(FrontLotteryLogServiceImpl.class);

	@Autowired
	private LotteryLogDAO lotteryLogDAO;
	
	public void save(LotteryLogModel lotteryLogModel) {
		// TODO Auto-generated method stub
		lotteryLogDAO.attachDirty(lotteryLogModel);
	}

	public void update(LotteryLogModel lotteryLogModel) {
		// TODO Auto-generated method stub
		lotteryLogDAO.attachDirty(lotteryLogModel);
	}

	public LotteryLogModel findById(int id) {
		return lotteryLogDAO.findById(id);
		// TODO Auto-generated method stub
		
	}

	public List<LotteryLogModel> getlist(int firstResult,
			int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return lotteryLogDAO.list(firstResult, maxResults, filter, isFY);
	}

	public List<LotteryLogModel> findByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return lotteryLogDAO.findByProperty(propertyName, value);
	}
	public List<Map<String,Object>> getLogList(int id) {
		// TODO Auto-generated method stub
		return lotteryLogDAO.getLogList(id);
	}

}
