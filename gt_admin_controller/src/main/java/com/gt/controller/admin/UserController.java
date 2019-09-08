package com.gt.controller.admin;

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

import com.gt.Enum.IdentityTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.Enum.PprojectTypeEnum;
import com.gt.Enum.RegTypeEnum;
import com.gt.Enum.UserStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.FuserFaceID;
import com.gt.entity.Fusersetting;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pproject;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.admin.ScoreService;
import com.gt.service.admin.SystemArgsService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.UsersettingService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPprojectService;
import com.gt.service.front.FrontUserFaceIDService;
import com.gt.service.front.FrontUserService;
import com.gt.util.HttpUtils;
import com.gt.util.Utils;
import com.gt.util.XlsExport;

@Controller
public class UserController extends BaseAdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private UsersettingService usersettingService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FrontPprojectService pprojectService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private FrontUserFaceIDService frontUserFaceIDService;
	
	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/buluo718admin/userList")
	public ModelAndView Index(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String uid = request.getParameter("uid");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(0, "全部");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fregisterTime,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fregisterTime,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		String kyc1 = "";
		if (request.getParameter("kyc1")!=null){
			kyc1=request.getParameter("kyc1");
		}
		if (kyc1.equals("True")){
			filter.append("and fhasRealValidate=1 ");
		}else if (kyc1.equals("False")){
			filter.append("and fhasRealValidate=0 ");
		}
		
		String kyc2 = "";
		if (request.getParameter("kyc2")!=null){
			kyc2=request.getParameter("kyc2");
		}
		if (kyc2.equals("True")){
			filter.append("and fhasImgValidate=1 ");
		}else if (kyc2.equals("False")){
			filter.append("and fhasImgValidate=0 ");
		}
		
		modelAndView.addObject("kyc1", kyc1);
		modelAndView.addObject("kyc2", kyc2);
		
		if (request.getParameter("hasIntro") != null){
			String hasIntro = request.getParameter("hasIntro");
			
			if (hasIntro.equals("True")){
				filter.append("and fIntroUser_id!=null ");
			}
			
			modelAndView.addObject("hasIntro", hasIntro);
		}
		
		if (request.getParameter("isactime") != null){
			String isactime = request.getParameter("isactime");
			
			if (isactime.equals("True")){
				filter.append("and  DATE_FORMAT(fregisterTime,'%H:%i:%s') >= '09:00:00' \n");
				filter.append("and  DATE_FORMAT(fregisterTime,'%H:%i:%s') <= '21:00:00' \n");
			}
			
			modelAndView.addObject("isactime", isactime);
		}
		
		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("troNo") != null
					&& request.getParameter("troNo").trim().length() > 0) {
				String troNo = request.getParameter("troNo").trim();
				filter.append("and fuserNo like '%" + troNo + "%' \n");
				modelAndView.addObject("troNo", troNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/introList")
	public ModelAndView introIndex(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/introList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String uid = request.getParameter("uid");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filter.append(" and fid<>7215 \n");
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(0, "全部");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fregisterTime,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fregisterTime,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		String kyc1 = "";
		if (request.getParameter("kyc1")!=null){
			kyc1=request.getParameter("kyc1");
		}
		if (kyc1.equals("True")){
			filter.append("and fhasRealValidate=1 ");
		}else if (kyc1.equals("False")){
			filter.append("and fhasRealValidate=0 ");
		}
		
		String kyc2 = "";
		if (request.getParameter("kyc2")!=null){
			kyc2=request.getParameter("kyc2");
		}
		if (kyc2.equals("True")){
			filter.append("and fhasImgValidate=1 ");
		}else if (kyc2.equals("False")){
			filter.append("and fhasImgValidate=0 ");
		}
		
		modelAndView.addObject("kyc1", kyc1);
		modelAndView.addObject("kyc2", kyc2);
		
		if (request.getParameter("hasIntro") != null){
			String hasIntro = request.getParameter("hasIntro");
			
			if (hasIntro.equals("True")){
				filter.append("and fIntroUser_id!=null ");
			}
			
			modelAndView.addObject("hasIntro", hasIntro);
		}
		
		if (request.getParameter("isactime") != null){
			String isactime = request.getParameter("isactime");
			
			if (isactime.equals("True")){
				filter.append("and  DATE_FORMAT(fregisterTime,'%H:%i:%s') >= '09:00:00' \n");
				filter.append("and  DATE_FORMAT(fregisterTime,'%H:%i:%s') <= '21:00:00' \n");
			}
			
			modelAndView.addObject("isactime", isactime);
		}
		
		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("troNo") != null
					&& request.getParameter("troNo").trim().length() > 0) {
				String troNo = request.getParameter("troNo").trim();
				filter.append("and fuserNo like '%" + troNo + "%' \n");
				modelAndView.addObject("troNo", troNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/viewUser1")
	public ModelAndView viewUser1(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/viewUser1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filter.append(" and fid<>7215 \n");
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			filter.append("and (floginName like '%" + keyWord + "%' or \n");
			filter.append("fnickName like '%" + keyWord + "%'  or \n");
			filter.append("frealName like '%" + keyWord + "%'  or \n");
			filter.append("ftelephone like '%" + keyWord + "%'  or \n");
			filter.append("femail like '%" + keyWord + "%'  or \n");
			filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		Map typeMap = new HashMap();
		typeMap.put(0, "全部");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(request.getParameter("cid") != null){
			int cid =Integer.parseInt(request.getParameter("cid"));
			Fcapitaloperation c = this.capitaloperationService.findById(cid);
			filter.append("and fid ="+c.getFuser().getFid()+" \n");
			modelAndView.addObject("cid",cid);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fregisterTime \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "viewUser1");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	
	@RequestMapping("/buluo718admin/userLookup")
	public ModelAndView userLookup(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userLookup");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filter.append(" and fid<>7215 \n");
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList1", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/userAuditList")
	public ModelAndView userAuditList(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userAuditList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and fpostRealValidate=1 and fhasRealValidate=0 \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		List<Fuser> list = this.userService.listUserForAudit((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/ajaxDone")
	public ModelAndView ajaxDone() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/userForbbin")
	public ModelAndView userForbbin(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		// modelAndView.setViewName("redirect:/pages/buluo718admin/comm/ajaxDone.jsp")
		// ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		int fid = Integer.parseInt(request.getParameter("uid"));
		int status = Integer.parseInt(request.getParameter("status"));
		Fuser user = this.userService.findById(fid);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		String logDesc = null;
		if (status == 1) {
			if (user.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "会员已禁用，无需做此操作");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "禁用成功");
			user.setFstatus(UserStatusEnum.FORBBIN_VALUE);
			logDesc = "会员禁用成功："+user.getFloginName();
			//远程调用
//			Map<String,String> query = new HashMap<String,String>();
//			query.put("method", "blockUser");
//			query.put("params", user.getFid()+"");
//			HttpUtils.simpleRPC(query);
			
			//需要远程调用
			//this.realTimeData.addBlackUser(user.getFid()) ;
		} else if (status == 2) {
			if (user.getFstatus() == UserStatusEnum.NORMAL_VALUE) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "会员状态为正常，无需做此操作");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "解除禁用成功");
			user.setFstatus(UserStatusEnum.NORMAL_VALUE);
			logDesc = "会员解禁成功："+user.getFloginName();
			//需要远程调用
			//远程调用
//			Map<String,String> query = new HashMap<String,String>();
//			query.put("method", "enableUser");
//			query.put("params", user.getFid()+"");
//			HttpUtils.simpleRPC(query);
			//this.realTimeData.removeBlackUser(user.getFid()) ;
		} else if (status == 3) {// 重设登陆密码
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "重设登陆密码成功，密码为:ABC123");
			user.setFloginPassword(Utils.MD5("ABC123",user.getSalt()));
			logDesc = "重设登录密码成功："+user.getFloginName();
		} else if (status == 4) {// 重设交易密码
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "会员交易密码重置成功");
			logDesc = "重设交易密码成功："+user.getFloginName();
			user.setFtradePassword(null);
		}
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,logDesc);
		this.userService.updateObj(user);
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/auditUser")
	public ModelAndView auditUser(HttpServletRequest request) throws Exception {
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
		int status = Integer.parseInt(request.getParameter("status"));
		int fid = Integer.parseInt(request.getParameter("uid"));

		Fuser user = this.userService.findById(fid);
		FuserFaceID userFaceID = frontUserFaceIDService.findByUserId(fid);
		Fscore fscore = user.getFscore();
		Fuser fintrolUser = null;
		Fintrolinfo introlInfo = null;
		Fvirtualwallet fvirtualwallet = null;
		String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
		int coinID = Integer.parseInt(auditSendCoin[0]);
		double coinQty = Double.valueOf(auditSendCoin[1]);
		if (status == 1) {
//			if(!user.getFhasRealValidate()){
//				if(!fscore.isFissend() && user.getfIntroUser_id() != null){
//					fintrolUser = this.userService.findById(user.getfIntroUser_id().getFid());
//					fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
//					fscore.setFissend(true);
//				}
//				if(coinQty >0){
//					fvirtualwallet = this.frontUserService.findVirtualWalletByUser(user.getFid(), coinID);
//					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
//					introlInfo = new Fintrolinfo();
//					introlInfo.setFcreatetime(Utils.getTimestamp());
//					introlInfo.setFiscny(false);
//					introlInfo.setFqty(coinQty);
//					introlInfo.setFuser(user);
//					introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_REG);
//					introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
//					introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
//				}
//			}
			user.setFhasRealValidateTime(Utils.getTimestamp());
			user.setFhasRealValidate(true);
			user.setFisValid(true);
			
			userFaceID.setStatusValue("OK_ADMIN");
			userFaceID.setUpdateTime(Utils.getTimestamp());
		} else {
			user.setFhasRealValidate(false);
			user.setFpostRealValidate(false);
			user.setFidentityNo(null);
			user.setFareaCode(null);
			user.setFrealName(null);
			user.setFhasImgValidateTime(null);
			user.setFhasImgValidate(false);
			user.setFpostImgValidate(false);
			user.setFpostImgValidateTime(null);
			user.setfIdentityPath(null);
			user.setfIdentityPath2(null);
			user.setfIdentityPath3(null);
			userFaceID.setStatus(103);
			userFaceID.setStatusValue("CANCEL_ADMIN");
			userFaceID.setUpdateTime(Utils.getTimestamp());
		}
		try {
			this.userService.updateObj(user,fscore,fintrolUser,fvirtualwallet,introlInfo);
			frontUserFaceIDService.updateUserFaceID(userFaceID);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "网络异常");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "审核成功");

		return modelAndView;
	}

	@RequestMapping("/buluo718admin/goUserJSP")
	public ModelAndView goUserJSP(HttpServletRequest request) throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fuser user = this.userService.findById(fid);
			modelAndView.addObject("fuser", user);
			
			List<Fusersetting> alls = this.usersettingService.list(0, 0, "where fuser.fid="+fid, false);
			Fusersetting usersetting = alls.get(0);
			modelAndView.addObject("usersetting", usersetting);

			Map<Integer,String> map = new HashMap<Integer,String>();
			map.put(IdentityTypeEnum.SHENFENZHENG, IdentityTypeEnum
					.getEnumString(IdentityTypeEnum.SHENFENZHENG));
			map.put(IdentityTypeEnum.JUNGUANGZHEN, IdentityTypeEnum
					.getEnumString(IdentityTypeEnum.JUNGUANGZHEN));
			map.put(IdentityTypeEnum.HUZHAO,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.HUZHAO));
			map.put(IdentityTypeEnum.TAIWAN,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.TAIWAN));
			map.put(IdentityTypeEnum.GANGAO,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.GANGAO));
			modelAndView.addObject("identityTypeMap", map);
		}
		
		Map<Integer,String> map = new HashMap<Integer,String>();
		for(int i=1;i<=6;i++){
			map.put(i, "VIP"+i);
		}
		modelAndView.addObject("typeMap", map);
		
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/updateUserLevel")
	public ModelAndView updateUserLevel(HttpServletRequest request) throws Exception {
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
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fuser user = this.userService.findById(fid);
		Fscore score = user.getFscore();
		int newLevel = Integer.parseInt(request.getParameter("newLevel"));
		score.setFlevel(newLevel);
		this.scoreService.updateObj(score);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"会员等级成功调整为"+newLevel);
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/updateIntroCount")
	public ModelAndView updateIntroCount(HttpServletRequest request,
			@RequestParam(required = true) int fInvalidateIntroCount)
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
		Fuser user = this.userService.findById(fid);
		user.setfInvalidateIntroCount(fInvalidateIntroCount);
		this.userService.updateObj(user);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"会员的推荐人数设置为："+fInvalidateIntroCount);
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateUserGrade")
	public ModelAndView updateUserGrade(HttpServletRequest request)
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
		Fuser user = this.userService.findById(fid);
		Fscore fscore = user.getFscore();
		fscore.setFlevel(Integer.parseInt(request.getParameter("fuserGrade")));
		this.scoreService.updateObj(fscore);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"会员等级成功调整为"+request.getParameter("fuserGrade"));
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateIntroPerson")
	public ModelAndView updateIntroPerson(HttpServletRequest request)
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
		Fuser user = this.userService.findById(fid);
		String id = request.getParameter("fintrolId");
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		String logDesc = null;
		if(id != null && id.trim().length() >0){
			int fintrolId = Integer.parseInt(id.trim());
			Fuser fintrolUser = this.userService.findById(fintrolId);
			if(fintrolUser == null){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "用户不存在");
				return modelAndView;
			}
			user.setfIntroUser_id(fintrolUser);
			logDesc="会员推荐人设置为："+fintrolUser.getFid();
			
		}else{
			user.setfIntroUser_id(null);
			logDesc="会员推荐人取消";
		}

		
		this.userService.updateObj(user);
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,logDesc);
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateUserScore")
	public ModelAndView updateUserScore(HttpServletRequest request)
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
		Fusersetting usersetting = this.usersettingService.findById(fid);
		usersetting.setFscore(Double.valueOf(request.getParameter("fscore")));
		this.usersettingService.updateObj(usersetting);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/cancelGoogleCode")
	public ModelAndView cancelGoogleCode(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		user.setFgoogleAuthenticator(null);
		user.setFgoogleBind(false);
		user.setFgoogleurl(null);
		user.setFgoogleValidate(false);
		user.setFopenSecondValidate(false);
		this.userService.updateObj(user);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"重置会员谷歌认证成功"+user.getFloginName());
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重置谷歌认证成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/cancelTel")
	public ModelAndView cancelTel(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		if(user.getFregType()==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "重置手机号码失败，手机注册用户不允许后台重置手机号码");
			return modelAndView;
		}
		user.setFtelephone(null);
		user.setFisTelephoneBind(false);
		user.setFareaCode(null);
		this.userService.updateObj(user);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"重置会员手机成功"+user.getFloginName());
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重置手机号码成功");
		return modelAndView;
	}


	 
	private static enum ExportFiled {
		会员UID,推荐人UID,会员登陆名,会员状态,昵称,真实姓名,会员等级,累计推荐注册数,电话号码,
		邮箱地址,证件类型,证件号码,注册时间,上次登陆时间;
	}

	private List<Fuser> getUserList(HttpServletRequest request) {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String uid = request.getParameter("uid");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
		}
		if (uid != null && uid.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(uid);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {}
		}

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		
		List<Fuser> list = this.userService.list(0, 0, filter + "", false);
		return list;
	}
	
	private List<Fuser> getThirdUserList(HttpServletRequest request) {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String uid = request.getParameter("uid");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append("and fregfrom != null \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
		}
		if (uid != null && uid.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(uid);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {}
		}

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(0, 0, filter + "", false);
		return list;
	}

	@RequestMapping("/buluo718admin/userExport")
	public ModelAndView userExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=userList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fuser> userList = getUserList(request);
		for (Fuser user : userList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 会员UID:
					e.setCell(filed.ordinal(), user.getFid());
					break;
				case 推荐人UID:
					if(user.getfIntroUser_id() != null)
					e.setCell(filed.ordinal(), user.getfIntroUser_id().getFid());
					break;
				case 会员登陆名:
					e.setCell(filed.ordinal(), user.getFloginName());
					break;
				case 会员状态:
					e.setCell(filed.ordinal(), user.getFstatus_s());
					break;
				case 昵称:
					e.setCell(filed.ordinal(), user.getFnickName());
					break;
				case 真实姓名:
					e.setCell(filed.ordinal(), user.getFrealName());
					break;
				case 会员等级:
					if(user.getFscore() != null)
					e.setCell(filed.ordinal(), "VIP"
							+ user.getFscore().getFlevel());
					break;
				case 累计推荐注册数:
					e.setCell(filed.ordinal(), user.getfInvalidateIntroCount());
					break;
				case 电话号码:
					e.setCell(filed.ordinal(), user.getFtelephone());
					break;
				case 邮箱地址:
					e.setCell(filed.ordinal(), user.getFemail());
					break;
				case 证件类型:
					e.setCell(filed.ordinal(), user.getFidentityType_s());
					break;
				case 证件号码:
					e.setCell(filed.ordinal(), user.getFidentityNo());
					break;
				case 注册时间:
					e.setCell(filed.ordinal(), user.getFregisterTime());
					break;
				case 上次登陆时间:
					e.setCell(filed.ordinal(), user.getFlastLoginTime());
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
	
	@RequestMapping("/buluo718admin/thirdUserExport")
	public ModelAndView thirdUserExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=userList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fuser> userList = getThirdUserList(request);
		for (Fuser user : userList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 会员UID:
					e.setCell(filed.ordinal(), user.getFid());
					break;
				case 推荐人UID:
					if(user.getfIntroUser_id() != null)
					e.setCell(filed.ordinal(), user.getfIntroUser_id().getFid());
					break;
				case 会员登陆名:
					e.setCell(filed.ordinal(), user.getFloginName());
					break;
				case 会员状态:
					e.setCell(filed.ordinal(), user.getFstatus_s());
					break;
				case 昵称:
					e.setCell(filed.ordinal(), user.getFnickName());
					break;
				case 真实姓名:
					e.setCell(filed.ordinal(), user.getFrealName());
					break;
				case 会员等级:
					if(user.getFscore() != null)
					e.setCell(filed.ordinal(), "VIP"
							+ user.getFscore().getFlevel());
					break;
				case 累计推荐注册数:
					e.setCell(filed.ordinal(), user.getfInvalidateIntroCount());
					break;
				case 电话号码:
					e.setCell(filed.ordinal(), user.getFtelephone());
					break;
				case 邮箱地址:
					e.setCell(filed.ordinal(), user.getFemail());
					break;
				case 证件类型:
					e.setCell(filed.ordinal(), user.getFidentityType_s());
					break;
				case 证件号码:
					e.setCell(filed.ordinal(), user.getFidentityNo());
					break;
				case 注册时间:
					e.setCell(filed.ordinal(), user.getFregisterTime());
					break;
				case 上次登陆时间:
					e.setCell(filed.ordinal(), user.getFlastLoginTime());
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
	
	@RequestMapping("/buluo718admin/setUserNo")
	public ModelAndView setUserNo(HttpServletRequest request) throws Exception {
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
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fuser user = this.userService.findById(fid);
		String userNo = request.getParameter("fuserNo");
		if(userNo == null || userNo.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "服务中心号不能为空");
			return modelAndView;
		}else if(userNo.trim().length() >100){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "服务中心号长度不能大于100个字符");
			return modelAndView;
		}
		
		if(user.getFuserNo() != null && user.getFuserNo().trim().length() > 0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该用户已存在服务中心号，不允许修改！");
			return modelAndView;
		}
		
		String filter = "where fuserNo='"+userNo+"'";
		List<Fuser> fusers = this.userService.list(0, 0, filter, false);
		if(fusers.size() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该服务中心号已存在！");
			return modelAndView;
		}

		user.setFuserNo(userNo);
		this.userService.updateObj(user);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"服务中心号设置成功："+user.getFloginName());
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType","closeCurrent");
		modelAndView.addObject("message", "服务中心号设置成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/cancelPhone")
	public ModelAndView cancelPhone(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		user.setFtelephone(null);
		user.setFisTelephoneBind(false);
		user.setFisTelValidate(false);
		this.userService.updateObj(user);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"充值会员手机绑定成功："+user.getFloginName());
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重置手机绑定成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/addUsers")
	public ModelAndView addUsers(HttpServletRequest request) throws Exception {
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
		
		for (int i=0;i<10;i++) {
			Fuser fuser = new Fuser() ;
			
			String regName = Utils.getRandomString(10)+"@163.com";
			fuser.setSalt(Utils.getUUID());
			fuser.setFrealName("系统生成");
			fuser.setfIntroUser_id(null) ;
			fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
			fuser.setFemail(regName) ;
			fuser.setFisMailValidate(true) ;
			fuser.setFnickName(regName.split("@")[0]) ;
			fuser.setFloginName(regName) ;
			
			
			fuser.setFregisterTime(Utils.getTimestamp()) ;
			fuser.setFloginPassword(Utils.MD5("123456abc",fuser.getSalt())) ;
			fuser.setFtradePassword(null) ;
			String ip = getIpAddr(request) ;
			fuser.setFregIp(ip);
			fuser.setFlastLoginIp(ip);
			fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
			fuser.setFlastLoginTime(Utils.getTimestamp()) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			boolean saveFlag = false ;
			
			try {
				fuser = this.frontUserService.saveRegister(fuser) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "操作成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/setTiger")
	public ModelAndView setTiger(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		String logDesc = null;
		if(user.isFistiger()){
			user.setFistiger(false);
			logDesc = "操盘手设置成功："+user.getFloginName();
		}else{
			user.setFistiger(true);
			logDesc = "操盘手设置成功："+user.getFloginName();
		}
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,"服务中心号设置成功："+logDesc);
		
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "设置成功");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/auditUserALL")
	public ModelAndView auditUserALL(HttpServletRequest request) throws Exception{
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
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		int type = Integer.parseInt(request.getParameter("type"));
		for(int i=0;i<idString.length;i++){
			int id = Integer.parseInt(idString[i]);
			Fuser user = this.userService.findById(id);
			Fscore fscore = user.getFscore();
			Fuser fintrolUser = null;
			Fvirtualwallet fvirtualwallet = null;
			String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
			int coinID = Integer.parseInt(auditSendCoin[0]);
			double coinQty = Double.valueOf(auditSendCoin[1]);
			Fintrolinfo introlInfo = null;
			if (type == 1) {
				if(!user.getFhasRealValidate()){
					if(!fscore.isFissend() && user.getfIntroUser_id() != null){
						fintrolUser = this.userService.findById(user.getfIntroUser_id().getFid());
						fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
						fscore.setFissend(true);
					}
					if(coinQty >0){
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(user.getFid(), coinID);
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(coinQty);
						introlInfo.setFuser(user);
						introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_REG);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
					}
				}
				user.setFhasRealValidateTime(Utils.getTimestamp());
				user.setFhasRealValidate(true);
				user.setFisValid(true);
			} else {
				user.setFhasRealValidate(false);
				user.setFpostRealValidate(false);
				user.setFidentityNo(null);
				user.setFrealName(null);
			}
			try {
				this.userService.updateObj(user,fscore,fintrolUser,fvirtualwallet,introlInfo);
			} catch (Exception e) {
				continue;
			}
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/userAuditList1")
	public ModelAndView userAuditList1(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userAuditList1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and fpostImgValidate=1 and fhasImgValidate=0 \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		List<Fuser> list = this.userService.listUserForAudit((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/auditUser1")
	public ModelAndView auditUser1(HttpServletRequest request) throws Exception {
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
		int status = Integer.parseInt(request.getParameter("status"));
		int fid = Integer.parseInt(request.getParameter("uid"));

		Fuser user = this.userService.findById(fid);
		FuserFaceID userFaceID = frontUserFaceIDService.findByUserId(fid);
		if (status == 1) {
			user.setFhasImgValidateTime(Utils.getTimestamp());
			user.setFhasImgValidate(true);
			userFaceID.setStatus(102);
			userFaceID.setStatusValue("OK_ADMIN");
			userFaceID.setUpdateTime(Utils.getTimestamp());
		} else {
			user.setFhasImgValidateTime(null);
			user.setFhasImgValidate(false);
			user.setFpostImgValidate(false);
			user.setFpostImgValidateTime(null);
			user.setfIdentityPath(null);
			user.setfIdentityPath2(null);
			user.setfIdentityPath3(null);
			userFaceID.setStatus(103);
			userFaceID.setStatusValue("CANCEL_ADMIN");
			userFaceID.setUpdateTime(Utils.getTimestamp());
		}
		try {
			this.userService.updateObj(user);
			frontUserFaceIDService.updateUserFaceID(userFaceID);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "网络异常");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "审核成功");

		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/setProject")
	public ModelAndView setProject(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		String logDesc = null;
		if(user.isFisprojecter()){
			user.setFisprojecter(false);
			logDesc = "项目方取消成功："+user.getFloginName();
		}else{
			user.setFisprojecter(true);
			logDesc = "项目方设置成功："+user.getFloginName();
			
			//保存项目方信息到项目方表中，如果项目方不存在则新建一笔，如果项目方存在则更新时间
			Pproject project =null;
			List<Pproject> projects = this.pprojectService.list(0, -1, "where projectId.fid="+user.getFid(), false);
			if(null!=projects && projects.size()>0) {
				project=projects.get(0);
				project.setProjectId(user);
				project.setType(PprojectTypeEnum.PROJECT);
				project.setUpdateTime(Utils.getTimestamp());
				pprojectService.update(project);
			}else {
				project = new Pproject();
				project.setProjectId(user);
				project.setType(PprojectTypeEnum.PROJECT);
				project.setName(user.getFrealName());
				project.setCreateTime(Utils.getTimestamp());
				project.setUpdateTime(Utils.getTimestamp());
				pprojectService.save(project);
			}
			
		}
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,logDesc);
		
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "操作成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/setCommunity")
	public ModelAndView setCommunity(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		String logDesc = null;
		if(user.isFisprojecter()){
			user.setFisprojecter(false);
			logDesc = "社群账户取消成功："+user.getFloginName();
		}else{
			user.setFisprojecter(true);
			logDesc = "社群账户设置成功："+user.getFloginName();
			
			//保存项目方信息到项目方表中，如果项目方不存在则新建一笔，如果项目方存在则更新时间
			Pproject project =null;
			List<Pproject> projects = this.pprojectService.list(0, -1, "where projectId.fid="+user.getFid(), false);
			if(null!=projects && projects.size()>0) {
				project=projects.get(0);
				project.setProjectId(user);
				project.setType(PprojectTypeEnum.COMMUNITY);
				project.setUpdateTime(Utils.getTimestamp());
				pprojectService.update(project);
			}else {
				project = new Pproject();
				project.setProjectId(user);
				project.setType(PprojectTypeEnum.COMMUNITY);
				project.setCreateTime(Utils.getTimestamp());
				project.setUpdateTime(Utils.getTimestamp());
				pprojectService.save(project);
			}
			
		}
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_USER,logDesc);
		
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "操作成功");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/setMerchant")
	public ModelAndView setMerchant(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		if(user.isFisorganization()){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "机构商不能设置为承兑商");
			return modelAndView;
		}
		
		if(user.isFismerchant()){
			user.setFismerchant(false);
		}else{
			user.setFismerchant(true);
			
		}
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "设置成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/setOpenSecondVal")
	public ModelAndView setOpenSecondVal(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		
		if(user.getFopenSecondValidate()){
			user.setFopenSecondValidate(false);
		}else{
			user.setFopenSecondValidate(true);
			
		}
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "设置成功");
		return modelAndView;
	}
	
	
	/**
	 * 设置APP权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/setAppAuth")
	public ModelAndView setAppAuth(HttpServletRequest request) throws Exception {
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
		Fuser user = this.userService.findById(fid);
		
		if(null == user.getFgrade() || 0 == user.getFgrade()){
			user.setFgrade(1);
		}else{
			user.setFgrade(0);
			
		}
		this.userService.updateObj(user);
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "设置成功");
		return modelAndView;
	}
	
	
	/**
	 * 第三方注册用户列表（APP来源）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/thirdUserList")
	public ModelAndView thirdUserList(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/thirdUserList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String uid = request.getParameter("uid");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append("and fregfrom != null \n");
		
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append(" and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append(" and (floginName like '%" + keyWord + "%' or \n");
				filter.append(" fnickName like '%" + keyWord + "%'  or \n");
				filter.append(" frealName like '%" + keyWord + "%'  or \n");
				filter.append(" ftelephone like '%" + keyWord + "%'  or \n");
				filter.append(" femail like '%" + keyWord + "%'  or \n");
				filter.append(" fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(0, "全部");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append(" and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append(" and  DATE_FORMAT(fregisterTime,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		if(endDate != null && endDate.trim().length() >0){
			filter.append(" and  DATE_FORMAT(fregisterTime,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		String kyc1 = "";
		if (request.getParameter("kyc1")!=null){
			kyc1=request.getParameter("kyc1");
		}
		if (kyc1.equals("True")){
			filter.append(" and fhasRealValidate=1 ");
		}else if (kyc1.equals("False")){
			filter.append(" and fhasRealValidate=0 ");
		}
		
		String kyc2 = "";
		if (request.getParameter("kyc2")!=null){
			kyc2=request.getParameter("kyc2");
		}
		if (kyc2.equals("True")){
			filter.append(" and fhasImgValidate=1 ");
		}else if (kyc2.equals("False")){
			filter.append(" and fhasImgValidate=0 ");
		}
		
		modelAndView.addObject("kyc1", kyc1);
		modelAndView.addObject("kyc2", kyc2);
		
		if (request.getParameter("hasIntro") != null){
			String hasIntro = request.getParameter("hasIntro");
			
			if (hasIntro.equals("True")){
				filter.append(" and fIntroUser_id!=null ");
			}
			
			modelAndView.addObject("hasIntro", hasIntro);
		}
		
		if (request.getParameter("isactime") != null){
			String isactime = request.getParameter("isactime");
			
			if (isactime.equals("True")){
				filter.append(" and  DATE_FORMAT(fregisterTime,'%H:%i:%s') >= '09:00:00' \n");
				filter.append(" and  DATE_FORMAT(fregisterTime,'%H:%i:%s') <= '21:00:00' \n");
			}
			
			modelAndView.addObject("isactime", isactime);
		}
		
		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append(" and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("troNo") != null
					&& request.getParameter("troNo").trim().length() > 0) {
				String troNo = request.getParameter("troNo").trim();
				filter.append(" and fuserNo like '%" + troNo + "%' \n");
				modelAndView.addObject("troNo", troNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append(" order by " + orderField + "\n");
		} else {
			filter.append(" order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "thirdUserList");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
}
