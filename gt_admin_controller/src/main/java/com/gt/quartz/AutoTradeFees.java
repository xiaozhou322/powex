package com.gt.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.entity.Fentrust;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.Utils;

public class AutoTradeFees {

	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	
	private double rate1=0d;
	private double rate2=0d;
	private double rate3=0d;
	
	public void work() {
		synchronized (this) {
		try{
			//String[] introlTradeRate = this.map.getString("introlTradeRate").trim().split("#");
			String[] introlTradeRate = this.frontConstantMapService.getString("introlTradeRate").trim().split("#");
			rate1 = Double.valueOf(introlTradeRate[0]);
			rate2 = Double.valueOf(introlTradeRate[1]);
			rate3 = Double.valueOf(introlTradeRate[2]);
			String filter = " where (fstatus="+EntrustStatusEnum.AllDeal+" or fstatus="+EntrustStatusEnum.Cancel+") and fhasSubscription=0  " ;
			List<Fentrust> fentrusts = this.frontTradeService.findFentrustsByParam(0, 0, filter, false) ;
			if (fentrusts.size()>0) {
				for (Fentrust fentrust : fentrusts) {
					try {
						if(fentrust.isFhasSubscription()) continue;
						if(fentrust.getFstatus()==EntrustStatusEnum.AllDeal||fentrust.getFstatus()==EntrustStatusEnum.Cancel){
							double fee = Utils.getDouble(fentrust.getFfees()-fentrust.getFleftfees(), 6) ;
							fentrust.setFhasSubscription(true) ;
							Fuser fuser = this.frontUserService.findById(fentrust.getFuser().getFid());
							int tradeMappingID = fentrust.getFtrademapping().getFid();
							Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(tradeMappingID) ;
							Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
							Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
							String coinName = coin2.getFname()+"("+coin1.getfShortName()+")" ;
							
							if(fee>0d){
								if(fentrust.getFentrustType() == EntrustTypeEnum.SELL){
									List<Fvirtualwallet> fvirtualwallets = new ArrayList<Fvirtualwallet>();
									List<Fintrolinfo> fintrolinfos = new ArrayList<Fintrolinfo>();
									//普通用户
									getAmtList(fvirtualwallets, fintrolinfos, fuser, fee, 1, fuser.getFid(), coinName, tradeMappingID);
									this.frontTradeService.updateCoinFentrust(fentrust,fvirtualwallets, fintrolinfos);
								}else{
									List<Fvirtualwallet> fvirtualwallets = new ArrayList<Fvirtualwallet>();
									List<Fintrolinfo> fintrolinfos = new ArrayList<Fintrolinfo>();
									//普通用户
									getCoinList(fvirtualwallets, fintrolinfos, fuser, fee, 1, fuser.getFid(), coinName, coin2.getFid());
									this.frontTradeService.updateCoinFentrust(fentrust,fvirtualwallets, fintrolinfos);
								}
							}else{
								this.frontTradeService.updateFentrust(fentrust);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	}
	
	private void getAmtList(List<Fvirtualwallet> fvirtualwallets,List<Fintrolinfo> fintrolinfos,
			Fuser fuser,double amt,int i,int userId,String name,int tradeMappingID) {
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(tradeMappingID) ;
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		if(i <= 3){
			if(fuser.getfIntroUser_id() != null){
				double total = 0d;
				if(i == 1){
					total = Utils.getDouble(amt*rate1, 6);
				}
				else if(i == 2){
					total = Utils.getDouble(amt*rate2, 6);
				}
				else if(i == 3){
					total = Utils.getDouble(amt*rate3, 6);
				}
				int introlUserId = fuser.getfIntroUser_id().getFid();
				Fuser intro = this.frontUserService.findById(introlUserId) ;
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(intro.getFid(),coin1.getFid());
				if(total > 0d){
					
					boolean isTake = false;
					for (Fvirtualwallet v : fvirtualwallets) {
						if(v.getFid().intValue() == fvirtualwallet.getFid().intValue()){
							v.setFtotal(v.getFtotal()+total);
							isTake = true;
							break;
						}
					}
					if(!isTake){
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+total);
						fvirtualwallets.add(fvirtualwallet);
					}
					
					Fintrolinfo fintrolinfo = new Fintrolinfo() ;
					fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
					fintrolinfo.setFqty(total) ;
					fintrolinfo.setFname(coin1.getFname());
					fintrolinfo.setFtitle("用户UID："+userId+"卖出"+name+",奖励:"+coin1.getfSymbol()+new BigDecimal(total).setScale(6, BigDecimal.ROUND_HALF_UP));
					fintrolinfo.setFuser(intro) ;
					fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_TRADE_FEE);
					fintrolinfo.setFiscny(coin1.getFtype() == CoinTypeEnum.FB_CNY_VALUE?true:false);
					fintrolinfos.add(fintrolinfo);
				}
				i = i + 1;
				getAmtList(fvirtualwallets, fintrolinfos, intro, amt,i,userId,name,tradeMappingID);
			}
		}
	}

	private void getCoinList(List<Fvirtualwallet> fvirtualwallets,List<Fintrolinfo> fintrolinfos,
			Fuser fuser,double amt,int i,int userId,String name,int vid) {
		Fvirtualcointype coin = this.virtualCoinService.findById(vid);
		if(i <= 3){
			if(fuser.getfIntroUser_id() != null){
				double total = 0d;
				if(i == 1){
					total = Utils.getDouble(amt*rate1, 6);
				}
				else if(i == 2){
					total = Utils.getDouble(amt*rate2, 6);
				}
				else if(i == 3){
					total = Utils.getDouble(amt*rate3, 6);
				}
				int introlUserId = fuser.getfIntroUser_id().getFid();
				Fuser intro = this.frontUserService.findById(introlUserId) ;
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(introlUserId, vid);
				if(total > 0d){
					
					boolean isTake = false;
					for (Fvirtualwallet v : fvirtualwallets) {
						if(v.getFid().intValue() == fvirtualwallet.getFid().intValue()){
							v.setFtotal(v.getFtotal()+total);
							isTake = true;
							break;
						}
					}
					if(!isTake){
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+total);
						fvirtualwallets.add(fvirtualwallet);
					}
					
					Fintrolinfo fintrolinfo = new Fintrolinfo() ;
					fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
					fintrolinfo.setFqty(total) ;
					fintrolinfo.setFtitle("用户UID："+userId+"买入"+name+",奖励:"+coin.getfSymbol()+new BigDecimal(total).setScale(6, BigDecimal.ROUND_HALF_UP));
					fintrolinfo.setFuser(intro) ;
					fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_TRADE_FEE);
					fintrolinfo.setFname(coin.getFname());
					fintrolinfo.setFiscny(false);
					fintrolinfos.add(fintrolinfo);
				}
				i = i + 1;
				getCoinList(fvirtualwallets, fintrolinfos, intro, amt,i,userId,name,vid);
			}
		}
	}
}
