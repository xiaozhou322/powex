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

import com.gt.Enum.LinkTypeEnum;

/**
 * Ffriendlink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ffriendlink", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ffriendlink implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2596612340565259188L;
	private int fid;
	private String fname;
	private String fdescription;
	private int forder;
	private Timestamp fcreateTime;
	private String furl;
	private int ftype;//LinkTypeEnum
	private String ftype_s;
	private int version ;
	private String fimages;

	// Constructors
	@Transient
	public String getFtype_s() {
		String type = type = LinkTypeEnum.getEnumString(this.ftype);
		return type;
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	/** default constructor */
	public Ffriendlink() {
	}

	/** full constructor */
	public Ffriendlink(String fname, String fdescription, int forder,
			Timestamp fcreateTime, String furl, int ftype) {
		this.fname = fname;
		this.fdescription = fdescription;
		this.forder = forder;
		this.fcreateTime = fcreateTime;
		this.furl = furl;
		this.ftype = ftype;
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

	@Column(name = "fName", length = 128)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fDescription", length = 1024)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fOrder")
	public int getForder() {
		return this.forder;
	}

	public void setForder(int forder) {
		this.forder = forder;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fUrl", length = 128)
	public String getFurl() {
		return this.furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	@Column(name = "fType")
	public int getFtype() {
		return this.ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}
	
	@Column(name = "fimages")
	public String getFimages() {
		return fimages;
	}

	public void setFimages(String fimages) {
		this.fimages = fimages;
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