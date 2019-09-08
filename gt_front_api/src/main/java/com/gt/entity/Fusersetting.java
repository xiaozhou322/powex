package com.gt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * Fusersetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fusersetting", catalog = "gtcoin")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fusersetting implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8341577221528948210L;
	private Integer fid;
	private Fuser fuser;
	private boolean fisAutoReturnToAccount;
	private double fscore;
	private double flotteryeggTimes;
	private int version;
	private double fticketQty;
	private Timestamp fsendDate;
	private boolean fissend;
	private Timestamp fp2pmsgDate1;
	private Timestamp fp2pmsgDate2;

	/** default constructor */
	public Fusersetting() {
	}

	/** full constructor */
	public Fusersetting(Fuser fuser, boolean fisAutoReturnToAccount) {
		this.fuser = fuser;
		this.fisAutoReturnToAccount = fisAutoReturnToAccount;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuser")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fisAutoReturnToAccount")
	public boolean getFisAutoReturnToAccount() {
		return this.fisAutoReturnToAccount;
	}

	public void setFisAutoReturnToAccount(boolean fisAutoReturnToAccount) {
		this.fisAutoReturnToAccount = fisAutoReturnToAccount;
	}

	@Column(name = "fscore")
	public double getFscore() {
		return fscore;
	}

	public void setFscore(double fscore) {
		this.fscore = fscore;
	}

	@Column(name = "flotteryeggTimes")
	public double getFlotteryeggTimes() {
		return flotteryeggTimes;
	}

	public void setFlotteryeggTimes(double flotteryeggTimes) {
		this.flotteryeggTimes = flotteryeggTimes;
	}

	@Column(name = "fticketQty")
	public double getFticketQty() {
		return fticketQty;
	}

	public void setFticketQty(double fticketQty) {
		this.fticketQty = fticketQty;
	}

	@Column(name = "fsendDate")
	public Timestamp getFsendDate() {
		return fsendDate;
	}

	public void setFsendDate(Timestamp fsendDate) {
		this.fsendDate = fsendDate;
	}

	@Column(name = "fissend")
	public boolean isFissend() {
		return fissend;
	}

	public void setFissend(boolean fissend) {
		this.fissend = fissend;
	}
	
	@Column(name = "fp2pmsgDate1")
	public Timestamp getFp2pmsgDate1() {
		return fp2pmsgDate1;
	}

	public void setFp2pmsgDate1(Timestamp fp2pmsgDate1) {
		this.fp2pmsgDate1 = fp2pmsgDate1;
	}
	
	@Column(name = "fp2pmsgDate2")
	public Timestamp getFp2pmsgDate2() {
		return fp2pmsgDate2;
	}

	public void setFp2pmsgDate2(Timestamp fp2pmsgDate2) {
		this.fp2pmsgDate2 = fp2pmsgDate2;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}