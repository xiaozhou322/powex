package com.gt.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gt.entity.Fuser;
/**
 * 请求重定向
 * **/
public class UrlRedirectFilter implements Filter {
	
	public void init(FilterConfig arg0) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response ;
		
		String uri = req.getRequestURI().toLowerCase().trim() ;
//		System.out.println(req.getRequestURL().toString());

		
		//不接受任何jsp请求
		if(uri.endsWith(".jsp")){
			return ;
		}
		//只拦截.html结尾的请求
		//只拦截.html结尾的请求
		if(!uri.endsWith(".html")){
			chain.doFilter(request, response) ;
			return ;
		}
		
		///////////////////////////////////////////////////////////////////////////////
		if(	uri.startsWith("/index.html")//首页
				||uri.startsWith("/appApi.action".toLowerCase()) 
				||uri.startsWith("/market".toLowerCase()) 
				
			){
			chain.doFilter(request, response) ;
			return ;
		}else{
			
			
			if(uri.startsWith("/buluo718admin/")){
				//后台
				if(uri.startsWith("/buluo718admin/login_btc718.html")
						||uri.startsWith("/buluo718admin/submitlogin_btc718.html")
						|| req.getSession().getAttribute("login_admin")!=null){
					chain.doFilter(request, response) ;
				}else{
					resp.sendRedirect("/") ;
				}
				return ;
			}else if(uri.startsWith("/redirectPage".toLowerCase())){
				
				System.out.println(request.getParameter("pageUrl"));
				chain.doFilter(request, response) ;
				return ;
				
			}else{
				
				Object login_user = req.getSession().getAttribute("login_user") ;
				if(login_user==null){
					resp.sendRedirect("/user/login.html?forwardUrl="+req.getRequestURI().trim()) ;
					return ;
				}else{
					if( ((Fuser)login_user).getFpostRealValidate()  ){
						//提交身份认证信息了
						if (/*uri.startsWith("/account/withdrawbtc.html") || uri.startsWith("/exchange/withdrawusdt.html") 
							||*/ uri.startsWith("/account/withdrawcny.html") || uri.startsWith("/account/transfer.html")){
							if (((Fuser)login_user).isFhasImgValidate()){
								chain.doFilter(request, response) ;
							}else{
								resp.sendRedirect("/user/realCertification.html") ;
							}
						}else{
							chain.doFilter(request, response) ;
						}
						return ;
					}else{
						if(uri.startsWith("/user/realCertification.html".toLowerCase())
							||uri.startsWith("/trade/")
							||uri.startsWith("/common/upload.html")
							||uri.startsWith("/user/validateidentity.html")
							||uri.startsWith("/user/security.html")
							){
							chain.doFilter(request, response) ;
						}else{
							resp.sendRedirect("/user/realCertification.html");
						}
						return ;
					}
				}
				
			}
			
			
		}
		
	}

}
