package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Flimittrade;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.LimittradeService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.util.Utils;

@Controller
public class LimittradeController extends BaseAdminController {
	@Autowired
	private LimittradeService limittradeService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/limittradeList")
	public ModelAndView limittradeList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/limittradeList") ;
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
//		if(keyWord != null && keyWord.trim().length() >0){
//			filter.append("and (fname like '%"+keyWord+"%' or \n");
//			filter.append("furl like '%"+keyWord+"%' ) \n");
//			modelAndView.addObject("keywords", keyWord);
//		}
//		if(orderField != null && orderField.trim().length() >0){
//			filter.append("order by "+orderField+"\n");
//		}else{
//			filter.append("order by fcreateTime \n");
//		}
//		
//		if(orderDirection != null && orderDirection.trim().length() >0){
//			filter.append(orderDirection+"\n");
//		}else{
//			filter.append("desc \n");
//		}
		
		List<Flimittrade> list = this.limittradeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("limittradeList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "limittradeList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Flimittrade", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goLimittradeJSP")
	public ModelAndView goLimittradeJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Flimittrade flimittrade = this.limittradeService.findById(fid);
			modelAndView.addObject("flimittrade", flimittrade);
		}
		
		String filter = "where fstatus="+TrademappingStatusEnum.ACTIVE;
		List<Ftrademapping> trademappings = this.tradeMappingService.list(0, 0, filter, false);
		modelAndView.addObject("trademappings", trademappings);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveLimittrade")
	public ModelAndView saveLimittrade(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		int vid = Integer.parseInt(request.getParameter("vid"));
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(vid) ;
		String filter = "where ftrademapping.fid="+vid;
		int count = this.adminService.getAllCount("Flimittrade", filter);
		if(count >0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","此币种已存在记录");
			return modelAndView;
		}
		Flimittrade limittrade = new Flimittrade();
		limittrade.setFtrademapping(ftrademapping) ;
		limittrade.setFpercent(Double.valueOf(request.getParameter("fpercent")));
		limittrade.setFdownprice(Double.valueOf(request.getParameter("fdownprice")));
		limittrade.setFupprice(Double.valueOf(request.getParameter("fupprice")));
		this.limittradeService.saveObj(limittrade);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteLimittrade")
	public ModelAndView deleteLimittrade(HttpServletRequest request) throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.limittradeService.deleteObj(fid);
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/updateLimittrade")
	public ModelAndView updateLimittrade(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("fid"));
		Flimittrade limittrade = this.limittradeService.findById(fid);
		int vid = Integer.parseInt(request.getParameter("vid"));
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(vid) ;
		String filter = "where ftrademapping.fid="+vid+" and fid <>"+fid;
		int count = this.adminService.getAllCount("Flimittrade", filter);
		if(count >0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","此币种已存在记录");
			return modelAndView;
		}
		limittrade.setFtrademapping(ftrademapping) ;
		limittrade.setFpercent(Double.valueOf(request.getParameter("fpercent")));
		limittrade.setFdownprice(Double.valueOf(request.getParameter("fdownprice")));
		limittrade.setFupprice(Double.valueOf(request.getParameter("fupprice")));
		this.limittradeService.updateObj(limittrade);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
