package com.gt.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fentrust;
import com.gt.entity.Flimittrade;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.PaginUtil;
import com.gt.util.Utils;




@Controller
public class FrontTradeController extends BaseController {
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontCacheService frontCacheService;
	
	
	/*https://www.okcoin.com/trade/entrust.do?symbol=1
	 * */
	@RequestMapping("/trade/entrust")
	public ModelAndView entrust(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="0")int status,
			@RequestParam(required=false,defaultValue="1")int currentPage
			)throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null){
			List<Ftrademapping> ftrademapactive = this.ftradeMappingService.findActiveTradeMapping();
			if(ftrademapactive==null || ftrademapactive.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			modelAndView.setViewName("redirect:/trade/entrust.html?symbol="+ftrademapactive.get(0).getFid()+"&status="+status) ;
			return modelAndView ;
		}
		
		List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMapping() ;
		
		
		int fstatus[] = null ;
		
		if(status<=0){
			status=0;
		}else{
			status=1;
		}
		
		if(status==0){
			//正在委托
			fstatus = new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal} ;
		}else{
			//委托完成
			fstatus = new int[]{EntrustStatusEnum.AllDeal,EntrustStatusEnum.Cancel} ;
		}
		
		List<Fentrust> fentrusts = 
				this.frontTradeService.findFentrustHistory(
						GetSession(request).getFid(), 
						ftrademapping.getFid(),
						null, PaginUtil.firstResult(currentPage, maxResults), 
						maxResults, 
						"id desc ", fstatus) ;
		int total = this.frontTradeService.findFentrustHistoryCount(
				GetSession(request).getFid(), 
				ftrademapping.getFid(),
				null,fstatus) ;
		String pagin = PaginUtil.generatePagin((int)(total/maxResults+(total%maxResults==0?0:1) ), currentPage, "/trade/entrust.html?symbol="+symbol+"&status="+status+"&") ;

		modelAndView.addObject("currentPage", currentPage) ;
		modelAndView.addObject("pagin",pagin) ;
		modelAndView.addObject("fentrusts", fentrusts) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("status", status) ;
		modelAndView.setViewName("front/trade/trade_entrust") ;
		
		
		if(this.isMobile(request))
		{
			int totalPage = total/maxResults+(total%maxResults==0?0:1);
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.setViewName("mobile/trade/trade_entrust") ;
			if(currentPage>1)//ajax 加载
			{
				modelAndView.setViewName("mobile/trade/trade_entrust_ajax") ;
			}
		}
		
		return modelAndView ;
	}
	

	@RequestMapping("/trade/type")
	public ModelAndView type(
			@RequestParam(required=false,defaultValue="0")int type
			){
		
		//限制法币为人民币和比特币
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(type) ;
		if(fvirtualcointype==null 
				||(fvirtualcointype.getFtype() != CoinTypeEnum.FB_CNY_VALUE && fvirtualcointype.getFtype() != CoinTypeEnum.FB_COIN_VALUE  && fvirtualcointype.getFtype() != CoinTypeEnum.FB_USDT_VALUE)
				|| fvirtualcointype.getFstatus() != VirtualCoinTypeStatusEnum.Normal){
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		List<Ftrademapping> ftrademappings = this.utilsService.list(0, 1, " where fvirtualcointypeByFvirtualcointype1.fid=? and fstatus=? order by fid asc ", true, Ftrademapping.class,fvirtualcointype.getFid(),TrademappingStatusEnum.ACTIVE) ;
		if(ftrademappings.size()>0){
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("redirect:/trade/coin.html?coinType="+ftrademappings.get(0).getFid()+"&tradeType=0") ;
			return modelAndView ;
		}else{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
	}
	
	@RequestMapping("/trade/coin")
	public ModelAndView coin(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int coinType,
			@RequestParam(required=false,defaultValue="0")int tradeType
			) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		int userid = 0;
		Fuser fuser = null;
		boolean isTelephoneBind =false;
		if(GetSession(request) != null){
			fuser = this.frontUserService.findById(GetSession(request).getFid());
			userid = fuser.getFid();
			isTelephoneBind = fuser.isFisTelephoneBind();
		}
		
		
		tradeType = tradeType < 0? 0: tradeType ;
		tradeType = tradeType > 1? 1: tradeType ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(coinType) ;
		List<Ftrademapping> ftrademappings = null ;
		if(ftrademapping==null ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID ){
			ftrademappings = this.utilsService.list(0, 1, " where fstatus=? order by fid asc ", true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/trade/coin.html?coinType="+ftrademappings.get(0).getFid()+"&tradeType=0") ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		//限制法币为人民币和比特币和USDT
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_USDT_VALUE){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		
		ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(ftrademapping.getFvirtualcointypeByFvirtualcointype1()) ;
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		
		
		boolean isTradePassword = false;
		if(userid !=0 && fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
			isTradePassword = true;
		}
		
		Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
		boolean isLimittrade = false;
		double upPrice = 0d;
		double downPrice = 0d;
		if(limittrade != null){
			isLimittrade = true;
			upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
			downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
			if(downPrice <0) downPrice=0;
		}
		modelAndView.addObject("isLimittrade", isLimittrade) ;
		modelAndView.addObject("upPrice", upPrice) ;
		modelAndView.addObject("downPrice", downPrice) ;
		
		//委托记录
		List<Fentrust> fentrusts = this.frontTradeService.findFentrustHistory(
				userid, coinType,null, 0, 10, " fid desc ", new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal}) ;
		
		//是否需要输入交易密码
		modelAndView.addObject("needTradePasswd", super.isNeedTradePassword(request)) ;
				
		String tradestock = this.frontCacheService.getJsonString(coinType, 0) ; 
		modelAndView.addObject("tradestock", tradestock) ;
		modelAndView.addObject("fentrusts", fentrusts) ;
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("userid", userid) ;
		modelAndView.addObject("isTradePassword", isTradePassword) ;
		modelAndView.addObject("isTelephoneBind", isTelephoneBind) ;
		modelAndView.addObject("recommendPrizesell", this.frontCacheService.getHighestBuyPrize(coinType)) ;
		modelAndView.addObject("recommendPrizebuy", this.frontCacheService.getLowestSellPrize(coinType)) ;
		modelAndView.addAllObjects(this.setRealTimeData(coinType)) ;
		modelAndView.addObject("coin1",coin1) ;
		modelAndView.addObject("coin2",coin2) ;
		modelAndView.addObject("ftrademappings",ftrademappings) ;
		modelAndView.addObject("ftrademapping",ftrademapping) ;
		modelAndView.addObject("coinType", coinType) ;
		modelAndView.addObject("tradeType", tradeType) ;
		modelAndView.setViewName("front/trade/trade_coin") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/trade/trade_coin") ;
		}
		
		return modelAndView ;
	}
	
	
	/*
	 * @param type:0未成交前十条，1成交前10条
	 * @param symbol:1币种
	 * */
	@RequestMapping("/trade/entrustInfo")
	public ModelAndView entrustInfo(
			HttpServletRequest request,
			@RequestParam(required=true)int type,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)int tradeType
			) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		int userid = 0;
		if(GetSession(request) != null){
			userid = GetSession(request).getFid();
		}
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null || ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;

		
		
		List<Fentrust> fentrusts1 = null ;
		fentrusts1 = this.frontTradeService.findFentrustHistory(
				userid, symbol,null, 0, 10, " fid desc ", new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal}) ;
		
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("coin1", coin1) ;
		modelAndView.addObject("coin2", coin2) ;
		modelAndView.addObject("tradeType", tradeType) ;
		modelAndView.addObject("symbol",symbol) ;
		modelAndView.addObject("type", type) ;
		modelAndView.addObject("fentrusts1", fentrusts1) ;
		modelAndView.setViewName("front/trade/entrust_info") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/trade/entrust_info") ;
		}
		return modelAndView ;
	}
}
