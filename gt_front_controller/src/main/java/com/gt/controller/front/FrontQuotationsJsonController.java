package com.gt.controller.front;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pdomain;
import com.gt.entity.Psystemconfig;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPdomainService;
import com.gt.service.front.FrontPsystemconfigService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;
import com.gt.util.redis.RedisKey;

@Controller
public class FrontQuotationsJsonController extends BaseController {
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontCacheService frontCacheService;		
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontPdomainService frontPdomainService;
	@Autowired
	private FrontPsystemconfigService frontPsystemconfigService;
	
	@ResponseBody
	@RequestMapping(value="/real/indexmarket",produces={JsonEncode})
	public String indexmarket(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject() ;
		//加权参数
		Double adjustnum = 1d;
		if (frontConstantMapService.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(frontConstantMapService.getString("adjustvalue"));
		}
		
		if(adjustnum<0.75){
			adjustnum= 0.75;
		}
		if (adjustnum>1.85){
			adjustnum =1.85;
		}
		
		//根据域名获取市场列表
		List<Ftrademapping> ftrademappings = getTrademappingListByDomain(request);
		
//		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=? ", false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		for (Ftrademapping ftrademapping : ftrademappings) {
			JSONObject js = new JSONObject() ;
			js.accumulate("symbol", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfSymbol());
			js.accumulate("price", Utils.getDouble(this.frontCacheService.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
			js.accumulate("total", Utils.getDouble(this.frontCacheService.getTotal(ftrademapping.getFid())*adjustnum, ftrademapping.getFcount2()));
			js.accumulate("low", Utils.getDouble(this.frontCacheService.getLowest(ftrademapping.getFid()), ftrademapping.getFcount1()));
			js.accumulate("high", Utils.getDouble(this.frontCacheService.getHighest(ftrademapping.getFid()), ftrademapping.getFcount1()));
			js.accumulate("pbits", ftrademapping.getFcount1());
			js.accumulate("vbits", ftrademapping.getFcount2());
			
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==1){
				js.accumulate("fbprice",  Utils.getDouble(Double.valueOf(this.frontCacheService.getLatestDealPrize(1)), 2));
			}else if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==3){
				js.accumulate("fbprice",  Utils.getDouble(Double.valueOf(this.frontCacheService.getLatestDealPrize(2)), 2));
			}else{
				js.accumulate("fbprice",  1);
			}
			js.accumulate("usdtrate",  this.frontConstantMapService.get("USDT_PRICE").toString());
			js.accumulate("total24RMB",  this.frontCacheService.get24Total(ftrademapping.getFid()));
			
			
			double s = this.frontCacheService.get24Price(ftrademapping.getFid());

			List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)frontConstantMapService.get("tradehistory");
			for (Ftradehistory ftradehistory : ftradehistorys) {
				if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
					s= ftradehistory.getFprice();
					break;
				}
			}
			
			
			double last = 0d;
			try {
				last = Utils.getDouble((this.frontCacheService.getLatestDealPrize(ftrademapping.getFid())-s)/s*100, 2);
			} catch (Exception e) {}
			js.accumulate("rose", last);
			JSONArray dataArray = new JSONArray();
			try {
				String content = this.frontCacheService.getJsonString(ftrademapping.getFid(), 5) ;
				JSONArray jsonArray = JSONArray.fromObject(content) ;
				if(jsonArray != null && jsonArray.size() >0){
					for(int i=(jsonArray.size()-72 >=0?jsonArray.size()-72:0);i<jsonArray.size();i++){
						JSONArray retItem = new JSONArray() ;
						JSONArray item = jsonArray.getJSONArray(i) ;
						retItem.add(item.getString(0)) ;
						retItem.add(item.getString(1)) ;
						retItem.add(item.getString(2)) ;
						retItem.add(item.getString(3)) ;
						retItem.add(item.getString(4)) ;
						retItem.add(item.getString(5)) ;
						dataArray.add(retItem);
					}
				}
			} catch (Exception e) {}
			
			js.accumulate("data", dataArray);
			jsonObject.accumulate(String.valueOf(ftrademapping.getFid()), js);
		}
		
		return jsonObject.toString();
	}
	
/*	
	@ResponseBody
	@RequestMapping(value="/real/switchlan",produces={JsonEncode})
	public String switchlan(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true) int lang
			){
		JSONObject jsonObject = new JSONObject() ;
		this.setLang(lang, request, response);
		jsonObject.accumulate("code", 0);
		return jsonObject.toString();
	}
	
	*/
	@ResponseBody
	@RequestMapping("/real/userassets")
	public String userassets(
			HttpServletRequest request,
			@RequestParam(required=true)int symbol
			) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		Fuser fuser = GetSession(request) ;
		if(fuser==null){
			//可用
			jsonObject.accumulate("availableCny", 0) ;
			jsonObject.accumulate("availableCoin", 0) ;
			jsonObject.accumulate("frozenCny", 0) ;
			jsonObject.accumulate("frozenCoin", 0) ;
			//借貸明細
			JSONObject leveritem = new JSONObject() ;
			leveritem.accumulate("total", 0) ;
			leveritem.accumulate("asset", 0) ;
			leveritem.accumulate("score", 0) ;
			jsonObject.accumulate("leveritem", leveritem) ;
			//人民幣明細
			JSONObject cnyitem = new JSONObject() ;
			cnyitem.accumulate("total", 0) ;
			cnyitem.accumulate("frozen",0) ;
			cnyitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("cnyitem", cnyitem) ;
			//人民幣明細
			JSONObject coinitem = new JSONObject() ;
			coinitem.accumulate("id", symbol) ;
			coinitem.accumulate("total", 0) ;
			coinitem.accumulate("frozen",0) ;
			coinitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("coinitem", coinitem) ;
		}else{
			fuser = this.frontUserService.findById(fuser.getFid()) ;
			Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid());
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
			Double total=0.0;
			Double frozen=0.0;
			if(fvirtualwallet!=null){
				total=fvirtualwallet.getFtotal();
				frozen=fvirtualwallet.getFfrozen();
			}
			//可用
			jsonObject.accumulate("availableCny", fwallet.getFtotal()) ;
			jsonObject.accumulate("availableCoin", total) ;
			jsonObject.accumulate("frozenCny", fwallet.getFfrozen()) ;
			jsonObject.accumulate("frozenCoin", frozen) ;
			//借貸明細
			JSONObject leveritem = new JSONObject() ;
			Map<Fvirtualcointype,Fvirtualwallet> fvirtualwallets = this.frontUserService.findVirtualWallet(fuser.getFid()) ;
			//估计总资产，重点注意
			//CNY+冻结CNY+（币种+冻结币种）*最高买价
			double totalCapital = 0F ;
			totalCapital+=fwallet.getFtotal()+fwallet.getFfrozen() ;
			Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapService.get("tradeMappings");
			for (Map.Entry<Fvirtualcointype, Fvirtualwallet> entry : fvirtualwallets.entrySet()) {
				if(entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_CNY_VALUE || entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_USDT_VALUE) continue;
				Integer trade=0;
				if(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid())!=null){
					trade=tradeMappings.get(entry.getValue().getFvirtualcointype().getFid());
				}
				totalCapital += ( entry.getValue().getFfrozen()+entry.getValue().getFtotal() )* this.frontCacheService.getLatestDealPrize(trade) ;
			}
			leveritem.accumulate("total", Utils.getDouble(totalCapital,2)) ;
			leveritem.accumulate("asset", 0) ;
			leveritem.accumulate("score", 0) ;
			jsonObject.accumulate("leveritem", leveritem) ;
			//人民幣明細
			JSONObject cnyitem = new JSONObject() ;
			cnyitem.accumulate("total", fwallet.getFtotal()) ;
			cnyitem.accumulate("frozen", fwallet.getFfrozen()) ;
			cnyitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("cnyitem", cnyitem) ;
			//人民幣明細
			JSONObject coinitem = new JSONObject() ;
			coinitem.accumulate("id", symbol) ;
			coinitem.accumulate("total", total) ;
			coinitem.accumulate("frozen", frozen) ;
			coinitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("coinitem", coinitem) ;
		}

	    
		
		return jsonObject.toString() ;
		
	}
	
}
