package com.gt.quartz;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.FactivityStatusEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FactivityModel;
import com.gt.entity.Fentrust;
import com.gt.entity.Flimittrade;
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.service.admin.EntrustService;
import com.gt.service.admin.LimittradeService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.TradehistoryService;
import com.gt.service.front.FrontActivityService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontTradeService;
import com.gt.util.Utils;
import com.gt.util.activity.FactivitytUtil;
import com.gt.utils.ApplicationContextUtil;

public class FactivityTasks {
	private FrontActivityService frontActivityService = (FrontActivityService)ApplicationContextUtil.getBean("frontActivityService");

	public void work() {
		String sql="where status in ("+FactivityStatusEnum.Not_Open+","+FactivityStatusEnum.open+")";
		
		List<FactivityModel> list=frontActivityService.getlist(0, 0, sql, false);
		
		for(FactivityModel factivityModel:list){
			
			try {
				FactivitytUtil factivitytUtil=FactivitytUtil.createWalletByClass(factivityModel.getFclassPath());
				factivitytUtil.luckyDrawTask(factivityModel);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			
		}

	}

	
}