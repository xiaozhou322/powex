package com.gt.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.entity.BTCMessage;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.util.BTCUtils;
import com.gt.util.ETHUtils;

public class AutoSendCoinUtils {
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService;
	
	public void work() {
		synchronized (this) {
			String filter = "where ftype=2 and fstatus=3 and fisaudit=0 and (ftradeUniqueNumber is null or ftradeUniqueNumber='')";
			List<Fvirtualcaptualoperation> alls = this.virtualCapitaloperationService.list(0, 0, filter, false);
			for (Fvirtualcaptualoperation fvirtualcaptualoperation : alls) {
				Fvirtualcointype fvirtualcointype = fvirtualcaptualoperation.getFvirtualcointype();
				if(fvirtualcaptualoperation.isFisaudit()) continue;
				System.out.println("============1==="+fvirtualcaptualoperation.getFtradeUniqueNumber());
				if(fvirtualcaptualoperation.getFtradeUniqueNumber() != null && fvirtualcaptualoperation.getFtradeUniqueNumber().trim().length() >0) continue;
				System.out.println("============12==="+fvirtualcaptualoperation.getFtradeUniqueNumber());
				try {
					fvirtualcaptualoperation.setFisaudit(true);
					this.virtualCapitaloperationService.updateObj(fvirtualcaptualoperation);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					if(fvirtualcointype.isFisEth()){
						BTCMessage btcMsg = new BTCMessage();
						btcMsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
						btcMsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
						btcMsg.setIP(fvirtualcointype.getFip());
						btcMsg.setPASSWORD(fvirtualcointype.getFpassword());
						btcMsg.setPORT(fvirtualcointype.getFport());
						ETHUtils ethUtils = new ETHUtils(btcMsg);

						String address = fvirtualcaptualoperation.getWithdraw_virtual_address();
						double amount = fvirtualcaptualoperation.getFamount();
						String flag = ethUtils.sendtoaddressValue(fvirtualcointype.getMainAddr(), address, amount,ethUtils.EXfee(fvirtualcaptualoperation.getFfees()*0.67));
						if(flag != null && !"".equals(flag)){
							fvirtualcaptualoperation.setFtradeUniqueNumber("[out]"+flag);
							this.virtualCapitaloperationService.updateObj(fvirtualcaptualoperation);
						}
					
					}else{
						BTCMessage btcMsg = new BTCMessage();
						btcMsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
						btcMsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
						btcMsg.setIP(fvirtualcointype.getFip());
						btcMsg.setPASSWORD(fvirtualcointype.getFpassword());
						btcMsg.setPORT(fvirtualcointype.getFport());
						BTCUtils btcUtils = new BTCUtils(btcMsg);
						
						String address = fvirtualcaptualoperation.getWithdraw_virtual_address();
						double amount = fvirtualcaptualoperation.getFamount();
						String flag = btcUtils.sendtoaddressValue(address,amount,0,fvirtualcaptualoperation.getFid().toString());
						if(flag != null && flag.trim().length() >0){
							fvirtualcaptualoperation.setFtradeUniqueNumber(flag);
							this.virtualCapitaloperationService.updateObj(fvirtualcaptualoperation);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}