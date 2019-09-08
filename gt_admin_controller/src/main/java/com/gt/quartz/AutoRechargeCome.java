package com.gt.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.WalletMessage;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.Utils;
import com.gt.util.wallet.WalletUtil;

public class AutoRechargeCome {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeCome_1.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	private boolean m_sync_flag = false ;
	
	public void work() {
		synchronized (this) {
				
				try{
					String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
						int id = fvirtualcointype.getFid();
							try{

								if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal || null==fvirtualcointype.getFclassPath() || fvirtualcointype.getFclassPath().equals("")){
									continue ;
								}
								
								BTCMessage btcMessage = new BTCMessage() ;
								btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
								btcMessage.setIP(fvirtualcointype.getFip()) ;
								btcMessage.setPORT(fvirtualcointype.getFport()) ;
								btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
								
								WalletMessage wmsg = new WalletMessage();
								wmsg.setIP(fvirtualcointype.getFip());
								wmsg.setPORT(fvirtualcointype.getFport());
								wmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
								wmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
								wmsg.setISERC20(fvirtualcointype.isFiserc20());
								wmsg.setCONTRACT(fvirtualcointype.getFcontract());
//								wmsg.setISERC20(false);
								System.out.println(fvirtualcointype.getfShortName()+" 充值确认扫描...");
								WalletUtil wallet =  WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), wmsg);
								String xx = "where ftype=1 and fstatus <>3 and fvirtualcointype.fid="+id;
								List<Fvirtualcaptualoperation> alls = virtualCapitaloperationService.list(0, 0, xx, false);
								for (Fvirtualcaptualoperation fvirtualcaptualoperation : alls) {
									try {
										if(fvirtualcaptualoperation!=null){
											fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(fvirtualcaptualoperation.getFid()) ;
											String tradeNo = fvirtualcaptualoperation.getFtradeUniqueNumber();
											String txid = "";
											//分布处理两种不同的交易ID信息
											if (tradeNo.indexOf("_")>0){
												txid = tradeNo.split("_")[0] ;
											}else{
												txid = tradeNo ;
											}
											String address = fvirtualcaptualoperation.getRecharge_virtual_address() ;
											//System.out.println(fvirtualcointype.getfShortName()+" 确认:"+txid);
											if(fvirtualcaptualoperation.getFstatus()!=VirtualCapitalOperationInStatusEnum.SUCCESS){
												int confirms = wallet.getConforms(txid);
												System.out.println(txid+" 确认数:"+confirms);
												if(confirms>=0){
													fvirtualcaptualoperation.setFconfirmations(confirms) ;
													if(confirms < fvirtualcointype.getFconfirm()){
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
															//充值成功则进行是否进行充值奖励
															int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fvirtualcaptualoperation.getFuser().getFid(),"首次充值奖励");
															if (sendcount==0 ) {
																Fintrolinfo introlInfo = null;
																Fvirtualwallet fvirtualwallet = null;
																//String[] auditSendCoin = constantMap.getString("tradePassWordSendCoin").split("#");
																String[] auditSendCoin = frontConstantMapService.getString("tradePassWordSendCoin").split("#");
																int coinID = Integer.parseInt(auditSendCoin[0]);
																double coinQty = Double.valueOf(auditSendCoin[1]);
																Fvirtualcointype fvirtualcointype1=frontVirtualCoinService.findFvirtualCoinById(coinID);
																if(coinID>0 && coinQty>0 && null!=fvirtualcointype1) {
																	fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fvirtualcaptualoperation.getFuser().getFid(), fvirtualcointype1.getFid());
																	fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
																	introlInfo = new Fintrolinfo();
																	introlInfo.setFcreatetime(Utils.getTimestamp());
																	introlInfo.setFiscny(false);
																	introlInfo.setFqty(coinQty);
																	introlInfo.setFuser(fvirtualcaptualoperation.getFuser());
																	introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_RECHARHE);
																	introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
																	introlInfo.setFtitle("首次充值奖励");
																}
																this.userService.updateObj(null, introlInfo, fvirtualwallet);
															}
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
										e.printStackTrace();
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
