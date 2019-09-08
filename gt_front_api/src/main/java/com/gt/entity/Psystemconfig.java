package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 项目方系统配置表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_systemconfig", catalog = "gtcoin")
public class Psystemconfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4548082472839512574L;
	//主键id
	private Integer id;
	//项目方id
	private Fuser projectId;
	//key
	private String fkey;
	//value
	private String fvalue;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：项目方id
	 */
	public void setProjectId(Fuser projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目方id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	public Fuser getProjectId() {
		return projectId;
	}
	/**
	 * 设置：fkey
	 */
	public void setFkey(String fkey) {
		this.fkey = fkey;
	}
	/**
	 * 获取：fkey
	 */
	@Column(name = "fkey")
	public String getFkey() {
		return fkey;
	}
	/**
	 * 设置：fvalue
	 */
	public void setFvalue(String fvalue) {
		this.fvalue = fvalue;
	}
	/**
	 * 获取：fvalue
	 */
	@Column(name = "fvalue")
	public String getFvalue() {
		return fvalue;
	}
	/**
	 * 设置：版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取：版本号
	 */
	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}
}
