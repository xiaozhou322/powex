package com.gt.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.Enum.OtcOrderTypeEnum;
import com.gt.Enum.OtcPayTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.FotcOrder;
import com.gt.entity.FotcUserPaytype;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FotcUserPaytypeService;
import com.gt.util.Utils;

@Controller
public class FotcUserPaytypeController extends BaseAdminController {
	
	private int numPerPage = Utils.getNumPerPage();
	
	@Autowired
	private FotcUserPaytypeService fotcuserpaytypeService;
	@Autowired
	private AdminService adminService ;


	/**
     * 
     * 查询OTC用户账户支付方式列表
     * @param response
     * @param re
     * @return
     * @throws Exception
     */
	@RequestMapping("/buluo718admin/otcUserPaytypeList")
	public ModelAndView otcUserPaytypeList(HttpServletResponse response,HttpServletRequest re) throws Exception{
		ModelAndView modelAndView = new ModelAndView("ssadmin/order/otcUserPaytypeList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String userId = re.getParameter("userId");
		String payType = re.getParameter("payType");
		String orderField = re.getParameter("orderField");
		String orderDirection = re.getParameter("orderDirection");
		
		if(re.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(re.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1  \n");
		
		if(userId != null && userId.trim().length() >0){
			filter.append("and  fuser.fid = "+Integer.parseInt(userId)+"  \n");
 			modelAndView.addObject("userId", userId);
		}
		if(payType != null && payType.trim().length() >0&&!"-10".equals(payType)){
			filter.append("and  payType = "+Integer.parseInt(payType)+"  \n");
 			modelAndView.addObject("payType", payType);
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by id \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		
		List<FotcUserPaytype> list = this.fotcuserpaytypeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		Map<Integer,String> payTypeMap=OtcPayTypeEnum.getAll();
		modelAndView.addObject("paytypeList", list);
		modelAndView.addObject("payTypeMap", payTypeMap);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FotcUserPaytype", filter+""));
		return modelAndView;
	}
	
	
	
	@RequestMapping(value="/buluo718admin/otcUserPaytypeDetail")
	public ModelAndView otcUserPaytypeDetail(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("ssadmin/order/otcUserPaytypeDetail") ;
		
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			FotcUserPaytype paytype = fotcuserpaytypeService.queryById(Integer.parseInt(id));
			modelAndView.addObject("paytype", paytype);
		}
		return modelAndView;
	}

}
