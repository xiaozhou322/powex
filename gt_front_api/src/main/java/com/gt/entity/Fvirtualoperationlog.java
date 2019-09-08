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
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.OperationlogEnum;

/**
 * Fvirtualoperationlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvirtualoperationlog", catalog = "gtcoin")
public class Fvirtualoperationlog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1700582642269843250L;
	private Integer fid;
	private Integer version;
	private Fadmin fcreator;
	private Fuser fuser;
	private Fvirtualcointype fvirtualcointype;
	private Double fqty;
	private Integer fstatus;
	private String fstatus_s;//OperationlogEnum
	private Integer fisSendMsg;
	private Timestamp fcreateTime;

	// Constructors

	/** default constructor */
	public Fvirtualoperationlog() {
	}

	/** full constructor */
	public Fvirtualoperationlog(Fadmin fcreator, Fuser fuser,
			Fvirtualcointype fvirtualcointype, Double fqty, Integer fstatus,
			Integer fisSendMsg, Timestamp fcreateTime) {
		this.fcreator = fcreator;
		this.fuser = fuser;
		this.fvirtualcointype = fvirtualcointype;
		this.fqty = fqty;
		this.fstatus = fstatus;
		this.fisSendMsg = fisSendMsg;
		this.fcreateTime = fcreateTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "FId", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FCreatorId")
	public Fadmin getFcreator() {
		return this.fcreator;
	}

	public void setFcreator(Fadmin fcreator) {
		this.fcreator = fcreator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUserId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FVirtualCoinTypeId")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@Column(name = "FQty", precision = 16, scale = 6)
	public Double getFqty() {
		return this.fqty;
	}

	public void setFqty(Double fqty) {
		this.fqty = fqty;
	}

	@Column(name = "FStatus")
	public Integer getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "FIsSendMsg")
	public Integer getFisSendMsg() {
		return this.fisSendMsg;
	}

	public void setFisSendMsg(Integer fisSendMsg) {
		this.fisSendMsg = fisSendMsg;
	}

	@Column(name = "FCreateTime", length = 19)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Transient
	public String getFstatus_s() {
	    return OperationlogEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}
}