package com.gt.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FvalidatemessageDAO;
import com.gt.entity.Fvalidatemessage;

@Service("validatemessageService")
public class ValidatemessageServiceImpl implements ValidatemessageService {
	@Autowired
	private FvalidatemessageDAO validatemessageDAO;
	@Autowired
	private HttpServletRequest request;

	public Fvalidatemessage findById(int id) {
		return this.validatemessageDAO.findById(id);
	}

	public void saveObj(Fvalidatemessage obj) {
		this.validatemessageDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fvalidatemessage obj = this.validatemessageDAO.findById(id);
		this.validatemessageDAO.delete(obj);
	}

	public void updateObj(Fvalidatemessage obj) {
		this.validatemessageDAO.attachDirty(obj);
	}

	public List<Fvalidatemessage> findByProperty(String name, Object value) {
		return this.validatemessageDAO.findByProperty(name, value);
	}

	public List<Fvalidatemessage> findAll() {
		return this.validatemessageDAO.findAll();
	}

	public List<Fvalidatemessage> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.validatemessageDAO.list(firstResult, maxResults, filter,isFY);
	}
}
