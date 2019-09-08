package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.ScoreRecordTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.FscoreRecord;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ScoreRecordService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.util.Utils;

@Controller
public class ScoreRecordController extends BaseAdminController {
	@Autowired
	private ScoreRecordService scoreRecordService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/scoreRecordList")
	public ModelAndView balanceList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/scoreRecordList") ;
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
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filter.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filter.append("fuser.frealName like '%" + keyWord + "%' ) \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreatetime,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreatetime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}
		
		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and type=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
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
		
        Map<Integer,String> type = new HashMap<Integer,String>();
        type.put(0, "全部");
        for(int i=1;i<=9;i++){
        	type.put(i, ScoreRecordTypeEnum.getEnumString(i));
        }
        modelAndView.addObject("type", type);
		
		List<FscoreRecord> list = this.scoreRecordService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("scoreRecordList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "scoreRecordList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FscoreRecord", filter+""));
		return modelAndView ;
	}
}
