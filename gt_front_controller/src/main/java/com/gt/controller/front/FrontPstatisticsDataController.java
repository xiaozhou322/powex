package com.gt.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Fuser;
import com.gt.entity.PchargeWithdrawStatistics;
import com.gt.entity.PcwTotalStatistics;
import com.gt.entity.PdataStatistics;
import com.gt.entity.PentrustStatistics;
import com.gt.entity.PtradeStatistics;
import com.gt.result.EchartDataVo;
import com.gt.result.JsonResult;
import com.gt.result.ResultCode;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.EntrustService;
import com.gt.service.front.FentrustlogService;
import com.gt.service.front.FrontPstatisticsDataService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;
import com.gt.util.XlsExport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * 数据统计controller
 * @author zhouyong
 *
 */
@Controller
@RequestMapping("/statistics")
public class FrontPstatisticsDataController extends BaseController{
	
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontPstatisticsDataService frontPstatisticsDataService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private EntrustService entrustService ;
	@Autowired
	private FentrustlogService entrustlogService ;
	
	/**
	 * 
	* @Title: getDataStatisticsByProjectId  
	* @Description: 根据项目方id获取数据中心统计列表 
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/dataStatistics/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getDataStatisticsByProjectId(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1") int currentPage,
			@RequestParam(required=false,defaultValue="8") int pageNum) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			filter.append(" order by createTime desc");
			
			List<PdataStatistics> pdataStatisticsList = frontPstatisticsDataService.dataStatisticsList(0, 0, filter+"", false);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr = JSONArray.fromObject(pdataStatisticsList, jsonConfig);
			
			List<PcwTotalStatistics> pcwTotalStatistics = frontPstatisticsDataService.
					pcwStatisticslist((currentPage-1)*pageNum, pageNum, filter+"", true);
			JsonConfig jsonConfig1  = new JsonConfig();
			jsonConfig1.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr1 = JSONArray.fromObject(pcwTotalStatistics, jsonConfig);
			
			int totalCount = this.adminService.getAllCount("PcwTotalStatistics", filter+"");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pdataStatisticsList", jsonStr);
			map.put("pcwTotalStatistics", jsonStr1);
			map.put("totalCount", totalCount);
			
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
		
	}
	
	
	/**
	 * 
	* @Title: getChargeWithdrawStatisticsByProjectId  
	* @Description: 根据项目方id获取充提币统计列表  
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/chargeWithdraw/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getChargeWithdrawStatisticsByProjectId(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int coinTypeId,
			@RequestParam(required=false,defaultValue="1") int currentPage,
			@RequestParam(required=false,defaultValue="8") int pageNum,
			@RequestParam(required=false,defaultValue="") String startDate,
			@RequestParam(required=false,defaultValue="") String endDate,
			@RequestParam(required=false,defaultValue="0") int coinId) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			
			if(StringUtils.isNotBlank(startDate)){
				filter.append(" and  statisticalDate >= '"+startDate+"' \n");
			}
			
			if(StringUtils.isNotBlank(endDate)){
				filter.append(" and  statisticalDate <= '"+endDate+"' \n");
			}
			
			if(0 != coinId){
				filter.append(" and  cointypeId.fid = "+coinId+" \n");
			}
			
			filter.append(" order by createTime desc");
			
			List<PchargeWithdrawStatistics> pchargeWithdrawStatisticsList = frontPstatisticsDataService.
					chargeWithdrawStatisticsList((currentPage-1)*pageNum, pageNum, filter+"", true);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId"});
			JSONArray jsonStr = JSONArray.fromObject(pchargeWithdrawStatisticsList, jsonConfig);
			
			int totalCount = this.adminService.getAllCount("PchargeWithdrawStatistics", filter+"");
			
			//周曲线图---冲币总额
			String sqlfilter = "select DATE_FORMAT(statistical_date,'%Y%m%d') day,"
					+ "	sum(charge_amount) amount from p_charge_withdraw_statistics"
					+ " where project_id =" + fuser.getFid() 
					+ " AND cointype_id =" + coinTypeId
					+ " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) "
					+ " group by day order by day";
			Map<Integer,Object> weekStatisticsChargeMap = adminService.getSQLValue1(sqlfilter);
			//按key排序
			weekStatisticsChargeMap = Utils.sortMapByKey(weekStatisticsChargeMap);
			List<EchartDataVo> weekStatisticsChargeList = new ArrayList<EchartDataVo>();
			if(null != weekStatisticsChargeMap) {
				for (Map.Entry<Integer, Object> entry : weekStatisticsChargeMap.entrySet()) {
					EchartDataVo echartDataVo = new EchartDataVo();
					echartDataVo.setName(entry.getKey().toString());
					echartDataVo.setValue(entry.getValue());
					weekStatisticsChargeList.add(echartDataVo);
				}
			}
			
			//周曲线图---提币总额
			sqlfilter = "select DATE_FORMAT(statistical_date,'%Y%m%d') day,"
					+ "	sum(withdraw_amount) amount from p_charge_withdraw_statistics"
					+ " where project_id =" + fuser.getFid() 
					+ " AND cointype_id =" + coinTypeId
					+ " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) "
					+ " group by day order by day";
			Map<Integer,Object> weekStatisticsWithdrawMap = adminService.getSQLValue1(sqlfilter);
			//按key排序
			weekStatisticsWithdrawMap = Utils.sortMapByKey(weekStatisticsWithdrawMap);
			List<EchartDataVo> weekStatisticsWithdrawList = new ArrayList<EchartDataVo>();
			if(null != weekStatisticsWithdrawMap) {
				for (Map.Entry<Integer, Object> entry : weekStatisticsWithdrawMap.entrySet()) {
					EchartDataVo echartDataVo = new EchartDataVo();
					echartDataVo.setName(entry.getKey().toString());
					echartDataVo.setValue(entry.getValue());
					weekStatisticsWithdrawList.add(echartDataVo);
				}
			}
			
			sqlfilter = " where cointypeId.fid =" + coinTypeId;
			//充币数量总额
			Double totalChargeAmount = adminService.getAllSum("PchargeWithdrawStatistics", "chargeAmount", sqlfilter);
			
			//提币数量总额
			Double totalWithdrawAmount = adminService.getAllSum("PchargeWithdrawStatistics", "withdrawAmount", sqlfilter);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("cwStatisticsList", jsonStr);
			//充币数量，提币数量
			map.put("totalChargeAmount", totalChargeAmount);
			map.put("totalWithdrawAmount", totalWithdrawAmount);
			map.put("weekStatisticsChargeList", weekStatisticsChargeList);
			map.put("weekStatisticsWithdrawList", weekStatisticsWithdrawList);
			map.put("totalCount", totalCount);
			
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
		
	}
	
	/**
	 * 
	* @Title: getTradeStatisticsByProjectId  
	* @Description: 根据项目方id获取交易统计列表  
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/tradeStatistics/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getTradeStatisticsByProjectId(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int trademappingId,
			@RequestParam(required=false,defaultValue="1") int currentPage,
			@RequestParam(required=false,defaultValue="8") int pageNum,
			@RequestParam(required=false,defaultValue="") String startDate,
			@RequestParam(required=false,defaultValue="") String endDate,
			@RequestParam(required=false,defaultValue="0") int marketId) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			
			if(StringUtils.isNotBlank(startDate)){
				filter.append(" and  statisticalDate >= '"+startDate+"' \n");
			}
			
			if(StringUtils.isNotBlank(endDate)){
				filter.append(" and  statisticalDate <= '"+endDate+"' \n");
			}
			
			if(0 != marketId){
				filter.append(" and  trademappingId.fid = "+marketId+" \n");
			}
			
			filter.append(" order by createTime desc");
			
			List<PtradeStatistics> ptradeStatisticsList = frontPstatisticsDataService.
					tradeStatisticsList((currentPage-1)*pageNum, pageNum, filter+"", true);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId","trademappingId"});
			JSONArray jsonStr = JSONArray.fromObject(ptradeStatisticsList, jsonConfig);
			
			int totalCount = this.adminService.getAllCount("PtradeStatistics", filter+"");
			
			//周曲线图----买入成交总额
			String sqlfilter = "select DATE_FORMAT(statistical_date,'%Y%m%d') day,"
					+ "	sum(buy_amount) amount from p_trade_statistics "
					+ " where project_id =" + fuser.getFid()
					+ " AND trademapping_id =" + trademappingId
					+ " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) "
					+ " group by day order by day";
			Map<Integer,Object> weekStatisticsBuyMap = adminService.getSQLValue1(sqlfilter);
			//按key排序
			weekStatisticsBuyMap = Utils.sortMapByKey(weekStatisticsBuyMap);
			List<EchartDataVo> weekStatisticsBuyList = new ArrayList<EchartDataVo>();
			if(null != weekStatisticsBuyMap) {
				for (Map.Entry<Integer, Object> entry : weekStatisticsBuyMap.entrySet()) {
					EchartDataVo echartDataVo = new EchartDataVo();
					echartDataVo.setName(entry.getKey().toString());
					echartDataVo.setValue(entry.getValue());
					weekStatisticsBuyList.add(echartDataVo);
				}
			}
			
			//周曲线图----卖出成交总额
			sqlfilter = "select DATE_FORMAT(statistical_date,'%Y%m%d') day,"
					+ "	sum(sell_amount) amount from p_trade_statistics "
					+ " where project_id =" + fuser.getFid() 
					+ " AND trademapping_id =" + trademappingId
					+ " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) "
					+ " group by day order by day";
			Map<Integer,Object> weekStatisticsSellMap = adminService.getSQLValue1(sqlfilter);
			//按key排序
			weekStatisticsSellMap = Utils.sortMapByKey(weekStatisticsSellMap);
			List<EchartDataVo> weekStatisticsSellList = new ArrayList<EchartDataVo>();
			if(null != weekStatisticsSellMap) {
				for (Map.Entry<Integer, Object> entry : weekStatisticsSellMap.entrySet()) {
					EchartDataVo echartDataVo = new EchartDataVo();
					echartDataVo.setName(entry.getKey().toString());
					echartDataVo.setValue(entry.getValue());
					weekStatisticsSellList.add(echartDataVo);
				}
			}
			
			
			sqlfilter = " where trademappingId.fid =" + trademappingId;
			//买入成交总额
			Double totalBuyAmount = adminService.getAllSum("PtradeStatistics", "buyAmount", sqlfilter);
			
			//卖出成交总额
			Double totalSellAmount = adminService.getAllSum("PtradeStatistics", "sellAmount", sqlfilter);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tradeStatisticsList", jsonStr);
			//买入成交，卖出成交
			map.put("totalBuyAmount", totalBuyAmount);
			map.put("totalSellAmount", totalSellAmount);
			map.put("weekStatisticsBuyList", weekStatisticsBuyList);
			map.put("weekStatisticsSellList", weekStatisticsSellList);
			map.put("totalCount", totalCount);
			
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
		
	}
	
	
	/**
	 * 
	* @Title: getEntrustStatisticsByProjectId  
	* @Description: 根据项目方id获取委托单统计列表  
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/entrustStatistics/list",produces=JsonEncode,method = { RequestMethod.GET })
	public String getEntrustStatisticsByProjectId(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int trademappingId,
			@RequestParam(required=false,defaultValue="1") int currentPage,
			@RequestParam(required=false,defaultValue="8") int pageNum,
			@RequestParam(required=false,defaultValue="") String startDate,
			@RequestParam(required=false,defaultValue="") String endDate,
			@RequestParam(required=false,defaultValue="0") int marketId) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where projectId.fid=" + fuser.getFid());
			
			if(StringUtils.isNotBlank(startDate)){
				filter.append(" and  statisticalDate >= '"+startDate+"' \n");
			}
			
			if(StringUtils.isNotBlank(endDate)){
				filter.append(" and  statisticalDate <= '"+endDate+"' \n");
			}
			
			if(0 != marketId){
				filter.append(" and  trademappingId.fid = "+marketId+" \n");
			}
			
			filter.append(" order by createTime desc");
			
			List<PentrustStatistics> pentrustStatisticsList = frontPstatisticsDataService.
					entrustStatisticsList((currentPage-1)*pageNum, pageNum, filter+"", true);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"projectId","trademappingId"});
			JSONArray jsonStr = JSONArray.fromObject(pentrustStatisticsList, jsonConfig);
			
			int totalCount = this.adminService.getAllCount("PentrustStatistics", filter+"");
			
			//周曲线图--买入挂单总额
			String sqlfilter = "select DATE_FORMAT(statistical_date,'%Y%m%d') day,"
					+ "	sum(buy_entrust_amount) amount from p_entrust_statistics"
					+ " where project_id =" + fuser.getFid()
					+ " AND trademapping_id =" + trademappingId
					+ " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) "
					+ " group by day order by day";
			Map<Integer,Object> weekStatisticsBuyMap = adminService.getSQLValue1(sqlfilter);
			//按key排序
			weekStatisticsBuyMap = Utils.sortMapByKey(weekStatisticsBuyMap);
			List<EchartDataVo> weekStatisticsBuyList = new ArrayList<EchartDataVo>();
			if(null != weekStatisticsBuyMap) {
				for (Map.Entry<Integer, Object> entry : weekStatisticsBuyMap.entrySet()) {
					EchartDataVo echartDataVo = new EchartDataVo();
					echartDataVo.setName(entry.getKey().toString());
					echartDataVo.setValue(entry.getValue());
					weekStatisticsBuyList.add(echartDataVo);
				}
			}
			
			//周曲线图--卖出挂单总额
			sqlfilter = "select DATE_FORMAT(statistical_date,'%Y%m%d') day,"
					+ "	sum(sell_entrust_amount) amount from p_entrust_statistics"
					+ " where project_id =" + fuser.getFid() 
					+ " AND trademapping_id =" + trademappingId
					+ " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) "
					+ " group by day order by day";
			Map<Integer,Object> weekStatisticsSellMap = adminService.getSQLValue1(sqlfilter);
			//按key排序
			weekStatisticsSellMap = Utils.sortMapByKey(weekStatisticsSellMap);
			List<EchartDataVo> weekStatisticsSellList = new ArrayList<EchartDataVo>();
			if(null != weekStatisticsSellMap) {
				for (Map.Entry<Integer, Object> entry : weekStatisticsSellMap.entrySet()) {
					EchartDataVo echartDataVo = new EchartDataVo();
					echartDataVo.setName(entry.getKey().toString());
					echartDataVo.setValue(entry.getValue());
					weekStatisticsSellList.add(echartDataVo);
				}
			}
			
			
			sqlfilter = " where trademappingId.fid =" + trademappingId;
			//买入挂单总额
			Double totalBuyEntrustAmount = adminService.getAllSum("PentrustStatistics", "buyEntrustAmount", sqlfilter);
			
			//卖出挂单总额
			Double totalSellEntrustAmount = adminService.getAllSum("PentrustStatistics", "sellEntrustAmount", sqlfilter);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("entrustStatisticsList", jsonStr);
			//买入挂单，卖出挂单
			map.put("totalBuyEntrustAmount", totalBuyEntrustAmount);
			map.put("totalSellEntrustAmount", totalSellEntrustAmount);
			map.put("weekStatisticsBuyList", weekStatisticsBuyList);
			map.put("weekStatisticsSellList", weekStatisticsSellList);
			map.put("totalCount", totalCount);
			
			return new JsonResult(ResultCode.SUCCESS, "success", JSONObject.fromObject(map)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
		
	}
	
	
	
	/**
	 * 
	* @Title: entrustList  
	* @Description: 获取项目方市场的前300条委托订单记录(未成交)
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/project/entrustList",produces=JsonEncode,method = { RequestMethod.GET })
	public String entrustList(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int trademappingId) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			filter.append(" where fstatus = " + EntrustStatusEnum.Going +" \n");
			if(0 == trademappingId) {
				filter.append(" and ftrademapping.fid in (select fid from Ftrademapping where fprojectId = " + fuser.getFid() + ") \n");
			} else {
				filter.append(" and ftrademapping.fid = " + trademappingId + " \n");
			}
			filter.append(" order by flastUpdatTime desc \n");
			
			List<Fentrust> entrustList = this.entrustService.list(0, 300, filter+"",true);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"fuser"});
			JSONArray jsonStr = JSONArray.fromObject(entrustList, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	/**
	 * 
	* @Title: entrustlogList  
	* @Description: 获取项目方市场的前300条成交记录
	* @author Ryan
	* @param @param request
	* @param @return
	* @param @throws Exception  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/project/entrustlogList",produces=JsonEncode,method = { RequestMethod.GET })
	public String entrustlogList(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int trademappingId) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			StringBuffer filter = new StringBuffer();
			if(0 == trademappingId) {
				filter.append(" where ftrademapping.fid in (select fid from Ftrademapping where fprojectId = " + fuser.getFid() + ") \n");
			} else {
				filter.append(" where ftrademapping.fid = " + trademappingId + " \n");
			}
			filter.append(" order by fcreateTime desc \n");
			
			List<Fentrustlog> entrustlogList = this.entrustlogService.entrustlogList(0, 300, filter+"",true);
			JsonConfig jsonConfig  = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"fentrust"});
			JSONArray jsonStr = JSONArray.fromObject(entrustlogList, jsonConfig);
			
			return new JsonResult(ResultCode.SUCCESS, "success", jsonStr).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	
	
	private static enum ExportFiled {
		订单号,交易市场,委托类型,委托价格,委托数量,委托总额,状态,创建时间;
	}
	
	
	/**
	 * 导出项目方市场的前300条委托订单记录(未成交)
	 * @param request
	 * @param response
	 * @param trademappingId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/project/exportEntrustList",produces=JsonEncode,method = { RequestMethod.GET,RequestMethod.POST })
	public String exportEntrustList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=false,defaultValue="0") int trademappingId) throws Exception{
		try {
			Integer fid = GetSession(request).getFid();
//			Integer fid = 107676;
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			
			//查询委托list
			StringBuffer filter = new StringBuffer();
			filter.append(" where fstatus = " + EntrustStatusEnum.Going +" \n");
			if(0 == trademappingId) {
				filter.append(" and ftrademapping.fid in (select fid from Ftrademapping where fprojectId = " + fuser.getFid() + ") \n");
			} else {
				filter.append(" and ftrademapping.fid = " + trademappingId + " \n");
			}
			filter.append(" order by flastUpdatTime desc \n");
			
			List<Fentrust> entrustList = this.entrustService.list(0, 300, filter+"",true);
			
			if(null==entrustList || entrustList.size()==0) {
				return new JsonResult(ResultCode.SUCCESS, "success", "无数据需要导出").toString();
			}
			
			//开始进行导出操作
			response.setContentType("Application/excel");
			response.addHeader("Content-Disposition", "attachment;filename=exportEntrustList.xls");
			
			XlsExport e = new XlsExport();
			int rowIndex = 0;

			// header
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				e.setCell(filed.ordinal(), filed.toString());
			}
			
			for (Fentrust order : entrustList) {
				e.createRow(rowIndex++);
				for (ExportFiled filed : ExportFiled.values()) {
					switch (filed) {
					case 订单号:
						e.setCell(filed.ordinal(), order.getFid());
						break;
					case 交易市场:
						e.setCell(filed.ordinal(), order.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getfShortName()+"/"
									+order.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getfShortName());
						break;
					case 委托类型:
						e.setCell(filed.ordinal(), order.getFentrustType_s());
						break;
					case 委托价格:
						e.setCell(filed.ordinal(), order.getFprize());
						break;
					case 委托数量:
						e.setCell(filed.ordinal(), order.getFcount());
						break;
					case 委托总额:
						e.setCell(filed.ordinal(), order.getFamount());
						break;
					case 状态:
						e.setCell(filed.ordinal(), order.getFstatus_s());
						break;
					case 创建时间:
						e.setCell(filed.ordinal(), Utils.dateFormat(order.getFcreateTime()));
						break;
					default:
						break;
					}
				}
			}
			
			e.exportXls(response);
			response.getOutputStream().close();
			//结束导出操作
			
			return new JsonResult(ResultCode.SUCCESS, "success", "导出成功").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
}
