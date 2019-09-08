package com.gt.utils;


import java.sql.Timestamp;

import org.springframework.stereotype.Service;


import com.gt.entity.FactivityModel;
import com.gt.service.front.FrontActivityService;
import com.gt.util.Utils;
import com.gt.util.activity.FactivitytUtil;

@Service
public class Lottery extends FactivitytUtil {
	private FrontActivityService frontActivityService = (FrontActivityService)ApplicationContextUtil.getBean("frontActivityService");	
	public Lottery() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void luckyDrawTask(FactivityModel factivityModel) throws Exception {
		//如果活动为一次性活动
		if(factivityModel.getType()==1){
			//活动方式为抽奖
			if(factivityModel.getWay()==1){
				Timestamp thisTime=Utils.getTimestamp();
				if(thisTime.getTime()>factivityModel.getStart_time().getTime()&&thisTime.getTime()<factivityModel.getEnd_time().getTime()){
					//如果为
					frontActivityService.luckyDrawTask(factivityModel.getId());
					
				}else if(thisTime.getTime()>factivityModel.getEnd_time().getTime()){
					frontActivityService.close(factivityModel);
					
				}
			}
			
				
		}
	}

	@Override
	public void close(FactivityModel factivityModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

	
}
