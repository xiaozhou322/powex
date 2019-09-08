package com.gt.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FmessageDAO;
import com.gt.dao.FscoreDAO;
import com.gt.dao.FsystemargsDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.FusersettingDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fmessage;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fusersetting;
import com.gt.util.Utils;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private FuserDAO fuserDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO;
	@Autowired
	private FintrolinfoDAO fintrolinfoDAO;
	@Autowired
	private FscoreDAO fscoreDAO;
	@Autowired
	private FsystemargsDAO fsystemargsDAO;
	@Autowired
	private FmessageDAO messageDAO;
	@Autowired
	private FusersettingDAO fusersettingDAO;

	public Fuser findById(int id) {
		Fuser fuser = this.fuserDAO.findById(id);
		if(fuser != null){
			if (fuser.getFscore() != null) {
				fuser.getFscore().getFlevel();
			}
			if (fuser.getfIntroUser_id() != null) {
				fuser.getfIntroUser_id().getFlastLoginTime();
			}
		}
		return fuser;
	}

	public void saveObj(Fuser obj) {
		this.fuserDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fuser obj = this.fuserDAO.findById(id);
		this.fuserDAO.delete(obj);
	}

	public void updateObj(Fuser obj) {
		this.fuserDAO.attachDirty(obj);
	}
	
	public void updateObj(Fuser obj,Fmessage message) {
		try {
			this.fuserDAO.attachDirty(obj);
			this.messageDAO.save(message);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateObj(Fuser obj,Fscore fscore,Fuser fintrolUser,Fvirtualwallet fvirtualwallet,Fintrolinfo introlinfo,Fvirtualwallet fvirtualwalletIntro,Fintrolinfo introlinfoIntro) {
		try {
			if(null != obj) {
				this.fuserDAO.attachDirty(obj);
			}
			if(fscore != null){
				this.fscoreDAO.attachDirty(fscore);
			}
			if(fintrolUser != null){
				this.fuserDAO.attachDirty(fintrolUser);
				if(fvirtualwalletIntro!=null){
					this.fvirtualwalletDAO.attachDirty(fvirtualwalletIntro);
				}
				if(introlinfoIntro!=null){
					this.fintrolinfoDAO.save(introlinfoIntro);
				}
			}
			if(fvirtualwallet != null){
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			}
			if(introlinfo != null){
				this.fintrolinfoDAO.save(introlinfo);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}	
	}
	
	
	public void updateObj(Fuser obj,Fintrolinfo introlinfo,Fvirtualwallet fvirtualwallet) {
		try {
			if(null != obj) {
				this.fuserDAO.attachDirty(obj);
			}
			
			if(introlinfo != null){
				this.fintrolinfoDAO.save(introlinfo);
			}
			
			if(fvirtualwallet != null){
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}	
	}

	public List<Fuser> findByProperty(String name, Object value) {
		return this.fuserDAO.findByProperty(name, value);
	}

	public List<Fuser> findAll() {
		return this.fuserDAO.findAll();
	}

	public List<Fuser> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<Fuser> all = this.fuserDAO.list(firstResult, maxResults, filter,
				isFY);
		for (Fuser fuser : all) {
			if (fuser.getFscore() != null) {
				fuser.getFscore().getFlevel();
			}
/*			if(fuser.getfIntroUser_id() != null){
		        fuser.getfIntroUser_id().getFnickName();
			}*/
		}
		return all;
	}
	
	public List<Fuser> simpleList(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<Fuser> all = this.fuserDAO.list(firstResult, maxResults, filter,
				isFY);
		return all;
	}

	public List findByDate(String propertyName, Date value) {
		return this.fuserDAO.findByDate(propertyName, value);
	}

	public List getUserGroup(String filter) {
		return this.fuserDAO.getUserGroup(filter);
	}

	public List<Fuser> listUserForAudit(int firstResult, int maxResults,
			String filter, boolean isFY) {
		return this.fuserDAO.listUserForAudit(firstResult, maxResults, filter,
				isFY);
	}
	
	public List getUser(int type) {
		return this.fuserDAO.getUser(type);
	}
	
	public void updateUser(Fvirtualwallet fvirtualwallet,Fscore fscore) {
		try {
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.fscoreDAO.attachDirty(fscore);
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}
	
	public void updateObj(Fuser obj,Fscore fscore,Fuser fintrolUser,Fvirtualwallet fvirtualwallet,Fintrolinfo introlinfo) {
		try {
			this.fuserDAO.attachDirty(obj);
			if(fscore != null){
				this.fscoreDAO.attachDirty(fscore);
			}
			if(fintrolUser != null){
				this.fuserDAO.attachDirty(fintrolUser);
			}
			if(fvirtualwallet != null){
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			}
			if(introlinfo != null){
				this.fintrolinfoDAO.save(introlinfo);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}	
	}
}