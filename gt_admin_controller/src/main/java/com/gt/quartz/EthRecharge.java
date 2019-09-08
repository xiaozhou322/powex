package com.gt.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.auto.RechargeBtcData;
import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.UtilsService;
import com.gt.util.ETHUtils;
import com.gt.util.Utils;

public class EthRecharge {
	private static final Logger log = LoggerFactory
			.getLogger(EthRecharge.class);
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
		System.out.println("EthRecharge init。。。");
		String filter = "where fstatus=1 and fisEth=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
		List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
		//获取钱包中新数据
		for (Fvirtualcointype fvirtualcointype : coins) {
			List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 1, " where ftype=? and fvirtualcointype.fid=? order by fid desc ", true, Fvirtualcaptualoperation.class,VirtualCapitalOperationTypeEnum.COIN_IN,fvirtualcointype.getFid()) ;
			
			Long block = 0L ;
//			if(fvirtualcaptualoperations.size() > 0 ){
//				try {
//					Fvirtualcaptualoperation fvirtualcaptualoperation = fvirtualcaptualoperations.get(0) ;
//					
//					BTCMessage msg = new BTCMessage();
//					msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
//					msg.setIP(fvirtualcointype.getFip());
//					msg.setPORT(fvirtualcointype .getFport());
//					msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
//					ETHUtils ethUtils = new ETHUtils(msg) ;
//					BTCInfo btcInfo = ethUtils.eth_getTransactionByHashValue(fvirtualcaptualoperation.getFtradeUniqueNumber()) ;
//					
//					block = btcInfo.getBlockNumber() ;
//				} catch (Exception e) {
//					log.error(fvirtualcointype.getFname()+"钱包链接失败，请到后台修改后重启") ;
//					e.printStackTrace();
//				}
//			}else{
				block = (long)fvirtualcointype.getStartBlockId() ;
//			}
			
			putMap(fvirtualcointype.getFid(), block) ;
			
		}
	}
	
	public void work(){
		synchronized (this) {
			System.out.println("EthRecharge working。。。");
			String filter = "where fstatus=1 and fisEth=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE ;
			List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
			List<Fvirtualcointype> ethcoins = new ArrayList<Fvirtualcointype>();
			//代币集合
			Map<String, Fvirtualcointype> erc20coins = new HashMap<String, Fvirtualcointype>();
			//提取以太坊代币，除ETH和ETC之外的以太系都是代币
			for (Fvirtualcointype coin : coins){
				if(coin.isFiserc20()){
					erc20coins.put(coin.getFcontract().toLowerCase(), coin);
				}else{
					ethcoins.add(coin);
				}
			}
			
			//处理以太坊和以太经典的循环
			for (Fvirtualcointype fvirtualcointype : ethcoins) {
				try {
					
					
					BTCMessage btcMessage = new BTCMessage() ;
					btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
					btcMessage.setIP(fvirtualcointype.getFip()) ;
					btcMessage.setPORT(fvirtualcointype.getFport()) ;
					btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
					
					ETHUtils ethUtils = new ETHUtils(btcMessage) ;
					
					Long block = getMap(fvirtualcointype.getFid()) ;
					Long maxblock = ethUtils.eth_blockNumberValue();
					while(maxblock>block){
						long count = ethUtils.eth_getBlockTransactionCountByNumberValue(block) ;
						System.out.println(fvirtualcointype.getfShortName() + " Scan block :"+block + "/" +maxblock+" , txs count:"+count);
						for (int i = 0; i < count; i++) {
							BTCInfo btcInfo = ethUtils.getTransactionByBlockNumberAndIndexValue(block, i) ;
							String txid = btcInfo.getTxid() ;
							String address = btcInfo.getAddress().trim() ;
							int c = this.utilsService.count(" where ftradeUniqueNumber='"+txid+"' ", Fvirtualcaptualoperation.class) ;
							if(c>0){
								continue ;
							}
							//System.out.println("eth:"+address);
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
								double minchargeqty = fvirtualcointype.getFpushMinQty();
								if (btcInfo.getComment().equals("")){
									fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
								}else{
									//如果确定是代币，则需要保存为代币收入
									Fvirtualcointype erccoin = erc20coins.get(btcInfo.getComment());
									
									if (erccoin!=null){
										fvirtualcaptualoperation.setFvirtualcointype(erccoin);
										minchargeqty=erccoin.getFpushMinQty();
										//如果价格精度不是以太坊标准的18，则要对价格精度进行调整处理
										long deci = 0;
										if (erccoin.getFdecimals()<18){
											//小于18位的，需要补乘以相差位数的10次方
											deci=18-erccoin.getFdecimals();
											fvirtualcaptualoperation.setFamount(btcInfo.getAmount()*Math.pow(10,deci)) ;
										}else if (erccoin.getFdecimals()>18){
											//大于18位的，需要补除以相差位数的10次方
											deci=erccoin.getFdecimals()-18;
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
							
						}
						block +=1 ;
						putMap(fvirtualcointype.getFid(), block) ;
						fvirtualcointype.setStartBlockId((int)block.longValue());
						this.virtualCoinService.updateObj(fvirtualcointype);
						
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		}
	}
}
