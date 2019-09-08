package com.gt.entity;

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


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * Fasset entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fassetgt", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fassetgt implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5311698599759340872L;
	private int fid;
	private int version;
	private Fuser fuser;
	private Double ftotal;
	private Double ffrozen;
	private Double fgtqty;
	private Timestamp fcreatetime;
	private Timestamp flastupdatetime;
	private Boolean status;
	private Double ftotalamount;
	private Double ftotalfee;
	private Double freward;
	private double flocked;//锁仓数量


	// Constructors

	/** default constructor */
	public Fassetgt() {
	}

	/** full constructor */
	public Fassetgt(Fuser fuser, Double ftotal,Double ffrozen, Timestamp fcreatetime,
			Timestamp flastupdatetime, Boolean status) {
		this.fuser = fuser;
		this.ftotal = ftotal;
		this.ffrozen = ffrozen;
		this.fcreatetime = fcreatetime;
		this.flastupdatetime = flastupdatetime;
		this.status = status;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuser")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "ftotal", precision = 20, scale = 8)
	public Double getFtotal() {
		return this.ftotal;
	}

	public void setFtotal(Double ftotal) {
		this.ftotal = ftotal;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name = "flastupdatetime", length = 0)
	public Timestamp getFlastupdatetime() {
		return this.flastupdatetime;
	}

	public void setFlastupdatetime(Timestamp flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "ffrozen", precision = 20, scale = 8)
	public Double getFfrozen() {
		return ffrozen;
	}

	public void setFfrozen(Double ffrozen) {
		this.ffrozen = ffrozen;
	}

	@Column(name = "fgtqty", precision = 20, scale = 8)
	public Double getFgtqty() {
		return fgtqty;
	}

	public void setFgtqty(Double fgtqty) {
		this.fgtqty = fgtqty;
	}

	@Column(name = "ftotalamount", precision = 20, scale = 8)
	public Double getFtotalamount() {
		return ftotalamount;
	}

	public void setFtotalamount(Double ftotalamount) {
		this.ftotalamount = ftotalamount;
	}

	@Column(name = "ftotalfee", precision = 20, scale = 8)
	public Double getFtotalfee() {
		return ftotalfee;
	}

	public void setFtotalfee(Double ftotalfee) {
		this.ftotalfee = ftotalfee;
	}

	@Column(name = "freward", precision = 20, scale = 8)
	public Double getFreward() {
		return freward;
	}

	public void setFreward(Double freward) {
		this.freward = freward;
	}

	@Column(name = "fLocked", precision = 20, scale = 8)
	public double getFlocked() {
		return flocked;
	}

	public void setFlocked(double flocked) {
		this.flocked = flocked;
	}


}