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
 * 手续费USDT兑换BFSC操作日志表
 * @author zhouyong
 *
 */
@Entity
@Table(name = "ffeesconvert", catalog = "gtcoin")
public class FfeesConvert implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4079204313609255137L;
	//主键id
	private Integer id;
	//项目方id
	private Fuser projectId;
	//BFSC数量
	private double bfscAmount;
	//USDT数量
	private double usdtAmount;
	//BFSC价格
	private double bfscPrice;
	//状态(1：未处理   2：已处理)
	private Integer status;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp updateTime;

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
	
	/**
	 * 设置：项目方id
	 */
	public void setProjectId(Fuser projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目方id
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	public Fuser getProjectId() {
		return projectId;
	}
	
	@Column(name = "bfsc_amount")
	public double getBfscAmount() {
		return bfscAmount;
	}
	
	public void setBfscAmount(double bfscAmount) {
		this.bfscAmount = bfscAmount;
	}
	
	@Column(name = "usdt_amount")
	public double getUsdtAmount() {
		return usdtAmount;
	}
	
	public void setUsdtAmount(double usdtAmount) {
		this.usdtAmount = usdtAmount;
	}
	
	@Column(name = "bfsc_price")
	public double getBfscPrice() {
		return bfscPrice;
	}
	
	public void setBfscPrice(double bfscPrice) {
		this.bfscPrice = bfscPrice;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
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
	
}
