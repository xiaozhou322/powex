package com.gt.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.controller.BaseController;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.IntrolinfoService;
import com.gt.service.admin.UserService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Constant;
import com.gt.util.PaginUtil;

@Controller
public class FrontDivideController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private IntrolinfoService introlinfoService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	
	
	
	@RequestMapping("/introl/mydivide")
	public ModelAndView introl(
			HttpServletRequest request,
			@RequestParam(required=true,defaultValue="1")int type,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String url = Constant.Domain+"?";
/*		if(fuser.getFregfrom() != null && fuser.getFregfrom().equals("agon")){
			url = url+"agon&";
		}
		if(fuser.getFuserNo() != null && fuser.getFuserNo().trim().length() >0){
			url = url+"sn="+fuser.getFuserNo().trim() +"&";
		}*/
		
		url = url+"invite_code="+fuser.getFuserNo();
		
		modelAndView.addObject("spreadLink", url) ;
		modelAndView.addObject("FuserNo", fuser.getFuserNo()) ;
		
		String filter = "where fIntroUser_id.fid="+fuser.getFid()+" order by fid desc";
		
		List<Fuser> fusers = this.userService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
		int introTotal=0;
		for(Fuser fu:fusers){
			introTotal=introTotal+fu.getfInvalidateIntroCount();
		}
		modelAndView.addObject("introTotal", introTotal) ;
		modelAndView.addObject("fusers", fusers) ;
		int total = adminService.getAllCount("Fuser","");
		Double firsttranslation = (double)(Math.round(fuser.getfInvalidateIntroCount()*100)/(total*1.0)) ;
		Double twotranslation = (double)(Math.round(introTotal*100)/(total*1.0)) ;
		modelAndView.addObject("fristtotal", fuser.getfInvalidateIntroCount()) ;
		modelAndView.addObject("firsttranslation", firsttranslation) ;
		modelAndView.addObject("twotranslation", twotranslation) ;
		if(type == 1){
	
			int total1 = adminService.getAllCount("Fuser",filter);
			int totalPage = total1/Constant.RecordPerPage + ((total1%Constant.RecordPerPage) ==0?0:1) ;
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/mydivide.html?type=1&") ;
			
			modelAndView.addObject("total", total1) ;
			
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front/introl/index") ;
			
			if(this.isMobile(request))
			{
				modelAndView.setViewName("mobile/introl/index") ;
			}
		}else if(type ==2){
			filter = "where fuser.fid="+fuser.getFid()+" and ftype in ("+IntrolInfoTypeEnum.INTROL_REG+","+IntrolInfoTypeEnum.INTROL_INTROL+") order by fid desc";
			int introlinfoTotal = this.adminService.getAllCount("Fintrolinfo", filter);
			int totalPage = introlinfoTotal/Constant.RecordPerPage + ((introlinfoTotal%Constant.RecordPerPage)  ==0?0:1) ;
			List<Fintrolinfo> fintrolinfos = this.introlinfoService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/mydivide.html?type=2&") ;
			
			modelAndView.addObject("fintrolinfos", fintrolinfos) ;
			modelAndView.addObject("IntrolInfoTypeEnum", IntrolInfoTypeEnum.getEnumMap()) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front/introl/index2") ;
			
			if(this.isMobile(request))
			{
				modelAndView.setViewName("mobile/introl/index2") ;
			}
		}
		modelAndView.addObject("type", type) ;
		
		return modelAndView ;
	}
	
	
	
	
	@RequestMapping("/introl/redPocket")
	public ModelAndView redPocket(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

			String filter = "where fuser.fid="+fuser.getFid()+" and ftype in ("+IntrolInfoTypeEnum.INTROL_AIRDROP+","+IntrolInfoTypeEnum.INTROL_CANDY+","+IntrolInfoTypeEnum.INTROL_RED_ENVELOPE+","+IntrolInfoTypeEnum.INTROL_LOTTERY+") order by fid desc";
			int total = this.adminService.getAllCount("Fintrolinfo", filter);
			int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage)  ==0?0:1) ;
			List<Fintrolinfo> fintrolinfos = this.introlinfoService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			
			
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/redPocket.html?type=2&") ;
			modelAndView.addObject("IntrolInfoTypeEnum", IntrolInfoTypeEnum.getEnumMap()) ;
			modelAndView.addObject("fintrolinfos", fintrolinfos) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front/introl/redPocket") ;
			
			if(this.isMobile(request))
			{
				modelAndView.setViewName("mobile/introl/redPocket") ;
			}

		
		return modelAndView ;
	
}
	
	@RequestMapping("/introl/tradingRewards")
	public ModelAndView tradingRewards(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

			String filter = "where fuser.fid="+fuser.getFid()+" and ftype in ("+IntrolInfoTypeEnum.INTROL_TRADE+","+IntrolInfoTypeEnum.INTROL_TRADE_INTROL+","+IntrolInfoTypeEnum.INTROL_RECHARHE+") order by fid desc";
			int total = this.adminService.getAllCount("Fintrolinfo", filter);
			int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage)  ==0?0:1) ;
			List<Fintrolinfo> fintrolinfos = this.introlinfoService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			
			
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/tradingRewards.html?type=2&") ;
			modelAndView.addObject("IntrolInfoTypeEnum", IntrolInfoTypeEnum.getEnumMap()) ;
			modelAndView.addObject("fintrolinfos", fintrolinfos) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front/introl/tradingRewards") ;
			
			if(this.isMobile(request))
			{
				modelAndView.setViewName("mobile/introl/tradingRewards") ;
			}

		
		return modelAndView ;
	
	}
	
	
	
	/**
	 * 跳转到邀请好友页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/introl/introlIndex")
	public ModelAndView introlIndex(HttpServletRequest request) throws Exception{
		ModelAndView  modelAndView=new ModelAndView();
		
		Fuser user = GetSession(request);
		if(null != user) {
			Fuser fuser = this.frontUserService.findById(user.getFid()) ;
			String url = Constant.Domain+"?";
			
			url = url+"invite_code="+fuser.getFuserNo();
			//邀请链接
			modelAndView.addObject("spreadLink", url) ;
			//邀请码
			modelAndView.addObject("fuserNo", fuser.getFuserNo()) ;
			
			String filter = "where fIntroUser_id.fid="+fuser.getFid()+" order by fid desc";
			
			List<Fuser> fusers = this.userService.list(0, 0,filter,false) ;
			int introTotal=0;
			for(Fuser fu:fusers){
				introTotal=introTotal+fu.getfInvalidateIntroCount();
			}
			modelAndView.addObject("introTotal", introTotal) ;
			modelAndView.addObject("fusers", fusers) ;
			int total = adminService.getAllCount("Fuser","");
			Double firsttranslation = (double)(Math.round(fuser.getfInvalidateIntroCount()*100)/(total*1.0)) ;
			Double twotranslation = (double)(Math.round(introTotal*100)/(total*1.0)) ;
			modelAndView.addObject("fristtotal", fuser.getfInvalidateIntroCount()) ;
			modelAndView.addObject("firsttranslation", firsttranslation) ;
			modelAndView.addObject("twotranslation", twotranslation) ;
			
			String[] introlSendCoin = frontConstantMapService.getString("introlSendCoin").split("#");
			int coinIDIntro = Integer.parseInt(introlSendCoin[0]);
			double coinQtyIntro = Double.valueOf(introlSendCoin[1]);
			Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinIDIntro);
			//推荐奖励金
			double introReward = fuser.getfInvalidateIntroCount()*coinQtyIntro;
			String rewardCoin = "POW";
			if(null != fvirtualcointype) {
				rewardCoin = fvirtualcointype.getfShortName();
			}
			modelAndView.addObject("introReward", introReward) ;
			modelAndView.addObject("rewardCoin", rewardCoin) ;
			
		}
		
		
		modelAndView.setViewName("front/introl/introlIndex") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/introl/introlIndex") ;
		}
		return modelAndView;
	}
}