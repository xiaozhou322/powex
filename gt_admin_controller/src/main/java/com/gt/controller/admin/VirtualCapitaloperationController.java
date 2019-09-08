package com.gt.controller.admin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CapitalOperationOutStatus;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.RemittanceTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fadmin;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.WalletMessage;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;
import com.gt.util.XlsExport;
import com.gt.util.wallet.WalletUtil;

@Controller
public class VirtualCapitaloperationController extends BaseAdminController {
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private TradeMappingService tradeMappingService;
	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/buluo718admin/virtualCaptualoperationList")
	public ModelAndView Index(HttpServletRequest request) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCaptualoperationList1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
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
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
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


		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		filterSQL.append("and fuser.fid is not null \n");

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

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL.toString(), true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitaloperationList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL.toString()));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/virtualCapitalInList")
	public ModelAndView virtualCapitalInList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalInList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String regfrom = request.getParameter("regfrom");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_IN + "\n");
		
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if (regfrom != null && regfrom.trim().length() > 0) {
			try {
				int fappid = Integer.parseInt(regfrom);
				filterSQL.append("and fuser.fregfrom ='" + fappid + "' \n");
				modelAndView.addObject("regfrom", regfrom);
			} catch (Exception e) {
				modelAndView.addObject("regfrom", "");
			}
			
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


		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}

		filterSQL.append("and fuser.fid is not null \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}else{
			filterSQL.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}else{
			filterSQL.append( " desc \n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalInList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		modelAndView.addObject("totalAmount", this.adminService.getAllSum(
				"Fvirtualcaptualoperation","famount", filterSQL + ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/virtualCapitalOutList")
	public ModelAndView virtualCapitalOutList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalOutList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String regfrom = request.getParameter("regfrom");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
				+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if (regfrom != null && regfrom.trim().length() > 0) {
			try {
				int fappid = Integer.parseInt(regfrom);
				filterSQL.append("and fuser.fregfrom ='" + fappid + "' \n");
				modelAndView.addObject("regfrom", regfrom);
			} catch (Exception e) {
				modelAndView.addObject("regfrom", "");
			}
			
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

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}else {
			filterSQL.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/virtualCapitalOutSucList")
	public ModelAndView virtualCapitalOutSucList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalOutSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String regfrom = request.getParameter("regfrom");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.OperationSuccess +")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if (regfrom != null && regfrom.trim().length() > 0) {
			try {
				int fappid = Integer.parseInt(regfrom);
				filterSQL.append("and fuser.fregfrom ='" + fappid + "' \n");
				modelAndView.addObject("regfrom", regfrom);
			} catch (Exception e) {
				modelAndView.addObject("regfrom", "");
			}
			
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}else{
			filterSQL.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}else{
			filterSQL.append("desc \n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutSucList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		modelAndView.addObject("totalAmount", this.adminService.getAllSum(
				"Fvirtualcaptualoperation","famount", filterSQL + ""));
		modelAndView.addObject("totalFees", this.adminService.getAllSum(
				"Fvirtualcaptualoperation","ffees", filterSQL + ""));
		return modelAndView;
		
	}
	
	@RequestMapping("/buluo718admin/virtualCapitalTransferList")
	public ModelAndView virtualCapitalTransferList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalTransferList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_TRANSFER + "\n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
				+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
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

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/virtualCapitalTransferSucList")
	public ModelAndView virtualCapitalTransferSucList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalTransferSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_TRANSFER + "\n");
		if (!this.isSuper(request)){
			filterSQL.append(" and fuser.fid<>7215 \n");
		}
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.OperationSuccess +")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}else{
			filterSQL.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}else{
			filterSQL.append("desc \n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutSucList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		modelAndView.addObject("totalAmount", this.adminService.getAllSum(
				"Fvirtualcaptualoperation","famount", filterSQL + ""));
		modelAndView.addObject("totalFees", this.adminService.getAllSum(
				"Fvirtualcaptualoperation","ffees", filterSQL + ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/goVirtualCapitaloperationChangeStatus")
	public ModelAndView goVirtualCapitaloperationChangeStatus(HttpServletRequest request,
			@RequestParam(required = true) int type,
			@RequestParam(required = true) int uid) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		Fvirtualcaptualoperation fvirtualcaptualoperation = this.virtualCapitaloperationService
				.findById(uid);
		fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());

		int userId = fvirtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = fvirtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();
		List<Fvirtualwallet> virtualWallet = this.virtualWalletService
				.findByTwoProperty("fuser.fid", userId, "fvirtualcointype.fid",
						coinTypeId);
		if (virtualWallet == null || virtualWallet.size() == 0) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，会员虚拟币钱包信息异常!");
			return modelAndView;
		}
		Fvirtualwallet virtualWalletInfo = virtualWallet.get(0);

		int status = fvirtualcaptualoperation.getFstatus();
		String tips = "";
		switch (type) {
		case 1:
			tips = "锁定";
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
						+ "’的充值记录才允许锁定!");
				return modelAndView;
			}
			
			String confirmCode = request.getParameter("confirmcode");
			if (confirmCode!=null && !confirmCode.equals("")){
				Fadmin admin = (Fadmin) request.getSession()
						.getAttribute("login_admin");
				String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
				if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
					return modelAndView;
				}
			}else{
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码不能为空!");
				return modelAndView;
			}
			
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationLock);
			modelAndView.addObject("callbackType", "closeCurrent");
			break;
		case 2:
			tips = "取消锁定";
			if (status != CapitalOperationOutStatus.OperationLock) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.OperationLock);
				modelAndView.addObject("message", "取消锁定失败,只有状态为:‘" + status_s
						+ "’的充值记录才允许取消锁定!");
				return modelAndView;
			}
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation);
			break;
		case 3:
			tips = "取消提现";
			if (status == CapitalOperationOutStatus.Cancel) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "取消提现失败,该记录已处于取消状态!");
				return modelAndView;
			}
			double fee = fvirtualcaptualoperation.getFfees();
			double famount = fvirtualcaptualoperation.getFamount();
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.Cancel);
			virtualWalletInfo.setFfrozen(virtualWalletInfo.getFfrozen() - fee
					- famount);
			virtualWalletInfo.setFtotal(virtualWalletInfo.getFtotal() + fee
					+ famount);
			virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
			break;
		}

		boolean flag = false;
		try {
			this.virtualCapitaloperationService.updateCapital(fvirtualcaptualoperation, virtualWalletInfo);
			/*this.virtualCapitaloperationService
					.updateObj(fvirtualcaptualoperation);
			this.virtualWalletService.updateObj(virtualWalletInfo);*/
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (flag) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", tips + "成功！");
		} else {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "未知错误，请刷新列表后重试！");
		}

		return modelAndView;
	}

	@RequestMapping("/buluo718admin/goVirtualCapitaloperationJSP")
	public ModelAndView goVirtualCapitaloperationJSP(HttpServletRequest request) throws Exception {

		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		String xid = "";
		Fvirtualcaptualoperation virtualcaptualoperation =  null ;
		BTCMessage msg = new BTCMessage();
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			virtualcaptualoperation = this.virtualCapitaloperationService
					.findById(fid);
			modelAndView.addObject("virtualCapitaloperation",
					virtualcaptualoperation);
			int userId = virtualcaptualoperation.getFuser().getFid();
			int coinId = virtualcaptualoperation.getFvirtualcointype().getFid();
			Fvirtualcointype fcoin = this.virtualCoinService.findById(coinId);
			double tixianzhi = Utils.getDouble(virtualcaptualoperation.getFamount()+virtualcaptualoperation.getFfees(), 6);
			//成功买入、卖出、挂单不可用的币数
			double buycount = 0d;
			double sellcount =0d;
			double sellfrozen =0d;
			String s1 = "";
			
			//提取币种的所有交易市场
			List<Ftrademapping> tmps = this.tradeMappingService.list(0,10,"where fvirtualcointypeByFvirtualcointype2.fid="+coinId, false);
			if (tmps.size()>0){
				String trdmpIds = "";
				for(Ftrademapping tradmapping:tmps){
					if (trdmpIds.equals("")){
						trdmpIds = tradmapping.getFid().toString();
					}else{
						trdmpIds = trdmpIds +"," +tradmapping.getFid().toString();
					}
				}
				
				s1 = "SELECT sum(fCount-fleftCount) from fentrust where fEntrustType=0 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
				buycount = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT sum(fCount-fleftCount) from fentrust where fEntrustType=1 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
				sellcount = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT sum(fleftCount) from fentrust where fEntrustType=1 and fStatus in (1,2) and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
				sellfrozen = this.adminService.getSQLValue(s1);
			}
			
			double fabiusecount = 0d;
			double fabigetcount = 0d;
			double fabifrozen = 0d;
			
			//如果是法币的虚拟币
			if (fcoin.getFtype()==CoinTypeEnum.FB_COIN_VALUE || fcoin.getFtype()==CoinTypeEnum.FB_USDT_VALUE){
				
				List<Ftrademapping> fbtmps = this.tradeMappingService.list(0,10,"where fvirtualcointypeByFvirtualcointype1.fid="+coinId, false);
				if (fbtmps.size()>0){
					String trdmpIds = "";
					for(Ftrademapping tradmapping:fbtmps){
						if (trdmpIds.equals("")){
							trdmpIds = tradmapping.getFid().toString();
						}else{
							trdmpIds = trdmpIds +"," +tradmapping.getFid().toString();
						}
					}
					
					s1 = "SELECT sum(fsuccessAmount) from fentrust where fEntrustType=0 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					fabiusecount = this.adminService.getSQLValue(s1);
					
					s1 = "SELECT sum(fsuccessAmount) from fentrust where fEntrustType=1 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					fabigetcount = this.adminService.getSQLValue(s1);
					
					s1 = "SELECT sum(fAmount-fsuccessAmount) from fentrust where fEntrustType=0 and fStatus in (1,2) and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					fabifrozen = this.adminService.getSQLValue(s1);
				}
				
			}
			
			//充值、提现、提币冻结的币数
			double rechargecount =0d;
			double operationcount =0d;
			double withdrawcount =0d;
			double withdrawfrozen =0d;
			double rewardcount =0d;
			
			
			//OTC买入卖出数量
			double otcbuycount =0d;
			double otcsellcount =0d;
			
			//站内转入转出数量
			double tranincount =0d;
			double tranoutcount =0d;
			
			double wallettotal =0d;
			double walletfrozen =0d;
			
			//APP划转到交易所的USDT数量
			double drawcount =0d;
			
			s1 = "SELECT sum(fAmount) from fvirtualcaptualoperation where fType=1 and fStatus =3 and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
			rechargecount = this.adminService.getSQLValue(s1);
			
			
			s1 = "SELECT sum(fAmount) from fvirtualcaptualoperation where fType=3 and fStatus =3 and fVi_fId2="+coinId + " and withdraw_virtual_address='" +userId+"'";
			tranincount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(fAmount) from fvirtualcaptualoperation where fType=3 and fStatus =3 and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
			tranoutcount = this.adminService.getSQLValue(s1);
			
			
			//USDT的是查询foperationlog，虚拟币的查询fvirtualoperationlog
			if(fcoin.getFtype()==CoinTypeEnum.FB_USDT_VALUE) {
				s1 = "SELECT sum(famount) from foperationlog where fstatus=2 and ftype=4 and fuid=" +userId;
				operationcount =this.adminService.getSQLValue(s1);
			}else {
				s1 = "SELECT sum(FQty) from fvirtualoperationlog where FStatus=2 and FVirtualCoinTypeId="+coinId + " and FUserId=" +userId;
				operationcount =this.adminService.getSQLValue(s1);
			}
			
			s1 = "SELECT sum(fAmount+ffees) from fvirtualcaptualoperation where fType in (2,3) and fStatus =3 and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
			withdrawcount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(fAmount+ffees) from fvirtualcaptualoperation where fType in (2,3) and fStatus in (1,2) and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
			withdrawfrozen = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(fqty) from fintrolinfo where fname='"+fcoin.getfShortName() + "' and fuserid=" +userId;
			rewardcount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(amount) from f_otcorder where order_type=1 and order_status=107 and coin_type="+coinId + " and user_id=" +userId;
			otcbuycount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(amount) from f_otcorder where order_type=2 and order_status=107 and coin_type="+coinId + " and user_id=" +userId;
			otcsellcount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(fAmount) from fdrawaccounts where fCointype=4 and fType=1 and fUserTo=" +userId;
			drawcount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT fTotal from fvirtualwallet where fVi_fId="+coinId+ " and fuid=" +userId;
			wallettotal = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT fFrozen from fvirtualwallet where fVi_fId="+coinId+ " and fuid=" +userId;
			walletfrozen = this.adminService.getSQLValue(s1);
			
			
			
			
			modelAndView.addObject("buycount",buycount);
			modelAndView.addObject("sellcount",sellcount);
			modelAndView.addObject("sellfrozen",sellfrozen);
			modelAndView.addObject("rechargecount",rechargecount);
			modelAndView.addObject("operationcount",operationcount);
			modelAndView.addObject("withdrawcount",withdrawcount);
			modelAndView.addObject("withdrawfrozen",withdrawfrozen);
			modelAndView.addObject("rewardcount",rewardcount);
			modelAndView.addObject("wallettotal",wallettotal);
			modelAndView.addObject("walletfrozen",walletfrozen);
			modelAndView.addObject("fabigetcount",fabigetcount);
			modelAndView.addObject("fabiusecount",fabiusecount);
			modelAndView.addObject("fabifrozen",fabifrozen);
			
			modelAndView.addObject("otcbuycount",otcbuycount);
			modelAndView.addObject("otcsellcount",otcsellcount);
			
			modelAndView.addObject("drawcount",drawcount);
			
			modelAndView.addObject("tranincount",tranincount);
			modelAndView.addObject("tranoutcount",tranoutcount);
			
			BigDecimal a1 = new BigDecimal(Double.toString(rewardcount+buycount+rechargecount+operationcount+fabigetcount+otcbuycount+drawcount+tranincount));
			BigDecimal a2 = new BigDecimal(Double.toString(sellcount+withdrawcount+sellfrozen+withdrawfrozen+fabiusecount+fabifrozen+otcsellcount+tranoutcount));
			
			modelAndView.addObject("youxiaoshu", a1.subtract(a2).doubleValue());
			modelAndView.addObject("tixianzhi", tixianzhi);
			Fadmin admin = (Fadmin) request.getSession()
					.getAttribute("login_admin");
			boolean teshuflag = false;
			
			Fuser fuser = this.frontUserService.findById(userId);
			if (fuser.isFistiger() || fuser.getFscore().getFlevel()>=3){
				teshuflag = true;
			}
			
			if (admin.getFname().equals("admin")){
				modelAndView.addObject("needadmin",false);
			}else{
				if(teshuflag){
					modelAndView.addObject("needadmin",true);
				}else{
					modelAndView.addObject("needadmin",false);
				}
			}
			modelAndView.addObject("teshuflag",teshuflag);
			
		}
		
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/virtualCapitalOutAudit")
	public ModelAndView virtualCapitalOutAudit(HttpServletRequest request) throws Exception {
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
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
		int status = virtualcaptualoperation.getFstatus();
		if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
			modelAndView.addObject("statusCode", 300);
			String status_s = VirtualCapitalOperationOutStatusEnum
					.getEnumString(VirtualCapitalOperationOutStatusEnum.OperationLock);
			modelAndView.addObject("message", "审核失败,只有状态为:" + status_s
					+ "的提现记录才允许审核!");
			return modelAndView;
		}
		
		if (VirtualCapitalOperationTypeEnum.COIN_OUT != virtualcaptualoperation.getFtype()) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，非提币操作不允许审核!");
			return modelAndView;
		}
		
		//校验审核码，审核码正确才能进行人民币审核操作
		
		String confirmCode = request.getParameter("confirmcode");
		if (confirmCode!=null && !confirmCode.equals("")){
			Fadmin admin = (Fadmin) request.getSession()
					.getAttribute("login_admin");
			String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
			if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				return modelAndView;
			}
		}else{
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，审核码不能为空!");
			return modelAndView;
		}
		
		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();

		Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
		double amount = Utils.getDouble(virtualcaptualoperation.getFamount()+virtualcaptualoperation.getFfees(), 4);
		double frozenRmb = Utils.getDouble(virtualWalletInfo.getFfrozen(), 4);
		if (frozenRmb-amount < -0.0001) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败,冻结数量:" + frozenRmb
					+ "小于提现数量:" + amount + "，操作异常!");
			return modelAndView;
		}
		
		if(virtualcaptualoperation.getFtradeUniqueNumber() != null &&
        		virtualcaptualoperation.getFtradeUniqueNumber().trim().length() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "提现记录已经存在交易ID，非法操作！请检查钱包！");
			return modelAndView;
        }
		
		String password = request.getParameter("password");
		if(password == null || password.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "请输入钱包密码!");
			return modelAndView;
		}
		
		
		
		WalletMessage walletmsg = new WalletMessage();
		walletmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
		walletmsg.setIP(fvirtualcointype.getFip()) ;
		walletmsg.setPORT(fvirtualcointype.getFport()) ;
		walletmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
		walletmsg.setPASSWORD(password);
		walletmsg.setCONTRACT(fvirtualcointype.getFcontract());
		walletmsg.setISERC20(fvirtualcointype.isFiserc20());
		walletmsg.setDECIMALS(fvirtualcointype.getFdecimals());
		
		if(walletmsg.getACCESS_KEY()==null
				||walletmsg.getIP()==null
				||walletmsg.getPORT()==null
				||walletmsg.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		WalletUtil walletf = WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), walletmsg);
		
		
		boolean flag = false ;
		try {
			flag = walletf.unlockWallet(fvirtualcointype.getMainAddr().trim());
			walletf.lockWallet(fvirtualcointype.getMainAddr().trim()) ;
		} catch (Exception e1) {
			
		}
		if(flag == false ){
			modelAndView.addObject("message","钱包链接错误，或密码错误");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
		
		// 钱包操作
		virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfo.setFfrozen(virtualWalletInfo.getFfrozen() - amount);
		String address = virtualcaptualoperation.getWithdraw_virtual_address();
		try {
			String txid = walletf.transfer(fvirtualcointype.getMainAddr(), address, virtualcaptualoperation.getFamount(), fvirtualcointype.getFpushMinPrice(),virtualcaptualoperation.getFischarge());
			//远程提币失败则直接处理为已审核，待添加交易ID
			System.out.println("转账交易ID："+txid);
			if(txid != null && !"".equals(txid)){
				virtualcaptualoperation.setFtradeUniqueNumber(txid);
			}
			virtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
			if(fvirtualcointype.isFisautosend()){
				virtualcaptualoperation.setFisaudit(false);
			}else{
				virtualcaptualoperation.setFisaudit(true);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("发送失败");
		}
		
		try {
			/*this.virtualCapitaloperationService.updateCapital(
					virtualcaptualoperation, virtualWalletInfo, walletf,fvirtualcointype);*/
			this.virtualCapitaloperationService.updateCapital(virtualcaptualoperation, virtualWalletInfo);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/virtualCapitalOutRedoAudit")
	public ModelAndView virtualCapitalOutRedoAudit(HttpServletRequest request) throws Exception {
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
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
		int status = virtualcaptualoperation.getFstatus();
		String txid = virtualcaptualoperation.getFtradeUniqueNumber();
		if (status != VirtualCapitalOperationOutStatusEnum.OperationSuccess || (null!=txid && !txid.equals(""))) {
			modelAndView.addObject("statusCode", 300);
			String status_s = VirtualCapitalOperationOutStatusEnum
					.getEnumString(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
			modelAndView.addObject("message", "审核失败,只有状态为:" + status_s
					+ "的提现记录且没有交易ID才允许重新审核!");
			return modelAndView;
		}
		
		if (VirtualCapitalOperationTypeEnum.COIN_OUT != virtualcaptualoperation.getFtype()) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，非提币操作不允许审核!");
			return modelAndView;
		}
		
		//校验审核码，审核码正确才能进行人民币审核操作
		
		String confirmCode = request.getParameter("confirmcode");
		if (confirmCode!=null && !confirmCode.equals("")){
			Fadmin admin = (Fadmin) request.getSession()
					.getAttribute("login_admin");
			if (admin.getFrole().getFid().intValue()!=1){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，只有超级管理员才有审核权限，请仔细检查!");
				return modelAndView;
			}
			String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
			if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				return modelAndView;
			}
		}else{
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，审核码不能为空!");
			return modelAndView;
		}
		
		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();

		if(virtualcaptualoperation.getFtradeUniqueNumber() != null &&
        		virtualcaptualoperation.getFtradeUniqueNumber().trim().length() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "提现记录已经存在交易ID，非法操作！请检查钱包！");
			return modelAndView;
        }
		
		String password = request.getParameter("password");
		if(password == null || password.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "请输入钱包密码!");
			return modelAndView;
		}
		
		WalletMessage walletmsg = new WalletMessage();
		walletmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
		walletmsg.setIP(fvirtualcointype.getFip()) ;
		walletmsg.setPORT(fvirtualcointype.getFport()) ;
		walletmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
		walletmsg.setPASSWORD(password);
		walletmsg.setCONTRACT(fvirtualcointype.getFcontract());
		walletmsg.setISERC20(fvirtualcointype.isFiserc20());
		walletmsg.setDECIMALS(fvirtualcointype.getFdecimals());
		
		if(walletmsg.getACCESS_KEY()==null
				||walletmsg.getIP()==null
				||walletmsg.getPORT()==null
				||walletmsg.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		WalletUtil walletf = WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), walletmsg);
		
		
		boolean flag = false ;
		try {
			flag = walletf.unlockWallet(fvirtualcointype.getMainAddr().trim());
			walletf.lockWallet(fvirtualcointype.getMainAddr().trim()) ;
		} catch (Exception e1) {
			
		}
		if(flag == false ){
			modelAndView.addObject("message","钱包链接错误，或密码错误");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
		
		String address = virtualcaptualoperation.getWithdraw_virtual_address();
		double amount = virtualcaptualoperation.getFamount();
		
		try {
			String txid_new = walletf.transfer(fvirtualcointype.getMainAddr(), address, amount, fvirtualcointype.getFpushMinPrice(),virtualcaptualoperation.getFischarge());
			//远程提币失败则直接处理为已审核，待添加交易ID
			if(txid_new != null && !"".equals(txid_new)){
				virtualcaptualoperation.setFtradeUniqueNumber(txid_new);
			}
			virtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
			if(fvirtualcointype.isFisautosend()){
				virtualcaptualoperation.setFisaudit(false);
			}else{
				virtualcaptualoperation.setFisaudit(true);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("发送失败");
		}
		
		try {
//			this.virtualCapitaloperationService.updateCapitalRedo(virtualcaptualoperation, walletf,fvirtualcointype);
			this.virtualCapitaloperationService.updateObj(virtualcaptualoperation);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", e.getMessage());
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重新审核成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/virtualCapitalTransferAudit")
	public ModelAndView virtualCapitalTransferAudit(HttpServletRequest request) throws Exception {
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
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
		int status = virtualcaptualoperation.getFstatus();
		
		if (VirtualCapitalOperationTypeEnum.COIN_TRANSFER != virtualcaptualoperation.getFtype()) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，非转账操作不允许审核!");
			return modelAndView;
		}
		
		//校验审核码，审核码正确才能进行人民币审核操作
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		String confirmCode = request.getParameter("confirmcode");
		String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
		if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
			return modelAndView;
		}
		
		if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
			modelAndView.addObject("statusCode", 300);
			String status_s = VirtualCapitalOperationOutStatusEnum
					.getEnumString(VirtualCapitalOperationOutStatusEnum.OperationLock);
			modelAndView.addObject("message", "审核失败,只有状态为:" + status_s
					+ "的转账记录才允许审核!");
			return modelAndView;
		}
		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		int skrUid = Integer.valueOf(virtualcaptualoperation.getWithdraw_virtual_address()).intValue();
		
		Fuser fuser = this.frontUserService.findById(userId);
		Fuser fuskr = this.frontUserService.findById(skrUid);
		
		if(fuskr==null || !fuskr.getFloginName().equals(virtualcaptualoperation.getFischarge())){
			modelAndView.addObject("message", "审核失败,收款人信息有误!");
			return modelAndView;
		}
		
		
		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();

		Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
		Fvirtualwallet virtualWalletInfoskr = frontUserService.findVirtualWalletByUser(skrUid, coinTypeId);
		
		double amount = Utils.getDouble(virtualcaptualoperation.getFamount()+virtualcaptualoperation.getFfees(), 4);
		double frozenRmb = Utils.getDouble(virtualWalletInfo.getFfrozen(), 4);
		if (frozenRmb-amount < -0.0001) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败,冻结数量:" + frozenRmb
					+ "小于转账数量:" + amount + "，操作异常!");
			return modelAndView;
		}

		
        if(virtualcaptualoperation.getFtradeUniqueNumber() != null &&
        		virtualcaptualoperation.getFtradeUniqueNumber().trim().length() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "非法操作！请检查钱包！");
			return modelAndView;
        }
		
        //生成交易ID
        //订单ID_转账人ID_收款人ID_收款人salt
        String tradeid = "";
		tradeid = Utils.MD5(String.valueOf(virtualcaptualoperation.getFid()) + "_" +String.valueOf(fuser.getFid()) + "_" + String.valueOf(fuskr.getFid())+"_" + fuskr.getSalt(),fuser.getSalt())+"_"+String.valueOf(virtualcaptualoperation.getFid());
		
		virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());

		// 钱包操作
		virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfo.setFfrozen(virtualWalletInfo.getFfrozen() - amount);
		
		virtualWalletInfoskr.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfoskr.setFtotal(virtualWalletInfoskr.getFtotal()+virtualcaptualoperation.getFamount());
		try {
			this.virtualCapitaloperationService.updateCapitalTransfer(
					virtualcaptualoperation, virtualWalletInfo, virtualWalletInfoskr,tradeid);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", e.getMessage());
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/auditVCInlog")
	public ModelAndView auditVCInlog(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
		int status = virtualcaptualoperation.getFstatus();
		int type = virtualcaptualoperation.getFtype();
		if (status == VirtualCapitalOperationInStatusEnum.SUCCESS || type != VirtualCapitalOperationTypeEnum.COIN_IN) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "充值记录已自动审核或该记录非充值记录！");
			return modelAndView;
		}
		
		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();

		Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
		// 钱包操作
		virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfo.setFtotal(virtualWalletInfo.getFtotal()+virtualcaptualoperation.getFamount());
		
		
		virtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS);
		try {
			this.virtualCapitaloperationService.updateCapital(
					virtualcaptualoperation, virtualWalletInfo);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "网络异常");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}
	
	private static enum Export1Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 金额, 手续费, 提现地址, 创建时间;
	}

	@RequestMapping("/buluo718admin/vcOutExport")
	public ModelAndView vcOutExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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

		List<Fvirtualcaptualoperation> alls = getRecords(request);
		for (Fvirtualcaptualoperation v : alls) {
			e.createRow(rowIndex++);
			for (Export1Filed filed : Export1Filed.values()) {
				switch (filed) {
				case 订单ID:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFid());
					break;
				case 会员UID:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser().getFid());
					break;
				case 会员登陆名:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), v.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), v.getFstatus_s());
					break;
				case 金额:
					e.setCell(filed.ordinal(), v.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), v.getFfees());
					break;
				case 提现地址:
					e.setCell(filed.ordinal(), v.getWithdraw_virtual_address());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),
							v.getFcreateTime());
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
	
	public List<Fvirtualcaptualoperation> getRecords(HttpServletRequest request) throws Exception {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
				+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}


		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list(0, 0, filterSQL.toString(), false);
		return list;
	}
	
	private static enum Export2Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 金额, 手续费, 提现地址,交易ID, 创建时间;
	}

	@RequestMapping("/buluo718admin/vcSucOutExport")
	public ModelAndView vcSucOutExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=vcSucOutExport.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export2Filed filed : Export2Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fvirtualcaptualoperation> alls = getRecords_suc(request);
		for (Fvirtualcaptualoperation v : alls) {
			e.createRow(rowIndex++);
			for (Export2Filed filed : Export2Filed.values()) {
				switch (filed) {
				case 订单ID:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFid());
					break;
				case 会员UID:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser().getFid());
					break;
				case 会员登陆名:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), v.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), v.getFstatus_s());
					break;
				case 金额:
					e.setCell(filed.ordinal(), v.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), v.getFfees());
					break;
				case 提现地址:
					e.setCell(filed.ordinal(), v.getWithdraw_virtual_address());
					break;
				case 交易ID:
					e.setCell(filed.ordinal(), v.getFtradeUniqueNumber());
					break;	
				case 创建时间:
					e.setCell(filed.ordinal(),
							v.getFcreateTime());
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
	
	public List<Fvirtualcaptualoperation> getRecords_suc(HttpServletRequest request) throws Exception {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.OperationSuccess +")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') >= '"+logDate1+"' \n");
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}
		
		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService.list(0, 0, filterSQL.toString(), false);

		return list;
	}
	
	private static enum Export3Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 金额, 手续费, 充值地址,交易ID, 创建时间;
	}

	@RequestMapping("/buluo718admin/vcInExport")
	public ModelAndView vcInExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=vcInExport.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export3Filed filed : Export3Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fvirtualcaptualoperation> alls = getRecords_in(request);
		for (Fvirtualcaptualoperation v : alls) {
			e.createRow(rowIndex++);
			for (Export3Filed filed : Export3Filed.values()) {
				switch (filed) {
				case 订单ID:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFid());
					break;
				case 会员UID:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser().getFid());
					break;
				case 会员登陆名:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (v.getFuser() != null)
						e.setCell(filed.ordinal(), v.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), v.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), v.getFstatus_s());
					break;
				case 金额:
					e.setCell(filed.ordinal(), v.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), v.getFfees());
					break;
				case 充值地址:
					e.setCell(filed.ordinal(), v.getRecharge_virtual_address());
					break;
				case 交易ID:
					e.setCell(filed.ordinal(), v.getFtradeUniqueNumber());
					break;	
				case 创建时间:
					e.setCell(filed.ordinal(),
							v.getFcreateTime());
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
	
	public List<Fvirtualcaptualoperation> getRecords_in(HttpServletRequest request) throws Exception {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_IN + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
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


		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
		}

		filterSQL.append("and fuser.fid is not null \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}


		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list(0, 0, filterSQL.toString(), false);
		return list;
	}
	
	@RequestMapping("/buluo718admin/updateVcTradeNumber")
	public ModelAndView updateVcTradeNumber(HttpServletRequest request) throws Exception{
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
		
		try {
			int fid = Integer.parseInt(request.getParameter("fid"));
			Fvirtualcaptualoperation vc = this.virtualCapitaloperationService.findById(fid);
			if(vc == null){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","记录不存在");
				return modelAndView;
			}
			if(vc.getFtype() != 2 && vc.getFstatus() !=3){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","只有成功提成的记录才允许补交易ID");
				return modelAndView;
			}
			
			if(vc.getFtradeUniqueNumber() != null && vc.getFtradeUniqueNumber().trim().length() >0){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","记录已存在交易ID，不允许覆盖");
				return modelAndView;
			}
			vc.setFtradeUniqueNumber(request.getParameter("ftradeUniqueNumber"));
			this.virtualCapitaloperationService.updateObj(vc);
		} catch (Exception e) {
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","网络异常");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","操作成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/virtualSucOp")
	public ModelAndView virtualSucOp(HttpServletRequest request) throws Exception {
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
		int uid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcaptualoperation ca = this.virtualCapitaloperationService.findById(uid);
		if(ca.getFtradeUniqueNumber() != null && ca.getFtradeUniqueNumber().trim().length() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "请勿重复拔币");
			return modelAndView;
		}
		ca.setFisaudit(false);
		this.virtualCapitaloperationService.updateObj(ca);


		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "操作成功");
		return modelAndView;
	}
}
