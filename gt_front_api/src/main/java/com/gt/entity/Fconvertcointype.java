package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;

/**
 * APP钱包兑换币种实体
 * @author zhouyong
 *
 */
@Entity
@Table(name = "fconvertcointype", catalog = "gtcoin")
public class Fconvertcointype implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8764378041550552445L;
	private int fid;
	private String fname;
	private String fShortName;
	private String fdescription;
	private Timestamp faddTime;
	private int fstatus;// VirtualCoinTypeStatusEnum
	private String fstatus_s;
	private int version;
	private String fSymbol;
	private String faccess_key;
	private String fsecrt_key;
	private String fip;
	private String fport;
	private String furl;
	private String fweburl;
	// 虚拟币类型。是否法币
	private int ftype;
	private String ftype_s;

	private boolean fisEth ;
	private String mainAddr ;
	private long startBlockId ;
	
	private boolean fispush;

	private boolean fisautosend;
	private String fpassword;

	private int fconfirm;
	private int faddressCount;
	
	private boolean fiserc20;
	private String fcontract;
	private int fdecimals;
	private String ftransfer;
	private String fbalance;
	
	private boolean fisBts; //是否为BTS核心钱包
	private String startTranId;//扫描的起始交易ID
	private Integer forder;//币种排序
	private String fclassPath; //引用类路径，为钱包统一标准化做准备
	private long parentCid;
	
	
	/** default constructor */
	public Fconvertcointype() {
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
	
	
}