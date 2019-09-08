package com.gt.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Utils;
import com.gt.util.WalletAPI;

import net.sf.json.JSONObject;

public class AutoRechargeCome_1 {
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

								if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
									continue ;
								}
								
								/*BTCMessage btcMessage = new BTCMessage() ;
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
								WalletUtil wallet =  WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), wmsg);*/
								System.out.println(fvirtualcointype.getfShortName()+" 充值确认扫描...");
								String xx = "where ftype=1 and fstatus <>3 and fvirtualcointype.fid="+id;
								List<Fvirtualcaptualoperation> alls = virtualCapitaloperationService.list(0, 0, xx, false);
								
								StringBuilder txIds = new StringBuilder();
								for(Fvirtualcaptualoperation fvirtualcaptualoperation : alls){
									try {
										if(fvirtualcaptualoperation!=null){
											//fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(fvirtualcaptualoperation.getFid()) ;
											String tradeNo = fvirtualcaptualoperation.getFtradeUniqueNumber();
											String txid = "";
											//分布处理两种不同的交易ID信息
											if (tradeNo.indexOf("_")>0){
												txid = tradeNo.split("_")[0] ;
											}else{
												txid = tradeNo ;
											}
											txIds.append(txid + ",");
										}
									} catch (Exception e) {
										e.printStackTrace();
										continue;
									}
								}
								
								Map<String, Integer> txMap = new HashMap<String, Integer>();
								String coinType = fvirtualcointype.getfShortName();
								Long parentId = fvirtualcointype.getParentCid();
								if(0 != parentId){
									coinType = this.virtualCoinService.findById(parentId.intValue()).getfShortName();
								}
								if(!"".equals(txIds.toString()) && txIds.toString().endsWith(",")){
									try {
										JSONObject walletType = WalletAPI.getWalletType();
										Map<String, Object> params = new HashMap<String, Object>();
										params.put("walletTypeId", walletType.get(coinType));
										params.put("txIds", txIds.substring(0, txIds.length()-1));
										JSONObject result = WalletAPI.main("getConforms", params );
										if(null != result && 200 == result.getInt("code")){
											txMap = result.getJSONObject("data");
										}
									} catch (Exception e) {
										// TODO: handle exception
										return ;
									}
								}
								
								StringBuilder confirm_txid = new StringBuilder();
								for (Fvirtualcaptualoperation fvirtualcaptualoperation : alls) {
									try {
										if(fvirtualcaptualoperation!=null){
											//fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(fvirtualcaptualoperation.getFid()) ;
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
												int confirms = txMap.get(txid);
												System.out.println(txid+" 确认数:"+confirms);
												if(confirms>=0){
													fvirtualcaptualoperation.setFconfirmations(confirms) ;
													if(confirms < fvirtualcointype.getFconfirm()){
														fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_2) ;
													}else{
														confirm_txid.append(txid+",");
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
										e.printStackTrace();
										continue;
									}
					 			}
								
								if(!"".equals(confirm_txid.toString()) && confirm_txid.toString().endsWith(",")){
									try {
										JSONObject walletType = WalletAPI.getWalletType();
										Map<String, Object> params = new HashMap<String, Object>();
										params.put("walletTypeId", walletType.get(coinType));
										params.put("txids", confirm_txid.substring(0, confirm_txid.length()-1));
										WalletAPI.main("changeTransactionsStatus", params );
									} catch (Exception e) {
										// TODO: handle exception
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
