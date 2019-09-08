package com.gt.service.admin;

import java.util.List;

import com.gt.entity.FscoreRecord;

public interface ScoreRecordService {

	public FscoreRecord findById(int id);

	public void saveObj(FscoreRecord obj);

	public void deleteObj(int id);

	public void updateObj(FscoreRecord obj);

	public List<FscoreRecord> findByProperty(String name, Object value);

	public List<FscoreRecord> findAll();

	public List<FscoreRecord> list(int firstResult, int maxResults, String filter,boolean isFY);
}
