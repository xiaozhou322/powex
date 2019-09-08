package com.gt.controller.front;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.gt.Enum.FactivityStatusEnum;
import com.gt.Enum.FactivityTypeEnum;
import com.gt.Enum.FactivityWayEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FactivityModel;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.LotteryLogModel;
import com.gt.entity.LotteryPeriodsModel;
import com.gt.entity.LotteryRecordModel;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontActivityService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontLotteryAwardsService;
import com.gt.service.front.FrontLotteryLogService;
import com.gt.service.front.FrontLotteryPeriodsService;
import com.gt.service.front.FrontLotteryRecordService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;


@Controller
public class FrontLuckydrawJsonController extends BaseController {
	
	@Autowired
	private FrontActivityService frontActivitiesService;
	@Autowired
	private FrontLotteryAwardsService frontLotteryAwardsService;
	@Autowired
	private FvirtualWalletService fvirtualWalletService;
	@Autowired
	private FrontLotteryPeriodsService frontLotteryPeriodsService;
	@Autowired
	private FrontLotteryLogService frontLotteryLogService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FrontLotteryRecordService frontLotteryRecordService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontUserService frontUserService ;

	/**
	 * 抽奖
	 * @param request
	 * @param id 活动Id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/activitity/lottery",produces={JsonEncode})
	public String saveLottery(HttpServletRequest request,
			@RequestParam(required = true) int id) throws Exception {
		JSONObject json = new JSONObject();

		long newTime = Utils.getTimestamp().getTime();
		if (getSession(request).getAttribute("time") != null && StringUtils.isNotEmpty((String) getSession(request).getAttribute("time"))) {
			long time = Long.parseLong((String) getSession(request).getAttribute("time"));

			if (newTime < time + 1 * 1000) {
				json.accumulate("code", "-1");
				json.accumulate("msg", "操作频繁，10秒后再来抽奖");
				return json.toString();
			}

		}
		
		Fuser user = GetSession(request);
        if(user==null){
	      json.accumulate("code", "-4");
	      json.accumulate("msg", "未登录");
	      return json.toString();
          }
		if (user.getFstatus() != 1) {
			json.accumulate("code", "-1");
			json.accumulate("msg", "账号异常");
			return json.toString();

		}
		
		

		if (!user.getFhasRealValidate() || !user.getFpostRealValidate()) {
			json.accumulate("code", "101");
			json.accumulate("msg", "用户未进行KYC1实名认证");
			return json.toString();

		}
		if (!user.isFhasImgValidate() || !user.isFpostImgValidate()) {
			json.accumulate("code", "102");
			json.accumulate("msg", "用户未进行KYC2认证");
			return json.toString();

		}
		
				

		FactivityModel factivityModel = frontActivitiesService.findById(id);
		if (factivityModel == null || factivityModel.getStatus()!= FactivityStatusEnum.open) {
			json.accumulate("code", "-1");
			json.accumulate("msg", "活动异常");
			return json.toString();
		}
		//充值抽奖时间
		getSession(request).setAttribute("time", Long.toString(newTime));
		
		String str = frontActivitiesService.saveLottery(user.getFid(), id);
		json = JSONObject.fromObject(str);

		return json.toString();
	}
	/**
	 * 获取一次性抽奖活动列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/activitity/getLottery",produces={JsonEncode})
	public String getLottery(
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		String sql=" where type="+FactivityTypeEnum.disposable+" and way="+FactivityWayEnum.lottery+" and status="+FactivityStatusEnum.open;
		List<FactivityModel> list=frontActivitiesService.getlist(0,0, sql, false);
		for(FactivityModel factivityModel:list){
			JSONObject json=new JSONObject();
			json.accumulate("id", factivityModel.getId());
			json.accumulate("name", factivityModel.getName());
			json.accumulate("this_round", factivityModel.getThis_round());
			jsonArray.add(json);
		}
			
		jsonObject.accumulate("list", jsonArray)	;
		jsonObject.accumulate("code", 0)	;	
		
		return jsonObject.toString();
	}
	
	
	
	/**
	 * 获取奖池
	 * @param request
	 * * @param id 活动id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/activitity/getPrizePool",produces={JsonEncode})
	public String getPrizePool(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		JSONObject jsonObject=new JSONObject();
		JSONObject fvirtualwalletJson=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		FactivityModel factivityModel=frontActivitiesService.findById(id);
		Fuser user=GetSession(request);
		 List<LotteryPeriodsModel> list=frontLotteryPeriodsService.findByTwoProperty("factivityModel.id", factivityModel.getId(), "periods_num", factivityModel.getThis_round());
		if(list!=null&&list.size()>0){
			LotteryPeriodsModel lotteryPeriodsModel=list.get(0);
			List<LotteryRecordModel> recordList=frontLotteryRecordService.findByProperty("factivityPeriodsModel.id", lotteryPeriodsModel.getId());
			for(LotteryRecordModel lotteryRecordModel:recordList){
				JSONObject json=new JSONObject();
				json.accumulate("surplus_num", lotteryRecordModel.getSurplus_num());
				json.accumulate("awards_name", lotteryRecordModel.getFactivityAwardsModel().getAwards_name());
				json.accumulate("fee_amount", lotteryRecordModel.getFactivityAwardsModel().getFee_amount());
				json.accumulate("coin_name", lotteryRecordModel.getFactivityAwardsModel().getFvirtualcointype().getfShortName());
				jsonArray.add(json);
			}
		}
		
		Fvirtualwallet fvirtualwallet=fvirtualWalletService.findFvirtualwallet(user.getFid(), factivityModel.getFvirtualcointype().getFid());
		if(fvirtualwallet!=null){
			fvirtualwalletJson.accumulate("ftotal", fvirtualwallet.getFtotal())	;
			fvirtualwalletJson.accumulate("ffrozen", fvirtualwallet.getFfrozen())	;
			fvirtualwalletJson.accumulate("coin_name", fvirtualwallet.getFvirtualcointype().getfShortName())	;
		}
		
		jsonObject.accumulate("fvirtualwallet", fvirtualwalletJson);
		
		jsonObject.accumulate("list", jsonArray)	;
		jsonObject.accumulate("code", 0)	;	
		
		return jsonObject.toString();
	}

	
	
	/**
	 * 获取活动中奖急录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/activitity/getLotteryLog",produces={JsonEncode})
	public String getLotteryLog(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="10")int maxResult,
			@RequestParam(required=true)int id
			) throws Exception{
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		FactivityModel factivityModel=frontActivitiesService.findById(id);
		
		 List<LotteryPeriodsModel> list=frontLotteryPeriodsService.findByTwoProperty("factivityModel.id", factivityModel.getId(), "periods_num", factivityModel.getThis_round());
		if(list!=null&&list.size()>0){
			LotteryPeriodsModel lotteryPeriodsModel=list.get(0);
			String sql=" where lotteryPeriodsModel.id="+lotteryPeriodsModel.getId();
			int total = this.adminService.getAllCount("LotteryLogModel", sql);			
			int totalPage = total / maxResult + (total % maxResult == 0 ? 0 :1) ;

			currentPage = currentPage <0?0:currentPage ;
			currentPage = currentPage > totalPage?totalPage:currentPage ;
			
			List<LotteryLogModel> listlog=frontLotteryLogService.getlist((currentPage-1)*maxResult, maxResult, sql, false);
			for(LotteryLogModel lotteryLogModel:listlog){
				JSONObject json=new JSONObject();
				String userId=String.valueOf(lotteryLogModel.getUser().getFid());
				json.accumulate("userId", userId.replaceAll("(?<=[\\d]{2})\\d(?=[\\d]{2})", "*"));
				
				json.accumulate("time", Utils.dateFormat(lotteryLogModel.getCreate_time()));
				json.accumulate("awards_name", lotteryLogModel.getLotteryAwardsModel().getAwards_name());
				json.accumulate("num", lotteryLogModel.getLotteryAwardsModel().getFee_amount());
				json.accumulate("coin_name", lotteryLogModel.getLotteryAwardsModel().getFvirtualcointype().getfShortName());
				jsonArray.add(json);
			}
			jsonObject.accumulate("total", total);	
			jsonObject.accumulate("totalPage", totalPage);	
		}
		
		jsonObject.accumulate("list", jsonArray);
		jsonObject.accumulate("code", 0);	
		
		return jsonObject.toString();
	}
	
	

}
