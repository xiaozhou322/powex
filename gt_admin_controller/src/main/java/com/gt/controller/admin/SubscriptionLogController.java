package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.SubStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fsubscriptionlog;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.SubscriptionLogService;
import com.gt.service.admin.SubscriptionService;
import com.gt.service.admin.SystemArgsService;
import com.gt.service.front.FrontUserService;

@Controller
public class SubscriptionLogController extends BaseAdminController {
	@Autowired
	private SubscriptionLogService subscriptionLogService;
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private SystemArgsService systemArgsService;
	//每页显示多少条数据
	private int numPerPage = 500;
	
	
	@RequestMapping("/buluo718admin/subscriptionLogList")
	public ModelAndView subscriptionLogList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/subscriptionLogList") ;
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
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fuser.floginName like '%"+ keyWord+"%' OR \n");
			filter.append("fuser.fnickName like '%"+ keyWord+"%' OR \n");
			filter.append("fuser.frealName like '%"+ keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(request.getParameter("parentId") != null){
			int parentId = Integer.parseInt(request.getParameter("parentId"));
			filter.append("and fsubscription.fid="+parentId+"\n");
			modelAndView.addObject("parentId", parentId);
		}
		
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(0, "全部");
		typeMap.put(1,SubStatusEnum.getEnumString(1));
		typeMap.put(2,SubStatusEnum.getEnumString(2));
		typeMap.put(3,SubStatusEnum.getEnumString(3));
		modelAndView.addObject("typeMap", typeMap);
		
		if(request.getParameter("ftype") != null && request.getParameter("ftype").trim().length() >0){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and fstatus="+request.getParameter("ftype")+" \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}else{
			modelAndView.addObject("ftype", 0);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}
		List<Fsubscriptionlog> list = this.subscriptionLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		
		
		modelAndView.addObject("subscriptionLogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "subscriptionLogList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fsubscriptionlog", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/subscriptionLogList1")
	public ModelAndView subscriptionLogList1(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/subscriptionLogList1") ;
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
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fuser.floginName like '%"+ keyWord+"%' OR \n");
			filter.append("fuser.fnickName like '%"+ keyWord+"%' OR \n");
			filter.append("fuser.frealName like '%"+ keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(request.getParameter("parentId") != null){
			int parentId = Integer.parseInt(request.getParameter("parentId"));
			filter.append("and fsubscription.fid="+parentId+"\n");
			modelAndView.addObject("parentId", parentId);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}
		List<Fsubscriptionlog> list = this.subscriptionLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("subscriptionLogList1", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "subscriptionLogList1");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fsubscriptionlog", filter+""));
		return modelAndView ;
	}

}
