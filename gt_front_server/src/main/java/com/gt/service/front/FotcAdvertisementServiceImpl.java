package com.gt.service.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FotcAdvertisementDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.FotcAdvertisement;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.PageBean;
import com.gt.util.Utils;

import net.sf.json.JSONObject;


/**
 * 承兑商广告service
 * @author zhouyong
 *
 */
@Service("fotcAdvertisementService")
public class FotcAdvertisementServiceImpl implements FotcAdvertisementService{
	@Autowired
	private FotcAdvertisementDAO advertisementDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	@Autowired
	private FotcOrderWalletRecordService fotcOrderWalletRecordServiceImpl;
	
	public void saveSell(FotcAdvertisement obj,Fvirtualwallet fvirtualwallet1) {
		Map<String,Object> map=new HashMap<String,Object>();
		//获取承兑商钱包信息
		//承兑商确认收到款之后，在承兑商的钱包表冻结数量中减去该订单的购买数量

		fvirtualwallet1.setFtotal(fvirtualwallet1.getFtotal()- obj.getTotal_count());
		fvirtualwallet1.setFfrozen(fvirtualwallet1.getFfrozen() + obj.getTotal_count());
		fvirtualwallet1.setFlastUpdateTime(Utils.getTimestamp());
		virtualwalletDAO.attachDirty(fvirtualwallet1);	
		fotcOrderWalletRecordServiceImpl.saveObj(1, obj.getRepertory_count(),obj.getFvirtualcointype(),
											obj.getUser(), 0);
		advertisementDAO.save(obj);
			
			
	}
	public void saveBuy(FotcAdvertisement obj) {
		   advertisementDAO.save(obj);						
	}
	public PageBean<FotcAdvertisement> findAdvertisementByPage(int fid, int pageSize, int pageIndex){
		PageBean<FotcAdvertisement>  pageBean=new PageBean<FotcAdvertisement>(pageSize,pageIndex,advertisementDAO.getAdvertisementCount(fid).intValue());
		int offset=(pageBean.getPageIndex()-1)*pageBean.getPageSize();
		pageBean.setList(advertisementDAO.findAllAdvertisement(fid, pageSize, offset));
		return pageBean;
	}
	
	public List<FotcAdvertisement> queryOtcAdvertisementByPage(FotcAdvertisement obj, int firstResult,int maxResult) {
		List<FotcAdvertisement> adList = advertisementDAO.queryOtcAdvertisementByPage(obj, firstResult, maxResult);
		return adList ;
	}

	
	public int queryOtcAdvertisementCount(FotcAdvertisement obj){
		return advertisementDAO.queryOtcAdvertisementCount(obj) ;
	}

	
	public FotcAdvertisement queryById(Integer adId) {
		return advertisementDAO.queryById(adId);
	}

	public List<FotcAdvertisement> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		return this.advertisementDAO.list(firstResult, maxResults, filter,isFY);
	}

	public JSONObject updateAdvertisementDown(int id) {
		JSONObject jsonObject = new JSONObject() ;
		FotcAdvertisement fotcAdvertisement=this.advertisementDAO.queryById(id);
		if(fotcAdvertisement!=null&&fotcAdvertisement.getAd_type()==1&&fotcAdvertisement.getStatus()==0){
			List<Fvirtualwallet> fvirtualwallets1 = virtualwalletDAO.findByTwoProperty("fuser.fid", fotcAdvertisement.getUser().getFid(), "fvirtualcointype.fid",fotcAdvertisement.getFvirtualcointype().getFid());
			Fvirtualwallet fvirtualwallet1 = fvirtualwallets1.get(0);
			if(null != fvirtualwallets1 && fvirtualwallets1.size() > 0){
				if(fvirtualwallet1.getFfrozen()>=fotcAdvertisement.getRepertory_count()){
					//承兑商确认收到款之后，在承兑商的钱包表冻结数量中减去该订单的购买数量
					fvirtualwallet1.setFtotal(fvirtualwallet1.getFtotal()+ fotcAdvertisement.getRepertory_count());
					fvirtualwallet1.setFfrozen(fvirtualwallet1.getFfrozen() - fotcAdvertisement.getRepertory_count());
					fvirtualwallet1.setFlastUpdateTime(Utils.getTimestamp());
					virtualwalletDAO.attachDirty(fvirtualwallet1);					
					fotcAdvertisement.setStatus(1);
					advertisementDAO.save(fotcAdvertisement);
					fotcOrderWalletRecordServiceImpl.saveObj(2, fotcAdvertisement.getRepertory_count(),
							fotcAdvertisement.getFvirtualcointype(), fotcAdvertisement.getUser(), 0);
					
					jsonObject.accumulate("success", true);
					jsonObject.accumulate("massage", "广告下架成功");
					return jsonObject;
				}else{
					jsonObject.accumulate("success", false);
					jsonObject.accumulate("massage", "钱包冻结数量不足");
					return jsonObject;
				}

			}else{
				jsonObject.accumulate("success", false);
				jsonObject.accumulate("massage", "系统异常，操作失败");
				return jsonObject;
			}
		}else if(fotcAdvertisement!=null&&fotcAdvertisement.getAd_type()==2&&fotcAdvertisement.getStatus()==0){
			fotcAdvertisement.setStatus(1);
			advertisementDAO.save(fotcAdvertisement);
			
			jsonObject.accumulate("success", true);
			jsonObject.accumulate("massage", "广告下架成功");
			return jsonObject;
		}

		return jsonObject;
	}
	
	public void updateObj(FotcAdvertisement ad) {
		advertisementDAO.update(ad);
	}
	
	
	public JSONObject updateAdvertisementOnline(FotcAdvertisement obj) {
		
		JSONObject jsonObject = new JSONObject() ;
		if(obj!=null && obj.getAd_type()==1 && obj.getStatus()==1){
			List<Fvirtualwallet> fvirtualwallets1 = virtualwalletDAO.findByTwoProperty("fuser.fid", obj.getUser().getFid(), "fvirtualcointype.fid",obj.getFvirtualcointype().getFid());
			Fvirtualwallet fvirtualwallet1 = fvirtualwallets1.get(0);
			if(null != fvirtualwallets1 && fvirtualwallets1.size() > 0){
				
					//承兑商确认收到款之后，在承兑商的钱包表冻结数量中减去该订单的购买数量
					fvirtualwallet1.setFtotal(fvirtualwallet1.getFtotal()- obj.getRepertory_count());
					fvirtualwallet1.setFfrozen(fvirtualwallet1.getFfrozen() + obj.getRepertory_count());
					fvirtualwallet1.setFlastUpdateTime(Utils.getTimestamp());
					virtualwalletDAO.attachDirty(fvirtualwallet1);
					
					obj.setStatus(0);
					advertisementDAO.update(obj);
					fotcOrderWalletRecordServiceImpl.saveObj(1, obj.getRepertory_count(),obj.getFvirtualcointype(), obj.getUser(), 0);

					jsonObject.accumulate("success", true);
					jsonObject.accumulate("massage", "广告上架成功");
					return jsonObject;
			}else{
				jsonObject.accumulate("success", false);
				jsonObject.accumulate("massage", "系统异常，操作失败");
				return jsonObject;
			}
		}else if(obj!=null&&obj.getAd_type()==2&&obj.getStatus()==1){
			obj.setStatus(0);
			advertisementDAO.update(obj);
			
			jsonObject.accumulate("success", true);
			jsonObject.accumulate("massage", "广告上架成功");
			return jsonObject;
		}

		return jsonObject;
	}
}