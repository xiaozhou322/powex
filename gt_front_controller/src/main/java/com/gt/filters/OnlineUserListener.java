package com.gt.filters;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.gt.entity.Fuser;
import com.gt.service.front.FrontUserService;
import com.gt.utils.ApplicationContextUtil;

public class OnlineUserListener implements HttpSessionListener {
	
	protected FrontUserService frontUserService = null;

    public void sessionCreated(HttpSessionEvent event) {
    	
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();

        // 取得登录的用户名
        Fuser fuser = (Fuser) session.getAttribute("login_user");
        
        frontUserService = (FrontUserService)ApplicationContextUtil.getBean("frontUserService");

        // 从在线列表中删除用户名
        /*List onlineUserList = (List) application.getAttribute("onlineUserList");
        if(fuser!=null && onlineUserList!=null && onlineUserList.contains(Integer.valueOf(fuser.getFid()))){
             onlineUserList.remove(Integer.valueOf(fuser.getFid()));
             application.setAttribute("onlineUserList",onlineUserList);
        }*/
        if(null != fuser) {
        	//从 redis移除用户 
        	this.frontUserService.removeOTCOnlineUserMap(fuser.getFid());
        }
    }

}