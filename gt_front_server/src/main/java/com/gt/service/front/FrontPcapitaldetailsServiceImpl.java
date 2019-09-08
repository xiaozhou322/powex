package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.PcapitaldetailsDAO;
import com.gt.entity.Pcapitaldetails;



@Service("frontPcapitaldetailsService")
public class FrontPcapitaldetailsServiceImpl implements FrontPcapitaldetailsService {
	@Autowired
	private PcapitaldetailsDAO pcapitaldetailsDAO;
	
	public void save(Pcapitaldetails instance) {
		pcapitaldetailsDAO.save(instance);
	}
	
	public Pcapitaldetails findById(java.lang.Integer id) {
		return pcapitaldetailsDAO.findById(id);
	}
	
	
	public void update(Pcapitaldetails instance) {
		pcapitaldetailsDAO.attachDirty(instance);
	}
	
	public List<Pcapitaldetails> list(int firstResult, int maxResults, String filter,boolean isFY) {
		return pcapitaldetailsDAO.list(firstResult, maxResults, filter, isFY);
	}
}
