package com.gt.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fdrawaccounts;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontDrawAccountsService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;
import com.gt.util.XlsExport;

/**
 * 虚拟币划账  admin--controller
 * @author zhouyong
 *
 */
@Controller
public class DrawAccountsController extends BaseAdminController {
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontDrawAccountsService frontDrawAccountsService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontUserService frontUserService;
	
	private int numPerPage = Utils.getNumPerPage();
	
	
	@RequestMapping("/buluo718admin/drawAccountsList")
	public ModelAndView drawAccountsList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/drawAccountsList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String ftype = request.getParameter("ftype");
		String coinType = request.getParameter("coinType");
		String drawAccountsFrom = request.getParameter("drawAccountsFrom");
		String drawAccountsTo = request.getParameter("drawAccountsTo");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1  \n");
		
		if (StringUtils.isNotBlank(drawAccountsFrom)) {
			try {
				int fid = Integer.parseInt(drawAccountsFrom);
				filter.append(" and fuserFrom.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and fuserFrom.frealName like '%" + drawAccountsFrom + "%' \n");
			}
			modelAndView.addObject("drawAccountsFrom", drawAccountsFrom);
		}
		
		if (StringUtils.isNotBlank(drawAccountsTo)) {
			try {
				int fid = Integer.parseInt(drawAccountsTo);
				filter.append(" and fuserTo.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and fuserTo.frealName like '%" + drawAccountsTo + "%' \n");
			}
			modelAndView.addObject("drawAccountsTo", drawAccountsTo);
		}
		
		if (StringUtils.isNotBlank(ftype) && !ftype.equals("-10")) {
			filter.append(" and ftype =" + Integer.parseInt(ftype) + " \n");
			modelAndView.addObject("ftype", ftype);
		}
		if (StringUtils.isNotBlank(coinType) && !coinType.equals("0")) {
			filter.append(" and fcointype.fid =" + Integer.parseInt(coinType) + " \n");
			modelAndView.addObject("coinType", coinType);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append(" and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append(" and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}


		if (orderField != null && orderField.trim().length() > 0) {
			filter.append(" order by " + orderField + "\n");
		}else{
			filter.append(" order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}else{
			filter.append( " desc \n");
		}
		
		//虚拟币列表
		List<Fvirtualcointype> cointype = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map<Integer, Object> coinTypeMap = new HashMap<Integer, Object>();
		for (Fvirtualcointype fvirtualcointype : cointype) {
			coinTypeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		coinTypeMap.put(0, "全部");
		modelAndView.addObject("coinTypeMap", coinTypeMap);

		List<Fdrawaccounts> list = this.frontDrawAccountsService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("drawAccountsList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "drawAccountsList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fdrawaccounts", filter+""));
		modelAndView.addObject("totalAmount",this.adminService.getAllSum("Fdrawaccounts", "famount",filter+""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/drawAccountReport")
	public ModelAndView drawAccountReport(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/drawAccountReport");
		
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(d);
		
		String filterapp = "where ftype=1 and fcointype.fid =";
		String filterjys = "where ftype=2 and fcointype.fid = 20";
		
		double appbfsc = this.adminService.getAllSum("Fdrawaccounts", "famount",filterapp+"20");
		double appusdt = this.adminService.getAllSum("Fdrawaccounts", "famount",filterapp+"4");
		double jysbfs = this.adminService.getAllSum("Fdrawaccounts", "famount",filterjys);
		
		modelAndView.addObject("appbfsc", appbfsc);
		modelAndView.addObject("appusdt", appusdt);
		modelAndView.addObject("jysbfs",jysbfs);
		modelAndView.addObject("now",now);
		modelAndView.addObject("rel", "drawAccountsList");
		return modelAndView;
	}
	
	
	private static enum ExportFiled {
		订单号,划账人ID,划账人姓名,被划账人ID,被划账人姓名,划账币种,划账数量,创建时间;
	}

	//查询划账数据
	private List<Fdrawaccounts> getDrawAccountsList(HttpServletRequest request) {
		
		String ftype = request.getParameter("ftype");
		String coinType = request.getParameter("coinType");
		// 搜索关键字
		String drawAccountsFrom = request.getParameter("drawAccountsFrom");
		String drawAccountsTo = request.getParameter("drawAccountsTo");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1  \n");
		
		
		if (StringUtils.isNotBlank(drawAccountsFrom)) {
			try {
				int fid = Integer.parseInt(drawAccountsFrom);
				filter.append(" and fuserFrom.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and fuserFrom.frealName like '%" + drawAccountsFrom + "%' \n");
			}
		}
		
		if (StringUtils.isNotBlank(drawAccountsTo)) {
			try {
				int fid = Integer.parseInt(drawAccountsTo);
				filter.append(" and fuserTo.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and fuserTo.frealName like '%" + drawAccountsTo + "%' \n");
			}
		}
		
		if (StringUtils.isNotBlank(ftype) && !ftype.equals("-10")) {
			filter.append(" and ftype =" + Integer.parseInt(ftype) + " \n");
		}
		
		if (StringUtils.isNotBlank(coinType) && !coinType.equals("0")) {
			filter.append(" and fcointype.fid =" + Integer.parseInt(coinType) + " \n");
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		}else{
			filter.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}else{
			filter.append( " desc \n");
		}


		List<Fdrawaccounts> list = this.frontDrawAccountsService.list(0, 0, filter+"",false);
		return list;
	}
	
	
	/**
	 * 导出划账记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/drawAccountsExport")
	public String drawAccountsExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=drawAccountsExport.xls");
		
		
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

	//	订单号,划账人ID,划账人姓名,被划账人ID,被划账人姓名,划账币种,划账数量,创建时间;
		List<Fdrawaccounts> drawaccountsList = this.getDrawAccountsList(request);
		for (Fdrawaccounts drawaccounts : drawaccountsList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 订单号:
					e.setCell(filed.ordinal(), drawaccounts.getFid());
					break;
				case 划账人ID:
					e.setCell(filed.ordinal(), drawaccounts.getFuserFrom().getFid());
					break;
				case 划账人姓名:
					e.setCell(filed.ordinal(), drawaccounts.getFuserFrom().getFrealName());
					break;
				case 被划账人ID:
					e.setCell(filed.ordinal(), drawaccounts.getFuserTo().getFid());
					break;
				case 被划账人姓名:
					e.setCell(filed.ordinal(), drawaccounts.getFuserTo().getFrealName());
					break;
				case 划账币种:
					e.setCell(filed.ordinal(), drawaccounts.getFcointype().getfShortName());
					break;
				case 划账数量:
					e.setCell(filed.ordinal(), Utils.getDouble(drawaccounts.getFamount(), 4));
					break;
				case 创建时间:
					e.setCell(filed.ordinal(), Utils.dateFormat(drawaccounts.getFcreateTime()));
					break;
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		return null;
	}
	
	
	/**
	 * 钱包划转到交易所BFSC统计
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/walletToExcUsdtList")
	public ModelAndView walletToExcUsdtList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/walletToExcUsdtList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String userId = request.getParameter("userId");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int type = 1;
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and t.fType = "+ type +" \n");
		
		//虚拟币列表
		String drawaccountsCfg = frontConstantMapService.getString("drawaccounts");
		
		String[] drawaccounts = drawaccountsCfg.split("#");
		int drawaccountsUserId = Integer.parseInt(drawaccounts[0]);
		String[] drawaccountsCoinId = drawaccounts[1].split(",");
		
		int usdtCoinId = 0;
		for (String coinId : drawaccountsCoinId) {
			Fvirtualcointype cointype = this.virtualCoinService.findById(Integer.valueOf(coinId));
			if("USDT".equals(cointype.getfShortName())) {
				usdtCoinId = cointype.getFid();
			}
		}
		filter.append(" and t.fCointype = "+usdtCoinId+" \n");
		
		
		if (StringUtils.isNotBlank(userId)) {
			try {
				int fid = Integer.parseInt(userId);
				filter.append(" and t.fUserTo =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and a.fRealName like '%" + userId + "%' \n");
			}
			modelAndView.addObject("userId", userId);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}
		

		List<Map<String, Object>> list = this.frontDrawAccountsService.queryDrawaccountsStatisticsList((currentPage-1)*numPerPage, numPerPage, filter+"",true, type);
		
		int total = this.frontDrawAccountsService.getDrawaccountsStatisticsCount(filter+"", type);
		
		modelAndView.addObject("walletToExcUsdtList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "walletToExcUsdtList");
		modelAndView.addObject("totalCount",total);
		return modelAndView;
	}
	
	/**
	 * 钱包划转到交易所BFSC统计
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/walletToExcBfscList")
	public ModelAndView walletToExchangeList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/walletToExcBfscList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String userId = request.getParameter("userId");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int type = 1;
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and t.fType = "+ type +" \n");
		
		//虚拟币列表
		String drawaccountsCfg = frontConstantMapService.getString("drawaccounts");
		
		String[] drawaccounts = drawaccountsCfg.split("#");
		int drawaccountsUserId = Integer.parseInt(drawaccounts[0]);
		String[] drawaccountsCoinId = drawaccounts[1].split(",");
		
		int bfscCoinId = 0;
		for (String coinId : drawaccountsCoinId) {
			Fvirtualcointype cointype = this.virtualCoinService.findById(Integer.valueOf(coinId));
			if("BFSC".equals(cointype.getfShortName())) {
				bfscCoinId = cointype.getFid();
			}
		}
		filter.append(" and t.fCointype = "+bfscCoinId+" \n");
		
		if (StringUtils.isNotBlank(userId)) {
			try {
				int fid = Integer.parseInt(userId);
				filter.append(" and t.fUserTo =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and a.fRealName like '%" + userId + "%' \n");
			}
			modelAndView.addObject("userId", userId);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}
		

		List<Map<String, Object>> list = this.frontDrawAccountsService.queryDrawaccountsStatisticsList((currentPage-1)*numPerPage, numPerPage, filter+"",true, type);
		
		int total = this.frontDrawAccountsService.getDrawaccountsStatisticsCount(filter+"", type);
		
		modelAndView.addObject("walletToExcBfscList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "walletToExcBfscList");
		modelAndView.addObject("totalCount",total);
		return modelAndView;
	}
	
	
	/**
	 * 交易所划转到钱包BFSC统计
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/excToWalletBfscList")
	public ModelAndView excToWalletBfscList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/excToWalletBfscList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String userId = request.getParameter("userId");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int type = 2;
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and t.fType = "+ type +" \n");
		
		//虚拟币列表
		String drawaccountsCfg = frontConstantMapService.getString("drawaccounts");
		
		String[] drawaccounts = drawaccountsCfg.split("#");
		int drawaccountsUserId = Integer.parseInt(drawaccounts[0]);
		String[] drawaccountsCoinId = drawaccounts[1].split(",");
		
		int bfscCoinId = 0;
		Map<Integer, Object> coinTypeMap = new HashMap<Integer, Object>();
		for (String coinId : drawaccountsCoinId) {
			Fvirtualcointype cointype = this.virtualCoinService.findById(Integer.valueOf(coinId));
			if("BFSC".equals(cointype.getfShortName())) {
				bfscCoinId = cointype.getFid();
			}
		}
		filter.append(" and t.fCointype = "+bfscCoinId+" \n");
		
		if (StringUtils.isNotBlank(userId)) {
			try {
				int fid = Integer.parseInt(userId);
				filter.append(" and t.fUserFrom =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and a.fRealName like '%" + userId + "%' \n");
			}
			modelAndView.addObject("userId", userId);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		List<Map<String, Object>> list = this.frontDrawAccountsService.queryDrawaccountsStatisticsList((currentPage-1)*numPerPage, numPerPage, filter+"",true, type);
		
		int total = this.frontDrawAccountsService.getDrawaccountsStatisticsCount(filter+"", type);
		
		modelAndView.addObject("excToWalletBfscList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "excToWalletBfscList");
		modelAndView.addObject("totalCount",total);
		return modelAndView;
	}
	
	
	
	private static enum DrawAccountsStatisticsExportFiled {
		用户ID,用户姓名,划账币种,划转类型,划账总数;
	}
	
	/**
	 * 导出划账统计记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/drawAccountsStatisticsExport")
	public String drawAccountsStatisticsExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=drawAccountsStatisticsExport.xls");
		
		
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (DrawAccountsStatisticsExportFiled filed : DrawAccountsStatisticsExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

	//	用户ID,用户姓名,划账币种,划转类型,划账总数;
		List<Map<String, Object>> drawaccountsStatisticsList = this.getDrawAccountsStatisticsList(request);
		for (Map<String, Object> drawaccountsMap : drawaccountsStatisticsList) {
			e.createRow(rowIndex++);
			for (DrawAccountsStatisticsExportFiled filed : DrawAccountsStatisticsExportFiled.values()) {
				switch (filed) {
				case 用户ID:
					e.setCell(filed.ordinal(), drawaccountsMap.get("userId").toString());
					break;
				case 用户姓名:
					e.setCell(filed.ordinal(), drawaccountsMap.get("userName").toString());
					break;
				case 划账币种:
					e.setCell(filed.ordinal(), drawaccountsMap.get("coinShortName").toString());
					break;
				case 划转类型:
					if(Integer.valueOf(drawaccountsMap.get("type").toString()) == 1) {
						e.setCell(filed.ordinal(), "钱包——>交易所");
					} else {
						e.setCell(filed.ordinal(), "交易所——>钱包");
					}
					break;
				case 划账总数:
					e.setCell(filed.ordinal(), drawaccountsMap.get("totalAmount").toString());
					break;
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		return null;
	}
	
	
	//查询划账统计数据
	private List<Map<String, Object>> getDrawAccountsStatisticsList(HttpServletRequest request) {
		
		// 搜索关键字
		String userId = request.getParameter("userId");
		String shortName = request.getParameter("shortName");
		int type = Integer.valueOf(request.getParameter("type"));
		
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and t.fType = "+ type +" \n");
		
		//虚拟币列表
		String drawaccountsCfg = frontConstantMapService.getString("drawaccounts");
		
		String[] drawaccounts = drawaccountsCfg.split("#");
		int drawaccountsUserId = Integer.parseInt(drawaccounts[0]);
		String[] drawaccountsCoinId = drawaccounts[1].split(",");
		
		int queryCoinId = 0;
		Map<Integer, Object> coinTypeMap = new HashMap<Integer, Object>();
		for (String coinId : drawaccountsCoinId) {
			Fvirtualcointype cointype = this.virtualCoinService.findById(Integer.valueOf(coinId));
			if(shortName.equals(cointype.getfShortName())) {
				queryCoinId = cointype.getFid();
			}
		}
		filter.append(" and t.fCointype = "+queryCoinId+" \n");
		
		
		if (StringUtils.isNotBlank(userId)) {
			if(type == 1) {
				try {
					int fid = Integer.parseInt(userId);
					filter.append(" and t.fUserTo =" + fid + " \n");
				} catch (Exception e) {
					filter.append(" and a.fRealName like '%" + userId + "%' \n");
				}
			} else {
				try {
					int fid = Integer.parseInt(userId);
					filter.append(" and t.fUserFrom =" + fid + " \n");
				} catch (Exception e) {
					filter.append(" and a.fRealName like '%" + userId + "%' \n");
				}
			}
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append(" and  DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
		}

		List<Map<String, Object>> list = this.frontDrawAccountsService.queryDrawaccountsStatisticsList(0, 0, filter+"",false, type);
		return list;
	}
	
}
