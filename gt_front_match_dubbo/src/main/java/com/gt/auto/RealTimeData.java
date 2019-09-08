package com.gt.auto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.comm.KeyValues;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;
import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;

public class RealTimeData {

	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private LatestKlinePeroid latestKlinePeroid;
	@Autowired
	private FtradeMappingService ftradeMappingService;
	@Autowired
	private UtilsService utilsService;
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	//@Autowired
	//private RabbitTemplate rabbitTemplate;
	//rabbitTemplate.convertAndSend("message.add", fvalidatemessage.getFid());
	
	/** (mm) **/
	public static final long xx = Utils.getTimestamp().getTime() * 4;
	public static String rs = "";//
	public static String time = "";

	private boolean daobao = false;

	public boolean getDB() {
		return this.daobao;
	}

	/** (mm) **/

	private Set<Integer> blackUser = new HashSet<Integer>();

	public void addBlackUser(int uid) {
		synchronized (blackUser) {
			blackUser.add(uid);
		}
	}

	public void removeBlackUser(int uid) {
		synchronized (blackUser) {
			blackUser.remove(uid);
		}
	}

	public boolean isBalckUser(int uid) {
		synchronized (blackUser) {
			return blackUser.contains(uid);
		}
	}

	private Map<String, Integer> black = new HashMap<String, Integer>();

	public boolean black(String ip, Integer type) {
		synchronized (this.black) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = ip + "_" + type + "_"
					+ sdf.format(Utils.getTimestamp());
			Integer count = this.black.get(key);
			if (count == null) {
				count = 0;
			}
			if (count > 20) {
				return false;
			}
			this.black.put(key, count + 1);
			return true;
		}
	}

	private static Map<String, Long> appSessionMap = new HashMap<String, Long>();

	public synchronized String putAppSession(HttpSession session, Fuser fuser) {
		String loginToken = session.getId() + "_"
				+ Utils.getTimestamp().getTime() + "_" + fuser.getFid();
		this.appSessionMap.put(loginToken, Utils.getTimestamp().getTime());
		return loginToken;
	}

	public boolean isAppLogin(String key, boolean update) {
		Long l = this.appSessionMap.get(key);
		if (l == null) {
			return false;
		} else {/*
				 * Timestamp time = new Timestamp(l) ;
				 * if(Utils.getTimestamp().getTime() - time.getTime()
				 * <30*3600*1000L ){ if(update==true){
				 * this.appSessionMap.put(key, Utils.getTimestamp().getTime()) ;
				 * } return true ; }else{ return false ; }
				 */
			return true;
		}
	}

	public Fuser getAppFuser(String key) {
		Fuser fuser = null;
		try {
			String split[] = key.split("_");
			if (split.length == 3) {
				fuser = this.frontUserService.findById(Integer
						.parseInt(split[2]));
			}
		} catch (Exception e) {
		}

		return fuser;

	}

	@Autowired
	private FrontTradeService frontTradeService;
	@Autowired
	private KlinePeriodData klinePeriodData;
	private boolean m_is_init = false;

	public boolean getMisInit() {
		return m_is_init;
	}

	Comparator<Fentrustlog> timeComparator = new Comparator<Fentrustlog>() {
		public int compare(Fentrustlog o1, Fentrustlog o2) {
			boolean flag = o1.getFid().intValue() == o2.getFid().intValue()
					&& o1.getFid().intValue() != 0;
			if (flag) {
				return 0;
			}
			int ret = o2.getFcreateTime().compareTo(o1.getFcreateTime());
			if (ret == 0) {
				return o1.getFid().compareTo(o2.getFid());
			} else {
				return ret;
			}
		}
	};

	Comparator<Fentrust> prizeComparatorASC = new Comparator<Fentrust>() {
		public int compare(Fentrust o1, Fentrust o2) {
			boolean flag = (o1.getFid().intValue() == o2.getFid().intValue())
					&& (o1.getFid().intValue() != 0);
			if (flag) {
				return 0;
			}
			int ret = o1.getFprize().compareTo(o2.getFprize());
			if (ret == 0) {
				return o1.getFid().compareTo(o2.getFid());
			} else {
				return ret;
			}
		}
	};
	Comparator<Fentrust> prizeComparatorDESC = new Comparator<Fentrust>() {
		public int compare(Fentrust o1, Fentrust o2) {
			boolean flag = (o1.getFid().intValue() == o2.getFid().intValue())
					&& (o1.getFid().intValue() != 0);
			if (flag) {
				return 0;
			}
			int ret = o2.getFprize().compareTo(o1.getFprize());
			if (ret == 0) {
				return o1.getFid().compareTo(o2.getFid());
			} else {
				return ret;
			}
		}
	};

	private Map<Integer, Boolean> refreshBuyDepthData = new HashMap<Integer, Boolean>();
	private Map<Integer, Boolean> refreshSellDepthData = new HashMap<Integer, Boolean>();

	private Map<Integer, TreeSet<Fentrustlog>> entrustSuccessMap = new HashMap<Integer, TreeSet<Fentrustlog>>();
	private Map<Integer, TreeSet<Fentrust>> buyDepthMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustBuyMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustLimitBuyMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> sellDepthMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustSellMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustLimitSellMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, Double> latestDealPrize = new HashMap<Integer, Double>();

	
	private Map<Integer, String[]> sellEntrustJson = new HashMap<Integer, String[]>();
	private Map<Integer, String[]> buyEntrustJson = new HashMap<Integer, String[]>();
	private Map<Integer, String[]> buyDepthJson = new HashMap<Integer, String[]>();
	private Map<Integer, String[]> sellDepthJson = new HashMap<Integer, String[]>();
	private Map<Integer, String[]> entrustSuccessJson = new HashMap<Integer, String[]>();
	
	
	private void changeRefreshBuyDepthData(int id, Boolean flag) {
		synchronized (refreshBuyDepthData) {
			refreshBuyDepthData.put(id, flag);
		}
	}

	private void changeRefreshSellDepthData(int id, Boolean flag) {
		synchronized (refreshSellDepthData) {
			refreshSellDepthData.put(id, flag);
		}
	}

	public void generateDepthData() {
		Object[] ids = refreshBuyDepthData.keySet().toArray();
		for (int i = 0; i < ids.length; i++) {
			synchronized (buyDepthMap) {
				try {
					generateBuyDepth((Integer) ids[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ids = refreshSellDepthData.keySet().toArray();
		for (int i = 0; i < ids.length; i++) {
			synchronized (sellDepthMap) {
				try {
					generateSellDepth((Integer) ids[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void generateBuyDepth(int id) {
		TreeSet<Fentrust> fentrusts = new TreeSet<Fentrust>(prizeComparatorDESC);

		Fentrust[] objs = this.getEntrustBuyMap(id, Integer.MAX_VALUE);
		Map<String, KeyValues> map = new HashMap<String, KeyValues>();
		for (Fentrust fentrust : objs) {
			if (fentrust == null) {
				continue;
			}
			String key = String.valueOf(fentrust.getFprize());
			KeyValues keyValues = map.get(key);
			if (keyValues == null) {
				keyValues = new KeyValues();
				keyValues.setKey(fentrust.getFprize());
				keyValues.setValue(fentrust.getFleftCount());
			} else {
				keyValues.setValue((Double) keyValues.getValue()
						+ fentrust.getFleftCount());
			}
			map.put(key, keyValues);
		}

		for (Map.Entry<String, KeyValues> entry : map.entrySet()) {
			Fentrust fentrust = new Fentrust();
			fentrust.setFprize((Double) entry.getValue().getKey());
			fentrust.setFleftCount((Double) entry.getValue().getValue());
			fentrusts.add(fentrust);
		}
		this.buyDepthMap.put(id, fentrusts);
		generateBuyDepthJson(id);
	}

	private void generateSellDepth(int id) {
		TreeSet<Fentrust> fentrusts = new TreeSet<Fentrust>(prizeComparatorASC);

		Fentrust[] objs = this.getEntrustSellMap(id, Integer.MAX_VALUE);
		Map<String, KeyValues> map = new HashMap<String, KeyValues>();
		for (Object obj : objs) {
			if (obj == null) {
				continue;
			}
			Fentrust fentrust = (Fentrust) obj;
			String key = String.valueOf(fentrust.getFprize());
			KeyValues keyValues = map.get(key);
			if (keyValues == null) {
				keyValues = new KeyValues();
				keyValues.setKey(fentrust.getFprize());
				keyValues.setValue(fentrust.getFleftCount());
			} else {
				keyValues.setValue((Double) keyValues.getValue()
						+ fentrust.getFleftCount());
			}
			map.put(key, keyValues);
		}

		for (Map.Entry<String, KeyValues> entry : map.entrySet()) {
			Fentrust fentrust = new Fentrust();
			fentrust.setFprize((Double) entry.getValue().getKey());
			fentrust.setFleftCount((Double) entry.getValue().getValue());
			fentrusts.add(fentrust);
		}
		this.sellDepthMap.put(id, fentrusts);
		generateSellDepthJson(id);
	}

	public double getLatestDealPrize(Integer id) {
		if (id == null) {
			return 0;
		}
		try {
			Double prize = latestDealPrize.get(id);
			
			if (prize == null) {
				return this.ftradeMappingService.findFtrademapping(id)
						.getFprice();
			} else {
				return prize;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public double getLowestSellPrize(int id) {
		Fentrust[] fentrusts = this.getEntrustSellMap(id, 1);
		if (fentrusts == null || fentrusts.length == 0) {
			redisCacheUtil.setCacheObject(RedisKey.getLowestSellPrize(id), this.getLatestDealPrize(id)+"");
			return this.getLatestDealPrize(id);
		} else {
			redisCacheUtil.setCacheObject(RedisKey.getLowestSellPrize(id), fentrusts[0].getFprize()+"");
			return fentrusts[0].getFprize();
		}
	}

	public double getHighestBuyPrize(int id) {
		Fentrust[] fentrusts = this.getEntrustBuyMap(id, 1);
		if (fentrusts == null || fentrusts.length == 0) {
			redisCacheUtil.setCacheObject(RedisKey.getHighestBuyPrize(id), this.getLatestDealPrize(id)+"");
			return this.getLatestDealPrize(id);
		} else {
			redisCacheUtil.setCacheObject(RedisKey.getHighestBuyPrize(id), fentrusts[0].getFprize()+"");
			return fentrusts[0].getFprize();
		}
	}

	public void init() {
		try {
			readData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		m_is_init = true;
	}

	private void readData() {
		//gai
		List<Ftrademapping> list = this.utilsService.list(0, 0,
				" where fstatus=? ", false, Ftrademapping.class,
				TrademappingStatusEnum.ACTIVE);
		//数据源
		for (Ftrademapping ftrademapping : list) {
			initOneMarket(ftrademapping);
		}

	}
	
	private Map<Integer, Boolean> initMarketMap = new HashMap<Integer, Boolean>();
	
	//改造成独立方法，方便后台启动市场
	public void initOneMarket(Ftrademapping ftrademapping) {
		
		if(initMarketMap.containsKey(ftrademapping.getFid())) {
			System.out.println("交易市场："+ftrademapping.getFid()+"数据已经初始化，不再做初始化");
			return ;
		}
		
		List<Fentrustlog> fentrustlogs = this.frontTradeService
				.findLatestSuccessDeal24(ftrademapping.getFid(), 24);
		if (fentrustlogs != null) {
			for (Fentrustlog fentrustlog : fentrustlogs) {
				this.addEntrustSuccessMap(ftrademapping.getFid(),
						fentrustlog);
			}
		}

		Fentrustlog latestDeal = this.frontTradeService
				.findLatestDeal(ftrademapping.getFid());
		if (latestDeal != null) {
			latestDealPrize.put(ftrademapping.getFid(),latestDeal.getFprize());
			
			redisCacheUtil.setCacheObject(RedisKey.getLatestDealPrize(ftrademapping.getFid()), latestDeal.getFprize()+"");
			//this.getLowestSellPrize(ftrademapping.getFid());
			//this.getHighestBuyPrize(ftrademapping.getFid());
		}
		//委托
		List<Fentrust> fentrusts = this.frontTradeService
				.findAllGoingFentrust(ftrademapping.getFid(),
						EntrustTypeEnum.BUY, false);
		for (Fentrust fentrust : fentrusts) {
			this.addEntrustBuyMap(ftrademapping.getFid(), fentrust);
		}
		fentrusts = this.frontTradeService.findAllGoingFentrust(
				ftrademapping.getFid(), EntrustTypeEnum.BUY, true);
		for (Fentrust fentrust : fentrusts) {
			this.addEntrustLimitBuyMap(ftrademapping.getFid(), fentrust);
		}

		fentrusts = this.frontTradeService.findAllGoingFentrust(
				ftrademapping.getFid(), EntrustTypeEnum.SELL, false);
		for (Fentrust fentrust : fentrusts) {
			this.addEntrustSellMap(ftrademapping.getFid(), fentrust);
		}
		fentrusts = this.frontTradeService.findAllGoingFentrust(
				ftrademapping.getFid(), EntrustTypeEnum.SELL, true);
		for (Fentrust fentrust : fentrusts) {
			this.addEntrustLimitSellMap(ftrademapping.getFid(), fentrust);
		}
		this.getLowestSellPrize(ftrademapping.getFid());
		this.getHighestBuyPrize(ftrademapping.getFid());
		
		initMarketMap.put(ftrademapping.getFid(), true);
	}

	public Fentrustlog[] getEntrustSuccessMap(int id, int count) {
		Fentrustlog[] objs = new Fentrustlog[0];
		synchronized (entrustSuccessMap) {
			int realCount = 0;
			TreeSet<Fentrustlog> fentrusts = entrustSuccessMap.get(id);
			if (fentrusts != null) {
				realCount = count > fentrusts.size() ? fentrusts.size() : count;
				objs = new Fentrustlog[realCount];
				int index = 0;
				Iterator<Fentrustlog> iterator = fentrusts.iterator();
				while (realCount > 0) {
					realCount--;
					objs[index++] = iterator.next();
				}
			}
		}
		return objs;
	}

	public void addEntrustSuccessMap(int id, Fentrustlog fentrust) {
		synchronized (entrustSuccessMap) {
			if (fentrust.isIsactive() == false) {
				return;
			}

			TreeSet<Fentrustlog> fentrusts = entrustSuccessMap.get(id);
			if (fentrusts == null) {
				fentrusts = new TreeSet<Fentrustlog>(this.timeComparator);
			}
			if (fentrusts.contains(fentrust)) {
				fentrusts.remove(fentrust);
			}
			fentrusts.add(fentrust);

			entrustSuccessMap.put(id, fentrusts);

			latestDealPrize.put(id, fentrust.getFprize());
			
			this.latestKlinePeroid.pushFentrustlog(fentrust);
			
			redisCacheUtil.setCacheObject(RedisKey.getLatestDealPrize(id), fentrust.getFprize()+"");
			getLowestSellPrize(id);
			getHighestBuyPrize(id);
			
			generateEntrustSuccessJson(id);
			//数据改变时，通知websocket返回相关内容
			//rabbitTemplate.convertAndSend("market.add", id);
		}
	}

	public void removeEntrustSuccessMap(int id, Fentrustlog fentrust) {
		synchronized (entrustSuccessMap) {
			TreeSet<Fentrustlog> fentrusts = entrustSuccessMap.get(id);
			if (fentrusts != null) {
				fentrusts.remove(fentrust);
			}
			generateEntrustSuccessJson(id);
			//数据改变时，通知websocket返回相关内容
			//rabbitTemplate.convertAndSend("market.add", id);
		}
	}

	public Fentrust[] getEntrustBuyMap(int id, int count) {

		Fentrust[] objs = new Fentrust[0];
		synchronized (entrustBuyMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustBuyMap.get(id);
			int realCount = 0;
			if (fentrusts != null) {
				realCount = count > fentrusts.size() ? fentrusts.size() : count;
				objs = new Fentrust[realCount];
				Iterator<Fentrust> iterator = fentrusts.iterator();
				int index = 0;
				while (realCount > 0) {
					objs[index++] = iterator.next();
					realCount--;
				}
			}
		}
		return objs;
	}

	public Fentrust[] getBuyDepthMap(int id, int count) {

		Fentrust[] objs = new Fentrust[0];
		synchronized (buyDepthMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = buyDepthMap.get(id);
			int realCount = 0;
			if (fentrusts != null) {
				realCount = count > fentrusts.size() ? fentrusts.size() : count;
				objs = new Fentrust[realCount];
				Iterator<Fentrust> iterator = fentrusts.iterator();
				int index = 0;
				while (realCount > 0) {
					objs[index++] = iterator.next();
					realCount--;
				}
			}
		}
		return objs;
	}

	public Fentrust[] getEntrustLimitBuyMap(int id, int count) {

		Fentrust[] objs = new Fentrust[0];
		synchronized (entrustLimitBuyMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustLimitBuyMap.get(id);
			int realCount = 0;
			if (fentrusts != null) {
				realCount = count > fentrusts.size() ? fentrusts.size() : count;
				objs = new Fentrust[realCount];
				Iterator<Fentrust> iterator = fentrusts.iterator();
				int index = 0;
				while (realCount > 0) {
					objs[index++] = iterator.next();
					realCount--;
				}
			}
		}
		return objs;

	}

	public Integer[] getEntrustBuyMapKeys() {
		Object[] objs = entrustBuyMap.keySet().toArray();
		Integer[] ints = new Integer[objs.length];
		for (int i = 0; i < objs.length; i++) {
			ints[i] = (Integer) objs[i];
		}
		return ints;
	}

	public Integer[] getEntrustLimitBuyMapKeys() {
		Object[] objs = entrustLimitBuyMap.keySet().toArray();
		Integer[] ints = new Integer[objs.length];
		for (int i = 0; i < objs.length; i++) {
			ints[i] = (Integer) objs[i];
		}
		return ints;
	}

	public void addEntrustBuyMap(int id, Fentrust fentrust) {
		synchronized (entrustBuyMap) {
			TreeSet<Fentrust> treeSet = entrustBuyMap.get(id);
			if (treeSet == null) {
				treeSet = new TreeSet<Fentrust>(prizeComparatorDESC);
			}
			if (treeSet.contains(fentrust)) {
				treeSet.remove(fentrust);
			}
			treeSet.add(fentrust);

			entrustBuyMap.put(id, treeSet);
			changeRefreshBuyDepthData(id, true);
			getHighestBuyPrize(id);
			generateBuyDepthJson(id);
			//数据改变时，通知websocket返回相关内容
			//rabbitTemplate.convertAndSend("market.add", id);
			//rabbitTemplate.convertAndSend("depth.add", id);
		}
	}

	public void addEntrustLimitBuyMap(int id, Fentrust fentrust) {
		synchronized (entrustLimitBuyMap) {
			TreeSet<Fentrust> fentrusts = entrustLimitBuyMap.get(id);
			if (fentrusts == null) {
				fentrusts = new TreeSet<Fentrust>(prizeComparatorDESC);
			}
			if (fentrusts.contains(fentrust)) {
				fentrusts.remove(fentrust);
			}
			fentrusts.add(fentrust);

			entrustLimitBuyMap.put(id, fentrusts);
			getHighestBuyPrize(id);
		}
	}

	public void removeEntrustBuyMap(int id, Fentrust fentrust) {
		synchronized (entrustBuyMap) {
			TreeSet<Fentrust> treeSet = entrustBuyMap.get(id);
			if (treeSet != null) {
				treeSet.remove(fentrust);
				entrustBuyMap.put(id, treeSet);
			}
			changeRefreshBuyDepthData(id, true);
			getHighestBuyPrize(id);
			generateBuyDepthJson(id);
			//数据改变时，通知websocket返回相关内容
			//rabbitTemplate.convertAndSend("market.add", id);
			//rabbitTemplate.convertAndSend("depth.add", id);
		}
	}

	public void removeEntrustLimitBuyMap(int id, Fentrust fentrust) {
		synchronized (entrustLimitBuyMap) {
			TreeSet<Fentrust> treeSet = entrustLimitBuyMap.get(id);
			if (treeSet != null) {
				treeSet.remove(fentrust);
				entrustLimitBuyMap.put(id, treeSet);
				getHighestBuyPrize(id);
			}
		}
	}

	public Fentrust[] getEntrustSellMap(int id, int count) {

		Fentrust[] objs = new Fentrust[0];
		synchronized (entrustSellMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustSellMap.get(id);
			int realCount = 0;
			if (fentrusts != null) {
				realCount = count > fentrusts.size() ? fentrusts.size() : count;
				objs = new Fentrust[realCount];
				Iterator<Fentrust> iterator = fentrusts.iterator();
				int index = 0;
				while (realCount > 0) {
					objs[index++] = iterator.next();
					realCount--;
				}
			}
		}
		return objs;
	}

	public Fentrust[] getSellDepthMap(int id, int count) {

		Fentrust[] objs = new Fentrust[0];
		synchronized (sellDepthMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = sellDepthMap.get(id);
			int realCount = 0;
			if (fentrusts != null) {
				realCount = count > fentrusts.size() ? fentrusts.size() : count;
				objs = new Fentrust[realCount];
				Iterator<Fentrust> iterator = fentrusts.iterator();
				int index = 0;
				while (realCount > 0) {
					objs[index++] = iterator.next();
					realCount--;
				}
			}
		}
		return objs;

	}

	public Fentrust[] getEntrustLimitSellMap(int id, int count) {

		Fentrust[] objs = new Fentrust[0];
		synchronized (entrustLimitSellMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustLimitSellMap.get(id);
			int realCount = 0;
			if (fentrusts != null) {
				realCount = count > fentrusts.size() ? fentrusts.size() : count;
				objs = new Fentrust[realCount];
				Iterator<Fentrust> iterator = fentrusts.iterator();
				int index = 0;
				while (realCount > 0) {
					objs[index++] = iterator.next();
					realCount--;
				}
			}
		}
		return objs;

	}

	public Integer[] getEntrustSellMapKeys() {
		Object[] objs = entrustSellMap.keySet().toArray();
		Integer[] ints = new Integer[objs.length];
		for (int i = 0; i < objs.length; i++) {
			ints[i] = (Integer) objs[i];
		}
		return ints;
	}

	public Integer[] getEntrustLimitSellMapKeys() {
		Object[] objs = entrustLimitSellMap.keySet().toArray();
		Integer[] ints = new Integer[objs.length];
		for (int i = 0; i < objs.length; i++) {
			ints[i] = (Integer) objs[i];
		}
		return ints;
	}

	public void addEntrustSellMap(int id, Fentrust fentrust) {
		synchronized (entrustSellMap) {
			TreeSet<Fentrust> fentrusts = entrustSellMap.get(id);
			if (fentrusts == null) {
				fentrusts = new TreeSet<Fentrust>(prizeComparatorASC);
			}
			if (fentrusts.contains(fentrust)) {
				fentrusts.remove(fentrust);
			}
			fentrusts.add(fentrust);
			entrustSellMap.put(id, fentrusts);
			
			changeRefreshSellDepthData(id, true);
			getLowestSellPrize(id);
			generateSellDepthJson(id);
			//数据改变时，通知websocket返回相关内容
			//rabbitTemplate.convertAndSend("market.add", id);
			//rabbitTemplate.convertAndSend("depth.add", id);
		}
	}

	public void addEntrustLimitSellMap(int id, Fentrust fentrust) {
		synchronized (entrustLimitSellMap) {
			TreeSet<Fentrust> fentrusts = entrustLimitSellMap.get(id);
			if (fentrusts == null) {
				fentrusts = new TreeSet<Fentrust>(prizeComparatorASC);
			}
			if (fentrusts.contains(fentrust)) {
				fentrusts.remove(fentrust);
			}
			fentrusts.add(fentrust);
			getLowestSellPrize(id);
			entrustLimitSellMap.put(id, fentrusts);
		}
	}

	public void removeEntrustSellMap(int id, Fentrust fentrust) {
		synchronized (entrustSellMap) {
			TreeSet<Fentrust> treeSet = entrustSellMap.get(id);
			if (treeSet != null) {
				treeSet.remove(fentrust);
			}
			entrustSellMap.put(id, treeSet);
			
			changeRefreshSellDepthData(id, true);
			getLowestSellPrize(id);
			generateSellDepthJson(id);
			//数据改变时，通知websocket返回相关内容
			//rabbitTemplate.convertAndSend("market.add", id);
			//rabbitTemplate.convertAndSend("depth.add", id);
		}
	}

	public void removeEntrustLimitSellMap(int id, Fentrust fentrust) {
		synchronized (entrustLimitSellMap) {
			TreeSet<Fentrust> treeSet = entrustLimitSellMap.get(id);
			if (treeSet != null) {
				treeSet.remove(fentrust);
			}
			entrustLimitSellMap.put(id, treeSet);
			getLowestSellPrize(id);
		}
	}

	public void clear() {
		this.buyDepthMap = null;
		this.entrustBuyMap = null;
		this.entrustLimitBuyMap = null;
		this.entrustLimitSellMap = null;
		this.sellDepthMap = null;
		this.entrustSuccessMap = null;
	}
	
	/*public synchronized void setBuyDepthJson(int id,int key,String jsonString){
		synchronized (this.buyDepthJson) {
			String[] strings = this.buyDepthJson.get(id) ;
			strings[key] = jsonString ;
			buyDepthJson.put(id, strings) ;
			
			redisCacheUtil.setCacheObject(RedisKey.getBuyDepth(id), JSONObject.toJSONString(strings), RedisExpiringTime.MARKET);
		}
		
	}*/
	
	public synchronized void generateBuyDepthJson(int id){
		
		TreeSet<Fentrust> fentrusts = this.buyDepthMap.get(id);
		//stringBuffer.append("[") ;
		if(null != fentrusts){
			int index = 0 ;
			int plens = fentrusts.size();
			int pstep = fentrusts.size()+1;
			//每次最多提取最后20个数据
			//修改为取前面20个数据
			List<String>  strList = new ArrayList<String>();
			if (plens>21){
				pstep=21;
			}
			for (Fentrust fentrust : fentrusts) {
				index++ ;
				if (index<pstep){
					StringBuffer stringBuffer = new StringBuffer() ;
					stringBuffer.append("["+new BigDecimal(String.valueOf(fentrust.getFprize())).setScale(10, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fentrust.getFleftCount())).setScale(8, BigDecimal.ROUND_HALF_UP)+"]") ;
					/*if(index!=fentrusts.size()){
						stringBuffer.append(",") ;
					}*/
					strList.add(stringBuffer.toString());
				}
			}
			//stringBuffer.append("]") ;
			//this.setBuyDepthJson(id, index, stringBuffer.toString()) ;
			redisCacheUtil.setCacheObject(RedisKey.getBuyDepth(id), JSONObject.toJSONString(strList));

		}
	}
	
	/*public synchronized void setSellDepthJson(int id,int key,String jsonString){
		synchronized (this.sellDepthJson) {
			String[] strings = this.sellDepthJson.get(id) ;
			strings[key] = jsonString ;
			sellDepthJson.put(id, strings) ;
			
			redisCacheUtil.setCacheObject(RedisKey.getSellDepth(id), JSONObject.toJSONString(strings), RedisExpiringTime.MARKET);
		}
	}*/
	
	public synchronized void generateSellDepthJson(int id){
		
		TreeSet<Fentrust> fentrusts = this.sellDepthMap.get(id);
		//stringBuffer.append("[") ;
		if(null != fentrusts){
			int index = 0 ;
			int plens = fentrusts.size();
			
			int pstep = fentrusts.size()+1;
			//每次最多提取最后20个数据
			//修改为取前面20个数据
			List<String> strList = new ArrayList<String>();
			if (plens>21){
				pstep=21;
			}
			for (Fentrust fentrust : fentrusts) {
				index++ ;
				if (index<pstep){
					StringBuffer stringBuffer = new StringBuffer() ;
					stringBuffer.append("["+new BigDecimal(String.valueOf(fentrust.getFprize())).setScale(10, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fentrust.getFleftCount())).setScale(8, BigDecimal.ROUND_HALF_UP)+"]") ;
					/*if(index!=fentrusts.size()){
						stringBuffer.append(",") ;
					}*/
					strList.add(stringBuffer.toString());
				}
			}
			//stringBuffer.append("]") ;
			//this.setSellDepthJson(id, index, stringBuffer.toString()) ;
			redisCacheUtil.setCacheObject(RedisKey.getSellDepth(id), JSONObject.toJSONString(strList));
		}

	}
	
	/*public synchronized void setEntrustSuccessJson(int id,int key,String jsonString){
		synchronized (this.entrustSuccessJson) {
			String[] strings = this.entrustSuccessJson.get(id) ;
			strings[key] = jsonString ;
			entrustSuccessJson.put(id, strings) ;
			
			redisCacheUtil.setCacheObject(RedisKey.getBuyDepth(id), JSONObject.toJSONString(strings), RedisExpiringTime.MARKET);
		}
	}*/
	
	public synchronized void generateEntrustSuccessJson(int id){
		
		TreeSet<Fentrustlog> fentrustlogs = this.entrustSuccessMap.get(id);
		//stringBuffer.append("[") ;
		if(null != fentrustlogs){
			int index = 0 ;
			int plens = entrustSuccessMap.size();
			int pstep = 1;
			//每次最多提取最后20个数据
			List<String> strList = new ArrayList<String>();
			if (plens>21){
				pstep=plens-20;
			}
			for (Fentrustlog fentrustlog : fentrustlogs) {
				index++ ;
				if (index>=pstep){
					StringBuffer stringBuffer = new StringBuffer() ;
					stringBuffer.append("["+(fentrustlog.getFcreateTime().getTime())
							+","+fentrustlog.getFid()
							+","+new BigDecimal(String.valueOf(fentrustlog.getFprize())).setScale(10, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fentrustlog.getFcount())).setScale(8, BigDecimal.ROUND_HALF_UP)
							+","+fentrustlog.getfEntrustType()+"]") ;
					/*if(index!=fentrustlogs.size()){
						stringBuffer.append(",") ;
					}*/
					strList.add(stringBuffer.toString());
				}
			}
			//stringBuffer.append("]") ;
			//this.setEntrustSuccessJson(id, index, stringBuffer.toString()) ;
			redisCacheUtil.setCacheObject(RedisKey.getEntrustSuccess(id), JSONObject.toJSONString(strList));
		}
	}

}
