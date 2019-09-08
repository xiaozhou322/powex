package com.gt.service.front;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gt.comm.MessageValidate;
import com.gt.entity.Emailvalidate;

@Service
public class FrontValidateMapServiceImpl implements FrontValidateMapService {
	
	private static Map<String, MessageValidate> messageMap = new HashMap<String, MessageValidate>() ;
	private static Map<String, Emailvalidate> mailMap = new HashMap<String, Emailvalidate>() ;
	
	public void putMessageMap(String key,MessageValidate messageValidate){
		synchronized (this) {
			FrontValidateMapServiceImpl.messageMap.put(key, messageValidate) ;
		}
	}
	
	public MessageValidate getMessageMap(String key){
		return FrontValidateMapServiceImpl.messageMap.get(key) ;
	}
	
	public void removeMessageMap(String key){
		if(FrontValidateMapServiceImpl.messageMap.isEmpty()) return;
		if(!FrontValidateMapServiceImpl.messageMap.containsKey(key)) return;
		FrontValidateMapServiceImpl.messageMap.remove(key) ;
	}
	
	public void putMailMap(String key,Emailvalidate messageValidate){
		synchronized (this) {
			FrontValidateMapServiceImpl.mailMap.put(key, messageValidate) ;
		}
	}
	
	public Emailvalidate getMailMap(String key){
		return FrontValidateMapServiceImpl.mailMap.get(key) ;
	}
	public void removeMailMap(String key){
		if(FrontValidateMapServiceImpl.mailMap.isEmpty()) return;
		if(!FrontValidateMapServiceImpl.mailMap.containsKey(key)) return;
		FrontValidateMapServiceImpl.mailMap.remove(key) ;
	}
}
