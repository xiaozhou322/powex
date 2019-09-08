package com.gt.controller.front;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.gt.result.JsonResult;
import com.gt.result.ResultCode;
import com.gt.service.front.FrontPcointypeService;
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
			@RequestParam(required=false)String contractAddr,
			@RequestParam(required=false)String blockAddr,
			@RequestParam(required=false)String contractLink,
			@RequestParam(required=false)String coinUsed) throws Exception{
		
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107594;
			Fuser fuser = frontUserService.findById(fid);
			
			List<Pcointype> cointypeList = frontPcointypeService.
					findByTwoProperty("projectId.fid", fuser.getFid(), "name", name.trim());
			if(null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "币种名称重复。").toString();
			}
			
			cointypeList = frontPcointypeService.
					findByTwoProperty("projectId.fid", fuser.getFid(), "shortName", shortName.trim());
			if(null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "币种简称重复。").toString();
			}
			
			contractAddr = contractAddr.trim().toLowerCase();
			cointypeList = frontPcointypeService.
					findByTwoProperty("projectId.fid", fuser.getFid(), "contractAddr", contractAddr);
			if(null != cointypeList && cointypeList.size() > 0) {
				return new JsonResult(ResultCode.SUCCESS_IS_HAVE, "合约地址重复。").toString();
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
			pcointype.setAuditStatus(AuditStatusEnum.waitAudit);
			pcointype.setCreateTime(Utils.getTimestamp());
			pcointype.setUpdateTime(pcointype.getCreateTime());
			
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
				return "";
			}
			String[] nameSplit = realName.split("\\.") ;
			String ext = nameSplit[nameSplit.length-1] ;
			if(ext!=null && !ext.trim().toLowerCase().endsWith("jpg")
					&& !ext.trim().toLowerCase().endsWith("bmp")
					 && !ext.trim().toLowerCase().endsWith("png")
					 && !ext.trim().toLowerCase().endsWith("jpeg")
					 && !ext.trim().toLowerCase().endsWith("gif")){
				return "";
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
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
				
			StringBuffer filter = new StringBuffer();
//			filter.append(" where projectId.fid=" + 107594);
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
			@RequestParam(required=false)String contractAddr,
			@RequestParam(required=false)String blockAddr,
			@RequestParam(required=false)String contractLink,
			@RequestParam(required=false)String coinUsed) throws Exception{
		try {
			Pcointype pcointype = frontPcointypeService.findById(id);
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
				return new JsonResult(ResultCode.SUCCESS, "success", fuser.getFloginName()).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
}
