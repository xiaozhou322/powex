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

import org.hibernate.annotations.GenericGenerator;

/**
 * Fperiod entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fperiod", catalog = "gtcoin")
public class Fperiod implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2065966505970804957L;
	private Integer fid;
	private double fkai;
	private double fgao;
	private double fdi;
	private double fshou;
	private double fliang;
	private Timestamp ftime;
	private Ftrademapping ftrademapping;

	// Constructors

	/** default constructor */
	public Fperiod() {
	}

	/** full constructor */
	public Fperiod(double fkai, double fgao, double fdi, double fshou,
			double fliang, Timestamp ftime) {
		this.fkai = fkai;
		this.fgao = fgao;
		this.fdi = fdi;
		this.fshou = fshou;
		this.fliang = fliang;
		this.ftime = ftime;
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

	@Column(name = "fkai", precision = 15, scale = 5)
	public double getFkai() {
		return this.fkai;
	}

	public void setFkai(double fkai) {
		this.fkai = fkai;
	}

	@Column(name = "fgao", precision = 15, scale = 5)
	public double getFgao() {
		return this.fgao;
	}

	public void setFgao(double fgao) {
		this.fgao = fgao;
	}

	@Column(name = "fdi", precision = 15, scale = 5)
	public double getFdi() {
		return this.fdi;
	}

	public void setFdi(double fdi) {
		this.fdi = fdi;
	}

	@Column(name = "fshou", precision = 15, scale = 5)
	public double getFshou() {
		return this.fshou;
	}

	public void setFshou(double fshou) {
		this.fshou = fshou;
	}

	@Column(name = "fliang", precision = 15, scale = 5)
	public double getFliang() {
		return this.fliang;
	}

	public void setFliang(double fliang) {
		this.fliang = fliang;
	}

	@Column(name = "ftime", length = 0)
	public Timestamp getFtime() {
		return this.ftime;
	}

	public void setFtime(Timestamp ftime) {
		this.ftime = ftime;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ftrademapping")
	public Ftrademapping getFtrademapping() {
		return ftrademapping;
	}

	public void setFtrademapping(Ftrademapping ftrademapping) {
		this.ftrademapping = ftrademapping;
	}

	
}