package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.LogTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Flog;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.LogService;
import com.gt.util.Utils;

@Controller
public class LogController extends BaseAdminController {
	@Autowired
	private LogService logService;
	@Autowired
	private AdminService adminService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/logList")
	public ModelAndView logList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/logList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String ftype = request.getParameter("ftype"); 
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (!this.isSuper(request)){
			filter.append(" and fkey1<>'8' and fkey1<>'7215' \n");
		}
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and fkey1 = '"+keyWord+"' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		if (ftype != null
				&& ftype.trim().length() > 0) {
			int type = Integer.parseInt(ftype);
			if (type != 0) {
				filter.append("and ftype=" + ftype + " \n");
			}
			modelAndView.addObject("ftype", ftype);
		}else{
			modelAndView.addObject("ftype", 0);
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

		List<Flog> list = this.logService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		
		Map<Integer,String> logType = new HashMap<Integer,String>();
		for(int i=1;i<=14;i++){
			logType.put(i, LogTypeEnum.getEnumString(i));
		}
		logType.put(0, "全部");
		modelAndView.addObject("logType", logType);
		
		modelAndView.addObject("logList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "logList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Flog", filter+""));
		return modelAndView ;
	}
}
