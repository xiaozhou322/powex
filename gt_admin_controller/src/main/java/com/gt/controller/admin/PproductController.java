package com.gt.controller.admin;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.PCoinTypeStatusEnum;
import com.gt.Enum.ProductPeriodEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pproduct;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontPproductService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.DateUtil;
import com.gt.util.Utils;

/**
 * 产品admin控制器
 * @author zhouyong
 *
 */
@Controller
public class PproductController extends BaseAdminController{
		//每页显示多少条数据
		private int numPerPage = Utils.getNumPerPage();
		
		@Autowired
		private FrontPproductService pproductService;
		@Autowired
		private VirtualCoinService virtualCoinService;
		@Autowired
		private AdminService adminService ;
		@Autowired
		private FrontUserService frontUserService ;
		@Autowired
		private FvirtualWalletService virtualWalletService;	
		
		
		
		@RequestMapping("/buluo718admin/saveProduct")
		public ModelAndView saveProduct(HttpServletRequest request,
				@RequestParam(required=false) String name,
				@RequestParam(required=false) String shortName,
				@RequestParam(required=false) String convertRatio,
				@RequestParam(required=false) String convertRatioExpire,
				@RequestParam(required=false) String startDate,
				@RequestParam(required=false) Integer period,
				@RequestParam(required=false) Integer convertCointype,
				@RequestParam(required=false) Double pushTotal,
				@RequestParam(required=false) Double maxTotalAmount,
				@RequestParam(required=false) Double minTimeAmount
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
			
			Fvirtualcointype fvirtualcointype = virtualCoinService.findById(convertCointype);
			
			//保存产品信息
			Pproduct product = new Pproduct();
			product.setName(name);
			product.setShortName(shortName);
			product.setConvertRatio(convertRatio);
			product.setConvertRatioExpire(convertRatioExpire);
			product.setConvertCointype(fvirtualcointype);
			product.setStartDate(new Timestamp(DateUtil.strToDate(startDate).getTime()));
			product.setPeriod(period);
			product.setPushTotal(pushTotal);
			product.setMaxTotalAmount(maxTotalAmount);
			product.setMinTimeAmount(minTimeAmount);
			//TODO 待定
			product.setProAvailableAmount(0);
			product.setProFrozenAmount(0);
			product.setConvertAvailableAmount(pushTotal);
			product.setConvertFrozenAmount(0);
			product.setAuditStatus(AuditStatusEnum.waitAudit);
			product.setCreateTime(Utils.getTimestamp());
			product.setUpdateTime(Utils.getTimestamp());

			pproductService.save(product);
			
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","保存成功");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
		
		/**
		 * 查询待审核的产品
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pproductAuditList")
		public ModelAndView getPproductAuditList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/pproductAuditList") ;
			//当前页
			int currentPage = 1;
			//搜索关键字
			String shortName = request.getParameter("shortName");
			String orderField = request.getParameter("orderField");
			String orderDirection = request.getParameter("orderDirection");
			if(request.getParameter("pageNum") != null){
				currentPage = Integer.parseInt(request.getParameter("pageNum"));
			}
			StringBuffer filter = new StringBuffer();
			filter.append("where 1=1 \n");
			if(shortName != null && shortName.trim().length() >0){
				filter.append("and shortName = '"+shortName+"' \n");
				modelAndView.addObject("shortName", shortName);
			}
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
			
			List<Pproduct> list = this.pproductService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("pproductList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "pproductAuditList");
			modelAndView.addObject("currentPage", currentPage);
			modelAndView.addObject("periodType", ProductPeriodEnum.getAll());
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pproduct", filter+""));
			return modelAndView ;
		}
		
		
		/**
		 * 查询审核过的产品
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pproductList")
		public ModelAndView getPproductList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/pproductList") ;
			//当前页
			int currentPage = 1;
			//搜索关键字
			String shortName = request.getParameter("shortName");
			String orderField = request.getParameter("orderField");
			String orderDirection = request.getParameter("orderDirection");
			if(request.getParameter("pageNum") != null){
				currentPage = Integer.parseInt(request.getParameter("pageNum"));
			}
			StringBuffer filter = new StringBuffer();
			filter.append("where 1=1 \n");
			if(shortName != null && shortName.trim().length() >0){
				filter.append("and shortName = '"+shortName+"' \n");
				modelAndView.addObject("shortName", shortName);
			}
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
			
			List<Pproduct> list = this.pproductService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("pproductList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "pproductList");
			modelAndView.addObject("currentPage", currentPage);
			modelAndView.addObject("periodType", ProductPeriodEnum.getAll());
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pproduct", filter+""));
			return modelAndView ;
		}
		
		
		/**
		 * 页面跳转
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/goPproductJSP")
		public ModelAndView goPproductJSP(HttpServletRequest request) throws Exception{

			String url = request.getParameter("url");
			String pid = request.getParameter("pid");
			String type = request.getParameter("type");
			ModelAndView modelAndView = new ModelAndView();
			if(StringUtils.isNotBlank(pid)) {
				
				Pproduct pproduct = this.pproductService.findById(Integer.valueOf(pid));
				modelAndView.addObject("pproduct", pproduct);
			}
			if(StringUtils.isNotBlank(type)) {
				modelAndView.addObject("type", Integer.parseInt(type));
			}
			
			//获取虚拟币列表
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE,1);
			modelAndView.addObject("fvirtualcointypes", fvirtualcointypes);
			
			modelAndView.addObject("periodType", ProductPeriodEnum.getAll());
			modelAndView.setViewName(url);
			return modelAndView;
		}
		
		
		/**
		 * 审核产品信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pproductAudit")
		public ModelAndView pproductAudit (HttpServletRequest request) throws Exception{
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			int status = Integer.parseInt(request.getParameter("auditStatus"));
			Integer productId = Integer.parseInt(request.getParameter("productId"));

			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			
			try {
				Pproduct product = this.pproductService.findById(productId);
				product.setAuditStatus(status);
				product.setAuditTime(Utils.getTimestamp());
				
				if(AuditStatusEnum.auditPass == status) {   //审核通过
					
					//TODO 若为项目方创建，则修改查询项目方钱包余额。判断是否余额充足，若充足则锁仓
					
					Fvirtualcointype fvirtualcointype = new Fvirtualcointype();
					fvirtualcointype.setFname(product.getName());
					fvirtualcointype.setfShortName(product.getShortName());
					fvirtualcointype.setFaddTime(Utils.getTimestamp());
					fvirtualcointype.setFstatus(PCoinTypeStatusEnum.PAUSE);
					fvirtualcointype.setFtype(CoinTypeEnum.COIN_VALUE);
					fvirtualcointype.setFprojectId(0);
					
					this.pproductService.updateAudit(product, fvirtualcointype, 1);
					
				} else {     //审核失败
					this.pproductService.update(product);
				}
			} catch (Exception e) {
				e.printStackTrace();
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "网络异常");
				return modelAndView;
			}
			
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("callbackType", "closeCurrent");
			modelAndView.addObject("message", "审核成功");

			return modelAndView;
		}
		
		
		/**
		 * 币种拨付
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/saveApproCoinLogs")
		public ModelAndView saveApproCoinLogs (HttpServletRequest request,
				@RequestParam(required=true) Integer productId,
				@RequestParam(required=true) Double amount) throws Exception{
			
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
				int userId = Integer.parseInt(request.getParameter("userLookup.id"));
				
				Pproduct product = this.pproductService.findById(productId);
				
				Fuser fuser = this.frontUserService.findById(userId);
				
				if(!fuser.isFisprojecter()) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "用户不是项目方账户，不能拨付");
					return modelAndView;
				}
				
				//获取用户ABOT钱包
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(userId, product.getCoinType().getFid()) ;
				if(null == fvirtualwallet) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "用户"+product.getCoinType().getfShortName()+"钱包不存在");
					return modelAndView;
				}
				
				virtualWalletService.updateApproCoinLogs(amount, fvirtualwallet, product, fuser);
				
			} catch (Exception e) {
				e.printStackTrace();
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "网络异常");
				return modelAndView;
			}
			
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("callbackType", "closeCurrent");
			modelAndView.addObject("message", "操作成功");

			return modelAndView;
		}
}
