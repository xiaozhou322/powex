package com.gt.quartz;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.gt.entity.WalletMessage;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.BTCUtils;
import com.gt.util.Utils;
import com.gt.util.WalletAPI;
import com.gt.util.wallet.WalletUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AutoRechargeCoinAll_1 {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeCoinAll_1.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	/*private boolean m_sync_flag = false ;
	
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
	}*/
	
	private ConcurrentMap<Integer, Map<String, Fvirtualcointype>> erc20coinsMap = new ConcurrentHashMap<Integer, Map<String,Fvirtualcointype>>();
	
	public void init() {
		synchronized (this) {
				try{
					//只检测主币种，代币都不需要做区块扫描，由主币种完成扫描
					String filter = "where fstatus=1 and fparentCid=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
						/*if(fvirtualcointype.getStartBlockId()>0){
							Long block = (long)fvirtualcointype.getStartBlockId() ;
							putMap(fvirtualcointype.getFid(), block) ;
						}*/
						
						int id = fvirtualcointype.getFid();
						loaderc20coins(id);
						
						job(fvirtualcointype) ;
						
					}//for
					
				}catch (Exception e) {
					e.printStackTrace() ;
				}
		
		}

		
	}
	
	public void loaderc20coins(int id){
		//读取代币
		String filter2 = "where fstatus=1 and fparentCid=" + id + " and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> coins2 = this.virtualCoinService.list(0, 0, filter2, false);
		//代币集合
		Map<String, Fvirtualcointype> erc20coins = new HashMap<String, Fvirtualcointype>();
		for (Fvirtualcointype coin : coins2){
			erc20coins.put(coin.getFcontract().toLowerCase(), coin);
		}
		erc20coinsMap.put(id, erc20coins);
	}
	
	/*public void loadUserAddress(int id){
		List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(id) ;
		Map<String, Fuser> map = new HashMap<String , Fuser>();
		for(Fvirtualaddress fvirtualaddress : fvirtualaddresses){
			map.put(fvirtualaddress.getFadderess(), fvirtualaddress.getFuser());
		}
		userAddressMap.put(id, map);
	}*/
	
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

		System.out.println(fvirtualcointype.getfShortName()+"("+fvirtualcointype.getStartBlockId()+") Scan rechage...");
		rechargeByList(fvirtualcointype);
		return false ;
	}
	
	public boolean rechargeByList(Fvirtualcointype fvirtualcointype){

		int id = fvirtualcointype.getFid();
		System.out.println(fvirtualcointype.getfShortName()+" Scan rechage By List...");
			try{
				
				if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
					return false ;
				}
				
				
				String firstAddress = null ;
				List<JSONObject> arr = new ArrayList<JSONObject>();
				try {
					//调用钱包管理系统获取最新交易数据
					JSONObject walletType = WalletAPI.getWalletType();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("walletTypeId", walletType.get(fvirtualcointype.getfShortName()));
					JSONObject result = WalletAPI.main("listTransactions", params );
					if(null != result && 200 == result.getInt("code")){
						arr = result.getJSONArray("data");
					}
					
					if(firstAddress==null && arr.size()>0){
						firstAddress = arr.get(0).getString("txId").trim() ;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				
				Map<String, Fvirtualcointype> erc20coins = erc20coinsMap.get(id);
				//Map<String, Fuser> userAddress = userAddressMap.get(id);
				for (JSONObject btcInfo : arr) {
					String txid = btcInfo.getString("txId").trim();
					
					Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
	
					Fuser fuser = null ;
					boolean hasOwner = true ;
					String address = btcInfo.getString("address").trim();
					List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address) ;
					if(fvirtualaddresses.size()==0){
						hasOwner = false ;//没有这个地址，充错进来了？没收！
						continue ;
					}else if(fvirtualaddresses.size()>1){
						log.error("Dumplicate Fvirtualaddress for address:"+address+" ,Fvirtualcointype:"+fvirtualcointype.getFid()) ;
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
					//System.out.println(fvirtualcointype.getfShortName()+":"+txid);
					fvirtualcaptualoperation.setFhasOwner(hasOwner) ;
					fvirtualcaptualoperation.setFamount(btcInfo.getDouble("amount")) ;
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
					double minchargeqty = fvirtualcointype.getFpushMinQty();
					if (btcInfo.getString("contract").equals("")){
						fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
					}else{
						//如果确定是代币，则需要保存为代币收入
						Fvirtualcointype erccoin = erc20coins.get(btcInfo.getString("contract").toLowerCase());
						//代币数值精度可能与主币不一致，所以需要做调整
						if (erccoin!=null){
							fvirtualcaptualoperation.setFvirtualcointype(erccoin);
							minchargeqty=erccoin.getFpushMinQty();
							/*long deci = 0;
							if (erccoin.getFdecimals()<fvirtualcointype.getFdecimals()){
								//小于18位的，需要补乘以相差位数的10次方
								deci=fvirtualcointype.getFdecimals()-erccoin.getFdecimals();
								fvirtualcaptualoperation.setFamount(btcInfo.getDouble("amount")*Math.pow(10,deci)) ;
							}else if (erccoin.getFdecimals()>fvirtualcointype.getFdecimals()){
								//大于18位的，需要补除以相差位数的10次方
								deci=erccoin.getFdecimals()-fvirtualcointype.getFdecimals();
								fvirtualcaptualoperation.setFamount(btcInfo.getDouble("amount")/Math.pow(10,deci));
							}*/
							fvirtualcaptualoperation.setFamount(btcInfo.getDouble("amount")/Math.pow(10,erccoin.getFdecimals()));
						}else{
							fvirtualcaptualoperation.setFvirtualcointype(null);
							minchargeqty=0;
							
						}
					}
//						fvirtualcaptualoperation.setFisPreAudit(false);
					try {
						if(fvirtualcaptualoperation.getFamount()>=minchargeqty){
							this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation) ;
							this.rechargeBtcData.subPut(id, fvirtualcaptualoperation.getFtradeUniqueNumber(), fvirtualcaptualoperation) ;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(firstAddress!=null){
					this.rechargeBtcData.setTradeRecordMap(id, firstAddress) ;
				}
				
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
			return false ;
	}
	
}
