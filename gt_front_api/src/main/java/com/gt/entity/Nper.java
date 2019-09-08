package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.NperStatusEnum;
import com.gt.util.Utils;

@Entity
@Table(name = "t_nper", catalog = "lockdraw")
public class Nper implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 134021151992595103L;

	private Integer id;
	
	private String nper;
	
	private Integer lottery_min;
	
	private Integer lottery_max;
	
	private String win_no;
	
	private Integer win_uid;
	
	private Integer status;
	private String status_v;
	
	private boolean is_send_prize;
	
	private String ball;
	
	private Timestamp create_time;
	
	private Integer creater;

	private Timestamp start_time;
	
	private Timestamp draw_time;
	
	private Timestamp end_time;
	
	private Integer prize_coin_type;
	
	private Double prize_amount;
	
	private Integer lottery_coin_type;
	
	private Double lottery_amount;

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

	@Column(name = "nper")
	public String getNper() {
		return nper;
	}

	public void setNper(String nper) {
		this.nper = nper;
	}

	@Column(name = "lottery_min")
	public Integer getLottery_min() {
		return lottery_min;
	}

	public void setLottery_min(Integer lottery_min) {
		this.lottery_min = lottery_min;
	}

	@Column(name = "lottery_max")
	public Integer getLottery_max() {
		return lottery_max;
	}

	public void setLottery_max(Integer lottery_max) {
		this.lottery_max = lottery_max;
	}

	@Column(name = "win_no")
	public String getWin_no() {
		return win_no;
	}

	public void setWin_no(String win_no) {
		this.win_no = win_no;
	}

	@Column(name = "win_uid")
	public Integer getWin_uid() {
		return win_uid;
	}

	public void setWin_uid(Integer win_uid) {
		this.win_uid = win_uid;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Transient
	public String getStatus_v() {
		this.setStatus_v(NperStatusEnum.getEnumString(this.status));
		return this.status_v;
	}

	public void setStatus_v(String status_v) {
		this.status_v = status_v;
	}
	
	@Column(name = "is_send_prize")
	public boolean isIs_send_prize() {
		return is_send_prize;
	}

	public void setIs_send_prize(boolean is_send_prize) {
		this.is_send_prize = is_send_prize;
	}

	@Column(name = "ball")
	public String getBall() {
		return ball;
	}

	public void setBall(String ball) {
		this.ball = ball;
	}

	@Column(name = "create_time")
	public Timestamp getCreate_time() {
		return create_time;
	}
	
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
	@Column(name = "creater")
	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	@Column(name = "start_time")
	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	@Column(name = "draw_time")
	public Timestamp getDraw_time() {
		return draw_time;
	}

	public void setDraw_time(Timestamp draw_time) {
		this.draw_time = draw_time;
	}

	@Column(name = "end_time")
	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	@Column(name = "prize_coin_type")
	public Integer getPrize_coin_type() {
		return prize_coin_type;
	}

	public void setPrize_coin_type(Integer prize_coin_type) {
		this.prize_coin_type = prize_coin_type;
	}

	@Column(name = "prize_amount")
	public Double getPrize_amount() {
		return prize_amount;
	}

	public void setPrize_amount(Double prize_amount) {
		this.prize_amount = prize_amount;
	}

	@Column(name = "lottery_coin_type")
	public Integer getLottery_coin_type() {
		return lottery_coin_type;
	}

	public void setLottery_coin_type(Integer lottery_coin_type) {
		this.lottery_coin_type = lottery_coin_type;
	}

	@Column(name = "lottery_amount")
	public Double getLottery_amount() {
		return lottery_amount;
	}

	public void setLottery_amount(Double lottery_amount) {
		this.lottery_amount = lottery_amount;
	}
	
}
