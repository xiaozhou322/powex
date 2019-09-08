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

import com.gt.Enum.OperationlogEnum;
import com.gt.Enum.RemittanceTypeEnum;

/**
 * Foperationlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "foperationlog", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Foperationlog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7895404564294189623L;
	private int fid;
	private Fuser fuser;
	private int ftype;// RemittanceTypeEnum
	private String ftype_s;
	private int fstatus;// OperationlogEnum
	private String fstatus_s;
	private double famount;
	private Timestamp fcreateTime;
	private Timestamp flastUpdateTime;
	private String fdescription;
	private String fkey1;
	private String fkey2;
	private String fkey3;
	private String fkey4;
	private String fkey5;
	private int version ;
	// Constructors

	/** default constructor */
	public Foperationlog() {
	}

	/** full constructor */
	public Foperationlog(Fuser fuser, int ftype, int fstatus,
			double famount, Timestamp fcreateTime, Timestamp flastUpdateTime,
			String fdescription, String fkey1, String fkey2, String fkey3,
			String fkey4, String fkey5) {
		this.fuser = fuser;
		this.ftype = ftype;
		this.fstatus = fstatus;
		this.famount = famount;
		this.fcreateTime = fcreateTime;
		this.flastUpdateTime = flastUpdateTime;
		this.fdescription = fdescription;
		this.fkey1 = fkey1;
		this.fkey2 = fkey2;
		this.fkey3 = fkey3;
		this.fkey4 = fkey4;
		this.fkey5 = fkey5;
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

	@Column(name = "fstatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "famount", precision = 15, scale = 5)
	public double getFamount() {
		return this.famount;
	}

	public void setFamount(double famount) {
		this.famount = famount;
	}

	@Column(name = "fcreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "flastUpdateTime", length = 0)
	public Timestamp getFlastUpdateTime() {
		return this.flastUpdateTime;
	}

	public void setFlastUpdateTime(Timestamp flastUpdateTime) {
		this.flastUpdateTime = flastUpdateTime;
	}

	@Column(name = "fdescription", length = 256)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fkey1", length = 256)
	public String getFkey1() {
		return this.fkey1;
	}

	public void setFkey1(String fkey1) {
		this.fkey1 = fkey1;
	}

	@Column(name = "fkey2", length = 256)
	public String getFkey2() {
		return this.fkey2;
	}

	public void setFkey2(String fkey2) {
		this.fkey2 = fkey2;
	}

	@Column(name = "fkey3", length = 256)
	public String getFkey3() {
		return this.fkey3;
	}

	public void setFkey3(String fkey3) {
		this.fkey3 = fkey3;
	}

	@Column(name = "fkey4", length = 256)
	public String getFkey4() {
		return this.fkey4;
	}

	public void setFkey4(String fkey4) {
		this.fkey4 = fkey4;
	}

	@Column(name = "fkey5", length = 256)
	public String getFkey5() {
		return this.fkey5;
	}

	public void setFkey5(String fkey5) {
		this.fkey5 = fkey5;
	}

	@Transient
	public String getFtype_s() {
		return RemittanceTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Transient
	public String getFstatus_s() {
		return OperationlogEnum.getEnumString(this.getFstatus());
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