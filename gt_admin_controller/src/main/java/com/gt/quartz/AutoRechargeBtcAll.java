package com.gt.quartz;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.BTCUtils;
import com.gt.util.Utils;

public class AutoRechargeBtcAll {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeBtcAll.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	private boolean m_sync_flag = false ;
	
	public void init() {
		synchronized (this) {
				try{
					String filter = "where fstatus=1 and isEth=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
						job(fvirtualcointype) ;
						
					}//for
					
					System.out.println("AutoRechargeBtcAll");
				}catch (Exception e) {
					e.printStackTrace() ;
				}
		
		}

		
	}
	
	public void job(final Fvirtualcointype fvirtualcointype){
		
		try {
			new Thread(new Runnable() {
				
				public void run() {
					while(true){
						
						try {
							recharge(fvirtualcointype) ;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						try {
							Thread.sleep(60000L);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public boolean recharge(Fvirtualcointype fvirtualcointype){

		int id = fvirtualcointype.getFid();
		System.out.println(fvirtualcointype.getfShortName()+" Scan rechage...");
			try{
				int begin = 0 ;
				int step = 1000 ;
				boolean is_continue = true ;
				
				if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
					return false ;
				}
				BTCMessage btcMessage = new BTCMessage() ;
				btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
				btcMessage.setIP(fvirtualcointype.getFip()) ;
				btcMessage.setPORT(fvirtualcointype.getFport()) ;
				btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
				
				String firstAddress = null ;
				BTCUtils btcUtils = new BTCUtils(btcMessage) ;
				List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
				
				while(is_continue){
					try {
						btcInfos = btcUtils.listtransactionsValue(step, begin) ;
						if(firstAddress==null && btcInfos.size()>0){
							firstAddress = btcInfos.get(0).getTxid().trim() ;
						}
						if(btcInfos == null || btcInfos.size() == 0){
							is_continue = false ;
						}else{
							begin+=btcInfos.size() ;
						}
					} catch (Exception e1) {
						break;
					}
					
					for (BTCInfo btcInfo : btcInfos) {
						
						String txid = btcInfo.getTxid().trim() ;
						
						Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
		
						Fuser fuser = null ;
						boolean hasOwner = true ;
						String address = btcInfo.getAddress().trim() ;
						List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address) ;
						if(fvirtualaddresses.size()==0){
							hasOwner = false ;//没有这个地址，充错进来了？没收！
							continue ;
						}else if(fvirtualaddresses.size()>1){
							log.error("Dumplicate Fvirtualaddress for address:"+address+" ,Fvirtualcointype:"+fvirtualcointype.getFid()) ;
							continue ;
						}else if(btcInfo.getAmount()<fvirtualcointype.getFpushMinQty()){
							log.error("到账金额小于最小充值金额，不入账") ;//小于最小充值金额，不入账！
							continue ;
						}else{
							Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0) ;
							fuser = fvirtualaddress.getFuser() ;
							fvirtualcaptualoperation.setFuser(fuser) ;
							
							
							List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.frontVirtualCoinService.findFvirtualcaptualoperationByProperty("ftradeUniqueNumber", txid+"_"+fuser.getFid()) ;
							if(fvirtualcaptualoperations.size()>0){
								continue ;
							}
						}
		
						fvirtualcaptualoperation.setFhasOwner(hasOwner) ;
						fvirtualcaptualoperation.setFamount(btcInfo.getAmount()) ;
						fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
						fvirtualcaptualoperation.setFfees(0F) ;
						fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
		
						fvirtualcaptualoperation.setFconfirmations(0) ;
						fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_0) ;
		
						if(fuser!=null ){
							fvirtualcaptualoperation.setFtradeUniqueNumber(txid+"_"+fuser.getFid()) ;
						}
						
						fvirtualcaptualoperation.setRecharge_virtual_address(address) ;
						fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
						fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
//						fvirtualcaptualoperation.setFisPreAudit(false);
						try {
							this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation) ;
							this.rechargeBtcData.subPut(id, fvirtualcaptualoperation.getFtradeUniqueNumber(), fvirtualcaptualoperation) ;
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}//for
					
					
				}//while
				if(firstAddress!=null){
					this.rechargeBtcData.setTradeRecordMap(id, firstAddress) ;
				}
				
			
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
			return false ;
	}
	
	
}
