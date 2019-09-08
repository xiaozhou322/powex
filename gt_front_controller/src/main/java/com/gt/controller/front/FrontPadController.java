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
import com.gt.entity.Pad;
import com.gt.result.JsonResult;
import com.gt.result.ResultCode;
import com.gt.service.front.FrontPadService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 公告管理controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/ad")
public class FrontPadController extends BaseController{

	@Autowired
	private FrontPadService frontPadService;
	@Autowired
	private FrontUserService frontUserService;
	
	/**
	 * 
	* @Title: coinApplicationSubmit  
	* @Description: 提交公告申请 
	* @author Ryan
	* @param @param request
	* @param @param adTittle
	* @param @param adContent
	* @param @return  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/ad/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String adApplicationSubmit (
			HttpServletRequest request,
			@RequestParam(required=true)String adTittle,
			@RequestParam(required=true)String adContent){
		try {
			if(adTittle.trim().length()>50){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "公告标题不能超过50个字!").toString();
			}
			
			if(adContent.trim().length()>65535){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "公告内容不能超过65535个字符!").toString();
			}
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			Pad pad = new Pad();
			pad.setProjectId(fuser);
			pad.setAdTittle(adTittle);
			pad.setAdContent(adContent);
			pad.setAuditStatus(AuditStatusEnum.waitAudit);
			pad.setCreateTime(Utils.getTimestamp());
			pad.setUpdateTime(pad.getCreateTime());
			
			frontPadService.save(pad);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: getAdByProjectId  
	* @Description: 根据项目方id获取公告列表 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/ad/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getAdByProjectId(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			String filter = "where projectId.fid=" + fuser.getFid();
			List<Pad> pads = frontPadService.list(0, 0, filter, false);
			
            JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr = JSONArray.fromObject(pads, jsonConfig);
			
            return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: findAdById  
	* @Description: 根据id获取公告详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/ad/detail",produces=JsonEncode,method = { RequestMethod.GET })
	public String findAdById(@RequestParam(required=true)int id) throws Exception{
		try {
			Pad pad = frontPadService.findById(id);
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONObject jsonStr = JSONObject.fromObject(pad, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/ad/update",produces=JsonEncode,method = { RequestMethod.POST })
	public String adApplicationUpdate (
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true)String adTittle,
			@RequestParam(required=true)String adContent){
		try {
			if(adTittle.trim().length()>50){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "公告标题不能超过50个字!").toString();
			}
			
			if(adContent.trim().length()>65535){
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "公告内容不能超过65535个字符!").toString();
			}
			
			Pad pad = frontPadService.findById(id);
			pad.setAdTittle(adTittle);
			pad.setAdContent(adContent);
			pad.setAuditStatus(AuditStatusEnum.waitAudit);
			pad.setUpdateTime(Utils.getTimestamp());
			
			frontPadService.update(pad);
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
}
