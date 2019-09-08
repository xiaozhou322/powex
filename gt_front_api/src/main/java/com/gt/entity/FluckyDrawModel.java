package com.gt.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Farticletype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "flucky_draw", catalog = "gtcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FluckyDrawModel implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 227620991604313650L;
	private Integer id;//用户抽奖记录的id',
	private Fuser user;// '抽奖的用户id',
	private String period_number;//'抽奖活动id',
	private String  lucky_draw_number;//'抽奖号码',
	private Timestamp create_date;//'记录创建时间',
	private Timestamp update_date;// '开奖时间',
	private String remark;// '备注',
	private Integer status;// '是否开奖(0:未开奖  1:已开奖)',

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
	@NotFound(action=NotFoundAction.IGNORE)
	public Fuser getUser() {
		return user;
	}

	public void setUser(Fuser user) {
		this.user = user;
	}
	@Column(name = "period_number")
	public String getPeriod_number() {
		return period_number;
	}

	public void setPeriod_number(String period_number) {
		this.period_number = period_number;
	}

	@Column(name = "lucky_draw_number")
	public String getLucky_draw_number() {
		return lucky_draw_number;
	}

	public void setLucky_draw_number(String lucky_draw_number) {
		this.lucky_draw_number = lucky_draw_number;
	}
	
	@Column(name = "create_date")
	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}
	
	@Column(name = "update_date")
	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}