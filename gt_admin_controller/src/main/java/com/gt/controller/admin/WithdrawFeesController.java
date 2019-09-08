package com.gt.controller.admin;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fwithdrawfees;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.WithdrawFeesService;
import com.gt.util.Utils;

@Controller
public class WithdrawFeesController extends BaseAdminController {
	@Autowired
	private WithdrawFeesService withdrawFeesService;
	@Autowired
	private AdminService adminService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/withdrawFeesList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/withdrawFeesList") ;
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
			Pattern pattern = Pattern.compile("[0-9]*"); 
		    boolean flag = pattern.matcher(keyWord).matches();
		    if(flag){
				filter.append("and flevel ="+keyWord+" \n");
				modelAndView.addObject("keywords", keyWord);
		    }
		}
		
		filter.append(" and fvirtualcointype.ftype="+CoinTypeEnum.FB_CNY_VALUE+" \n");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}
		
		List<Fwithdrawfees> list = this.withdrawFeesService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("withdrawFeesList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "withdrawFeesList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fwithdrawfees", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/usdtWithdrawFeesList")
	public ModelAndView UsdtIndex(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/exchange/withdrawFeesList") ;
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
			Pattern pattern = Pattern.compile("[0-9]*"); 
		    boolean flag = pattern.matcher(keyWord).matches();
		    if(flag){
				filter.append("and flevel ="+keyWord+" \n");
				modelAndView.addObject("keywords", keyWord);
		    }
		}
		
		filter.append(" and fvirtualcointype.ftype="+CoinTypeEnum.FB_USDT_VALUE+" \n");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}
		
		List<Fwithdrawfees> list = this.withdrawFeesService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("withdrawFeesList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "withdrawFeesList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fwithdrawfees", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goWithdrawFeesJSP")
	public ModelAndView goWithdrawFeesJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fwithdrawfees withdrawfees = this.withdrawFeesService.findById(fid);
			modelAndView.addObject("withdrawfees", withdrawfees);
		}
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateWithdrawFees")
	public ModelAndView updateWithdrawFees(HttpServletRequest request) throws Exception{
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
        Fwithdrawfees withdrawfees = this.withdrawFeesService.findById(fid);
        withdrawfees.setFfee(Double.valueOf(request.getParameter("ffee")));
        
        if(withdrawfees.getFfee()>=1 || withdrawfees.getFfee()<0){
        	modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","手续费率只能是小于1的小数！");
			return modelAndView;
        }
        
        this.withdrawFeesService.updateObj(withdrawfees);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/usdtUpdateWithdrawFees")
	public ModelAndView usdtUpdateWithdrawFees(HttpServletRequest request) throws Exception{
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
        Fwithdrawfees withdrawfees = this.withdrawFeesService.findById(fid);
        withdrawfees.setFfee(Double.valueOf(request.getParameter("ffee")));
        
        if(withdrawfees.getFfee()>=1 || withdrawfees.getFfee()<0){
        	modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","手续费率只能是小于1的小数！");
			return modelAndView;
        }
        
        this.withdrawFeesService.updateObj(withdrawfees);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
}
