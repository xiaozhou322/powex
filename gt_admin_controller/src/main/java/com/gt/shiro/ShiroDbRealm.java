package com.gt.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.InitializingBean;

import com.gt.Enum.AdminStatusEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.entity.Fadmin;
import com.gt.entity.Flog;
import com.gt.entity.FroleSecurity;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.LogService;
import com.gt.util.Utils;
import com.gt.utils.ApplicationContextUtil;

public class ShiroDbRealm extends AuthorizingRealm implements InitializingBean {

	@Resource(name = "adminService")
	private AdminService adminService;
	@Resource(name = "logService")
	private LogService logService;

	// private AdminService adminService = (AdminService)ApplicationContextUtil.getBean("adminService");
	// private LogService logService = (LogService)ApplicationContextUtil.getBean("logService");

	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (SecurityUtils.getSubject().getSession().getAttribute("permissions") != null) {
			info = (SimpleAuthorizationInfo) SecurityUtils.getSubject()
					.getSession().getAttribute("permissions");
		} else {
			// 获取当前登录的用户名
			String name = (String) super.getAvailablePrincipal(principals);
			List<String> permissions = new ArrayList<String>();
			permissions.add("xxxxxxx");
			List<Fadmin> all = adminService.findByProperty("fname", name);
			if (all != null && all.size() > 0) {
				Fadmin admin = all.get(0);
				if (admin.getFrole() != null) {
					String roleName = admin.getFrole().getFname();
					// 给当前用户设置角色
					info.addRole(roleName);
					Set<FroleSecurity> set = admin.getFrole()
							.getFroleSecurities();
					for (FroleSecurity froleSecurity : set) {
						permissions.add(froleSecurity.getFsecurity().getFurl());
					}
				}
			} else {
				throw new AuthorizationException();
			}
			// 给当前用户设置权限
			info.addStringPermissions(permissions);
			SecurityUtils.getSubject().getSession().setTimeout(3600000);
			SecurityUtils.getSubject().getSession()
					.setAttribute("permissions", info);
		}
		return info;
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Fadmin fadmin = new Fadmin();
		fadmin.setFname(token.getUsername());
		fadmin.setFpassword(new String(token.getPassword()));
		List<Fadmin> all = null;
		try {
			all = this.adminService.login(fadmin);
		} catch (Exception e) {
			throw new AuthenticationException("登陆异常！");
		}
		if (all != null && all.size() > 0) {
			if (all.get(0).getFstatus() == AdminStatusEnum.FORBBIN_VALUE) {
				throw new AuthenticationException("管理员已禁用！");
			}
			SecurityUtils.getSubject().getSession().setTimeout(3600000);
			SecurityUtils.getSubject().getSession()
					.setAttribute("login_admin", all.get(0));

			Flog flog = new Flog();
			flog.setFcreateTime(Utils.getTimestamp());
			flog.setFkey1(String.valueOf(all.get(0).getFid()));
			flog.setFkey2(all.get(0).getFname());
			flog.setFkey3(token.getHost());
			flog.setFtype(LogTypeEnum.Admin_LOGIN);
			this.logService.saveObj(flog);

			return new SimpleAuthenticationInfo(all.get(0).getFname(), all.get(
					0).getFpassword(), all.get(0).getFname());
		} else {
			throw new AuthenticationException("错误的用户名或密码！");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}