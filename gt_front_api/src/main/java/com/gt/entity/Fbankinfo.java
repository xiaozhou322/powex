package com.gt.entity;

import java.sql.Timestamp;
import java.util.Set;

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
 * Fbankinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbankinfo", catalog = "gtcoin")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fbankinfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1080227211687522052L;
	private int fid;
	private Fuser fuser;
	private String fname;//银行名称
	private String fbankNumber;
	private int fbankType;
	private Timestamp fcreateTime;
	private int fstatus;//BankInfoStatusEnum
	private int version ;
	// Constructors

	/** default constructor */
	public Fbankinfo() {
	}

	/** full constructor */
	public Fbankinfo(Fuser fuser, String fname, String fbankNumber,
			int fbankType, Timestamp fcreateTime, int fstatus,
			Set<Fcapitaloperation> fcapitaloperations) {
		this.fuser = fuser;
		this.fname = fname;
		this.fbankNumber = fbankNumber;
		this.fbankType = fbankType;
		this.fcreateTime = fcreateTime;
		this.fstatus = fstatus;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fId", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUs_fId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fName", length = 128)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fBankNumber", length = 128)
	public String getFbankNumber() {
		return this.fbankNumber;
	}

	public void setFbankNumber(String fbankNumber) {
		this.fbankNumber = fbankNumber;
	}

	@Column(name = "fBankType")
	public int getFbankType() {
		return this.fbankType;
	}

	public void setFbankType(int fbankType) {
		this.fbankType = fbankType;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}