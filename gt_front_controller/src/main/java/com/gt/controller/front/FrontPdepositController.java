package com.gt.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.PprojectSettleStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositcointype;
import com.gt.entity.Pdepositlogs;
import com.gt.entity.Pprofitlogs;
import com.gt.entity.Ptrademapping;
import com.gt.result.EchartDataVo;
import com.gt.result.JsonResult;
import com.gt.result.KeyValueVo;
import com.gt.result.ResultCode;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPcointypeService;
import com.gt.service.front.FrontPdepositService;
import com.gt.service.front.FrontPdepositcointypeService;
import com.gt.service.front.FrontPprofitlogsService;
import com.gt.service.front.FrontPtrademappingService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 资产管理controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/capital")
public class FrontPdepositController extends BaseController{
	
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontPdepositService frontPdepositService;
	@Autowired
	private FrontPcointypeService frontPcointypeService;
	@Autowired
	private FrontPdepositcointypeService frontPdepositcointypeService;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private FrontPtrademappingService frontPtrademappingService;
	@Autowired
	private UtilsService UtilsServiceImpl;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private FrontPprofitlogsService frontPprofitlogsService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	
	/**
	 * 
	* @Title: depositSubmit  
	* @Description: 缴纳押金
	* @author Ryan
	* @param @param request
	* @param @param cointypeId
	* @param @param depositCointypeId
	* @param @param payAmount
	* @param @return  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/deposit/submit",produces=JsonEncode,method = { RequestMethod.POST })
	public String depositSubmit(
			HttpServletRequest request,
			@RequestParam(required=true)int cointypeId,
			@RequestParam(required=true)int depositCointypeId,
			@RequestParam(required=true)Double payAmount){
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;

			String filter = " where cointypeId.fid=" + depositCointypeId;
			List<Pdepositcointype> pdepositcointypes = frontPdepositcointypeService.findByParam(filter);
			Pdepositcointype depositcointype = null;
			if(null != pdepositcointypes && pdepositcointypes.size() > 0) {
				depositcointype = pdepositcointypes.get(0);
			} else {
				return new JsonResult(ResultCode.SYS_ERROR, "保证金币种不存在").toString();
			}
			//查询该用户钱包的ETH
			List<Fvirtualwallet> virtualWallets = this.virtualWalletService.findByTwoProperty("fuser.fid", fuser.getFid(), "fvirtualcointype.fid",depositCointypeId);
			//余额不足
			if (virtualWallets == null || virtualWallets.size() == 0 || virtualWallets.get(0).getFtotal() < depositcointype.getDepositLimit()){
				return new JsonResult(ResultCode.SYS_ERROR, "账户余额不足").toString();
			}
			//是否需要输入交易密码
			Pdeposit pdeposit = new Pdeposit();
			Pdepositlogs pdepositlog = new Pdepositlogs();
			Fvirtualwallet fvirtualwallet = virtualWallets.get(0);
			
			//修改钱包记录
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-depositcointype.getDepositLimit());
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
			
			Pcointype pcointype = frontPcointypeService.findById(cointypeId);
			
			//生成押金表记录
			pdeposit.setProjectId(fuser);
			pdeposit.setCointypeId(pcointype);
			pdeposit.setDepositCointypeId(depositcointype);
			pdeposit.setPayAmount(depositcointype.getDepositLimit());
			pdeposit.setAvailableAmount(pdeposit.getPayAmount());
			pdeposit.setFrozenAmount(0);
			pdeposit.setCreateTime(Utils.getTimestamp());
			
			//生成押金操作记录表
			pdepositlog.setProjectId(fuser);
			pdepositlog.setDepositId(pdeposit);
			pdepositlog.setAmount(payAmount);
			pdepositlog.setOperateType(1);
			pdepositlog.setAuditStatus(101);
			pdepositlog.setCreateTime(Utils.getTimestamp());
			pdepositlog.setUpdateTime(pdepositlog.getCreateTime());
			
			
			//根据币id和项目方id找到对应的市场，修改保证金状态
			String filter1 = "where tradeCurrencyType = " + cointypeId + " and projectId = " + fuser.getFid();
			List<Ptrademapping> ptrademappings = frontPtrademappingService.list(0, 0, filter1, false);
			for (Ptrademapping ptrademapping : ptrademappings) {
				ptrademapping.setDepositStatus(2);      // （1：未缴纳；2：已缴纳）
				ptrademapping.setUpdateTime(Utils.getTimestamp());
			}
			
			frontPdepositService.saveAll(pdeposit,fvirtualwallet,pdepositlog,ptrademappings, pcointype);
			
			return new JsonResult(ResultCode.SUCCESS, "success").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: getAdByProjectId  
	* @Description: 根据项目方id获取保证金列表 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/deposit/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getAdByProjectId(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			String filter = " where projectId = " + fuser.getFid();
			List<Pdeposit> pdeposits = frontPdepositService.list(0, 0, filter, false);
			
			//统计总的保证金 可用保证金余额  TODO
			double totalDeposit = 0;
			double availableAmount = 0;
			for (Pdeposit deposit : pdeposits) {
				totalDeposit = totalDeposit + deposit.getPayAmount();
				availableAmount = availableAmount + deposit.getAvailableAmount();
			}
			filter = " where 1 = 1 ";
			List<Pdepositcointype> pdepositcointypes = frontPdepositcointypeService.findByParam(filter);
			Pdepositcointype depositcointype = pdepositcointypes.get(0);

			
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr = JSONArray.fromObject(pdeposits, jsonConfig);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pdepositList", jsonStr);
			//总的保证金   可用保证金余额
			map.put("totalDeposit", totalDeposit);
			map.put("availableAmount", availableAmount);
			map.put("depositCoinType", depositcointype);
			
			return new JsonResult(ResultCode.SUCCESS, "success", map).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 查询该币种是否已缴纳保证金
	 * @param request
	 * @param cointypeId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/deposit/queryIsDeposit",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryIsDeposit(HttpServletRequest request,
			@RequestParam(required=true)int cointypeId) throws Exception{
		boolean flag = false;
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			String filter = " where projectId.fid = " + fuser.getFid() + " and cointypeId.id = "+ cointypeId;
			List<Pdeposit> pdeposits = frontPdepositService.list(0, 0, filter, false);
			
			if(null != pdeposits && pdeposits.size() >0) {
				flag = true ;
			} else {
				flag = false ;
			}
			return new JsonResult(ResultCode.SUCCESS, "success", flag).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 查询保证金币种信息
	 * @param request
	 * @param cointypeId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/deposit/queryDepositCoinType",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryDepositCoinType(HttpServletRequest request) throws Exception{
		try {
			String filter = " where 1 = 1";
			List<Pdeposit> pdeposits = UtilsServiceImpl.list(0, 0, filter, false, Pdepositcointype.class);
			JSONObject jsonStr = null;
			if(null != pdeposits && pdeposits.size() >0) {
				Pdeposit pdeposit = pdeposits.get(0);
				JsonConfig jsonConfig  = new JsonConfig();
				jsonConfig.setExcludes(new String[]{"projectId"});
				jsonStr = JSONObject.fromObject(pdeposit, jsonConfig);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 
	* @Title: getCapitaloperationList  
	* @Description: 根据项目方id获取资金记录列表
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/capitaloperation/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getCapitaloperationList(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			String filter = " where fvirtualcointype.fid in (select fid from Fvirtualcointype where fprojectId = " + fuser.getFid() + ") ";
			
			List<Fvirtualcaptualoperation> virtualcaptualoperationList =  
					frontVirtualCoinService.findFvirtualcaptualoperations(0, 0, filter, false);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"fuser"});
			JSONArray jsonStr = JSONArray.fromObject(virtualcaptualoperationList, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 查询资产管理---收益记录
	 * @param request
	 * @param coinTypeId
	 * @param startTime
	 * @param endTime
	 * @param currentPage
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/profitlogs/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryProfitlogsList(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int coinTypeId,
			@RequestParam(required=false) String startTime,
			@RequestParam(required=false) String endTime,
			@RequestParam(required=false,defaultValue="1") int currentPage,
			@RequestParam(required=false,defaultValue="8") int pageNum) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			if(StringUtils.isNotBlank(startTime)) {
				filter.append(" and statisticalDate >= '" + startTime+"'");
			}
			if(StringUtils.isNotBlank(endTime)) {
				filter.append(" and statisticalDate <= '" + endTime+"'");
			}
			filter.append(" order by createTime desc");
			
			List<Pprofitlogs> profitlogsList = frontPprofitlogsService.list((currentPage-1)*pageNum, pageNum, filter+"", true);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId","trademappingId"});
			JSONArray jsonStr = JSONArray.fromObject(profitlogsList, jsonConfig);
			
			int totalCount = this.adminService.getAllCount("Pprofitlogs", filter+"");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("profitlogsList", jsonStr);
			map.put("totalCount", totalCount);
			
			//当前收益总额
			String sqlfilter = " where cointypeId.fid =" + coinTypeId;
			Double totalProfit = adminService.getAllSum("Pprofitlogs", "amount", sqlfilter+"");
			//待收益总额
			sqlfilter = " where status = " + PprojectSettleStatusEnum.WAIT_SETTLE;
			Double waitSettleProfit = adminService.getAllSum("Pprofitlogs", "amount", sqlfilter+"");
			//已收益总额
			sqlfilter = " where status = " + PprojectSettleStatusEnum.HAS_SETTLE;
			Double hasSettleProfit = adminService.getAllSum("Pprofitlogs", "amount", sqlfilter+"");
			List<EchartDataVo> echartDataList = new ArrayList<EchartDataVo>();
			EchartDataVo echartData1 = new EchartDataVo();
			echartData1.setName("当前收益总额");
			echartData1.setValue(totalProfit);
			echartDataList.add(echartData1);
			EchartDataVo echartData2 = new EchartDataVo();
			echartData2.setName("待收益总额");
			echartData2.setValue(waitSettleProfit);
			echartDataList.add(echartData2);
			EchartDataVo echartData3 = new EchartDataVo();
			echartData3.setName("已收益总额");
			echartData3.setValue(hasSettleProfit);
			echartDataList.add(echartData3);
			map.put("echartDataList", echartDataList);
			
			
			//按月统计
			sqlfilter = "select DATE_FORMAT(statistical_date,'%Y%m') months,"
					+ "	sum(amount) amount from p_profitlogs"
					+ " where project_id =" + fuser.getFid()
					+ " AND cointype_id =" + coinTypeId
					+ " group by months";
			Map<Integer,Object> monthStatisticsMap = adminService.getSQLValue1(sqlfilter);
			List<EchartDataVo> monthStatisticsList = new ArrayList<EchartDataVo>();
			for (Map.Entry<Integer, Object> entry : monthStatisticsMap.entrySet()) {
				EchartDataVo echartDataVo = new EchartDataVo();
				echartDataVo.setName(entry.getKey().toString());
				echartDataVo.setValue(entry.getValue());
				monthStatisticsList.add(echartDataVo);
			}
			map.put("monthStatisticsList", monthStatisticsList);
			
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: getAssetList  
	* @Description: 查询资产管理---账户总览
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/asset/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getAssetList(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			//用户钱包
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid()) ;
			//USDT钱包
			Fvirtualwallet usdtfwallet = this.frontUserService.findUSDTWalletByUser(fuser.getFid()) ;
			
			//虚拟钱包full 
			LinkedHashMap<Fvirtualcointype,Fvirtualwallet> fvirtualwallets= this.frontUserService.findVirtualWallet(fuser.getFid()) ;
			//估计总资产，重点注意
			//CNY+冻结CNY+（币种+冻结币种）*最高买价
			//CNY先要折算成USDT来统计
			double totalCapital = 0F ;
			double balanceCapital = 0F;
			double frozenCapital = 0F;
			totalCapital+=((fwallet.getFtotal()+fwallet.getFfrozen())/6.5);
			totalCapital+=usdtfwallet.getFtotal()+usdtfwallet.getFfrozen();
			balanceCapital+=fwallet.getFtotal()/6.5;
			balanceCapital+=usdtfwallet.getFtotal();
			frozenCapital+=fwallet.getFfrozen()/6.5;
			frozenCapital+=usdtfwallet.getFfrozen();
			
			Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapService.get("tradeMappings");
			for (Map.Entry<Fvirtualcointype, Fvirtualwallet> entry : fvirtualwallets.entrySet()) {
				if(entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_CNY_VALUE || entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_USDT_VALUE) continue;
				try {
					if(tradeMappings!=null&&tradeMappings.containsKey(entry.getValue().getFvirtualcointype().getFid())){
						totalCapital += ( entry.getValue().getFfrozen()+entry.getValue().getFtotal() )* this.frontCacheService.getLatestDealPrize(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid())) ;
						balanceCapital += ( entry.getValue().getFtotal() )* this.frontCacheService.getLatestDealPrize(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid())) ;
						frozenCapital += ( entry.getValue().getFfrozen() )* this.frontCacheService.getLatestDealPrize(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid())) ;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//收益列表
			List<Map> assetList = frontPprofitlogsService.getAssetListByParams(fuser.getFid());
			
			//币种收益分配图
			Map<String,Object> coinChargeMap = new HashMap<String,Object>();
			
			//手续费收益总额
			double totalServiceCharge = 0F;
			for(Map<String,Object> m : assetList){
				Fvirtualcointype fvirtualcointype = frontVirtualCoinService.findById(Integer.valueOf(m.get("coinTypeId").toString()));
				m.put("shortName", fvirtualcointype.getfShortName());
				if(fvirtualcointype.getFtype() == CoinTypeEnum.FB_CNY_VALUE || fvirtualcointype.getFtype() == CoinTypeEnum.FB_USDT_VALUE){
					if(fvirtualcointype.getFtype() == CoinTypeEnum.FB_USDT_VALUE) {
						coinChargeMap.put(m.get("shortName").toString(), Double.valueOf(m.get("totalAmount").toString()));
					}
					continue;
				}
				if(tradeMappings!=null&&tradeMappings.containsKey(m.get("coinTypeId"))){
					double temp = ( Double.valueOf(m.get("totalAmount").toString()) )* this.frontCacheService.getLatestDealPrize(tradeMappings.get(Integer.valueOf(m.get("coinTypeId").toString())));
					totalServiceCharge += temp ;
					coinChargeMap.put(m.get("shortName").toString(), temp);
				}
			}
			
			//当月手续费收益
			double monthServiceCharge = 0F;
			List<Map> allMonassetList = frontPprofitlogsService.getMonthServiceCharge(3, fuser.getFid());
			for(Map<String,Object> m : allMonassetList){
				if(tradeMappings!=null&&tradeMappings.containsKey(m.get("coinTypeId"))){
					monthServiceCharge += ( Double.valueOf(m.get("totalAmount").toString()) )* this.frontCacheService.getLatestDealPrize(tradeMappings.get(Integer.valueOf(m.get("coinTypeId").toString()))) ;
				}
			}
			
			//当月待分配收益
			double monthWaitForCharge = 0F;
			List<Map> waitMonassetList = frontPprofitlogsService.getMonthServiceCharge(0, fuser.getFid());
			for(Map<String,Object> m : waitMonassetList){
				if(tradeMappings!=null&&tradeMappings.containsKey(m.get("coinTypeId"))){
					monthWaitForCharge += ( Double.valueOf(m.get("totalAmount").toString()) )* this.frontCacheService.getLatestDealPrize(tradeMappings.get(Integer.valueOf(m.get("coinTypeId").toString()))) ;
				}
			}
			
			List<EchartDataVo> coinChargeList = new ArrayList<EchartDataVo>();
			if(null != coinChargeMap) {
				for (Map.Entry<String, Object> entry : coinChargeMap.entrySet()) {
					EchartDataVo echartDataVo = new EchartDataVo();
					echartDataVo.setName(entry.getKey().toString());
					echartDataVo.setValue(entry.getValue());
					coinChargeList.add(echartDataVo);
				}
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("totalCapital", totalCapital);
			map.put("balanceCapital", balanceCapital);
			map.put("frozenCapital", frozenCapital);
			map.put("assetList", assetList);
			map.put("totalServiceCharge", totalServiceCharge);
			map.put("monthServiceCharge", monthServiceCharge);
			map.put("monthWaitForCharge", monthWaitForCharge);
			map.put("coinChargeList", coinChargeList);
			
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
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
	@RequestMapping(value="/profitlogs/queryCoinType",produces=JsonEncode,method = { RequestMethod.GET })
	public String queryTrademappings(HttpServletRequest request) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			String filter = " where projectId.fid=" + fuser.getFid();
			List<Integer> coinTypeList = frontPprofitlogsService.getAllProfitCoinType("Pprofitlogs", "cointypeId.fid", filter);
			List<KeyValueVo> keyValueVoList = new ArrayList<KeyValueVo>();
			for (Integer coinTypeId : coinTypeList) {
				Fvirtualcointype fcointype = frontVirtualCoinService.findById(coinTypeId);
				KeyValueVo keyValue = new KeyValueVo();
				keyValue.setKey(fcointype.getFid());
				keyValue.setValue(fcointype.getfShortName());
				keyValueVoList.add(keyValue);
			}
			
			return new JsonResult(ResultCode.SUCCESS, "success", keyValueVoList).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
}
