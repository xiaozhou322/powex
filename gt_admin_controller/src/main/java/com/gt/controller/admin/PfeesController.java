package com.gt.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.AuditStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Ffees;
import com.gt.entity.Pfees;
import com.gt.entity.Ptrademapping;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.FeeService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPfeesService;
import com.gt.service.front.FrontPtrademappingService;
import com.gt.util.DataOperationUtil;
import com.gt.util.Utils;
import com.gt.utils.redis.RedisCacheUtil;

@Controller
public class PfeesController extends BaseAdminController{
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@Autowired
	private FrontPfeesService pfeesService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontPtrademappingService ptrademappingService;
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private FeeService feesService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	/**
	 * 查询待审核的费率
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pfeesAuditList")
	public ModelAndView getPfeesAuditList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/project/pfeesAuditList") ;
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
		
		List<Pfees> list = this.pfeesService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("pfeesList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "pfeesAuditList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Pfees", filter+""));
		return modelAndView ;
	}
	
	
	/**
	 * 查询待审核的费率
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pfeesList")
	public ModelAndView getPfeesList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/project/pfeesList") ;
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
		
		List<Pfees> list = this.pfeesService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("pfeesList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "pfeesList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Pfees", filter+""));
		return modelAndView ;
	}
	
	/**
	 * 
	* @Title: findTradeMappingById  
	* @Description: 根据id获取费率详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/buluo718admin/pfeesDetail")
	public ModelAndView findPfeesById(@RequestParam(required=true)int pid,
			@RequestParam(required=true)int type) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		Pfees pfees = pfeesService.findById(pid);
		
		modelAndView.addObject("pfees", pfees);
		modelAndView.addObject("type", type);
		modelAndView.setViewName("ssadmin/project/pfeesAuditDetail");
		return modelAndView;
	}
	
	
	/**
	 * 审核费率信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buluo718admin/pfeesAudit")
	public ModelAndView pfeesAudit (HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int status = Integer.parseInt(request.getParameter("auditStatus"));
		Integer pfeesId = Integer.parseInt(request.getParameter("pfeesId"));
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		try {
			Pfees pfees = pfeesService.findById(pfeesId);
			if(pfees.getBuyFee()>0.001 || pfees.getSellFee()>0.001) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "买入或卖出手续费最高不能超过0.001");
				return modelAndView;
			}
			if(pfees.getBuyFee()<0 || pfees.getSellFee()<0) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "买入或卖出手续费不能小于0");
				return modelAndView;
			}
			
			Ptrademapping ptrademapping =ptrademappingService.findById(pfees.getTrademappingId().getId());
			if(ptrademapping.getAuditStatus()!=102) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "交易市场未审核通过，不能设置交易手续费");
				return modelAndView;
			}
			
//			if(ptrademapping.getDepositStatus()!=2) {
//				modelAndView.addObject("statusCode", 300);
//				modelAndView.addObject("message", "尚未缴纳市场保证金，不能设置交易手续费");
//				return modelAndView;
//			}
			
			if(ptrademapping.getTradeMappingId()==null || ptrademapping.getTradeMappingId()==0) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "尚未创建市场，不能设置交易手续费");
				return modelAndView;
			}
			
			//设置系统交易市场手续费设置
			double BuyFee = DataOperationUtil.add(0.001, pfees.getBuyFee());
			double SellFee = DataOperationUtil.add(0.001, pfees.getSellFee());
			double buyfeecent = DataOperationUtil.div(BuyFee, 5, 6);
			double sellfeecent = DataOperationUtil.div(SellFee, 5, 6);
			
			for(int i=0;i<5;i++) {
				double fee = DataOperationUtil.sub(SellFee, sellfeecent*i);
				double buyfee = DataOperationUtil.sub(BuyFee, buyfeecent*i);
				Ffees ffee =feesService.findFfee(ptrademapping.getTradeMappingId(), i+1);
				ffee.setFfee(fee);
				ffee.setFbuyfee(buyfee);
				feesService.updateObj(ffee);
				redisCacheUtil.setCacheObject("front:rate:"+ffee.getFtrademapping().getFid()+"_buy_"+ffee.getFlevel(), ffee.getFbuyfee()+"");
				redisCacheUtil.setCacheObject("front:rate:"+ffee.getFtrademapping().getFid()+"_sell_"+ffee.getFlevel(), ffee.getFbuyfee()+"");
			}
			pfees.setAuditStatus(status);
			pfees.setAuditTime(Utils.getTimestamp());
			
			this.pfeesService.update(pfees);
			
			//审核通过刷新项目方手续费缓存
			if(status == AuditStatusEnum.auditPass) {
				this.reloadPfees();
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
	
	
	//刷新项目方手续费缓存
	private void reloadPfees() {
		String filter = "where auditStatus = " + AuditStatusEnum.auditPass+" order by id asc";
		List<Pfees> pfeesList = this.pfeesService.list(0, 0, filter, false);
		List<Ptrademapping> ptrademappingList = new ArrayList<Ptrademapping>();
		for (Pfees pfees : pfeesList) {
			Ptrademapping ptrademapping = this.ptrademappingService.findById(pfees.getTrademappingId().getId());
			double buyFeesRate = DataOperationUtil.div(pfees.getBuyFee(), 0.001 + pfees.getBuyFee(), 6);
			double sellFeesRate = DataOperationUtil.div(pfees.getSellFee(), 0.001 + pfees.getSellFee(), 6);
			ptrademapping.setBuyFeesRate(buyFeesRate);
			ptrademapping.setSellFeesRate(sellFeesRate);
			ptrademappingList.add(ptrademapping);
		}
		this.frontConstantMapService.put("ptrademappingList", ptrademappingList);
	}
}
