package com.gt.controller.front;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.NperStatusEnum;
import com.gt.Enum.UserStatusEnum;
import com.gt.comm.KeyValues;
import com.gt.controller.BaseController;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Nper;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.LotteryService;
import com.gt.service.front.NperService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;


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
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private NperService nperService;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private FvirtualWalletService fvirtualWalletService;
	
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
	
	@RequestMapping("/index")
	public ModelAndView index(
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
		
/*		try{
			String sn = HtmlUtils.htmlEscape(request.getParameter("sn").trim()) ;
			if(sn != null && sn.length() >0){
				List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", sn);
				if(services!=null && services.size() ==1){
					resp.addCookie(new Cookie("sn", services.get(0).getFuserNo().trim())) ;
				}
			}
		}catch(Exception e){}*/
		
		ModelAndView modelAndView = new ModelAndView() ;
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
		for (Fvirtualcointype fvirtualcointype : fbs) {
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			if(ftrademappings.size()>0){
				fMap.put(fvirtualcointype, ftrademappings) ;
			}
		}
		modelAndView.addObject("fMap", fMap) ;
		
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		
		
		List<KeyValues> articles = new ArrayList<KeyValues>() ;
		List<Farticletype> farticletypes = this.frontOtherService.findFarticleTypeAll() ;
		for (int i = 0; i < farticletypes.size(); i++) {
			KeyValues keyValues = new KeyValues() ;
			Farticletype farticletype = farticletypes.get(i) ;
			List<Farticle> farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 3) ;
			keyValues.setKey(farticletype) ;
			keyValues.setValue(farticles) ;
			articles.add(keyValues) ;
			
		}
		modelAndView.addObject("articles", articles) ;
		
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
		
		String awardsStartTime = frontConstantMapService.get("awardsStartTime").toString();
		modelAndView.addObject("awardsStartTime", awardsStartTime) ;
		//int usertotal = adminService.getAllCount("Fuser","");
		double totalfwalle=adminService.getSQLValue("select sum(ftotal+ffrozen) from fvirtualwallet where fVi_fId=5");
		int usertotal=frontUserService.getMaxId();
		modelAndView.addObject("totalfwalle", totalfwalle) ;		
		modelAndView.addObject("usertotal", usertotal) ;
		
		
		modelAndView.setViewName("front/index") ;
		
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
	
	@ResponseBody
	@RequestMapping(value="/getNewLottery",produces={JsonEncode})
	public String getNewLottery(HttpServletRequest request){
		JSONObject result = new JSONObject();
		String uid = request.getParameter("uid");
		String num = request.getParameter("num");
		if(Utils.isNull(uid)){
			result.accumulate("data", "");
			result.accumulate("code", 500);
			result.accumulate("msg", "uid is null");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		List<Nper> list = nperService.findByProperty("status", NperStatusEnum.START_ING);
		if(null == list || list.size()<=0){
			result.accumulate("code", -1);
			result.accumulate("msg", "活动不存在");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		Nper nper = list.get(0);
		if(null == nper.getStart_time() ||
				(null != nper.getStart_time() && Utils.getTimestamp().getTime() < nper.getStart_time().getTime())){
			result.accumulate("data", "");
			result.accumulate("code", -1);
			result.accumulate("msg", "本次活动已结束或未开启");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		
		List<String> li = new ArrayList<String>();
		if(Utils.isNull(num)){
			num = "1";
		}
		int count = Integer.valueOf(num);
		if(count <= 10){
			for(int i = 0;i<count;i++){
				String lottery_no = lotteryService.saveNewNper(nper, Integer.valueOf(uid));
				if(!Utils.isNull(lottery_no)){
					li.add(lottery_no);
				}
			}
		}else{
			final Nper nper_ = nper;
			final String uid_ = uid;
			final int count_ = count;
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = 0;i<count_;i++){
						lotteryService.saveNewNper(nper_, Integer.valueOf(uid_));
					}
				}
			}).start();
			result.accumulate("msg", "后台正在处理中，请稍后查看");
		}
	
		result.accumulate("data", li);
		result.accumulate("code", 200);
		result.accumulate("time", Utils.getTimestamp().getTime());
		return result.toString();
	}
	
	/*@ResponseBody
	@RequestMapping(value="/getNewLottery1",produces={JsonEncode})
	public String getNewLottery1(HttpServletRequest request){
		JSONObject result = new JSONObject();
		
		Fuser user = GetSession(request);
		
		if (user.getFstatus() != 1) {
			result.accumulate("code", "-1");
			result.accumulate("msg", "账号异常");
			result.accumulate("time", Utils.getTimestamp().getTime());
			result.toString();

		}
		if (!user.getFhasRealValidate() || !user.getFpostRealValidate()) {
			result.accumulate("code", "-1");
			result.accumulate("msg", "用户未进行CYK1实名认证");
			result.accumulate("time", Utils.getTimestamp().getTime());
			result.toString();

		}
		if (!user.isFhasImgValidate() || !user.isFpostImgValidate()) {
			result.accumulate("code", "-1");
			result.accumulate("msg", "用户未进行CYK2认证");
			result.accumulate("time", Utils.getTimestamp().getTime());
			result.toString();

		}
		
		String num = request.getParameter("num");
		List<Nper> list = nperService.findByProperty("status", NperStatusEnum.START_ING);
		if(null == list || list.size()<=0){
			result.accumulate("code", -1);
			result.accumulate("msg", "活动不存在");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		Nper nper = list.get(0);
		if(null == nper.getStart_time() ||
				(null != nper.getStart_time() && Utils.getTimestamp().getTime() < nper.getStart_time().getTime())){
			result.accumulate("data", "");
			result.accumulate("code", -1);
			result.accumulate("msg", "本次活动已结束或未开启");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		
		if(Utils.isNull(num)){
			num = "1";
		}
		int count = Integer.valueOf(num);
		
		Fvirtualwallet fvirtualwallet = fvirtualWalletService.findFvirtualwallet(user.getFid(), nper.getLottery_coin_type());
		if(fvirtualwallet == null || fvirtualwallet.getFtotal() < count*nper.getLottery_amount()){
			result.accumulate("data", "");
			result.accumulate("code", -1);
			result.accumulate("msg", "余额不足");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() - count*nper.getLottery_amount());
		fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() + count*nper.getLottery_amount());
		fvirtualWalletService.attachDirty(fvirtualwallet);

		
		fvirtualwallet = fvirtualWalletService.findFvirtualwallet(user.getFid(), nper.getLottery_coin_type());
		List<String> li = new ArrayList<String>();
		if(count <= 10){
			int validNum = 0;
			try {
				for(int i = 0;i<count;i++){
					String lottery_no = lotteryService.saveNewNper(nper, user.getFid());
					if(!Utils.isNull(lottery_no)){
						li.add(lottery_no);
						validNum++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() + (count - validNum)*nper.getLottery_amount());
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() - count*nper.getLottery_amount());
			fvirtualWalletService.attachDirty(fvirtualwallet);
		}else{
			final Nper nper_ = nper;
			final int uid_ = user.getFid();
			final int count_ = count;
			final Fvirtualwallet fvirtualwallet_ = fvirtualwallet;
			new Thread(new Runnable() {
				@Override
				public void run() {
					int validNum = 0;
					try {
						for(int i = 0;i<count_;i++){
							String lottery_no = lotteryService.saveNewNper(nper_, uid_);
							if(!Utils.isNull(lottery_no)){
								validNum++;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					fvirtualwallet_.setFtotal(fvirtualwallet_.getFtotal() + (count_ - validNum));
					fvirtualwallet_.setFfrozen(fvirtualwallet_.getFfrozen() - count_*nper_.getLottery_amount());
					fvirtualWalletService.attachDirty(fvirtualwallet_);
				}
			}).start();
			result.accumulate("msg", "后台正在处理中，请稍后查看");
		}
	
		result.accumulate("data", li);
		result.accumulate("code", 200);
		result.accumulate("time", Utils.getTimestamp().getTime());
		return result.toString();
	}*/
	
}
