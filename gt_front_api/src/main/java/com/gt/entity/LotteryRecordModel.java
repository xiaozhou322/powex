package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.util.HTMLSpirit;

/**
 * Farticle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "lottery_record", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LotteryRecordModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2562383703727498872L;
	// Fields
	private int id;
	private LotteryAwardsModel factivityAwardsModel; //'奖项id' ,
	private LotteryPeriodsModel factivityPeriodsModel;// '期数ID' ,
	private int surplus_num;// '剩余注数' ,
	private Timestamp update_time;// '修改时间' ,
	

	
	
	// Constructors



	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "awards_id")
	public LotteryAwardsModel getFactivityAwardsModel() {
		return factivityAwardsModel;
	}

	public void setFactivityAwardsModel(LotteryAwardsModel factivityAwardsModel) {
		this.factivityAwardsModel = factivityAwardsModel;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "periods_id")
	public LotteryPeriodsModel getFactivityPeriodsModel() {
		return factivityPeriodsModel;
	}

	public void setFactivityPeriodsModel(LotteryPeriodsModel factivityPeriodsModel) {
		this.factivityPeriodsModel = factivityPeriodsModel;
	}
	@Column(name = "surplus_num")
	public int getSurplus_num() {
		return surplus_num;
	}

	public void setSurplus_num(int surplus_num) {
		this.surplus_num = surplus_num;
	}
	@Column(name = "update_time")
	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}


}