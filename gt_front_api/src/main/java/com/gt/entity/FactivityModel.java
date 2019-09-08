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
@Table(name = "factivity", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FactivityModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6637320346304193332L;
	// Fields
	  private int id;
	  private String name;//T '活动名称 ',
	  private String content;//'活动内容 ',
	  private int type;//'活动类型 1.大转轮2...',
	  private String type_s;//'活动类型 1.大转轮2...',
	  private int this_round;// '总期数',
	  private int total_round;// '总期数',
	  private Fvirtualcointype fvirtualcointype;// '奖励币种ID',
	  private double coin_amount;// '活动所花费虚拟币数量',
	  private Timestamp create_time;// '创建时间',
	  private Timestamp update_time;//'更新时间',
	  private Timestamp start_time;//'开始时间',
	  private Timestamp end_time;// '结束时间',
	  private int status;//'状态 1.未开启2.已开启3.已结束4.已关闭',
	  private String status_s;//'状态 1.未开启2.已开启3.已结束4.已关闭',
	  private boolean show_jackpot;// '是否显示奖池',
	  private int way;//活动方式
	  private String way_s;//活动方式
	  private String fclassPath; //引用类路径，
	  private Fuser create_user; //引用类路径，
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="fclasspath")
	public String getFclassPath() {
		return fclassPath;
	}

	public void setFclassPath(String fclassPath) {
		this.fclassPath = fclassPath;
	}
	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	@Column(name = "this_round")
	public int getThis_round() {
		return this_round;
	}

	public void setThis_round(int this_round) {
		this.this_round = this_round;
	}

	@Column(name = "total_round")
	public int getTotal_round() {
		return total_round;
	}

	public void setTotal_round(int total_round) {
		this.total_round = total_round;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "coin_type")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}
	@Column(name = "coin_amount")
	public double getCoin_amount() {
		return coin_amount;
	}

	public void setCoin_amount(double coin_amount) {
		this.coin_amount = coin_amount;
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
	@Column(name = "show_jackpot")
	public boolean isShow_jackpot() {
		return show_jackpot;
	}

	public void setShow_jackpot(boolean show_jackpot) {
		this.show_jackpot = show_jackpot;
	}
	@Column(name = "way")
	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}
	@Transient
	public String getType_s() {
		return type_s;
	}

	public void setType_s(String type_s) {
		this.type_s = type_s;
	}
	@Transient
	public String getStatus_s() {
		return status_s;
	}

	public void setStatus_s(String status_s) {
		this.status_s = status_s;
	}
	@Transient
	public String getWay_s() {
		return way_s;
	}

	public void setWay_s(String way_s) {
		this.way_s = way_s;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public Fuser getCreate_user() {
		return create_user;
	}

	public void setCreate_user(Fuser create_user) {
		this.create_user = create_user;
	}


	
	
	
	
}