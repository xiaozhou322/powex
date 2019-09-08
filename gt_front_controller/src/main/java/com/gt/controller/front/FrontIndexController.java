package com.gt.controller.front;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.UserStatusEnum;
import com.gt.comm.KeyValues;
import com.gt.controller.BaseController;
import com.gt.entity.CooperateOrgModel;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pdomain;
import com.gt.entity.Pproject;
import com.gt.entity.Psystemconfig;
import com.gt.service.admin.ArticleService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontPprojectService;
import com.gt.service.front.FrontPsystemconfigService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.ConstantKeys;
import com.gt.util.Device;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@Controller
public class FrontIndexController extends BaseController {
	
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontOthersService frontOtherService ;
	@Autowired
	private FrontPprojectService frontPprojectService;
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private FrontPsystemconfigService frontPsystemconfigService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
/*	@RequestMapping("/redirectPage")
	public ModelAndView redirectPage(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="front/index")String pageUrl,
			@RequestParam(required=false,defaultValue="")String param
			){
		ModelAndView modelAndView = new ModelAndView() ;
		if(param!=null&&!"".equals(param)){
			JSONObject json=JSONObject.fromObject(param);
			Iterator<String> it = json.keys(); 
			while(it.hasNext()){
			// 获得key
			String key = it.next(); 
			String value = json.getString(key);    
			modelAndView.addObject(key, value);
			}
			
			
			
		}
	
		modelAndView.setViewName(pageUrl.trim()) ;
		
		return modelAndView;
	}*/
	
	/*@RequestMapping("/index1")
	public ModelAndView index1(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int index,
			@RequestParam(required=false,defaultValue="")String forwardUrl,
			@RequestParam(required=false,defaultValue="0")int symbol,
			HttpServletResponse resp
			){
		
		//推广注册
		try{
			
			String id = "" ;
			if(null!=request.getParameter("invite_code")){
				id = request.getParameter("invite_code") ;
				if(id!=null&&!"".equals(id)){
					List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", id);
					if(services!=null&&services.size()>0){
						Fuser intro = services.get(0) ;
						if(intro!=null){
							resp.addCookie(new Cookie("invite_code", id)) ;
						}
					}
					
				}
			}
		}catch(Exception e){}
		
		try{
			String sn = HtmlUtils.htmlEscape(request.getParameter("sn").trim()) ;
			if(sn != null && sn.length() >0){
				List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", sn);
				if(services!=null && services.size() ==1){
					resp.addCookie(new Cookie("sn", services.get(0).getFuserNo().trim())) ;
				}
			}
		}catch(Exception e){}
		
		ModelAndView modelAndView = new ModelAndView() ;
		//获取活动主题页面
		try{
			if(null!=request.getParameter("agon")){
				resp.addCookie(new Cookie("agon", "agon")) ;
				modelAndView.setViewName("redirect:/user/register.html?phone=cn&lang=zh_CN") ;
				return modelAndView;
			}
		}catch(Exception e){}
		
		if(GetSession(request)==null){
			modelAndView.addObject("forwardUrl",forwardUrl) ;
		}else{
			
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
		}
		
		if(index==1){
			RemoveSecondLoginSession(request) ;
		}
		
		Map<Fvirtualcointype, List<Ftrademapping>> fMap = new TreeMap<Fvirtualcointype, List<Ftrademapping>>(new Comparator<Fvirtualcointype>() {

			public int compare(Fvirtualcointype o1, Fvirtualcointype o2) {
				if(o1.getForder()!=o2.getForder()){
					return o1.getForder().compareTo(o2.getForder());
				}else{
					return o1.getFid().compareTo(o2.getFid());
				}

			}
		}) ;

		List<Fvirtualcointype> fbs =  this.utilsService.list(0, 0, " where ftype=? or ftype=? or ftype=? order by fid desc ", false, Fvirtualcointype.class,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_COIN_VALUE,CoinTypeEnum.FB_USDT_VALUE) ;
		
		//根据域名获取市场列表
		List<Ftrademapping> ftrademappingList = getTrademappingListByDomain(request);
		
		for (Fvirtualcointype fvirtualcointype : fbs) {
			
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			List<Ftrademapping> realTrademappingList = new ArrayList<Ftrademapping>();
			for (int i = 0; i < ftrademappings.size(); i++) {
				for (Ftrademapping ftrademapping : ftrademappingList) {
					if(ftrademappings.get(i).getFid().equals(ftrademapping.getFid())) {
						realTrademappingList.add(ftrademappings.get(i));
					}
				}
			}
			
			if(ftrademappings.size()>0){
				fMap.put(fvirtualcointype, realTrademappingList) ;
			}
		}
		modelAndView.addObject("fMap", fMap) ;
		
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		
		Pdomain pdomain = getReqDomain(request);
		
		List<KeyValues> articles = new ArrayList<KeyValues>() ;
		List<Farticletype> farticletypes = this.frontOtherService.findFarticleTypeAll() ;
		for (int i = 0; i < farticletypes.size(); i++) {
			KeyValues keyValues = new KeyValues() ;
			Farticletype farticletype = farticletypes.get(i) ;
			
			//List<Farticle> farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			List<Farticle> farticles = new ArrayList<Farticle>();
			//i=0时查询公告
			if(i==0){
				//根据域名获取对应的公告
				if(null!=pdomain){
					farticles = this.frontOtherService.findProjectFarticle(pdomain.getProjectId().getFid(), farticletype.getFid(), 0, 3);
				}else{
					farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
				}
			}else{
				farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			}
			
			
			keyValues.setKey(farticletype) ;
			keyValues.setValue(farticles) ;
			articles.add(keyValues) ;
			
		}
		modelAndView.addObject("articles", articles) ;
		
		boolean isDomain = false;
		//根据域名获取logo
		if(null!=pdomain){
			Pproject pproject = frontPprojectService.findByProperty("projectId.fid", pdomain.getProjectId().getFid());
			modelAndView.addObject("logoUrl", pproject.getLogoUrl()) ;
			isDomain = true;
		}
		modelAndView.addObject("isDomain", isDomain) ;
		
		try{
			int alert=1;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("alert")){
					alert=0;
					break ;
				}
			}
			if(alert ==1){
				resp.addCookie(new Cookie("alert", String.valueOf(1))) ;
			}
			modelAndView.addObject("alert", alert) ;
		}catch(Exception e){}
		
		if(GetSession(request) != null){
			//用户钱包
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid()) ;
			Fvirtualwallet usdtfwallet = this.frontUserService.findUSDTWalletByUser(GetSession(request).getFid()) ;
			modelAndView.addObject("fwallet", fwallet) ;
			modelAndView.addObject("usdtfwallet", usdtfwallet) ;
		}
		
		modelAndView.setViewName("front/index") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/index") ;
		}
		

		return modelAndView ;
	}*/
	
	
	@RequestMapping("/index")
	public ModelAndView index(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int index,
			@RequestParam(required=false,defaultValue="")String forwardUrl,
			@RequestParam(required=false,defaultValue="0")int symbol,
			HttpServletResponse resp
			){
		ModelAndView modelAndView = new ModelAndView();
		
		//推广注册
		try{
			String id = "" ;
			if(null!=request.getParameter("invite_code")){
				id = request.getParameter("invite_code") ;
				if(id!=null&&!"".equals(id)){
					List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", id);
					if(services!=null&&services.size()>0){
						Fuser intro = services.get(0) ;
						if(intro!=null){
							resp.addCookie(new Cookie("invite_code", id)) ;
							modelAndView.setViewName("redirect:/user/register.html");
							return modelAndView;
						}
					}
					
				}
			}
		}catch(Exception e){}
		
		//针对IOS手机进行免登录操作
		if (Device.isIOSDevice(request)){
			Fuser fuser = this.GetSession(request);
			//如果不存在登录，则检测是否有Cookie
			if(null==fuser) {
				String token = "";
				Cookie cs[] = request.getCookies() ;
				String ip = getIpAddr(request) ;
				if(null!=cs) {
					for (Cookie cookie : cs) {
						if(cookie.getName().endsWith(Utils.getMD5_32_xx(ip))){
							token=cookie.getValue();
							break ;
						}
					}
					//如果存在token
					if(null!=token & !token.equals("")) {
						String loginKey = this.getToken(token);
						if(loginKey!=null) {
							if (this.isAppLogin(loginKey, false)) {
								fuser = this.getAppFuser(loginKey);
								if (null!=fuser) {
									//进行登录操作
									this.SetSession(fuser, request);
									//清除现有token，重新写入token
									this.removeToken(token);
									String md5Token=this.putKeySession(getSession(request), fuser);
									Cookie mToken = new Cookie(Utils.getMD5_32_xx(ip), md5Token);
									mToken.setMaxAge(24 * 60 * 60);// 设置存在时间为24
									mToken.setPath("/");//设置作用域
									resp.addCookie(mToken);
									
								}
							}
						}
					}
				}
			}
		}
		
		//获取域名
		Pdomain pdomain = getReqDomain(request);
		
		String androidDownloadUrl = "https://www.powex.pro/static/app/powex.pro.apk";
		
		Object mainAndroidDownloadUrl = frontConstantMapService.get("android_download_url");
		if(null != mainAndroidDownloadUrl) {
			androidDownloadUrl = (String)mainAndroidDownloadUrl;
		}
		
		if(null != pdomain) {
			//获取项目方系统配置
			String filter = " where projectId.fid = " + pdomain.getProjectId().getFid();
			Map<String, Object> systemconfigMap = frontPsystemconfigService.findAllMap(filter);
			
			//解析项目方配置的下载地址
			Object domainAndroidDownloadUrl = systemconfigMap.get(ConstantKeys.P_Android_download_url);
			if(null != domainAndroidDownloadUrl) {
				androidDownloadUrl = (String)domainAndroidDownloadUrl;
			}
		}
		modelAndView.addObject("androidDownloadUrl", androidDownloadUrl);
		
		if(null == this.getReqDomain(request)) {
			return this.mainDomainIndex(request, index, forwardUrl, symbol, resp, modelAndView);
		} else {
			return this.secondDomainIndex(request, index, forwardUrl, symbol, resp, modelAndView);
		}
	}
	
	
	/**
	 * 跳转到主域名网站
	 * @param request
	 * @param index
	 * @param forwardUrl
	 * @param symbol
	 * @param resp
	 * @return
	 */
	public ModelAndView mainDomainIndex(HttpServletRequest request,
			int index,String forwardUrl,int symbol,
			HttpServletResponse resp, ModelAndView modelAndView){
		

/*		try{
			String sn = HtmlUtils.htmlEscape(request.getParameter("sn").trim()) ;
			if(sn != null && sn.length() >0){
				List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", sn);
				if(services!=null && services.size() ==1){
					resp.addCookie(new Cookie("sn", services.get(0).getFuserNo().trim())) ;
				}
			}
		}catch(Exception e){}*/
		
		//获取活动主题页面
/*		try{
			if(null!=request.getParameter("agon")){
				resp.addCookie(new Cookie("agon", "agon")) ;
				modelAndView.setViewName("redirect:/user/register.html?phone=cn&lang=zh_CN") ;
				return modelAndView;
			}
		}catch(Exception e){}*/
		
		if(GetSession(request)==null){
			modelAndView.addObject("forwardUrl",forwardUrl) ;
		}else{
			
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
		}
		
		if(index==1){
			RemoveSecondLoginSession(request) ;
		}
		
		Map<Fvirtualcointype, List<Ftrademapping>> fMap = new TreeMap<Fvirtualcointype, List<Ftrademapping>>(new Comparator<Fvirtualcointype>() {

			public int compare(Fvirtualcointype o1, Fvirtualcointype o2) {
				if(o1.getForder()!=o2.getForder()){
					return o1.getForder().compareTo(o2.getForder());
				}else{
					return o1.getFid().compareTo(o2.getFid());
				}

			}
		}) ;

		List<Fvirtualcointype> fbs =  this.utilsService.list(0, 0, " where ftype=? or ftype=? or ftype=? order by fid desc ", false, Fvirtualcointype.class,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_COIN_VALUE,CoinTypeEnum.FB_USDT_VALUE) ;
		
		//根据域名获取市场列表
		List<Ftrademapping> ftrademappingList = getTrademappingListByDomain(request);
		
		for (Fvirtualcointype fvirtualcointype : fbs) {
			
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			List<Ftrademapping> realTrademappingList = new ArrayList<Ftrademapping>();
			for (int i = 0; i < ftrademappings.size(); i++) {
				for (Ftrademapping ftrademapping : ftrademappingList) {
					if(ftrademappings.get(i).getFid().equals(ftrademapping.getFid())) {
						realTrademappingList.add(ftrademappings.get(i));
					}
				}
			}
			
			if(ftrademappings.size()>0){
				fMap.put(fvirtualcointype, realTrademappingList) ;
			}
		}
		modelAndView.addObject("fMap", fMap) ;
		
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		
		Pdomain pdomain = getReqDomain(request);
		
		List<KeyValues> articles = new ArrayList<KeyValues>() ;
		List<Farticletype> farticletypes = this.frontOtherService.findFarticleTypeAll() ;
		for (int i = 0; i < farticletypes.size(); i++) {
			KeyValues keyValues = new KeyValues() ;
			Farticletype farticletype = farticletypes.get(i) ;
			
			//List<Farticle> farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			List<Farticle> farticles = new ArrayList<Farticle>();
			//i=0时查询公告
			if(1 == farticletype.getFid()){
				//根据域名获取对应的公告
				if(null!=pdomain){
					farticles = this.frontOtherService.findProjectFarticle(pdomain.getProjectId().getFid(), farticletype.getFid(), 0, 3);
				}else{
//					farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
					
					StringBuffer filter = new StringBuffer();
					filter.append(" where farticletype.fid="+ farticletype.getFid());
					filter.append(" and projectId.fid= null or projectId.fid= 0 order by fisding desc,id desc ");
					farticles = this.articleService.list(0, 3, filter+"", true);
				}
			}else{
				farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			}
			
			
			keyValues.setKey(farticletype) ;
			keyValues.setValue(farticles) ;
			articles.add(keyValues) ;
			
		}
		modelAndView.addObject("articles", articles) ;
		
		boolean isDomain = false;
		//根据域名获取logo
		if(null!=pdomain){
			Pproject pproject = frontPprojectService.findByProperty("projectId.fid", pdomain.getProjectId().getFid());
			modelAndView.addObject("logoUrl", pproject.getLogoUrl()) ;
			isDomain = true;
		}
		modelAndView.addObject("isDomain", isDomain) ;
		
		try{
			int alert=1;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("alert")){
					alert=0;
					break ;
				}
			}
			if(alert ==1){
				resp.addCookie(new Cookie("alert", String.valueOf(1))) ;
			}
			modelAndView.addObject("alert", alert) ;
		}catch(Exception e){}
		
		if(GetSession(request) != null){
			//用户钱包
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid()) ;
			Fvirtualwallet usdtfwallet = this.frontUserService.findUSDTWalletByUser(GetSession(request).getFid()) ;
			modelAndView.addObject("fwallet", fwallet) ;
			modelAndView.addObject("usdtfwallet", usdtfwallet) ;
		}
		
		//获取当前注册人数
		//int usertotal = adminService.getAllCount("Fuser","");
		int usertotal=frontUserService.getMaxId();
		
		modelAndView.addObject("usertotal", usertotal) ;
		
		modelAndView.setViewName("front/index") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/index") ;
		}

		return modelAndView ;
	}
	
	
	
	/**
	 * 跳转到二级域名网站
	 * @param request
	 * @param index
	 * @param forwardUrl
	 * @param symbol
	 * @param resp
	 * @return
	 */
	public ModelAndView secondDomainIndex(HttpServletRequest request,
			int index,String forwardUrl,int symbol,
			HttpServletResponse resp, ModelAndView modelAndView){
		
		
		
/*		try{
			String sn = HtmlUtils.htmlEscape(request.getParameter("sn").trim()) ;
			if(sn != null && sn.length() >0){
				List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", sn);
				if(services!=null && services.size() ==1){
					resp.addCookie(new Cookie("sn", services.get(0).getFuserNo().trim())) ;
				}
			}
		}catch(Exception e){}*/
		
		//获取活动主题页面
/*		try{
			if(null!=request.getParameter("agon")){
				resp.addCookie(new Cookie("agon", "agon")) ;
				modelAndView.setViewName("redirect:/user/register.html?phone=cn&lang=zh_CN") ;
				return modelAndView;
			}
		}catch(Exception e){}*/
		
		if(GetSession(request)==null){
			modelAndView.addObject("forwardUrl",forwardUrl) ;
		}else{
			
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
		}
		
		if(index==1){
			RemoveSecondLoginSession(request) ;
		}
		
		Map<Fvirtualcointype, List<Ftrademapping>> fMap = new TreeMap<Fvirtualcointype, List<Ftrademapping>>(new Comparator<Fvirtualcointype>() {

			public int compare(Fvirtualcointype o1, Fvirtualcointype o2) {
				if(o1.getForder()!=o2.getForder()){
					return o1.getForder().compareTo(o2.getForder());
				}else{
					return o1.getFid().compareTo(o2.getFid());
				}

			}
		}) ;

		List<Fvirtualcointype> fbs =  this.utilsService.list(0, 0, " where ftype=? or ftype=? or ftype=? order by fid desc ", false, Fvirtualcointype.class,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_COIN_VALUE,CoinTypeEnum.FB_USDT_VALUE) ;
		
		//根据域名获取市场列表
		List<Ftrademapping> ftrademappingList = getTrademappingListByDomain(request);
		
		for (Fvirtualcointype fvirtualcointype : fbs) {
			
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			List<Ftrademapping> realTrademappingList = new ArrayList<Ftrademapping>();
			for (int i = 0; i < ftrademappings.size(); i++) {
				for (Ftrademapping ftrademapping : ftrademappingList) {
					if(ftrademappings.get(i).getFid().equals(ftrademapping.getFid())) {
						realTrademappingList.add(ftrademappings.get(i));
					}
				}
			}
			
			if(ftrademappings.size()>0){
				fMap.put(fvirtualcointype, realTrademappingList) ;
			}
		}
		modelAndView.addObject("fMap", fMap) ;
		
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		
		Pdomain pdomain = getReqDomain(request);
		
		List<KeyValues> articles = new ArrayList<KeyValues>() ;
		List<Farticletype> farticletypes = this.frontOtherService.findFarticleTypeAll() ;
		for (int i = 0; i < farticletypes.size(); i++) {
			KeyValues keyValues = new KeyValues() ;
			Farticletype farticletype = farticletypes.get(i) ;
			
			//List<Farticle> farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			List<Farticle> farticles = new ArrayList<Farticle>();
			//i=0时查询公告
			if(1 == farticletype.getFid()){
				//根据域名获取对应的公告
				if(null!=pdomain){
					farticles = this.frontOtherService.findProjectFarticle(pdomain.getProjectId().getFid(), farticletype.getFid(), 0, 3);
				}else{
//					farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
					
					StringBuffer filter = new StringBuffer();
					filter.append(" where farticletype.fid="+ farticletype.getFid());
					filter.append(" and projectId.fid= null or projectId.fid= 0 order by fisding desc,id desc ");
					farticles = this.articleService.list(0, 3, filter+"", true);
				}
			}else{
				farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			}
			
			
			keyValues.setKey(farticletype) ;
			keyValues.setValue(farticles) ;
			articles.add(keyValues) ;
			
		}
		modelAndView.addObject("articles", articles) ;
		
		boolean isDomain = false;
		//根据域名获取logo
		if(null!=pdomain){
			Pproject pproject = frontPprojectService.findByProperty("projectId.fid", pdomain.getProjectId().getFid());
			modelAndView.addObject("logoUrl", pproject.getLogoUrl()) ;
			isDomain = true;
		}
		modelAndView.addObject("isDomain", isDomain) ;
		
		try{
			int alert=1;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("alert")){
					alert=0;
					break ;
				}
			}
			if(alert ==1){
				resp.addCookie(new Cookie("alert", String.valueOf(1))) ;
			}
			modelAndView.addObject("alert", alert) ;
		}catch(Exception e){}
		
		if(GetSession(request) != null){
			//用户钱包
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid()) ;
			Fvirtualwallet usdtfwallet = this.frontUserService.findUSDTWalletByUser(GetSession(request).getFid()) ;
			modelAndView.addObject("fwallet", fwallet) ;
			modelAndView.addObject("usdtfwallet", usdtfwallet) ;
		}
		
		//获取项目方系统配置
		String filter = " where projectId.fid = " + pdomain.getProjectId().getFid();
		Map<String, Object> systemconfigMap = frontPsystemconfigService.findAllMap(filter);
		
		modelAndView.addObject("systemconfigMap", systemconfigMap) ;
		
		//解析项目方配置的合作伙伴
		String cooperateOrg = (String) systemconfigMap.get(ConstantKeys.P_CooperateOrg);
		if(StringUtils.isNotBlank(cooperateOrg)) {
			JSONArray jsonArr = JSONArray.fromObject(cooperateOrg);
			
			if(jsonArr.size()>0){
				// JSONArray 转 java List
				List<CooperateOrgModel> cooperateOrgModelList = JSONArray.toList(jsonArr, new CooperateOrgModel(), new JsonConfig());
				modelAndView.addObject("cooperateOrgModelList", cooperateOrgModelList) ;
			}
		}
		
		//获取模板配置
		Object templateCfg = systemconfigMap.get(ConstantKeys.P_template);
		if(null == templateCfg || StringUtils.isEmpty((String)templateCfg)) {
			templateCfg = "template1";
		}
		if(templateCfg.equals("template1")) {
			modelAndView.setViewName("front/projectTemplate/templateIndex-1") ;
		} else if(templateCfg.equals("template2")) {
			modelAndView.setViewName("front/projectTemplate/templateIndex-2") ;
		} else if (templateCfg.equals("template3")) {
			modelAndView.setViewName("front/projectTemplate/templateIndex-3") ;
		} else {
			modelAndView.setViewName("front/projectTemplate/templateIndex-1") ;
		}
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/index") ;
		}
		
		return modelAndView ;
	}
	
/*	
	@ResponseBody
	@RequestMapping(value="/index1",produces={JsonEncode})
	public String index1(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int index,
			@RequestParam(required=false,defaultValue="")String forwardUrl,
			@RequestParam(required=false,defaultValue="0")int symbol,
			HttpServletResponse resp
			){
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate("code",1002) ;
		//推广注册
		try{
			
			int id = 0 ;
			if(null!=request.getParameter("r")){
				id = Integer.parseInt(request.getParameter("r")) ;
				if(id!=0){
					Fuser intro = this.frontUserService.findById(id) ;
					if(intro!=null){
						resp.addCookie(new Cookie("r", String.valueOf(id))) ;
					}
				}
			}
		}catch(Exception e){}
		
		try{
			String sn = HtmlUtils.htmlEscape(request.getParameter("sn").trim()) ;
			if(sn != null && sn.length() >0){
				List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", sn);
				if(services!=null && services.size() ==1){
					resp.addCookie(new Cookie("sn", services.get(0).getFuserNo().trim())) ;
				}
			}
		}catch(Exception e){}
		
		ModelAndView modelAndView = new ModelAndView() ;
		//获取活动主题页面
		try{
			if(null!=request.getParameter("agon")){
				resp.addCookie(new Cookie("agon", "agon")) ;
				jsonObject.accumulate("code",1001) ;
				jsonObject.accumulate("redirectUrl","redirect:/user/register.html?phone=cn&lang=zh_CN") ;
				return jsonObject.toString();
			}
		}catch(Exception e){}
		
		if(GetSession(request)==null){
			jsonObject.accumulate("forwardUrl",forwardUrl) ;
		}else{
			
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
		}
		
		if(index==1){
			RemoveSecondLoginSession(request) ;
		}
		
		Map<Object, Object> fMap = new TreeMap<Object, Object>() ;
		JSONArray s0=new JSONArray();
		List<Fvirtualcointype> fbs =  this.utilsService.list(0, 0, " where ftype=? or ftype=? or ftype=? order by fid desc ", false, Fvirtualcointype.class,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_COIN_VALUE,CoinTypeEnum.FB_USDT_VALUE) ;
		for (Fvirtualcointype fvirtualcointype : fbs) {
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			if(ftrademappings.size()>0){
				JSONObject s1=new JSONObject();
				//JSONObject a=JSONObject.fromObject(ftrademappings);
				JSONArray json = JSONArray.fromObject(ftrademappings);
				JSONObject json1 = JSONObject.fromObject(fvirtualcointype);
				s1.accumulate("key", json1);
				s1.accumulate("value", json);
				s0.add(s1);
			}
		}
//		JSONObject s=JSONObject.fromObject(fMap);
		
		jsonObject.accumulate("fMap", s0) ;
		
		int isIndex = 1;
		jsonObject.accumulate("isIndex", isIndex) ;
		
		
		JSONArray articles = new JSONArray() ;
		List<Farticletype> farticletypes = this.frontOtherService.findFarticleTypeAll() ;
		for (int i = 0; i < farticletypes.size(); i++) {
			KeyValues keyValues = new KeyValues() ;
			Farticletype farticletype = farticletypes.get(i) ;
			List<Farticle> farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			JSONArray arti = new JSONArray() ;
			for(Farticle article: farticles){
				JSONObject articlejson=new JSONObject();
				articlejson.accumulate("fid", article.getFid());
				articlejson.accumulate("fcreateDate", Utils.dateFormat(article.getFcreateDate()));
				articlejson.accumulate("ftitle_cn", article.getFtitle_cn());
				articlejson.accumulate("ftitle",  article.getFtitle());
				arti.add(articlejson);
			}
			JSONObject value=new JSONObject();
			value.accumulate("value", arti);
			articles.add(value) ;
			
		}
		jsonObject.accumulate("articles", articles) ;
		
		try{
			int alert=1;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("alert")){
					alert=0;
					break ;
				}
			}
			if(alert ==1){
				resp.addCookie(new Cookie("alert", String.valueOf(1))) ;
			}
			jsonObject.accumulate("alert", alert) ;
		}catch(Exception e){}
		
		if(GetSession(request) != null){
			//用户钱包
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid()) ;
			Fvirtualwallet usdtfwallet = this.frontUserService.findUSDTWalletByUser(GetSession(request).getFid()) ;
			//jsonObject.accumulate("fwallet", JSONObject.fromObject(fwallet)) ;
			//jsonObject.accumulate("usdtfwallet", JSONObject.fromObject(usdtfwallet)) ;
		}
		
		return jsonObject.toString() ;
	}
	*/
	
	/*@ResponseBody
	@RequestMapping(value="/getValidate",produces={JsonEncode})
	public String getValidate(
			HttpServletRequest request,
			@RequestParam(required=true)String loginName,
			HttpServletResponse resp
			){
		JSONObject jsonObject = new JSONObject() ;
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("floginName", loginName);
		if(fusers==null||fusers.size()!=1){			
				jsonObject.accumulate("code", 1001) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.noaccount")) ;
				return jsonObject.toString() ;			
		}else{
			Fuser user=fusers.get(0);
			
			jsonObject.accumulate("code", 1002) ;
			jsonObject.accumulate("fisTelephoneBind", user.isFisTelephoneBind()) ;
			jsonObject.accumulate("fisMailValidate", user.getFisMailValidate()) ;
			jsonObject.accumulate("fgoogleBind", user.getFgoogleBind()) ;
			return jsonObject.toString() ;	
		}
	}*/
	
}
