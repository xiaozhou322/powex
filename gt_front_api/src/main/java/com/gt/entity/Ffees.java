package com.gt.entity;

import javax.persistence.CascadeType;
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
 * Ffees entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ffees", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ffees implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7424965436043910108L;
	private int fid;
	private double ffee;// 交易手续费
	private double fbuyfee;
//	private double withdraw;// 提现手续费
	private int flevel ;
	private Ftrademapping ftrademapping ;
	private int version;

	// Constructors

	/** default constructor */
	public Ffees() {
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

	@Column(name = "ffee", precision = 15, scale = 0)
	public double getFfee() {
		return this.ffee;
	}

	public void setFfee(double ffee) {
		this.ffee = ffee;
	}
//
//	public double getWithdraw() {
//		return withdraw;
//	}
//
//	public void setWithdraw(double withdraw) {
//		this.withdraw = withdraw;
//	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ftrademapping")
	public Ftrademapping getFtrademapping() {
		return ftrademapping;
	}

	public void setFtrademapping(Ftrademapping ftrademapping) {
		this.ftrademapping = ftrademapping;
	}

	@Column(name="flevel")
	public int getFlevel() {
		return flevel;
	}

	public void setFlevel(int flevel) {
		this.flevel = flevel;
	}
	
	@Column(name="fbuyfee")
	public double getFbuyfee() {
		return fbuyfee;
	}

	public void setFbuyfee(double fbuyfee) {
		this.fbuyfee = fbuyfee;
	}


}