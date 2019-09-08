package com.gt.controller.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.SubFrozenTypeEnum;
import com.gt.Enum.SubscriptionTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fsubscription;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.SubscriptionService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.util.Utils;

@Controller
public class SubscriptionController extends BaseAdminController {
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	// 每页显示多少条数据
	private int numPerPage = 20;

	@RequestMapping("/buluo718admin/subscriptionList")
	public ModelAndView subscriptionList(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/subscriptionList");
		// 当前页
		int currentPage = 1;
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		
		if (request.getParameter("ftype1") != null) {
			int type = Integer.parseInt(request.getParameter("ftype1"));
			if (type != 0) {
				filter.append("and fvirtualcointypeCost.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype1", type);
		} else {
			modelAndView.addObject("ftype1", 0);
		}
		
		filter.append("and ftype=" + SubscriptionTypeEnum.COIN + "\n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll();
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fsubscription> list = this.subscriptionService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("subscriptionList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "subscriptionList");
		modelAndView.addObject("currentPage", currentPage);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fsubscription", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/goSubscriptionJSP")
	public ModelAndView goSubscriptionJSP(HttpServletRequest request) throws Exception {
		String url = request.getParameter("url");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fsubscription subscription = this.subscriptionService.findById(fid);
			modelAndView.addObject("subscription", subscription);
			if(subscription.getFendTime() != null){
				String e = sdf.format(subscription.getFendTime());
				request.setAttribute("e", e);
			}
			if(subscription.getFbeginTime() != null){
				String s = sdf.format(subscription.getFbeginTime());
				request.setAttribute("s", s);
			}
		}

		List<Fvirtualcointype> allType = this.virtualCoinService.findAll();
		modelAndView.addObject("allType", allType);
		
		Map<Integer,String> coins = new HashMap<Integer,String>();
		for (Fvirtualcointype fvirtualcointype : allType) {
			coins.put(fvirtualcointype.getFid().intValue(), fvirtualcointype.getFname());
		}
		modelAndView.addObject("coins", coins);
		
		Map<Integer,String> frozenType = new HashMap<Integer,String>();
		for(int i=0;i<=2;i++){
			frozenType.put(i, SubFrozenTypeEnum.getEnumString(i));
		}
		modelAndView.addObject("frozenType", frozenType);

		return modelAndView;
	}

	@RequestMapping("/buluo718admin/saveSubscription")
	public ModelAndView saveSubscription(HttpServletRequest request) throws Exception {
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int vid = Integer.parseInt(request.getParameter("vid"));
		int vid1 = Integer.parseInt(request.getParameter("vid1"));
		Fvirtualcointype vt = this.virtualCoinService.findById(vid);
		Fvirtualcointype vt1 = this.virtualCoinService.findById(vid1);
		String fisopen = request.getParameter("fisopen");
		double ftotal = Double.valueOf(request.getParameter("ftotal"));
		double fprice = Double.valueOf(request.getParameter("fprice"));
		int fbuyCount = Integer.parseInt(request.getParameter("fbuyCount"));
		int fbuyTimes = Integer.parseInt(request.getParameter("fbuyTimes"));
		String fbeginTimes = request.getParameter("fbeginTime");
		String fendTimes = request.getParameter("fendTime");
		Date beginDate = sdf.parse(fbeginTimes);
		Date endDate = sdf.parse(fendTimes);
		if (beginDate.compareTo(endDate) > 0) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "开始时间不能大于结束时间");
			return modelAndView;
		}
		Fsubscription subscription = new Fsubscription();
		subscription.setFbeginTime(Timestamp.valueOf(fbeginTimes));
		subscription.setFendTime(Timestamp.valueOf(fendTimes));
		if (fisopen != null && fisopen.trim().length() > 0) {
			subscription.setFisopen(true);
		} else {
			subscription.setFisopen(false);
		}
		subscription.setFvirtualcointype(vt);
		subscription.setFvirtualcointypeCost(vt1);
		subscription.setFbuyCount(fbuyCount);
		subscription.setFbuyTimes(fbuyTimes);
		subscription.setFprice(fprice);
		subscription.setFtotal(ftotal);
		subscription.setFcreateTime(Utils.getTimestamp());
		subscription.setFtype(SubscriptionTypeEnum.COIN);
//		subscription.setFqty(0d);
		subscription.setFtitle(request.getParameter("ftitle"));
		this.subscriptionService.saveObj(subscription);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "新增成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/deleteSubscription")
	public ModelAndView deleteSubscription(HttpServletRequest request) throws Exception {
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
		this.subscriptionService.deleteObj(fid);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "删除成功");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/updateSubscription")
	public ModelAndView updateSubscription(HttpServletRequest request) throws Exception {
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
		Fsubscription subscription = this.subscriptionService.findById(fid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int vid = Integer.parseInt(request.getParameter("vid"));
		int vid1 = Integer.parseInt(request.getParameter("vid1"));
		Fvirtualcointype vt = this.virtualCoinService.findById(vid);
		Fvirtualcointype vt1 = this.virtualCoinService.findById(vid1);
		String fisopen = request.getParameter("fisopen");
		double ftotal = Double.valueOf(request.getParameter("ftotal"));
		double fprice = Double.valueOf(request.getParameter("fprice"));
		int fbuyCount = Integer.parseInt(request.getParameter("fbuyCount"));
		int fbuyTimes = Integer.parseInt(request.getParameter("fbuyTimes"));
		String fbeginTimes = request.getParameter("fbeginTime");
		String fendTimes = request.getParameter("fendTime");
		Date beginDate = sdf.parse(fbeginTimes);
		Date endDate = sdf.parse(fendTimes);
		if (beginDate.compareTo(endDate) > 0) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "开始时间不能大于结束时间");
			return modelAndView;
		}

		subscription.setFbeginTime(Timestamp.valueOf(fbeginTimes));
		subscription.setFendTime(Timestamp.valueOf(fendTimes));
		if (fisopen != null && fisopen.trim().length() > 0) {
			subscription.setFisopen(true);
		} else {
			subscription.setFisopen(false);
		}
		subscription.setFvirtualcointypeCost(vt1);
		subscription.setFvirtualcointype(vt);
		subscription.setFbuyCount(fbuyCount);
		subscription.setFbuyTimes(fbuyTimes);
		subscription.setFprice(fprice);
		subscription.setFtotal(ftotal);
		subscription.setFtype(SubscriptionTypeEnum.COIN);
		subscription.setFtitle(request.getParameter("ftitle"));
		this.subscriptionService.updateObj(subscription);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "修改成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	
	
	@RequestMapping("/buluo718admin/subscriptionList1")
	public ModelAndView subscriptionList1(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/subscriptionList1");
		// 当前页
		int currentPage = 1;
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		
		filter.append("and ftype=" + SubscriptionTypeEnum.RMB + "\n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll();
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fsubscription> list = this.subscriptionService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("subscriptionList1", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "subscriptionList1");
		modelAndView.addObject("currentPage", currentPage);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fsubscription", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/saveSubscription1")
	public ModelAndView saveSubscription1(HttpServletRequest request) throws Exception {
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int vid = Integer.parseInt(request.getParameter("vid"));
		int vid1 = Integer.parseInt(request.getParameter("vid1"));
		Fvirtualcointype vt = this.virtualCoinService.findById(vid);
		Fvirtualcointype vt1 = this.virtualCoinService.findById(vid1);
		String fisopen = request.getParameter("fisopen");
		String fisstart = request.getParameter("fisstart");
//		String fisICO = request.getParameter("fisICO");
		double ftotal = Double.valueOf(request.getParameter("ftotal"));
		double fprice = Double.valueOf(request.getParameter("fprice"));
		int fbuyCount = Integer.parseInt(request.getParameter("fbuyCount"));
		int fbuyTimes = Integer.parseInt(request.getParameter("fbuyTimes"));
		String fbeginTimes = request.getParameter("fbeginTime");
		String fendTimes = request.getParameter("fendTime");
		Date beginDate = sdf.parse(fbeginTimes);
		Date endDate = sdf.parse(fendTimes);
		if (beginDate.compareTo(endDate) > 0) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "开始时间不能大于结束时间");
			return modelAndView;
		}
		Fsubscription subscription = new Fsubscription();
		subscription.setFbeginTime(Timestamp.valueOf(fbeginTimes));
		subscription.setFendTime(Timestamp.valueOf(fendTimes));
		if (fisopen != null && fisopen.trim().length() > 0) {
			subscription.setFisopen(true);
		} else {
			subscription.setFisopen(false);
		}
		if (fisstart != null && fisstart.trim().length() > 0) {
			subscription.setFisstart(true);
		} else {
			subscription.setFisstart(false);
		}
		subscription.setFisICO(false);
		subscription.setFprice(fprice);
		
		subscription.setFvirtualcointype(vt);
		subscription.setFvirtualcointypeCost(vt1);
		subscription.setFbuyCount(fbuyCount);
		subscription.setFbuyTimes(fbuyTimes);
		
		subscription.setFtotal(ftotal);
		subscription.setFnumber(0);
		subscription.setFcreateTime(Utils.getTimestamp());
		subscription.setFtype(SubscriptionTypeEnum.RMB);
		subscription.setFtitle(request.getParameter("ftitle"));
		subscription.setFcontent(request.getParameter("fcontent"));
		subscription.setFminbuyCount(Integer.parseInt(request.getParameter("fminbuyCount")));
		subscription.setFfrozenType(Integer.parseInt(request.getParameter("ffrozenType")));
		subscription.setFrate(Double.valueOf(request.getParameter("frate")));
		this.subscriptionService.saveObj(subscription);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "新增成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/updateSubscription1")
	public ModelAndView updateSubscription1(HttpServletRequest request) throws Exception {
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
		int vid1 = Integer.parseInt(request.getParameter("vid1"));
		Fsubscription subscription = this.subscriptionService.findById(fid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int vid = Integer.parseInt(request.getParameter("vid"));
		Fvirtualcointype vt = this.virtualCoinService.findById(vid);
		Fvirtualcointype vt1 = this.virtualCoinService.findById(vid1);
		String fisopen = request.getParameter("fisopen");
		String fisstart = request.getParameter("fisstart");
		double ftotal = Double.valueOf(request.getParameter("ftotal"));
		double fprice = Double.valueOf(request.getParameter("fprice"));
		int fbuyCount = Integer.parseInt(request.getParameter("fbuyCount"));
		int fbuyTimes = Integer.parseInt(request.getParameter("fbuyTimes"));
		String fbeginTimes = request.getParameter("fbeginTime");
		String fendTimes = request.getParameter("fendTime");
		Date beginDate = sdf.parse(fbeginTimes);
		Date endDate = sdf.parse(fendTimes);
		if (beginDate.compareTo(endDate) > 0) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "开始时间不能大于结束时间");
			return modelAndView;
		}

		subscription.setFbeginTime(Timestamp.valueOf(fbeginTimes));
		subscription.setFendTime(Timestamp.valueOf(fendTimes));
		if (fisopen != null && fisopen.trim().length() > 0) {
			subscription.setFisopen(true);
		} else {
			subscription.setFisopen(false);
		}
		if (fisstart != null && fisstart.trim().length() > 0) {
			subscription.setFisstart(true);
		} else {
			subscription.setFisstart(false);
		}
		if(subscription.isFisICO()){
			subscription.setFprice(0d);
		}else{
			subscription.setFprice(fprice);
		}
		subscription.setFvirtualcointype(vt);
		subscription.setFvirtualcointypeCost(vt1);
		subscription.setFbuyCount(fbuyCount);
		subscription.setFbuyTimes(fbuyTimes);
		
		subscription.setFtotal(ftotal);
		subscription.setFnumber(0);
		subscription.setFcontent(request.getParameter("fcontent"));
		subscription.setFtitle(request.getParameter("ftitle"));
		subscription.setFtype(SubscriptionTypeEnum.RMB);
		subscription.setFminbuyCount(Integer.parseInt(request.getParameter("fminbuyCount")));
		subscription.setFfrozenType(Integer.parseInt(request.getParameter("ffrozenType")));
		subscription.setFrate(Double.valueOf(request.getParameter("frate")));
		this.subscriptionService.updateObj(subscription);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "修改成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
}
