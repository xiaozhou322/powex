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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.SubStatusEnum;

/**
 * Fsubscriptionlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fsubscriptionlog", catalog = "gtcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fsubscriptionlog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3407615030473819804L;
	private Integer fid;
	private Fsubscription fsubscription;
	private Fuser fuser;// 用户
	private Double fcount;// 众筹数量
	private Double fprice;// 众筹价格
	private Double ftotalCost;// 总消费
	private Timestamp fcreatetime;
	private String fischarge;
    private int fstatus;
    private String fstatus_s;
	private double flastcount;//中签份数
//	private double flastqty;//中签总数量
//	private double foneqty;//每份数量
//	private double frate;
	private boolean fissend;

	// Constructors


	/** default constructor */
	public Fsubscriptionlog() {
	}


	/** full constructor */
	public Fsubscriptionlog(Fsubscription fsubscription, Fuser fuser,
			Double fcount, Double fprice, Double ftotalCost,
			Timestamp fcreatetime) {
		this.fsubscription = fsubscription;
		this.fuser = fuser;
		this.fcount = fcount;
		this.fprice = fprice;
		this.ftotalCost = ftotalCost;
		this.fcreatetime = fcreatetime;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fsub_id")
	public Fsubscription getFsubscription() {
		return this.fsubscription;
	}

	public void setFsubscription(Fsubscription fsubscription) {
		this.fsubscription = fsubscription;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuser_fid")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fcount", precision = 16, scale = 6)
	public Double getFcount() {
		return this.fcount;
	}

	public void setFcount(Double fcount) {
		this.fcount = fcount;
	}

	@Column(name = "fprice", precision = 16, scale = 6)
	public Double getFprice() {
		return this.fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}

	@Column(name = "ftotalCost", precision = 16, scale = 6)
	public Double getFtotalCost() {
		return this.ftotalCost;
	}

	public void setFtotalCost(Double ftotalCost) {
		this.ftotalCost = ftotalCost;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name = "fischarge")
	public String getFischarge() {
		return fischarge;
	}

	public void setFischarge(String fischarge) {
		this.fischarge = fischarge;
	}
	
	@Column(name = "flastcount")
	public double getFlastcount() {
		return flastcount;
	}

	public void setFlastcount(double flastcount) {
		this.flastcount = flastcount;
	}
//	
//	@Column(name = "foneqty")
//	public double getFoneqty() {
//		return foneqty;
//	}
//
//
//	public void setFoneqty(double foneqty) {
//		this.foneqty = foneqty;
//	}

	
	@Column(name = "fstatus")
	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}
	
	@Column(name = "fissend")
	public boolean isFissend() {
		return fissend;
	}

	public void setFissend(boolean fissend) {
		this.fissend = fissend;
	}
//	
//	@Column(name = "flastqty")
//	public double getFlastqty() {
//		return flastqty;
//	}
//
//
//	public void setFlastqty(double flastqty) {
//		this.flastqty = flastqty;
//	}
//	
//	@Column(name = "frate")
//	public double getFrate() {
//		return frate;
//	}
//
//
//	public void setFrate(double frate) {
//		this.frate = frate;
//	}

	
	@Transient
	public String getFstatus_s() {
		return SubStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}
}