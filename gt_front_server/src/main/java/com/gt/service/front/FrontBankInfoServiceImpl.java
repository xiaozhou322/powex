package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.SystemBankInfoEnum;
import com.gt.dao.SystembankinfoDAO;
import com.gt.entity.Systembankinfo;

@Service("frontBankInfoService")
public class FrontBankInfoServiceImpl implements FrontBankInfoService{
	@Autowired
	private SystembankinfoDAO systembankinfoDAO ;
	
	public List<Systembankinfo> findAllSystemBankInfo(){
		return this.systembankinfoDAO.findByProperty("fstatus", SystemBankInfoEnum.NORMAL_VALUE) ;
	}
	
	public Systembankinfo findSystembankinfoById(int id){
		return this.systembankinfoDAO.findById(id) ;
	}
}
