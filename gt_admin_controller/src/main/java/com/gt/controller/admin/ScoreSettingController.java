package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.FscoreSetting;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ScoreSettingService;
import com.gt.util.Utils;

@Controller
public class ScoreSettingController extends BaseAdminController {
	@Autowired
	private ScoreSettingService scoreSettingService;
	@Autowired
	private AdminService adminService ;
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/scoreSettingList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/scoreSettingList") ;
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
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("asc \n");
		}
		List<FscoreSetting> list = this.scoreSettingService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("scoreSettingList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "scoreSettingList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FscoreSetting", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goScoreSettingJSP")
	public ModelAndView goScoreSettingJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			FscoreSetting scoreSetting = this.scoreSettingService.findById(fid);
			modelAndView.addObject("fscoreSetting", scoreSetting);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateScoreSetting")
	public ModelAndView updateScoreSetting(HttpServletRequest request) throws Exception{
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
        FscoreSetting scoreSetting = this.scoreSettingService.findById(fid);
        scoreSetting.setScore(Double.valueOf(request.getParameter("score")));
        scoreSetting.setRemark(request.getParameter("remark"));
        this.scoreSettingService.updateObj(scoreSetting);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
