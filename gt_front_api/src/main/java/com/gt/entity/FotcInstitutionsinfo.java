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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "f_otcinstitutionsinfo", catalog = "gtcoin")
public class FotcInstitutionsinfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6662492054543138030L;
	
	private int id;// 主键
	private String institutions_name;// 机构名称
	private String institutions_short_name;// 机构简称
	private Fuser fuser;// 机构id
	private Fadmin fadmin;// 录入人
	private String institutions_username;// 机构负责人
	private String institutions_user_contact;// 机构负责人联系方式
	private String business_people;// 业务人员
	private String business_people_contact;// 业务人员联系方式
	private int institutions_status;// 机构状态：1.启用 2.禁用
	private String key_md5;// key-md5
	private String key_rsa;// key-rsa公钥
	private String page_callback_url;// 页面回调地址
	private String server_callback_url;
	private Timestamp create_time;// 创建时间
	private Timestamp update_time;// 修改时间
	private int auto_confirm;//是否支持卖单自动确认（0：否，1：是）
	private String index_url;//机构商首页链接
	private int version;       //版本号
	
	public FotcInstitutionsinfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "institutions_name")
	public String getInstitutions_name() {
		return institutions_name;
	}
	public void setInstitutions_name(String institutions_name) {
		this.institutions_name = institutions_name;
	}
	@Column(name = "institutions_short_name")
	public String getInstitutions_short_name() {
		return institutions_short_name;
	}
	public void setInstitutions_short_name(String institutions_short_name) {
		this.institutions_short_name = institutions_short_name;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "institutions_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public Fuser getFuser() {
		return fuser;
	}
	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}
 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manage_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public Fadmin getFadmin() {
		return fadmin;
	}
	public void setFadmin(Fadmin fadmin) {
		this.fadmin = fadmin;
	}
	@Column(name = "institutions_username")
	public String getInstitutions_username() {
		return institutions_username;
	}
	public void setInstitutions_username(String institutions_username) {
		this.institutions_username = institutions_username;
	}
	@Column(name = "institutions_user_contact")
	public String getInstitutions_user_contact() {
		return institutions_user_contact;
	}
	public void setInstitutions_user_contact(String institutions_user_contact) {
		this.institutions_user_contact = institutions_user_contact;
	}
	@Column(name = "business_people")
	public String getBusiness_people() {
		return business_people;
	}
	public void setBusiness_people(String business_people) {
		this.business_people = business_people;
	}
	@Column(name = "business_people_contact")
	public String getBusiness_people_contact() {
		return business_people_contact;
	}
	public void setBusiness_people_contact(String business_people_contact) {
		this.business_people_contact = business_people_contact;
	}
	@Column(name = "institutions_status")
	public int getInstitutions_status() {
		return institutions_status;
	}
	public void setInstitutions_status(int institutions_status) {
		this.institutions_status = institutions_status;
	}
	@Column(name = "key_md5")
	public String getKey_md5() {
		return key_md5;
	}
	public void setKey_md5(String key_md5) {
		this.key_md5 = key_md5;
	}
	@Column(name = "key_rsa")
	public String getKey_rsa() {
		return key_rsa;
	}
	public void setKey_rsa(String key_rsa) {
		this.key_rsa = key_rsa;
	}
	@Column(name = "page_callback_url")
	public String getPage_callback_url() {
		return page_callback_url;
	}
	public void setPage_callback_url(String page_callback_url) {
		this.page_callback_url = page_callback_url;
	}
	@Column(name = "server_callback_url")
	public String getServer_callback_url() {
		return server_callback_url;
	}
	public void setServer_callback_url(String server_callback_url) {
		this.server_callback_url = server_callback_url;
	}
	@Column(name = "create_time")
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	@Column(name = "update_time")
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	
	
	@Column(name = "auto_confirm")
	public int getAuto_confirm() {
		return auto_confirm;
	}
	public void setAuto_confirm(int auto_confirm) {
		this.auto_confirm = auto_confirm;
	}
	@Column(name = "index_url")
	public String getIndex_url() {
		return index_url;
	}
	public void setIndex_url(String index_url) {
		this.index_url = index_url;
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
