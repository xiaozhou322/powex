package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 项目表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_project", catalog = "gtcoin")
public class Pproject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3504949084931186805L;
	//主键
	private Integer id;
	//项目方id
	private Fuser projectId;
	//项目方名称
	private String name;
	//项目方logo地址
	private String logoUrl;
	//项目方网站
	private String website;
	//项目亮点
	private String advantage;
	//项目介绍
	private String introduce;
	//审核状态（101：待审核；102：审核通过；103：审核失败）
	private Integer auditStatus;
	//备注
	private String remark;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp updateTime;
	//审核时间
	private Timestamp auditTime;
	//项目方类型：1是项目方，2是社群
	private Integer type;

	/**
	 * 设置：主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键
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
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	public Fuser getProjectId() {
		return projectId;
	}
	/**
	 * 设置：项目方名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：项目方名称
	 */
	@Column(name = "name")
	public String getName() {
		return name;
	}
	/**
	 * 设置：项目方logo地址
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	/**
	 * 获取：项目方logo地址
	 */
	@Column(name = "logo_url")
	public String getLogoUrl() {
		return logoUrl;
	}
	/**
	 * 设置：项目方网站
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * 获取：项目方网站
	 */
	@Column(name = "website")
	public String getWebsite() {
		return website;
	}
	/**
	 * 设置：项目亮点
	 */
	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}
	/**
	 * 获取：项目亮点
	 */
	@Column(name = "advantage")
	public String getAdvantage() {
		return advantage;
	}
	/**
	 * 设置：项目介绍
	 */
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	/**
	 * 获取：项目介绍
	 */
	@Column(name = "introduce")
	public String getIntroduce() {
		return introduce;
	}
	/**
	 * 设置：审核状态（101：待审核；102：审核通过；103：审核失败）
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取：审核状态（101：待审核；102：审核通过；103：审核失败）
	 */
	@Column(name = "audit_status")
	public Integer getAuditStatus() {
		return auditStatus;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	@Column(name = "remark")
	public String getRemark() {
		return remark;
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
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	@Column(name = "update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：审核时间
	 */
	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * 获取：审核时间
	 */
	@Column(name = "audit_time")
	public Timestamp getAuditTime() {
		return auditTime;
	}
	/**
	 * 设置：项目方类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：项目方类型
	 */
	@Column(name = "type")
	public Integer getType() {
		return type;
	}
}
