package com.gt.auto;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.entity.Fentrustlog;
import com.gt.entity.Fperiod;
import com.gt.entity.Ftrademapping;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;


public class AutoMinuteKline {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoMinuteKline.class);

	private static final String CLASS_NAME = AutoMinuteKline.class.getSimpleName();

	@Autowired
	private KlinePeriodData klinePeriodData ;
	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private RealTimeData realTimeData ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private LatestKlinePeroid latestKlinePeroid ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	
	public void work() {
			
			try {
				while(KlinePeriodData.addFlags == false ){
					try {
						Thread.sleep(10000L) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//gai
				List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMapping() ;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm") ;
				long now = simpleDateFormat.parse(simpleDateFormat.format(Utils.getTimestamp())).getTime() ;
				
				for (Ftrademapping ftrademapping : ftrademappings) {
					int id = ftrademapping.getFid() ;
					try{
						List<Fperiod> fperiods = getNewFperiod(ftrademapping,now) ;
						if(fperiods.size()>0){
							for (Fperiod fperiod : fperiods) {
								LOGGER.info(CLASS_NAME + " work,添加frontOthersService.addFperiod开始");
								this.frontOthersService.addFperiod(fperiod);
								LOGGER.info(CLASS_NAME + " work,添加frontOthersService.addFperiod结束");
								LOGGER.info(CLASS_NAME + " work,添加klinePeriodData.addFperiod开始");
								this.klinePeriodData.addFperiod(id, 0, fperiod);
								LOGGER.info(CLASS_NAME + " work,添加klinePeriodData.addFperiod结束");
							}
						}
						this.klinePeriodData.generateJson(id) ;
						this.klinePeriodData.generateIndexJson(id) ;
					}catch(Exception e){
						e.printStackTrace() ;
					}
				}
				this.klinePeriodData.lastUpdateTime = new Timestamp(now) ;
				this.latestKlinePeroid.cleanTime(now) ;
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}
	
	
	public List<Fperiod> getNewFperiod(Ftrademapping ftrademapping,long now) throws Exception {
		List<Fperiod> lastFperiods = utilsService.list(0, 1, " where ftrademapping.fid="+ftrademapping.getFid()+" order by ftime desc ", true, Fperiod.class) ;
		Fperiod lastFperiod = null;
		Timestamp lastTime = null ;
		Timestamp lastTime2 = null ;
		if(lastFperiods.size() == 1 ){
			lastFperiod = lastFperiods.get(0)  ;
			lastTime = new Timestamp(lastFperiod.getFtime().getTime()+60*1000L) ;
			lastTime2 = new Timestamp(lastFperiod.getFtime().getTime()+60*1000L) ;
		}
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm") ;
		TreeMap<String, Fperiod> map = new TreeMap<String, Fperiod>(new Comparator<String>() {

			public int compare(String o1, String o2) {
				try {
					return sdf.parse(o1).compareTo(sdf.parse(o2)) ;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return 0 ;
			}
		}) ;
		
		
		
		List<Fentrustlog> fentrustlogs = new ArrayList<Fentrustlog>() ;
		if(lastFperiod == null ){
			fentrustlogs = this.utilsService.list(0, 0, " where ftrademapping.fid="+ftrademapping.getFid()+" and isactive=1 order by fid asc ", false, Fentrustlog.class) ;
			map.put(sdf.format(new Timestamp(now-60*1000L)), null) ;
		}else{
			while(true ){
				if(lastTime.getTime()<now){
					if(Utils.openTrade(ftrademapping, lastTime)){
						map.put(sdf.format(lastTime), null) ;
					}
					lastTime = new Timestamp(lastTime.getTime()+60*1000L) ;
				}else{
					break;
				}
			}
			
			fentrustlogs = this.utilsService.list(0, 0, " where ftrademapping.fid="+ftrademapping.getFid()+" and isactive=1 and fcreateTime>=? and fcreateTime<?  order by fid asc ", false, Fentrustlog.class,lastTime2,new Timestamp(now)) ;
		}
		
		
		for (int i = 0; i < fentrustlogs.size(); i++) {
			Fentrustlog fentrustlog = fentrustlogs.get(i) ;
			Timestamp time = fentrustlog.getFcreateTime() ;
			double price = fentrustlog.getFprize() ;
			double count = fentrustlog.getFcount() ;
			
			if(time.getTime()>=now){
				continue ;
			}
			
			String times = sdf.format(time) ;
			Fperiod fperiod = map.get(times) ;
			if(fperiod == null ){
				fperiod = new Fperiod() ;
				fperiod.setFdi(price) ;
				fperiod.setFgao(price) ;
				fperiod.setFkai(price) ;
				fperiod.setFliang(count) ;
				fperiod.setFshou(price) ;
				fperiod.setFtime(new Timestamp(sdf.parse(times).getTime())) ;
				fperiod.setFtrademapping(ftrademapping) ;
			}else{
				if(fperiod.getFdi()>price){
					fperiod.setFdi(price) ;
				}
				
				if(fperiod.getFgao()<price){
					fperiod.setFgao(price) ;
				}
				
				fperiod.setFliang(fperiod.getFliang()+count) ;
				fperiod.setFshou(price) ;
			}
			map.put(times, fperiod) ;
		}

		List<Fperiod> rets = new ArrayList<Fperiod>() ;
		double lastprice = this.realTimeData.getLatestDealPrize(ftrademapping.getFid())  ;
		//double lastprice = Double.valueOf(redisCache.GET(RedisKey.getLatestDealPrize(ftrademapping.getFid())));
		for (Map.Entry<String, Fperiod> entry : map.entrySet()) {
			String key = entry.getKey() ;
			Fperiod value = entry.getValue() ;
			if(value == null ){
				value = new Fperiod() ;
				double price = lastprice  ;
				value.setFdi(price) ;
				value.setFgao(price) ;
				value.setFkai(price) ;
				value.setFliang(0) ;
				value.setFshou(price) ;
				value.setFtime(new Timestamp(sdf.parse(key).getTime())) ;
				value.setFtrademapping(ftrademapping) ;
			}else{
				lastprice = value.getFshou() ;
			}
			
			rets.add(value) ;
		}
		
		return rets ;
	}
}
