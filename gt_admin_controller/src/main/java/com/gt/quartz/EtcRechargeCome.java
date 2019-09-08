package com.gt.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.ETHUtils;
import com.gt.util.Utils;

public class EtcRechargeCome {
	private static final Logger log = LoggerFactory
			.getLogger(EtcRechargeCome.class);
	
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private UtilsService utilsService ;
	
	public void work() {
		synchronized (this) {
				
				try{
					String filter = "where fstatus=1 and fisEth=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					for (Fvirtualcointype fvirtualcointype : coins) {
						try {
							BTCMessage msg = new BTCMessage();
							msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
							msg.setIP(fvirtualcointype.getFip());
							msg.setPORT(fvirtualcointype .getFport());
							msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
							ETHUtils ethUtils = new ETHUtils(msg) ;
							
							List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 1, " where ftype=? and fvirtualcointype.fid=? and fstatus<>? order by fid desc ", true, Fvirtualcaptualoperation.class,VirtualCapitalOperationTypeEnum.COIN_IN,fvirtualcointype.getFid(),VirtualCapitalOperationInStatusEnum.SUCCESS) ;
							for (Fvirtualcaptualoperation fvirtualcaptualoperation : fvirtualcaptualoperations) {
								try {
									String txid = fvirtualcaptualoperation.getFtradeUniqueNumber() ;
									BTCInfo btcInfo = ethUtils.eth_getTransactionByHashValue(txid) ;
									if(btcInfo.getConfirmations()>=fvirtualcointype.getFconfirm()){
										fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
										fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
										this.frontVirtualCoinService.updateFvirtualcaptualoperationCoinIn(fvirtualcaptualoperation) ;
										
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}catch (Exception e) {
					e.printStackTrace() ;
				}
		
		}

		
	}
	
}
