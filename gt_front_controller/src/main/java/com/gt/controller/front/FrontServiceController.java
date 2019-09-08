package com.gt.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gt.controller.BaseController;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.entity.Pdomain;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ArticleService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.util.PaginUtil;



@Controller
public class FrontServiceController extends BaseController {

	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private AdminService adminService ;
	
	@RequestMapping("/service/ourService")
	public ModelAndView ourService(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="2")int id,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception{//12,5,5
		ModelAndView modelAndView = new ModelAndView() ;
		
		String lcal = RequestContextUtils.getLocale(request).toString();
		
		Farticletype type = this.frontOthersService.findFarticleTypeById(id);
		if(type == null){
			id = 2;
		}
		
		List<Farticletype> articletypes = this.frontOthersService.findFarticleTypeAll();
		if (lcal.equals("zh_CN")){
			for (Farticletype fartt:articletypes){
				fartt.setFname(fartt.getFname_cn());
			}
			type.setFname(type.getFname_cn());
		}
		modelAndView.addObject("articletypes",articletypes) ;
		
		int page =9;
		int total =1;
		Pdomain pdomain = getReqDomain(request);
		List<Farticle> farticles = new ArrayList<Farticle>();
		//项目方域名展示官方公告
			if(id==1&&null!=pdomain){
				farticles = this.frontOthersService.findProjectFarticle(pdomain.getProjectId().getFid(), id,(currentPage-1)*page, page);
				total = this.frontOthersService.findProjectFarticleCount(pdomain.getProjectId().getFid(), id);
			}else{
//				farticles = this.frontOthersService.findFarticle(id, (currentPage-1)*page, page) ;
//				total = this.frontOthersService.findFarticleCount(id) ;
				
				StringBuffer filter = new StringBuffer();
				filter.append(" where farticletype.fid="+ id);
				filter.append(" and projectId.fid= null or projectId.fid= 0 order by fisding desc,id desc ");
				farticles = this.articleService.list((currentPage-1)*page, page, filter+"", true);
				total = this.adminService.getAllCount("Farticle", filter+"");
			}
		
		String pagin = PaginUtil.generatePagin(total/page+(total%page==0?0:1), currentPage, "/service/ourService.html?id="+id+"&") ;
		if (lcal.equals("zh_CN")){
			for (Farticle fart:farticles){
				fart.setFtitle(fart.getFtitle_cn());
				fart.setFcontent(fart.getFcontent_cn());
			}
		}
		
		
		
		String filter = "where farticletype.fid="+id+" order by fid desc";
		List<Farticle> hotsArticles = this.articleService.list(0, 6, filter, true);
		
		if (lcal.equals("zh_CN")){
			for (Farticle fart:hotsArticles){
				fart.setFtitle(fart.getFtitle_cn());
				fart.setFcontent(fart.getFcontent_cn());
			}
		}
		
		modelAndView.addObject("hotsArticles", hotsArticles) ;
		
		modelAndView.addObject("id",id) ;
		/*modelAndView.addObject("isIndex", 1) ;*/
		modelAndView.addObject("pagin",pagin) ;
		modelAndView.addObject("type",type.getFname()) ;
		modelAndView.addObject("farticles", farticles) ;
		modelAndView.setViewName("front/service/index") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/service/index") ;
		}
		
		return modelAndView ;
	}
	
	
	
	
	@RequestMapping("/service/article")
	public ModelAndView article(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int id
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		String lcal = RequestContextUtils.getLocale(request).toString();
		
		Farticle farticle = this.frontOthersService.findFarticleById(id) ;
		if(farticle == null){
			modelAndView.setViewName("redirect:/service/ourService.html") ;
			return modelAndView;
		}
//		farticle.setFcount(farticle.getFcount()+1);
//		try {
//			this.articleService.updateObj(farticle);
//		} catch (Exception e) {}
		
		if (lcal.equals("zh_CN")){
			farticle.setFtitle(farticle.getFtitle_cn());
			farticle.setFcontent(farticle.getFcontent_cn());
		}
		
		List<Farticletype> articletypes = this.frontOthersService.findFarticleTypeAll();
		if (lcal.equals("zh_CN")){
			for (Farticletype fartt:articletypes){
				fartt.setFname(fartt.getFname_cn());
			}
		}
		modelAndView.addObject("articletypes",articletypes) ;
		
		modelAndView.addObject("farticle", farticle) ;
		modelAndView.addObject("typeid",farticle.getFarticletype().getFid()) ;
		if (lcal.equals("zh_CN")){
			modelAndView.addObject("type",farticle.getFarticletype().getFname_cn()) ;
		}else{
			modelAndView.addObject("type",farticle.getFarticletype().getFname()) ;
		}
		
		//根据域名展示对应的项目方公告
		Pdomain pdomain = getReqDomain(request);
		//String filter = "where farticletype.fid="+farticle.getFarticletype().getFid()+" order by fid desc";
		String filter = null;
		if(null != pdomain){
			filter = "where farticletype.fid="+farticle.getFarticletype().getFid()+" and projectId.fid="+pdomain.getProjectId().getFid()+" order by fid desc";
		}else{
			filter = "where farticletype.fid="+farticle.getFarticletype().getFid()+" order by fid desc";
		}
		
		List<Farticle> hotsArticles = this.articleService.list(0, 6, filter, true);
		if (lcal.equals("zh_CN")){
			for (Farticle fart:hotsArticles){
				fart.setFtitle(fart.getFtitle_cn());
				fart.setFcontent(fart.getFcontent_cn());
			}
		}
		modelAndView.addObject("hotsArticles", hotsArticles) ;
		modelAndView.setViewName("front/service/article") ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/service/article") ;
		}
		
		return modelAndView ;
	}
	
	@RequestMapping("/service/activity")
	public ModelAndView activity(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="") String code
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(code == null || code.equals("")){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		String allcode = this.frontConstantMapService.get("allcode").toString().trim();
		if (allcode==null || allcode.equals("") || !allcode.contains("#"+code+"#")){
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
		modelAndView.setViewName("front/diypage/"+code) ;
		
		if(this.isMobile(request))
		{
			modelAndView.setViewName("mobile/diypage/"+code) ;
		}
		
		return modelAndView ;
	}
}
