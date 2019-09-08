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
@Table(name = "zdis_factivity_wallet_record", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ZdisFactivityWalletRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3336660807515325949L;
	// Fields
	  private int id;
	  private FactivityModel factivityModel;
	  private Fuser user;
	  private Fvirtualcointype fvirtualcointype;// '奖励币种ID',
	  private double famount;
	  private int type;//'流水类型：1是入账，2是奖金，3是退款，4是备用',
	  private Timestamp createtime;//'更新时间',
		  
	  private int version;
	// Property accessors
		@GenericGenerator(name = "generator", strategy = "identity")
		@Id
		@GeneratedValue(generator = "generator")
		@Column(name = "id", unique = true, nullable = false)
		public int getId() {
			return this.id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "faid")
		public FactivityModel getFactivityModel() {
			return factivityModel;
		}

		public void setFactivityModel(FactivityModel factivityModel) {
			this.factivityModel = factivityModel;
		}
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "fuid")
		public Fuser getUser() {
			return user;
		}

		public void setUser(Fuser user) {
			this.user = user;
		}
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "fvid")
		public Fvirtualcointype getFvirtualcointype() {
			return fvirtualcointype;
		}

		public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
			this.fvirtualcointype = fvirtualcointype;
		}
		@Column(name = "famount")
		public double getFamount() {
			return famount;
		}

		public void setFamount(double famount) {
			this.famount = famount;
		}
		@Column(name = "ftype")
		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
		@Column(name = "createtime")
		public Timestamp getCreatetime() {
			return createtime;
		}

		public void setCreatetime(Timestamp createtime) {
			this.createtime = createtime;
		}
		
		
		@Column(name = "version")
		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}


	
	
	
	
}