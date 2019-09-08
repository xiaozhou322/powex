package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.FactiveCoinLogs;
import com.gt.entity.FapproCoinLogs;
import com.gt.entity.FconvertCoinLogs;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;

/**
 * 虚拟币兑换.激活记录  admin--controller
 * @author zhouyong
 *
 */
@Controller
public class ConvertActiveLogsController extends BaseAdminController {
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FvirtualWalletService virtualWalletService;	
	
	private int numPerPage = Utils.getNumPerPage();
	
	
	/**
	 * 兑换记录列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/convertCoinLogsList")
	public ModelAndView drawAccountsList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/convertCoinLogsList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String fuser = request.getParameter("fuser");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1  \n");
		
		if (StringUtils.isNotBlank(fuser)) {
			try {
				int fid = Integer.parseInt(fuser);
				filter.append(" and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and fuser.frealName like '%" + fuser + "%' \n");
			}
			modelAndView.addObject("fuser", fuser);
		}
		
		String startDate = request.getParameter("startDate");
		if(StringUtils.isNotBlank(startDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(endDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append(" order by " + orderField + "\n");
		}else{
			filter.append(" order by id \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}else{
			filter.append( " desc \n");
		}


		List<FconvertCoinLogs> convertCoinLogsList = this.virtualWalletService.convertCoinLogsList((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("convertCoinLogsList", convertCoinLogsList);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "convertCoinLogsList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FconvertCoinLogs", filter+""));
		return modelAndView;
	}
	
	
	/**
	 * 激活记录列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/activeCoinLogsList")
	public ModelAndView activeCoinLogsList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/activeCoinLogsList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String fuser = request.getParameter("fuser");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1  \n");
		
		if (StringUtils.isNotBlank(fuser)) {
			try {
				int fid = Integer.parseInt(fuser);
				filter.append(" and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and fuser.frealName like '%" + fuser + "%' \n");
			}
			modelAndView.addObject("fuser", fuser);
		}
		
		String startDate = request.getParameter("startDate");
		if(StringUtils.isNotBlank(startDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(endDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append(" order by " + orderField + "\n");
		}else{
			filter.append(" order by id \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}else{
			filter.append( " desc \n");
		}


		List<FactiveCoinLogs> activeCoinLogsList = this.virtualWalletService.queryActiveCoinLogsList((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("activeCoinLogsList", activeCoinLogsList);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "activeCoinLogsList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FactiveCoinLogs", filter+""));
		return modelAndView;
	}
	
	
	/**
	 * 币种拨付记录列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/approCoinLogsList")
	public ModelAndView approCoinLogsList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/approCoinLogsList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String fuser = request.getParameter("fuser");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1  \n");
		
		if (StringUtils.isNotBlank(fuser)) {
			try {
				int fid = Integer.parseInt(fuser);
				filter.append(" and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and fuser.frealName like '%" + fuser + "%' \n");
			}
			modelAndView.addObject("fuser", fuser);
		}
		
		String startDate = request.getParameter("startDate");
		if(StringUtils.isNotBlank(startDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(endDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append(" order by " + orderField + "\n");
		}else{
			filter.append(" order by id \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}else{
			filter.append( " desc \n");
		}


		List<FapproCoinLogs> approCoinLogsList = this.virtualWalletService.queryApproCoinLogsList((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("approCoinLogsList", approCoinLogsList);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "approCoinLogsList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FapproCoinLogs", filter+""));
		return modelAndView;
	}
	
}
