package com.gt.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.result.JsonResult;
import com.gt.result.ResultCode;
import com.gt.util.HbdmRestApi;
import com.gt.util.SubmissionLimitUtil;
import com.gt.util.redis.RedisCacheUtil;

/**
 * 
* @ClassName: HbApiController  
* @Description: 火币api接口  
* @author Ryan  
* @date 2018年12月12日
* @version   
*
 */
@Controller
public class HbApiController extends BaseController{
	
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HbApiController.class);
	
	/**
	 * 
	* @Title: orderSubmit  
	* @Description: 合约下单 
	* @author Ryan
	* @param @param request  
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/v1/order/orders/place", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String orderPlace(HttpServletRequest request){
		String contractOrder;
		try {
			//合约下单
			contractOrder = HbdmRestApi.futureContractOrder("BTC", "this_week", "BTC181116", "", "6188", "12", "buy", "open", "10", "limit");
			LOGGER.info("合约下单返回" + contractOrder);
			return contractOrder;
		} catch (HttpException | IOException e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: cancelSubmit  
	* @Description: 合约撤单 
	* @author Ryan
	* @param @param request
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/v1/order/orders/{order-id}/submitcancel", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String cancelSubmit(HttpServletRequest request){
		String contractcancel;
		try {
			//合约撤单
			contractcancel = HbdmRestApi.futureContractCancel("123556", "","BTC");
			LOGGER.info("合约取消订单" + contractcancel);
			return contractcancel;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: marketHistoryKline  
	* @Description: 获取K线数据 
	* @author Ryan
	* @param @param request
	* @param @return  
	* @return String
	* @throws
	 */
	//GET /market/history/kline
	@ResponseBody
	@RequestMapping(value="/market/history/kline", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String marketHistoryKline(HttpServletRequest request){
		String historyKline;
		try {
			SubmissionLimitUtil.orderSubmissionLimit("order");
			// 获取K线数据
			historyKline = HbdmRestApi.futureMarketHistoryKline("BTC_CW", "15min","100");
			LOGGER.info("获取K线数据" + historyKline);
			return historyKline;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: marketTickers  
	* @Description: 获取聚合行情 
	* @author Ryan
	* @param @param request
	* @param @return  
	* @return String
	* @throws
	 */
	//	GET /market/detail/merged
	@ResponseBody
	@RequestMapping(value="/market/detail/merged", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String marketTickers(HttpServletRequest request){
		try {
			// 获取聚合行情
			String merged = HbdmRestApi.futureMarketDetailMerged("BTC_CW");
			LOGGER.info("获取聚合行情" + merged);
			return merged;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: marketDepth  
	* @Description: 市场深度行情（单个symbol）
	* @author Ryan
	* @param @param request
	* @param @return  
	* @return String
	* @throws
	 */
	//GET /market/depth
	@ResponseBody
	@RequestMapping(value="/market/depth", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String marketDepth(HttpServletRequest request){
		try {
			// 获取行情深度数据
			String marketDepth = HbdmRestApi.futureMarketDepth("BTC_CW", "step0");
			LOGGER.info("获取行情深度数据" + marketDepth);
			return marketDepth;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: marketTradeDetail  
	* @Description: 单个symbol最新成交记录 
	* @author Ryan
	* @param @param request
	* @param @return  
	* @return String
	* @throws
	 */
	//GET /market/trade 获取 Trade Detail 数据
	@ResponseBody
	@RequestMapping(value="/market/trade", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String marketTrade(HttpServletRequest request){
		try {
			// 获取市场最近成交记录
			String trade = HbdmRestApi.futureMarketDetailTrade("BTC_CW", "200");
			LOGGER.info("获取市场最近成交记录" + trade);

			return trade;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: marketTradeHistory  
	* @Description: 单个symbol批量成交记录 
	* @author Ryan
	* @param @param request
	* @param @return  
	* @return String
	* @throws
	 */
	//GET /market/history/trade
	@ResponseBody
	@RequestMapping(value="/market/history/trade", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String marketTradeHistory(HttpServletRequest request){
		try {
			// 批量获取最近的交易记录
			String historTrade = HbdmRestApi.futureMarketHistoryTrade("BTC_CW", "10");
			LOGGER.info("批量获取最近的交易记录" + historTrade);

			return historTrade;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
	/**
	 * 
	* @Title: marketDetail  
	* @Description: 滚动24小时交易聚合行情(单个symbol) 
	* @author Ryan
	* @param @param request
	* @param @return  
	* @return String
	* @throws
	 */
	//GET /market/detail
	@ResponseBody
	@RequestMapping(value="/market/detail", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String marketDetail(HttpServletRequest request){
		try {
			// 批量获取最近的交易记录
			String marketDetail = HbdmRestApi.futureMarketDetail("BTC_CW");
			LOGGER.info("滚动24小时交易聚合行情(单个symbol)" + marketDetail);

			return marketDetail;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(ResultCode.SYS_ERROR, "网络异常").toString();
		}
	}
	
}
