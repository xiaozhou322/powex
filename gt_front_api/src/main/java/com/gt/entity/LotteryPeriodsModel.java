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
@Table(name = "lottery_periods", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LotteryPeriodsModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 310731652268000712L;
	// Fields
	private int id;
	private FactivityModel factivityModel;// '活动id',
	private int periods_num;//'期数' ,
	private Timestamp start_time;// '开始时间' ,
	private Timestamp end_time;// '结束时间' ,
	private int status;// '状态 1.未开启2.已开启3.已结束4.已关闭' ,
	
	
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
	@JoinColumn(name = "activity_id")
	public FactivityModel getFactivityModel() {
		return factivityModel;
	}

	public void setFactivityModel(FactivityModel factivityModel) {
		this.factivityModel = factivityModel;
	}
	@Column(name = "periods_num")
	public int getPeriods_num() {
		return periods_num;
	}

	public void setPeriods_num(int periods_num) {
		this.periods_num = periods_num;
	}
	@Column(name = "start_time")
	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	@Column(name = "end_time")
	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	
}