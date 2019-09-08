package com.gt.controller.admin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.Fadmin;
import com.gt.entity.Fbanner;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.BannerService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontOthersService;
import com.gt.util.Constant;
import com.gt.util.HttpUtils;
import com.gt.util.OSSPostObject;
import com.gt.util.Utils;

@Controller
public class BannerController extends BaseAdminController {
	@Autowired
	private BannerService bannerService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontOthersService frontOtherService;
	/*@Autowired
	private ConstantMap map;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/bannerList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/banner/list") ;
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
			filter.append("and fTitle like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(request.getParameter("ftype") != null){
			filter.append("and ftype='"+request.getParameter("ftype")+"' \n");
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}else{
			modelAndView.addObject("ftype", "");
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
		
		//String[] args = this.map.get("bannertype").toString().split("#");
		String[] args = this.frontConstantMapService.get("bannertype").toString().split("#");
		Map<String,String> type = new HashMap<String,String>();
		for (int i = 0; i < args.length; i++) {
			type.put(args[i], args[i]);
		}
		modelAndView.addObject("type", type);
		List<Fbanner> list = this.bannerService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("bannerList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "bannerList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fbanner", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/goBannerJSP")
	public ModelAndView goBannerJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		//String[] args = this.map.get("bannertype").toString().split("#");
		String[] args = this.frontConstantMapService.get("bannertype").toString().split("#");
		Map<String,String> type = new HashMap<String,String>();
		for (int i = 0; i < args.length; i++) {
			type.put(args[i], args[i]);
		}
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fbanner banner = this.bannerService.findById(fid);
			modelAndView.addObject("fbanner", banner);
		}
		modelAndView.addObject("type", type);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveBanner")
	public ModelAndView saveBanner(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) MultipartFile filedatam,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String furl,
			@RequestParam(required=false) String ftype,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String fcontent_en,
			@RequestParam(required=false) String fisding
			) throws Exception{
		Fbanner banner = new Fbanner();
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
		
		if (filedata == null && filedata.isEmpty()){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","保存失败，必须上传Banner图片");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
		if (filedatam == null && filedatam.isEmpty()){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","保存失败，必须上传Banner手机版图片");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
		
		banner.setFtitle(ftitle);
		banner.setFlastModifyDate(Utils.getTimestamp());
		banner.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		banner.setFadminByFcreateAdmin(admin);
		banner.setFadminByFmodifyAdmin(admin);
		banner.setFurl(furl);
		banner.setFcontent(fcontent);
		banner.setFcontent_en(fcontent_en);
		banner.setFtype(ftype);
		if(fisding!=null && fisding.trim().length()>0){
			banner.setFisding(true);
		}else{
			banner.setFisding(false);
		}
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
			banner.setFimgUrl(fpictureUrl);
		}
		
		isTrue=false;
		String fpictureUrlm = "";
		if(filedatam != null && !filedatam.isEmpty()){
			InputStream inputStream = filedatam.getInputStream() ;
			String fileRealName = filedatam.getOriginalFilename() ;
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
				String fileName = Utils.getRandomImageName()+"_m."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrlm = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrlm = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			banner.setFimgUrlm(fpictureUrlm);
		}
		
		this.bannerService.saveObj(banner);
		
		//远程调用
		this.reloadbanner();

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteBanner")
	public ModelAndView deleteBanner(HttpServletRequest request) throws Exception{
		
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
		this.bannerService.deleteObj(fid);
		
		//远程调用
		this.reloadbanner();
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/enableBanner")
	public ModelAndView dingArticle(HttpServletRequest request) throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		boolean status = false;
		if(request.getParameter("status")!=null && request.getParameter("status").toString().equals("1")){
			status=true;
		}
		Fbanner a = this.bannerService.findById(fid);
		a.setFstatus(status);

		
		this.bannerService.updateObj(a);
		
		//远程调用
		this.reloadbanner();
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","操作成功");
		return modelAndView;
	}

	@RequestMapping("/buluo718admin/updateBanner")
	public ModelAndView updateBanner(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) MultipartFile filedatam,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String furl,
			@RequestParam(required=false) String ftype,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String fcontent_en,
			@RequestParam(required=false) String fisding
			) throws Exception{
		Fbanner banner = this.bannerService.findById(fid);
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
		if ( banner.getFimgUrl()==null || banner.getFimgUrl().equals("")){
			if (filedata == null && filedata.isEmpty()){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","保存失败，必须上传Banner图片");
				modelAndView.addObject("callbackType","closeCurrent");
				return modelAndView;
			}
		}
		if ( banner.getFimgUrlm()==null || banner.getFimgUrlm().equals("")){
			if (filedatam == null && filedatam.isEmpty()){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","保存失败，必须上传Banner手机版图片");
				modelAndView.addObject("callbackType","closeCurrent");
				return modelAndView;
			}
		}
		banner.setFtitle(ftitle);
		banner.setFlastModifyDate(Utils.getTimestamp());
		banner.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		banner.setFadminByFcreateAdmin(admin);
		banner.setFadminByFmodifyAdmin(admin);
		banner.setFurl(furl);
		banner.setFcontent(fcontent);
		banner.setFcontent_en(fcontent_en);
		banner.setFtype(ftype);
		if(fisding!=null && fisding.trim().length()>0){
			banner.setFisding(true);
		}else{
			banner.setFisding(false);
		}
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
			banner.setFimgUrl(fpictureUrl);
		}
		
		isTrue=false;
		String fpictureUrlm = "";
		if(filedatam != null && !filedatam.isEmpty()){
			InputStream inputStream = filedatam.getInputStream() ;
			String fileRealName = filedatam.getOriginalFilename() ;
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
				String fileName = Utils.getRandomImageName()+"_m."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrlm = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrlm = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			banner.setFimgUrlm(fpictureUrlm);
		}
		
		this.bannerService.updateObj(banner);
		

		//远程调用
		this.reloadbanner();
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	private void reloadbanner() throws Exception{
		
		List<Fbanner> fbanners = this.frontOtherService.findFbanner("index", 0, 5) ;
		if(fbanners != null && fbanners.size() >0){
			//map.put("fbanners", fbanners);
			frontConstantMapService.put("fbanners", fbanners);
		}
		
		List<Fbanner> fbannerProject = this.frontOtherService.findFbanner("fbannerProject", 0, 5) ;
		if(fbannerProject != null && fbannerProject.size() >0){
			//map.put("fbanners", fbanners);
			frontConstantMapService.put("fbannerProject", fbannerProject);
		}
		
//		Map<String,String> query = new HashMap<String,String>();
//		query.put("method", "reloadbanner");
//		query.put("params", "");
//		HttpUtils.simpleRPC(query);
	}
}
