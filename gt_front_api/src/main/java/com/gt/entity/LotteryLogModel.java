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
@Table(name = "lottery_log", catalog = "gtcoin")
public class LotteryLogModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505169131536851368L;
	// Fields
	private int id;
	private LotteryAwardsModel lotteryAwardsModel; //'奖项id' ,
	private LotteryPeriodsModel lotteryPeriodsModel;// '期数ID' ,
	private Fuser user;// '中奖用户id' ,
	private Timestamp create_time;// '中奖时间' ,
	

	
	
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
	@JoinColumn(name = "user_id")
	public Fuser getUser() {
		return user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "awards_id")
	public LotteryAwardsModel getLotteryAwardsModel() {
		return lotteryAwardsModel;
	}

	public void setLotteryAwardsModel(LotteryAwardsModel lotteryAwardsModel) {
		this.lotteryAwardsModel = lotteryAwardsModel;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "periods_id")
	public LotteryPeriodsModel getLotteryPeriodsModel() {
		return lotteryPeriodsModel;
	}

	public void setLotteryPeriodsModel(LotteryPeriodsModel lotteryPeriodsModel) {
		this.lotteryPeriodsModel = lotteryPeriodsModel;
	}

	public void setUser(Fuser user) {
		this.user = user;
	}
	@Column(name = "create_time")
	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}


}