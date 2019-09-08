package com.gt.controller.front;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.PCoinTypeStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.CooperateOrgModel;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pdomain;
import com.gt.entity.Psystemconfig;
import com.gt.result.JsonResult;
import com.gt.result.KeyValueVo;
import com.gt.result.MarketConfigVo;
import com.gt.result.ResultCode;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPdomainService;
import com.gt.service.front.FrontPsystemconfigService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.util.ConstantKeys;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 配置管理controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/config")
public class FrontPsystemConfigController extends BaseController{
	
	@Autowired
	private FrontPsystemconfigService frontPsystemconfigService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontPdomainService frontPdomainService;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private FtradeMappingService ftradeMappingService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	/**
	 * 
	* @Title: domainSubmit  
	* @Description: 提交域名 
	* @author Ryan
	* @param @param request
	* @param @param exclusiveDomain
	* @param @param completeDomain
	* @param @return  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/domain/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String domainSubmit(HttpServletRequest request, 
		@RequestParam(required=true)String exclusiveDomain,
		@RequestParam(required=true)String completeDomain){
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			//验证域名的合法性  TODO
			if(Utils.isNumeric(exclusiveDomain)) {
				return new JsonResult(ResultCode.PARAMS_ERROR, "域名不能为纯数字").toString();
			}
			
			
			if(!Utils.isDomain(exclusiveDomain)) {
				return new JsonResult(ResultCode.PARAMS_ERROR, "域名不合法").toString();
			}
			
			Pdomain isExistPdomain = frontPdomainService.findByProperty("completeDomain", completeDomain.toLowerCase());
			if(null != isExistPdomain) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "域名已存在").toString();
			}
			

			Pdomain pdomain = new Pdomain();
			pdomain.setProjectId(fuser);
			pdomain.setExclusiveDomain(exclusiveDomain.toLowerCase());
			pdomain.setCompleteDomain(completeDomain.toLowerCase());
			pdomain.setCreateTime(Utils.getTimestamp());
			pdomain.setAuditStatus(AuditStatusEnum.waitAudit);
			pdomain.setStatus(0);
			
			frontPdomainService.save(pdomain);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: getAdByProjectId  
	* @Description: 域名详情 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/domain/detail",produces=JsonEncode,method = { RequestMethod.GET })
	public String getAdByProjectId(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			String filter = "where projectId=" + fuser.getFid();
			List<Pdomain> pdomains = frontPdomainService.list(0, 0, filter, false);
			Pdomain pdomain = pdomains!=null && pdomains.size()>0 ? pdomains.get(0) : null;
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONObject jsonStr = JSONObject.fromObject(pdomain, jsonConfig);
            
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	
	/**
	 * 查询项目方市场配置
	 * @param request
	 * @param fvirtualcointypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/market/configList2",produces=JsonEncode,method = { RequestMethod.GET })
	public String configList2(HttpServletRequest request){
        try{
        	Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
        	
        	Fuser fuser = this.frontUserService.findById(fid) ;
        	
        	//1.查出所有正常的法币
        	String filter1 = "where ftype in (" + CoinTypeEnum.FB_COIN_VALUE + ","+ CoinTypeEnum.FB_USDT_VALUE + ") and fstatus = " + VirtualCoinTypeStatusEnum.Normal;
			List<Fvirtualcointype> fvirtualcointypes = frontVirtualCoinService.list(0, 0, filter1, false);
			
			List<KeyValueVo> fbList = new ArrayList<KeyValueVo>();
			for(Fvirtualcointype f : fvirtualcointypes){
				KeyValueVo keyValue = new KeyValueVo();
				keyValue.setKey(f.getFid());
				keyValue.setValue(f.getfShortName());
				fbList.add(keyValue);
			}
			
			List<MarketConfigVo> marketConfigVoList = new ArrayList<MarketConfigVo>(fvirtualcointypes.size());
			
			for(Fvirtualcointype f : fvirtualcointypes){
				MarketConfigVo marketConfigVo = new MarketConfigVo(); 
				Map<String,Object> marketmap = new HashMap<String,Object>();
				List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();
				
				String filter2 = "where fvirtualcointype1 = " + f.getFid() + " and fprojectId = " + fuser.getFid();
				List<Ftrademapping> ftrademappings = ftradeMappingService.list(0, 0, filter2, false);
				List<KeyValueVo> selfTradeMappingList = new ArrayList<KeyValueVo>();
				for (Ftrademapping t : ftrademappings) {
					KeyValueVo keyValue = new KeyValueVo();
					keyValue.setKey(t.getFid().toString());
					keyValue.setValue(t.getFvirtualcointypeByFvirtualcointype2().getfShortName()
							+"/"+t.getFvirtualcointypeByFvirtualcointype1().getfShortName());
					selfTradeMappingList.add(keyValue);
				}
				
				String filter3 = "where fvirtualcointype1 = " + f.getFid() + " and fprojectId != " + fuser.getFid();
				List<Ftrademapping> othersFtrademappings = ftradeMappingService.list(0, 0, filter3, false);
				List<KeyValueVo> othersTradeMappingList = new ArrayList<KeyValueVo>();
				for (Ftrademapping t : othersFtrademappings) {
					KeyValueVo keyValue = new KeyValueVo();
					keyValue.setKey(t.getFid().toString());
					keyValue.setValue(t.getFvirtualcointypeByFvirtualcointype2().getfShortName()
							+"/"+t.getFvirtualcointypeByFvirtualcointype1().getfShortName());
					othersTradeMappingList.add(keyValue);
				}
				
				//2.查出项目方开通的市场
				marketmap.put("selfTrademappingMap", selfTradeMappingList);
				//3.查出除项目方以外所有的市场
				marketmap.put("othersTrademappingMap", othersTradeMappingList);
				
				
				children.add(marketmap);
				
				marketConfigVo.setFbType(f.getfShortName());
				marketConfigVo.setFbId(f.getFid().toString());
				marketConfigVo.setChildren(children);
				
				marketConfigVoList.add(marketConfigVo);
			}
			
			
        	//4.查出项目方勾选的的市场
			String marketChecked = null;
			List<KeyValueVo> checkedList = new ArrayList<KeyValueVo>();
			Pdomain domain = frontPdomainService.findByProjectId(fuser.getFid());
			Map<String,Object> map = new HashMap<String,Object>();
			if(null != domain) {
				marketChecked = domain.getTrademappings();
				if(StringUtils.isBlank(marketChecked)) {
					for(Fvirtualcointype f : fvirtualcointypes){
						KeyValueVo keyValue = new KeyValueVo();
						keyValue.setKey(f.getFid().toString());
						keyValue.setValue("");
						checkedList.add(keyValue);
					}
					map.put("checkedList", checkedList);
				} else {
					map.put("checkedList", marketChecked);
				}
			} else {
				map.put("checkedList", checkedList);
			}
			
			map.put("marketConfigVoList", marketConfigVoList);
			map.put("fbList", fbList);
			map.put("marketChecked", marketChecked);
			
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 提交项目方市场配置
	 * @param request
	 * @param otcMerchantJson
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/market/configSubmit2",produces=JsonEncode,method = { RequestMethod.POST })
	public String marketConfigSubmit2(HttpServletRequest request, 
			@RequestParam(required=true)String trademappings){
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			Pdomain domain = frontPdomainService.findByProjectId(fuser.getFid());
			if(null == domain) {
				return new JsonResult(ResultCode.SYS_ERROR, "请先配置域名").toString();
			}
			
			domain.setTrademappings(trademappings);
			domain.setUpdateTime(Utils.getTimestamp());
			frontPdomainService.update(domain);
			
			//刷新项目方域名缓存
			this.reloadPdomain();
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	
	/**
	 * 查询项目方合作信息列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/cooperateOrg/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getCooperateOrgList(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid = " + fuser.getFid());
			filter.append(" and fkey = '" + ConstantKeys.P_CooperateOrg + "'");
			
			Psystemconfig psystemconfig = this.frontPsystemconfigService.findByProjectId(filter+"");
			
			List<CooperateOrgModel> cooperateOrgModelList = new ArrayList<CooperateOrgModel>();
			if(null == psystemconfig || StringUtils.isBlank(psystemconfig.getFvalue())) {
				return new JsonResult(ResultCode.SUCCESS, "success", cooperateOrgModelList).toString();
			} else {
				String fvalue = psystemconfig.getFvalue();
				JSONArray jsonArr = JSONArray.fromObject(fvalue);
				
				if(jsonArr.size()>0){
					// JSONArray 转 java List
			        cooperateOrgModelList = JSONArray.toList(jsonArr, new CooperateOrgModel(), new JsonConfig());
			        
			        //根据id排序  ---倒序
			        Collections.sort(cooperateOrgModelList);  //只有一个参数参数为List
				}
			}
			
			JSONArray jsonStr = JSONArray.fromObject(cooperateOrgModelList);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 保存项目方合作机构
	 * @param request
	 * @param orgName
	 * @param picUrl
	 * @param linkUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/cooperateOrg/save",produces=JsonEncode,method = { RequestMethod.POST })
	public String saveCooperateOrg(HttpServletRequest request, 
			@RequestParam(required=true)String orgName,
			@RequestParam(required=true)String picUrl,
			@RequestParam(required=true)String linkUrl){
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid = " + fuser.getFid());
			filter.append(" and fkey = '" + ConstantKeys.P_CooperateOrg + "'");
			
			Psystemconfig psystemconfig = this.frontPsystemconfigService.findByProjectId(filter+"");
			if(null == psystemconfig) {
				psystemconfig = new Psystemconfig();
				CooperateOrgModel cooperateOrgModel = new CooperateOrgModel();
				cooperateOrgModel.setId(1);
				cooperateOrgModel.setOrgName(orgName);
				cooperateOrgModel.setPicUrl(picUrl);
				cooperateOrgModel.setLinkUrl(linkUrl);
				
				psystemconfig.setProjectId(fuser);
				psystemconfig.setFkey(ConstantKeys.P_CooperateOrg);
				psystemconfig.setFvalue(JSONArray.fromObject(cooperateOrgModel).toString());
				psystemconfig.setCreateTime(Utils.getTimestamp());
				
				this.frontPsystemconfigService.save(psystemconfig);
			} else {
				String fvalue = psystemconfig.getFvalue();
				JSONArray jsonArr = JSONArray.fromObject(fvalue);
				
				if(jsonArr.size()>0){
					// JSONArray 转 java List
			        List<CooperateOrgModel> cooperateOrgModelList = JSONArray.toList(jsonArr, new CooperateOrgModel(), new JsonConfig());
			        
			        //java List 根据id倒序
			        //排序
			        Collections.sort(cooperateOrgModelList);  //只有一个参数参数为List
					
					CooperateOrgModel cooperateOrgModel = new CooperateOrgModel();
					cooperateOrgModel.setId(cooperateOrgModelList.get(0).getId()+1);
					cooperateOrgModel.setOrgName(orgName);
					cooperateOrgModel.setPicUrl(picUrl);
					cooperateOrgModel.setLinkUrl(linkUrl);
					
					cooperateOrgModelList.add(cooperateOrgModel);
					
					psystemconfig.setFkey(ConstantKeys.P_CooperateOrg);
					psystemconfig.setFvalue(JSONArray.fromObject(cooperateOrgModelList).toString());
					psystemconfig.setCreateTime(Utils.getTimestamp());
				} else {
					CooperateOrgModel cooperateOrgModel = new CooperateOrgModel();
					cooperateOrgModel.setId(1);
					cooperateOrgModel.setOrgName(orgName);
					cooperateOrgModel.setPicUrl(picUrl);
					cooperateOrgModel.setLinkUrl(linkUrl);
					
					psystemconfig.setProjectId(fuser);
					psystemconfig.setFkey(ConstantKeys.P_CooperateOrg);
					psystemconfig.setFvalue(JSONArray.fromObject(cooperateOrgModel).toString());
					psystemconfig.setCreateTime(Utils.getTimestamp());
				}
				this.frontPsystemconfigService.update(psystemconfig);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 删除项目方合作机构
	 * @param request
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/cooperateOrg/delete",produces=JsonEncode,method = { RequestMethod.POST })
	public String deleteCooperateOrg(HttpServletRequest request, 
			@RequestParam(required=true)int orgId){
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid = " + fuser.getFid());
			filter.append(" and fkey = '" + ConstantKeys.P_CooperateOrg + "'");
			
			Psystemconfig psystemconfig = this.frontPsystemconfigService.findByProjectId(filter+"");
			if(null == psystemconfig || StringUtils.isBlank(psystemconfig.getFvalue())) {
				return new JsonResult(ResultCode.SYS_ERROR, "配置不存在").toString();
			} else {
				String fvalue = psystemconfig.getFvalue();
				JSONArray jsonArr = JSONArray.fromObject(fvalue);
				
				if(jsonArr.size()>0){
					// JSONArray 转 java List
			        List<CooperateOrgModel> cooperateOrgModelList = JSONArray.toList(jsonArr, new CooperateOrgModel(), new JsonConfig());
			        
			        //List转Map
					Map<Integer, CooperateOrgModel> maps = Maps.uniqueIndex(cooperateOrgModelList, new Function<CooperateOrgModel, Integer>() {
						@Override
						public Integer apply(CooperateOrgModel cooperateOrg) {
							return cooperateOrg.getId();
						}
					});
					
					cooperateOrgModelList.remove(maps.get(orgId));
					
					psystemconfig.setFkey(ConstantKeys.P_CooperateOrg);
					//没有任何合作机构时，必须填写[]，否则首页报错
					if(cooperateOrgModelList.size() == 0) {
						psystemconfig.setFvalue("[]");
					}else {
						psystemconfig.setFvalue(JSONArray.fromObject(cooperateOrgModelList).toString());
					}
					psystemconfig.setCreateTime(Utils.getTimestamp());
					this.frontPsystemconfigService.update(psystemconfig);
				}
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	
	/**
	 * 项目方其他配置提交
	 * @param request
	 * @param weixin
	 * @param QQ
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/othersConfig/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String othersConfigtSubmit(
			HttpServletRequest request,
			@RequestParam(required=false)String weixin,
			@RequestParam(required=false)String QQ,
			@RequestParam(required=false)String email,
			@RequestParam(required=true)String template){
		try {
			if(StringUtils.isNotBlank(QQ) && !Utils.checkQQ(QQ)){
				return new JsonResult(ResultCode.FAIL, "QQ号格式不正确，请更正！").toString();
			}
			if(StringUtils.isNotBlank(email) && !Utils.checkEmail(email)){
				return new JsonResult(ResultCode.FAIL, "email格式不正确，请更正！").toString();
			}
			
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			//修改其他配置公共方法
			this.updateOthersConfig(fuser, weixin, 1);
			this.updateOthersConfig(fuser, QQ, 2);
			this.updateOthersConfig(fuser, email, 3);
			this.updateOthersConfig(fuser, template, 4);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
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
	@RequestMapping(value="/othersConfig/detail",produces=JsonEncode,method = { RequestMethod.GET })
	public String findProjectById(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			//获取项目方系统配置
			String filter = " where projectId.fid = " + fuser.getFid();
			Map<String, Object> systemconfigMap = frontPsystemconfigService.findAllMap(filter);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("systemconfigMap", systemconfigMap) ;
            
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 修改项目方其他配置公共方法
	 * @param fuser
	 * @param config
	 * @param type
	 */
	private void updateOthersConfig(Fuser fuser, String config, int type) {
		String constantKey = "";
		if(1 == type) {
			constantKey = ConstantKeys.P_WeiXin;
		} else if(2 == type) {
			constantKey = ConstantKeys.P_QQ;
		} else if(3 == type) {
			constantKey = ConstantKeys.P_Email;
		} else if(4 == type) {
			constantKey = ConstantKeys.P_template;
		}
		
		String filter = " where projectId.fid = " + fuser.getFid() +
				" and fkey = '" + constantKey + "'";
		
		Psystemconfig psystemconfig = this.frontPsystemconfigService.findByProjectId(filter+"");
		if(null == psystemconfig) {
			psystemconfig = new Psystemconfig();
			
			psystemconfig.setProjectId(fuser);
			psystemconfig.setFkey(constantKey);
			psystemconfig.setFvalue(config);
			psystemconfig.setCreateTime(Utils.getTimestamp());
			
			this.frontPsystemconfigService.save(psystemconfig);
		} else {
			psystemconfig.setFkey(constantKey);
			psystemconfig.setFvalue(config);
			psystemconfig.setCreateTime(Utils.getTimestamp());
			this.frontPsystemconfigService.update(psystemconfig);
		}
	}
	
	
	//刷新项目方域名信息缓存
	private void reloadPdomain() {
		//获取所有正常状态的项目方域名
		String filter = "where status=" + PCoinTypeStatusEnum.NORMAL+" and auditStatus="+AuditStatusEnum.auditPass+" order by id asc";
		List<Pdomain> pdomainList = this.frontPdomainService.list(0, 0, filter, false);
		this.frontConstantMapService.put("pdomainList", pdomainList) ;
	}
}
