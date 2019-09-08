package com.gt.controller.admin;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.PprojectSettleStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fentrustminer;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.PuserProfitlogs;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.EntrustMinerService;
import com.gt.service.front.FrontPuserProfitlogsService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;

@Controller
public class UserProfitLogsController extends BaseAdminController {
	
	@Autowired
	private EntrustMinerService entrustMinerService;
	@Autowired
	private FrontPuserProfitlogsService frontPuserProfitlogsService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontUserService frontUserService;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	/**
	 * 
	* @Title: Index  
	* @Description: 查询交易奖励列表 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/buluo718admin/userProfitLogsList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/userProfitLogsList") ;
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
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fTitle like '%"+keyWord+"%' OR \n");
			filter.append("fkeyword like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		//if(request.getParameter("ftype") != null){
		if(StringUtils.isNotBlank(request.getParameter("ftype"))){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and farticletype.fid="+request.getParameter("ftype")+"\n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}
		
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
		
		List<PuserProfitlogs> puserProfitlogsList = this.frontPuserProfitlogsService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("list", puserProfitlogsList);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "puserProfitlogsList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("PuserProfitlogs", filter+""));
		return modelAndView ;
	}
	
	/**
	 * 
	* @Title: sendUserProfitReward  
	* @Description: 用户交易奖励发放 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/buluo718admin/sendUserProfitReward")
	public ModelAndView sendUserProfitReward(HttpServletRequest request) throws Exception{
		
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
		
		//获取交易奖励
		int fid = Integer.parseInt(request.getParameter("uid"));
		PuserProfitlogs puserProfitlogs = frontPuserProfitlogsService.findById(fid);
		
		Double reward = Utils.getDouble(puserProfitlogs.getRewardAmount().doubleValue(), 6);
		
		if (puserProfitlogs.getStatus()==PprojectSettleStatusEnum.HAS_SETTLE || reward<=0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "已经发放交易奖励，不允许重新发放!");
			return modelAndView;
		}
		
		if(puserProfitlogs.getUser().isFistiger()){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "操盘账户不进行交易奖励!");
			return modelAndView;
		}
		
		//根据奖励的币种进行发放
		Fvirtualwallet walletInfo = this.frontUserService.findVirtualWalletByUser(puserProfitlogs.getUser().getFid(),puserProfitlogs.getRewardCointype().getFid());
		if (walletInfo == null) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "奖励发放失败，会员钱包信息异常!");
			return modelAndView;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			//发放交易奖励到用户钱包账户
			walletInfo.setFtotal(walletInfo.getFtotal()+reward);
			walletInfo.setFlastUpdateTime(Utils.getTimestamp());
			System.out.println(puserProfitlogs.getUser().getFid());
			System.out.println(puserProfitlogs.getCointype().getFid());
			System.out.println(walletInfo.getFtotal()+reward);
			
			//修改状态为已结算
			puserProfitlogs.setStatus(PprojectSettleStatusEnum.HAS_SETTLE);
			puserProfitlogs.setUpdateTime(Utils.getTimestamp());
			
			this.frontPuserProfitlogsService.updateUserProfit(walletInfo, puserProfitlogs);
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "用户交易奖励发放成功!");
			return modelAndView ;
		}catch(Exception e){
			e.printStackTrace();
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "用户交易奖励发放失败!");
			return modelAndView ;
		}
		
	}
	
	/**
	 * 
	* @Title: sendUserProfitReward  
	* @Description: 批量发放用户交易奖励 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/buluo718admin/sendUserProfitRewardBatch")
	public ModelAndView sendUserProfitRewardBatch(HttpServletRequest request) throws Exception{
		
		
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
		
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		int err_has = 0;
		int err_exception = 0;
		int err_fail = 0;
		int err_succ =0;
		int err_nologin =0 ;
		Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<idString.length;i++){
			int fid = Integer.parseInt(idString[i]);
			PuserProfitlogs puserProfitlogs = frontPuserProfitlogsService.findById(fid);
			
			Double reward = Utils.getDouble(puserProfitlogs.getRewardAmount().doubleValue(), 6);
			
			if (puserProfitlogs.getStatus()==PprojectSettleStatusEnum.HAS_SETTLE || reward<=0){
				err_has +=1;
				continue;
			}
			
			if(puserProfitlogs.getUser().isFistiger()){
				err_nologin+=1;
				continue;
			}
			
			Fvirtualwallet walletInfo = this.frontUserService.findVirtualWalletByUser(puserProfitlogs.getUser().getFid(),puserProfitlogs.getRewardCointype().getFid());
			if (walletInfo == null) {
				err_exception+=1;
				continue;
			}
			
			try {
				//发放交易奖励到用户账户
				walletInfo.setFtotal(walletInfo.getFtotal()+reward);
				walletInfo.setFlastUpdateTime(Utils.getTimestamp());
				
				//修改状态为已结算
				puserProfitlogs.setStatus(PprojectSettleStatusEnum.HAS_SETTLE);
				puserProfitlogs.setUpdateTime(Utils.getTimestamp());
				
				this.frontPuserProfitlogsService.updateUserProfit(walletInfo, puserProfitlogs);
				err_succ+=1;
			}catch(Exception e){
				e.printStackTrace();
				err_fail+=1;
			}
		}
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重复发放："+err_has+"个，已跳过；"+"钱包异常："+err_exception+"个；"+"发放失败："+err_fail+"个；成功发放："+err_succ+"个，操盘手:"+err_nologin+"个");
		return modelAndView ;
	}
}
