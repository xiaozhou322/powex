package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FlevelSettingDAO;
import com.gt.dao.FscoreDAO;
import com.gt.dao.FscoreRecordDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.UtilsDAO;
import com.gt.entity.FlevelSetting;
import com.gt.entity.Fscore;
import com.gt.entity.FscoreRecord;
import com.gt.entity.Fuser;

@Service("levelScoreService")
public class LevelScoreServiceImpl implements LevelScoreService{

	@Autowired
	private FuserDAO fuserDAO ;
	@Autowired
	private FscoreRecordDAO fscoreRecordDAO ;
	@Autowired
	private FscoreDAO fscoreDAO ;
	@Autowired
	private UtilsDAO utilsDAO ;
	@Autowired
	private FlevelSettingDAO flevelSettingDAO ;
	
	public void updateScoreRecord(List<FscoreRecord> fscoreRecords,Fuser fuser){
		Fscore fscore = this.fscoreDAO.findById(fuser.getFscore().getFid()) ;
		double score = 0 ;
		for (FscoreRecord fscoreRecord : fscoreRecords) {
			this.fscoreRecordDAO.save(fscoreRecord); 
			score += fscoreRecord.getScore() ;
		}
		fscore.setFscore(fscore.getFscore()+score);
		
		List<FlevelSetting> flevelSettings = this.utilsDAO.findByParam(0, 1, " where score<? and score2>? order by level asc ", true, FlevelSetting.class,fscore.getFscore(),fscore.getFscore()) ;
		FlevelSetting flevelSetting = null ;
		if(flevelSettings.size()==0){
			flevelSetting = this.flevelSettingDAO.findById(6) ;
		}else{
			flevelSetting = flevelSettings.get(0) ;
		}
		if(fscore.getFlevel() < flevelSetting.getLevel()){
			fscore.setFlevel(flevelSetting.getLevel());
		}
		this.fscoreDAO.attachDirty(fscore);
	}
}
