package com.gt.auto;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.TrademappingStatusEnum;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Ftrademapping;
import com.gt.service.front.UtilsService;
import com.gt.util.TradeMappingUtil;
import com.gt.util.Utils;

public class AutoDealingOneDayData {

	@Autowired
	private OneDayData oneDayData;
	@Autowired
	private RealTimeData realTimeData;
	@Autowired
	private UtilsService utilsService ;

	public void init() {
		new Thread(new Work()).start();
	}

	class Work implements Runnable {

		public void run() {
			while (true) {

				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					long time = Utils.getTimestamp().getTime();
					//gai
					List<Ftrademapping> ftrademappings = utilsService.list(0, 0, " where fstatus=? ", false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
					for (Ftrademapping ftrademapping : ftrademappings) {
						double totalDeal = 0F;
						double lowest = 0F;
						double highest = 0F;
						double start24Price = 0F;
						double total24 = 0F;
						Object[] objs = realTimeData
								.getEntrustSuccessMap(ftrademapping.getFid(),Integer.MAX_VALUE);
						for (int i = 0; i < objs.length; i++) {
							Fentrustlog ent = (Fentrustlog) objs[i];
							long createTime = ent.getFcreateTime().getTime() ;
							if (time - createTime>=24*3600*1000L) {
								realTimeData.removeEntrustSuccessMap(
										ftrademapping.getFid(), ent);
							} else {
								if(i == objs.length-1){
									start24Price = ent.getFprize();
								}
								if(ent.getFcreateTime().getTime()>=time)
								total24 = total24+ent.getFamount();
								totalDeal += ent.getFcount();
								double prize = ent.getFprize();

								lowest = (lowest > prize || lowest == 0F) ? prize
										: lowest;
								highest = (highest < prize) ? prize : highest;
							}
						}
						oneDayData.put24Total(ftrademapping.getFid(), total24);
						oneDayData.put24Price(ftrademapping.getFid(), start24Price);
						oneDayData.putHighest(ftrademapping.getFid(), highest);
						oneDayData.putLowest(ftrademapping.getFid(), lowest);
						oneDayData.putTotal(ftrademapping.getFid(), totalDeal);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try{
					Thread.sleep(20L) ;
				}catch(Exception e){
					e.printStackTrace() ;
				}
			}
		}
	}
}
