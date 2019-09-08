package com.gt.entity;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Ftradehistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftradehistory", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ftradehistory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4058939556935119236L;
	private Integer fid;
	private Date fdate;
	private Double fprice;
	private Ftrademapping ftrademapping;
	private Double ftotal;
//	private Fuser fuser;

	// Constructors

	/** default constructor */
	public Ftradehistory() {
	}

	/** full constructor */
	public Ftradehistory(Date fdate, Double fprice) {
		this.fdate = fdate;
		this.fprice = fprice;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fdate", length = 0)
	public Date getFdate() {
		return this.fdate;
	}

	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}

	@Column(name = "fprice", precision = 16, scale = 6)
	public Double getFprice() {
		return this.fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fvid")
	public Ftrademapping getFtrademapping() {
		return ftrademapping;
	}

	public void setFtrademapping(Ftrademapping ftrademapping) {
		this.ftrademapping = ftrademapping;
	}
	
	@Column(name = "ftotal")
	public Double getFtotal() {
		return ftotal;
	}

	public void setFtotal(Double ftotal) {
		this.ftotal = ftotal;
	}
//
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "fuserid")
//	public Fuser getFuser() {
//		return fuser;
//	}
//
//	public void setFuser(Fuser fuser) {
//		this.fuser = fuser;
//	}

}