package com.gt.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pfees;
import com.gt.entity.Ptrademapping;
import com.gt.result.JsonResult;
import com.gt.result.KeyValueVo;
import com.gt.result.ResultCode;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.front.FrontPfeesService;
import com.gt.service.front.FrontPtrademappingService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * 手续费管理controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/market")
public class FrontPfeesController extends BaseController{
	
	@Autowired
	private FrontPfeesService frontPfeesService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontPtrademappingService frontPtrademappingService;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private AdminService adminService;
	
	/**
	 * 
	* @Title: feesSubmit  
	* @Description: 提交手续费申请 
	* @author Ryan
	* @param @param request
	* @param @param trademappingId
	* @param @param buyFee
	* @param @param sellFee
	* @param @return  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/fee/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String feesSubmit(
			HttpServletRequest request,
			@RequestParam(required=true)int trademappingId,
			@RequestParam(required=true)Double buyFee,
			@RequestParam(required=true)Double sellFee){
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			//校验项目方类型
			String verifyResult = this.virifyProject(fuser);
			if(StringUtils.isNotBlank(verifyResult)) {
				return verifyResult;
			}
			
			//数据校验  TODO
			if(buyFee > 0.001 || buyFee < 0 || sellFee > 0.001 || sellFee < 0){
				return new JsonResult(ResultCode.PARAMS_ERROR, "手续费不能超过1‰或小于0").toString();
			}
			
			String filter = " where projectId.fid = " + fuser.getFid() + 
							" and trademappingId.id="+trademappingId;
			int count = this.adminService.getAllCount("Pfees", filter);
			if(count > 0){
				return new JsonResult(ResultCode.FAIL, "你已提交该市场的手续费申请，无需重复提交！").toString();
			}

			Pfees pfees = new Pfees();
			pfees.setProjectId(fuser);
			pfees.setTrademappingId(frontPtrademappingService.findById(trademappingId));
			pfees.setBuyFee(buyFee);
			pfees.setSellFee(sellFee);
			pfees.setAuditStatus(AuditStatusEnum.waitAudit);
			pfees.setCreateTime(Utils.getTimestamp());
			pfees.setUpdateTime(pfees.getCreateTime());
			
			frontPfeesService.save(pfees);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: getFeesByProjectId  
	* @Description: 根据项目方id获取费率列表 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/fee/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getFeesByProjectId(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int auditStatus) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			if(auditStatus != 0) {
				filter.append(" and auditStatus = "+ auditStatus);
			}
			
			List<Pfees> pfees = frontPfeesService.list(0, 0, filter+"", false);
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr = JSONArray.fromObject(pfees, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: findFeeById  
	* @Description: 根据id获取费率详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/fee/detail",produces=JsonEncode,method = { RequestMethod.GET })
	public String findFeeById(@RequestParam(required=true)int id) throws Exception{
		try {
			Pfees pfee = frontPfeesService.findById(id);
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONObject jsonStr = JSONObject.fromObject(pfee, jsonConfig);
            
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: feesUpdate  
	* @Description: 更新 
	* @author Ryan
	* @param @param request
	* @param @param id
	* @param @param buyFee
	* @param @param sellFee
	* @param @return  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/fee/update",produces=JsonEncode,method = { RequestMethod.POST })
	public String feesUpdate(
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true)Double buyFee,
			@RequestParam(required=true)Double sellFee){
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			//校验项目方类型
			String verifyResult = this.virifyProject(fuser);
			if(StringUtils.isNotBlank(verifyResult)) {
				return verifyResult;
			}
			
			//数据校验  TODO
			if(buyFee > 0.001 || buyFee < 0 || sellFee > 0.001 || sellFee < 0){
				return new JsonResult(ResultCode.PARAMS_ERROR, "手续费不能超过1‰或小于0").toString();
			}
			
			Pfees pfees = frontPfeesService.findById(id);
			pfees.setBuyFee(buyFee);
			pfees.setSellFee(sellFee);
			pfees.setAuditStatus(AuditStatusEnum.waitAudit);
			pfees.setUpdateTime(Utils.getTimestamp());
			
			frontPfeesService.update(pfees);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 查询项目方id查询交易市场列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/queryTrademappingByProjectId",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryTradeCurrencyType(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			filter.append(" and auditStatus=" + AuditStatusEnum.auditPass);
			
			if(null != fuser) {
				filter.append(" and id not in ( select trademappingId.id from Pfees"
						+" where projectId.fid="+ fuser.getFid() +")");
			}
			
//			String filter = " where projectId.fid=" + fuser.getFid() + " and auditStatus=" + AuditStatusEnum.auditPass;
			List<Ptrademapping> ptrademappings = frontPtrademappingService.list(0, 0, filter+"", false);
			List<KeyValueVo> keyValueVoList = new ArrayList<KeyValueVo>();
			for (Ptrademapping trademapping : ptrademappings) {
				KeyValueVo keyValue = new KeyValueVo();
				keyValue.setKey(trademapping.getId());
				keyValue.setValue(trademapping.getTradeCurrencyType().getShortName()
						+"/"+trademapping.getFrenchCurrencyType().getfShortName());
				keyValueVoList.add(keyValue);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", keyValueVoList).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 根据项目方查询交易币下拉列表-----【统计专用】
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/queryCoinTypeByProjectId",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryCoinTypeByProjectId(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			String filter = " where fprojectId=" + fuser.getFid() + " and fstatus=" + VirtualCoinTypeStatusEnum.Normal;
			List<Fvirtualcointype> fcointypeList = frontVirtualCoinService.list(0, 0, filter+"", false);
			List<KeyValueVo> keyValueVoList = new ArrayList<KeyValueVo>();
			for (Fvirtualcointype cointype : fcointypeList) {
				KeyValueVo keyValue = new KeyValueVo();
				keyValue.setKey(cointype.getFid());
				keyValue.setValue(cointype.getfShortName());
				keyValueVoList.add(keyValue);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", keyValueVoList).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 查询项目方id查询交易市场列表-----【统计专用】
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/queryTrademappings",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryTrademappings(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			String filter = " where fprojectId=" + fuser.getFid() + " and fstatus=" + TrademappingStatusEnum.ACTIVE;
			List<Ftrademapping> ftrademappings = tradeMappingService.list(0, 0, filter+"", false);
			List<KeyValueVo> keyValueVoList = new ArrayList<KeyValueVo>();
			for (Ftrademapping trademapping : ftrademappings) {
				KeyValueVo keyValue = new KeyValueVo();
				keyValue.setKey(trademapping.getFid());
				keyValue.setValue(trademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()
						+"/"+trademapping.getFvirtualcointypeByFvirtualcointype1().getfShortName());
				keyValueVoList.add(keyValue);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", keyValueVoList).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
}
