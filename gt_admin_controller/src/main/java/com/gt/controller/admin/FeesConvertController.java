package com.gt.controller.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.FfeesConvert;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;

/**
 * 手续费兑换记录  admin--controller
 * @author zhouyong
 *
 */
@Controller
public class FeesConvertController extends BaseAdminController {
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontOthersService frontOthersService;	
	@Autowired
	private FrontUserService frontUserService ;
	
	private int numPerPage = Utils.getNumPerPage();
	
	
	/**
	 * 兑换记录列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/feesConvertList")
	public ModelAndView feesConvertList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/feesconvertList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String status = request.getParameter("status");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1  \n");
		
		if (StringUtils.isNotBlank(status) && !status.equals("0")) {
			filter.append(" and status =" + Integer.parseInt(status) + " \n");
			modelAndView.addObject("status", status);
		}
		
		String startDate = request.getParameter("startDate");
		if(StringUtils.isNotBlank(startDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') >= '"+startDate+"' \n");
			modelAndView.addObject("startDate", startDate);
		}
		
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(endDate)){
			filter.append(" and  DATE_FORMAT(createTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append(" order by " + orderField + "\n");
		}else{
			filter.append(" order by id \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}else{
			filter.append( " desc \n");
		}


		List<FfeesConvert> feesConvertList = this.frontOthersService.queryFfeesConvertList((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("feesConvertList", feesConvertList);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "feesConvertList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FfeesConvert", filter+""));
		return modelAndView;
	}
	
	
	/**
	 * 页面跳转
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/goFeesConvertJSP")
	public ModelAndView goPproductJSP(HttpServletRequest request) throws Exception{

		String url = request.getParameter("url");
		String pid = request.getParameter("pid");
		ModelAndView modelAndView = new ModelAndView();
		if(StringUtils.isNotBlank(pid)) {
			
			FfeesConvert feesConvert = this.frontOthersService.findFeesConvertById(Integer.valueOf(pid));
			modelAndView.addObject("feesConvert", feesConvert);
		}
		
		modelAndView.setViewName(url);
		return modelAndView;
	}
	
	
	/**
	 * 新增手续费兑换记录
	 * @param request
	 * @param projectId
	 * @param usdtAmount
	 * @param bfscPrice
	 * @param bfscAmount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/saveFeesConvert")
	public ModelAndView saveFeesConvert(HttpServletRequest request,
			@RequestParam(required=false) Integer projectId,
			@RequestParam(required=false) Double usdtAmount,
			@RequestParam(required=false) Double bfscPrice,
			@RequestParam(required=false) Double bfscAmount
			) throws Exception{
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
		String feeday = request.getParameter("feeday");
		if(feeday==null || feeday.equals("")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "交割日期必须设置");
			return modelAndView;
		}
		//TODO 数据校验
		
		Fuser fuser = frontUserService.findById(projectId);
		if(null != fuser && fuser.isFisprojecter()) {
			//保存手续费兑换记录
			FfeesConvert feesConvert = new FfeesConvert();
			feesConvert.setProjectId(fuser);
			feesConvert.setUsdtAmount(usdtAmount);
			feesConvert.setBfscPrice(bfscPrice);
			feesConvert.setBfscAmount(bfscAmount);
			feesConvert.setStatus(1);     //处理状态  1：未处理    2：已处理
			
			Date calcDay = new Date();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				calcDay = df.parse(feeday);
			}catch(Exception e) {
				calcDay = new Date();
			}
			feesConvert.setCreateTime(new Timestamp(calcDay.getTime()));
			feesConvert.setUpdateTime(Utils.getTimestamp());
			
			frontOthersService.saveFfeesConvert(feesConvert);
		} else {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "项目方不存在");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	
	/**
	 * 修改未处理的手续费兑换记录
	 * @param request
	 * @param pid
	 * @param projectId
	 * @param usdtAmount
	 * @param bfscPrice
	 * @param bfscAmount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/editFeesConvert")
	public ModelAndView editFeesConvert(HttpServletRequest request,
			@RequestParam(required=true) Integer pid,
			@RequestParam(required=false) Integer projectId,
			@RequestParam(required=false) Double usdtAmount,
			@RequestParam(required=false) Double bfscPrice,
			@RequestParam(required=false) Double bfscAmount
			) throws Exception{
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
		
		//TODO 数据校验
		String feeday = request.getParameter("feeday");
		if(feeday==null || feeday.equals("")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "交割日期必须设置");
			return modelAndView;
		}
		
		FfeesConvert feesConvert = this.frontOthersService.findFeesConvertById(Integer.valueOf(pid));
		if(null != feesConvert) {
			if(2 == feesConvert.getStatus()) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "已处理记录不允许修改");
				return modelAndView;
			}
			//保存手续费兑换记录
			feesConvert.setUsdtAmount(usdtAmount);
			feesConvert.setBfscPrice(bfscPrice);
			feesConvert.setBfscAmount(bfscAmount);
			feesConvert.setStatus(1);     //处理状态  1：未处理    2：已处理
			Date calcDay = new Date();
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				calcDay = df.parse(feeday);
			}catch(Exception e) {
				calcDay = new Date();
			}
			feesConvert.setCreateTime(new Timestamp(calcDay.getTime()));
			feesConvert.setUpdateTime(Utils.getTimestamp());
			
			frontOthersService.updateFfeesConvert(feesConvert);
		} else {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "记录不存在");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	/**
	 * 修改未处理的手续费兑换记录
	 * @param request
	 * @param pid
	 * @param projectId
	 * @param usdtAmount
	 * @param bfscPrice
	 * @param bfscAmount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/deleteFeesConvert")
	public ModelAndView deleteFeesConvert(HttpServletRequest request,
			@RequestParam(required=true) Integer uid
			) throws Exception{
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
		
		FfeesConvert feesConvert = this.frontOthersService.findFeesConvertById(Integer.valueOf(uid));
		if(null != feesConvert) {
			if(feesConvert.getStatus().intValue()==2) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "已处理的记录不能删除");
				return modelAndView;
			}
			frontOthersService.deleteFfeesConvert(feesConvert);
		} else {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "记录不存在");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
}
