package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.LinkTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Ffriendlink;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.FriendLinkService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.Utils;

@Controller
public class QQController extends BaseAdminController {
	@Autowired
	private FriendLinkService friendLinkService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/qqList")
	public ModelAndView qqList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/qqList") ;
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
        filter.append("where ftype="+LinkTypeEnum.QQ_VALUE+" \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fname like '%"+keyWord+"%' or \n");
			filter.append("furl like '%"+keyWord+"%' ) \n");
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
		
		List<Ffriendlink> list = this.friendLinkService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("linkList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "qqList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ffriendlink", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goQQJSP")
	public ModelAndView goQQJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ffriendlink friendlink = this.friendLinkService.findById(fid);
			modelAndView.addObject("friendlink", friendlink);
		}
		Map map = new HashMap();
		map.put(LinkTypeEnum.QQ_VALUE, LinkTypeEnum.getEnumString(LinkTypeEnum.QQ_VALUE));
		modelAndView.addObject("linkTypeMap", map);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveQQ")
	public ModelAndView saveQQ(HttpServletRequest request) throws Exception{
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
		Ffriendlink link = new Ffriendlink();
		link.setFdescription(request.getParameter("link.fdescription"));
		link.setFname(request.getParameter("link.fname"));
		link.setForder(Integer.parseInt(request.getParameter("link.forder")));
		link.setFurl(request.getParameter("link.furl"));
		link.setFcreateTime(Utils.getTimestamp());
		link.setFtype(LinkTypeEnum.QQ_VALUE);
		this.friendLinkService.saveObj(link);
		
		String filter = "where ftype="+LinkTypeEnum.QQ_VALUE;
		List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
		//constantMap.put("quns", ffriendlinks) ;
		frontConstantMapService.put("quns", ffriendlinks);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteQQ")
	public ModelAndView deleteQQ(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.friendLinkService.deleteObj(fid);
		
		String filter = "where ftype="+LinkTypeEnum.QQ_VALUE;
		List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
		//constantMap.put("quns", ffriendlinks) ;
		frontConstantMapService.put("quns", ffriendlinks);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/updateQQ")
	public ModelAndView updateQQ(HttpServletRequest request) throws Exception{
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
		Ffriendlink link = this.friendLinkService.findById(Integer.parseInt(request.getParameter("link.fid")));
		link.setFdescription(request.getParameter("link.fdescription"));
		link.setFname(request.getParameter("link.fname"));
		link.setForder(Integer.parseInt(request.getParameter("link.forder")));
		link.setFurl(request.getParameter("link.furl"));
		this.friendLinkService.updateObj(link);
		
		String filter = "where ftype="+LinkTypeEnum.QQ_VALUE;
		List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
		//constantMap.put("quns", ffriendlinks) ;
		frontConstantMapService.put("quns", ffriendlinks);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
