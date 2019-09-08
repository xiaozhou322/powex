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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 保证金表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_deposit", catalog = "gtcoin")
public class Pdeposit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4707199688496048104L;
	//主键
	private Integer id;
	//项目方id
	private Fuser projectId;
	//币种id
	private Pcointype cointypeId;
	//保证金币种id
	private Pdepositcointype depositCointypeId;
	//缴纳数量
	private double payAmount;
	//可用数量
	private double availableAmount;
	//冻结数量
	private double frozenAmount;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;

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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	public Fuser getProjectId() {
		return projectId;
	}
	/**
	 * 设置：币种id
	 */
	public void setCointypeId(Pcointype cointypeId) {
		this.cointypeId = cointypeId;
	}
	/**
	 * 获取：币种id
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cointype_id")
	public Pcointype getCointypeId() {
		return cointypeId;
	}
	/**
	 * 设置：保证金币种id
	 */
	public void setDepositCointypeId(Pdepositcointype depositCointypeId) {
		this.depositCointypeId = depositCointypeId;
	}
	/**
	 * 获取：保证金币种id
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deposit_cointype_id")
	public Pdepositcointype getDepositCointypeId() {
		return depositCointypeId;
	}
	/**
	 * 设置：缴纳数量
	 */
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	/**
	 * 获取：缴纳数量
	 */
	@Column(name = "pay_amount")
	public double getPayAmount() {
		return payAmount;
	}
	/**
	 * 设置：可用数量
	 */
	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}
	/**
	 * 获取：可用数量
	 */
	@Column(name = "available_amount")
	public double getAvailableAmount() {
		return availableAmount;
	}
	/**
	 * 设置：冻结数量
	 */
	public void setFrozenAmount(double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	/**
	 * 获取：冻结数量
	 */
	@Column(name = "frozen_amount")
	public double getFrozenAmount() {
		return frozenAmount;
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
