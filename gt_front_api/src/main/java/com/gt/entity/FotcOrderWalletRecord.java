package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Farticletype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "f_otc_order_wallet_record", catalog = "gtcoin")
public class FotcOrderWalletRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8927493130866875771L;
	// Fields

	private Integer id;// '主键(广告编号)',
	private Fvirtualcointype fvirtualcointype;// 虚拟币信息
	private Fuser user;// '商户编号',
	private int order_id ;// '订单id',
	private double reward_num;//
	private int change_type; // '变动类型(1:冻结,2:解冻,3:出账，4入账,5手工充值审核，6手工充值发放冻结)', 
	private String wallet_detil  ;//'钱包明细',
	private String remark ;// '备注',
	private int bdelete  ;// '是否删除(0:未删除  1:已删除)',
	private Timestamp create_time;// '创建时间',
	/** default constructor */
	public FotcOrderWalletRecord() {
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


	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public Fuser getUser() {
		return user;
	}

	public void setUser(Fuser user) {
		this.user = user;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reward_type")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}
	@Column(name = "order_id")
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	@Column(name = "reward_num")
	public double getReward_num() {
		return reward_num;
	}

	public void setReward_num(double reward_num) {
		this.reward_num = reward_num;
	}
	@Column(name = "change_type")
	public int getChange_type() {
		return change_type;
	}

	public void setChange_type(int change_type) {
		this.change_type = change_type;
	}
	@Column(name = "wallet_detil")
	public String getWallet_detil() {
		return wallet_detil;
	}

	public void setWallet_detil(String wallet_detil) {
		this.wallet_detil = wallet_detil;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "bdelete")
	public int getBdelete() {
		return bdelete;
	}

	public void setBdelete(int bdelete) {
		this.bdelete = bdelete;
	}
	@Column(name = "create_time")
	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

}