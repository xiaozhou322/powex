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

import com.gt.entity.Fuser;
import com.gt.util.HTMLSpirit;

/**
 * Farticle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "farticle", catalog = "gtcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Farticle implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 216337201299808953L;
	private int fid;
	private Farticletype farticletype;
	private Fadmin fadminByFcreateAdmin;
	private Fadmin fadminByFmodifyAdmin;
	private String ftitle;
	private String ftitle_short ;
	private String fkeyword;
	private String fdescription;
	private String fcontent;
	private String fcontent_short;
	private String fcontent_m;
	private Timestamp fcreateDate;
	private Timestamp flastModifyDate;
	private int version ;
	private int fcount;
	private String furl;
	private boolean fisout;
	private String foutUrl;
	private boolean fisding;
	private String url;
	private String ftitle_cn;
	private String fcontent_cn;
	//项目方id
		private Fuser projectId;
	// Constructors



	/** default constructor */
	public Farticle() {
	}

	/** full constructor */
	public Farticle(Farticletype farticletype, Fadmin fadminByFcreateAdmin,
			Fadmin fadminByFmodifyAdmin, String ftitle, String fkeyword,
			String fdescription, String fcontent, Timestamp fcreateDate,
			Timestamp flastModifyDate) {
		this.farticletype = farticletype;
		this.fadminByFcreateAdmin = fadminByFcreateAdmin;
		this.fadminByFmodifyAdmin = fadminByFmodifyAdmin;
		this.ftitle = ftitle;
		this.fkeyword = fkeyword;
		this.fdescription = fdescription;
		this.fcontent = fcontent;
		this.fcreateDate = fcreateDate;
		this.flastModifyDate = flastModifyDate;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fArticleType")
	public Farticletype getFarticletype() {
		return this.farticletype;
	}

	public void setFarticletype(Farticletype farticletype) {
		this.farticletype = farticletype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fCreateAdmin")
	public Fadmin getFadminByFcreateAdmin() {
		return this.fadminByFcreateAdmin;
	}

	public void setFadminByFcreateAdmin(Fadmin fadminByFcreateAdmin) {
		this.fadminByFcreateAdmin = fadminByFcreateAdmin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fModifyAdmin")
	public Fadmin getFadminByFmodifyAdmin() {
		return this.fadminByFmodifyAdmin;
	}

	public void setFadminByFmodifyAdmin(Fadmin fadminByFmodifyAdmin) {
		this.fadminByFmodifyAdmin = fadminByFmodifyAdmin;
	}

	@Column(name = "fTitle", length = 1024)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "fKeyword", length = 1024)
	public String getFkeyword() {
		return this.fkeyword;
	}

	public void setFkeyword(String fkeyword) {
		this.fkeyword = fkeyword;
	}

	@Column(name = "fDescription", length = 1024)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fContent", length = 65535)
	public String getFcontent() {
		return this.fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	@Column(name = "fCreateDate", length = 0)
	public Timestamp getFcreateDate() {
		return this.fcreateDate;
	}

	public void setFcreateDate(Timestamp fcreateDate) {
		this.fcreateDate = fcreateDate;
	}

	@Column(name = "fLastModifyDate", length = 0)
	public Timestamp getFlastModifyDate() {
		return this.flastModifyDate;
	}

	public void setFlastModifyDate(Timestamp flastModifyDate) {
		this.flastModifyDate = flastModifyDate;
	}
	
	@Column(name = "fcount")
	public int getFcount() {
		return fcount;
	}

	public void setFcount(int fcount) {
		this.fcount = fcount;
	}

	@Column(name = "furl")
	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}
	

	@Column(name = "fisout")
	public boolean isFisout() {
		return fisout;
	}

	public void setFisout(boolean fisout) {
		this.fisout = fisout;
	}

	@Column(name = "foutUrl")
	public String getFoutUrl() {
		return foutUrl;
	}

	public void setFoutUrl(String foutUrl) {
		this.foutUrl = foutUrl;
	}
	
	@Column(name = "fisding")
	public boolean isFisding() {
		return fisding;
	}

	public void setFisding(boolean fisding) {
		this.fisding = fisding;
	}
	
	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public String getFtitle_short() {
		String t = this.getFtitle() ;
		if(t==null){
			return t ;
		}
		if(t.length()>15){
			t = t.substring(0,15) ;
			t+="..." ;
		}
		
		return t;
	}

	public void setFtitle_short(String ftitle_short) {
		this.ftitle_short = ftitle_short;
	}

	@Transient
	public String getFcontent_short() {
		String content = this.getFcontent() ;
		String retf = "" ;
		if(content!=null){
			content = HTMLSpirit.delHTMLTag(content) ;
			if(content.length()<80){
				retf = content+"..." ;
			}else{
				retf = content.substring(0,80)+"..." ;
			}
		}
		
		return retf ;
	}

	public void setFcontent_short(String fcontent_short) {
		this.fcontent_short = fcontent_short;
	}

	@Transient
	public String getFcontent_m() {
		String content = this.getFcontent() ;
		String retf = "" ;
		if(content!=null){
			content = HTMLSpirit.delHTMLTag(content) ;
			if(content.length()<230){
				retf = content+"..." ;
			}else{
				retf = content.substring(0,230)+"..." ;
			}
		}
		
		return retf ;
	}

	public void setFcontent_m(String fcontent_m) {
		this.fcontent_m = fcontent_m;
	}
	
	
    @Transient
	public String getUrl() {
    	if(this.isFisout()){
    		return this.getFoutUrl();
    	}else{
    		return "/service/article.html?id="+this.getFid();
    	}
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "fTitle_cn", length = 1024)
	public String getFtitle_cn() {
		return ftitle_cn;
	}

	public void setFtitle_cn(String ftitle_cn) {
		this.ftitle_cn = ftitle_cn;
	}

	@Column(name = "fContent_cn", length = 65535)
	public String getFcontent_cn() {
		return fcontent_cn;
	}

	public void setFcontent_cn(String fcontent_cn) {
		this.fcontent_cn = fcontent_cn;
	}
	
	/**
	 * 设置：项目方id
	 */
	public void setProjectId(Fuser projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目方id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	public Fuser getProjectId() {
		return projectId;
	}
}