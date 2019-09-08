package com.gt.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;

public class HbdmRestApi {
	
	public static final String HUOBI_FUTURE_ORDER 				= "/v1/order/orders/place";
	public static final String HUOBI_FUTURE_ORDER_CANCEL 		= "/v1/order/orders/{order-id}/submitcancel";
	public static final String HUOBI_FUTURE_DEPTH 				= "/market/depth";
	public static final String HUOBI_FUTURE_KLINE 				= "/market/history/kline";
	public static final String HUOBI_FUTURE_TICKER 				= "/market/detail/merged";
	public static final String HUOBI_FUTURE_TRADE 				= "/market/history/trade";
	public static final String HUOBI_FUTURE_TRADE_DETAIL 		= "/market/trade";
	public static final String HUOBI_FUTURE_MARKET_DETAIL 		= "/market/detail";
	public static final String HUOBI_FUTURE_ORDER_OPEN_ORDERS 	= "/v1/order/openOrders";
	public static final String api_key 							= ""; 							// huobi申请的apiKey
	public static final String secret_key 						= ""; 							// huobi申请的secretKey
	public static final String url_prex 						= "https://api.hbdm.com";		//火币api接口地址https://api.hbdm.com

	/**
	 * 用户下单
	 * 
	 * @param symbol
	 *            "BTC","ETH"...
	 * @param contractType
	 *            合约类型: this_week:当周 next_week:下周 month:当月 quarter:季度
	 * @param contractCode
	 *            BTC1403
	 * @param client_order_id
	 *            客户自己填写和维护，这次一定要大于上一次
	 * @param price
	 *            价格
	 * @param volume
	 *            委托数量(张)
	 * @param direction
	 *            "buy":买 "sell":卖
	 * @param offset
	 *            "open":开 "close":平
	 * @param leverRate
	 *            杠杆倍数[“开仓”若有10倍多单，就不能再下20倍多单]
	 * @param orderPriceType
	 *            "limit":限价 "opponent":对手价
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureContractOrder(String symbol, String contractType, String contractCode, String clientOrderId,
			String price, String volume, String direction, String offset, String leverRate, String orderPriceType)
			throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		if (!StringUtils.isEmpty(contractType)) {
			params.put("contract_type", contractType);
		}

		if (!StringUtils.isEmpty(contractCode)) {
			params.put("contract_code", contractCode);
		}
		if (!StringUtils.isEmpty(clientOrderId)) {
			params.put("client_order_id", clientOrderId);
		}
		if (!StringUtils.isEmpty(price)) {
			params.put("price", price);
		}
		if (!StringUtils.isEmpty(volume)) {
			params.put("volume", volume);
		}
		if (!StringUtils.isEmpty(direction)) {
			params.put("direction", direction);
		}
		if (!StringUtils.isEmpty(offset)) {
			params.put("offset", offset);
		}
		if (!StringUtils.isEmpty(leverRate)) {
			params.put("lever_rate", leverRate);
		}
		if (!StringUtils.isEmpty(orderPriceType)) {
			params.put("order_price_type", orderPriceType);
		}
		String res = HbdmHttpClient.getInstance().call(api_key, secret_key, "POST", url_prex + HUOBI_FUTURE_ORDER, params, new HashMap<String,String>());
		return res;
	}
	
	/**
	 * 撤销订单
	 * 
	 * @param orderId
	 *            订单ID（ 多个订单ID中间以","分隔,一次最多允许撤消50个订单 ）
	 * @return clientOrderId 客户订单ID(多个订单ID中间以","分隔,一次最多允许撤消50个订单)
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureContractCancel(String orderId, String clientOrderId,String symbol) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(orderId)) {
			params.put("order_id", orderId);
		}
		if (!StringUtils.isEmpty(clientOrderId)) {
			params.put("client_order_id", clientOrderId);
		}
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		String res = HbdmHttpClient.getInstance().call(api_key, secret_key, "POST", url_prex + HUOBI_FUTURE_ORDER_CANCEL, params, new HashMap<String,String>());
		return res;
	}
	
	/**
	 * 获取K线数据
	 * 
	 * @param symbol
	 *            "BTC","ETH"...
	 * @param period
	 *            K线类型 1min, 5min, 15min, 30min, 60min, 1hour,4hour,1day, 1mon
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureMarketHistoryKline(String symbol, String period,String size) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		if (!StringUtils.isEmpty(period)) {
			params.put("period", period);
		}
		if (!StringUtils.isEmpty(size)) {
			params.put("size", size);
		}
		String res = HbdmHttpClient.getInstance().doGet(url_prex + HUOBI_FUTURE_KLINE, params);
		return res;
	}
	
	/**
	 * 获取行情深度数据
	 * 
	 * @param symbol
	 *            "BTC","ETH"...
	 * @param type
	 *            step0, step1, step2, step3, step4, step5（合并深度0-5）；step0时，不合并深度
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureMarketDepth(String symbol, String type) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		if (!StringUtils.isEmpty(type)) {
			params.put("type", type);
		}
		String contractinfoRes = HbdmHttpClient.getInstance().doGet(url_prex + HUOBI_FUTURE_DEPTH, params);
		return contractinfoRes;
	}
	
	/**
	 * 获取聚合行情
	 * 
	 * @param symbol
	 *            如"BTC_CW"表示BTC当周合约，"BTC_NW"表示BTC次周合约，"BTC_CQ"表示BTC季度合约
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureMarketDetailMerged(String symbol) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}		
		String res = HbdmHttpClient.getInstance().doGet(url_prex + HUOBI_FUTURE_TICKER, params);
		return res;
	}
	
	/**
	 * 获取市场最近成交记录
	 * 
	 * @param symbol
	 *            如"BTC_CW"表示BTC当周合约，"BTC_NW"表示BTC次周合约，"BTC_CQ"表示BTC季度合约
	 * @return size 获取交易记录的数量 [1, 2000]
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureMarketDetailTrade(String symbol, String size) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		if (!StringUtils.isEmpty(size)) {
			params.put("size", size);
		}
		String res = HbdmHttpClient.getInstance().doGet(url_prex + HUOBI_FUTURE_TRADE_DETAIL, params);
		return res;
	}

	/**
	 * 批量获取最近的交易记录
	 * 
	 * @param symbol
	 *            如"BTC_CW"表示BTC当周合约，"BTC_NW"表示BTC次周合约，"BTC_CQ"表示BTC季度合约
	 * @return size 获取交易记录的数量 [1, 2000]
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureMarketHistoryTrade(String symbol, String size) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		if (!StringUtils.isEmpty(size)) {
			params.put("size", size);
		}
		String res = HbdmHttpClient.getInstance().doGet(url_prex + HUOBI_FUTURE_TRADE, params);
		return res;
	}

	/**
	 * 	滚动24小时交易聚合行情(单个symbol)
	 * 
	 * @param symbol
	 *            如"BTC_CW"表示BTC当周合约，"BTC_NW"表示BTC次周合约，"BTC_CQ"表示BTC季度合约
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String futureMarketDetail(String symbol) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		String res = HbdmHttpClient.getInstance().doGet(url_prex + HUOBI_FUTURE_MARKET_DETAIL, params);
		return res;
	}

	/**
	 * 	滚动24小时交易聚合行情(单个symbol)
	 * 
	 * @param symbol
	 *            如"BTC_CW"表示BTC当周合约，"BTC_NW"表示BTC次周合约，"BTC_CQ"表示BTC季度合约
	 *            account-id 和 symbol 需同时指定或者二者都不指定。如果二者都不指定，返回最多500条尚未成交订单，按订单号降序排列
	 * @param 主动交易方向 buy或者sell，缺省将返回所有符合条件尚未成交订单
	 * @param accountId 账户id
	 * @param size所需返回记录数[0,500] 默认为10
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String orderOpenOrders(String symbol, String accountId,
			String side, String size) {
		Map<String, String> params = new HashMap<>();
		if (!StringUtils.isEmpty(symbol)) {
			params.put("symbol", symbol);
		}
		if (!StringUtils.isEmpty(accountId)) {
			params.put("account-id", accountId);
		}
		if (!StringUtils.isEmpty(side)) {
			params.put("side", side);
		}
		if (!StringUtils.isEmpty(size)) {
			params.put("size", size);
		}
		String res = HbdmHttpClient.getInstance().doGet(url_prex + HUOBI_FUTURE_ORDER_OPEN_ORDERS, params);
		return res;
	}
}
