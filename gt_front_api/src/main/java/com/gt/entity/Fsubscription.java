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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.SubFrozenTypeEnum;
import com.gt.Enum.SubscriptionTypeEnum;

/**
 * Fsubscription entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fsubscription", catalog = "gtcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fsubscription implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 104936280331533643L;
	private int fid;
	private Fvirtualcointype fvirtualcointypeCost;
	private Fvirtualcointype fvirtualcointype;
	private boolean fisopen;// 是否开放众筹
	// private double ftotalqty;
	private double ftotal;// 众筹总量
	private double fAlreadyByCount;// 已众筹
	// private Double fqty;//已中签
	private double fprice;// 众筹价格
	private int fbuyCount;// 每人最大众筹数量，0为无限
	private int fbuyTimes;// 每人最多众筹次数，0为无限
	private int fminbuyCount;// 最少买 几手
	private Timestamp fcreateTime;
	private Timestamp fbeginTime;// 开始时间
	private Timestamp fendTime;// 结束时间
	private int ftype;// SubscriptionTypeEnum
	private String ftype_s;
	private String fstatus;
	private String fcontent;
	private String ftitle;

	private Integer fnumber;
	private int version;
	private String symbol;
	private String symbol1;
	private boolean fisICO;
	private int ffrozenType;
	private String ffrozenType_s;
	private double frate;
	private boolean fisstart;


	private Set<Fsubscriptionlog> fsubscriptionlogs = new HashSet<Fsubscriptionlog>(
			0);

	// Constructors

	/** default constructor */
	public Fsubscription() {
	}

	/** full constructor */
	public Fsubscription(Fvirtualcointype fvirtualcointype, boolean fisopen,
			Double ftotal, Double fprice, int fbuyCount, int fbuyTimes,
			Timestamp fcreateTime, Timestamp fbeginTime, Timestamp fendTime,
			Set<Fsubscriptionlog> fsubscriptionlogs) {
		this.fvirtualcointype = fvirtualcointype;
		this.fisopen = fisopen;
		this.ftotal = ftotal;
		this.fprice = fprice;
		this.fbuyCount = fbuyCount;
		this.fbuyTimes = fbuyTimes;
		this.fcreateTime = fcreateTime;
		this.fbeginTime = fbeginTime;
		this.fendTime = fendTime;
		this.fsubscriptionlogs = fsubscriptionlogs;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fvi_id")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fcost_vi_id")
	public Fvirtualcointype getFvirtualcointypeCost() {
		return fvirtualcointypeCost;
	}

	public void setFvirtualcointypeCost(Fvirtualcointype fvirtualcointypeCost) {
		this.fvirtualcointypeCost = fvirtualcointypeCost;
	}

	@Column(name = "fisopen")
	public boolean getFisopen() {
		return this.fisopen;
	}

	public void setFisopen(boolean fisopen) {
		this.fisopen = fisopen;
	}

	@Column(name = "ftotal", precision = 16, scale = 6)
	public Double getFtotal() {
		return this.ftotal;
	}

	public void setFtotal(Double ftotal) {
		this.ftotal = ftotal;
	}

	@Column(name = "fprice", precision = 16, scale = 6)
	public Double getFprice() {
		return this.fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}

	@Column(name = "fbuyCount")
	public int getFbuyCount() {
		return this.fbuyCount;
	}

	public void setFbuyCount(int fbuyCount) {
		this.fbuyCount = fbuyCount;
	}

	@Column(name = "fbuyTimes")
	public int getFbuyTimes() {
		return this.fbuyTimes;
	}

	public void setFbuyTimes(int fbuyTimes) {
		this.fbuyTimes = fbuyTimes;
	}

	@Column(name = "fcreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fBeginTime", length = 0)
	public Timestamp getFbeginTime() {
		return this.fbeginTime;
	}

	public void setFbeginTime(Timestamp fbeginTime) {
		this.fbeginTime = fbeginTime;
	}

	@Column(name = "fEndTime", length = 0)
	public Timestamp getFendTime() {
		return this.fendTime;
	}

	public void setFendTime(Timestamp fendTime) {
		this.fendTime = fendTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fsubscription")
	public Set<Fsubscriptionlog> getFsubscriptionlogs() {
		return this.fsubscriptionlogs;
	}

	public void setFsubscriptionlogs(Set<Fsubscriptionlog> fsubscriptionlogs) {
		this.fsubscriptionlogs = fsubscriptionlogs;
	}

	@Column(name = "fAlreadyByCount")
	public Double getfAlreadyByCount() {
		return fAlreadyByCount;
	}

	public void setfAlreadyByCount(Double fAlreadyByCount) {
		this.fAlreadyByCount = fAlreadyByCount;
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
		return SubscriptionTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	//
	// @Column(name = "fqty")
	// public Double getFqty() {
	// return fqty;
	// }
	//
	// public void setFqty(Double fqty) {
	// this.fqty = fqty;
	// }

	@Column(name = "fcontent")
	public String getFcontent() {
		return fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	@Column(name = "ftitle")
	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "fnumber")
	public Integer getFnumber() {
		return fnumber;
	}

	public void setFnumber(Integer fnumber) {
		this.fnumber = fnumber;
	}

	@Column(name = "fminbuyCount")
	public int getFminbuyCount() {
		return fminbuyCount;
	}

	public void setFminbuyCount(int fminbuyCount) {
		this.fminbuyCount = fminbuyCount;
	}

	//
	// @Column(name = "ftotalqty")
	// public double getFtotalqty() {
	// return ftotalqty;
	// }
	//
	// public void setFtotalqty(double ftotalqty) {
	// this.ftotalqty = ftotalqty;
	// }

	@Column(name = "fisICO")
	public boolean isFisICO() {
		return fisICO;
	}

	public void setFisICO(boolean fisICO) {
		this.fisICO = fisICO;
	}

	@Column(name = "ffrozenType")
	public int getFfrozenType() {
		return ffrozenType;
	}

	public void setFfrozenType(int ffrozenType) {
		this.ffrozenType = ffrozenType;
	}

	@Transient
	public String getFfrozenType_s() {
		return SubFrozenTypeEnum.getEnumString(this.getFfrozenType());
	}

	public void setFfrozenType_s(String ffrozenType_s) {
		this.ffrozenType_s = ffrozenType_s;
	}

	@Column(name = "frate")
	public double getFrate() {
		return frate;
	}

	public void setFrate(double frate) {
		this.frate = frate;
	}
	
	@Column(name = "fisstart")
	public boolean isFisstart() {
		return fisstart;
	}

	public void setFisstart(boolean fisstart) {
		this.fisstart = fisstart;
	}

	@Transient
	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}

	@Transient
	public String getSymbol() {
		return this.getFvirtualcointypeCost().getfShortName();
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Transient
	public String getSymbol1() {
		if (this.getFvirtualcointypeCost().getFtype() == CoinTypeEnum.FB_CNY_VALUE) {
			return "￥";
		}else if (this.getFvirtualcointypeCost().getFtype() == CoinTypeEnum.FB_USDT_VALUE) {
			return "$";
		} else {
			return this.getFvirtualcointypeCost().getfShortName();
		}
	}

	public void setSymbol1(String symbol1) {
		this.symbol1 = symbol1;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}