package com.gt.service.front;

import com.gt.entity.FuserFaceID;

public interface FrontUserFaceIDService {
	
	/**
	 * 插入FaceID记录表
	 * @param record
	 */
	public void addUserFaceID(FuserFaceID record);
	
	
	/**
	 * 更新FaceID记录表
	 * @param record
	 */
	public void updateUserFaceID(FuserFaceID record);
	
	/**
	 * 根据userId查询最新的记录
	 * @param userId
	 * @return
	 */
	public FuserFaceID findByUserId(Integer userId);
	
}
