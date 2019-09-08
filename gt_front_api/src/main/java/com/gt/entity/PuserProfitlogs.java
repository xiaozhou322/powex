package com.gt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 用户挖矿奖励记录表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Entity
@Table(name = "p_user_profitlogs", catalog = "gtcoin")
public class PuserProfitlogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -934599077701472763L;
	//主键
	private Integer id;
	//项目方id
	private Fuser user;
	//统计时间
	private String statisticalDate;
	//币种id
	private Fvirtualcointype cointype;
	//交易市场id
	private Ftrademapping trademappingId;
	//收益总额
	private BigDecimal amount;
	//结算状态  0：未结算  1：已结算
	private Integer status;
	//版本
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp updateTime;
	//交易市场货币对
	private String trademappingStr;
	//法币类型
	private Fvirtualcointype fbCointype;
	//美元估值
	private BigDecimal usdtValuation;
	//奖励币种
	private Fvirtualcointype rewardCointype;
	//奖励数量
	private BigDecimal rewardAmount;
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
	 * 设置：用户id
	 */
	public void setUser(Fuser user) {
		this.user = user;
	}
	/**
	 * 获取：用户id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public Fuser getUser() {
		return user;
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
	 * 设置：币种id
	 */
	public void setCointype(Fvirtualcointype cointype) {
		this.cointype = cointype;
	}
	/**
	 * 获取：币种id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cointype_id")
	public Fvirtualcointype getCointype() {
		return cointype;
	}
	/**
	 * 获取：收益总额
	 */
	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：收益总额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 设置：结算状态  0：未结算  1：已结算
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：结算状态  0：未结算  1：已结算
	 */
	@Column(name = "status")
	public Integer getStatus() {
		return status;
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
	/**
	 * 设置：交易市场id
	 */
	public void setTrademappingId(Ftrademapping trademappingId) {
		this.trademappingId = trademappingId;
	}
	/**
	 * 获取：交易市场id
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trademapping_id")
	public Ftrademapping getTrademappingId() {
		return trademappingId;
	}
	
	@Transient
	public String getTrademappingStr() {
		return trademappingId.getFvirtualcointypeByFvirtualcointype2().getfShortName()
				+"/"+trademappingId.getFvirtualcointypeByFvirtualcointype1().getfShortName();
	}
	public void setTrademappingStr(String trademappingStr) {
		this.trademappingStr = trademappingStr;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fb_cointype_id")
	public Fvirtualcointype getFbCointype() {
		return fbCointype;
	}
	public void setFbCointype(Fvirtualcointype fbCointype) {
		this.fbCointype = fbCointype;
	}
	
	@Column(name = "usdt_valuation")
	public BigDecimal getUsdtValuation() {
		return usdtValuation;
	}
	
	public void setUsdtValuation(BigDecimal usdtValuation) {
		this.usdtValuation = usdtValuation;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reward_cointype")
	public Fvirtualcointype getRewardCointype() {
		return rewardCointype;
	}
	
	public void setRewardCointype(Fvirtualcointype rewardCointype) {
		this.rewardCointype = rewardCointype;
	}
	
	@Column(name = "reward_amount")
	public BigDecimal getRewardAmount() {
		return rewardAmount;
	}
	
	public void setRewardAmount(BigDecimal rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	
}
