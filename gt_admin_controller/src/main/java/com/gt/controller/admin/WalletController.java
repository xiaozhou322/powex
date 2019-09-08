package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;

@Controller
public class WalletController extends BaseAdminController {
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private CapitaloperationService capitaloperationService;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/walletList")
	public ModelAndView walletList(HttpServletRequest request) throws Exception{
	
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/walletList") ;
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
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and fvirtualcointype.ftype ="+CoinTypeEnum.FB_CNY_VALUE+" \n");

		
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
		

		modelAndView.addObject("totalAmount",this.adminService.getAllSum("Fvirtualwallet","ftotal", filter+""));
		modelAndView.addObject("totalFreze",this.adminService.getAllSum("Fvirtualwallet","ffrozen", filter+""));
		
		List<Fvirtualwallet> list = this.virtualWalletService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);

		modelAndView.addObject("walletList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "walletList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/usdtWalletList")
	public ModelAndView usdtWalletList(HttpServletRequest request) throws Exception{
	
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/exchange/walletList") ;
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
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and fvirtualcointype.ftype ="+CoinTypeEnum.FB_USDT_VALUE+" \n");
		
		String orderCondi = "";
		
		if(orderField != null && orderField.trim().length() >0){
			orderCondi = "order by "+orderField+"\n";
		}else{
			orderCondi = "order by fid \n";
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			orderCondi += orderDirection+"\n";
		}else{
			orderCondi += "desc \n";
		}
		
		double totalAmount = this.adminService.getAllSum("Fvirtualwallet","ftotal", filter+"");
		double totalFreze = this.adminService.getAllSum("Fvirtualwallet","ffrozen", filter+"");
		List<Fvirtualwallet> list = this.virtualWalletService.list((currentPage-1)*numPerPage, numPerPage, filter + " "+orderCondi,true);
		
		modelAndView.addObject("totalAmount",totalAmount);
		modelAndView.addObject("totalFreze",totalFreze);
		
		
		modelAndView.addObject("walletList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "walletList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/viewUserWallet")
	public ModelAndView viewUserWallet(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/viewUserWallet") ;
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
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and fvirtualcointype.ftype ="+CoinTypeEnum.FB_CNY_VALUE+" \n");
		
		if(request.getParameter("cid") != null){
			int cid =Integer.parseInt(request.getParameter("cid"));
			Fcapitaloperation c = this.capitaloperationService.findById(cid);
			filter.append("and fuser.fid ="+c.getFuser().getFid()+" \n");
			modelAndView.addObject("cid",cid);
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
		List<Fvirtualwallet> list = this.virtualWalletService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "viewUserWallet");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/usdtViewUserWallet")
	public ModelAndView usdtViewUserWallet(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/exchange/viewUserWallet") ;
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
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and fvirtualcointype.ftype ="+CoinTypeEnum.FB_USDT_VALUE+" \n");
		
		if(request.getParameter("cid") != null){
			int cid =Integer.parseInt(request.getParameter("cid"));
			Fcapitaloperation c = this.capitaloperationService.findById(cid);
			filter.append("and fuser.fid ="+c.getFuser().getFid()+" \n");
			modelAndView.addObject("cid",cid);
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
		List<Fvirtualwallet> list = this.virtualWalletService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "viewUserWallet");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		return modelAndView ;
	}
}
