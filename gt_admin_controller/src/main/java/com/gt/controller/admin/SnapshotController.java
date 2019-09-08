package com.gt.controller.admin;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.MessageStatusEnum;
import com.gt.Enum.OperationlogEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fassetgt;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fentrustsnap;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Flog;
import com.gt.entity.Fmessage;
import com.gt.entity.Freleaselog;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualpresalelog;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.admin.IntrolinfoService;
import com.gt.service.admin.MessageService;
import com.gt.service.admin.ReleaseLogService;
import com.gt.service.admin.SnapEntrustService;
import com.gt.service.admin.SnapGtService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.admin.VirtualPresaleLogService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;
import com.gt.entity.Fentrustminer;
import com.gt.service.admin.EntrustMinerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gt.service.admin.LogService;

@Controller
public class SnapshotController extends BaseAdminController {
	@Autowired
	private SnapGtService snapGtService;
	@Autowired
	private SnapEntrustService snapEntrustService;
	@Autowired
	private EntrustMinerService entrustMinerService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private IntrolinfoService introlinfoService;
	@Autowired
	private ReleaseLogService releaseLogService;
	/*@Autowired
	private ConstantMap constantMap;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private LogService logService;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/snapGtList")
	public ModelAndView snapGtList(HttpServletRequest request) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/snapGtList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 and freward>=0.0001 \n");
		if (!this.isSuper(request)){
			filter.append(" and fuser.fid<>7215 \n");
		}
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
			filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		String logDate = request.getParameter("logDate");
		if(logDate == null || logDate.trim().length() ==0){
			Calendar cale = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			cale.setTime(now);
			cale.add(cale.DAY_OF_YEAR, -1);
			logDate = sdf.format(cale.getTime());
		}
		filter.append("and  DATE_FORMAT(flastupdatetime,'%Y-%m-%d') = '"+logDate+"' \n");
		modelAndView.addObject("logDate", logDate);
		
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Fassetgt> list = this.snapGtService.list((currentPage-1)*numPerPage, numPerPage, filter+"");
		modelAndView.addObject("list", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "snapGtList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fassetgt", filter+""));
		modelAndView.addObject("totalreward",this.adminService.getAllSum("Fassetgt","freward", filter+""));
		modelAndView.addObject("usergts",this.adminService.getAllSum("Fassetgt","ftotal+ffrozen", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/snapEntrustList")
	public ModelAndView snapEntrustList(HttpServletRequest request) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/snapEntrustList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 and freward>=0.0001 \n");
		if (!this.isSuper(request)){
			filter.append(" and fuser.fid<>7215 \n");
		}
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
			filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		String logDate = request.getParameter("logDate");
		if(logDate == null || logDate.trim().length() ==0){
			Calendar cale = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			cale.setTime(now);
			cale.add(cale.DAY_OF_YEAR, -1);
			logDate = sdf.format(cale.getTime());
		}
		filter.append("and  DATE_FORMAT(flastupdatetime,'%Y-%m-%d') = '"+logDate+"' \n");
		modelAndView.addObject("logDate", logDate);
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Fentrustsnap> list = this.snapEntrustService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("list", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "snapEntrustList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fentrustsnap", filter+""));
		modelAndView.addObject("totalreward",this.adminService.getAllSum("Fentrustsnap", "freward",filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/entrustMinerList")
	public ModelAndView entrustMinerList(HttpServletRequest request) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/entrustMinerList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		int ftype = 0;
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 and freward>0 \n");
		if (!this.isSuper(request)){
			filter.append(" and fuser.fid<>7215 \n");
		}
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
			filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		String logDate = request.getParameter("logDate");
		if(logDate == null || logDate.trim().length() ==0){
			Calendar cale = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			cale.setTime(now);
			cale.add(cale.DAY_OF_YEAR, -1);
			logDate = sdf.format(cale.getTime());
		}
		filter.append("and  DATE_FORMAT(flastupdatetime,'%Y-%m-%d') = '"+logDate+"' \n");
		modelAndView.addObject("logDate", logDate);
		
		if (request.getParameter("ftype") != null) {
			ftype =Integer.valueOf(request.getParameter("ftype"));
		}
		
		if (frontConstantMapService.get("trademiner")!=null){
			//String trademiner = constantMap.getString("trademiner");
			String trademiner = frontConstantMapService.getString("trademiner");
			if(null!=trademiner && !trademiner.equals("")){
				JSONArray miners = JSONArray.fromObject(trademiner);
				if (miners.size()>0){
					List<Map>  mineTrades = new ArrayList();;
					for(int i=0;i<miners.size();i++){
						JSONObject miner = miners.getJSONObject(i);
						Map map = new HashMap();
						map.put("fid", miner.getString("marketid"));
						map.put("fname", miner.getString("marketname"));
						mineTrades.add(map);
					}
					modelAndView.addObject("trades", mineTrades);
					if(ftype==0){
						ftype =Integer.valueOf(miners.getJSONObject(0).getString("marketid"));
					}
				}
			}
		}
		
		
		filter.append( " and fmktid="+ftype+" ");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Fentrustminer> list = this.entrustMinerService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("list", list);
		
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "snapEntrustList");
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("ftype", ftype);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fentrustminer", filter+""));
		modelAndView.addObject("totalreward",this.adminService.getAllSum("Fentrustminer", "freward",filter+""));
		return modelAndView ;
	}
	
	//发放GT分红
	@RequestMapping("/buluo718admin/sendGtReward")
	public ModelAndView sendGtReward(HttpServletRequest request) throws Exception{
		
		
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
		
		//获取分红
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fassetgt fassetgt = this.snapGtService.findById(fid);
		
		Double reward = Utils.getDouble(fassetgt.getFreward(), 4);
		
		if (fassetgt.getStatus() || reward<=0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "已经发放分红，不允许重新发放!");
			return modelAndView;
		}
		
		//检测用户当天是否有登陆，没有登陆则不发放500GT锁仓分红
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuffer filter = new StringBuffer();
		filter.append("where ftype=1 \n");
		filter.append("and DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+sdf.format(fassetgt.getFlastupdatetime())+"' \n");
		filter.append("and fkey1 = '"+fassetgt.getFuser().getFid()+"' \n");
		
		List<Flog> list = this.logService.list(0, numPerPage, filter+"",true);
		if(list.size()<1){
			Double gtlock = 0d;
			String sql =  "where fvirtualcointype.fid=58 and fuser.fid="+fassetgt.getFuser().getFid();
			List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0,sql, false);
			if(all != null && all.size() == 1){
				gtlock = all.get(0).getFlocked();
			}
			if (gtlock>0){
				reward=Utils.getDouble((fassetgt.getFtotal()-gtlock)*fassetgt.getFtotalfee()/fassetgt.getFgtqty(), 4);
				if (reward<=0){
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "当日未登陆，500GT奖励不发!");
					return modelAndView;
				}
			}
		}
		
		
		
		//GT分红，是直接发放USDT
		Fvirtualwallet walletInfo = this.frontUserService.findUSDTWalletByUser(fassetgt.getFuser().getFid());
		if (walletInfo == null) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "分红发放失败，会员钱包信息异常!");
			return modelAndView;
		}
		
		
		try {
			//发放分红到用户USDT账户
			
			walletInfo.setFtotal(walletInfo.getFtotal()+reward);
			walletInfo.setFlastUpdateTime(Utils.getTimestamp());
			
			
			//修改快照状态为已处理
			fassetgt.setStatus(true);
			
			Fintrolinfo fintrolinfo = new Fintrolinfo() ;
			fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
			fintrolinfo.setFqty(reward) ;
			fintrolinfo.setFtitle(sdf.format(fassetgt.getFlastupdatetime())+"分红："+ reward +"USDT");
			fintrolinfo.setFuser(fassetgt.getFuser()) ;
			fintrolinfo.setFname("USDT");
			fintrolinfo.setFiscny(false);
			fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_GT_BONUS);
			
			//添加发放日志
			Freleaselog freleaselog = new Freleaselog();
			freleaselog.setFcreatetime(Utils.getTimestamp());
			freleaselog.setFqty(reward);
			freleaselog.setFreleasetype(2);
			freleaselog.setFuser(fassetgt.getFuser());
			freleaselog.setFvirtualcointype(walletInfo.getFvirtualcointype());
			
			this.introlinfoService.saveObj(fintrolinfo);
			this.snapGtService.updateObj(fassetgt);
			this.releaseLogService.saveObj(freleaselog);
			this.virtualWalletService.updateObj(walletInfo);
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "分红发放成功!");
			return modelAndView ;
		}catch(Exception e){
			e.printStackTrace();
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "分红发放失败!");
			return modelAndView ;
		}
		
	}
	
	
	
	//发放GT分红
		@RequestMapping("/buluo718admin/sendGtRewardBatch")
		public ModelAndView sendGtRewardBatch(HttpServletRequest request) throws Exception{
			
			
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
			
			String ids = request.getParameter("ids");
			String[] idString = ids.split(",");
			int err_has = 0;
			int err_exception = 0;
			int err_fail = 0;
			int err_succ =0;
			int err_nologin =0 ;
			Fadmin admin = (Fadmin) request.getSession()
					.getAttribute("login_admin");
			
			//检测用户当天是否有登陆，没有登陆则不发放500GT锁仓分红
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			
			
			for(int i=0;i<idString.length;i++){
					int fid = Integer.parseInt(idString[i]);
					Fassetgt fassetgt = this.snapGtService.findById(fid);
				
					Double reward = Utils.getDouble(fassetgt.getFreward(), 4);
				
				if (fassetgt.getStatus() || reward<=0){
//					modelAndView.addObject("statusCode", 300);
//					modelAndView.addObject("message", "已经发放分红，不允许重新发放!");
//					return modelAndView;
					err_has +=1;
					continue;
				}
				
				StringBuffer filter = new StringBuffer();
				filter.append("where ftype=1 \n");
				filter.append("and DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+sdf.format(fassetgt.getFlastupdatetime())+"' \n");
				filter.append("and fkey1 = '"+fassetgt.getFuser().getFid()+"' \n");
				
				List<Flog> list = this.logService.list(0, numPerPage, filter+"",true);
				if(list.size()<1){
					Double gtlock = 0d;
					String sql =  "where fvirtualcointype.fid=58 and fuser.fid="+fassetgt.getFuser().getFid();
					List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0,sql, false);
					if(all != null && all.size() == 1){
						gtlock = all.get(0).getFlocked();
					}
					if (gtlock>0){
						reward=Utils.getDouble((fassetgt.getFtotal()-gtlock)*fassetgt.getFtotalfee()/fassetgt.getFgtqty(), 4);
						if (reward<=0){
							err_nologin +=1;
							continue;
						}
					}
				}
				
				//GT分红，是直接发放USDT
				Fvirtualwallet walletInfo = this.frontUserService.findUSDTWalletByUser(fassetgt.getFuser().getFid());
				if (walletInfo == null) {
//					modelAndView.addObject("statusCode", 300);
//					modelAndView.addObject("message", "分红发放失败，会员钱包信息异常!");
//					return modelAndView;
					err_exception+=1;
					continue;
				}
				
				try {
					//发放分红到用户USDT账户
					walletInfo.setFtotal(walletInfo.getFtotal()+reward);
					walletInfo.setFlastUpdateTime(Utils.getTimestamp());
					
					
					//修改快照状态为已处理
					fassetgt.setStatus(true);
					
					//添加收益信息
					Fintrolinfo fintrolinfo = new Fintrolinfo() ;
					fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
					fintrolinfo.setFqty(reward) ;
					fintrolinfo.setFtitle(sdf.format(fassetgt.getFlastupdatetime())+"分红："+ reward +"USDT");
					fintrolinfo.setFuser(fassetgt.getFuser()) ;
					fintrolinfo.setFname("USDT");
					fintrolinfo.setFiscny(false);
					fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_GT_BONUS);
					
					//添加发放日志
					Freleaselog freleaselog = new Freleaselog();
					freleaselog.setFcreatetime(Utils.getTimestamp());
					freleaselog.setFqty(reward);
					freleaselog.setFreleasetype(2);
					freleaselog.setFuser(fassetgt.getFuser());
					freleaselog.setFvirtualcointype(walletInfo.getFvirtualcointype());
					
					this.introlinfoService.saveObj(fintrolinfo);
					this.snapGtService.updateObj(fassetgt);
					this.releaseLogService.saveObj(freleaselog);
					this.virtualWalletService.updateObj(walletInfo);
					err_succ+=1;
//					modelAndView.addObject("statusCode", 200);
//					modelAndView.addObject("message", "分红发放成功!");
//					return modelAndView ;
				}catch(Exception e){
					e.printStackTrace();
//					modelAndView.addObject("statusCode", 300);
//					modelAndView.addObject("message", "分红发放失败!");
//					return modelAndView ;
					err_fail+=1;
				}
			}
			
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "重复发放："+err_has+"个，已跳过；"+"钱包异常："+err_exception+"个；"+"发放失败："+err_fail+"个；成功发放："+err_succ+"个，未登陆500GT无奖励:"+err_nologin+"个");
			return modelAndView ;
		}
		//发放挖矿奖励
		@RequestMapping("/buluo718admin/sendEntrustMinerReward")
		public ModelAndView sendEntrustMinerReward(HttpServletRequest request) throws Exception{
			
			
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
			
			//获取分红
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fentrustminer fentrustminer = this.entrustMinerService.findById(fid);
			
			Double reward = Utils.getDouble(fentrustminer.getFreward(), 4);
			
			if (fentrustminer.getStatus() || reward<=0){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "已经发放挖矿奖励，不允许重新发放!");
				return modelAndView;
			}
			
			Fuser fuser = this.userService.findById(fentrustminer.getFuser().getFid());
			if(fuser.isFistiger()){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "操盘账户不进行挖矿奖励!");
				return modelAndView;
			}
			
			Fvirtualcointype fvcoin = this.virtualCoinService.findById(fentrustminer.getFcoin().getFid());
			
			
			//挖矿奖励，根据配置的币种进行发放
			Fvirtualwallet walletInfo = this.frontUserService.findVirtualWalletByUser(fentrustminer.getFuser().getFid(),fentrustminer.getFcoin().getFid());
			if (walletInfo == null) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "奖励发放失败，会员钱包信息异常!");
				return modelAndView;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				//发放分红到用户钱包账户
				
				walletInfo.setFtotal(walletInfo.getFtotal()+reward);
				walletInfo.setFlastUpdateTime(Utils.getTimestamp());
				
				//修改快照状态为已处理
				fentrustminer.setStatus(true);
				
				Fintrolinfo fintrolinfo = new Fintrolinfo() ;
				fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
				fintrolinfo.setFqty(reward) ;
				fintrolinfo.setFtitle(sdf.format(fentrustminer.getFlastupdatetime())+"获得挖矿奖励："+ reward +fvcoin.getfShortName());
				fintrolinfo.setFuser(fuser) ;
				fintrolinfo.setFname(fvcoin.getFname());
				fintrolinfo.setFiscny(false);
				fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_TRADE);
				
				this.entrustMinerService.updateMiner(walletInfo, fintrolinfo, fentrustminer);
				modelAndView.addObject("statusCode", 200);
				modelAndView.addObject("message", "挖矿奖励发放成功!");
				return modelAndView ;
			}catch(Exception e){
				e.printStackTrace();
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "挖矿奖励发放失败!");
				return modelAndView ;
			}
			
		}
		//发放GT分红
	@RequestMapping("/buluo718admin/sendEntrustMinerRewardBatch")
	public ModelAndView sendEntrustMinerRewardBatch(HttpServletRequest request) throws Exception{
		
		
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
		
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		int err_has = 0;
		int err_exception = 0;
		int err_fail = 0;
		int err_succ =0;
		int err_nologin =0 ;
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		
		//检测用户当天是否有登陆，没有登陆则不发放500GT锁仓分红
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		for(int i=0;i<idString.length;i++){
			int fid = Integer.parseInt(idString[i]);
			Fentrustminer fentrustminer = this.entrustMinerService.findById(fid);
			
			Double reward = Utils.getDouble(fentrustminer.getFreward(), 4);
			
			if (fentrustminer.getStatus() || reward<=0){
				err_has +=1;
				continue;
			}
			
			Fuser fuser = this.userService.findById(fentrustminer.getFuser().getFid());
			if(fuser.isFistiger()){
				err_nologin+=1;
				continue;
			}
			
			Fvirtualcointype fvcoin = this.virtualCoinService.findById(fentrustminer.getFcoin().getFid());
			
			Fvirtualwallet walletInfo = this.frontUserService.findVirtualWalletByUser(fentrustminer.getFuser().getFid(),fentrustminer.getFcoin().getFid());
			if (walletInfo == null) {
				err_exception+=1;
				continue;
			}
			
			try {
				//发放分红到用户USDT账户
				walletInfo.setFtotal(walletInfo.getFtotal()+reward);
				walletInfo.setFlastUpdateTime(Utils.getTimestamp());
				
				
				//修改快照状态为已处理
				fentrustminer.setStatus(true);
				
				Fintrolinfo fintrolinfo = new Fintrolinfo() ;
				fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
				fintrolinfo.setFqty(reward) ;
				fintrolinfo.setFtitle(sdf.format(fentrustminer.getFlastupdatetime())+"获得挖矿奖励："+ reward +fvcoin.getfShortName());
				fintrolinfo.setFuser(fuser) ;
				fintrolinfo.setFname(fvcoin.getFname());
				fintrolinfo.setFiscny(false);
				fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_TRADE);
				
				this.entrustMinerService.updateMiner(walletInfo, fintrolinfo, fentrustminer);
				err_succ+=1;
			}catch(Exception e){
				e.printStackTrace();
				err_fail+=1;
			}
		}
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重复发放："+err_has+"个，已跳过；"+"钱包异常："+err_exception+"个；"+"发放失败："+err_fail+"个；成功发放："+err_succ+"个，操盘手:"+err_nologin+"个");
		return modelAndView ;
	}
		
	//发放GT分红
		@RequestMapping("/buluo718admin/sendEntrustReward")
		public ModelAndView sendEntrustReward(HttpServletRequest request) throws Exception{
			
			
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
			
			if (frontConstantMapService.get("tradereward")==null){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "暂停交易奖励GT活动!");
				return modelAndView;
			}
			
			if (!frontConstantMapService.get("tradereward").toString().equals("1")){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "暂停交易奖励GT活动!");
				return modelAndView;
			}
			
			if (frontConstantMapService.get("gtid")==null){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "尚未设置需要发放GT代币的id!");
				return modelAndView;
			}
			int gtid = Integer.valueOf(frontConstantMapService.getString("gtid"));
			//获取分红
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fentrustsnap fentrustsnap = this.snapEntrustService.findById(fid);
			
			Double reward = fentrustsnap.getFreward();
			
			if (fentrustsnap.getStatus()){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "已经发放GT奖励，不允许重新发放!");
				return modelAndView;
			}
			Fuser fuser = this.userService.findById(fentrustsnap.getFuser().getFid());
			Fscore score = fuser.getFscore();
			if (fuser.isFistiger() || score.getFlevel()>5){
				if(fuser.getFid()!=9926){
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "特殊账户，不允许发放GT奖励!");
					return modelAndView;
				}
			}
			
			try {
				//GT奖励，是直接发放GT
				String sql =  "where fvirtualcointype.fid="+gtid+"and fuser.fid="+fentrustsnap.getFuser().getFid();
				List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0,sql, false);
				if(all != null && all.size() == 1){
					Fvirtualwallet walletInfo = all.get(0);
					//发放奖励到用户GT账户
					walletInfo.setFtotal(walletInfo.getFtotal()+reward);
					walletInfo.setFlastUpdateTime(Utils.getTimestamp());
					
					
					//修改快照状态为已处理
					fentrustsnap.setStatus(true);
					
					//添加收益信息
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Fintrolinfo fintrolinfo = new Fintrolinfo() ;
					fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
					fintrolinfo.setFqty(reward) ;
					fintrolinfo.setFtitle(sdf.format(fentrustsnap.getFlastupdatetime())+"获得GT奖励："+ reward +"GT");
					fintrolinfo.setFuser(fentrustsnap.getFuser()) ;
					fintrolinfo.setFname("G Token");
					fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_TRADE);
					fintrolinfo.setFiscny(false);
					
					//添加发放日志
					Freleaselog freleaselog = new Freleaselog();
					freleaselog.setFcreatetime(Utils.getTimestamp());
					freleaselog.setFqty(reward);
					freleaselog.setFreleasetype(2);
					freleaselog.setFuser(fentrustsnap.getFuser());
					freleaselog.setFvirtualcointype(walletInfo.getFvirtualcointype());
					
					//提交更新修改
					this.introlinfoService.saveObj(fintrolinfo);
					this.snapEntrustService.updateObj(fentrustsnap);
					this.releaseLogService.saveObj(freleaselog);
					this.virtualWalletService.updateObj(walletInfo);
					
					modelAndView.addObject("statusCode", 200);
					modelAndView.addObject("message", "GT奖励发放成功!");
					return modelAndView ;
				}else{
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "GT奖励发放失败，会员钱包信息异常!");
					return modelAndView;
	
				}
			}catch(Exception e){
				e.printStackTrace();
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "GT奖励发放失败!");
				return modelAndView ;
			}
			
		}
	
	@RequestMapping("/buluo718admin/goVirtualSnapGtJSP")
	public ModelAndView goVirtualSnapGtJSP(HttpServletRequest request) throws Exception{

		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fassetgt fassetgt = this.snapGtService.findById(fid);
			Fuser fuser = this.userService.findById(fassetgt.getFuser().getFid());
			modelAndView.addObject("fassetgt", fassetgt);
			modelAndView.addObject("fuser", fuser);
		}
		modelAndView.setViewName(url);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/snapEntrustIntroList")
	public ModelAndView snapEntrustIntroList(HttpServletRequest request) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/snapEntrustIntroList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 and status=1 and fintroUser!=null \n");
		if (!this.isSuper(request)){
			filter.append(" and fintroUser.fid<>7215 \n");
		}
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fintroUser.floginName like '%"+keyWord+"%' or \n");
			filter.append("fintroUser.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("fintroUser.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("fintroUser.frealName like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String logDate = request.getParameter("logDate");
		if(logDate == null || logDate.trim().length() ==0){
			Calendar cale = Calendar.getInstance();
			
			Date now = new Date();
			cale.setTime(now);
			cale.add(cale.DAY_OF_YEAR, -1);
			logDate = sdf.format(cale.getTime());
		}
		filter.append("and  DATE_FORMAT(flastupdatetime,'%Y-%m-%d') = '"+logDate+"' \n");
		modelAndView.addObject("logDate", logDate);
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		Double totalreward = this.adminService.getAllSum("Fentrustsnap", "famount/ftotalamount*fgtqty*0.3", " where fintroUser is not null and  DATE_FORMAT(flastupdatetime,'%Y-%m-%d') = '"+logDate+"'");
		List<Map> list = this.snapEntrustService.listIntro(sdf.parse(logDate));
		for(Map map:list){
			Fuser fintroUser = this.frontUserService.findById(Integer.valueOf(map.get("fiuid").toString()));
			map.put("fintroUser", fintroUser);
		}
		modelAndView.addObject("list", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "snapEntrustIntroList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",list.size());
		modelAndView.addObject("totalreward", totalreward);
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/entrustIntroReward")
	public ModelAndView entrustIntroReward(HttpServletRequest request) throws Exception{

		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/presale/entrustIntroAudit") ;
		
		int fid = Integer.parseInt(request.getParameter("uid"));
		
		
		String logDate = request.getParameter("logDate");
		if(logDate == null || logDate.trim().length() ==0){
			Calendar cale = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			cale.setTime(now);
			cale.add(cale.DAY_OF_YEAR, -1);
			logDate = sdf.format(cale.getTime());
		}
		
		boolean flag =false;
		List<Freleaselog> logs = this.releaseLogService.simplelist(0, maxResults, " where fuser.fid=" + fid +" and freleasetype=5 and (fcreatetime between '" +logDate+" 00:00:00' and '" +logDate+" 23:59:59')");
		if (logs.size()>0){
			flag =true;
		}
		
		Fuser fintroUser = null;
		fintroUser=this.frontUserService.findById(fid);
		
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append(" and status=1 and fintroUser.fid =" + fid + " \n");
		filter.append(" and (flastupdatetime between '" +logDate+" 00:00:00' and '" +logDate+" 23:59:59') \n");
		modelAndView.addObject("logDate", logDate);
		Double totalAmount = 0d;
		Double totalFee = 0d;
		Double introTotal = 0d;
		Double reward = 0d;
		List<Fentrustsnap> list = this.snapEntrustService.list(0, numPerPage, filter+"",false);
		if(list.size()>0){
			for(Fentrustsnap fentrustsnap:list){
				totalAmount = fentrustsnap.getFtotalamount();
				totalFee= fentrustsnap.getFtotalfee();
				introTotal+=fentrustsnap.getFamount();
			}
			reward = (introTotal/totalAmount)*30000;
		}
		
		
		modelAndView.addObject("totalAmount", totalAmount);
		modelAndView.addObject("totalFee", totalFee);
		modelAndView.addObject("introTotal", introTotal);
		modelAndView.addObject("reward", reward);
		modelAndView.addObject("fintroUser", fintroUser);
		modelAndView.addObject("list", list);
		modelAndView.addObject("flag", flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/auditEntrustIntro")
	public ModelAndView auditEntrustIntro(HttpServletRequest request) throws Exception{

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
		
		if (frontConstantMapService.get("tradereward")==null){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "暂停交易奖励GT活动!");
			return modelAndView;
		}
		
		if (!frontConstantMapService.get("tradereward").toString().equals("1")){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "暂停交易奖励GT活动!");
			return modelAndView;
		}
		
		if (frontConstantMapService.get("gtid")==null){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "尚未设置需要发放GT代币的id!");
			return modelAndView;
		}
		int gtid = Integer.valueOf(frontConstantMapService.getString("gtid"));
		
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		String confirmCode = request.getParameter("confirmcode");
		//校验审核码，审核码正确才能进行人民币审核操作
		String confirmCodeMD5 = Utils.MD5(confirmCode,admin.getSalt());
		if (!confirmCodeMD5.equals(admin.getFconfirmcode())) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，审核码错误，请仔细检查!");
			return modelAndView;
		}
		
		//比对奖励GT数量
		Double reward  = 0d;
		if (request.getParameter("reward")!=null){
			reward=Double.valueOf(request.getParameter("reward").toString());
		}
		
		if(reward<=0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "提交的奖励数量不能小于等于0!");
			return modelAndView;
		}
		
		int fid = Integer.parseInt(request.getParameter("uid"));
		
		String logDate = request.getParameter("logDate");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(logDate == null || logDate.trim().length() ==0){
			Calendar cale = Calendar.getInstance();
			Date now = new Date();
			cale.setTime(now);
			cale.add(cale.DAY_OF_YEAR, -1);
			logDate = sdf.format(cale.getTime());
		}
		
		boolean flag =false;
		List<Freleaselog> logs = this.releaseLogService.simplelist(0, maxResults, " where fuser.fid=" + fid +" and freleasetype=5 and (fcreatetime between '" +logDate+" 00:00:00' and '" +logDate+" 23:59:59')");
		if (logs.size()>0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "已经发放经纪人推荐奖励，请不要重新发放!");
			return modelAndView;
		}
		
		Fuser fintroUser = null;
		fintroUser=this.frontUserService.findById(fid);
		
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append(" and status=1 and fintroUser.fid =" + fid + " \n");
		filter.append(" and (flastupdatetime between '" +logDate+" 00:00:00' and '" +logDate+" 23:59:59') \n");
		modelAndView.addObject("logDate", logDate);
		Double totalAmount = 0d;
		Double totalFee = 0d;
		Double introTotal = 0d;
		Double newreward = 0d;
		List<Fentrustsnap> list = this.snapEntrustService.list(0, numPerPage, filter+"",false);
		if(list.size()>0){
			for(Fentrustsnap fentrustsnap:list){
				totalAmount = fentrustsnap.getFtotalamount();
				totalFee= fentrustsnap.getFtotalfee();
				introTotal+=fentrustsnap.getFamount();
			}
			newreward = (introTotal/totalAmount)*30000;
		}else{
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "经纪人当日没有推荐交易量，请仔细检查!");
			return modelAndView;
		}
		
		if(!reward.equals(newreward)){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "提交的奖励GT数量审核有误，请仔细检查!");
			return modelAndView;
		}
		
		try {
			//GT奖励，是直接发放GT
			String sql =  "where fvirtualcointype.fid="+gtid+"and fuser.fid="+fid;
			List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0,sql, false);
			if(all != null && all.size() == 1){
				Fvirtualwallet walletInfo = all.get(0);
				//发放分红到用户USDT账户
				walletInfo.setFtotal(walletInfo.getFtotal()+reward);
				walletInfo.setFlastUpdateTime(Utils.getTimestamp());
				
				//添加收益信息
				Fintrolinfo fintrolinfo = new Fintrolinfo() ;
				fintrolinfo.setFcreatetime(Utils.getTimestamp()) ;
				fintrolinfo.setFqty(reward) ;
				fintrolinfo.setFtitle(logDate+"获得经纪人推荐奖励："+ reward +"GT");
				fintrolinfo.setFuser(fintroUser) ;
				fintrolinfo.setFname("GT");
				fintrolinfo.setFtype(IntrolInfoTypeEnum.INTROL_TRADE_INTROL);
				fintrolinfo.setFiscny(false);
				
				//添加发放日志
				Freleaselog freleaselog = new Freleaselog();
				freleaselog.setFcreatetime(Timestamp.valueOf(logDate+" 23:59:59"));
				freleaselog.setFqty(reward);
				freleaselog.setFreleasetype(2);
				freleaselog.setFuser(fintroUser);
				freleaselog.setFvirtualcointype(walletInfo.getFvirtualcointype());
				freleaselog.setFreleasetype(5);
				//提交更新修改
				this.introlinfoService.saveObj(fintrolinfo);
				this.releaseLogService.saveObj(freleaselog);
				this.virtualWalletService.updateObj(walletInfo);
				
				modelAndView.addObject("statusCode", 200);
				modelAndView.addObject("message", "经纪人推荐奖励GT发放成功!");
				modelAndView.addObject("callbackType","closeCurrent");
				return modelAndView ;
			}else{
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "经纪人推荐奖励GT奖励发放失败，会员钱包信息异常!");
				return modelAndView;

			}
		}catch(Exception e){
			e.printStackTrace();
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "GT奖励发放失败!");
			return modelAndView ;
		}
		
	}
	
}
