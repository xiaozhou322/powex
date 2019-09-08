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

import com.gt.util.HTMLSpirit;

/**
 * Fsystemargs entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fsystemargs", catalog = "gtcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fsystemargs implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5473974640323513458L;
	private int fid;
	private String fkey;
	private String fvalue;
	private String fvalue_s;
	private String fdescription;
	private int version;
//	private String furl;

	// Constructors

	/** default constructor */
	public Fsystemargs() {
	}

	/** full constructor */
	public Fsystemargs(String fkey, String fvalue, String fdescription) {
		this.fkey = fkey;
		this.fvalue = fvalue;
		this.fdescription = fdescription;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fId", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Column(name = "fKey", length = 100)
	public String getFkey() {
		return this.fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	@Column(name = "fValue", length = 100)
	public String getFvalue() {
		return this.fvalue;
	}

	public void setFvalue(String fvalue) {
		this.fvalue = fvalue;
	}

	@Column(name = "fDescription", length = 1024)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public String getFvalue_s() {
		return HTMLSpirit.delHTMLTag(getFvalue());
	}

	public void setFvalue_s(String fvalue_s) {
		this.fvalue_s = fvalue_s;
	}

	@Transient
	public boolean getBoolean() {
		boolean flag = false;
		String value = this.getFvalue();
		if (value != null && "true".equalsIgnoreCase(value.trim())) {
			flag = true;
		}
		return flag;
	}

	@Transient
	public int getInteger() {
		int flag = 0;
		String value = this.getFvalue();
		if (value != null) {
			try {
				flag = Integer.parseInt(value.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return flag;
	}
//
//	@Column(name = "furl")
//	public String getFurl() {
//		return furl;
//	}
//
//	public void setFurl(String furl) {
//		this.furl = furl;
//	}
}