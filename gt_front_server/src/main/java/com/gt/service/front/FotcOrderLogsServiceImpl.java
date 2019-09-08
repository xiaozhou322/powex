package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FotcOrderLogsDAO;
import com.gt.entity.FotcOrderLogs;



@Service("fotcOrderLogsService")
public class FotcOrderLogsServiceImpl implements FotcOrderLogsService {
	
	@Autowired
	private FotcOrderLogsDAO otcOrderLogsDAO;
	
	public void save(FotcOrderLogs instance) {
		otcOrderLogsDAO.save(instance);
	}
	
	public FotcOrderLogs findById(java.lang.Integer id) {
		return otcOrderLogsDAO.findById(id);
	}
	
	
	public void update(FotcOrderLogs instance) {
		otcOrderLogsDAO.attachDirty(instance);
	}
	
	public List<FotcOrderLogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FotcOrderLogs> otcOrderLogsList = otcOrderLogsDAO.list(firstResult, maxResults, filter, isFY);
		
		for (FotcOrderLogs otcOrderLogs : otcOrderLogsList) {
			if(null != otcOrderLogs.getBuyUser()) {
				otcOrderLogs.getBuyUser().getFrealName();
			}
			if(null != otcOrderLogs.getSellUser()) {
				otcOrderLogs.getSellUser().getFrealName();
			}
			if(null != otcOrderLogs.getAppealUser()) {
				otcOrderLogs.getAppealUser().getFrealName();
			}
		}
		return otcOrderLogsList;
	}
	
}
