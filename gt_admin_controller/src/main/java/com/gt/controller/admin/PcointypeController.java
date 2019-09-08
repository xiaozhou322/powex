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
import com.gt.entity.Fwithdrawfees;
import com.gt.entity.Pcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.admin.WithdrawFeesService;
import com.gt.service.front.FrontPcointypeService;
import com.gt.util.Utils;

/**
 * 虚拟币种admin控制器
 * @author zhouyong
 *
 */
@Controller
public class PcointypeController extends BaseAdminController{
		//每页显示多少条数据
		private int numPerPage = Utils.getNumPerPage();
		
		@Autowired
		private FrontPcointypeService pcointypeService;
		@Autowired
		private VirtualCoinService virtualCoinService;
		@Autowired
		private WithdrawFeesService withdrawFeesService;
		@Autowired
		private AdminService adminService ;
		
		
		/**
		 * 查询待审核的币种
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pcointypeAuditList")
		public ModelAndView getPcointypeAuditList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/pcointypeAuditList") ;
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
			
			List<Pcointype> list = this.pcointypeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("pcointypeList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "pcointypeAuditList");
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pcointype", filter+""));
			return modelAndView ;
		}
		
		
		/**
		 * 查询审核过的币种
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pcointypeList")
		public ModelAndView getPcointypeList(HttpServletRequest request) throws Exception{
			ModelAndView modelAndView = new ModelAndView() ;
			modelAndView.setViewName("ssadmin/project/pcointypeList") ;
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
			
			List<Pcointype> list = this.pcointypeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("pcointypeList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("rel", "pcointypeList");
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Pcointype", filter+""));
			return modelAndView ;
		}
		
		
		/**
		 * 
		* @Title: findTradeMappingById  
		* @Description: 根据id获取币种详情 
		* @author Ryan
		* @param @param id
		* @param @return
		* @param @throws Exception  
		* @return ModelAndView
		* @throws
		 */
		@RequestMapping("/buluo718admin/pcointypeDetail")
		public ModelAndView findTradeMappingById(
				@RequestParam(required=true)int pid,
				@RequestParam(required=true)int type
				) throws Exception{
			ModelAndView modelAndView = new ModelAndView();
			
			Pcointype pcointype = pcointypeService.findById(pid);
			
			modelAndView.addObject("pcointype", pcointype);
			modelAndView.addObject("type", type);
			modelAndView.setViewName("ssadmin/project/pcointypeAuditDetail");
			return modelAndView;
		}
		
		
		/**
		 * 审核交易市场信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/buluo718admin/pcointypeAudit")
		public ModelAndView pcointypeAudit (HttpServletRequest request) throws Exception{
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			int status = Integer.parseInt(request.getParameter("auditStatus"));
			Integer pcointypeId = Integer.parseInt(request.getParameter("pcointypeId"));

			//进行谷歌验证码确认
			String msgcode = this.gAuth(request);
			if(!msgcode.equals("ok")) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", msgcode);
				return modelAndView;
			}
			//结束谷歌验证码确认
			
			try {
				Pcointype pcointype = pcointypeService.findById(pcointypeId);
				pcointype.setAuditStatus(status);
				pcointype.setAuditTime(Utils.getTimestamp());
				
				if(AuditStatusEnum.auditPass == status) {   //审核通过
					if(null != pcointype.getCoinId()) {
						Fvirtualcointype fvirtualcointype = virtualCoinService.findById(pcointype.getCoinId());
						fvirtualcointype.setFname(pcointype.getName());
						fvirtualcointype.setfShortName(pcointype.getShortName());
						fvirtualcointype.setFurl(pcointype.getLogoUrl());
						fvirtualcointype.setFaddTime(Utils.getTimestamp());
						fvirtualcointype.setFstatus(PCoinTypeStatusEnum.PAUSE);     //1：正常   2：禁用
						fvirtualcointype.setFtype(CoinTypeEnum.COIN_VALUE);
						fvirtualcointype.setFcontract(pcointype.getContractAddr());
						fvirtualcointype.setFdecimals(pcointype.getDecimals());
						fvirtualcointype.setFprojectId(pcointype.getProjectId().getFid());
						
						this.pcointypeService.updateAudit(pcointype, fvirtualcointype, 2);
						
					} else {
						Fvirtualcointype fvirtualcointype = new Fvirtualcointype();
						fvirtualcointype.setFname(pcointype.getName());
						fvirtualcointype.setfShortName(pcointype.getShortName());
						fvirtualcointype.setFurl(pcointype.getLogoUrl());
						fvirtualcointype.setFaddTime(Utils.getTimestamp());
						fvirtualcointype.setFstatus(PCoinTypeStatusEnum.PAUSE);
						fvirtualcointype.setFtype(CoinTypeEnum.COIN_VALUE);
						fvirtualcointype.setFcontract(pcointype.getContractAddr());
						fvirtualcointype.setFdecimals(pcointype.getDecimals());
						fvirtualcointype.setFprojectId(pcointype.getProjectId().getFid());
						
						this.pcointypeService.updateAudit(pcointype, fvirtualcointype, 1);
						
						
						
						Pcointype pcoin = pcointypeService.findById(pcointypeId);
						Fvirtualcointype fvcoin = virtualCoinService.findById(pcoin.getCoinId());
						
						for(int i=1;i<=6;i++){
							Fwithdrawfees fees = new Fwithdrawfees();
							fees.setFlevel(i);
							fees.setFvirtualcointype(fvcoin);
							fees.setFfee(0d);
							this.withdrawFeesService.saveObj(fees);
						}
					}
					
				} else {     //审核失败
					this.pcointypeService.update(pcointype);
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
}
