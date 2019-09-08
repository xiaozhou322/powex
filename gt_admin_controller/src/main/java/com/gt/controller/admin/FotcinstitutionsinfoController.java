package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.FotcInstitutionsinfo;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.UserService;
import com.gt.service.front.FotcInstitutionsinfoService;
import com.gt.util.Utils;


/**
 * OTC--机构商controller
 * @author zhouyong
 *
 */
@Controller
public class FotcinstitutionsinfoController extends BaseAdminController {
	@Autowired
	private FotcInstitutionsinfoService fotcinstitutionsinfoService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	
	/**
	 * 查询机构商列表
	 * @param response
	 * @param re
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/buluo718admin/institutionsList")
	public ModelAndView goInstitutionsPage(HttpServletResponse response,HttpServletRequest re) throws Exception {
		ModelAndView modelAndView = new ModelAndView("ssadmin/institutions/list");
		//当前页
		int currentPage = 1;
		//搜索关键字
		String title = request.getParameter("title");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(title != null && title.trim().length() >0){
			filter.append("and (title like '%"+title+"%' OR \n");
			filter.append("intro like '%"+title+"%' ) \n");
			modelAndView.addObject("title", title);
		}
		
		List<FotcInstitutionsinfo> list = this.fotcinstitutionsinfoService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		
		modelAndView.addObject("institutionsinfoList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcInstitutionsinfo", filter+""));
		return modelAndView;
	}
	
	@RequestMapping(value="/buluo718admin/goAddInstitutions")
	public ModelAndView goAddInstitutions(HttpServletRequest request) {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url);
		String param = request.getParameter("uid");
		if (param != null) {
			int fid = Integer.parseInt(param);
			FotcInstitutionsinfo fotcinstitutionsinfo = this.fotcinstitutionsinfoService.findById(fid);
			modelAndView.addObject("fotcinstitutionsinfo", fotcinstitutionsinfo);
		}
		return modelAndView;
	}
	
	
	/**
	 * 保存机构商信息
	 * @param request
	 * @param fotcinstitutionsinfo
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/saveInstitutions")
	public ModelAndView saveInstitutions(HttpServletRequest request, FotcInstitutionsinfo fotcinstitutionsinfo) {
		ModelAndView modelAndView = new ModelAndView();
		Fadmin fadmin = (Fadmin)request.getSession().getAttribute("login_admin");
		int userId = Integer.parseInt(request.getParameter("userLookup.id"));
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
				
		Fuser fuser = this.userService.findById(userId);
		if(fuser.isFistiger()){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","承兑商不能设置为机构商");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
		try {
			fotcinstitutionsinfo.setFadmin(fadmin);
			fotcinstitutionsinfo.setFuser(fuser);
			fotcinstitutionsinfo.setCreate_time(Utils.getTimestamp());
			fotcinstitutionsinfoService.save(fotcinstitutionsinfo);
			fuser.setFisprojecter(true);
			this.userService.updateObj(fuser);
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","保存成功");
			modelAndView.addObject("callbackType","closeCurrent");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","保存错误");
			modelAndView.addObject("callbackType","closeCurrent");
		}
		return modelAndView;
	}
	
	
	/**
	 * 修改机构商信息
	 * @param request
	 * @param fotcinstitutionsinfo
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/updateInstitutions")
	public ModelAndView updateInstitutions(HttpServletRequest request, FotcInstitutionsinfo fotcinstitutionsinfo) {
		ModelAndView modelAndView = new ModelAndView();
		Fadmin fadmin = (Fadmin)request.getSession().getAttribute("login_admin");
		int userId = Integer.parseInt(request.getParameter("userLookup.id"));
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
				
		Fuser fuser = this.userService.findById(userId);
		try {
			fotcinstitutionsinfo.setFadmin(fadmin);
			fotcinstitutionsinfo.setFuser(fuser);
			fotcinstitutionsinfo.setUpdate_time(Utils.getTimestamp());
			fotcinstitutionsinfoService.save(fotcinstitutionsinfo);
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","保存成功");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
	/**
	 * 禁用机构商
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/forbidden")
	public ModelAndView forbidden(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Fadmin fadmin = (Fadmin)request.getSession().getAttribute("login_admin");
		String param = request.getParameter("uid");
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
				
		try {
			
			if (param != null) {
				int fid = Integer.parseInt(param);
				FotcInstitutionsinfo fotcinstitutionsinfo = this.fotcinstitutionsinfoService.findById(fid);
				if(fotcinstitutionsinfo!=null&&fotcinstitutionsinfo.getInstitutions_status()==1){
					fotcinstitutionsinfo.setInstitutions_status(2);
					fotcinstitutionsinfo.setFadmin(fadmin);
					fotcinstitutionsinfo.setUpdate_time(Utils.getTimestamp());
					fotcinstitutionsinfoService.save(fotcinstitutionsinfo);
					 modelAndView.addObject("message","禁用成功");
				}else{
					 modelAndView.addObject("message","机构商已禁用");
					 modelAndView.addObject("statusCode",300);
				}
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
	
				return modelAndView;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
	/**
	 * 启用机构商
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/startuse")
	public ModelAndView startuse(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Fadmin fadmin = (Fadmin)request.getSession().getAttribute("login_admin");
		String param = request.getParameter("uid");
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
				
		try {
			
			if (param != null) {
				int fid = Integer.parseInt(param);
				FotcInstitutionsinfo fotcinstitutionsinfo = this.fotcinstitutionsinfoService.findById(fid);
				if(fotcinstitutionsinfo!=null&&fotcinstitutionsinfo.getInstitutions_status()==2){
					fotcinstitutionsinfo.setInstitutions_status(1);
					fotcinstitutionsinfo.setFadmin(fadmin);
					fotcinstitutionsinfo.setUpdate_time(Utils.getTimestamp());
					fotcinstitutionsinfoService.save(fotcinstitutionsinfo);
					 modelAndView.addObject("message","启用成功");
					 modelAndView.addObject("statusCode",200);
				}else{
					 modelAndView.addObject("message","机构商已启用");
					 modelAndView.addObject("statusCode",300);
				}
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;

				return modelAndView;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
	
	/**
	 * 禁用卖单自动确认
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/forbiddenconfirm")
	public ModelAndView forbiddenconfirm(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Fadmin fadmin = (Fadmin)request.getSession().getAttribute("login_admin");
		String param = request.getParameter("uid");
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
				
		try {
			
			if (param != null) {
				int fid = Integer.parseInt(param);
				FotcInstitutionsinfo fotcinstitutionsinfo = this.fotcinstitutionsinfoService.findById(fid);
				if(fotcinstitutionsinfo!=null&&fotcinstitutionsinfo.getAuto_confirm()==1){
					fotcinstitutionsinfo.setAuto_confirm(0);
					fotcinstitutionsinfo.setFadmin(fadmin);
					fotcinstitutionsinfo.setUpdate_time(Utils.getTimestamp());
					fotcinstitutionsinfoService.save(fotcinstitutionsinfo);
					 modelAndView.addObject("message","禁用成功");
				}else{
					 modelAndView.addObject("message","卖单自动确认已禁用");
					 modelAndView.addObject("statusCode",300);
				}
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
	
				return modelAndView;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
	/**
	 * 启用机构商卖单自动确认
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buluo718admin/startuseconfirm")
	public ModelAndView startuseconfirm(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Fadmin fadmin = (Fadmin)request.getSession().getAttribute("login_admin");
		String param = request.getParameter("uid");
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
				
		try {
			
			if (param != null) {
				int fid = Integer.parseInt(param);
				FotcInstitutionsinfo fotcinstitutionsinfo = this.fotcinstitutionsinfoService.findById(fid);
				if(fotcinstitutionsinfo!=null&&fotcinstitutionsinfo.getAuto_confirm()==0){
					fotcinstitutionsinfo.setAuto_confirm(1);
					fotcinstitutionsinfo.setFadmin(fadmin);
					fotcinstitutionsinfo.setUpdate_time(Utils.getTimestamp());
					fotcinstitutionsinfoService.save(fotcinstitutionsinfo);
					 modelAndView.addObject("message","禁用成功");
					 modelAndView.addObject("statusCode",200);
				}else{
					 modelAndView.addObject("message","卖单自动确认已启用");
					 modelAndView.addObject("statusCode",300);
				}
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;

				return modelAndView;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
}
