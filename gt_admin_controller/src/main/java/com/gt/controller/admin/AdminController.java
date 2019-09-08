package com.gt.controller.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.AdminStatusEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.comm.ParamArray;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Frole;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.RoleService;
import com.gt.service.admin.UserService;
import com.gt.util.Utils;

@Controller
public class AdminController extends BaseAdminController {
	@Autowired
	private AdminService adminService ;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/adminList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/adminList") ;
		int currentPage = 1;
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filter.append(" and fid<>8 \n");
		}
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and fname like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("asc \n");
		}
		List<Fadmin> list = this.adminService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("adminList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "adminList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fadmin", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goAdminJSP")
	public ModelAndView goAdminJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fadmin admin = this.adminService.findById(fid);
			modelAndView.addObject("fadmin", admin);
		}
		if(request.getSession().getAttribute("login_admin") != null){
			Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
			modelAndView.addObject("login_admin", admin);
		}
		
		List<Frole> roleList = this.roleService.findAll();
		Map map = new HashMap();
		for (Frole frole : roleList) {
			map.put(frole.getFid(),frole.getFname());
		}
		modelAndView.addObject("roleMap", map);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveAdmin")
	public ModelAndView saveAdmin(HttpServletRequest request,ParamArray param) throws Exception{
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
		Fadmin fadmin = param.getFadmin() ;
		String fname = fadmin.getFname();
		List<Fadmin> all = this.adminService.findByProperty("fname", fname);
		if(all != null && all.size() >0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","管理员登陆名已存在！");
			return modelAndView;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		String dateString = sdf.format(new java.util.Date());
		fadmin.setFcreateTime(Timestamp.valueOf(dateString));
		String passWord = fadmin.getFpassword();
		fadmin.setSalt(Utils.getUUID());
		fadmin.setFpassword(Utils.MD5(passWord,fadmin.getSalt()));
		fadmin.setFstatus(AdminStatusEnum.NORMAL_VALUE);
		fadmin.setFuserid(0);
		
		int roleId = Integer.parseInt(request.getParameter("frole.fid"));
		Frole role = this.roleService.findById(roleId);
		fadmin.setFrole(role);
		
		this.adminService.saveObj(fadmin);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_ADD,"管理员添加成功："+fadmin.getFname());
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/forbbinAdmin")
	public ModelAndView forbbinAdmin(HttpServletRequest request) throws Exception{
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
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		if(fid == sessionAdmin.getFid()){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","不允许禁用当前登陆的管理员！");
			return modelAndView;
		}
		Fadmin admin = this.adminService.findById(fid);
		int status = Integer.parseInt(request.getParameter("status"));
		if(status == 1){
			admin.setFstatus(AdminStatusEnum.FORBBIN_VALUE);
		}else if (status == 2){
			admin.setFstatus(AdminStatusEnum.NORMAL_VALUE);
		}
		this.adminService.updateObj(admin);
		
		modelAndView.addObject("statusCode",200);
		if(status == 1){
			modelAndView.addObject("message","禁用成功");
			this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE,"管理员禁用成功："+admin.getFname());
		}else if(status == 1){
			modelAndView.addObject("message","解除禁用成功");
			this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE,"管理员解禁成功："+admin.getFname());
		}
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateAdmin")
	public ModelAndView updateAdmin(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
		Fadmin fadmin = this.adminService.findById(fid);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		if(fid == sessionAdmin.getFid()){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","不允许修改当前登陆的管理员密码！");
			return modelAndView;
		}
		String passWord = request.getParameter("fadmin.fpassword");
		fadmin.setFpassword(Utils.MD5(passWord,fadmin.getSalt()));
	
		this.adminService.updateObj(fadmin);
        this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE,"密码修改成功：管理员"+fadmin.getFname());
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改密码成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateAdminPassword")
	public ModelAndView updateAdminPassword(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
		Fadmin fadmin = this.adminService.findById(fid);
		String truePassword = fadmin.getFpassword();
		String newPassWord = request.getParameter("fadmin.fpassword");
		String oldPassword = request.getParameter("oldPassword");
		String newPasswordMD5 = Utils.MD5(newPassWord,fadmin.getSalt());
		String oldPasswordMD5 = Utils.MD5(oldPassword,fadmin.getSalt());
        if(!truePassword.equals(oldPasswordMD5)){
    		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
    		modelAndView.addObject("statusCode",300);
    		modelAndView.addObject("message","原密码输入有误，请重新输入");
    		return modelAndView;
        }
        fadmin.setFpassword(newPasswordMD5);
		this.adminService.updateObj(fadmin);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE,"密码修改成功：管理员"+fadmin.getFname());
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改密码成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateAdminConfirm")
	public ModelAndView updateAdminConfirm(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
		Fadmin fadmin = this.adminService.findById(fid);
		String trueConfirmCode = fadmin.getFconfirmcode();
		String newConfirmCode = request.getParameter("fadmin.confirmcode");
		String newConfirmCodeMD5 = Utils.MD5(newConfirmCode,fadmin.getSalt());
        if(newConfirmCodeMD5.equals(trueConfirmCode)){
    		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
    		modelAndView.addObject("statusCode",300);
    		modelAndView.addObject("message","审核码与老审核码不能相同");
    		return modelAndView;
        }
        fadmin.setFconfirmcode(newConfirmCodeMD5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		String dateString = sdf.format(new java.util.Date());
		fadmin.setFconfirmcodetime(Timestamp.valueOf(dateString));
		
		this.adminService.updateObj(fadmin);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE,"审核码修改成功：管理员"+fadmin.getFname());
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","设置审核码成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/loginOut")
	public ModelAndView loginOut(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Subject admin = SecurityUtils.getSubject();
		
		//退出登录时，清除管理员在线列表
		Fadmin sessionAdmin = (Fadmin)admin.getSession().getAttribute("login_admin");
		admin.getSession().removeAttribute("permissions");
		admin.logout();
		modelAndView.setViewName("ssadmin/login");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/updateAdminRole")
	public ModelAndView updateAdminRole(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
		Fadmin fadmin = this.adminService.findById(fid);
		if(fadmin.getFname().equals("admin")){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","超级管理员角色不允许修改！");
			return modelAndView;
		}
		
		int roleId = Integer.parseInt(request.getParameter("frole.fid"));
		Frole role = this.roleService.findById(roleId);
		fadmin.setFrole(role);
		this.adminService.updateObj(fadmin);
		
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_ROLE,"管理员"+fadmin.getFname()+"的角色设置为："+role.getFname());
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateAdminUser")
	public ModelAndView updateAdminUser(HttpServletRequest request) throws Exception{
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
		
		try {
			int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
			Fadmin fadmin = this.adminService.findById(fid);
			if(fadmin.getFuserid() >0){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","已关联用户ID，不允许重复操作");
				return modelAndView;
			}
			
			int roleId = Integer.parseInt(request.getParameter("fuserid"));
			Fuser fuser = this.userService.findById(roleId);
			if(fuser == null){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","会员UID不存在");
				return modelAndView;
			}
			if(!fuser.getFgoogleBind()){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","该会员未绑定GOOGLE认证");
				return modelAndView;
			}
			fadmin.setFuserid(fuser.getFid());
			this.adminService.updateObj(fadmin);
			Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
			this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE,"管理员"+fadmin.getFname()+"成功关联用户"+fuser.getFloginName());
		} catch (Exception e) {
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","会员UID不存在");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","关联成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
