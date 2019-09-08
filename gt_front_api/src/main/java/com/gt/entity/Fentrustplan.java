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

import com.gt.Enum.EntrustPlanStatusEnum;
import com.gt.Enum.EntrustPlanTypeEnum;

/**
 * Fentrustplan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fentrustplan", catalog = "gtcoin")
// @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fentrustplan implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7139835224245917319L;
	private int fid;
	private Fvirtualcointype fvirtualcointype;
	private Fuser fuser;
	private Timestamp fcreateTime;
	private double fcount;
	private double fprize;
	private boolean fisLimit;
	private double famount;
	private int ftype;// EntrustPlanTypeEnum
	private String ftype_s;
	private int fstatus;// EntrustPlanStatusEnum
	private String fstatus_s;
	private Fentrust fentrust;
	private int version;

	// Constructors

	/** default constructor */
	public Fentrustplan() {
	}

	/** full constructor */
	public Fentrustplan(Fvirtualcointype fvirtualcointype, Fuser fuser,
			Timestamp fcreateTime, double fcount, double fprize, double famount,
			int fstatus, Set<Fentrust> fentrusts) {
		this.fvirtualcointype = fvirtualcointype;
		this.fuser = fuser;
		this.fcreateTime = fcreateTime;
		this.fcount = fcount;
		this.fprize = fprize;
		this.famount = famount;
		this.fstatus = fstatus;
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
	@JoinColumn(name = "fVi_fId")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUs_fId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fCount", precision = 12, scale = 0)
	public double getFcount() {
		return this.fcount;
	}

	public void setFcount(double fcount) {
		this.fcount = fcount;
	}

	@Column(name = "fPrize", precision = 12, scale = 0)
	public Double getFprize() {
		return this.fprize;
	}

	public void setFprize(double fprize) {
		this.fprize = fprize;
	}

	@Column(name = "fAmount", precision = 12, scale = 0)
	public double getFamount() {
		return this.famount;
	}

	public void setFamount(double famount) {
		this.famount = famount;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entrust_id")
	public Fentrust getFentrust() {
		return fentrust;
	}

	public void setFentrust(Fentrust fentrust) {
		this.fentrust = fentrust;
	}

	@Column(name = "ftype")
	public int getFtype() {
		return ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Transient
	public String getFtype_s() {
		int type = this.getFtype();
		return EntrustPlanTypeEnum.getEnumString(type);
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Transient
	public String getFstatus_s() {
		int status = this.getFstatus();
		return EntrustPlanStatusEnum.getEnumString(status);
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name = "fisLimit")
	public boolean isFisLimit() {
		return fisLimit;
	}

	public void setFisLimit(boolean fisLimit) {
		this.fisLimit = fisLimit;
	}

}