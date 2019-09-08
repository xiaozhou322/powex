package com.gt.quartz;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.SubFrozenTypeEnum;
import com.gt.Enum.SubStatusEnum;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fsubscriptionlog;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.SubscriptionLogService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;

public class SubUtils {
	
	@Autowired
	private SubscriptionLogService subscriptionLogService;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	
	public void work(){	
		synchronized (this) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			int day = c.get(Calendar.DAY_OF_MONTH);
			String filter = "where flastcount>0 and fstatus="+SubStatusEnum.INIT+" and fsubscription.fisstart=1";
			List<Fsubscriptionlog> fsubscriptionlogs = this.subscriptionLogService.list(0, 0, filter, false);
			for (Fsubscriptionlog fsubscriptionlog : fsubscriptionlogs) {
				if(fsubscriptionlog.getFlastcount() <= 0)continue;
                if(day !=1 && fsubscriptionlog.getFsubscription().getFfrozenType() == SubFrozenTypeEnum.MON_VALUE){
                	continue;
                }
				int userid = fsubscriptionlog.getFuser().getFid();				
				double frozenRate = fsubscriptionlog.getFsubscription().getFrate();
				int vid = fsubscriptionlog.getFsubscription().getFvirtualcointype().getFid();
				double count = fsubscriptionlog.getFcount();
				double lastqty = fsubscriptionlog.getFlastcount();
				double nowQty = Utils.getDouble(count*frozenRate, 4);
				if(nowQty >= lastqty){
					nowQty = lastqty;
					fsubscriptionlog.setFlastcount(0d);
					fsubscriptionlog.setFstatus(SubStatusEnum.YES);
				}else{
					fsubscriptionlog.setFlastcount(fsubscriptionlog.getFlastcount()-nowQty);
				}
				String ss = "where fuser.fid="+userid+" and fvirtualcointype.fid="+vid;
				Fvirtualwallet fvirtualwallet = this.virtualWalletService.list(0, 0, ss, false).get(0);
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+nowQty);
				fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-nowQty);
				Fintrolinfo info = new Fintrolinfo();
				info.setFcreatetime(Utils.getTimestamp());
				info.setFiscny(false);
				info.setFqty(nowQty);
				info.setFname(fvirtualwallet.getFvirtualcointype().getFname());
				info.setFtype(IntrolInfoTypeEnum.INTROL_RELEASE);
				info.setFtitle("众筹解冻"+fvirtualwallet.getFvirtualcointype().getFname()+"数量："+new BigDecimal(nowQty).setScale(4, BigDecimal.ROUND_HALF_UP));
				info.setFuser(fsubscriptionlog.getFuser());
				try {
					this.subscriptionLogService.updateSendFrozen(fvirtualwallet, fsubscriptionlog,info);
				} catch (Exception e) {
					continue;
				}
				
			}
		}
	}
  
}
