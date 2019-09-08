package com.gt.entity;
// default package

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Frole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "frole", catalog = "gtcoin", uniqueConstraints = @UniqueConstraint(columnNames = "fname"))
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Frole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4873379978777196045L;
	private Integer fid;
	private String fdescription;
	private String fname;
	private Set<FroleSecurity> froleSecurities = new HashSet<FroleSecurity>(0);
	private Set<Fadmin> fadmins = new HashSet<Fadmin>(0);

	// Constructors

	/** default constructor */
	public Frole() {
	}

	/** minimal constructor */
	public Frole(String fname) {
		this.fname = fname;
	}

	/** full constructor */
	public Frole(String fdescription, String fname,
			Set<FroleSecurity> froleSecurities) {
		this.fdescription = fdescription;
		this.fname = fname;
		this.froleSecurities = froleSecurities;
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

	@Column(name = "fdescription", length = 256)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fname", unique = true, nullable = false, length = 64)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "frole")
	public Set<FroleSecurity> getFroleSecurities() {
		return this.froleSecurities;
	}

	public void setFroleSecurities(Set<FroleSecurity> froleSecurities) {
		this.froleSecurities = froleSecurities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "frole")
	public Set<Fadmin> getFadmins() {
		return this.fadmins;
	}

	public void setFadmins(Set<Fadmin> fadmins) {
		this.fadmins = fadmins;
	}
}