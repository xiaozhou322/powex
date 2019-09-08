package com.gt.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.CountLimitTypeEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FfeesConvert;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Constant;
import com.gt.util.PaginUtil;
import com.gt.util.Utils;

import net.sf.json.JSONObject;
/**
 * 
 * 手续费兑换处理controller(USDT兑换BFSC)
 * @author zhouy
 *
 */
@Controller
public class FrontFeesConvertController extends BaseController {
	
	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	
	/**
	 * 查询项目方手续费兑换列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/feesConvert/queryFeesConvertList")
	public ModelAndView queryFeesConvertList(HttpServletRequest request)throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("redirect:/index.html") ;
		if(this.isMobile(request)) {
			int currentPage = 1;
			int numPerPage=20;
			if(this.isMobile(request)){
				numPerPage = maxResults;
			}
			if(request.getParameter("currentPage") != null){
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			
			if(request.getParameter("numPerPage") != null){
				numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
			}
			if(GetSession(request).getFid()==366665) {
				StringBuffer filter = new StringBuffer();
				filter.append(" where projectId.fid="+GetSession(request).getFid());
				filter.append(" ORDER BY id desc,createTime desc \n");
				
				List<FfeesConvert> list = this.frontOthersService.queryFfeesConvertList((currentPage-1)*numPerPage, numPerPage, filter+"",true);
				int totalCount = this.adminService.getAllCount("FfeesConvert", filter.toString());
				int totalPage = totalCount/numPerPage +(totalCount%numPerPage==0?0:1) ;
				String pagin = PaginUtil.generatePagin(totalPage, currentPage,  "/feesConvert/queryFeesConvertList.html?") ;
				modelAndView.addObject("pagin", pagin);
				modelAndView.addObject("list", list);
				modelAndView.addObject("totalCount", totalCount) ;	
				modelAndView.addObject("numPerPage", numPerPage);
				modelAndView.addObject("currentPage", currentPage);
			
			
				modelAndView.addObject("totalPage", totalPage) ;
				modelAndView.setViewName("mobile/feesconvert/feesconvert") ;
				if(currentPage>1)//ajax 加载
				{
					modelAndView.setViewName("mobile/feesconvert/feesconvert_ajax") ;
				}
			}
 	    }
		return modelAndView;
	}
	
	
	/**
	 * 处理手续费兑换订单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/feesConvert/handleFeesConvert")
	public String handleFeesConvert(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		
		try {
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
			if(null != fuser && !fuser.isFisprojecter()) {
				jsonObject.accumulate("code", -5) ;
				jsonObject.accumulate("msg", "您不是项目方，不允许操作") ;
				return jsonObject.toString() ;
			}
			
			if(fuser.getFid()!=366665) {
				jsonObject.accumulate("code", -6) ;
				jsonObject.accumulate("msg", "非指定项目方，不允许操作") ;
				return jsonObject.toString() ;
			}
			
			String ip = getIpAddr(request) ;
			
			int id = Integer.valueOf(request.getParameter("id"));
			String tradePwd = request.getParameter("tradePwd").trim();
			
			if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
				jsonObject.accumulate("code", -2) ;
				jsonObject.accumulate("msg","请先设置交易密码") ;
				return jsonObject.toString() ;
			}
			
			int trade_limit = frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			if(trade_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC,"交易密码校验"+"：频繁密码验证");
				return jsonObject.toString() ;
			}
			
			try {
				if(!fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt()))){
					jsonObject.accumulate("code", -3) ;
					Object[] params = new Object[]{trade_limit};
					jsonObject.accumulate("msg", "交易密码有误，您还有"+trade_limit+"次机会");
					frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					return jsonObject.toString() ;
				}else if(trade_limit<Constant.ErrorCountLimit){
					frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonObject.accumulate("code", -3) ;
				Object[] params = new Object[]{trade_limit};
				jsonObject.accumulate("msg", "交易密码有误，您还有"+trade_limit+"次机会");
				frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				return jsonObject.toString() ; 
			}
			
			FfeesConvert feesConvert = this.frontOthersService.findFeesConvertById(id);
			//获取BFSC币种信息
			List<Fvirtualcointype> bfscCoinTypeList = frontVirtualCoinService.findByProperty("fShortName", "BFSC");
			Fvirtualcointype bfscCoinType = null;
			if(null != bfscCoinTypeList && bfscCoinTypeList.size() > 0) {
				bfscCoinType = bfscCoinTypeList.get(0);
			} else {
				jsonObject.accumulate("code", -4) ;
				jsonObject.accumulate("msg", "BFSC币种不存在") ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC,"交易密码校验"+"：频繁密码验证");
				return jsonObject.toString() ;
			}
			
			List<Fvirtualcointype> usdtCoinTypeList = frontVirtualCoinService.findByProperty("fShortName", "USDT");
			
			//获取用户BFSC钱包
			Fvirtualwallet bfscWallet = this.frontUserService.
					findVirtualWalletByUser(feesConvert.getProjectId().getFid(), bfscCoinType.getFid());
			
			//获取用户USDT钱包
			Fvirtualwallet usdtWallet = this.frontUserService.
					findVirtualWalletByUser(feesConvert.getProjectId().getFid(), usdtCoinTypeList.get(0).getFid());
			
			//更新钱包信息和手续费兑换记录
			frontOthersService.updateFfeesConvertAndWallet(usdtWallet, bfscWallet, feesConvert);
			
			jsonObject.accumulate("code" , 0) ;
			jsonObject.accumulate("msg", "处理成功") ;
			
		} catch (Exception e) {
			e.printStackTrace() ;
			jsonObject.accumulate("code", -10000);
			jsonObject.accumulate("msg", "系统异常，操作失败");
		}
		return jsonObject.toString();
	}
	
	
	
}
