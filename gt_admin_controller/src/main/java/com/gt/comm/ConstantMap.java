/*package com.gt.comm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.FriendLinkService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.TradehistoryService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontSystemArgsService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;

public class ConstantMap {
	private static final Logger log = LoggerFactory.getLogger(ConstantMap.class);
	@Autowired
	private FrontSystemArgsService frontSystemArgsService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FriendLinkService friendLinkService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private TradehistoryService tradehistoryService;
	@Autowired
	private FrontOthersService frontOtherService;
	@Autowired
	private UtilsService utilsService;
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	private Map<String, Object> map = new HashMap<String, Object>() ;
	public void init(){
		log.info("Init SystemArgs ==> ConstantMap.") ;
		Map<String, Object> tMap = this.frontSystemArgsService.findAllMap() ;
		for (Map.Entry<String, Object> entry : tMap.entrySet()) {
			this.put(entry.getKey(), entry.getValue()) ;
		}
		log.info("Init virtualCoinType ==> ConstantMap.") ;
		
		String sql = "where fstatus=1";
		List<Fvirtualcointype> allCoins= this.virtualCoinService.list(0, 0, sql, false);
		map.put("allCoins", allCoins) ;
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		map.put("virtualCoinType", fvirtualcointypes) ;
		
		{
			String filter = "where fstatus=1 and FIsWithDraw=1 and (ftype ="+CoinTypeEnum.FB_COIN_VALUE +" or ftype ="+CoinTypeEnum.COIN_VALUE+")";
			List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, filter, false);
			map.put("allWithdrawCoins", allWithdrawCoins) ;
		}
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and (ftype ="+CoinTypeEnum.FB_COIN_VALUE +" or ftype ="+CoinTypeEnum.COIN_VALUE+")";
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			map.put("allRechargeCoins", allRechargeCoins) ;
		}
		
		{
			String filter = "where fstatus=1 and fisTransfer=1 and (ftype ="+CoinTypeEnum.FB_COIN_VALUE +" or ftype ="+CoinTypeEnum.COIN_VALUE+")";
			List<Fvirtualcointype> allTransferCoins= this.virtualCoinService.list(0, 0, filter, false);
			map.put("allTransferCoins", allTransferCoins) ;
		}
		
		{
			String filter = "where ftype="+LinkTypeEnum.LINK_VALUE+" order by forder asc";
			List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
			map.put("ffriendlinks", ffriendlinks) ;
		}
		
		{
			String filter = "where ftype="+LinkTypeEnum.QQ_VALUE+" order by forder asc";
			List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
			map.put("quns", ffriendlinks) ;
		}
		
		map.put("webinfo", this.frontSystemArgsService.findFwebbaseinfoById(1)) ;
		
		{
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = sdf.format(c.getTime());
			String xx = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+key+"'";
			List<Ftradehistory> ftradehistorys = this.tradehistoryService.list(0, 0, xx, false);
			map.put("tradehistory", ftradehistorys);
		}
		
		List<Farticle> farticles = this.frontOtherService.findFarticle(1, 0, 5) ;
		if(farticles != null && farticles.size() >0){
			map.put("news", farticles);
		}
		
		List<Fbanner> fbanners = this.frontOtherService.findFbanner("index", 0, 5) ;
		if(fbanners != null && fbanners.size() >0){
			map.put("fbanners", fbanners);
		}
		
		String filter = " select distinct a.fvirtualcointypeByFvirtualcointype1 from  Ftrademapping a where a.fstatus=? order by a.fvirtualcointypeByFvirtualcointype1.ftype asc" ;
		List<Fvirtualcointype> fbs = this.utilsService.findHQL(0, 0, filter, false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE ) ;
		map.put("fbs", fbs);
		
		Map<Integer,Integer> tradeMappings = new HashMap<Integer,Integer>();
		String sql1 = "where fstatus="+TrademappingStatusEnum.ACTIVE;
		List<Ftrademapping> mappings = this.tradeMappingService.list(0, 0, sql1, false);
		for (Ftrademapping ftrademapping : mappings) {
			tradeMappings.put(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid(), ftrademapping.getFid());
		}
		map.put("tradeMappings", tradeMappings);
		map.put("tradeMappingss", mappings);
		
		{

			List<Ftrademapping> all = this.tradeMappingService.list(0, 0, " where fstatus="+TrademappingStatusEnum.ACTIVE+" ", false) ;
			Map<Integer,List> ftradehistory7D = new HashMap<Integer,List>();
			for (Ftrademapping ftrademapping : all) {
				List<String> day7String = Day7UpsDowns.getDays(7) ;
				List day7 = new ArrayList();
				for (String s : day7String) {
					String sql2 = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+s+"' and ftrademapping.fid="+ftrademapping.getFid();
					List<Ftradehistory> ss = this.tradehistoryService.list(0, 1, sql2, true);
					if(ss != null && ss.size() >0){
						day7.add(ss.get(0).getFprice()) ;
					}else{
						day7.add(0d);
					}
				}
				System.out.print(day7);
				ftradehistory7D.put(ftrademapping.getFid().intValue(), day7);
			}
			map.put("ftradehistory7D", ftradehistory7D);
		}
	}
	
	public Map<String, Object> getMap(){
		return this.map ;
	}
	
	public synchronized void put(String key,Object value){
		log.info("ConstantMap put key:"+key+",value:"+value+".") ;
		map.put(key, value) ;
		
		//生成redis版本号
		if(key.equals("allCoins")||key.equals("virtualCoinType")||key.equals("allWithdrawCoins")||key.equals("allRechargeCoins")
				||key.equals("allTransferCoins")||key.equals("ffriendlinks")||key.equals("quns")||key.equals("webinfo")||key.equals("news")
				||key.equals("fbanners")||key.equals("fbs")||key.equals("tradeMappings")||key.equals("tradeMappingss")){
			
			StringBuilder keyBuilder = new StringBuilder();
			keyBuilder.append("admin:").append("constant_map:").append(key.toLowerCase()+":").append("version").toString();
			String version = redisCacheUtil.getCacheString(keyBuilder.toString());
			redisCacheUtil.setCacheObject(keyBuilder.toString(), version == null?"1":(Integer.valueOf(version)+1)+"");
		}else{
			String version = redisCacheUtil.getCacheString(RedisKey.getSystemArgsVersion());
			redisCacheUtil.setCacheObject(RedisKey.getSystemArgsVersion(), version == null?"1":(Integer.valueOf(version)+1)+"");
		}
	}
	
	public Object get(String key){
		return map.get(key) ;
	}
	
	public String getString(String key){
		return (String)map.get(key) ;
	}
}
*/