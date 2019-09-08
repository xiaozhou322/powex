package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.QuestionStatusEnum;
import com.gt.Enum.QuestionTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fquestion;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontQuestionService;
import com.gt.util.Utils;

@Controller
public class QuestionController extends BaseAdminController {
	@Autowired
	private FrontQuestionService questionService ;
	@Autowired
	private AdminService adminService ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/questionList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/questionList") ;
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
		filter.append("where 1 = 1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and ftelephone like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fcreateTime \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Fquestion> list = this.questionService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("questionList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "questionList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fquestion", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/questionForAnswerList")
	public ModelAndView questionForAnswerList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/questionForAnswerList") ;
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
		filter.append("where fstatus =1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and fname like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}
		List<Fquestion> list = this.questionService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("questionList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "questionList");
		//总数量
		modelAndView.addObject("totalCount",this.questionService.list(0, 0,  filter+"", false).size());
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goQuestionJSP")
	public ModelAndView goQuestionJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fquestion question = this.questionService.findById_user(fid);
			modelAndView.addObject("fquestion", question);
		}
		Map<Integer,String> map = new HashMap<Integer,String>();
/*		map.put(QuestionTypeEnum.CNY_RECHARGE, QuestionTypeEnum.getEnumString(QuestionTypeEnum.CNY_RECHARGE));
		map.put(QuestionTypeEnum.CNY_WITHDRAW, QuestionTypeEnum.getEnumString(QuestionTypeEnum.CNY_WITHDRAW));
*/		map.put(QuestionTypeEnum.COIN_RECHARGE, QuestionTypeEnum.getEnumString(QuestionTypeEnum.COIN_RECHARGE));
		map.put(QuestionTypeEnum.COIN_WITHDRAW, QuestionTypeEnum.getEnumString(QuestionTypeEnum.COIN_WITHDRAW));
		map.put(QuestionTypeEnum.OTHERS, QuestionTypeEnum.getEnumString(QuestionTypeEnum.OTHERS));
		modelAndView.addObject("typeMap", map);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateQuestion")
	public ModelAndView updateQuestion(HttpServletRequest request) throws Exception{
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
		
		int fid = Integer.parseInt(request.getParameter("fid"));
		String fanswer = request.getParameter("fanswer");
        Fquestion question = this.questionService.findById(fid);
        question.setFanswer(fanswer);
        this.questionService.updateObj(question);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/answerQuestion")
	public ModelAndView answerQuestion(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("fid"));
		String fanswer = request.getParameter("fanswer");
        Fquestion question = this.questionService.findById(fid);
        question.setFanswer(fanswer);
        question.setFstatus(QuestionStatusEnum.SOLVED);
        question.setfSolveTime(Utils.getTimestamp());
        Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
        question.setFadmin(admin);
        this.questionService.updateObj(question);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","回复成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
