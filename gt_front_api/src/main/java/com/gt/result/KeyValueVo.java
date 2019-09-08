package com.gt.result;

import java.io.Serializable;

public class KeyValueVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3484122673995345299L;
	private Object key;
	private Object value;
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	
}
