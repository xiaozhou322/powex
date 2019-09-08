package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FmessageDAO;
import com.gt.dao.FquestionDAO;
import com.gt.dao.FuserDAO;
import com.gt.entity.Fmessage;
import com.gt.entity.Fquestion;
import com.gt.entity.Fuser;

@Service("frontQuestionService")
public class FrontQuestionServiceImpl implements FrontQuestionService {

	@Autowired
	public FquestionDAO fquestionDAO ;
	@Autowired
	public FuserDAO fuserDAO ;
	@Autowired
	private FmessageDAO fmessageDAO ;
	
	public void save(Fquestion fquestion){
		this.fquestionDAO.save(fquestion) ;
	}
	
	public List<Fquestion> findByUserId(int uid){
		return this.fquestionDAO.findByProperty("fuser.fid", uid) ;
	}
	
	public List<Fquestion> findAll(int uid,int status){
		Fuser fuser = this.fuserDAO.findById(uid) ;
		Fquestion fquestion = new Fquestion() ;
		fquestion.setFuser(fuser) ;
		fquestion.setFstatus(status) ;
		return this.fquestionDAO.findByExample(fquestion) ;
	}
	
	public int findByTodayQuestionCount(){
		return this.fquestionDAO.findByTodayQuestionCount() ;
	}
	
	public List<Fquestion> findByProperty(String key,Object value){
		return this.fquestionDAO.findByProperty(key, value) ;
	}
	
	public int findByParamCount(Map<String, Object> param) {
		return this.fquestionDAO.findByParamCount(param) ;
	}
	
	public List<Fquestion> findByParam(Map<String, Object> param,int firstResult,int maxResult,String order) {
		return this.fquestionDAO.findByParam(param, firstResult, maxResult, order) ;
	}
	public int findFmessageByParamCount(Map<String, Object> param) {
		return this.fmessageDAO.findFmessageByParamCount(param) ;
	}
	
	public List<Fmessage> findFmessageByParam(Map<String, Object> param,int firstResult,int maxResult,String order) {
		return this.fmessageDAO.findFmessageByParam(param, firstResult, maxResult, order) ;
	}
	
	public Fquestion findById(int id){
		Fquestion fquestion = this.fquestionDAO.findById(id) ;
		return fquestion ;
	}
	public void delete(Fquestion fquestion){
		this.fquestionDAO.delete(fquestion) ;
	}
	
	public Fmessage findFmessageById(int id){
		return this.fmessageDAO.findById(id) ;
	}
	public void updateFmessage(Fmessage fmessage){
		this.fmessageDAO.attachDirty(fmessage) ;
	}
	
	public Fquestion findById_user(int id) {
		Fquestion q = this.fquestionDAO.findById(id);
		if(q.getFuser() != null){
			q.getFuser().getFnickName();
		}
		return q;
	}

	public void updateObj(Fquestion obj) {
		this.fquestionDAO.attachDirty(obj);
	}

	public List<Fquestion> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fquestion> all = this.fquestionDAO.list(firstResult, maxResults, filter,isFY);
		for (Fquestion fquestion : all) {
			if(fquestion.getFuser() != null){
				fquestion.getFuser().getFemail();
			}
			if(fquestion.getFadmin() != null){
				fquestion.getFadmin().getFname();
			}		
		}
		return all;
	}
}
