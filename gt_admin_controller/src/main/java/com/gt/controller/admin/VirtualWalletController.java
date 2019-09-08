package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;
import com.gt.entity.Fuser;
import com.gt.service.admin.UserService;

@Controller
public class VirtualWalletController extends BaseAdminController {
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private UserService userService;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/virtualwalletList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
	
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/virtualwalletList") ;
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
		
		if (request.getParameter("fistiger") != null){
			String fistiger = request.getParameter("fistiger");
			
			if (fistiger.equals("True")){
				filter.append("and fuser.fistiger=true ");
			}
			
			modelAndView.addObject("fistiger", fistiger);
		}
		
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and fvirtualcointype.fid="+type+"\n");
			}
			modelAndView.addObject("ftype", type);
		}else{
			modelAndView.addObject("ftype", 0);
		}
		
		filter.append(" and fvirtualcointype.ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and fvirtualcointype.ftype <>"+CoinTypeEnum.FB_USDT_VALUE+" \n");
		
		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);
		
		
		
		modelAndView.addObject("totalAmount",this.adminService.getAllSum("Fvirtualwallet","ftotal", filter+""));
		modelAndView.addObject("totalFreze",this.adminService.getAllSum("Fvirtualwallet","ffrozen", filter+""));
		modelAndView.addObject("totalLocked",this.adminService.getAllSum("Fvirtualwallet","flocked", filter+""));
		
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
		modelAndView.addObject("virtualwalletList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualwalletList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/presalewalletList")
	public ModelAndView PresaleIndex(HttpServletRequest request) throws Exception{
	
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/virtualwalletList") ;
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
		
		filter.append("and fcanlendBtc>0 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			if (!this.isSuper(request)){
				filter.append(" and fuser.fid<>7215 \n");
			}
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
		
		if (request.getParameter("fistiger") != null){
			String fistiger = request.getParameter("fistiger");
			
			if (fistiger.equals("True")){
				filter.append("and fuser.fistiger=true ");
			}
			
			modelAndView.addObject("fistiger", fistiger);
		}
		
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and fvirtualcointype.fid="+type+"\n");
			}
			modelAndView.addObject("ftype", type);
		}else{
			modelAndView.addObject("ftype", 0);
		}
		
		filter.append(" and fvirtualcointype.ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and fvirtualcointype.ftype <>"+CoinTypeEnum.FB_USDT_VALUE+" \n");
		
		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);
		

		modelAndView.addObject("totalPresale",this.adminService.getAllSum("Fvirtualwallet","fcanlendBtc" ,filter+""));
		modelAndView.addObject("ridPresale",this.adminService.getAllSum("Fvirtualwallet","falreadyLendBtc" ,filter+""));
		
		if (!this.isSuper(request)){
			filter.append(" and fuser.fid<>7215 \n");
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
		modelAndView.addObject("virtualwalletList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualwalletList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		return modelAndView ;
	}
	
	
	@RequestMapping("/buluo718admin/goWalletJSP")
	public ModelAndView goWalletJSP(HttpServletRequest request) throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fvirtualwallet wallet = this.virtualWalletService.findById(fid);
			modelAndView.addObject("wallet", wallet);
			Fvirtualcointype coin = this.virtualCoinService.findById(wallet.getFvirtualcointype().getFid());
			Fuser fuser = this.userService.findById(wallet.getFuser().getFid()); 
			modelAndView.addObject("coin", coin);
			modelAndView.addObject("fuser", fuser);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/lockUserWallet")
	public ModelAndView lockUserWallet(HttpServletRequest request) throws Exception {
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
		String act = request.getParameter("act");
		String fqty = request.getParameter("fqty");
		int uid = Integer.parseInt(request.getParameter("uid"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		int wid = Integer.parseInt(request.getParameter("wid"));
		
		Fuser user = this.userService.findById(uid);
		Fvirtualwallet wallet = this.virtualWalletService.findById(wid);
		Fvirtualcointype coin = this.virtualCoinService.findById(cid);
		
		if(wallet.getFuser().getFid()!=uid || wallet.getFvirtualcointype().getFid()!=cid){
			modelAndView.addObject("message","用户钱包信息错误，请仔细检查");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		//锁仓操作
		double qty = Double.valueOf(fqty);
		if (act.equals("lock")){
			if (qty>wallet.getFtotal()){
				modelAndView.addObject("message","可用数量不足以支付锁仓数量");
				modelAndView.addObject("statusCode",300);
				return modelAndView;
			}
			wallet.setFtotal(wallet.getFtotal()-qty);
			wallet.setFlocked(wallet.getFlocked()+qty);
			
		}else if(act.equals("unlock")){
			if (qty>wallet.getFlocked()){
				modelAndView.addObject("message","锁仓数量不足以支付解锁数量");
				modelAndView.addObject("statusCode",300);
				return modelAndView;
			}
			wallet.setFtotal(wallet.getFtotal()+qty);
			wallet.setFlocked(wallet.getFlocked()-qty);
		}
		this.virtualWalletService.updateObj(wallet);
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "设置成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}

}
