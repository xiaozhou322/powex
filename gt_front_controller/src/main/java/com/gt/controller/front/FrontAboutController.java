package com.gt.controller.front;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gt.comm.KeyValues;
import com.gt.controller.BaseController;
import com.gt.entity.Fabout;
import com.gt.entity.Pdomain;
import com.gt.service.admin.AboutService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontPsystemconfigService;
import com.gt.util.ConstantKeys;

@Controller
public class FrontAboutController extends BaseController {

	@Autowired
	private FrontOthersService frontOthersService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private AboutService aboutService;
	@Autowired
	private FrontPsystemconfigService frontPsystemconfigService;

	@RequestMapping("/about/index")
	public ModelAndView index(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int id)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String lcal = RequestContextUtils.getLocale(request).toString();
		Fabout fabout = this.frontOthersService.findFabout(id);
		if (fabout != null) {
			if (lcal.equals("zh_CN")) {
				fabout.setFtype(fabout.getFtype_cn());
				fabout.setFcontent(fabout.getFcontent_cn());
				fabout.setFtitle(fabout.getFtitle_cn());
			}
			modelAndView.addObject("fabout", fabout);
		}

		List<KeyValues> abouts = new ArrayList<KeyValues>();

		String helpkey = "helpType";
		if (lcal.equals("zh_CN")) {
			helpkey = helpkey + "_CN";
		}

		String[] args = this.frontConstantMapService.get(helpkey).toString().split("#");
		for (int i = 0; i < args.length; i++) {
			KeyValues keyValues = new KeyValues();
			keyValues.setKey(i);
			keyValues.setName(args[i]);
			String filter = "where ftype='" + args[i] + "'";
			if (lcal.equals("zh_CN")) {
				filter = "where ftype_cn='" + args[i] + "'";
			}
			List<Fabout> curabout = this.aboutService.list(0, 0, filter, false);
			if (lcal.equals("zh_CN")) {
				for (Fabout fabt : curabout) {
					fabt.setFcontent(fabt.getFcontent_cn());
					fabt.setFtitle(fabt.getFtitle_cn());
					fabt.setFtype(fabt.getFtype_cn());
				}
			}
			keyValues.setValue(curabout);
			abouts.add(keyValues);
		}
		modelAndView.addObject("abouts", abouts);

		modelAndView.setViewName("front/about/index");

		if (this.isMobile(request)) {
			modelAndView.setViewName("mobile/about/index");
		}

		return modelAndView;
	}
/*
	@ResponseBody
	@RequestMapping(value="/about/index1",produces={JsonEncode})
	public String index1(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int id)
			throws Exception {
		JSONObject jsonobject = new JSONObject();
		String lcal = RequestContextUtils.getLocale(request).toString();
		Fabout fabout = this.frontOthersService.findFabout(id);
		if (fabout != null) {
			if (lcal.equals("zh_CN")) {
				fabout.setFtype(fabout.getFtype_cn());
				fabout.setFcontent(fabout.getFcontent_cn());
				fabout.setFtitle(fabout.getFtitle_cn());
			}
			jsonobject.accumulate("fabout", JSONObject.fromObject(fabout));
		}

		JSONArray abouts = new JSONArray();

		String helpkey = "helpType";
		if (lcal.equals("zh_CN")) {
			helpkey = helpkey + "_CN";
		}

		String[] args = this.frontConstantMapService.get(helpkey).toString()
				.split("#");
		for (int i = 0; i < args.length; i++) {
			JSONObject keyValues = new JSONObject();
			keyValues.accumulate("key",i);
			keyValues.accumulate("name",args[i]);
			String filter = "where ftype='" + args[i] + "'";
			if (lcal.equals("zh_CN")) {
				filter = "where ftype_cn='" + args[i] + "'";
			}
			List<Fabout> curabout = this.aboutService.list(0, 0, filter, false);
			JSONArray jsoncurabout = new JSONArray();
			if (lcal.equals("zh_CN")) {
				for (Fabout fabt : curabout) {
					fabt.setFcontent(fabt.getFcontent_cn());
					fabt.setFtitle(fabt.getFtitle_cn());
					fabt.setFtype(fabt.getFtype_cn());
					jsoncurabout.add(JSONObject.fromObject(fabt));
				}
			}
			keyValues.accumulate("value",jsoncurabout);
			abouts.add(keyValues);
		}
		jsonobject.accumulate("abouts", abouts);

		
		return jsonobject.toString();
	}*/
	@RequestMapping("/dowload/index")
	public ModelAndView dowload(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("front/dowload/index");

		if (this.isMobile(request)) {
			modelAndView.setViewName("mobile/dowload/index");
		}
		return modelAndView;
	}
	
	/**
	 * APP下载
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download/index")
	public ModelAndView downloadApp(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		//获取域名
		Pdomain pdomain = getReqDomain(request);
		
		String androidDownloadUrl = "https://www.powex.pro/static/app/powex.pro.apk";
		
		Object mainAndroidDownloadUrl = frontConstantMapService.get("android_download_url");
		if(null != mainAndroidDownloadUrl) {
			androidDownloadUrl = (String)mainAndroidDownloadUrl;
		}
		
		if(null != pdomain) {
			//获取项目方系统配置
			String filter = " where projectId.fid = " + pdomain.getProjectId().getFid();
			Map<String, Object> systemconfigMap = frontPsystemconfigService.findAllMap(filter);
			
			//解析项目方配置的下载地址
			Object domainAndroidDownloadUrl = systemconfigMap.get(ConstantKeys.P_Android_download_url);
			if(null != domainAndroidDownloadUrl) {
				androidDownloadUrl = (String)domainAndroidDownloadUrl;
			}
		}
		modelAndView.addObject("androidDownloadUrl", androidDownloadUrl);
		modelAndView.setViewName("front/download/index");
		
		if (this.isMobile(request)) {
			modelAndView.setViewName("mobile/download/index");
		}
		return modelAndView;
	}
}
