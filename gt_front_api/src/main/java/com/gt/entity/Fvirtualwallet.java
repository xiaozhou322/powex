package com.gt.entity;

import java.sql.Timestamp;
import java.util.Set;

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

import org.hibernate.annotations.GenericGenerator;

/**
 * Fvirtualwallet entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvirtualwallet", catalog = "gtcoin")
// @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fvirtualwallet implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3541463102851281122L;
	private int fid;
	private Fvirtualcointype fvirtualcointype;
	private double ftotal;
	private double ffrozen;
	private Timestamp flastUpdateTime;
	private Fuser fuser;
	private int version;

	private double fborrowBtc;// 已借款
	private double fHaveAppointBorrowBtc;// 已预约借款
	
	private int fseason ; //预购释放季数，默认200季，一天一季释放
	private double fcanlendBtc;// 预购数量
	private double ffrozenLendBtc;// 冻结预购
	private double falreadyLendBtc;// 已经释放币数

	private double flocked;//锁仓数量
	
	private String fack_id ;//用于撮合，不需要查出记录就可以更新
	private int fack_uid ;
	private int fack_vid ;
	// Constructors

	/** default constructor */
	public Fvirtualwallet() {
	}

	/** full constructor */
	public Fvirtualwallet(Fvirtualcointype fvirtualcointype, double ftotal,
			double ffrozen, Timestamp flastUpdateTime, Set<Fuser> fusers) {
		this.fvirtualcointype = fvirtualcointype;
		this.ftotal = ftotal;
		this.ffrozen = ffrozen;
		this.flastUpdateTime = flastUpdateTime;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fVi_fId")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@Column(name = "fTotal", precision = 12, scale = 0)
	public double getFtotal() {
		return this.ftotal;
	}

	public void setFtotal(double ftotal) {
		this.ftotal = ftotal;
	}

	@Column(name = "fFrozen", precision = 12, scale = 0)
	public double getFfrozen() {
		return this.ffrozen;
	}

	public void setFfrozen(double ffrozen) {
		this.ffrozen = ffrozen;
	}

	@Column(name = "fLastUpdateTime", length = 0)
	public Timestamp getFlastUpdateTime() {
		return this.flastUpdateTime;
	}

	public void setFlastUpdateTime(Timestamp flastUpdateTime) {
		this.flastUpdateTime = flastUpdateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuid")
	public Fuser getFuser() {
		return fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "fBorrowBtc", precision = 16, scale = 6)
	public double getFborrowBtc() {
		return this.fborrowBtc;
	}

	public void setFborrowBtc(double fborrowBtc) {
		this.fborrowBtc = fborrowBtc;
	}

	@Column(name = "fCanlendBtc", precision = 16, scale = 6)
	public double getFcanlendBtc() {
		return this.fcanlendBtc;
	}

	public void setFcanlendBtc(double fcanlendBtc) {
		this.fcanlendBtc = fcanlendBtc;
	}

	@Column(name = "fFrozenLendBtc", precision = 16, scale = 6)
	public double getFfrozenLendBtc() {
		return this.ffrozenLendBtc;
	}

	public void setFfrozenLendBtc(double ffrozenLendBtc) {
		this.ffrozenLendBtc = ffrozenLendBtc;
	}

	@Column(name = "fAlreadyLendBtc", precision = 16, scale = 6)
	public double getFalreadyLendBtc() {
		return this.falreadyLendBtc;
	}

	public void setFalreadyLendBtc(double falreadyLendBtc) {
		this.falreadyLendBtc = falreadyLendBtc;
	}

	@Column(name = "fHaveAppointBorrowBtc")
	public double getfHaveAppointBorrowBtc() {
		return fHaveAppointBorrowBtc;
	}

	public void setfHaveAppointBorrowBtc(double fHaveAppointBorrowBtc) {
		this.fHaveAppointBorrowBtc = fHaveAppointBorrowBtc;
	}
	
	@Column(name = "fSeason")
	public int getFseason() {
		return fseason;
	}

	public void setFseason(int fseason) {
		this.fseason = fseason;
	}
	
	@Column(name = "fLocked", precision = 16, scale = 6)
	public double getFlocked() {
		return flocked;
	}

	public void setFlocked(double flocked) {
		this.flocked = flocked;
	}
	
	@Transient
	public String getFack_id() {
		return fack_id;
	}

	public void setFack_id(String fack_id) {
		this.fack_id = fack_id;
	}
	@Transient
	public int getFack_uid() {
		return fack_uid;
	}

	public void setFack_uid(int fack_uid) {
		this.fack_uid = fack_uid;
	}
	@Transient
	public int getFack_vid() {
		return fack_vid;
	}

	public void setFack_vid(int fack_vid) {
		this.fack_vid = fack_vid;
	}

	

}