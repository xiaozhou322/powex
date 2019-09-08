package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.LogTypeEnum;

/**
 * Flog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "flog", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Flog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5045972823003778441L;
	private Integer fid;
	private Integer version;
	private Timestamp fcreateTime;
	private Integer ftype;
	private String ftype_s;
	private String fkey1;
	private String fkey2;
	private String fkey3;
	private String fkey4;
	private String fkey5;
	private String fkey6;

	// Constructors

	/** default constructor */
	public Flog() {
	}

	/** full constructor */
	public Flog(Timestamp fcreateTime, Integer ftype, String fkey1,
			String fkey2, String fkey3, String fkey4, String fkey5, String fkey6) {
		this.fcreateTime = fcreateTime;
		this.ftype = ftype;
		this.fkey1 = fkey1;
		this.fkey2 = fkey2;
		this.fkey3 = fkey3;
		this.fkey4 = fkey4;
		this.fkey5 = fkey5;
		this.fkey6 = fkey6;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fId", unique = true, nullable = false)
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

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fType")
	public Integer getFtype() {
		return this.ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fkey1", length = 128)
	public String getFkey1() {
		return this.fkey1;
	}

	public void setFkey1(String fkey1) {
		this.fkey1 = fkey1;
	}

	@Column(name = "fkey2", length = 128)
	public String getFkey2() {
		return this.fkey2;
	}

	public void setFkey2(String fkey2) {
		this.fkey2 = fkey2;
	}

	@Column(name = "fkey3", length = 128)
	public String getFkey3() {
		return this.fkey3;
	}

	public void setFkey3(String fkey3) {
		this.fkey3 = fkey3;
	}

	@Column(name = "fkey4", length = 128)
	public String getFkey4() {
		return this.fkey4;
	}

	public void setFkey4(String fkey4) {
		this.fkey4 = fkey4;
	}

	@Column(name = "fkey5", length = 128)
	public String getFkey5() {
		return this.fkey5;
	}

	public void setFkey5(String fkey5) {
		this.fkey5 = fkey5;
	}

	@Column(name = "fkey6", length = 128)
	public String getFkey6() {
		return this.fkey6;
	}

	public void setFkey6(String fkey6) {
		this.fkey6 = fkey6;
	}
	
	@Transient
	public String getFtype_s() {
		return LogTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

}