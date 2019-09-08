package com.gt.entity;

import java.sql.Timestamp;
import java.util.List;

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

/**
 * Farticletype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "f_otcadvertisement", catalog = "gtcoin")
public class FotcAdvertisement implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 880301025781371734L;
	// Fields

	private Integer id;// '主键(广告编号)',
	private Integer ad_type;// '广告类型：1.出售 2.求购',
	private Fvirtualcointype fvirtualcointype;// 虚拟币信息
	private Fuser user;// '商户编号',
	private double total_count;// '总数量(不变)',
	private double repertory_count;// '库存数量或者剩余求购数量',
	private double freeze_count;// '冻结数量：已求购的数量',
	private double order_limit_min;// '每笔订单最小限额',
	private double order_limit_max;// '每笔订单最大限额',
	private double price;// '单价',
	private Integer status;// '上下架：0.上架 1.下架',
	private String ad_desc;// '广告描述',
	private String remark; // '备注',
	private Timestamp create_time;// '创建时间',
	private Timestamp update_time;// '修改时间',
	private int version;       //版本号
	
	private String respTimeStr;
	List<FotcUserPaytype> paytypeList;  //支付方式
	
	private String onlineUserList;
	// Constructors

	/** default constructor */
	public FotcAdvertisement() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ad_type")
	public Integer getAd_type() {
		return ad_type;
	}

	public void setAd_type(Integer ad_type) {
		this.ad_type = ad_type;
	}

	@Column(name = "total_count")
	public double getTotal_count() {
		return total_count;
	}

	public void setTotal_count(double total_count) {
		this.total_count = total_count;
	}

	@Column(name = "repertory_count")
	public double getRepertory_count() {
		return repertory_count;
	}

	public void setRepertory_count(double repertory_count) {
		this.repertory_count = repertory_count;
	}

	@Column(name = "freeze_count")

	public double getFreeze_count() {
		return freeze_count;
	}

	public void setFreeze_count(double freeze_count) {
		this.freeze_count = freeze_count;
	}

	@Column(name = "order_limit_min")
	public double getOrder_limit_min() {
		return order_limit_min;
	}

	public void setOrder_limit_min(double order_limit_min) {
		this.order_limit_min = order_limit_min;
	}

	@Column(name = "order_limit_max")

	public double getOrder_limit_max() {
		return order_limit_max;
	}

	public void setOrder_limit_max(double order_limit_max) {
		this.order_limit_max = order_limit_max;
	}

	@Column(name = "price")

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "ad_desc")

	public String getAd_desc() {
		return ad_desc;
	}

	public void setAd_desc(String ad_desc) {
		this.ad_desc = ad_desc;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "create_time")
	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	@Column(name = "update_time")
	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public Fuser getUser() {
		return user;
	}

	public void setUser(Fuser user) {
		this.user = user;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "amount_type")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@Transient
	public List<FotcUserPaytype> getPaytypeList() {
		return paytypeList;
	}

	public void setPaytypeList(List<FotcUserPaytype> paytypeList) {
		this.paytypeList = paytypeList;
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
	public String getRespTimeStr() {
		return respTimeStr;
	}

	public void setRespTimeStr(String respTimeStr) {
		this.respTimeStr = respTimeStr;
	}
	@Transient
	public String getOnlineUserList() {
		return onlineUserList;
	}

	public void setOnlineUserList(String onlineUserList) {
		this.onlineUserList = onlineUserList;
	}

}