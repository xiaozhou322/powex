package com.gt.service.front;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.FactivityStatusEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.LotteryAwardsDAO;
import com.gt.dao.FactivityDAO;
import com.gt.dao.LotteryLogDAO;
import com.gt.dao.LotteryPeriodsDAO;
import com.gt.dao.LotteryRecordDAO;
import com.gt.dao.FentrustDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.ZdisFactivityWalletDAO;
import com.gt.dao.ZdisFactivityWalletRecordDAO;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.LotteryAwardsModel;
import com.gt.entity.LotteryLogModel;
import com.gt.entity.FactivityModel;
import com.gt.entity.LotteryPeriodsModel;
import com.gt.entity.LotteryRecordModel;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.ZdisFactivityWallet;
import com.gt.entity.ZdisFactivityWalletRecord;
import com.gt.util.Utils;

@Service("frontActivityService")
public class FrontActivityServiceImpl implements FrontActivityService {
	
	@Autowired
	private FactivityDAO factivityDAO;
	@Autowired
	private LotteryRecordDAO lotteryRecordDAO;
	@Autowired
	private LotteryPeriodsDAO lotteryPeriodsDAO;
	@Autowired
	private LotteryAwardsDAO lotteryAwardsDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private LotteryLogDAO lotteryLogDAO;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;
	@Autowired
	private ZdisFactivityWalletDAO zdisFactivityWalletDAO;
	@Autowired
	private ZdisFactivityWalletRecordDAO zdisFactivityWalletRecordDAO;
	@Autowired
	private FuserDAO fuserDAO ;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	
	public void save(FactivityModel factivityModel) {
		// TODO Auto-generated method stub
		factivityDAO.attachDirty(factivityModel);
	}

	public void update(FactivityModel factivityModel) {
		// TODO Auto-generated method stub
		factivityDAO.attachDirty(factivityModel);
	}

	public FactivityModel findById(int id) {
		return factivityDAO.findById(id);
		// TODO Auto-generated method stub
		
	}

	public List<FactivityModel> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<FactivityModel> all = this.factivityDAO.list(firstResult, maxResults, filter,isFY);

		return all;
	}
//周期性抽奖活动审核
	public JSONObject verifyActivity(int id) {		
		JSONObject jsonObject=new JSONObject();
		FactivityModel factivityModel=this.factivityDAO.findById(id);
		if(factivityModel.getStatus()!=0 || factivityModel.getType()!=2 ||factivityModel.getWay()!=1){
			jsonObject.accumulate("message", "活动状态异常");
			jsonObject.accumulate("statusCode", "300");
			return jsonObject;
		}
		List<LotteryAwardsModel> list=lotteryAwardsDAO.findByProperty("factivityModel.id", id);
		if(list==null|| list.size()==0){
			jsonObject.accumulate("message", "该活动未添加奖项");
			jsonObject.accumulate("statusCode", "300");
			return jsonObject;
		}
		Double total=0.0;
		for(LotteryAwardsModel lotteryAwardsModel:list){
			total=total+(lotteryAwardsModel.getFee_amount()*lotteryAwardsModel.getTotal());
		}
		total=total*factivityModel.getTotal_round();
		Fvirtualwallet fvirtualwallet=fvirtualwalletDAO.findVirtualWallet(factivityModel.getCreate_user().getFid(), list.get(0).getFvirtualcointype().getFid());
		if(fvirtualwallet==null || fvirtualwallet.getFtotal()<total){
			jsonObject.accumulate("message", "余额不足");
			jsonObject.accumulate("statusCode", "300");
			return jsonObject;
		}
		fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-total);
		fvirtualwalletDAO.attachDirty(fvirtualwallet);	
		factivityModel.setStatus(FactivityStatusEnum.Not_Open);
		factivityDAO.attachDirty(factivityModel);
		
		
		//活动钱包表
		ZdisFactivityWallet zdisFactivityWallet=new ZdisFactivityWallet();
		zdisFactivityWallet.setFactivityModel(factivityModel);
		zdisFactivityWallet.setFvirtualcointype(list.get(0).getFvirtualcointype());
		zdisFactivityWallet.setUpdateTime(Utils.getTimestamp());
		zdisFactivityWallet.setFtotal(total);
		zdisFactivityWallet.setFfrozen(0);
		zdisFactivityWalletDAO.attachDirty(zdisFactivityWallet);
		//添加钱包变动信息
		ZdisFactivityWalletRecord zdisFactivityWalletRecord=new ZdisFactivityWalletRecord();
		zdisFactivityWalletRecord.setCreatetime(Utils.getTimestamp());
		zdisFactivityWalletRecord.setFactivityModel(factivityModel);
		zdisFactivityWalletRecord.setFamount(total);
		zdisFactivityWalletRecord.setFvirtualcointype(list.get(0).getFvirtualcointype());
		zdisFactivityWalletRecord.setType(4);
		zdisFactivityWalletRecord.setUser(factivityModel.getCreate_user());
		zdisFactivityWalletRecordDAO.attachDirty(zdisFactivityWalletRecord);
		
		jsonObject.accumulate("message", "审核成功");
		jsonObject.accumulate("statusCode", "200");
		return jsonObject;
	}
	
	
	public List<FactivityModel> getlist(int firstResult,
			int maxResults, String filter, boolean isFY) {
		// TODO Auto-generated method stub
		return factivityDAO.list(firstResult, maxResults, filter, isFY);
	}

	@SuppressWarnings("null")
	public void luckyDrawTask(int id) {
		//如果活动为第一期
		FactivityModel factivityModel1=factivityDAO.findById(id);
		LotteryPeriodsModel factivityPeriodsModel= new LotteryPeriodsModel();
		
		if(factivityModel1.getThis_round()==0){
			factivityPeriodsModel.setFactivityModel(factivityModel1);
			factivityPeriodsModel.setStart_time(Utils.getTimestamp());
			factivityPeriodsModel.setEnd_time(new Timestamp(Utils.getTimestamp().getTime()+1*24*60*60*1000));
			factivityPeriodsModel.setStatus(FactivityStatusEnum.open);
			factivityPeriodsModel.setPeriods_num(factivityModel1.getThis_round()+1);
			//查询活动对应的奖项
			List<LotteryAwardsModel> list= lotteryAwardsDAO.findByProperty("factivityModel.id", factivityModel1.getId());
			if(list!=null &&list.size()>0){
				lotteryPeriodsDAO.attachDirty(factivityPeriodsModel);
				for(LotteryAwardsModel factivityAwardsModel:list){
					LotteryRecordModel factivityRecordModel=new LotteryRecordModel();
					factivityRecordModel.setFactivityAwardsModel(factivityAwardsModel);
					factivityRecordModel.setFactivityPeriodsModel(factivityPeriodsModel);
					factivityRecordModel.setSurplus_num(factivityAwardsModel.getTotal());
					factivityRecordModel.setUpdate_time(Utils.getTimestamp());
					lotteryRecordDAO.attachDirty(factivityRecordModel);
				}
			}
			//首次进行开启活动
			factivityModel1.setStatus(FactivityStatusEnum.open);
			
			factivityModel1.setUpdate_time(Utils.getTimestamp());
			factivityModel1.setThis_round(factivityModel1.getThis_round()+1);
			factivityDAO.attachDirty(factivityModel1);
		}else if(factivityModel1.getThis_round()>0&&factivityModel1.getThis_round()<factivityModel1.getTotal_round()){   
			factivityPeriodsModel.setFactivityModel(factivityModel1);
			factivityPeriodsModel.setStart_time(Utils.getTimestamp());
			factivityPeriodsModel.setEnd_time(new Timestamp(Utils.getTimestamp().getTime()+1*24*60*60*1000));
			factivityPeriodsModel.setStatus(FactivityStatusEnum.open);
			factivityPeriodsModel.setPeriods_num(factivityModel1.getThis_round()+1);
			
			List<LotteryAwardsModel> list= lotteryAwardsDAO.findByProperty("factivityModel.id", factivityModel1.getId());
			if(list!=null &&list.size()>0){
				lotteryPeriodsDAO.attachDirty(factivityPeriodsModel);
				for(LotteryAwardsModel factivityAwardsModel:list){
					int Surplus_num=0;
					//通过活动id和当前期数，查询期数表信息
					List<LotteryPeriodsModel> Periodslist= lotteryPeriodsDAO.findByTwoProperty("factivityModel.id", factivityModel1.getId(), "periods_num", factivityModel1.getThis_round());
						if(Periodslist!=null&&Periodslist.size()>0)	{
							LotteryPeriodsModel factivityPeriodsModel1=Periodslist.get(0);
							//把当前期的活动设为结束，
							factivityPeriodsModel1.setStatus(FactivityStatusEnum.end);
							//存入下一期活动数据
							lotteryPeriodsDAO.attachDirty(factivityPeriodsModel);
							//修改当前期活动
							lotteryPeriodsDAO.attachDirty(factivityPeriodsModel1);
							//通过期数ID和奖项ID查询本期活动本奖项记录信息
							List<LotteryRecordModel> Recordlist=lotteryRecordDAO.findByTwoProperty("factivityPeriodsModel.id", Periodslist.get(0).getId(), "factivityAwardsModel.id", factivityAwardsModel.getId());
						   if(Recordlist!=null&&Recordlist.size()>0){
							   LotteryRecordModel factivityRecordModel=new LotteryRecordModel();
								factivityRecordModel.setFactivityAwardsModel(factivityAwardsModel);
								factivityRecordModel.setFactivityPeriodsModel(factivityPeriodsModel);
								//去本次该奖项的剩余注数，加入到下期该奖项的剩余注数中
								factivityRecordModel.setSurplus_num(factivityAwardsModel.getTotal()+Recordlist.get(0).getSurplus_num());
								factivityRecordModel.setUpdate_time(Utils.getTimestamp());
								lotteryRecordDAO.attachDirty(factivityRecordModel);
						   }
						
						}								
					
				}
			}
			//修改活动期数
			factivityModel1.setUpdate_time(Utils.getTimestamp());
			factivityModel1.setThis_round(factivityModel1.getThis_round()+1);
			factivityDAO.attachDirty(factivityModel1);
		}else{
			//结束活动
			factivityModel1.setStatus(FactivityStatusEnum.end);
			factivityModel1.setUpdate_time(Utils.getTimestamp());
			factivityDAO.attachDirty(factivityModel1);		

			List<LotteryPeriodsModel> Periodslist= lotteryPeriodsDAO.findByTwoProperty("factivityModel.id", factivityModel1.getId(), "periods_num", factivityModel1.getThis_round());
			if(Periodslist!=null&&Periodslist.size()>0)	{
				LotteryPeriodsModel factivityPeriodsModel1=Periodslist.get(0);
				factivityPeriodsModel1.setStatus(FactivityStatusEnum.end);
				//修改当前期活动未结束状态
				lotteryPeriodsDAO.attachDirty(factivityPeriodsModel1);
			}
		}
	}
//抽奖

	
	public synchronized String saveLottery(int userId, int activityId) {
		FactivityModel factivityModel1=factivityDAO.findById(activityId);
		Fuser user=fuserDAO.findById(userId);
		// TODO Auto-generated method stub
		Object obj=frontConstantMapService.get("draw_probability");
		JSONObject jsonobject=new JSONObject();
		int draw_probability=0;
		if(obj!=null&&!obj.equals("")){
			draw_probability=Integer.parseInt(frontConstantMapService.getString("draw_probability"));
		}
	    if(draw_probability==0){
		    jsonobject.accumulate("msg", "系统配置未找到中奖概率，请联系管理员");
		    jsonobject.accumulate("code", -1001);
		    return jsonobject.toString();
		
	    }
		Fvirtualwallet fvirtualwallet=fvirtualwalletDAO.findVirtualWallet(user.getFid(), factivityModel1.getFvirtualcointype().getFid());
		if(fvirtualwallet!=null&&fvirtualwallet.getFtotal()>=factivityModel1.getCoin_amount()){
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-factivityModel1.getCoin_amount());
			//扣币
			fvirtualwalletDAO.attachDirty(fvirtualwallet);
			  List<Integer> list1 = new ArrayList<Integer>();
			  list1.add(draw_probability);//中奖概率
			  list1.add(100-draw_probability);//不中奖概率
			  //抽奖是否中奖
			  int iswin=Utils.getLottery(list1); 
			  //中奖
			  if(iswin==0){
				  List<LotteryAwardsModel> list= lotteryAwardsDAO.findByProperty("factivityModel.id", factivityModel1.getId());
				  List<Integer> list2 = new ArrayList<Integer>();
				  for(LotteryAwardsModel factivityAwardsModel:list){
					  list2.add(factivityAwardsModel.getTotal());
				  }
				  int istrue=Utils.getLottery(list2); 
				  if(istrue<list.size()){
					  //中奖奖项
					  LotteryAwardsModel factivityAwardsModel=list.get(istrue);
					  
					//通过活动id和当前期数，查询期数表信息
						List<LotteryPeriodsModel> Periodslist= lotteryPeriodsDAO.findByTwoProperty("factivityModel.id", factivityModel1.getId(), "periods_num", factivityModel1.getThis_round());
							if(Periodslist!=null&&Periodslist.size()>0)	{
								LotteryPeriodsModel factivityPeriodsModel=Periodslist.get(0);
								//通过期数ID和奖项ID查询本期活动本奖项记录信息
								List<LotteryRecordModel> Recordlist=lotteryRecordDAO.findByTwoProperty("factivityPeriodsModel.id", Periodslist.get(0).getId(), "factivityAwardsModel.id", factivityAwardsModel.getId());
								if(Recordlist!=null&&Recordlist.size()>0)	{
								LotteryRecordModel factivityRecordModel=Recordlist.get(0);
								//当前中奖注数有剩余，中奖
								LotteryLogModel factivityLogModel=new LotteryLogModel();
								if(factivityRecordModel.getSurplus_num()>=1){
									
							/*		//活动钱包表
									List<ZdisFactivityWallet> zdisFactivityWalletList=zdisFactivityWalletDAO.findByProperty("factivityModel.id", factivityModel1.getId());
									if(zdisFactivityWalletList==null||zdisFactivityWalletList.size()<=0){
										jsonobject.accumulate("msg", "活动奖池不足");
										jsonobject.accumulate("code", -1001);
										return jsonobject.toString();
									}
									ZdisFactivityWallet zdisFactivityWallet=zdisFactivityWalletList.get(0);
                                    if(zdisFactivityWallet.getFtotal()<factivityAwardsModel.getFee_amount()){
                                    	jsonobject.accumulate("msg", "活动奖池不足");
 										jsonobject.accumulate("code", -1001);
 										return jsonobject.toString();
                                     }
									zdisFactivityWallet.setUpdateTime(Utils.getTimestamp());
									zdisFactivityWallet.setFtotal(zdisFactivityWallet.getFtotal()-factivityAwardsModel.getFee_amount());
									zdisFactivityWalletDAO.attachDirty(zdisFactivityWallet);
									//添加钱包变动信息
									ZdisFactivityWalletRecord zdisFactivityWalletRecord=new ZdisFactivityWalletRecord();
									zdisFactivityWalletRecord.setCreatetime(Utils.getTimestamp());
									zdisFactivityWalletRecord.setFactivityModel(factivityModel1);
									zdisFactivityWalletRecord.setFamount(factivityAwardsModel.getFee_amount());
									zdisFactivityWalletRecord.setFvirtualcointype(list.get(0).getFvirtualcointype());
									zdisFactivityWalletRecord.setType(2);
									zdisFactivityWalletRecord.setUser(factivityModel1.getCreate_user());
									zdisFactivityWalletRecordDAO.attachDirty(zdisFactivityWalletRecord);									
									*/
									//修改活动记录表
									factivityRecordModel.setSurplus_num(factivityRecordModel.getSurplus_num()-1);
									lotteryRecordDAO.attachDirty(factivityRecordModel);
									
									//添加用户中奖记录表
									factivityLogModel.setCreate_time(Utils.getTimestamp());
									factivityLogModel.setLotteryAwardsModel(factivityAwardsModel);
									factivityLogModel.setLotteryPeriodsModel(factivityPeriodsModel);
									factivityLogModel.setUser(user);
									lotteryLogDAO.attachDirty(factivityLogModel);
									
									
									//添加用户收益记录表
									Fintrolinfo fintrolinfo=new Fintrolinfo();								
									fintrolinfo.setFcreatetime(Utils.getTimestamp());
									fintrolinfo.setFuser(user);
									fintrolinfo.setFtitle("中奖："+factivityAwardsModel.getAwards_name());
									fintrolinfo.setFqty(factivityAwardsModel.getFee_amount());
									fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_LOTTERY);
									fintrolinfo.setFname(factivityAwardsModel.getFvirtualcointype().getfShortName());
									
									introlinfoDAO.save(fintrolinfo);
									
									
									
									//发放用户奖励
									Fvirtualwallet fvirtualwallet1=fvirtualwalletDAO.findVirtualWallet(user.getFid(), factivityAwardsModel.getFvirtualcointype().getFid());
									if(fvirtualwallet1!=null){
										fvirtualwallet1.setFtotal(fvirtualwallet1.getFtotal()+factivityAwardsModel.getFee_amount());										
										fvirtualwalletDAO.attachDirty(fvirtualwallet1);
									}
									jsonobject.accumulate("msg", "恭喜你中得"+factivityAwardsModel.getAwards_name());
									jsonobject.accumulate("type", factivityAwardsModel.getId());
									jsonobject.accumulate("code", 0);
									return jsonobject.toString();
								}else{
									//当前抽中的奖项已抽完，未中奖
									jsonobject.accumulate("msg", "很遗憾您未中奖");
									jsonobject.accumulate("code", 1);
									return jsonobject.toString();
								}
								
								}
							
							}
				  }
				  
			  }else{
				  
				  //未中奖
				    jsonobject.accumulate("msg", "很遗憾您未中奖");
					jsonobject.accumulate("code", 1);
					return jsonobject.toString();
			  }
			  

		}else{
			//可用余额不足
			jsonobject.accumulate("msg", "您的可用余额不足");
			jsonobject.accumulate("code", -3);
			return jsonobject.toString();
		}
		return jsonobject.toString();
	}

	

	public void close(FactivityModel factivityModel) {
		// TODO Auto-generated method stub
		factivityModel.setStatus(FactivityStatusEnum.close);
		factivityDAO.attachDirty(factivityModel);
		List<LotteryPeriodsModel> Periodslist= lotteryPeriodsDAO.findByTwoProperty("activity_id", factivityModel.getId(), "periods_num", factivityModel.getThis_round());
		if(Periodslist!=null&&Periodslist.size()>0){
			
			LotteryPeriodsModel FactivityPeriodsModel=Periodslist.get(0);
			lotteryPeriodsDAO.attachDirty(FactivityPeriodsModel);
		}

	}

	
}
