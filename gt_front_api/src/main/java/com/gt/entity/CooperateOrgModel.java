package com.gt.entity;

import java.io.Serializable;

/**
 * 合作机构model
 * @author zhouyong
 *
 */
public class CooperateOrgModel implements Serializable, Comparable<CooperateOrgModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5900613703881972711L;
	
	/** id **/
	private Integer id;   
	
	/** 机构名称 **/
	private String orgName;
	
	/** 图片url **/
	private String picUrl;
	
	/** 链接地址 **/
	private String linkUrl;

	
	
	public CooperateOrgModel() {
		super();
	}
	

	public CooperateOrgModel(Integer id, String orgName, String picUrl, String linkUrl) {
		super();
		this.id = id;
		this.orgName = orgName;
		this.picUrl = picUrl;
		this.linkUrl = linkUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	
	// 重写 比较方法   按id  ——> desc排序
	@Override 
	public int compareTo(CooperateOrgModel org) {
		if (this.id < org.getId()) {
			return 1;
		} else {
			return -1; 
		}
	}

}
