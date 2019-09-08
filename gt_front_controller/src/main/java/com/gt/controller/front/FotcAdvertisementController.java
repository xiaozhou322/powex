package com.gt.controller.front;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseController;
import com.gt.entity.FotcAdvertisement;
import com.gt.entity.FotcOrder;
import com.gt.entity.FotcUserPaytype;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.SystemArgsService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FotcAdvertisementService;
import com.gt.service.front.FotcOrderService;
import com.gt.service.front.FotcUserPaytypeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.PaginUtil;
import com.gt.util.Utils;

import net.sf.json.JSONObject;
/**
 * 
 * 
 * 广告操作
 * @author admin
 *
 */
@Controller
public class FotcAdvertisementController extends BaseController {
	
	@Autowired
	private FotcAdvertisementService advertisementService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FvirtualWalletService fvirtualWalletService;
	@Autowired
	private FotcOrderService otcorderService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FotcUserPaytypeService otcuserpaytypeService ;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FrontUserService frontUserService ;
	
	/**
	 * 跳转到广告信息填写页面
	 * 
	 * 
	 */
	@RequestMapping("/advertisement/puborder")
	public ModelAndView index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
	
		List<Fvirtualcointype> allType = this.virtualCoinService.list(0,10," where fstatus=1 and fisotc=1",true);
		modelAndView.addObject("amountTypeMap", allType);
		modelAndView.addObject("menuflag", "puborder");
	    modelAndView.setViewName("front/advertisement/puborder") ;
		if(this.isMobile(request)) {
 	    	modelAndView.setViewName("mobile/advertisement/puborder") ;
 	    }
		return modelAndView ;
	}
	@ResponseBody
	@RequestMapping("/advertisement/saveAdvertisement")
	public String saveAdvertisement(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		
		double price=0.0;
		try{
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			//查询承兑商绑定的支付方式
			List<FotcUserPaytype> paytypeList = otcuserpaytypeService.findAllPayType("fuser.fid", fuser.getFid());
			if(paytypeList.size() == 0) {
				jsonObject.accumulate("success", false);
				jsonObject.accumulate("massage", "请先绑定支付方式");
				return jsonObject.toString() ;
			}
			int ad_type=Integer.valueOf(request.getParameter("ad_type"));
			double total_count= Double.valueOf(request.getParameter("total_count"));
			double order_limit_min= Double.valueOf(request.getParameter("order_limit_min"));
			double order_limit_max= Double.valueOf(request.getParameter("order_limit_max"));
			
			if(order_limit_min>order_limit_max){
				jsonObject.accumulate("success", false);
				jsonObject.accumulate("massage", "最小交易限额不得大于最大交易限额");
				return jsonObject.toString() ;
			}
			String ad_desc=request.getParameter("ad_desc");
			String remark = request.getParameter("remark");
			int amount_type=Integer.valueOf(request.getParameter("amount_type"));
			Fvirtualcointype fvirtualcointype=this.virtualCoinService.findById(amount_type);
			if(fvirtualcointype!=null&&fvirtualcointype.getfShortName().equals("WTO")){
				if(ad_type==1){
					String sellPrice = this.systemArgsService.getValue("wtopricesell");
					price=Double.valueOf(sellPrice);
				}else if(ad_type==2){
					String sellPrice = this.systemArgsService.getValue("wtopricebuy");
					price=Double.valueOf(sellPrice);
				}
				
			}else{
				 price= Double.valueOf(request.getParameter("price"));	
			}
	
			FotcAdvertisement fotcAdvertisement=new FotcAdvertisement();
			Fuser user=GetSession(request);
			fotcAdvertisement.setAd_type(ad_type);
			fotcAdvertisement.setTotal_count(total_count);
			fotcAdvertisement.setOrder_limit_max(order_limit_max);
			fotcAdvertisement.setOrder_limit_min(order_limit_min);
			fotcAdvertisement.setPrice(price);
			fotcAdvertisement.setAd_desc(ad_desc);
			fotcAdvertisement.setRemark(remark);
			fotcAdvertisement.setUser(user);
			fotcAdvertisement.setFreeze_count(0);
			fotcAdvertisement.setStatus(0);
			fotcAdvertisement.setRepertory_count(total_count);
			fotcAdvertisement.setCreate_time(Utils.getTimestamp());
			fotcAdvertisement.setUpdate_time(Utils.getTimestamp());
		//	Fvirtualcointype fvirtualcointype=virtualCoinService.findById(amount_type);
			
			fotcAdvertisement.setFvirtualcointype(fvirtualcointype);
		
			if(ad_type==1){
				List<Fvirtualwallet> fvirtualwallets1 = fvirtualWalletService.findByTwoProperty("fuser.fid", user.getFid(), "fvirtualcointype.fid",fvirtualcointype.getFid());
				
				if(null != fvirtualwallets1 && fvirtualwallets1.size() > 0){
					Fvirtualwallet fvirtualwallet1 = fvirtualwallets1.get(0);
			       if(fvirtualwallet1.getFtotal()< total_count){
			    	   jsonObject.accumulate("success", false) ;
			   		   jsonObject.accumulate("massage","您可用的"+fvirtualcointype.getfShortName()+"不足") ;
			   		return jsonObject.toString() ;
			       }	
			       
			      advertisementService.saveSell(fotcAdvertisement,fvirtualwallet1);
	
				}
				
			}else{
				
				 advertisementService.saveBuy(fotcAdvertisement);	
			}
	
		}catch(Exception e){
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("massage", "系统异常，操作失败");
			return jsonObject.toString() ;
		}
		jsonObject.accumulate("success",  true) ;
		jsonObject.accumulate("massage","广告发布成功") ;
		return jsonObject.toString() ;
	}
	/**
	 * 查询所有出售的广告列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping("/advertisement/buyList")
	public ModelAndView buyList(HttpServletRequest request)throws Exception{
		ModelAndView modelAndView = new ModelAndView("front/newagent/customerbuylist") ;
		if(this.isMobile(request)) {
 	    	modelAndView.setViewName("mobile/newagent/customerbuylist") ;
 	    }
		int currentPage = 1;
		int symbol = 0;
		String orderField = request.getParameter("orderField");
		List<Fvirtualcointype> fcoins = this.virtualCoinService.list(0,10," where fstatus=1 and fisotc=1",true);
		//List<Fvirtualcointype> fcoins =  this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE, 1);
		
		int numPerPage=10;
		if(request.getParameter("currentPage") != null){
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		if(request.getParameter("numPerPage") != null){
			numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
		}
		if(request.getParameter("symbol") != null){
			symbol = Integer.parseInt(request.getParameter("symbol"));
		}
		Fvirtualcointype fcoin = this.virtualCoinService.findById(symbol);
		if (symbol==0&&fcoin==null&&fcoins!=null&&fcoins.size()>0){
			symbol = fcoins.get(0).getFid();
		}
		
		//获取在线用户
		/*HttpSession session = getSession(request) ;
		ServletContext application = session.getServletContext();
        List<Integer> onlineUserList = (List<Integer>) application.getAttribute("onlineUserList");*/
		List<Integer> onlineUserList = this.frontUserService.getOTCOnlineUserList();
		
		//根据域名获取OTC商户
		List<Integer> otcMerchantList = getOtcMerchantListByDomain(request);
		if(null != onlineUserList && onlineUserList.size() > 0 ) {
			// 求交集，当前域名不存在有效OTC商户
			if(otcMerchantList != null && otcMerchantList.size() > 0) {
				onlineUserList.retainAll(otcMerchantList);
			}else {
				onlineUserList = null;
			}
		}
		
		if(null == onlineUserList || onlineUserList.size() == 0) {
			modelAndView.addObject("pagin", "");
			if(GetSession(request)!=null){
				modelAndView.addObject("isMerchant",GetSession(request).isFismerchant());	
			}else{
				modelAndView.addObject("isMerchant",true);	
			}
			modelAndView.addObject("list", new ArrayList<FotcAdvertisement>());
			modelAndView.addObject("totalCount", 0) ;	
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("currentPage", currentPage);
			modelAndView.addObject("menuflag", "agentlist");
			modelAndView.addObject("symbol", symbol);
			modelAndView.addObject("fcoins", fcoins);
			return modelAndView;
		}
		
		StringBuffer userList = new StringBuffer() ;
		for (Integer userId : onlineUserList) {
			if(userList.length()>0){
				userList.append(",") ;
			}
			userList.append(userId) ;
		}
		
		
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and ad_type=1 and status=0 and repertory_count>0 and fvirtualcointype.fid="+symbol + "and user.fid in ("+userList.toString()+")" );
//		filter.append(" where 1=1 and ad_type=1 and status=0 and repertory_count>0 and fvirtualcointype.fid="+symbol );
		if(orderField != null && orderField.trim().length() >0){
			filter.append(" order by "+orderField+"\n");
		}else{
			filter.append("  order by rand() \n");
		}
		int totalCount = this.adminService.getAllCount("FotcAdvertisement", filter.toString());
		int totalPage = totalCount/numPerPage +(totalCount%numPerPage==0?0:1) ;
		String pagin = PaginUtil.generatePagin(totalPage, currentPage,  "/advertisement/buyList.html?symbol="+symbol+"&") ;
		List<FotcAdvertisement> list = this.advertisementService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		for (FotcAdvertisement ad : list) {
			//查询承兑商绑定的支付方式
			List<FotcUserPaytype> paytypeList = otcuserpaytypeService.findAllPayType("fuser.fid", ad.getUser().getFid());
			ad.setPaytypeList(paytypeList);
			
			String respTimeStr = otcorderService.queryAcceptAvgResptime(ad.getUser().getFid());
			ad.setRespTimeStr(respTimeStr);
		}
		Collections.shuffle(list); // 随机排序
		
		modelAndView.addObject("pagin", pagin);
		if(GetSession(request)!=null){
			modelAndView.addObject("isMerchant",GetSession(request).isFismerchant());	
		}else{
			modelAndView.addObject("isMerchant",true);	
		}
		modelAndView.addObject("list", list);
		modelAndView.addObject("totalCount", totalCount) ;	
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("menuflag", "agentlist");
		modelAndView.addObject("symbol", symbol);
		modelAndView.addObject("fcoins", fcoins);
		return modelAndView;
	}
	
	/**
	 * 查询所有求购的广告列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/advertisement/sellList")
	public ModelAndView sellList(HttpServletRequest request)throws Exception{
		ModelAndView modelAndView = new ModelAndView("front/newagent/customerselllist") ;
		if(this.isMobile(request)) {
 	    	modelAndView.setViewName("mobile/newagent/customerselllist") ;
 	    }
		
		int currentPage = 1;
		int symbol = 0;
		String orderField = request.getParameter("orderField");

		int numPerPage=10;
		if(request.getParameter("currentPage") != null){
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if(request.getParameter("symbol") != null){
			symbol = Integer.parseInt(request.getParameter("symbol"));
		}
		if(request.getParameter("numPerPage") != null){
			numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
		}
		Fvirtualcointype fcoin = this.virtualCoinService.findById(symbol);
		
		
		List<Fvirtualcointype> fcoins = this.virtualCoinService.list(0,10," where fstatus=1 and fisotc=1",true);
		//List<Fvirtualcointype> fcoins =  this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE, 1);
		if (symbol==0&&fcoin==null&&fcoins!=null&&fcoins.size()>0){
			symbol = fcoins.get(0).getFid();
		}

		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		//获取在线用户
		/*HttpSession session = getSession(request) ;
		ServletContext application = session.getServletContext();
        List<Integer> onlineUserList = (List<Integer>) application.getAttribute("onlineUserList");*/
		List<Integer> onlineUserList = this.frontUserService.getOTCOnlineUserList();
		
		//根据域名获取OTC商户
		List<Integer> otcMerchantList = getOtcMerchantListByDomain(request);
		if(null != onlineUserList && onlineUserList.size() > 0 ) {
			// 求交集，当前域名不存在有效OTC商户
			if(otcMerchantList != null && otcMerchantList.size() > 0) {
				onlineUserList.retainAll(otcMerchantList);
			}else {
				onlineUserList = null;
			}
		}
		
		if(null == onlineUserList || onlineUserList.size() == 0) {
			modelAndView.addObject("pagin", "");
			if(GetSession(request)!=null){
				modelAndView.addObject("isMerchant",GetSession(request).isFismerchant());	
			}else{
				modelAndView.addObject("isMerchant",true);	
			}
			modelAndView.addObject("list", new ArrayList<FotcAdvertisement>());
			modelAndView.addObject("totalCount", 0) ;	
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("currentPage", currentPage);
			modelAndView.addObject("menuflag", "buyerlist");
			modelAndView.addObject("symbol", symbol);
			modelAndView.addObject("fcoins", fcoins);
			return modelAndView;
		}
				
		StringBuffer userList = new StringBuffer() ;
		for (Integer userId : onlineUserList) {
			if(userList.length()>0){
				userList.append(",") ;
			}
			userList.append(userId) ;
		}
		
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and ad_type=2 and status=0  and repertory_count>0 and  fvirtualcointype.fid="+symbol+ " and user.fid in ("+userList.toString()+")");
//		filter.append(" where 1=1 and ad_type=2 and status=0  and repertory_count>0 and  fvirtualcointype.fid="+symbol);
		if(orderField != null && orderField.trim().length() >0){
			filter.append(" order by "+orderField+"\n");
		}else{
			filter.append("  order by rand() \n");
		}
		List<FotcAdvertisement> list = this.advertisementService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		for (FotcAdvertisement ad : list) {
			//查询承兑商绑定的支付方式
			List<FotcUserPaytype> paytypeList = otcuserpaytypeService.findAllPayType("fuser.fid", ad.getUser().getFid());
			ad.setPaytypeList(paytypeList);
			
			String respTimeStr = otcorderService.queryAcceptAvgResptime(ad.getUser().getFid());
			ad.setRespTimeStr(respTimeStr);
		}
		Collections.shuffle(list); // 随机排序
		
		int totalCount = this.adminService.getAllCount("FotcAdvertisement", filter.toString());		
		int totalPage = totalCount/numPerPage +(totalCount%numPerPage==0?0:1) ;
		String pagin = PaginUtil.generatePagin(totalPage, currentPage,  "/advertisement/sellList.html?symbol="+symbol+"&") ;
		modelAndView.addObject("pagin", pagin);
		modelAndView.addObject("list", list);
		modelAndView.addObject("totalCount", totalCount) ;	
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("menuflag", "buyerlist");
		modelAndView.addObject("symbol", symbol);
		modelAndView.addObject("fcoins", fcoins);
		if(GetSession(request)!=null){
			modelAndView.addObject("isMerchant",GetSession(request).isFismerchant());	
		}else{
			modelAndView.addObject("isMerchant",true);	
		}

		return modelAndView;
	}
	/**
	 * 查询自己发布的广告列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/advertisement/advertisementMine")
	public ModelAndView advertisementMine(HttpServletRequest request)throws Exception{
		ModelAndView modelAndView = new ModelAndView("front/advertisement/mineList") ;
		
		int currentPage = 1;
		int numPerPage=15;
		if(this.isMobile(request)){
			numPerPage = maxResults;
		}
		String orderField = request.getParameter("orderField");
		if(request.getParameter("currentPage") != null){
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		if(request.getParameter("numPerPage") != null){
			numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and user.fid="+GetSession(request).getFid());
		if(orderField != null && orderField.trim().length() >0){
			filter.append(" order by "+orderField+"\n");
		}else{
			filter.append(" ORDER BY status asc,update_time desc \n");
		}
		List<FotcAdvertisement> list = this.advertisementService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		int totalCount = this.adminService.getAllCount("FotcAdvertisement", filter.toString());
		int totalPage = totalCount/numPerPage +(totalCount%numPerPage==0?0:1) ;
		String pagin = PaginUtil.generatePagin(totalPage, currentPage,  "/advertisement/advertisementMine.html?") ;
		modelAndView.addObject("pagin", pagin);
		modelAndView.addObject("list", list);
		modelAndView.addObject("totalCount", totalCount) ;	
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("menuflag", "advertisementMine");
		
		if(this.isMobile(request)) {
			modelAndView.addObject("totalPage", totalPage) ;
			modelAndView.setViewName("mobile/advertisement/mineList") ;
			if(currentPage>1)//ajax 加载
			{
				modelAndView.setViewName("mobile/advertisement/mineList_ajax") ;
			}
 	    }
		return modelAndView;
	}
	
	
	// 广告下架
	@ResponseBody
	@RequestMapping(value = "/advertisement/doDismount")
	public String doDismount(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		int id = Integer.valueOf(request.getParameter("id"));
		try{
			List<FotcOrder> fotcorder = this.otcorderService.findByProperty1(
					"fotcAdvertisement.id", id);
			if (fotcorder!= null && fotcorder.size() > 0) {
				jsonObject.accumulate("success", false);
				jsonObject.accumulate("massage", "该广告有订单正在交易中");
				return jsonObject.toString();
				
			}
			jsonObject = advertisementService.updateAdvertisementDown(id);
		}catch(Exception e){
			jsonObject.accumulate("success", false);
			jsonObject.accumulate("massage", "系统异常，操作失败");
		}
		return jsonObject.toString();
	}
	
	
	
	
	public static void main(String[] args) {
		List<String> list1 = new ArrayList<String>();
		list1.add("A");
		list1.add("B");
		list1.add("C");
		List<String> list2 = new ArrayList<String>();
		list2.add("C");
		list2.add("B");
		list2.add("D");
		// 交集
		list1.retainAll(list2);
		
		
		System.out.println(list1);
	}
	
}
