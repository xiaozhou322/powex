package com.gt.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FbannerDAO;
import com.gt.entity.Fbanner;

@Service("bannerService")
public class BannerServiceImpl implements BannerService {
	@Autowired
	private FbannerDAO fbannerDAO;
	@Autowired
	private HttpServletRequest request;

	public Fbanner findById(int id) {
		return this.fbannerDAO.findById(id);
	}

	public void saveObj(Fbanner obj) {
		this.fbannerDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fbanner obj = this.fbannerDAO.findById(id);
		this.fbannerDAO.delete(obj);
	}

	public void updateObj(Fbanner obj) {
		this.fbannerDAO.attachDirty(obj);
	}

	public List<Fbanner> findByProperty(String name, Object value) {
		return this.fbannerDAO.findByProperty(name, value);
	}

	public List<Fbanner> findAll() {
		return this.fbannerDAO.findAll();
	}

	public List<Fbanner> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fbanner> all = this.fbannerDAO.list(firstResult, maxResults, filter,isFY);
		for (Fbanner fbanner : all) {
			if(fbanner.getFadminByFcreateAdmin() != null) fbanner.getFadminByFcreateAdmin().getFname();
			if(fbanner.getFadminByFmodifyAdmin() != null) fbanner.getFadminByFmodifyAdmin().getFname();
		}
		return all;
	}
}
