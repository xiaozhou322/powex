package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FotcInstitutionsinfoDAO;
import com.gt.entity.FotcInstitutionsinfo;

/**
 * 设置机构商service
 * @author zhouyong
 *
 */
@Service("fotcInstitutionsinfoService")
public class FotcInstitutionsinfoServiceImpl implements FotcInstitutionsinfoService{
	@Autowired
	private FotcInstitutionsinfoDAO fotcInstitutionsinfoDao;
	
	public void save(FotcInstitutionsinfo fotcinstitutionsinfo) {
		fotcInstitutionsinfoDao.save(fotcinstitutionsinfo);
	}
	
	public List<FotcInstitutionsinfo> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<FotcInstitutionsinfo> all = this.fotcInstitutionsinfoDao.list(firstResult, maxResults, filter,isFY);	
		return all;
	}

	public FotcInstitutionsinfo findById(int fid) {
		return fotcInstitutionsinfoDao.findById(fid);
	}
	
	public FotcInstitutionsinfo findByUserId(Integer userId) {
		return fotcInstitutionsinfoDao.findByUserId(userId);
	}
}
