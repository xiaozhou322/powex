package com.gt.entity;

// default package

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

import com.gt.Enum.AutotradeStatusEnum;
import com.gt.Enum.SynTypeEnum;
import com.gt.Enum.TradeTypeEnum;

/**
 * Fautotrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fautotrade", catalog = "gtcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fautotrade implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -866040409853350126L;
	private Integer fid;
	private Fuser fuser;
	private Integer ftype;
	private String ftype_s;// TradeTypeEnum
	private Double fminqty;
	private Double fminprice;
	private Double fmaxqty;
	private Double fmaxprice;
	private Timestamp fcreatetime;
	private Integer fstatus;
	private String fstatus_s;
	private Ftrademapping ftrademapping;
	private int fsynType;
	private String fsynType_s;
	private int ftimes;
	private int fstoptimes;
	
	// Constructors


	/** default constructor */
	public Fautotrade() {
	}

	/** full constructor */
	public Fautotrade(Fuser fuser, Integer ftype, Double fqty, Double fprice,
			Timestamp fcreatetime) {
		this.fuser = fuser;
		this.ftype = ftype;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuserid")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "ftype")
	public Integer getFtype() {
		return this.ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Transient
	public String getFtype_s() {
		return TradeTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Column(name = "fstatus")
	public Integer getFstatus() {
		return fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@Transient
	public String getFstatus_s() {
		return AutotradeStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fVi_fId")
    public Ftrademapping getFtrademapping() {
		return ftrademapping;
	}

	public void setFtrademapping(Ftrademapping ftrademapping) {
		this.ftrademapping = ftrademapping;
	}

	
	@Column(name = "fminqty")
	public Double getFminqty() {
		return fminqty;
	}

	public void setFminqty(Double fminqty) {
		this.fminqty = fminqty;
	}

	@Column(name = "fminprice")
	public Double getFminprice() {
		return fminprice;
	}

	public void setFminprice(Double fminprice) {
		this.fminprice = fminprice;
	}

	@Column(name = "fmaxqty")
	public Double getFmaxqty() {
		return fmaxqty;
	}

	public void setFmaxqty(Double fmaxqty) {
		this.fmaxqty = fmaxqty;
	}

	@Column(name = "fmaxprice")
	public Double getFmaxprice() {
		return fmaxprice;
	}

	public void setFmaxprice(Double fmaxprice) {
		this.fmaxprice = fmaxprice;
	}
	
	@Column(name = "fsynType")
	public int getFsynType() {
		return fsynType;
	}

	public void setFsynType(int fsynType) {
		this.fsynType = fsynType;
	}
	
	@Column(name = "ftimes")
	public int getFtimes() {
		return ftimes;
	}

	public void setFtimes(int ftimes) {
		this.ftimes = ftimes;
	}

	@Column(name = "fstoptimes")
	public int getFstoptimes() {
		return fstoptimes;
	}

	public void setFstoptimes(int fstoptimes) {
		this.fstoptimes = fstoptimes;
	}

	@Transient
	public String getFsynType_s() {
		return SynTypeEnum.getEnumString(this.getFsynType());
	}

	public void setFsynType_s(String fsynType_s) {
		this.fsynType_s = fsynType_s;
	}

}