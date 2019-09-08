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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 项目方交易市场表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_trademapping", catalog = "gtcoin")
public class Ptrademapping implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8242747016890751544L;
	//主键
	private Integer id;
	//项目方id
	private Fuser projectId;
	//法币类型
	private Fvirtualcointype frenchCurrencyType;
	//交易币类型
	private Pcointype tradeCurrencyType;
	//单价小数位
	private Integer unitPriceDecimal;
	//交易时间
	private String tradeTime;
	//开盘价
	private double openPrice;
	//最小挂单单价
	private double minEntrustPrice;
	//最大挂单单价
	private double maxEntrustPrice;
	//最小挂单金额
	private double minEntrustMoney;
	//最大挂单金额
	private double maxEntrustMoney;
	//最小挂单数量
	private double minEntrustQty;
	//最大挂单数量
	private double maxEntrustQty;
	//市场状态（1：正常；2：风险；3：隐藏；4：暂停）
	private Integer status;
	//审核状态（101：待审核；102：审核通过；103：审核失败）
	private Integer auditStatus;
	//保证金缴纳状态（1：未缴纳；2：已缴纳）
	private Integer depositStatus;
	//版本号
	private Integer version;
	//备注
	private String remark;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp updateTime;
	//审核时间
	private Timestamp auditTime;
	//对应ftrademapping中的fid
	private Integer tradeMappingId;
	
	//项目方交易市场手续费比例
	private double buyFeesRate;
	private double sellFeesRate;
	
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
	 * 设置：法币类型
	 */
	public void setFrenchCurrencyType(Fvirtualcointype frenchCurrencyType) {
		this.frenchCurrencyType = frenchCurrencyType;
	}
	/**
	 * 获取：法币类型
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "french_currency_id")
	public Fvirtualcointype getFrenchCurrencyType() {
		return frenchCurrencyType;
	}
	/**
	 * 设置：交易币类型
	 */
	public void setTradeCurrencyType(Pcointype tradeCurrencyType) {
		this.tradeCurrencyType = tradeCurrencyType;
	}
	/**
	 * 获取：交易币类型
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trade_currency_id")
	public Pcointype getTradeCurrencyType() {
		return tradeCurrencyType;
	}
	/**
	 * 设置：单价小数位
	 */
	public void setUnitPriceDecimal(Integer unitPriceDecimal) {
		this.unitPriceDecimal = unitPriceDecimal;
	}
	/**
	 * 获取：单价小数位
	 */
	@Column(name = "unit_price_decimal")
	public Integer getUnitPriceDecimal() {
		return unitPriceDecimal;
	}
	/**
	 * 设置：交易时间
	 */
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	/**
	 * 获取：交易时间
	 */
	@Column(name = "trade_time")
	public String getTradeTime() {
		return tradeTime;
	}
	/**
	 * 设置：开盘价
	 */
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	/**
	 * 获取：开盘价
	 */
	@Column(name = "open_price")
	public double getOpenPrice() {
		return openPrice;
	}
	/**
	 * 设置：最小挂单单价
	 */
	public void setMinEntrustPrice(double minEntrustPrice) {
		this.minEntrustPrice = minEntrustPrice;
	}
	/**
	 * 获取：最小挂单单价
	 */
	@Column(name = "min_entrust_price")
	public double getMinEntrustPrice() {
		return minEntrustPrice;
	}
	/**
	 * 设置：最大挂单单价
	 */
	public void setMaxEntrustPrice(double maxEntrustPrice) {
		this.maxEntrustPrice = maxEntrustPrice;
	}
	/**
	 * 获取：最大挂单单价
	 */
	@Column(name = "max_entrust_price")
	public double getMaxEntrustPrice() {
		return maxEntrustPrice;
	}
	/**
	 * 设置：最小挂单金额
	 */
	public void setMinEntrustMoney(double minEntrustMoney) {
		this.minEntrustMoney = minEntrustMoney;
	}
	/**
	 * 获取：最小挂单金额
	 */
	@Column(name = "min_entrust_money")
	public double getMinEntrustMoney() {
		return minEntrustMoney;
	}
	/**
	 * 设置：最大挂单金额
	 */
	public void setMaxEntrustMoney(double maxEntrustMoney) {
		this.maxEntrustMoney = maxEntrustMoney;
	}
	/**
	 * 获取：最大挂单金额
	 */
	@Column(name = "max_entrust_money")
	public double getMaxEntrustMoney() {
		return maxEntrustMoney;
	}
	/**
	 * 设置：最小挂单数量
	 */
	public void setMinEntrustQty(double minEntrustQty) {
		this.minEntrustQty = minEntrustQty;
	}
	/**
	 * 获取：最小挂单数量
	 */
	@Column(name = "min_entrust_qty")
	public double getMinEntrustQty() {
		return minEntrustQty;
	}
	/**
	 * 设置：最大挂单数量
	 */
	public void setMaxEntrustQty(double maxEntrustQty) {
		this.maxEntrustQty = maxEntrustQty;
	}
	/**
	 * 获取：最大挂单数量
	 */
	@Column(name = "max_entrust_qty")
	public double getMaxEntrustQty() {
		return maxEntrustQty;
	}
	/**
	 * 设置：市场状态（1：正常；2：风险；3：隐藏；4：暂停）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：市场状态（1：正常；2：风险；3：隐藏；4：暂停）
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
	 * 设置：保证金缴纳状态（1：未缴纳；2：已缴纳）
	 */
	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}
	/**
	 * 获取：保证金缴纳状态（1：未缴纳；2：已缴纳）
	 */
	@Column(name = "deposit_status")
	public Integer getDepositStatus() {
		return depositStatus;
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
	
	public void setTradeMappingId(Integer tradeMappingId) {
		this.tradeMappingId = tradeMappingId;
	}
	
	@Column(name = "trade_mapping_id")
	public Integer getTradeMappingId() {
		return tradeMappingId;
	}
	
	@Transient
	public double getBuyFeesRate() {
		return buyFeesRate;
	}
	public void setBuyFeesRate(double buyFeesRate) {
		this.buyFeesRate = buyFeesRate;
	}
	
	@Transient
	public double getSellFeesRate() {
		return sellFeesRate;
	}
	public void setSellFeesRate(double sellFeesRate) {
		this.sellFeesRate = sellFeesRate;
	}
	
	
}
