package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.OperationlogEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualpresalelog;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.admin.VirtualPresaleLogService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;

@Controller
public class VirtualPresaleLogController extends BaseAdminController {
	@Autowired
	private VirtualPresaleLogService virtualPresaleLogService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/virtualpresalelogList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/virtualpresalelogList") ;
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
			filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
			filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
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
		List<Fvirtualpresalelog> list = this.virtualPresaleLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("virtualpresalelogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualpresalelog", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goVirtualPresaleLogJSP")
	public ModelAndView govirtualpresalelogJSP(HttpServletRequest request) throws Exception{

		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fvirtualpresalelog virtualpresalelog = this.virtualPresaleLogService.findById(fid);
			Fuser fuser = this.userService.findById(virtualpresalelog.getFuser().getFid());
			modelAndView.addObject("virtualpresalelog", virtualpresalelog);
			modelAndView.addObject("fuser", fuser);
		}
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE,1);
		modelAndView.addObject("allType", allType);
		modelAndView.setViewName(url);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveVirtualPresaleLog")
	public ModelAndView savevirtualpresalelog(HttpServletRequest request) throws Exception{
		
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
		
		Fvirtualpresalelog virtualpresalelog = new Fvirtualpresalelog();
		int userId = Integer.parseInt(request.getParameter("userLookup.id"));
		Fuser user = this.userService.findById(userId);
		int vid = Integer.parseInt(request.getParameter("vid"));
		Fvirtualcointype coinType = this.virtualCoinService.findById(vid);
		Double fqty = Double.valueOf(request.getParameter("fqty"));
		int season = Integer.parseInt(request.getParameter("season"));
		if(season==0){
			season = 200;
		}
		virtualpresalelog.setFqty(fqty);
		virtualpresalelog.setFvirtualcointype(coinType);
		virtualpresalelog.setFuser(user);
		virtualpresalelog.setFseason(season);
		virtualpresalelog.setFstatus(OperationlogEnum.SAVE);
		if(request.getParameter("fisSendMsg") != null){
			virtualpresalelog.setFisSendMsg(1);
		}else{
			virtualpresalelog.setFisSendMsg(0);
		}
		this.virtualPresaleLogService.saveObj(virtualpresalelog);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteVirtualPresaleLog")
	public ModelAndView deletevirtualpresalelog(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualpresalelog virtualpresalelog = this.virtualPresaleLogService.findById(fid);
		if(virtualpresalelog.getFstatus() != OperationlogEnum.SAVE){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","删除失败，记录已审核");
			return modelAndView;
		}
		
		this.virtualPresaleLogService.deleteObj(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/auditVirtualPresaleLog")
	public ModelAndView auditvirtualpresalelog(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("uid"));
		int type =0 ;
		if (request.getParameter("type")!=null){
			type=Integer.parseInt(request.getParameter("type"));
		}
		boolean flag = false;
		Fvirtualpresalelog virtualpresalelog = this.virtualPresaleLogService.findById(fid);
		
		if(virtualpresalelog.getFstatus() != OperationlogEnum.SAVE){
			if (type==0){
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","已审核，不允许重复审核");
				return modelAndView;
			}else{
				if(virtualpresalelog.getFstatus() == OperationlogEnum.AUDIT){
					modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","已发放冻结，不允许取消审核");
					return modelAndView;
				}
			}
		}

		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		if (type==0){
			//校验审核码，审核码正确才能进行人民币审核操作
			String confirmCode = request.getParameter("confirmcode");
			String confirmCodeMD5 = Utils.MD5(confirmCode,sessionAdmin.getSalt());
			if (!confirmCodeMD5.equals(sessionAdmin.getFconfirmcode())) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				return modelAndView;
			}
			modelAndView.addObject("callbackType","closeCurrent");

		}
		
		try {
			double qty = virtualpresalelog.getFqty();
			int coinTypeId = virtualpresalelog.getFvirtualcointype().getFid();
			int userId = virtualpresalelog.getFuser().getFid();
			String sql =  "where fvirtualcointype.fid="+coinTypeId+"and fuser.fid="+userId;
			List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0,sql, false);
			if(all != null && all.size() == 1){
				Fvirtualwallet virtualwallet = all.get(0);
				if (type==0){
					virtualwallet.setFfrozenLendBtc(qty);//先冻结额度
					virtualwallet.setFseason(virtualpresalelog.getFseason()); //释放季数
					virtualpresalelog.setFstatus(OperationlogEnum.FFROZEN);
				}else{
					virtualwallet.setFfrozenLendBtc(virtualwallet.getFfrozenLendBtc()-qty);//先冻结额度
					virtualwallet.setFseason(0); //释放季数
					virtualpresalelog.setFstatus(OperationlogEnum.SAVE);
				}
				virtualpresalelog.setFcreator(sessionAdmin);
				virtualpresalelog.setFcreateTime(Utils.getTimestamp());
				this.virtualPresaleLogService.updateVirtualPresaleLog(virtualwallet,virtualpresalelog);
			}else{
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","会员钱包有误");
				return modelAndView;
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		
		if(!flag){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			
			modelAndView.addObject("message","审核失败");
			return modelAndView;
		}
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","审核成功");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/sendVirtualPresaleLog")
	public ModelAndView sendvirtualpresalelog(HttpServletRequest request) throws Exception{
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
		
		int fid = Integer.parseInt(request.getParameter("uid"));
		boolean flag = false;
		Fvirtualpresalelog virtualpresalelog = this.virtualPresaleLogService.findById(fid);
		
		if(virtualpresalelog.getFstatus() != OperationlogEnum.FFROZEN){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","只会状态为冻结，才允许发放！");
			return modelAndView;
		}

		try {
			double qty = virtualpresalelog.getFqty();
			int coinTypeId = virtualpresalelog.getFvirtualcointype().getFid();
			int userId = virtualpresalelog.getFuser().getFid();
			String sql =  "where fvirtualcointype.fid="+coinTypeId+"and fuser.fid="+userId;
			List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0,sql, false);
			if(all != null && all.size() == 1){
				Fvirtualwallet virtualwallet = all.get(0);
				//设置预购的币数和季数，用来定时发布任务
				virtualwallet.setFseason(virtualpresalelog.getFseason());
				virtualwallet.setFcanlendBtc(qty);
				virtualwallet.setFfrozenLendBtc(0);
				Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
				virtualpresalelog.setFstatus(OperationlogEnum.AUDIT);
				virtualpresalelog.setFcreator(sessionAdmin);
				virtualpresalelog.setFcreateTime(Utils.getTimestamp());
				this.virtualPresaleLogService.updateVirtualPresaleLog(virtualwallet,virtualpresalelog);
			}else{
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","会员钱包有误");
				return modelAndView;
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		
		if(!flag){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","发放失败");
			return modelAndView;
		}
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","发放成功");
		return modelAndView;
	}
}
