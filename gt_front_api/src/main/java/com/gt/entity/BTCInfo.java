package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class BTCInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8008889998190121307L;
	
	private String account;// 帐户，USERID
	private String address;// 充向地址
	private String category;// 类型，receive OR SEND
	private double amount;// 数量
	private int confirmations;// 确认数
	private String txid;// 交易ID
	private Timestamp time;// 时间
	private String comment;// 备注
	private String contract;// 合约名称
	private long blockNumber;// 备注

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(long blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

}
