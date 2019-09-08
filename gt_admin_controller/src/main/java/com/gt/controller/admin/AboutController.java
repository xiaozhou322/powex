package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




//import com.gt.comm.ConstantMap;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fabout;
import com.gt.entity.Fvalidatemessage;
import com.gt.service.admin.AboutService;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ValidatemessageService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.Utils;

@Controller
public class AboutController extends BaseAdminController {
	@Autowired
	private AboutService aboutService;
	@Autowired
	private ValidatemessageService validatemessageService;
	@Autowired
	private AdminService adminService ;
	/*@Autowired
	private ConstantMap map;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/aboutList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/aboutList") ;
		int currentPage = 1;
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and ftitle like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by ftype \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Fabout> list = this.aboutService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("aboutList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "aboutList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fabout", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/validatemessageList")
	public ModelAndView validatemessageList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/validatemessageList") ;
		int currentPage = 1;
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		
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
		List<Fvalidatemessage> list = this.validatemessageService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("validatemessageList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "aboutList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvalidatemessage", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goAboutJSP")
	public ModelAndView goAboutJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fabout about = this.aboutService.findById(fid);
			modelAndView.addObject("fabout", about);
		}
		
		//String[] args = this.map.get("helpType").toString().split("#");
		String[] args = this.frontConstantMapService.get("helpType").toString().split("#");
		Map<String,String> type = new HashMap<String,String>();
		for (int i = 0; i < args.length; i++) {
			type.put(args[i], args[i]);
		}
		
		//String[] args_cn = this.map.get("helpType_CN").toString().split("#");
		String[] args_cn = this.frontConstantMapService.get("helpType_CN").toString().split("#");
		Map<String,String> type_cn = new HashMap<String,String>();
		for (int i = 0; i < args_cn.length; i++) {
			type_cn.put(args_cn[i], args_cn[i]);
		}
		
		modelAndView.addObject("type", type);
		modelAndView.addObject("type_cn", type_cn);
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateAbout")
	public ModelAndView updateAbout(HttpServletRequest request) throws Exception{
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
        int fid = Integer.parseInt(request.getParameter("fid"));
        Fabout about = this.aboutService.findById(fid);
        about.setFcontent(request.getParameter("fcontent"));
        about.setFtitle(request.getParameter("ftitle"));
        about.setFtype(request.getParameter("ftype"));
        about.setFcontent_cn(request.getParameter("fcontent_cn"));
        about.setFtitle_cn(request.getParameter("ftitle_cn"));
        about.setFtype_cn(request.getParameter("ftype_cn"));
        this.aboutService.updateObj(about);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveAbout")
	public ModelAndView saveAbout(HttpServletRequest request) throws Exception{
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
        Fabout about = new Fabout();
        about.setFcontent(request.getParameter("fcontent"));
        about.setFtitle(request.getParameter("ftitle"));
        about.setFtype(request.getParameter("ftype"));
        about.setFcontent_cn(request.getParameter("fcontent_cn"));
        about.setFtitle_cn(request.getParameter("ftitle_cn"));
        about.setFtype_cn(request.getParameter("ftype_cn"));
        this.aboutService.saveObj(about);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteAbout")
	public ModelAndView deleteAbout(HttpServletRequest request) throws Exception{
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
		this.aboutService.deleteObj(fid);
		
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
}
