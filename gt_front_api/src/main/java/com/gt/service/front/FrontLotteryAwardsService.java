package com.gt.service.front;

import java.util.List;

import com.gt.entity.LotteryAwardsModel;
import com.gt.entity.FactivityModel;


public interface FrontLotteryAwardsService {
public void save(LotteryAwardsModel factivityAwardsModel);
public void update(LotteryAwardsModel factivityAwardsModel);
//通过id查询活动
public LotteryAwardsModel findById(int id);
//
public List<LotteryAwardsModel> getlist(int firstResult, int maxResults,String filter,boolean isFY);
public List<LotteryAwardsModel> findByProperty(String propertyName, Object value);
public List<LotteryAwardsModel> findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2);

}
