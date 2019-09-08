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
@Table(name = "fentrustminer", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fentrustminer implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3077956659606661988L;
	private int fid;
	private int version;
	private Fuser fuser;
	private Double famount;
	private Double ffee;
	private Double frewardqty;
	private Timestamp fcreatetime;
	private Timestamp flastupdatetime;
	private Boolean status;
	private Double ftotalamount;
	private Double ftotalfee;
	private Double freward;
	private Fuser fintroUser ;
	private Fvirtualcointype fcoin ;
	private int fmktid;


	// Constructors

	/** default constructor */
	public Fentrustminer() {
	}

	/** full constructor */
	public Fentrustminer(Fuser fuser, Double famount,Double ffee, Timestamp fcreatetime,
			Timestamp flastupdatetime, Boolean status) {
		this.fuser = fuser;
		this.setFamount(famount);
		this.setFfee(ffee);
		this.fcreatetime = fcreatetime;
		this.flastupdatetime = flastupdatetime;
		this.status = status;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
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

	@Column(name = "famount", precision = 20, scale = 8)
	public Double getFamount() {
		return famount;
	}

	public void setFamount(Double famount) {
		this.famount = famount;
	}

	@Column(name = "ffee", precision = 20, scale = 8)
	public Double getFfee() {
		return ffee;
	}

	public void setFfee(Double ffee) {
		this.ffee = ffee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fIntroUser")
	public Fuser getFintroUser() {
		return fintroUser;
	}

	public void setFintroUser(Fuser fintroUser) {
		this.fintroUser = fintroUser;
	}

	@Column(name = "frewardqty", precision = 20, scale = 8)
	public Double getFrewardqty() {
		return frewardqty;
	}

	public void setFrewardqty(Double frewardqty) {
		this.frewardqty = frewardqty;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fcoin")
	public Fvirtualcointype getFcoin() {
		return fcoin;
	}

	public void setFcoin(Fvirtualcointype fcoin) {
		this.fcoin = fcoin;
	}

	@Column(name = "fmktId")
	public int getFmktid() {
		return fmktid;
	}

	public void setFmktid(int fmktid) {
		this.fmktid = fmktid;
	}


}