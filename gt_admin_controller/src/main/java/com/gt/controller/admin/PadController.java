package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.entity.Pad;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ArticleTypeService;
import com.gt.service.front.FrontPadService;
import com.gt.util.Utils;

/**
 * 公告admin控制器
 * @author zhouyong
 *
 */
@Controller
public class PadController extends BaseAdminController{

		//每页显示多少条数据
		private int numPerPage = Utils.getNumPerPage();
		
		@Autowired
		private FrontPadService padService;
		@Autowired
		private AdminService adminService ;
		@Autowired
		private ArticleTypeService articleTypeService;
		
		/**
		 * 查询待审核的公告列表
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/padAuditList")
		public ModelAndView padAuditList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/padAuditList") ;
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
			
			List<Pad> list = this.padService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("padList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "padList");
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pad", filter+""));
			return modelAndView ;
		}
		
		
		
		/**
		 * 查询待审核的公告列表
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/padList")
		public ModelAndView padList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/padList") ;
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
			
			List<Pad> list = this.padService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("padList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "padList");
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pad", filter+""));
			return modelAndView ;
		}
		
		/**
		 * 
		* @Title: findTradeMappingById  
		* @Description: 根据id获取公告详情 
		* @author Ryan
		* @param @param id
		* @param @return
		* @param @throws Exception  
		* @return ModelAndView
		* @throws
		 */
		@RequestMapping("/buluo718admin/padDetail")
		public ModelAndView findPadById(@RequestParam(required=true)int pid,
				@RequestParam(required=true)int type) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			
			Pad pad = padService.findById(pid);
			
			modelAndView.addObject("pad", pad);
			modelAndView.addObject("type", type);
			modelAndView.setViewName("ssadmin/project/padAuditDetail");
			return modelAndView;
		}
		
		
		/**
		 * 审核公告信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/padAudit")
		public ModelAndView padAudit (HttpServletRequest request) throws Exception{
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			int status = Integer.parseInt(request.getParameter("auditStatus"));
			Integer padId = Integer.parseInt(request.getParameter("padId"));
			
			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			
			try {
				//TODO 公告类型待定
				int articleLookupid = 1;
				Farticletype articletype = this.articleTypeService.findById(articleLookupid);
				
				Pad pad = padService.findById(padId);
				pad.setAuditStatus(status);
				pad.setAuditTime(Utils.getTimestamp());
				
				Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
				if(102 == status) {   //审核通过
					Farticle article = new Farticle();
					article.setFarticletype(articletype);
					article.setFtitle(pad.getAdTittle());
					article.setFtitle_cn(pad.getAdTittle());
					article.setProjectId(pad.getProjectId());
					//article.setFkeyword(fKeyword);
					article.setFcontent(pad.getAdContent());
					article.setFcontent_cn(pad.getAdContent());
					article.setFlastModifyDate(Utils.getTimestamp());
					article.setFcreateDate(Utils.getTimestamp());
					article.setFadminByFcreateAdmin(admin);
					article.setFadminByFmodifyAdmin(admin);
					
					this.padService.updateAudit(pad, article);
				} else {     //审核失败
					this.padService.update(pad);
				}
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
}
