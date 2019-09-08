package com.gt.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.AuditStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fuser;
import com.gt.entity.Pproject;
import com.gt.result.JsonResult;
import com.gt.result.ResultCode;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPprojectService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * 配置管理——————项目方controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/config")
public class FrontPprojectController extends BaseController{
	
	@Autowired
	private FrontPprojectService frontPprojectService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	/**
	 * 
	* @Title: projectSubmit  
	* @Description: 提交项目方信息 
	* @author Ryan
	* @param @param request
	* @param @param name
	* @param @param website
	* @param @param advantage
	* @param @param introduce
	* @param @param logoUrl
	* @param @return  
	* @return String
	* @throws
	 */
	/*@ResponseBody
	@RequestMapping(value="/project/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String projectSubmit(
			HttpServletRequest request,
			@RequestParam(required=true)String name,
			@RequestParam(required=true)String website,
			@RequestParam(required=true)String advantage,
			@RequestParam(required=true)String introduce,
			@RequestParam(required=true)String logoUrl){
		try {
			if(name.trim().length()>15){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "项目名称不能超过15个字!").toString();
			}
			if(advantage.trim().length()>200){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "项目亮点不能超过200个字!").toString();
			}
			if(introduce.trim().length()>500){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "项目介绍不能超过500个字!").toString();
			}
			
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			Pproject pproject = new Pproject();
			
			pproject.setProjectId(fuser);
			pproject.setName(name);
			pproject.setWebsite(website);
			pproject.setAdvantage(advantage);
			pproject.setIntroduce(introduce);
			pproject.setLogoUrl(logoUrl);
			pproject.setAuditStatus(AuditStatusEnum.waitAudit);
			pproject.setCreateTime(Utils.getTimestamp());
			pproject.setUpdateTime(pproject.getCreateTime());
			
			frontPprojectService.save(pproject);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}*/
	
	
	/**
	 * 
	* @Title: 跳转到项目方详情页面  
	* @Description: 根据项目方id查询详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/project/queryProject",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryProject(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			
			Pproject pproject = null;
			List<Pproject> pprojectList = frontPprojectService.list(0, 0, filter+"", false);
			if(null != pprojectList && pprojectList.size() > 0) {
				pproject = pprojectList.get(0);
			}
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			String jsonStr = JSONObject.fromObject(pproject, jsonConfig).toString();
            
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 
	* @Title: findProjectById  
	* @Description: 根据项目方id查询详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/project/detail",produces=JsonEncode,method = { RequestMethod.GET })
	public String findProjectById(@RequestParam(required=true)int id) throws Exception{
		try {
			Pproject pproject = frontPprojectService.findById(id);
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			String jsonStr = JSONObject.fromObject(pproject, jsonConfig).toString();
            
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: projectUpdate  
	* @Description: 更新 
	* @author Ryan
	* @param @param request
	* @param @param id
	* @param @param name
	* @param @param website
	* @param @param advantage
	* @param @param introduce
	* @param @param logoUrl
	* @param @return  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/project/update",produces=JsonEncode,method = { RequestMethod.POST })
	public String projectUpdate(
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true)String name,
			@RequestParam(required=true)String website,
			@RequestParam(required=true)String advantage,
			@RequestParam(required=true)String introduce,
			@RequestParam(required=true)String logoUrl){
		try {
			
			if(!Utils.isUrl(website)) {
				return new JsonResult(ResultCode.PARAMS_ERROR, "网站地址不合法").toString();
			}
			
			if(name.trim().length()>15){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "项目名称不能超过15个字!").toString();
			}
			if(advantage.trim().length()>200){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "项目亮点不能超过200个字!").toString();
			}
			if(introduce.trim().length()>500){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "项目介绍不能超过500个字!").toString();
			}
			Pproject pproject = frontPprojectService.findById(id);
			pproject.setName(name);
			pproject.setWebsite(website);
			pproject.setAdvantage(advantage);
			pproject.setIntroduce(introduce);
			pproject.setLogoUrl(logoUrl);
			pproject.setUpdateTime(Utils.getTimestamp());

			frontPprojectService.update(pproject);
			
			//刷新项目方信息缓存
			this.reloadPproject();
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	//刷新项目方信息缓存
	private void reloadPproject() {
		//获取所有项目方信息
		String filter = "where 1 = 1 order by id asc";
		List<Pproject> pprojectList = this.frontPprojectService.list(0, 0, filter, false);
		this.frontConstantMapService.put("pprojectList", pprojectList) ;
	}
}
