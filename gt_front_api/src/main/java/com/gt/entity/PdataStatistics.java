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
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Entity
@Table(name = "p_data_statistics", catalog = "gtcoin")
public class PdataStatistics implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5476546893787282172L;
	//id
	private Integer id;
	//项目方id
	private Fuser projectId;
	//统计时间
	private String statisticalDate;
	//委托总数
	private double entrustTotalAmount;
	//买入挂单数
	private double entrustBuyAmount;
	//卖出挂单数
	private double entrustSellAmount;
	//交易额
	private double tradeTotalAmount;
	//买入成交数
	private double tradeBuyAmount;
	//卖出成交数
	private double tradeSellAmount;
	//充提总额
	private double chargeWithdrawNum;
	//充币数量
	private double chargeNum;
	//提币数量
	private double withdrawNum;
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
	 * 设置：统计时间
	 */
	public void setStatisticalDate(String statisticalDate) {
		this.statisticalDate = statisticalDate;
	}
	/**
	 * 获取：统计时间
	 */
	@Column(name = "statistical_date")
	public String getStatisticalDate() {
		return statisticalDate;
	}
	/**
	 * 设置：委托总数
	 */
	public void setEntrustTotalAmount(double entrustTotalAmount) {
		this.entrustTotalAmount = entrustTotalAmount;
	}
	/**
	 * 获取：委托总数
	 */
	@Column(name = "entrust_total_amount")
	public double getEntrustTotalAmount() {
		return entrustTotalAmount;
	}
	/**
	 * 设置：买入挂单数
	 */
	public void setEntrustBuyAmount(double entrustBuyAmount) {
		this.entrustBuyAmount = entrustBuyAmount;
	}
	/**
	 * 获取：买入挂单数
	 */
	@Column(name = "entrust_buy_amount")
	public double getEntrustBuyAmount() {
		return entrustBuyAmount;
	}
	/**
	 * 设置：卖出挂单数
	 */
	public void setEntrustSellAmount(double entrustSellAmount) {
		this.entrustSellAmount = entrustSellAmount;
	}
	/**
	 * 获取：卖出挂单数
	 */
	@Column(name = "entrust_sell_amount")
	public double getEntrustSellAmount() {
		return entrustSellAmount;
	}
	/**
	 * 设置：交易额
	 */
	public void setTradeTotalAmount(double tradeTotalAmount) {
		this.tradeTotalAmount = tradeTotalAmount;
	}
	/**
	 * 获取：交易额
	 */
	@Column(name = "trade_total_amount")
	public double getTradeTotalAmount() {
		return tradeTotalAmount;
	}
	/**
	 * 设置：买入成交数
	 */
	public void setTradeBuyAmount(double tradeBuyAmount) {
		this.tradeBuyAmount = tradeBuyAmount;
	}
	/**
	 * 获取：买入成交数
	 */
	@Column(name = "trade_buy_amount")
	public double getTradeBuyAmount() {
		return tradeBuyAmount;
	}
	/**
	 * 设置：卖出成交数
	 */
	public void setTradeSellAmount(double tradeSellAmount) {
		this.tradeSellAmount = tradeSellAmount;
	}
	/**
	 * 获取：卖出成交数
	 */
	@Column(name = "trade_sell_amount")
	public double getTradeSellAmount() {
		return tradeSellAmount;
	}
	/**
	 * 设置：充提总额
	 */
	public void setChargeWithdrawNum(double chargeWithdrawNum) {
		this.chargeWithdrawNum = chargeWithdrawNum;
	}
	/**
	 * 获取：充提总额
	 */
	@Column(name = "charge_withdraw_num")
	public double getChargeWithdrawNum() {
		return chargeWithdrawNum;
	}
	/**
	 * 设置：充币数量
	 */
	public void setChargeNum(double chargeNum) {
		this.chargeNum = chargeNum;
	}
	/**
	 * 获取：充币数量
	 */
	@Column(name = "charge_num")
	public double getChargeNum() {
		return chargeNum;
	}
	/**
	 * 设置：提币数量
	 */
	public void setWithdrawNum(double withdrawNum) {
		this.withdrawNum = withdrawNum;
	}
	/**
	 * 获取：提币数量
	 */
	@Column(name = "withdraw_num")
	public double getWithdrawNum() {
		return withdrawNum;
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
