package com.gt.controller.app;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.controller.constant.ApiConstant;
import com.gt.entity.ConvertVirtualCoin;
import com.gt.entity.Fapi;
import com.gt.entity.Fpool;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.quartz.AutoScanAndSendTransaction;
import com.gt.service.admin.PoolService;
import com.gt.service.front.ConvertVirtualCoinService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.HttpClientUtils;
import com.gt.util.MD5SignHelper;
import com.gt.util.Utils;
import com.gt.utils.Constant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class AppApiController extends BaseController {

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
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private ConvertVirtualCoinService convertVirtualCoinService;
	@Autowired
	private PoolService poolService;
	@Autowired
	private AutoScanAndSendTransaction autoScanAndSendTransaction;
	
	@ResponseBody
	@RequestMapping(value="/appWalletApi",produces="text/html;charset=UTF-8")
	public String appApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String action
			) throws Exception {
		request.setCharacterEncoding("UTF-8") ;
		Integer actionInteger = ApiConstant.getInteger(action) ;
		
		if(actionInteger / 100>=2){

			String sign = request.getParameter("sign") ;
			
			Map<String, String[]> params = request.getParameterMap() ;
			TreeSet<String> paramTreeSet = new TreeSet<String>() ;
			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String key = entry.getKey() ;
				String[] values = entry.getValue() ;
				if(values.length!=1){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10001) ;
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
			paramString.append("&secret_key="+Constant.APP_MD5_KEY) ;
			String md5 = Utils.getMD5_32_xx(paramString.toString()).toUpperCase() ;
			
			if(md5.equals(sign) == false ){
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE , -10003) ;
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
				Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class) ;
				ret = (String)method.invoke(this, request) ;
			} catch (Exception e) {
				ret = ApiConstant.getUnknownError(e) ;
			}
			break ;	
		}
		
		if(Constant.isRelease == false )
		{
			System.out.println(ret);
		}
		return ret ;
	}
	
	
	
	public String depth(HttpServletRequest request) throws Exception {
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
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public String kline(HttpServletRequest request) throws Exception {
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
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public String market(HttpServletRequest request) throws Exception {
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
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public String tickers(HttpServletRequest request) throws Exception {
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
			return ApiConstant.getUnknownError(e) ;
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
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 获取币种兑换列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetConvertCoinList(HttpServletRequest request) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "兑换币种") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			String filter = " where fstatus ="+VirtualCoinTypeStatusEnum.Normal ;
			List<Fvirtualcointype> coinList = frontVirtualCoinService.list(0, 0, filter, false);
			
			Map<Integer, Object> coinTypeMap = new HashMap<Integer, Object>();
			for (Fvirtualcointype coinType : coinList) {
				coinTypeMap.put(coinType.getFid(), coinType.getfShortName());
			}
			
			jsonObject.accumulate("coinTypeMap",coinTypeMap);
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public double GetConvertRatio(Integer coinTypeFrom, Integer coinTypeTo){
		//获USDT币种信息
		List<Fvirtualcointype> usdtCoinTypeList = frontVirtualCoinService.findByProperty("fShortName", "USDT");
		Fvirtualcointype usdtCoinType = usdtCoinTypeList.get(0);
		
		//查询兑USDT的交易市场
		String filter = " where fvirtualcointypeByFvirtualcointype1.fid="+usdtCoinType.getFid()
					   +" and fvirtualcointypeByFvirtualcointype2.fid="+coinTypeFrom;
		List<Ftrademapping> coinFromUsdtMappingList = ftradeMappingService.list(0, 0, filter, false);
		Ftrademapping coinFromUsdtMapping = null;
		if(null != coinFromUsdtMappingList && coinFromUsdtMappingList.size() > 0) {
			coinFromUsdtMapping = coinFromUsdtMappingList.get(0);
		}
		
		filter = " where fvirtualcointypeByFvirtualcointype1.fid="+usdtCoinType.getFid()
					   +" and fvirtualcointypeByFvirtualcointype2.fid="+coinTypeTo;
		List<Ftrademapping> coinToUsdtMappingList = ftradeMappingService.list(0, 0, filter, false);
		Ftrademapping coinToUsdtMapping = null;
		if(null != coinToUsdtMappingList && coinToUsdtMappingList.size() > 0) {
			coinToUsdtMapping = coinToUsdtMappingList.get(0);
		}
		
		double coinFromUsdtPrice = Utils.getDouble(this.frontCacheService.
				getLatestDealPrize(coinFromUsdtMapping.getFid()), coinFromUsdtMapping.getFcount1());
		
		double coinToUsdtPrice = Utils.getDouble(this.frontCacheService.
				getLatestDealPrize(coinToUsdtMapping.getFid()), coinToUsdtMapping.getFcount1());
		
		double convertRatio = Utils.getDouble(coinFromUsdtPrice/coinToUsdtPrice, 12);
		
		return convertRatio;
	}
	
	/**
	 * 获取兑换汇率
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetConvertRatio(HttpServletRequest request) throws Exception {
		try{
			Integer coinTypeFrom = Integer.valueOf(request.getParameter("coinTypeFrom"));
			Integer coinTypeTo = Integer.valueOf(request.getParameter("coinTypeTo"));
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "币种兑换汇率") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			double convertRatio = GetConvertRatio(coinTypeFrom, coinTypeTo);
			
			jsonObject.accumulate("convertRatio", convertRatio) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 获取币种兑换手续费费率
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetConvertCoinFee(HttpServletRequest request) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "币种兑换手续费费率") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			//从缓存获取币种兑换手续费费率
			jsonObject.accumulate("convertCoinFee",this.frontConstantMapService.get("ConvertCoinFee").toString());
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 发起兑换
	 * @return
	 * @throws Exception
	 */
	public String ConvertCoin(HttpServletRequest request) throws Exception {
		try{
			String coinType1 = request.getParameter("coinType1");
			String fromAddress1 = request.getParameter("fromAddress1");
			String amount1 = request.getParameter("amount1");
			String coinType2 = request.getParameter("coinType2");
			String toAddress2 = request.getParameter("toAddress2");
			
			JSONObject jsonObject = new JSONObject();
			
			String ConvertCoinFee = this.frontConstantMapService.get("ConvertCoinFee").toString();
				
			ConvertVirtualCoin instance = new ConvertVirtualCoin();
			instance.setOrderId(Utils.getPrototypeUUID());
			instance.setCoinType1(Integer.valueOf(coinType1));
			instance.setCoinType2(Integer.valueOf(coinType2));
			instance.setFromAddress1(fromAddress1);
			instance.setAmount1(Double.valueOf(amount1));
			instance.setFee(Double.valueOf(amount1) * Double.valueOf(ConvertCoinFee));
			instance.setToAddress2(toAddress2);
			instance.setStatus(1);
			instance.setCreateTime(Utils.getTimestamp());
			instance.setUpdateTime(Utils.getTimestamp());
			
			Fvirtualcointype fvirtualcointype = frontVirtualCoinService.findById(Integer.valueOf(coinType1));
			
			Fpool fpool = poolService.getOneFpool(fvirtualcointype) ;
			if(fpool == null){
				jsonObject.accumulate(CODE, -1) ;
				jsonObject.accumulate(MESSAGE, getLocaleMessage(request,null,"json.account.noaddress")) ;
				return jsonObject.toString() ;
			}else{
				fpool.setFstatus(1) ;
				poolService.updateObj(fpool) ;
			}
			String addr = fpool.getFaddress() ;
			if(addr==null || addr.trim().equalsIgnoreCase("null") || addr.trim().equals("")){
				jsonObject.accumulate(CODE, -1) ;
				jsonObject.accumulate(MESSAGE, getLocaleMessage(request,null,"json.account.noaddress")) ;
				return jsonObject.toString() ;
			}
			instance.setToAddress1(addr);
			convertVirtualCoinService.save(instance);
			
			jsonObject.accumulate(DATA, instance) ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public String submitConvertCoin(HttpServletRequest request) throws Exception {
		try{
			String orderId = request.getParameter("orderId");
			String txid1 = request.getParameter("txid1");
			
			JSONObject jsonObject = new JSONObject() ;
			
			ConvertVirtualCoin instance = convertVirtualCoinService.findByOrderId(orderId);
			if(null != instance){
				instance.setTxid1(txid1);
				convertVirtualCoinService.update(instance);
				autoScanAndSendTransaction.start(instance);
				jsonObject.accumulate(CODE, 200) ;
				jsonObject.accumulate(MESSAGE, "") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			jsonObject.accumulate(CODE, -1) ;
			jsonObject.accumulate(MESSAGE, "") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 取消
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String cancelConvertCoin(HttpServletRequest request) throws Exception {
		try{
			String orderId = request.getParameter("orderId");
			
			JSONObject jsonObject = new JSONObject() ;
			
			ConvertVirtualCoin instance = convertVirtualCoinService.findByOrderId(orderId);
			if(null != instance){
				instance.setStatus(2);
				convertVirtualCoinService.update(instance);
				jsonObject.accumulate(CODE, 200) ;
				jsonObject.accumulate(MESSAGE, "") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			jsonObject.accumulate(CODE, -1) ;
			jsonObject.accumulate(MESSAGE, "") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//以下为测试方法
	public static void UserRegister() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("email", "15021635312");
		paramMap.put("password", "123qwe");
		paramMap.put("type", "1");
		paramMap.put("code", "111111");
		paramMap.put("api_key", "359b8b8b-beb8-4652-b588-9f4d72651930");
		
		//传递参数
		String paramStr = MD5SignHelper.getParamStr(paramMap);

		//md5密钥  
	    String MD5_KEY = "AI7R2FGAKLYSHQGYGHCILKK7CGXHOF80KXOB";
	    
	    //对RSA密文后边追加MD5密钥后进行MD5加密，
	    String MD5OrderParam = MD5SignHelper.getSign(paramStr, MD5_KEY);
	    paramMap.put("action", "UserRegister");
	    paramMap.put("sign", MD5OrderParam);
	    HttpClientUtils httppost = new HttpClientUtils();
	    // 设置默认请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");  
	    String respResult = httppost.sendPostFromData("http://192.168.0.193:8082/appApi.html",headers, paramMap,"UTF-8");
//		String orderParam = JSON.toJSONString(paramMap);
		System.out.println(respResult);
	}
	
	
	
	
	public static void main(String[] args) {
//		UserRegister();
//		UserLogin();
//		GetDrawaccountsCoinlist();
//		GetAccountsByCoin();
//		drawAccountsSubmit();
	}
}
