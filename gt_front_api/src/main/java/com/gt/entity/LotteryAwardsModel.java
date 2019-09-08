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
@Table(name = "lottery_awards", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LotteryAwardsModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1083510392501166265L;
	// Fields
	  private int id;//
	  private FactivityModel factivityModel;// '活动id',
	  private String awards_name;// '奖项名称 ',
	  private Fvirtualcointype fvirtualcointype;// '奖励币种ID',
	  private double fee_amount;// '奖项每注奖励金额',
	  private int total;// '注数',
	
	
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

	@Column(name = "awards_name")
	public String getAwards_name() {
		return awards_name;
	}

	public void setAwards_name(String awards_name) {
		this.awards_name = awards_name;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "coin_type")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@Column(name = "fee_amount")
	public double getFee_amount() {
		return fee_amount;
	}

	public void setFee_amount(double fee_amount) {
		this.fee_amount = fee_amount;
	}
	@Column(name = "total")
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
		
	
}