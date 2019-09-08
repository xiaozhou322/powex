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
 * 资产明细
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Entity
@Table(name = "p_capitaldetails", catalog = "gtcoin")
public class Pcapitaldetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7216828307186684834L;
	//主键
	private Integer id;
	//项目方id
	private Fuser projectId;
	//币种id
	private Fvirtualcointype cointypeId;
	//可用余额
	private double availableBalance;
	//冻结资金
	private double frozenFund;
	//估值
	private double valuation;
	//收益总额
	private double totalProfit;
	//版本
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp updateTime;

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
	 * 设置：可用余额
	 */
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	/**
	 * 获取：可用余额
	 */
	@Column(name = "available_balance")
	public double getAvailableBalance() {
		return availableBalance;
	}
	/**
	 * 设置：冻结资金
	 */
	public void setFrozenFund(double frozenFund) {
		this.frozenFund = frozenFund;
	}
	/**
	 * 获取：冻结资金
	 */
	@Column(name = "frozen_fund")
	public double getFrozenFund() {
		return frozenFund;
	}
	/**
	 * 设置：估值
	 */
	public void setValuation(double valuation) {
		this.valuation = valuation;
	}
	/**
	 * 获取：估值
	 */
	@Column(name = "valuation")
	public double getValuation() {
		return valuation;
	}
	/**
	 * 设置：收益总额
	 */
	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}
	/**
	 * 获取：收益总额
	 */
	@Column(name = "total_profit")
	public double getTotalProfit() {
		return totalProfit;
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
