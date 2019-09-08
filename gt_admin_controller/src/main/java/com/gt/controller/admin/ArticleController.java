package com.gt.controller.admin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gt.comm.ParamArray;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ArticleService;
import com.gt.service.admin.ArticleTypeService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.util.Constant;
import com.gt.util.HttpUtils;
import com.gt.util.OSSPostObject;
import com.gt.util.Utils;

@Controller
public class ArticleController extends BaseAdminController {
	@Autowired
	private ArticleService articleService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private ArticleTypeService articleTypeService ;
	@Autowired
	private FrontOthersService frontOtherService;
	/*@Autowired
	private ConstantMap map;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/articleList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/articleList") ;
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
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fTitle like '%"+keyWord+"%' OR \n");
			filter.append("fkeyword like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and farticletype.fid="+request.getParameter("ftype")+"\n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fcreateDate \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		Map typeMap = new HashMap();
		typeMap.put(0, "全部");
		List<Farticletype> all = this.articleTypeService.findAll();
		for (Farticletype farticletype : all) {
			typeMap.put(farticletype.getFid(), farticletype.getFname());
		}
		modelAndView.addObject("typeMap", typeMap);
		
		List<Farticle> list = this.articleService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("articleList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "articleList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Farticle", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goArticleJSP")
	public ModelAndView goArticleJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Farticle article = this.articleService.findById(fid);
			modelAndView.addObject("farticle", article);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/buluo718admin/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,ParamArray param) throws Exception{
		MultipartFile multipartFile = param.getFiledata() ;
		InputStream inputStream = multipartFile.getInputStream() ;
		String realName = multipartFile.getOriginalFilename() ;
		
		if(realName!=null && realName.trim().toLowerCase().endsWith("jsp")){
			return "" ;
		}
		
		String[] nameSplit = realName.split("\\.") ;
		String ext = nameSplit[nameSplit.length-1] ;
		String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
		String fileName = Utils.getRandomImageName()+"."+ext;
		boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
		String result = "";
		if(!flag){
			result = "上传失败";
		}
		JSONObject resultJson = new JSONObject() ;
		resultJson.accumulate("err",result) ;
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		if(Constant.IS_OPEN_OSS.equals("false")){
			resultJson.accumulate("msg","/"+Constant.uploadPicDirectory+"/"+fileName) ;
		}else{
			resultJson.accumulate("msg",OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName) ;
		}
		
		return resultJson.toString();
	}
	
	@RequestMapping("/buluo718admin/saveArticle")
	public ModelAndView saveArticle(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String fKeyword,
			@RequestParam("articleLookup.id") int articleLookupid,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String ftitle_cn,
			@RequestParam(required=false) String fcontent_cn
			) throws Exception{
		Farticle article = new Farticle();
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
		Farticletype articletype = this.articleTypeService.findById(articleLookupid);
		article.setFarticletype(articletype);
		article.setFtitle(ftitle);
		article.setFtitle_cn(ftitle_cn);
		article.setFkeyword(fKeyword);
/*		article.setFoutUrl(foutUrl);
		if(fisout == null || fisout.trim().length() ==0){
			article.setFisout(false);
		}else{
			article.setFisout(true);
		}*/
		article.setFcontent(fcontent);
		article.setFcontent_cn(fcontent_cn);
		article.setFlastModifyDate(Utils.getTimestamp());
		article.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		article.setFadminByFcreateAdmin(admin);
		article.setFadminByFmodifyAdmin(admin);
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			article.setFurl(fpictureUrl);
		}
		this.articleService.saveObj(article);
		
		List<Farticle> farticles = this.frontOtherService.findFarticle(1, 0, 5) ;
		//map.put("news", farticles);
		frontConstantMapService.put("news", farticles);
		
		//远程调用
		Map<String,String> query = new HashMap<String,String>();
		query.put("method", "reloadnews");
		query.put("params", "");
		HttpUtils.simpleRPC(query);

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteArticle")
	public ModelAndView deleteArticle(HttpServletRequest request) throws Exception{
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
		this.articleService.deleteObj(fid);
		
		List<Farticle> farticles = this.frontOtherService.findFarticle(1, 0, 5) ;
		//map.put("news", farticles);
		frontConstantMapService.put("news", farticles);
		
		Map<String,String> query = new HashMap<String,String>();
		query.put("method", "reloadnews");
		query.put("params", "");
		HttpUtils.simpleRPC(query);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/dingArticle")
	public ModelAndView dingArticle(HttpServletRequest request) throws Exception{
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
		Farticle a = this.articleService.findById(fid);
		if(a.isFisding()){
			a.setFisding(false);
		}else{
			a.setFisding(true);
		}
		
		this.articleService.updateObj(a);
		
		List<Farticle> farticles = this.frontOtherService.findFarticle(1, 0, 5) ;
		//map.put("news", farticles);
		frontConstantMapService.put("news", farticles);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","操作成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateArticle")
	public ModelAndView updateArticle(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String fKeyword,
			@RequestParam("articleLookup.id") int articleLookupid,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String ftitle_cn,
			@RequestParam(required=false) String fcontent_cn
			) throws Exception{
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
		
		Farticle article = this.articleService.findById(fid);

		Farticletype articletype = this.articleTypeService.findById(articleLookupid);
		article.setFarticletype(articletype);
/*		article.setFoutUrl(foutUrl);
		if(fisout == null || fisout.trim().length() ==0){
			article.setFisout(false);
		}else{
			article.setFisout(true);
		}*/
		article.setFtitle(ftitle);
		article.setFtitle_cn(ftitle_cn);
		article.setFkeyword(fKeyword);
		article.setFcontent(fcontent);
		article.setFcontent_cn(fcontent_cn);
		article.setFlastModifyDate(Utils.getTimestamp());
		article.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		article.setFadminByFcreateAdmin(admin);
		article.setFadminByFmodifyAdmin(admin);
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			article.setFurl(fpictureUrl);
		}
		this.articleService.updateObj(article);
		
		List<Farticle> farticles = this.frontOtherService.findFarticle(1, 0, 5) ;
		if(farticles != null && farticles.size() >0){
			//map.put("news", farticles);
			frontConstantMapService.put("news", farticles);
		}

		//远程调用
//		Map<String,String> query = new HashMap<String,String>();
//		query.put("method", "reloadnews");
//		query.put("params", "");
//		HttpUtils.simpleRPC(query);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
}
