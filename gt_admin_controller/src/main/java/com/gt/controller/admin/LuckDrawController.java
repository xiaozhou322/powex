package com.gt.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.NperStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Lottery;
import com.gt.entity.Nper;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.IntrolinfoService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.LotteryService;
import com.gt.service.front.NperService;
import com.gt.util.PowerBallUtil;
import com.gt.util.Utils;

@Controller
public class LuckDrawController extends BaseAdminController{
	
	@Autowired
	private VirtualCoinService virtualCoinService ;
	
	@Autowired
	private TradeMappingService tradeMappingService;
	
	@Autowired
	private FrontUserService frontUserService;
	
	@Autowired
	private FvirtualWalletService virtualWalletService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private NperService nperService;
	@Autowired
	private IntrolinfoService introlinfoService;
	
	private int numPerPage = Utils.getNumPerPage();
	

	@RequestMapping("/buluo718admin/nperList")
	public ModelAndView nperList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/nperList") ;
		
		int currentPage = 1;
		String nper = request.getParameter("nper");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuilder filter = new StringBuilder();
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		filter.append("where 1=1 \n");
		if(nper != null && nper.trim().length() >0){
			filter.append("and nper = '"+nper+"' \n");
			modelAndView.addObject("nper", nper);
		}else{
			modelAndView.addObject("nper", "");
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by id \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Nper> list = nperService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		
		List<Fvirtualcointype> coinTypeList = this.virtualCoinService.findAll();
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		for (Fvirtualcointype coinType : coinTypeList) {
			map.put(coinType.getFid(),coinType.getfShortName());
		}
		modelAndView.addObject("coinTypeMap", map);
		modelAndView.addObject("list", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("totalCount",nperService.getAllCount("Nper", filter+""));
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/saveNper")
	public ModelAndView saveNper(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		String creater = request.getParameter("creater");
		String lottery_min = request.getParameter("lottery_min");
		String lottery_max = request.getParameter("lottery_max");
		String prize_coin_type = request.getParameter("prize_coin_type");
		String prize_amount = request.getParameter("prize_amount");
		String lottery_coin_type = request.getParameter("lottery_coin_type");
		String lottery_amount = request.getParameter("lottery_amount");
		String filter = "order by nper desc";
		List<Nper> list = nperService.list(0, 0, filter, false);
		Nper nper = new Nper();
		if(null != list && list.size()>0){
			int new_nper_no = Integer.valueOf(list.get(0).getNper())+1;
			nper.setNper(new_nper_no+"");
		}else{
			nper.setNper("1001");
		}
		if(lottery_min != null && lottery_min.trim().length() >0){
			nper.setLottery_min(Integer.valueOf(lottery_min));
		}else{
			nper.setLottery_min(100000001);
		}
		if(lottery_max != null && lottery_max.trim().length() >0){
			nper.setLottery_max(Integer.valueOf(lottery_max));
		}else{
			nper.setLottery_max(999999999);
		}
		if(creater != null && creater.trim().length() >0){
			nper.setCreater(Integer.valueOf(creater));
		}else{
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","creater is null");
			return modelAndView;
		}
		if(prize_coin_type != null && prize_coin_type.trim().length() >0){
			nper.setPrize_coin_type(Integer.valueOf(prize_coin_type));
		}else{
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","prize_coin_type is null");
			return modelAndView;
		}
		if(lottery_coin_type != null && lottery_coin_type.trim().length() >0){
			nper.setLottery_coin_type(Integer.valueOf(lottery_coin_type));
		}else{
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","lottery_coin_type is null");
			return modelAndView;
		}
		if(prize_amount != null && prize_amount.trim().length() >0){
			nper.setPrize_amount(Double.valueOf(prize_amount));
		}else{
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","prize_amount is null");
			return modelAndView;
		}
		if(lottery_amount != null && lottery_amount.trim().length() >0){
			nper.setLottery_amount(Double.valueOf(lottery_amount));
		}else{
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","lottery_amount is null");
			return modelAndView;
		}
		nper.setStatus(NperStatusEnum.NO_START);
		nper.setCreate_time(Utils.getTimestamp());
		nperService.saveObj(nper);
		lotteryService.createTable(Utils.getLotteryTable(nper.getNper()));
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","添加成功");
		modelAndView.addObject("callbackType","closeCurrent");
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/startNper")
	public ModelAndView startNper(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		String nper_id = request.getParameter("nper_id");
		if(Utils.isNull(nper_id)){
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","nper_id is null");
			return modelAndView;
		}
		String filter = "where status="+NperStatusEnum.START_ING;
		List<Nper> list = nperService.list(0, 0, filter, false);
		if(null != list && list.size()>0){
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","存在未结束的活动，请先结束！");
			return modelAndView;
		}else{
			Nper nper = nperService.findById(Integer.valueOf(nper_id));
			nper.setStart_time(Utils.getTimestamp());
			nper.setStatus(NperStatusEnum.START_ING);
			nperService.updateObj(nper);
			
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","开启成功");
		}
		return modelAndView;
	}
	
	
	
	@RequestMapping("/buluo718admin/drawLottery")
	public ModelAndView drawLottery(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		String nper_id = request.getParameter("nper_id");
		if(Utils.isNull(nper_id)){
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","nper_id is null");
			return modelAndView;
		}
		Nper nper = nperService.findById(Integer.valueOf(nper_id));
		if(nper.getStatus() != NperStatusEnum.START_ING){
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","本期抽奖未开始或已结束");
			return modelAndView;
		}
		Long count = lotteryService.currentLotteryNumber(nper.getNper(),Utils.getLotteryTable(nper.getNper()));
		
		//判断是否满标   1000w
		Long fullPow = 10000000L;
		if(nper.getLottery_amount()*count < fullPow) {
			modelAndView.addObject("statusCode",-1);
			modelAndView.addObject("message","本期奖票未抽完");
			return modelAndView;
		}
		
		nper.setStatus(NperStatusEnum.DRAW_LOTTERY);
		nper.setDraw_time(Utils.getTimestamp());
		nperService.updateObj(nper);
		
		//int[] ball = new int[]{11,04,39,48,50,51};
		String ballStr = PowerBallUtil.getBall();
		int[] ball = PowerBallUtil.toArray(ballStr);

		int sum = 0;
		int index=ball[0]-1;
		for(int j=1;j<ball.length;j++){
			
			String sql = "SELECT CONCAT(SUBSTR(createtime,12,2),SUBSTR(createtime,15,2),SUBSTR(createtime,18,2),SUBSTR(createtime,21,3)) as time "
					+ "from Lottery  where nper ='"+nper.getNper()+"' order by createtime desc";
			List<String> lotterys = lotteryService.queryList(index, ball[j], sql, true,Utils.getLotteryTable(nper.getNper()));
			
			for(int i=0;i<ball[j];i++){
				sum += Integer.valueOf(lotterys.get(i));
			}
			
			index += 10000;
		}
		
		int num = sum%count.intValue();
		num += nper.getLottery_min();
		
		String win_no = num+"";
		List<Lottery> win = lotteryService.findByTwoProperty("nper", nper.getNper(), "lottery_no", win_no,Utils.getLotteryTable(nper.getNper()));
		if(null != win && win.size()==1){
			nper.setWin_uid(win.get(0).getUid());
		}
		nper.setBall(ballStr);
		nper.setWin_no(win_no);
		nper.setStatus(NperStatusEnum.END);
		nper.setEnd_time(Utils.getTimestamp());
		nperService.updateObj(nper);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","中奖号码为："+win_no);
		
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/goNperJsp")
	public ModelAndView goNperJsp(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		List<Fvirtualcointype> coinTypeList = this.virtualCoinService.findAll();
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		for (Fvirtualcointype coinType : coinTypeList) {
			map.put(coinType.getFid(),coinType.getFname());
		}
		String nper_id = request.getParameter("nper_id");
		if(StringUtils.isNotEmpty(nper_id)){
			Nper nper = nperService.findById(Integer.parseInt(nper_id));
			
			modelAndView.addObject("nper", nper);
			
			if( null != nper.getWin_uid() && nper.getWin_uid() > 0){
				int userId = nper.getWin_uid();
				int coinId = nper.getPrize_coin_type();
				
				//成功买入、卖出、挂单不可用的币数
				double buycount = 0d;
				double sellcount =0d;
				double sellfrozen =0d;
				String s1 = "";
				
				//提取币种的所有交易市场
				List<Ftrademapping> tmps = this.tradeMappingService.list(0,10,"where fvirtualcointypeByFvirtualcointype2.fid="+coinId, false);
				if (tmps.size()>0){
					String trdmpIds = "";
					for(Ftrademapping tradmapping:tmps){
						if (trdmpIds.equals("")){
							trdmpIds = tradmapping.getFid().toString();
						}else{
							trdmpIds = trdmpIds +"," +tradmapping.getFid().toString();
						}
					}
					
					s1 = "SELECT sum(fCount-fleftCount) from fentrust where fEntrustType=0 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					buycount = this.adminService.getSQLValue(s1);
					
					s1 = "SELECT sum(fCount-fleftCount) from fentrust where fEntrustType=1 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					sellcount = this.adminService.getSQLValue(s1);
					
					s1 = "SELECT sum(fleftCount) from fentrust where fEntrustType=1 and fStatus in (1,2) and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					sellfrozen = this.adminService.getSQLValue(s1);
				}
				
				double fabiusecount = 0d;
				double fabigetcount = 0d;
				double fabifrozen = 0d;
				
				//如果是法币的虚拟币
				if (coinId==1 || coinId==3){
					
					List<Ftrademapping> fbtmps = this.tradeMappingService.list(0,10,"where fvirtualcointypeByFvirtualcointype1.fid="+coinId, false);
					if (tmps.size()>0){
						String trdmpIds = "";
						for(Ftrademapping tradmapping:fbtmps){
							if (trdmpIds.equals("")){
								trdmpIds = tradmapping.getFid().toString();
							}else{
								trdmpIds = trdmpIds +"," +tradmapping.getFid().toString();
							}
						}
						
						s1 = "SELECT sum(fsuccessAmount) from fentrust where fEntrustType=0 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
						fabiusecount = this.adminService.getSQLValue(s1);
						
						s1 = "SELECT sum(fsuccessAmount) from fentrust where fEntrustType=1 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
						fabigetcount = this.adminService.getSQLValue(s1);
						
						s1 = "SELECT sum(fAmount-fsuccessAmount) from fentrust where fEntrustType=0 and fStatus in (1,2) and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
						fabifrozen = this.adminService.getSQLValue(s1);
					}
					
				}
				
				
				//充值、提现、提币冻结的币数
				double rechargecount =0d;
				double withdrawcount =0d;
				double withdrawfrozen =0d;
				double rewardcount =0d;
				
				
				double wallettotal =0d;
				double walletfrozen =0d;
				
				s1 = "SELECT sum(fAmount) from fvirtualcaptualoperation where fType=1 and fStatus =3 and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
				rechargecount = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT sum(fAmount+ffees) from fvirtualcaptualoperation where fType in (2,3) and fStatus =3 and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
				withdrawcount = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT sum(fAmount+ffees) from fvirtualcaptualoperation where fType in (2,3) and fStatus in (1,2) and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
				withdrawfrozen = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT sum(fqty) from fintrolinfo where fname='"+map.get(coinId) + "' and fuserid=" +userId;
				rewardcount = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT fTotal from fvirtualwallet where fVi_fId="+coinId+ " and fuid=" +userId;
				wallettotal = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT fFrozen from fvirtualwallet where fVi_fId="+coinId+ " and fuid=" +userId;
				walletfrozen = this.adminService.getSQLValue(s1);
				
				modelAndView.addObject("buycount",buycount);
				modelAndView.addObject("sellcount",sellcount);
				modelAndView.addObject("sellfrozen",sellfrozen);
				modelAndView.addObject("rechargecount",rechargecount);
				modelAndView.addObject("withdrawcount",withdrawcount);
				modelAndView.addObject("withdrawfrozen",withdrawfrozen);
				modelAndView.addObject("rewardcount",rewardcount);
				modelAndView.addObject("wallettotal",wallettotal);
				modelAndView.addObject("walletfrozen",walletfrozen);
				modelAndView.addObject("fabigetcount",fabigetcount);
				modelAndView.addObject("fabiusecount",fabiusecount);
				modelAndView.addObject("fabifrozen",fabifrozen);
				
				BigDecimal a1 = new BigDecimal(Double.toString(rewardcount+buycount+rechargecount+fabigetcount));
				BigDecimal a2 = new BigDecimal(Double.toString(sellcount+withdrawcount+sellfrozen+withdrawfrozen+fabiusecount+fabifrozen));
				
				modelAndView.addObject("youxiaoshu", a1.subtract(a2).doubleValue());
				Fuser fuser = this.frontUserService.findById(userId);
				modelAndView.addObject("user",fuser);
				modelAndView.addObject("coin_name",map.get(coinId));
			}
		}
		modelAndView.addObject("admin_id", getAdminSession(request).getFid());
		modelAndView.addObject("coinTypeMap", map);
		return modelAndView;
	}
	
	
	
	@RequestMapping("/buluo718admin/doSendPrize")
	public ModelAndView doSendPrize(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		String confirmCode = request.getParameter("confirmcode");
		if (confirmCode!=null && !confirmCode.equals("")){
			Fadmin admin = (Fadmin) request.getSession()
					.getAttribute("login_admin");
			String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
			if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
				return modelAndView;
			}
		}else{
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，审核码不能为空!");
			return modelAndView;
		}
		String userId = request.getParameter("uid");
		String coinTypeId = request.getParameter("coin_type");
		String amount = request.getParameter("amount");
		
		Fuser fuser = this.frontUserService.findById(Integer.valueOf(userId));
		Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(Integer.valueOf(coinTypeId));
		
		String nper_id = request.getParameter("nper_id");
		if(Utils.isNull(nper_id)){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "nper_id is null!");
			return modelAndView;
		}
		int r = nperService.sendPrize(Integer.valueOf(nper_id));
		if(r > 0){
			//添加用户收益记录表
			Fintrolinfo fintrolinfo=new Fintrolinfo();								
			fintrolinfo.setFcreatetime(Utils.getTimestamp());
			fintrolinfo.setFuser(fuser);
			fintrolinfo.setFtitle("万币活动中奖");
			fintrolinfo.setFqty(Double.valueOf(amount));
			fintrolinfo.setFtype(IntrolInfoTypeEnum.ETH10000);
			fintrolinfo.setFname(fvirtualcointype.getfShortName());
			
			introlinfoService.saveObj(fintrolinfo);
			
			//发放用户奖励
			Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(Integer.valueOf(userId), Integer.valueOf(coinTypeId));
			if(null != virtualWalletInfo) {
				virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
				virtualWalletInfo.setFtotal(virtualWalletInfo.getFtotal() + Double.valueOf(amount));
				virtualWalletService.updateObj(virtualWalletInfo);
			}
		}else{
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "操作失败!");
			return modelAndView;
		}
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","操作成功");
		return modelAndView;
	}
	
	
	
	@RequestMapping("/buluo718admin/lotteryList")
	public ModelAndView lotteryList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/lotteryList") ;
		
		int currentPage = 1;
		String uid = request.getParameter("uid");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String nper = request.getParameter("nper");
		StringBuilder filter = new StringBuilder();
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		filter.append("where 1=1 \n");
		if(uid != null && uid.trim().length() >0){
			filter.append("and uid = "+uid+" \n");
			modelAndView.addObject("uid", uid);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by id \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		if(Utils.isNull(nper)){
			String sql = "where status>="+NperStatusEnum.START_ING+" order by start_time desc";
			List<Nper> list = nperService.list(0, 0, sql, false);
			if(null != list && list.size()>0){
				nper = list.get(0).getNper();
			}
		}
		List<Lottery> lotteryList = lotteryService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true,Utils.getLotteryTable(nper));
		
		int totalCount = lotteryService.getAllCount(filter+"",Utils.getLotteryTable(nper));
		
		
		String nperFilter = "where status >"+NperStatusEnum.NO_START+" order by start_time desc";
		List<Nper> list = nperService.list(0, 5, nperFilter+"",true);
		List<String> li = new ArrayList<String>();
		for(Nper n : list){
			li.add(n.getNper());
		}
		
		modelAndView.addObject("list", lotteryList);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("totalCount",totalCount);
		
		modelAndView.addObject("nperList", li);
		
		return modelAndView;
	}
	
}
