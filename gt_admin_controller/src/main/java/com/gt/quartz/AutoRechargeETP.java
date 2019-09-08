package com.gt.quartz;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.ETPUtils;
import com.gt.util.Utils;

public class AutoRechargeETP {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeETP.class);
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	
	public void work() {
		synchronized (this) {
				try{
					String filter = "where fstatus=1 and fisrecharge=1 and fisEtp=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
							try{
								
								if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
									continue ;
								}
								System.out.println("钱包循环开始。。。。。。");
								BTCMessage btcMessage = new BTCMessage() ;
								btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
								btcMessage.setIP(fvirtualcointype.getFip()) ;
								btcMessage.setPORT(fvirtualcointype.getFport()) ;
								btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
								
								ETPUtils btcUtils = new ETPUtils(btcMessage) ;
								List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
								String hash = btcUtils.getBestBlockHash();
								int block = btcUtils.getBlockHeight(hash);
								int s = Long.valueOf(fvirtualcointype.getStartBlockId()).intValue();
								for(int i=s;i<=block;i++){
									boolean isTrue = true ;
									try {
										btcInfos = btcUtils.listAll(i);
										System.out.println("区块："+i+"本次找到记录共："+btcInfos.size());
									} catch (Exception e1) {
										e1.printStackTrace();
										isTrue = false ;
										break;
									}
									
									for (BTCInfo btcInfo : btcInfos) {
										
										String txid = btcInfo.getTxid().trim() ;
										System.out.println("正在循环读取每一条记录："+txid);
										List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.frontVirtualCoinService.findFvirtualcaptualoperationByProperty("ftradeUniqueNumber", txid) ;
										if(fvirtualcaptualoperations.size()>0){
											System.out.println("记录已经存在");
											continue ;
										}
										
										Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
						
										
										boolean hasOwner = true ;
										String address = btcInfo.getAddress().trim() ;
										List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address) ;
										if(fvirtualaddresses.size()==0){
											hasOwner = false ;//没有这个地址，充错进来了？没收！
										}else if(fvirtualaddresses.size()>1){
											log.error("Dumplicate Fvirtualaddress for address:"+address+" ,Fvirtualcointype:"+fvirtualcointype.getFid()) ;
											continue ;
										}else{
											Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0) ;
											Fuser fuser = fvirtualaddress.getFuser() ;
											fvirtualcaptualoperation.setFuser(fuser) ;
										}
						
										fvirtualcaptualoperation.setFhasOwner(hasOwner) ;
										fvirtualcaptualoperation.setFamount(btcInfo.getAmount()) ;
										fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
										fvirtualcaptualoperation.setFfees(0F) ;
										fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
						
										long times = btcInfo.getBlockNumber();
										if(times < fvirtualcointype.getFconfirm()) continue;
										fvirtualcaptualoperation.setFconfirmations(Integer.parseInt(btcInfo.getBlockNumber()+"")) ;
										fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
						
										fvirtualcaptualoperation.setFtradeUniqueNumber(btcInfo.getTxid().trim()) ;
										fvirtualcaptualoperation.setRecharge_virtual_address(btcInfo.getAddress().trim()) ;
										fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
										fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
										
										try {
											this.frontVirtualCoinService.updateFvirtualcaptualoperationCoinIn_ETP(fvirtualcaptualoperation) ;
										} catch (Exception e) {
											e.printStackTrace();
											isTrue = false ;
											break;
										}
										
									}//for
//									if(isTrue){
//										fvirtualcointype.setStartBlockId(i);
//										this.virtualCoinService.updateObj(fvirtualcointype);
//									}
								}//while
								
								
							}catch(Exception e){
								e.printStackTrace() ;
							}
							
						}//for
					
				}catch (Exception e) {
					e.printStackTrace() ;
				}
		}

		
	}
}
