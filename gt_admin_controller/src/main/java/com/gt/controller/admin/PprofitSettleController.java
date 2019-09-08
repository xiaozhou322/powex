package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.PprojectSettleStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pprofitlogs;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontPprofitlogsService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;

/**
 * 项目方收益结算admin控制器
 * @author zhouyong
 *
 */
@Controller
public class PprofitSettleController extends BaseAdminController{
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontPprofitlogsService pprofitlogsService;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	
	
	
	/**
	 * 查询待结算的项目方收益
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pprofitWaitSettleList")
	public ModelAndView getPprofitWaitSettleList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/project/pprofitWaitSettleList") ;
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
			filter.append("and (projectId.floginName like '%"+keyWord+"%' or \n");
			filter.append("projectId.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("projectId.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("projectId.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		String startDate = request.getParameter("startDate");
		if(StringUtils.isNotBlank(startDate)){
			filter.append("and DATE_FORMAT(statisticalDate,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(endDate)){
			filter.append("and DATE_FORMAT(statisticalDate,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		filter.append("and status=0 \n");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by statisticalDate \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		List<Pprofitlogs> list = this.pprofitlogsService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("pprofitlogsList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "pprofitWaitSettleList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Pprofitlogs", filter+""));
		return modelAndView ;
	}
	
	
	/**
	 * 查询已结算的项目方收益
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pprofitSettleList")
	public ModelAndView getPprofitSettleList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/project/pprofitSettleList") ;
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
			filter.append("and (projectId.floginName like '%"+keyWord+"%' or \n");
			filter.append("projectId.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("projectId.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("projectId.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		String startDate = request.getParameter("startDate");
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and DATE_FORMAT(statisticalDate,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and DATE_FORMAT(statisticalDate,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		filter.append("and status=1 \n");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by statisticalDate \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		List<Pprofitlogs> list = this.pprofitlogsService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("pprofitlogsList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "pprofitSettleList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Pprofitlogs", filter+""));
		return modelAndView ;
	}
	
	
	/**
	 * 项目方收益结算
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pprofitSettle")
	public ModelAndView ptrademappingAudit (HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");

		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (projectId.floginName like '%"+keyWord+"%' or \n");
			filter.append("projectId.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("projectId.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("projectId.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		String startDate = request.getParameter("startDate");
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and DATE_FORMAT(statisticalDate,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and DATE_FORMAT(statisticalDate,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		
		filter.append("and status=0 \n");
		
		try {
			List<Pprofitlogs> list = this.pprofitlogsService.list(0, 0, filter+"",false);
			for (Pprofitlogs profit : list) {
				
				if (profit.getStatus()==PprojectSettleStatusEnum.HAS_SETTLE){
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "已经结算，不允许重复结算!");
					return modelAndView;
				}
				
				//查询该用户钱包
				List<Fvirtualwallet> virtualWallets = this.virtualWalletService.findByTwoProperty
						("fuser.fid", profit.getProjectId().getFid(), "fvirtualcointype.fid",profit.getCointypeId().getFid());
				
				if(null != virtualWallets && virtualWallets.size() > 0) {
					Fvirtualwallet wallet = virtualWallets.get(0);
					wallet.setFtotal(wallet.getFtotal() + profit.getAmount().doubleValue());
					wallet.setFlastUpdateTime(Utils.getTimestamp());
					
					profit.setStatus(1);    //0:未结算      1：已结算
					profit.setUpdateTime(Utils.getTimestamp());
					pprofitlogsService.updateProfitlogsSettle(wallet, profit);
				}
			}
			
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "网络异常");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "结算成功");

		return modelAndView;
	}
	
	
}
