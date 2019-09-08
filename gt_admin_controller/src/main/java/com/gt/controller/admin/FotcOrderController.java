package com.gt.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.OtcAppealReasonEnum;
import com.gt.Enum.OtcOrderLogsTypeEnum;
import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.Enum.OtcOrderTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.FotcBlacklist;
import com.gt.entity.FotcOrder;
import com.gt.entity.FotcOrderLogs;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FotcBlacklistService;
import com.gt.service.front.FotcOrderLogsService;
import com.gt.service.front.FotcOrderService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;


/**
 * 
 * @author jqy
 *
 */
@Controller
public class FotcOrderController extends BaseAdminController {
	
	private int numPerPage = Utils.getNumPerPage();
	@Autowired
	private FotcOrderService fotcOrderService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FotcOrderLogsService fotcOrderLogsService ;
	@Autowired
	private FotcBlacklistService fotcBlacklistService ;

    /**
     * 
     * 查询已经申诉的异常订单列表
     * @param response
     * @param re
     * @return
     * @throws Exception
     */
	@RequestMapping("/buluo718admin/appealOrderList")
	public ModelAndView appealOrderList(HttpServletResponse response,HttpServletRequest re) throws Exception{
		ModelAndView modelAndView = new ModelAndView("ssadmin/order/appealOrderList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String id = re.getParameter("id");
		String startDate = re.getParameter("startDate");
		String endDate = re.getParameter("endDate");
		String orderUserId = re.getParameter("orderUserId");
		String adUserId = re.getParameter("adUserId");
		String ad_type = re.getParameter("ad_type");
		String orderField = re.getParameter("orderField");
		String orderDirection = re.getParameter("orderDirection");
		
		if(re.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(re.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n  and orderStatus="+OtcOrderStatusEnum.ExceptionOrder);
		if(id != null && id.trim().length() >0){
			filter.append("and  id = "+id+"  \n");
 			modelAndView.addObject("id", id);
		}
		
		if(adUserId != null && adUserId.trim().length() >0){
			filter.append("and  fotcAdvertisement.user.fid = "+adUserId+"  \n");
 			modelAndView.addObject("adUserId", adUserId);
		}
		if(orderUserId != null && orderUserId.trim().length() >0){
			filter.append("and  fuser.fid = "+orderUserId+"  \n");
 			modelAndView.addObject("orderUserId", orderUserId);
		}
		if(ad_type != null && ad_type.trim().length() >0&&!"-10".equals(ad_type)){
			filter.append("and  orderType = "+Integer.parseInt(ad_type)+"  \n");
 			modelAndView.addObject("ad_type", ad_type);
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by id \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		
		List<FotcOrder> list = this.fotcOrderService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		Map<Integer,String> orderStatus=OtcOrderStatusEnum.getAll();	
		Map<Integer,String> orderType=OtcOrderTypeEnum.getAll();
		modelAndView.addObject("orderList", list);
		modelAndView.addObject("orderStatus", orderStatus);
		modelAndView.addObject("orderType", orderType);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcOrder", filter+""));
		return modelAndView;
	}
	
	
	 //申诉处理
	@RequestMapping(value = "/buluo718admin/appealProcess")
	public ModelAndView appealProcess(HttpServletRequest request) throws Exception {
		int orderId = Integer.valueOf(request.getParameter("orderId"));
		int type=Integer.valueOf(request.getParameter("procss"));//0：订单成功，1：订单失败
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
		
		 //查询订单
		JSONObject jsonObject = new JSONObject();
		try {
			FotcOrder order = fotcOrderService.queryById(orderId);
			
			if(order.getOrderStatus()!=OtcOrderStatusEnum.ExceptionOrder){
			   modelAndView.addObject("statusCode", 300);
			   modelAndView.addObject("message", "不是异常订单不能处理申诉");
			   modelAndView.addObject("callbackType","closeCurrent");
			   return modelAndView;
			}
			if(type==0){
				jsonObject=fotcOrderService.updateSucess(orderId, OtcOrderLogsTypeEnum.appeal_success);
			}else if(type==1){
				jsonObject=fotcOrderService.updateFailedOrder(orderId, 
						OtcOrderLogsTypeEnum.appeal_failed);
			}
			if(Integer.valueOf(jsonObject.get("code").toString()) == 1) {
				modelAndView.addObject("statusCode", 200);
				modelAndView.addObject("message", "操作成功");
			} else {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", jsonObject.get("msg"));
			}
			modelAndView.addObject("callbackType","closeCurrent");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "操作失败");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
		
 		return modelAndView;
	}
		
		
		
		
	@RequestMapping("/buluo718admin/orderNewList")
	public ModelAndView orderNewList(HttpServletResponse response,HttpServletRequest re) throws Exception{
		ModelAndView modelAndView = new ModelAndView("ssadmin/order/orderNewList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String id = re.getParameter("id");
		String startDate = re.getParameter("startDate");
		String endDate = re.getParameter("endDate");
		String orderUserId = re.getParameter("orderUserId");
		String adUserId = re.getParameter("adUserId");
		String status = re.getParameter("status");
		String ad_type = re.getParameter("ad_type");
		String orderField = re.getParameter("orderField");
		String orderDirection = re.getParameter("orderDirection");
		String regfrom = re.getParameter("regfrom");
		
		if(re.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(re.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(id != null && id.trim().length() >0){
			filter.append("and  id = "+id+"  \n");
 			modelAndView.addObject("id", id);
		}
		
		if(adUserId != null && adUserId.trim().length() >0){
			filter.append("and  fotcAdvertisement.user.fid = "+adUserId+"  \n");
 			modelAndView.addObject("adUserId", adUserId);
		}
		if(orderUserId != null && orderUserId.trim().length() >0){
			filter.append("and  fuser.fid = "+orderUserId+"  \n");
 			modelAndView.addObject("orderUserId", orderUserId);
		}
		if (regfrom != null && regfrom.trim().length() > 0) {
			try {
				int fappid = Integer.parseInt(regfrom);
				filter.append("and fuser.fregfrom ='" + fappid + "' \n");
				modelAndView.addObject("regfrom", regfrom);
			} catch (Exception e) {
				modelAndView.addObject("regfrom", "");
			}
			
		}
		if(status != null && status.trim().length() >0&&!"-10".equals(status)){
			filter.append("and  orderStatus = "+Integer.parseInt(status)+"  \n");
 			modelAndView.addObject("status", status);
		}
		if(ad_type != null && ad_type.trim().length() >0&&!"-10".equals(ad_type)){
			filter.append("and  orderType = "+Integer.parseInt(ad_type)+"  \n");
 			modelAndView.addObject("ad_type", ad_type);
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by id \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		
		List<FotcOrder> list = this.fotcOrderService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		Map<Integer,String> orderStatus=OtcOrderStatusEnum.getAll();	
		Map<Integer,String> orderType=OtcOrderTypeEnum.getAll();
		modelAndView.addObject("orderList", list);
		modelAndView.addObject("orderStatus", orderStatus);
		modelAndView.addObject("orderType", orderType);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcOrder", filter+""));
		modelAndView.addObject("totalAmount",this.adminService.getAllSum("FotcOrder", "amount",filter+""));
		modelAndView.addObject("totalPrice",this.adminService.getAllSum("FotcOrder", "totalPrice",filter+""));
		return modelAndView;
	}
		
		
	/**
	 * 修改订单状态
	 * @param request
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/buluo718admin/updateNewOrderStatus")
	public ModelAndView updateOrderStatus(HttpServletRequest request,
			@RequestParam(required=true)Integer orderId) throws Exception {
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
		
		//查询订单
		FotcOrder order = fotcOrderService.queryById(orderId);
		if(order.getOrderStatus()==OtcOrderStatusEnum.ExceptionOrder){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "异常订单不可手动取消");
			return modelAndView;
		}
		
		if(order.getOrderStatus()==OtcOrderStatusEnum.failOrder){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "已经交易失败的订单不可取消");
			return modelAndView;
		}
		if(order.getOrderStatus()==OtcOrderStatusEnum.success){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "已经交易成功的订单不可取消");
			return modelAndView;
		}
		try {
		    //取消订单，订单失败
			JSONObject jsonResult = fotcOrderService.updateFailedOrder(order.getId(),
					OtcOrderLogsTypeEnum.manager_cancel);
			if(Integer.valueOf(jsonResult.get("code").toString()) == 1) {
				modelAndView.addObject("statusCode", 200);
				modelAndView.addObject("message", "操作成功");
			} else {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", jsonResult.get("msg"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "操作失败");
		}
	
		return modelAndView;
	}
	
	
	/**
	 * 查看订单详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/orderNewDetail")
	public ModelAndView orderNewDetail(HttpServletRequest request) {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url);
		String param = request.getParameter("orderId");
		if (param != null) {
			int fid = Integer.parseInt(param);
			FotcOrder order = fotcOrderService.queryById(fid);
			modelAndView.addObject("order", order);
		}
		return modelAndView;
	}
	
	
	/**
	 * otc订单操作日志表
	 * @param response
	 * @param re
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/otcOrderLogsList")
	public ModelAndView otcOrderLogsList(HttpServletResponse response,HttpServletRequest re) throws Exception{
		ModelAndView modelAndView = new ModelAndView("ssadmin/order/otcOrderLogsList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String orderId = re.getParameter("orderId");
		String buyUserId = re.getParameter("buyUserId");
		String sellUserId = re.getParameter("sellUserId");
		String type = re.getParameter("type");
		String complainSucc = re.getParameter("complainSucc");
		String startDate = re.getParameter("startDate");
		String endDate = re.getParameter("endDate");
		String orderField = re.getParameter("orderField");
		String orderDirection = re.getParameter("orderDirection");
		
		if(re.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(re.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(StringUtils.isNotBlank(orderId)){
			filter.append(" and orderId.id = "+Integer.parseInt(orderId)+"  \n");
 			modelAndView.addObject("orderId", orderId);
		}
		
		if(StringUtils.isNotBlank(buyUserId)){
			filter.append(" and buyUser.fid = "+Integer.parseInt(buyUserId)+"  \n");
 			modelAndView.addObject("buyUserId", buyUserId);
		}
		if(StringUtils.isNotBlank(sellUserId)){
			filter.append(" and sellUser.fid = "+Integer.parseInt(sellUserId)+"  \n");
			modelAndView.addObject("sellUserId", sellUserId);
		}
		if(StringUtils.isNotBlank(type) && !"-10".equals(type)){
			filter.append(" and type = "+Integer.parseInt(type)+"  \n");
 			modelAndView.addObject("type", type);
		}
		if(StringUtils.isNotBlank(complainSucc) && !"-10".equals(complainSucc)){
			filter.append(" and complainSucc = "+Integer.parseInt(complainSucc)+"  \n");
			modelAndView.addObject("complainSucc", complainSucc);
		}
		
		if(StringUtils.isNotBlank(startDate)){
			filter.append(" and DATE_FORMAT(createTime,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		if(StringUtils.isNotBlank(endDate)){
			filter.append("and  DATE_FORMAT(createTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by id \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		
		List<FotcOrderLogs> list = this.fotcOrderLogsService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		Map<Integer,String> logsType=OtcOrderLogsTypeEnum.getAll();	
		Map<Integer,String> appealReasonMap = OtcAppealReasonEnum.getAll();	
		modelAndView.addObject("appealReasonMap", appealReasonMap);
		modelAndView.addObject("orderLogsList", list);
		modelAndView.addObject("logsType", logsType);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcOrderLogs", filter+""));
		return modelAndView;
	}
	
	
	
	/**
	 * otc黑名单用户列表
	 * @param response
	 * @param re
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/otcBlackList")
	public ModelAndView otcBlackList(HttpServletResponse response,HttpServletRequest re) throws Exception{
		ModelAndView modelAndView = new ModelAndView("ssadmin/order/otcBlackList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String userId = re.getParameter("userId");
		String orderField = re.getParameter("orderField");
		String orderDirection = re.getParameter("orderDirection");
		
		if(re.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(re.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		
		if(StringUtils.isNotBlank(userId)){
			filter.append(" and userId.fid = "+Integer.parseInt(userId)+"  \n");
 			modelAndView.addObject("userId", userId);
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by id \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		
		List<FotcBlacklist> list = this.fotcBlacklistService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("otcBlackList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcBlacklist", filter+""));
		return modelAndView;
	}
	
	
	/**
	 * 查看订单操作日志详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/otcOrderLogsDetail")
	public ModelAndView otcOrderLogsDetail(HttpServletRequest request) {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url);
		String param = request.getParameter("orderLogsId");
		if (param != null) {
			int fid = Integer.parseInt(param);
			FotcOrderLogs otcOrderLogs = fotcOrderLogsService.findById(fid);
			
			Map<Integer,String> logsType = OtcOrderLogsTypeEnum.getAll();	
			modelAndView.addObject("logsType", logsType);
			Map<Integer,String> appealReasonMap = OtcAppealReasonEnum.getAll();	
			modelAndView.addObject("appealReasonMap", appealReasonMap);
			modelAndView.addObject("otcOrderLogs", otcOrderLogs);
		}
		return modelAndView;
	}
	
}
