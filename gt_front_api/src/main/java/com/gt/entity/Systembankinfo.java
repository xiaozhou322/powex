package com.gt.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.SystemBankInfoEnum;

/**
 * Systembankinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "systembankinfo", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Systembankinfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2439318484038642390L;
	private int fid;
	private String fbankName;
	private String fownerName;
	private String fbankAddress;// 具体支行
	private String fbankNumber;
	private Timestamp fcreateTime;
	private int fstatus;// 1正常,2停用
	private String fstatus_s;

	private Set<Fcapitaloperation> fcapitaloperations = new HashSet<Fcapitaloperation>(
			0);
	private int version ;
	// Constructors

	/** default constructor */
	public Systembankinfo() {
	}

	/** full constructor */
	public Systembankinfo(String fbankName, String fbankAddress,
			String fbankNumber, Timestamp fcreateTime, int fstatus,
			Set<Fcapitaloperation> fcapitaloperations) {
		this.fbankName = fbankName;
		this.fbankAddress = fbankAddress;
		this.fbankNumber = fbankNumber;
		this.fcreateTime = fcreateTime;
		this.fstatus = fstatus;
		this.fcapitaloperations = fcapitaloperations;
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

	@Column(name = "fBankName", length = 128)
	public String getFbankName() {
		return this.fbankName;
	}

	public void setFbankName(String fbankName) {
		this.fbankName = fbankName;
	}

	@Column(name = "fownerName", length = 128)
	public String getFownerName() {
		return fownerName;
	}

	public void setFownerName(String fownerName) {
		this.fownerName = fownerName;
	}

	@Column(name = "fBankAddress", length = 256)
	public String getFbankAddress() {
		return this.fbankAddress;
	}

	public void setFbankAddress(String fbankAddress) {
		this.fbankAddress = fbankAddress;
	}

	@Column(name = "fBankNumber", length = 128)
	public String getFbankNumber() {
		return this.fbankNumber;
	}

	public void setFbankNumber(String fbankNumber) {
		this.fbankNumber = fbankNumber;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systembankinfo")
	public Set<Fcapitaloperation> getFcapitaloperations() {
		return this.fcapitaloperations;
	}

	public void setFcapitaloperations(Set<Fcapitaloperation> fcapitaloperations) {
		this.fcapitaloperations = fcapitaloperations;
	}

	@Transient
	public String getFstatus_s() {
		return SystemBankInfoEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
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