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
import com.gt.Enum.CoinTypeEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositcointype;
import com.gt.entity.Ptrademapping;
import com.gt.result.JsonResult;
import com.gt.result.KeyValueVo;
import com.gt.result.ResultCode;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontPcointypeService;
import com.gt.service.front.FrontPdepositService;
import com.gt.service.front.FrontPdepositcointypeService;
import com.gt.service.front.FrontPtrademappingService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * 市场管理——————交易市场controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/market")
public class FrontPtrademappingController extends BaseController{
	@Autowired
	private FrontPtrademappingService frontPtrademappingService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontPcointypeService frontPcointypeService;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FrontPdepositcointypeService frontPdepositcointypeService;
	@Autowired
	private FrontPdepositService frontPdepositService;
	
	/**
	 * 
	* @Title: coinApplicationSubmit  
	* @Description: 提交市场申请 
	* @author Ryan
	* @param @param request
	* @param @param frenchCurrencyType
	* @param @param tradeCurrencyType
	* @param @param unitPriceDecimal
	* @param @param tradeTime
	* @param @param openPrice
	* @param @param minEntrustPrice
	* @param @param maxEntrustPrice
	* @param @param minEntrustMoney
	* @param @param maxEntrustMoney
	* @param @param minEntrustQty
	* @param @param maxEntrustQty
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String tradeMappingApplicationSubmit (
			HttpServletRequest request,
			@RequestParam(required=true)int frenchCurrencyType,
			@RequestParam(required=true)int tradeCurrencyType,
			@RequestParam(required=true)int unitPriceDecimal,
			@RequestParam(required=true)String tradeTime,
			@RequestParam(required=true)Double openPrice,
			@RequestParam(required=true)Double minEntrustPrice,
			@RequestParam(required=true)Double maxEntrustPrice,
			@RequestParam(required=true)Double minEntrustMoney,
			@RequestParam(required=true)Double maxEntrustMoney,
			@RequestParam(required=true)Double minEntrustQty,
			@RequestParam(required=true)Double maxEntrustQty) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			//校验项目方类型
			String verifyResult = this.virifyProject(fuser);
			if(StringUtils.isNotBlank(verifyResult)) {
				return verifyResult;
			}
			
			//数据校验
			if(unitPriceDecimal > 8 || unitPriceDecimal <1){
				return new JsonResult(ResultCode.FAIL, "小数位最小1位，最大8位").toString();
			}
			
			if(openPrice <= 0 || openPrice >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "开盘价取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustPrice <= 0 || minEntrustPrice >= 999999999.0 || maxEntrustPrice <= 0 || maxEntrustPrice >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "挂单单价取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustMoney <= 0 || minEntrustMoney >= 999999999.0 || maxEntrustMoney <= 0 || maxEntrustMoney >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "挂单金额取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustQty <= 0 || minEntrustQty >= 999999999.0 || maxEntrustQty <= 0 || maxEntrustQty >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "挂单数量取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustPrice > maxEntrustPrice){
				return new JsonResult(ResultCode.FAIL, "挂单单价的最大值不能小于最小值，请更正！").toString();
			}
			
			if(minEntrustMoney > maxEntrustMoney){
				return new JsonResult(ResultCode.FAIL, "挂单金额的最大值不能小于最小值，请更正！").toString();
			}
			
			if(minEntrustQty > maxEntrustQty){
				return new JsonResult(ResultCode.FAIL, "挂单数量的最大值不能小于最小值，请更正！").toString();
			}
			
			String filter = " where projectId.fid = " + fuser.getFid() + 
							" and frenchCurrencyType.fid="+frenchCurrencyType + 
							" and tradeCurrencyType.id="+tradeCurrencyType +
							" and auditStatus !="+AuditStatusEnum.auditNopass;
			int count = this.adminService.getAllCount("Ptrademapping", filter);
			if(count > 0){
				return new JsonResult(ResultCode.FAIL, "你已提交该市场的开通申请，无需重复提交！").toString();
			}
			
			Ptrademapping ptrademapping = new Ptrademapping();
			ptrademapping.setProjectId(fuser);
			ptrademapping.setFrenchCurrencyType(frontVirtualCoinService.findFvirtualCoinById(frenchCurrencyType));
			ptrademapping.setTradeCurrencyType(frontPcointypeService.findById(tradeCurrencyType));
			ptrademapping.setUnitPriceDecimal(unitPriceDecimal);
			ptrademapping.setTradeTime(tradeTime);
			ptrademapping.setOpenPrice(openPrice);
			ptrademapping.setMinEntrustPrice(minEntrustPrice);
			ptrademapping.setMaxEntrustPrice(maxEntrustPrice);
			ptrademapping.setMinEntrustMoney(minEntrustMoney);
			ptrademapping.setMaxEntrustMoney(maxEntrustMoney);
			ptrademapping.setMinEntrustQty(minEntrustQty);
			ptrademapping.setMaxEntrustQty(maxEntrustQty);
			ptrademapping.setStatus(0);  //币种状态（1：正常；2：禁用；3：风险；4：隐藏）
			ptrademapping.setAuditStatus(AuditStatusEnum.waitAudit);  //审核状态（101：待审核；102：审核通过；103：审核失败）
			ptrademapping.setCreateTime(Utils.getTimestamp());
			ptrademapping.setUpdateTime(ptrademapping.getCreateTime());
			
			//查询是否已缴纳保证金
			filter = " where projectId.fid = " + fuser.getFid() + " and cointypeId.id = "+ tradeCurrencyType;
			List<Pdeposit> pdeposits = frontPdepositService.list(0, 0, filter, false);
			if(null != pdeposits && pdeposits.size() >0) {
				ptrademapping.setDepositStatus(2);     //保证金缴纳状态（1：未缴纳；2：已缴纳）
			} else {
				ptrademapping.setDepositStatus(1);     //保证金缴纳状态（1：未缴纳；2：已缴纳）
			}
			
			frontPtrademappingService.save(ptrademapping);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: getCoinListByProjectId  
	* @Description: 根据项目方id查询市场列表 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getMarketListByProjectId(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int auditStatus,
			@RequestParam(required=false,defaultValue="0") int depositStatus) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			if(auditStatus != 0) {
				filter.append(" and auditStatus = "+ auditStatus);
			}
			if(depositStatus != 0) {
				filter.append(" and depositStatus = "+ depositStatus);
			}
			
			List<Ptrademapping> ptrademappings = frontPtrademappingService.list(0, 0, filter+"", false);
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr = JSONArray.fromObject(ptrademappings, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: findTradeMappingById  
	* @Description: 根据id获取市场详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/detail",produces=JsonEncode,method = { RequestMethod.GET })
	public String findTradeMappingById(@RequestParam(required=true)int id) throws Exception{
		try {
			Ptrademapping ptrademapping = frontPtrademappingService.findById(id);
			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONObject jsonStr = JSONObject.fromObject(ptrademapping, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: coinApplicationUpdate  
	* @Description: 更新
	* @author Ryan
	* @param @param request
	* @param @param id
	* @param @param frenchCurrencyType
	* @param @param tradeCurrencyType
	* @param @param unitPriceDecimal
	* @param @param tradeTime
	* @param @param openPrice
	* @param @param minEntrustPrice
	* @param @param maxEntrustPrice
	* @param @param minEntrustMoney
	* @param @param maxEntrustMoney
	* @param @param minEntrustQty
	* @param @param maxEntrustQty
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/update",produces=JsonEncode,method = { RequestMethod.POST })
	public String coinApplicationUpdate (
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true)int frenchCurrencyType,
			@RequestParam(required=true)int tradeCurrencyType,
			@RequestParam(required=true)int unitPriceDecimal,
			@RequestParam(required=true)String tradeTime,
			@RequestParam(required=true)Double openPrice,
			@RequestParam(required=true)Double minEntrustPrice,
			@RequestParam(required=true)Double maxEntrustPrice,
			@RequestParam(required=true)Double minEntrustMoney,
			@RequestParam(required=true)Double maxEntrustMoney,
			@RequestParam(required=true)Double minEntrustQty,
			@RequestParam(required=true)Double maxEntrustQty) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			Fuser fuser = frontUserService.findById(fid);
			
			//校验项目方类型
			String verifyResult = this.virifyProject(fuser);
			if(StringUtils.isNotBlank(verifyResult)) {
				return verifyResult;
			}
			
			//数据校验  TODO
			if(unitPriceDecimal > 8 || unitPriceDecimal < 1){
				return new JsonResult(ResultCode.FAIL, "小数位最小1位，最大8位").toString();
			}
			
			if(openPrice <= 0 || openPrice >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "开盘价取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustPrice <= 0 || minEntrustPrice >= 999999999.0 || maxEntrustPrice <= 0 || maxEntrustPrice >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "挂单单价取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustMoney <= 0 || minEntrustMoney >= 999999999.0 || maxEntrustMoney <= 0 || maxEntrustMoney >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "挂单金额取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustQty <= 0 || minEntrustQty >= 999999999.0 || maxEntrustQty <= 0 || maxEntrustQty >= 999999999.0){
				return new JsonResult(ResultCode.FAIL, "挂单数量取值必须大于0.0小于999999999.0！").toString();
			}
			
			if(minEntrustPrice > maxEntrustPrice){
				return new JsonResult(ResultCode.FAIL, "挂单单价的最大值不能小于最小值，请更正！").toString();
			}
			
			if(minEntrustMoney > maxEntrustMoney){
				return new JsonResult(ResultCode.FAIL, "挂单金额的最大值不能小于最小值，请更正！").toString();
			}
			
			if(minEntrustQty > maxEntrustQty){
				return new JsonResult(ResultCode.FAIL, "挂单数量的最大值不能小于最小值，请更正！").toString();
			}
			
			Ptrademapping ptrademapping = frontPtrademappingService.findById(id);
			ptrademapping.setFrenchCurrencyType(frontVirtualCoinService.findFvirtualCoinById(frenchCurrencyType));
			ptrademapping.setTradeCurrencyType(frontPcointypeService.findById(tradeCurrencyType));
			ptrademapping.setUnitPriceDecimal(unitPriceDecimal);
			ptrademapping.setTradeTime(tradeTime);
			ptrademapping.setOpenPrice(openPrice);
			ptrademapping.setMinEntrustPrice(minEntrustPrice);
			ptrademapping.setMaxEntrustPrice(maxEntrustPrice);
			ptrademapping.setMinEntrustMoney(minEntrustMoney);
			ptrademapping.setMaxEntrustMoney(maxEntrustMoney);
			ptrademapping.setMinEntrustQty(minEntrustQty);
			ptrademapping.setMaxEntrustQty(maxEntrustQty);
			ptrademapping.setAuditStatus(AuditStatusEnum.waitAudit);
			ptrademapping.setUpdateTime(Utils.getTimestamp());
			
			frontPtrademappingService.update(ptrademapping);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 查询法币下拉列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/queryFrenchCurrencyType",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryFrenchCurrencyType(HttpServletRequest request) throws Exception{
		try {
			List<Fvirtualcointype> cointypeList = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.COIN_VALUE,1);
			List<KeyValueVo> keyValueVoList = new ArrayList<KeyValueVo>();
			for (Fvirtualcointype fvirtualcointype : cointypeList) {
				if(fvirtualcointype.getFstatus()==1) {
					KeyValueVo keyValue = new KeyValueVo();
					keyValue.setKey(fvirtualcointype.getFid());
					keyValue.setValue(fvirtualcointype.getfShortName());
					keyValueVoList.add(keyValue);
				}
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", keyValueVoList).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	

	/**
	 * 查询交易币下拉列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/queryTradeCurrencyType",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryTradeCurrencyType(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int frenchCoinType) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			filter.append(" and auditStatus=" + AuditStatusEnum.auditPass);
			
			if(0 != frenchCoinType) {
				filter.append(" and id not in ( select tradeCurrencyType.id from Ptrademapping"
						+" where auditStatus !="+ AuditStatusEnum.auditNopass
						+" and projectId.fid="+ fuser.getFid()
						+" and frenchCurrencyType.fid="+ frenchCoinType +")");
			}
			
//			String filter = " where projectId.fid=" + fuser.getFid() + " and auditStatus=" + AuditStatusEnum.auditPass;
			List<Pcointype> pcointypes = frontPcointypeService.list(0, 0, filter+"", false);
			List<KeyValueVo> keyValueVoList = new ArrayList<KeyValueVo>();
			for (Pcointype pcointype : pcointypes) {
				KeyValueVo keyValue = new KeyValueVo();
				keyValue.setKey(pcointype.getId());
				keyValue.setValue(pcointype.getShortName());
				keyValueVoList.add(keyValue);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", keyValueVoList).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 查询缴纳的保证金币种信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trademapping/queryDepositcointype",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryDepositcointype(HttpServletRequest request) throws Exception{
		try {
			
			String filter = " where 1=1";
			List<Pdepositcointype> depositcointypeList = frontPdepositcointypeService.findByParam(filter);
			Pdepositcointype depositcointype = null;
			if(null != depositcointypeList && depositcointypeList.size() > 0) {
				depositcointype = depositcointypeList.get(0);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", depositcointype).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
}
