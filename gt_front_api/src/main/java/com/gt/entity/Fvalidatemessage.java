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

import org.hibernate.annotations.GenericGenerator;

/**
 * Fvalidatemessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvalidatemessage", catalog = "gtcoin")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fvalidatemessage implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3786683312567693430L;
	private int fid;
	private Fuser fuser;
	private String fphone;
	private String fcontent;
	private int fstatus;
	private Timestamp fcreateTime;
	private Timestamp fsendTime;
	private int version ;
	private int flang;
	// Constructors

	/** default constructor */
	public Fvalidatemessage() {
	}

	/** full constructor */
	public Fvalidatemessage(Fuser fuser, String fphone, String fcontent,
			int fstatus, Timestamp fcreateTime, Timestamp fsendTime) {
		this.fuser = fuser;
		this.fphone = fphone;
		this.fcontent = fcontent;
		this.fstatus = fstatus;
		this.fcreateTime = fcreateTime;
		this.fsendTime = fsendTime;
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

	@Column(name = "fPhone", length = 32)
	public String getFphone() {
		return this.fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	@Column(name = "fContent", length = 65535)
	public String getFcontent() {
		return this.fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fSendTime", length = 0)
	public Timestamp getFsendTime() {
		return this.fsendTime;
	}

	public void setFsendTime(Timestamp fsendTime) {
		this.fsendTime = fsendTime;
	}
	
	@Column(name = "flang")
	public int getFlang() {
		return flang;
	}

	public void setFlang(int flang) {
		this.flang = flang;
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