package com.gt.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.comm.ParamArray;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fwebbaseinfo;
import com.gt.service.admin.WebBaseInfoService;
import com.gt.service.front.FrontConstantMapService;

@Controller
public class WebBaseInfoController extends BaseAdminController {
	@Autowired
	private WebBaseInfoService webBaseInfoService ;
	/*@Autowired
	private ConstantMap constantMap ;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	@RequestMapping("/buluo718admin/webBaseInfoList")
	public ModelAndView index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/webBaseInfo") ;
		List<Fwebbaseinfo> webBaseList = this.webBaseInfoService.findAll();
		if(webBaseList.size() >0){
			modelAndView.addObject("webBaseInfo",(Fwebbaseinfo)webBaseList.get(0));
		}
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/saveOrUpdateWebInfo")
	public ModelAndView saveOrUpdateWebInfo(HttpServletRequest request,
			ParamArray param) throws Exception{
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
		
		Fwebbaseinfo fwebbaseinfo = param.getFwebbaseinfo();
		Fwebbaseinfo newInfo = null;
		if(request.getParameter("fwebbaseinfo.fid") != null){
			int fid = Integer.parseInt(request.getParameter("fwebbaseinfo.fid"));
			newInfo = this.webBaseInfoService.findById(fid);
			newInfo.setFbeianInfo(fwebbaseinfo.getFbeianInfo());
			newInfo.setFcopyRights(fwebbaseinfo.getFcopyRights());
			newInfo.setFdescription(fwebbaseinfo.getFdescription());
			newInfo.setFkeywords(fwebbaseinfo.getFkeywords());
			newInfo.setFsystemMail(fwebbaseinfo.getFsystemMail());
			newInfo.setFtitle(fwebbaseinfo.getFtitle());
			newInfo.setFtitle_cn(fwebbaseinfo.getFtitle_cn());
			newInfo.setFkeywords_cn(fwebbaseinfo.getFkeywords_cn());
			newInfo.setFdescription_cn(fwebbaseinfo.getFdescription_cn());
			this.webBaseInfoService.updateObj(newInfo);
		}else{
			newInfo = new Fwebbaseinfo();
			newInfo.setFbeianInfo(fwebbaseinfo.getFbeianInfo());
			newInfo.setFcopyRights(fwebbaseinfo.getFcopyRights());
			newInfo.setFdescription(fwebbaseinfo.getFdescription());
			newInfo.setFkeywords(fwebbaseinfo.getFkeywords());
			newInfo.setFsystemMail(fwebbaseinfo.getFsystemMail());
			newInfo.setFtitle(fwebbaseinfo.getFtitle());
			newInfo.setFtitle_cn(fwebbaseinfo.getFtitle_cn());
			newInfo.setFkeywords_cn(fwebbaseinfo.getFkeywords_cn());
			newInfo.setFdescription_cn(fwebbaseinfo.getFdescription_cn());
			this.webBaseInfoService.saveObj(newInfo);
		}
		
		//constantMap.put("webinfo", fwebbaseinfo);
		frontConstantMapService.put("webinfo", fwebbaseinfo);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		
		return modelAndView ;
	}

}
