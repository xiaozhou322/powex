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
 * 项目方币种表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_cointype", catalog = "gtcoin")
public class Pcointype implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9052153294982386019L;
	//主键
	private Integer id;
	//币种名称
	private String name;
	//币种简称
	private String shortName;
	//项目方id
	private Fuser projectId;
	//logo地址
	private String logoUrl;
	//发行总量
	private double pushTotal;
	//发行价格
	private double pushPrice;
	//当前流通量(截止填表当天)
	private double currentCirculation;
	//当前市值(截止填表当天)
	private double currentMarketValue;
	//当前持币用户(注册有效用户)
	private double currentHoldNum;
	//小数精度位
	private Integer decimals;
	//官网地址
	private String website;
	//合约地址
	private String contractAddr;
	//区块链浏览器地址
	private String blockAddr;
	//已签合约代码链接
	private String contractLink;
	//币实际用途
	private String coinUsed;
	//币种状态（1：正常；2：禁用；3：风险；4：隐藏）
	private Integer status;
	//审核状态（101：待审核；102：审核通过；103：审核失败）
	private Integer auditStatus;
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
	
	//保证金id
	private Integer depositId;
	//对应fvirtualcointype中的fid
	private Integer coinId;
	
	//开放充值时间
	private String openChargeTime;
	//开放交易时间
	private String openTradeTime;
	//开放提现时间
	private String openWithdrawTime;
	
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
	 * 设置：币种名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：币种名称
	 */
	@Column(name = "name")
	public String getName() {
		return name;
	}
	/**
	 * 设置：币种简称
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * 获取：币种简称
	 */
	@Column(name = "short_name")
	public String getShortName() {
		return shortName;
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
	 * 设置：logo地址
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	/**
	 * 获取：logo地址
	 */
	@Column(name = "logo_url")
	public String getLogoUrl() {
		return logoUrl;
	}
	/**
	 * 设置：发行总量
	 */
	public void setPushTotal(double pushTotal) {
		this.pushTotal = pushTotal;
	}
	/**
	 * 获取：发行总量
	 */
	@Column(name = "push_total")
	public double getPushTotal() {
		return pushTotal;
	}
	/**
	 * 设置：发行价格
	 */
	public void setPushPrice(double pushPrice) {
		this.pushPrice = pushPrice;
	}
	/**
	 * 获取：发行价格
	 */
	@Column(name = "push_price")
	public double getPushPrice() {
		return pushPrice;
	}
	/**
	 * 设置：当前流通量(截止填表当天)
	 */
	public void setCurrentCirculation(double currentCirculation) {
		this.currentCirculation = currentCirculation;
	}
	/**
	 * 获取：当前流通量(截止填表当天)
	 */
	@Column(name = "current_circulation")
	public double getCurrentCirculation() {
		return currentCirculation;
	}
	/**
	 * 设置：当前市值(截止填表当天)
	 */
	public void setCurrentMarketValue(double currentMarketValue) {
		this.currentMarketValue = currentMarketValue;
	}
	/**
	 * 获取：当前市值(截止填表当天)
	 */
	@Column(name = "current_market_value")
	public double getCurrentMarketValue() {
		return currentMarketValue;
	}
	/**
	 * 设置：当前持币用户(注册有效用户)
	 */
	public void setCurrentHoldNum(double currentHoldNum) {
		this.currentHoldNum = currentHoldNum;
	}
	/**
	 * 获取：当前持币用户(注册有效用户)
	 */
	@Column(name = "current_hold_num")
	public double getCurrentHoldNum() {
		return currentHoldNum;
	}
	/**
	 * 设置：小数精度位
	 */
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	/**
	 * 获取：小数精度位
	 */
	@Column(name = "decimals")
	public Integer getDecimals() {
		return decimals;
	}
	/**
	 * 设置：官网地址
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * 获取：官网地址
	 */
	@Column(name = "website")
	public String getWebsite() {
		return website;
	}
	/**
	 * 设置：合约地址
	 */
	public void setContractAddr(String contractAddr) {
		this.contractAddr = contractAddr;
	}
	/**
	 * 获取：合约地址
	 */
	@Column(name = "contract_addr")
	public String getContractAddr() {
		return contractAddr;
	}
	/**
	 * 设置：区块链浏览器地址
	 */
	public void setBlockAddr(String blockAddr) {
		this.blockAddr = blockAddr;
	}
	/**
	 * 获取：区块链浏览器地址
	 */
	@Column(name = "block_addr")
	public String getBlockAddr() {
		return blockAddr;
	}
	/**
	 * 设置：已签合约代码链接
	 */
	public void setContractLink(String contractLink) {
		this.contractLink = contractLink;
	}
	/**
	 * 获取：已签合约代码链接
	 */
	@Column(name = "contract_link")
	public String getContractLink() {
		return contractLink;
	}
	/**
	 * 设置：币实际用途
	 */
	public void setCoinUsed(String coinUsed) {
		this.coinUsed = coinUsed;
	}
	/**
	 * 获取：币实际用途
	 */
	@Column(name = "coin_used")
	public String getCoinUsed() {
		return coinUsed;
	}
	/**
	 * 设置：币种状态（1：正常；2：风险；3：隐藏；4：暂停）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：币种状态（1：正常；2：风险；3：隐藏；4：暂停）
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
	
	/**
	 * 设置：保证金id
	 */
	public void setDepositId(Integer depositId) {
		this.depositId = depositId;
	}
	/**
	 * 获取：保证金id
	 */
	@Column(name = "deposit_id")
	public Integer getDepositId() {
		return depositId;
	}
	
	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}
	@Column(name = "coin_id")
	public Integer getCoinId() {
		return coinId;
	}
	
	@Column(name = "open_charge_time")
	public String getOpenChargeTime() {
		return openChargeTime;
	}
	
	public void setOpenChargeTime(String openChargeTime) {
		this.openChargeTime = openChargeTime;
	}
	
	@Column(name = "open_trade_time")
	public String getOpenTradeTime() {
		return openTradeTime;
	}
	
	public void setOpenTradeTime(String openTradeTime) {
		this.openTradeTime = openTradeTime;
	}
	
	@Column(name = "open_withdraw_time")
	public String getOpenWithdrawTime() {
		return openWithdrawTime;
	}
	
	public void setOpenWithdrawTime(String openWithdrawTime) {
		this.openWithdrawTime = openWithdrawTime;
	}
	
	
}
