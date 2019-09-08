package com.gt.quartz;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gt.entity.WalletMessage;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.BTCUtils;
import com.gt.util.Utils;
import com.gt.util.wallet.WalletUtil;

public class AutoRechargeCoinAll {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeCoinAll_1.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	private boolean m_sync_flag = false ;
	
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
	
	public void init() {
		synchronized (this) {
				try{
					//只检测主币种，代币都不需要做区块扫描，由主币种完成扫描
					String filter = "where fstatus=1 and fparentCid=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
						if(fvirtualcointype.getStartBlockId()>0){
							Long block = (long)fvirtualcointype.getStartBlockId() ;
							putMap(fvirtualcointype.getFid(), block) ;
						}
						job(fvirtualcointype) ;
						
					}//for
					
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
		System.out.println(fvirtualcointype.getfShortName()+"("+fvirtualcointype.getStartBlockId()+") Scan rechage...");
		//扫描区块的方法和直接获取交易列表的方法不一样
		if(fvirtualcointype.getStartBlockId()>0){
			rechargeByBlock(fvirtualcointype);
		}else{
			rechargeByList(fvirtualcointype);
		}
		return false ;
	}
	
	public boolean rechargeByList(Fvirtualcointype fvirtualcointype){

		int id = fvirtualcointype.getFid();
		System.out.println(fvirtualcointype.getfShortName()+" Scan rechage By List...");
			try{
				int begin = 0 ;
				int step = 1000 ;
				boolean is_continue = true ;
				
				if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal || null==fvirtualcointype.getFclassPath() || fvirtualcointype.getFclassPath().equals("")){
					return false ;
				}
				
				//读取代币
				String filter = "where fstatus=1 and fparentCid=" + id + " and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
				List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
				//代币集合
				Map<String, Fvirtualcointype> erc20coins = new HashMap<String, Fvirtualcointype>();
				for (Fvirtualcointype coin : coins){
					erc20coins.put(coin.getFcontract().toLowerCase(), coin);
				}
				
				WalletMessage walletmsg = new WalletMessage();
				walletmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
				walletmsg.setIP(fvirtualcointype.getFip()) ;
				walletmsg.setPORT(fvirtualcointype.getFport()) ;
				walletmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
				walletmsg.setDECIMALS(fvirtualcointype.getFdecimals());
				
				String firstAddress = null ;
				WalletUtil wallet = WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), walletmsg);
				List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
				
				//非按区块扫描的方式
				if(fvirtualcointype.getStartBlockId()<=0){
					while(is_continue){
						try {
							btcInfos = wallet.listTransactionsValue(String.valueOf(step), String.valueOf(begin)) ;
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
							if(address.toLowerCase().equals(fvirtualcointype.getMainAddr().toLowerCase())) {
								//存入汇总地址的操作，都不做处理，直接入账
								log.error("存入汇总地址，address:"+address+" ,Fvirtualcointype:"+fvirtualcointype.getFid()) ;
								continue ;
							}
							
							
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
							double minchargeqty = fvirtualcointype.getFpushMinQty();
							if (btcInfo.getContract().equals("")){
								fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
							}else{
								//如果确定是代币，则需要保存为代币收入
								Fvirtualcointype erccoin = erc20coins.get(btcInfo.getContract().toLowerCase());
								//代币数值精度可能与主币不一致，所以需要做调整
								if (erccoin!=null){
									fvirtualcaptualoperation.setFvirtualcointype(erccoin);
									minchargeqty=erccoin.getFpushMinQty();
									long deci = 0;
									if (erccoin.getFdecimals()<fvirtualcointype.getFdecimals()){
										//小于18位的，需要补乘以相差位数的10次方
										deci=fvirtualcointype.getFdecimals()-erccoin.getFdecimals();
										fvirtualcaptualoperation.setFamount(btcInfo.getAmount()*Math.pow(10,deci)) ;
									}else if (erccoin.getFdecimals()>fvirtualcointype.getFdecimals()){
										//大于18位的，需要补除以相差位数的10次方
										deci=erccoin.getFdecimals()-fvirtualcointype.getFdecimals();
										fvirtualcaptualoperation.setFamount(btcInfo.getAmount()/Math.pow(10,deci));
									}
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
							
						}//for
					}//while
					if(firstAddress!=null){
						this.rechargeBtcData.setTradeRecordMap(id, firstAddress) ;
					}
				
				}
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
			return false ;
	}
	
	public boolean rechargeByBlock(Fvirtualcointype fvcoin){

		int id = fvcoin.getFid();
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findById(id);
		boolean isBts = fvirtualcointype.isFisBts();
		String mainAddress = fvirtualcointype.getMainAddr();
		System.out.println(fvirtualcointype.getfShortName()+" Scan rechage By Block...,Start block:"+getMap(fvirtualcointype.getFid()));
			try{
				
				if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal || null==fvirtualcointype.getFclassPath() || fvirtualcointype.getFclassPath().equals("")){
					return false ;
				}
				
				//读取代币
				String filter = "where fstatus=1 and fparentCid=" + id + " and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
				List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);

				//代币集合
				Map<String, Fvirtualcointype> erc20coins = new HashMap<String, Fvirtualcointype>();
				//提取以太坊代币，除ETH和ETC之外的以太系都是代币
				for (Fvirtualcointype coin : coins){
					erc20coins.put(coin.getFcontract().toLowerCase(), coin);
				}
				
				WalletMessage walletmsg = new WalletMessage();
				walletmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
				walletmsg.setIP(fvirtualcointype.getFip()) ;
				walletmsg.setPORT(fvirtualcointype.getFport()) ;
				walletmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
				walletmsg.setDECIMALS(fvirtualcointype.getFdecimals());
				
				String firstAddress = null ;
				WalletUtil wallet = WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), walletmsg);
				List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
				//非按区块扫描的方式
				if(fvirtualcointype.getStartBlockId()>0){
					Long block = getMap(fvirtualcointype.getFid()) ;
					Long maxblock = wallet.bestBlockNumberValue();
					int maxCount = 0;
					while(maxblock>block){
						
						try {
							btcInfos = wallet.listTransactionsValue(String.valueOf(block), "") ;
							if(firstAddress==null && btcInfos.size()>0){
								firstAddress = btcInfos.get(0).getTxid().trim() ;
							}
						} catch (Exception e1) {
							e1.printStackTrace();
							break;
						}
						
						for (BTCInfo btcInfo : btcInfos) {
							String txid = btcInfo.getTxid() ;
							//System.out.println(fvirtualcointype.getfShortName() + ":" + txid);
							String address = btcInfo.getAddress().trim() ;
							List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.frontVirtualCoinService.findFvirtualcaptualoperationByProperty("ftradeUniqueNumber", txid) ;
							if(fvirtualcaptualoperations.size()>0){
								continue ;
							}
							//检测是否为BTS或者EOS核心的钱包，该类钱包都是使用备注信息区分用户
							if(isBts && null!=mainAddress) {
								//首先需要判断是否收款地址是否为主地址，否则忽略
								if(!mainAddress.toLowerCase().equals(address.toLowerCase())){
									continue ;
								}
								//如果收款地址是主地址，则将备注信息设置为地址进行处理
								//没有备注信息的充值记录，全部都不处理
								if(null==btcInfo.getComment()) {
									continue ;
								}
								address = btcInfo.getComment().trim() ;
							}
							List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address.toLowerCase()) ;
							if(fvirtualaddresses.size()==1){
								Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
								Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0);
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
								fvirtualcaptualoperation.setRecharge_virtual_address(address) ;
								fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
								double minchargeqty = fvirtualcointype.getFpushMinQty();
								if (null==btcInfo.getContract() || btcInfo.getContract().equals("")){
									fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
								}else{
									//如果确定是代币，则需要保存为代币收入
									Fvirtualcointype erccoin = erc20coins.get(btcInfo.getContract().toLowerCase());
									
									if (erccoin!=null){
										fvirtualcaptualoperation.setFvirtualcointype(erccoin);
										minchargeqty=erccoin.getFpushMinQty();
										
										long deci = 0;
										if (erccoin.getFdecimals()<fvirtualcointype.getFdecimals()){
											//小于18位的，需要补乘以相差位数的10次方
											deci=fvirtualcointype.getFdecimals()-erccoin.getFdecimals();
											fvirtualcaptualoperation.setFamount(btcInfo.getAmount()*Math.pow(10,deci)) ;
										}else if (erccoin.getFdecimals()>fvirtualcointype.getFdecimals()){
											//大于18位的，需要补除以相差位数的10次方
											deci=erccoin.getFdecimals()-fvirtualcointype.getFdecimals();
											fvirtualcaptualoperation.setFamount(btcInfo.getAmount()/Math.pow(10,deci));
										}
										
									}else{
										fvirtualcaptualoperation.setFvirtualcointype(null);
										minchargeqty=0;
									}
								}
//								fvirtualcaptualoperation.setFisPreAudit(false);
								//只有到账金额大于等于最小充值金额才会进行入账操作
								if(fvirtualcaptualoperation.getFamount()>=minchargeqty){
									this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation) ;
								}
							}
							
						}//for
						
						block +=1 ;
						putMap(fvirtualcointype.getFid(), block) ;
						fvirtualcointype.setStartBlockId(block.longValue());
						maxCount++;
						
						//每次扫描500个块
						//if (fvirtualcointype.getfShortName().equals("ETH")){
							//System.out.println(fvirtualcointype.getfShortName() + ":" + block);
						if (maxCount>=500) {break;}
						//}
						
					}//while
					System.out.println(fvirtualcointype.getfShortName() + "更新区块id："+block);
					this.virtualCoinService.updateObj(fvirtualcointype);
					
					if(firstAddress!=null){
						this.rechargeBtcData.setTradeRecordMap(id, firstAddress) ;
					}
				
				}
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
			return false ;
	}
	
	
}
