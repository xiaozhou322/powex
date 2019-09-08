package com.gt.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.SubStatusEnum;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fsubscription;
import com.gt.entity.Fsubscriptionlog;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.SubscriptionLogService;
import com.gt.service.admin.SubscriptionService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.Utils;

public class SubIntrolUtils {
	@Autowired
	private SubscriptionLogService subscriptionLogService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private SubscriptionService subscriptionService;
	
	
	private double rate1 = 0d;
	private double rate2 = 0d;
	private double rate3 = 0d;

	public void work() {
		try {
			//String[] subRates = this.map.getString("subRate").split("#");
			String[] subRates = this.frontConstantMapService.getString("subRate").split("#");
			rate1 = Double.valueOf(subRates[0]);
			rate2 = Double.valueOf(subRates[1]);
			rate3 = Double.valueOf(subRates[2]);
			String filter = "where (fischarge='' or fischarge is null) and fstatus="+SubStatusEnum.YES;
			List<Fsubscriptionlog> fsubscriptionlogs = this.subscriptionLogService.list(0, 0, filter, false);
			for (Fsubscriptionlog fsubscriptionlog : fsubscriptionlogs) {
				if(fsubscriptionlog.getFischarge() != null && fsubscriptionlog.getFischarge().length()>0) continue;
				Fsubscription sub = this.subscriptionService.findById(fsubscriptionlog.getFsubscription().getFid());
				fsubscriptionlog.setFischarge("true");
				double amt = fsubscriptionlog.getFtotalCost();
				int userid = fsubscriptionlog.getFuser().getFid();
				Fuser fuser = this.frontUserService.findById(userid);
				List<Fvirtualwallet> fwallets = new ArrayList<Fvirtualwallet>();
				List<Fintrolinfo> fintrolinfos = new ArrayList<Fintrolinfo>();
				if(amt >= 0.0001){
					//服务中心奖
					getAmtList(fwallets, fintrolinfos, fuser, amt, 1, userid,sub);
				}
				
				try {
					this.subscriptionLogService.updateChargeLog1(fwallets, fintrolinfos, fsubscriptionlog);
				} catch (Exception e) {
					continue;
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getAmtList(List<Fvirtualwallet> fwallets,List<Fintrolinfo> fintrolinfos,
			Fuser fuser,double amt,int i,int userId,Fsubscription sub) {
		if(i <= 3){
			if(fuser.getfIntroUser_id() != null){
				double total = 0d;
				if(i == 1){
					total = Utils.getDouble(amt*rate1, 4);
				}else if(i == 2){
					total = Utils.getDouble(amt*rate2, 4);
				}else if(i == 3){
					total = Utils.getDouble(amt*rate3, 4);
				}
				int introlUserId = fuser.getfIntroUser_id().getFid();
				Fuser intro = this.frontUserService.findById(introlUserId) ;
				if(total >= 0.0001){
					Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(introlUserId, sub.getFvirtualcointypeCost().getFid());
					boolean isTake = false;
					for (Fvirtualwallet v : fwallets) {
						if(v.getFid().intValue() == fvirtualwallet.getFid().intValue()){
							v.setFtotal(v.getFtotal()+total);
							isTake = true;
							break;
						}
					}
					if(!isTake){
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+total);
						fwallets.add(fvirtualwallet);
					}
										
					Fintrolinfo fintrolinfo = new Fintrolinfo() ;
					fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
					fintrolinfo.setFqty(total) ;
					fintrolinfo.setFtitle("用户UID:"+userId+"在参与众筹，奖励："+sub.getFvirtualcointypeCost().getFname()+" "+new BigDecimal(total).setScale(4,BigDecimal.ROUND_HALF_UP)) ;
					fintrolinfo.setFuser(intro) ;
					fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_SUB);
					fintrolinfo.setFname(sub.getFvirtualcointypeCost().getFname());
					fintrolinfo.setFiscny(sub.getFvirtualcointypeCost().getFtype() == CoinTypeEnum.FB_CNY_VALUE?true:false);
					fintrolinfos.add(fintrolinfo);
				}
				i = i + 1;
				getAmtList(fwallets, fintrolinfos, intro, amt,i,userId,sub);
			}
		}
	}
	
}