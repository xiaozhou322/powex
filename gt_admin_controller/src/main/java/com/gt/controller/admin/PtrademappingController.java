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
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Ptrademapping;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontPtrademappingService;
import com.gt.util.Utils;

/**
 * 交易市场admin控制器
 * @author zhouyong
 *
 */
@Controller
public class PtrademappingController extends BaseAdminController{
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@Autowired
	private FrontPtrademappingService ptrademappingService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private TradeMappingService tradeMappingService;
	
	
	/**
	 * 查询待审核的交易市场
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/ptrademappingAuditList")
	public ModelAndView getPtrademappingAuditList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/project/ptrademappingAuditList") ;
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
		
		List<Ptrademapping> list = this.ptrademappingService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("ptrademappingList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "ptrademappingList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ptrademapping", filter+""));
		return modelAndView ;
	}
	
	
	/**
	 * 查询待审核的交易市场
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/ptrademappingList")
	public ModelAndView getPtrademappingList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/project/ptrademappingList") ;
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
		
		List<Ptrademapping> list = this.ptrademappingService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("ptrademappingList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "ptrademappingList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ptrademapping", filter+""));
		return modelAndView ;
	}
	
	/**
	 * 
	* @Title: findTradeMappingById  
	* @Description: 根据id获取市场详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/buluo718admin/ptrademappingDetail")
	public ModelAndView findTradeMappingById(@RequestParam(required=true)int pid,
			@RequestParam(required=true)int type) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		Ptrademapping ptrademapping = ptrademappingService.findById(pid);
		
		modelAndView.addObject("ptrademapping", ptrademapping);
		modelAndView.addObject("type", type);
		modelAndView.setViewName("ssadmin/project/ptrademappingAuditDetail");
		return modelAndView;
	}
	
	
	/**
	 * 审核交易市场信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/ptrademappingAudit")
	public ModelAndView ptrademappingAudit (HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int status = Integer.parseInt(request.getParameter("auditStatus"));
		Integer ptrademappingId = Integer.parseInt(request.getParameter("ptrademappingId"));

		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		Ptrademapping ptrademapping = ptrademappingService.findById(ptrademappingId);
		ptrademapping.setAuditStatus(status);
		ptrademapping.setAuditTime(Utils.getTimestamp());
		try {
			Fvirtualcointype fvirtualcointype = virtualCoinService.findById(ptrademapping.getTradeCurrencyType().getCoinId());
			
			if(AuditStatusEnum.auditPass == status) {   //审核通过
				if(null != ptrademapping.getTradeMappingId()) {
					Ftrademapping ftrademapping = tradeMappingService.findById(ptrademapping.getTradeMappingId());
					ftrademapping.setFprojectId(ptrademapping.getProjectId().getFid());
					ftrademapping.setFcount1(ptrademapping.getUnitPriceDecimal());
					ftrademapping.setFcount2(ptrademapping.getUnitPriceDecimal());
					ftrademapping.setFmaxBuyAmount(ptrademapping.getMaxEntrustMoney());
					ftrademapping.setFminBuyAmount(ptrademapping.getMinEntrustMoney());
					ftrademapping.setFmaxBuyCount(ptrademapping.getMaxEntrustQty());
					ftrademapping.setFminBuyCount(ptrademapping.getMinEntrustQty());
					ftrademapping.setFmaxBuyPrice(ptrademapping.getMaxEntrustPrice());
					ftrademapping.setFminBuyPrice(ptrademapping.getMinEntrustPrice());
					ftrademapping.setFprice(ptrademapping.getOpenPrice());
					ftrademapping.setFtradeTime(ptrademapping.getTradeTime());
					//ftrademapping.setFstatus(PCoinTypeStatusEnum.PAUSE);   //状态   （1：正常；2：禁用；3：风险；4：隐藏）
					
					this.ptrademappingService.updateAudit(ptrademapping, ftrademapping, 2);
				} else {
					Ftrademapping ftrademapping = new Ftrademapping();
					ftrademapping.setFprojectId(ptrademapping.getProjectId().getFid());
					ftrademapping.setFcount1(ptrademapping.getUnitPriceDecimal());
					ftrademapping.setFcount2(ptrademapping.getUnitPriceDecimal());
					ftrademapping.setFmaxBuyAmount(ptrademapping.getMaxEntrustMoney());
					ftrademapping.setFminBuyAmount(ptrademapping.getMinEntrustMoney());
					ftrademapping.setFmaxBuyCount(ptrademapping.getMaxEntrustQty());
					ftrademapping.setFminBuyCount(ptrademapping.getMinEntrustQty());
					ftrademapping.setFmaxBuyPrice(ptrademapping.getMaxEntrustPrice());
					ftrademapping.setFminBuyPrice(ptrademapping.getMinEntrustPrice());
					ftrademapping.setFprice(ptrademapping.getOpenPrice());
					ftrademapping.setFtradeTime(ptrademapping.getTradeTime());
					ftrademapping.setFvirtualcointypeByFvirtualcointype1(ptrademapping.getFrenchCurrencyType());
					ftrademapping.setFvirtualcointypeByFvirtualcointype2(fvirtualcointype);
					ftrademapping.setFstatus(PCoinTypeStatusEnum.PAUSE);   //状态   （1：正常；2：禁用；3：风险；4：隐藏）
					
					this.ptrademappingService.updateAudit(ptrademapping, ftrademapping, 1);
					
				}
			} else {     //审核失败
				this.ptrademappingService.update(ptrademapping);
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
