package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;

/**
 * Fvirtualcointype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvirtualcointype", catalog = "gtcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvirtualcointype implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6681732408139344670L;
	private int fid;
	private int fid_s;
	// private boolean fisShare;// 是否可以交易
	private boolean FIsWithDraw;// 是否可以充值提现
	private String fname;
	private String fShortName;
	private String fdescription;
	private Timestamp faddTime;
	private int fstatus;// VirtualCoinTypeStatusEnum
	private String fstatus_s;
	private String fSymbol;
	private String faccess_key;
	private String fsecrt_key;
	private String fip;
	private String fport;
	private double lastDealPrize;// fake,最新成交价格
	private double higestBuyPrize;
	private double lowestSellPrize;
	private boolean canLend;// 是否可以预售
//	private Set<Fvirtualcaptualoperation> fvirtualcaptualoperations = new HashSet<Fvirtualcaptualoperation>(
//			0);
//	private Set<Fvirtualwallet> fvirtualwallets = new HashSet<Fvirtualwallet>(0);
	private int version;
	private String furl;

	private String fweburl;

	// private int fcount;
	// private String ftradetime;
	// private double fprice;
	private Double total;

	// 虚拟币类型。是否法币
	private int ftype;
	private String ftype_s;

	private boolean fisauto;
	private boolean fisrecharge;
	private Double fmaxqty;
	private Double fminqty;
	
	private boolean fisEth ;
	private boolean fisEtp ;
	private String mainAddr ;
	private long startBlockId ;
	
	
	private boolean fispush;
	private double fpushMinQty;	//字段借用做最小充值币数
	private double fpushMaxQty;
	private double fpushMinPrice;
	private double fpushMaxPrice;
	private double fpushRate;

	private boolean fisautosend;
	private String fpassword;

	private int fconfirm;
	private int faddressCount;
	
	private boolean fiserc20;
	private String fcontract;
	private String ftransfer;
	private String fbalance;
	private int fdecimals;
	
	private boolean fislocked; //是否支持锁仓增值
	
	private boolean fisBts; //是否为BTS核心钱包
	private String startTranId;//扫描的起始交易ID
	private Integer forder;//币种排序
	private boolean fisTransfer; //是否允许转账
	private String fclassPath; //引用类路径，为钱包统一标准化做准备
	private long parentCid;
	
	private Integer fprojectId;  //项目方id
	private String fblockAddr;  //区块浏览器地址
	private String ftags;  //标签
	
	private boolean fisotc;// 是否可以进行OTC交易
	
	private boolean fcanConvert;// 是否可以兑换
	
	/** default constructor */
	public Fvirtualcointype() {
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

	@Column(name = "fName", length = 32)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fDescription", length = 32)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fAddTime", length = 0)
	public Timestamp getFaddTime() {
		return this.faddTime;
	}

	public void setFaddTime(Timestamp faddTime) {
		this.faddTime = faddTime;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fvirtualcointype")
//	public Set<Fvirtualcaptualoperation> getFvirtualcaptualoperations() {
//		return this.fvirtualcaptualoperations;
//	}
//
//	public void setFvirtualcaptualoperations(
//			Set<Fvirtualcaptualoperation> fvirtualcaptualoperations) {
//		this.fvirtualcaptualoperations = fvirtualcaptualoperations;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fvirtualcointype")
//	public Set<Fvirtualwallet> getFvirtualwallets() {
//		return this.fvirtualwallets;
//	}
//
//	public void setFvirtualwallets(Set<Fvirtualwallet> fvirtualwallets) {
//		this.fvirtualwallets = fvirtualwallets;
//	}

	@Column(name = "fstatus")
	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fShortName")
	public String getfShortName() {
		return fShortName;
	}

	public void setfShortName(String fShortName) {
		this.fShortName = fShortName;
	}

	@Transient
	public String getFstatus_s() {
		return VirtualCoinTypeStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "fSymbol")
	public String getfSymbol() {
		return fSymbol;
	}

	public void setfSymbol(String fSymbol) {
		this.fSymbol = fSymbol;
	}

	@Column(name = "faccess_key")
	public String getFaccess_key() {
		return faccess_key;
	}

	public void setFaccess_key(String faccess_key) {
		this.faccess_key = faccess_key;
	}

	@Column(name = "fsecrt_key")
	public String getFsecrt_key() {
		return fsecrt_key;
	}

	public void setFsecrt_key(String fsecrt_key) {
		this.fsecrt_key = fsecrt_key;
	}

	@Column(name = "fip")
	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	@Column(name = "fport")
	public String getFport() {
		return fport;
	}

	public void setFport(String fport) {
		this.fport = fport;
	}

	@Transient
	public double getLastDealPrize() {
		return lastDealPrize;
	}

	public void setLastDealPrize(double lastDealPrize) {
		this.lastDealPrize = lastDealPrize;
	}

	@Transient
	public double getHigestBuyPrize() {
		return higestBuyPrize;
	}

	public void setHigestBuyPrize(double higestBuyPrize) {
		this.higestBuyPrize = higestBuyPrize;
	}

	@Transient
	public double getLowestSellPrize() {
		return lowestSellPrize;
	}

	public void setLowestSellPrize(double lowestSellPrize) {
		this.lowestSellPrize = lowestSellPrize;
	}

	@Transient
	public String getFid_s() {
		Integer id = this.getFid();
		if (id != null) {
			return String.valueOf(id);
		}
		return String.valueOf(0);
	}

	public void setFid_s(int fid_s) {
		this.fid_s = fid_s;
	}

	@Column(name = "FIsWithDraw")
	public boolean isFIsWithDraw() {
		return FIsWithDraw;
	}

	public void setFIsWithDraw(boolean fIsWithDraw) {
		FIsWithDraw = fIsWithDraw;
	}

	@Column(name = "furl")
	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	@Column(name = "fweburl")
	public String getFweburl() {
		return fweburl;
	}

	public void setFweburl(String fweburl) {
		this.fweburl = fweburl;
	}

	@Column(name = "ftype")
	public int getFtype() {
		return ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fisauto")
	public boolean isFisauto() {
		return fisauto;
	}

	public void setFisauto(boolean fisauto) {
		this.fisauto = fisauto;
	}

	@Column(name = "fisrecharge")
	public boolean isFisrecharge() {
		return fisrecharge;
	}

	public void setFisrecharge(boolean fisrecharge) {
		this.fisrecharge = fisrecharge;
	}

	@Transient
	public String getFtype_s() {
		return CoinTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Transient
	public boolean isCanLend() {
		return true;
	}

	public void setCanLend(boolean canLend) {
		this.canLend = canLend;
	}

	@Column(name = "fmaxqty")
	public Double getFmaxqty() {
		return fmaxqty;
	}

	public void setFmaxqty(Double fmaxqty) {
		this.fmaxqty = fmaxqty;
	}

	@Column(name = "fminqty")
	public Double getFminqty() {
		return fminqty;
	}

	public void setFminqty(Double fminqty) {
		this.fminqty = fminqty;
	}

	@Transient
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	

	@Column(name="mainAddr")
	public String getMainAddr() {
		return mainAddr;
	}

	public void setMainAddr(String mainAddr) {
		this.mainAddr = mainAddr;
	}
	@Column(name="isEth")
	public boolean isFisEth() {
		return fisEth;
	}

	public void setFisEth(boolean fisEth) {
		this.fisEth = fisEth;
	}

	@Column(name="startBlockId")
	public long getStartBlockId() {
		return startBlockId;
	}

	public void setStartBlockId(long startBlockId) {
		this.startBlockId = startBlockId;
	}

	@Column(name="fispush")
	public boolean isFispush() {
		return fispush;
	}

	public void setFispush(boolean fispush) {
		this.fispush = fispush;
	}

	@Column(name="fpushMinQty")
	public double getFpushMinQty() {
		return fpushMinQty;
	}

	public void setFpushMinQty(double fpushMinQty) {
		this.fpushMinQty = fpushMinQty;
	}

	@Column(name="fpushMaxQty")
	public double getFpushMaxQty() {
		return fpushMaxQty;
	}

	public void setFpushMaxQty(double fpushMaxQty) {
		this.fpushMaxQty = fpushMaxQty;
	}

	@Column(name="fpushMinPrice")
	public double getFpushMinPrice() {
		return fpushMinPrice;
	}

	public void setFpushMinPrice(double fpushMinPrice) {
		this.fpushMinPrice = fpushMinPrice;
	}

	@Column(name="fpushMaxPrice")
	public double getFpushMaxPrice() {
		return fpushMaxPrice;
	}

	public void setFpushMaxPrice(double fpushMaxPrice) {
		this.fpushMaxPrice = fpushMaxPrice;
	}

	@Column(name="fpushRate")
	public double getFpushRate() {
		return fpushRate;
	}

	public void setFpushRate(double fpushRate) {
		this.fpushRate = fpushRate;
	}
	
	@Column(name="fisEtp")
	public boolean isFisEtp() {
		return fisEtp;
	}

	public void setFisEtp(boolean fisEtp) {
		this.fisEtp = fisEtp;
	}

	
	@Column(name="fisautosend")
	public boolean isFisautosend() {
		return fisautosend;
	}

	public void setFisautosend(boolean fisautosend) {
		this.fisautosend = fisautosend;
	}

	@Column(name="fpassword")
	public String getFpassword() {
		return fpassword;
	}

	public void setFpassword(String fpassword) {
		this.fpassword = fpassword;
	}
	
	@Column(name="fconfirm")
	public int getFconfirm() {
		return fconfirm;
	}

	public void setFconfirm(int fconfirm) {
		this.fconfirm = fconfirm;
	}

	@Column(name="faddressCount")
	public int getFaddressCount() {
		return faddressCount;
	}

	public void setFaddressCount(int faddressCount) {
		this.faddressCount = faddressCount;
	}
	
	@Column(name="fiserc20")
	public boolean isFiserc20() {
		return fiserc20;
	}

	public void setFiserc20(boolean fiserc20) {
		this.fiserc20 = fiserc20;
	}
	
	@Column(name="fcontract")
	public String getFcontract() {
		return fcontract;
	}

	public void setFcontract(String fcontract) {
		this.fcontract = fcontract;
	}
	
	@Column(name="ftransfer")
	public String getFtransfer() {
		return ftransfer;
	}

	public void setFtransfer(String ftransfer) {
		this.ftransfer = ftransfer;
	}
	
	@Column(name="fbalance")
	public String getFbalance() {
		return fbalance;
	}

	public void setFbalance(String fbalance) {
		this.fbalance = fbalance;
	}
	
	@Column(name="fdecimals")
	public int getFdecimals() {
		return fdecimals;
	}

	public void setFdecimals(int fdecimals) {
		this.fdecimals = fdecimals;
	}
	
	@Column(name="fisLocked")
	public boolean isFislocked() {
		return fislocked;
	}

	public void setFislocked(boolean fislocked) {
		this.fislocked = fislocked;
	}

	@Column(name="fisBts")
	public boolean isFisBts() {
		return fisBts;
	}

	public void setFisBts(boolean fisBts) {
		this.fisBts = fisBts;
	}

	@Column(name="startTranId")
	public String getStartTranId() {
		return startTranId;
	}

	public void setStartTranId(String startTranId) {
		this.startTranId = startTranId;
	}
	
	@Column(name="forder")
	public Integer getForder() {
		return forder;
	}

	public void setForder(Integer forder) {
		this.forder = forder;
	}
	
	@Column(name="fistransfer")
	public boolean isFisTransfer() {
		return fisTransfer;
	}

	public void setFisTransfer(boolean fisTransfer) {
		this.fisTransfer = fisTransfer;
	}

	@Column(name="fclasspath")
	public String getFclassPath() {
		return fclassPath;
	}

	public void setFclassPath(String fclassPath) {
		this.fclassPath = fclassPath;
	}

	@Column(name="fparentCid")
	public long getParentCid() {
		return parentCid;
	}

	public void setParentCid(long parentCid) {
		this.parentCid = parentCid;
	}
	
	@Column(name="fprojectId")
	public Integer getFprojectId() {
		return fprojectId;
	}

	public void setFprojectId(Integer fprojectId) {
		this.fprojectId = fprojectId;
	}
	@Column(name="fblockAddr")
	public String getFblockAddr() {
		return fblockAddr;
	}

	public void setFblockAddr(String fblockAddr) {
		this.fblockAddr = fblockAddr;
	}
	@Column(name="ftags")
	public String getFtags() {
		return ftags;
	}

	public void setFtags(String ftags) {
		this.ftags = ftags;
	}
	@Column(name="fisotc")
	public boolean isFisotc() {
		return fisotc;
	}

	public void setFisotc(boolean fisotc) {
		this.fisotc = fisotc;
	}

	@Transient
	public boolean isFcanConvert() {
		return fcanConvert;
	}

	public void setFcanConvert(boolean fcanConvert) {
		this.fcanConvert = fcanConvert;
	}
	
}