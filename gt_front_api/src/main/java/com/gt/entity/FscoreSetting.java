package com.gt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gt.Enum.ScoreRecordTypeEnum;

/**
 * FscoreSetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fscore_setting", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FscoreSetting implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8650912924494831544L;
	private Integer fid;
	private Integer version;
	private Integer type;
	private String type_s;
	private Double score;
	private String remark;

	// Constructors

	/** default constructor */
	public FscoreSetting() {
	}

	/** full constructor */
	public FscoreSetting(Integer type, Integer score) {
		this.type = type;
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

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "score")
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Transient
	public String getType_s() {
		return ScoreRecordTypeEnum.getEnumString(this.getType());
	}

	public void setType_s(String type_s) {
		this.type_s = type_s;
	}
}