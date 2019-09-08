package com.gt.service.front;

import java.util.List;

import com.gt.entity.FactivityModel;
import com.gt.entity.LotteryLogModel;
import com.gt.entity.LotteryRecordModel;


public interface FrontLotteryRecordService {
public void save(LotteryRecordModel factivityRecordModel);
public void update(LotteryRecordModel factivityRecordModel);
//通过id查询活动
public LotteryRecordModel findById(int id);
public List<LotteryRecordModel> findByProperty(String propertyName, Object value);
public List<LotteryRecordModel> getlist(int firstResult, int maxResults,String filter,boolean isFY);
}
