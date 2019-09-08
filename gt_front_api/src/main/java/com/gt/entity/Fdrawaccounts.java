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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * 划账记录表
 * @author zhouyong
 *
 */
@Entity
@Table(name = "fdrawaccounts", catalog = "gtcoin")
public class Fdrawaccounts implements Serializable {

	private static final long serialVersionUID = 416941490261159556L;
	
	
	private Integer fid;
	
	/** 划账人 **/
	private Fuser fuserFrom;
	
	/** 被划账人 **/
	private Fuser fuserTo;
	
	/** 划账数量 **/
	private double famount;
	
	/** 划账币种 **/
	private Fvirtualcointype fcointype;
	
	/** 划账时间 **/
	private Timestamp fcreateTime;
	
	private int version;
	
	/** 划账类型 **/
	private Integer ftype;

	
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fId", unique = true, nullable = false)
	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fUserFrom")
	public Fuser getFuserFrom() {
		return fuserFrom;
	}

	public void setFuserFrom(Fuser fuserFrom) {
		this.fuserFrom = fuserFrom;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fUserTo")
	public Fuser getFuserTo() {
		return fuserTo;
	}

	public void setFuserTo(Fuser fuserTo) {
		this.fuserTo = fuserTo;
	}

	@Column(name = "fAmount")
	public double getFamount() {
		return famount;
	}

	public void setFamount(double famount) {
		this.famount = famount;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fCointype")
	public Fvirtualcointype getFcointype() {
		return fcointype;
	}

	public void setFcointype(Fvirtualcointype fcointype) {
		this.fcointype = fcointype;
	}

	@Column(name = "fCreateTime")
	public Timestamp getFcreateTime() {
		return fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "fType")
	public Integer getFtype() {
		return ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	
}
