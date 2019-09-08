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
 * 币种拨付操作日志表
 * @author zhouyong
 *
 */
@Entity
@Table(name = "fapprocoinlogs", catalog = "gtcoin")
public class FapproCoinLogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1600080822000448105L;
	//主键id
	private Integer id;
	//操作人
	private Fuser fuser;
	//拨付币种
	private Fvirtualcointype coinType;
	//拨付数量
	private double amount;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;

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
	
	@Column(name = "amount")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
