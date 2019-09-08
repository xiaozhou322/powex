package com.gt.controller.front;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Flimittrade;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pdomain;
import com.gt.entity.Ptrademapping;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontTradeJsonController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FrontEntrustChangeService frontEntrustChangeService;
	
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private AdminService adminService ;
	
	@ResponseBody
	@RequestMapping(value="/trade/entrustLog",produces=JsonEncode)
	public String entrustLog(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		if(GetSession(request) == null){
			jsonObject.accumulate("result", false) ;
		}
		
		
		Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
		if(fentrust==null){
			jsonObject.accumulate("result", false) ;
		}else{
			List<Fentrustlog> fentrustlogs = this.frontTradeService.findFentrustLogByFentrust(fentrust) ;
			
			jsonObject.accumulate("result", true) ;
			jsonObject.accumulate("title", getLocaleMessage(request,null,"json.trade.trandetail")+"["+ getLocaleMessage(request,null,"json.trade.order")+"ID:"+id+"]") ;
			
			StringBuffer content = new StringBuffer() ;
			content.append("<div> <table class=\"table\"> " +
					"<tr> " +
					"<td>"+getLocaleMessage(request,null,"entrust.entrust.date")+"</td> " +
					"<td>"+getLocaleMessage(request,null,"entrust.entrust.type")+"</td> " +
					"<td>"+getLocaleMessage(request,null,"entrust.entrust.price")+"</td> " +
					"<td>"+getLocaleMessage(request,null,"json.trade.dealprice")+"</td> " +
					"<td>"+getLocaleMessage(request,null,"entrust.deal.qty")+"</td> " +
					"<td>"+getLocaleMessage(request,null,"entrust.deal.amount")+"</td> " +
					"</tr>") ;
			
			if(fentrustlogs.size()==0){
				content.append("<tr><td colspan='6' class='no-data-tips'><span>"+getLocaleMessage(request,null,"json.trade.noord")+"</span></td></tr>") ;
			}
			
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(fentrust.getFtrademapping().getFid()) ;
			Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
			Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
			
			for (Fentrustlog fentrustlog : fentrustlogs) {
				content.append("<tr> " +
									"<td class='gray'>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fentrustlog.getFcreateTime())+"</td> " +
									"<td class='"+(fentrust.getFentrustType()==EntrustTypeEnum.BUY?"text-success":"text-danger")+"'>"+getLocaleMessage(request,null,"enum.entrust."+fentrust.getFentrustType())+"</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrust.getFprize())+"</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrustlog.getFprize())+"</td>" +
									"<td>"+coin2.getfSymbol()+Utils.number2String(fentrustlog.getFcount())+"</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrustlog.getFamount())+"</td>" +
								"</tr>") ;
			}
			
			content.append("</table> </div>") ;
			jsonObject.accumulate("content", content.toString()) ;
		}
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping(value="/trade/cancelEntrust",produces=JsonEncode)
	public String cancelEntrust(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
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
//					rabbitTemplate.convertAndSend("entrust.limit.buy.remove", symbolAndIdStr);
					} else {
						frontEntrustChangeService.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//					rabbitTemplate.convertAndSend("entrust.buy.remove", symbolAndIdStr);
					}
				} else {
					if (fentrust.isFisLimit()) {
						frontEntrustChangeService.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//						rabbitTemplate.convertAndSend("entrust.limit.sell.remove", symbolAndIdStr);
					} else {
						frontEntrustChangeService.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//						rabbitTemplate.convertAndSend("entrust.sell.remove", symbolAndIdStr);
					}
				}
				
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.cancelsucc")) ;
		return jsonObject.toString() ;
	}
	@ResponseBody
	@RequestMapping(value="/trade/cancelAllEntrust",produces=JsonEncode)
	public String cancelAllEntrust(
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true,defaultValue="0")int type
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and ftrademapping.fid="+id+" and fstatus in(1,2)";
		if(type ==1){
			filter = filter+" and fentrustType="+EntrustTypeEnum.BUY;
		}else if(type ==2){
			filter = filter+" and fentrustType="+EntrustTypeEnum.SELL;
		}
		List<Fentrust> fentrusts = this.frontTradeService.findFentrustByParam(0, 0, filter, false);
		for (Fentrust fentrust : fentrusts) {
			if(fentrust!=null
					&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
					&&fentrust.getFuser().getFid() == fuser.getFid() ){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
					flag = true ;
				} catch (Exception e) {
//					e.printStackTrace();
				}
				if(flag==true){
					if(fentrust.getFentrustType()==EntrustTypeEnum.BUY){
						
						//买
						if(fentrust.isFisLimit()){
							this.frontEntrustChangeService.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}else{
							this.frontEntrustChangeService.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}
					}else{
						
						//卖
						if(fentrust.isFisLimit()){
							this.frontEntrustChangeService.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}else{
							this.frontEntrustChangeService.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}
						
					}
				}
			}
		}
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.cancelsucc")) ;
		return jsonObject.toString() ;
	}

	/*
	 * @param type:0未成交前十条，1成交前10条
	 * @param symbol:1币种
	 * */
	@ResponseBody
	@RequestMapping(value="/kline/trade_history",produces=JsonEncode)
	public String trade_history(
			HttpServletRequest request,
			@RequestParam(required=true)int symbol
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		int userid = 0;
		if(GetSession(request) != null){
			userid = GetSession(request).getFid();
		}
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		if(ftrademapping==null || ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			return null;
		}
		{
			int entrust_status[] = null;
			entrust_status = new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal};
			
			List<Fentrust> fentrusts1 = null ;
			fentrusts1 = this.frontTradeService.findFentrustHistory(
					userid, symbol,null, 0, 10, " fid desc ", entrust_status) ;
	        for (Fentrust fentrust : fentrusts1) {
	        	fentrust.setFtrademapping(null);
	        	fentrust.setFuser(null);
	        	fentrust.setFentrustType_s(getLocaleMessage(request,null,"enum.entrust."+fentrust.getFentrustType()));
	        	fentrust.setFstatus_s(getLocaleMessage(request,null,"enum.entrust.status."+fentrust.getFstatus()));
			}
			jsonObject.accumulate("fentrusts1", fentrusts1);
		}
		{
			int entrust_status[] = null;
			entrust_status = new int[]{EntrustStatusEnum.Cancel,EntrustStatusEnum.AllDeal};
			
			List<Fentrust> fentrusts2 = null ;
			fentrusts2 = this.frontTradeService.findFentrustHistory(
					userid, symbol,null, 0, 10, " fid desc ", entrust_status) ;
	        for (Fentrust fentrust : fentrusts2) {
	        	fentrust.setFtrademapping(null);
	        	fentrust.setFuser(null);
	        	fentrust.setFentrustType_s(getLocaleMessage(request,null,"enum.entrust."+fentrust.getFentrustType()));
	        	//System.out.println(fentrust.getFentrustType_s()+"  " + getLocaleMessage(request,null,"enum.entrust."+fentrust.getFentrustType()));
	        	fentrust.setFstatus_s(getLocaleMessage(request,null,"enum.entrust.status."+fentrust.getFstatus()));
			}
			jsonObject.accumulate("fentrusts2", fentrusts2);
		}
		jsonObject.accumulate("code", 0);
		//System.out.println(jsonObject.toString());
		return jsonObject.toString() ;
	}
	
	public static String getLocaleMessage(HttpServletRequest request,Object[] args,String code){
        WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
        return ac.getMessage(code,args, RequestContextUtils.getLocale(request));
    }
	
	
	@ResponseBody
	@RequestMapping(value="/trade/buyBtcSubmit",produces={JsonEncode})
	public String buyBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int limited,//是否按照市场价买入
			@RequestParam(required=true)int symbol,//币种
			@RequestParam(required=true)double tradeAmount,//数量
			@RequestParam(required=true)double tradeCnyPrice,//单价
			@RequestParam(required=false,defaultValue="")String tradePwd
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(isNeedTradePassword(request)){
			if(tradePwd == null || tradePwd.trim().length() == 0){
				jsonObject.accumulate("resultCode", -50) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.tranincorrect"));
				return jsonObject.toString() ;
			}
			
			if(fuser.getFtradePassword() == null){
				jsonObject.accumulate("resultCode", -5) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.notradpwd"));
				return jsonObject.toString() ;
			}
			if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
				jsonObject.accumulate("resultCode", -2) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.tranincorrect"));
				return jsonObject.toString() ;
			}
		}
		return this.buy(request, limited, symbol, tradeAmount, tradeCnyPrice,fuser) ;
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
		
		//检测是否只允许指定APP注册用户进行交易
		String forceAppApiUid = this.frontConstantMapService.getString("forceAppApiUid_"+symbol);
		if(null!=forceAppApiUid  && !forceAppApiUid.equals("") && !forceAppApiUid.equals(fuser.getFregfrom())) {
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"市场只允许指定APP注册用户交易"));
			return jsonObject.toString() ;
		}
		
		//不允许挂买单
		if(null!=ftrademapping.getFblock()  && ftrademapping.getFblock().equals("DISBUY")){
			String unDisUid = this.frontConstantMapService.getString("UNDISUID_"+symbol);
			int unDuid = 0;
			if(null!=unDisUid && !unDisUid.equals("")) {
				try {
					unDuid = Integer.valueOf(unDisUid).intValue();
				}catch(Exception e) {
					unDuid = 0;
				}
			}
			if(fuser.getFid()!=unDuid) {
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"市场不允许挂买单"));
				return jsonObject.toString() ;
			}
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
		
		//获取系统配置的市场交易购买总量限制
		Object maxBuyLimitCount = this.frontConstantMapService.get("maxLimitTotalBuyVol_"+symbol);
		if(null != maxBuyLimitCount &&  Double.valueOf((String)maxBuyLimitCount) >0
				&& StringUtils.isNotBlank((String)maxBuyLimitCount)) {
			//检测是否有不受限账号
			int maxUid = 0;
			String maxBuyUnlimitUID = this.frontConstantMapService.getString("maxBuyUnlimitUID_"+symbol);
			if(null!=maxBuyUnlimitUID && !maxBuyUnlimitUID.equals("")) {
				try {
					maxUid = Integer.valueOf(maxBuyUnlimitUID).intValue();
				}catch(Exception e) {
					maxUid = 0;
				}
			}
			if(fuser.getFid()!=maxUid) {
				//校验用户当前市场购买量是否超出系统配置的总购买量
				double totalBuyCount = this.getTotalBuyCount(fuser, ftrademapping);
				//超出最大总购买数量
				if((totalBuyCount+tradeAmount) > Double.valueOf((String)maxBuyLimitCount)) {
					jsonObject.accumulate("resultCode", -1) ;
					jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.maxtotalbuyvol"));
					return jsonObject.toString() ;
				}
			}
		}
		
		
		//定价单
		if(limited == 0 ){
			
			if(tradeAmount<minBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.mintravol")+coin2.getfSymbol()+minBuyCount);
				return jsonObject.toString() ;
			}
			
			
			if(tradeAmount>maxBuyCount && !fuser.isFismerchant()){
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
			
			if(total > maxBuyAmount && !fuser.isFismerchant()){
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
				if(tradeCnyPrice > maxBuyPrice && !fuser.isFismerchant()){
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
	
	
	
	@ResponseBody
	@RequestMapping(value="/trade/sellBtcSubmit",produces={JsonEncode})
	public String sellBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int limited,//是否按照市场价买入
			@RequestParam(required=true)int symbol,//币种
			@RequestParam(required=true)double tradeAmount,//数量
			@RequestParam(required=true)double tradeCnyPrice,//单价
			@RequestParam(required=false,defaultValue="")String tradePwd
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(isNeedTradePassword(request)){
			if(tradePwd == null || tradePwd.trim().length() == 0){
				jsonObject.accumulate("resultCode", -50) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.tranincorrect"));
				return jsonObject.toString() ;
			}
			
			if(fuser.getFtradePassword() == null){
				jsonObject.accumulate("resultCode", -5) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.trade.notradpwd"));
				return jsonObject.toString() ;
			}
			if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
				jsonObject.accumulate("resultCode", -2) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.user.tranincorrect"));
				return jsonObject.toString() ;
			}
		}
		return this.sell(request, limited, symbol, tradeAmount, tradeCnyPrice,fuser) ;
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
		
		//检测是否只允许指定APP注册用户进行交易
		String forceAppApiUid = this.frontConstantMapService.getString("forceAppApiUid_"+symbol);
		if(null!=forceAppApiUid && !forceAppApiUid.equals("") && !forceAppApiUid.equals(fuser.getFregfrom())) {
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"市场只允许指定APP注册用户交易"));
			return jsonObject.toString() ;
		}
		
		//不允许挂卖单
		if(null!=ftrademapping.getFblock()  && ftrademapping.getFblock().equals("DISSELL")){
			String unDisUid = this.frontConstantMapService.getString("UNDISUID_"+symbol);
			int unDuid = 0;
			if(null!=unDisUid && !unDisUid.equals("")) {
				try {
					unDuid = Integer.valueOf(unDisUid).intValue();
				}catch(Exception e) {
					unDuid = 0;
				}
			}
			if(fuser.getFid()!=unDuid) {
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"市场不允许挂卖单"));
				return jsonObject.toString() ;
			}
			
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
			
			if(tradeAmount>maxBuyCount && !fuser.isFismerchant()){
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
			
			if(total > maxBuyAmount && !fuser.isFismerchant()){
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
				if(tradeCnyPrice > maxBuyPrice && !fuser.isFismerchant()){
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
	
	
	/**
	 * 统计用户在该市场已购买的总量
	 * @param fuser
	 * @param ftrademapping
	 * @return
	 */
	private double getTotalBuyCount(Fuser fuser,Ftrademapping ftrademapping) {
		
		//统计用户买入完全成交及撤销的总量
		String sqlfilter = "select sum(a.fCount-a.fleftCount) from fentrust a"
				+"   where a.fEntrustType = "+ EntrustTypeEnum.BUY
				+"   and a.fStatus in ("+ EntrustStatusEnum.AllDeal +"," + EntrustStatusEnum.Cancel +")"
				+"   and a.ftrademapping = "+ ftrademapping.getFid()
				+"   and a.FUs_fId = "+ fuser.getFid();
		double buyEntrustCount1 = adminService.getSQLValue(sqlfilter);
		
		//统计用户买入未成交和部分成交的总量
		sqlfilter = "select sum(a.fCount) from fentrust a"
				+"   where a.fEntrustType = "+ EntrustTypeEnum.BUY
				+"   and a.fStatus in ("+ EntrustStatusEnum.Going +"," + EntrustStatusEnum.PartDeal +")"
				+"   and a.ftrademapping = "+ ftrademapping.getFid()
				+"   and a.FUs_fId = "+ fuser.getFid();
		
		double buyEntrustCount2 = adminService.getSQLValue(sqlfilter);
		
		double totalBuyCount = Utils.getDouble(buyEntrustCount1 + buyEntrustCount2, ftrademapping.getFcount2());
		
		return totalBuyCount;
	}
	
}
