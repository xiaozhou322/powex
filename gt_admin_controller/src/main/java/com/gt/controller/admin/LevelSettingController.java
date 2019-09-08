package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.FlevelSetting;
import com.gt.service.admin.LevelSettingService;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ValidatemessageService;
import com.gt.util.Utils;

@Controller
public class LevelSettingController extends BaseAdminController {
	@Autowired
	private LevelSettingService levelSettingService;
	@Autowired
	private ValidatemessageService validatemessageService;
	@Autowired
	private AdminService adminService ;
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/levelSettingList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/levelSettingList") ;
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
			filter.append("order by level \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("asc \n");
		}
		List<FlevelSetting> list = this.levelSettingService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("levelSettingList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "levelSettingList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FlevelSetting", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goLevelSettingJSP")
	public ModelAndView goLevelSettingJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			FlevelSetting levelSetting = this.levelSettingService.findById(fid);
			modelAndView.addObject("flevelSetting", levelSetting);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateLevelSetting")
	public ModelAndView updateLevelSetting(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
        int fid = Integer.parseInt(request.getParameter("fid"));
        FlevelSetting levelSetting = this.levelSettingService.findById(fid);
        levelSetting.setScore(Double.valueOf(request.getParameter("score")));
        if(levelSetting.getLevel() != 6){
        	 levelSetting.setScore2(Double.valueOf(request.getParameter("score2")));
        }
        this.levelSettingService.updateObj(levelSetting);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
