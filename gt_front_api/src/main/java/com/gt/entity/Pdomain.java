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
 * 项目方域名表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_domain", catalog = "gtcoin")
public class Pdomain implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5585470296008170877L;
	//主键id
	private Integer id;
	//项目方id
	private Fuser projectId;
	//专属域名
	private String exclusiveDomain;
	//完整域名
	private String completeDomain;
	//分成比例
	private double proportions;
	//币种状态（1：正常；4：暂停）
	private Integer status;
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
	
	//otc商家
	private String otcMerchant;
	//市场配置
	private String trademappings;
	
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
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	public Fuser getProjectId() {
		return projectId;
	}
	/**
	 * 设置：专属域名
	 */
	public void setExclusiveDomain(String exclusiveDomain) {
		this.exclusiveDomain = exclusiveDomain;
	}
	/**
	 * 获取：专属域名
	 */
	@Column(name = "exclusive_domain")
	public String getExclusiveDomain() {
		return exclusiveDomain;
	}
	/**
	 * 设置：完整域名
	 */
	public void setCompleteDomain(String completeDomain) {
		this.completeDomain = completeDomain;
	}
	/**
	 * 获取：完整域名
	 */
	@Column(name = "complete_domain")
	public String getCompleteDomain() {
		return completeDomain;
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
	 * 获取：分成比例
	 */
	@Column(name = "proportions")
	public double getProportions() {
		return proportions;
	}
	/**
	 * 设置：分成比例
	 */
	public void setProportions(double proportions) {
		this.proportions = proportions;
	}
	
	/**
	 * 设置：状态（1：正常；0：暂停）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态（1：正常；0：暂停）
	 */
	@Column(name = "status")
	public Integer getStatus() {
		return status;
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
	
	@Column(name = "otc_merchant")
	public String getOtcMerchant() {
		return otcMerchant;
	}
	public void setOtcMerchant(String otcMerchant) {
		this.otcMerchant = otcMerchant;
	}
	public String getTrademappings() {
		return trademappings;
	}
	public void setTrademappings(String trademappings) {
		this.trademappings = trademappings;
	}
	
	
}
