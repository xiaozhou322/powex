package com.gt.service.front;

import java.util.List;

import com.gt.entity.FactivityModel;
import com.gt.entity.LotteryAwardsModel;
import com.gt.entity.LotteryPeriodsModel;


public interface FrontLotteryPeriodsService {
public void save(LotteryPeriodsModel factivityPeriodsModel);
public void update(LotteryPeriodsModel factivityPeriodsModel);
//通过id查询活动
public LotteryPeriodsModel findById(int id);
//
public List<LotteryPeriodsModel> getlist(int firstResult, int maxResults,String filter,boolean isFY);
public List<LotteryPeriodsModel> findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2);
}
