package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.LotteryAwardsModel;
import com.gt.entity.LotteryLogModel;
import com.gt.entity.FactivityModel;


public interface FrontLotteryLogService {
public void save(LotteryLogModel factivityLogModel);
public void update(LotteryLogModel factivityLogModel);
//通过id查询活动
public LotteryLogModel findById(int id);
//
public List<LotteryLogModel> findByProperty(String propertyName, Object value);
public List<LotteryLogModel> getlist(int firstResult, int maxResults,String filter,boolean isFY);
public List<Map<String,Object>> getLogList(int id);
}
