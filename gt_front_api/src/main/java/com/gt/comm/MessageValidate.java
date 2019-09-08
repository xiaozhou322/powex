package com.gt.comm;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageValidate implements Serializable {
	
	private static final long serialVersionUID = 4829363723316990682L;
	
	private String areaCode ;
	private String phone ;
	private String code ;
	private Timestamp createTime ;
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
