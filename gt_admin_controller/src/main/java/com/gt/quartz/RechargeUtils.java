package com.gt.quartz;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.Utils;

public class RechargeUtils {
	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private UserService userService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	
	
	public void work() {
		synchronized (this) {
			//String[] args = this.map.getString("firstTimeRechargeRate").split("#");
			String[] args = this.frontConstantMapService.getString("firstTimeRechargeRate").split("#");
			int vid = Integer.parseInt(args[0]);
			double qty = Double.valueOf(args[1]);
			int times = Integer.parseInt(args[2]);
			String filter = "where fischarge=0 and ftype=1 and fstatus=3";
			List<Fcapitaloperation> alls = this.capitaloperationService.list(0, 0, filter, false);
			for (Fcapitaloperation fcapitaloperation : alls) {
				try {
					if(fcapitaloperation.isFischarge()) continue;
					fcapitaloperation.setFischarge(true);
					Fuser fuser =this.userService.findById(fcapitaloperation.getFuser().getFid());
					if(fuser.getfIntroUser_id() != null && qty >0){
						Fuser fintroluser = this.userService.findById(fuser.getfIntroUser_id().getFid());
						Fscore fscore = fintroluser.getFscore();
						if(fscore.getFkillQty() >= times){
							this.capitaloperationService.updateObj(fcapitaloperation);
						}else{
							fscore.setFkillQty(fscore.getFkillQty()+1);
							Fvirtualcointype coin = this.virtualCoinService.findById(vid);
							Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fintroluser.getFid(), vid);
							fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+qty);
							Fintrolinfo info = new Fintrolinfo();
							info.setFcreatetime(Utils.getTimestamp());
							info.setFiscny(coin.getFtype() == CoinTypeEnum.FB_CNY_VALUE?true:false);
							info.setFname(coin.getFname());
							info.setFtitle("用户UID:"+fuser.getFid()+"充值，奖励"+coin.getFname()+":"+new BigDecimal(String.valueOf(qty)).setScale(4,BigDecimal.ROUND_DOWN));
							info.setFtype(IntrolInfoTypeEnum.INTROL_RECHARHE);
							info.setFqty(qty);
							info.setFuser(fintroluser);
							
							this.capitaloperationService.updateCapital(fcapitaloperation, fvirtualwallet, fscore,info);
						}
					}else{
						this.capitaloperationService.updateObj(fcapitaloperation);
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}
}