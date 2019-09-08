package com.gt.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CountLimitTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.UserService;
import com.gt.service.front.FrontValidateService;
import com.gt.util.GoogleAuth;
import com.gt.util.Utils;

@Controller
public class IndexController extends BaseAdminController {
	@Autowired
	private FrontValidateService frontValidateService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	
	

	@RequestMapping("/buluo718admin/index")
	public ModelAndView Index(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/index");
		modelAndView.addObject("dateTime", sdf1.format(new Date()));
		modelAndView.addObject("login_admin", request.getSession()
				.getAttribute("login_admin"));
		return modelAndView;
	}


	@RequestMapping("/buluo718admin/login_btc718.html")
	public ModelAndView login() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/login");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/submitLogin_btc718.html")
	public ModelAndView submitLogin(
			HttpServletRequest request,
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String google,
			@RequestParam(required = true) String vcode) throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		if (name == null || "".equals(name.trim()) || password == null
				|| "".equals(password.trim()) || vcode == null
				|| "".equals(vcode.trim()) || google == null
						|| "".equals(google.trim())) {
			modelAndView.addObject("error", "登录信息不能为空！");
			modelAndView.setViewName("/ssadmin/login");
			return modelAndView;
		} else {
			String ip = getIpAddr(request);
			int admin_limit = this.frontValidateService.getLimitCount(ip,
					CountLimitTypeEnum.AdminLogin);
			if (admin_limit <= 0) {
				modelAndView.addObject("error", "连续登陆错误30次，为安全起见，禁止登陆2小时！");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}

			if (!vcode.equalsIgnoreCase((String) getSession(request).getAttribute(
					"checkcode"))) {
				modelAndView.addObject("error", "验证码错误！");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}

			List<Fadmin> admins = this.adminService.findByProperty("fname", name);
			if(admins == null || admins.size() !=1){
				modelAndView.addObject("error", "管理员不存在！");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}else{
				Fadmin fadmin = admins.get(0) ;
				int userid = fadmin.getFuserid();
				if(userid != 0){
					//验证谷歌认证
					boolean flag = false ;
					try{
						Fuser fuser = this.userService.findById(userid);
						flag = GoogleAuth.auth(Long.parseLong(google.trim()),fuser.getFgoogleAuthenticator()) ;
					}catch(Exception e){
						e.printStackTrace() ;
						flag = false ;
					}
					if(flag==false){
						modelAndView.addObject("error", "谷歌验证码错误！");
						modelAndView.setViewName("/ssadmin/login");
						return modelAndView;
					}
				}else {
					modelAndView.addObject("error", "账号未绑定谷歌验证码，不能登录！");
					modelAndView.setViewName("/ssadmin/login");
					return modelAndView;
				}
			}
			Subject admin = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name,
					Utils.MD5(password,admins.get(0).getSalt()));
			token.setRememberMe(true);
			token.setHost(getIpAddr(request));
			try {
				admin.login(token);
			} catch (Exception e) {
				e.printStackTrace();
				token.clear();
				this.frontValidateService.updateLimitCount(ip,
						CountLimitTypeEnum.AdminLogin);
				modelAndView.addObject("error", e.getMessage());
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}
		}
		modelAndView.setViewName("redirect:/buluo718admin/index.html");
		return modelAndView;
	}

}
