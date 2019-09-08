package com.gt.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gt.Enum.AutotradeStatusEnum;
import com.gt.Enum.SynTypeEnum;
import com.gt.Enum.TradeTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fautotrade;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.AutotradeService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.UserService;
import com.gt.util.Utils;

@Controller
public class AutotradeController extends BaseAdminController {
	@Autowired
	private AutotradeService autotradeService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	@Autowired
	private TradeMappingService tradeMappingService;
	
	@RequestMapping("/buluo718admin/autotradeList")
	public ModelAndView autotradeList(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/autotradeList") ;
		//当前页
		int currentPage = 1;
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");

		List<Fautotrade> list = this.autotradeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("autotradeList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "autotradeList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fautotrade", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goAutotradeJSP")
	public ModelAndView goAutotradeJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fautotrade fautotrade = this.autotradeService.findById(fid);
			modelAndView.addObject("fautotrade", fautotrade);
		}
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, TradeTypeEnum.getEnumString(1));
		map.put(2, TradeTypeEnum.getEnumString(2));
		modelAndView.addObject("map", map);
		
		String sql = "where fstatus=1";
		List<Ftrademapping> allType = this.tradeMappingService.list(0, 0, sql, false);
		modelAndView.addObject("allType", allType);
		
		Map<Integer, String> typemap = new HashMap<Integer, String>();
		typemap.put(0, SynTypeEnum.getEnumString(0));
		typemap.put(1, SynTypeEnum.getEnumString(1));
		typemap.put(2, SynTypeEnum.getEnumString(2));
		modelAndView.addObject("typemap", typemap);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveAutotrade")
	public ModelAndView saveAutotrade(HttpServletRequest request) throws Exception{
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
        Fautotrade autotrade = new Fautotrade();
        int userid = Integer.parseInt(request.getParameter("fuserid"));
        List<Fuser> fusers = this.userService.list(0, 0, "where fid="+userid, false);
		if(fusers == null || fusers.size() == 0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","用户不存在");
			return modelAndView;
		}
		
		autotrade.setFsynType(Integer.parseInt(request.getParameter("fsynType")));

		autotrade.setFstatus(AutotradeStatusEnum.NORMAL);
		autotrade.setFuser(fusers.get(0));
		autotrade.setFcreatetime(Utils.getTimestamp());
		autotrade.setFmaxprice(Double.valueOf(request.getParameter("fmaxprice")));
		autotrade.setFminprice(Double.valueOf(request.getParameter("fminprice")));
		autotrade.setFmaxqty(Double.valueOf(request.getParameter("fmaxqty")));
		autotrade.setFminqty(Double.valueOf(request.getParameter("fminqty")));
		autotrade.setFtype(Integer.parseInt(request.getParameter("ftype")));
		autotrade.setFtimes(Integer.parseInt(request.getParameter("ftimes")));
		autotrade.setFstoptimes(Integer.parseInt(request.getParameter("fstoptimes")));
		int vid = Integer.parseInt(request.getParameter("vid"));
		Ftrademapping trademapping = this.tradeMappingService.findById(vid);
		autotrade.setFtrademapping(trademapping);
		this.autotradeService.saveObj(autotrade);

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateAutotrade")
	public ModelAndView updateAutotrade(HttpServletRequest request) throws Exception{
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
		int uid = Integer.parseInt(request.getParameter("uid"));
        Fautotrade autotrade = this.autotradeService.findById(uid);
        int userid = Integer.parseInt(request.getParameter("fuserid"));
        List<Fuser> fusers = this.userService.list(0, 0, "where fid="+userid, false);
		if(fusers == null || fusers.size() == 0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","用户不存在");
			return modelAndView;
		}
		
		autotrade.setFsynType(Integer.parseInt(request.getParameter("fsynType")));
		
		autotrade.setFstatus(AutotradeStatusEnum.NORMAL);
		autotrade.setFuser(fusers.get(0));
		autotrade.setFcreatetime(Utils.getTimestamp());
		autotrade.setFmaxprice(Double.valueOf(request.getParameter("fmaxprice")));
		autotrade.setFminprice(Double.valueOf(request.getParameter("fminprice")));
		autotrade.setFmaxqty(Double.valueOf(request.getParameter("fmaxqty")));
		autotrade.setFminqty(Double.valueOf(request.getParameter("fminqty")));
		autotrade.setFtype(Integer.parseInt(request.getParameter("ftype")));
		autotrade.setFtimes(Integer.parseInt(request.getParameter("ftimes")));
		autotrade.setFstoptimes(Integer.parseInt(request.getParameter("fstoptimes")));
		int vid = Integer.parseInt(request.getParameter("vid"));
		Ftrademapping trademapping = this.tradeMappingService.findById(vid);
		autotrade.setFtrademapping(trademapping);
		this.autotradeService.updateObj(autotrade);

		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteAutotrade")
	public ModelAndView deleteAutotrade(HttpServletRequest request) throws Exception{
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
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.autotradeService.deleteObj(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/doAutotrade")
	public ModelAndView doAutotrade(HttpServletRequest request) throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		int type = Integer.parseInt(request.getParameter("type"));
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
		Fautotrade autotrade = this.autotradeService.findById(fid);
		if(type == 1){
			autotrade.setFstatus(AutotradeStatusEnum.FORBIN);
		}else{
			autotrade.setFstatus(AutotradeStatusEnum.NORMAL);
		}
		
		this.autotradeService.updateObj(autotrade);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","操作成功");
		return modelAndView;
	}
}
