package com.gt.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.TrademappingStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Farticle;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Pdepositcointype;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FtradeMappingService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Controller
public class AppController extends BaseController {

	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FtradeMappingService ftradeMappingService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	@RequestMapping("/app/article")
	public ModelAndView article(
			@RequestParam(required=true )int id
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Farticle farticle = this.frontOthersService.findFarticleById(id) ;
		modelAndView.addObject("farticle",farticle) ;
		modelAndView.setViewName("app/article") ;
		return modelAndView ;
	}
	
	@RequestMapping("/real/appkline")
	public ModelAndView appkline(
			@RequestParam(required=true )int symbol
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		
		modelAndView.setViewName("app/kline/kline") ;
		return modelAndView ;
	}
	

	@ResponseBody
	@RequestMapping("/real/appdepth")
	public String appdepth(
			@RequestParam(required=true )int symbol
			) throws Exception {
		//Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		JSONArray ret = new JSONArray() ;
		JSONArray buy = new JSONArray() ;
		JSONArray sell = new JSONArray() ;
		
		/*double val = this.frontCacheService.getLatestDealPrize(symbol) ;
		double low = val*0.3D ;
		double high = val*3D ;*/
		
	//	Fentrust[] buyArray = this.frontCacheService.getBuyDepthMap(fvirtualcointype.getFid(),10) ;
		
		String sellEntrustsString = this.frontCacheService.getSellDepthMap(symbol);
		JSONArray sellArray  =JSONArray.fromObject(sellEntrustsString);
		String buyEntrustsString = this.frontCacheService.getBuyDepthMap(symbol);
		JSONArray buyArray = JSONArray.fromObject(buyEntrustsString);
/*		

		if (sellEntrustsList != null) {
			for (int i = 0; i < sellEntrustsList.size() && i < 20; i++){
				String fentrustString = sellEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				
				JSONObject item = new JSONObject() ;
				item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[0]), count1)+"") ;
				item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[1]), count2)+"") ;
				sellArray.add(item) ;
			}
		}
		
		if (buyEntrustsList != null) {
			for (int i = 0; i < buyEntrustsList.size() && i < 20 ; i++) {
				String fentrustString = buyEntrustsList.get(i);
				String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
				
				JSONObject item = new JSONObject() ;
				item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[0]), count1)+"") ;
				item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[1]), count2)+"") ;
				buyArray.add(item) ;
			}
		}*/
		for (Object object : buyArray) {
			/*JSONObject fentrust =JSONObject.fromObject(object);
			
			if(fentrust.getInt("price")<low||fentrust.getInt("price")>high){
				continue ;
			}
			
			JSONArray item = new JSONArray() ;
			item.add(fentrust.getInt("price")) ;
			item.add(fentrust.getInt("amount")) ;
			item.add(0) ;
			
			buy.add(item) ;*/
			
			JSONArray fentrust =JSONArray.fromObject(object);
			buy.add(fentrust) ;
		}
		
		//Fentrust[] sellArray = this.realTimeData.getSellDepthMap(fvirtualcointype.getFid(),10) ;
		for (Object object : sellArray) {
			
			/*JSONObject fentrust =JSONObject.fromObject(object);
			if(fentrust.getInt("price")<low||fentrust.getInt("price")>high){
				continue ;
			}
			
			JSONArray item = new JSONArray() ;
			item.add(fentrust.getInt("price")) ;
			item.add(fentrust.getInt("amount")) ;
			item.add(0) ;
			
			sell.add(item) ;*/
			
			JSONArray fentrust =JSONArray.fromObject(object);
			sell.add(fentrust) ;
		}
		
		
		ret.add(buy) ;
		ret.add(sell) ;
		return ret.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/real/appperiod")
	public String appperiod(
			@RequestParam(required=true )int symbol
			) throws Exception {
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		
		StringBuffer content = new StringBuffer(
				"chart = {" +
				" symbol : \""+ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()+
				"_"+ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfShortName()+"\"," +
				" symbol_view : \""+ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()+
				"/"+ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfShortName()+"\"," +
				" ask : 1.2," +
				" time_line :" ) ;

		JSONObject timeLine = new JSONObject() ;
		String m5 = this.frontCacheService.getJsonString(symbol, 2) ;
		String m15 = this.frontCacheService.getJsonString(symbol, 3) ;
		String m30 = this.frontCacheService.getJsonString(symbol, 4) ;
		String h1 = this.frontCacheService.getJsonString(symbol, 5) ;
		String h8 = this.frontCacheService.getJsonString(symbol, 8) ;
		String d1 = this.frontCacheService.getJsonString(symbol, 10) ;
		timeLine.accumulate("5m", m5) ;
		timeLine.accumulate("15m", m15) ;
		timeLine.accumulate("30m", m30) ;
		timeLine.accumulate("1h", h1) ;
		timeLine.accumulate("8h", h8) ;
		timeLine.accumulate("1d", d1) ;
		
		content.append(timeLine.toString()) ;
		
		content.append("};") ;
		
		return content.toString() ;
	}
	
	
	
	/*
	 * https://www.okcoin.com/market.do?symbol=0
	 * */
	@RequestMapping("/real/trademarket")
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
		if(null!=ftrademapping.getFprojectId() && ftrademapping.getFprojectId()>0){
			Pdepositcointype pdepositcointype = (Pdepositcointype) frontConstantMapService.get("pdepositcointype");
			if(null != pdepositcointype){
				modelAndView.addObject("coinName", pdepositcointype.getCointypeId().getfShortName()) ;
				modelAndView.addObject("coinmount", pdepositcointype.getDepositLimit()) ;
			}
		}
		
		
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
		modelAndView.setViewName("app/market/trademarket") ;
		
		return modelAndView ;
	}
}
