package com.gt.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Farticletype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "farticletype", catalog = "gtcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Farticletype implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5610261435563256768L;
	private int fid;
	private String fname;
	private String fkeywords;
	private String fdescription;
	private Timestamp flastCreateDate;
	private Timestamp flastModifyDate;
	private int version;
	private String fname_cn;
	// Constructors

	/** default constructor */
	public Farticletype() {
	}

	/** full constructor */
	public Farticletype(String fname, String fkeywords, String fdescription,
			Timestamp flastCreateDate, Timestamp flastModifyDate,
			Set<Farticle> farticles) {
		this.fname = fname;
		this.fkeywords = fkeywords;
		this.fdescription = fdescription;
		this.flastCreateDate = flastCreateDate;
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

	@Column(name = "fName", length = 128)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fKeywords", length = 1024)
	public String getFkeywords() {
		return this.fkeywords;
	}

	public void setFkeywords(String fkeywords) {
		this.fkeywords = fkeywords;
	}

	@Column(name = "fDescription", length = 1024)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fLastCreateDate", length = 0)
	public Timestamp getFlastCreateDate() {
		return this.flastCreateDate;
	}

	public void setFlastCreateDate(Timestamp flastCreateDate) {
		this.flastCreateDate = flastCreateDate;
	}

	@Column(name = "fLastModifyDate", length = 0)
	public Timestamp getFlastModifyDate() {
		return this.flastModifyDate;
	}

	public void setFlastModifyDate(Timestamp flastModifyDate) {
		this.flastModifyDate = flastModifyDate;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(name = "fName_cn", length = 128)
	public String getFname_cn() {
		return fname_cn;
	}

	public void setFname_cn(String fname_cn) {
		this.fname_cn = fname_cn;
	}

}