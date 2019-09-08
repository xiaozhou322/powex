package com.gt.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

import com.gt.comm.MultipleValues;

import net.sf.json.JSONObject;

/**
 * Fasset entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fasset", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fasset implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7828977291932981387L;
	private int fid;
	private int version;
	private Fuser fuser;
	private Double ftotal;
	private Timestamp fcreatetime;
	private Timestamp flastupdatetime;
	private Boolean status;
	private String detail;

	private List<MultipleValues> list;

	// Constructors

	/** default constructor */
	public Fasset() {
	}

	/** full constructor */
	public Fasset(Fuser fuser, Double ftotal, Timestamp fcreatetime,
			Timestamp flastupdatetime, Boolean status) {
		this.fuser = fuser;
		this.ftotal = ftotal;
		this.fcreatetime = fcreatetime;
		this.flastupdatetime = flastupdatetime;
		this.status = status;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuser")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "ftotal", precision = 16, scale = 6)
	public Double getFtotal() {
		return this.ftotal;
	}

	public void setFtotal(Double ftotal) {
		this.ftotal = ftotal;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name = "flastupdatetime", length = 0)
	public Timestamp getFlastupdatetime() {
		return this.flastupdatetime;
	}

	public void setFlastupdatetime(Timestamp flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Transient
	public List<MultipleValues> getList() {
		return list;
	}

	public void setList(List<MultipleValues> list) {
		this.list = list;
	}

	public void parseJson(List<Fvirtualcointype> fvirtualcointypes) {
		try {
			List<MultipleValues> multipleValues = new ArrayList<MultipleValues>();

			JSONObject jsonObject = JSONObject.fromObject(this.getDetail());
			// cny
			MultipleValues cny = new MultipleValues();
			if (jsonObject.containsKey("0")){
				JSONObject cnyJson = jsonObject.getJSONObject("0");
				cny.setValue1(cnyJson.getDouble("total"));
				cny.setValue2(cnyJson.getDouble("frozen"));
			}else{
				cny.setValue1(0);
				cny.setValue2(0);
			}
			cny.setValue3("CNY");
			cny.setValue4("CNY");
			multipleValues.add(cny);
			// USDT
			MultipleValues usdt = new MultipleValues();
			if (jsonObject.containsKey("-1")){
				JSONObject usdtJson = jsonObject.getJSONObject("-1");
				usdt.setValue1(usdtJson.getDouble("total"));
				usdt.setValue2(usdtJson.getDouble("frozen"));
			}else{
				usdt.setValue1(0);
				usdt.setValue2(0);
			}
			usdt.setValue3("USDT");
			usdt.setValue4("USDT");
			multipleValues.add(usdt);

			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
				MultipleValues mulItem = new MultipleValues();
				if (jsonObject
						.containsKey(String.valueOf(fvirtualcointype.getFid()))) {
					JSONObject jsonItem = jsonObject.getJSONObject(String
							.valueOf(fvirtualcointype.getFid()));
					mulItem.setValue1(jsonItem.getDouble("total"));
					mulItem.setValue2(jsonItem.getDouble("frozen"));
				} else {
					mulItem.setValue1(0);
					mulItem.setValue2(0);
				}
				mulItem.setValue3(fvirtualcointype.getfShortName());
				mulItem.setValue4(fvirtualcointype.getFname());
				multipleValues.add(mulItem);
			}

			this.setList(multipleValues);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}