package com.gt.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.NperStatusEnum;
import com.gt.entity.Lottery;
import com.gt.entity.Nper;
import com.gt.entity.WalletMessage;
import com.gt.service.front.LotteryService;
import com.gt.service.front.NperService;
import com.gt.util.Constant;
import com.gt.util.Utils;
import com.gt.util.wallet.BitCoin;
import com.gt.util.wallet.WalletUtil;

public class AutoSendToBlock {

	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private NperService nperService;
	
	public void work(){
		synchronized (this) {
			
			String nper = "";
			String sql = "where status >= "+NperStatusEnum.START_ING;
			List<Nper> li = nperService.list(0, 0, sql, false);
			if(null != li && li.size()==1){
				nper = li.get(0).getNper();
			}
			
			String filter = "where block_link is null or block_link=''";
			List<Lottery> list =lotteryService.list(0, 0, filter , false,Utils.getLotteryTable(nper));
			
			for(Lottery lottery : list){
				
				WalletMessage wmsg = new WalletMessage();
				wmsg.setIP(Constant.WalletIp);
				wmsg.setPORT(Constant.WalletPost);
				wmsg.setACCESS_KEY(Constant.WalletAccessKey);
				wmsg.setSECRET_KEY(Constant.WalletSecretKey);
				wmsg.setPASSWORD(Constant.WalletPassword);
				BitCoin wallet = null;
				try {
					wallet = (BitCoin) WalletUtil.createWalletByClass("com.gt.util.wallet.BitCoin", wmsg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String txid = "";
				try {
					txid = wallet.sendmessage1("", lottery.getLottery_no()+lottery.getSerial_no(), 0.0002);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lottery.setBlock_link("https://chainz.cryptoid.info/dgb/tx.dws?"+txid+".htm");
				lotteryService.updateObj(lottery,Utils.getLotteryTable(nper));
			}
		}
	}
	
}
