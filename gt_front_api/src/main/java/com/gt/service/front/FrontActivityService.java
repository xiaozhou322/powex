package com.gt.service.front;

import java.util.List;

import net.sf.json.JSONObject;

import com.gt.entity.FactivityModel;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;


public interface FrontActivityService {
public void save(FactivityModel factivityModel);
public void update(FactivityModel factivityModel);
//通过id查询活动
public FactivityModel findById(int id);
//
public List<FactivityModel> getlist(int firstResult, int maxResults,String filter,boolean isFY);
public void luckyDrawTask(int id);
public String saveLottery(int userId, int activityId);

public void close(FactivityModel factivityModel);

public List<FactivityModel> list(int firstResult, int maxResults,
		String filter,boolean isFY) ;
//周期性抽奖活动审核
public JSONObject verifyActivity(int id);
}
