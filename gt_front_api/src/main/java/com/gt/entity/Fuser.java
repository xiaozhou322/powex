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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.IdentityTypeEnum;
import com.gt.Enum.RegTypeEnum;
import com.gt.Enum.UserStatusEnum;

/**
 * Fuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fuser", catalog = "gtcoin")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fuser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6342112843675844665L;
	private int fid;
	private String floginName;
	private String floginPassword;
	private String ftradePassword;
	private String fnickName;
	private String frealName;
	private String fareaCode ;
	private String ftelephone;
	private boolean fisTelephoneBind;//是否成功绑定手机
	private String femail;
	private int fidentityType ;//IdentityTypeEnum
	private String fidentityType_s ;
	private String fidentityNo;
	private String fidentityNo_s;//身份證號碼加***
	private Timestamp fregisterTime;
	private Timestamp flastLoginTime;
	private Timestamp flastUpdateTime ;
	private String fgoogleAuthenticator;
	private String fgoogleurl ;
	private boolean fgoogleBind ;//谷歌验证是否绑定设备
	
	private String fmobilMessageCode;
	private int fstatus;
	private String fstatus_s;
	private boolean fisTelValidate;//手机认证
	private boolean fisMailValidate;//邮件认证
	private boolean fgoogleValidate;//谷歌验证
	
	private boolean fpostRealValidate ;//已经提交身份认证
	private boolean fhasRealValidate ;//通过身份认证
	private String fIdentityPath ;//身份证。
	private String fIdentityPath2 ;//身份证。反面
	private String fIdentityPath3 ;//身份证。反面
	private Timestamp fpostRealValidateTime ;
	private Timestamp fhasRealValidateTime ;
	
	private boolean fopenTelValidate ;
	private boolean fopenGoogleValidate ;
	private boolean fopenSecondValidate ;
	private Fscore fscore ;
	private Fapi fapi ;
	
	private Fuser fIntroUser_id ;
	
	private String qqlogin ;//qq登陆字符串
	
	private int fInvalidateIntroCount ;
	private Fusersetting fusersetting;
	
	private Set<Fvirtualaddress> fvirtualaddresses = new HashSet<Fvirtualaddress>(0) ;
	private Set<FvirtualaddressWithdraw> fvirtualaddressWithdraws = new HashSet<FvirtualaddressWithdraw>(0) ;
	private Set<Emailvalidate> emailvalidates = new HashSet<Emailvalidate>(0);
	private Set<Fvalidateemail> validateemailses = new HashSet<Fvalidateemail>(
			0);
	private Set<Fentrustplan> fentrustplans = new HashSet<Fentrustplan>(0);
	private Set<Fentrust> fentrusts = new HashSet<Fentrust>(0);
	private Set<Fcapitaloperation> fcapitaloperations = new HashSet<Fcapitaloperation>(
			0);
	private Set<Fvirtualcaptualoperation> fvirtualcaptualoperations = new HashSet<Fvirtualcaptualoperation>(
			0);
	private Set<Fbankinfo> fbankinfos = new HashSet<Fbankinfo>(0);
	private Set<FbankinfoWithdraw> fbankinfoWithdraws = new HashSet<FbankinfoWithdraw>(0);
	private Set<Fvirtualwallet> fvirtualwallets = new HashSet<Fvirtualwallet>(0) ;
//	private Set<Fagentinfo> fagentinfos = new HashSet<Fagentinfo>(0) ;
	private int version ;
	private int fregType;//注册类型。1手机，0邮箱
	private String fregtype_s;
	private String fuserNo;
	private String fintrolUserNo;
	private boolean fisValid;
	private boolean fistiger;
	private String fregIp;
	private String flastLoginIp;
	private String salt;
	
	
	private boolean fpostImgValidate;
	private boolean fhasImgValidate;
	private Timestamp fhasImgValidateTime ;
	private Timestamp fpostImgValidateTime ;
	
	private boolean fisprojecter;
	private String fregfrom;
	
	private boolean fismerchant;     //是否为承兑商
	private boolean fisorganization;    //是否为机构商
	
	private Integer fgrade;           //是否拥有app权限      0：无     1：有
	
	// Constructors




	/** default constructor */
	public Fuser() {
	}

	/** full constructor */
	public Fuser(Fvirtualwallet fvirtualwallet,
			String floginName, String floginPassword, String ftradePassword,
			String fnickName, String frealName, String ftelephone,
			String femail, String fidentityNo, Timestamp fregisterTime,
			Timestamp flastLoginTime, String fgoogleAuthenticator,
			String fmobilMessageCode, String fqqVerifyCode,
			String fweiboVerifyCode, int fstatus, boolean fisTelValidate,
			boolean fisMailValidate, boolean fgoogleValidate,
			Set<Emailvalidate> emailvalidates,
			Set<Fvalidateemail> validateemailses, Set<Flog> flogs,
			Set<Fentrustplan> fentrustplans, Set<Fentrust> fentrusts,
			Set<Fcapitaloperation> fcapitaloperations,
			Set<Fvirtualcaptualoperation> fvirtualcaptualoperations,
			Set<Fbankinfo> fbankinfos/*,
			Set<Fagentinfo> fagentinfos*/) {
		this.floginName = floginName;
		this.floginPassword = floginPassword;
		this.ftradePassword = ftradePassword;
		this.fnickName = fnickName;
		this.frealName = frealName;
		this.ftelephone = ftelephone;
		this.femail = femail;
		this.fidentityNo = fidentityNo;
		this.fregisterTime = fregisterTime;
		this.flastLoginTime = flastLoginTime;
		this.fgoogleAuthenticator = fgoogleAuthenticator;
		this.fmobilMessageCode = fmobilMessageCode;
		this.fstatus = fstatus;
		this.fisTelValidate = fisTelValidate;
		this.fisMailValidate = fisMailValidate;
		this.fgoogleValidate = fgoogleValidate;
		this.emailvalidates = emailvalidates;
		this.validateemailses = validateemailses;
		this.fentrustplans = fentrustplans;
		this.fentrusts = fentrusts;
		this.fcapitaloperations = fcapitaloperations;
		this.fvirtualcaptualoperations = fvirtualcaptualoperations;
		this.fbankinfos = fbankinfos;
//		this.fagentinfos = fagentinfos;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fId", unique = true, nullable = false)
	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Column(name = "floginName", length = 32)
	public String getFloginName() {
		return this.floginName;
	}

	public void setFloginName(String floginName) {
		this.floginName = floginName;
	}

	@Column(name = "fLoginPassword", length = 32)
	public String getFloginPassword() {
		return this.floginPassword;
	}

	public void setFloginPassword(String floginPassword) {
		this.floginPassword = floginPassword;
	}

	@Column(name = "fTradePassword", length = 32)
	public String getFtradePassword() {
		return this.ftradePassword;
	}

	public void setFtradePassword(String ftradePassword) {
		this.ftradePassword = ftradePassword;
	}

	@Column(name = "fNickName", length = 32)
	public String getFnickName() {
		return this.fnickName;
	}

	public void setFnickName(String fnickName) {
		this.fnickName = fnickName;
	}

	@Column(name = "fRealName", length = 32)
	public String getFrealName() {
		return this.frealName;
	}

	public void setFrealName(String frealName) {
		this.frealName = frealName;
	}

	@Column(name = "fTelephone", length = 32)
	public String getFtelephone() {
		return this.ftelephone;
	}

	public void setFtelephone(String ftelephone) {
		this.ftelephone = ftelephone;
	}

	@Column(name = "fEmail", length = 128)
	public String getFemail() {
		return this.femail;
	}

	public void setFemail(String femail) {
		this.femail = femail;
	}

	@Column(name = "fIdentityNo", length = 128)
	public String getFidentityNo() {
		return this.fidentityNo;
	}

	public void setFidentityNo(String fidentityNo) {
		this.fidentityNo = fidentityNo;
	}

	@Column(name = "fRegisterTime", length = 0)
	public Timestamp getFregisterTime() {
		return this.fregisterTime;
	}

	public void setFregisterTime(Timestamp fregisterTime) {
		this.fregisterTime = fregisterTime;
	}

	@Column(name = "fLastLoginTime", length = 0)
	public Timestamp getFlastLoginTime() {
		return this.flastLoginTime;
	}

	public void setFlastLoginTime(Timestamp flastLoginTime) {
		this.flastLoginTime = flastLoginTime;
	}

	@Column(name = "fGoogleAuthenticator", length = 128)
	public String getFgoogleAuthenticator() {
		return this.fgoogleAuthenticator;
	}

	public void setFgoogleAuthenticator(String fgoogleAuthenticator) {
		this.fgoogleAuthenticator = fgoogleAuthenticator;
	}

	
	@Column(name = "fgoogleBind")
	public boolean getFgoogleBind() {
		return fgoogleBind;
	}

	public void setFgoogleBind(boolean fgoogleBind) {
		this.fgoogleBind = fgoogleBind;
	}

	@Column(name = "fMobilMessageCode", length = 128)
	public String getFmobilMessageCode() {
		return this.fmobilMessageCode;
	}

	public void setFmobilMessageCode(String fmobilMessageCode) {
		this.fmobilMessageCode = fmobilMessageCode;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Transient
	public String getFstatus_s() {
		int status = getFstatus() ;
		return UserStatusEnum.getEnumString(status) ;
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name = "fIsTelValidate")
	public boolean getFisTelValidate() {
		return this.fisTelValidate;
	}

	public void setFisTelValidate(boolean fisTelValidate) {
		this.fisTelValidate = fisTelValidate;
	}

	@Column(name = "fIsMailValidate")
	public boolean getFisMailValidate() {
		return this.fisMailValidate;
	}

	public void setFisMailValidate(boolean fisMailValidate) {
		this.fisMailValidate = fisMailValidate;
	}

	@Column(name = "fGoogleValidate")
	public boolean getFgoogleValidate() {
		return this.fgoogleValidate;
	}

	public void setFgoogleValidate(boolean fgoogleValidate) {
		this.fgoogleValidate = fgoogleValidate;
	}

	
	@Column(name = "fopenTelValidate")
	public boolean getFopenTelValidate() {
		return fopenTelValidate;
	}

	public void setFopenTelValidate(boolean fopenTelValidate) {
		this.fopenTelValidate = fopenTelValidate;
	}
	@Column(name = "fopenGoogleValidate")
	public boolean getFopenGoogleValidate() {
		return fopenGoogleValidate;
	}

	public void setFopenGoogleValidate(boolean fopenGoogleValidate) {
		this.fopenGoogleValidate = fopenGoogleValidate;
	}
	@Column(name = "fopenSecondValidate")
	public boolean getFopenSecondValidate() {
		return fopenSecondValidate;
	}

	public void setFopenSecondValidate(boolean fopenSecondValidate) {
		this.fopenSecondValidate = fopenSecondValidate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Emailvalidate> getEmailvalidates() {
		return this.emailvalidates;
	}

	public void setEmailvalidates(Set<Emailvalidate> emailvalidates) {
		this.emailvalidates = emailvalidates;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fvalidateemail> getValidateemailses() {
		return this.validateemailses;
	}

	public void setValidateemailses(Set<Fvalidateemail> validateemailses) {
		this.validateemailses = validateemailses;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fvirtualaddress> getFvirtualaddresses() {
		return fvirtualaddresses;
	}

	public void setFvirtualaddresses(Set<Fvirtualaddress> fvirtualaddresses) {
		this.fvirtualaddresses = fvirtualaddresses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<FvirtualaddressWithdraw> getFvirtualaddressWithdraws() {
		return fvirtualaddressWithdraws;
	}

	public void setFvirtualaddressWithdraws(
			Set<FvirtualaddressWithdraw> fvirtualaddressWithdraws) {
		this.fvirtualaddressWithdraws = fvirtualaddressWithdraws;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fentrustplan> getFentrustplans() {
		return this.fentrustplans;
	}

	public void setFentrustplans(Set<Fentrustplan> fentrustplans) {
		this.fentrustplans = fentrustplans;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fentrust> getFentrusts() {
		return this.fentrusts;
	}

	public void setFentrusts(Set<Fentrust> fentrusts) {
		this.fentrusts = fentrusts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fcapitaloperation> getFcapitaloperations() {
		return this.fcapitaloperations;
	}

	public void setFcapitaloperations(Set<Fcapitaloperation> fcapitaloperations) {
		this.fcapitaloperations = fcapitaloperations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fvirtualcaptualoperation> getFvirtualcaptualoperations() {
		return this.fvirtualcaptualoperations;
	}

	public void setFvirtualcaptualoperations(
			Set<Fvirtualcaptualoperation> fvirtualcaptualoperations) {
		this.fvirtualcaptualoperations = fvirtualcaptualoperations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fbankinfo> getFbankinfos() {
		return this.fbankinfos;
	}

	public void setFbankinfos(Set<Fbankinfo> fbankinfos) {
		this.fbankinfos = fbankinfos;
	}

	@Column(name = "fpostRealValidate")
	public boolean getFpostRealValidate() {
		return fpostRealValidate;
	}

	public void setFpostRealValidate(boolean fpostRealValidate) {
		this.fpostRealValidate = fpostRealValidate;
	}

	@Column(name = "fhasRealValidate")
	public boolean getFhasRealValidate() {
		return fhasRealValidate;
	}

	public void setFhasRealValidate(boolean fhasRealValidate) {
		this.fhasRealValidate = fhasRealValidate;
	}

	@Column(name = "fpostRealValidateTime")
	public Timestamp getFpostRealValidateTime() {
		return fpostRealValidateTime;
	}

	public void setFpostRealValidateTime(Timestamp fpostRealValidateTime) {
		this.fpostRealValidateTime = fpostRealValidateTime;
	}

	@Column(name = "fhasRealValidateTime")
	public Timestamp getFhasRealValidateTime() {
		return fhasRealValidateTime;
	}

	public void setFhasRealValidateTime(Timestamp fhasRealValidateTime) {
		this.fhasRealValidateTime = fhasRealValidateTime;
	}

	@Column(name="fidentityType")
	public int getFidentityType() {
		return fidentityType;
	}

	public void setFidentityType(int fidentityType) {
		this.fidentityType = fidentityType;
	}

	@Transient
	public String getFidentityType_s() {
		int type = getFidentityType() ;
		return IdentityTypeEnum.getEnumString(type) ;
	}

	public void setFidentityType_s(String fidentityType_s) {
		this.fidentityType_s = fidentityType_s;
	}

	@Column(name = "fisTelephoneBind")
	public boolean isFisTelephoneBind() {
		return fisTelephoneBind;
	}

	public void setFisTelephoneBind(boolean fisTelephoneBind) {
		this.fisTelephoneBind = fisTelephoneBind;
	}

	@Column(name="flastUpdateTime")
	public Timestamp getFlastUpdateTime() {
		return flastUpdateTime;
	}

	public void setFlastUpdateTime(Timestamp flastUpdateTime) {
		this.flastUpdateTime = flastUpdateTime;
	}

	@Column(name="fgoogleurl")
	public String getFgoogleurl() {
		return fgoogleurl;
	}

	public void setFgoogleurl(String fgoogleurl) {
		this.fgoogleurl = fgoogleurl;
	}

	@Column(name="fareaCode")
	public String getFareaCode() {
		return fareaCode;
	}

	public void setFareaCode(String fareaCode) {
		this.fareaCode = fareaCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fscoreid")
	public Fscore getFscore() {
		return fscore;
	}

	public void setFscore(Fscore fscore) {
		this.fscore = fscore;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<Fvirtualwallet> getFvirtualwallets() {
		return fvirtualwallets;
	}

	public void setFvirtualwallets(Set<Fvirtualwallet> fvirtualwallets) {
		this.fvirtualwallets = fvirtualwallets;
	}

	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name="fIdentityPath")
	public String getfIdentityPath() {
		return fIdentityPath;
	}

	public void setfIdentityPath(String fIdentityPath) {
		this.fIdentityPath = fIdentityPath;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fapi")
	public Fapi getFapi() {
		return fapi;
	}

	public void setFapi(Fapi fapi) {
		this.fapi = fapi;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuser")
	public Set<FbankinfoWithdraw> getFbankinfoWithdraws() {
		return fbankinfoWithdraws;
	}

	public void setFbankinfoWithdraws(Set<FbankinfoWithdraw> fbankinfoWithdraws) {
		this.fbankinfoWithdraws = fbankinfoWithdraws;
	}

	@Column(name="fIdentityPath2")
	public String getfIdentityPath2() {
		return fIdentityPath2;
	}

	public void setfIdentityPath2(String fIdentityPath2) {
		this.fIdentityPath2 = fIdentityPath2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fIntroUser_id")
	public Fuser getfIntroUser_id() {
		return fIntroUser_id;
	}

	public void setfIntroUser_id(Fuser fIntroUser_id) {
		this.fIntroUser_id = fIntroUser_id;
	}

	@Column(name="qqlogin")
	public String getQqlogin() {
		return qqlogin;
	}

	public void setQqlogin(String qqlogin) {
		this.qqlogin = qqlogin;
	}
	
	@Column(name="fInvalidateIntroCount")
	public int getfInvalidateIntroCount() {
		return fInvalidateIntroCount;
	}

	public void setfInvalidateIntroCount(int fInvalidateIntroCount) {
		this.fInvalidateIntroCount = fInvalidateIntroCount;
	}
	
	@Column(name="fregType")
	public int getFregType() {
		return fregType;
	}

	public void setFregType(int fregType) {
		this.fregType = fregType;
	}
	
	@Column(name="fuserNo")
	public String getFuserNo() {
		return fuserNo;
	}

	public void setFuserNo(String fuserNo) {
		this.fuserNo = fuserNo;
	}
	
	public int effectiveIntroUser(){
		return getfInvalidateIntroCount() ;
	}
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "fuser")
	public Fusersetting getFusersetting() {
		return this.fusersetting;
	}

	public void setFusersetting(Fusersetting fusersetting) {
		this.fusersetting = fusersetting;
	}
	
	@Column(name="fisValid")
	public boolean isFisValid() {
		return fisValid;
	}

	public void setFisValid(boolean fisValid) {
		this.fisValid = fisValid;
	}
	
	@Column(name="fistiger")
	public boolean isFistiger() {
		return fistiger;
	}

	public void setFistiger(boolean fistiger) {
		this.fistiger = fistiger;
	}
	
	@Column(name="fregIp")
	public String getFregIp() {
		return fregIp;
	}

	public void setFregIp(String fregIp) {
		this.fregIp = fregIp;
	}

	@Column(name="flastLoginIp")
	public String getFlastLoginIp() {
		return flastLoginIp;
	}

	public void setFlastLoginIp(String flastLoginIp) {
		this.flastLoginIp = flastLoginIp;
	}
	
	@Column(name="fintrolUserNo")
	public String getFintrolUserNo() {
		return fintrolUserNo;
	}

	public void setFintrolUserNo(String fintrolUserNo) {
		this.fintrolUserNo = fintrolUserNo;
	}

	

	@Column(name="salt")
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@Column(name="fpostImgValidate")
	public boolean isFpostImgValidate() {
		return fpostImgValidate;
	}

	public void setFpostImgValidate(boolean fpostImgValidate) {
		this.fpostImgValidate = fpostImgValidate;
	}

	@Column(name="fhasImgValidate")
	public boolean isFhasImgValidate() {
		return fhasImgValidate;
	}

	public void setFhasImgValidate(boolean fhasImgValidate) {
		this.fhasImgValidate = fhasImgValidate;
	}

	@Column(name="fhasImgValidateTime")
	public Timestamp getFhasImgValidateTime() {
		return fhasImgValidateTime;
	}

	public void setFhasImgValidateTime(Timestamp fhasImgValidateTime) {
		this.fhasImgValidateTime = fhasImgValidateTime;
	}

	@Column(name="fpostImgValidateTime")
	public Timestamp getFpostImgValidateTime() {
		return fpostImgValidateTime;
	}

	public void setFpostImgValidateTime(Timestamp fpostImgValidateTime) {
		this.fpostImgValidateTime = fpostImgValidateTime;
	}
	
	@Column(name="fIdentityPath3")
	public String getfIdentityPath3() {
		return fIdentityPath3;
	}

	public void setfIdentityPath3(String fIdentityPath3) {
		this.fIdentityPath3 = fIdentityPath3;
	}


	
	@Transient
	public String getFregtype_s() {
		return RegTypeEnum.getEnumString(this.getFregType());
	}

	public void setFregtype_s(String fregtype_s) {
		this.fregtype_s = fregtype_s;
	}

	@Transient
	public String getFidentityNo_s() {
		String no = getFidentityNo() ;
		if(no==null || no.length()<8){
			return no ;
		}
		
		return no.substring(0,4)+"************"+no.substring(no.length()-4,no.length()) ;
	}

	public void setFidentityNo_s(String fidentityNo_s) {
		this.fidentityNo_s = fidentityNo_s;
	}

	@Column(name="fisprojecter")
	public boolean isFisprojecter() {
		return fisprojecter;
	}

	public void setFisprojecter(boolean fisprojecter) {
		this.fisprojecter = fisprojecter;
	}

	@Column(name="fregfrom")
	public String getFregfrom() {
		return fregfrom;
	}

	public void setFregfrom(String fregfrom) {
		this.fregfrom = fregfrom;
	}

	@Column(name="fismerchant")
	public boolean isFismerchant() {
		return fismerchant;
	}

	public void setFismerchant(boolean fismerchant) {
		this.fismerchant = fismerchant;
	}

	@Column(name="fisorganization")
	public boolean isFisorganization() {
		return fisorganization;
	}

	public void setFisorganization(boolean fisorganization) {
		this.fisorganization = fisorganization;
	}

	@Column(name="fgrade")
	public Integer getFgrade() {
		return fgrade;
	}

	public void setFgrade(Integer fgrade) {
		this.fgrade = fgrade;
	}


}