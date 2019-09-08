package com.gt.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.TrademappingStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositcointype;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPcointypeService;
import com.gt.service.front.FrontPdepositService;
import com.gt.service.front.FrontPdepositcointypeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;


@Controller
public class FrontMarketController extends BaseController {

	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private FrontPcointypeService frontPcointypeService;
	@Autowired
	private FrontPdepositService frontPdepositService;
	@Autowired
	private FrontPdepositcointypeService frontPdepositcointypeService;
	/*
	 * https://www.okcoin.com/market.do?symbol=0
	 * */
	@RequestMapping("/trademarket")
	public ModelAndView trademarket(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
//		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=?" , false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		
		//根据域名获取市场列表
		List<Ftrademapping> ftrademappings = getTrademappingListByDomain(request);
		
		if(ftrademapping==null || ftrademapping.getFstatus() != TrademappingStatusEnum.ACTIVE){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/trademarket.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
//		Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
//		boolean isLimittrade = false;
//		double upPrice = 0d;
//		double downPrice = 0d;
//		if(limittrade != null){
//			isLimittrade = true;
//			upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
//			downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
//			if(downPrice <0) downPrice=0;
//		}
//		modelAndView.addObject("isLimittrade", isLimittrade) ;
//		modelAndView.addObject("upPrice", upPrice) ;
//		modelAndView.addObject("downPrice", downPrice) ;
		
		String userid = "";
		boolean tradePassword = false;
		boolean isTelephoneBind = false;
		boolean isGoogleBind = false;
		boolean isForceAppUser = false;
		int login = 0;
		int userlevel = 1;
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
			userid = String.valueOf(fuser.getFid());
			if(fuser.isFisTelephoneBind()){
				isTelephoneBind = true;
			}
			if(fuser.getFgoogleBind()){
				isGoogleBind = true;
			}
			if(fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
				tradePassword = true;
			}
			login = 1;
			userlevel = fuser.getFscore().getFlevel();
			
			String forceAppApiUid = this.frontConstantMapService.getString("forceAppApiUid_"+symbol);
			if(null!=forceAppApiUid && !forceAppApiUid.equals("") && !forceAppApiUid.equals(fuser.getFregfrom())) {
				isForceAppUser = true;
			}
		}
		double ffee = this.frontCacheService.getRates(ftrademapping.getFid(),false,userlevel);
		double fbuyfee = this.frontCacheService.getRates(ftrademapping.getFid(),true,userlevel);	
		if(null!=ftrademapping.getFprojectId() && ftrademapping.getFprojectId()>0){
			Pcointype pcointype = frontPcointypeService.findByCoinId(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid());
			if( null != pcointype && null != pcointype.getDepositId()) {
				String filter = " where cointypeId.id =" + pcointype.getDepositId();
				List<Pdeposit> pdepositList = frontPdepositService.list(0, 0, filter, false);
				if(null != pdepositList && pdepositList.size() > 0) {
					Pdeposit pdeposit = pdepositList.get(0);
					
					Pdepositcointype pdepositcointype= frontPdepositcointypeService.findById(pdeposit.getDepositCointypeId().getId());
					
					modelAndView.addObject("coinmount", pdeposit.getAvailableAmount()) ;
					if(null != pdepositcointype){
						modelAndView.addObject("coinName", pdepositcointype.getCointypeId().getfShortName()) ;
					}
				}
			}
//			Pdepositcointype pdepositcointype = (Pdepositcointype) frontConstantMapService.get("pdepositcointype");
		}
		
		modelAndView.addObject("isForceAppUser", isForceAppUser) ;
		modelAndView.addObject("ffee", ffee) ;
		modelAndView.addObject("fbuyfee", fbuyfee) ;
		modelAndView.addObject("userid", userid) ;
		modelAndView.addObject("tradePassword", tradePassword) ;
		modelAndView.addObject("isTelephoneBind", isTelephoneBind) ;
		modelAndView.addObject("isGoogleBind", isGoogleBind) ;
		modelAndView.addObject("login", login) ;
		
		//是否需要输入交易密码
		modelAndView.addObject("needTradePasswd", super.isNeedTradePassword(request)) ;
		
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front/market/trademarket") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/market/trademarket") ;
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("/tradeindex")
	public ModelAndView tradeindex(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=?" , false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		if(ftrademapping==null || ftrademapping.getFstatus() != TrademappingStatusEnum.ACTIVE){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/trademarket.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		

		
		String userid = "";
		boolean tradePassword = false;
		boolean isTelephoneBind = false;
		boolean isGoogleBind = false;
		int login = 0;
		int userlevel = 1;
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
			userid = String.valueOf(fuser.getFid());
			if(fuser.isFisTelephoneBind()){
				isTelephoneBind = true;
			}
			if(fuser.getFgoogleBind()){
				isGoogleBind = true;
			}
			if(fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
				tradePassword = true;
			}
			login = 1;
			userlevel = fuser.getFscore().getFlevel();
		}
		double ffee = this.frontCacheService.getRates(ftrademapping.getFid(),false,userlevel);
		double fbuyfee = this.frontCacheService.getRates(ftrademapping.getFid(),true,userlevel);
		modelAndView.addObject("ffee", ffee) ;
		modelAndView.addObject("fbuyfee", fbuyfee) ;
		modelAndView.addObject("userid", userid) ;
		modelAndView.addObject("tradePassword", tradePassword) ;
		modelAndView.addObject("isTelephoneBind", isTelephoneBind) ;
		modelAndView.addObject("isGoogleBind", isGoogleBind) ;
		modelAndView.addObject("login", login) ;
		
		//是否需要输入交易密码
		modelAndView.addObject("needTradePasswd", super.isNeedTradePassword(request)) ;
		
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front/market/tradeindex") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/market/tradeindex") ;
		}
		
		return modelAndView ;
	}
	
	
	@RequestMapping("/market")
	public ModelAndView market(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=?" , false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		if(ftrademapping==null || ftrademapping.getFstatus() != TrademappingStatusEnum.ACTIVE){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/market.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front/market/market") ;
		
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/market/market") ;
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("/kline/fullstart")
	public ModelAndView start(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="86400")int step
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 1, " where fstatus=? " , true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;

		
		if(ftrademapping==null ){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/kline/fullstart.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("step", step) ;
		modelAndView.setViewName("front/market/start") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/market/start") ;
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("/kline/h5")
	public ModelAndView h5kline(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="86400")int step
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 1, " where fstatus=? " , true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		if(ftrademapping==null ){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/kline/h5.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("step", step) ;
		modelAndView.setViewName("mobile/market/start_newer") ;
		return modelAndView ;
	}
	
	@RequestMapping("/kline/fullstart2018")
	public ModelAndView start2018(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="86400")int step
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 1, " where fstatus=? " , true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;

		
		if(ftrademapping==null ){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/kline/fullstart2018.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("step", step) ;
		modelAndView.setViewName("front/market/start_tradingview") ;
		return modelAndView ;
	}
	
}
