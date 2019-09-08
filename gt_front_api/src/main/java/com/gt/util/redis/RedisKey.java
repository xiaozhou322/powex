package com.gt.util.redis;
/**
 * 
* @ClassName: RedisKey  
* @Description: 构建key工具类 
* @author Ryan  
* @date 2018年7月24日
* @version   
*
 */
public class RedisKey {
	
	public final static String ADMIN 	= "admin:";		//后台系统
	public final static String FRONT 	= "front:";		//前台系统
	
	/**
	 * 
	* @Title: getAllLatestDealPrize  
	* @Description: 构建全部最后的成交价格 key
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllLatestDealPrize() {
		return new StringBuilder(FRONT).append("latest_deal_prize:").append("all").toString();
	}
	
	/**
	 * 
	* @Title: getLatestDealPrize  
	* @Description: 根据id构建全部最新成交价格 key
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getLatestDealPrize(int id){
		return new StringBuilder(FRONT).append("latest_deal_prize:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getAllLowestPrize24  
	* @Description: 构建全部24小时最低价格 key
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllLowestPrize24(){
		return new StringBuilder(FRONT).append("lowest_prize_24:").append("all").toString();
	}
	
	/**
	 * 
	* @Title: getAllLowestPrize24  
	* @Description: 根据id构建24小时最低价格key 
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getLowestPrize24(int id){
		return new StringBuilder(FRONT).append("lowest_prize_24:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getAllHighestPrize24  
	* @Description: 构建全部24小时最高价格 key
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllHighestPrize24(){
		return new StringBuilder(FRONT).append("highest_prize_24:").append("all").toString();
	}
	
	/**
	 * 
	* @Title: getHighestPrize24  
	* @Description: 根据id构建24小时最高价格 key
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getHighestPrize24(int id){
		return new StringBuilder(FRONT).append("highest_prize_24:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getAllTotalDeal24  
	* @Description: 构建所有24小时的成交量 key
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllTotalDeal24(){
		return new StringBuilder(FRONT).append("total_deal_24:").append("all").toString();
	}
	
	/**
	 * 
	* @Title: getTotalDeal24  
	* @Description: 根据id构建24小时的成交量 key
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getTotalDeal24(int id){
		return new StringBuilder(FRONT).append("total_deal_24:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getAllStart24Price  
	* @Description: 构建所有24小时的初始价格 key
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllStart24Price(){
		return new StringBuilder(FRONT).append("start_24_price:").append("all").toString();
	}
	
	/**
	 * 
	* @Title: getStart24Price  
	* @Description: 根据id构建24小时的初始价格 key
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getStart24Price(int id){
		return new StringBuilder(FRONT).append("start_24_price:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getAllTotalRMB24  
	* @Description: 构建24小时所有的人民币 key
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllTotalRMB24(){
		return new StringBuilder(FRONT).append("total_rmb_24:").append("all").toString();
	}
	
	/**
	 * 
	* @Title: getTotalRMB24  
	* @Description: 根据id构建24小时的人民币key 
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getTotalRMB24(int id){
		return new StringBuilder(FRONT).append("total_rmb_24:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getPeriod  
	* @Description: 根据id，key构建行情key 
	* @author Ryan
	* @param @param id
	* @param @param key
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getPeriod(int id,int key){
		return new StringBuilder(FRONT).append("period:").append(id).append(":").append(key).toString();
	}
	
	/**
	 * 
	* @Title: getJsonString  
	* @Description: 根据id，key构建k线图Json的key  
	* @author Ryan
	* @param @param id
	* @param @param key
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getJsonString(int id,int key){
		return new StringBuilder(FRONT).append("json:").append(id).append(":").append(key).toString();
	}
	
	/**
	 * 
	* @Title: getIndexJsonString  
	* @Description: 根据id，key构建k线图indexJson的key 
	* @author Ryan
	* @param @param id
	* @param @param key
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getIndexJsonString(int id,int key){
		return new StringBuilder(FRONT).append("index:json:").append(id).append(":").append(key).toString();
	}
	
	/**
	 * 
	* @Title: getBuyDepth  
	* @Description: 根据id构建买入深度key 
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getBuyDepth(int id){
		return new StringBuilder(FRONT).append("buy:depth:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getSellDepth 
	* @Description: 根据id构建卖出深度key 
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getSellDepth(int id){
		return new StringBuilder(FRONT).append("sell:depth:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getLowestSellPrize  
	* @Description: 根据id构建最低的卖出价格key
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getLowestSellPrize(int id){
		return new StringBuilder(FRONT).append("lowest:sell:prize:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getHighestBuyPrize  
	* @Description: 根据id构建最高买入价格key 
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getHighestBuyPrize(int id){
		return new StringBuilder(FRONT).append("highest:buy:prize:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getEntrustSuccess  
	* @Description: 根据id构建成功的委托单key 
	* @author Ryan
	* @param @param id
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getEntrustSuccess(int id){
		return new StringBuilder(FRONT).append("entrust:success:").append(id).toString();
	}
	
	/**
	 * 
	* @Title: getRates  
	* @Description: 构建汇率key 
	* @author Ryan
	* @param @param vid
	* @param @param isbuy
	* @param @param level
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getRates(int vid,boolean isbuy,int level){
		return new StringBuilder(FRONT).append("rate:").append(vid+"_"+(isbuy?"buy":"sell")+"_"+level).toString();
	}
	
	/**
	 * 
	* @Title: getSystemArgsVersion  
	* @Description: 构建constantMap中的系统参数版本号  
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getSystemArgsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("systemargs:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getAllCoinsVersion  
	* @Description: 构建constantMap中的全部币种版本号  
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllCoinsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("allcoins:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getVirtualCoinTypeVersion  
	* @Description: 构建constantMap中的币种类型版本号  
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getVirtualCoinTypeVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("virtualcointype:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getAllWithdrawCoinsVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllWithdrawCoinsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("allwithdrawcoins:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getAllRechargeCoinsVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllRechargeCoinsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("allrechargecoins:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getAllTransferCoinsVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getAllTransferCoinsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("alltransfercoins:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getFfriendLinksVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getFfriendLinksVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("ffriendlinks:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getQunsVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getQunsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("quns:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getWebInfoVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getWebInfoVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("webinfo:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getNewsVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getNewsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("news:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getFbannersVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getFbannersVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("fbanners:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getFbsVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getFbsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("fbs:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getTradeMappingsVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getTradeMappingsVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("tradeMappings:").append("version").toString();
	}
	
	/**
	 * 
	* @Title: getTradeMappingssVersion  
	* @Description: TODO 
	* @author Ryan
	* @param @return  
	* @return String
	* @throws
	 */
	public static String getTradeMappingssVersion(){
		return new StringBuilder(ADMIN).append("constant_map:").append("tradeMappingss:").append("version").toString();
	}
	
	
	/**
	 * getOTCOnlineUserList
	 * @return
	 */
	public static String getOTCOnlineUserList(){
		return new StringBuilder(FRONT).append("otc:").append("onlineUserList").toString();
	}
}
