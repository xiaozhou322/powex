package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FmessageDAO;
import com.gt.entity.Fmessage;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	@Autowired
	private FmessageDAO messageDAO;

	public Fmessage findById(int id) {
		return this.messageDAO.findById(id);
	}

	public void saveObj(Fmessage obj) {
		this.messageDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fmessage obj = this.messageDAO.findById(id);
		this.messageDAO.delete(obj);
	}

	public void updateObj(Fmessage obj) {
		this.messageDAO.attachDirty(obj);
	}

	public List<Fmessage> findByProperty(String name, Object value) {
		return this.messageDAO.findByProperty(name, value);
	}

	public List<Fmessage> findAll() {
		return this.messageDAO.findAll();
	}

	public List<Fmessage> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fmessage> lists = this.messageDAO.list(firstResult, maxResults, filter,isFY);
		for (Fmessage fmessage : lists) {
			if(fmessage.getFreceiver() != null){
				fmessage.getFreceiver().getFnickName();
			}
		}
		return lists;
	}
}