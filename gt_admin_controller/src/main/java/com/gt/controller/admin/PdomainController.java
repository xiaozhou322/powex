package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.PCoinTypeStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fuser;
import com.gt.entity.Pdomain;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.UserService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPdomainService;
import com.gt.util.Utils;

/**
 * 项目方域名admin控制器
 * @author zhouyong
 *
 */
@Controller
public class PdomainController extends BaseAdminController{

		//每页显示多少条数据
		private int numPerPage = Utils.getNumPerPage();
		
		@Autowired
		private FrontPdomainService pdomainService;
		@Autowired
		private AdminService adminService ;
		@Autowired
		private FrontConstantMapService frontConstantMapService;
		@Autowired
		private UserService userService;
		
		/**
		 * 查询待审核的域名列表
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pdomainAuditList")
		public ModelAndView pdomainAuditList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/pdomainAuditList") ;
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
			
			/*if(keyWord != null && keyWord.trim().length() >0){
				filter.append("and fTitle like '%"+keyWord+"%' \n");
				modelAndView.addObject("keywords", keyWord);
			}
			if(request.getParameter("ftype") != null){
				filter.append("and ftype='"+request.getParameter("ftype")+"' \n");
				modelAndView.addObject("ftype", request.getParameter("ftype"));
			}else{
				modelAndView.addObject("ftype", "");
			}*/
			filter.append("and auditStatus=101 \n");
			
			if(orderField != null && orderField.trim().length() >0){
				filter.append("order by "+orderField+"\n");
			}else{
				filter.append("order by createTime \n");
			}
			
			if(orderDirection != null && orderDirection.trim().length() >0){
				filter.append(orderDirection+"\n");
			}else{
				filter.append("desc \n");
			}
			
			List<Pdomain> list = this.pdomainService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("pdomainList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "pdomainList");
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pdomain", filter+""));
			return modelAndView ;
		}
		
		
		
		/**
		 * 查询已审核的域名列表
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pdomainList")
		public ModelAndView pdomainList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/pdomainList") ;
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
			
			/*if(keyWord != null && keyWord.trim().length() >0){
				filter.append("and fTitle like '%"+keyWord+"%' \n");
				modelAndView.addObject("keywords", keyWord);
			}
			if(request.getParameter("ftype") != null){
				filter.append("and ftype='"+request.getParameter("ftype")+"' \n");
				modelAndView.addObject("ftype", request.getParameter("ftype"));
			}else{
				modelAndView.addObject("ftype", "");
			}*/
			filter.append("and auditStatus!=101 \n");
			
			if(orderField != null && orderField.trim().length() >0){
				filter.append("order by "+orderField+"\n");
			}else{
				filter.append("order by createTime \n");
			}
			
			if(orderDirection != null && orderDirection.trim().length() >0){
				filter.append(orderDirection+"\n");
			}else{
				filter.append("desc \n");
			}
			
			List<Pdomain> list = this.pdomainService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("pdomainList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "pdomainList");
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pdomain", filter+""));
			return modelAndView ;
		}
		
		/**
		 * 
		* @Description: 根据id获取域名详情 
		* @author Ryan
		* @param @param id
		* @param @return
		* @param @throws Exception  
		* @return ModelAndView
		* @throws
		 */
		@RequestMapping("/buluo718admin/pdomainDetail")
		public ModelAndView findPdomainById(@RequestParam(required=true)int pid,
				@RequestParam(required=true)int type) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			
			Pdomain pdomain = pdomainService.findById(pid);
			
			modelAndView.addObject("pdomain", pdomain);
			modelAndView.addObject("type", type);
			modelAndView.setViewName("ssadmin/project/pdomainAuditDetail");
			return modelAndView;
		}
		
		
		/**
		 * 审核域名信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pdomainAudit")
		public ModelAndView pdomainAudit (HttpServletRequest request) throws Exception{
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			int status = Integer.parseInt(request.getParameter("auditStatus"));
			Integer pdomainId = Integer.parseInt(request.getParameter("pdomainId"));
			
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			
			try {
				
				Pdomain pdomain = pdomainService.findById(pdomainId);
				pdomain.setAuditStatus(status);
				pdomain.setStatus(0);
				pdomain.setAuditTime(Utils.getTimestamp());
				
				this.pdomainService.update(pdomain);
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "网络异常网");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("callbackType", "closeCurrent");
			modelAndView.addObject("message", "审核成功");

			return modelAndView;
		}
		
		
		/**
		 * 
		* @Description: 根据id获取域名详情 
		* @author Ryan
		* @param @param id
		* @param @return
		* @param @throws Exception  
		* @return ModelAndView
		* @throws
		 */
		@RequestMapping("/buluo718admin/setProportions")
		public ModelAndView setProportions(@RequestParam(required=true)int pid) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			
			Pdomain pdomain = pdomainService.findById(pid);
			
			modelAndView.addObject("pdomain", pdomain);
			modelAndView.setViewName("ssadmin/project/pdomainSetProportions");
			return modelAndView;
		}
		
		/**
		 * 设置分成比例
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pdomainSetProportions")
		public ModelAndView pdomainSetProportions (HttpServletRequest request,
				@RequestParam(required=true)int pdomainId,
				@RequestParam(required=true)Double proportions) throws Exception{
			
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
			
			//分成比例不能超过30%，不能小于0
			if(proportions>0.3 || proportions<0) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "分成比例不能超过30%也不能小于0");
				return modelAndView;
			}
			
			
			try {
				
				Pdomain pdomain = pdomainService.findById(pdomainId);
				pdomain.setProportions(proportions);
				pdomain.setStatus(1);    //1：正常  2：禁用
				pdomain.setUpdateTime(Utils.getTimestamp());
				
				this.pdomainService.update(pdomain);
				
				//刷新项目方域名缓存
				this.reloadPdomain();
				
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "网络异常网");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("callbackType", "closeCurrent");
			modelAndView.addObject("message", "设置成功");

			return modelAndView;
		}
		
		
		@RequestMapping("/buluo718admin/otcUserLookup")
		public ModelAndView userLookup(HttpServletRequest request) throws Exception {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/project/otcUserLookup");
			// 当前页
			int currentPage = 1;
			// 搜索关键字
			String keyWord = request.getParameter("keywords");

			if (request.getParameter("pageNum") != null) {
				currentPage = Integer.parseInt(request.getParameter("pageNum"));
			}
			StringBuffer filter = new StringBuffer();
			filter.append("where 1=1 \n");
			filter.append("and fstatus=1 \n");
			filter.append("and fismerchant=1 \n");
			
			if (keyWord != null && keyWord.trim().length() > 0) {
				try {
					int fid = Integer.parseInt(keyWord);
					filter.append("and fid =" + fid + " \n");
				} catch (Exception e) {
					filter.append("and (floginName like '%" + keyWord + "%' or \n");
					filter.append("fnickName like '%" + keyWord + "%'  or \n");
					filter.append("frealName like '%" + keyWord + "%'  or \n");
					filter.append("ftelephone like '%" + keyWord + "%'  or \n");
					filter.append("femail like '%" + keyWord + "%'  or \n");
					filter.append("fidentityNo like '%" + keyWord + "%' )\n");
				}
				modelAndView.addObject("keywords", keyWord);
			}
			
			List<Fuser> list = this.userService.list(
					(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
			modelAndView.addObject("userList1", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "operationLogList");
			modelAndView.addObject("currentPage", currentPage);
			// 总数量
			modelAndView.addObject("totalCount",
					this.adminService.getAllCount("Fuser", filter + ""));
			return modelAndView;
		}
		
		
		/**
		 * 
		* @Description: 跳转到设置OTC商户页面 
		* @author Ryan
		* @param @param id
		* @param @return
		* @param @throws Exception  
		* @return ModelAndView
		* @throws
		 */
		@RequestMapping("/buluo718admin/setOtcMerchant")
		public ModelAndView setOtcMerchant(@RequestParam(required=true)int pid) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			
			Pdomain pdomain = pdomainService.findById(pid);
			
			modelAndView.addObject("pdomain", pdomain);
			modelAndView.setViewName("ssadmin/project/pdomainSetOtcMerchant");
			return modelAndView;
		}
		
		
		/**
		 * 设置设置OTC商户
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pdomainSetOtcMerchant")
		public ModelAndView pdomainSetOtcMerchant (HttpServletRequest request,
				@RequestParam(required=true)int pdomainId) throws Exception{
			
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
			
			String otcMerhantIds = request.getParameter("otcUserLookup.userId");
			try {
				Pdomain pdomain = pdomainService.findById(pdomainId);
				pdomain.setOtcMerchant(otcMerhantIds.trim());
				pdomain.setUpdateTime(Utils.getTimestamp());
				
				this.pdomainService.update(pdomain);
				
				//刷新项目方域名缓存
				this.reloadPdomain();
				
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "网络异常网");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("callbackType", "closeCurrent");
			modelAndView.addObject("message", "设置成功");

			return modelAndView;
		}
		
		//刷新项目方域名信息缓存
		private void reloadPdomain() {
			//获取所有正常状态的项目方域名
			String filter = "where status=" + PCoinTypeStatusEnum.NORMAL+" and auditStatus="+AuditStatusEnum.auditPass+" order by id asc";
			List<Pdomain> pdomainList = this.pdomainService.list(0, 0, filter, false);
			this.frontConstantMapService.put("pdomainList", pdomainList) ;
		}
}
