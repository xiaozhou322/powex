package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.OtcPayTypeEnum;

/**
 * 
 * @author jqy
 *
 */
@Entity
@Table(name = "f_otcuserpaytype", catalog = "gtcoin")
public class FotcUserPaytype implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5766855569303553833L;
	
	/** 主键  **/
	private Integer id;
	/** 用户id  **/
	private Fuser fuser;
	/** 用户支付类型(1:网银，2:微信，3:支付宝)  **/
	private Integer payType;
	/** 真是姓名  **/
	private String realName;
	/** 账号  **/
	private String paymentAccount;
	/** 二维码  **/
	private String qrCode;
	/** 所属银行  **/
	private String bank;
	/** 所属银行支行  **/
	private String bankBranch;
	/** 备注  **/
	private String remark;
	/** 创建时间  **/
	private Timestamp createTime;
	/** 更新时间  **/
	private Timestamp updateTime;
	/** 启用禁用  **/
	private Integer status;
	private int version;       //版本号
	
	
	private String paytypename;
	
	public FotcUserPaytype() {
		super();
		// TODO Auto-generated constructor stub
	}
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public Fuser getFuser() {
		return fuser;
	}
	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}
	
	@Column(name = "pay_type")
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	@Column(name = "real_name")
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name = "payment_account")
	public String getPaymentAccount() {
		return paymentAccount;
	}
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}
	
	@Column(name = "qr_code")
	public String getQrCode() {
		return this.qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	@Column(name = "bank")
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@Column(name = "bank_branch")
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Transient
	public String getPaytypename() {
		return OtcPayTypeEnum.getDescription(payType);
	}
	
	public void setPaytypename(String paytypename) {
		this.paytypename = paytypename;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
