package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fconvertvirtualcoin", catalog = "gtcoin")
public class ConvertVirtualCoin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -983976307916786488L;
	
	private Integer id;
	private String orderId;
	private Integer coinType1;
	private Integer coinType2;
	private String fromAddress1;
	private String toAddress1;
	private Double amount1;
	private String fromAddress2;
	private String toAddress2;
	private Double amount2;
	private Double convertRatio;
	private Double fee;
	private String txid1;
	private String txid2;
	private Double minerFee;
	private Double finalAmount2;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer version;
	
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
	
	@Column(name = "orderId")
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "coinType1")
	public Integer getCoinType1() {
		return coinType1;
	}
	
	public void setCoinType1(Integer coinType1) {
		this.coinType1 = coinType1;
	}
	
	@Column(name = "coinType2")
	public Integer getCoinType2() {
		return coinType2;
	}
	
	public void setCoinType2(Integer coinType2) {
		this.coinType2 = coinType2;
	}
	
	@Column(name = "fromAddress1")
	public String getFromAddress1() {
		return fromAddress1;
	}
	
	public void setFromAddress1(String fromAddress1) {
		this.fromAddress1 = fromAddress1;
	}
	
	@Column(name = "toAddress1")
	public String getToAddress1() {
		return toAddress1;
	}
	
	public void setToAddress1(String toAddress1) {
		this.toAddress1 = toAddress1;
	}
	
	@Column(name = "amount1")
	public Double getAmount1() {
		return amount1;
	}
	
	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}
	
	@Column(name = "fromAddress2")
	public String getFromAddress2() {
		return fromAddress2;
	}
	
	public void setFromAddress2(String fromAddress2) {
		this.fromAddress2 = fromAddress2;
	}
	
	@Column(name = "toAddress2")
	public String getToAddress2() {
		return toAddress2;
	}
	
	public void setToAddress2(String toAddress2) {
		this.toAddress2 = toAddress2;
	}
	
	@Column(name = "amount2")
	public Double getAmount2() {
		return amount2;
	}
	
	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}
	
	@Column(name = "convertRatio")
	public Double getConvertRatio() {
		return convertRatio;
	}
	
	public void setConvertRatio(Double convertRatio) {
		this.convertRatio = convertRatio;
	}
	
	@Column(name = "fee")
	public Double getFee() {
		return fee;
	}
	
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	@Column(name = "txid1")
	public String getTxid1() {
		return txid1;
	}
	
	public void setTxid1(String txid1) {
		this.txid1 = txid1;
	}
	
	@Column(name = "txid2")
	public String getTxid2() {
		return txid2;
	}
	
	public void setTxid2(String txid2) {
		this.txid2 = txid2;
	}
	
	@Column(name = "minerFee")
	public Double getMinerFee() {
		return minerFee;
	}
	
	public void setMinerFee(Double minerFee) {
		this.minerFee = minerFee;
	}
	
	@Column(name = "finalAmount2")
	public Double getFinalAmount2() {
		return finalAmount2;
	}
	
	public void setFinalAmount2(Double finalAmount2) {
		this.finalAmount2 = finalAmount2;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
