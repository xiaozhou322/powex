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
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * 
 * BFSC数据统计
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Entity
@Table(name = "fbfsc_data_statistics", catalog = "gtcoin")
public class FbfscDataStatistics implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7297204751186109685L;
	//id
	private Integer id;
	//项目方id
	private Fuser fuser;
	//统计时间
	private String statistical_date;
	//充币数量
	private double charge_amount;
	//提币数量
	private double withdraw_amount;
	//充币笔数
	private double charge_num;
	//提币笔数
	private double withdraw_num;
	//OTC购买USDT数量
	private double otc_buy_usdt_amount;
	//OTC卖出USDT数量
	private double otc_sell_usdt_amount;
	//OTC购买金额（人名币）
	private double otc_buy_amount;
	//OTC出售金额（人名币）
	private double otc_sell_amount;
	//OTC购买笔数
	private double otc_buy_num;
	//OTC出售笔数
	private double otc_sell_num;
	//USDT转入数量
	private double transfer_usdt_in_amount;
	//USDT转入笔数
	private double transfer_usdt_in_num;
	//BFSC转入数量
	private double transfer_bfsc_in_amount;
	//BFSC转出数量
	private double transfer_bfsc_out_amount;
	//BFSC存量数
	private double bfsc_stock_amount;
	//BFSC/USDT买入数量
	private double market_buy_qty;
	//BFSC/USDT卖出数量
	private double market_sell_qty;
	//BFSC/USDT买入金额
	private double market_buy_amount;
	//BFSC/USDT卖出金额
	private double market_sell_amount;
	//BFSC/USDT买入手续费
	private double market_buy_fees;
	//BFSC/USDT卖出手续费
	private double market_sell_fees;
	//BFSC/USDT总买入数量
	private double market_buy_total_qty;
	//BFSC/USDT总卖出数量
	private double market_sell_total_qty;
	//BFSC/USDT总买入金额
	private double market_buy_total_amount;
	//BFSC/USDT总卖出金额
	private double market_sell_total_amount;
	//手续费交割BFSC数量
	private double bfsc_fees_amount;
	//BFSC/USDT均价
	private double bfsc_avg_price;
	//待交割USDT数量
	private double usdt_fees_amount;
	//总注册人数
	private Integer register_total_num;
	//新增注册人数
	private Integer register_add_num;
	//版本
	private Integer version;
	//创建时间
	private Timestamp createTime;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public Fuser getFuser() {
		return fuser;
	}
	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}
	
	@Column(name = "statistical_date")
	public String getStatistical_date() {
		return statistical_date;
	}
	public void setStatistical_date(String statistical_date) {
		this.statistical_date = statistical_date;
	}
	
	
	@Column(name = "charge_amount")
	public double getCharge_amount() {
		return charge_amount;
	}
	
	public void setCharge_amount(double charge_amount) {
		this.charge_amount = charge_amount;
	}
	
	@Column(name = "withdraw_amount")
	public double getWithdraw_amount() {
		return withdraw_amount;
	}
	
	public void setWithdraw_amount(double withdraw_amount) {
		this.withdraw_amount = withdraw_amount;
	}
	
	@Column(name = "charge_num")
	public double getCharge_num() {
		return charge_num;
	}
	
	public void setCharge_num(double charge_num) {
		this.charge_num = charge_num;
	}
	
	@Column(name = "withdraw_num")
	public double getWithdraw_num() {
		return withdraw_num;
	}
	
	public void setWithdraw_num(double withdraw_num) {
		this.withdraw_num = withdraw_num;
	}
	
	@Column(name = "otc_buy_usdt_amount")
	public double getOtc_buy_usdt_amount() {
		return otc_buy_usdt_amount;
	}
	
	public void setOtc_buy_usdt_amount(double otc_buy_usdt_amount) {
		this.otc_buy_usdt_amount = otc_buy_usdt_amount;
	}
	
	@Column(name = "otc_sell_usdt_amount")
	public double getOtc_sell_usdt_amount() {
		return otc_sell_usdt_amount;
	}
	
	public void setOtc_sell_usdt_amount(double otc_sell_usdt_amount) {
		this.otc_sell_usdt_amount = otc_sell_usdt_amount;
	}
	
	@Column(name = "otc_buy_amount")
	public double getOtc_buy_amount() {
		return otc_buy_amount;
	}
	public void setOtc_buy_amount(double otc_buy_amount) {
		this.otc_buy_amount = otc_buy_amount;
	}
	
	@Column(name = "otc_sell_amount")
	public double getOtc_sell_amount() {
		return otc_sell_amount;
	}
	public void setOtc_sell_amount(double otc_sell_amount) {
		this.otc_sell_amount = otc_sell_amount;
	}
	
	@Column(name = "otc_buy_num")
	public double getOtc_buy_num() {
		return otc_buy_num;
	}
	public void setOtc_buy_num(double otc_buy_num) {
		this.otc_buy_num = otc_buy_num;
	}
	
	@Column(name = "otc_sell_num")
	public double getOtc_sell_num() {
		return otc_sell_num;
	}
	public void setOtc_sell_num(double otc_sell_num) {
		this.otc_sell_num = otc_sell_num;
	}
	
	@Column(name = "transfer_usdt_in_amount")
	public double getTransfer_usdt_in_amount() {
		return transfer_usdt_in_amount;
	}
	public void setTransfer_usdt_in_amount(double transfer_usdt_in_amount) {
		this.transfer_usdt_in_amount = transfer_usdt_in_amount;
	}
	
	@Column(name = "transfer_usdt_in_num")
	public double getTransfer_usdt_in_num() {
		return transfer_usdt_in_num;
	}
	public void setTransfer_usdt_in_num(double transfer_usdt_in_num) {
		this.transfer_usdt_in_num = transfer_usdt_in_num;
	}
	
	@Column(name = "transfer_bfsc_in_amount")
	public double getTransfer_bfsc_in_amount() {
		return transfer_bfsc_in_amount;
	}
	public void setTransfer_bfsc_in_amount(double transfer_bfsc_in_amount) {
		this.transfer_bfsc_in_amount = transfer_bfsc_in_amount;
	}
	
	@Column(name = "transfer_bfsc_out_amount")
	public double getTransfer_bfsc_out_amount() {
		return transfer_bfsc_out_amount;
	}
	public void setTransfer_bfsc_out_amount(double transfer_bfsc_out_amount) {
		this.transfer_bfsc_out_amount = transfer_bfsc_out_amount;
	}
	
	@Column(name = "bfsc_stock_amount")
	public double getBfsc_stock_amount() {
		return bfsc_stock_amount;
	}
	public void setBfsc_stock_amount(double bfsc_stock_amount) {
		this.bfsc_stock_amount = bfsc_stock_amount;
	}
	
	@Column(name = "market_buy_qty")
	public double getMarket_buy_qty() {
		return market_buy_qty;
	}
	public void setMarket_buy_qty(double market_buy_qty) {
		this.market_buy_qty = market_buy_qty;
	}
	
	@Column(name = "market_sell_qty")
	public double getMarket_sell_qty() {
		return market_sell_qty;
	}
	public void setMarket_sell_qty(double market_sell_qty) {
		this.market_sell_qty = market_sell_qty;
	}
	
	@Column(name = "market_buy_amount")
	public double getMarket_buy_amount() {
		return market_buy_amount;
	}
	public void setMarket_buy_amount(double market_buy_amount) {
		this.market_buy_amount = market_buy_amount;
	}
	
	@Column(name = "market_sell_amount")
	public double getMarket_sell_amount() {
		return market_sell_amount;
	}
	public void setMarket_sell_amount(double market_sell_amount) {
		this.market_sell_amount = market_sell_amount;
	}
	
	@Column(name = "market_buy_total_qty")
	public double getMarket_buy_total_qty() {
		return market_buy_total_qty;
	}
	public void setMarket_buy_total_qty(double market_buy_total_qty) {
		this.market_buy_total_qty = market_buy_total_qty;
	}
	
	@Column(name = "market_sell_total_qty")
	public double getMarket_sell_total_qty() {
		return market_sell_total_qty;
	}
	public void setMarket_sell_total_qty(double market_sell_total_qty) {
		this.market_sell_total_qty = market_sell_total_qty;
	}
	
	@Column(name = "market_buy_total_amount")
	public double getMarket_buy_total_amount() {
		return market_buy_total_amount;
	}
	public void setMarket_buy_total_amount(double market_buy_total_amount) {
		this.market_buy_total_amount = market_buy_total_amount;
	}
	
	@Column(name = "market_sell_total_amount")
	public double getMarket_sell_total_amount() {
		return market_sell_total_amount;
	}
	public void setMarket_sell_total_amount(double market_sell_total_amount) {
		this.market_sell_total_amount = market_sell_total_amount;
	}
	
	@Column(name = "market_buy_fees")
	public double getMarket_buy_fees() {
		return market_buy_fees;
	}
	public void setMarket_buy_fees(double market_buy_fees) {
		this.market_buy_fees = market_buy_fees;
	}
	
	@Column(name = "market_sell_fees")
	public double getMarket_sell_fees() {
		return market_sell_fees;
	}
	public void setMarket_sell_fees(double market_sell_fees) {
		this.market_sell_fees = market_sell_fees;
	}
	
	@Column(name = "bfsc_fees_amount")
	public double getBfsc_fees_amount() {
		return bfsc_fees_amount;
	}
	public void setBfsc_fees_amount(double bfsc_fees_amount) {
		this.bfsc_fees_amount = bfsc_fees_amount;
	}
	
	@Column(name = "bfsc_avg_price")
	public double getBfsc_avg_price() {
		return bfsc_avg_price;
	}
	public void setBfsc_avg_price(double bfsc_avg_price) {
		this.bfsc_avg_price = bfsc_avg_price;
	}
	
	@Column(name = "usdt_fees_amount")
	public double getUsdt_fees_amount() {
		return usdt_fees_amount;
	}
	public void setUsdt_fees_amount(double usdt_fees_amount) {
		this.usdt_fees_amount = usdt_fees_amount;
	}
	
	@Column(name = "register_total_num")
	public Integer getRegister_total_num() {
		return register_total_num;
	}
	public void setRegister_total_num(Integer register_total_num) {
		this.register_total_num = register_total_num;
	}
	
	@Column(name = "register_add_num")
	public Integer getRegister_add_num() {
		return register_add_num;
	}
	public void setRegister_add_num(Integer register_add_num) {
		this.register_add_num = register_add_num;
	}
	/**
	 * 设置：版本
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取：版本
	 */
	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}
}
