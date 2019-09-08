package com.gt.service.front;

import java.util.List;

import com.gt.entity.Systembankinfo;

public interface FrontBankInfoService {
	
	public List<Systembankinfo> findAllSystemBankInfo();
	
	public Systembankinfo findSystembankinfoById(int id);
}
