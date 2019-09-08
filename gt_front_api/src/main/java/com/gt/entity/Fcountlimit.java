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

import com.gt.Enum.CountLimitTypeEnum;

/**
 * Fcountlimit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fcountlimit", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fcountlimit implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2194219705687241277L;
	private int fid;
	private String fip;
	private Timestamp fcreateTime;
	private int fcount;
	private int ftype;// CountLimitTypeEnum
	private String ftype_s;
	private int version;

	// Constructors

	/** default constructor */
	public Fcountlimit() {
	}

	/** full constructor */
	public Fcountlimit(String fip, Timestamp fcreateTime, int fcount) {
		this.fip = fip;
		this.fcreateTime = fcreateTime;
		this.fcount = fcount;
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

	@Column(name = "fIp", length = 64)
	public String getFip() {
		return this.fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fCount")
	public int getFcount() {
		return this.fcount;
	}

	public void setFcount(int fcount) {
		this.fcount = fcount;
	}

	@Column(name = "ftype")
	public int getFtype() {
		return ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public String getFtype_s() {
		return CountLimitTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}
}