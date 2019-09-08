package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.OtcAppealStatusEnum;
import com.gt.Enum.OtcOrderStatusEnum;

/**
 * otc订单表
 * 
 * @author zhouyong
 * 
 */
@Entity
@Table(name = "f_otcorder", catalog = "gtcoin")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FotcOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 252370709021730887L;
	
	// 主键
	private Integer id;
	// 用户id
	private Fuser fuser;
	// 广告id
	private FotcAdvertisement fotcAdvertisement;
	// 虚拟币类型
	private Fvirtualcointype fvirtualcointype;
	// 订单类型（1：买入 2：卖出）
	private Integer orderType;
	// 单价（人名币）
	private double unitPrice;
	// 数量
	private double amount;
	// 总价（人名币）
	private double totalPrice;
	// 订单状态（101：未接单  102：待支付  103：已付款 104已确认收款 ;105:异常订单   106：失败 107：成功）',
	private Integer orderStatus;
	// 创建时间
	private Timestamp createTime;
	// 更新时间
	private Timestamp updateTime;
	// 备注
	private String remark;
	//机构订单id
	private String userOrderId;
	private int version;       //版本号
	private String pageUrl;              //页面回调地址
	private String serverUrl;            //服务端回调地址
	private String returnUrl;            //返回地址
	private double fee;            //手续费
	private Integer is_third;            //是否第三方订单
	private Integer overtime ;// '是否是超时订单(1001:否，1002：是)',
	private Integer    appeal_reason; //'申诉原因(1:买家申诉——已付款忘记点(我已付款)，2：买家申诉——已付款，卖家未确认，3：卖家申诉——买家未付款，点了已付款按钮，4：卖家申诉——尚未收到款错点确认收款)',
	private Integer    appeal_status; //'申诉状态(1001:未处理，1002：申诉失败，1003：申诉成功)',
	private Fuser  appeal_user; //'申诉人',
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
	/** 用户手机号 **/
	private String phone;
	/** 响应时间 **/
	private Long respTime;
	
	private Integer is_callback_success;
	
	private String orderStatusDesc;
	private String appeal_statusDesc;
	

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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ad_id")
	public FotcAdvertisement getFotcAdvertisement() {
		return fotcAdvertisement;
	}

	public void setFotcAdvertisement(FotcAdvertisement fotcAdvertisement) {
		this.fotcAdvertisement = fotcAdvertisement;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "coin_type")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@Column(name = "order_type")
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Column(name = "unit_price")
	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "amount")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "total_price")
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "order_status")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "user_orderId")
	public String getUserOrderId() {
		return userOrderId;
	}

	public void setUserOrderId(String userOrderId) {
		this.userOrderId = userOrderId;
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
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Transient
	public String getOrderStatusDesc() {
		return OtcOrderStatusEnum.getEnumString(orderStatus);
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(name = "page_url")
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	@Column(name = "server_url")
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@Column(name = "resp_time")
	public Long getRespTime() {
		return respTime;
	}

	public void setRespTime(Long respTime) {
		this.respTime = respTime;
	}
	
	@Column(name = "return_url")
	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Column(name = "fee")
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	@Column(name = "is_third")
	public Integer getIs_third() {
		return is_third;
	}

	public void setIs_third(Integer is_third) {
		this.is_third = is_third;
	}
	@Column(name = "overtime")
	public Integer getOvertime() {
		return overtime;
	}

	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}
	@Column(name = "appeal_reason")
	public Integer getAppeal_reason() {
		return appeal_reason;
	}

	public void setAppeal_reason(Integer appeal_reason) {
		this.appeal_reason = appeal_reason;
	}
	@Column(name = "appeal_status")
	public Integer getAppeal_status() {
		return appeal_status;
	}

	public void setAppeal_status(Integer appeal_status) {
		this.appeal_status = appeal_status;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "appeal_user_id")
	public Fuser getAppeal_user() {
		return appeal_user;
	}

	public void setAppeal_user(Fuser appeal_user) {
		this.appeal_user = appeal_user;
	}
	@Column(name = "is_callback_success")
	public Integer getIs_callback_success() {
		return is_callback_success;
	}

	public void setIs_callback_success(Integer is_callback_success) {
		this.is_callback_success = is_callback_success;
	}
	@Transient
	public String getAppeal_statusDesc() {
		return OtcAppealStatusEnum.getEnumString(appeal_status);
	}

	public void setAppeal_statusDesc(String appeal_statusDesc) {
		this.appeal_statusDesc = appeal_statusDesc;
	}
	
	
	
}
