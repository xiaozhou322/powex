package com.gt.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FscoreRecordDAO;
import com.gt.entity.FscoreRecord;

@Service("scoreRecordService")
public class ScoreRecordServiceImpl implements ScoreRecordService {
	@Autowired
	private FscoreRecordDAO scoreRecordDAO;
	@Autowired
	private HttpServletRequest request;

	public FscoreRecord findById(int id) {
		return this.scoreRecordDAO.findById(id);
	}

	public void saveObj(FscoreRecord obj) {
		this.scoreRecordDAO.save(obj);
	}

	public void deleteObj(int id) {
		FscoreRecord obj = this.scoreRecordDAO.findById(id);
		this.scoreRecordDAO.delete(obj);
	}

	public void updateObj(FscoreRecord obj) {
		this.scoreRecordDAO.attachDirty(obj);
	}

	public List<FscoreRecord> findByProperty(String name, Object value) {
		return this.scoreRecordDAO.findByProperty(name, value);
	}

	public List<FscoreRecord> findAll() {
		return this.scoreRecordDAO.findAll();
	}

	public List<FscoreRecord> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<FscoreRecord> list = this.scoreRecordDAO.list(firstResult, maxResults, filter,isFY);
		for (FscoreRecord fscoreRecord : list) {
			fscoreRecord.getFuser().getFnickName();
		}
		return list;
	}
}
