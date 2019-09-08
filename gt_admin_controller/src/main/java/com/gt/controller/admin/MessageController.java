package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.MessageStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fmessage;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.MessageService;
import com.gt.service.admin.UserService;
import com.gt.util.Utils;

@Controller
public class MessageController extends BaseAdminController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/messageList")
	public ModelAndView balanceList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/messageList") ;
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
		if (!this.isSuper(request)){
			filter.append(" and freceiver.fid<>7215 \n");
		}
		if(keyWord != null && keyWord.trim().length() >0){
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and freceiver.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (freceiver.floginName like '%" + keyWord
						+ "%' OR \n");
				filter.append("freceiver.fnickName like '%" + keyWord + "%' OR \n");
				filter.append("freceiver.frealName like '%" + keyWord + "%' ) \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		List<Fmessage> list = this.messageService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("messageList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "messageList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fmessage", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goMessageJSP")
	public ModelAndView goMessageJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fmessage message = this.messageService.findById(fid);
			modelAndView.addObject("fmessage", message);
		}
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/sendMessage")
	public ModelAndView sendMessage(HttpServletRequest request) throws Exception{
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
        String uid = request.getParameter("userLookup.id");
        if(uid != null && uid.trim().length() >0){
            Fmessage message = new Fmessage();
            message.setFtitle(request.getParameter("ftitle"));
            message.setFcontent(request.getParameter("fcontent"));
            message.setFreceiver(this.userService.findById(Integer.parseInt(uid)));
            message.setFcreateTime(Utils.getTimestamp());
            message.setFstatus(MessageStatusEnum.NOREAD_VALUE);
            this.messageService.saveObj(message);
        }else{
			final String ftitle = request.getParameter("ftitle");
			final String ftitle_en = request.getParameter("ftitle_en");
			final String fcontent = request.getParameter("fcontent");
			final String fcontent_en = request.getParameter("fcontent_en");
        	new Thread(new Runnable() {
				public void run() {
					doSend(ftitle,ftitle_en,fcontent,fcontent_en);
				}
			}).start();
        }
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","发送成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	private void doSend(String ftitle ,String ftitle_en,String fcontent,String fcontent_en) {
		String filter = "where fstatus=1";
    	List<Fuser> fusers = this.userService.list(0, 0, filter, false);
    	for (Fuser fuser : fusers) {
    		 Fmessage message = new Fmessage();
             message.setFtitle(ftitle);
             message.setFcontent(fcontent);
             message.setFreceiver(fuser);
             message.setFcreateTime(Utils.getTimestamp());
             message.setFstatus(MessageStatusEnum.NOREAD_VALUE);
             this.messageService.saveObj(message);
		}
	}
}
