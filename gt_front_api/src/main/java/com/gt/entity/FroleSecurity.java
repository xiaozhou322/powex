package com.gt.entity;

// default package
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * FroleSecurity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "frole_security", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FroleSecurity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7747721695713589365L;
	private Integer fid;
	private Fsecurity fsecurity;
	private Frole frole;

	// Constructors

	/** default constructor */
	public FroleSecurity() {
	}

	/** full constructor */
	public FroleSecurity(Fsecurity fsecurity, Frole frole) {
		this.fsecurity = fsecurity;
		this.frole = frole;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fsecurityid")
	public Fsecurity getFsecurity() {
		return this.fsecurity;
	}

	public void setFsecurity(Fsecurity fsecurity) {
		this.fsecurity = fsecurity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "froleid")
	public Frole getFrole() {
		return this.frole;
	}

	public void setFrole(Frole frole) {
		this.frole = frole;
	}

}