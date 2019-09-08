package com.gt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.front.FentrustService;
import com.gt.service.front.FentrustlogService;
import com.gt.service.front.UtilsService;
import com.gt.util.Utils;
import com.gt.util.redis.RedisCacheUtil;

@Service
public class TradeDealMakingServiceImpl {
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	@Autowired
	private UtilsService utilsService;
	@Autowired
	private FentrustlogService fentrustlogService;
	@Autowired
	private FentrustService fentrustService;
	//手续费率
	private Map<String, Double> rates = new HashMap<String, Double>() ;
	
	public void putRates(String key,double value){
		synchronized (this.rates) {
			this.rates.put(key, value) ;
			redisCacheUtil.setCacheObject("front:rate:"+key, value+"");
		}
	}
	
	public double getRates(int vid,boolean isbuy,int level){
		String key = vid+"_"+(isbuy?"buy":"sell")+"_"+level ;
		synchronized (this.rates) {
			return this.rates.get(key) ;
		}
	}
	
	//撮合
		public void updateDealMaking(Ftrademapping ftrademapping ,Fentrust buy, Fentrust sell,
				Fentrustlog buyLog, Fentrustlog sellLog, int id) {

			
			double buyFee = 0D;
			if (buy.isFisLimit() == false ) {//limit=0
				buyFee = (buyLog.getFamount() / buy.getFamount())
						* buy.getFfees();
			}else{//limit==1
				double ffeeRate = this.getRates(buy.getFtrademapping().getFid(), true,buy.getFuser().getFscore().getFlevel()) ;
				buyFee = buyLog.getFcount()*ffeeRate ;
			}
			
			if (buy.isFisLimit()) {
				buy.setFcount(buy.getFcount() + buyLog.getFcount());
				buy.setFsuccessAmount(buy.getFsuccessAmount()
						+ (buyLog.getFamount()));
				buy.setFfees(buy.getFfees()+buyFee) ;
				buy.setFlastUpdatTime(Utils.getTimestamp());
				if (buy.getFamount() - buy.getFsuccessAmount() < 0.000001D) {
					buy.setFstatus(EntrustStatusEnum.AllDeal);
				} else {
					buy.setFstatus(EntrustStatusEnum.PartDeal);
				}
			} else {
				buy.setFleftCount(buy.getFleftCount() - buyLog.getFcount());
				buy.setFsuccessAmount(buy.getFsuccessAmount()
						+ (buyLog.getFamount()));
				buy.setFlastUpdatTime(Utils.getTimestamp());
				buy.setFleftfees(buy.getFleftfees() - buyFee);
				if (buy.getFleftCount() < 0.000001D) {
					buy.setFstatus(EntrustStatusEnum.AllDeal);
				} else {
					buy.setFstatus(EntrustStatusEnum.PartDeal);
				}
			}

			double sellFee = 0D;
			if (sell.isFisLimit() == false ) {//limit==0
				sellFee = (buyLog.getFcount() / sell.getFcount())
						* sell.getFfees();
			}else{//limit==1
				double sellRate = this.getRates(sell.getFtrademapping().getFid(), false,sell.getFuser().getFscore().getFlevel()) ;
				sellFee = sellRate * sellLog.getFamount() ;
			}

			if (sell.isFisLimit()) {
			sell.setFsuccessAmount(sell.getFsuccessAmount()
					+ buyLog.getFamount());
			sell.setFamount(sell.getFamount() + buyLog.getFamount());
			sell.setFleftCount(sell.getFleftCount() - buyLog.getFcount());
			sell.setFfees(sell.getFfees()+sellFee) ;
			sell.setFlastUpdatTime(Utils.getTimestamp());
			if (sell.getFleftCount() < 0.000001F) {
				sell.setFstatus(EntrustStatusEnum.AllDeal);
			} else {
				sell.setFstatus(EntrustStatusEnum.PartDeal);
			}
			
		} else {
			sell.setFleftCount(sell.getFleftCount() - buyLog.getFcount());
			sell.setFsuccessAmount(sell.getFsuccessAmount()
					+ (sellLog.getFamount()));
			sell.setFleftfees(sell.getFleftfees() - sellFee);
			sell.setFlastUpdatTime(Utils.getTimestamp());
			if (sell.getFleftCount() < 0.000001D) {
				sell.setFstatus(EntrustStatusEnum.AllDeal);
			} else {
				sell.setFstatus(EntrustStatusEnum.PartDeal);
			}
		}

		// 虚拟钱包
		/*Fvirtualwallet fbuyWallet = this.fvirtualWalletService.findFvirtualwallet(buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fbuyVirtualwallet = this.fvirtualwalletDAO.findVirtualWallet( buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		Fvirtualwallet fsellWallet = this.fvirtualWalletService.findFvirtualwallet(sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fsellVirtualwallet = this.fvirtualwalletDAO.findVirtualWallet( sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		*/
			
		Fvirtualwallet fbuyWallet = this.fackFvirtualwallet(buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fbuyVirtualwallet = this.fackFvirtualwallet( buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		Fvirtualwallet fsellWallet = this.fackFvirtualwallet(sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fsellVirtualwallet = this.fackFvirtualwallet( sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		
		fsellWallet = theSame(fsellWallet, fbuyWallet, fbuyVirtualwallet, fsellVirtualwallet) ;
		fbuyVirtualwallet = theSame(fbuyVirtualwallet, fbuyWallet, fsellWallet, fsellVirtualwallet) ;
		fsellVirtualwallet = theSame(fsellVirtualwallet, fbuyWallet, fbuyVirtualwallet, fsellWallet) ;

		//买法币
		fbuyWallet.setFfrozen(fbuyWallet.getFfrozen()
				- buyLog.getFamount());
		//卖法币
		fsellWallet.setFtotal(fsellWallet.getFtotal()
				+ buyLog.getFamount() - sellFee);
		//买虚拟
		fbuyVirtualwallet.setFtotal(fbuyVirtualwallet.getFtotal()
				+ buyLog.getFcount() - buyFee);
		//卖虚拟
		fsellVirtualwallet.setFfrozen(fsellVirtualwallet.getFfrozen()
				- buyLog.getFcount());
		
		

		if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
			// 因为有人低价卖出，冻结剩余部分返回钱包
			double left_amount = (buy.getFamount() - buy
					.getFsuccessAmount());
			fbuyWallet.setFfrozen(fbuyWallet.getFfrozen()
					- left_amount);
			fbuyWallet
					.setFtotal(fbuyWallet.getFtotal() + left_amount);
		}
		
		/*this.fvirtualwalletDAO.attachDirty(fsellVirtualwallet);
		this.fvirtualwalletDAO.attachDirty(fbuyVirtualwallet);
		this.fvirtualwalletDAO.attachDirty(fbuyWallet);
		this.fvirtualwalletDAO.attachDirty(fsellWallet);*/
		
		
		fentrustService.saveTradeDealMaking(buyLog,sellLog,buy,sell,fsellVirtualwallet,fbuyVirtualwallet,fbuyWallet,fsellWallet);
		
		//fentrustlogDAO.save(buyLog);
		//fentrustlogService.save(buyLog);
		//fentrustlogDAO.save(sellLog);
		//fentrustlogService.save(sellLog);
		//fentrustDAO.attachDirty(buy);
		//fentrustService.attachDirty(buy);
		//fentrustDAO.attachDirty(sell);
		//fentrustService.attachDirty(sell);
		
		//this.updateFackFvirtualwallet(fsellVirtualwallet,fbuyVirtualwallet,fbuyWallet,fsellWallet);

	}
		
	private Fvirtualwallet fackFvirtualwallet(int fuserid,int vid){
		Fvirtualwallet fvirtualwallet = new Fvirtualwallet() ;
		fvirtualwallet.setFack_uid(fuserid); 
		fvirtualwallet.setFack_vid(vid);
			
		fvirtualwallet.setFack_id(fuserid+"_"+vid);
		return fvirtualwallet ;
	}
	
	private Fvirtualwallet theSame(Fvirtualwallet v1,Fvirtualwallet v2,Fvirtualwallet v3,Fvirtualwallet v4){
		if(v1.getFack_id().equals(v2.getFack_id())){
			return v2 ;
		}
		if(v1.getFack_id().equals(v3.getFack_id())){
			return v3 ;
		}
		if(v1.getFack_id().equals(v4.getFack_id())){
			return v4 ;
		}
		return v1 ;
	}
	
	private void updateFackFvirtualwallet(Fvirtualwallet w1,Fvirtualwallet w2,Fvirtualwallet w3,Fvirtualwallet w4){
		String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=?" ;
		//int count = this.utilsDAO.executeHQL(hql, w1.getFtotal(),w1.getFfrozen(),w1.getFack_uid(),w1.getFack_vid()) ;
		int count = this.utilsService.executeHQL(hql, w1.getFtotal(),w1.getFfrozen(),w1.getFack_uid(),w1.getFack_vid()) ;
		
		if(w2.getFack_id().equals(w1.getFack_id()) == false ){
			//count = this.utilsDAO.executeHQL(hql, w2.getFtotal(),w2.getFfrozen(),w2.getFack_uid(),w2.getFack_vid()) ;
			count = this.utilsService.executeHQL(hql, w2.getFtotal(),w2.getFfrozen(),w2.getFack_uid(),w2.getFack_vid()) ;
		}

		if(w3.getFack_id().equals(w1.getFack_id()) == false &&w3.getFack_id().equals(w2.getFack_id()) == false ){
			//count = this.utilsDAO.executeHQL(hql, w3.getFtotal(),w3.getFfrozen(),w3.getFack_uid(),w3.getFack_vid()) ;
			count = this.utilsService.executeHQL(hql, w3.getFtotal(),w3.getFfrozen(),w3.getFack_uid(),w3.getFack_vid()) ;
		}

		if(w4.getFack_id().equals(w1.getFack_id()) == false &&w4.getFack_id().equals(w2.getFack_id()) == false &&w4.getFack_id().equals(w3.getFack_id()) == false ){
			//count = this.utilsDAO.executeHQL(hql, w4.getFtotal(),w4.getFfrozen(),w4.getFack_uid(),w4.getFack_vid()) ;
			count = this.utilsService.executeHQL(hql, w4.getFtotal(),w4.getFfrozen(),w4.getFack_uid(),w4.getFack_vid()) ;
		}
		
	}
}
