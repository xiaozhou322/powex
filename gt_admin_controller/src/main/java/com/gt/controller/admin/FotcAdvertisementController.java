package com.gt.controller.admin;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.OtcOrderTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.FotcAdvertisement;
import com.gt.entity.FotcOrder;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FotcAdvertisementService;
import com.gt.service.front.FotcOrderService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;
/**
 * 广告操作
 * @author admin
 *
 */
@Controller
public class FotcAdvertisementController extends BaseAdminController {

	@Autowired
	private FotcAdvertisementService advertisementService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FotcOrderService fotcorderService;
	
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping(value="/buluo718admin/advertisementList")
	public ModelAndView advertisementList(HttpServletResponse response,HttpServletRequest re) {
		ModelAndView modelAndView = new ModelAndView("ssadmin/order/advertisementList");
		//当前页
		int currentPage = 1;
		//搜索关键字
		String title = request.getParameter("title");
		
		String userId = re.getParameter("userid");
		String startDate = re.getParameter("startDate");
		String endDate = re.getParameter("endDate");
		String ad_status = re.getParameter("ad_status");
		String ad_type = re.getParameter("ad_type");
		String coin_type = re.getParameter("cur_type");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(title != null && title.trim().length() >0){
			filter.append("and (title like '%"+title+"%' OR \n");
			filter.append("intro like '%"+title+"%' ) \n");
			modelAndView.addObject("title", title);
		}
		
		if(userId != null && userId.trim().length() >0){
			filter.append("and  user.fid = "+Integer.parseInt(userId)+"  \n");
 			modelAndView.addObject("userid", userId);
		}
		if(ad_status != null && ad_status.trim().length() >0&&!"-10".equals(ad_status)){
			filter.append("and  status = "+Integer.parseInt(ad_status)+"  \n");
 			modelAndView.addObject("ad_status", ad_status);
		}

		if(request.getParameter("cur_type") != null){
			int type = Integer.parseInt(request.getParameter("cur_type"));
			if(type != 0){
				filter.append("and fvirtualcointype.fid="+type+"\n");
			}
			modelAndView.addObject("cur_type", type);
		}else{
			modelAndView.addObject("cur_type", 0);
		}
		
		if(ad_type != null && ad_type.trim().length() >0&&!"-10".equals(ad_type)){
			filter.append("and  ad_type = "+Integer.parseInt(ad_type)+"  \n");
 			modelAndView.addObject("ad_type", ad_type);
		}
		
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		if(endDate != null && endDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		List<FotcAdvertisement> list = this.advertisementService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);
		Map<Integer,String> adStatusList = new LinkedHashMap<Integer, String>();
		
		adStatusList.put(-10, "全部");
		adStatusList.put(0, "上架");
		adStatusList.put(1, "下架");
		Map<Integer,String> orderType=OtcOrderTypeEnum.getAll();
		modelAndView.addObject("adStatusList", adStatusList);
		modelAndView.addObject("orderType", orderType);
		modelAndView.addObject("advertisementList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcAdvertisement", filter+""));
		return modelAndView;
	}
	
	
	//下架
	@RequestMapping(value = "/buluo718admin/updateadvertisementStatus")
	public ModelAndView updateadvertisementStatus(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int id = Integer.valueOf(request.getParameter("id"));
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
				
		FotcAdvertisement f = advertisementService.queryById(id);
		try{
			List<FotcOrder> fotcorder = this.fotcorderService.findByProperty1(
					"fotcAdvertisement.id", id);
			if (fotcorder!= null && fotcorder.size() > 0) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "该广告有订单正在交易中");
				return modelAndView;
				
			}
			if(f.getStatus()==1){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "广告已经下架");
				return modelAndView;
			}
			jsonObject = advertisementService.updateAdvertisementDown(id);
		}catch(Exception e){
			e.printStackTrace();
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "系统异常，操作失败");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "下架成功");
 		return modelAndView;

	}
	
	 //上架
	@RequestMapping(value = "/buluo718admin/updateadvertisementStatus0")
	public ModelAndView updateadvertisementStatus0(HttpServletRequest request) throws Exception {
		int id = Integer.valueOf(request.getParameter("id"));
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
				
		FotcAdvertisement f = advertisementService.queryById(id);
		if(f.getStatus()==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "广告已经上架");
			return modelAndView;
		}
		JSONObject jsonObject = advertisementService.updateAdvertisementOnline(f);
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", jsonObject.getString("massage"));
 		return modelAndView;

	}
		
	
}
