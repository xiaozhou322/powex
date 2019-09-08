package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Fbankin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbankin", catalog = "gtcoin")
public class Fbankin implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8099040592082000265L;
	private Integer fid;
	private Integer uid;
	private Double rmb;
	private String bankname;
	private Timestamp tday;
	private Integer ok;

	// Constructors

	/** default constructor */
	public Fbankin() {
	}

	/** full constructor */
	public Fbankin(Integer uid, Double rmb, String bankname, Timestamp tday,
			Integer ok) {
		this.uid = uid;
		this.rmb = rmb;
		this.bankname = bankname;
		this.tday = tday;
		this.ok = ok;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@Column(name = "uid")
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "rmb", precision = 16, scale = 6)
	public Double getRmb() {
		return this.rmb;
	}

	public void setRmb(Double rmb) {
		this.rmb = rmb;
	}

	@Column(name = "bankname", length = 200)
	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Column(name = "tday", length = 0)
	public Timestamp getTday() {
		return this.tday;
	}

	public void setTday(Timestamp tday) {
		this.tday = tday;
	}

	@Column(name = "ok")
	public Integer getOk() {
		return this.ok;
	}

	public void setOk(Integer ok) {
		this.ok = ok;
	}

}