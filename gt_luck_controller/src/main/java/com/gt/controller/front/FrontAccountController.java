package com.gt.controller.front;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.gt.Enum.BankTypeEnum;
import com.gt.Enum.CapitalOperationTypeEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.RemittanceTypeEnum;
import com.gt.Enum.TradeRecordTypeEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.comm.KeyValues;
import com.gt.controller.BaseController;
import com.gt.entity.Fasset;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwithdrawfees;
import com.gt.entity.Systembankinfo;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.admin.WithdrawFeesService;
import com.gt.service.front.FrontAccountService;
import com.gt.service.front.FrontBankInfoService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.PaginUtil;
import com.gt.util.Utils;


@Controller
public class FrontAccountController extends BaseController{
	@Autowired
	private FrontBankInfoService frontBankInfoService ;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private CapitaloperationService capitaloperationService;
	//充值
	@RequestMapping("account/rechargeCny")
	public ModelAndView rechargeCny(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int type
			) throws Exception{
		type =0;
		if(type !=0 /* && type !=3&& type !=4*/){
			type =0;
		}
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		//人民币随机尾数
		int randomMoney = (new Random().nextInt(80)+11) ;
		//系统银行账号
		List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo() ;
		modelAndView.addObject("randomMoney",randomMoney) ;
		modelAndView.addObject("bankInfo",systembankinfos) ;
		
		//record
		Fuser fuser = this.GetSession(request) ;
		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_IN+"\n");
//		filter.append("and fstatus <>"+ CapitalOperationInStatus.Invalidate+"\n");
		if(type !=0){
			filter.append("and fremittanceType='"+RemittanceTypeEnum.getEnumString(type)+"' \n");
		}else{
			filter.append("and systembankinfo is not null \n");
		}
		filter.append(" order by fid desc \n");
		List<Fcapitaloperation> list = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter.toString(), true,Fcapitaloperation.class);
		
		
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
		String url = "/account/rechargeCny.html?type="+type+"&";
		String pagin = PaginUtil.generatePagin(PaginUtil.totalPage(totalCount, maxResults),currentPage,url) ;
		
		//最小最大充值金额
		double minRecharge = Double.parseDouble(this.frontConstantMapService.get("minrechargecny").toString().trim()) ;
		double maxRecharge = Double.parseDouble(this.frontConstantMapService.get("maxrechargecny").toString().trim()) ;
		modelAndView.addObject("minRecharge", minRecharge) ;
		modelAndView.addObject("maxRecharge", maxRecharge) ;
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
//		boolean isproxy = false;
//		String ss = "where fuser.fid="+fuser.getFid()+" and fstatus=1";
//		int cc = this.adminService.getAllCount("Fproxy", ss);
//		if(cc >0){
//			isproxy = true;
//		}
//		modelAndView.addObject("isproxy", isproxy) ;
		
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("currentPage", currentPage) ;
		modelAndView.addObject("fuser",GetSession(request)) ;
		modelAndView.addObject("type", type) ;
		modelAndView.setViewName("front/account/account_rechargecny"+type) ;
		
		
		if(this.isMobile(request))
		{
			int totalPage = PaginUtil.totalPage(totalCount, maxResults);
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.setViewName("mobile/account/account_rechargecny"+type) ;
			if(currentPage>1)
			{
				modelAndView.setViewName("mobile/account/account_rechargecny"+type+"_ajax");
			}
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("financial/assetsrecord")
	public ModelAndView assetsrecord(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage
			)  throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE, 1);
		modelAndView.addObject("fvirtualcointypes", fvirtualcointypes) ;
		
		int maxResults = Constant.RecordPerPage ;
		int firstResult = PaginUtil.firstResult(currentPage, maxResults) ;
		String filter =  " where fuser.fid="+fuser.getFid()+" and status=1 order by fid desc " ;
		List<Fasset> list= this.utilsService.list(firstResult, maxResults,filter, true, Fasset.class) ;
		int total = this.utilsService.count(filter, Fasset.class) ;
		String pagin = PaginUtil.generatePagin(PaginUtil.firstResult(currentPage, maxResults), currentPage, "/financial/assetsrecord.html?") ;
		modelAndView.addObject("list",list) ;
		modelAndView.addObject("pagin",pagin);
		
		//处理json
		for (Fasset fasset : list) {
			fasset.parseJson(fvirtualcointypes);
		}
		
		modelAndView.setViewName("front/financial/assetsrecord") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/financial/assetsrecord") ;
		}
		return modelAndView ;
	}
	
	
	@RequestMapping("/exchange/success")
	public ModelAndView orderSuccess(HttpServletRequest request)throws Exception{
			ModelAndView modelAndView = new ModelAndView("front/exchange/success") ;
		modelAndView.addObject("massage", "交易成功");
		modelAndView.addObject("success", true);
		return modelAndView;
	}
	
	
	//充值
	@RequestMapping("exchange/rechargeUsdt")
	public ModelAndView rechargeUsdt(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		//人民币随机尾数
		int randomMoney = (new Random().nextInt(80)+11) ;
		//系统银行账号
		List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo() ;
		Systembankinfo bankInfo = systembankinfos.get(0);
		modelAndView.addObject("randomMoney",randomMoney) ;
		modelAndView.addObject("bankInfo",bankInfo) ;
		
		//record
		Fuser fuser = this.GetSession(request) ;
		
	
		//最小最大充值金额
		double minRecharge = Double.parseDouble(this.frontConstantMapService.get("minrechargecny").toString().trim()) ;
		double maxRecharge = Double.parseDouble(this.frontConstantMapService.get("maxrechargecny").toString().trim()) ;
		modelAndView.addObject("minRecharge", minRecharge) ;
		modelAndView.addObject("maxRecharge", maxRecharge) ;
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		modelAndView.addObject("fuser",GetSession(request)) ;
		modelAndView.setViewName("front/exchange/rechargeusdt") ;
		
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE,1);
		modelAndView.addObject("amountTypeMap", allType);
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/exchange/rechargeusdt") ;
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("account/rechargeBtc")
	public ModelAndView rechargeBtc(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE 
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal 
				|| !fvirtualcointype.isFisrecharge()){
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <> "+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		Fvirtualaddress fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
		
		//最近十次充值记录
		String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_IN
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/rechargeBtc.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("fvirtualcaptualoperations",fvirtualcaptualoperations) ;
		modelAndView.addObject("fvirtualcointype",fvirtualcointype) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("fvirtualaddress", fvirtualaddress) ;
		modelAndView.setViewName("front/account/account_rechargebtc") ;
		
		if(this.isMobile(request))
		{
			int totalPage = totalCount/maxResults+( (totalCount%maxResults)==0?0:1);
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.addObject("currentPage", currentPage) ;
			modelAndView.setViewName("mobile/account/account_rechargebtc") ;
			if(currentPage>1)
			{
				modelAndView.setViewName("mobile/account/account_rechargebtc_ajax") ;
			}
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("account/withdrawBtc")
	public ModelAndView withdrawBtc(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				/* || !fvirtualcointype.isFIsWithDraw()*/ ||fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE){
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		
		String sql ="where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		/*//以太坊系的代币共用相同的以太坊收款地址
		if (fvirtualcointype.isFisEth() && !fvirtualcointype.equals("ETH") && !fvirtualcointype.equals("ETC")){
			String filter = "where fstatus=1 and FIsWithDraw=1 and fShortName='ETH' and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			Fvirtualcointype ethcoin = alls.get(0);
			sql ="where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+ethcoin.getFid();
		}*/
		List<FvirtualaddressWithdraw> fvirtualaddressWithdraws = this.frontVirtualCoinService.findFvirtualaddressWithdraws(0, 0, sql, false);
		
		
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		
		
		
		//近10条提现记录
		String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_OUT
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/withdrawBtc.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("pagin", pagin) ;
		
		int isEmptyAuth = 0;
		if(fuser.isFisTelephoneBind() || fuser.getFgoogleBind()){
			isEmptyAuth = 1;
		}
		modelAndView.addObject("isEmptyAuth",isEmptyAuth) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
        
        String xx = "where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and (ffee >0 or flevel=5)  order by flevel asc";
        List<Fwithdrawfees> withdrawFees = this.withdrawFeesService.list(0, 0, xx, false);
        modelAndView.addObject("withdrawFees",withdrawFees) ;
		
		modelAndView.addObject("symbol",symbol) ;
		modelAndView.addObject("fvirtualcaptualoperations", fvirtualcaptualoperations) ;
		modelAndView.addObject("fvirtualwallet",fvirtualwallet) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fvirtualaddressWithdraws", fvirtualaddressWithdraws) ;
		modelAndView.addObject("fvirtualcointype",fvirtualcointype) ;
		modelAndView.setViewName("front/account/account_withdrawbtc") ;
		
		if(this.isMobile(request))
		{
			int totalPage = totalCount/maxResults+( (totalCount%maxResults)==0?0:1);
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.addObject("currentPage", currentPage) ;
			modelAndView.setViewName("mobile/account/account_withdrawbtc") ;
			if(currentPage>1)
			{
				modelAndView.setViewName("mobile/account/account_withdrawbtc_ajax") ;
			}
		}
		
		return modelAndView ;
	}
	
	
	@RequestMapping("exchange/withdrawUsdt")
	public ModelAndView withdrawUsdt(
			@RequestParam(required=false,defaultValue="1")int currentPage,
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> fbankinfoWithdraws =this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
			int l = fbankinfoWithdraw.getFbankNumber().length();
			fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l-4, l));
		}
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		Fvirtualwallet fwallet = this.frontUserService.findUSDTWalletByUser(GetSession(request).getFid());
		request.getSession().setAttribute("fwallet", fwallet) ;
		
		double fee = this.withdrawFeesService.findFfee(fwallet.getFvirtualcointype().getFid(),fuser.getFscore().getFlevel()).getFfee();
		modelAndView.addObject("fee", fee) ;
		
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fbankinfoWithdraws",fbankinfoWithdraws) ;
		modelAndView.setViewName("front/exchange/withdrawusdt") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/exchange/withdrawusdt") ;
			
		}
		
		return modelAndView ;
	}
	
	
	@RequestMapping("/account/record")
	public ModelAndView record(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int recordType,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="2")int datetype,
			@RequestParam(required=false,defaultValue="")String begindate,
			@RequestParam(required=false,defaultValue="")String enddate
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!(datetype >=1 && datetype <=4)){
			datetype =2;
		}
		if(enddate == null || enddate.trim().length() ==0){
			enddate = sdf.format(new Date());
		}else{
			try {
				enddate = sdf.format(sdf.parse(HtmlUtils.htmlEscape(enddate)));
			} catch (Exception e) {
				enddate = "";
			}
		}
		if(begindate == null || begindate.trim().length() ==0){
			switch (datetype) {
			case 1:
				begindate = sdf.format(new Date());
				break;
	        case 2:
	        	begindate = Utils.getAfterDay(7);
				break;
	        case 3:
	        	begindate = Utils.getAfterDay(15);
	    	    break;
	        case 4:
	        	begindate = Utils.getAfterDay(30);
		       break;
			}
		}else{
			try {
				begindate = sdf.format(sdf.parse(HtmlUtils.htmlEscape(begindate)));
			} catch (Exception e) {
				begindate = "";
			}
		}
		
		
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null || fvirtualcointype.getFtype() == CoinTypeEnum.FB_CNY_VALUE){
			fvirtualcointype = this.frontVirtualCoinService.findFirstFirtualCoin() ;
			symbol = fvirtualcointype.getFid() ;
		}
		
		if(recordType>TradeRecordTypeEnum.USDT_WITHDRAW){
			recordType = TradeRecordTypeEnum.USDT_WITHDRAW ;
		}
		
		String filter = "where fstatus=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and (FIsWithDraw=1 or fisrecharge=1)";
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
		//过滤器
		List<KeyValues> filters = new ArrayList<KeyValues>() ;
		for (int i = 6; i >= TradeRecordTypeEnum.BTC_RECHARGE; i--) {
			if(i==1 || i==2){
				KeyValues keyValues = new KeyValues() ;
				String key = "/account/record.html?recordType="+i+"&symbol=0" ;
				String value = TradeRecordTypeEnum.getEnumString(i,request) ;
				keyValues.setKey(key) ;
				keyValues.setValue(value) ;
				filters.add(keyValues) ;
			}else if(i==5 || i==6){
				KeyValues keyValues = new KeyValues() ;
				String key = "/account/record.html?recordType="+i+"&symbol=4" ;
				String value = TradeRecordTypeEnum.getEnumString(i,request) ;
				keyValues.setKey(key) ;
				keyValues.setValue(value) ;
				filters.add(keyValues) ;
			}else{
				String key = "/account/record.html?recordType="+i+"&symbol=" ;
				for (int j = 0; j < fvirtualcointypes.size(); j++) {
					String value = TradeRecordTypeEnum.getEnumString(i,request) ;
					Fvirtualcointype vc = fvirtualcointypes.get(j) ;
					
					if(i==TradeRecordTypeEnum.BTC_WITHDRAW){
						if(!vc.isFIsWithDraw()){
							continue ;
						}
					}
					
					if(i==TradeRecordTypeEnum.BTC_RECHARGE){
						if(!vc.isFisrecharge()){
							continue ;
						}
					}
					
					value = vc.getfShortName() + " " +value ;
					KeyValues keyValues = new KeyValues() ;
					keyValues.setKey(key+vc.getFid()) ;
					keyValues.setValue(value) ;
					filters.add(keyValues) ;
				}
			}
		}
		
		//内容
		List list = new ArrayList() ;
		int totalCount = 0 ;
		String pagin = "" ;
		String param = "";
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		switch (recordType) {
		case TradeRecordTypeEnum.CNY_RECHARGE:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_IN
			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);
			break;
			
		case TradeRecordTypeEnum.CNY_WITHDRAW:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_OUT
					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);

			break;
		case TradeRecordTypeEnum.BTC_RECHARGE:
			param = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" and ftype ="+VirtualCapitalOperationTypeEnum.COIN_IN
					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list =  this.frontVirtualCoinService.findFvirtualcaptualoperations(PaginUtil.firstResult(currentPage, maxResults),maxResults,param,true) ;
			totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", param);
			
			break;
		case TradeRecordTypeEnum.BTC_WITHDRAW:
			param = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" and ftype ="+VirtualCapitalOperationTypeEnum.COIN_OUT
			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list =  this.frontVirtualCoinService.findFvirtualcaptualoperations(PaginUtil.firstResult(currentPage, maxResults),maxResults,param,true) ;
			totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", param);
			
			break;
		case TradeRecordTypeEnum.USDT_RECHARGE:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.USDT_IN
			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);
			break;
			
		case TradeRecordTypeEnum.USDT_WITHDRAW:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.USDT_OUT
					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);

			break;
//		case TradeRecordTypeEnum.BTC_BUY:
//			param = "where fuser.fid="+fuser.getFid()+" and fentrustType="+EntrustTypeEnum.BUY+" and fvirtualcointype.fid="+fvirtualcointype.getFid()
//					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
//			list = this.frontTradeService.findFentrustHistory(PaginUtil.firstResult(currentPage, maxResults), param, true);
//			totalCount = this.adminService.getAllCount("Fentrust", param);
//			
//			break;
//		case TradeRecordTypeEnum.BTC_SELL:
//			param = "where fuser.fid="+fuser.getFid()+" and fentrustType="+EntrustTypeEnum.SELL+" and fvirtualcointype.fid="+fvirtualcointype.getFid()
//			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
//			list = this.frontTradeService.findFentrustHistory(PaginUtil.firstResult(currentPage, maxResults), param, true);
//			totalCount = this.adminService.getAllCount("Fentrust", param);
//			
//			break;
		}
		
		String url = "/account/record.html?recordType="+recordType+"&symbol="+symbol+"&datetype="+datetype+"&begindate="+begindate+"&enddate="+enddate+"&";
		pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		modelAndView.addObject("datetype", datetype) ;
		modelAndView.addObject("begindate", begindate) ;
		modelAndView.addObject("enddate", enddate) ;
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin",pagin) ;
		modelAndView.addObject("recordType",recordType ) ;
		modelAndView.addObject("symbol" ,symbol) ;
		modelAndView.addObject("filters", filters) ;
		modelAndView.addObject("fvirtualcointype", fvirtualcointype) ;
		if(recordType==1 || recordType==2 || recordType==5 || recordType==6 ){
			modelAndView.addObject("select", TradeRecordTypeEnum.getEnumString(recordType,request)) ;
		}else{
			modelAndView.addObject("select", fvirtualcointype.getfShortName()+" "+TradeRecordTypeEnum.getEnumString(recordType,request)) ;
		}
		modelAndView.setViewName("front/account/account_record") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/account/account_record") ;
		}
		
		return modelAndView ;
	}
	@RequestMapping(value="/exchange/index")
	public ModelAndView exchange_index(
			HttpServletRequest request
			) throws Exception{

		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.GetSession(request) ;
		
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.setViewName("front/exchange/index") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/exchange/index") ;
		}
		return modelAndView ;
	}
	@RequestMapping("exchange/recordUsdt")
	public ModelAndView recordUsdt(
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int type,
			@RequestParam(required=false,defaultValue="0")int operId,
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		//type 0  购买记录
		//type 1 兑换记录
		if(type!=0){
			type=1;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = null;
		//编号不为空，则尝试提取订单
		if (operId>0){
			fcapitaloperation = this.frontAccountService.findCapitalOperationById(operId) ;
		}
		
		//如果获取到了订单，则需要根据订单类型来提取数据
		if (fcapitaloperation!=null){
			if (fcapitaloperation.getFtype()==CapitalOperationTypeEnum.USDT_IN){
				type=0;
			}else{
				type=1;
			}
			
			operId = fcapitaloperation.getFid();
		}
		
		//獲得千12條交易記錄
		String param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.USDT_IN+" order by fid desc";
		if (type==1){
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.USDT_OUT+" order by fid desc";
		}
		List<Fcapitaloperation> fcapitaloperations = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param, true) ;
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", param.toString());
		String url = "/exchange/recordUsdt.html?type="+type+'&';
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		//如果订单为空，则获取用户的最新一条订单
		if (fcapitaloperation==null && fcapitaloperations.size()>0){
			fcapitaloperation = fcapitaloperations.get(0);
			operId = fcapitaloperation.getFid();
		}
		
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("type", type) ;
		modelAndView.addObject("fcapitaloperations", fcapitaloperations) ;
		modelAndView.addObject("fcapitaloperation", fcapitaloperation) ;
		modelAndView.addObject("operId", operId) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("currentPage",currentPage) ;
		modelAndView.addObject("cur_page",currentPage) ;
		modelAndView.setViewName("front/exchange/recordusdt") ;
		int totalPage = totalCount/maxResults +(totalCount%maxResults==0?0:1) ;
		modelAndView.addObject("totalPage",totalPage) ;	
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/exchange/recordusdt") ;
			if(currentPage>1)
			{
				modelAndView.setViewName("mobile/exchange/recordusdt_ajax") ;
			}			
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("exchange/exchangeUsdt")
	public ModelAndView exchangeUsdt(
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="")String code,
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("code",code) ;
		modelAndView.setViewName("front/exchange/exchangeusdt") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/exchange/exchangeusdt") ;
		}
		
		return modelAndView ;
	}
	

	@RequestMapping(value="/account/refTenbody")
	public ModelAndView refTenbody(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int type
			) throws Exception{
		if(type !=0/* &&  type !=3 && type !=4*/){
			type =0;
		}
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.GetSession(request) ;
		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_IN+"\n");
		if(type !=0){
			filter.append("and fremittanceType='"+RemittanceTypeEnum.getEnumString(type)+"' \n");
		}else{
			filter.append("and systembankinfo is not null \n");
		}
		filter.append(" order by fid desc \n");
		List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*maxResults, maxResults, filter.toString(), true);
		
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
		String url = "/account/rechargeCny.html?type="+type+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("currentPage_page", currentPage) ;
		modelAndView.setViewName("front/account/reftenbody") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/account/reftenbody") ;
		}
		return modelAndView ;
	}
	
	@RequestMapping(value="/exchange/refTenUsdtbody")
	public ModelAndView refTenUsdtbody(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int type,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception{

		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.GetSession(request) ;
		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and ftype ="+CapitalOperationTypeEnum.USDT_IN+"\n");
		filter.append("and systembankinfo is not null \n");
		
		filter.append(" order by fid desc \n");
		List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*maxResults, maxResults, filter.toString(), true);
		
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
		String url = "/exchange/recordUsdt.html?type="+type+'&';
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("currentPage_page", currentPage) ;
		modelAndView.addObject("currentPage",currentPage) ;
		modelAndView.setViewName("front/exchange/reftenusdtbody") ;
		modelAndView.addObject("type", type) ;
		int totalPage = totalCount/maxResults +(totalCount%maxResults==0?0:1) ;
		modelAndView.addObject("totalPage",totalPage) ;	
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/exchange/reftenusdtbody") ;
		}
		return modelAndView ;
	}
	
	
	@RequestMapping("account/transfer")
	public ModelAndView transfer(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				 || !fvirtualcointype.isFIsWithDraw() ||fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE){
			String filter = "where fstatus=1 and fisTransfer=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		
		String sql ="where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		List<FvirtualaddressWithdraw> fvirtualaddressWithdraws = this.frontVirtualCoinService.findFvirtualaddressWithdraws(0, 0, sql, false);
		
		
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		
		
		
		//近10条提现记录,转账使用提现地址字段存放收款人ID
		String filter ="where (fuser.fid="+fuser.getFid()+" or withdraw_virtual_address='"+fuser.getFid()+"') and ftype="+VirtualCapitalOperationTypeEnum.COIN_TRANSFER
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/transfer.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("pagin", pagin) ;
		
		int isEmptyAuth = 0;
		if(fuser.isFisTelephoneBind() || fuser.getFgoogleBind()){
			isEmptyAuth = 1;
		}
		modelAndView.addObject("isEmptyAuth",isEmptyAuth) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
        
        //按照USDT提现的费率处理，最高不超过4级
        int level = fuser.getFscore().getFlevel();
        if (level>4){
        	level=4;
        }
        double fee = this.withdrawFeesService.findFfee(4,level).getFfee();
		modelAndView.addObject("fee", fee) ;
		modelAndView.addObject("level", level) ;
		
		modelAndView.addObject("symbol",symbol) ;
		modelAndView.addObject("fvirtualcaptualoperations", fvirtualcaptualoperations) ;
		modelAndView.addObject("fvirtualwallet",fvirtualwallet) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fvirtualaddressWithdraws", fvirtualaddressWithdraws) ;
		modelAndView.addObject("fvirtualcointype",fvirtualcointype) ;
		modelAndView.setViewName("front/account/account_transfer") ;
		
		if(this.isMobile(request))
		{
			int totalPage = totalCount/maxResults+( (totalCount%maxResults)==0?0:1);
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.addObject("currentPage", currentPage) ;
			modelAndView.setViewName("mobile/account/account_transfer") ;
			if(currentPage>1)
			{
				modelAndView.setViewName("mobile/account/account_transfer_ajax") ;
			}
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("account/withdrawCny")
	public ModelAndView withdrawCny(
			@RequestParam(required=false,defaultValue="1")int currentPage,
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> fbankinfoWithdraws =this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
			int l = fbankinfoWithdraw.getFbankNumber().length();
			fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l-4, l));
		}
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid());
		request.getSession().setAttribute("fwallet", fwallet) ;
		
		double fee = this.withdrawFeesService.findFfee(fwallet.getFvirtualcointype().getFid(),fuser.getFscore().getFlevel()).getFfee();
		modelAndView.addObject("fee", fee) ;
		
		//獲得千12條交易記錄
		String param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_OUT+" order by fid desc";
		List<Fcapitaloperation> fcapitaloperations = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param, true) ;
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", param.toString());
		String url = "/account/withdrawCny.html?";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("fcapitaloperations", fcapitaloperations) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fbankinfoWithdraws",fbankinfoWithdraws) ;
		modelAndView.setViewName("front/account/account_withdrawcny") ;
		
		if(this.isMobile(request))
		{
			
			int totalPage = PaginUtil.totalPage(totalCount, maxResults);
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.addObject("currentPage", currentPage) ;
			modelAndView.setViewName("mobile/account/account_withdrawcny") ;
			if(currentPage>1)
			{
				modelAndView.setViewName("mobile/account/account_withdrawcny_ajax");
			}
			
		}
		
		return modelAndView ;
	}
	
}
