package com.gt.comm;

import java.util.HashMap;
import java.util.Map;

import com.gt.entity.Emailvalidate;

public class ValidateMap {
	private Map<String, MessageValidate> messageMap = new HashMap<String, MessageValidate>() ;
	private Map<String, Emailvalidate> mailMap = new HashMap<String, Emailvalidate>() ;
	
	public synchronized void putMessageMap(String key,MessageValidate messageValidate){
		this.messageMap.put(key, messageValidate) ;
	}
	
	public MessageValidate getMessageMap(String key){
		return this.messageMap.get(key) ;
	}
	public void removeMessageMap(String key){
		if(this.messageMap.isEmpty()) return;
		if(!this.messageMap.containsKey(key)) return;
		this.messageMap.remove(key) ;
	}
	
	public synchronized void putMailMap(String key,Emailvalidate messageValidate){
		this.mailMap.put(key, messageValidate) ;
	}
	
	public Emailvalidate getMailMap(String key){
		return this.mailMap.get(key) ;
	}
	public void removeMailMap(String key){
		if(this.mailMap.isEmpty()) return;
		if(!this.mailMap.containsKey(key)) return;
		this.mailMap.remove(key) ;
	}
}
