package com.gt.service.front;

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
import org.springframework.stereotype.Service;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.LinkTypeEnum;
import com.gt.Enum.PCoinTypeStatusEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.comm.Day7UpsDowns;
import com.gt.entity.Farticle;
import com.gt.entity.Fbanner;
import com.gt.entity.Ffriendlink;
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Pdepositcointype;
import com.gt.entity.Pdomain;
import com.gt.entity.Pfees;
import com.gt.entity.Pproject;
import com.gt.entity.Ptrademapping;
import com.gt.service.admin.FriendLinkService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.TradehistoryService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.util.DataOperationUtil;
import com.gt.util.redis.RedisKey;

@Service
public class FrontConstantMapServiceImpl implements FrontConstantMapService {
	private static final Logger log = LoggerFactory.getLogger(FrontConstantMapServiceImpl.class);
	@Autowired
	private FrontSystemArgsService frontSystemArgsServiceImpl ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinServiceImpl ;
	@Autowired
	private FriendLinkService friendLinkServiceImpl;
	@Autowired
	private VirtualCoinService virtualCoinServiceImpl;
	@Autowired
	private TradehistoryService tradehistoryServiceImpl;
	@Autowired
	private FrontOthersService frontOthersServiceImpl;
	@Autowired
	private UtilsService utilsServiceImpl;
	@Autowired
	private TradeMappingService tradeMappingServiceImpl;
	@Autowired
	private FrontPdomainService frontPdomainServiceImpl;
	@Autowired
	private FrontPprojectService frontPprojectServiceImpl;
	@Autowired
	private FrontPfeesService frontPfeesServiceImpl;
	@Autowired
	private FrontPtrademappingService frontPtrademappingServiceImpl;
	@Autowired
	private FrontPdepositcointypeService pdepositcointypeService;
	
	
	private static Map<String, Object> map = new HashMap<String, Object>() ;
	//对比redis缓存的内存map
	private static Map<String, Object> redisMap = new HashMap<String, Object>() ;
	public void init(){
		log.info("Init SystemArgs ==> ConstantMap.") ;
		Map<String, Object> tMap = this.frontSystemArgsServiceImpl.findAllMap() ;
		for (Map.Entry<String, Object> entry : tMap.entrySet()) {
			this.put(entry.getKey(), entry.getValue()) ;
		}
		log.info("Init virtualCoinType ==> ConstantMap.") ;
		map.put("sys_eth_nonce", "0") ;
		String sql = "where fstatus=1";
		List<Fvirtualcointype> allCoins= this.virtualCoinServiceImpl.list(0, 0, sql, false);
		map.put("allCoins", allCoins) ;
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinServiceImpl.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		map.put("virtualCoinType", fvirtualcointypes) ;
		
		{
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
			List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinServiceImpl.list(0, 0, filter, false);
			map.put("allWithdrawCoins", allWithdrawCoins) ;
		}
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinServiceImpl.list(0, 0, filter, false);
			map.put("allRechargeCoins", allRechargeCoins) ;
		}
		
		{
			String filter = "where fstatus=1 and fisTransfer=1 and  ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allTransferCoins= this.virtualCoinServiceImpl.list(0, 0, filter, false);
			map.put("allTransferCoins", allTransferCoins) ;
		}
		
		{
			String filter = "where ftype="+LinkTypeEnum.LINK_VALUE+" order by forder asc";
			List<Ffriendlink> ffriendlinks = this.friendLinkServiceImpl.list(0, 0, filter, false);
			map.put("ffriendlinks", ffriendlinks) ;
		}
		
		{
			String filter = "where ftype="+LinkTypeEnum.QQ_VALUE+" order by forder asc";
			List<Ffriendlink> ffriendlinks = this.friendLinkServiceImpl.list(0, 0, filter, false);
			map.put("quns", ffriendlinks) ;
		}
		
		map.put("webinfo", this.frontSystemArgsServiceImpl.findFwebbaseinfoById(1)) ;
		
		{
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = sdf.format(c.getTime());
			String xx = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+key+"'";
			List<Ftradehistory> ftradehistorys = this.tradehistoryServiceImpl.list(0, 0, xx, false);
			map.put("tradehistory", ftradehistorys);
		}
		
		List<Farticle> farticles = this.frontOthersServiceImpl.findFarticle(1, 0, 3) ;
		if(farticles != null && farticles.size() >0){
			map.put("news", farticles);
		}
		
		List<Fbanner> fbanners = this.frontOthersServiceImpl.findFbanner("index", 0, 3) ;
		if(fbanners != null && fbanners.size() >0){
			map.put("fbanners", fbanners);
		}
		
		List<Fbanner> fbannerProject = this.frontOthersServiceImpl.findFbanner("fbannerProject", 0, 3) ;
		if(fbannerProject != null && fbannerProject.size() >0){
			map.put("fbannerProject", fbannerProject);
		}
		
		String filter1 = " where ftype=index and fstatus=1 and projectId.fid!=0 order by fisding desc,id desc";
		List<Fbanner> projectBanners = this.frontOthersServiceImpl.findFbanner("index", 0, 3) ;
		
		
		String filter = " select distinct a.fvirtualcointypeByFvirtualcointype1 from  Ftrademapping a where a.fstatus=? order by a.fvirtualcointypeByFvirtualcointype1.forder asc" ;
		List<Fvirtualcointype> fbs = this.utilsServiceImpl.findHQL(0, 0, filter, false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE ) ;
		map.put("fbs", fbs);
		
		Map<Integer,Integer> tradeMappings = new HashMap<Integer,Integer>();
		String sql1 = "where fstatus="+TrademappingStatusEnum.ACTIVE;
		List<Ftrademapping> mappings = this.tradeMappingServiceImpl.list(0, 0, sql1, false);
		for (Ftrademapping ftrademapping : mappings) {
			tradeMappings.put(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid(), ftrademapping.getFid());
		}
		map.put("tradeMappings", tradeMappings);
		map.put("tradeMappingss", mappings);
		
		{

			List<Ftrademapping> all = this.tradeMappingServiceImpl.list(0, 0, " where fstatus="+TrademappingStatusEnum.ACTIVE+" ", false) ;
			Map<Integer,List> ftradehistory7D = new HashMap<Integer,List>();
			for (Ftrademapping ftrademapping : all) {
				List<String> day7String = Day7UpsDowns.getDays(7) ;
				List day7 = new ArrayList();
				for (String s : day7String) {
					String sql2 = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+s+"' and ftrademapping.fid="+ftrademapping.getFid();
					List<Ftradehistory> ss = this.tradehistoryServiceImpl.list(0, 1, sql2, true);
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
		
		//获取所有正常状态的项目方域名
		{
			filter = "where status=" + PCoinTypeStatusEnum.NORMAL+" and auditStatus="+AuditStatusEnum.auditPass+" order by id asc";
			List<Pdomain> pdomainList = this.frontPdomainServiceImpl.list(0, 0, filter, false);
			map.put("pdomainList", pdomainList) ;
		}
		
		//获取所有项目方信息
		{
			filter = "where 1 = 1 order by id asc";
			List<Pproject> pprojectList = this.frontPprojectServiceImpl.list(0, 0, filter, false);
			map.put("pprojectList", pprojectList) ;
		}
		
		//初始化项目方手续费分成比例
		{
			filter = "where auditStatus = " + AuditStatusEnum.auditPass+" order by id asc";
			List<Pfees> pfeesList = this.frontPfeesServiceImpl.list(0, 0, filter, false);
			List<Ptrademapping> ptrademappingList = new ArrayList<Ptrademapping>();
			for (Pfees pfees : pfeesList) {
				Ptrademapping ptrademapping = frontPtrademappingServiceImpl.findById(pfees.getTrademappingId().getId());
				double buyFeesRate = DataOperationUtil.div(pfees.getBuyFee(), 0.001 + pfees.getBuyFee(), 6);
				double sellFeesRate = DataOperationUtil.div(pfees.getSellFee(), 0.001 + pfees.getSellFee(), 6);
				ptrademapping.setBuyFeesRate(buyFeesRate);
				ptrademapping.setSellFeesRate(sellFeesRate);
				ptrademappingList.add(ptrademapping);
			}
			map.put("ptrademappingList", ptrademappingList);
		}
		
		//获取保证金信息
		{
			filter = " where 1 = 1 ";
			List<Pdepositcointype> pdepositcointypeList = this.pdepositcointypeService.findByParam(filter);
			if(null != pdepositcointypeList && pdepositcointypeList.size() > 0) {
				map.put("pdepositcointype", pdepositcointypeList.get(0)) ;
			}
		}
		
	}
	
	public Map<String, Object> getMap(){
		return FrontConstantMapServiceImpl.map ;
	}
	
	public void put(String key,Object value){
//		log.info("ConstantMap put key:"+key+",value:"+value+".") ;
		synchronized (this.map) {
			map.put(key, value) ;
		}
	}
	
	public Object get(String key){
		return map.get(key) ;
	}
	
	public String getString(String key){
		return (String)map.get(key) ;
	}
	
	
	/**
	 * 对比redis缓存的内存map
	 * @param key
	 * @param version
	 */
	public void putRedisMap(String key, Integer version) {
		synchronized (this.redisMap) {
			redisMap.put(key, version) ;
		}
	}
	
	public Integer getRedisMap(String key) {
		return (Integer)redisMap.get(key) ;
	}
}
