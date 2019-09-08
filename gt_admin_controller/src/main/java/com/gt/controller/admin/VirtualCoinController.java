package com.gt.controller.admin;

import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import com.gt.entity.BTCMessage;
import com.gt.entity.ETHMessage;
import com.gt.entity.Fabout;
import com.gt.entity.Fpool;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fwithdrawfees;
import com.gt.entity.WalletMessage;
import com.gt.service.admin.AboutService;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.PoolService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.admin.WithdrawFeesService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.BTCUtils;
import com.gt.util.DCTUtils;
import com.gt.util.Constant;
import com.gt.util.ETHUtils;
import com.gt.util.ETPUtils;
import com.gt.util.HttpUtils;
import com.gt.util.OSSPostObject;
import com.gt.util.SpringUtil;
import com.gt.util.Utils;
import com.gt.util.WalletAPI;
import com.gt.util.wallet.WalletUtil;



import net.sf.json.JSONObject;

@Controller
public class VirtualCoinController extends BaseAdminController {
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService;
	@Autowired
	private PoolService poolService;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	/*@Autowired
	private ConstantMap map;*/
	@Autowired
	private FrontConstantMapService frontConstantMapService;
	@Autowired
	private AboutService aboutService;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/buluo718admin/virtualCoinTypeList")
	public ModelAndView Index(HttpServletRequest request) throws Exception{
		
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
		List<Fvirtualcointype> list = this.virtualCoinService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("virtualCoinTypeList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "virtualCoinTypeList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualcointype", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/buluo718admin/walletAddressList")
	public ModelAndView walletAddressList(HttpServletRequest request) throws Exception{
	
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/walletAddressList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and b.fname like '%"+keyWord+"%'\n");
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and (a.fstatus=0 or a.fstatus is null)\n");
		
		List list = this.poolService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("walletAddressList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "walletAddressList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.poolService.list((currentPage-1)*numPerPage, numPerPage,filter+"",false).size());
		return modelAndView ;
	}
	
	
	@RequestMapping("/buluo718admin/goVirtualCoinTypeJSP")
	public ModelAndView goVirtualCoinTypeJSP(HttpServletRequest request) throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fvirtualcointype virtualCoinType = this.virtualCoinService.findById(fid);
			modelAndView.addObject("virtualCoinType", virtualCoinType);
			
			String filter = "where fvirtualcointype.fid="+fid+" order by flevel asc";
			List<Fwithdrawfees> allFees = this.withdrawFeesService.list(0, 0, filter, false);
			modelAndView.addObject("allFees", allFees);
			
			filter = "where parentCid=0 and fstatus=1 and (ftype=1 or ftype=2) and fiserc20=0";
			List<Fvirtualcointype> allParenCoin =  this.virtualCoinService.list(0, 100, filter, false);
			Map<Integer,String> typeMap1 = new HashMap<Integer,String>();
			for (Fvirtualcointype fvirtualcointype : allParenCoin) {
				typeMap1.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
			}
			modelAndView.addObject("typeMap1", typeMap1);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/saveVirtualCoinType")
	public ModelAndView saveVirtualCoinType(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) String FIsWithDraw,
			@RequestParam(required=false) String fweburl,
			@RequestParam(required=false) String fisauto,
			@RequestParam(required=false) String fisEth,
			@RequestParam(required=false) String fisautosend,
			@RequestParam(required=false) String fislocked,
			@RequestParam(required=false) String fisBts,
			@RequestParam(required=false) String fpassword,
			@RequestParam(required=false) String fisEtp,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) String fisrecharge,
			@RequestParam(required=false) double fmaxqty,
			@RequestParam(required=false) int fconfirm,
			@RequestParam(required=false) int faddressCount,
			@RequestParam(required=false) double fminqty,
			@RequestParam(required=false) String fiserc20,
			@RequestParam(required=false) String fcontract,
			@RequestParam(required=false) String ftransfer,
			@RequestParam(required=false) String fbalance,
			@RequestParam(required=false) int fdecimals,
			@RequestParam(required=false) double fpushMaxPrice,
			@RequestParam(required=false) double fpushMaxQty,
			@RequestParam(required=false) double fpushMinPrice,
			@RequestParam(required=false) double fpushMinQty,
			@RequestParam(required=false) double fpushRate,
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
		
		Fvirtualcointype virtualCoinType = new Fvirtualcointype();
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
			virtualCoinType.setFurl(fpictureUrl);
		}
		
		virtualCoinType.setFpushMaxPrice(fpushMaxPrice);
		virtualCoinType.setFpushMaxQty(fpushMaxQty);
		virtualCoinType.setFpushMinPrice(fpushMinPrice);
		virtualCoinType.setFpushMinQty(fpushMinQty);
		virtualCoinType.setFpushRate(fpushRate);
		if(fispush != null && fispush.trim().length() >0){
			virtualCoinType.setFispush(true);
		}else{
			virtualCoinType.setFispush(false);
		}
		
		virtualCoinType.setFaddTime(Utils.getTimestamp());
		virtualCoinType.setFdescription(fdescription);
		virtualCoinType.setFname(fname);
		virtualCoinType.setfShortName(fShortName);
		virtualCoinType.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		virtualCoinType.setFaccess_key(faccess_key);
		virtualCoinType.setFsecrt_key(fsecrt_key);
		virtualCoinType.setFip(fip);
		virtualCoinType.setFtype(CoinTypeEnum.COIN_VALUE);
		virtualCoinType.setFport(fport);
		virtualCoinType.setfSymbol(fSymbol);
		virtualCoinType.setFmaxqty(fmaxqty);
		virtualCoinType.setFminqty(fminqty);
		if(FIsWithDraw != null && FIsWithDraw.trim().length() >0){
			virtualCoinType.setFIsWithDraw(true);
		}else{
			virtualCoinType.setFIsWithDraw(false);
		}
		if(fisauto != null && fisauto.trim().length() >0){
			virtualCoinType.setFisauto(true);
		}else{
			virtualCoinType.setFisauto(false);
		}
		
		if(fisEth != null && fisEth.trim().length() >0){
			virtualCoinType.setFisEth(true);
			
			if("".equals(mainAddr.trim())){
				virtualCoinType.setMainAddr("") ;
			}else{
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","以太坊汇总地址错误");
					return modelAndView;
				}
				virtualCoinType.setMainAddr(mainAddr) ;
			}
			
			//以太坊的需要检测是否为代币
			
		}else if(fisEtp != null && fisEtp.trim().length() >0){
			virtualCoinType.setFisEtp(true);
			if(mainAddr == null || mainAddr.trim().length() ==0){
				modelAndView.addObject("statusCode",500);
				modelAndView.addObject("message","ETP必须填主地址");
				return modelAndView;
			}
			virtualCoinType.setMainAddr(mainAddr) ;
		}else if(fisBts != null && fisBts.trim().length() >0){
			virtualCoinType.setFisBts(true);
			virtualCoinType.setStartTranId("0.0.0");
			if("".equals(mainAddr.trim())){
				virtualCoinType.setMainAddr("") ;
			}else{
				virtualCoinType.setMainAddr(mainAddr) ;
			}
		}else{
			virtualCoinType.setFisEth(false);
			virtualCoinType.setFiserc20(false);
			virtualCoinType.setFisEtp(false);
			virtualCoinType.setFisBts(false);
			virtualCoinType.setStartTranId("");
			virtualCoinType.setMainAddr("") ;
		}
		
		//代币支持多种主链
		if("".equals(mainAddr.trim())){
			virtualCoinType.setMainAddr("") ;
		}else{
			virtualCoinType.setMainAddr(mainAddr) ;
		}
		
		if(fiserc20 != null && fiserc20.trim().length() >0){
			
			if(parentCid==0){
				modelAndView.addObject("statusCode",500);
				modelAndView.addObject("message","代币需要设置上级币种");
				return modelAndView;
			}
			
			virtualCoinType.setFiserc20(true);
		}
		
		virtualCoinType.setFcontract(fcontract);
		virtualCoinType.setFtransfer(ftransfer);
		virtualCoinType.setFbalance(fbalance);
		virtualCoinType.setFdecimals(fdecimals);
		
		if(fisautosend != null && fisautosend.trim().length() >0){
			virtualCoinType.setFisautosend(true);
		}else{
			virtualCoinType.setFisautosend(false);
		}
		if(fislocked != null && fislocked.trim().length() >0){
			virtualCoinType.setFislocked(true);
		}else{
			virtualCoinType.setFislocked(false);
		}
		
		if(fistransfer != null && fistransfer.trim().length() >0){
			virtualCoinType.setFisTransfer(true);
		}else{
			virtualCoinType.setFisTransfer(false);
		}
		
		virtualCoinType.setFpassword(fpassword);
		virtualCoinType.setFconfirm(fconfirm);
		virtualCoinType.setFaddressCount(faddressCount);
		virtualCoinType.setParentCid(parentCid);
		if(fisrecharge != null && fisrecharge.trim().length() >0){
			virtualCoinType.setFisrecharge(true);
		}else{
			virtualCoinType.setFisrecharge(false);
		}
		
		virtualCoinType.setFclassPath(fclasspath);
		
		Fabout about = null;
		try {
			about = new Fabout();
			about.setFcontent(virtualCoinType.getFname()+" 介绍");
			about.setFtitle(virtualCoinType.getFname()+" 介绍");
			about.setFtype("Coin Introduction");
			this.aboutService.saveObj(about);
			virtualCoinType.setFweburl(String.valueOf(about.getFid()));
		} catch (Exception e) {}

		int cid = this.virtualCoinService.saveObj(virtualCoinType);
		Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(cid);
		fvirtualcointype.setForder(fvirtualcointype.getFid());
		this.virtualCoinService.updateObj(fvirtualcointype);
		
		for(int i=1;i<=6;i++){
			Fwithdrawfees fees = new Fwithdrawfees();
			fees.setFlevel(i);
			fees.setFvirtualcointype(fvirtualcointype);
			fees.setFfee(0d);
			this.withdrawFeesService.saveObj(fees);
		}
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		//map.put("virtualCoinType", fvirtualcointypes) ;
		frontConstantMapService.put("virtualCoinType", fvirtualcointypes);
		
		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		//map.put("allWithdrawCoins", allWithdrawCoins) ;
		frontConstantMapService.put("allWithdrawCoins", allWithdrawCoins);
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			//map.put("allRechargeCoins", allRechargeCoins) ;
			frontConstantMapService.put("allWithdrawCoins", allWithdrawCoins);
		}

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
	
	@RequestMapping("/buluo718admin/updateVirtualCoinType")
	public ModelAndView updateVirtualCoinType(HttpServletRequest request,
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) String FIsWithDraw,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String fweburl,
			@RequestParam(required=false) String fisauto,
			@RequestParam(required=false) String fisEth,
			@RequestParam(required=false) String fisautosend,
			@RequestParam(required=false) String fislocked,
			@RequestParam(required=false) String fisBts,
			@RequestParam(required=false) String fpassword,
			@RequestParam(required=false) int fconfirm,
			@RequestParam(required=false) int faddressCount,
			@RequestParam(required=false) String fisEtp,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) String fisrecharge,
			@RequestParam(required=false) double fmaxqty,
			@RequestParam(required=false) double fminqty,
			@RequestParam(required=false) String fiserc20,
			@RequestParam(required=false) String fcontract,
			@RequestParam(required=false) String ftransfer,
			@RequestParam(required=false) String fbalance,
			@RequestParam(required=false) int fdecimals,
			@RequestParam(required=false) double fpushMaxPrice,
			@RequestParam(required=false) double fpushMaxQty,
			@RequestParam(required=false) double fpushMinPrice,
			@RequestParam(required=false) double fpushMinQty,
			@RequestParam(required=false) double fpushRate,
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
		Fvirtualcointype virtualCoinType = this.virtualCoinService.findById(fid);
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
			virtualCoinType.setFurl(fpictureUrl);
		}
		
		virtualCoinType.setFpushMaxPrice(fpushMaxPrice);
		virtualCoinType.setFpushMaxQty(fpushMaxQty);
		virtualCoinType.setFpushMinPrice(fpushMinPrice);
		virtualCoinType.setFpushMinQty(fpushMinQty);
		virtualCoinType.setFpushRate(fpushRate);
		if(fispush != null && fispush.trim().length() >0){
			virtualCoinType.setFispush(true);
		}else{
			virtualCoinType.setFispush(false);
		}
//		
//		virtualCoinType.setFweburl(fweburl);
		virtualCoinType.setFaddTime(Utils.getTimestamp());
		virtualCoinType.setFdescription(fdescription);
		virtualCoinType.setFname(fname);
		virtualCoinType.setfShortName(fShortName);
		virtualCoinType.setFaccess_key(faccess_key);
		virtualCoinType.setFsecrt_key(fsecrt_key);
		virtualCoinType.setFip(fip);
		virtualCoinType.setFport(fport);
		virtualCoinType.setfSymbol(fSymbol);
		virtualCoinType.setFmaxqty(fmaxqty);
		virtualCoinType.setFminqty(fminqty);
		if(FIsWithDraw != null && FIsWithDraw.trim().length() >0){
			virtualCoinType.setFIsWithDraw(true);
		}else{
			virtualCoinType.setFIsWithDraw(false);
		}
		if(fisauto != null && fisauto.trim().length() >0){
			virtualCoinType.setFisauto(true);
		}else{
			virtualCoinType.setFisauto(false);
		}
		if(fislocked != null && fislocked.trim().length() >0){
			virtualCoinType.setFislocked(true);
		}else{
			virtualCoinType.setFislocked(false);
		}
		if(fisautosend != null && fisautosend.trim().length() >0){
			virtualCoinType.setFisautosend(true);
		}else{
			virtualCoinType.setFisautosend(false);
		}
		virtualCoinType.setFconfirm(fconfirm);
		virtualCoinType.setFaddressCount(faddressCount);
		virtualCoinType.setFpassword(fpassword);
		
		if(fisEth != null && fisEth.trim().length() >0){
			virtualCoinType.setFisEth(true);
			
			if("".equals(mainAddr.trim())){
				virtualCoinType.setMainAddr("") ;
			}else{
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","以太坊汇总地址错误");
					return modelAndView;
				}
				virtualCoinType.setMainAddr(mainAddr) ;
			}
			
			
		}else if(fisEtp != null && fisEtp.trim().length() >0){
			virtualCoinType.setFisEtp(true);
			if(mainAddr == null || mainAddr.trim().length() ==0){
				modelAndView.addObject("statusCode",500);
				modelAndView.addObject("message","ETP必须填主地址");
				return modelAndView;
			}
			virtualCoinType.setMainAddr(mainAddr) ;
		}else if(fisBts != null && fisBts.trim().length() >0){
			virtualCoinType.setFisBts(true);
			virtualCoinType.setStartTranId("0.0.0");
		}else{
			virtualCoinType.setFisEth(false);
			virtualCoinType.setFiserc20(false);
			virtualCoinType.setFisEtp(false);
			virtualCoinType.setFisBts(false);
			virtualCoinType.setStartTranId("");
			virtualCoinType.setMainAddr("") ;
		}
		
		if("".equals(mainAddr.trim())){
			virtualCoinType.setMainAddr("") ;
		}else{
			virtualCoinType.setMainAddr(mainAddr) ;
		}
		
		if(fiserc20 != null && fiserc20.trim().length() >0){
			
			if(parentCid==0){
				modelAndView.addObject("statusCode",500);
				modelAndView.addObject("message","代币需要设置上级币种");
				return modelAndView;
			}
			
			virtualCoinType.setFiserc20(true);
		}
		
		virtualCoinType.setFcontract(fcontract);
		virtualCoinType.setFtransfer(ftransfer);
		virtualCoinType.setFbalance(fbalance);
		virtualCoinType.setFdecimals(fdecimals);
		
		if(fisrecharge != null && fisrecharge.trim().length() >0){
			virtualCoinType.setFisrecharge(true);
		}else{
			virtualCoinType.setFisrecharge(false);
		}
		
		if(fistransfer != null && fistransfer.trim().length() >0){
			virtualCoinType.setFisTransfer(true);
		}else{
			virtualCoinType.setFisTransfer(false);
		}
		
		virtualCoinType.setFclassPath(fclasspath);
		virtualCoinType.setParentCid(parentCid);
		
		if(virtualCoinType.getFtype() ==CoinTypeEnum.FB_CNY_VALUE ){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币不允许修改");
			return modelAndView;
		}
		virtualCoinType.setForder(forder);
		
		this.virtualCoinService.updateObj(virtualCoinType);
		
//		try {
//			Fabout about = this.aboutService.findById(Integer.parseInt(virtualCoinType.getFweburl()));
//			about.setFtitle(virtualCoinType.getFname()+" 介绍");
//			this.aboutService.updateObj(about);
//		} catch (Exception e) {}
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		//map.put("virtualCoinType", fvirtualcointypes) ;
		frontConstantMapService.put("virtualCoinType", fvirtualcointypes);
		
		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		//map.put("allWithdrawCoins", allWithdrawCoins) ;
		frontConstantMapService.put("allWithdrawCoins", allWithdrawCoins);
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			//map.put("allRechargeCoins", allRechargeCoins) ;
			frontConstantMapService.put("allRechargeCoins", allRechargeCoins);
		}
		
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
	
	@RequestMapping("/buluo718admin/goWallet")
	public ModelAndView goWallet(HttpServletRequest request) throws Exception{
		
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
		String password = request.getParameter("passWord");
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		boolean flag = false;
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Normal);
		String msg = "";
		try {
			flag = this.virtualCoinService.updateCoinType(virtualcointype,password);
			flag = true;
		} catch (Exception e) {
			 msg =e.getMessage();
		}
		
		//重新刷新缓存数据
		this.reloadcoin();
		
		//远程调用
		/*Map<String,String> query = new HashMap<String,String>();
		query.put("method", "reloadcoin");
		query.put("params", "");
		HttpUtils.simpleRPC(query);*/
		
		
		if(!flag){
			modelAndView.addObject("message",msg);
			modelAndView.addObject("statusCode",300);
		}else{
			modelAndView.addObject("message","启用成功");
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("callbackType","closeCurrent");
		}
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/enabledScanWallet")
	public ModelAndView enabledScanWallet(HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		String password = request.getParameter("passWord");
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		boolean flag = false;
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Normal);
		String msg = "";
		try {
			flag = this.virtualCoinService.updateCoinType(virtualcointype,password);
			flag = true;
		} catch (Exception e) {
			 msg =e.getMessage();
		}
		
		if(virtualcointype.isFisBts()==false){
			modelAndView.addObject("message","仅BTS、DCT类型的钱包才允许开启钱包扫描");
			modelAndView.addObject("statusCode",300);
		}else{
			walletmsg = new WalletMessage();
			walletmsg.setACCESS_KEY(type.getFaccess_key()) ;
			walletmsg.setIP(type.getFip()) ;
			walletmsg.setPORT(type.getFport()) ;
			walletmsg.setSECRET_KEY(type.getFsecrt_key()) ;
			walletmsg.setPASSWORD(password);
			if(walletmsg.getACCESS_KEY()==null
					||walletmsg.getIP()==null
					||walletmsg.getPORT()==null
					||walletmsg.getSECRET_KEY()==null){
				modelAndView.addObject("message","钱包连接失败，请检查配置信息");
				modelAndView.addObject("statusCode",300);
			}
			
			WalletUtil wallet1 = WalletUtil.createWalletByClass(type.getFclassPath(), walletmsg);
			if(!wallet1.unlockWallet("")){
				modelAndView.addObject("message","钱包扫描开启失败");
				modelAndView.addObject("statusCode",300);
			}else{
				modelAndView.addObject("message","钱包成功开启扫描");
				modelAndView.addObject("statusCode",200);
				modelAndView.addObject("callbackType","closeCurrent");
			}
		}
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/disabledScanWallet")
	public ModelAndView disabledScanWallet(HttpServletRequest request) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		boolean flag = false;
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Normal);
		String msg = "";
		try {
			flag = this.virtualCoinService.updateCoinType(virtualcointype,"");
			flag = true;
		} catch (Exception e) {
			 msg =e.getMessage();
		}
		
		if(virtualcointype.isFisBts()==false){
			modelAndView.addObject("message","仅BTS、DCT类型的钱包才允许禁用钱包扫描");
			modelAndView.addObject("statusCode",300);
		}else{
			walletmsg = new WalletMessage();
			walletmsg.setACCESS_KEY(type.getFaccess_key()) ;
			walletmsg.setIP(type.getFip()) ;
			walletmsg.setPORT(type.getFport()) ;
			walletmsg.setSECRET_KEY(type.getFsecrt_key()) ;
			if(walletmsg.getACCESS_KEY()==null
					||walletmsg.getIP()==null
					||walletmsg.getPORT()==null
					||walletmsg.getSECRET_KEY()==null){
				modelAndView.addObject("message","钱包连接失败，请检查配置信息");
				modelAndView.addObject("statusCode",300);
			}
			
			WalletUtil wallet1 = WalletUtil.createWalletByClass(type.getFclassPath(), walletmsg);
			wallet1.lockWallet("");
			modelAndView.addObject("message","钱包成功禁用扫描");
			modelAndView.addObject("statusCode",200);
		}
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/deleteVirtualCoinType")
	public ModelAndView deleteVirtualCoinType(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		if(virtualcointype.getFtype() ==CoinTypeEnum.FB_CNY_VALUE || virtualcointype.getFtype() ==CoinTypeEnum.FB_USDT_VALUE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币或USDT不允许禁用");
			return modelAndView;
		}
		
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		this.virtualCoinService.updateObj(virtualcointype);
		
		//重新刷新缓存数据
		this.reloadcoin();
		//远程调用
		/*Map<String,String> query = new HashMap<String,String>();
		query.put("method", "reloadcoin");
		query.put("params", "");
		HttpUtils.simpleRPC(query);*/
		
		
		modelAndView.addObject("message","禁用成功");
		modelAndView.addObject("statusCode",200);
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/setOtcCoin")
	public ModelAndView setOtcCoin(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		//进行谷歌验证码确认
		String msgcode = this.gAuth(request);
		if(!msgcode.equals("ok")) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", msgcode);
			return modelAndView;
		}
		//结束谷歌验证码确认
		
		if(virtualcointype.getFtype() ==CoinTypeEnum.FB_CNY_VALUE || virtualcointype.getFtype() ==CoinTypeEnum.COIN_VALUE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币或普通虚拟币不允许设置为OTC币种");
			return modelAndView;
		}
		boolean isOtc = !virtualcointype.isFisotc();
		virtualcointype.setFisotc(isOtc);
		this.virtualCoinService.updateObj(virtualcointype);
		
		
		modelAndView.addObject("message","设置成功");
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
		Fvirtualcointype type = this.virtualCoinService.findById(fid);
		
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
		Fvirtualcointype type = this.virtualCoinService.findById(fid);
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
	
	@RequestMapping("/buluo718admin/updateCoinFee")
	public ModelAndView updateCoinFee(HttpServletRequest request) throws Exception{
		
		
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
		
		int fid = Integer.parseInt(request.getParameter("fid"));
		List<Fwithdrawfees> all = this.withdrawFeesService.findByProperty("fvirtualcointype.fid", fid);
		
		//add by hank
		for (Fwithdrawfees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			
			if(fee>=1000000 || fee<0){
				
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","手续费率只能是小于1的小数！");
				return modelAndView;
			}
		}
		
		for (Fwithdrawfees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			ffees.setFfee(fee);
			this.withdrawFeesService.updateObj(ffees);
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/createWalletAddress")
	public ModelAndView createWalletAddress(HttpServletRequest request) throws Exception{
		
		
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
		type = this.virtualCoinService.findById(fid);
		if(!type.isFIsWithDraw() && !type.isFisrecharge()){
			modelAndView.addObject("message","不允许充值和提现的虚拟币类型不能生成虚拟地址!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		//BTS备注地址收款的钱包不做地址生成
		if(type.isFiserc20() || type.getParentCid()>0){
			modelAndView.addObject("message","BTS备注地址收款的钱包需要生成虚拟地址!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		//以太坊代币不生产虚拟地址，与以太坊共用地址
		if(type.isFiserc20() || type.getParentCid()>0){
			modelAndView.addObject("message","代币或者子资产不生成虚拟地址!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		if(type.getFclassPath()==null || type.getFclassPath().equals("")){
			modelAndView.addObject("message","引用类库地址未设置!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		walletmsg = new WalletMessage();
		walletmsg.setACCESS_KEY(type.getFaccess_key()) ;
		walletmsg.setIP(type.getFip()) ;
		walletmsg.setPORT(type.getFport()) ;
		walletmsg.setSECRET_KEY(type.getFsecrt_key()) ;
		walletmsg.setPASSWORD(request.getParameter("passWord"));
		
		if(walletmsg.getACCESS_KEY()==null
				||walletmsg.getIP()==null
				||walletmsg.getPORT()==null
				||walletmsg.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		try {
			new Thread(new Work()).start() ;
		} catch (Exception e) {
			modelAndView.addObject("message","钱包异常!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		
		modelAndView.addObject("message","后台执行中!");
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("rel", "createWalletAddress");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	private WalletMessage walletmsg;
	private BTCMessage btcMessage;
	private Fvirtualcointype type;
	class Work implements Runnable{
		public void run() {
			createAddress(walletmsg, type);
		}
	}
	
	private void createAddress(WalletMessage walletmsg,Fvirtualcointype type) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		if(type.getFclassPath()!=null && !type.getFclassPath().equals("")){
			WalletUtil wallet = null;
			try {
				wallet = WalletUtil.createWalletByClass(type.getFclassPath(), walletmsg);
				//更新钱包扫描地址，针对需要按照区块扫描的钱包，非扫描区块的钱包直接不处理
				if(type.getStartBlockId()==0){
					type.setStartBlockId(wallet.bestBlockNumberValue()) ;
					this.virtualCoinService.updateObj(type) ;
				}
				
				for(int i=0;i<type.getFaddressCount();i++){
					//主地址作为种子信息，提供给以主地址为前缀的代币
					String address = wallet.getNewaddressValue(type.getMainAddr());
					if(address != null && address.trim().length() >0){
						Fpool poolInfo = new Fpool();
						poolInfo.setFaddress(address);
						poolInfo.setFvirtualcointype(type);
						poolService.saveObj(poolInfo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					wallet.lockWallet("");
				} catch (Exception e) {}
			}
		}

	}
	
	
	
	
	@RequestMapping("/buluo718admin/etcMainAddr")
	public ModelAndView etcMainAddr(
			HttpServletRequest request,
			@RequestParam(required=true )int uid,
			@RequestParam(required=true )final String password
			) throws Exception {
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
		
		final Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(uid) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFclassPath().equals("") ){
			modelAndView.addObject("message","钱包错误或者引用类库尚未设置");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}

		if(fvirtualcointype.getMainAddr()==null||"".equals(fvirtualcointype.getMainAddr().trim())){
			modelAndView.addObject("message","未设置主钱包地址");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		
		WalletMessage walletmsg = new WalletMessage();
		walletmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
		walletmsg.setIP(fvirtualcointype.getFip()) ;
		walletmsg.setPORT(fvirtualcointype.getFport()) ;
		walletmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
		walletmsg.setPASSWORD(password);
		walletmsg.setCONTRACT(fvirtualcointype.getFcontract());
		walletmsg.setISERC20(fvirtualcointype.isFiserc20());
		walletmsg.setDECIMALS(fvirtualcointype.getFdecimals());
		
		if(walletmsg.getACCESS_KEY()==null
				||walletmsg.getIP()==null
				||walletmsg.getPORT()==null
				||walletmsg.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
		}
		
		final WalletUtil walletf = WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), walletmsg);
		
		boolean flag = false ;
		try {
			flag = walletf.unlockWallet(fvirtualcointype.getMainAddr().trim());
			walletf.lockWallet(fvirtualcointype.getMainAddr().trim()) ;
		} catch (Exception e1) {
			
		}
		if(flag == false ){
			modelAndView.addObject("message","钱包链接错误，或密码错误");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		{
			new Thread(new Runnable() {
				public void run() {
					try {
						walletf.sendMain(fvirtualcointype.getMainAddr().trim(), fvirtualcointype.getFminqty());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start() ;
			modelAndView.addObject("message","后台执行中,执行时间由钱包中地址数量决定，短时间内请不要重复执行该功能!");
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("rel", "etcMainAddr");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
	}
	
	
	//重新加载币种
	private void reloadcoin(){
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		this.frontConstantMapService.put("virtualCoinType", fvirtualcointypes) ;
		
		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		this.frontConstantMapService.put("allWithdrawCoins", allWithdrawCoins) ;
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			this.frontConstantMapService.put("allRechargeCoins", allRechargeCoins) ;
		}
		
		{
			String filter = "where fstatus=1 and fisTransfer=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allTransferCoins= this.virtualCoinService.list(0, 0, filter, false);
			this.frontConstantMapService.put("allTransferCoins", allTransferCoins) ;
		}
		
		String sql = "where fstatus=1";
		List<Fvirtualcointype> allCoins= this.virtualCoinService.list(0, 0, sql, false);
		this.frontConstantMapService.put("allCoins", allCoins) ;
	}

}
