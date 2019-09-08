package com.gt.auto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.entity.Fperiod;
import com.gt.entity.Ftrademapping;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FtradeMappingService;
import com.gt.util.Utils;
import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;



public class KlinePeriodData {
	public static boolean addFlags = false ;
	
	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private LatestKlinePeroid latestKlinePeroid ;
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	public static Timestamp lastUpdateTime = Utils.getTimestamp() ;
	
	private Map<Integer, String[]> json = new HashMap<Integer, String[]>() ;
	private Map<Integer, String[]> indexJson = new HashMap<Integer, String[]>() ;

	private Map<Integer, TreeSet<Fperiod>> oneMiniteData = new HashMap<Integer, TreeSet<Fperiod>>() ;
	
	private Map<Integer, Map<Integer, TreeSet<Fperiod>>> periodMap = new HashMap<Integer, Map<Integer,TreeSet<Fperiod>>>() ;
	private Map<Integer, Map<Integer, TreeSet<Fperiod>>> containerMap = new HashMap<Integer, Map<Integer,TreeSet<Fperiod>>>() ;
	private long[] timeStep = 
			new long[]{
			1, 
			3, 
			5, 
			15,
			30,
			1*60, 
			2*60,
			4*60,
			6*60,
			12*60,
			1*24*60,
			3*24*60,
			7*24*60,
	} ;
	
	Integer[] keys = null ;
	
	public void init(){
		readData() ;
	}
	
	
	
	private void readData(){
		keys = new Integer[13] ;
		for (int i = 0; i < 13; i++) {
			keys[i] = i ;
		}
		long now = Utils.getTimestamp().getTime() ;
		
		final List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMapping() ;
		for (Ftrademapping ftrademapping : ftrademappings) {
			int id = ftrademapping.getFid() ;
			Map<Integer, TreeSet<Fperiod>> subMap = new HashMap<Integer, TreeSet<Fperiod>>() ;
			for (int j = 0; j < keys.length; j++) {
				Integer key = keys[j] ;
				TreeSet<Fperiod> fperiodSet = new TreeSet<Fperiod>(this.comparator) ;
				subMap.put(key, fperiodSet) ;
			}
			periodMap.put(id, subMap) ;
			
			Map<Integer, TreeSet<Fperiod>> subMap2 = new HashMap<Integer, TreeSet<Fperiod>>() ;
			for (int j = 0; j < keys.length; j++) {
				Integer key = keys[j] ;
				TreeSet<Fperiod> fperiodSet = new TreeSet<Fperiod>(this.comparator) ;
				subMap2.put(key, fperiodSet) ;
			}
			containerMap.put(id, subMap2) ;
			
			TreeSet<Fperiod> fperiodSets2 = new TreeSet<Fperiod>(this.comparator) ;
			this.oneMiniteData.put(id, fperiodSets2) ;
			
			String[] jsonStrings = new String[keys.length] ;
			json.put(id, jsonStrings) ;
			String[] indexJsonStrings = new String[keys.length] ;
			indexJson.put(id, indexJsonStrings) ;
		}
		
		new Thread(new Runnable() {
			
			public void run() {
				for (Ftrademapping ftrademapping : ftrademappings) {
					int id = ftrademapping.getFid() ;
					//gai
					List<Fperiod> fperiods = frontOthersService.findAllFperiod(0L, id) ;
					for (int j=0;j<fperiods.size();j++) {
						Fperiod fperiod = fperiods.get(j) ;
						addFperiod(id,keys[0], fperiod) ;
					}
					//生成k线数据
//					generateJson(id);
//					generateIndexJson(id);
					System.out.println("id:"+id+" finished");
				}
				addFlags = true ;
			}
		}).start() ;
		
	}
	
	Timestamp startTime = null ; 
	public synchronized void addFperiod(int id,int key,Fperiod fperiod){
		
			try {
				if(startTime == null ){ 
					startTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(fperiod.getFtime())).getTime()) ;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("K线图启动失败，请重启！");
			}
			
			
			Map<Integer, TreeSet<Fperiod>> tmap = this.periodMap.get(id) ;
			for (int i = 0; i < this.timeStep.length; i++) {
				TreeSet<Fperiod> fperiods = tmap.get(i) ;
				long step = this.timeStep[i] ;

				Fperiod last = null ;
				if(fperiods.size()>0){
					last = fperiods.last() ;
				}
				
				Timestamp openNew = isOpenNewPeriod(step, fperiod,last) ;
				
				
				Fperiod addPeriod = null ;
				if(openNew!=null ){
					addPeriod = new Fperiod() ;
					addPeriod.setFtime(openNew) ;
					
					addPeriod.setFkai(fperiod.getFkai()) ;
					addPeriod.setFgao(fperiod.getFgao()) ;
					addPeriod.setFdi(fperiod.getFdi()) ;
					addPeriod.setFshou(fperiod.getFshou()) ;
					addPeriod.setFliang(fperiod.getFliang()) ;
				}else{
					addPeriod = last ;
					

					addPeriod.setFgao(fperiod.getFgao()>addPeriod.getFgao()?fperiod.getFgao():addPeriod.getFgao()) ;
					addPeriod.setFdi(fperiod.getFdi()>addPeriod.getFdi()?addPeriod.getFdi():fperiod.getFdi()) ;
					addPeriod.setFshou(fperiod.getFshou()) ;
					addPeriod.setFliang(addPeriod.getFliang()+fperiod.getFliang()) ;

				}
				
				fperiods.add(addPeriod) ;
				tmap.put(i, fperiods) ;
				
			}
			this.periodMap.put(id, tmap) ;
			
	}
	
	private Timestamp isOpenNewPeriod(long step,Fperiod fperiod,Fperiod last) { 
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm") ;
			Timestamp ftime = new Timestamp(sdf1.parse(sdf1.format(fperiod.getFtime())).getTime()) ;
			
			long minus = (ftime.getTime()-startTime.getTime())/(60*1000L) ;
			Timestamp nowTime = new Timestamp(startTime.getTime() + minus/step*step*60*1000L ) ; 
			
			if(last == null 
					|| (minus%step==0&&last.getFtime().getTime()!=nowTime.getTime()) 
					|| (minus%step!=0&&last.getFtime().getTime()!=nowTime.getTime()) ){
				return new Timestamp(startTime.getTime() + minus/step*step*60*1000L ) ;
			}else{
				return null ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	
	private Comparator<Fperiod> comparator = new Comparator<Fperiod>() {

		public int compare(Fperiod o1, Fperiod o2) {
			return o1.getFtime().compareTo(o2.getFtime()) ;
		}
	};
	
	
	public String getJsonString(int id,int key){
			synchronized (this.json) {
				return json.get(id)[key] ;
			}
	}
	
	public synchronized void setJsonString(int id,int key,String jsonString){
		synchronized (this.json) {
			String[] strings = this.json.get(id) ;
			strings[key] = jsonString ;
			json.put(id, strings) ;
			
			redisCacheUtil.setCacheObject(RedisKey.getJsonString(id, key), jsonString);
		}
		
	}
	
	public synchronized void generateJson(int id){
		this.latestKlinePeroid.clearPeriodMap(id) ;
		
		Map<Integer,TreeSet<Fperiod>> map = this.periodMap.get(id) ;
		for (Map.Entry<Integer, TreeSet<Fperiod>> entry : map.entrySet()) {
			TreeSet<Fperiod> fperiods = entry.getValue();
			StringBuffer stringBuffer = new StringBuffer() ;
			stringBuffer.append("[") ;
			int index = 0 ;
			int plens = fperiods.size();
			int pstep = 1;
			//每次最多提取最后500个数据
			if (plens>501){
				pstep=plens-500;
			}
			for (Fperiod fperiod : fperiods) {
				index++ ;
				if (index>=pstep){
					stringBuffer.append("["+(fperiod.getFtime().getTime())
							+","+new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(8, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(8, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(8, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(8, BigDecimal.ROUND_HALF_UP)
							+","+new BigDecimal(String.valueOf(fperiod.getFliang())).setScale(4, BigDecimal.ROUND_HALF_UP)+"]") ;
					if(index!=fperiods.size()){
						stringBuffer.append(",") ;
					}else{
						this.latestKlinePeroid.putPeriodMap(id, (int)timeStep[entry.getKey()], fperiod) ;
					}
				}
			}
			stringBuffer.append("]") ;
			//System.out.print("Periods: " + stringBuffer.toString());
			this.setJsonString(id, entry.getKey(), stringBuffer.toString()) ;
		}
	}
	
	public String getIndexJsonString(int id,int key){
		return indexJson.get(id)[key] ;
	}
	

	public synchronized void setIndexJsonString(int id,int key,String indexJsonString){
		synchronized (this.indexJson) {
			String[] strings = this.indexJson.get(id) ;
			strings[key] = indexJsonString ;
			indexJson.put(id, strings) ;
			
			redisCacheUtil.setCacheObject(RedisKey.getIndexJsonString(id, key), indexJsonString);
		}
	}
	
	public synchronized void generateIndexJson(int id){
		Map<Integer,TreeSet<Fperiod>> map = this.periodMap.get(id) ;
		for (Map.Entry<Integer, TreeSet<Fperiod>> entry : map.entrySet()) {
			TreeSet<Fperiod> fperiods = entry.getValue();
			StringBuffer stringBuffer = new StringBuffer() ;
			stringBuffer.append("[") ;
			int index = 0 ;
			int before = fperiods.size() - 50 ;
			before = before<0?0:before ;
			for (Fperiod fperiod : fperiods) {
				index++ ;
				if(index<before){
					continue ;
				}
				stringBuffer.append("["+(fperiod.getFtime().getTime()/1000)+",0,0,"+fperiod.getFkai()+","+fperiod.getFshou()+","+fperiod.getFgao()+","+fperiod.getFdi()+","+Utils.getDoubleS(fperiod.getFliang(), 4)+"]") ;
				if(index!=fperiods.size()){
					stringBuffer.append(",") ;
				}
			}
			stringBuffer.append("]") ;
			System.out.println("id:"+id+" key:"+entry.getKey()+" json:"+stringBuffer.toString());
			this.setIndexJsonString(id, entry.getKey(), stringBuffer.toString()) ;
		}
		
	}
}
