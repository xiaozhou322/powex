package com.gt.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.entity.BTCInfo;
import com.gt.entity.ConvertVirtualCoin;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.WalletMessage;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.ConvertVirtualCoinService;
import com.gt.util.wallet.WalletUtil;


public class AutoScanAndSendTransaction {
	
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private ConvertVirtualCoinService convertVirtualCoinService;
	
	public void start(final ConvertVirtualCoin convertVirtualCoin){
		try {
			new Thread(new Runnable() {
				public void run() {
					
					job(convertVirtualCoin);
					
				}
			}).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void job(ConvertVirtualCoin convertVirtualCoin){
		try {
			Fvirtualcointype fvirtualcointype1 = virtualCoinService.findById(convertVirtualCoin.getCoinType1());
			WalletUtil wallet1 = getWallet(fvirtualcointype1);
			Fvirtualcointype fvirtualcointype2 = virtualCoinService.findById(convertVirtualCoin.getCoinType2());
			WalletUtil wallet2 = getWallet(fvirtualcointype2);
			boolean exists = false;
			for(int i=0;i<30;i++){
				try {
					if(!exists){
						BTCInfo info = wallet1.getTransactionsValue(convertVirtualCoin.getTxid1(), convertVirtualCoin.getToAddress1());
						if(null != info){
							if(info.getAmount() != convertVirtualCoin.getAmount1() || info.getAddress() != convertVirtualCoin.getToAddress1()){
								break;
							}
							exists = true;
						}
					}
					int conforms = wallet1.getConforms(convertVirtualCoin.getTxid1());
					if(conforms >= fvirtualcointype1.getFconfirm()){
						convertVirtualCoin.setStatus(2);
						convertVirtualCoinService.update(convertVirtualCoin);
						boolean isSuccess = true;
						try {
							isSuccess = send(convertVirtualCoin, fvirtualcointype2, wallet2);
						} catch (Exception e) {
							e.printStackTrace();
							backAmount(convertVirtualCoin, fvirtualcointype1, wallet1);
						}
						if(!isSuccess){
							backAmount(convertVirtualCoin, fvirtualcointype1, wallet1);
						}
						break;
					}
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
			convertVirtualCoin = convertVirtualCoinService.findById(convertVirtualCoin.getId());
			if(1 == convertVirtualCoin.getStatus()){
				convertVirtualCoin.setStatus(5);
				convertVirtualCoinService.update(convertVirtualCoin);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private WalletUtil getWallet(Fvirtualcointype fvirtualcointype){
		WalletUtil wallet = null;
		try {
			WalletMessage walletmsg = new WalletMessage();
			walletmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
			walletmsg.setIP(fvirtualcointype.getFip()) ;
			walletmsg.setPORT(fvirtualcointype.getFport()) ;
			walletmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
			walletmsg.setDECIMALS(fvirtualcointype.getFdecimals());
			walletmsg.setPASSWORD(fvirtualcointype.getFpassword());
			if(fvirtualcointype.getParentCid()>0){
				walletmsg.setISERC20(true);
				walletmsg.setCONTRACT(fvirtualcointype.getFcontract());
			}
		
			wallet = WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), walletmsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wallet;
	}
	
	/**
	 * 转账
	 * @param convertVirtualCoin
	 * @param fvirtualcointype
	 * @param wallet
	 * @throws Exception 
	 */
	private boolean send(ConvertVirtualCoin convertVirtualCoin,Fvirtualcointype fvirtualcointype, WalletUtil wallet) throws Exception {
		String txid2 = wallet.transfer(fvirtualcointype.getMainAddr(), convertVirtualCoin.getToAddress2(), convertVirtualCoin.getAmount2(), fvirtualcointype.getFpushMinPrice(), "");
		System.out.println("转账交易ID："+txid2);
		if(txid2 != null && !"".equals(txid2)){
			convertVirtualCoin.setTxid2(txid2);
			convertVirtualCoin.setStatus(3);
			convertVirtualCoinService.update(convertVirtualCoin);
			return true;
		}
		return false;
	}
	
	/**
	 * 退款
	 * @param convertVirtualCoin
	 * @param fvirtualcointype
	 * @param wallet
	 */
	private void backAmount(ConvertVirtualCoin convertVirtualCoin, Fvirtualcointype fvirtualcointype, WalletUtil wallet){
		try {
			String txid3 = wallet.transfer(fvirtualcointype.getMainAddr(), convertVirtualCoin.getFromAddress1(), convertVirtualCoin.getAmount1(), fvirtualcointype.getFpushMinPrice(), "");
			System.out.println("转账交易ID："+txid3);
			if(txid3 != null && !"".equals(txid3)){
				convertVirtualCoin.setTxid2(txid3);
				convertVirtualCoinService.update(convertVirtualCoin);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
