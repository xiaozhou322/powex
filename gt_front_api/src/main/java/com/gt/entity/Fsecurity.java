package com.gt.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fsecurity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fsecurity", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fsecurity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7426074451158563641L;
	private Integer fid;
	private Fsecurity fsecurity;
	private String fdescription;
	private String fname;
	private Integer fpriority;
	private String furl;
	private Set<Fsecurity> fsecurities = new HashSet<Fsecurity>(0);
	private Set<FroleSecurity> froleSecurities = new HashSet<FroleSecurity>(0);

	// Constructors

	/** default constructor */
	public Fsecurity() {
	}

	/** full constructor */
	public Fsecurity(Fsecurity fsecurity, String fdescription, String fname,
			Integer fpriority, Set<Fsecurity> fsecurities) {
		this.fsecurity = fsecurity;
		this.fdescription = fdescription;
		this.fname = fname;
		this.fpriority = fpriority;
		this.fsecurities = fsecurities;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fparentid")
	public Fsecurity getFsecurity() {
		return this.fsecurity;
	}

	public void setFsecurity(Fsecurity fsecurity) {
		this.fsecurity = fsecurity;
	}

	@Column(name = "fdescription")
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fname", length = 32)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fpriority")
	public Integer getFpriority() {
		return this.fpriority;
	}

	public void setFpriority(Integer fpriority) {
		this.fpriority = fpriority;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "fsecurity")
	@OrderBy("fpriority ASC")
	public Set<Fsecurity> getFsecurities() {
		return this.fsecurities;
	}

	public void setFsecurities(Set<Fsecurity> fsecurities) {
		this.fsecurities = fsecurities;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fsecurity")
	public Set<FroleSecurity> getFroleSecurities() {
		return this.froleSecurities;
	}

	public void setFroleSecurities(Set<FroleSecurity> froleSecurities) {
		this.froleSecurities = froleSecurities;
	}
	
	@Column(name = "furl")
	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}
}