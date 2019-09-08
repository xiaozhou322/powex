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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.QuestionStatusEnum;
import com.gt.Enum.QuestionTypeEnum;

/**
 * Fquestion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fquestion", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fquestion implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1934432176940788220L;
	private int fid;
	private String fname ;
	private Fuser fuser;
	private int ftype;//QuestionTypeEnum
	private String ftype_s ;
	private String fdesc;
	private int fstatus ;//QuestionStatusEnum
	private String fstatus_s ;
	private String ftelephone;
	private Timestamp fcreateTime ;
	private Timestamp fSolveTime ;
	private String fanswer ;
	private Fadmin fadmin ;//解决问题的客服
	private int version ;
	// Constructors

	/** default constructor */
	public Fquestion() {
	}

	/** full constructor */
	public Fquestion(Fuser fuser, int ftype, String fdesc, String ftelephone) {
		this.fuser = fuser;
		this.ftype = ftype;
		this.fdesc = fdesc;
		this.ftelephone = ftelephone;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuid")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "ftype")
	public int getFtype() {
		return this.ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fdesc", length = 1024)
	public String getFdesc() {
		return this.fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	@Column(name = "ftelephone", length = 32)
	public String getFtelephone() {
		return this.ftelephone;
	}

	public void setFtelephone(String ftelephone) {
		this.ftelephone = ftelephone;
	}

	@Column(name="fstatus")
	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Transient
	public String getFstatus_s() {
		int status = getFstatus() ;
		return QuestionStatusEnum.getEnumString(status) ;
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name="fcreateTime")
	public Timestamp getFcreateTime() {
		return fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name="fSolveTime")
	public Timestamp getfSolveTime() {
		return fSolveTime;
	}

	public void setfSolveTime(Timestamp fSolveTime) {
		this.fSolveTime = fSolveTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faid")
	public Fadmin getFadmin() {
		return fadmin;
	}

	public void setFadmin(Fadmin fadmin) {
		this.fadmin = fadmin;
	}

	@Transient
	public String getFtype_s() {
		int type = getFtype() ;
		return QuestionTypeEnum.getEnumString(type) ;
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Column(name="fname")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name="fanswer")
	public String getFanswer() {
		return fanswer;
	}

	public void setFanswer(String fanswer) {
		this.fanswer = fanswer;
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