package com.gt.controller.admin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CapitalOperationInStatus;
import com.gt.Enum.CapitalOperationOutStatus;
import com.gt.Enum.CapitalOperationTypeEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.admin.EntrustService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;
import com.gt.util.XlsExport;

@Controller
public class ReportController extends BaseAdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService;
	@Autowired
	private EntrustService entrustService;
	@Autowired
	private UserService userService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	/*@Autowired
	private ConstantMap constantMap;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private TradeMappingService tradeMappingService;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
		
	@RequestMapping("/buluo718admin/totalReport")
	public ModelAndView totalReport(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.setViewName("ssadmin/totalReport");
		// 总会员数
		modelAndView.addObject("totalUser",
				this.adminService.getAllCount("Fuser", "where fstatus=1"));
		// 总认证数
		modelAndView.addObject("totalValidateUser",this.adminService.getAllCount("Fuser", "where fhasRealValidate=1"));

		// 今天注册数
		modelAndView.addObject("todayTotalUser",this.adminService.getAllCount("Fuser", "where date_format(fregisterTime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')"));
		// 今天认证数
		modelAndView.addObject("todayValidateUser", this.adminService.getAllCount("Fuser", "where date_format(fhasRealValidateTime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')"));

		// 全站总币数量
		List<Map> virtualQtyList = this.virtualWalletService.getTotalQty();
		modelAndView.addObject("virtualQtyList", virtualQtyList);

		// 今日充值总金额
		Map amountInMap = this.capitaloperationService.getTotalAmountIn(
				CapitalOperationTypeEnum.RMB_IN, "("
						+ CapitalOperationInStatus.Come + ")", true);
		Map totalAmountInMap = this.capitaloperationService.getTotalAmountIn(
				CapitalOperationTypeEnum.RMB_IN, "("
						+ CapitalOperationInStatus.Come + ")", false);
		modelAndView.addObject("amountInMap", amountInMap);
		modelAndView.addObject("totalAmountInMap", totalAmountInMap);
		
		String s1 = "SELECT sum(famount) from foperationlog where fstatus=2 and date_format(fcreatetime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d') and fuid not in (7215,42540,42544,42184,42186,10781,42513,42519,42191,42508,42505,42192,42193,42195,42196,42197,42507,42514,42090,42541,42543,42538,42539,42533,42537,42512,42520)";
		String s2 = "SELECT sum(famount) from foperationlog where fstatus=2 and fuid not in (7215,42540,42544,42184,42186,10781,42513,42519,42191,42508,42505,42192,42193,42195,42196,42197,42507,42514,42090,42541,42543,42538,42539,42533,42537,42512,42520)";
		double todayOpAmt = this.adminService.getSQLValue(s1);
		double totalOpAmt = this.adminService.getSQLValue(s2);
		modelAndView.addObject("todayOpAmt", todayOpAmt);
		modelAndView.addObject("totalOpAmt", totalOpAmt);
		
		String frozenQtySQL = "SELECT SUM(fqty) from fintrolinfo where ftitle like '%释放%' and date_format(fcreatetime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')";
		double frozenQty = this.adminService.getSQLValue(frozenQtySQL);
		modelAndView.addObject("frozenQty", frozenQty);
		
		// 今日提现总金额
		Map amountOutMap = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				true);
		Map amountOutMap1 = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				false);
		modelAndView.addObject("amountOutMap", amountOutMap);
		modelAndView.addObject("amountOutMap1", amountOutMap1);

		// 所有提现未转帐总金额
		String coStatus = "(" + CapitalOperationOutStatus.WaitForOperation
				+ "," + CapitalOperationOutStatus.OperationLock + ")";
		Map amountOutWaitingMap = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, coStatus, false);
		modelAndView.addObject("amountOutWaitingMap", amountOutWaitingMap);

		// 今日充值虚拟币总数量
		List virtualInMap = this.virtualCapitaloperationService.getTotalAmount(
				VirtualCapitalOperationTypeEnum.COIN_IN, "("
						+ VirtualCapitalOperationInStatusEnum.SUCCESS + ")",
				true);
		modelAndView.addObject("virtualInMap", virtualInMap);

		// 今日提现虚拟币
		List virtualOutSuccessMap = this.virtualCapitaloperationService
				.getTotalAmount(VirtualCapitalOperationTypeEnum.COIN_OUT, "("
						+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
						+ ")", true);
		modelAndView.addObject("virtualOutSuccessMap", virtualOutSuccessMap);

		// 等待提现虚拟币
		String voStatus = "("
				+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
				+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")";

		List virtualOutWaitingMap = this.virtualCapitaloperationService
				.getTotalAmount(VirtualCapitalOperationTypeEnum.COIN_OUT,
						voStatus, false);
		modelAndView.addObject("virtualOutWaitingMap", virtualOutWaitingMap);

		// 当前委托买入
		List entrustBuyMap = this.entrustService
				.getTotalQty(EntrustTypeEnum.BUY);
		modelAndView.addObject("entrustBuyMap", entrustBuyMap);

		// 当前委托卖出
		List entrustSellMap = this.entrustService
				.getTotalQty(EntrustTypeEnum.SELL);
		modelAndView.addObject("entrustSellMap", entrustSellMap);
		
		// 今日提现总手续费
		Map amountOutFeeMap1 = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				true,true);
		Map amountOutFeeMap2 = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				false,true);
		modelAndView.addObject("amountOutFeeMap1", amountOutFeeMap1);
		modelAndView.addObject("amountOutFeeMap2", amountOutFeeMap2);
		
		List entrustBuyFeesMap1 = this.entrustService
				.getTotalQty(EntrustTypeEnum.BUY,true);
		List entrustBuyFeesMap2 = this.entrustService
				.getTotalQty(EntrustTypeEnum.BUY,false);
		modelAndView.addObject("entrustBuyFeesMap1", entrustBuyFeesMap1);
		modelAndView.addObject("entrustBuyFeesMap2", entrustBuyFeesMap2);
		
		List entrustSellFeesMap1 = this.entrustService
				.getTotalQty(EntrustTypeEnum.SELL,true);
		List entrustSellFeesMap2 = this.entrustService
				.getTotalQty(EntrustTypeEnum.SELL,false);
		modelAndView.addObject("entrustSellFeesMap1", entrustSellFeesMap1);
		modelAndView.addObject("entrustSellFeesMap2", entrustSellFeesMap2);

		modelAndView.addObject("rel", "totalReport");
		return modelAndView;
	}
	

	@RequestMapping("/buluo718admin/userReport")
	public ModelAndView userReport(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userReport");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if ((startDate == null || startDate.trim().length() == 0)
				&&(endDate == null || endDate.trim().length() == 0)) {
			Calendar cal_1=Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cal_1.set(Calendar.DAY_OF_MONTH,1);
			startDate = sdf.format(cal_1.getTime());
			Calendar cale = Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cale.set(Calendar.DATE, cale.getActualMaximum(Calendar.DATE)); 
			endDate = sdf.format(cale.getTime());
		}


		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(endDate));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) + 1);
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and date_format(Fregistertime,'%Y-%m-%d') >='" + startDate + "' \n");
		}
		if(endDate != null && endDate.trim().length() >0){
    		filter.append("and date_format(Fregistertime,'%Y-%m-%d') <='" + endDate+ "' \n");
		}
		List all = this.userService.getUserGroup(filter + "");
		double total = 0;
		JSONArray key =new JSONArray();
		JSONArray value =new JSONArray();
		
		if(all != null && all.size() >0){
			Iterator it = all.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				key.add(o[1]);
				value.add(o[0]);
				total = total + Double.valueOf(o[0].toString());
			}
		}
		modelAndView.addObject("key", key);
		modelAndView.addObject("value",value);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("total", total);
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/capitaloperationReport")
	public ModelAndView capitaloperationReport(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(request.getParameter("url"));
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if ((startDate == null || startDate.trim().length() == 0)
				&&(endDate == null || endDate.trim().length() == 0)) {
			Calendar cal_1=Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cal_1.set(Calendar.DAY_OF_MONTH,1);
			startDate = sdf.format(cal_1.getTime());
			Calendar cale = Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cale.set(Calendar.DATE, cale.getActualMaximum(Calendar.DATE)); 
			endDate = sdf.format(cale.getTime());
		}

		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append("and date_format(fcreateTime,'%Y-%m-%d') >='" + startDate + "' \n");
		filter.append("and date_format(fcreateTime,'%Y-%m-%d') <='" + endDate + "' \n");
		filter.append("and ftype=" + request.getParameter("type") + "\n");
		filter.append("and fstatus =" + request.getParameter("status") + "\n");
		List all = this.capitaloperationService.getTotalGroup(filter.toString());
		BigDecimal total = BigDecimal.ZERO;
		JSONArray key =new JSONArray();
		JSONArray value =new JSONArray();
		if(all != null && all.size() >0){
			Iterator it = all.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				key.add(o[1]);
				value.add(o[0]);
				total = total.add(new BigDecimal(o[0].toString()));
			}
		}
		modelAndView.addObject("key", key);
		modelAndView.addObject("value",value);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("total", total);
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/vcOperationReport")
	public ModelAndView vcOperationReport(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ModelAndView modelAndView = new ModelAndView();
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		modelAndView.addObject("allType", allType);
		if (request.getParameter("vid") != null) {
			int vid = Integer.parseInt(request.getParameter("vid"));
			Fvirtualcointype coinType = this.virtualCoinService.findById(vid);
			modelAndView.addObject("vid", vid);
			modelAndView.addObject("coinType", coinType.getFname());
		}
		modelAndView.setViewName(request.getParameter("url"));
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if ((startDate == null || startDate.trim().length() == 0)
				&&(endDate == null || endDate.trim().length() == 0)) {
			Calendar cal_1=Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cal_1.set(Calendar.DAY_OF_MONTH,1);
			startDate = sdf.format(cal_1.getTime());
			Calendar cale = Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cale.set(Calendar.DATE, cale.getActualMaximum(Calendar.DATE)); 
			endDate = sdf.format(cale.getTime());
		}

		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append("and date_format(fcreateTime,'%Y-%m-%d') >='" + startDate + "' \n");
		filter.append("and date_format(fcreateTime,'%Y-%m-%d') <='" + endDate + "' \n");
		filter.append("and ftype=" + request.getParameter("type") + "\n");
		filter.append("and fstatus =" + request.getParameter("status") + "\n");
		filter.append("and fVi_fId2 =" + request.getParameter("vid") + "\n");
		List all = null;

		if (request.getParameter("vid") != null) {
			all = this.virtualCapitaloperationService
					.getTotalGroup(filter.toString());
		}

		
		BigDecimal total = BigDecimal.ZERO;
		JSONArray key =new JSONArray();
		JSONArray value =new JSONArray();
		
		if(all != null && all.size() >0){
			Iterator it = all.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				key.add(o[1]);
				value.add(o[0]);
				total = total.add(new BigDecimal(o[0].toString()));
			}
		}

		
		modelAndView.addObject("key", key);
		modelAndView.addObject("value",value);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("total", total);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/entrustMember")
	public ModelAndView MemberEntrust(HttpServletRequest request) throws Exception {
		//返回的查询时间
		List<Map> queryday = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//指定好统计起始时间
		
		String starday ="2017-11-01";
		Date starday1 = sdf.parse(starday);
		Calendar cale = Calendar.getInstance();
		Date now = new Date();
		cale.setTime(now);
		
		
		ModelAndView modelAndView = new ModelAndView();
		String ftype = "d";
		int currentPage = 1;
		String day = request.getParameter("day");
		
		String filter = "";
		if (request.getParameter("ftype") != null) {
			ftype = request.getParameter("ftype");
		}
		
		if (!Utils.isNumeric(day)){
			day = "0";
		}
		
		int rday = Integer.parseInt(day);
		int i=0;
		//如果不是指定类型，就强制设置为按日统计
		if (ftype.equals("m")){
			sdf = new SimpleDateFormat("yyyy-MM");
			ftype = "m";
			if (rday>12 || rday<0){
				rday=0;
			}
			cale.add(cale.MONTH, 0-rday);
			Date d = cale.getTime();
			//如果时间早于起始时间，则查询起始日期
			if (starday1.getTime() >d.getTime()){
				rday=0;
				cale.setTime(now);
			}
			day= sdf.format(cale.getTime());
			filter = " where fstatus=3 and DATE_FORMAT(flastUpdatTime,'%Y-%m') ='" + day +"' ";
			cale.setTime(now);
			for(i=0;i<12;i++){
				if (i!=0){
					cale.add(cale.MONTH, -1);
				}
				if (starday1.getTime() >cale.getTime().getTime()){
					break;
				}
				Map map = new HashMap();
				map.put("key", i);
				map.put("value", sdf.format(cale.getTime()));
				queryday.add(map);
			}
		}else if (ftype.equals("y")){
			sdf = new SimpleDateFormat("yyyy");
			ftype = "y";
			if (rday>6 || rday<0){
				rday=0;
			}
			cale.add(cale.YEAR, 0-rday);
			Date d = cale.getTime();
			//如果时间早于起始时间，则查询起始日期
			if (starday1.getTime() >d.getTime()){
				rday=0;
				cale.setTime(now);
			}
			day= sdf.format(cale.getTime());
			filter = " where fstatus=3 and DATE_FORMAT(flastUpdatTime,'%Y') ='" + day +"' ";
			cale.setTime(now);
			for(i=0;i<6;i++){
				if (i!=0){
					cale.add(cale.YEAR, -1);
				}
				if (starday1.getTime() >cale.getTime().getTime()){
					break;
				}
				Map map = new HashMap();
				map.put("key", i);
				map.put("value", sdf.format(cale.getTime()));
				queryday.add(map);
			}
		}else{
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			ftype = "d";
			if (rday>30 || rday<0){
				rday=0;
			}
			cale.add(cale.DAY_OF_YEAR, 0-rday);
			Date d = cale.getTime();
			//如果时间早于起始时间，则查询起始日期
			if (starday1.getTime() >d.getTime()){
				rday=0;
				cale.setTime(now);
			}
			day= sdf.format(cale.getTime());
			filter = " where fstatus=3 and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') ='" + day +"' ";
			cale.setTime(now);
			for(i=0;i<30;i++){
				if (i!=0){
					cale.add(cale.DAY_OF_YEAR, -1);
				}
				if (starday1.getTime() >cale.getTime().getTime()){
					break;
				}
				Map map = new HashMap();
				map.put("key", i);
				map.put("value", sdf.format(cale.getTime()));
				queryday.add(map);
			}
		}
		
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		List<Map> allmember = this.entrustService.getMemberQty(ftype, day, 1, 40);
		//加权参数
		Double adjustnum = 1d;
		/*if (constantMap.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(constantMap.getString("adjustvalue"));
		}*/
		if (frontConstantMapService.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(frontConstantMapService.getString("adjustvalue"));
		}
		
		Double entrustTotal = this.adminService.getAllSum("Fentrust", "fsuccessAmount", filter)*adjustnum;
		int total = this.adminService.getAllCount("Fentrust", filter);
		modelAndView.addObject("allmember", allmember);
		modelAndView.addObject("ftype", ftype);
		modelAndView.addObject("rday", rday);
		modelAndView.addObject("day", day);
		modelAndView.addObject("queryday", queryday);
		modelAndView.addObject("entrustTotal", entrustTotal);
		modelAndView.addObject("total", total);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.setViewName("ssadmin/entrustMemberList");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/entrustMemberMarket")
	public ModelAndView entrustMemberMarket(HttpServletRequest request) throws Exception {
		//返回的查询时间
		List<Map> queryday = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//指定好统计起始时间
		
		ModelAndView modelAndView = new ModelAndView();
		int ftype = 0;
		int currentPage = 1;
		String logdate = "";
		String logdate1 = "";
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 ");

		if (request.getParameter("logdate") != null) {
			logdate = request.getParameter("logdate");
		}
		
		if (request.getParameter("logdate1") != null) {
			logdate1 = request.getParameter("logdate1");
		}
		
		if (request.getParameter("ftype") != null) {
			ftype =Integer.valueOf(request.getParameter("ftype"));
		}
		if(logdate.equals("")){
			Calendar cale = Calendar.getInstance();
			Date now = new Date();
			cale.setTime(now);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			logdate= sdf.format(cale.getTime());
		}
		if (logdate1.equals("")){
			filter.append("  and fstatus=3 and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') ='" + logdate +"' ");
		}else{
			filter.append(" and fstatus=3 and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') >='" + logdate +"' and DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') <='" + logdate1 +"'");
		}
		List<Ftrademapping> list = this.tradeMappingService.list((currentPage-1)*numPerPage, numPerPage, "where fstatus=1",true);
		if(ftype==0){
			ftype = list.get(0).getFid();
		}
		filter.append( " and ftrademapping.fid="+ftype);
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		List<Map> allmember = null;
		
		if (logdate1.equals("")){
			allmember = this.entrustService.getMemberMarket(ftype, logdate, 1, 40);
		}else{
			allmember = this.entrustService.getMemberMarket(ftype, logdate,logdate1, 1, 40);
		}
		if (!this.isSuper(request)){
			for(Map am:allmember){
				if(am.get("uid").equals("7215")){
					allmember.remove(am);
					break;
				}
			}
		}
		//加权参数
		Double adjustnum = 1d;
		/*if (constantMap.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(constantMap.getString("adjustvalue"));
		}*/
		if (frontConstantMapService.get("adjustvalue")!=null){
			adjustnum = Double.valueOf(frontConstantMapService.getString("adjustvalue"));
		}
		
		Double entrustTotal = this.adminService.getAllSum("Fentrust", "fsuccessAmount", filter+"")*adjustnum;
		int total = this.adminService.getAllCount("Fentrust", filter+"");
		modelAndView.addObject("allmember", allmember);
		modelAndView.addObject("tmap", list);
		modelAndView.addObject("ftype", ftype);
		modelAndView.addObject("logdate", logdate);
		modelAndView.addObject("logdate1", logdate1);
		modelAndView.addObject("entrustTotal", entrustTotal);
		modelAndView.addObject("total", total);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.setViewName("ssadmin/entrustMemberMarketList");
		return modelAndView;
	}
	
	private static enum Export1Filed {
		会员UID, 会员真实姓名, 是否认证, 推荐人ID, 完全成交额;
	}
	
	@RequestMapping("/buluo718admin/enMemberExport")
	public ModelAndView enMemberExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		String day = request.getParameter("day");
		String ftype = request.getParameter("ftype");
		
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=vcOutExport.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export1Filed filed : Export1Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Map> alls = this.entrustService.getMemberQty(ftype, day, 1, 40);
		for (Map v : alls) {
			e.createRow(rowIndex++);
			for (Export1Filed filed : Export1Filed.values()) {
				switch (filed) {
				case 会员UID:
					e.setCell(filed.ordinal(), v.get("uid").toString());
					break;
				case 会员真实姓名:
					e.setCell(filed.ordinal(), v.get("realname").toString());
					break;
				case 是否认证:
					if(v.get("hasRealValidate").equals("1")){
						if(v.get("hasImgValidate").equals("1")){
							e.setCell(filed.ordinal(),"已完成KYC1、KYC2认证");
						}else{
							e.setCell(filed.ordinal(), "已完成KYC1认证");
						}
					}else{
						e.setCell(filed.ordinal(), "未认证");
					}
					break;
				case 推荐人ID:
					e.setCell(filed.ordinal(), v.get("iuid").toString());
					break;
				case 完全成交额:
					e.setCell(filed.ordinal(), v.get("total").toString());
					break;
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "导出成功");
		return modelAndView;
	}
}
