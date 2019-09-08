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
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * Emailvalidate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emailvalidate", catalog = "gtcoin")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Emailvalidate implements java.io.Serializable {

	// Fields

	private int fid;
	private Fuser fuser;
	private String fuuid;
	private Timestamp fcreateTime;
	private Timestamp fvalidateTime;
	private int type ;//SendMailTypeEnum
	private String fmail ;
	private int version ;
	private String fNewUUid ;
	
	private String code ;
	// Constructors

	/** default constructor */
	public Emailvalidate() {
	}

	/** minimal constructor */
	public Emailvalidate(String fuuid) {
		this.fuuid = fuuid;
	}

	/** full constructor */
	public Emailvalidate(Fuser fuser, String fuuid, Timestamp fcreateTime,
			Timestamp fvalidateTime) {
		this.fuser = fuser;
		this.fuuid = fuuid;
		this.fcreateTime = fcreateTime;
		this.fvalidateTime = fvalidateTime;
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

	@Column(name = "fUuid", nullable = false, length = 128)
	public String getFuuid() {
		return this.fuuid;
	}

	public void setFuuid(String fuuid) {
		this.fuuid = fuuid;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fValidateTime", length = 0)
	public Timestamp getFvalidateTime() {
		return this.fvalidateTime;
	}

	public void setFvalidateTime(Timestamp fvalidateTime) {
		this.fvalidateTime = fvalidateTime;
	}
	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name="type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name="fmail")
	public String getFmail() {
		return fmail;
	}

	public void setFmail(String fmail) {
		this.fmail = fmail;
	}

	@Column(name="fNewUUid")
	public String getfNewUUid() {
		return fNewUUid;
	}

	public void setfNewUUid(String fNewUUid) {
		this.fNewUUid = fNewUUid;
	}

	@Transient
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	
}