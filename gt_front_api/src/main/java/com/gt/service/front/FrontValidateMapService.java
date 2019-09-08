package com.gt.service.front;

import com.gt.comm.MessageValidate;
import com.gt.entity.Emailvalidate;

public interface FrontValidateMapService {
	
	public MessageValidate getMessageMap(String key);
	
	public void removeMessageMap(String key);
	
	
	public Emailvalidate getMailMap(String key);
	
	public void removeMailMap(String key);
	
	public void putMessageMap(String key,MessageValidate messageValidate);
	
	public void putMailMap(String key,Emailvalidate messageValidate);
}
