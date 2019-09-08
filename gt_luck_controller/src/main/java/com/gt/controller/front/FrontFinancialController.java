package com.gt.controller.front;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.BankTypeEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fuser;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Constant;
import com.gt.util.Utils;

@Controller
public class FrontFinancialController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	
	@RequestMapping("/financial/index")
	public ModelAndView index(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request) != null){
			//用户钱包
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid()) ;
			modelAndView.addObject("fwallet", fwallet) ;
			//USDT钱包
			Fvirtualwallet usdtfwallet = this.frontUserService.findUSDTWalletByUser(GetSession(request).getFid()) ;
			modelAndView.addObject("usdtfwallet", usdtfwallet) ;
			
			//虚拟钱包full 
			LinkedHashMap<Fvirtualcointype,Fvirtualwallet> fvirtualwallets= this.frontUserService.findVirtualWallet(GetSession(request).getFid()) ;
			modelAndView.addObject("fvirtualwallets", fvirtualwallets) ; 
			//估计总资产，重点注意
			//CNY+冻结CNY+（币种+冻结币种）*最高买价
			//CNY先要折算成USDT来统计
			double totalCapital = 0F ;
			totalCapital+=((fwallet.getFtotal()+fwallet.getFfrozen())/6.5);
			totalCapital+=usdtfwallet.getFtotal()+usdtfwallet.getFfrozen();
			Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapService.get("tradeMappings");
			for (Map.Entry<Fvirtualcointype, Fvirtualwallet> entry : fvirtualwallets.entrySet()) {
				if(entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_CNY_VALUE || entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_USDT_VALUE) continue;
				try {
					if(tradeMappings!=null&&tradeMappings.containsKey(entry.getValue().getFvirtualcointype().getFid())){
						totalCapital += ( entry.getValue().getFfrozen()+entry.getValue().getFtotal() )* this.frontCacheService.getLatestDealPrize(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid())) ;

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			modelAndView.addObject("totalNet", Utils.getDouble(totalCapital, 2)) ;
			modelAndView.addObject("totalCapital", Utils.getDouble(totalCapital,2)) ;
			
			modelAndView.addObject("totalNetTrade", Utils.getDouble(totalCapital, 2)) ;
			modelAndView.addObject("totalCapitalTrade", Utils.getDouble(totalCapital,2)) ;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		String url = Constant.Domain+"?";
		if(fuser.getFregfrom() != null && fuser.getFregfrom().equals("agon")){
			url = url+"agon&";
		}
		if(fuser.getFuserNo() != null && fuser.getFuserNo().trim().length() >0){
			url = url+"sn="+fuser.getFuserNo().trim() +"&";
		}
		
		url = url+"r="+fuser.getFid();
		
		modelAndView.addObject("spreadLink", url) ;
		modelAndView.setViewName("front/financial/index") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/financial/index") ;
		}
		
		return modelAndView ;
	}


	@RequestMapping("/financial/accountbank")
	public ModelAndView accountbank(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> bankinfos = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : bankinfos) {
			try {
				int length = fbankinfoWithdraw.getFbankNumber().length();
				String number = "**** **** **** "+fbankinfoWithdraw.getFbankNumber().substring(length-4,length);
				fbankinfoWithdraw.setFbankNumber(number);
			} catch (Exception e) {}
		}
		modelAndView.addObject("bankinfos", bankinfos) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.setViewName("front/financial/accountbank") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/financial/accountbank") ;
		}
		
		return modelAndView ;
	}
	
	
	
	@RequestMapping("/financial/accountcoin")
	public ModelAndView accountcoin(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				||fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
				 || !fvirtualcointype.isFIsWithDraw()){
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+"  order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		String coinName = fvirtualcointype.getfShortName();
		
		String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		List<FvirtualaddressWithdraw> alls = this.frontVirtualCoinService.findFvirtualaddressWithdraws(0, 0, filter, false);
		modelAndView.addObject("alls", alls) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("coinName", coinName) ;
		modelAndView.setViewName("front/financial/accountcoin") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/financial/accountcoin") ;
		}
		
		return modelAndView ;
	}
}
