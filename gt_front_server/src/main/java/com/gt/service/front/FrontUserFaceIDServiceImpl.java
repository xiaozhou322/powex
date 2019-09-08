package com.gt.service.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FuserFaceIDDAO;
import com.gt.entity.FuserFaceID;

@Service("frontUserFaceIDService")
public class FrontUserFaceIDServiceImpl implements FrontUserFaceIDService {
	private static final Logger log = LoggerFactory.getLogger(FrontUserFaceIDServiceImpl.class);
	@Autowired
	private FuserFaceIDDAO fuserFaceIDTempDAO ;
	
	
	/**
	 * 插入FaceID记录表
	 * @param record
	 */
	public void addUserFaceID(FuserFaceID record){
		this.fuserFaceIDTempDAO.save(record) ;
	}
	
	
	/**
	 * 更新FaceID记录表
	 * @param record
	 */
	public void updateUserFaceID(FuserFaceID record) {
		fuserFaceIDTempDAO.attachDirty(record);
	}
	
	
	/**
	 * 根据userId查询最新的记录
	 * @param userId
	 * @return
	 */
	public FuserFaceID findByUserId(Integer userId) {
		return fuserFaceIDTempDAO.findByUserId(userId);
	}
	
}
