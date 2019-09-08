package com.gt.quartz;
/*package com.gt.main.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.main.service.admin.VirtualCapitaloperationService;
import com.gt.main.service.admin.VirtualCoinService;
import com.gt.main.service.front.FrontVirtualCoinService;
import com.gt.util.WalletAPI;

import net.sf.json.JSONObject;

public class AutoGetWithdrawTxId {
	private static final Logger log = LoggerFactory
			.getLogger(AutoGetWithdrawTxId.class);
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService ;
	
	public void work() {
		synchronized (this) {
				
				try{
					String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
						int id = fvirtualcointype.getFid();
							try{

								if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
									continue ;
								}
								
								System.out.println(fvirtualcointype.getfShortName()+" 获取提现区块交易id...");
								String xx = "where ftype=2 and fstatus =3 and (ftradeUniqueNumber is null or ftradeUniqueNumber='') and (fTransferOrderId is not null or fTransferOrderId != '') and fvirtualcointype.fid="+id;
								List<Fvirtualcaptualoperation> alls = virtualCapitaloperationService.list(0, 0, xx, false);
								
								Map<String, String> txMap = new HashMap<String, String>();
								String coinType = fvirtualcointype.getfShortName();
								try {
									JSONObject walletType = WalletAPI.getWalletType();
									Map<String, Object> params = new HashMap<String, Object>();
									Long parentId = fvirtualcointype.getParentCid();
									if(0 != parentId){
										coinType = this.virtualCoinService.findById(parentId.intValue()).getfShortName();
										params.put("contract", fvirtualcointype.getFcontract());
									}
									params.put("walletTypeId", walletType.get(coinType));
									JSONObject result = WalletAPI.main("listTransfers", params );
									if(null != result && 200 == result.getInt("code")){
										txMap = result.getJSONObject("data");
									}
								} catch (Exception e) {
									// TODO: handle exception
									return ;
								}
								
								
								StringBuilder orderIds = new StringBuilder();
								if(null != txMap && txMap.size()>0){
									for (Fvirtualcaptualoperation fvirtualcaptualoperation : alls) {
										try {
											if(fvirtualcaptualoperation!=null){
												fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(fvirtualcaptualoperation.getFid()) ;
												String orderId = fvirtualcaptualoperation.getfTransferOrderId();
												if(txMap.containsKey(orderId) && null != txMap.get(orderId) && !"".equals(txMap.get(orderId))){
													fvirtualcaptualoperation.setFtradeUniqueNumber(txMap.get(orderId));
													virtualCapitaloperationService.updateObj(fvirtualcaptualoperation);
													orderIds.append(orderId+",");
												}
											}
										} catch (Exception e) {
											e.printStackTrace();
											continue;
										}
						 			}
								}
								
								if(null != orderIds && orderIds.toString().endsWith(",")){
									try {
										JSONObject walletType = WalletAPI.getWalletType();
										Map<String, Object> params = new HashMap<String, Object>();
										params.put("walletTypeId", walletType.get(coinType));
										params.put("orderIds", orderIds.substring(0, orderIds.length()-1));
										WalletAPI.main("changeTransfersStatus", params);
									} catch (Exception e) {
										// TODO: handle exception
										return ;
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
*/