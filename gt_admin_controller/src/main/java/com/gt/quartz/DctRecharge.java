package com.gt.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.DCTUtils;
import com.gt.util.ETHUtils;
import com.gt.util.Utils;

public class DctRecharge {
	private static final Logger log = LoggerFactory
			.getLogger(DctRecharge.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private UtilsService utilsService ;
	
	//last hash block,index,
	private Map<Integer, String> map = new HashMap<Integer, String>() ;
	public void putMap(Integer key,String value){
		synchronized (map) {
			map.put(key, value) ;
		}
	}
	public String getMap(Integer key){
		synchronized (key) {
			return map.get(key) ;
		}
	}
	
	public void init(){
		System.out.println("DctRecharge init。。。");
		String filter = "where fstatus=1 and fisBts=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
		List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
		//获取钱包中新数据
		for (Fvirtualcointype fvirtualcointype : coins) {
			String startTid = "" ;

			startTid =  fvirtualcointype.getStartTranId() ;

			putMap(fvirtualcointype.getFid(), startTid) ;
			
		}
	}
	
	public void work(){
		synchronized (this) {
			System.out.println("DctRecharge working。。。");
			String filter = "where fstatus=1 and fisBts=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
			List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
			
			//处理BTS核心的的循环
			for (Fvirtualcointype fvirtualcointype : coins) {
				try {
					
					
					BTCMessage btcMessage = new BTCMessage() ;
					btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
					btcMessage.setIP(fvirtualcointype.getFip()) ;
					btcMessage.setPORT(fvirtualcointype.getFport()) ;
					btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
					
					DCTUtils dctUtils = new DCTUtils(btcMessage) ;
					
					String scanfrom = getMap(fvirtualcointype.getFid()) ;
					String newfrom = "";
					
					List<BTCInfo> btcInfos = dctUtils.listtransactionsValue(100, scanfrom);
					
					for (BTCInfo btcInfo:btcInfos){
						String txid = btcInfo.getTxid() ;
						String address = btcInfo.getAddress().trim() ;
						int c = this.utilsService.count(" where ftradeUniqueNumber='"+txid+"' ", Fvirtualcaptualoperation.class) ;
						if(c>0){
							continue ;
						}
						newfrom =btcInfo.getTxid();
						List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address.toLowerCase()) ;
						if(fvirtualaddresses.size()==1){
							Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
							Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0) ;
							Fuser fuser = fvirtualaddress.getFuser() ;
							fvirtualcaptualoperation.setFuser(fuser) ;
							fvirtualcaptualoperation.setFhasOwner(true) ;
							fvirtualcaptualoperation.setFamount(btcInfo.getAmount()) ;
							fvirtualcaptualoperation.setFcreateTime(btcInfo.getTime()) ;
							fvirtualcaptualoperation.setFfees(0F) ;
							fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
			
							fvirtualcaptualoperation.setFconfirmations(0) ;
							fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_0) ;
			
							fvirtualcaptualoperation.setFtradeUniqueNumber(btcInfo.getTxid().trim()) ;
							fvirtualcaptualoperation.setRecharge_virtual_address(btcInfo.getAddress().trim()) ;
							fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
							fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
							fvirtualcaptualoperation.setFisaudit(false);
							//只有到账金额大于等于最小充值金额，才进行入账操作
							if(btcInfo.getAmount()>=fvirtualcointype.getFpushMinQty()){
								this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation) ;
							}
						}
					}
					
					//如果最新的订单id，不是起始扫描ID，则进行更新处理
					if (!newfrom.equals(scanfrom)){
						putMap(fvirtualcointype.getFid(), newfrom) ;
						fvirtualcointype.setStartTranId(newfrom);;
						this.virtualCoinService.updateObj(fvirtualcointype);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		}
	}
}
