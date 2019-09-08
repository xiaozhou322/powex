package com.gt.service.front;

import java.util.List;

import com.gt.entity.FscoreRecord;
import com.gt.entity.Fuser;

public interface LevelScoreService {
	
	public void updateScoreRecord(List<FscoreRecord> fscoreRecords,Fuser fuser);
}
