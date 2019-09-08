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
 * 币种兑换操作日志表
 * @author zhouyong
 *
 */
@Entity
@Table(name = "fconvertcoinlogs", catalog = "gtcoin")
public class FconvertCoinLogs implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5625355433115218582L;
	//主键id
	private Integer id;
	//操作人
	private Fuser fuser;
	//待兑换币种
	private Fvirtualcointype convertCointype1;
	//兑换币种
	private Fvirtualcointype convertCointype2;
	//待兑换币种数量
	private double convertAmount1;
	//兑换币种数量
	private double convertAmount2;
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
	@JoinColumn(name = "convert_cointype1")
	public Fvirtualcointype getConvertCointype1() {
		return convertCointype1;
	}
	public void setConvertCointype1(Fvirtualcointype convertCointype1) {
		this.convertCointype1 = convertCointype1;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "convert_cointype2")
	public Fvirtualcointype getConvertCointype2() {
		return convertCointype2;
	}
	public void setConvertCointype2(Fvirtualcointype convertCointype2) {
		this.convertCointype2 = convertCointype2;
	}
	
	@Column(name = "convert_amount1")
	public double getConvertAmount1() {
		return convertAmount1;
	}
	public void setConvertAmount1(double convertAmount1) {
		this.convertAmount1 = convertAmount1;
	}
	
	@Column(name = "convert_amount2")
	public double getConvertAmount2() {
		return convertAmount2;
	}
	public void setConvertAmount2(double convertAmount2) {
		this.convertAmount2 = convertAmount2;
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
