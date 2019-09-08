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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 项目方手续费率表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_fees", catalog = "gtcoin")
public class Pfees implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7352900453736112278L;
	//主键
	private Integer id;
	//项目方id
	private Fuser projectId;
	//交易市场id
	private Ptrademapping trademappingId;
	//买入手续费率
	private double buyFee;
	//卖出手续费率
	private double sellFee;
	//审核状态（101：待审核；102：审核通过；103：审核失败）
	private Integer auditStatus;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp updateTime;
	//审核时间
	private Timestamp auditTime;
	//对应ffees中的fid
	private Integer feesId;
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
	 * 设置：交易市场id
	 */
	public void setTrademappingId(Ptrademapping trademappingId) {
		this.trademappingId = trademappingId;
	}
	/**
	 * 获取：交易市场id
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trademapping_id")
	public Ptrademapping getTrademappingId() {
		return trademappingId;
	}
	/**
	 * 设置：买入手续费率
	 */
	public void setBuyFee(double buyFee) {
		this.buyFee = buyFee;
	}
	/**
	 * 获取：买入手续费率
	 */
	@Column(name = "buy_fee")
	public double getBuyFee() {
		return buyFee;
	}
	/**
	 * 设置：卖出手续费率
	 */
	public void setSellFee(double sellFee) {
		this.sellFee = sellFee;
	}
	/**
	 * 获取：卖出手续费率
	 */
	@Column(name = "sell_fee")
	public double getSellFee() {
		return sellFee;
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
	
	public void setFeesId(Integer feesId) {
		this.feesId = feesId;
	}
	@Column(name = "fees_id")
	public Integer getFeesId() {
		return feesId;
	}
	
	@Transient
	public String getTrademappingStr() {
		return trademappingId.getTradeCurrencyType().getShortName()
				+"/"+trademappingId.getFrenchCurrencyType().getfShortName();
	}
	public void setTrademappingStr(String trademappingStr) {
		this.trademappingStr = trademappingStr;
	}
	
	
}
