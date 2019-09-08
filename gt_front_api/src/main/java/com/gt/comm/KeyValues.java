package com.gt.comm;

import java.io.Serializable;

public class KeyValues  implements Serializable {
	
	private static final long serialVersionUID = 5854228191372675829L;
	
	private Object key;
	private Object value;
	private Object name;
	private Object total;
	private Object status;

	public KeyValues(){}
	public KeyValues(Object key,Object value){
		this.key = key ;
		this.value = value ;
	}
	
	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}

	public Object getTotal() {
		return total;
	}

	public void setTotal(Object total) {
		this.total = total;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

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
