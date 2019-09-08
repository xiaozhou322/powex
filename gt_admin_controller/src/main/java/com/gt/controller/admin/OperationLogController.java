package com.gt.controller.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.OperationlogEnum;
import com.gt.Enum.RemittanceTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Foperationlog;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.OperationLogService;
import com.gt.service.admin.UserService;
import com.gt.util.Utils;

@Controller
public class OperationLogController extends BaseAdminController {
	@Autowired
	private OperationLogService operationLogService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/operationLogList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/operationLogList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String logDate = request.getParameter("logDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		
		//检索人民币的手动充值记录
		filter.append("and ftype="+ RemittanceTypeEnum.Type1 +" \n");
		if(keyWord != null && keyWord.trim().length() >0){
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fkey1 like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Foperationlog> list = this.operationLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("operationlogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Foperationlog", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/usdtOperationLogList")
	public ModelAndView usdtIndex(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/exchange/operationLogList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String logDate = request.getParameter("logDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		
		//检索USDT的手动充值记录
		filter.append("and ftype="+ RemittanceTypeEnum.Type4 +" \n");
		if(keyWord != null && keyWord.trim().length() >0){
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fkey1 like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Foperationlog> list = this.operationLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("operationlogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Foperationlog", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goOperationLogJSP")
	public ModelAndView goOperationLogJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Foperationlog operationlog = this.operationLogService.findById(fid);
			Fuser fuser = this.userService.findById(operationlog.getFuser().getFid());
			modelAndView.addObject("operationlog", operationlog);
			modelAndView.addObject("fuser", fuser);
		}
		modelAndView.setViewName(url) ;
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(RemittanceTypeEnum.Type1, RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type1));
		typeMap.put(RemittanceTypeEnum.Type2, RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type2));
		typeMap.put(RemittanceTypeEnum.Type3, RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type3));
		typeMap.put(RemittanceTypeEnum.Type4, RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type4));
		modelAndView.addObject("typeMap", typeMap);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveOperationLog")
	public ModelAndView saveOperationLog(HttpServletRequest request) throws Exception{
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
		Foperationlog operationlog = new Foperationlog();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		String dateString = sdf.format(new java.util.Date());
		Timestamp tm = Timestamp.valueOf(dateString);
		int userId = Integer.parseInt(request.getParameter("userLookup.id"));
		int ftype = Integer.parseInt(request.getParameter("ftype"));
		String famount = request.getParameter("famount");
		String fdescription = request.getParameter("fdescription");
		
		Fuser user = this.userService.findById(userId);
		operationlog.setFcreateTime(tm);
		operationlog.setFlastUpdateTime(tm);
		operationlog.setFamount(Double.valueOf(famount));
		operationlog.setFdescription(fdescription);
		operationlog.setFtype(RemittanceTypeEnum.Type1);
		operationlog.setFuser(user);
		operationlog.setFstatus(OperationlogEnum.SAVE);
		this.operationLogService.saveObj(operationlog);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtSaveOperationLog")
	public ModelAndView usdtSaveOperationLog(HttpServletRequest request) throws Exception{
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
		Foperationlog operationlog = new Foperationlog();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		String dateString = sdf.format(new java.util.Date());
		Timestamp tm = Timestamp.valueOf(dateString);
		int userId = Integer.parseInt(request.getParameter("userLookup.id"));
		int ftype = Integer.parseInt(request.getParameter("ftype"));
		String famount = request.getParameter("famount");
		String fdescription = request.getParameter("fdescription");
		
		Fuser user = this.userService.findById(userId);
		operationlog.setFcreateTime(tm);
		operationlog.setFlastUpdateTime(tm);
		operationlog.setFamount(Double.valueOf(famount));
		operationlog.setFdescription(fdescription);
		operationlog.setFtype(RemittanceTypeEnum.Type4);
		operationlog.setFuser(user);
		operationlog.setFstatus(OperationlogEnum.SAVE);
		this.operationLogService.saveObj(operationlog);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteOperationLog")
	public ModelAndView deleteOperationLog(HttpServletRequest request) throws Exception{
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
		Foperationlog operationLog = this.operationLogService.findById(fid);
		if(operationLog.getFstatus() != OperationlogEnum.SAVE){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","删除失败");
			return modelAndView;
		}
		
		this.operationLogService.deleteObj(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtDeleteOperationLog")
	public ModelAndView usdtDeleteOperationLog(HttpServletRequest request) throws Exception{
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
		Foperationlog operationLog = this.operationLogService.findById(fid);
		if(operationLog.getFstatus() != OperationlogEnum.SAVE){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","删除失败");
			return modelAndView;
		}
		
		this.operationLogService.deleteObj(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/auditOperationLog")
	public ModelAndView auditOperationLog(HttpServletRequest request) throws Exception{
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
		boolean flag = false;
		try {
			Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
			//校验审核码，审核码正确才能进行人民币审核操作
			String confirmCode = request.getParameter("confirmcode");
			String confirmCodeMD5 = Utils.MD5(confirmCode,sessionAdmin.getSalt());
			if (!confirmCodeMD5.equals(sessionAdmin.getFconfirmcode())) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				return modelAndView;
			}
			flag = this.operationLogService.updateOperationLog(fid,sessionAdmin);
		} catch (Exception e) {
			flag = false;
		}
		if(!flag){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","审核失败");
			return modelAndView;
		}
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","审核成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtAuditOperationLog")
	public ModelAndView usdtAuditOperationLog(HttpServletRequest request) throws Exception{
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
		boolean flag = false;
		try {
			Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
			
			//校验审核码，审核码正确才能进行人民币审核操作
			String confirmCode = request.getParameter("confirmcode");
			String confirmCodeMD5 = Utils.MD5(confirmCode,sessionAdmin.getSalt());
			if (!confirmCodeMD5.equals(sessionAdmin.getFconfirmcode())) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				return modelAndView;
			}
			
			flag = this.operationLogService.updateOperationLog(fid,sessionAdmin);
		} catch (Exception e) {
			flag = false;
		}
		if(!flag){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","审核失败");
			return modelAndView;
		}
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("callbackType","closeCurrent");
		modelAndView.addObject("message","审核成功");
		return modelAndView;
	}
}
