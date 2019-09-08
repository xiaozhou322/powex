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

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.Fconvertcointype;
import com.gt.entity.WalletMessage;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.FconvertcointypeService;
import com.gt.service.admin.PoolService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.Constant;
import com.gt.util.ETHUtils;
import com.gt.util.OSSPostObject;
import com.gt.util.Utils;
import com.gt.util.wallet.WalletUtil;

@Controller
public class ConvertCoinTypeController extends BaseAdminController {
	@Autowired
	private FconvertcointypeService fconvertcointypeService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private PoolService poolService;
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/convertcointypeList")
	public ModelAndView convertcointypeList(HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/virtualCoinTypeList") ;
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
			filter.append("and (fname like '%"+keyWord+"%' OR \n");
			filter.append("fShortName like '%"+keyWord+"%' OR \n");
			filter.append("fdescription like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" \n");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by forder asc \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("asc \n");
		}
		List<Fconvertcointype> list = this.fconvertcointypeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("virtualCoinTypeList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "virtualCoinTypeList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualcointype", filter+""));
		return modelAndView ;
	}
	
	
	
	@RequestMapping("/buluo718admin/goConvertcointypeJSP")
	public ModelAndView goConvertcointypeJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fconvertcointype virtualCoinType = this.fconvertcointypeService.findById(fid);
			modelAndView.addObject("virtualCoinType", virtualCoinType);
			
			String filter = "where parentCid=0 and fstatus=1 and (ftype=1 or ftype=2) and fiserc20=0";
			List<Fconvertcointype> allParenCoin =  this.fconvertcointypeService.list(0, 100, filter, false);
			Map<Integer,String> typeMap1 = new HashMap<Integer,String>();
			for (Fconvertcointype fvirtualcointype : allParenCoin) {
				typeMap1.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
			}
			modelAndView.addObject("typeMap1", typeMap1);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveConvertcointype")
	public ModelAndView saveConvertcointype(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) String fweburl,
			@RequestParam(required=false) String fisEth,
			@RequestParam(required=false) String fisautosend,
			@RequestParam(required=false) String fisBts,
			@RequestParam(required=false) String fpassword,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) int fconfirm,
			@RequestParam(required=false) int faddressCount,
			@RequestParam(required=false) String fiserc20,
			@RequestParam(required=false) String fcontract,
			@RequestParam(required=false) String ftransfer,
			@RequestParam(required=false) String fbalance,
			@RequestParam(required=false) int fdecimals,
			@RequestParam(required=false) String fispush,
			@RequestParam(required=false) String fistransfer,
			@RequestParam(required=false) String fclasspath,
			@RequestParam(required=false) int parentCid,
			@RequestParam(required=false) int forder
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
		
		if(fclasspath!=null && !fclasspath.equals("")){
			//先检测classpath
			try{
				Class.forName(fclasspath);
			}catch(Exception e){
				e.printStackTrace();
				modelAndView.addObject("message","钱包类配置有误，请检查重新配置");
				modelAndView.addObject("statusCode",300);
				return modelAndView;
			}
		}
		
		Fconvertcointype convertcointype = new Fconvertcointype();
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
			convertcointype.setFurl(fpictureUrl);
		}
		
		if(fispush != null && fispush.trim().length() >0){
			convertcointype.setFispush(true);
		}else{
			convertcointype.setFispush(false);
		}
		
		convertcointype.setFaddTime(Utils.getTimestamp());
		convertcointype.setFdescription(fdescription);
		convertcointype.setFname(fname);
		convertcointype.setfShortName(fShortName);
		convertcointype.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		convertcointype.setFaccess_key(faccess_key);
		convertcointype.setFsecrt_key(fsecrt_key);
		convertcointype.setFip(fip);
		convertcointype.setFtype(CoinTypeEnum.COIN_VALUE);
		convertcointype.setFport(fport);
		convertcointype.setfSymbol(fSymbol);
		
		if(fisEth != null && fisEth.trim().length() >0){
			convertcointype.setFisEth(true);
			
			if("".equals(mainAddr.trim())){
				convertcointype.setMainAddr("") ;
			}else{
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","以太坊汇总地址错误");
					return modelAndView;
				}
				convertcointype.setMainAddr(mainAddr) ;
			}
			
			//以太坊的需要检测是否为代币
			
		}else if(fisBts != null && fisBts.trim().length() >0){
			convertcointype.setFisBts(true);
			convertcointype.setStartTranId("0.0.0");
			if("".equals(mainAddr.trim())){
				convertcointype.setMainAddr("") ;
			}else{
				convertcointype.setMainAddr(mainAddr) ;
			}
		}else{
			convertcointype.setFisEth(false);
			convertcointype.setFiserc20(false);
			convertcointype.setFisBts(false);
			convertcointype.setStartTranId("");
			convertcointype.setMainAddr("") ;
		}
		
		//代币支持多种主链
		if("".equals(mainAddr.trim())){
			convertcointype.setMainAddr("") ;
		}else{
			convertcointype.setMainAddr(mainAddr) ;
		}
		
		if(fiserc20 != null && fiserc20.trim().length() >0){
			
			if(parentCid==0){
				modelAndView.addObject("statusCode",500);
				modelAndView.addObject("message","代币需要设置上级币种");
				return modelAndView;
			}
			
			convertcointype.setFiserc20(true);
		}
		
		convertcointype.setFcontract(fcontract);
		convertcointype.setFtransfer(ftransfer);
		convertcointype.setFbalance(fbalance);
		convertcointype.setFdecimals(fdecimals);
		
		if(fisautosend != null && fisautosend.trim().length() >0){
			convertcointype.setFisautosend(true);
		}else{
			convertcointype.setFisautosend(false);
		}
		
		convertcointype.setFpassword(fpassword);
		convertcointype.setFconfirm(fconfirm);
		convertcointype.setFaddressCount(faddressCount);
		convertcointype.setParentCid(parentCid);
		
		convertcointype.setFclassPath(fclasspath);
		

		int cid = this.fconvertcointypeService.saveObj(convertcointype);
		Fconvertcointype fconvertcointype = this.fconvertcointypeService.findById(cid);
		fconvertcointype.setForder(fconvertcointype.getFid());
		this.fconvertcointypeService.updateObj(fconvertcointype);
		
		//远程调用
//		Map<String,String> query = new HashMap<String,String>();
//		query.put("method", "reloadcoin");
//		query.put("params", "");
//		HttpUtils.simpleRPC(query);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/updateConvertcointype")
	public ModelAndView updateConvertcointype(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String fweburl,
			@RequestParam(required=false) String fisEth,
			@RequestParam(required=false) String fisautosend,
			@RequestParam(required=false) String fisBts,
			@RequestParam(required=false) String fpassword,
			@RequestParam(required=false) int fconfirm,
			@RequestParam(required=false) int faddressCount,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) String fiserc20,
			@RequestParam(required=false) String fcontract,
			@RequestParam(required=false) String ftransfer,
			@RequestParam(required=false) String fbalance,
			@RequestParam(required=false) int fdecimals,
			@RequestParam(required=false) String fispush,
			@RequestParam(required=false) String fistransfer,
			@RequestParam(required=false) String fclasspath,
			@RequestParam(required=false) int parentCid,
			@RequestParam(required=false) int forder
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
		
		if(fclasspath!=null && !fclasspath.equals("")){
			//先检测classpath
			try{
				Class.forName(fclasspath);
			}catch(Exception e){
				e.printStackTrace();
				modelAndView.addObject("message","钱包类配置有误，请检查重新配置");
				modelAndView.addObject("statusCode",300);
				return modelAndView;
			}
		}
		Fconvertcointype convertcointype = this.fconvertcointypeService.findById(fid);
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
			convertcointype.setFurl(fpictureUrl);
		}
		
		if(fispush != null && fispush.trim().length() >0){
			convertcointype.setFispush(true);
		}else{
			convertcointype.setFispush(false);
		}
//		
//		convertcointype.setFweburl(fweburl);
		convertcointype.setFaddTime(Utils.getTimestamp());
		convertcointype.setFdescription(fdescription);
		convertcointype.setFname(fname);
		convertcointype.setfShortName(fShortName);
		convertcointype.setFaccess_key(faccess_key);
		convertcointype.setFsecrt_key(fsecrt_key);
		convertcointype.setFip(fip);
		convertcointype.setFport(fport);
		convertcointype.setfSymbol(fSymbol);
		if(fisautosend != null && fisautosend.trim().length() >0){
			convertcointype.setFisautosend(true);
		}else{
			convertcointype.setFisautosend(false);
		}
		convertcointype.setFconfirm(fconfirm);
		convertcointype.setFaddressCount(faddressCount);
		convertcointype.setFpassword(fpassword);
		
		if(fisEth != null && fisEth.trim().length() >0){
			convertcointype.setFisEth(true);
			
			if("".equals(mainAddr.trim())){
				convertcointype.setMainAddr("") ;
			}else{
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","以太坊汇总地址错误");
					return modelAndView;
				}
				convertcointype.setMainAddr(mainAddr) ;
			}
			
			
		}else if(fisBts != null && fisBts.trim().length() >0){
			convertcointype.setFisBts(true);
			convertcointype.setStartTranId("0.0.0");
		}else{
			convertcointype.setFisEth(false);
			convertcointype.setFiserc20(false);
			convertcointype.setFisBts(false);
			convertcointype.setStartTranId("");
			convertcointype.setMainAddr("") ;
		}
		
		if("".equals(mainAddr.trim())){
			convertcointype.setMainAddr("") ;
		}else{
			convertcointype.setMainAddr(mainAddr) ;
		}
		
		if(fiserc20 != null && fiserc20.trim().length() >0){
			
			if(parentCid==0){
				modelAndView.addObject("statusCode",500);
				modelAndView.addObject("message","代币需要设置上级币种");
				return modelAndView;
			}
			
			convertcointype.setFiserc20(true);
		}
		
		convertcointype.setFcontract(fcontract);
		convertcointype.setFtransfer(ftransfer);
		convertcointype.setFbalance(fbalance);
		convertcointype.setFdecimals(fdecimals);
		
		
		convertcointype.setFclassPath(fclasspath);
		convertcointype.setParentCid(parentCid);
		
		convertcointype.setForder(forder);
		
		this.fconvertcointypeService.updateObj(convertcointype);
		
		
		//远程调用
//		Map<String,String> query = new HashMap<String,String>();
//		query.put("method", "reloadcoin");
//		query.put("params", "");
//		HttpUtils.simpleRPC(query);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/deleteConvertcointype")
	public ModelAndView deleteConvertcointype(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fconvertcointype convertcointype = this.fconvertcointypeService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		if(convertcointype.getFtype() ==CoinTypeEnum.FB_CNY_VALUE || convertcointype.getFtype() ==CoinTypeEnum.FB_USDT_VALUE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币或USDT不允许禁用");
			return modelAndView;
		}
		
		convertcointype.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		this.fconvertcointypeService.updateObj(convertcointype);
		
		//重新刷新缓存数据
//		this.reloadcoin();
		//远程调用
		/*Map<String,String> query = new HashMap<String,String>();
		query.put("method", "reloadcoin");
		query.put("params", "");
		HttpUtils.simpleRPC(query);*/
		
		
		modelAndView.addObject("message","禁用成功");
		modelAndView.addObject("statusCode",200);
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/testWallet")
	public ModelAndView testWallet(HttpServletRequest request) throws Exception{
		
		
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
		Fconvertcointype type = this.fconvertcointypeService.findById(fid);
		
		WalletMessage wmsg = new WalletMessage();
		wmsg.setACCESS_KEY(type.getFaccess_key()) ;
		wmsg.setIP(type.getFip()) ;
		wmsg.setPORT(type.getFport()) ;
		wmsg.setSECRET_KEY(type.getFsecrt_key()) ;
		wmsg.setPASSWORD(request.getParameter("passWord"));
		wmsg.setISERC20(type.isFiserc20());
		wmsg.setCONTRACT(type.getFcontract());
		wmsg.setDECIMALS(type.getFdecimals());
		if(wmsg.getACCESS_KEY()==null
				||wmsg.getIP()==null
				||wmsg.getPORT()==null
				||wmsg.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		try {
			WalletUtil wallet1 = WalletUtil.createWalletByClass(type.getFclassPath(), wmsg);
			modelAndView.addObject("message","钱包余额："+wallet1.getBalanceValue(type.getMainAddr()));
			modelAndView.addObject("rel", "testWallet");
			modelAndView.addObject("callbackType","closeCurrent");
			modelAndView.addObject("statusCode",200);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
		}
		
		return modelAndView;
	}
	
	
	@RequestMapping("/buluo718admin/testAddress")
	public ModelAndView testAddress(HttpServletRequest request) throws Exception{
		
		
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
		Fconvertcointype type = this.fconvertcointypeService.findById(fid);
		String address = request.getParameter("address").toString();
		
		WalletMessage wmsg = new WalletMessage();
		wmsg.setACCESS_KEY(type.getFaccess_key()) ;
		wmsg.setIP(type.getFip()) ;
		wmsg.setPORT(type.getFport()) ;
		wmsg.setSECRET_KEY(type.getFsecrt_key()) ;
		wmsg.setISERC20(type.isFiserc20());
		wmsg.setCONTRACT(type.getFcontract());
		wmsg.setDECIMALS(type.getFdecimals());
		if(wmsg.getACCESS_KEY()==null
				||wmsg.getIP()==null
				||wmsg.getPORT()==null
				||wmsg.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		try {
			WalletUtil wallet1 = WalletUtil.createWalletByClass(type.getFclassPath(), wmsg);
			modelAndView.addObject("message","地址"+address+"状态为："+wallet1.validateAddress(address));
			modelAndView.addObject("rel", "testAddress");
			modelAndView.addObject("callbackType","closeCurrent");
			modelAndView.addObject("statusCode",200);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
		}
		
		return modelAndView;
	}
	

}
