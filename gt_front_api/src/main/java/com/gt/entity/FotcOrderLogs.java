package com.gt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;



/**
 * otc订单操作日志表
 * @author zhouyong
 *
 */
@Entity
@Table(name = "f_otcorderlogs", catalog = "gtcoin")
public class FotcOrderLogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2801167618788254827L;
	
	//主键id
	private Integer id;
	//买家用户id
	private Fuser buyUser;
	//卖家用户id
	private Fuser sellUser;
	//订单id
	private FotcOrder orderId;
	// 订单类型（1：买入 2：卖出）
	private Integer orderType;
	//'申诉原因(1:买家申诉——已付款忘记点(我已付款)，2：买家申诉——已付款，卖家未确认，3：卖家申诉——买家未付款，点了已付款按钮，4：卖家申诉——尚未收到款错点确认收款)',
	private Integer  appealReason; 
	//'申诉人',
	private Fuser  appealUser; 
	//操作类型操作类型 （1：用户手动取消   2：系统自取消   3：异常订单申诉失败  4：异常订单申诉成功  5：管理员手动取消）
	private Integer type;
	//版本号
	private Integer version;
	//创建时间
	private Timestamp createTime;
	//是否投诉成功
	private Integer complainSucc;

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：买家用户id
	 */
	public void setBuyUser(Fuser buyUser) {
		this.buyUser = buyUser;
	}
	/**
	 * 获取：买家用户id
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buy_user_id")
	public Fuser getBuyUser() {
		return buyUser;
	}
	
	/**
	 * 设置：卖家用户id
	 */
	public void setSellUser(Fuser sellUser) {
		this.sellUser = sellUser;
	}
	/**
	 * 获取：买家用户id
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sell_user_id")
	public Fuser getSellUser() {
		return sellUser;
	}
	
	/**
	 * 设置：订单id
	 */
	public void setOrderId(FotcOrder orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	public FotcOrder getOrderId() {
		return orderId;
	}
	
	/**
	 * 设置：操作类型（1：手动取消 ；2：自动取消 ；3：申诉失败； 4：管理员手动取消）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：操作类型（1：手动取消 ；2：自动取消 ；3：申诉失败； 4：管理员手动取消）
	 */
	@Column(name = "type")
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取：版本号
	 */
	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	@Column(name = "order_type")
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
	@Column(name = "appeal_reason")
	public Integer getAppealReason() {
		return appealReason;
	}

	public void setAppealReason(Integer appealReason) {
		this.appealReason = appealReason;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appeal_user_id")
	public Fuser getAppealUser() {
		return appealUser;
	}

	public void setAppealUser(Fuser appealUser) {
		this.appealUser = appealUser;
	}
	
	@Column(name = "complain_succ")
	public Integer getComplainSucc() {
		return complainSucc;
	}
	public void setComplainSucc(Integer complainSucc) {
		this.complainSucc = complainSucc;
	}
	
}
