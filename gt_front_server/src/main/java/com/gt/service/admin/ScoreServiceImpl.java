package com.gt.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FscoreDAO;
import com.gt.entity.Fscore;

@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {
	@Autowired
	private FscoreDAO scoreDAO;
	@Autowired
	private HttpServletRequest request;

	public Fscore findById(int id) {
		return this.scoreDAO.findById(id);
	}

	public void saveObj(Fscore obj) {
		this.scoreDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fscore obj = this.scoreDAO.findById(id);
		this.scoreDAO.delete(obj);
	}

	public void updateObj(Fscore obj) {
		this.scoreDAO.attachDirty(obj);
	}
	
	public void updateObj1(Fscore obj,Fscore obj1) {
		try {
			this.scoreDAO.attachDirty(obj);
			if(obj1 != null){
				this.scoreDAO.attachDirty(obj1);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public List<Fscore> findByProperty(String name, Object value) {
		return this.scoreDAO.findByProperty(name, value);
	}

	public List<Fscore> findAll() {
		return this.scoreDAO.findAll();
	}
	
	public void updateData(String sql) {
		this.scoreDAO.updateData(sql);
	}
}
