package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.Pproject;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPprojectService;
import com.gt.util.Utils;

@Controller
public class PprojectController extends BaseAdminController{
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@Autowired
	private FrontPprojectService pprojectService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	/**
	 * 查询项目方列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pprojectList")
	public ModelAndView getPprojectList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/project/pprojectList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		/*if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and fTitle like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(request.getParameter("ftype") != null){
			filter.append("and ftype='"+request.getParameter("ftype")+"' \n");
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}else{
			modelAndView.addObject("ftype", "");
		}*/
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by createTime \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		List<Pproject> list = this.pprojectService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("pprojectList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "pprojectList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Pproject", filter+""));
		return modelAndView ;
	}
	
	/**
	 * 
	* @Title: findTradeMappingById  
	* @Description: 根据id获取币种详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/buluo718admin/pprojectDetail")
	public ModelAndView findPprojectById(@RequestParam(required=true)int pid) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		Pproject pproject = pprojectService.findById(pid);
		
		modelAndView.addObject("pproject", pproject);
		modelAndView.setViewName("ssadmin/project/pprojectUpdate");
		return modelAndView;
	}
	
	
	/**
	 * 修改项目方信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pprojectUpdate")
	public ModelAndView pprojectUpdate (HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int type = Integer.parseInt(request.getParameter("type"));
		Integer pprojectId = Integer.parseInt(request.getParameter("pprojectId"));

		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		Pproject pproject = pprojectService.findById(pprojectId);
		//项目账户类型只能由社群升级为项目方，不能由项目方降级为社群
		if(pproject.getType()==1) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "项目方不能降级为社群");
			return modelAndView;
		}
		if(pproject.getType()==2) {
			pproject.setType(type);
			pproject.setUpdateTime(Utils.getTimestamp());
			
			try {
				this.pprojectService.update(pproject);
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "网络异常");
				return modelAndView;
			}
		}
		
		//刷新项目方信息缓存
		this.reloadPproject();
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");

		return modelAndView;
	}
	
	
	//刷新项目方信息缓存
	private void reloadPproject() {
		//获取所有项目方信息
		String filter = "where 1=1 order by id asc";
		List<Pproject> pprojectList = this.pprojectService.list(0, 0, filter, false);
		frontConstantMapService.put("pprojectList", pprojectList) ;
	}
}
