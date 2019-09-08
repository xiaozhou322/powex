package com.gt.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.FactivityStatusEnum;
import com.gt.Enum.FactivityTypeEnum;
import com.gt.Enum.FactivityWayEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.FactivityModel;
import com.gt.entity.LotteryAwardsModel;
import com.gt.entity.LotteryLogModel;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ArticleTypeService;
import com.gt.service.front.FrontActivityService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontLotteryAwardsService;
import com.gt.service.front.FrontLotteryLogService;
import com.gt.service.front.FrontOthersService;
import com.gt.util.Utils;

@Controller
public class ActivityController extends BaseAdminController {
	
	@Autowired
	private FrontActivityService frontActivitiesService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private ArticleTypeService articleTypeService ;
	@Autowired
	private HttpServletRequest request ;
	@Autowired
	private FrontOthersService frontOtherService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontLotteryAwardsService frontLotteryAwardsService;
	@Autowired
	private FrontLotteryLogService frontLotteryLogService;

	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/activityList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
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
			filter.append("and (name like '%"+keyWord+"%' OR \n");
			filter.append("content like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(request.getParameter("ty")!=null&&request.getParameter("ty").equals("0")){
			filter.append("and status="+0+"\n");
			modelAndView.setViewName("ssadmin/activityList") ;
		}else{
			if(request.getParameter("status") != null  &&request.getParameter("status").trim().length() >0){
				int status = Integer.parseInt(request.getParameter("status"));
				if(status != 0){
					filter.append("and status="+status+"\n");
				}
				modelAndView.addObject("status", request.getParameter("status"));
			}
			modelAndView.setViewName("ssadmin/activityList1") ;
		}
		
		
		if(request.getParameter("way") != null &&request.getParameter("way").trim().length() >0){
			int way = Integer.parseInt(request.getParameter("way"));
			if(way != 0){
				filter.append("and way="+way+"\n");
			}
			modelAndView.addObject("way", request.getParameter("way"));
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by create_time \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		Map<Integer,String> statusMap=FactivityStatusEnum.getEnumMap();
		Map<Integer,String> wayMap=FactivityWayEnum.getEnumMap();
		List<FactivityModel> list = this.frontActivitiesService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("factivityModelList", list);
		modelAndView.addObject("statusMap", statusMap);
		modelAndView.addObject("wayMap", wayMap);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "factivityModelList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FactivityModel", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goActivityJSP")
	public ModelAndView goArticleJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("id") != null){
			int fid = Integer.parseInt(request.getParameter("id"));
			FactivityModel factivityModel = this.frontActivitiesService.findById(fid);
			factivityModel.setStatus_s(FactivityStatusEnum.getEnumString(factivityModel.getStatus()));
			factivityModel.setType_s(FactivityTypeEnum.getEnumString(factivityModel.getType()));
			factivityModel.setWay_s(FactivityWayEnum.getEnumString(factivityModel.getWay()));
			modelAndView.addObject("factivityModel", factivityModel);
			List<LotteryAwardsModel> list=frontLotteryAwardsService.findByProperty("factivityModel.id", fid);
			modelAndView.addObject("list", list);
			
		}
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateActivity")
	public ModelAndView updateArticle(
			@RequestParam(required=true) int id,
			@RequestParam(required=true) int verify			
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
		FactivityModel factivityModel = this.frontActivitiesService.findById(id);
		
		if(factivityModel==null){
			modelAndView.addObject("statusCode","300");
    		modelAndView.addObject("message","活动不存在");
    		modelAndView.addObject("callbackType","closeCurrent");
    		return modelAndView;
		}
		//活动审核不通过
        if(verify==0){
        	factivityModel.setStatus(-1);
        	this.frontActivitiesService.update(factivityModel);
        }else if(verify==1){//活动审核通过
        	JSONObject jsonObject=this.frontActivitiesService.verifyActivity(id);
        	
        	modelAndView.addObject("statusCode",jsonObject.get("statusCode"));
    		modelAndView.addObject("message",jsonObject.get("message"));
    		modelAndView.addObject("callbackType","closeCurrent");
    		return modelAndView;
        }else{
        	modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "参数异常");
			return modelAndView;
        }
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}

	
	
	@RequestMapping("/buluo718admin/lotteryLogList")
	public ModelAndView lotteryLogList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/lotteryLogList") ;
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
			filter.append("and (lotteryAwardsModel.factivityModel.name like '%"+keyWord+"%' OR \n");
			filter.append("lotteryAwardsModel.factivityModel.content like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		

			if(request.getParameter("activityId") != null && request.getParameter("activityId").trim().length() >0){
				int activityId = Integer.parseInt(request.getParameter("activityId"));
				if(activityId != 0){
					filter.append("and lotteryAwardsModel.factivityModel.id="+activityId+"\n");
				}
				modelAndView.addObject("activityId", request.getParameter("activityId"));
			}
		
		
		if(request.getParameter("userId") != null && request.getParameter("userId").trim().length() >0){
			int userId = Integer.parseInt(request.getParameter("userId"));
			if(userId != 0){
				filter.append("and user.fid="+userId+"\n");
			}
			modelAndView.addObject("userId", request.getParameter("userId"));
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by create_time \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		List<LotteryLogModel> list = this.frontLotteryLogService.getlist((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("lotteryLogList", list);
		
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "lotteryLogList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("LotteryLogModel", filter+""));
		return modelAndView ;
	}
	
}
