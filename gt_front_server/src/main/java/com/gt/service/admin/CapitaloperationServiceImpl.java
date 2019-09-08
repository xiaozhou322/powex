package com.gt.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FcapitaloperationDAO;
import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FscoreDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;

@Service("capitaloperationService")
public class CapitaloperationServiceImpl implements CapitaloperationService{
	@Autowired
	private FcapitaloperationDAO fcapitaloperationDAO;
	@Autowired
	private FintrolinfoDAO fintrolinfoDAO;
	@Autowired
	private FuserDAO fuserDao;
	@Autowired
	private FscoreDAO fscoreDAO;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;

	public Fcapitaloperation findById(int id) {
		Fcapitaloperation fcapitaloperation = this.fcapitaloperationDAO.findById(id);
		return fcapitaloperation;
	}

	public void saveObj(Fcapitaloperation obj) {
		this.fcapitaloperationDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fcapitaloperation obj = this.fcapitaloperationDAO.findById(id);
		this.fcapitaloperationDAO.delete(obj);
	}

	public void updateObj(Fcapitaloperation obj) {
		this.fcapitaloperationDAO.attachDirty(obj);
	}

	public List<Fcapitaloperation> findByProperty(String name, Object value) {
		return this.fcapitaloperationDAO.findByProperty(name, value);
	}

	public List<Fcapitaloperation> findAll() {
		return this.fcapitaloperationDAO.findAll();
	}

	public List<Fcapitaloperation> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fcapitaloperation> all = this.fcapitaloperationDAO.list(firstResult, maxResults, filter,isFY);
		for (Fcapitaloperation fcapitaloperation : all) {
			if(null != fcapitaloperation.getFuser()) fcapitaloperation.getFuser().getFemail() ;
			if(fcapitaloperation.getfAuditee_id() != null){
				fcapitaloperation.getfAuditee_id().getFname();
			}
			if(fcapitaloperation.getSystembankinfo() != null) fcapitaloperation.getSystembankinfo().getFbankAddress();
		}
		return all;
	}

	public void updateCapital(Fcapitaloperation capitaloperation,Fvirtualwallet fvirtualwallet,boolean isRecharge) throws RuntimeException {
		try {
			this.fcapitaloperationDAO.attachDirty(capitaloperation);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCapital(Fcapitaloperation fcapitaloperation,Fvirtualwallet fvirtualwallet,Fscore fscore,Fintrolinfo info){
		try {
			this.fcapitaloperationDAO.attachDirty(fcapitaloperation);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.fscoreDAO.attachDirty(fscore);
			this.fintrolinfoDAO.save(info);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday) {
		return this.fcapitaloperationDAO.getTotalAmount(type, fstatus,isToday);
	}
	
	public Map getTotalAmountIn(int type,String fstatus,boolean isToday) {
		return this.fcapitaloperationDAO.getTotalAmountIn(type, fstatus,isToday);
	}
	
	public List getTotalGroup(String filter) {
		return this.fcapitaloperationDAO.getTotalGroup(filter);
	}
	
	public List getTotalAmountForReport(String filter) {
		return this.fcapitaloperationDAO.getTotalAmountForReport(filter);
	}
	
	public List getTotalOperationlog(String filter) {
		return this.fcapitaloperationDAO.getTotalOperationlog(filter);
	}
	
	public Map getTotalAmount(int type,String fstatus,boolean isToday,boolean isFee) {
		return this.fcapitaloperationDAO.getTotalAmount(type, fstatus,isToday,isFee);
	}

	public Double getTotalAmountForUser(Fuser fuser){
		return this.fcapitaloperationDAO.getTotalPayAmountForUser(fuser);

	}

}