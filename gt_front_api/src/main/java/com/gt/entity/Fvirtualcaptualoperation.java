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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTransferStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;

/**
 * Fvirtualcaptualoperation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvirtualcaptualoperation", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvirtualcaptualoperation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7762340727586455415L;
	private int fid;
	private Fuser fuser;
	private Fvirtualcointype fvirtualcointype;
	private Timestamp fcreateTime;
	private Timestamp flastUpdateTime;
	private double famount;
	private double ffees;
	private int ftype;// 鍏呭�兼垨鎻愮幇VirtualCapitalOperationTypeEnum
	private String ftype_s;
	private int fstatus;// VirtualCoinOperationInStatusEnum||VirtualCoinOperationOutStatusEnum
	private String fstatus_s;
	private String withdraw_virtual_address;// 鎻愮幇鍦板潃
	private String recharge_virtual_address;// 鍏呭�煎湴鍧�
	private String ftradeUniqueNumber ;
	private int fconfirmations ;//纭鏁�
	private int version;
	private boolean fhasOwner ;//鍏呭�艰褰曟槸鍚﹀綊灞炴煇鐢ㄦ埛
    private String fischarge;
    private boolean fisaudit;
	// Constructors

	private double tocny ;

	/** default constructor */
	public Fvirtualcaptualoperation() {
	}

	/** full constructor */
	public Fvirtualcaptualoperation(Fvirtualaddress fvirtualaddress,
			Fuser fuser, Fvirtualcointype fvirtualcointype, int fusFId,
			Timestamp fcreateTime, double famount, int ftype,
			int fstatus, double ffees) {
		this.fuser = fuser;
		this.fvirtualcointype = fvirtualcointype;
		this.fcreateTime = fcreateTime;
		this.famount = famount;
		this.ftype = ftype;
		this.fstatus = fstatus;
		this.ffees = ffees;
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
	@JoinColumn(name = "FUs_fId2")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fVi_fId2")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}


	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fAmount", precision = 12, scale = 0)
	public double getFamount() {
		return this.famount;
	}

	public void setFamount(double famount) {
		this.famount = famount;
	}

	@Column(name = "fType")
	public int getFtype() {
		return this.ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "flastUpdateTime")
	public Timestamp getFlastUpdateTime() {
		return flastUpdateTime;
	}

	public void setFlastUpdateTime(Timestamp flastUpdateTime) {
		this.flastUpdateTime = flastUpdateTime;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public String getFtype_s() {
		return VirtualCapitalOperationTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Transient
	public String getFstatus_s() {
		int status = this.getFstatus();
		if (this.getFtype() == VirtualCapitalOperationTypeEnum.COIN_IN) {
			return VirtualCapitalOperationInStatusEnum.getEnumString(status);
		} else if (this.getFtype() == VirtualCapitalOperationTypeEnum.COIN_TRANSFER) {
			return VirtualCapitalOperationTransferStatusEnum.getEnumString(status);
		} else {
			return VirtualCapitalOperationOutStatusEnum.getEnumString(status);
		}
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name = "withdraw_virtual_address")
	public String getWithdraw_virtual_address() {
		return withdraw_virtual_address;
	}

	public void setWithdraw_virtual_address(String withdraw_virtual_address) {
		this.withdraw_virtual_address = withdraw_virtual_address;
	}

	@Column(name = "recharge_virtual_address")
	public String getRecharge_virtual_address() {
		return recharge_virtual_address;
	}

	public void setRecharge_virtual_address(String recharge_virtual_address) {
		this.recharge_virtual_address = recharge_virtual_address;
	}

	@Column(name = "ffees")
	public double getFfees() {
		return ffees;
	}

	public void setFfees(double ffees) {
		this.ffees = ffees;
	}

	@Column(name="ftradeUniqueNumber")
	public String getFtradeUniqueNumber() {
		return ftradeUniqueNumber;
	}

	public void setFtradeUniqueNumber(String ftradeUniqueNumber) {
		this.ftradeUniqueNumber = ftradeUniqueNumber;
	}

	@Column(name="fconfirmations")
	public int getFconfirmations() {
		return fconfirmations;
	}

	public void setFconfirmations(int fconfirmations) {
		this.fconfirmations = fconfirmations;
	}

	@Column(name="fhasOwner")
	public boolean isFhasOwner() {
		return fhasOwner;
	}

	public void setFhasOwner(boolean fhasOwner) {
		this.fhasOwner = fhasOwner;
	}
	
	@Column(name="fischarge")
	public String getFischarge() {
		return fischarge;
	}

	public void setFischarge(String fischarge) {
		this.fischarge = fischarge;
	}

	@Column(name="tocny")
	public double getTocny() {
		return tocny;
	}

	public void setTocny(double tocny) {
		this.tocny = tocny;
	}

	@Column(name="fisaudit")
    public boolean isFisaudit() {
		return fisaudit;
	}

	public void setFisaudit(boolean fisaudit) {
		this.fisaudit = fisaudit;
	}

}