package com.gt.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.LinkTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.entity.Farticle;
import com.gt.entity.Fbanner;
import com.gt.entity.Ffriendlink;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.FriendLinkService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontSystemArgsService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;


/**
 * 检查刷新ConstantMap缓存定时任务
 * @author zhouyong
 *
 */
public class AutoRefreshConstantMap {
	private static final Logger log = LoggerFactory.getLogger(AutoRefreshConstantMap.class);
	
	@Autowired
	private FrontSystemArgsService frontSystemArgsServiceImpl ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinServiceImpl ;
	@Autowired
	private FriendLinkService friendLinkServiceImpl;
	@Autowired
	private VirtualCoinService virtualCoinServiceImpl;
	@Autowired
	private FrontOthersService frontOthersServiceImpl;
	@Autowired
	private UtilsService utilsServiceImpl;
	@Autowired
	private TradeMappingService tradeMappingServiceImpl;
	@Autowired
	private FrontConstantMapService frontConstantMapServiceImpl;
	
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	/**
	 * 检查刷新ConstantMap缓存定时任务入口
	 */
	public void work() {
		synchronized (this) {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行检查刷新ConstantMap缓存<<<<<<<<<<<<<<<<<<<<<<<<<<");
			try{
				
				Integer redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getSystemArgsVersion());
				Integer mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getSystemArgsVersion());
				if(redisVersion != mapVersion) {
					log.info("Refresh SystemArgs ==> ConstantMap.") ;
					Map<String, Object> tMap = this.frontSystemArgsServiceImpl.findAllMap() ;
					for (Map.Entry<String, Object> entry : tMap.entrySet()) {
						frontConstantMapServiceImpl.put(entry.getKey(), entry.getValue());
					}
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getSystemArgsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getAllCoinsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getAllCoinsVersion());
				if(redisVersion != mapVersion) {
					log.info("Refresh Fvirtualcointype ==> ConstantMap.") ;
					
					String sql = "where fstatus=1";
					List<Fvirtualcointype> allCoins= this.virtualCoinServiceImpl.list(0, 0, sql, false);
					frontConstantMapServiceImpl.put("allCoins", allCoins) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getAllCoinsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getVirtualCoinTypeVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getVirtualCoinTypeVersion());
				if(redisVersion != mapVersion) {
					List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinServiceImpl.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
					frontConstantMapServiceImpl.put("virtualCoinType", fvirtualcointypes) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getVirtualCoinTypeVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getAllWithdrawCoinsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getAllWithdrawCoinsVersion());
				if(redisVersion != mapVersion) {
					String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinServiceImpl.list(0, 0, filter, false);
					frontConstantMapServiceImpl.put("allWithdrawCoins", allWithdrawCoins) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getAllWithdrawCoinsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getAllRechargeCoinsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getAllRechargeCoinsVersion());
				if(redisVersion != mapVersion) {
					String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> allRechargeCoins= this.virtualCoinServiceImpl.list(0, 0, filter, false);
					frontConstantMapServiceImpl.put("allRechargeCoins", allRechargeCoins) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getAllRechargeCoinsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getAllTransferCoinsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getAllTransferCoinsVersion());
				if(redisVersion != mapVersion) {
					String filter = "where fstatus=1 and fisTransfer=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> allTransferCoins= this.virtualCoinServiceImpl.list(0, 0, filter, false);
					frontConstantMapServiceImpl.put("allTransferCoins", allTransferCoins) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getAllTransferCoinsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getFbsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getFbsVersion());
				if(redisVersion != mapVersion) {
					String filter = " select distinct a.fvirtualcointypeByFvirtualcointype1 from  Ftrademapping a where a.fstatus=? order by a.fvirtualcointypeByFvirtualcointype1.forder asc" ;
					List<Fvirtualcointype> fbs = this.utilsServiceImpl.findHQL(0, 0, filter, false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE ) ;
					frontConstantMapServiceImpl.put("fbs", fbs);
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getFbsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getFfriendLinksVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getFfriendLinksVersion());
				if(redisVersion != mapVersion) {
					log.info("Refresh Ffriendlink ==> ConstantMap.") ;
					String filter = "where ftype="+LinkTypeEnum.LINK_VALUE+" order by forder asc";
					List<Ffriendlink> ffriendlinks = this.friendLinkServiceImpl.list(0, 0, filter, false);
					frontConstantMapServiceImpl.put("ffriendlinks", ffriendlinks) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getFfriendLinksVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getQunsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getQunsVersion());
				if(redisVersion != mapVersion) {
					String filter = "where ftype="+LinkTypeEnum.QQ_VALUE+" order by forder asc";
					List<Ffriendlink> ffriendlinks = this.friendLinkServiceImpl.list(0, 0, filter, false);
					frontConstantMapServiceImpl.put("quns", ffriendlinks) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getQunsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getWebInfoVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getWebInfoVersion());
				if(redisVersion != mapVersion) {
					log.info("Refresh Fwebbaseinfo ==> ConstantMap.") ;
					
					frontConstantMapServiceImpl.put("webinfo", this.frontSystemArgsServiceImpl.findFwebbaseinfoById(1)) ;
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getWebInfoVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getNewsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getNewsVersion());
				if(redisVersion != mapVersion) {
					log.info("Refresh Farticle ==> ConstantMap.") ;
					
					List<Farticle> farticles = this.frontOthersServiceImpl.findFarticle(1, 0, 3) ;
					if(farticles != null && farticles.size() >0){
						frontConstantMapServiceImpl.put("news", farticles);
						
						frontConstantMapServiceImpl.putRedisMap(RedisKey.getNewsVersion(), redisVersion);
					}
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getFbannersVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getFbannersVersion());
				if(redisVersion != mapVersion) {
					log.info("Refresh Fbanner ==> ConstantMap.") ;
					
					List<Fbanner> fbanners = this.frontOthersServiceImpl.findFbanner("index", 0, 3) ;
					if(fbanners != null && fbanners.size() >0){
						frontConstantMapServiceImpl.put("fbanners", fbanners);
						
						frontConstantMapServiceImpl.putRedisMap(RedisKey.getFbannersVersion(), redisVersion);
					}
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getTradeMappingsVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getTradeMappingsVersion());
				if(redisVersion != mapVersion) {
					log.info("Refresh Ftrademapping ==> ConstantMap.") ;
					
					Map<Integer,Integer> tradeMappings = new HashMap<Integer,Integer>();
					String sql1 = "where fstatus="+TrademappingStatusEnum.ACTIVE;
					List<Ftrademapping> mappings = this.tradeMappingServiceImpl.list(0, 0, sql1, false);
					for (Ftrademapping ftrademapping : mappings) {
						tradeMappings.put(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid(), ftrademapping.getFid());
					}
					frontConstantMapServiceImpl.put("tradeMappings", tradeMappings);
					frontConstantMapServiceImpl.put("tradeMappingss", mappings);
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getTradeMappingsVersion(), redisVersion);
				}
				
				
				redisVersion = redisCacheUtil.getCacheInteger(RedisKey.getTradeMappingssVersion());
				mapVersion = frontConstantMapServiceImpl.getRedisMap(RedisKey.getTradeMappingssVersion());
				if(redisVersion != mapVersion) {
					Map<Integer,Integer> tradeMappings = new HashMap<Integer,Integer>();
					String sql1 = "where fstatus="+TrademappingStatusEnum.ACTIVE;
					List<Ftrademapping> mappings = this.tradeMappingServiceImpl.list(0, 0, sql1, false);
					for (Ftrademapping ftrademapping : mappings) {
						tradeMappings.put(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid(), ftrademapping.getFid());
					}
					frontConstantMapServiceImpl.put("tradeMappings", tradeMappings);
					frontConstantMapServiceImpl.put("tradeMappingss", mappings);
					
					frontConstantMapServiceImpl.putRedisMap(RedisKey.getTradeMappingssVersion(), redisVersion);
					
				}
				
			}catch (Exception e) {
				e.printStackTrace() ;
				log.error("检查刷新ConstantMap缓存异常", e.getMessage());
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>结束检查刷新ConstantMap缓存<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
	
}
