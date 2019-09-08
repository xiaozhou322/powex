package com.gt.controller.admin;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CapitalOperationInStatus;
import com.gt.Enum.CapitalOperationOutStatus;
import com.gt.Enum.CapitalOperationTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Systembankinfo;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.admin.IntrolinfoService;
import com.gt.service.admin.SystembankService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.service.front.FrontAccountService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;
import com.gt.util.XlsExport;


@Controller
public class CapitaloperationController extends BaseAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CapitaloperationController.class);

	private static final String CLASS_NAME = CapitaloperationController.class.getSimpleName();

	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private SystembankService systembankService;
	@Autowired
	private IntrolinfoService introlinfoService;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private UtilsService utilsService ;
	/*@Autowired
	private ConstantMap constantMap ;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private FrontAccountService frontAccountService;
	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/buluo718admin/capitaloperationList")
	public ModelAndView Index(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/capitaloperationList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and (fuser.fid =" + fid + " \n");
				filterSQL.append("OR fremark like '%" + keyWord + "%') \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append("AND fid =" + capitalId + " \n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}

		String status = request.getParameter("fstatus");
		if (status != null && status.trim().length() > 0) {
			String fstatus = status.trim();
			if (!fstatus.equals("0")) {
				if (fstatus.indexOf("充值") != -1) {
					filterSQL.append("AND ftype ="
							+ CapitalOperationTypeEnum.RMB_IN + " \n");
					filterSQL.append("AND fstatus ="
							+ fstatus.replace("充值-", "") + " \n");
				} else if (fstatus.indexOf("提现") != -1) {
					filterSQL.append("AND ftype ="
							+ CapitalOperationTypeEnum.RMB_OUT + " \n");
					filterSQL.append("AND fstatus ="
							+ fstatus.replace("提现-", "") + " \n");
				}
			}else{
				filterSQL.append("and ftype in ("+CapitalOperationTypeEnum.RMB_IN + ","+ CapitalOperationTypeEnum.RMB_OUT+") \n");
			}
			modelAndView.addObject("fstatus", fstatus);
		} else {
			filterSQL.append("and ftype in ("+CapitalOperationTypeEnum.RMB_IN + ","+ CapitalOperationTypeEnum.RMB_OUT+") \n");
			modelAndView.addObject("fstatus", "0");
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}

		Map<String,String> statusMap = new HashMap<String,String>();
		statusMap.put("0", "全部");
		statusMap.put(
				"充值-" + CapitalOperationInStatus.Come,
				"充值-"
						+ CapitalOperationInStatus
								.getEnumString(CapitalOperationInStatus.Come));
		statusMap
				.put("充值-" + CapitalOperationInStatus.Invalidate,
						"充值-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.Invalidate));
		statusMap
				.put("充值-" + CapitalOperationInStatus.NoGiven,
						"充值-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.NoGiven));
		statusMap
				.put("充值-" + CapitalOperationInStatus.WaitForComing,
						"充值-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.WaitForComing));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.Cancel,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.Cancel));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.OperationLock,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.OperationLock));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.OperationSuccess,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.OperationSuccess));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.WaitForOperation,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.WaitForOperation));
		modelAndView.addObject("statusMap", statusMap);

		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitaloperationList");
		// 总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fcapitaloperation", filterSQL+ ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/usdtCapitaloperationList")
	public ModelAndView usdtIndex(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/exchange/capitaloperationList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and (fuser.fid =" + fid + " \n");
				filterSQL.append("OR fremark like '%" + keyWord + "%') \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append("AND fid =" + capitalId + " \n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}

		String status = request.getParameter("fstatus");
		if (status != null && status.trim().length() > 0) {
			String fstatus = status.trim();
			if (!fstatus.equals("0")) {
				if (fstatus.indexOf("USDT购买") != -1) {
					filterSQL.append("AND ftype ="
							+ CapitalOperationTypeEnum.USDT_IN + " \n");
					filterSQL.append("AND fstatus ="
							+ fstatus.replace("USDT购买-", "") + " \n");
				} else if (fstatus.indexOf("USDT兑换") != -1) {
					filterSQL.append("AND ftype ="
							+ CapitalOperationTypeEnum.USDT_OUT + " \n");
					filterSQL.append("AND fstatus ="
							+ fstatus.replace("USDT兑换-", "") + " \n");
				}
			}else{
				filterSQL.append("and ftype in ("+CapitalOperationTypeEnum.USDT_IN + ","+ CapitalOperationTypeEnum.USDT_OUT+") \n");
			}
			modelAndView.addObject("fstatus", fstatus);
		} else {
			filterSQL.append("and ftype in ("+CapitalOperationTypeEnum.USDT_IN + ","+ CapitalOperationTypeEnum.USDT_OUT+") \n");
			modelAndView.addObject("fstatus", "0");
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}

		Map<String,String> statusMap = new HashMap<String,String>();
		statusMap.put("0", "全部");
		statusMap.put(
				"USDT购买-" + CapitalOperationInStatus.Come,
				"USDT购买-"
						+ CapitalOperationInStatus
								.getEnumString(CapitalOperationInStatus.Come));
		statusMap
				.put("USDT购买-" + CapitalOperationInStatus.Invalidate,
						"USDT购买-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.Invalidate));
		statusMap
				.put("USDT购买-" + CapitalOperationInStatus.NoGiven,
						"USDT购买-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.NoGiven));
		statusMap
				.put("USDT购买-" + CapitalOperationInStatus.WaitForComing,
						"USDT购买-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.WaitForComing));
		statusMap
				.put("USDT兑换-" + CapitalOperationOutStatus.Cancel,
						"USDT兑换-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.Cancel));
		statusMap
				.put("USDT兑换-" + CapitalOperationOutStatus.OperationLock,
						"USDT兑换-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.OperationLock));
		statusMap
				.put("USDT兑换-" + CapitalOperationOutStatus.OperationSuccess,
						"USDT兑换-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.OperationSuccess));
		statusMap
				.put("USDT兑换-" + CapitalOperationOutStatus.WaitForOperation,
						"USDT兑换-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.WaitForOperation));
		modelAndView.addObject("statusMap", statusMap);

		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitaloperationList");
		// 总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fcapitaloperation", filterSQL+ ""));
		return modelAndView;
	}

	
	@RequestMapping("/buluo718admin/capitalInList")
	public ModelAndView capitalInList(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String type = "(" + CapitalOperationTypeEnum.RMB_IN + ")";
		String status = "(" + CapitalOperationInStatus.WaitForComing + ")";
		modelAndView.setViewName("ssadmin/capitalInList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		//filterSQL.append("where 1=1 and fremittanceType<>'"+RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type3)+"' and fremittanceType<>'"+RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type4)+"' \n");
		filterSQL.append(" where  1=1 ");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");

		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and (fuser.fid =" + fid + " \n");
				filterSQL.append("OR fremark like '%" + keyWord + "%') \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalInList");
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
		modelAndView.addObject("currentTime", currentTime.getTime());
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtCapitalInList")
	public ModelAndView usdtCapitalInList(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String type = "(" + CapitalOperationTypeEnum.USDT_IN + ")";
		String status = "(" + CapitalOperationInStatus.WaitForComing + ")";
		modelAndView.setViewName("ssadmin/exchange/capitalInList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		//filterSQL.append("where 1=1 and fremittanceType<>'"+RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type3)+"' and fremittanceType<>'"+RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type4)+"' \n");
		filterSQL.append(" where  1=1 ");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");

		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and (fuser.fid =" + fid + " \n");
				filterSQL.append("OR fremark like '%" + keyWord + "%') \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalInList");
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
		modelAndView.addObject("currentTime", currentTime.getTime());
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/capitalInSucList")
	public ModelAndView capitalInSucList(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String type = "(" + CapitalOperationTypeEnum.RMB_IN + ")";
		String status = "(" + CapitalOperationInStatus.Come + ")";
		modelAndView.setViewName("ssadmin/capitalInSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");

		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and (fuser.fid =" + fid + " \n");
				filterSQL.append("OR fremark like '%" + keyWord + "%') \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalInSucList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/usdtCapitalInSucList")
	public ModelAndView usdtCapitalInSucList(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String type = "(" + CapitalOperationTypeEnum.USDT_IN + ")";
		String status = "(" + CapitalOperationInStatus.Come + ")";
		modelAndView.setViewName("ssadmin/exchange/capitalInSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");

		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and (fuser.fid =" + fid + " \n");
				filterSQL.append("OR fremark like '%" + keyWord + "%') \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalInSucList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		modelAndView.addObject(
				"totalAmount",
				this.adminService.getAllSum("Fcapitaloperation", "famount",filterSQL
						+ ""));
		return modelAndView;
	}

	
	@RequestMapping("/buluo718admin/capitalOutList")
	public ModelAndView capitalOutList(HttpServletRequest request) throws Exception {
		String type = "(" + CapitalOperationTypeEnum.RMB_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.WaitForOperation + ","
				+ CapitalOperationOutStatus.OperationLock + ")";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/capitalOutList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalOutList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtCapitalOutList")
	public ModelAndView usdtCapitalOutList(HttpServletRequest request) throws Exception {
		String type = "(" + CapitalOperationTypeEnum.USDT_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.WaitForOperation + ","
				+ CapitalOperationOutStatus.OperationLock + ")";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/exchange/capitalOutList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalOutList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/capitalOutSucList")
	public ModelAndView capitalOutSucList(HttpServletRequest request) throws Exception {
		String type = "(" + CapitalOperationTypeEnum.RMB_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.OperationSuccess + ")";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/capitalOutSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalOutSucList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtCapitalOutSucList")
	public ModelAndView usdtCapitalOutSucList(HttpServletRequest request) throws Exception {
		String type = "(" + CapitalOperationTypeEnum.USDT_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.OperationSuccess + ")";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/exchange/capitalOutSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalOutSucList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		modelAndView.addObject(
				"totalAmount",
				this.adminService.getAllSum("Fcapitaloperation", "famount",filterSQL
						+ ""));
		modelAndView.addObject(
				"totalFee",
				this.adminService.getAllSum("Fcapitaloperation", "ffees",filterSQL
						+ ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/goCapitaloperationJSP")
	public ModelAndView goCapitaloperationJSP(HttpServletRequest request) throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fcapitaloperation capitaloperation = this.capitaloperationService
					.findById(fid);
			Fuser fuser = this.frontUserService.findById(capitaloperation.getFuser().getFid());
			//充值类型需要传递官方收款银行信息
			if (capitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_IN || capitaloperation.getFtype()==CapitalOperationTypeEnum.USDT_IN){
				Systembankinfo systembankinfo = this.systembankService.findById(capitaloperation.getSystembankinfo().getFid());
				modelAndView.addObject("systembankinfo", systembankinfo);
			}
			modelAndView.addObject("capitaloperation", capitaloperation);
			modelAndView.addObject("fuser", fuser);
			
		}
		return modelAndView;
	}

	//取消充值
	@RequestMapping("/buluo718admin/capitalInCancel")
	public ModelAndView capitalInCancel(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		int status = capitalOperation.getFstatus();
		if (status == CapitalOperationInStatus.Come || status == CapitalOperationInStatus.Invalidate) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "取消失败");
			return modelAndView;
		}
		capitalOperation.setFstatus(CapitalOperationInStatus.SystemCancel);
		this.capitaloperationService.updateObj(capitalOperation);
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "取消成功");
		return modelAndView;
	}

	//取消充值
		@RequestMapping("/buluo718admin/usdtCapitalInCancel")
		public ModelAndView usdtCapitalInCancel(HttpServletRequest request) throws Exception {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fcapitaloperation capitalOperation = this.capitaloperationService
					.findById(fid);
			int status = capitalOperation.getFstatus();
			if (status == CapitalOperationInStatus.Come || status == CapitalOperationInStatus.Invalidate) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "取消失败");
				return modelAndView;
			}
			capitalOperation.setFstatus(CapitalOperationInStatus.SystemCancel);
			this.capitaloperationService.updateObj(capitalOperation);
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "取消成功");
			return modelAndView;
		}
		
	//人民币充值审核
	@RequestMapping("/buluo718admin/capitalInAudit")
	public ModelAndView capitalInAudit(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		LOGGER.info(CLASS_NAME + " capitalInAudit,人民币充值审核开始");
		int fid = Integer.parseInt(request.getParameter("uid"));
		
		String confirmCode = request.getParameter("confirmcode");
		
		LOGGER.info(CLASS_NAME + " capitalInAudit,根据id:{}查询充值流水", fid);
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		int status = capitalOperation.getFstatus();
		LOGGER.info(CLASS_NAME + " capitalInAudit,充值状态是：" + status);
		if (status != CapitalOperationInStatus.WaitForComing) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			String status_s = CapitalOperationInStatus
					.getEnumString(CapitalOperationInStatus.WaitForComing);
			modelAndView.addObject("message", "审核失败,只有状态为:" + status_s
					+ "的充值记录才允许审核!");
			LOGGER.info(CLASS_NAME + " capitalInAudit,状态错误，只有：" + status_s+"状态的充值记录才允许审核");
			return modelAndView;
		}
		LOGGER.info(CLASS_NAME + " capitalInAudit,根据会员用户id:{}查询钱包信息", capitalOperation.getFuser().getFid());
		// 根据用户查钱包最后修改时间
		Fvirtualwallet walletInfo = this.frontUserService.findWalletByUser(capitalOperation.getFuser().getFid());
		if (walletInfo == null) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，会员钱包信息异常!");
			LOGGER.info(CLASS_NAME + " capitalInAudit,查询会员钱包信息为空");
			return modelAndView;
		}
		double amount = capitalOperation.getFamount();

		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		
		//校验审核码，审核码正确才能进行人民币审核操作
		String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
		if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
			LOGGER.info(CLASS_NAME + " capitalInAudit,审核码错误");
			return modelAndView;
		}
		
		// 充值操作
		capitalOperation.setFstatus(CapitalOperationInStatus.Come);
		capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
		capitalOperation.setfAuditee_id(admin);
		// 钱包操作
		walletInfo.setFlastUpdateTime(Utils.getTimestamp());
		walletInfo.setFtotal(walletInfo.getFtotal() + amount);

		boolean flag = false;
		try {
			LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息开始");
			this.capitaloperationService.updateCapital(capitalOperation,
					walletInfo, true);
			LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息结束");
			flag = true;
		} catch (Exception e) {
			LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息异常,exception:{}",e);
			flag = false;
		}
		if (!flag) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		modelAndView.addObject("callbackType","closeCurrent");
		LOGGER.info(CLASS_NAME + " capitalInAudit,人民币充值审核结束");
		return modelAndView;
	}
	
	//USDT充值审核
		@RequestMapping("/buluo718admin/usdtCapitalInAudit")
		public ModelAndView usdtCapitalInAudit(HttpServletRequest request) throws Exception {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			LOGGER.info(CLASS_NAME + " capitalInAudit,人民币充值审核开始");
			int fid = Integer.parseInt(request.getParameter("uid"));
			
			String confirmCode = request.getParameter("confirmcode");
			
			LOGGER.info(CLASS_NAME + " capitalInAudit,根据id:{}查询充值流水", fid);
			Fcapitaloperation capitalOperation = this.capitaloperationService
					.findById(fid);
			int status = capitalOperation.getFstatus();
			LOGGER.info(CLASS_NAME + " capitalInAudit,充值状态是：" + status);
			if (status != CapitalOperationInStatus.WaitForComing) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationInStatus
						.getEnumString(CapitalOperationInStatus.WaitForComing);
				modelAndView.addObject("message", "审核失败,只有状态为:" + status_s
						+ "的充值记录才允许审核!");
				LOGGER.info(CLASS_NAME + " capitalInAudit,状态错误，只有：" + status_s+"状态的充值记录才允许审核");
				return modelAndView;
			}
			LOGGER.info(CLASS_NAME + " capitalInAudit,根据会员用户id:{}查询钱包信息", capitalOperation.getFuser().getFid());
			// 根据用户查USDT钱包最后修改时间
			Fvirtualwallet walletInfo = this.frontUserService.findUSDTWalletByUser(capitalOperation.getFuser().getFid());
			if (walletInfo == null) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，会员钱包信息异常!");
				LOGGER.info(CLASS_NAME + " capitalInAudit,查询会员钱包信息为空");
				return modelAndView;
			}
			//USDT充值，充入的是人民币，到账的是USDT，汇率是6.5，所以需要将金额除以汇率，才是实际到账的USDT
			double amount = capitalOperation.getFamount()/6.5;

			Fadmin admin = (Fadmin) request.getSession()
					.getAttribute("login_admin");
			
			//校验审核码，审核码正确才能进行人民币审核操作
			String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
			if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				LOGGER.info(CLASS_NAME + " capitalInAudit,审核码错误");
				return modelAndView;
			}
			
			// 充值操作
			capitalOperation.setFstatus(CapitalOperationInStatus.Come);
			capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
			capitalOperation.setfAuditee_id(admin);
			// 钱包操作
			walletInfo.setFlastUpdateTime(Utils.getTimestamp());
			walletInfo.setFtotal(walletInfo.getFtotal() + amount);
			
			boolean flag = false;
			try {
				LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息开始");
				this.capitaloperationService.updateCapital(capitalOperation,
						walletInfo, true);
				LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息结束");
				flag = true;
			} catch (Exception e) {
				LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息异常,exception:{}",e);
				flag = false;
			}
			if (!flag) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败");
				return modelAndView;
			}else{

				//设置交易密码奖励发放
				int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, capitalOperation.getFuser().getFid(),"首次充值奖励");
				if (sendcount==0 ) {
					Fintrolinfo introlInfo = null;
					Fvirtualwallet fvirtualwallet = null;
					//String[] auditSendCoin = constantMap.getString("tradePassWordSendCoin").split("#");
					String[] auditSendCoin = this.frontConstantMapService.get("bannertype").toString().split("#");
					int coinID = Integer.parseInt(auditSendCoin[0]);
					double coinQty = Double.valueOf(auditSendCoin[1]);
					Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findFvirtualCoinById(coinID);
					if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(capitalOperation.getFuser().getFid(), fvirtualcointype.getFid());
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(coinQty);
						introlInfo.setFuser(capitalOperation.getFuser());
						introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_CANDY);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						introlInfo.setFtitle("首次充值奖励");
					}
					this.userService.updateObj(null, introlInfo, fvirtualwallet);
				}
				modelAndView.addObject("statusCode", 200);
				modelAndView.addObject("message", "审核成功");
				modelAndView.addObject("callbackType","closeCurrent");
				LOGGER.info(CLASS_NAME + " capitalInAudit,人民币充值审核结束");
				return modelAndView;
			}
		}

	//人民币提现审核、锁定
	@RequestMapping("/buluo718admin/capitalOutAudit")
	public ModelAndView capitalOutAudit(HttpServletRequest request,
			@RequestParam(required = true) int type) throws Exception {
		LOGGER.info(CLASS_NAME + " capitalOutAudit 人民币提现审核、锁定开始,type:{}", type );
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		LOGGER.info(CLASS_NAME + " capitalOutAudit fid:{}", fid );
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		int status = capitalOperation.getFstatus();
		LOGGER.info(CLASS_NAME + " capitalOutAudit 查询提现流水状态status:{}", status );
		switch (type) {
		case 1:
			if (status != CapitalOperationOutStatus.OperationLock) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.OperationLock);
				modelAndView.addObject("message", "审核失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许审核!");
				return modelAndView;
			}
			break;
		case 2:
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许锁定!");
				return modelAndView;
			}
			break;
		case 3:
			if (status != CapitalOperationOutStatus.OperationLock) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.OperationLock);
				modelAndView.addObject("message", "取消锁定失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许取消锁定!");
				return modelAndView;
			}
			break;
		case 4:
			if (status == CapitalOperationOutStatus.Cancel || status == CapitalOperationOutStatus.OperationSuccess) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "取消失败!");
				return modelAndView;
			}
			break;
		default:
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "非法提交！");
			return modelAndView;
		}

		LOGGER.info(CLASS_NAME + " capitalOutAudit，根据用户id查询用户钱包开始");
		// 根据用户查钱包最后修改时间
		Fvirtualwallet walletInfo = this.frontUserService.findWalletByUser(capitalOperation.getFuser().getFid());
		LOGGER.info(CLASS_NAME + " capitalOutAudit，根据用户id查询用户钱包结束");
		if (walletInfo == null) {
			LOGGER.info(CLASS_NAME + " capitalOutAudit，查询用户钱包信息为空，用户id:{}", capitalOperation.getFuser().getFid());
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，会员钱包信息异常!");
			return modelAndView;
		}

		double amount = capitalOperation.getFamount();
		double frees = capitalOperation.getFfees();
		double totalAmt = Utils.getDouble(amount + frees,2);
		LOGGER.info(CLASS_NAME + " capitalOutAudit 本次提现金额:{},提现手续费:{}", amount, frees);
		// 充值操作
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
		capitalOperation.setfAuditee_id(admin);

		// 钱包操作//1审核,2锁定,3取消锁定,4取消提现
		String tips = "";
		switch (type) {
		case 1:
			//校验审核码，审核码正确才能进行人民币审核操作
			String confirmCode = request.getParameter("confirmcode");
			String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
			if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				LOGGER.info(CLASS_NAME + " capitalInAudit,审核码错误");
				return modelAndView;
			}
			
			capitalOperation
					.setFstatus(CapitalOperationOutStatus.OperationSuccess);

			walletInfo.setFlastUpdateTime(Utils.getTimestamp());
			walletInfo.setFfrozen(walletInfo.getFfrozen() - totalAmt);
			modelAndView.addObject("callbackType","closeCurrent");
			tips = "审核";
			break;
		case 2:
			capitalOperation
					.setFstatus(CapitalOperationOutStatus.OperationLock);
			tips = "锁定";
			break;
		case 3:
			capitalOperation
					.setFstatus(CapitalOperationOutStatus.WaitForOperation);
			tips = "取消锁定";
			break;
		case 4:
			capitalOperation.setFstatus(CapitalOperationOutStatus.SystemCancel);
			walletInfo.setFlastUpdateTime(Utils.getTimestamp());
			walletInfo.setFfrozen(walletInfo.getFfrozen() - totalAmt);
			walletInfo.setFtotal(walletInfo.getFtotal() + totalAmt);
			tips = "取消";
			break;
		}

		boolean flag = false;
		try {
			LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息开始");
			this.capitaloperationService.updateCapital(capitalOperation,
					walletInfo, false);
			LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息结束");
			flag = true;
		} catch (Exception e) {
			LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息异常exception:{}", e);
			flag = false;
		}
		if (!flag) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", tips + "失败");
			return modelAndView;
		}

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", tips + "成功");
		LOGGER.info(CLASS_NAME + " capitalOutAudit 人民币提现审核、锁定结束");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/usdtCapitalOutAuditTeshu")
	public ModelAndView usdtCapitalOutAuditTeshu(HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		LOGGER.info(CLASS_NAME + " capitalOutAudit fid:{}", fid );
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		
		Fuser fuser = this.frontUserService.findById(capitalOperation.getFuser().getFid());
		if(fuser.getFscore().getFlevel()<6 || !fuser.isFistiger()){
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "正常用户，不需要进行审核！");
			return modelAndView;
		}
		//设置财务核对的审核码
		int num = (int) (Math.random() * 100000);
		String tscode = String.format("%05d", num);
		if(capitalOperation.getFremittanceType()!=null && capitalOperation.getFremittanceType().trim().length()>0){
			tscode = capitalOperation.getFremittanceType();
		}else{
			capitalOperation.setFremittanceType(tscode);
			this.capitaloperationService.updateObj(capitalOperation);
		}
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "特殊用户审核成功,特殊码是："+tscode);
		LOGGER.info(CLASS_NAME + " capitalOutAudit 人民币提现审核、锁定结束");
		return modelAndView;
	}
	
	//人民币提现审核、锁定
		@RequestMapping("/buluo718admin/usdtCapitalOutAudit")
		public ModelAndView usdtCapitalOutAudit(HttpServletRequest request,
				@RequestParam(required = true) int type) throws Exception {
			LOGGER.info(CLASS_NAME + " capitalOutAudit 人民币提现审核、锁定开始,type:{}", type );
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			int fid = Integer.parseInt(request.getParameter("uid"));
			LOGGER.info(CLASS_NAME + " capitalOutAudit fid:{}", fid );
			Fcapitaloperation capitalOperation = this.capitaloperationService
					.findById(fid);
			int status = capitalOperation.getFstatus();
			LOGGER.info(CLASS_NAME + " capitalOutAudit 查询提现流水状态status:{}", status );
			switch (type) {
			case 1:
				if (status != CapitalOperationOutStatus.OperationLock) {
					modelAndView.setViewName("ssadmin/comm/ajaxDone");
					modelAndView.addObject("statusCode", 300);
					String status_s = CapitalOperationOutStatus
							.getEnumString(CapitalOperationOutStatus.OperationLock);
					modelAndView.addObject("message", "审核失败,只有状态为:‘" + status_s
							+ "’的提现记录才允许审核!");
					return modelAndView;
				}
				break;
			case 2:
				if (status != CapitalOperationOutStatus.WaitForOperation) {
					modelAndView.setViewName("ssadmin/comm/ajaxDone");
					modelAndView.addObject("statusCode", 300);
					String status_s = CapitalOperationOutStatus
							.getEnumString(CapitalOperationOutStatus.WaitForOperation);
					modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
							+ "’的提现记录才允许锁定!");
					return modelAndView;
				}
				break;
			case 3:
				if (status != CapitalOperationOutStatus.OperationLock) {
					modelAndView.setViewName("ssadmin/comm/ajaxDone");
					modelAndView.addObject("statusCode", 300);
					String status_s = CapitalOperationOutStatus
							.getEnumString(CapitalOperationOutStatus.OperationLock);
					modelAndView.addObject("message", "取消锁定失败,只有状态为:‘" + status_s
							+ "’的提现记录才允许取消锁定!");
					return modelAndView;
				}
				break;
			case 4:
				if (status == CapitalOperationOutStatus.Cancel || status == CapitalOperationOutStatus.OperationSuccess) {
					modelAndView.setViewName("ssadmin/comm/ajaxDone");
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "取消失败!");
					return modelAndView;
				}
				break;
			default:
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "非法提交！");
				return modelAndView;
			}

			LOGGER.info(CLASS_NAME + " capitalOutAudit，根据用户id查询用户钱包开始");
			// 根据用户查钱包最后修改时间
			Fvirtualwallet walletInfo = this.frontUserService.findUSDTWalletByUser(capitalOperation.getFuser().getFid());
			LOGGER.info(CLASS_NAME + " capitalOutAudit，根据用户id查询用户钱包结束");
			if (walletInfo == null) {
				LOGGER.info(CLASS_NAME + " capitalOutAudit，查询用户钱包信息为空，用户id:{}", capitalOperation.getFuser().getFid());
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，会员钱包信息异常!");
				return modelAndView;
			}

			double amount = capitalOperation.getFamount();
			double frees = capitalOperation.getFfees();
			double totalAmt = Utils.getDouble(amount + frees,2);
			LOGGER.info(CLASS_NAME + " capitalOutAudit 本次提现金额:{},提现手续费:{}", amount, frees);
			// 充值操作
			Fadmin admin = (Fadmin) request.getSession()
					.getAttribute("login_admin");
			capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
			capitalOperation.setfAuditee_id(admin);

			// 钱包操作//1审核,2锁定,3取消锁定,4取消提现
			String tips = "";
			switch (type) {
			case 1:
				//校验审核码，审核码正确才能进行人民币审核操作
				String confirmCode = request.getParameter("confirmcode");
				String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
				if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
					modelAndView.setViewName("ssadmin/comm/ajaxDone");
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
					LOGGER.info(CLASS_NAME + " capitalInAudit,审核码错误");
					return modelAndView;
				}
				
				Fuser fuser = this.frontUserService.findById(capitalOperation.getFuser().getFid());
				if(fuser.getFscore().getFlevel()==6 || fuser.isFistiger()){
					String teshuCode = request.getParameter("teshucode");
					if(!capitalOperation.getFremittanceType().equals(teshuCode)){
						modelAndView.setViewName("ssadmin/comm/ajaxDone");
						modelAndView.addObject("statusCode", 300);
						modelAndView.addObject("message", "审核失败，特殊验证码错误，请仔细检查!");
						return modelAndView;
					}
				}
				
				capitalOperation
						.setFstatus(CapitalOperationOutStatus.OperationSuccess);

				walletInfo.setFlastUpdateTime(Utils.getTimestamp());
				walletInfo.setFfrozen(walletInfo.getFfrozen() - totalAmt);
				tips = "审核";
				modelAndView.addObject("callbackType","closeCurrent");
				break;
			case 2:
				capitalOperation
						.setFstatus(CapitalOperationOutStatus.OperationLock);
				tips = "锁定";
				break;
			case 3:
				capitalOperation
						.setFstatus(CapitalOperationOutStatus.WaitForOperation);
				tips = "取消锁定";
				break;
			case 4:
				capitalOperation.setFstatus(CapitalOperationOutStatus.SystemCancel);
				walletInfo.setFlastUpdateTime(Utils.getTimestamp());
				walletInfo.setFfrozen(walletInfo.getFfrozen() - totalAmt);
				walletInfo.setFtotal(walletInfo.getFtotal() + totalAmt);
				tips = "取消";
				break;
			}

			boolean flag = false;
			try {
				LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息开始");
				this.capitaloperationService.updateCapital(capitalOperation,
						walletInfo, false);
				LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息结束");
				flag = true;
			} catch (Exception e) {
				LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息异常exception:{}", e);
				flag = false;
			}
			if (!flag) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", tips + "失败");
				return modelAndView;
			}

			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", tips + "成功");
			LOGGER.info(CLASS_NAME + " capitalOutAudit 人民币提现审核、锁定结束");
			return modelAndView;
		}
		
	private static enum ExportFiled {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 金额, 手续费, 备注, 创建时间, 最后修改时间, 审核人;
	}
	private static enum ExportFiledUsdt {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, USDT数量, 人民币金额, 手续费, 备注或提币码, 创建时间, 最后修改时间, 审核人;
	}
	private List<Fcapitaloperation> getCapitalOperationList(HttpServletRequest request,
			String type, String status) {
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (type != null && type.trim().length() > 0) {
			filterSQL.append("and ftype IN " + type + "\n");
		}
		if (status != null && status.trim().length() > 0) {
			filterSQL.append("AND fstatus IN " + status + "\n");
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
			}
		}

		try {
			if (request.getParameter("fstatus") != null
					&& request.getParameter("fstatus").trim().length() > 0) {
				String fstatus = new String(request.getParameter("fstatus")
						.getBytes("iso8859-1"), "utf-8");
				if (!fstatus.equals("0")) {
					if (fstatus.indexOf("充值") != -1) {
						filterSQL.append("AND ftype ="
								+ CapitalOperationTypeEnum.RMB_IN + " \n");
						filterSQL.append("AND fstatus ="
								+ fstatus.replace("充值-", "") + " \n");
					} else if (fstatus.indexOf("提现") != -1) {
						filterSQL.append("AND ftype ="
								+ CapitalOperationTypeEnum.RMB_OUT + " \n");
						filterSQL.append("AND fstatus ="
								+ fstatus.replace("提现-", "") + " \n");
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}
		List<Fcapitaloperation> list = this.capitaloperationService.list(0, 0,
				filterSQL + "", false);
		return list;
	}

	@RequestMapping("/buluo718admin/capitaloperationExport")
	public ModelAndView capitaloperationExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=capitaloperationList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}
		String type = "(" + CapitalOperationTypeEnum.RMB_IN + "," + CapitalOperationTypeEnum.RMB_OUT+ ")";
		String status = null;
		List<Fcapitaloperation> capitalOperationList = getCapitalOperationList(request, type, status);
		for (Fcapitaloperation capitalOperation : capitalOperationList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 订单ID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFid());
					break;
				case 会员UID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), capitalOperation.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), capitalOperation.getFstatus_s());
					break;
				case 金额:
					e.setCell(filed.ordinal(), capitalOperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), capitalOperation.getFfees());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getFcreateTime());
					break;
				case 最后修改时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getfLastUpdateTime());
					break;
				case 审核人:
					if (capitalOperation.getfAuditee_id() != null)
						e.setCell(filed.ordinal(), capitalOperation
								.getfAuditee_id().getFname());
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

	@RequestMapping("/buluo718admin/usdtCapitaloperationExport")
	public ModelAndView usdtCapitaloperationExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=capitaloperationList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiledUsdt filed : ExportFiledUsdt.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}
		String type = "(" + CapitalOperationTypeEnum.USDT_IN + "," + CapitalOperationTypeEnum.USDT_OUT+ ")";
		String status = null;
		List<Fcapitaloperation> capitalOperationList = getCapitalOperationList(request, type, status);
		for (Fcapitaloperation capitalOperation : capitalOperationList) {
			e.createRow(rowIndex++);
			for (ExportFiledUsdt filed : ExportFiledUsdt.values()) {
				switch (filed) {
				case 订单ID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFid());
					break;
				case 会员UID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), capitalOperation.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), capitalOperation.getFstatus_s());
					break;
				case USDT数量:
					if (capitalOperation.getFtype()==CapitalOperationTypeEnum.USDT_IN){
						e.setCell(filed.ordinal(), capitalOperation.getFamount()/6.5);
					}else if (capitalOperation.getFtype()==CapitalOperationTypeEnum.USDT_OUT){
						e.setCell(filed.ordinal(), capitalOperation.getFamount());
					}
					break;
				case 人民币金额:
					if (capitalOperation.getFtype()==CapitalOperationTypeEnum.USDT_IN){
						e.setCell(filed.ordinal(), capitalOperation.getFamount());
					}else if (capitalOperation.getFtype()==CapitalOperationTypeEnum.USDT_OUT){
						e.setCell(filed.ordinal(), capitalOperation.getFamount()*6.5);
					}
					break;
				case 手续费:
					e.setCell(filed.ordinal(), capitalOperation.getFfees());
					break;
				case 备注或提币码:
					e.setCell(filed.ordinal(), capitalOperation.getFremark());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getFcreateTime());
					break;
				case 最后修改时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getfLastUpdateTime());
					break;
				case 审核人:
					if (capitalOperation.getfAuditee_id() != null)
						e.setCell(filed.ordinal(), capitalOperation
								.getfAuditee_id().getFname());
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
	
	private static enum Export1Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 金额, 手续费, 银行, 收款帐号, 手机号码, 创建时间, 最后修改时间;
	}

	private static enum Export1FiledUsdt {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 提币数量, 手续费, 提现人民币, 银行, 收款帐号, 手机号码, 提币码, 创建时间, 最后修改时间;
	}
	
	@RequestMapping("/buluo718admin/capitalOutExport")
	public ModelAndView capitalOutExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=capitalOutList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export1Filed filed : Export1Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}
		String type = "(" + CapitalOperationTypeEnum.RMB_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.WaitForOperation + ","
				+ CapitalOperationOutStatus.OperationLock + ")";
		List<Fcapitaloperation> capitalOperationList = getCapitalOperationList(request, type, status);
		for (Fcapitaloperation capitalOperation : capitalOperationList) {
			e.createRow(rowIndex++);
			for (Export1Filed filed : Export1Filed.values()) {
				switch (filed) {
				case 订单ID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFid());
					break;
				case 会员UID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), capitalOperation.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), capitalOperation.getFstatus_s());
					break;
				case 金额:
					e.setCell(filed.ordinal(), capitalOperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), capitalOperation.getFfees());
					break;
				case 银行:
					e.setCell(filed.ordinal(), capitalOperation.getfBank());
					break;
				case 收款帐号:
					e.setCell(filed.ordinal(), capitalOperation.getFaccount_s());
					break;
				case 手机号码:
					e.setCell(filed.ordinal(), capitalOperation.getfPhone());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getFcreateTime());
					break;
				case 最后修改时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getfLastUpdateTime());
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

	@RequestMapping("/buluo718admin/usdtCapitalOutExport")
	public ModelAndView usdtCapitalOutExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=capitalOutList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export1FiledUsdt filed : Export1FiledUsdt.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}
		String type = "(" + CapitalOperationTypeEnum.USDT_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.WaitForOperation + ","
				+ CapitalOperationOutStatus.OperationLock + ")";
		List<Fcapitaloperation> capitalOperationList = getCapitalOperationList(request, type, status);
		for (Fcapitaloperation capitalOperation : capitalOperationList) {
			e.createRow(rowIndex++);
			for (Export1FiledUsdt filed : Export1FiledUsdt.values()) {
				switch (filed) {
				case 订单ID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFid());
					break;
				case 会员UID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), capitalOperation.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), capitalOperation.getFstatus_s());
					break;
				case 提币数量:
					e.setCell(filed.ordinal(), capitalOperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), capitalOperation.getFfees());
					break;
				case 提现人民币:
					e.setCell(filed.ordinal(), capitalOperation.getFamount()*6.5);
					break;
				case 银行:
					e.setCell(filed.ordinal(), capitalOperation.getfBank());
					break;
				case 收款帐号:
					e.setCell(filed.ordinal(), capitalOperation.getFaccount_s());
					break;
				case 手机号码:
					e.setCell(filed.ordinal(), capitalOperation.getfPhone());
					break;
				case 提币码:
					e.setCell(filed.ordinal(), capitalOperation.getFremark());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getFcreateTime());
					break;
				case 最后修改时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getfLastUpdateTime());
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
	
	@RequestMapping("/buluo718admin/capitalOutAuditAll")
	public ModelAndView capitalOutAuditAll(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		// 充值操作
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		for(int i=0;i<idString.length;i++){
			int id = Integer.parseInt(idString[i]);
			Fcapitaloperation capitalOperation = this.capitaloperationService.findById(id);
			int status = capitalOperation.getFstatus();
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许锁定!");
				return modelAndView;
			}
			capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
			capitalOperation.setfAuditee_id(admin);
			capitalOperation.setFstatus(CapitalOperationOutStatus.OperationLock);
			this.capitaloperationService.updateObj(capitalOperation);
		}

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "批量锁定成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtCapitalOutAuditAll")
	public ModelAndView usdtCapitalOutAuditAll(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		// 充值操作
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		for(int i=0;i<idString.length;i++){
			int id = Integer.parseInt(idString[i]);
			Fcapitaloperation capitalOperation = this.capitaloperationService.findById(id);
			int status = capitalOperation.getFstatus();
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许锁定!");
				return modelAndView;
			}
			capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
			capitalOperation.setfAuditee_id(admin);
			capitalOperation.setFstatus(CapitalOperationOutStatus.OperationLock);
			this.capitaloperationService.updateObj(capitalOperation);
		}

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "批量锁定成功");
		return modelAndView;
	}

}
