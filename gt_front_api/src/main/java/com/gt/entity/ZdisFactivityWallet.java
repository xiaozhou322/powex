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
@Table(name = "zdis_factivity_wallet", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ZdisFactivityWallet implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3156835043188801669L;
	// Fields
	  private int fid;
	  private Fvirtualcointype fvirtualcointype;// '奖励币种ID',
	  private double ftotal;
	  private double ffrozen;

	  private FactivityModel factivityModel;
	  private Timestamp updateTime;//'更新时间',
		  
	  private int version;
		// Constructors

	// Property accessors
		@GenericGenerator(name = "generator", strategy = "identity")
		@Id
		@GeneratedValue(generator = "generator")
		@Column(name = "fId", unique = true, nullable = false)
		public int getFid() {
			return this.fid;
		}

		public void setFid(int fid) {
			this.fid = fid;
		}
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "fVi_fId")
		public Fvirtualcointype getFvirtualcointype() {
			return fvirtualcointype;
		}

		public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
			this.fvirtualcointype = fvirtualcointype;
		}
		@Column(name = "ftotal")
		public double getFtotal() {
			return ftotal;
		}

		public void setFtotal(double ftotal) {
			this.ftotal = ftotal;
		}
		@Column(name = "ffrozen")
		public double getFfrozen() {
			return ffrozen;
		}

		public void setFfrozen(double ffrozen) {
			this.ffrozen = ffrozen;
		}
		@Column(name = "fLastUpdateTime")
		public Timestamp getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Timestamp updateTime) {
			this.updateTime = updateTime;
		}
		@Column(name = "version")
		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "faid")
		public FactivityModel getFactivityModel() {
			return factivityModel;
		}

		public void setFactivityModel(FactivityModel factivityModel) {
			this.factivityModel = factivityModel;
		}


	
	
	
	
}