package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.PCoinTypeStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pdepositcointype;
import com.gt.entity.Pdomain;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPdepositcointypeService;
import com.gt.util.Utils;

/**
 * 保证金币种管理admin控制器
 * @author zhouyong
 *
 */
@Controller
public class PdepositcointypeController extends BaseAdminController{

		//每页显示多少条数据
		private int numPerPage = Utils.getNumPerPage();
		
		@Autowired
		private FrontPdepositcointypeService pdepositcointypeService;
		@Autowired
		private AdminService adminService ;
		@Autowired
		private FrontConstantMapService frontConstantMapService;
		@Autowired
		private VirtualCoinService virtualCoinService;
		
		/**
		 * 查询待审核的域名列表
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pdepositcointypeList")
		public ModelAndView pdomainAuditList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/pdepositcointypeList") ;
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
			
			List<Pdepositcointype> list = this.pdepositcointypeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("pdepositcointypeList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "pdepositcointypeList");
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pdepositcointype", filter+""));
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
		@RequestMapping("/buluo718admin/pdepositcointypeDetail")
		public ModelAndView findPdepositcointypeById(@RequestParam(required=true)int pid
				) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			
			Pdepositcointype pdepositcointype = pdepositcointypeService.findById(pid);
			
			List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
			modelAndView.addObject("allType", allType);
			
			modelAndView.addObject("pdepositcointype", pdepositcointype);
			modelAndView.setViewName("ssadmin/project/pdepositcointypeDetail");
			return modelAndView;
		}
		
		
		/**
		 * 修改保证金币种信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pdepositcointypeUpdate")
		public ModelAndView pdomainSetProportions (HttpServletRequest request,
				@RequestParam(required=true)int pid,
				@RequestParam(required=false)Integer cointypeId,
				@RequestParam(required=false)Double depositLimit) throws Exception{
			
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
			
			
			try {
				Pdepositcointype pdepositcointype = pdepositcointypeService.findById(pid);
				Fvirtualcointype fvirtualcointype = virtualCoinService.findById(cointypeId);
				pdepositcointype.setCointypeId(fvirtualcointype);
				pdepositcointype.setDepositLimit(depositLimit);
				pdepositcointype.setCreateTime(Utils.getTimestamp());
				
				this.pdepositcointypeService.update(pdepositcointype);
				
				//刷新保证金信息缓存
				this.reloadPdepositcointype();
				
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
		
		
		//刷新保证金信息缓存
		private void reloadPdepositcointype() {
			//获取保证金信息
			String filter = " where 1 = 1 ";
			List<Pdepositcointype> pdepositcointypeList = this.pdepositcointypeService.findByParam(filter);
			if(null != pdepositcointypeList && pdepositcointypeList.size() > 0) {
				this.frontConstantMapService.put("pdepositcointype", pdepositcointypeList.get(0)) ;
			}
		}
}
