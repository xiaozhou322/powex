package com.gt.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * Fasset entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "freleaselog", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Freleaselog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4775330611434597924L;
	private int fid;
	private Fuser fuser;
	private Fvirtualcointype fvirtualcointype;
	private Double fqty;
	private Timestamp fcreatetime;
	private int fseason;
	private int freleasetype;



	// Constructors

	/** default constructor */
	public Freleaselog() {
	}

	/** full constructor */
	public Freleaselog(Fuser fuser, Double fqty,Double ffee, Timestamp fcreatetime,int fseason) {
		this.fuser = fuser;
		this.fcreatetime = fcreatetime;
		this.setFseason(fseason);
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuserId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fvc_fId")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@Column(name = "fqty", precision = 20, scale = 8)
	public Double getFqty() {
		return fqty;
	}

	public void setFqty(Double fqty) {
		this.fqty = fqty;
	}

	@Column(name = "fSeason")
	public int getFseason() {
		return fseason;
	}

	public void setFseason(int fseason) {
		this.fseason = fseason;
	}

	@Column(name = "freleasetype")
	public int getFreleasetype() {
		return freleasetype;
	}

	public void setFreleasetype(int freleasetype) {
		this.freleasetype = freleasetype;
	}
}