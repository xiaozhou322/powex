package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.CoinTypeEnum;
import com.gt.dao.FassetDAO;
import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fasset;
import com.gt.entity.Fvirtualwallet;

import net.sf.json.JSONObject;

@Service("assetService")
public class AssetServiceImpl implements AssetService {

	@Autowired
	private FassetDAO fassetDAO ;
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FrontUserService frontUserServiceImpl ;
	@Autowired
	private FrontConstantMapService frontConstantMapServiceImpl;
	
	//记录所有用户的资产明细
	public boolean updateAllAssets(){
		String sql = " insert into fasset( fuser, ftotal, fcreatetime, flastupdatetime, version, status ) select fid,0,now(),now(),1,0 from fuser " ;
		int count = this.fassetDAO.executeSQL(sql) ;
		return count>0 ;
	}
	

	//更新明细
	public boolean updateAllAssetsDetail() {
		List<Fasset> fassets = this.fassetDAO.findByParam(0, 300, " where status=0 ", true, Fasset.class) ;
		Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapServiceImpl.get("tradeMappings");
		for (Fasset fasset : fassets) {
			if(fasset.getFid()<=0||fasset.getStatus()==true) continue ;
			
			JSONObject jsonObject = new JSONObject() ;
			Double total = 0D ;//预估总资产
			List<Fvirtualwallet> fvirtualwallets = this.fvirtualwalletDAO.findByParam(0, 0, " where fuser.fid="+fasset.getFuser().getFid(), false, Fvirtualwallet.class) ;

			for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
				if(fvirtualwallet.getFvirtualcointype().getFtype() ==CoinTypeEnum.FB_CNY_VALUE){
					JSONObject cny = new JSONObject() ;
					cny.accumulate("total", fvirtualwallet.getFtotal()) ;
					cny.accumulate("frozen", fvirtualwallet.getFfrozen()) ;
					jsonObject.accumulate("0", cny) ;
//					total+=fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen();
				}else if(fvirtualwallet.getFvirtualcointype().getFtype() ==CoinTypeEnum.FB_USDT_VALUE){
					JSONObject usdt = new JSONObject() ;
					usdt.accumulate("total", fvirtualwallet.getFtotal()) ;
					usdt.accumulate("frozen", fvirtualwallet.getFfrozen()) ;
					jsonObject.accumulate("-1", usdt) ;
//					total+=fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen();
				}else{
					JSONObject item = new JSONObject() ;
					item.accumulate("total", fvirtualwallet.getFtotal()) ;
					item.accumulate("frozen", fvirtualwallet.getFfrozen()) ;
					jsonObject.accumulate(String.valueOf(fvirtualwallet.getFvirtualcointype().getFid()), item) ;
//					total+=(fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen())*this.realTimeData.getLatestDealPrize(tradeMappings.get(fvirtualwallet.getFvirtualcointype().getFid())) ;
				}
			}
			
			fasset.setDetail(jsonObject.toString()) ;
			fasset.setFtotal(0d) ;
			fasset.setStatus(true) ;
			this.fassetDAO.attachDirty(fasset) ;
		}
		return fassets.size()>0 ;
	}
}
