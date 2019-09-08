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
 * Validateemails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvalidateemail", catalog = "gtcoin")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fvalidateemail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4417514413853613805L;
	private int fid;
	private Fuser fuser;
	private String ftitle;
	private String fcontent;
	private int fstatus;//ValidateMailStatusEnum
	private Timestamp fcreateTime;
	private Timestamp fsendTime;
	private String email ;
	private int version ;
	private int flang;
	// Constructors


	/** default constructor */
	public Fvalidateemail() {
	}

	/** full constructor */
	public Fvalidateemail(Fuser fuser, String ftitle, String fcontent,
			int fstatus, Timestamp fcreateTime, Timestamp fsendTime) {
		this.fuser = fuser;
		this.ftitle = ftitle;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FUs_fId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fTitle", length = 256)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
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
	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="flang")
	public int getFlang() {
		return flang;
	}

	public void setFlang(int flang) {
		this.flang = flang;
	}
	
}