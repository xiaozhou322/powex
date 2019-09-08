package com.gt.controller.front;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CapitalOperationInStatus;
import com.gt.Enum.CapitalOperationTypeEnum;
import com.gt.Enum.FactivityStatusEnum;
import com.gt.Enum.FactivityTypeEnum;
import com.gt.Enum.FactivityWayEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FactivityModel;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.LotteryAwardsModel;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontAccountService;
import com.gt.service.front.FrontActivityService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontLotteryAwardsService;
import com.gt.service.front.FrontLotteryLogService;
import com.gt.service.front.FrontLotteryPeriodsService;
import com.gt.service.front.FrontLotteryRecordService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FvirtualWalletService;


@Controller
public class FrontLuckydrawController extends BaseController {
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
	private FrontLotteryRecordService frontLotteryRecordService;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FrontUserService frontUserService ;
	/**
	 * 跳转到抽奖页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/luckydraw/luckydrawIndex")
	public ModelAndView luckydrawIndex(HttpServletRequest request) throws Exception{
		ModelAndView  modelAndView=new ModelAndView();
		Fuser user=frontUserService.findById(GetSession(request).getFid());
		String filter=" where type="+FactivityTypeEnum.disposable+" and way="+FactivityWayEnum.lottery+" and status="+FactivityStatusEnum.open;
		if(user!=null){
			List<FactivityModel> factivityModelList=frontActivitiesService.getlist(0, 0, filter, false);
			if(factivityModelList!=null&&factivityModelList.size()>0){
				FactivityModel factivityModel=factivityModelList.get(0);
				modelAndView.addObject("factivityModel", factivityModel);
				List<LotteryAwardsModel> awardslist=frontLotteryAwardsService.findByProperty("factivityModel.id", factivityModel.getId());
				Fvirtualwallet fvirtualwallet=fvirtualWalletService.findFvirtualwallet(user.getFid(), factivityModel.getFvirtualcointype().getFid());
				Map<Integer,String> factivityStatusEnum=FactivityStatusEnum.getEnumMap();
				modelAndView.addObject("fvirtualwallet", fvirtualwallet);
				modelAndView.addObject("factivityStatusEnum", factivityStatusEnum);
				modelAndView.addObject("awardslist", awardslist);
			}
			
			int countBTC=adminService.getAllCount("Fvirtualcaptualoperation", " where  fuser.fid="+user.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_IN+" and fstatus="+VirtualCapitalOperationInStatusEnum.SUCCESS);
			int countUSDT=adminService.getAllCount("Fcapitaloperation", " where  fuser.fid="+user.getFid()+" and ftype="+CapitalOperationTypeEnum.USDT_IN+"  and fstatus="+CapitalOperationInStatus.Come);
			modelAndView.addObject("ftradePassword", user.getFtradePassword());
			if(countBTC<=0&&countUSDT<=0){
				modelAndView.addObject("isFristTrade", true);
			}else{
				modelAndView.addObject("isFristTrade", false);
			}
				
			
		}
		
		
		
	
		modelAndView.setViewName("front/luckydraw/lottery") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/luckydraw/lottery") ;
		}
		return modelAndView;
	}
	
	
	
	/**
	 * 跳转到社群
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/community/communityIndex")
	public ModelAndView communityIndex(HttpServletRequest request) throws Exception{
		ModelAndView  modelAndView=new ModelAndView();
		modelAndView.setViewName("front/luckydraw/community") ;
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/luckydraw/community") ;
		}
		return modelAndView;
	}
	
	
	
	/**
	 * 跳转到项目账号页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/projects/projectsIndex")
	public ModelAndView projectsIndex(HttpServletRequest request) throws Exception{
		ModelAndView  modelAndView=new ModelAndView();
		modelAndView.setViewName("front/luckydraw/project") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/luckydraw/project") ;
		}
		return modelAndView;
	}
	
	
	
	/**
	 * 跳转到10000ETH活动的首页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lucky/luckyIndex")
	public ModelAndView luckyIndex(HttpServletRequest request) throws Exception{
		ModelAndView  modelAndView=new ModelAndView();
		
		String awardsStartTime = frontConstantMapService.get("awardsStartTime").toString();
		modelAndView.addObject("awardsStartTime", awardsStartTime) ;
		//int usertotal = adminService.getAllCount("Fuser","");
		double totalfwalle=adminService.getSQLValue("select sum(ftotal+ffrozen) from fvirtualwallet where fVi_fId=5");
		int usertotal=frontUserService.getMaxId();
		modelAndView.addObject("totalfwalle", totalfwalle) ;		
		modelAndView.addObject("usertotal", usertotal) ;
		
		modelAndView.setViewName("front/luckydraw/index") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/luckydraw/index") ;
		}
		return modelAndView;
	}
	
	
	
	/**
	 * 关于平台
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/platform/platformIndex")
	public ModelAndView platformIndex(HttpServletRequest request) throws Exception{
		ModelAndView  modelAndView=new ModelAndView();
		modelAndView.setViewName("front/luckydraw/platform") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/luckydraw/platform") ;
		}
		return modelAndView;
	}
	
	
	
	
}
