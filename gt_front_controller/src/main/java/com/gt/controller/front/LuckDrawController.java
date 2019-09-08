package com.gt.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.Enum.NperStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Lottery;
import com.gt.entity.Nper;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.LotteryService;
import com.gt.service.front.NperService;
import com.gt.util.Utils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/luckDraw")
public class LuckDrawController extends BaseController {

	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private NperService nperService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FvirtualWalletService fvirtualWalletService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	
	/**
	 * 摇奖接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getNewLottery",produces={JsonEncode})
	public String getNewLottery(HttpServletRequest request){
		JSONObject result = new JSONObject();
//		Fuser user = GetSession(request);
		Integer uid = GetSession(request).getFid();
//		Integer uid = 107676;
		Fuser user = this.frontUserService.findById(uid) ;
		
		if (user.getFstatus() != 1) {
			result.accumulate("code", "-1");
			result.accumulate("msg", "账号异常");
			return result.toString();
		}
		
		if (!user.getFhasRealValidate() || !user.getFpostRealValidate()) {
			result.accumulate("code", "101");
			result.accumulate("msg", "用户未进行KYC1实名认证");
			return result.toString();
		}
		
		if (!user.isFhasImgValidate() || !user.isFpostImgValidate()) {
			result.accumulate("code", "102");
			result.accumulate("msg", "用户未进行KYC2认证");
			return result.toString();
		}
		
		String num = request.getParameter("num");
		List<Nper> list = nperService.findByProperty("status", NperStatusEnum.START_ING);
		if(null == list || list.size()<=0){
			result.accumulate("code", -1);
			result.accumulate("msg", "活动不存在");
			return result.toString();
		}
		Nper nper = list.get(0);
		if(null == nper.getStart_time() ||
				(null != nper.getStart_time() && Utils.getTimestamp().getTime() < nper.getStart_time().getTime())){
			result.accumulate("code", -1);
			result.accumulate("msg", "本次活动已结束或未开启");
			return result.toString();
		}
		
		//判断是否满标
		Long maxLotteryNo = lotteryService.maxLotteryNo(nper.getNper(), Utils.getLotteryTable(nper.getNper()));
		if(String.valueOf(maxLotteryNo).equals(String.valueOf(nper.getLottery_max()))) {
			result.accumulate("code", -4);
			result.accumulate("msg", "本次活动已满标等待开奖");
			return result.toString();
		}
		
		if(Utils.isNull(num)){
			num = "1";
		}
		int count = Integer.valueOf(num);
		
		Fvirtualwallet fvirtualwallet = fvirtualWalletService.findFvirtualwallet(user.getFid(), nper.getLottery_coin_type());
		if(fvirtualwallet == null || fvirtualwallet.getFtotal() < count*nper.getLottery_amount()){
			result.accumulate("code", -3);
			result.accumulate("msg", "账户余额不足");
			return result.toString();
		}
		
		final List<String> li = new ArrayList<String>();
		if(count <= 100){
			int validNum = 0;
			try {
				for(int i = 0;i<count;i++){
					String lottery_no = lotteryService.saveNewNper(nper, user.getFid());
					if(!Utils.isNull(lottery_no)){
						li.add(lottery_no);
						validNum++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//扣除有效抽奖的钱包的数量
			fvirtualwallet = fvirtualWalletService.findFvirtualwallet(user.getFid(), nper.getLottery_coin_type());
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() - validNum*nper.getLottery_amount());
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
			fvirtualWalletService.attachDirty(fvirtualwallet);
		}else{
			final Nper nper_ = nper;
			final int uid_ = user.getFid();
			final int count_ = count;
			new Thread(new Runnable() {
				@Override
				public void run() {
					int validNum = 0;
					try {
						for(int i = 0;i<count_;i++){
							String lottery_no = lotteryService.saveNewNper(nper_, uid_);
							if(!Utils.isNull(lottery_no)){
								li.add(lottery_no);
								validNum++;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					//扣除有效抽奖的钱包的数量
					final Fvirtualwallet fvirtualwallet_ = fvirtualWalletService.findFvirtualwallet(uid_, nper_.getLottery_coin_type());
					fvirtualwallet_.setFtotal(fvirtualwallet_.getFtotal() - validNum*nper_.getLottery_amount());
					fvirtualwallet_.setFlastUpdateTime(Utils.getTimestamp());
					fvirtualWalletService.attachDirty(fvirtualwallet_);
				}
			}).start();
			result.accumulate("msg", "后台正在处理中，请稍后查看");
		}
		
		result.accumulate("data", li);
		result.accumulate("code", 200);
		result.accumulate("time", Utils.getTimestamp().getTime());
		return result.toString();
	}
	
	
	
	/**
	 * 获取用户摇奖的列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getLotteryList",produces={JsonEncode})
	public String lotteryList(HttpServletRequest request) throws Exception{
		JSONObject result = new JSONObject();
		
		Integer uid = GetSession(request).getFid();
//		Integer uid = 107676;
		Fuser fuser = this.frontUserService.findById(uid) ;
		
		String nperId = request.getParameter("nper");
		
		StringBuffer filter = new StringBuffer();
		filter.append(" where uid=" + fuser.getFid());
		
		if(Utils.isNull(nperId)){
			result.accumulate("code", -1);
			result.accumulate("msg", "活动期数不能为空");
			return result.toString();
		}
		List<Nper> nperlist = nperService.findByProperty("nper", nperId);
		if(null == nperlist || nperlist.size()<=0){
			result.accumulate("code", -1);
			result.accumulate("msg", "活动未开启");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		Nper nper = nperlist.get(0);
		
		List<Lottery> lotteryList = lotteryService.list(0, 0, filter+"",false,Utils.getLotteryTable(nperId));
		for (int i = 0; i < lotteryList.size(); i++) {
			Lottery lottery = lotteryList.get(i);
			if(StringUtils.isEmpty(nper.getWin_no()) && NperStatusEnum.START_ING == nper.getStatus()) {
				lottery.setLotteryStatus("未开奖");
			} else if(StringUtils.isEmpty(nper.getWin_no()) && NperStatusEnum.DRAW_LOTTERY == nper.getStatus()) {
				lottery.setLotteryStatus("开奖中");
			} else if(StringUtils.isNotEmpty(nper.getWin_no()) && lottery.getLottery_no().equals(nper.getWin_no())) {
				lottery.setLotteryStatus("已中奖");
			} else if(StringUtils.isNotEmpty(nper.getWin_no()) && !lottery.getLottery_no().equals(nper.getWin_no())) {
				lottery.setLotteryStatus("未中奖");
			}
		}
		
//		int totalCount = lotteryService.getAllCount(filter+"",Utils.getLotteryTable(nperId));
		
//		result.accumulate("nper", nper);
		result.accumulate("lotteryList", lotteryList);
//		result.accumulate("totalCount", totalCount);
		result.accumulate("code", 200);
		
		return result.toString();
	}
	
	
	/**
	 * 当前期数剩余数量
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getRemainPowNum",produces={JsonEncode})
	public String getRemainPOWNum(HttpServletRequest request) throws Exception{
		JSONObject result = new JSONObject();
		
		Integer uid = GetSession(request).getFid();
//		Integer uid = 107676;
		Fuser fuser = this.frontUserService.findById(uid) ;
		
		List<Nper> nperList = nperService.findByStatus(NperStatusEnum.NO_START, 1);
		if(null == nperList || nperList.size()<=0){
			result.accumulate("code", -1);
			result.accumulate("msg", "活动未开启");
			result.accumulate("time", Utils.getTimestamp().getTime());
			return result.toString();
		}
		
		Nper nper = nperList.get(0);
		
		//获取配置的抽奖的币种
		String[] tradeRewardCoin = frontConstantMapService.getString("introlSendCoin").split("#");
		int rewardCoinId = 0;
		if(null != tradeRewardCoin) {
			rewardCoinId = Integer.parseInt(tradeRewardCoin[0]);
		}
		
		Fvirtualwallet fvirtualwallet = fvirtualWalletService.findFvirtualwallet(fuser.getFid(), rewardCoinId);
		if(null == fvirtualwallet){
			result.accumulate("code", -1);
			result.accumulate("msg", "未设置抽奖的币种");
			return result.toString();
		}
		
		
		Long count = lotteryService.currentLotteryNumber(nper.getNper(),Utils.getLotteryTable(nper.getNper()));
		
		//判断是否满标   1000w
		Long fullPow = 10000000L;
		
		Long remainNum = (long) (fullPow - nper.getLottery_amount()*count);
		result.accumulate("nper", nper.getNper());
		result.accumulate("remainNum", remainNum);
		result.accumulate("totalPowNum", fvirtualwallet.getFtotal());
		result.accumulate("code", 200);
		
		return result.toString();
	}
}
