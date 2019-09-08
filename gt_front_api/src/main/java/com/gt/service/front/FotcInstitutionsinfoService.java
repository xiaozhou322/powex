package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcInstitutionsinfo;

/**
 * 设置机构商service
 * @author zhouyong
 *
 */
public interface FotcInstitutionsinfoService {
	
	public void save(FotcInstitutionsinfo fotcinstitutionsinfo);
	
	public List<FotcInstitutionsinfo> list(int firstResult, int maxResults, String filter,boolean isFY);

	public FotcInstitutionsinfo findById(int fid);
	
	public FotcInstitutionsinfo findByUserId(Integer userId);
	
}
