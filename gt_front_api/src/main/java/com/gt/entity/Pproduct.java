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
 * 项目方权证产品表
 * @author zhouyong
 *
 */
@Entity
@Table(name = "p_product", catalog = "gtcoin")
public class Pproduct implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8658985319246140560L;
	//主键
	private Integer id;
	//产品名称
	private String name;
	//产品简称
	private String shortName;
	//项目方id
	private Fuser projectId;
	//兑换比例
	private String convertRatio;
	//到期兑换比例
	private String convertRatioExpire;
	//开始时间
	private Timestamp startDate;
	//周期（年）
	private Integer period;
	//兑换币种
	private Fvirtualcointype convertCointype;
	//对应的币种
	private Fvirtualcointype coinType;
	//发行总量
	private double pushTotal;
	//产品可用额
	private double proAvailableAmount;
	//产品冻结额
	private double proFrozenAmount;
	//兑换可用额
	private double convertAvailableAmount;
	//兑换冻结额
	private double convertFrozenAmount;
	//最大总兑换数量
	private double maxTotalAmount;
	//单次最少兑换数量
	private double minTimeAmount;
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
	 * 设置：产品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：产品名称
	 */
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	/**
	 * 设置：产品简称
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * 获取：产品简称
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
	
	@Column(name = "convert_ratio")
	public String getConvertRatio() {
		return convertRatio;
	}
	
	public void setConvertRatio(String convertRatio) {
		this.convertRatio = convertRatio;
	}
	
	@Column(name = "convert_ratio_expire")
	public String getConvertRatioExpire() {
		return convertRatioExpire;
	}
	public void setConvertRatioExpire(String convertRatioExpire) {
		this.convertRatioExpire = convertRatioExpire;
	}
	
	@Column(name = "start_date")
	public Timestamp getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "period")
	public Integer getPeriod() {
		return period;
	}
	
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "convert_cointype")
	public Fvirtualcointype getConvertCointype() {
		return convertCointype;
	}
	
	public void setConvertCointype(Fvirtualcointype convertCointype) {
		this.convertCointype = convertCointype;
	}
	
	@Column(name = "pro_available_amount")
	public double getProAvailableAmount() {
		return proAvailableAmount;
	}
	
	public void setProAvailableAmount(double proAvailableAmount) {
		this.proAvailableAmount = proAvailableAmount;
	}
	
	@Column(name = "pro_frozen_amount")
	public double getProFrozenAmount() {
		return proFrozenAmount;
	}
	
	public void setProFrozenAmount(double proFrozenAmount) {
		this.proFrozenAmount = proFrozenAmount;
	}
	
	@Column(name = "convert_available_amount")
	public double getConvertAvailableAmount() {
		return convertAvailableAmount;
	}
	
	public void setConvertAvailableAmount(double convertAvailableAmount) {
		this.convertAvailableAmount = convertAvailableAmount;
	}
	
	@Column(name = "convert_frozen_amount")
	public double getConvertFrozenAmount() {
		return convertFrozenAmount;
	}
	
	public void setConvertFrozenAmount(double convertFrozenAmount) {
		this.convertFrozenAmount = convertFrozenAmount;
	}
	
	@Column(name = "max_total_amount")
	public double getMaxTotalAmount() {
		return maxTotalAmount;
	}
	
	public void setMaxTotalAmount(double maxTotalAmount) {
		this.maxTotalAmount = maxTotalAmount;
	}
	
	@Column(name = "min_time_amount")
	public double getMinTimeAmount() {
		return minTimeAmount;
	}
	public void setMinTimeAmount(double minTimeAmount) {
		this.minTimeAmount = minTimeAmount;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "coin_type")
	public Fvirtualcointype getCoinType() {
		return coinType;
	}
	
	public void setCoinType(Fvirtualcointype coinType) {
		this.coinType = coinType;
	}
}
