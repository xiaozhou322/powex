package com.gt.controller.admin;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.gt.Enum.CapitalOperationOutStatus;
import com.gt.Enum.FactivityStatusEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.Enum.RegTypeEnum;
import com.gt.Enum.UserStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.controller.BaseAdminController;
import com.gt.entity.FactivityModel;
import com.gt.entity.Fadmin;
import com.gt.entity.Fapi;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fscore;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.WalletMessage;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCapitaloperationService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontActivityService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.GoogleAuth;
import com.gt.util.Utils;
import com.gt.util.wallet.WalletUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class FAPIController extends BaseAdminController {

	public final static String CODE = "code" ;
	public final static String MESSAGE = "msg" ;
	public final static String TIME = "time" ;
	public final static String DATA = "data" ;
	public final static String ORDERID = "orderId" ;
	public final static int maxResults = Constant.AppRecordPerPage ;
	
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FvirtualWalletService virtualWalletService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private UserService userService ;
	@Autowired
	private FrontActivityService frontActivitiesService;
	
	@ResponseBody
	@RequestMapping(value="/buluo718admin/appApi3",produces={JsonEncode})
	public String appApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String action
			) throws Exception {
		request.setCharacterEncoding("UTF-8");
		Integer actionInteger = APIConstant.getInteger(action);
		
		Fapi fapi = null ;
		Fuser fuser = null ;
		if(actionInteger / 100>=2){

			String api_key = request.getParameter("api_key") ;
			String sign = request.getParameter("sign") ;
			List<Fapi> fapis = this.utilsService.list(0, 0, " where fpartner=? ", false, Fapi.class,api_key) ;
			if(fapis.size() == 1 ){
				fapi = fapis.get(0) ;
				if( 
						(fapi.isFistrade()==false && actionInteger / 100==2)
						
						){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10004) ;
					jsonObject.accumulate(MESSAGE, "无交易权限") ;
					return jsonObject.toString() ;
				}
				
				if(
						(fapi.isFiswithdraw()==false && actionInteger / 100==3)
						){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10005) ;
					jsonObject.accumulate(MESSAGE, "无提现权限") ;
					return jsonObject.toString() ;
				}
				
				String ip = getIpAddr(request) ;
				if("*".equals(fapi.getFip()) == false && fapi.getFip().indexOf(ip)==-1){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10005) ;
					jsonObject.accumulate(MESSAGE, "IP无权限") ;
					return jsonObject.toString() ;
				}
				
				fuser = fapi.getFuser() ;
			}else{
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE , -10006) ;
				jsonObject.accumulate(MESSAGE, "api_key错误") ;
				return jsonObject.toString() ;
			}
			
			
			Map<String, String[]> params = request.getParameterMap() ;
			TreeSet<String> paramTreeSet = new TreeSet<String>() ;
			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String key = entry.getKey() ;
				String[] values = entry.getValue() ;
				if(values.length!=1){
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE , -10003) ;
					jsonObject.accumulate(MESSAGE, "参数错误") ;
					return jsonObject.toString() ;
				}
				if("sign".equals(key) == false &&"action".equals(key) == false){
					paramTreeSet.add(key+"="+values[0]) ;
				}
			}
			
			StringBuffer paramString = new StringBuffer() ;
			for (String p : paramTreeSet) {
				if(paramString.length()>0){
					paramString.append("&") ;
				}
				paramString.append(p) ;
			}
			paramString.append("&secret_key="+fapi.getFsecret()) ;
			String md5 = Utils.getMD5_32_xx(paramString.toString()).toUpperCase() ;
			
			if(md5.equals(sign) == false ){
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE , -10007) ;
				jsonObject.accumulate(MESSAGE, "签名错误") ;
				return jsonObject.toString() ;
			}
		}
		
		String admin = request.getParameter("admin").toString() ;
		List<Fadmin> admins = this.adminService.findByProperty("fname", admin);
		Fadmin fadmin = null;
		if(admins == null || admins.size() !=1){
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, -10008) ;
			jsonObject.accumulate(MESSAGE, "管理员不存在") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			return jsonObject.toString();
		}else{
			fadmin = admins.get(0) ;
			int userid = fadmin.getFuserid();
			if(userid == 0 || fuser.getFid()!=userid){
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE, -10009) ;
				jsonObject.accumulate(MESSAGE, "管理员无权限进行后台API操作") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
		}
		
		String ret = "" ;
		switch (actionInteger) {
		case 0 :
			{
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE , -10002) ;
				jsonObject.accumulate(MESSAGE, "API不存在") ;
				ret = jsonObject.toString() ;
			}
			break ;
			
		default:
			try {
				Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class,Fapi.class,Fadmin.class) ;
				ret = (String)method.invoke(this, request,fapi,fadmin) ;
			} catch (Exception e) {
				ret = APIConstant.getUnknownError(e) ;
			}
			break ;	
		}
		
		if(Constant.isRelease == false )
		{
			System.out.println(ret);
		}
		return ret ;
	}
	
	public String coolist(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "查询最新的提现申请") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			
			StringBuffer filterSQL = new StringBuffer();
			filterSQL.append("where 1=1 and ftype="
					+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
			filterSQL.append("and fstatus IN("
					+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
					+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")\n");
			JSONArray data = new JSONArray() ;
			List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
					.list(0, 50, filterSQL
							+ "", true);
			for (Fvirtualcaptualoperation vcout : list) {
				JSONObject item = new JSONObject() ;
				item.accumulate("id", vcout.getFid()) ;
				item.accumulate("cointype", vcout.getFvirtualcointype().getfShortName()) ;
				item.accumulate("uid", vcout.getFuser().getFid()) ;
				item.accumulate("username", vcout.getFuser().getFrealName()) ;
				item.accumulate("amount", vcout.getFamount()) ;
				item.accumulate("fee", vcout.getFfees()) ;
				item.accumulate("status", vcout.getFstatus()) ;
				item.accumulate("status_s", vcout.getFstatus_s()) ;
				item.accumulate("address", vcout.getWithdraw_virtual_address()) ;
				item.accumulate("remark", vcout.getFischarge()) ;
				item.accumulate("createtime", vcout.getFcreateTime().toString()) ;
				data.add(item) ;
			}
			jsonObject.accumulate(DATA, data) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String coodetail(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "提现申请详情") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			
			Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService
					.findById(fid);
			
			int userId = virtualcaptualoperation.getFuser().getFid();
			int coinId = virtualcaptualoperation.getFvirtualcointype().getFid();
			Fvirtualcointype fcoin = this.virtualCoinService.findById(coinId);
			double tixianzhi = Utils.getDouble(virtualcaptualoperation.getFamount()+virtualcaptualoperation.getFfees(), 6);
			//成功买入、卖出、挂单不可用的币数
			double buycount = 0d;
			double sellcount =0d;
			double sellfrozen =0d;
			String s1 = "";
			
			//提取币种的所有交易市场
			List<Ftrademapping> tmps = this.tradeMappingService.list(0,10,"where fvirtualcointypeByFvirtualcointype2.fid="+coinId, false);
			if (tmps.size()>0){
				String trdmpIds = "";
				for(Ftrademapping tradmapping:tmps){
					if (trdmpIds.equals("")){
						trdmpIds = tradmapping.getFid().toString();
					}else{
						trdmpIds = trdmpIds +"," +tradmapping.getFid().toString();
					}
				}
				
				s1 = "SELECT sum(fCount-fleftCount) from fentrust where fEntrustType=0 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
				buycount = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT sum(fCount-fleftCount) from fentrust where fEntrustType=1 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
				sellcount = this.adminService.getSQLValue(s1);
				
				s1 = "SELECT sum(fleftCount) from fentrust where fEntrustType=1 and fStatus in (1,2) and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
				sellfrozen = this.adminService.getSQLValue(s1);
			}
			
			double fabiusecount = 0d;
			double fabigetcount = 0d;
			double fabifrozen = 0d;
			
			//如果是法币的虚拟币
			if (coinId==1 || coinId==3){
				
				List<Ftrademapping> fbtmps = this.tradeMappingService.list(0,10,"where fstatus=1 and fvirtualcointypeByFvirtualcointype1.fid="+coinId, false);
				if (tmps.size()>0){
					String trdmpIds = "";
					for(Ftrademapping tradmapping:fbtmps){
						if (trdmpIds.equals("")){
							trdmpIds = tradmapping.getFid().toString();
						}else{
							trdmpIds = trdmpIds +"," +tradmapping.getFid().toString();
						}
					}
					
					s1 = "SELECT sum(fsuccessAmount) from fentrust where fEntrustType=0 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					fabiusecount = this.adminService.getSQLValue(s1);
					
					s1 = "SELECT sum(fsuccessAmount) from fentrust where fEntrustType=1 and fStatus<>1 and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					fabigetcount = this.adminService.getSQLValue(s1);
					
					s1 = "SELECT sum(fAmount-fsuccessAmount) from fentrust where fEntrustType=0 and fStatus in (1,2) and ftrademapping in ("+trdmpIds+") and FUs_fId=" + userId;
					fabifrozen = this.adminService.getSQLValue(s1);
				}
				
			}
			
			
			
			//充值、提现、提币冻结的币数
			double rechargecount =0d;
			double withdrawcount =0d;
			double withdrawfrozen =0d;
			double rewardcount =0d;
			
			double wallettotal =0d;
			double walletfrozen =0d;
			
			s1 = "SELECT sum(fAmount) from fvirtualcaptualoperation where fType=1 and fStatus =3 and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
			rechargecount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(fAmount+ffees) from fvirtualcaptualoperation where fType=2 and fStatus =3 and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
			withdrawcount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(fAmount+ffees) from fvirtualcaptualoperation where fType=2 and fStatus in (1,2) and fVi_fId2="+coinId + " and FUs_fId2=" +userId;
			withdrawfrozen = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT sum(fqty) from fintrolinfo where fname='"+fcoin.getFname() + "' and fuserid=" +userId;
			rewardcount = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT fTotal from fvirtualwallet where fVi_fId="+coinId+ " and fuid=" +userId;
			wallettotal = this.adminService.getSQLValue(s1);
			
			s1 = "SELECT fFrozen from fvirtualwallet where fVi_fId="+coinId+ " and fuid=" +userId;
			walletfrozen = this.adminService.getSQLValue(s1);
			

			BigDecimal a1 = new BigDecimal(Double.toString(rewardcount+buycount+rechargecount+fabigetcount));
			BigDecimal a2 = new BigDecimal(Double.toString(sellcount+withdrawcount+sellfrozen+withdrawfrozen+fabiusecount+fabifrozen));
			
			boolean teshuflag = false;
			
			Fuser vcfuser = this.frontUserService.findById(userId);
			if (vcfuser.isFistiger() || vcfuser.getFscore().getFlevel()>=3){
				teshuflag = true;
			}
			
			JSONObject data = new JSONObject() ;
			data.accumulate("uid",vcfuser.getFid()) ;
			data.accumulate("username",vcfuser.getFrealName()) ;
			data.accumulate("loginname",vcfuser.getFloginName()) ;
			data.accumulate("amount",virtualcaptualoperation.getFamount()) ;
			data.accumulate("fee",virtualcaptualoperation.getFfees()) ;
			data.accumulate("address",virtualcaptualoperation.getWithdraw_virtual_address()) ;
			data.accumulate("classname",fcoin.getFclassPath());
			data.accumulate("parentid",fcoin.getParentCid());
			
			data.accumulate("buycount",buycount) ;
			data.accumulate("sellcount", sellcount) ;
			data.accumulate("sellfrozen", sellfrozen) ;
			data.accumulate("rechargecount", rechargecount) ;
			data.accumulate("withdrawcount", withdrawcount) ;
			data.accumulate("withdrawfrozen", withdrawfrozen) ;
			data.accumulate("rewardcount", rewardcount) ;
			data.accumulate("wallettotal", wallettotal) ;
			data.accumulate("walletfrozen", walletfrozen) ;
			data.accumulate("fabigetcount", fabigetcount) ;
			data.accumulate("fabiusecount", fabiusecount) ;
			data.accumulate("fabifrozen", fabifrozen) ;
			data.accumulate("youxiaoshu", a1.subtract(a2).doubleValue()) ;
			data.accumulate("tixianzhi", tixianzhi) ;
			data.accumulate("teshuflag",teshuflag) ;
			jsonObject.accumulate(DATA, data) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String coollock(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			
			Fvirtualcaptualoperation fvirtualcaptualoperation = this.virtualCapitaloperationService
					.findById(fid);
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
			int userId = fvirtualcaptualoperation.getFuser().getFid();
			Fvirtualcointype fvirtualcointype = fvirtualcaptualoperation
					.getFvirtualcointype();
			int coinTypeId = fvirtualcointype.getFid();
			List<Fvirtualwallet> virtualWallet = this.virtualWalletService
					.findByTwoProperty("fuser.fid", userId, "fvirtualcointype.fid",
							coinTypeId);
			if (virtualWallet == null || virtualWallet.size() == 0) {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE, -300) ;
				jsonObject.accumulate(MESSAGE, "钱包异常") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			int status = fvirtualcaptualoperation.getFstatus();
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE,-400) ;
				jsonObject.accumulate(MESSAGE, "锁定失败，钱包状态不为等待提现") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			String confirmCode = request.getParameter("confirmcode");
			if (confirmCode!=null && !confirmCode.equals("")){
				String confirmCodeMD5 = Utils.MD5(confirmCode,fadmin.getSalt());
				if (!confirmCodeMD5.equals(fadmin.getFconfirmcode())) {
					JSONObject jsonObject = new JSONObject() ;
					jsonObject.accumulate(CODE,-500) ;
					jsonObject.accumulate(MESSAGE, "审核失败，审核码错误，请仔细检查!") ;
					jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
					return jsonObject.toString() ;
				}
			}else{
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE,-600) ;
				jsonObject.accumulate(MESSAGE, "审核失败，审核码不能为空") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationLock);
			boolean flag = false;
			try {
				this.virtualCapitaloperationService
						.updateObj(fvirtualcaptualoperation);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject() ;
			if (flag) {
				
				jsonObject.accumulate(CODE,200) ;
				jsonObject.accumulate(MESSAGE, "提现锁定成功") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			} else {
				jsonObject.accumulate(CODE,-1) ;
				jsonObject.accumulate(MESSAGE, "未知错误") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			}
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String coolunlock(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			
			Fvirtualcaptualoperation fvirtualcaptualoperation = this.virtualCapitaloperationService
					.findById(fid);
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
			int userId = fvirtualcaptualoperation.getFuser().getFid();
			Fvirtualcointype fvirtualcointype = fvirtualcaptualoperation
					.getFvirtualcointype();
			int coinTypeId = fvirtualcointype.getFid();
			List<Fvirtualwallet> virtualWallet = this.virtualWalletService
					.findByTwoProperty("fuser.fid", userId, "fvirtualcointype.fid",
							coinTypeId);
			if (virtualWallet == null || virtualWallet.size() == 0) {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE, -300) ;
				jsonObject.accumulate(MESSAGE, "钱包异常") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			int status = fvirtualcaptualoperation.getFstatus();
			if (status != CapitalOperationOutStatus.OperationLock) {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(CODE,-400) ;
				jsonObject.accumulate(MESSAGE, "取消锁定失败，钱包状态不为锁定") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation);
			boolean flag = false;
			try {
				this.virtualCapitaloperationService
						.updateObj(fvirtualcaptualoperation);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONObject jsonObject = new JSONObject() ;
			
			if (flag) {
				
				jsonObject.accumulate(CODE,200) ;
				jsonObject.accumulate(MESSAGE, "取消锁定成功") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			} else {
				jsonObject.accumulate(CODE,-1) ;
				jsonObject.accumulate(MESSAGE, "未知错误") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			}
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String coolaudit(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			JSONObject jsonObject = new JSONObject() ;
			Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
			int status = virtualcaptualoperation.getFstatus();
			if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
				jsonObject.accumulate(CODE,-400) ;
				jsonObject.accumulate(MESSAGE, "审核定失败，钱包状态不为锁定") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			//校验审核码，审核码正确才能进行人民币审核操作
			String confirmCode = request.getParameter("confirmcode");
			if (confirmCode!=null && !confirmCode.equals("")){
				String confirmCodeMD5 = Utils.MD5(confirmCode,fadmin.getSalt());
				if (!confirmCodeMD5.equals(fadmin.getFconfirmcode())) {
					jsonObject.accumulate(CODE,-500) ;
					jsonObject.accumulate(MESSAGE, "审核失败，审核码错误，请仔细检查!") ;
					jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate(CODE,-600) ;
				jsonObject.accumulate(MESSAGE, "审核失败，审核码不能为空!") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			// 根据用户查钱包最后修改时间
			int userId = virtualcaptualoperation.getFuser().getFid();
			Fvirtualcointype fvirtualcointype = virtualcaptualoperation
					.getFvirtualcointype();
			int coinTypeId = fvirtualcointype.getFid();

			Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
			double amount = Utils.getDouble(virtualcaptualoperation.getFamount()+virtualcaptualoperation.getFfees(), 4);
			double frozenRmb = Utils.getDouble(virtualWalletInfo.getFfrozen(), 4);
			if (frozenRmb-amount < -0.0001) {
				jsonObject.accumulate(CODE,-700) ;
				jsonObject.accumulate(MESSAGE, "审核失败,冻结数量:" + frozenRmb
						+ "小于提现数量:" + amount + "，操作异常!") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			if(virtualcaptualoperation.getFtradeUniqueNumber() != null &&
	        		virtualcaptualoperation.getFtradeUniqueNumber().trim().length() >0){
				jsonObject.accumulate(CODE,-800) ;
				jsonObject.accumulate(MESSAGE, "提现记录已经存在交易ID，非法操作！请检查钱包！") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
	        }
			/*if(virtualcaptualoperation.getfTransferOrderId() != null &&
					virtualcaptualoperation.getfTransferOrderId().trim().length() >0){
				jsonObject.accumulate(CODE,-800) ;
				jsonObject.accumulate(MESSAGE, "提现记录已经存在交易ID，非法操作！请检查钱包！") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}*/
			
			String password = request.getParameter("password");
			if(password == null || password.trim().length() ==0){
				jsonObject.accumulate(CODE,-800) ;
				jsonObject.accumulate(MESSAGE, "请输入钱包密码!") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
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
				jsonObject.accumulate(CODE,-800) ;
				jsonObject.accumulate(MESSAGE, "钱包连接失败，请检查配置信息") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			WalletUtil walletf = WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), walletmsg);
			
			
			boolean flag = false ;
			try {
				flag = walletf.unlockWallet(fvirtualcointype.getMainAddr().trim());
				walletf.lockWallet(fvirtualcointype.getMainAddr().trim()) ;
			} catch (Exception e1) {
				
			}
			/*boolean flag = false ;
			String encryptPasswors = WalletAPI.encryptPasswors(password);
			try {
				JSONObject walletType = WalletAPI.getWalletType();
				Map<String, Object> params = new HashMap<String, Object>();
				String coinType = fvirtualcointype.getfShortName();
				Long parentId = fvirtualcointype.getParentCid();
				if(0 != parentId){
					coinType = this.virtualCoinService.findById(parentId.intValue()).getfShortName();
				}
				params.put("walletTypeId", walletType.get(coinType));
				params.put("walletPassword", encryptPasswors);
				JSONObject result = WalletAPI.main("validatePassword", params );
				if(null != result && 200 == result.getInt("code")){
					System.out.println(result.get("data").toString());
					flag = Boolean.valueOf(result.get("data").toString());
				}
			} catch (Exception e) {
				jsonObject.accumulate(CODE,-800) ;
				jsonObject.accumulate(MESSAGE, "钱包连接失败，请检查配置信息") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}*/
			
			if(flag == false ){
				jsonObject.accumulate(CODE,-800) ;
				jsonObject.accumulate(MESSAGE, "钱包链接错误，或密码错误") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
			
			// 钱包操作
			virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
			virtualWalletInfo.setFfrozen(virtualWalletInfo.getFfrozen() - amount);
			try {
				this.virtualCapitaloperationService.updateCapital(
						virtualcaptualoperation, virtualWalletInfo, walletf,fvirtualcointype);
				/*this.virtualCapitaloperationService.updateCapital(
						virtualcaptualoperation, virtualWalletInfo,encryptPasswors,fvirtualcointype);*/
			} catch (Exception e) {
				jsonObject.accumulate(CODE,-900) ;
				jsonObject.accumulate(MESSAGE, "未知错误："+e.getMessage()) ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			jsonObject.accumulate(CODE,200) ;
			jsonObject.accumulate(MESSAGE, "审核成功！") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String reg(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			String id = request.getParameter("id").toString();
			String ip = request.getParameter("ip").toString();
			String type = request.getParameter("type").toString();
			String pid = request.getParameter("pid").toString();
			
			boolean flag = false;
			if(type.equals("0")) {
				flag = this.frontUserService.isTelephoneExists(id) ;
				flag = !id.matches(Constant.PhoneReg);
			}else {
				flag = this.frontUserService.isEmailExists(id) ;
				flag = !EmailValidator.getInstance().isValid(id);
			}
			
			if(flag) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作失败，数据重复或有误") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			//推广
			Fuser intro = null ;
			
			try {
				if(pid!=null && !"".equals(pid.trim())){
					List<Fuser> introList = this.frontUserService.findUserByProperty("fuserNo", pid) ;
					if(introList!=null&&introList.size()>0){
						intro=introList.get(0);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			Fuser fuser = new Fuser() ;
			if(intro!=null){
				fuser.setfIntroUser_id(intro) ;
			}
			fuser.setFintrolUserNo(null);
			if(type.equals("0")){
				//手机注册
				fuser.setFregType(RegTypeEnum.TEL_VALUE);
				fuser.setFtelephone(id);
				fuser.setFareaCode("86");
				fuser.setFisTelephoneBind(true);
				
				fuser.setFnickName(id) ;
				fuser.setFloginName(id) ;
			}else{
				fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
				fuser.setFemail(id) ;
				fuser.setFisMailValidate(true) ;
				fuser.setFnickName(id.split("@")[0]) ;
				fuser.setFloginName(id) ;
			}
			
			fuser.setSalt(Utils.getUUID());
			fuser.setFregisterTime(Utils.getTimestamp()) ;
			fuser.setFloginPassword(Utils.MD5("Qq@1234_K",fuser.getSalt())) ;
			fuser.setFtradePassword(null) ;
			fuser.setFregIp(ip);
			fuser.setFlastLoginIp(ip);
			fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
			fuser.setFlastLoginTime(Utils.getTimestamp()) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			if(Constant.openSecondValidate.equals("true")){
				fuser.setFopenSecondValidate(true);
			}else{
				fuser.setFopenSecondValidate(false);
			}
			
			Fuser saveUser = null ;
			try {
				saveUser = this.frontUserService.saveRegister(fuser) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(saveUser!=null){
				jsonObject.accumulate(CODE, 200) ;
				jsonObject.accumulate(MESSAGE, "操作成功") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				jsonObject.accumulate(DATA, saveUser.getFid()) ;
				//添加一笔登陆记录
				this.frontUserService.updateFUser(saveUser, 1, ip);
			}else{
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作失败") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			}
			

			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String kyc1(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			String realName = request.getParameter("real").toString();
			String identityNo = request.getParameter("idNo").toString().toLowerCase();
			String ip = request.getParameter("ip").toString();
			
			realName = HtmlUtils.htmlEscape(realName);
			Fuser fuser = this.frontUserService.findById(fid) ;
			if(fuser==null) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，用户不存在") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			}
			
			String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
	                "3", "2" };  
	        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
	                "9", "10", "5", "8", "4", "2" };  
			if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，号码长度不符") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			
			String Ai = "";
	        if (identityNo.length() == 18) {  
	            Ai = identityNo.substring(0, 17);  
	        } else if (identityNo.length() == 15) {  
	            Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);  
	        }

	        if (Utils.isNumeric(Ai) == false) {  
	        	jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，号码格式不符") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
	        }
	        // ================ 出生年月是否有效 ================  
	        String strYear = Ai.substring(6, 10);// 年份  
	        String strMonth = Ai.substring(10, 12);// 月份  
	        String strDay = Ai.substring(12, 14);// 月份  
	        if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {  
	        	jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，号码格式不符") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
	        }  
	        GregorianCalendar gc = new GregorianCalendar();  
	        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
	        try {
	            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 60  
	                    || (gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) <18) {
	            	jsonObject.accumulate(CODE, 300) ;
					jsonObject.accumulate(MESSAGE, "操作错误，号码超出18至60的范围") ;
					jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
					return jsonObject.toString();
	            }
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
	        	jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，号码格式不符") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
	        }
	        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
	        	jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，号码格式不符") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
	        }  
	        // =====================(end)=====================  
	  
	        // ================ 地区码时候有效 ================  
	        Hashtable h = Utils.getAreaCode();
	        if (h.get(Ai.substring(0, 2)) == null) {
	        	jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，号码格式不符") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
	        }
	        // ==============================================
	  
	        // ================ 判断最后一位的值 ================  
	        int TotalmulAiWi = 0;
	        for (int i = 0; i < 17; i++) {
	            TotalmulAiWi = TotalmulAiWi
	                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
	                    * Integer.parseInt(Wi[i]);
	        }
	        int modValue = TotalmulAiWi % 11;
	        String strVerifyCode = ValCodeArr[modValue];
	        Ai = Ai + strVerifyCode;
	        
	        if (identityNo.length() == 18) {
	            if (Ai.equals(identityNo) == false) {
	            	jsonObject.accumulate(CODE, 300) ;
					jsonObject.accumulate(MESSAGE, "操作错误，号码格式不符") ;
					jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
					return jsonObject.toString();
	            }
	        } else {
	        	jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，不支持15位") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString(); 
	        }
	        
	        if (realName.trim().length() > 32) {
	        	jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，名称长度不符") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			
			String sql = "where fidentityNo='"+identityNo+"'";
			int count = this.adminService.getAllCount("Fuser", sql);
			if(count >0){
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，号码已存在") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			
			fuser.setFidentityType(0) ;
			fuser.setFidentityNo(identityNo) ;
			fuser.setFrealName(realName) ;
			fuser.setFpostRealValidate(true) ;
			fuser.setFareaCode("86");
			fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
			fuser.setFhasRealValidate(true);
			fuser.setFhasRealValidateTime(Utils.getTimestamp());
			fuser.setFisValid(true);
			
			try {
				this.frontUserService.updateFUser(fuser, LogTypeEnum.User_CERT,ip) ;
				int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"KYC1认证奖励");
				if (sendcount==0 ) {
					Fintrolinfo introlInfo = null;
					Fvirtualwallet fvirtualwallet = null;
					String[] auditSendCoin = frontConstantMapService.getString("kyc1SendCoin").split("#");
					int coinID = Integer.parseInt(auditSendCoin[0]);
					double coinQty = Double.valueOf(auditSendCoin[1]);
					//如果设置了KYC1认证奖励，且币种存在的话，则进行奖励
					Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinID);
					if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid());
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(coinQty);
						introlInfo.setFuser(fuser);
						introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_REG);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						introlInfo.setFtitle("KYC1认证奖励");
					}
					this.userService.updateObj(null, introlInfo, fvirtualwallet);
				}
			} catch (Exception e) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，KYC1认证失败") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String kyc2(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			String ip = request.getParameter("ip").toString();
			
			Fuser fuser = frontUserService.findById(fid);
			
			if(fuser==null) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，用户不存在") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			
			fuser.setFpostImgValidate(true);
			fuser.setFhasImgValidate(true);
			this.frontUserService.updateFUser(fuser, LogTypeEnum.User_CERT,ip) ;
			
			Fuser fintrolUser = null;
			Fintrolinfo introlInfo = null;
			Fvirtualwallet fvirtualwallet = null;
			Fintrolinfo introlInfoIntro = null;
			Fvirtualwallet fvirtualwalletIntro = null;
			Fscore fscore = fuser.getFscore();
			
			String[] auditSendCoin = frontConstantMapService.getString("kyc2SendCoin").split("#");
			String[] introlSendCoin = frontConstantMapService.getString("introlSendCoin").split("#");
			int coinID = Integer.parseInt(auditSendCoin[0]);
			double coinQty = Double.valueOf(auditSendCoin[1]);
			int coinIDIntro = Integer.parseInt(introlSendCoin[0]);
			double coinQtyIntro = Double.valueOf(introlSendCoin[1]);
			
			int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"KYC2认证奖励");
			//kyc2认证奖励只奖励一次，同时需要检测fscore的设定
			if (sendcount==0 && !fscore.isFissend()) {
				fscore.setFissend(true);
				
				if(fuser.getfIntroUser_id() != null){
					fintrolUser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid());
					fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
				}
				//如果设置了KYC2认证奖励，且币种存在的话，则进行奖励
				Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinID);
				if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
					fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid());
					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
					introlInfo = new Fintrolinfo();
					introlInfo.setFcreatetime(Utils.getTimestamp());
					introlInfo.setFiscny(false);
					introlInfo.setFqty(coinQty);
					introlInfo.setFuser(fuser);
					introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_CANDY);
					introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
					introlInfo.setFtitle("KYC2认证奖励");
				}
				//如果存在邀请人，且邀请奖励存在的话
				fvirtualcointype=frontVirtualCoinService.findById(coinIDIntro);
				if (coinIDIntro>0 && coinQtyIntro>0 && null!=fintrolUser && null!=fvirtualcointype){
					fvirtualwalletIntro = this.frontUserService.findVirtualWalletByUser(fintrolUser.getFid(), coinIDIntro);
					fvirtualwalletIntro.setFtotal(fvirtualwalletIntro.getFtotal()+coinQtyIntro);
					introlInfoIntro = new Fintrolinfo();
					introlInfoIntro.setFcreatetime(Utils.getTimestamp());
					introlInfoIntro.setFiscny(false);
					introlInfoIntro.setFqty(coinQtyIntro);
					introlInfoIntro.setFuser(fintrolUser);
					introlInfoIntro.setFname(fvirtualwalletIntro.getFvirtualcointype().getFname());
					introlInfoIntro.setFtype(IntrolInfoTypeEnum.INTROL_INTROL);
					introlInfoIntro.setFtitle("推荐用户注册获得奖励"+coinQtyIntro+"个"+fvirtualwalletIntro.getFvirtualcointype().getFname());
				}
			}
			
			this.userService.updateObj(null, fscore, fintrolUser, fvirtualwallet, introlInfo, fvirtualwalletIntro, introlInfoIntro);
			
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String tradepwd(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			String ip = request.getParameter("ip").toString();
			
			Fuser fuser = frontUserService.findById(fid);
			if(fuser==null) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，用户不存在") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，已经设置交易密码") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			
			fuser.setFtradePassword(Utils.MD5("123@Qq_com.KkK",fuser.getSalt())) ;
			this.frontUserService.updateFUser(fuser,LogTypeEnum.User_SET_TRADE_PWD,ip) ;
			
			//设置交易密码奖励发放
			int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"设置交易密码奖励");
			if (sendcount==0 ) {
				Fintrolinfo introlInfo = null;
				Fvirtualwallet fvirtualwallet = null;
				String[] auditSendCoin = frontConstantMapService.getString("tradePassWordSendCoin").split("#");
				int coinID = Integer.parseInt(auditSendCoin[0]);
				double coinQty = Double.valueOf(auditSendCoin[1]);
				Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinID);
				if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
					fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid());
					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
					introlInfo = new Fintrolinfo();
					introlInfo.setFcreatetime(Utils.getTimestamp());
					introlInfo.setFiscny(false);
					introlInfo.setFqty(coinQty);
					introlInfo.setFuser(fuser);
					introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_CANDY);
					introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
					introlInfo.setFtitle("设置交易密码奖励");
				}
				this.userService.updateObj(null, introlInfo, fvirtualwallet);
			}
			
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String google(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			String ip = request.getParameter("ip").toString();
			
			Fuser fuser = this.frontUserService.findById(fid) ;
			if(fuser==null) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，用户不存在") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			
			if(fuser.getFgoogleBind()){
				//已经绑定机器了，属于非法提交
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，已经绑定") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString() ;
			}
			
			Map<String, String> map = GoogleAuth.genSecret(fuser.getFloginName()) ;
			String totpKey = map.get("secret") ;
			String qecode = map.get("url") ;
			
			fuser.setFgoogleAuthenticator(totpKey) ;
			fuser.setFgoogleurl(qecode) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			fuser.setFgoogleBind(true) ;
			fuser.setFgoogleValidate(false) ;
			this.frontUserService.updateFUser(fuser,LogTypeEnum.User_BIND_GOOGLE,ip) ;
			
			//谷歌认证奖励
			int sendcount = this.utilsService.count(" where fuser.fid=? and ftitle=?", Fintrolinfo.class, fuser.getFid(),"谷歌认证奖励");
			if (sendcount==0 ) {
				Fintrolinfo introlInfo = null;
				Fvirtualwallet fvirtualwallet = null;
				String[] auditSendCoin = frontConstantMapService.getString("googleSendCoin").split("#");
				int coinID = Integer.parseInt(auditSendCoin[0]);
				double coinQty = Double.valueOf(auditSendCoin[1]);
				//如果设置了谷歌认证奖励，且币种存在的话，则进行奖励
				Fvirtualcointype fvirtualcointype=frontVirtualCoinService.findById(coinID);
				if(coinID>0 && coinQty>0 && null!=fvirtualcointype) {
					fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid());
					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
					introlInfo = new Fintrolinfo();
					introlInfo.setFcreatetime(Utils.getTimestamp());
					introlInfo.setFiscny(false);
					introlInfo.setFqty(coinQty);
					introlInfo.setFuser(fuser);
					introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_CANDY);
					introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
					introlInfo.setFtitle("谷歌认证奖励");
				}
				this.userService.updateObj(null, introlInfo, fvirtualwallet);
			}
			
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String recharge(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			
			jsonObject.accumulate(DATA, "") ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String luckydraw(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			String ip = request.getParameter("ip").toString();
			Fuser fuser = this.frontUserService.findById(fid) ;
			if(fuser==null) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，用户不存在") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			if (fuser.getFstatus() != 1) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，用户禁用") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			if (!fuser.getFhasRealValidate() || !fuser.getFpostRealValidate()) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，未进行KYC1认证") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();

			}
			if (!fuser.isFhasImgValidate() || !fuser.isFpostImgValidate()) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，未进行KYC2认证") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			FactivityModel factivityModel = frontActivitiesService.findById(1);
			if (factivityModel == null || factivityModel.getStatus()!= FactivityStatusEnum.open) {
				jsonObject.accumulate(CODE, 300) ;
				jsonObject.accumulate(MESSAGE, "操作错误，活动不存在或者未开启") ;
				jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
				return jsonObject.toString();
			}
			String str = frontActivitiesService.saveLottery(fuser.getFid(), 1);
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			jsonObject.accumulate(DATA, JSONObject.fromObject(str)) ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String bindphone(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			
			jsonObject.accumulate(DATA, "") ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	public String bindemail(HttpServletRequest request,Fapi fapi,Fadmin fadmin) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(CODE, 200) ;
			jsonObject.accumulate(MESSAGE, "操作成功") ;
			jsonObject.accumulate(TIME, Utils.getTimestamp().getTime()) ;
			Integer fid = Integer.valueOf(request.getParameter("id").toString());
			
			jsonObject.accumulate(DATA, "") ;
			return jsonObject.toString() ;
		}catch(Exception e){
			return APIConstant.getUnknownError(e) ;
		}
	}
	
	
	
}
