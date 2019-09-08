package com.gt.quartz;

import java.security.Key;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.BTCUtils;
import com.gt.util.Utils;

public class AutoRechargeBtcCome {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeBtcCome.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService ;
	private boolean m_sync_flag = false ;
	
	public void work() {
		synchronized (this) {
				
				try{
					String filter = "where fstatus=1 and isEth=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
						int id = fvirtualcointype.getFid();
							try{

								if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
									continue ;
								}
								
								BTCMessage btcMessage = new BTCMessage() ;
								btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
								btcMessage.setIP(fvirtualcointype.getFip()) ;
								btcMessage.setPORT(fvirtualcointype.getFport()) ;
								btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
								
								BTCUtils btcUtils = new BTCUtils(btcMessage) ;
								String xx = "where ftype=1 and fstatus <>3 and fvirtualcointype.fid="+id;
								List<Fvirtualcaptualoperation> alls = virtualCapitaloperationService.list(0, 0, xx, false);
								for (Fvirtualcaptualoperation fvirtualcaptualoperation : alls) {
									try {
										if(fvirtualcaptualoperation!=null){
											fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(fvirtualcaptualoperation.getFid()) ;
											String tradeNo = fvirtualcaptualoperation.getFtradeUniqueNumber();
											String txid = tradeNo.split("_")[0] ;
											String address = fvirtualcaptualoperation.getRecharge_virtual_address() ;
											
											if(fvirtualcaptualoperation.getFstatus()!=VirtualCapitalOperationInStatusEnum.SUCCESS){
												BTCInfo btcInfo = null ;
												try {
													btcInfo = btcUtils.gettransactionValue(txid,address, "receive") ;
												} catch (Exception e1) {
												}
												if(btcInfo==null){
													break ;
												}
												if(btcInfo.getConfirmations()>=0){
													fvirtualcaptualoperation.setFamount(btcInfo.getAmount());
													fvirtualcaptualoperation.setFconfirmations(btcInfo.getConfirmations()) ;
													if(btcInfo.getConfirmations() < fvirtualcointype.getFconfirm()){
														fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_2) ;
													}else{
														if(fvirtualcointype.isFisauto()){
															fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
														}
													}
													fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
													try{
														this.frontVirtualCoinService.updateFvirtualcaptualoperationCoinIn(fvirtualcaptualoperation) ;
														if(fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS){
															this.rechargeBtcData.subRemove(id, tradeNo) ;
														}
													}catch(Exception e){
														e.printStackTrace() ;
													}
												}
												
											}else{
												this.rechargeBtcData.subRemove(id, tradeNo) ;
											}
										}
									} catch (Exception e) {
										continue;
									}
					 			}
							
							}catch(Exception e){
								e.printStackTrace() ;
							}
							
						}
					
				}catch (Exception e) {
					e.printStackTrace() ;
				}
		
		}

		
	}
	
}
