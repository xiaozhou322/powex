package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 保证金币种表
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Entity
@Table(name = "p_depositcointype", catalog = "gtcoin")
public class Pdepositcointype implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2596714515210207822L;
	//主键
	private Integer id;
	//保证金币种表
	private Fvirtualcointype cointypeId;
	//保证金额度
	private double depositLimit;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;

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
	 * 设置：保证金币种表
	 */
	public void setCointypeId(Fvirtualcointype cointypeId) {
		this.cointypeId = cointypeId;
	}
	/**
	 * 获取：保证金币种表
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cointype_id")
	public Fvirtualcointype getCointypeId() {
		return cointypeId;
	}
	/**
	 * 设置：保证金额度
	 */
	public void setDepositLimit(double depositLimit) {
		this.depositLimit = depositLimit;
	}
	/**
	 * 获取：保证金额度
	 */
	@Column(name = "deposit_limit")
	public double getDepositLimit() {
		return depositLimit;
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
