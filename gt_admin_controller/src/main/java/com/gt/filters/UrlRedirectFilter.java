package com.gt.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gt.entity.Fuser;

/**
 * 请求重定向
 * **/
public class UrlRedirectFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI().toLowerCase().trim();
		// System.out.println(req.getRequestURL().toString());

		// 不接受任何jsp请求
		if (uri.endsWith(".jsp")) {
			return;
		}

		// 只拦截.html结尾的请求
		if (!uri.endsWith(".html")) {
			chain.doFilter(request, response);
			return;
		}

		if (uri.startsWith("/index.html")// 首页
				|| uri.startsWith("/app/article.html")
				|| uri.startsWith("/appApi.html".toLowerCase())
				|| uri.startsWith("/appApi2.html".toLowerCase())
				|| uri.startsWith("/quickpay/qrpay_result.html")// app apiiiiii
				|| uri.startsWith("/appapi.html")// app apiiiiii
				|| uri.startsWith("/qqLogin")// qq
				|| uri.startsWith("/link/wx/callback")// qq
				|| uri.startsWith("/link/qq/call")// qq
				|| uri.startsWith("/error/")// error
				|| uri.startsWith("/api/")// api
				|| uri.startsWith("/data/ticker.html")
				|| uri.startsWith("/user/sendfindpasswordmsg")// api
				|| uri.startsWith("/user/sendregmsg")// api
				|| uri.startsWith("/json/findpassword")// api
				|| uri.startsWith("/line/period-btcdefault.html")
				|| uri.startsWith("/data/depth-btcdefault.html")
				|| uri.startsWith("/data/stock-btcdefault.html")
				|| uri.startsWith("/index_chart.html")
				|| uri.startsWith("/user/login")// 登陆
				|| uri.startsWith("/user/logout")// 退出
				|| uri.startsWith("/user/reg")// 注册
				|| uri.startsWith("/app/reg.html")// 注册
				|| uri.startsWith("/app/suc.html")// 注册
				|| uri.startsWith("/real/")// 行情
				|| uri.startsWith("/market")// 行情
				|| uri.startsWith("/trademarket.html")
				|| uri.startsWith("/kline/")// 行情
				|| uri.startsWith("/json.html")// 行情
				|| uri.startsWith("/json/")// 行情
				|| uri.startsWith("/validate/")// 邮件激活,找回密码
				|| uri.startsWith("/about/")// 文章管理
				|| (uri.startsWith("/trademarket"))
				|| uri.startsWith("/service/")// 文章管理
				|| uri.startsWith("/dowload/index.html")// 文章管理
				|| uri.startsWith("/api_doc.html")// 文章管理
				|| (uri.startsWith("/crowd/index"))
				// ||uri.startsWith("/trade/coin.html")//文章管理
				// ||uri.startsWith("/trade/entrustlog.html")//文章管理
				// ||uri.startsWith("/trade/entrustinfo.html")//文章管理
				// ||uri.startsWith("/trade/entrustinfo.html")//文章管理
				// ||uri.startsWith("/appcenter/index")//文章管理

				|| uri.startsWith("/user/sendMailCode".toLowerCase())// 注册邮件
				|| uri.startsWith("/user/sendMsg".toLowerCase())// 注册短信
				|| uri.startsWith("/new_user/register") // 后台新用户注册
		) {
			chain.doFilter(request, response);
			return;
		} else {

			if (uri.startsWith("/buluo718admin/")) {
				// 后台
				if (uri.startsWith("/buluo718admin/login_btc718.html")
						|| uri.startsWith("/buluo718admin/submitlogin_btc718.html")
						|| uri.startsWith("/buluo718admin/appapi3.html")
						|| req.getSession().getAttribute("login_admin") != null) {
					chain.doFilter(request, response);
				} else {
					resp.sendRedirect("/");
				}
				return;
			} else {

				Object login_user = req.getSession().getAttribute("login_user");
				if (login_user == null) {
					resp.sendRedirect("/user/login.html?forwardUrl="
							+ req.getRequestURI().trim());
					return;
				} else {
					if (((Fuser) login_user).getFpostRealValidate()) {
						// 提交身份认证信息了
						chain.doFilter(request, response);
						return;
					} else {
						if (uri.startsWith("/user/realCertification.html"
								.toLowerCase())
								|| uri.startsWith("/trade/")
								|| uri.startsWith("/common/upload.html")
								|| uri.startsWith("/user/validateidentity.html")) {
							chain.doFilter(request, response);
						} else {
							resp.sendRedirect("/user/realCertification.html");
						}
						return;
					}
				}

			}
		}

	}
}
