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
 * 收益记录表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Entity
@Table(name = "p_profitlogs", catalog = "gtcoin")
public class Pprofitlogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7636015351521079199L;
	//主键
	private Integer id;
	//项目方id
	private Fuser projectId;
	//统计时间
	private String statisticalDate;
	//币种id
	private Fvirtualcointype cointypeId;
	//交易市场id
	private Ftrademapping trademappingId;
	//收益类型  1：买入手续费   2：卖出手续费
	private Integer profitType;
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
	 * 设置：收益类型  1：买入手续费   2：卖出手续费
	 */
	public void setProfitType(Integer profitType) {
		this.profitType = profitType;
	}
	/**
	 * 获取：收益类型  1：买入手续费   2：卖出手续费
	 */
	@Column(name = "profit_type")
	public Integer getProfitType() {
		return profitType;
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
}
