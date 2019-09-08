package com.gt.controller.admin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.Fintrolinfo;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.IntrolinfoService;
import com.gt.util.Utils;
import com.gt.util.XlsExport;
import com.gt.Enum.IntrolInfoTypeEnum;

@Controller
public class IntrolinfoController extends BaseAdminController {
	@Autowired
	private IntrolinfoService introlinfoService;
	@Autowired
	private AdminService adminService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/introlinfoList")
	public ModelAndView introlinfoList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/introlinfoList") ;
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

		if(keyWord != null && keyWord.trim().length() >0){
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%"+keyWord+"%' \n");
				filter.append("or fuser.fnickName like '%"+keyWord+"%' \n");
				filter.append("or fuser.frealName like '%"+keyWord+"%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(ftype != null && ftype.trim().length() >0){
			try {
				int itype = Integer.parseInt(ftype);
				if(itype>-1){
					filter.append("and ftype =" + itype + " \n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			modelAndView.addObject("ftype", ftype);
		}
		
		Map typeMap = new HashMap();
		for(int i=0;i<=13;i++){
			if (!IntrolInfoTypeEnum.getEnumString(i).equals("")){
				typeMap.put(String.valueOf(i), IntrolInfoTypeEnum.getEnumString(i));
			}
		}
		modelAndView.addObject("typeMap", typeMap);
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
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
		List<Fintrolinfo> list = this.introlinfoService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("introlinfoList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "introlinfoList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fintrolinfo", filter+""));
		return modelAndView ;
	}
	
	
	public List<Fintrolinfo> getUserGradeList(HttpServletRequest request) throws Exception{
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String ftype = request.getParameter("ftype");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		int fid =0;
		if(keyWord != null && keyWord.trim().length() >0){
			try {
				fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%"+keyWord+"%' \n");
				filter.append("or fuser.fnickName like '%"+keyWord+"%' \n");
				filter.append("or fuser.frealName like '%"+keyWord+"%') \n");
			}
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
		}
		
		if(ftype != null && ftype.trim().length() >0){
			try {
				int itype = Integer.parseInt(ftype);
				if(itype>-1){
				filter.append("and ftype =" + itype + " \n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		
		List<Fintrolinfo> list = this.introlinfoService.list(0, 0, filter+"",false);
		return list ;
	}
	
	private static enum GradeDetailFiled {
		UID,登录名,昵称,真实姓名,收益名称,收益数量,创建时间;
	}
	
	@RequestMapping("/buluo718admin/introlinfoExport")
	public ModelAndView introlinfoExport(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=introlinfoDetails.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (GradeDetailFiled filed : GradeDetailFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fintrolinfo> gradeList = getUserGradeList(request);
		Iterator it = gradeList.iterator();
		while(it.hasNext()){
			Fintrolinfo v = (Fintrolinfo)it.next();
			e.createRow(rowIndex++);
			for (GradeDetailFiled filed : GradeDetailFiled.values()) {
				switch (filed) {
				case UID:
					e.setCell(filed.ordinal(),v.getFuser().getFid());
					break;
				case 登录名:
					e.setCell(filed.ordinal(), v.getFuser().getFloginName());
					break;
				case 昵称:
					e.setCell(filed.ordinal(), v.getFuser().getFnickName());
					break;
				case 真实姓名:
					e.setCell(filed.ordinal(), v.getFuser().getFrealName());
					break;
				case 收益名称:
					e.setCell(filed.ordinal(), v.getFtitle());
					break;
				case 收益数量:
					e.setCell(filed.ordinal(), v.getFqty());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(), v.getFcreatetime());
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
	
}
