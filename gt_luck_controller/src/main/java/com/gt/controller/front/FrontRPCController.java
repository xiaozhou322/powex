package com.gt.controller.front;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Farticle;
import com.gt.entity.Fbanner;
import com.gt.entity.Fentrust;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.EntrustService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontVirtualCoinService;

import net.sf.json.JSONObject;

@Controller
public class FrontRPCController extends BaseController {

	@Autowired
	private FrontOthersService frontOtherService;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private EntrustService entrustService;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FrontConstantMapService constantMap ;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FrontEntrustChangeService frontEntrustChangeService;
	
	
	@ResponseBody
	@RequestMapping("/json/rpc")
	public String jsonrpc(
			HttpServletRequest request
			) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		//前置条件检查
		//1、远程调用方法名称，method
		//2、远程调用的方法参数值列表，params
		//3、签名，sign
		
		String method = "";
		String params = "";
		if (request.getParameter("params")!=null){
			params = request.getParameter("params").toString();
		}
		if (request.getParameter("method")!=null){
			jsonObject.accumulate("code","0");
			jsonObject.accumulate("error","call success");
			
			method=request.getParameter("method").toString();
			if (method.equals("reloadnews")){
				reloadnews();
			}else if (method.equals("reloadbanner")){
				reloadbanner();
			}else if (method.equals("reloadcoin")){
				reloadcoin();
			}else if (method.equals("blockUser")){
				if(params.equals("")){
					jsonObject.accumulate("code","-2");
					jsonObject.accumulate("error","lose params");
				}else{
					int uid = Integer.valueOf(params);
					blockUser(uid); 
				}
			}else if (method.equals("enableUser")){
				if(params.equals("")){
					jsonObject.accumulate("code","-2");
					jsonObject.accumulate("error","lose params");
				}else{
					int uid = Integer.valueOf(params);
					enableUser(uid);
				}
			}else if (method.equals("cancelEntrust")){
				if(params.equals("")){
					jsonObject.accumulate("code","-2");
					jsonObject.accumulate("error","lose params");
				}else{
					cancelEntrust(params); 
				}
			}else if (method.equals("getTradePrice")){
				if(params.equals("")){
					jsonObject.accumulate("code","-2");
					jsonObject.accumulate("error","lose params");
				}else{
					if(request.getParameter("params1")!=null){
						String params1 = request.getParameter("params1").toString();
						if (params1.equals("") || params1==null){
							jsonObject.accumulate("code","-2");
							jsonObject.accumulate("error","params1 invild");
						}else{
							jsonObject.accumulate("result",getTradePrice(Integer.valueOf(params),Double.valueOf(params1))+"");
						}
					}else{
						jsonObject.accumulate("code","-2");
						jsonObject.accumulate("error","lose params1");
					}
				}
			}else if (method.equals("resetsystemargs")){
				if(params.equals("")){
					jsonObject.accumulate("code","-2");
					jsonObject.accumulate("error","lose params");
				}else{
					String params1 = request.getParameter("params1").toString();
					resetsystemargs(params,params1); 
				}
			}else{
				jsonObject.accumulate("code","-5");
				jsonObject.accumulate("error","call a not exist method");
			}
		}else{
			jsonObject.accumulate("code","-1");
			jsonObject.accumulate("error","no method");
		}
		
		return jsonObject.toString() ;
		
	}
	
	//重新加载新闻
	private void reloadnews(){
		List<Farticle> farticles = this.frontOtherService.findFarticle(1, 0, 3) ;
		if(farticles != null && farticles.size() >0){
			this.constantMap.put("news", farticles);
		}
	}
	
	//重新加载banner
	private void reloadbanner(){
		List<Fbanner> fbanners = this.frontOtherService.findFbanner("index", 0, 3) ;
		if(fbanners != null && fbanners.size() >0){
			this.constantMap.put("fbanners", fbanners);
		}
	}
	
	//重新加载币种
	private void reloadcoin(){
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		this.constantMap.put("virtualCoinType", fvirtualcointypes) ;
		
		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		this.constantMap.put("allWithdrawCoins", allWithdrawCoins) ;
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			this.constantMap.put("allRechargeCoins", allRechargeCoins) ;
		}
		
		{
			String filter = "where fstatus=1 and fisTransfer=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allTransferCoins= this.virtualCoinService.list(0, 0, filter, false);
			this.constantMap.put("allTransferCoins", allTransferCoins) ;
		}
		
		String sql = "where fstatus=1";
		List<Fvirtualcointype> allCoins= this.virtualCoinService.list(0, 0, sql, false);
		this.constantMap.put("allCoins", allCoins) ;
	}
	
	//重新配置项
	private void resetsystemargs(String argkey,String argvalue){
		this.constantMap.put(argkey, argvalue);
	}
	
	//禁用用户
	private void blockUser(int uid){
		this.addBlackUser(uid);
	}
	//取消禁用
	private void enableUser(int uid){
		this.removeBlackUser(uid);
	}
	
	private double getTradePrice(int coinId,double amount){
		Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
		return this.frontCacheService.getLatestDealPrize(tradeMappings.get(coinId))*amount;
	}
	
	//取消交易
	private void cancelEntrust(String ids){
		String[] idString = ids.split(",");
		for (int i=0;i<idString.length;i++) {
			Fentrust fentrust = this.entrustService.findById(Integer.parseInt(idString[i]));
			if(fentrust!=null
					&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust, fentrust.getFuser()) ;
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
			}
		}
	}
}
