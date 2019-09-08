package com.gt.controller.front;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gt.Enum.AuditStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fuser;
import com.gt.entity.Pcointype;
import com.gt.entity.Pproject;
import com.gt.result.JsonResult;
import com.gt.result.ResultCode;
import com.gt.service.front.FrontPcointypeService;
import com.gt.service.front.FrontPprojectService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Constant;
import com.gt.util.OSSPostObject;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * 币种管理controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/coin")
public class FrontPcoinTypeController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(FrontPcoinTypeController.class);
	@Autowired
	private FrontPcointypeService frontPcointypeService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontPprojectService frontPprojectService;
	
	/**
	 * 
	* @Title: coinApplicationSubmit  
	* @Description: 申请上币 
	* @author Ryan
	* @param @param request
	* @param @param name
	* @param @param shortName
	* @param @param logoUrl
	* @param @param pushTotal
	* @param @param pushPrice
	* @param @param currentCirculation
	* @param @param currentMarketValue
	* @param @param currentHoldNum
	* @param @param decimals
	* @param @param website
	* @param @param contractAddr
	* @param @param blockAddr
	* @param @param contractLink
	* @param @param coinUsed
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/coin/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String coinApplicationSubmit (
			HttpServletRequest request,
			@RequestParam(required=true)String name,
			@RequestParam(required=true)String shortName,
			@RequestParam(required=true)String logoUrl,
			@RequestParam(required=true)Double pushTotal,
			@RequestParam(required=true)Double pushPrice,
			@RequestParam(required=false)Double currentCirculation,
			@RequestParam(required=false)Double currentMarketValue,
			@RequestParam(required=false)Double currentHoldNum,
			@RequestParam(required=true)int decimals,
			@RequestParam(required=true)String website,
			@RequestParam(required=true)String contractAddr,
			@RequestParam(required=false)String blockAddr,
			@RequestParam(required=false)String contractLink,
			@RequestParam(required=false)String openChargeTime,
			@RequestParam(required=false)String openTradeTime,
			@RequestParam(required=false)String openWithdrawTime,
			@RequestParam(required=false)String coinUsed) throws Exception{
		
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			Fuser fuser = frontUserService.findById(fid);
			
			//校验项目方类型
			String verifyResult = this.virifyProject(fuser);
			if(StringUtils.isNotBlank(verifyResult)) {
				return verifyResult;
			}
			
			List<Pcointype> cointypeList = frontPcointypeService.findByTwoProperty("projectId.fid", fuser.getFid(), "name", name.trim());
			
			if(null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "币种名称重复。").toString();
			}
			
			if(name.trim().length() > 32){
				return new JsonResult(ResultCode.PARAMS_ERROR, "币名称不能超过32个字!").toString();
			}
			
			cointypeList = frontPcointypeService.
					findByTwoProperty("projectId.fid", fuser.getFid(), "shortName", shortName.trim());
			if(null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "币种简称重复。").toString();
			}
			
			contractAddr = contractAddr.trim().toLowerCase();
			cointypeList = frontPcointypeService.findByTwoProperty("projectId.fid", fuser.getFid(), "contractAddr", contractAddr);
			
			if(null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "合约地址重复。").toString();
			}
			
			if(coinUsed.trim().length() > 100){
				return new JsonResult(ResultCode.PARAMS_ERROR, "币的实际用途不能超过15个字!").toString();
			}
			
			if(decimals > 18 || decimals < 1){
				return new JsonResult(ResultCode.FAIL, "小数位最小1位，最大18位！").toString();
			}
			
			if(pushTotal <= 0 || pushTotal >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "发行总量取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(pushPrice <= 0 || pushPrice >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "发行价格取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(currentCirculation <= 0 || currentCirculation >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "当前流通取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(currentMarketValue <= 0 || currentMarketValue >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "当前市值取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(!Utils.isUrl(website)){
				return new JsonResult(ResultCode.FAIL, "官网地址格式不正确，请更正！").toString();
			}
			
			if(StringUtils.isNotBlank(blockAddr) && !Utils.isUrl(blockAddr)){
				return new JsonResult(ResultCode.FAIL, "区块链浏览器地址格式不正确，请更正！").toString();
			}
			
			if(StringUtils.isNotBlank(contractLink) && !Utils.isUrl(contractLink)){
				return new JsonResult(ResultCode.FAIL, "已签合约代码链接地址格式不正确，请更正！").toString();
			}
			
			Pcointype pcointype = new Pcointype();
			pcointype.setName(name);
			pcointype.setShortName(shortName);
			pcointype.setProjectId(fuser);
			pcointype.setLogoUrl(logoUrl);
			pcointype.setPushTotal(pushTotal);
			pcointype.setPushPrice(pushPrice);
			pcointype.setCurrentCirculation(currentCirculation);
			pcointype.setCurrentMarketValue(currentMarketValue);
			pcointype.setCurrentHoldNum(currentHoldNum);
			pcointype.setDecimals(decimals);
			pcointype.setWebsite(website);
			pcointype.setContractAddr(contractAddr);
			pcointype.setBlockAddr(blockAddr);
			pcointype.setContractLink(contractLink);
			pcointype.setCoinUsed(coinUsed);
			pcointype.setStatus(0);   //币种状态（1：正常；2：禁用；3：风险；4：隐藏）
			pcointype.setAuditStatus(AuditStatusEnum.waitAudit);  //审核状态（101：待审核；102：审核通过；103：审核失败）
			pcointype.setCreateTime(Utils.getTimestamp());
			pcointype.setUpdateTime(pcointype.getCreateTime());
			pcointype.setOpenChargeTime(openChargeTime);
			pcointype.setOpenTradeTime(openTradeTime);
			pcointype.setOpenWithdrawTime(openWithdrawTime);
			
			frontPcointypeService.save(pcointype);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: upload  
	* @Description: 上传币logo 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/coin/logoUpload",produces={"text/html;charset=UTF-8"},method = { RequestMethod.POST })
	public String upload(HttpServletRequest request) throws Exception{
		try {
			
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request ;
			MultipartFile multipartFile = mRequest.getFile("file");
			InputStream inputStream = multipartFile.getInputStream() ;
			String realName = multipartFile.getOriginalFilename() ;

			double size = multipartFile.getSize()/1000d;
			if(size > 5120d){
				return new JsonResult(ResultCode.PARAMS_ERROR, "图片大小不能超过5M").toString();
			}
			String[] nameSplit = realName.split("\\.") ;
			String ext = nameSplit[nameSplit.length-1] ;
			if(ext!=null && !ext.trim().toLowerCase().endsWith("jpg")
					&& !ext.trim().toLowerCase().endsWith("bmp")
					 && !ext.trim().toLowerCase().endsWith("png")
					 && !ext.trim().toLowerCase().endsWith("jpeg")
					 && !ext.trim().toLowerCase().endsWith("gif")){
				return new JsonResult(ResultCode.PARAMS_ERROR, "图片格式不支持").toString();
			}
			
			String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
			String fileName = Utils.getRandomImageName()+"."+ext;
			Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
			
			String resultUrl = null;
			if(Constant.IS_OPEN_OSS.equals("false")){
				resultUrl = "/"+Constant.uploadPicDirectory+"/"+fileName ;
			}else{
				resultUrl = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", resultUrl).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: getCoinListByProjectId  
	* @Description: 根据项目方id查询币申请币列表 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/coin/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getCoinListByProjectId(HttpServletRequest request,
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
			List<Pcointype> pcointypes = frontPcointypeService.list(0, 0, filter+"", false);
				
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr = JSONArray.fromObject(pcointypes, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: findCoinTypeById  
	* @Description: 根据id获取币信息  
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/coin/detail",produces=JsonEncode, method = { RequestMethod.POST, RequestMethod.GET })
	public String findCoinTypeById(@RequestParam(required=true)int id) throws Exception{
		try {
			Pcointype pcointype = frontPcointypeService.findById(id);
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONObject jsonStr = JSONObject.fromObject(pcointype, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: coinApplicationUpdate  
	* @Description: 修改 
	* @author Ryan
	* @param @param request
	* @param @param id
	* @param @param name
	* @param @param shortName
	* @param @param logoUrl
	* @param @param pushTotal
	* @param @param pushPrice
	* @param @param currentCirculation
	* @param @param currentMarketValue
	* @param @param currentHoldNum
	* @param @param decimals
	* @param @param website
	* @param @param contractAddr
	* @param @param blockAddr
	* @param @param contractLink
	* @param @param coinUsed
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/coin/update",produces=JsonEncode,method = { RequestMethod.POST })
	public String coinApplicationUpdate (
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true)String name,
			@RequestParam(required=true)String shortName,
			@RequestParam(required=true)String logoUrl,
			@RequestParam(required=true)Double pushTotal,
			@RequestParam(required=true)Double pushPrice,
			@RequestParam(required=false)Double currentCirculation,
			@RequestParam(required=false)Double currentMarketValue,
			@RequestParam(required=false)Double currentHoldNum,
			@RequestParam(required=true)int decimals,
			@RequestParam(required=true)String website,
			@RequestParam(required=true)String contractAddr,
			@RequestParam(required=false)String blockAddr,
			@RequestParam(required=false)String contractLink,
			@RequestParam(required=false)String openChargeTime,
			@RequestParam(required=false)String openTradeTime,
			@RequestParam(required=false)String openWithdrawTime,
			@RequestParam(required=false)String coinUsed) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			Fuser fuser = frontUserService.findById(fid);
			
			//校验项目方类型
			String verifyResult = this.virifyProject(fuser);
			if(StringUtils.isNotBlank(verifyResult)) {
				return verifyResult;
			}
			
			Pcointype pcointype = frontPcointypeService.findById(id);
			List<Pcointype> cointypeList = frontPcointypeService.findByTwoProperty("projectId.fid", fuser.getFid(), "name", name.trim());
			
			if(!name.equals(pcointype.getName()) && null != cointypeList && cointypeList.size() > 0 ) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "币种名称重复。").toString();
			}
			
			cointypeList = frontPcointypeService.findByTwoProperty("projectId.fid", fuser.getFid(), "shortName", shortName.trim());
			if(!shortName.equals(pcointype.getShortName()) && null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "币种简称重复。").toString();
			}
			
			contractAddr = contractAddr.trim().toLowerCase();
			cointypeList = frontPcointypeService.findByTwoProperty("projectId.fid", fuser.getFid(), "contractAddr", contractAddr);
			if(!contractAddr.equals(pcointype.getContractAddr()) && null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "合约地址重复。").toString();
			}
			
			if(name.trim().length() > 32){
				return new JsonResult(ResultCode.PARAMS_ERROR, "币名称不能超过32个字!").toString();
			}
			
			if(coinUsed.trim().length() >100){
				return new JsonResult(ResultCode.PARAMS_ERROR, "币的实际用途不能超过15个字!").toString();
			}
			
			if(decimals > 18 || decimals <1){
				return new JsonResult(ResultCode.FAIL, "小数位最小1位，最大18位！").toString();
			}
			
			if(pushTotal <= 0 || pushTotal >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "发行总量取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(pushPrice <= 0 || pushPrice >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "发行价格取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(currentCirculation <= 0 || currentCirculation >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "当前流通取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(currentMarketValue <= 0 || currentMarketValue >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "当前市值取值必须大于0.0小于1000000000000.0！").toString();
			}
			
			if(currentHoldNum <= 0 || currentHoldNum >= 1000000000000.0){
				return new JsonResult(ResultCode.FAIL, "当前持币用户取值必须大于0小于1000000000000！").toString();
			}
			
			if(!Utils.isUrl(website)){
				return new JsonResult(ResultCode.FAIL, "官网地址格式不正确，请更正！").toString();
			}
			
			if(StringUtils.isNotBlank(blockAddr) && !Utils.isUrl(blockAddr)){
				return new JsonResult(ResultCode.FAIL, "区块链浏览器地址格式不正确，请更正！").toString();
			}
			
			if(StringUtils.isNotBlank(contractLink) && !Utils.isUrl(contractLink)){
				return new JsonResult(ResultCode.FAIL, "已签合约代码链接地址格式不正确，请更正！").toString();
			}
			
			pcointype.setName(name);
			pcointype.setShortName(shortName);
			pcointype.setLogoUrl(logoUrl);
			pcointype.setPushTotal(pushTotal);
			pcointype.setPushPrice(pushPrice);
			pcointype.setCurrentCirculation(currentCirculation);
			pcointype.setCurrentMarketValue(currentMarketValue);
			pcointype.setCurrentHoldNum(currentHoldNum);
			pcointype.setDecimals(decimals);
			pcointype.setWebsite(website);
			pcointype.setContractAddr(contractAddr);
			pcointype.setBlockAddr(blockAddr);
			pcointype.setContractLink(contractLink);
			pcointype.setCoinUsed(coinUsed);
			pcointype.setUpdateTime(Utils.getTimestamp());
			pcointype.setOpenChargeTime(openChargeTime);
			pcointype.setOpenTradeTime(openTradeTime);
			pcointype.setOpenWithdrawTime(openWithdrawTime);
			pcointype.setAuditStatus(AuditStatusEnum.waitAudit);  //审核状态（101：待审核；102：审核通过；103：审核失败）
			
			frontPcointypeService.update(pcointype);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 从session获取用户信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/coin/getUserInfo",produces=JsonEncode,method = { RequestMethod.GET })
	public String getUserInfo(HttpServletRequest request) throws Exception{
		try {
			Fuser fuser = GetSession(request);
			logger.info(">>>>>>>>登录用户:<<<<<<<"+fuser);
			if(null == fuser) {
				return new JsonResult(ResultCode.NOT_LOGIN, "没有登录").toString();
			} else {
				fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
				logger.info(">>>>>>>>登录用户:<<<<<<<"+fuser.getFloginName());
				
				Pproject project = frontPprojectService.findByProperty("projectId.fid", fuser.getFid());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userName", fuser.getFloginName());
				if(null != project) {
					map.put("projectType", project.getType());
				} else {
					map.put("projectType", 0);
				}
				
				return new JsonResult(ResultCode.SUCCESS, "success", map).toString();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
}
