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

import com.gt.Enum.IntrolInfoTypeEnum;

/**
 * Fintrolinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fintrolinfo", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fintrolinfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6326811552411110669L;
	private Integer fid;
	private Fuser fuser;
	private String ftitle;
	private Double fqty;
	private Timestamp fcreatetime;
	private boolean fiscny;
    private String fname;
    
    //奖励类型
    private Integer ftype;
    private String ftype_s;
	// Constructors



	/** default constructor */
	public Fintrolinfo() {
	}

	/** full constructor */
	public Fintrolinfo(Fuser fuser, String ftitle, Double fqty,
			Timestamp fcreatetime) {
		this.fuser = fuser;
		this.ftitle = ftitle;
		this.fqty = fqty;
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

	@Column(name = "ftitle", length = 100)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "fqty", precision = 16, scale = 6)
	public Double getFqty() {
		return this.fqty;
	}

	public void setFqty(Double fqty) {
		this.fqty = fqty;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	
	@Column(name = "fiscny")
	public boolean isFiscny() {
		return fiscny;
	}

	public void setFiscny(boolean fiscny) {
		this.fiscny = fiscny;
	}

	
	@Column(name = "fname")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	@Column(name = "ftype")
	public Integer getFtype() {
		return ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	
	@Transient
	public String getFtype_s() {
		int ftype = this.getFtype();
		return IntrolInfoTypeEnum.getEnumString(ftype);
	}
	
	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}
}