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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Farticle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbanner", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fbanner implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9129347051572841526L;
	private int fid;
	private Fadmin fadminByFcreateAdmin;
	private Fadmin fadminByFmodifyAdmin;
	private String ftitle;
	private Timestamp fcreateDate;
	private Timestamp flastModifyDate;
	private int version ;
	private String furl;
	private String fimgUrl;
	private String fimgUrlm;
	private String ftype;
	private boolean fisding;
	private boolean fstatus;
	
	//中英文内容展示
	private String fcontent;
	private String fcontent_en;
	
	// Constructors



	/** default constructor */
	public Fbanner() {
	}

	/** full constructor */
	public Fbanner(Fadmin fadminByFcreateAdmin,
			Fadmin fadminByFmodifyAdmin, String ftitle, Timestamp fcreateDate,
			Timestamp flastModifyDate) {
		this.fadminByFcreateAdmin = fadminByFcreateAdmin;
		this.fadminByFmodifyAdmin = fadminByFmodifyAdmin;
		this.ftitle = ftitle;
		this.fcreateDate = fcreateDate;
		this.flastModifyDate = flastModifyDate;
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
	@JoinColumn(name = "fCreateAdmin")
	public Fadmin getFadminByFcreateAdmin() {
		return this.fadminByFcreateAdmin;
	}

	public void setFadminByFcreateAdmin(Fadmin fadminByFcreateAdmin) {
		this.fadminByFcreateAdmin = fadminByFcreateAdmin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fModifyAdmin")
	public Fadmin getFadminByFmodifyAdmin() {
		return this.fadminByFmodifyAdmin;
	}

	public void setFadminByFmodifyAdmin(Fadmin fadminByFmodifyAdmin) {
		this.fadminByFmodifyAdmin = fadminByFmodifyAdmin;
	}

	@Column(name = "fTitle", length = 200)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	
	@Column(name = "fCreateDate", length = 0)
	public Timestamp getFcreateDate() {
		return this.fcreateDate;
	}

	public void setFcreateDate(Timestamp fcreateDate) {
		this.fcreateDate = fcreateDate;
	}

	@Column(name = "fLastModifyDate", length = 0)
	public Timestamp getFlastModifyDate() {
		return this.flastModifyDate;
	}

	public void setFlastModifyDate(Timestamp flastModifyDate) {
		this.flastModifyDate = flastModifyDate;
	}
	

	@Column(name = "furl", length = 200)
	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}
	

	
	
	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "fimgurl", length = 200)
	public String getFimgUrl() {
		return fimgUrl;
	}

	public void setFimgUrl(String fimgUrl) {
		this.fimgUrl = fimgUrl;
	}

	@Column(name = "ftype", length = 20)
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
	@Column(name = "fisding")
	public boolean isFisding() {
		return fisding;
	}

	public void setFisding(boolean fisding) {
		this.fisding = fisding;
	}

	@Column(name = "fimgurlm", length = 200)
	public String getFimgUrlm() {
		return fimgUrlm;
	}

	public void setFimgUrlm(String fimgUrlm) {
		this.fimgUrlm = fimgUrlm;
	}

	@Column(name = "fstatus")
	public boolean isFstatus() {
		return fstatus;
	}

	public void setFstatus(boolean fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fcontent")
	public String getFcontent() {
		return fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	@Column(name = "fcontent_en")
	public String getFcontent_en() {
		return fcontent_en;
	}

	public void setFcontent_en(String fcontent_en) {
		this.fcontent_en = fcontent_en;
	}

}