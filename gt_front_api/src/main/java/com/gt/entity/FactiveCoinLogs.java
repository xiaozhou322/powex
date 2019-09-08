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

import com.gt.Enum.ActiveStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;



/**
 * 币种激活流水表
 * @author zhouyong
 *
 */
@Entity
@Table(name = "factivecoinlogs", catalog = "gtcoin")
public class FactiveCoinLogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2804513871275598547L;
	//主键id
	private Integer id;
	//统计时间
	private String statisticalDate;
	//用户id
	private Fuser fuser;
	//待激活币种
	private Fvirtualcointype coinType;
	//币种待激活数量
	private double activeAmount;
	//激活状态(1：待激活   2：已激活  3：放弃激活)
	private Integer status;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//更新时间
	private Timestamp updateTime;
	//激活状态描述
	private String statusDesc;

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public Fuser getFuser() {
		return fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "coin_type")
	public Fvirtualcointype getCoinType() {
		return coinType;
	}
	public void setCoinType(Fvirtualcointype coinType) {
		this.coinType = coinType;
	}
	
	
	@Column(name = "active_amount")
	public double getActiveAmount() {
		return activeAmount;
	}
	public void setActiveAmount(double activeAmount) {
		this.activeAmount = activeAmount;
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
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	@Transient
	public String getStatusDesc() {
		return ActiveStatusEnum.getDescription(this.getStatus());
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}
