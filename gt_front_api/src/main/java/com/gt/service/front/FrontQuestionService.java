package com.gt.service.front;

import java.util.List;
import java.util.Map;

import com.gt.entity.Fmessage;
import com.gt.entity.Fquestion;

public interface FrontQuestionService {

	public void save(Fquestion fquestion);
	
	public List<Fquestion> findByUserId(int uid);
	
	public List<Fquestion> findAll(int uid,int status);
	
	public int findByTodayQuestionCount();
	
	public List<Fquestion> findByProperty(String key,Object value);
	
	public int findByParamCount(Map<String, Object> param);
	
	public List<Fquestion> findByParam(Map<String, Object> param,int firstResult,int maxResult,String order);
	
	public int findFmessageByParamCount(Map<String, Object> param);
	
	public List<Fmessage> findFmessageByParam(Map<String, Object> param,int firstResult,int maxResult,String order);
	
	public Fquestion findById(int id);
	
	public void delete(Fquestion fquestion);
	
	public Fmessage findFmessageById(int id);
	
	public void updateFmessage(Fmessage fmessage);
	
	public Fquestion findById_user(int id);

	public void updateObj(Fquestion obj);

	public List<Fquestion> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
