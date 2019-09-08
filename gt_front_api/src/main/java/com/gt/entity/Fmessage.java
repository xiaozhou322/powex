package com.gt.entity;
// default package

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.MessageStatusEnum;

/**
 * 消息表
 */
@Entity
@Table(name = "fmessage", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fmessage implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5345288310781246716L;
	private Integer fid;
	private Fadmin fcreator;// 发送人
	private Fuser freceiver;// 接收人
	private Integer fstatus;// 状态//MessageStatusEnum
	private String fstatus_s;
	private String ftitle;// 主题
	private String ftitle_short;// 主题
	private String fcontent;// 内容
	private Timestamp fcreateTime;// 发送时间

	// Constructors

	/** default constructor */
	public Fmessage() {
	}

	/** full constructor */
	public Fmessage(Fadmin fcreator, Fuser freceiver, Integer fstatus,
			String ftitle, String fcontent, Timestamp fcreateTime) {
		this.fcreator = fcreator;
		this.freceiver = freceiver;
		this.fstatus = fstatus;
		this.ftitle = ftitle;
		this.fcontent = fcontent;
		this.fcreateTime = fcreateTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "FId", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FCreatorId")
	public Fadmin getFcreator() {
		return this.fcreator;
	}

	public void setFcreator(Fadmin fcreator) {
		this.fcreator = fcreator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FReceiverId")
	public Fuser getFreceiver() {
		return this.freceiver;
	}

	public void setFreceiver(Fuser freceiver) {
		this.freceiver = freceiver;
	}

	@Column(name = "FStatus")
	public Integer getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "FTitle", length = 100)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "FContent", length = 65535)
	public String getFcontent() {
		return this.fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	@Column(name = "FCreateTime", length = 19)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	
	@Transient
	public String getFstatus_s() {
		return MessageStatusEnum.getEnumString(this.getFstatus()) ;
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Transient
	public String getFtitle_short() {
		String ret =  this.getFtitle() ;
		if(ret!=null && ret.length()>30){
			ret = ret.substring(0,30)+"..." ;
		}
		return ret ;
	}

	public void setFtitle_short(String ftitle_short) {
		this.ftitle_short = ftitle_short;
	}

	
	
}