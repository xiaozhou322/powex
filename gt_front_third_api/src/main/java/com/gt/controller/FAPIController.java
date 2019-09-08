package com.gt.controller;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fapi;
import com.gt.entity.Fentrust;
import com.gt.entity.Flimittrade;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwithdrawfees;
import com.gt.entity.Pdomain;
import com.gt.entity.Ptrademapping;
import com.gt.service.admin.WithdrawFeesService;
import com.gt.service.front.FrontAccountService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.Utils;


@Controller
public class FAPIController extends BaseController {

	public final static String CODE = "code" ;
	public final static String MESSAGE = "msg" ;
	public final static String TIME = "time" ;
	public final static String DATA = "data" ;
	public final static String ORDERID = "orderId" ;
	public final static int maxResults = Constant.AppRecordPerPage ;
	
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontCacheService frontCacheService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService ;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontEntrustChangeService frontEntrustChangeService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	
	@ResponseBody
	@RequestMapping(value="/appApi",produces=JsonEncode)
	public String appApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String action
			) throws Exception {
		request.setCharacterEncoding("UTF-8") ;
		Integer actionInteger = APIConstant.getInteger(action) ;
		
		Fapi fapi = null ;
		Fuser fuser = null ;
		if(actionInteger / 100>=2){

			String api_key = request.getParameter("api_key") ;
			String sign = request.getParameter("sign") ;
			List<Fapi> fapis = this.utilsService.list(0, 0, " where fpartner=? ", false, Fapi.class,api_key) ;
			if(fapis.size() == 1 ){
				fapi = fapis.get(0) ;
				if( 
						(fapi.isFistrade()==false && actionInteger / 100==2)
						
						){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10004) ;
					jsonObject.accumulate(MESSAGE, "无交易权限") ;
					return jsonObject.toString() ;
				}
				
				if(
						(fapi.isFiswithdraw()==false && actionInteger / 100==3)
						){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10005) ;
					jsonObject.accumulate(MESSAGE, "无提现权限") ;
					return jsonObject.toString() ;
				}
				
				String ip = getIpAddr(request) ;
				if("*".equals(fapi.getFip()) == false && fapi.getFip().indexOf(ip)==-1){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10005) ;
					jsonObject.accumulate(MESSAGE, "IP无权限") ;
					return jsonObject.toString() ;
				}
				
				fuser = fapi.getFuser() ;
			}else{
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE , -10006) ;
				jsonObject.accumulate(MESSAGE, "api_key错误") ;
				return jsonObject.toString() ;
			}
			
			
			Map<String, String[]> params = request.getParameterMap() ;
			TreeSet<String> paramTreeSet = new TreeSet<String>() ;
			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String key = entry.getKey() ;
				String[] values = entry.getValue() ;
				if(values.length!=1){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10003) ;
					jsonObject.accumulate(MESSAGE, "参数错误") ;
					return jsonObject.toString() ;
				}
				if("sign".equals(key) == false &&"action".equals(key) == false){
					paramTreeSet.add(key+"="+values[0]) ;
				}
			}
			
			StringBuffer paramString = new StringBuffer() ;
			for (String p : paramTreeSet) {
				if(paramString.length()>0){
					paramString.append("&") ;
				}
				paramString.append(p) ;
			}
			paramString.append("&secret_key="+fapi.getFsecret()) ;
			String md5 = Utils.getMD5_32_xx(paramString.toString()).toUpperCase() ;
			
			if(md5.equals(sign) == false ){
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE , -10007) ;
				jsonObject.accumulate(MESSAGE, "签名错误") ;
				return jsonObject.toString() ;
			}
		}
		
		String ret = "" ;
		switch (actionInteger) {
		case 0 :
			{
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE , -10002) ;
				jsonObject.accumulate(MESSAGE, "API不存在") ;
				ret = jsonObject.toString() ;
			}
			break ;
			
		default:
			try {
				Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class,Fapi.class,Fuser.class) ;
				ret = (String)method.invoke(this, request,fapi,fuser) ;
			} catch (Exception e) {
				ret = APIConstant.getUnknownError(e) ;
			}
			break ;	
		}
		
		if(Constant.isRelease == false )
		{
			System.out.println(ret);
		}
		return ret ;
	}
	
	
	public String depth(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "获取市场深度") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			int size = 5 ;
			if (request.getParameter("size")!=null){
				size = Integer.parseInt(request.getParameter("size"));
			}
			if (size>50){size=50;}
			
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
			
			JSONObject data = new JSONObject() ;
			JSONArray asks = new JSONArray() ;
			JSONArray bids = new JSONArray() ;
			
			//Fentrust[] buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(),size) ;
			//Fentrust[] sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(),size) ;
			
			String sellEntrustsString = this.frontCacheService.getSellDepthMap(symbol);
			List<String> sellEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(sellEntrustsString, String.class);
			String buyEntrustsString = this.frontCacheService.getBuyDepthMap(symbol);
			List<String> buyEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(buyEntrustsString, String.class);
			
			if (buyEntrustsList != null) {
				for (int i = 0; i < buyEntrustsList.size() && i < 20 ; i++) {
					JSONArray item = new JSONArray() ;
					String fentrustString = buyEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					item.add(Utils.getDouble(Double.valueOf(fentrustParam[0]), ftrademapping.getFcount1())) ;
					item.add(Utils.getDouble(Double.valueOf(fentrustParam[1]), ftrademapping.getFcount2())) ;
					bids.add(item) ;
				}
			}
			
			if (sellEntrustsList != null) {
				for (int i = 0; i < sellEntrustsList.size() && i < 20 ; i++) {
					JSONArray item = new JSONArray() ;
					String fentrustString = sellEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					item.add(Utils.getDouble(Double.valueOf(fentrustParam[0]), ftrademapping.getFcount1())) ;
					item.add(Utils.getDouble(Double.valueOf(fentrustParam[1]), ftrademapping.getFcount2())) ;
					asks.add(item) ;
				}
			}
			
			data.accumulate("bids", bids) ;
			data.accumulate("asks", asks) ;
			
			jsonObject.accumulate(DATA, data) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String kline(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "获取K线数据") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int step = Integer.parseInt(request.getParameter("step")) ;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
			

			
			int key = 0 ;
			switch (step) {
			case 60:
				key = 0 ;
				break;
			case 60*3:
				key = 1 ;
				break;
			case 60*5:
				key = 2 ;
				break;
			case 60*15:
				key = 3 ;
				break;
			case 60*30:
				key = 4 ;
				break;
			case 60*60:
				key = 5 ;
				break;
			case 60*60*2:
				key = 6 ;
				break;
			case 60*60*4:
				key = 7 ;
				break;
			case 60*60*6:
				key = 8 ;
				break;
			case 60*60*12:
				key = 9 ;
				break;
			case 60*60*24:
				key = 10 ;
				break;
			case 60*60*24*3:
				key = 11 ;
				break;
			case 60*60*24*7:
				key = 12 ;
				break;
			}
			long l= System.currentTimeMillis() ;
			String data = null;
			try {
				data = this.frontCacheService.getJsonString(symbol, key) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			jsonObject.accumulate(DATA, data) ;
		
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String market(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "获取实时行情") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
			
			JSONObject data = new JSONObject() ;
			
			DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
			
			df.setMaximumFractionDigits(ftrademapping.getFcount1());
			df.setGroupingUsed(false);
			data.accumulate("last", df.format(Utils.getDouble(this.frontCacheService.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1())));
			data.accumulate("high", df.format(Utils.getDouble(this.frontCacheService.getHighest(ftrademapping.getFid()), ftrademapping.getFcount1())));
			data.accumulate("low", df.format(Utils.getDouble(this.frontCacheService.getLowest(ftrademapping.getFid()), ftrademapping.getFcount1())));
			data.accumulate("buy", df.format(Utils.getDouble(this.frontCacheService.getHighestBuyPrize(ftrademapping.getFid()), ftrademapping.getFcount1())));
			data.accumulate("sell", df.format(Utils.getDouble(this.frontCacheService.getLowestSellPrize(ftrademapping.getFid()), ftrademapping.getFcount1())));
			df.setMaximumFractionDigits(ftrademapping.getFcount2());
			data.accumulate("vol", df.format(Utils.getDouble(this.frontCacheService.getTotal(ftrademapping.getFid()), ftrademapping.getFcount2())));
			
			jsonObject.accumulate(DATA, data) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String tickers(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			Long curtime = Long.valueOf(Utils.getTimestamp().getTime() / 1000) ;
			DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
			jsonObject.accumulate("date",curtime);
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMapping();
			JSONArray tickers = new JSONArray();
			
			for(Ftrademapping ftmap:ftrademappings){
				JSONObject ticker = new JSONObject() ;
				Fvirtualcointype fbcoin= this.frontVirtualCoinService.findFvirtualCoinById(ftmap.getFvirtualcointypeByFvirtualcointype1().getFid());
				Fvirtualcointype bbcoin= this.frontVirtualCoinService.findFvirtualCoinById(ftmap.getFvirtualcointypeByFvirtualcointype2().getFid());
				ticker.accumulate("symbol",bbcoin.getfShortName().toLowerCase()+"_"+fbcoin.getfShortName().toLowerCase());
				df.setMaximumFractionDigits(ftmap.getFcount1());
				df.setGroupingUsed(false);
				ticker.accumulate("buy", df.format(Utils.getDouble(this.frontCacheService.getHighestBuyPrize(ftmap.getFid()), ftmap.getFcount1())));
				ticker.accumulate("high", df.format(Utils.getDouble(this.frontCacheService.getHighest(ftmap.getFid()), ftmap.getFcount1())));
				ticker.accumulate("last", df.format(Utils.getDouble(this.frontCacheService.getLatestDealPrize(ftmap.getFid()), ftmap.getFcount1())));
				ticker.accumulate("low", df.format(Utils.getDouble(this.frontCacheService.getLowest(ftmap.getFid()), ftmap.getFcount1())));
				ticker.accumulate("sell", df.format(Utils.getDouble(this.frontCacheService.getLowestSellPrize(ftmap.getFid()), ftmap.getFcount1())));
				df.setMaximumFractionDigits(ftmap.getFcount2());
				ticker.accumulate("vol", df.format(Utils.getDouble(this.frontCacheService.getTotal(ftmap.getFid()), ftmap.getFcount2())));
				tickers.add(ticker);
			}
			jsonObject.accumulate("ticker",tickers);
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String trades(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "获取最新成交记录") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
			
			String entrustSuccessString = this.frontCacheService.getEntrustSuccessMap(ftrademapping.getFid());
			List<String> successEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(entrustSuccessString, String.class);
			JSONArray data = new JSONArray() ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			
			if (successEntrustsList != null){
				for (int i = 0; i < successEntrustsList.size() && i < 20 ; i++) {
					JSONObject item = new JSONObject() ;
					String fentrustString = successEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[3]), ftrademapping.getFcount2())) ;
					item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[2]), ftrademapping.getFcount1())) ;
					item.accumulate("id", Integer.valueOf(fentrustParam[1])) ;
					item.accumulate("time", sdf.format(new Timestamp(Long.valueOf(fentrustParam[0])))) ;
					item.accumulate("en_type", Integer.valueOf(fentrustParam[4])==EntrustTypeEnum.BUY?"ask":"bid") ;
					item.accumulate("type", Integer.valueOf(fentrustParam[4])==EntrustTypeEnum.BUY?"买":"卖") ;
					data.add(item);
				}
			}
			
			
			jsonObject.accumulate(DATA, data) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String cancel_entrust(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int id = Integer.parseInt(request.getParameter("id")) ;
			
			Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
			if(fentrust!=null
					&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
					&&fentrust.getFuser().getFid() == fuser.getFid() ){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
					
					if (fentrust.getFentrustType() == EntrustTypeEnum.BUY) {
						if (fentrust.isFisLimit()) {
							frontEntrustChangeService.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//						rabbitTemplate.convertAndSend("entrust.limit.buy.remove", symbolAndIdStr);
						} else {
							frontEntrustChangeService.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//						rabbitTemplate.convertAndSend("entrust.buy.remove", symbolAndIdStr);
						}
					} else {
						if (fentrust.isFisLimit()) {
							frontEntrustChangeService.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//							rabbitTemplate.convertAndSend("entrust.limit.sell.remove", symbolAndIdStr);
						} else {
							frontEntrustChangeService.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//							rabbitTemplate.convertAndSend("entrust.sell.remove", symbolAndIdStr);
						}
					}
					flag = true ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(flag==true){
					
					jsonObject.accumulate(CODE, 200) ;
					jsonObject.accumulate(MESSAGE, "撤单成功") ;
				}else{
					jsonObject.accumulate(CODE, 500) ;
					jsonObject.accumulate(MESSAGE, "撤单失败") ;
				}
			}
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	public String trade(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			int type = Integer.parseInt(request.getParameter("type")) ;
			double amount = Double.parseDouble(request.getParameter("amount")) ;
			double price = Double.parseDouble(request.getParameter("price")) ;
			
			JSONObject retJsonObject = null ;
			if(type == EntrustTypeEnum.BUY){
				retJsonObject = JSONObject.fromObject(buy(request, 0, symbol, amount, price, fuser)) ;
			}else{
				retJsonObject = JSONObject.fromObject(sell(request, 0, symbol, amount, price, fuser)) ;
			}
			
			if(retJsonObject.getInt("resultCode") == 0 ){
				jsonObject.accumulate(CODE, 200) ;
				jsonObject.accumulate(ORDERID, retJsonObject.getString("orderId")) ;
				jsonObject.accumulate(MESSAGE, "委托成功") ;
			}else{
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, retJsonObject.getString("msg")) ;
			}
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String entrust(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "查询当前委单") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
			
			JSONArray data = new JSONArray() ;
			List<Fentrust> fentrusts = this.frontTradeService.findFentrustHistory(
					fuser.getFid(), symbol,null, 0, Integer.MAX_VALUE, " fid desc ", new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal}) ;
			for (Fentrust fentrust : fentrusts) {
				JSONObject item = new JSONObject() ;
				
				item.accumulate("price", Utils.getDouble(fentrust.getFprize(), ftrademapping.getFcount1())) ;
				item.accumulate("count", Utils.getDouble(fentrust.getFcount(), ftrademapping.getFcount2())) ;
				item.accumulate("success_count", Utils.getDouble(fentrust.getFcount()-fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
				item.accumulate("amount", Utils.getDouble(fentrust.getFamount(), ftrademapping.getFcount1())) ;
				item.accumulate("success_amount", Utils.getDouble(fentrust.getFsuccessAmount(), ftrademapping.getFcount1())) ;
				item.accumulate("type", fentrust.getFentrustType()) ;
				item.accumulate("type_s", fentrust.getFentrustType_s()) ;
				item.accumulate("status", fentrust.getFstatus()) ;
				item.accumulate("status_s", fentrust.getFstatus_s()) ;
				item.accumulate("id", fentrust.getFid()) ;
				
				data.add(item) ;
			}
			jsonObject.accumulate(DATA, data) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String order(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "根本id查委托订单详细信息") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			int id = Integer.parseInt(request.getParameter("id")) ;
			JSONArray data = new JSONArray() ;
			Fentrust fentrust = this.frontTradeService.findFentrustById(id);
			if(fentrust != null && fentrust.getFuser().getFid() == fuser.getFid()){
				JSONObject item = new JSONObject() ;
				Ftrademapping mapping = this.ftradeMappingService.findFtrademapping(fentrust.getFtrademapping().getFid());
				//单价
				item.accumulate("prize", Utils.getDouble(fentrust.getFprize(), mapping.getFcount1())) ;
				//挂单数量
				item.accumulate("count", Utils.getDouble(fentrust.getFcount(), mapping.getFcount2())) ;
				//已成交数量
				item.accumulate("success_count", Utils.getDouble(fentrust.getFcount()-fentrust.getFleftCount(), mapping.getFcount2())) ;
				//挂单金额
				item.accumulate("amount", Utils.getDouble(fentrust.getFamount(), mapping.getFcount1())) ;
				//已成交金额
				item.accumulate("success_amount", Utils.getDouble(fentrust.getFsuccessAmount(), mapping.getFcount1())) ;
				//订单类型，数字
				item.accumulate("type", fentrust.getFentrustType()) ;
				//订单类型，中文
				item.accumulate("type_s", fentrust.getFentrustType_s()) ;
				//订单状态，数字 
				item.accumulate("status", fentrust.getFstatus()) ;
				//订单状态，中文
				item.accumulate("status_s", fentrust.getFstatus_s()) ;
				//订单ID
				item.accumulate("id", fentrust.getFid()) ;
				
				data.add(item) ;
			}
			jsonObject.accumulate(DATA, data) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String lastentrust(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "查询最新10笔成交委单") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;			
			JSONArray data = new JSONArray() ;
			List<Fentrust> fentrusts = this.utilsService.list(0, 10, " where fuser.fid=? and ftrademapping.fid=? and fsuccessAmount>0 order by flastUpdatTime desc ", true, Fentrust.class,fuser.getFid(),ftrademapping.getFid()) ;
			for (Fentrust fentrust : fentrusts) {
				JSONObject item = new JSONObject() ;		
				item.accumulate("prize", Utils.getDouble(fentrust.getFprize(), ftrademapping.getFcount1())) ;
				item.accumulate("count", Utils.getDouble(fentrust.getFcount(), ftrademapping.getFcount2())) ;
				item.accumulate("success_count", Utils.getDouble(fentrust.getFcount()-fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
				item.accumulate("amount", Utils.getDouble(fentrust.getFamount(), ftrademapping.getFcount1())) ;
				item.accumulate("success_amount", Utils.getDouble(fentrust.getFsuccessAmount(), ftrademapping.getFcount1())) ;
				item.accumulate("type", fentrust.getFentrustType()) ;
				item.accumulate("type_s", fentrust.getFentrustType_s()) ;
				item.accumulate("status", fentrust.getFstatus()) ;
				item.accumulate("status_s", fentrust.getFstatus_s()) ;			
				data.add(item) ;
			}
			jsonObject.accumulate(DATA, data) ;		
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	
	public String userinfo(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "获取用户信息") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			JSONObject data = new JSONObject() ;
			JSONObject frozen = new JSONObject() ;
			JSONObject free = new JSONObject() ;
			
			List<Fvirtualwallet> fvirtualwallets = this.utilsService.
					list(0, 0, " where fuser.fid=? and  fvirtualcointype.fstatus=? order by fvirtualcointype.ftype asc  ", 
							false, Fvirtualwallet.class,fuser.getFid(),VirtualCoinTypeStatusEnum.Normal) ;
			for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
				free.accumulate(fvirtualwallet.getFvirtualcointype().getfShortName(), fvirtualwallet.getFtotal()) ;
				frozen.accumulate(fvirtualwallet.getFvirtualcointype().getfShortName(), fvirtualwallet.getFfrozen()) ;
			}
			data.accumulate("free", free) ;
			data.accumulate("frozen", frozen) ;
			
			jsonObject.accumulate(DATA, data) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String withdraw(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			double amount = Double.parseDouble(request.getParameter("amount")) ;
			String withdrawaddress = request.getParameter("withdrawaddress") ;
			
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
			if(fvirtualcointype==null 
					|| !fvirtualcointype.isFIsWithDraw() 
					|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
					|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "虚拟币错误") ;
				return jsonObject.toString() ;
			}
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
			List<FvirtualaddressWithdraw> fvirtualaddressWithdraws = this.utilsService.list(0, 0, " where fuser.fid=? and fvirtualcointype.fid=? and fadderess=? ", false, FvirtualaddressWithdraw.class,fuser.getFid(),symbol,withdrawaddress) ;
			FvirtualaddressWithdraw fvirtualaddressWithdraw = null;
			if(fvirtualaddressWithdraws.size()>0){
				fvirtualaddressWithdraw = fvirtualaddressWithdraws.get(0) ;
			}
			if(fvirtualaddressWithdraw == null
					|| fvirtualaddressWithdraw.getFuser().getFid() != fuser.getFid()
					|| fvirtualaddressWithdraw.getFvirtualcointype().getFid() != symbol){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "提现地址不存在") ;
				return jsonObject.toString() ;
			}
			
			

			double max_double = fvirtualcointype.getFmaxqty();
			double min_double = fvirtualcointype.getFminqty();
			if(amount<min_double){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE,"最小提现数量为："+min_double) ;
				return jsonObject.toString() ;
			}
			
			if(amount>max_double){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "最大提现数量为："+max_double) ;
				return jsonObject.toString() ;
			}
			
			//余额不足
			if(fvirtualwallet.getFtotal()<amount){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "您的余额不足") ;
				return jsonObject.toString() ;
			}
			
			
			String sql = "where flevel="+5+" and fvirtualcointype.fid="+symbol;
			List<Fwithdrawfees> alls = this.withdrawFeesService.list(0, 0, sql, false);
			if(alls == null || alls.size() ==0){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "手续费异常") ;
			}
			double ffees = alls.get(0).getFfee();
			if(ffees ==0 && alls.get(0).getFlevel() != 5){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "手续费有误") ;
				return jsonObject.toString();
			}
			
			if(amount <= ffees){
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "提现数量小于等于手续费数量") ;
				return jsonObject.toString();
			}
			
			
			boolean flag = false ;
			try {
				this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw, fvirtualcointype, fvirtualwallet, amount, ffees, fuser) ;
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(flag == true ){
				jsonObject.accumulate(CODE, 200) ;
				jsonObject.accumulate(MESSAGE, "提现成功") ;
				return jsonObject.toString();
			}else{
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "网络错误，请稍后再试") ;
				return jsonObject.toString();
			}
			
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	public String cancel_withdraw(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "撤消虚拟币提现") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int id = Integer.parseInt(request.getParameter("id")) ;
			
			

			boolean flag = false ;
			Fvirtualcaptualoperation fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(id) ;
			if(fvirtualcaptualoperation!=null
					&&fvirtualcaptualoperation.getFuser().getFid() ==fuser.getFid() 
					&&fvirtualcaptualoperation.getFtype()==VirtualCapitalOperationTypeEnum.COIN_OUT
					&&fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationOutStatusEnum.WaitForOperation
					){
				
				try{
					this.frontAccountService.updateCancelWithdrawBtc(fvirtualcaptualoperation, fuser) ;
					flag = true ;
				}catch(Exception e){
					e.printStackTrace() ;
				}
				
			}
		
			if(flag == true ){
				jsonObject.accumulate(CODE, 200) ;
				jsonObject.accumulate(MESSAGE, "撤销成功") ;
				return jsonObject.toString();
			}else{
				jsonObject.accumulate(CODE, 500) ;
				jsonObject.accumulate(MESSAGE, "网络错误，请稍后再试") ;
				return jsonObject.toString();
			}
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	public String withdraw_record(HttpServletRequest request,Fapi fapi,Fuser fuser) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "虚拟币提现记录") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
			
			String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_OUT
					+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
			List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 30, filter, false,Fvirtualcaptualoperation.class);
			JSONArray data = new JSONArray() ;
			for (Fvirtualcaptualoperation fvirtualcaptualoperation : fvirtualcaptualoperations) {
				JSONObject item = new JSONObject() ;
				item.accumulate("amount", fvirtualcaptualoperation.getFamount()) ;
				item.accumulate("fees", fvirtualcaptualoperation.getFfees()) ;
				item.accumulate("withdraw_virtual_address", fvirtualcaptualoperation.getWithdraw_virtual_address()) ;
				item.accumulate("createTime", fvirtualcaptualoperation.getFcreateTime()) ;
				item.accumulate("status", fvirtualcaptualoperation.getFstatus()) ;
				item.accumulate("status_s", fvirtualcaptualoperation.getFstatus_s()) ;
				
				data.add(item) ;
			}
			jsonObject.accumulate(DATA, data) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}


	public String buy(
			HttpServletRequest request,
			int limited,//是否按照市场价买入
			int symbol,//币种
			double tradeAmount,//数量
			double tradeCnyPrice,
			Fuser fuser
			) throws Exception{
		limited=0;//禁用市价单
		
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null  ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			jsonObject.accumulate("resultCode", -100) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.unabletrade"));
			return jsonObject.toString() ;
		}
		
		//限制法币为人民币和比特币和USDT
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_USDT_VALUE){
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror"));
			return jsonObject.toString() ;
			
		}
		
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		double minBuyCount = Utils.getDouble(ftrademapping.getFminBuyCount() ,ftrademapping.getFcount2()) ;
		double minBuyPrice = Utils.getDouble(ftrademapping.getFminBuyPrice(),ftrademapping.getFcount1()) ;
		double minBuyAmount = Utils.getDouble(ftrademapping.getFminBuyAmount(),ftrademapping.getFcount1()) ;
		double maxBuyCount = ftrademapping.getFmaxBuyCount() ;
		double maxBuyAmount = ftrademapping.getFmaxBuyAmount();
		double maxBuyPrice = ftrademapping.getFmaxBuyPrice();
		
		//是否开放交易
		if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
			jsonObject.accumulate("resultCode", -400) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.notradetime"));
			return jsonObject.toString() ;
		}
		
		tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
		tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
		if(!fuser.getFpostRealValidate()){
			jsonObject.accumulate("resultCode", -400) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.authfirst"));
			return jsonObject.toString() ;
		}
		//定价单
		if(limited == 0 ){
			
			if(tradeAmount<minBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.mintravol")+coin2.getfSymbol()+minBuyCount);
				return jsonObject.toString() ;
			}
			
			
			if(tradeAmount>maxBuyCount && !fuser.isFistiger()){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.maxtravol")+coin2.getfSymbol()+maxBuyCount);
				return jsonObject.toString() ;
			}
			
			if(tradeCnyPrice < minBuyPrice){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.minordpri")+coin1.getfSymbol()+minBuyPrice);
				return jsonObject.toString() ;
			}
			
			double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
			if(total < minBuyAmount){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.minordamn")+coin1.getfSymbol()+minBuyAmount);
				return jsonObject.toString() ;
			}
			
			if(total > maxBuyAmount && !fuser.isFistiger()){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.midtraamn")+coin1.getfSymbol()+maxBuyAmount);
				return jsonObject.toString() ;
			}
			
			Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
			double upPrice = 0d;
			double downPrice = 0d;
			if(limittrade != null){
				upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				if(downPrice <0) downPrice=0;
				if(tradeCnyPrice > upPrice){
					jsonObject.accumulate("resultCode", -500) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.ordpricehigh")+upPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
				if(tradeCnyPrice < downPrice){
					jsonObject.accumulate("resultCode", -600) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.ordpricelow")+downPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
			}else{
				if(tradeCnyPrice > maxBuyPrice && !fuser.isFistiger()){
					jsonObject.accumulate("resultCode", -3) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.maxordpri")+coin1.getfSymbol()+maxBuyPrice);
					return jsonObject.toString() ;
				}
			}
			
			
		}else{
			if(tradeCnyPrice <minBuyAmount){
				jsonObject.accumulate("resultCode", -33) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.midtraamn")+minBuyAmount+coin1.getFname());
				return jsonObject.toString() ;
			}
		}
		
		double totalTradePrice = 0F ;
		if(limited==0){
			totalTradePrice = tradeAmount*tradeCnyPrice ;
		}else{
			totalTradePrice = tradeAmount ;
		}
		Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),coin1.getFid());
		if(fwallet.getFtotal()<totalTradePrice){
			jsonObject.accumulate("resultCode", -4) ;
			jsonObject.accumulate("msg", coin1.getFname()+getLocaleMessage(request,null,"json.trade.balnoenough"));
			return jsonObject.toString() ;
		}
		
		//从请求匹配缓存中的项目方域名列表
		Pdomain pdomain = getReqDomain(request);
		Integer fsourceId = 0;
		double commissionRate = 0;
		double feesRate = 0;
		if(pdomain != null) {
			fsourceId = pdomain.getProjectId().getFid();
			//主站访问和项目方网站访问自己的市场无返佣
			if(null!=ftrademapping.getFprojectId() && !ftrademapping.getFprojectId().equals(fsourceId)) {
				commissionRate = pdomain.getProportions();
				if(commissionRate > 0.3) commissionRate = 0.2;
				if(commissionRate < 0) commissionRate = 0;
			}
		}
		
		//从缓存中读取项目方交易市场的手续费比例分成
		List<Ptrademapping> ptrademappingList = (List<Ptrademapping>) frontConstantMapService.get("ptrademappingList");
		if(null != ptrademappingList && ptrademappingList.size() > 0) {
			//List转Map
			Map<Integer, Ptrademapping> maps = Maps.uniqueIndex(ptrademappingList, new Function<Ptrademapping, Integer>() {
				@Override
				public Integer apply(Ptrademapping ptrademapping) {
					return ptrademapping.getTradeMappingId();
				}
			});
			
			Ptrademapping ptrademapping = maps.get(ftrademapping.getFid());
			if(null != ptrademapping) feesRate = ptrademapping.getBuyFeesRate();
		}
		
		
		boolean flag = false ;
		Fentrust fentrust = null ;
		try {
			fentrust = this.frontTradeService.updateEntrustBuy(symbol, tradeAmount, tradeCnyPrice,
					fuser, limited==1, fsourceId, commissionRate, feesRate) ;
			//加入MQ买单队列
			if (limited == 1) {
				frontEntrustChangeService.addEntrustLimitBuyMap(symbol, fentrust);
//				rabbitTemplate.convertAndSend("entrust.limit.buy.add", symbolAndIdStr);
			} else {
				frontEntrustChangeService.addEntrustBuyMap(symbol, fentrust);
//				rabbitTemplate.convertAndSend("entrust.buy.add", symbolAndIdStr);
			}
			
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag && fentrust != null){			
			
			jsonObject.accumulate("resultCode", 0) ;
			jsonObject.accumulate("orderId", fentrust.getFid()) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.checksucc"));
			setNoNeedPassword(request) ;
		}else{
			jsonObject.accumulate("resultCode", -200) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror"));
		}
		
		return jsonObject.toString() ;
	}
	
	public String sell(
			HttpServletRequest request,
			 int limited,//是否按照市场价买入
			 int symbol,//币种
			 double tradeAmount,//数量
			 double tradeCnyPrice,//单价
			 Fuser fuser
			) throws Exception{
		limited=0;
		
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null  || ftrademapping.getFstatus()!=TrademappingStatusEnum.ACTIVE){
			jsonObject.accumulate("resultCode", -100) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.unabletrade"));
			return jsonObject.toString() ;
		}
		
		//限制法币为人民币和比特币和USDT
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_USDT_VALUE){
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror"));
			return jsonObject.toString() ;
			
		}
		
		tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
		tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		double minBuyCount = ftrademapping.getFminBuyCount() ;
		double maxBuyCount = ftrademapping.getFmaxBuyCount() ;
		double minBuyPrice = ftrademapping.getFminBuyPrice() ;
		double minBuyAmount = ftrademapping.getFminBuyAmount() ;
		double maxBuyAmount = ftrademapping.getFmaxBuyAmount();
		double maxBuyPrice = ftrademapping.getFmaxBuyPrice();
		
		//是否开放交易
		if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
			jsonObject.accumulate("resultCode", -400) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.notradetime"));
			return jsonObject.toString() ;
		}
		if(!fuser.getFpostRealValidate()){
			jsonObject.accumulate("resultCode", -400) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.authfirst"));
			return jsonObject.toString() ;
		}
		if(limited == 0 ){
			
			if(tradeAmount<minBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.mintravol")+coin2.getfSymbol()+minBuyCount);
				return jsonObject.toString() ;
			}
			
			if(tradeAmount>maxBuyCount && !fuser.isFistiger()){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.maxtravol")+coin2.getfSymbol()+maxBuyCount);
				return jsonObject.toString() ;
			}
			
			if(tradeCnyPrice < minBuyPrice){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.minordpri")+coin1.getfSymbol()+minBuyPrice);
				return jsonObject.toString() ;
			}
			
			double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
			if(total < minBuyAmount){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.minordamn")+coin1.getfSymbol()+minBuyAmount);
				return jsonObject.toString() ;
			}
			
			if(total > maxBuyAmount && !fuser.isFistiger()){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.midtraamn")+coin1.getfSymbol()+maxBuyAmount);
				return jsonObject.toString() ;
			}
			
			Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
			double upPrice = 0d;
			double downPrice = 0d;
			if(limittrade != null){
				upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				if(downPrice <0) downPrice=0;
				if(tradeCnyPrice > upPrice){
					jsonObject.accumulate("resultCode", -500) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.ordpricehigh")+upPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
				if(tradeCnyPrice < downPrice){
					jsonObject.accumulate("resultCode", -600) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.ordpricelow")+downPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
			}else{
				if(tradeCnyPrice > maxBuyPrice && !fuser.isFistiger()){
					jsonObject.accumulate("resultCode", -3) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.maxordpri")+coin1.getfSymbol()+maxBuyPrice);
					return jsonObject.toString() ;
				}
			}
			
		}else{
			if(tradeAmount <minBuyCount){
				jsonObject.accumulate("resultCode", -33) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.mintravol")+minBuyCount+coin2.getFname());
				return jsonObject.toString() ;
			}
		}
		
		
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coin2.getFid()) ;
		if(fvirtualwallet==null){
			jsonObject.accumulate("resultCode", -200) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.syserror"));
			return jsonObject.toString() ;
		}
		if(fvirtualwallet.getFtotal()<tradeAmount){
			jsonObject.accumulate("resultCode", -4) ;
			jsonObject.accumulate("msg", coin2.getFname()+getLocaleMessage(request,null,"json.trade.balnoenough"));
			return jsonObject.toString() ;
		}
		
		//从请求匹配缓存中的项目方域名列表
		Pdomain pdomain = getReqDomain(request);
		Integer fsourceId = 0;
		double commissionRate = 0;
		double feesRate = 0;
		if(pdomain != null) {
			fsourceId = pdomain.getProjectId().getFid();
			//主站访问和项目方网站访问自己的市场无返佣
			if(null==ftrademapping.getFprojectId() || !ftrademapping.getFprojectId().equals(fsourceId)) {
				commissionRate = pdomain.getProportions();
				if(commissionRate > 0.3) commissionRate = 0.2;
				if(commissionRate < 0) commissionRate = 0;
			}
		}
		
		//从缓存中读取项目方交易市场的手续费比例分成
		List<Ptrademapping> ptrademappingList = (List<Ptrademapping>) frontConstantMapService.get("ptrademappingList");
		if(null != ptrademappingList && ptrademappingList.size() > 0) {
			//List转Map
			Map<Integer, Ptrademapping> maps = Maps.uniqueIndex(ptrademappingList, new Function<Ptrademapping, Integer>() {
				@Override
				public Integer apply(Ptrademapping ptrademapping) {
					return ptrademapping.getTradeMappingId();
				}
			});
			
			Ptrademapping ptrademapping = maps.get(ftrademapping.getFid());
			if(null != ptrademapping) feesRate = ptrademapping.getSellFeesRate();
		}
		
		boolean flag = false ;
		Fentrust fentrust = null ;
		try {
			fentrust = this.frontTradeService.updateEntrustSell(symbol, tradeAmount, tradeCnyPrice,
					fuser, limited==1, fsourceId, commissionRate, feesRate) ;
			
			if (limited == 1) {
				frontEntrustChangeService.addEntrustLimitSellMap(symbol, fentrust);
//				rabbitTemplate.convertAndSend("entrust.limit.sell.add", symbolAndIdStr);
			} else {
				frontEntrustChangeService.addEntrustSellMap(symbol, fentrust);
//				rabbitTemplate.convertAndSend("entrust.sell.add", symbolAndIdStr);
			}
			
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag && fentrust != null){			
			jsonObject.accumulate("orderId", fentrust.getFid()) ;
			jsonObject.accumulate("resultCode", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.checksucc"));
			setNoNeedPassword(request);
		}else{
			jsonObject.accumulate("resultCode", -200) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.networkerror"));
		}
		
		return jsonObject.toString() ;
	}
}
