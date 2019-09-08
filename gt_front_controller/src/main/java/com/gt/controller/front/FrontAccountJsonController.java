package com.gt.controller.front;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.gt.Enum.CapitalOperationInStatus;
import com.gt.Enum.CapitalOperationOutStatus;
import com.gt.Enum.CapitalOperationTypeEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.Enum.MessageTypeEnum;
import com.gt.Enum.ProductPeriodEnum;
import com.gt.Enum.RemittanceTypeEnum;
import com.gt.Enum.SystemBankInfoEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.controller.BaseController;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fpool;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwithdrawfees;
import com.gt.entity.Pproduct;
import com.gt.entity.Systembankinfo;
import com.gt.sdk.AliyunCheck;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.PoolService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.admin.VirtualaddressService;
import com.gt.service.admin.WithdrawFeesService;
import com.gt.service.front.FrontAccountService;
import com.gt.service.front.FrontBankInfoService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontPproductService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontValidateMapService;
import com.gt.service.front.FrontValidateService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FvirtualWalletService;
import com.gt.util.DateSupport;
import com.gt.util.Utils;

import net.sf.json.JSONObject;



@Controller
public class FrontAccountJsonController extends BaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(FrontAccountJsonController.class);

	private static final String CLASS_NAME = FrontAccountJsonController.class.getSimpleName();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontBankInfoService frontBankInfoService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;

	@Autowired
	private AdminService adminService;
	@Autowired
	private FrontValidateMapService frontValidateMapService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FvirtualWalletService virtualWalletService;	
	@Autowired
	private PoolService poolService;
	@Autowired
	private VirtualaddressService virtualaddressService;
	@Autowired
	private FrontPproductService pproductService;
	
	//会员用户自己充值人民币
	@RequestMapping(value="/account/alipayManual",produces = {JsonEncode})
	@ResponseBody
	public String alipayManual(
			HttpServletRequest request,
			@RequestParam(required=true) double money,
			@RequestParam(required=true) int type,
			@RequestParam(required=true) int sbank
			) throws Exception{
		LOGGER.info(CLASS_NAME + " alipayManual,会员用户自己充值人民币,入参money:{},type:{},sbank:{}", money, type, sbank);
		JSONObject jsonObject = new JSONObject() ;
		money = Utils.getDoubleUp(money, 2);
		double minRecharge = Double.parseDouble(this.frontConstantMapService.get("minrechargecny").toString()) ;
		double maxRecharge = Double.parseDouble(this.frontConstantMapService.get("maxrechargecny").toString().trim()) ;
		LOGGER.info(CLASS_NAME + " alipayManual,配置最小充值金额为minRecharge:{}", minRecharge);
		LOGGER.info(CLASS_NAME + " alipayManual,配置最大充值金额为maxRecharge:{}", maxRecharge);
		if(money < minRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			Object[] params = new Object[]{minRecharge};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.minrecharge"));
			return jsonObject.toString();
		}
		
		if(money > maxRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			Object[] params = new Object[]{maxRecharge};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.maxrecharge"));
			return jsonObject.toString();
		}
		
		if(type != 0){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation"));
			return jsonObject.toString();
		}
		
		Systembankinfo systembankinfo = this.frontBankInfoService.findSystembankinfoById(sbank) ;
		LOGGER.info(CLASS_NAME + " alipayManual,查询银行账户信息systembankinfo:{}", systembankinfo);
		if(systembankinfo==null || systembankinfo.getFstatus()==SystemBankInfoEnum.ABNORMAL ){
			LOGGER.info(CLASS_NAME + " alipayManual,查询银行账户信息为空或账户停用");
			//银行账号停用
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.nobank"));
			return jsonObject.toString();
		}
		
		Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
		fcapitaloperation.setFamount(money) ;
		fcapitaloperation.setSystembankinfo(systembankinfo) ;
		fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
		fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_IN) ;
		fcapitaloperation.setFuser(this.GetSession(request)) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.NoGiven) ;
		fcapitaloperation.setFremittanceType(systembankinfo.getFbankName());
		int num = (int) (Math.random() * 100000);
		fcapitaloperation.setFremark(String.format("%05d", num));
		//LOGGER.info(CLASS_NAME + " alipayManual,保存到数据库的人民币充值流水fcapitaloperation:{}", new Gson().toJson(fcapitaloperation));
		this.frontAccountService.addFcapitaloperation(fcapitaloperation) ;
		LOGGER.info(CLASS_NAME + " alipayManual,保存完毕，开始拼装返回对象");
		jsonObject.accumulate("code", 0);
		jsonObject.accumulate("money", String.valueOf(fcapitaloperation.getFamount())) ;
		jsonObject.accumulate("tradeId", fcapitaloperation.getFid()) ;
		jsonObject.accumulate("remark", fcapitaloperation.getFremark()) ;
		jsonObject.accumulate("fbankName", systembankinfo.getFbankName()) ;
		jsonObject.accumulate("fownerName", systembankinfo.getFownerName()) ;
		jsonObject.accumulate("fbankAddress", systembankinfo.getFbankAddress()) ;
		jsonObject.accumulate("fbankNumber", systembankinfo.getFbankNumber().replaceAll("\\d{4}(?!$)", "$0 ")) ;
		LOGGER.info(CLASS_NAME + " alipayManual,返回对象jsonObject:{}", jsonObject.toString());
		return jsonObject.toString() ;  
	}
	
	
	@ResponseBody
	@RequestMapping(value="/account/alipayTransfer",produces={JsonEncode})
	public String alipayTransfer(
			HttpServletRequest request,
			@RequestParam(required=true) double money,
			@RequestParam(required=true) String accounts,
			@RequestParam(required=true) String imageCode,
			@RequestParam(required=true) int type
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		accounts= HtmlUtils.htmlEscape(accounts.trim());
		money = Utils.getDouble(money, 2);
		if(type !=3 && type !=4){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation"));
			return jsonObject.toString();
		}
		if(accounts.length() >100){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.wrongaccount"));
			return jsonObject.toString();
		}
		double minRecharge = Double.parseDouble(this.frontConstantMapService.get("minrechargecny").toString()) ;
		if(money < minRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			Object[] params = new Object[]{minRecharge};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.minrecharge"));
			return jsonObject.toString();
		}
		
		if(vcodeValidate(request, imageCode) == false ){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.verinfo"));
			return jsonObject.toString() ;
		}
		
		Fcapitaloperation fcapitaloperation = new Fcapitaloperation();
		fcapitaloperation.setFuser(GetSession(request));
		fcapitaloperation.setFamount(money);
		fcapitaloperation.setFremittanceType(RemittanceTypeEnum.getEnumString(type));
		fcapitaloperation.setfBank(RemittanceTypeEnum.getEnumString(type)) ;
		fcapitaloperation.setfAccount(accounts) ;
		fcapitaloperation.setfPayee(null) ;
		fcapitaloperation.setfPhone(null) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
		fcapitaloperation.setFcreateTime(Utils.getTimestamp());
		fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp());
		fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_IN);
		fcapitaloperation.setFfees(0d);
		fcapitaloperation.setFischarge(false);
		try {
			this.frontAccountService.updateSaveCapitalOperation(fcapitaloperation) ;
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
		}
		
		return jsonObject.toString();
	}
	@ResponseBody
	@RequestMapping("/account/cancelWithdrawcny")
	public String cancelWithdrawcny(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation!=null
				&&fcapitaloperation.getFuser().getFid() ==GetSession(request).getFid()
				&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_OUT
				&&fcapitaloperation.getFstatus()==CapitalOperationOutStatus.WaitForOperation){
			try {
				this.frontAccountService.updateCancelWithdrawCny(fcapitaloperation, this.frontUserService.findById(GetSession(request).getFid())) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping(value="/account/fcapitaloperationStatus")
	public String fcapitaloperationStatus(@RequestParam(required=true)int id){
		JSONObject jsonObject = new JSONObject() ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation.getFstatus()==CapitalOperationInStatus.Come){
			jsonObject.accumulate("code", 0) ;
		}else{
			jsonObject.accumulate("code", -1) ;
		}
		return jsonObject.toString() ;
	}
	//虚拟币提现
	@ResponseBody
	@RequestMapping(value="/account/withdrawBtcSubmit",produces={JsonEncode})
	public String withdrawBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=true)int withdrawAddr,
			@RequestParam(required=true)double withdrawAmount,
			@RequestParam(required=true)String tradePwd,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)int level
			) throws Exception{
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币提现开始");
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 入参withdrawAddr:{},withdrawAmount:{},symbol:{}", withdrawAddr, withdrawAmount, symbol);
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		String scode=actionSecurityCheck(request,fuser,true,true,true,tradePwd,true,phoneCode,totpCode,MessageTypeEnum.VIRTUAL_TIXIAN,LogTypeEnum.User_BTC,"提币申请异常","msg");
		if(!scode.equals("ok")) {
			return scode;
		}
		
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 根据用户id查询用户信息结束");
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 根据symbol:{}查询虚拟币信息结束", symbol);
		if(fvirtualcointype==null 
				|| !fvirtualcointype.isFIsWithDraw() 
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString() ;
		}
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询用户虚拟币的钱包信息结束");
		FvirtualaddressWithdraw fvirtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(withdrawAddr);
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询用户虚拟币地址结束");
		if(fvirtualaddressWithdraw == null
				|| fvirtualaddressWithdraw.getFuser().getFid() != fuser.getFid()
				|| fvirtualaddressWithdraw.getFvirtualcointype().getFid() != symbol){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString() ;
		}
		
		

		double max_double = fvirtualcointype.getFmaxqty();
		double min_double = fvirtualcointype.getFminqty();
		if(withdrawAmount<min_double){
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{min_double};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.minwithdrawal"));
			return jsonObject.toString() ;
		}
		
		if(withdrawAmount>max_double){
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{max_double};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.maxwithdrawal"));
			return jsonObject.toString() ;
		}
		
		//余额不足
		if(fvirtualwallet.getFtotal()<withdrawAmount){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.notenough")) ;
			return jsonObject.toString() ;
		}
		
		//检测是否为本站充值地址，如果是本站充值地址，则进行内部转账
		boolean isInSite = false;
		Fuser fskr = null;
		String filter = "where fadderess='"+fvirtualaddressWithdraw.getFadderess()+"'";
		List<Fvirtualaddress> vaddres = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, fvirtualaddressWithdraw.getFadderess());
		if(vaddres!=null && vaddres.size()>0){
			isInSite = true;
			fskr = this.frontUserService.findById(vaddres.get(0).getFuser().getFid());
		}
		
		String sql = "where flevel="+level+" and fvirtualcointype.fid="+symbol;
		List<Fwithdrawfees> alls = this.withdrawFeesService.list(0, 0, sql, false);
		if(alls == null || alls.size() ==0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.abnormalfee")) ;
		}
		double ffees = alls.get(0).getFfee();
		if(ffees ==0 && alls.get(0).getFlevel() != 5){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.incorrectfee")) ;
			return jsonObject.toString();
		}
		
		//提币手续费默认按照千分之一计算
		double actfee = withdrawAmount*0.001;
		if(actfee>ffees) {
			ffees = actfee;
		}
		
		if(withdrawAmount <= ffees){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.amountlessfee")) ;
			return jsonObject.toString();
		}
		
		String ip = getIpAddr(request) ;
		
		//如果是站内地址，进行站内快速转账
		if(isInSite) {
			try{
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存站内快速转账流水，修改用户钱包信息开始");
				this.frontVirtualCoinService.updateTransfer(fvirtualcointype, fvirtualwallet, withdrawAmount, ffees, fuser,fskr) ;
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存站内快速转账流水，修改用户钱包信息结束");
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"提现地址为站内地址，快速转账成功！")) ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "站内快速转账申请成功："+withdrawAmount+fvirtualcointype.getfShortName());
			}catch(Exception e){
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit站内快速转账 异常exception:{}", e.getMessage());
				e.printStackTrace() ;
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "站内快速转账申请失败："+withdrawAmount+fvirtualcointype.getfShortName());
			}finally{
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_TIXIAN);
			}
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币站内快速转账结束");
		}else {
			try{
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息开始");
				this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw, fvirtualcointype, fvirtualwallet, withdrawAmount, ffees, fuser) ;
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息结束");
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success")) ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "提币申请成功："+withdrawAmount+fvirtualcointype.getfShortName());
			}catch(Exception e){
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 异常exception:{}", e.getMessage());
				e.printStackTrace() ;
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "提币申请失败："+withdrawAmount+fvirtualcointype.getfShortName());
			}finally{
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_TIXIAN);
			}
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币提现结束");
		}
		return jsonObject.toString() ;
	}
	
//获取提笔地址
	@ResponseBody
	@RequestMapping("/account/getVirtualAddress")
	public String getVirtualAddress(
			HttpServletRequest request,
			int symbol
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		int count = this.adminService.getAllCount("Fvirtualaddress", filter);
		if(count >0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.addressnogain")) ;
			return jsonObject.toString() ;
		}
		Fvirtualcointype coin = this.virtualCoinService.findById(symbol);
		if(coin.isFisrecharge()){
			//代币和子资产都与主币共用地址,parentCid不为0的都是代币
			if (coin.getParentCid()>0){
				String filters = "where fstatus=1 and fisrecharge=1 and (parentCid=" + coin.getParentCid() + " or fid=" + coin.getParentCid() +") and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
				List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filters, false);
				String addr = "";
				//存放无绑定地址的币种
				List<Fvirtualcointype> noaddrcoins = new ArrayList<Fvirtualcointype>();
				//循环遍历主钱包钱包，提取已绑定地址
				//只以主币种获取地址
				Fvirtualcointype ethcoin = null;
				for (Fvirtualcointype fvcoin : coins) {
					filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+fvcoin.getFid();
					Fvirtualaddress fvaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvcoin);
					if ( fvaddress!=null){
						addr = fvaddress.getFadderess();
					}else{
						noaddrcoins.add(fvcoin);
					}
					if (fvcoin.getFid()==coin.getParentCid()){
						ethcoin = fvcoin;
					}
				}
				//如果所有钱包都没有地址，则需要获取一个地址进行添加
				if(addr==null || addr.trim().equalsIgnoreCase("null") || addr.trim().equals("")){
					Fpool fpool = this.poolService.getOneFpool(ethcoin) ;
					if(fpool == null){
						//如果是用备注转账的钱包BTS，则用UID作为地址
						if(!ethcoin.isFisBts()) {
							jsonObject.accumulate("code", -1) ;
							jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.noaddress")) ;
							return jsonObject.toString() ;
						}else {
							addr = String.valueOf(fuser.getFid());
						}
					}else {
						addr = fpool.getFaddress() ;
						if(addr==null || addr.trim().equalsIgnoreCase("null") || addr.trim().equals("")){
							jsonObject.accumulate("code", -1) ;
							jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.noaddress")) ;
							return jsonObject.toString() ;
						}
					}
					
					
					for (Fvirtualcointype fvcoin : coins) {
						Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
						fvirtualaddress.setFadderess(addr) ;
						fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
						fvirtualaddress.setFuser(fuser) ;
						fvirtualaddress.setFvirtualcointype(fvcoin) ;
						this.virtualaddressService.saveObj(fvirtualaddress) ;
					}
					//如果存在地址，则更新地址库状态
					if(null!=fpool) {
						fpool.setFstatus(1) ;
						this.poolService.updateObj(fpool) ;
					}
				}else{
					for (Fvirtualcointype fvcoin : noaddrcoins) {
						Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
						fvirtualaddress.setFadderess(addr) ;
						fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
						fvirtualaddress.setFuser(fuser) ;
						fvirtualaddress.setFvirtualcointype(fvcoin) ;
						this.virtualaddressService.saveObj(fvirtualaddress) ;
					}
				}
				jsonObject.accumulate("address", addr) ;
			}else{
				Fpool fpool = this.poolService.getOneFpool(coin) ;
				String address =null;
				if(fpool == null){
					//如果是备注转账类型的BTS钱包，则直接将UID分配作为地址
					if(!coin.isFisBts()) {
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.noaddress")) ;
						return jsonObject.toString() ;
					}else {
						address = String.valueOf(fuser.getFid());
					}
				}else {
					address = fpool.getFaddress() ;
					if(address==null || address.trim().equalsIgnoreCase("null") || address.trim().equals("")){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.noaddress")) ;
						return jsonObject.toString() ;
					}
				}
				
				Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
				fvirtualaddress.setFadderess(address) ;
				fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
				fvirtualaddress.setFuser(fuser) ;
				fvirtualaddress.setFvirtualcointype(coin) ;
				this.virtualaddressService.saveObj(fvirtualaddress) ;
				
				if(null!=fpool) {
					fpool.setFstatus(1) ;
					this.poolService.updateObj(fpool) ;
				}
				jsonObject.accumulate("address", fvirtualaddress.getFadderess()) ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.nosupportrecharge")) ;
			return jsonObject.toString() ;
		}
		
		jsonObject.accumulate("code", 0) ;
		return jsonObject.toString();
	}
	
	
	//会员用户自己充值人民币
	@RequestMapping(value="/exchange/usdtManual",produces = {JsonEncode})
	@ResponseBody
	public String usdtManual(
			HttpServletRequest request,
			@RequestParam(required=true) double money,
			@RequestParam(required=true) int sbank
			) throws Exception{
		LOGGER.info(CLASS_NAME + " usdtManual,会员用户自己充值人民币,入参money:{},sbank:{}", money, sbank);
		JSONObject jsonObject = new JSONObject() ;
		money = Utils.getDoubleUp(money, 2);
		double minRecharge = Double.parseDouble(this.frontConstantMapService.get("minrechargecny").toString()) ;
		double maxRecharge = Double.parseDouble(this.frontConstantMapService.get("maxrechargecny").toString().trim()) ;
		LOGGER.info(CLASS_NAME + " usdtManual,配置最小充值金额为minRecharge:{}", minRecharge);
		LOGGER.info(CLASS_NAME + " usdtManual,配置最大充值金额为maxRecharge:{}", maxRecharge);
		if(money < minRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			Object[] params = new Object[]{minRecharge};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.minrecharge"));
			return jsonObject.toString();
		}
		
		if(money > maxRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			Object[] params = new Object[]{maxRecharge};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.maxrecharge"));
			return jsonObject.toString();
		}
		
		Systembankinfo systembankinfo = this.frontBankInfoService.findSystembankinfoById(sbank) ;
		LOGGER.info(CLASS_NAME + " usdtManual,查询银行账户信息systembankinfo:{}", systembankinfo);
		if(systembankinfo==null || systembankinfo.getFstatus()==SystemBankInfoEnum.ABNORMAL ){
			LOGGER.info(CLASS_NAME + " usdtManual,查询银行账户信息为空或账户停用");
			//银行账号停用
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.nobank"));
			return jsonObject.toString();
		}
		
		Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
		fcapitaloperation.setFamount(money) ;
		fcapitaloperation.setSystembankinfo(systembankinfo) ;
		fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
		fcapitaloperation.setFtype(CapitalOperationTypeEnum.USDT_IN) ;
		fcapitaloperation.setFuser(this.GetSession(request)) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.NoGiven) ;
		fcapitaloperation.setFremittanceType(systembankinfo.getFbankName());
		int num = (int) (Math.random() * 100000);
		fcapitaloperation.setFremark(String.format("%05d", num));
		//LOGGER.info(CLASS_NAME + " alipayManual,保存到数据库的人民币充值流水fcapitaloperation:{}", new Gson().toJson(fcapitaloperation));
		int tradeId=this.frontAccountService.addFcapitaloperation(fcapitaloperation) ;
		LOGGER.info(CLASS_NAME + " alipayManual,保存完毕，开始拼装返回对象");
		jsonObject.accumulate("code", 0);
		jsonObject.accumulate("money", String.valueOf(fcapitaloperation.getFamount())) ;
		jsonObject.accumulate("tradeId", tradeId) ;
		jsonObject.accumulate("remark", fcapitaloperation.getFremark()) ;
		jsonObject.accumulate("fbankName", systembankinfo.getFbankName()) ;
		jsonObject.accumulate("fownerName", systembankinfo.getFownerName()) ;
		jsonObject.accumulate("fbankAddress", systembankinfo.getFbankAddress()) ;
		jsonObject.accumulate("fbankNumber", systembankinfo.getFbankNumber().replaceAll("\\d{4}(?!$)", "$0 ")) ;
		LOGGER.info(CLASS_NAME + " alipayManual,返回对象jsonObject:{}", jsonObject.toString());
		return jsonObject.toString() ;  
	}
	
	@ResponseBody
	@RequestMapping(value="/exchange/rechargeUsdtSubmit",produces={JsonEncode})
	public String rechargeUsdtSubmit(
			HttpServletRequest request,
			@RequestParam(required=false) String bank,
			@RequestParam(required=false) String account,
			@RequestParam(required=false) String payee,
			@RequestParam(required=false) String phone,
			@RequestParam(required=true) int desc//记录的id
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(desc) ;
		if(fcapitaloperation==null ||fcapitaloperation.getFuser().getFid() !=GetSession(request).getFid() ){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation"));
			return jsonObject.toString();
		}
		
		if(fcapitaloperation.getFstatus() != CapitalOperationInStatus.NoGiven
				|| fcapitaloperation.getFtype() != CapitalOperationTypeEnum.USDT_IN){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
			return jsonObject.toString();
		}
		
//		fcapitaloperation.setfBank(bank) ;
//		fcapitaloperation.setfAccount(account) ;
//		fcapitaloperation.setfPayee(payee) ;
//		fcapitaloperation.setfPhone(phone) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
		fcapitaloperation.setFischarge(false);
		fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp());
		try {
			this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
		}
		
		return jsonObject.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/exchange/cancelRechargeUsdtSubmit")
	public String cancelRechargeUsdtSubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation==null ||
				fcapitaloperation.getFremittanceType().equals(RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type3))||
				fcapitaloperation.getFremittanceType().equals(RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type4))
				){
			return null ;
		}
		
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.USDT_IN
			&&(fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven || fcapitaloperation.getFstatus()==CapitalOperationInStatus.WaitForComing)){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.Invalidate) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	
	@ResponseBody
	@RequestMapping("/exchange/exchangeUsdtSubmit")
	public String exchangeUsdtSubmit(
			HttpServletRequest request,
			@RequestParam(required=true,defaultValue="0")String exchangecode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		//行为式验证码验证--无痕验证
		String retCode = new AliyunCheck().checkAliyunNVCMode(request);
		if(!(retCode.equals("100") || retCode.equals("200"))) {
			jsonObject.accumulate("code", retCode) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"验证失败")) ;
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(fuser.getFtradePassword()==null){
			//没有设置交易密码
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.settranpwd")) ;
			return jsonObject.toString() ;
		}
		
		if(!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()){
			//没有绑定谷歌或者手机
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.bindinfo")) ;
			return jsonObject.toString() ;
		}
		
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationByRemark(exchangecode) ;
		
		if(fcapitaloperation==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.exchange.noopera")) ;
			return jsonObject.toString() ;
		}
		if(fcapitaloperation.getFstatus()!=1){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.exchange.repeatsubmit")) ;
			return jsonObject.toString() ;
		}
		
		
		boolean flag = false ;
		try {
			//withdraw = this.frontAccountService.updateWithdrawUSDT(withdrawBalance, fuser,fbankinfoWithdraw) ;
			fcapitaloperation.setFstatus(CapitalOperationOutStatus.OperationLock);
			this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			flag = true;
			
		} catch (Exception e) {}finally{
			
		}
		
		if(flag){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("operId", fcapitaloperation.getFid()) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.succash")) ;
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
		}
		
		return jsonObject.toString() ;
	}
	
	
	//虚拟币提现
		@ResponseBody
		@RequestMapping(value="/account/transferSubmit",produces={JsonEncode})
		public String transferSubmit(
				HttpServletRequest request,
				@RequestParam(required=true)int skruid,
				@RequestParam(required=true)String skruname,
				@RequestParam(required=true)double withdrawAmount,
				@RequestParam(required=true)String tradePwd,
				@RequestParam(required=false,defaultValue="0")String totpCode,
				@RequestParam(required=false,defaultValue="0")String phoneCode,
				@RequestParam(required=true)int symbol,
				@RequestParam(required=true)int level
				) throws Exception{
			LOGGER.info(CLASS_NAME + " transferSubmit 虚拟币转账开始");
			LOGGER.info(CLASS_NAME + " transferSubmit 入参withdrawAddr:{},withdrawAmount:{},symbol:{}", skruid, withdrawAmount, symbol);
			JSONObject jsonObject = new JSONObject() ;
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;		
			
			String code=actionSecurityCheck(request, fuser, false, true, true, tradePwd, true, phoneCode, totpCode, MessageTypeEnum.VIRTUAL_TIXIAN, LogTypeEnum.User_BTC, "转账申请异常","msg");
			if(!code.equals("ok")){
				return code;
			}
			
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 根据用户id查询用户信息结束");
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 根据symbol:{}查询虚拟币信息结束", symbol);
			if(fvirtualcointype==null 
					|| !fvirtualcointype.isFisTransfer() 
					|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
					|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
					|| withdrawAmount<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
				return jsonObject.toString() ;
			}
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询用户虚拟币的钱包信息结束");
			
			Fuser fusk = this.frontUserService.findById(skruid) ;
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询收款人信息结束");
			if(fusk == null
					|| !fusk.getFloginName().equals(skruname)){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation.erroruser")) ;
				return jsonObject.toString() ;
			}
			
			if(fuser.getFid()==skruid){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation.denysameuser")) ;
				return jsonObject.toString() ;
			}
			
			
			String ip = getIpAddr(request) ;
			
			double max_double = fvirtualcointype.getFmaxqty();
			double min_double = fvirtualcointype.getFminqty();
			if(withdrawAmount<min_double){
				jsonObject.accumulate("code", -1) ;
				Object[] params = new Object[]{min_double};
				jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.mintransfer"));
				return jsonObject.toString() ;
			}
			
			if(withdrawAmount>max_double){
				jsonObject.accumulate("code", -1) ;
				Object[] params = new Object[]{max_double};
				jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.maxtransfer"));
				return jsonObject.toString() ;
			}
			
			//余额不足
			if(fvirtualwallet.getFtotal()<withdrawAmount){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.notenough")) ;
				return jsonObject.toString() ;
			}
			String sql = "where flevel="+level+" and fvirtualcointype.fid="+symbol;
			List<Fwithdrawfees> alls = this.withdrawFeesService.list(0, 0, sql, false);
			if(alls == null || alls.size() ==0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.abnormalfee")) ;
			}
			double ffees = alls.get(0).getFfee();
			if(ffees ==0 && alls.get(0).getFlevel() != 5){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.incorrectfee")) ;
				return jsonObject.toString();
			}
			
			//提币手续费默认按照千分之一计算
			double actfee = withdrawAmount*0.001;
			if(actfee>ffees) {
				ffees = actfee;
			}
			
			if(withdrawAmount <= ffees){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.amountlessfee")) ;
				return jsonObject.toString();
			}
			
			
			try{
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息开始");
				this.frontVirtualCoinService.updateTransfer( fvirtualcointype, fvirtualwallet, withdrawAmount, ffees, fuser,fusk) ;
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息结束");
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success")) ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "转账申请成功："+withdrawAmount+fvirtualcointype.getfShortName());
			}catch(Exception e){
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 异常exception:{}", e.getMessage());
				e.printStackTrace() ;
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "转账申请失败");
			}finally{
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_TIXIAN);
			}
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币提现结束");
			return jsonObject.toString() ;
		}
	
		@ResponseBody
		@RequestMapping("/exchange/cancelWithdrawusdt")
		public String cancelWithdrawusdt(
				HttpServletRequest request,
				int id
				) throws Exception{
			Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
			if(fcapitaloperation!=null
					&&fcapitaloperation.getFuser().getFid() ==GetSession(request).getFid()
					&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.USDT_OUT
					&&fcapitaloperation.getFstatus()==CapitalOperationOutStatus.WaitForOperation){
				try {
					this.frontAccountService.updateCancelWithdrawUsdt(fcapitaloperation, this.frontUserService.findById(GetSession(request).getFid())) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.frontUserService.updateUserLog(GetSession(request), getIpAddr(request), LogTypeEnum.User_USDT, "取消提现操作");
			return String.valueOf(0) ;
		}
	@ResponseBody
	@RequestMapping("/account/cancelRechargeCnySubmit")
	public String cancelRechargeCnySubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation==null ||
				fcapitaloperation.getFremittanceType().equals(RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type3))||
				fcapitaloperation.getFremittanceType().equals(RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type4))
				){
			return null ;
		}
		
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_IN
			&&(fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven || fcapitaloperation.getFstatus()==CapitalOperationInStatus.WaitForComing)){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.Invalidate) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/exchange/subRechargeUsdtSubmit")
	public String subRechargeUsdtSubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.USDT_IN
			&&fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}

	/**
	 * USDT生成提币码
	 * @param request
	 * @param tradePwd 交易密码
	 * @param withdrawBalance 提现数量
	 * @param phoneCode  手机验证码
	 * @param totpCode 谷歌验证码
	 * @param withdrawBlank 提现银行
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/exchange/withdrawUsdtSubmit")
	public String withdrawUsdtSubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String tradePwd,
			@RequestParam(required=true)double withdrawBalance,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int withdrawBlank
			) throws Exception{
		LOGGER.info(CLASS_NAME + " withdrawCnySubmit 会员用户申请提现人民币，提现金额：{}", withdrawBalance);
		JSONObject jsonObject = new JSONObject() ;
		//最大提现人民币
		double max_double = Double.parseDouble(this.frontConstantMapService.get("maxwithdrawcny").toString()) ;
		double min_double = Double.parseDouble(this.frontConstantMapService.get("minwithdrawcny").toString()) ;
		LOGGER.info(CLASS_NAME + " withdrawCnySubmit 最大提现人民币:{},最小提现人民币:{}", max_double, min_double);
		if(withdrawBalance<min_double){
			//提现金额不能小于10
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{min_double};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.minwithdrawal"));
			return jsonObject.toString() ;
		}
		
		if(withdrawBalance>max_double){
			//提现金额不能大于指定值
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{max_double};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.maxwithdrawal"));
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		String code=actionSecurityCheck(request, fuser, true, true, true, tradePwd, true, phoneCode, totpCode, MessageTypeEnum.USDT_TIXIAN, LogTypeEnum.User_USDT, "USDT生成提币码", "msg");
		if(!code.equals("ok")){
			return code;
		}
		
		Fvirtualwallet fwallet = this.frontUserService.findUSDTWalletByUser(fuser.getFid());
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(withdrawBlank);
		if(fbankinfoWithdraw == null || fbankinfoWithdraw.getFuser().getFid() != fuser.getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.illwithdrawal")) ;
			return jsonObject.toString() ;
		}
		
		if(fwallet.getFtotal()<withdrawBalance){
			//资金不足
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.notenough")) ;
			return jsonObject.toString() ;
		}
						
      
		
		int withdraw = -1 ;
		try {
			withdraw = this.frontAccountService.updateWithdrawUSDT(withdrawBalance, fuser,fbankinfoWithdraw) ;
			
		} catch (Exception e) {}finally{
			this.frontValidateMapService.removeMessageMap(MessageTypeEnum.getEnumString(MessageTypeEnum.USDT_TIXIAN)) ;
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.USDT_TIXIAN);
		}
		
		if(withdraw>0){
			Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(withdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("coincode", fcapitaloperation.getFremark()) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.succash")) ;
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
		}
		
		return jsonObject.toString() ;
	}


	@ResponseBody
	@RequestMapping("/account/cancelWithdrawBtc")
	public String cancelWithdrawBtc(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcaptualoperation fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(id) ;
		if(fvirtualcaptualoperation!=null
				&&fvirtualcaptualoperation.getFuser().getFid() ==fuser.getFid() 
				&&fvirtualcaptualoperation.getFtype()==VirtualCapitalOperationTypeEnum.COIN_OUT
				&&fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationOutStatusEnum.WaitForOperation
				){
			
			try{
				this.frontAccountService.updateCancelWithdrawBtc(fvirtualcaptualoperation, fuser) ;
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
		}
		this.frontUserService.updateUserLog(GetSession(request), getIpAddr(request), LogTypeEnum.User_BTC, "取消提现操作");
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/account/cancelTransfer")
	public String cancelTransfer(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcaptualoperation fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(id) ;
		if(fvirtualcaptualoperation!=null
				&&fvirtualcaptualoperation.getFuser().getFid() ==fuser.getFid() 
				&&fvirtualcaptualoperation.getFtype()==VirtualCapitalOperationTypeEnum.COIN_TRANSFER
				&&fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationOutStatusEnum.WaitForOperation
				){
			
			try{
				this.frontAccountService.updateCancelWithdrawBtc(fvirtualcaptualoperation, fuser) ;
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
		}
		this.frontUserService.updateUserLog(GetSession(request), getIpAddr(request), LogTypeEnum.User_BTC, "取消转账操作");
		return String.valueOf(0) ;
	}
	@ResponseBody
	@RequestMapping("/account/subRechargeCnySubmit")
	public String subRechargeCnySubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_IN
			&&fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	

	
	@ResponseBody
	@RequestMapping("/account/withdrawCnySubmit")
	public String withdrawCnySubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String tradePwd,
			@RequestParam(required=true)double withdrawBalance,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int withdrawBlank
			) throws Exception{
		LOGGER.info(CLASS_NAME + " withdrawCnySubmit 会员用户申请提现人民币，提现金额：{}", withdrawBalance);
		JSONObject jsonObject = new JSONObject() ;
		//最大提现人民币
		double max_double = Double.parseDouble(this.frontConstantMapService.get("maxwithdrawcny").toString()) ;
		double min_double = Double.parseDouble(this.frontConstantMapService.get("minwithdrawcny").toString()) ;
		LOGGER.info(CLASS_NAME + " withdrawCnySubmit 最大提现人民币:{},最小提现人民币:{}", max_double, min_double);
		if(withdrawBalance<min_double){
			//提现金额不能小于10
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{min_double};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.minwithdrawal"));
			return jsonObject.toString() ;
		}
		
		if(withdrawBalance>max_double){
			//提现金额不能大于指定值
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{max_double};
			jsonObject.accumulate("msg", getLocaleMessage(request,params,"json.account.maxwithdrawal"));
			return jsonObject.toString() ;
		}
		
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		String code=actionSecurityCheck(request, fuser, false, true, true, tradePwd, true, phoneCode, totpCode, MessageTypeEnum.CNY_TIXIAN, LogTypeEnum.User_CNY, "人民币提现","msg");
		if(!code.equals("ok")){
			return code;
		}
		
		
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid());
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(withdrawBlank);
		if(fbankinfoWithdraw == null || fbankinfoWithdraw.getFuser().getFid() != fuser.getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.illwithdrawal")) ;
			return jsonObject.toString() ;
		}
		
		if(fwallet.getFtotal()<withdrawBalance){
			//资金不足
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg",getLocaleMessage(request,null,"json.account.notenough")) ;
			return jsonObject.toString() ;
		}
		
		 
		boolean withdraw = false ;
		try {
			withdraw = this.frontAccountService.updateWithdrawCNY(withdrawBalance, fuser,fbankinfoWithdraw) ;
			
		} catch (Exception e) {}finally{
			this.frontValidateMapService.removeMessageMap(MessageTypeEnum.getEnumString(MessageTypeEnum.CNY_TIXIAN)) ;
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_TIXIAN);
		}
		
		if(withdraw){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.succash")) ;
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
		}
		
		return jsonObject.toString() ;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/account/rechargeCnySubmit",produces={JsonEncode})
	public String rechargeCnySubmit(
			HttpServletRequest request,
			@RequestParam(required=false) String bank,
			@RequestParam(required=false) String account,
			@RequestParam(required=false) String payee,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) int type,
			@RequestParam(required=true) int desc//记录的id
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(desc) ;
		if(fcapitaloperation==null ||fcapitaloperation.getFuser().getFid() !=GetSession(request).getFid() ){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation"));
			return jsonObject.toString();
		}
		
		if(fcapitaloperation.getFstatus() != CapitalOperationInStatus.NoGiven
				|| fcapitaloperation.getFtype() != CapitalOperationTypeEnum.RMB_IN){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
			return jsonObject.toString();
		}
		
//		fcapitaloperation.setfBank(bank) ;
//		fcapitaloperation.setfAccount(account) ;
//		fcapitaloperation.setfPayee(payee) ;
//		fcapitaloperation.setfPhone(phone) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
		fcapitaloperation.setFischarge(false);
		fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp());
		try {
			this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly"));
		}
		
		return jsonObject.toString();
	}
	
	
	
	/**
	 * 虚拟币兑换
	 * @param request
	 * @param convertCoinFrom     待兑换币种id
	 * @param convertCoinTo       兑换币种id
	 * @param convertAmount1                  待兑换币种数量
	 * @param convertAmount2                  兑换币种数量
	 * @param productId           产品id
	 * @param convertType         兑换类型 (1:ABO——>ABOT   2：ABOT——>ABO)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/account/convertCoinSubmit",produces={JsonEncode})
	public String convertCoinSubmit(
			HttpServletRequest request,
			@RequestParam(required=true)int cointypeFrom,
			@RequestParam(required=true)int cointypeTo,
			@RequestParam(required=true)double convertAmount1,
			@RequestParam(required=true)double convertAmount2,
			@RequestParam(required=true)String tradePwd,
			@RequestParam(required=true)int productId,
			@RequestParam(required=true)int convertType
			) throws Exception{
		LOGGER.info(CLASS_NAME + " convertCoinSubmit 虚拟币兑换开始");
		LOGGER.info(CLASS_NAME + " convertCoinSubmit 入参convertCoinFrom:{},convertCoinTo:{},"
				+ "convertAmount1:{},convertAmount2:{}", cointypeFrom, cointypeTo, convertAmount1,convertAmount2);
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		//进行交易密码验证
		String scode=actionSecurityCheck(request,fuser,false,true,true,tradePwd,false,null,null,MessageTypeEnum.VIRTUAL_TIXIAN,LogTypeEnum.User_BTC,"虚拟币兑换异常","msg");
		if(!scode.equals("ok")) {
			return scode;
		}
		
		String convertRatio = null;
		Pproduct product = pproductService.findById(productId);
		//获取兑换比例
		if(1 == convertType) {
			convertRatio = product.getConvertRatio();
			
		} else if(2 == convertType) {
			convertRatio = product.getConvertRatioExpire();
			
		} else {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString() ;
		}
		
		//除数
		Double beforeNum = Double.valueOf(convertRatio.split(":")[0]);
		//被除数
		Double afterNum = Double.valueOf(convertRatio.split(":")[1]);
		//校验金额是否正确
		double amount = Utils.getDouble(convertAmount1*afterNum/beforeNum, 2);
		if(convertAmount2 != amount) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString() ;
		}
		
		
		if(1 == convertType) {    // ABO——>ABOT
			//单次数量校验和每个用户总兑换量校验
			if(convertAmount2 < product.getMinTimeAmount()) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"单次最少兑换"+product.getMinTimeAmount()+"个")) ;
				return jsonObject.toString() ;
			}
			//计算已兑换总量
			String filter = "  where convertCointype2.fid = "+ cointypeTo
					+"  and fuser.fid = " + fuser.getFid();
			double convertTotalAmount = adminService.getAllSum("FconvertCoinLogs", "convertAmount2", filter);
			if((convertTotalAmount+convertAmount2) > product.getMaxTotalAmount()) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"您已超出每人最大可兑换总量")) ;
				return jsonObject.toString() ;
			}
			
			//计算产品是否过期
			String nowDateStr = sdf.format(new Date());
			Integer plusDate = ProductPeriodEnum.getDataValueMap().get(product.getPeriod());
			String expireDateStr = DateSupport.datePlusDay(nowDateStr, plusDate);
			if(nowDateStr.compareTo(expireDateStr) > 0) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"产品已过期，禁止兑换")) ;
				return jsonObject.toString() ;
			}
			
			//判断产品兑换可用额是否充足
			if(convertAmount2 > product.getConvertAvailableAmount()) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"兑换可用额不足")) ;
				return jsonObject.toString() ;
			}
			
		} else {    // ABOT——>ABO
			//判断产品可用额是否充足
			if(convertAmount1 > product.getProAvailableAmount()) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", getLocaleMessage(request,null,"产品可用额不足")) ;
				return jsonObject.toString() ;
			}
		}
		
		//校验钱包余额是否充足
		Fvirtualwallet fvirtualwalletFrom = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), cointypeFrom) ;
		//余额不足
		if(fvirtualwalletFrom.getFtotal()<convertAmount1){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.notenough")) ;
			return jsonObject.toString() ;
		}
		
		Fvirtualwallet fvirtualwalletTo = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), cointypeTo) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(cointypeTo) ;
		
		String ip = getIpAddr(request) ;
		try{
			
			LOGGER.info(CLASS_NAME + " convertCoinSubmit 保存虚拟币兑换流水，修改用户钱包信息开始");
			this.virtualWalletService.updateConvertCoinSubmit(fvirtualwalletFrom, fvirtualwalletTo, convertAmount1, convertAmount2, fuser, product, convertType);
			LOGGER.info(CLASS_NAME + " convertCoinSubmit 保存虚拟币兑换流水，修改用户钱包信息结束");
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.success")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "虚拟币兑换成功："+convertAmount2+fvirtualcointype.getfShortName());
		}catch(Exception e){
			LOGGER.info(CLASS_NAME + " convertCoinSubmit 异常exception:{}", e.getMessage());
			e.printStackTrace() ;
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", getLocaleMessage(request,null,"json.account.networkanomaly")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "虚拟币兑换失败："+convertAmount2+fvirtualcointype.getfShortName());
		}finally{
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_TIXIAN);
		}
		LOGGER.info(CLASS_NAME + " convertCoinSubmit 虚拟币兑换结束");
		return jsonObject.toString() ;
	}

	
}
