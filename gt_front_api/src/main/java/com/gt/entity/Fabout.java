package com.gt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.util.HTMLSpirit;

/**
 * Fabout entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fabout", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fabout implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911494738489352347L;
	private int fid;
	private String ftitle;
	private String fcontent;
	private String fcontent_s;
	private String ftype;
	private String ftitle_cn;
	private String fcontent_cn;
	private String ftype_cn;

	// Constructors

	/** default constructor */
	public Fabout() {
	}

	/** full constructor */
	public Fabout(String ftitle, String fcontent) {
		this.ftitle = ftitle;
		this.fcontent = fcontent;
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

	@Column(name = "ftitle", length = 128)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "fcontent", length = 65535)
	public String getFcontent() {
		return this.fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	@Transient
	public String getFcontent_s() {
		return HTMLSpirit.delHTMLTag(getFcontent());
	}

	public void setFcontent_s(String fcontent_s) {
		this.fcontent_s = fcontent_s;
	}
	
	@Column(name = "ftype")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
	@Column(name = "ftitle_cn", length = 128)
	public String getFtitle_cn() {
		return ftitle_cn;
	}

	public void setFtitle_cn(String ftitle_cn) {
		this.ftitle_cn = ftitle_cn;
	}
	
	@Column(name = "fcontent_cn", length = 65535)
	public String getFcontent_cn() {
		return fcontent_cn;
	}

	public void setFcontent_cn(String fcontent_cn) {
		this.fcontent_cn = fcontent_cn;
	}
	
	@Column(name = "ftype_cn", length = 100)
	public String getFtype_cn() {
		return ftype_cn;
	}

	public void setFtype_cn(String ftype_cn) {
		this.ftype_cn = ftype_cn;
	}

}