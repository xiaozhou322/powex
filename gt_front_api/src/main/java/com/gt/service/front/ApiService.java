package com.gt.service.front;

import com.gt.entity.Fapi;

public interface ApiService {
	
	public Fapi findFapi(String api_key);
	
	
	public Fapi findFapiByUserId(Integer userId);
}
