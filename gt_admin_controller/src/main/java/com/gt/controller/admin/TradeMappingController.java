package com.gt.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.PCoinTypeStatusEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Ffees;
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Ptrademapping;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.FeeService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.TradehistoryService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontPtrademappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.Utils;
import com.gt.utils.redis.RedisCacheUtil;

@Controller
public class TradeMappingController extends BaseAdminController {
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FeeService feesService;
	/*@Autowired
	private ConstantMap map;*/
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private UtilsService utilsService;
	@Autowired
	private FrontEntrustChangeService frontEntrustChangeService;
	@Autowired
	private FrontPtrademappingService frontPtrademappingService;
	@Autowired
	private TradehistoryService tradehistoryServiceImpl;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/tradeMappingList")
	public ModelAndView tradeMappingList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/tradeMappingList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}

        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 order by forder desc,fid asc\n");
//		if(keyWord != null && keyWord.trim().length() >0){
//			filter.append("and (fname like '%"+keyWord+"%' or \n");
//			filter.append("furl like '%"+keyWord+"%' ) \n");
//			modelAndView.addObject("keywords", keyWord);
//		}
//		if(orderField != null && orderField.trim().length() >0){
//			filter.append("order by "+orderField+"\n");
//		}else{
//			filter.append("order by forder desc\n");
//		}
//		
//		if(orderDirection != null && orderDirection.trim().length() >0){
//			filter.append(orderDirection+"\n");
//		}else{
//			filter.append("desc \n");
//		}
		
		List<Ftrademapping> list = this.tradeMappingService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("tradeMappingList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "tradeMappingList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ftrademapping", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goTradeMappingJSP")
	public ModelAndView goLimittradeJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ftrademapping ftradeMapping = this.tradeMappingService.findById(fid);
			modelAndView.addObject("ftradeMapping", ftradeMapping);
			
			String filter = "where ftrademapping.fid="+fid+" order by flevel asc";
			List<Ffees> allFees = this.feesService.list(0, 0, filter, false);
			modelAndView.addObject("allFees", allFees);
		}
		
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE,1);
		modelAndView.addObject("fvirtualcointypes", fvirtualcointypes);
		//暂时禁用人民币的法币显示
		List<Fvirtualcointype> fvirtualcointypes_fb = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.COIN_VALUE,1);
		modelAndView.addObject("fvirtualcointypes_fb", fvirtualcointypes_fb);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveTradeMapping")
	public ModelAndView saveTradeMapping(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fvirtualcointype1 = Integer.parseInt(request.getParameter("fvirtualcointype1"));
	    int fvirtualcointype2 = Integer.parseInt(request.getParameter("fvirtualcointype2"));
		String filter = "where fvirtualcointypeByFvirtualcointype1.fid="+fvirtualcointype1+" and fvirtualcointypeByFvirtualcointype2.fid="+fvirtualcointype2;
		int count = this.adminService.getAllCount("Ftrademapping", filter);
		if(count >0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","匹配记录重复，请更正");
			return modelAndView;
		}
		int count1 = Integer.parseInt(request.getParameter("fcount1"));
		int count2 = Integer.parseInt(request.getParameter("fcount2"));
		if(count1 >10 || count1 <1 || count2 >10 || count2 <1){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","小数位最小1位，最大10位");
			return modelAndView;
		}
		Ftrademapping tradeMapping = new Ftrademapping();
		Fvirtualcointype scbz = this.virtualCoinService.findById(fvirtualcointype2);
		Integer fprojectId =0;
		if(null!=scbz.getFprojectId()) {
			fprojectId = scbz.getFprojectId();
		}
		tradeMapping.setFcount1(count1);
		tradeMapping.setFcount2(count2);
		tradeMapping.setFisLimit(false);
		tradeMapping.setFmaxBuyPrice(Double.valueOf(request.getParameter("fmaxBuyPrice")));
		tradeMapping.setFmaxBuyAmount(Double.valueOf(request.getParameter("fmaxBuyAmount")));
		tradeMapping.setFmaxBuyCount(Double.valueOf(request.getParameter("fmaxBuyCount")));
		tradeMapping.setFminBuyAmount(Double.valueOf(request.getParameter("fminBuyAmount")));
		tradeMapping.setFminBuyCount(Double.valueOf(request.getParameter("fminBuyCount")));
		tradeMapping.setFminBuyPrice(Double.valueOf(request.getParameter("fminBuyPrice")));
		tradeMapping.setFprice(Double.valueOf(request.getParameter("fprice")));
		tradeMapping.setFstatus(TrademappingStatusEnum.FOBBID);
		tradeMapping.setFtradeTime(request.getParameter("ftradeTime"));
		tradeMapping.setFvirtualcointypeByFvirtualcointype1(this.virtualCoinService.findById(fvirtualcointype1));
		tradeMapping.setFvirtualcointypeByFvirtualcointype2(scbz);
		tradeMapping.setForder(Integer.valueOf(request.getParameter("forder")));
		tradeMapping.setFprojectId(fprojectId);
		int tid = this.tradeMappingService.saveObj(tradeMapping);
		
		Ftrademapping ftrademapping = this.tradeMappingService.findById(tid);
		
		//重新刷新缓存数据
		this.reloadTrademapping();
		
		for(int i=1;i<=Constant.VIP;i++){
			Ffees fees = new Ffees();
			fees.setFlevel(i);
			fees.setFtrademapping(ftrademapping);
			//设置默认交易费
			/*
			 * level 1,0.001
			 * level 2,0.0008
			 * level 3,0.0006
			 * level 4,0.0004
			 * level 5,0.0002
			 * level 6,0
			 * 
			 */
			fees.setFfee((6-i)*0.0002);
			fees.setFbuyfee((6-i)*0.0002);
			this.feesService.saveObj(fees);
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteTradeMapping")
	public ModelAndView deleteTradeMapping(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		tradeMapping.setFstatus(TrademappingStatusEnum.FOBBID);
		this.tradeMappingService.updateObj(tradeMapping);
		
		//重新刷新缓存数据
		this.reloadTrademapping();
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","禁用成功，请重启TOMCAT");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/pauseTradeMapping")
	public ModelAndView pauseTradeMapping(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		if(tradeMapping.getFstatus()==TrademappingStatusEnum.FOBBID  || tradeMapping.getFstatus()==TrademappingStatusEnum.PAUSE_HIDDEN) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "禁用市场不能进行停牌操作");
			return modelAndView;
		}
		if(tradeMapping.getFstatus()==TrademappingStatusEnum.ACTIVE) {
			tradeMapping.setFstatus(TrademappingStatusEnum.PAUSE);
		}else if(tradeMapping.getFstatus()==TrademappingStatusEnum.HIDDEN) {
			tradeMapping.setFstatus(TrademappingStatusEnum.PAUSE_HIDDEN);
		}
		this.tradeMappingService.updateObj(tradeMapping);
		
		//重新刷新缓存数据
		this.reloadTrademapping();
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","停牌成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/hiddenTradeMapping")
	public ModelAndView hiddenTradeMapping(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
	
		
		if(tradeMapping.getFstatus()==TrademappingStatusEnum.FOBBID || tradeMapping.getFstatus()==TrademappingStatusEnum.HIDDEN || tradeMapping.getFstatus()==TrademappingStatusEnum.PAUSE_HIDDEN) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "禁用市场或者隐藏市场不能进行隐藏操作");
			return modelAndView;
		}
		if(tradeMapping.getFstatus()==TrademappingStatusEnum.ACTIVE) {
			tradeMapping.setFstatus(TrademappingStatusEnum.HIDDEN);
		}else if(tradeMapping.getFstatus()==TrademappingStatusEnum.PAUSE) {
			tradeMapping.setFstatus(TrademappingStatusEnum.PAUSE_HIDDEN);
		}
		this.tradeMappingService.updateObj(tradeMapping);
		
		//重新刷新缓存数据
		this.reloadTrademapping();
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","隐藏成功");
		return modelAndView;
	}
	
	/*//设置为市场所在版块
	@RequestMapping("/buluo718admin/mainBoardTradeMapping")
	public ModelAndView mainBoardTradeMapping(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		
		tradeMapping.setFblock("main");
		
		this.tradeMappingService.updateObj(tradeMapping);
		
		//重新刷新缓存数据
		this.reloadTrademapping();
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","设置主板成功");
		return modelAndView;
	}
	
	//设置为市场所在版块
		@RequestMapping("/buluo718admin/otherBoardTradeMapping")
		public ModelAndView otherBoardTradeMapping(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
			
			tradeMapping.setFblock("other");
			
			this.tradeMappingService.updateObj(tradeMapping);
			
			//重新刷新缓存数据
			this.reloadTrademapping();
			
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","设置创新板成功");
			return modelAndView;
		}*/
		
		//设置禁止卖出
		@RequestMapping("/buluo718admin/TradeMappingDisSell")
		public ModelAndView TradeMappingDisSell(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
			
			tradeMapping.setFblock("DISSELL");
			
			this.tradeMappingService.updateObj(tradeMapping);
			
			//重新刷新缓存数据
			this.reloadTrademapping();
			
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","禁止卖出成功");
			return modelAndView;
		}
		
		//设置禁止卖出
		@RequestMapping("/buluo718admin/TradeMappingDisBuy")
		public ModelAndView TradeMappingDisBuy(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
			
			tradeMapping.setFblock("DISBUY");
			
			this.tradeMappingService.updateObj(tradeMapping);
			
			//重新刷新缓存数据
			this.reloadTrademapping();
			
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","禁止买入成功");
			return modelAndView;
		}
		//设置取消禁止
		@RequestMapping("/buluo718admin/TradeMappingDisCancle")
		public ModelAndView TradeMappingDisCancle(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
			
			tradeMapping.setFblock("");
			
			this.tradeMappingService.updateObj(tradeMapping);
			
			//重新刷新缓存数据
			this.reloadTrademapping();
			
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","市场取消禁止成功");
			return modelAndView;
		}
	
	@RequestMapping("/buluo718admin/goTradeMapping")
	public ModelAndView goTradeMapping(HttpServletRequest request) throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		int oldStatus =  tradeMapping.getFstatus();
		
		tradeMapping.setFstatus(TrademappingStatusEnum.ACTIVE);
		//项目方市场状态也需要修改
		Ptrademapping ptradeMapping = this.frontPtrademappingService.findByTrademappingId(fid);
		if(null!=ptradeMapping) {
			ptradeMapping.setStatus(PCoinTypeStatusEnum.NORMAL);
			this.tradeMappingService.updateTrademappings(tradeMapping, ptradeMapping);
		}else {
			this.tradeMappingService.updateObj(tradeMapping);
		}
		//重新刷新缓存数据
		this.reloadTrademapping();
		
		//如果是禁用状况启用则要处理K线数据初始化
		if(oldStatus==TrademappingStatusEnum.FOBBID) {
			//完成PeriodMap初始化，否则无法生成K线图
			frontEntrustChangeService.initPeriodMap(tradeMapping.getFid());
		}
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","启用成功，请重启TOMCAT");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/updateTradeMapping")
	public ModelAndView updateTradeMapping(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		int count1 = Integer.parseInt(request.getParameter("fcount1"));
		int count2 = Integer.parseInt(request.getParameter("fcount2"));
		if(count1 >10 || count1 <1 || count2 >10 || count2 <1){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","小数位最小1位，最大8位");
			return modelAndView;
		}
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		tradeMapping.setFcount1(count1);
		tradeMapping.setFcount2(count2);
		tradeMapping.setFisLimit(false);
		tradeMapping.setFmaxBuyPrice(Double.valueOf(request.getParameter("fmaxBuyPrice")));
		tradeMapping.setFmaxBuyAmount(Double.valueOf(request.getParameter("fmaxBuyAmount")));
		tradeMapping.setFmaxBuyCount(Double.valueOf(request.getParameter("fmaxBuyCount")));
		tradeMapping.setFminBuyAmount(Double.valueOf(request.getParameter("fminBuyAmount")));
		tradeMapping.setFminBuyCount(Double.valueOf(request.getParameter("fminBuyCount")));
		tradeMapping.setFminBuyPrice(Double.valueOf(request.getParameter("fminBuyPrice")));
		tradeMapping.setFprice(Double.valueOf(request.getParameter("fprice")));
		tradeMapping.setForder(Integer.valueOf(request.getParameter("forder")));
		tradeMapping.setFtradeTime(request.getParameter("ftradeTime"));

		this.tradeMappingService.updateObj(tradeMapping);
		
		//重新刷新缓存数据
		this.reloadTrademapping();
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateTradeFee")
	public ModelAndView updateTradeFee(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		int fid = Integer.parseInt(request.getParameter("fid"));
		List<Ffees> all = this.feesService.findByProperty("ftrademapping.fid", fid);
		
		//add by hank
		for (Ffees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			String buyfeeKey = "fbuyfee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			double buyfee = Double.valueOf(request.getParameter(buyfeeKey));
			
			if(fee>=1 || fee<0 || buyfee>=1 || buyfee<0){
				
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","手续费率只能是小于1的小数！");
				return modelAndView;
			}
		}
		
		for (Ffees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			String buyfeeKey = "fbuyfee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			double buyfee = Double.valueOf(request.getParameter(buyfeeKey));
			ffees.setFfee(fee);
			ffees.setFbuyfee(buyfee);
			this.feesService.updateObj(ffees);
			redisCacheUtil.setCacheObject("front:rate:"+ffees.getFtrademapping().getFid()+"_buy_"+ffees.getFlevel(), ffees.getFbuyfee()+"");
			redisCacheUtil.setCacheObject("front:rate:"+ffees.getFtrademapping().getFid()+"_sell_"+ffees.getFlevel(), ffees.getFbuyfee()+"");
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	
	//重新刷新缓存数据
	private void reloadTrademapping() {
		String filter = " select distinct a.fvirtualcointypeByFvirtualcointype1 from  Ftrademapping a where a.fstatus=? order by a.fvirtualcointypeByFvirtualcointype1.forder asc" ;
		List<Fvirtualcointype> fbs = this.utilsService.findHQL(0, 0, filter, false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE ) ;
		this.frontConstantMapService.put("fbs", fbs);
		
		Map<Integer,Integer> tradeMappings = new HashMap<Integer,Integer>();
		String sql1 = "where fstatus="+TrademappingStatusEnum.ACTIVE;
		List<Ftrademapping> mappings = this.tradeMappingService.list(0, 0, sql1, false);
		for (Ftrademapping ftrademapping : mappings) {
			tradeMappings.put(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid(), ftrademapping.getFid());
		}
		this.frontConstantMapService.put("tradeMappings", tradeMappings);
		this.frontConstantMapService.put("tradeMappingss", mappings);
		
		{
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = sdf.format(c.getTime());
			String xx = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+key+"'";
			List<Ftradehistory> ftradehistorys = this.tradehistoryServiceImpl.list(0, 0, xx, false);
			this.frontConstantMapService.put("tradehistory", ftradehistorys);
		}
	}
}
