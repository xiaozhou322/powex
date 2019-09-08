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
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fentrust;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.EntrustService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontTradeService;
import com.gt.util.HttpUtils;
import com.gt.util.Utils;

@Controller
public class EntrustController extends BaseAdminController {
	@Autowired
	private EntrustService entrustService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FrontEntrustChangeService frontEntrustChangeService;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/entrustList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/entrustList") ;
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
			
			Boolean isNotHave = false;
			if (request.getParameter("isNotHave")!=null){
				isNotHave=true;
				modelAndView.addObject("isNotHave", "True");
			}
			
			try {
				int fid = Integer.parseInt(keyWord);
				if(isNotHave) {
					filter.append("and fuser.fid <>" + fid + " \n");
				}else {
					filter.append("and fuser.fid =" + fid + " \n");
				}
			} catch (Exception e) {
					filter.append("and (fuser.floginName like '%"+keyWord+"%' OR \n");
					filter.append("fuser.frealName like '%"+keyWord+"%' OR \n");
					filter.append("fuser.fnickName like '%"+keyWord+"%' ) \n");
				
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and ftrademapping.fvirtualcointypeByFvirtualcointype2.fid="+type+"\n");
			}
			modelAndView.addObject("ftype", type);
		}else{
			modelAndView.addObject("ftype", 0);
		}
		
		if(request.getParameter("ftype1") != null){
			int type1 = Integer.parseInt(request.getParameter("ftype1"));
			if(type1 != 0){
				filter.append("and ftrademapping.fvirtualcointypeByFvirtualcointype1.fid="+type1+"\n");
			}
			modelAndView.addObject("ftype1", type1);
		}else{
			modelAndView.addObject("ftype1", 0);
		}
		
		String status = request.getParameter("status");
		if(status != null && status.trim().length() >0){
			if(!status.equals("0")){
				filter.append("and fstatus="+status+" \n");
			}
			modelAndView.addObject("status", status);
		}else{
			modelAndView.addObject("status", 0);
		}
		
		String entype = request.getParameter("entype");
		if(entype != null && entype.trim().length() >0){
			if(!entype.equals("-1")){
				filter.append("and fentrustType="+entype+" \n");
			}
			modelAndView.addObject("entype", entype);
		}else{
			modelAndView.addObject("entype", -1);
		}
		
		try {
			String price = request.getParameter("price");
			if(price != null && price.trim().length() >0){
				double p = Double.valueOf(price);
				filter.append("and fprize >="+p+" \n");
			}
			modelAndView.addObject("price", price);
		} catch (Exception e) {
		}
		
		try {
			String price = request.getParameter("price1");
			if(price != null && price.trim().length() >0){
				double p = Double.valueOf(price);
				filter.append("and fprize <="+p+" \n");
			}
			modelAndView.addObject("price1", price);
		} catch (Exception e) {
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d %H:%i:%s') >= '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d %H:%i:%s') <= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,0);
		Fvirtualcointype btc= null;
		Fvirtualcointype eth= null;
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
			
		}
		typeMap.put(0, "全部");
		
		
		List<Fvirtualcointype> type1 = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,1);
		Map<Integer,String> typeMap1 = new HashMap<Integer,String>();
		for (Fvirtualcointype fvirtualcointype : type1) {
			typeMap1.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
			if(fvirtualcointype.getfShortName().equals("BTC")){
				btc=fvirtualcointype;
			}
			if(fvirtualcointype.getfShortName().equals("ETH")){
				eth=fvirtualcointype;
			}
		}
		typeMap1.put(0, "全部");
		typeMap.put(btc.getFid(), btc.getFname());
		typeMap.put(eth.getFid(), eth.getFname());
		modelAndView.addObject("typeMap1", typeMap1);
		modelAndView.addObject("typeMap", typeMap);
		Map<Integer,String> statusMap = new HashMap<Integer,String>();
		statusMap.put(EntrustStatusEnum.AllDeal, EntrustStatusEnum.getEnumString(EntrustStatusEnum.AllDeal));
		statusMap.put(EntrustStatusEnum.Cancel, EntrustStatusEnum.getEnumString(EntrustStatusEnum.Cancel));
		statusMap.put(EntrustStatusEnum.Going, EntrustStatusEnum.getEnumString(EntrustStatusEnum.Going));
		statusMap.put(EntrustStatusEnum.PartDeal, EntrustStatusEnum.getEnumString(EntrustStatusEnum.PartDeal));
		statusMap.put(0,"全部");
		modelAndView.addObject("statusMap", statusMap);
		
		Map<Integer,String> entypeMap = new HashMap<Integer,String>();
		entypeMap.put(EntrustTypeEnum.BUY, EntrustTypeEnum.getEnumString(EntrustTypeEnum.BUY));
		entypeMap.put(EntrustTypeEnum.SELL, EntrustTypeEnum.getEnumString(EntrustTypeEnum.SELL));
		entypeMap.put(-1,"全部");
		modelAndView.addObject("entypeMap", entypeMap);
		
		double fees = this.adminService.getSQLValue2("select sum(ffees-fleftfees) from Fentrust "+filter.toString());
		double totalAmt = this.adminService.getSQLValue2("select sum(fcount-fleftCount) from Fentrust "+filter.toString());
		double totalQty = this.adminService.getSQLValue2("select sum(fsuccessAmount) from Fentrust "+filter.toString());
		
		
		modelAndView.addObject("fees", Utils.getDouble(fees, 8));
		modelAndView.addObject("totalAmt", Utils.getDouble(totalAmt, 8));
		modelAndView.addObject("totalQty", Utils.getDouble(totalQty, 8));
		
		
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
		
		List<Fentrust> list = this.entrustService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("entrustList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "entrustList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fentrust", filter+""));
				
		return modelAndView ;
	}
	
	
	@RequestMapping("/buluo718admin/cancelEntrust")
	public ModelAndView cancelEntrust(HttpServletRequest request) throws Exception{
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
		String ids = request.getParameter("ids");
//		Map<String,String> query = new HashMap<String,String>();
//		query.put("method", "cancelEntrust");
//		query.put("params", ids);
//		HttpUtils.simpleRPC(query);
//		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
//		modelAndView.addObject("statusCode",200);
//		modelAndView.addObject("message","已发送取消请求");
//		return modelAndView;
		
		String[] idString = ids.split(",");
		for (int i=0;i<idString.length;i++) {
			Fentrust fentrust2 = this.entrustService.findById(Integer.parseInt(idString[i]));
			if(fentrust2!=null
					&&(fentrust2.getFstatus()==EntrustStatusEnum.Going || fentrust2.getFstatus()==EntrustStatusEnum.PartDeal )){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust2, fentrust2.getFuser()) ;
					flag = true ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(flag==true){
					if(fentrust2.getFentrustType()==EntrustTypeEnum.BUY){
						if(fentrust2.isFisLimit()){
							this.frontEntrustChangeService.removeEntrustLimitBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}else{
							this.frontEntrustChangeService.removeEntrustBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}
					}else{
						if(fentrust2.isFisLimit()){
							this.frontEntrustChangeService.removeEntrustLimitSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}else{
							this.frontEntrustChangeService.removeEntrustSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}
						
					}
				}
			}
		}

		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","取消成功");
		return modelAndView;
	}
	
}
