package com.gt.result;

import java.io.Serializable;

public class EchartDataVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6772762620889266020L;
	private Object value;
	private String name;
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
