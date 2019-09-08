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
 * 
 * 冲提币总量统计
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Entity
@Table(name = "p_cw_total_statistics", catalog = "gtcoin")
public class PcwTotalStatistics implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5012587101126495443L;
	//id
	private Integer id;
	//项目方id
	private Fuser projectId;
	//币种id
	private Fvirtualcointype cointypeId;
	//充币人数
	private Integer chargeNum;
	//提币人数
	private Integer withdrawNum;
	//提币数量
	private double chargeAmount;
	//提币数量
	private double withdrawAmount;
	//待审核数量
	private double auditAmount;
	//版本
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//更新时间
	private Timestamp updateTime;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
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
	 * 设置：币种id
	 */
	public void setCointypeId(Fvirtualcointype cointypeId) {
		this.cointypeId = cointypeId;
	}
	/**
	 * 获取：币种id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cointype_id")
	public Fvirtualcointype getCointypeId() {
		return cointypeId;
	}
	/**
	 * 设置：充币人数
	 */
	public void setChargeNum(Integer chargeNum) {
		this.chargeNum = chargeNum;
	}
	/**
	 * 获取：充币人数
	 */
	@Column(name = "charge_num")
	public Integer getChargeNum() {
		return chargeNum;
	}
	/**
	 * 设置：提币人数
	 */
	public void setWithdrawNum(Integer withdrawNum) {
		this.withdrawNum = withdrawNum;
	}
	/**
	 * 获取：提币人数
	 */
	@Column(name = "withdraw_num")
	public Integer getWithdrawNum() {
		return withdrawNum;
	}
	/**
	 * 设置：提币数量
	 */
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	/**
	 * 获取：冲币数量
	 */
	@Column(name = "charge_amount")
	public double getChargeAmount() {
		return chargeAmount;
	}
	/**
	 * 设置：冲币数量
	 */
	public void setWithdrawAmount(double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	/**
	 * 获取：提币数量
	 */
	@Column(name = "withdraw_amount")
	public double getWithdrawAmount() {
		return withdrawAmount;
	}
	/**
	 * 设置：待审核数量
	 */
	public void setAuditAmount(double auditAmount) {
		this.auditAmount = auditAmount;
	}
	/**
	 * 获取：待审核数量
	 */
	@Column(name = "audit_amount")
	public double getAuditAmount() {
		return auditAmount;
	}
	/**
	 * 设置：版本
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取：版本
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
}
