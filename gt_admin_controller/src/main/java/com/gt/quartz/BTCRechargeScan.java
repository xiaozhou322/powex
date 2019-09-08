package com.gt.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.BTCUtils;

public class BTCRechargeScan {
	private static final Logger log = LoggerFactory
			.getLogger(BTCRechargeScan.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private UtilsService utilsService ;
	
	//last hash block,index,
	private Map<Integer, Long> map = new HashMap<Integer, Long>() ;
	public void putMap(Integer key,Long value){
		synchronized (map) {
			map.put(key, value) ;
		}
	}
	public Long getMap(Integer key){
		synchronized (key) {
			return map.get(key) ;
		}
	}
	
	public void init(){
		String filter = "where fstatus=1 and fisEth=0 and fisEtp=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
		List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
		//获取钱包中新数据
		for (Fvirtualcointype fvirtualcointype : coins) {
			List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 1, " where ftype=? and fvirtualcointype.fid=? order by fid desc ", true, Fvirtualcaptualoperation.class,VirtualCapitalOperationTypeEnum.COIN_IN,fvirtualcointype.getFid()) ;
			
			Long block = 0L ;

			block = (long)fvirtualcointype.getStartBlockId() ;

			putMap(fvirtualcointype.getFid(), block) ;
			
		}
	}
	
	public void work(){
		synchronized (this) {
			String filter = "where fstatus=1 and fisEth=0 and fisEtp=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
			List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
			
			
			//扫描处理比特币核心钱包的扫描
			for (Fvirtualcointype fvirtualcointype : coins) {
				try {
					
					
					BTCMessage btcMessage = new BTCMessage() ;
					btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
					btcMessage.setIP(fvirtualcointype.getFip()) ;
					btcMessage.setPORT(fvirtualcointype.getFport()) ;
					btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
					
					BTCUtils btcUtils = new BTCUtils(btcMessage) ;
					
					Long block = getMap(fvirtualcointype.getFid()) ;
					/*while(ethUtils.eth_blockNumberValue()>block){
						
						long count = ethUtils.eth_getBlockTransactionCountByNumberValue(block) ;
						for (int i = 0; i < count; i++) {
							BTCInfo btcInfo = ethUtils.getTransactionByBlockNumberAndIndexValue(block, i) ;
							String txid = btcInfo.getTxid() ;
							String address = btcInfo.getAddress().trim() ;
							int c = this.utilsService.count(" where ftradeUniqueNumber='"+txid+"' ", Fvirtualcaptualoperation.class) ;
							if(c>0){
								continue ;
							}
							System.out.println("eth:"+address);
							List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address.toLowerCase()) ;
							if(fvirtualaddresses.size()==1){
								Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
								Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0) ;
								Fuser fuser = fvirtualaddress.getFuser() ;
								fvirtualcaptualoperation.setFuser(fuser) ;
								fvirtualcaptualoperation.setFhasOwner(true) ;
								fvirtualcaptualoperation.setFamount(btcInfo.getAmount()) ;
								fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
								fvirtualcaptualoperation.setFfees(0F) ;
								fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
				
								fvirtualcaptualoperation.setFconfirmations(0) ;
								fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_0) ;
				
								fvirtualcaptualoperation.setFtradeUniqueNumber(btcInfo.getTxid().trim()) ;
								fvirtualcaptualoperation.setRecharge_virtual_address(btcInfo.getAddress().trim()) ;
								fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
								fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
								
//								fvirtualcaptualoperation.setFisPreAudit(false);
								this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation) ;
							}
							
						}
						block = block+1 ;
						putMap(fvirtualcointype.getFid(), block) ;
						fvirtualcointype.setStartBlockId((int)block.longValue());
						this.virtualCoinService.updateObj(fvirtualcointype);
					}*/
					
					
				} catch (Exception e) {
//					e.printStackTrace();
				}
				
				
			}
		}
	}
}
