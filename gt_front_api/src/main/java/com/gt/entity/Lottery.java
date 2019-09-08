package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_lottery", catalog = "lockdraw")
public class Lottery implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5774001513397686198L;

	private Integer id;
	
	private String nper;
	
	private String lottery_no;
	
	private Integer uid;
	
	private Timestamp createtime;
	
	private String serial_no;
	
	private String block_link;
	
	//奖票状态（未开奖，开奖中， 未中奖， 已中讲）
	private String lotteryStatus;

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

	@Column(name = "lottery_no")
	public String getLottery_no() {
		return lottery_no;
	}

	public void setLottery_no(String lottery_no) {
		this.lottery_no = lottery_no;
	}

	@Column(name = "uid")
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "createtime")
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp creattime) {
		this.createtime = creattime;
	}

	@Column(name = "serial_no")
	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	@Column(name = "block_link")
	public String getBlock_link() {
		return block_link;
	}

	public void setBlock_link(String block_link) {
		this.block_link = block_link;
	}

	@Transient
	public String getLotteryStatus() {
		return lotteryStatus;
	}

	public void setLotteryStatus(String lotteryStatus) {
		this.lotteryStatus = lotteryStatus;
	}
	
	
}
