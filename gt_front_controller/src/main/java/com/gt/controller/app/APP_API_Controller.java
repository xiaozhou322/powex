package com.gt.controller.app;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.HtmlUtils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.gt.Enum.BankInfoStatusEnum;
import com.gt.Enum.BankInfoWithdrawStatusEnum;
import com.gt.Enum.BankTypeEnum;
import com.gt.Enum.CapitalOperationInStatus;
import com.gt.Enum.CapitalOperationOutStatus;
import com.gt.Enum.CapitalOperationTypeEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.CountLimitTypeEnum;
import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.IntrolInfoTypeEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.Enum.MessageStatusEnum;
import com.gt.Enum.MessageTypeEnum;
import com.gt.Enum.QuestionStatusEnum;
import com.gt.Enum.QuestionTypeEnum;
import com.gt.Enum.RegTypeEnum;
import com.gt.Enum.RemittanceTypeEnum;
import com.gt.Enum.ScoreRecordTypeEnum;
import com.gt.Enum.SendMailTypeEnum;
import com.gt.Enum.SystemBankInfoEnum;
import com.gt.Enum.TrademappingStatusEnum;
import com.gt.Enum.UserStatusEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.comm.MessageValidate;
import com.gt.comm.MultipleValues;
import com.gt.controller.BaseController;
import com.gt.entity.Emailvalidate;
import com.gt.entity.Fabout;
import com.gt.entity.Fapi;
import com.gt.entity.Farticle;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fentrust;
import com.gt.entity.Ffees;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.FlevelSetting;
import com.gt.entity.Flimittrade;
import com.gt.entity.Flog;
import com.gt.entity.Fmessage;
import com.gt.entity.Fpool;
import com.gt.entity.Fquestion;
import com.gt.entity.Fscore;
import com.gt.entity.FscoreRecord;
import com.gt.entity.FscoreSetting;
import com.gt.entity.Ftradehistory;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.FuserFaceID;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwebbaseinfo;
import com.gt.entity.Fwithdrawfees;
import com.gt.entity.Pdomain;
import com.gt.entity.Pproject;
import com.gt.entity.Ptrademapping;
import com.gt.entity.Systembankinfo;
import com.gt.entity.WalletMessage;
import com.gt.result.KeyValueVo;
import com.gt.service.admin.AboutService;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.ArticleService;
import com.gt.service.admin.CapitaloperationService;
import com.gt.service.admin.FeeService;
import com.gt.service.admin.IntrolinfoService;
import com.gt.service.admin.LevelSettingService;
import com.gt.service.admin.LogService;
import com.gt.service.admin.MessageService;
import com.gt.service.admin.PoolService;
import com.gt.service.admin.ScoreSettingService;
import com.gt.service.admin.SystemArgsService;
import com.gt.service.admin.UserService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.admin.VirtualaddressService;
import com.gt.service.admin.WithdrawFeesService;
import com.gt.service.front.FrontAccountService;
import com.gt.service.front.FrontBankInfoService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.service.front.FrontDrawAccountsService;
import com.gt.service.front.FrontEntrustChangeService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontPprojectService;
import com.gt.service.front.FrontQuestionService;
import com.gt.service.front.FrontTradeService;
import com.gt.service.front.FrontUserFaceIDService;
import com.gt.service.front.FrontUserService;
import com.gt.service.front.FrontValidateMapService;
import com.gt.service.front.FrontValidateService;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.service.front.FtradeMappingService;
import com.gt.service.front.UtilsService;
import com.gt.util.Constant;
import com.gt.util.GoogleAuth;
import com.gt.util.HttpClientUtils;
import com.gt.util.IDCardVerifyUtil;
import com.gt.util.OSSPostObject;
import com.gt.util.PaginUtil;
import com.gt.util.Utils;
import com.gt.util.wallet.WalletUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller @Scope("prototype")
public class APP_API_Controller extends BaseController {

	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontCacheService frontCacheService;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FrontConstantMapService frontConstantMapService ;
	@Autowired
	private UserService userService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private IntrolinfoService introlinfoService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private CapitaloperationService capitaloperationService ;
	@Autowired
	private FrontOthersService frontOthersService;
	@Autowired
	private FrontValidateMapService frontValidateMapService ;
	@Autowired
	private LogService logService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService ;
	@Autowired
	private FrontBankInfoService frontBankInfoService ;
	@Autowired
	private FrontQuestionService frontQuestionService ;
	@Autowired
	private FeeService feeService ;
	@Autowired
	private LevelSettingService levelSettingService ;
	@Autowired
	private ScoreSettingService scoreSettingService ;
	@Autowired
	private PoolService poolService;
	@Autowired
	private VirtualaddressService virtualaddressService;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FrontEntrustChangeService frontEntrustChangeService;
	@Autowired
	private AboutService aboutService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private FrontUserFaceIDService frontUserFaceIDService;
	@Autowired
	private FrontPprojectService frontPprojectService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private FrontDrawAccountsService frontDrawAccountsService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(APP_API_Controller.class);
	private static final String CLASS_NAME = APP_API_Controller.class.getSimpleName();
	public final static String Result = "result" ;
	public final static String ErrorCode = "code" ;
	public final static String Value = "value" ;
	public final static String LoginToken = "loginToken" ;
	public final static String CurrentPage = "currentPage" ;
	public final static String TotalPage = "totalPage" ;
	public final static String LastUpdateTime = "lastUpdateTime" ;
	public final static String Fid = "Fid" ;
	

	private String curLoginToken = null ;
	private String appApiUid = null ;
	private String appApiIp = null ;
	int maxResult = Constant.AppRecordPerPage ;
	
	@ResponseBody
	@RequestMapping(value="/appApi2",produces="text/html;charset=UTF-8")
	public String appApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String action
			) throws Exception {
		request.setCharacterEncoding("UTF-8") ;
		curLoginToken = request.getParameter(LoginToken) ;
		Integer actionInteger = ApiConstant.getInteger(action) ;
		
		JSONObject jsonObject = new JSONObject() ;
		Fapi fapi = null ;
		Fuser fuser = null ;
		if(actionInteger / 100==2){
			boolean isLogin = this.isAppLogin(this.curLoginToken, false) ;
			if(isLogin==false){
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(ErrorCode , -10001) ;
				jsonObject.accumulate(Value, "未登录") ;
				return jsonObject.toString() ;
			}
		}
		
		String api_key = request.getParameter("api_key") ;
		String sign = request.getParameter("sign") ;
		List<Fapi> fapis = this.utilsService.list(0, 0, " where fpartner=? ", false, Fapi.class,api_key) ;
		if(fapis.size() == 1 ){
			fapi = fapis.get(0) ;
			if((fapi.isFistrade()==false)){
				jsonObject.accumulate(Result , false) ;
				jsonObject.accumulate(ErrorCode , -10002) ;
				jsonObject.accumulate(Value, "无交易权限") ;
				return jsonObject.toString() ;
			}
			
			if((fapi.isFiswithdraw()==false)){
				jsonObject.accumulate(Result , false) ;
				jsonObject.accumulate(ErrorCode , -10003) ;
				jsonObject.accumulate(Value, "无提现权限") ;
				return jsonObject.toString() ;
			}
			
			String ip = getIpAddr(request) ;
			if("*".equals(fapi.getFip()) == false && fapi.getFip().indexOf(ip)==-1){
				jsonObject.accumulate(Result , false) ;
				jsonObject.accumulate(ErrorCode , -10004) ;
				jsonObject.accumulate(Value, "IP无权限") ;
				return jsonObject.toString() ;
			}
			appApiIp = ip;
			fuser = fapi.getFuser() ;
			if(null == fuser.getFgrade() || fuser.getFgrade() == 0) {
				jsonObject.accumulate(Result , false) ;
				jsonObject.accumulate(ErrorCode , -10008) ;
				jsonObject.accumulate(Value, "无APP接口权限") ;
				return jsonObject.toString() ;
			}
			appApiUid = String.valueOf(fuser.getFid());
		}else{
			jsonObject.accumulate(Result , false) ;
			jsonObject.accumulate(ErrorCode , -10005) ;
			jsonObject.accumulate(Value, "api_key错误") ;
			return jsonObject.toString() ;
		}
		
		//数据验签
		Map<String, String[]> params = request.getParameterMap() ;
		TreeSet<String> paramTreeSet = new TreeSet<String>() ;
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey() ;
			String[] values = entry.getValue() ;
			if(values.length!=1){
				jsonObject.accumulate(Result , false) ;
				jsonObject.accumulate(ErrorCode , -10006) ;
				jsonObject.accumulate(Value, "参数错误") ;
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
		LOGGER.info("请求入参：paramString=" + paramString.toString());
		LOGGER.info("请求入参签名字符串：sign=" + sign);
		LOGGER.info("请求入参加密签名字符串：md5=" + md5);
		
		if(md5.equals(sign) == false ){
			jsonObject.accumulate(Result , false) ;
			jsonObject.accumulate(ErrorCode , -10007) ;
			jsonObject.accumulate(Value, "签名错误") ;
			return jsonObject.toString() ;
		}
		
		String ret = "" ;
		switch (actionInteger) {
		case 0 :
			{
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(ErrorCode , -10009) ;
				jsonObject.accumulate(Value, "API不存在") ;
				ret = jsonObject.toString() ;
			}
			break ;
			
		default:
			try {
				Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class) ;
				ret = (String)method.invoke(this, request) ;
			} catch (Exception e) {
				ret = ApiConstant.getUnknownError(e) ;
			}
			break ;	
		}
		
		if(Constant.isRelease == false ){
			System.out.println(ret);
		}
		return ret ;
	}
	
	
	public String TradePassword(HttpServletRequest request) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			String passwd = request.getParameter("passwd").trim() ;
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(fuser.getFtradePassword()==null){
				jsonObject.accumulate(Result, true) ;
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "未设置交易密码") ;
				return jsonObject.toString() ;
			}else if(fuser.getFtradePassword().endsWith(Utils.MD5(passwd, fuser.getSalt()))){
				jsonObject.accumulate(Result, true) ;
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "交易密码正确") ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate(Result, true) ;
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "未设置交易密码") ;
				return jsonObject.toString() ;
			}
			
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 下单提交
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String BtcTradeSubmit(HttpServletRequest request) throws Exception {
		try{
			int type = Integer.parseInt(request.getParameter("type")) ;
			if(type==0){
				return this.buyBtcSubmit(request) ;
			}else if(type==1){
				return this.sellBtcSubmit(request) ;
			}else{
				throw new Exception() ;
			}
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 查询委托交易列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetEntrustInfo(HttpServletRequest request) throws Exception {
		try{
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			int type = Integer.parseInt(request.getParameter("type")) ;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			
			
			int currentPage = 1 ;
			int totalPage = 0 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage<1?1:currentPage ;
			}catch(Exception e){}
			
			StringBuffer fstatus = new StringBuffer() ;
			if(type==0){
				//未成交
				fstatus.append(" (fstatus="+EntrustStatusEnum.Going+" or fstatus="+EntrustStatusEnum.PartDeal+") ") ;
			}else{
				//成交
				fstatus.append(" (fstatus="+EntrustStatusEnum.AllDeal+" or fstatus="+EntrustStatusEnum.Cancel+") ") ;
			}
			String filter = " where ftrademapping.fid="+symbol+" and "+fstatus.toString() +" and fuser.fid="+fuser.getFid()+" order by fid desc ";
			
			List<Fentrust> list = this.frontTradeService.findFentrustByParam((currentPage-1)*maxResult, maxResult, filter, true) ;
			int total = this.frontTradeService.findFentrustByParamCount(filter) ;
			totalPage = total/maxResult + (total%maxResult==0?0:1) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;
			
			JSONArray jsonArray = new JSONArray() ;
			for (int i = 0; i < list.size(); i++) {
				JSONObject item = new JSONObject() ;
				Fentrust fentrust = list.get(i) ;
				String title = null ;
				if(fentrust.isFisLimit()==true){
					if(fentrust.getFentrustType()==EntrustTypeEnum.BUY){
						title = "市价买入" ;
					}else{
						title = "市价卖出" ;
					}
				}else{
					if(fentrust.getFentrustType()==EntrustTypeEnum.BUY){
						title = "限价买入" ;
					}else{
						title = "限价卖出" ;
					}
				}
				
				item.accumulate("fid", fentrust.getFid()) ;
				item.accumulate("type", fentrust.getFentrustType()) ;
				item.accumulate("title", title) ;
				
				item.accumulate("fstatus", fentrust.getFstatus()) ;
				item.accumulate("fstatus_s", fentrust.getFstatus_s()) ;
				
				item.accumulate("flastUpdatTime", Utils.dateFormat(fentrust.getFlastUpdatTime())) ;
				
				item.accumulate("fprice", new BigDecimal(fentrust.getFprize()).setScale(6, BigDecimal.ROUND_HALF_UP)) ;
				item.accumulate("fcount", new BigDecimal(fentrust.getFcount()).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				item.accumulate("fsuccessCount", new BigDecimal(fentrust.getFcount()-fentrust.getFleftCount()).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				if(fentrust.getFcount()-fentrust.getFleftCount()<0.0001D){
					item.accumulate("fsuccessprice", 0D) ;
				}else{
					item.accumulate("fsuccessprice", new BigDecimal(fentrust.getFsuccessAmount()/(fentrust.getFcount()-fentrust.getFleftCount())).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				}
				
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("list", jsonArray) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 取消订单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String CancelEntrust(HttpServletRequest request) throws Exception {
		try {
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			int id = Integer.parseInt(request.getParameter("id")) ;
			
			Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			if(fentrust!=null
					&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
					&&fentrust.getFuser().getFid() == fuser.getFid() ){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
					if (fentrust.getFentrustType() == EntrustTypeEnum.BUY) {
						if (fentrust.isFisLimit()) {
							frontEntrustChangeService.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//						rabbitTemplate.convertAndSend("entrust.limit.buy.remove", symbolAndIdStr);
						} else {
							frontEntrustChangeService.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust);
//						rabbitTemplate.convertAndSend("entrust.buy.remove", symbolAndIdStr);
						}
					} else {
						if (fentrust.isFisLimit()) {
							frontEntrustChangeService.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//							rabbitTemplate.convertAndSend("entrust.limit.sell.remove", symbolAndIdStr);
						} else {
							frontEntrustChangeService.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust);
//							rabbitTemplate.convertAndSend("entrust.sell.remove", symbolAndIdStr);
						}
					}
					flag = true ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(flag==true){
					jsonObject.accumulate(ErrorCode , 0) ;//成功
					jsonObject.accumulate(Value, "取消成功") ;//成功
					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate(ErrorCode , -1) ;//失敗
					jsonObject.accumulate(Value, "取消失败") ;//成功
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate(ErrorCode , -1) ;//失敗
				jsonObject.accumulate(Value, "取消失败") ;//成功
				return jsonObject.toString() ;
			}
			
		} catch (Exception e) {
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 获取市场行情数据信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetMarketData(HttpServletRequest request) throws Exception {
		try{
			
	/*		String filter = "where fstatus="+TrademappingStatusEnum.ACTIVE+" order by fid asc";
			List<Ftrademapping> list = this.utilsService.list1(0, 0, filter,false,Ftrademapping.class);*/
			//根据域名获取市场列表
			List<Ftrademapping> list = getTrademappingListByDomain(request);
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;
			
			JSONArray jsonArray = new JSONArray() ;
			for (int i = 0; i < list.size(); i++) {
				Ftrademapping ftrademapping = list.get(i) ;
				int id = ftrademapping.getFid() ;
				int count1 = ftrademapping.getFcount1();//小数点
				int count2 = ftrademapping.getFcount2();//小数点
				
				JSONObject item = new JSONObject() ;
				item.accumulate("id", id) ;
				item.accumulate("cnyName", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname()) ;
				if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype()==CoinTypeEnum.FB_CNY_VALUE){
					item.accumulate("vir_id1", 0) ;
					item.accumulate("vir_id1_isWithDraw", true) ;
				}else{
					item.accumulate("vir_id1", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
					item.accumulate("vir_id1_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype1().isFIsWithDraw()) ;
				}
				item.accumulate("vir_id2", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
				item.accumulate("vir_id2_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype2().isFIsWithDraw()) ;
				item.accumulate("coinName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()) ;
				item.accumulate("cnName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname()) ;
				item.accumulate("currency", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfShortName()) ;
				item.accumulate("currencySymbol", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfSymbol()) ;
				item.accumulate("title", this.frontConstantMapService.get("webName"));
				item.accumulate("hasKline", true) ;
				item.accumulate("count1", count1) ;
				item.accumulate("count2", count2) ;
				
				boolean isLimittrade =false;
				String upPrice = "0";
				String downPrice = "0";
				Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
				if(limittrade != null){
					isLimittrade = true;
					upPrice = Utils.getDoubleS(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(),count1);
					downPrice = Utils.getDoubleS(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(),count1);
				}
				
				item.accumulate("isLimittrade", isLimittrade) ;
				item.accumulate("upPrice", upPrice) ;
				item.accumulate("downPrice", downPrice) ;
				
				//成交量，买一，卖一，最高，最低，现价
				item.accumulate("LatestDealPrice", Utils.getDoubleS(this.frontCacheService.getLatestDealPrize(id), count1)) ;
				item.accumulate("SellOnePrice", Utils.getDoubleS(this.frontCacheService.getLowestSellPrize(id), count1)) ;
				item.accumulate("BuyOnePrice", Utils.getDoubleS(this.frontCacheService.getHighestBuyPrize(id), count1)) ;
				item.accumulate("OneDayLowest", Utils.getDoubleS(this.frontCacheService.getLowest(id), count1)) ;
				item.accumulate("OneDayHighest", Utils.getDoubleS(this.frontCacheService.getHighest(id), count1)) ;
				item.accumulate("OneDayTotal", Utils.getDoubleS(this.frontCacheService.getTotal(id), 4)) ;

				if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==1){
					item.accumulate("fbprice",  Utils.getDouble(Double.valueOf(this.frontCacheService.getLatestDealPrize(1)), 2));
				}else if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==3){
					item.accumulate("fbprice",  Utils.getDouble(Double.valueOf(this.frontCacheService.getLatestDealPrize(2)), 2));
				}else{
					item.accumulate("fbprice",  1);
				}
				item.accumulate("usdtrate",  this.frontConstantMapService.get("USDT_PRICE").toString());
				
				//System.out.println(item.toString());
				
				double s = this.frontCacheService.get24Price(ftrademapping.getFid());
				List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)frontConstantMapService.get("tradehistory");
				for (Ftradehistory ftradehistory : ftradehistorys) {
					if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
						s= ftradehistory.getFprice();
						break;
					}
				}
				double last = 0d;
				try {
					last = Utils.getDouble((this.frontCacheService.getLatestDealPrize(ftrademapping.getFid())-s)/s*100, 2);
				} catch (Exception e) {}
				
				item.accumulate("priceRaiseRate", last) ;
				
				
				//logo
				item.accumulate("logo", Utils.curl(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl())) ;
				
				//七日涨跌幅数据
				JSONArray day7 = new JSONArray() ;
			
				Map ftradehistory7D = (Map)this.frontConstantMapService.get("ftradehistory7D");
				if(ftradehistory7D.containsKey(ftrademapping.getFid().intValue())){
					List ss = (List)ftradehistory7D.get(ftrademapping.getFid().intValue());
					Iterator it = ss.iterator();
					int index = 0 ;
					while(it.hasNext()){
						if(index++ == 6){
							day7.add(this.frontCacheService.getLatestDealPrize(ftrademapping.getFid())) ;
						}else{
							day7.add(it.next());
						}
					}
				}
				item.accumulate("day7", day7) ;
				
				jsonArray.add(item) ;
			}
			
			
			jsonObject.accumulate("list", jsonArray) ;
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	/**
	 * 获取交易挂单信息
	 * @param id 币种id
	 * @return
	 * @throws Exception
	 */
	public String GetMarketDepth(HttpServletRequest request) throws Exception {
		
		try{
			int symbol = Integer.parseInt(request.getParameter("id")) ;
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
			int count1 = ftrademapping.getFcount1() ;
			int count2 = ftrademapping.getFcount2() ;
			if(ftrademapping==null || ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(ErrorCode , -10000) ;//虚拟币不存在
				return jsonObject.toString() ;
			}
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;
			
			jsonObject.accumulate("cnyName", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname()) ;
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype()==CoinTypeEnum.FB_CNY_VALUE){
				jsonObject.accumulate("vir_id1", 0) ;
				jsonObject.accumulate("vir_id1_isWithDraw", true) ;
			}else{
				jsonObject.accumulate("vir_id1", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
				jsonObject.accumulate("vir_id1_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype1().isFIsWithDraw()) ;
			}
			jsonObject.accumulate("vir_id2", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
			jsonObject.accumulate("vir_id2_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype2().isFIsWithDraw()) ;
			jsonObject.accumulate("coinName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()) ;
			jsonObject.accumulate("cnName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname()) ;
			jsonObject.accumulate("currency", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfShortName()) ;
			jsonObject.accumulate("currencySymbol", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfSymbol()) ;
			//Object[] buyMap = this.frontCacheService.getBuyDepthMap(symbol,8) ;
			//Object[] sellMap = this.frontCacheService.getSellDepthMap(symbol,8) ;
			
			String sellEntrustsString = this.frontCacheService.getSellDepthMap(symbol);
			List<String> sellEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(sellEntrustsString, String.class);
			String buyEntrustsString = this.frontCacheService.getBuyDepthMap(symbol);
			List<String> buyEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(buyEntrustsString, String.class);
			
			JSONArray buyArray = new JSONArray() ;
			JSONArray sellArray = new JSONArray() ;
			if (sellEntrustsList != null) {
				for (int i = 0; i < sellEntrustsList.size() && i < 20; i++){
					String fentrustString = sellEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					JSONObject item = new JSONObject() ;
					item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[0]), count1)+"") ;
					item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[1]), count2)+"") ;
					sellArray.add(item) ;
				}
			}
			
			if (buyEntrustsList != null) {
				for (int i = 0; i < buyEntrustsList.size() && i < 20 ; i++) {
					String fentrustString = buyEntrustsList.get(i);
					String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
					
					JSONObject item = new JSONObject() ;
					item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[0]), count1)+"") ;
					item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[1]), count2)+"") ;
					buyArray.add(item) ;
				}
			}
			
			jsonObject.accumulate("sellDepth", sellArray) ;
			jsonObject.accumulate("buyDepth", buyArray) ;
			
			jsonObject.accumulate("usdtrate",  this.frontConstantMapService.get("USDT_PRICE").toString());
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==1){
				jsonObject.accumulate("fbprice",  Utils.getDouble(this.frontCacheService.getLatestDealPrize(1), 2));
			}else if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()==3){
				jsonObject.accumulate("fbprice",  Utils.getDouble(this.frontCacheService.getLatestDealPrize(2), 2));
			}else{
				jsonObject.accumulate("fbprice",  1);
			}
			//quotation
			JSONObject quotation = new JSONObject() ;
			//成交量，买一，卖一，最高，最低，现价
			BigDecimal LatestDealPrice = new BigDecimal(this.frontCacheService.getLatestDealPrize(symbol)).setScale(count2, BigDecimal.ROUND_HALF_UP) ;
			BigDecimal SellOnePrice = new BigDecimal(this.frontCacheService.getLowestSellPrize(symbol)).setScale(count2, BigDecimal.ROUND_HALF_UP) ;
			BigDecimal BuyOnePrice = new BigDecimal(this.frontCacheService.getHighestBuyPrize(symbol)).setScale(count2, BigDecimal.ROUND_HALF_UP) ;

			quotation.accumulate("LatestDealPrice", LatestDealPrice) ;
			quotation.accumulate("SellOnePrice", SellOnePrice) ;
			quotation.accumulate("BuyOnePrice", BuyOnePrice) ;
			quotation.accumulate("OneDayLowest", new BigDecimal(this.frontCacheService.getLowest(symbol)).setScale(count2, BigDecimal.ROUND_HALF_UP)) ;
			quotation.accumulate("OneDayHighest", new BigDecimal(this.frontCacheService.getHighest(symbol)).setScale(count2, BigDecimal.ROUND_HALF_UP)) ;
			quotation.accumulate("OneDayTotal", new BigDecimal(this.frontCacheService.getTotal(symbol)).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
			
			List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)frontConstantMapService.get("tradehistory");
			Ftradehistory ftradehistory = null;
			for (Ftradehistory th : ftradehistorys) {
				if(th.getFtrademapping().getFid() == ftrademapping.getFid().intValue()){
					ftradehistory = th ;
					break;
				}
			}
			
			//24小时涨跌幅
			double priceRaiseRate = 0 ;
			double sx = this.frontCacheService.get24Price(ftrademapping.getFid());
			if(ftradehistory!=null ){
				sx= ftradehistory.getFprice();
			}
			try {
				priceRaiseRate = Utils.getDouble((this.frontCacheService.getLatestDealPrize(ftrademapping.getFid())-sx)/sx*100, 2);
			} catch (Exception e) {}
			
			quotation.accumulate("priceRaiseRate", priceRaiseRate) ;
			
			jsonObject.accumulate("quotation", quotation) ;
			
			//coinInfo
			JSONObject coinInfo = new JSONObject() ;
			coinInfo.accumulate("id", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
			coinInfo.accumulate("fname", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname()) ;
			coinInfo.accumulate("fShortName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()) ;
			coinInfo.accumulate("fSymbol", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfSymbol()) ;
			jsonObject.accumulate("coinInfo", coinInfo) ;
			
			//asset
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(fuser!=null){
				JSONObject asset = new JSONObject() ;
				Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid());
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
				
				JSONObject rmb = new JSONObject() ;
				rmb.accumulate("total", new BigDecimal(fwallet.getFtotal()).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				rmb.accumulate("frozen", new BigDecimal(fwallet.getFfrozen()).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				rmb.accumulate("canBuy", new BigDecimal((fwallet.getFtotal()/SellOnePrice.doubleValue())).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				
				JSONObject coin = new JSONObject() ;
				coin.accumulate("total", new BigDecimal(fvirtualwallet.getFtotal()).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				coin.accumulate("frozen", new BigDecimal(fvirtualwallet.getFfrozen()).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				coin.accumulate("canSell", new BigDecimal(fvirtualwallet.getFtotal()*BuyOnePrice.doubleValue()).setScale(4, BigDecimal.ROUND_HALF_UP)) ;
				
				asset.accumulate("totalAsset", new BigDecimal((fwallet.getFtotal()+fwallet.getFfrozen())).add(this.getVirtualCoinAsset(fuser)).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
				asset.accumulate("rmb", rmb) ;
				asset.accumulate("coin", coin) ;
				jsonObject.accumulate("asset", asset) ;
				
				//其他信息
				JSONObject others = new JSONObject() ;
				others.accumulate("isNeedPasswd", fuser.getFtradePassword()!=null) ;
				others.accumulate("minTradePrice", new BigDecimal(1/(Math.pow(10, count2))).setScale(count2, BigDecimal.ROUND_HALF_UP)) ;
				others.accumulate("minTradeCount", 0.0001D) ;
				others.accumulate("maxDecimal", count2) ;
				jsonObject.accumulate("others", others) ;
			}
			
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, false) ;
			jsonObject.accumulate(ErrorCode , -10000) ;//未知错误
			return jsonObject.toString() ;
		}
	}
	
	/**
	 * 获取app首页信息
	 * @param currentPage 当前页数
	 * @return
	 * @throws Exception
	 */
	public String GetNews(HttpServletRequest request) throws Exception {
		
		try{
			
			int currentPage = ApiConstant.getInt(request.getParameter(CurrentPage)) ;
			int id = ApiConstant.getInt(request.getParameter("id")) ;
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;
			
			if(getReqDomain(request)!=null){
				
				jsonObject.accumulate("isprojectUrl", true) ;
				Pproject pproject = frontPprojectService.findByProperty("projectId.fid", getReqDomain(request).getProjectId().getFid());
				
				JSONObject pprojectjson=new JSONObject() ;
				
				pprojectjson.accumulate("id", pproject.getId()) ;
				pprojectjson.accumulate("logoUrl", pproject.getLogoUrl()) ;
				pprojectjson.accumulate("name", pproject.getName()) ;
				pprojectjson.accumulate("advantage", pproject.getAdvantage()) ;
				pprojectjson.accumulate("introduce", pproject.getIntroduce()) ;
				jsonObject.accumulate("project", pprojectjson) ;
				
				//request.setAttribute("projectname", pproject.getLogoUrl()) ;
			}else{
				
				jsonObject.accumulate("isprojectUrl", false) ;
				
				JSONArray banners = new JSONArray() ;
				
				for (int i = 0; i < 3; i++) {
					String banner = frontConstantMapService.getString("bigImage"+(i+1)) ;
					String bigImageURL = frontConstantMapService.getString("bigImageURL"+(i+1)) ;
					String title = frontConstantMapService.getString("bigImageTitle"+(i+1)) ;
					banner="".equals(banner)?null:(banner) ;				
					JSONObject item = new JSONObject() ;
					item.accumulate("img", OSSPostObject.URL+banner) ;
					item.accumulate("url", bigImageURL.trim()) ;
					item.accumulate("title", title) ;
					banners.add(item) ;
					
				}
				
				jsonObject.accumulate("banners", banners) ;
			}
			//banner图片
			
			
			//新闻
			

			int total =1;
			Pdomain pdomain = getReqDomain(request);
			List<Farticle> farticles = new ArrayList<Farticle>();
			//项目方域名展示官方公告
			if(id>0){
				if(id==1&&null!=pdomain){
					farticles = this.frontOthersService.findProjectFarticle(pdomain.getProjectId().getFid(), id,(currentPage-1)*maxResult, maxResult);
					total = this.frontOthersService.findProjectFarticleCount(pdomain.getProjectId().getFid(), id);
				}else{
					farticles = this.frontOthersService.findFarticle(id, (currentPage-1)*maxResult, maxResult) ;
					total = this.frontOthersService.findFarticleCount(id) ;
				}	
			}else{
				 farticles = this.utilsService.list4((currentPage-1)*maxResult, maxResult, "  order by fid desc ", true) ;
				 total = this.utilsService.count("", Farticle.class) ;
			}
									
			String appNameNews = this.frontConstantMapService.getString("webName") ;

			JSONArray jsonArray = new JSONArray() ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Farticle farticle : farticles) {
				JSONObject item = new JSONObject() ;
				int aid = farticle.getFid() ; 
				item.accumulate("id", aid);
				item.accumulate("title", farticle.getFtitle()) ;
				item.accumulate("title_cn", farticle.getFtitle_cn());
				item.accumulate("content", farticle.getFcontent());
				item.accumulate("content_cn", farticle.getFcontent_cn());
				item.accumulate("date", sdf.format(farticle.getFlastModifyDate())) ;
				item.accumulate("from", appNameNews) ;
				item.accumulate("img", farticle.getFurl()) ;
				item.accumulate("type", farticle.getFarticletype().getFname()) ;
				jsonArray.add(item) ;
			}
			
			jsonObject.accumulate("news", jsonArray) ;
			jsonObject.accumulate(CurrentPage, currentPage) ;
			jsonObject.accumulate(TotalPage, totalPage(total, maxResult)) ;
			
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}

	
	/**
	 * 获取文章信息
	 * @param request
	 * @param id  文章id
	 * @return
	 * @throws Exception
	 */
	public String article(HttpServletRequest request) throws Exception{
	
		int id = ApiConstant.getInt(request.getParameter("id")) ;
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;		
		Farticle farticle = this.frontOthersService.findFarticleById(id) ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(farticle!=null){
			JSONObject item = new JSONObject() ;
			int aid = farticle.getFid() ; 
			item.accumulate("id", aid);
			item.accumulate("title", farticle.getFtitle()) ;
			item.accumulate("title_cn", farticle.getFtitle_cn());
			item.accumulate("content", farticle.getFcontent());
			item.accumulate("content_cn", farticle.getFcontent_cn());
			item.accumulate("date", sdf.format(farticle.getFlastModifyDate())) ;

			item.accumulate("img", farticle.getFurl()) ;
			item.accumulate("type", farticle.getFarticletype().getFname()) ;
			item.accumulate("typeId", farticle.getFarticletype().getFid()) ;
			jsonObject.accumulate("accumulate",item) ;
		}
		
		return jsonObject.toString() ;
	}
	
	
	/**
	 * 获取用户总资产---供GetMarketDepth方法调用
	 * @param fuser
	 * @return
	 */
	private BigDecimal getVirtualCoinAsset(Fuser fuser){
		double total = 0D ;
		String filter = "where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and ftype<>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
		Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapService.get("tradeMappings");
		for (int i = 0; i < fvirtualcointypes.size(); i++) {
			if(!tradeMappings.containsKey(fvirtualcointypes.get(i).getFid())) continue;
			int trademapping_id = tradeMappings.get(fvirtualcointypes.get(i).getFid()) ;
			System.out.println("---------"+trademapping_id);
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointypes.get(i).getFid()) ;
			
			double curPrice = this.frontCacheService.getLatestDealPrize(trademapping_id) ;
			System.out.println("---------"+curPrice);
			double asset = curPrice*(fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen()) ;
			
			total+=asset ;
		}
		return new BigDecimal(total).setScale(4, BigDecimal.ROUND_HALF_UP) ;
	}
	
	
	/**
	 * 获取app版本信息，和下载地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetVersion(HttpServletRequest request) throws Exception {
		String android_version = this.frontConstantMapService.getString("android_version");
		String ios_version = this.frontConstantMapService.getString("ios_version");
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		jsonObject.accumulate("ios_version", ios_version) ;
		jsonObject.accumulate("android_version", android_version) ;
		jsonObject.accumulate("android_downurl", this.frontConstantMapService.getString("android_downurl")) ;
		jsonObject.accumulate("ios_downurl", this.frontConstantMapService.getString("ios_downurl")) ;
		return jsonObject.toString() ;
	}
	
	/**
	 * 获取关于我们的相关信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetAbout(HttpServletRequest request) throws Exception {
		String webName = this.frontConstantMapService.getString("webName");
		String telephone = this.frontConstantMapService.getString("telephone");
		String serviceQQ = this.frontConstantMapService.getString("serviceQQ");
		String email = this.frontConstantMapService.getString("email");
		String fulldomain = this.frontConstantMapService.getString("fulldomain");
		Fwebbaseinfo webinfo = (Fwebbaseinfo)this.frontConstantMapService.get("webinfo");
		
		
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		jsonObject.accumulate("fulldomain", fulldomain) ;
		jsonObject.accumulate("copyRights", webinfo.getFcopyRights()) ;
		jsonObject.accumulate("webName", webName) ;
		jsonObject.accumulate("telephone", telephone) ;
		jsonObject.accumulate("serviceQQ", serviceQQ) ;
		jsonObject.accumulate("email", email) ;
		return jsonObject.toString() ;
	}

	
	
	/**
	 * 获取虚拟币充值记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetBtcRechargeListRecord(HttpServletRequest request) throws Exception{
		
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			int fvirtualCointypeId = Integer.parseInt(request.getParameter("id")) ;
			int currentPage = 1 ;
			int totalPage = 0 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage<1?1:currentPage ;
			}catch(Exception e){}
			
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(fvirtualCointypeId) ;
			if(fvirtualcointype==null 
					|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE 
					|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal 
					|| !fvirtualcointype.isFisrecharge()){
				return ApiConstant.getUnknownError(null) ;
			}
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			Fvirtualaddress fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
			
			jsonObject.accumulate("address", fvirtualaddress.getFadderess()) ;
			
			JSONArray jsonArray = new JSONArray() ;
			String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_IN
					+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
			
			int totalCount = this.frontVirtualCoinService.findFvirtualcaptualoperationsCount(filter) ;
			totalPage = totalCount / maxResult +(totalCount%maxResult==0?0:1) ;
			
			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			
			List<Fvirtualcaptualoperation> list = this.frontVirtualCoinService.findFvirtualcaptualoperations((currentPage-1)*maxResult, maxResult, filter, true) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			for (int i = 0; i < list.size(); i++) {
				Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i) ;
				
				JSONObject item = new JSONObject() ;
				item.accumulate("id", fvirtualcaptualoperation.getFid()) ;
				item.accumulate("fvi_id", fvirtualcaptualoperation.getFvirtualcointype().getFid()) ;
				item.accumulate("fcreateTime", sdf.format(fvirtualcaptualoperation.getFcreateTime())) ;
				item.accumulate("flastUpdateTime", sdf.format(fvirtualcaptualoperation.getFlastUpdateTime())) ;
				item.accumulate("famount", fvirtualcaptualoperation.getFamount()) ;
				item.accumulate("ffees", fvirtualcaptualoperation.getFfees()) ;
				item.accumulate("ftype", fvirtualcaptualoperation.getFtype()) ;
				if(fvirtualcaptualoperation.getFtype()==VirtualCapitalOperationTypeEnum.COIN_IN){
					item.accumulate("fstatus", VirtualCapitalOperationInStatusEnum.getEnumString(fvirtualcaptualoperation.getFstatus())) ;
				}else{
					item.accumulate("fstatus", VirtualCapitalOperationOutStatusEnum.getEnumString(fvirtualcaptualoperation.getFstatus())) ;
				}
				item.accumulate("recharge_virtual_address", fvirtualcaptualoperation.getRecharge_virtual_address()) ;
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("list", jsonArray) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 获取订单记录列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetAccountRecord(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		try{
			int currentPage = 1 ;
			int type = 0 ;
			int symbol = 0 ;
			Fvirtualcointype fvirtualcointype = null ;
			try{
				symbol = Integer.parseInt(request.getParameter("symbol")) ;
				fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
				type = Integer.parseInt(request.getParameter("type")) ;
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}
			
			//内容
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			int maxReulsts = maxResult ;
			StringBuffer sb = new StringBuffer(" where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol+"  ") ;
			if(type == 0 ){
				//未成交
				sb.append(" and (fstatus="+EntrustStatusEnum.PartDeal+"  or fstatus="+EntrustStatusEnum.Going+") ") ;
			}else{
				sb.append(" and (fstatus="+EntrustStatusEnum.Cancel+"  or fstatus="+EntrustStatusEnum.AllDeal+") ") ;
			}
			sb.append(" order by fid desc ") ;
			List<Fentrust> fentrusts = this.utilsService.list((currentPage-1)*maxReulsts, maxReulsts, sb.toString(), true, Fentrust.class) ;
			int count = this.utilsService.count(sb.toString(), Fentrust.class) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			jsonObject.accumulate("totalPage", count/maxReulsts+(count%maxReulsts==0?0:1)) ;
			
			JSONArray jsonArray = new JSONArray() ;
			for (int i = 0; i < fentrusts.size(); i++) {
				Fentrust fentrust = fentrusts.get(i) ;
				
				JSONObject item = new JSONObject() ;
				item.accumulate("fid", fentrust.getFid()) ;
				item.accumulate("date", Utils.dateFormat(fentrust.getFcreateTime())) ;
				item.accumulate("title", fvirtualcointype.getfShortName()+fentrust.getFentrustType_s()) ;
				item.accumulate("count", fentrust.getFcount()) ;
				item.accumulate("leftCount", new BigDecimal(fentrust.getFleftCount())) ;
				item.accumulate("price", fentrust.getFprize()) ;
				item.accumulate("fee", fentrust.getFfees()) ;
				item.accumulate("status", fentrust.getFstatus()) ;
				item.accumulate("status_s", fentrust.getFstatus_s()) ;
				item.accumulate("type", fentrust.getFentrustType()) ;
				item.accumulate("type_s", fentrust.getFentrustType_s()) ;
				
				jsonArray.add(item) ;
			}
			
			jsonObject.accumulate("list", jsonArray) ;
			return jsonObject.toString() ;
			
			
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return null ;
	}

	
	/**
	 * 获取币种列表信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetCoinListInfo(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		try{
			JSONArray jsonArray = new JSONArray() ;
			String filter = "where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and ftype<>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
			for (int i = 0; i < fvirtualcointypes.size(); i++) {
				JSONObject item = new JSONObject();
				Fvirtualcointype fvirtualcointype = fvirtualcointypes.get(i) ;				
				item.accumulate("id", fvirtualcointype.getFid()) ;
				item.accumulate("fname", fvirtualcointype.getFname()) ;
				item.accumulate("fShortName", fvirtualcointype.getfShortName()) ;
				item.accumulate("fSymbol", fvirtualcointype.getfSymbol()) ;
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("coinList", jsonArray) ;
			return jsonObject.toString() ;
 		}catch(Exception e){
 			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
 		}
	}
	
	/**
	 * 获取用户信息及资产信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetAccountInfo(HttpServletRequest request) throws Exception {
		
		try{

			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			JSONObject userInfoObject = new JSONObject() ;
			userInfoObject.accumulate("nickName", fuser.getFnickName()) ;
			userInfoObject.accumulate("isGoogleBind", fuser.getFgoogleBind()) ;
			userInfoObject.accumulate("isSecondValidate", fuser.getFopenSecondValidate()) ;
			userInfoObject.accumulate("fid", fuser.getFid()) ;
			//真实姓名，手机号码
			userInfoObject.accumulate("isRealName", fuser.getFpostRealValidate()) ;
			if(fuser.getFpostRealValidate() == true ){
				userInfoObject.accumulate("realName", fuser.getFrealName()) ;
			}else{
				userInfoObject.accumulate("realName", null) ;
			}
			userInfoObject.accumulate("isBindMobil", fuser.isFisTelephoneBind()) ;
			if(fuser.isFisTelephoneBind() == true ){
				userInfoObject.accumulate("tel", fuser.getFtelephone()) ;
			}else{
				userInfoObject.accumulate("tel", null) ;
			}
			if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
				userInfoObject.accumulate("isHasTradePWD",0) ;
			}else{
				userInfoObject.accumulate("isHasTradePWD",1) ;
			}
			
			jsonObject.accumulate("userInfo", userInfoObject) ;
			
			JSONObject assetInfoObject = new JSONObject() ;
			JSONArray tradeAccountArray = new JSONArray() ;
			JSONArray marginAccountArray = new JSONArray() ;
			
			//JSONObject tradeCnyOjbect = new JSONObject() ;
			JSONObject marginCnyObject = new JSONObject() ;
			
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid()) ;
			/*tradeCnyOjbect.accumulate("id", 0) ;
			tradeCnyOjbect.accumulate("cnName", "人民币") ;
			tradeCnyOjbect.accumulate("shortName", "CNY") ;
			tradeCnyOjbect.accumulate("total", new BigDecimal(fwallet.getFtotal()).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
			tradeCnyOjbect.accumulate("frozen", new BigDecimal(fwallet.getFfrozen()).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
			tradeCnyOjbect.accumulate("isCoin", false) ;
			
			tradeAccountArray.add(tradeCnyOjbect) ;*/
			
			marginCnyObject.accumulate("id", 0) ;
			marginCnyObject.accumulate("total", 0) ;
			marginCnyObject.accumulate("frozen",0) ;
			marginCnyObject.accumulate("borrow",0) ;
			marginAccountArray.add(marginCnyObject) ;
			
			double totalAsset = 0D;//总资产
			double netAsset = 0D ;//净资产
			double tradeTotalAsset = 0D ;//交易总资产
			double tradeNetAsset = 0D ;//交易净资产
			double marginTotalAsset = 0D ;//放款总资产
			//总借金额
			double totalBorrow = 0d;
			
			totalAsset+=fwallet.getFtotal()+fwallet.getFfrozen()+marginTotalAsset ;
			netAsset+=fwallet.getFtotal()+fwallet.getFfrozen()+marginTotalAsset ;
			
			tradeTotalAsset+=fwallet.getFtotal()+fwallet.getFfrozen() ;
			tradeNetAsset+=fwallet.getFtotal()+fwallet.getFfrozen() ;

			//USDT钱包
			Fvirtualwallet usdtfwallet = this.frontUserService.findUSDTWalletByUser(this.getAppFuser(this.curLoginToken).getFid()) ;
			JSONObject usdtfwalletJson = new JSONObject() ;
			usdtfwalletJson.accumulate("id", usdtfwallet.getFvirtualcointype().getFid()) ;
			usdtfwalletJson.accumulate("logo", Utils.curl(usdtfwallet.getFvirtualcointype().getFurl())) ;
			usdtfwalletJson.accumulate("cnName", usdtfwallet.getFvirtualcointype().getFname()) ;
			usdtfwalletJson.accumulate("shortName", usdtfwallet.getFvirtualcointype().getfShortName()) ;
			usdtfwalletJson.accumulate("total", new BigDecimal(usdtfwallet.getFtotal()).setScale(2, BigDecimal.ROUND_DOWN)) ;
			usdtfwalletJson.accumulate("frozen", new BigDecimal(usdtfwallet.getFfrozen()).setScale(2, BigDecimal.ROUND_DOWN)) ;
			usdtfwalletJson.accumulate("borrow", 0) ;

			usdtfwalletJson.accumulate("isCoin", true) ;
			usdtfwalletJson.accumulate("isWithDraw",  usdtfwallet.getFvirtualcointype().isFIsWithDraw()) ;
			usdtfwalletJson.accumulate("isRecharge",  usdtfwallet.getFvirtualcointype().isFisrecharge()) ;
			
			jsonObject.accumulate("usdtfwallet", usdtfwalletJson) ;
			
			//虚拟币地址：
			JSONArray coinAddress = new JSONArray() ;
			
			String filter = "where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and  ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and ftype <>"+CoinTypeEnum.FB_USDT_VALUE+"order by fid asc";
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
			Map<Integer,Integer> tradeMappings = (Map)this.frontConstantMapService.get("tradeMappings");
			for (int i = 0; i < fvirtualcointypes.size(); i++) {
				Fvirtualcointype fvirtualcointype = fvirtualcointypes.get(i) ;
				
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
				JSONObject tradeObj = new JSONObject() ;
				JSONObject marginObj = new JSONObject() ;
				double curPrice =0.0;
				double asset=0.0;
				if(tradeMappings.containsKey(fvirtualcointype.getFid())) {
					int ftrademapping_id = tradeMappings.get(fvirtualcointype.getFid()) ;
					 curPrice = this.frontCacheService.getLatestDealPrize(ftrademapping_id) ;
					 asset = curPrice*(fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen()) ;
					
					totalAsset = totalAsset + asset;
					netAsset = netAsset + asset;
					tradeTotalAsset+=asset ;
					tradeNetAsset+=asset ;
				};
				
				tradeObj.accumulate("id", fvirtualcointype.getFid()) ;
				tradeObj.accumulate("logo", Utils.curl(fvirtualcointype.getFurl())) ;
				tradeObj.accumulate("cnName", fvirtualcointype.getFname()) ;
				tradeObj.accumulate("shortName", fvirtualcointype.getfShortName()) ;
				tradeObj.accumulate("total", new BigDecimal(fvirtualwallet.getFtotal()).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
				tradeObj.accumulate("frozen", new BigDecimal(fvirtualwallet.getFfrozen()).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
				tradeObj.accumulate("borrow", 0) ;
				tradeObj.accumulate("zhehe", Utils.getDouble(asset, 2)) ;
				tradeObj.accumulate("curPrice", Utils.getDouble(curPrice, 2)) ;
				tradeObj.accumulate("isCoin", true) ;
				tradeObj.accumulate("isWithDraw", fvirtualcointype.isFIsWithDraw()) ;
				tradeObj.accumulate("isRecharge", fvirtualcointype.isFisrecharge()) ;
				tradeAccountArray.add(tradeObj) ;
				
				marginObj.accumulate("id", fvirtualcointype.getFid()) ;
				marginObj.accumulate("total", 0) ;
				marginObj.accumulate("frozen",0) ;
				marginObj.accumulate("borrow",0) ;
				marginAccountArray.add(marginObj) ;
				
				//虚拟币地址
				if(fvirtualcointype.isFisrecharge()){
					Fvirtualaddress fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
					JSONObject item = new JSONObject() ;
					item.accumulate("coinId", fvirtualcointype.getFid()) ;
					item.accumulate("coinName", fvirtualcointype.getFname()) ;
					item.accumulate("coinShortName", fvirtualcointype.getfShortName()) ;
					if(fvirtualaddress != null){
						item.accumulate("address", fvirtualaddress.getFadderess()) ;
					}else{
						item.accumulate("address", null) ;
					}
					coinAddress.add(item) ;
				}
			}
			assetInfoObject.accumulate("totalAsset", new BigDecimal(totalAsset).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
			
			JSONObject tradeAccountObject = new JSONObject() ;
			tradeAccountObject.accumulate("coins", tradeAccountArray) ;
			tradeAccountObject.accumulate("totalAsset", new BigDecimal(tradeTotalAsset).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
			tradeAccountObject.accumulate("netAsset", new BigDecimal(tradeNetAsset-totalBorrow).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
			assetInfoObject.accumulate("tradeAccount", tradeAccountObject) ;
			
			
			JSONObject marginAccountObject = new JSONObject() ;
			marginAccountObject.accumulate("coins", marginAccountArray) ;
			marginAccountObject.accumulate("totalAsset", new BigDecimal(marginTotalAsset).setScale(2, BigDecimal.ROUND_HALF_UP)) ;
			assetInfoObject.accumulate("marginAccount", marginAccountObject) ;
			
			jsonObject.accumulate("asset", assetInfoObject) ;
			
			//提现银行卡信息
			List<FbankinfoWithdraw> fbankinfoWithdraw = this.utilsService.list(0, 0, " where fuser.fid="+fuser.getFid()+" and  fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", false, FbankinfoWithdraw.class) ;
			JSONArray withdrawInfos = new JSONArray() ;
			for (FbankinfoWithdraw withdrawInfo : fbankinfoWithdraw) {
				JSONObject item = new JSONObject() ;
				item.accumulate("id", withdrawInfo.getFid()) ;
				item.accumulate("bankType", withdrawInfo.getFbankType()) ;
				item.accumulate("address", withdrawInfo.getFaddress()) ;
				item.accumulate("bankNumber", BankTypeEnum.getEnumString(withdrawInfo.getFbankType())+" 尾号"+(withdrawInfo.getFbankNumber().length()>=4?(withdrawInfo.getFbankNumber().substring(withdrawInfo.getFbankNumber().length()-4)):withdrawInfo.getFbankNumber())) ;
				
				withdrawInfos.add(item) ;
			}
			jsonObject.accumulate("withdrawInfos", withdrawInfos) ;
			
			//虚拟币地址
			jsonObject.accumulate("coinAddress", coinAddress) ;
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	/**
	 * 获取虚拟币地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getCoinAddress(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		try{
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			int symbol = Integer.parseInt(request.getParameter("symbol"));
			String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
			int count = this.adminService.getAllCount("Fvirtualaddress", filter);
			if(count >0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "已存在地址，不允许重复获取") ;
				return jsonObject.toString() ;
			}
			Fvirtualcointype coin = this.virtualCoinService.findById(symbol);
			if(coin.isFisrecharge()){
				Fpool fpool = this.poolService.getOneFpool(coin) ;
				if(fpool == null){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "地址库地址不足，请联系管理员") ;
					return jsonObject.toString() ;
				}
				String address = fpool.getFaddress() ;
				Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
				fvirtualaddress.setFadderess(address) ;
				fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
				fvirtualaddress.setFuser(fuser) ;
				fvirtualaddress.setFvirtualcointype(coin) ;
				if(address==null || address.trim().equalsIgnoreCase("null") || address.trim().equals("")){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "地址库地址不足，请联系管理员") ;
					return jsonObject.toString() ;
				}
				fpool.setFstatus(1) ;
				this.poolService.updateObj(fpool) ;
				this.virtualaddressService.saveObj(fvirtualaddress) ;
				jsonObject.accumulate("address", fvirtualaddress.getFadderess()) ;
			}else{
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "该虚拟币不支持充值") ;
				return jsonObject.toString() ;
			}
			
			jsonObject.accumulate(ErrorCode, 0) ;
			return jsonObject.toString();
 		}catch(Exception e){
 			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
 		}
	}
	
	/**
	 * 用户注册
	 * @param password
	 * @param email
	 * @param type
	 * @param code
	 * @param request
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String UserRegister(HttpServletRequest request) throws Exception {
		try{

			String areaCode = "86" ;
			String password = request.getParameter("password") ;
			String regName = request.getParameter("email") ;
			int regType = Integer.parseInt( request.getParameter("type") ) ;
			String ecode = request.getParameter(ErrorCode) ;
			String phoneCode = request.getParameter(ErrorCode) ;
			
			String api_key = request.getParameter("api_key") ;
			List<Fapi> fapis = this.utilsService.list(0, 0, " where fpartner=? ", false, Fapi.class,api_key) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			String phone = HtmlUtils.htmlEscape(regName).toLowerCase();
			phoneCode = HtmlUtils.htmlEscape(phoneCode);
			String isOpenReg = frontConstantMapService.get("isOpenReg").toString().trim();
			if(!isOpenReg.equals("1")){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "暂停注册") ;
				return jsonObject.toString() ;
			}
			
			password = HtmlUtils.htmlEscape(password.trim());
			if(password == null || password.length() <6){
				jsonObject.accumulate(ErrorCode, -2) ;
				jsonObject.accumulate(Value, "登陆密码长度有误") ;
				return jsonObject.toString() ;
			}
			//邮箱
			if(regType==1){
				//手机注册
				
				boolean flag1 = this.frontUserService.isTelephoneExists(regName) ;
				if(flag1){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "手机号码已经被注册") ;
					return jsonObject.toString() ;
				}
				
				if(!phone.matches(Constant.PhoneReg)){
					jsonObject.accumulate(ErrorCode, -4) ;
					jsonObject.accumulate(Value, "手机格式错误") ;
					return jsonObject.toString() ;
				}
				
				boolean mobilValidate = validateMessageCode(getIpAddr(request),areaCode,phone, MessageTypeEnum.REG_CODE, phoneCode) ;
				if(!mobilValidate){
					jsonObject.accumulate(ErrorCode, -5) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}
				
			}else{
				//邮箱注册
				boolean flag = this.frontUserService.isEmailExists(regName) ;
				if(flag){
					jsonObject.accumulate(ErrorCode, -6) ;
					jsonObject.accumulate(Value, "邮箱已经存在") ;
					return jsonObject.toString() ;
				}
				
				boolean mailValidate = validateMailCode(getIpAddr(request), phone, SendMailTypeEnum.RegMail, ecode);
				if(!mailValidate){
					jsonObject.accumulate(ErrorCode, -7) ;
					jsonObject.accumulate(Value, "邮箱验证码错误") ;
					return jsonObject.toString() ;
				}
				
				if(!regName.matches(Constant.EmailReg)){
					jsonObject.accumulate(ErrorCode, -8) ;
					jsonObject.accumulate(Value, "邮箱格式错误") ;
					return jsonObject.toString() ;
				}
				
			}
			
			//推广
			Fuser intro = null ;
			Integer fIntroUserId = 0;
			
			try {
				String intro_user = request.getParameter("intro_user") ;
				if(intro_user!=null && !"".equals(intro_user.trim())){
					List<Fuser> introList = this.frontUserService.findUserByProperty("fuserNo", intro_user) ;
					if(introList!=null&&introList.size()>0){
						intro=introList.get(0);
						fIntroUserId = intro.getFid();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String isMustIntrol = frontConstantMapService.get("isMustIntrol_"+this.appApiUid).toString().trim();
			
			if(intro==null){
				if(isMustIntrol.equals("1")){
					jsonObject.accumulate(ErrorCode, -9) ;
					jsonObject.accumulate(Value, "请填写正确的邀请码") ;
					return jsonObject.toString();
				}
			}else {
				if(isMustIntrol.equals("1") && !this.appApiUid.equals(intro.getFregfrom())){
					jsonObject.accumulate(ErrorCode, -9) ;
					jsonObject.accumulate(Value, "请填写正确的邀请码") ;
					return jsonObject.toString();
				}
			}
			
			Fuser fuser = new Fuser() ;
			if(intro!=null){
				fuser.setfIntroUser_id(intro) ;
			}
			fuser.setFintrolUserNo(null);
			
			if(regType == 1){
				//手机注册
				fuser.setFregType(RegTypeEnum.TEL_VALUE);
				fuser.setFtelephone(phone);
				fuser.setFareaCode(areaCode);
				fuser.setFisTelephoneBind(true);
				
				fuser.setFnickName(phone) ;
				fuser.setFloginName(phone) ;
			}else{
				fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
				fuser.setFemail(regName) ;
				fuser.setFisMailValidate(true) ;
				fuser.setFnickName(regName.split("@")[0]) ;
				fuser.setFloginName(regName) ;
			}
			
			fuser.setSalt(Utils.getUUID());
			fuser.setFregisterTime(Utils.getTimestamp()) ;
			fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt())) ;
			fuser.setFtradePassword(null) ;
			String ip = getIpAddr(request) ;
			fuser.setFregIp(ip);
			fuser.setFlastLoginIp(ip);
			fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
			fuser.setFlastLoginTime(Utils.getTimestamp()) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			//注册来源
			fuser.setFregfrom(this.appApiUid);
			//是否拥有APP权限
			fuser.setFgrade(0);
			Fuser fuser1 =null ;
			try {
				fuser1 = this.frontUserService.saveRegister(fuser) ;
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(regType==1){
					String key1 = ip+"message_"+MessageTypeEnum.REG_CODE ;
					String key2 = ip+"_"+phone+"_"+MessageTypeEnum.REG_CODE ;
					this.frontValidateMapService.removeMessageMap(key1);
					this.frontValidateMapService.removeMessageMap(key2);
				}else{
					String key1 = ip+"mail_"+SendMailTypeEnum.RegMail ;
					String key2 = ip+"_"+phone+"_"+SendMailTypeEnum.RegMail ;
					this.frontValidateMapService.removeMailMap(key1);
					this.frontValidateMapService.removeMailMap(key2);
				}
			}
			
			if(fuser1!=null){
				String loginToken = this.putAppSession(getSession(request), fuser1);
				jsonObject.accumulate(ErrorCode, 0);// 注册成功
				jsonObject.accumulate(Value, "注册成功");// 注册成功
				jsonObject.accumulate(LoginToken, loginToken);
				jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate());
				jsonObject.accumulate("inviteCode", fuser1.getFuserNo());
				jsonObject.accumulate("introUserId", fIntroUserId);
				jsonObject.accumulate(Fid, fuser1.getFid());
				return jsonObject.toString();
			
			}else{
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}
		
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 手机用户登录
	 * @param email 登录名
	 * @param password 密码
	 * @return
	 * @throws Exception
	 */
	public String UserLogin(HttpServletRequest request) throws Exception {
		try {
			
			String email = request.getParameter("email").trim().toLowerCase() ;
			String password = request.getParameter("password").trim() ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			int longLogin = -1;//0是手机，1是邮箱
			if(email.matches(Constant.PhoneReg)){
				longLogin = 0 ;
			}else{
				if(EmailValidator.getInstance().isValid(email)){
					longLogin = 1 ;
				}
			}
			if(longLogin == -1){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "用户异常或者登录频繁") ;
				return jsonObject.toString() ;
			}
			
			List<Fuser> fusers = this.frontUserService.findUserByProperty("floginName", email);
			if(fusers == null || fusers.size() != 1){
				jsonObject.accumulate(ErrorCode, -4) ;
				jsonObject.accumulate(Value, "用户名不存在") ;
				return jsonObject.toString() ;
			}

			String regFrom = fusers.get(0).getFregfrom();
			
			if(null==regFrom || !regFrom.equals(this.appApiUid)) {
				jsonObject.accumulate(ErrorCode, -4) ;
				jsonObject.accumulate(Value, "用户名不存在") ;
				return jsonObject.toString() ;
			}
			
			Fuser fuser = new Fuser() ;
			fuser.setFloginName(email);
			fuser.setFloginPassword(password) ;
			fuser.setSalt(fusers.get(0).getSalt());
			
			fuser.setFloginPassword(password) ;
			String ip = getIpAddr(request) ;
			int limitedCount = this.frontValidateService.getLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
			if(limitedCount<=0) {
				String appApiIp = this.frontConstantMapService.getString("appApiIp_"+this.appApiUid);
				if(null!=appApiIp && !appApiIp.equals("") && appApiIp.equals(ip)) {
					limitedCount=1;
					System.out.println("用户登录失败超过限制："+email);
				}
			}
			
			if(limitedCount>0){
				fuser = this.frontUserService.updateCheckLogin(fuser, ip,email.matches(Constant.EmailReg)) ;
				if(fuser!=null){
					
					String isCanlogin = frontConstantMapService.get("isCanlogin").toString().trim();
					if(!isCanlogin.equals("1")){
						boolean isCanLogin = false;
						String[] canLoginUsers = frontConstantMapService.get("canLoginUsers").toString().split("#");
						for(int i=0;i<canLoginUsers.length;i++){
							if(canLoginUsers[i].equals(String.valueOf(fuser.getFid()))){
								isCanLogin = true;
								break;
							}
						}
						if(!isCanLogin){
							jsonObject.accumulate(ErrorCode , -3);//用户被禁用
							jsonObject.accumulate(Value, "系统升级中，暂停登录 ") ;
							return jsonObject.toString() ;
						}
					}
					
					if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
						this.frontValidateService.deleteCountLimite(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
						jsonObject.accumulate(ErrorCode , 0);//登陆成功
						jsonObject.accumulate(Value, "登陆成功") ;
						String loginToken = this.putAppSession(getSession(request),fuser) ;
						jsonObject.accumulate(LoginToken, loginToken) ;
						jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate()) ;
						jsonObject.accumulate("postImagesValidate", fuser.isFpostImgValidate()) ;
						jsonObject.accumulate("hasImagesValidate", fuser.isFhasImgValidate()) ;
						if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
							jsonObject.accumulate("isHasTradePWD",0) ;
						}else{
							jsonObject.accumulate("isHasTradePWD",1) ;
						}
						
						boolean isBindGoogle = fuser.getFgoogleBind() ;
						boolean isBindTelephone = fuser.isFisTelephoneBind() ;
						boolean isBindEmail = fuser.getFisMailValidate();
						boolean isTradePassword = false;
						boolean isLoginPassword = true;
						if(fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
							isTradePassword = true;
						}
	
						Integer fIntroUserId = 0;
						if(null != fuser.getfIntroUser_id()) {
							fIntroUserId = fuser.getfIntroUser_id().getFid();
						}
						
				        jsonObject.accumulate("isBindGoogle", isBindGoogle);
				        jsonObject.accumulate("isBindTelephone", isBindTelephone);
				        jsonObject.accumulate("isBindEmail", isBindEmail);
				        jsonObject.accumulate("isTradePassword", isTradePassword);
				        jsonObject.accumulate("isLoginPassword", isLoginPassword);
				        jsonObject.accumulate("vip", fuser.getFscore().getFlevel());
						
						jsonObject.accumulate(Fid, fuser.getFid()) ;
						//推荐人id
				    jsonObject.accumulate("introUserId", fIntroUserId);
				    //邀请码
				    jsonObject.accumulate("inviteCode", fuser.getFuserNo());
						return jsonObject.toString() ;
					}else{
						jsonObject.accumulate(ErrorCode , -5);//用户被禁用
						jsonObject.accumulate(Value, "用户被禁用") ;
						return jsonObject.toString() ;
					}
				}else{
					//错误的用户名或密码
					if(limitedCount==Constant.ErrorCountLimit){
						jsonObject.accumulate(ErrorCode, -2) ;
						jsonObject.accumulate(Value, "用户名或密码错误") ;
					}else{
						jsonObject.accumulate(ErrorCode, -2) ;
						Object[] param = new Object[]{limitedCount};
						jsonObject.accumulate(Value, "用户名或密码错误") ;
						this.frontUserService.updateUserLog(fusers.get(0), ip, LogTypeEnum.User_LOGIN, "登录异常：账密错误超过"+limitedCount+"次");
					}
					this.frontValidateService.updateLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "用户异常或者登录频繁") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	//买币
	public String buyBtcSubmit(HttpServletRequest request) throws Exception{
		try{
			int limited=0;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			double tradeAmount = Double.parseDouble(request.getParameter("tradeAmount")) ;
			double tradeCnyPrice = Double.parseDouble(request.getParameter("tradeCnyPrice")) ;
			String tradePwd = request.getParameter("tradePwd") ;
			
			JSONObject jsonObject = new JSONObject() ;
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(isNeedTradePassword(request)){
				if(tradePwd == null || tradePwd.trim().length() == 0){
					jsonObject.accumulate(ErrorCode, -50) ;
					jsonObject.accumulate(Value, "交易密码不正确");
					return jsonObject.toString() ;
				}
				
				if(fuser.getFtradePassword() == null){
					jsonObject.accumulate(ErrorCode, -5) ;
					jsonObject.accumulate(Value, "您还没有设置交易密码，请到安全中心设置");
					return jsonObject.toString() ;
				}
				if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
					jsonObject.accumulate(ErrorCode, -2) ;
					jsonObject.accumulate(Value, "交易密码不正确");
					return jsonObject.toString() ;
				}
			}

			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
			if(ftrademapping==null  ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
				jsonObject.accumulate(ErrorCode, -100) ;
				jsonObject.accumulate(Value, "该币暂时不能交易");
				return jsonObject.toString() ;
			}
			
			//限制法币为人民币和比特币
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
					&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_USDT_VALUE
					&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误");
				return jsonObject.toString() ;
			
			}
			
			Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
			Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
			double minBuyCount = Utils.getDouble(ftrademapping.getFminBuyCount() ,ftrademapping.getFcount2()) ;
			double minBuyPrice = Utils.getDouble(ftrademapping.getFminBuyPrice(),ftrademapping.getFcount1()) ;
			double minBuyAmount = Utils.getDouble(ftrademapping.getFminBuyAmount(),ftrademapping.getFcount1()) ;
			double maxBuyCount = ftrademapping.getFmaxBuyCount() ;
			double maxBuyAmount = ftrademapping.getFmaxBuyAmount();
			double maxBuyPrice = ftrademapping.getFmaxBuyPrice();
			//是否开放交易
			if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
				jsonObject.accumulate(ErrorCode, -400) ;
				jsonObject.accumulate(Value, "现在不是交易时间");
				return jsonObject.toString() ;
			}
			
			tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
			tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
			if(!fuser.getFpostRealValidate()){
				jsonObject.accumulate(ErrorCode, -400) ;
				jsonObject.accumulate(Value, "请先进行实名认证");
				return jsonObject.toString() ;
			}
			
			//定价单
			if(limited == 0 ){
				
				if(tradeAmount<minBuyCount){
					DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "最小交易数量："+coin2.getfSymbol()+decimalFormat.format(minBuyCount));
					return jsonObject.toString() ;
				}
				
				
				if(tradeAmount>maxBuyCount && !fuser.isFistiger()){
					DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "最大交易数量："+coin2.getfSymbol()+decimalFormat.format(maxBuyCount));
					return jsonObject.toString() ;
				}
				
				if(tradeCnyPrice < minBuyPrice)
				{
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
					return jsonObject.toString() ;
				}
				
				double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
				if(total < minBuyAmount){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
					return jsonObject.toString() ;
				}
				
				if(total > maxBuyAmount && !fuser.isFistiger()){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最大挂单金额："+coin1.getfSymbol()+maxBuyAmount);
					return jsonObject.toString() ;
				}
				
				Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
				double upPrice = 0d;
				double downPrice = 0d;
				if(limittrade != null){
					upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
					downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
					if(downPrice <0) downPrice=0;
					if(tradeCnyPrice > upPrice){
						jsonObject.accumulate(ErrorCode, -3) ;
						jsonObject.accumulate(Value, "挂单价格不能高于涨停价:"+upPrice+coin1.getFname()) ;
						return jsonObject.toString() ; 
					}
					if(tradeCnyPrice < downPrice){
						jsonObject.accumulate(ErrorCode, -3) ;
						jsonObject.accumulate(Value, "挂单价格不能低于跌停价:"+downPrice+coin1.getFname()) ;
						return jsonObject.toString() ; 
					}
				}else{
					if(tradeCnyPrice > maxBuyPrice && !fuser.isFistiger()){
						jsonObject.accumulate(ErrorCode, -3) ;
						jsonObject.accumulate(Value, "最大挂单价格："+coin1.getfSymbol()+maxBuyPrice);
						return jsonObject.toString() ;
					}
				}
			}else{
				if(tradeCnyPrice <minBuyAmount){
					jsonObject.accumulate(ErrorCode, -33) ;
					jsonObject.accumulate(Value, "最小交易金额："+minBuyAmount+coin1.getFname());
					return jsonObject.toString() ;
				}
			}

			double totalTradePrice = 0F ;
			if(limited==0){
				totalTradePrice = tradeAmount*tradeCnyPrice ;
			}else{
				totalTradePrice = tradeAmount ;
			}
			Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),coin1.getFid());
			if(fwallet.getFtotal()<totalTradePrice){
				jsonObject.accumulate(ErrorCode, -4) ;
				jsonObject.accumulate(Value, coin1.getFname()+"余额不足");
				return jsonObject.toString() ;
			}
			
			//从请求匹配缓存中的项目方域名列表
			Pdomain pdomain = getReqDomain(request);
			Integer fsourceId = 0;
			double commissionRate = 0;
			double feesRate = 0;
			if(pdomain != null) {
				fsourceId = pdomain.getProjectId().getFid();
				//主站访问和项目方网站访问自己的市场无返佣
				if(null!=ftrademapping.getFprojectId() && !ftrademapping.getFprojectId().equals(fsourceId)) {
					commissionRate = pdomain.getProportions();
					if(commissionRate > 0.3) commissionRate = 0.2;
					if(commissionRate < 0) commissionRate = 0;
				}
			}
			
			//从缓存中读取项目方交易市场的手续费比例分成
			List<Ptrademapping> ptrademappingList = (List<Ptrademapping>) frontConstantMapService.get("ptrademappingList");
			if(null != ptrademappingList && ptrademappingList.size() > 0) {
				//List转Map
				Map<Integer, Ptrademapping> maps = Maps.uniqueIndex(ptrademappingList, new Function<Ptrademapping, Integer>() {
					@Override
					public Integer apply(Ptrademapping ptrademapping) {
						return ptrademapping.getTradeMappingId();
					}
				});
				
				Ptrademapping ptrademapping = maps.get(ftrademapping.getFid());
				if(null != ptrademapping) feesRate = ptrademapping.getBuyFeesRate();
			}
			
			boolean flag = false ;
			Fentrust fentrust = null ;
			try {
				fentrust = this.frontTradeService.updateEntrustBuy(symbol, tradeAmount,
						tradeCnyPrice, fuser, limited==1, fsourceId, commissionRate, feesRate) ;
				//加入MQ买单队列
				if (limited == 1) {
					frontEntrustChangeService.addEntrustLimitBuyMap(symbol, fentrust);
//					rabbitTemplate.convertAndSend("entrust.limit.buy.add", symbolAndIdStr);
				} else {
					frontEntrustChangeService.addEntrustBuyMap(symbol, fentrust);
//					rabbitTemplate.convertAndSend("entrust.buy.add", symbolAndIdStr);
				}
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag && fentrust != null){
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "下单成功");
			}else{
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试");
			}
			
			return jsonObject.toString() ;
		
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//卖币
	public String sellBtcSubmit(HttpServletRequest request) throws Exception{
		try{

			int limited=0;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			double tradeAmount = Double.parseDouble(request.getParameter("tradeAmount")) ;
			double tradeCnyPrice = Double.parseDouble(request.getParameter("tradeCnyPrice")) ;
			String tradePwd = request.getParameter("tradePwd") ;
			JSONObject jsonObject = new JSONObject() ;
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(isNeedTradePassword(request)){
				if(tradePwd == null || tradePwd.trim().length() == 0){
					jsonObject.accumulate(ErrorCode, -50) ;
					jsonObject.accumulate(Value, "交易密码不正确");
					return jsonObject.toString() ;
				}
				
				if(fuser.getFtradePassword() == null){
					jsonObject.accumulate(ErrorCode, -5) ;
					jsonObject.accumulate(Value, "您还没有设置交易密码，请到安全中心设置");
					return jsonObject.toString() ;
				}
				if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
					jsonObject.accumulate(ErrorCode, -2) ;
					jsonObject.accumulate(Value, "交易密码不正确");
					return jsonObject.toString() ;
				}
			}
			
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
			if(ftrademapping==null  || ftrademapping.getFstatus()!=TrademappingStatusEnum.ACTIVE){
				jsonObject.accumulate(ErrorCode, -100) ;
				jsonObject.accumulate(Value, "该币暂时不能交易");
				return jsonObject.toString() ;
			}
			
			//限制法币为人民币和比特币
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
					&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_USDT_VALUE
					&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误");
				return jsonObject.toString() ;
			
			}
			
			tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
			tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
			Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
			Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
			double minBuyCount = ftrademapping.getFminBuyCount() ;
			double minBuyPrice = ftrademapping.getFminBuyPrice() ;
			double minBuyAmount = ftrademapping.getFminBuyAmount() ;
			double maxBuyCount = ftrademapping.getFmaxBuyCount() ;
			double maxBuyAmount = ftrademapping.getFmaxBuyAmount();
			double maxBuyPrice = ftrademapping.getFmaxBuyPrice();
			
			
			//是否开放交易
			if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
				jsonObject.accumulate(ErrorCode, -400) ;
				jsonObject.accumulate(Value, "现在不是交易时间");
				return jsonObject.toString() ;
			}
			
			if(limited == 0 ){
				
				if(tradeAmount<minBuyCount){

					DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "最小交易数量："+coin2.getfSymbol()+decimalFormat.format(minBuyCount));
					return jsonObject.toString() ;
				}
				
				if(tradeAmount>maxBuyCount && !fuser.isFistiger()){
					DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "最大交易数量："+coin2.getfSymbol()+decimalFormat.format(maxBuyCount));
					return jsonObject.toString() ;
				}
				
				if(tradeCnyPrice < minBuyPrice){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
					return jsonObject.toString() ;
				}
				double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
				if(total < minBuyAmount){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
					return jsonObject.toString() ;
				}
				
				if(total > maxBuyAmount && !fuser.isFistiger()){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最大挂单金额："+coin1.getfSymbol()+maxBuyAmount);
					return jsonObject.toString() ;
				}
				
				Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
				double upPrice = 0d;
				double downPrice = 0d;
				if(limittrade != null){
					upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
					downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
					if(downPrice <0) downPrice=0;
					if(tradeCnyPrice > upPrice){
						jsonObject.accumulate(ErrorCode, -3) ;
						jsonObject.accumulate(Value, "挂单价格不能高于涨停价:"+upPrice+coin1.getFname()) ;
						return jsonObject.toString() ; 
					}
					if(tradeCnyPrice < downPrice){
						jsonObject.accumulate(ErrorCode, -3) ;
						jsonObject.accumulate(Value, "挂单价格不能低于跌停价:"+downPrice+coin1.getFname()) ;
						return jsonObject.toString() ; 
					}
				}else{
					if(tradeCnyPrice > maxBuyPrice && !fuser.isFistiger()){
						jsonObject.accumulate(ErrorCode, -3) ;
						jsonObject.accumulate(Value, "最大挂单价格："+coin1.getfSymbol()+maxBuyPrice);
						return jsonObject.toString() ;
					}
				}
				
			}else{
				if(tradeAmount <minBuyCount){
					jsonObject.accumulate(ErrorCode, -33) ;
					jsonObject.accumulate(Value, "最小交易数量："+minBuyCount+coin2.getFname());
					return jsonObject.toString() ;
				}
			}
		
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coin2.getFid()) ;
			if(fvirtualwallet==null){
				jsonObject.accumulate(ErrorCode, -200) ;
				jsonObject.accumulate(Value, "系统错误，请联系管理员");
				return jsonObject.toString() ;
			}
			if(fvirtualwallet.getFtotal()<tradeAmount){
				jsonObject.accumulate(ErrorCode, -4) ;
				jsonObject.accumulate(Value, coin2.getFname()+"余额不足");
				return jsonObject.toString() ;
			}
			
			//从请求匹配缓存中的项目方域名列表
			Pdomain pdomain = getReqDomain(request);
			Integer fsourceId = 0;
			double commissionRate = 0;
			double feesRate = 0;
			if(pdomain != null) {
				fsourceId = pdomain.getProjectId().getFid();
				//主站访问和项目方网站访问自己的市场无返佣
				if(null==ftrademapping.getFprojectId() || !ftrademapping.getFprojectId().equals(fsourceId)) {
					commissionRate = pdomain.getProportions();
					if(commissionRate > 0.3) commissionRate = 0.2;
					if(commissionRate < 0) commissionRate = 0;
				}
			}
			
			//从缓存中读取项目方交易市场的手续费比例分成
			List<Ptrademapping> ptrademappingList = (List<Ptrademapping>) frontConstantMapService.get("ptrademappingList");
			if(null != ptrademappingList && ptrademappingList.size() > 0) {
				//List转Map
				Map<Integer, Ptrademapping> maps = Maps.uniqueIndex(ptrademappingList, new Function<Ptrademapping, Integer>() {
					@Override
					public Integer apply(Ptrademapping ptrademapping) {
						return ptrademapping.getTradeMappingId();
					}
				});
				
				Ptrademapping ptrademapping = maps.get(ftrademapping.getFid());
				if(null != ptrademapping) feesRate = ptrademapping.getSellFeesRate();
			}
			
			boolean flag = false ;
			Fentrust fentrust = null ;
			try {
				fentrust = this.frontTradeService.updateEntrustSell(symbol, tradeAmount, 
						tradeCnyPrice, fuser, limited==1, fsourceId, commissionRate, feesRate) ;
				
				if (limited == 1) {
					frontEntrustChangeService.addEntrustLimitSellMap(symbol, fentrust);
//					rabbitTemplate.convertAndSend("entrust.limit.sell.add", symbolAndIdStr);
				} else {
					frontEntrustChangeService.addEntrustSellMap(symbol, fentrust);
//					rabbitTemplate.convertAndSend("entrust.sell.add", symbolAndIdStr);
				}
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag && fentrust != null){							
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "下单成功");
			}else{
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试");
			}
			
			return jsonObject.toString() ;
		
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	

	/**
	 * 推广收益明细
	 * @param request
	 * @return
	 * @throws Exception
	 */

	public String GetIntrolinfo(HttpServletRequest request) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int currentPage = 1 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}

			Fuser fuser = this.getAppFuser(this.curLoginToken) ;

			String filter = "where fuser.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fintrolinfo", filter);
			int totalPage = total/maxResult + ((total%maxResult)  ==0?0:1) ;
			List<Fintrolinfo> fintrolinfos = this.introlinfoService.list((currentPage-1)*maxResult, maxResult,filter,true) ;

			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			JSONArray jsonArray = new JSONArray() ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			for (int i=0;i<fintrolinfos.size();i++) {
				Fintrolinfo fintrolinfo = fintrolinfos.get(i);
				JSONObject item = new JSONObject();
				item.accumulate("createTime", sdf.format(fintrolinfo.getFcreatetime()));
				item.accumulate("title", fintrolinfo.getFtitle());
				jsonArray.add(item);
			}
			jsonObject.accumulate("list", jsonArray);
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//取得线下明细
	public String GetIntrolDetail(HttpServletRequest request) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			int currentPage = 1 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			String filter = "where fIntroUser_id.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fuser", filter);
			int totalPage = total/maxResult + ((total%maxResult) ==0?0:1) ;
			List<Fuser> fusers = this.userService.list((currentPage-1)*maxResult, maxResult,filter,true) ;
			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			JSONArray jsonArray = new JSONArray() ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i=0;i<fusers.size();i++) {
				Fuser user = fusers.get(i);
				JSONObject item = new JSONObject();
				item.accumulate("id",user.getFid());
				item.accumulate("loginName",user.getFloginName());
				item.accumulate("time", sdf.format(user.getFregisterTime()));
				jsonArray.add(item);
			}
			jsonObject.accumulate("list", jsonArray);
			
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 实名认证
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String ValidateIdentity(
			HttpServletRequest request
			) throws Exception {
		try {
			String identityNo = request.getParameter("identityNo");
			String realName = request.getParameter("realName");
			LOGGER.info(CLASS_NAME + " validateIdentity,identityNo:{},realName:{}", identityNo, realName);
			JSONObject js = new JSONObject();
			js.accumulate(Result, true) ;
			
			realName = HtmlUtils.htmlEscape(realName);
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			if(fuser.getFregType()==0){
				//注册类型为手机的账户，必须要绑定手机号码
				if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
					js.accumulate(ErrorCode, 1);
					js.accumulate(Value, "请先绑定手机");
					return js.toString() ;
				}
			}else{
				//对注册类型为邮箱的账户，必须要绑定手机号码
				if(null==fuser.getFemail() || fuser.getFemail().equals("")){
					js.accumulate(ErrorCode, 1);
					js.accumulate(Value, "请先绑定手机号码或者GOOGLE验证码");
					return js.toString() ;
				}
			}
			
			if(fuser.getFpostRealValidate()){
				js.accumulate(ErrorCode, 1);
				js.accumulate(Value, "请耐心等待审核!");
				return js.toString() ;
			}
			
			identityNo = identityNo.toLowerCase();
			String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
		                "3", "2" };  
	        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
	                "9", "10", "5", "8", "4", "2" };  
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate(Result, true) ;
			
			if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
				jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码长度应该为15位或18位!");
				LOGGER.info(CLASS_NAME + " validateIdentity,身份证号码长度应该为15位或18位");
				return jsonObject.toString();
			}
			
			String Ai = "";
	        if (identityNo.length() == 18) {
	            Ai = identityNo.substring(0, 17);  
	        } else if (identityNo.length() == 15) {  
	            Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);  
	        }
			LOGGER.info(CLASS_NAME + " validateIdentity,Ai:{}", Ai);
	        if (Utils.isNumeric(Ai) == false) {  
	            jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码有误!");
				return jsonObject.toString();
	        }
	        // ================ 出生年月是否有效 ================  
	        String strYear = Ai.substring(6, 10);// 年份  
	        String strMonth = Ai.substring(10, 12);// 月份  
	        String strDay = Ai.substring(12, 14);// 月份  
	        if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {  
	        	jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码有误!");
				return jsonObject.toString();
	        }  
	        GregorianCalendar gc = new GregorianCalendar();  
	        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
	        try {
	            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
	                    || (gc.getTime().getTime() - s.parse(  
	                            strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
	            	jsonObject.accumulate(ErrorCode, 1);
					jsonObject.accumulate(Value, "身份证号码有误!");
					return jsonObject.toString();
	            }
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        } catch (java.text.ParseException e) {
	            e.printStackTrace();
	        }
	        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
	        	jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码有误!");
				return jsonObject.toString();
	        }
	        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
	        	jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码有误!");
				return jsonObject.toString();
	        }  
	        // =====================(end)=====================  
	  
	        // ================ 地区码时候有效 ================  
	        Hashtable h = Utils.getAreaCode();
	        if (h.get(Ai.substring(0, 2)) == null) {
	        	jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码有误!");
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
	            	jsonObject.accumulate(ErrorCode, 1);
					jsonObject.accumulate(Value, "身份证号码有误!");
					return jsonObject.toString();
	            }
	        } else {
	            return "";  
	        }
	        
			if (realName.trim().length() > 50) {
				jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "真实姓名不合法!");
				return jsonObject.toString();
			}
			
			String sql = "where fidentityNo='"+identityNo+"'";
			int count = this.adminService.getAllCount("Fuser", sql);
			LOGGER.info(CLASS_NAME + " validateIdentity,从数据库中查询身份证号，返回结果集count:{}", count);
			if(count >0){
				jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码已存在!");
				return jsonObject.toString();
			}
			
			//是否通过KYC认证，避免国外用户提交KYC1，未经验证的情况
			boolean isTrue =false;
			
			if(Constant.IS_APPKEY.equals("true")){
				IDCardVerifyUtil verifyUtil = new IDCardVerifyUtil();
				isTrue = verifyUtil.isRealPerson(realName, identityNo);
				LOGGER.info(CLASS_NAME + " validateIdentity,调verifyUtil.isRealPerson进行实名认证，返回结果isTrue:{}", isTrue);
				if(!isTrue){
					jsonObject.accumulate(ErrorCode, 1);
					jsonObject.accumulate(Value, "您的姓名与身份证号有误，请核对!");
					return jsonObject.toString();
				}
			}
			
			fuser.setFidentityType(0) ;
			fuser.setFidentityNo(identityNo) ;
			fuser.setFrealName(realName) ;
			fuser.setFpostRealValidate(true) ;
			fuser.setFareaCode("86");
			fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
			if(isTrue){
				//通过国内实名认证则进行标记
				fuser.setFhasRealValidate(true);
				fuser.setFhasRealValidateTime(Utils.getTimestamp());
				fuser.setFisValid(true);
			}
			String ip = getIpAddr(request) ;
			try {
				LOGGER.info(CLASS_NAME + " validateIdentity,修改用户信息,实名认证成功");
				this.frontUserService.updateFUser(fuser, LogTypeEnum.User_CERT,ip) ;
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
				
				if(isTrue){
					//KYC1认证奖励，只奖励中国大陆用户
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
								
					LOGGER.info(CLASS_NAME + " validateIdentity,修改用户钱包信息成功，增加了奖励金额");
				}
				this.SetSession(fuser,request) ;
				
			} catch (Exception e) {
				e.printStackTrace();
				js.accumulate(ErrorCode, 1);
				js.accumulate(Value, "证件号码已存在");
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_CERT, "实名认证失败：身份证号码重复"+identityNo);
				return js.toString();
			}
			if(isTrue){
				js.accumulate(Value, "证件验证成功");
				js.accumulate(ErrorCode, 0);
				return js.toString();
			}else{
				js.accumulate(Value, "证件号码提交成功，请提交照片以完成身份认证");
				js.accumulate(ErrorCode, 0);
				return js.toString();
			}
			//实名认证奖励
		
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
    /**
     * CYK2认证(弃用，现在使用FaceID进行验证)
     * @param request
     * @return
     * @throws Exception
     */
	public String ValidateKyc(HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject();
        try {
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(!fuser.getFhasRealValidate()){
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "请先通过实名认证");
				return jsonObject.toString();
			}
			if(fuser.isFpostImgValidate()){
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "您已上传，请耐心等待审核");
				return jsonObject.toString();
			}
			if(fuser.isFhasImgValidate()){
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "已审核，无须重复上传");
				return jsonObject.toString();
			}
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request ;
			MultipartFile filedata1 = mRequest.getFile("filedata1") ;
			MultipartFile filedata2 = mRequest.getFile("filedata2") ;
			MultipartFile filedata3 = mRequest.getFile("filedata3") ;
			{
				String file1 = null;
		        boolean isFlag = false;
		        if(filedata1 != null && !filedata1.isEmpty()){
					InputStream inputStream = filedata1.getInputStream() ;
					String fileRealName = filedata1.getOriginalFilename() ;
					double size = filedata1.getSize()/1000d;
					if(size > 1024d){
						jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证正面图片1大小不能超过1M");
						return jsonObject.toString();
					}
					if(fileRealName != null && fileRealName.trim().length() >0){
						String[] nameSplit = fileRealName.split("\\.") ;
						String ext = nameSplit[nameSplit.length-1] ;
						if(ext!=null && !ext.trim().toLowerCase().endsWith("jpg")
								 && !ext.trim().toLowerCase().endsWith("png")){
							jsonObject.accumulate(ErrorCode, 1);
							jsonObject.accumulate(Value, "身份证正面非jpg、PNG文件格式");
							return jsonObject.toString();
						}
						String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
						String fileName = Utils.getRandomImageName()+"."+ext;
						boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
						if(flag){
							if(Constant.IS_OPEN_OSS.equals("false")){
								file1 = "/"+Constant.uploadPicDirectory+"/"+fileName ;
							}else{
								file1 = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
							}
						}
					}
				}
		        if(!isFlag){
		        	fuser.setfIdentityPath(file1);
		        }else{
		        	jsonObject.accumulate(ErrorCode, 1);
					jsonObject.accumulate(Value, "请上传身份证正面复印件");
					return jsonObject.toString();
	            }
			}
			{
			    String file1 = null;
		        boolean isFlag = false;
		        if(filedata2 != null && !filedata2.isEmpty()){
					InputStream inputStream = filedata2.getInputStream() ;
					String fileRealName = filedata2.getOriginalFilename() ;
					double size = filedata2.getSize()/1000d;
					if(size > 1024d){
						jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证反面大小不能超过1M");
						return jsonObject.toString();
					}
					if(fileRealName != null && fileRealName.trim().length() >0){
						String[] nameSplit = fileRealName.split("\\.") ;
						String ext = nameSplit[nameSplit.length-1] ;
						if(ext!=null && !ext.trim().toLowerCase().endsWith("jpg")
								 && !ext.trim().toLowerCase().endsWith("png")){
							jsonObject.accumulate(ErrorCode, 1);
							jsonObject.accumulate(Value, "身份证反面非jpg、PNG文件格式");
							return jsonObject.toString();
						}
						String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
						String fileName = Utils.getRandomImageName()+"."+ext;
						boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
						if(flag){
							if(Constant.IS_OPEN_OSS.equals("false")){
								file1 = "/"+Constant.uploadPicDirectory+"/"+fileName ;
							}else{
								file1 = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
							}
						}
					}
				}
		        if(!isFlag){
		        	fuser.setfIdentityPath2(file1);
		        }else{
		        	jsonObject.accumulate(ErrorCode, 1);
					jsonObject.accumulate(Value, "请上传身份证反面复印件");
					return jsonObject.toString();
		        }
			}		
			{
			    String file1 = null;
		        boolean isFlag = false;
		        if(filedata3 != null && !filedata3.isEmpty()){
					InputStream inputStream = filedata3.getInputStream() ;
					String fileRealName = filedata3.getOriginalFilename() ;
					double size = filedata3.getSize()/1000d;
					if(size > 1024d){
						jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "手持身份证大小不能超过1M");
						return jsonObject.toString();
					}
					if(fileRealName != null && fileRealName.trim().length() >0){
						String[] nameSplit = fileRealName.split("\\.") ;
						String ext = nameSplit[nameSplit.length-1] ;
						if(ext!=null && !ext.trim().toLowerCase().endsWith("jpg")
								 && !ext.trim().toLowerCase().endsWith("png")){
							jsonObject.accumulate(ErrorCode, 1);
							jsonObject.accumulate(Value, "手持身份证非jpg、PNG文件格式");
							return jsonObject.toString();
						}
						String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
						String fileName = Utils.getRandomImageName()+"."+ext;
						boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
						if(flag){
							if(Constant.IS_OPEN_OSS.equals("false")){
								file1 = "/"+Constant.uploadPicDirectory+"/"+fileName ;
							}else{
								file1 = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
							}
						}
					}
				}
		        if(!isFlag){
		        	fuser.setfIdentityPath3(file1);
		        }else{
		        	jsonObject.accumulate("code", 1);
					jsonObject.accumulate(Value, "请上传手持身份证复印件");
					return jsonObject.toString();
		        }
			}
			fuser.setFpostImgValidate(true);
			fuser.setFpostImgValidateTime(Utils.getTimestamp());
			
			try {
				this.frontUserService.updateFuser(fuser);
			} catch (Exception e) {
				e.printStackTrace();
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "网络异常，请重新上传");
				return jsonObject.toString();
			}
			jsonObject.accumulate(Value, "上传成功，请耐心等待审核");
			jsonObject.accumulate(ErrorCode, 0);
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 查看实名认证信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String ViewValidateIdentity(HttpServletRequest request) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate(ErrorCode , 0) ;
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(fuser.getFpostRealValidate()){
				jsonObject.accumulate("realName", fuser.getFrealName()) ;
				jsonObject.accumulate("identityNo", fuser.getFidentityNo()) ;
				jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate()) ;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.accumulate("postRealValidateTime", sdf.format(fuser.getFpostRealValidateTime())) ;
			}else{
				jsonObject.accumulate("realName", null) ;
				jsonObject.accumulate("identityNo",null) ;
				jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate()) ;
				jsonObject.accumulate("postRealValidateTime",null) ;
			}
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//支付宝充值
	public String CnyRecharge(HttpServletRequest request) throws Exception {
		try {
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;

			String ZFBImages = this.frontConstantMapService.getString("ZFBImages") ;
			String rechargeZFB = this.frontConstantMapService.getString("rechargeZFB") ;
			String rechargeZFN = this.frontConstantMapService.getString("rechargeZFN") ;
			String ZFBRemark = this.frontConstantMapService.getString("ZFBRemark") ;
			
			jsonObject.accumulate("rechargeZFB" ,rechargeZFB) ;
			jsonObject.accumulate("rechargeZFN" , rechargeZFN) ;
			jsonObject.accumulate("ZFBRemark" , ZFBRemark) ;
			jsonObject.accumulate("userid" , fuser.getFid()) ;
			jsonObject.accumulate("ZFBImages" ,ZFBImages) ;

			jsonObject.accumulate(ErrorCode , 0) ;
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}

	
	//发送手机验证码
	public String SendValidateCode(
			HttpServletRequest request
			) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			int type = Integer.parseInt(request.getParameter("type")) ;
			/*if(type!=MessageTypeEnum.BEGIN && type!=MessageTypeEnum.END){
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "类型错误") ;
				return jsonObject.toString() ;
			}*/
			
			String phone = request.getParameter("phone") ;
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(fuser!=null && fuser.isFisTelephoneBind()){
				phone = fuser.getFtelephone() ;
			}
			
			if(phone.matches("^\\d{10,14}$")){
				if(type == MessageTypeEnum.BANGDING_MOBILE){
					boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone) ;
					if(isPhoneExists){
						jsonObject.accumulate(ErrorCode , 2) ;
						jsonObject.accumulate(Value, "手机号码已经存在") ;
						return jsonObject.toString() ;
					}
				}else{
					if(fuser.isFisTelephoneBind() == false ){
						jsonObject.accumulate(ErrorCode , 3) ;
						jsonObject.accumulate(Value, "该账号没有绑定手机号码") ;
						return jsonObject.toString() ;
					}
				}
				
				MessageValidate messageValidate2=getMessageValidate("86", phone);
				Object[] params = new Object[]{messageValidate2.getCode()};
				String mas=getLocaleMessage(request,params,"smstpl.enum."+type);
				frontValidateService.saveSendMessageHaslogin(messageValidate2,fuser,fuser.getFid(),mas, type,0) ;
				//super.SendMessage(request,fuser,fuser.getFid(), "86" ,phone, type,0) ;
				
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "验证码发送成功！") ;
				return jsonObject.toString() ;
				
			}else{
				jsonObject.accumulate(ErrorCode , 4) ;
				jsonObject.accumulate(Value, "手机号码格式错误") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 解绑手机号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String UnbindPhone(
			HttpServletRequest request
			) throws Exception{
		try {
			String phoneCode = request.getParameter("phoneCode") ;
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			if(fuser.getFregType()==1){
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "您通过手机注册，不可解绑") ;
				return jsonObject.toString() ;
			}
			
			if(fuser.isFisTelephoneBind()){
				String ip = getIpAddr(request) ;
				int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				if(mobil_limit<=0){
					jsonObject.accumulate(ErrorCode , 1) ;
					jsonObject.accumulate(Value, "验证码错误次数超限，请稍后再试！") ;
					return jsonObject.toString() ;
				}else{
					if(super.validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.JIEBANG_MOBILE, phoneCode)){
						boolean flag = false ;
						try {
							fuser.setFisTelephoneBind(false) ;
							fuser.setFtelephone(null) ;
							fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
							this.frontUserService.updateFuser(fuser) ;
							
							//推广数量-1
							Fuser introFuser = fuser.getfIntroUser_id() ;
							if(introFuser!=null){
								introFuser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid()) ;
								introFuser.setfInvalidateIntroCount(introFuser.getfInvalidateIntroCount()-1) ;
								this.frontUserService.updateFuser(introFuser) ;
							}
							
							flag = true ;
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if(flag){
							jsonObject.accumulate(ErrorCode , 0) ;
							jsonObject.accumulate(Value, "解除绑定成功！") ;
							return jsonObject.toString() ;
						}else{
							jsonObject.accumulate(ErrorCode , -10000) ;
							jsonObject.accumulate(Value, "网络错误，请稍后再试！") ;
							return jsonObject.toString() ;
						}
					}else{
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
						jsonObject.accumulate(ErrorCode , 1) ;
						jsonObject.accumulate(Value, "验证码错误") ;
						return jsonObject.toString() ;
					}
				}
			}else{
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "您的账号没有绑定手机号码") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	
	/**
	 * 提现银行列表
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	public String GetWithdrawBankList(
			HttpServletRequest request
			) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;

			JSONArray jsonArray = new JSONArray() ;
			for(int i=BankTypeEnum.GH;i<=BankTypeEnum.QT;i++){
				JSONObject item = new JSONObject() ;
				item.accumulate("id", i) ;
				item.accumulate("name", BankTypeEnum.getEnumString(i)) ;
				jsonArray.add(item) ;
				
			}
			jsonObject.accumulate("list", jsonArray) ;
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
//	http://test.ruizton.com:8080/user/updateOutAddress.html?random=4&account=3333&address=55555&cnyOutType=1&openBankType=1&phoneCode=333333&totpCode=0&type=1

	/**
	 * 设置提现银行卡信息
	 * param account 银行卡号
     * param phoneCode 手机验证码
     * param totpCode 谷歌验证码
     * param openBankType 银行类型
     * param address 分行名称
     * param prov 银行所属省份
     * param city 银行所属城市
     * param dist 银行所属区局
     * param payeeAddr 开户姓名
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	public String SetWithdrawCnyBankInfo(HttpServletRequest request) throws Exception {
		
		try {
			String account = request.getParameter("account");
			String phoneCode = request.getParameter("phoneCode") ;
			String totpCode = request.getParameter("totpCode");
			int openBankType = Integer.parseInt(request.getParameter("openBankType"));
			String address = request.getParameter("address") ;
			String prov= request.getParameter("prov") ;
			String city= request.getParameter("city") ;
			String dist= request.getParameter("dist") ;
		    String payeeAddr= request.getParameter("payeeAddr") ;
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if( !fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
				//没有绑定谷歌或者手机
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "请先绑定谷歌认证或手机号码") ;
				return jsonObject.toString() ;
			}
			address = HtmlUtils.htmlEscape(address);
			prov = HtmlUtils.htmlEscape(prov);
			city = HtmlUtils.htmlEscape(city);
			dist = HtmlUtils.htmlEscape(dist);
			payeeAddr = HtmlUtils.htmlEscape(payeeAddr);
			
			String last = prov+city;
			if(!dist.equals("0")){
				last = last+dist;
			}
			if(account.length() < 10){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.bankaccill")) ;
				return jsonObject.toString();
			}
			
			if(address.length() > 300){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.addrtolong")) ;
				return jsonObject.toString();
			}
			
			if(last.length() > 50){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.illoperation")) ;
				return jsonObject.toString();
			}
			
			if(!fuser.getFrealName().equals(payeeAddr)){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "银行卡账号名必须与您的实名认证姓名一致") ;
				return jsonObject.toString();
			}
			
			String bankName = BankTypeEnum.getEnumString(openBankType);
			if(bankName == null || bankName.trim().length() ==0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.bankmistake")) ;
				return jsonObject.toString();
			}
			
			int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fbankType="+openBankType+" and fbankNumber='"+account+"' and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
			if(count>0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.bankcardexist")) ;
				return jsonObject.toString();
			}
			
			String ip = getIpAddr(request) ;
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			
			if(fuser.isFisTelephoneBind()){
				if(tel_limit<=0){
					jsonObject.accumulate(ErrorCode , 1) ;
					jsonObject.accumulate(Value, "短信验证码错误次数超限，请稍后再试！") ;
					return jsonObject.toString() ;
				}else{
					if(!validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)){
						//手機驗證錯誤
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
						jsonObject.accumulate(ErrorCode , 1) ;
						jsonObject.accumulate(Value, "手机验证码错误") ;
						return jsonObject.toString() ;
					
					}else{
						this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
					}
				}
			}
			
			if(fuser.getFgoogleBind()){
				if(google_limit<=0){
					jsonObject.accumulate(ErrorCode, 1) ;
					jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!") ;
					return jsonObject.toString() ;
				}
				
				boolean googleValidate = GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator()) ;
				if(!googleValidate){
					jsonObject.accumulate(ErrorCode, 1) ;
					jsonObject.accumulate(Value, "谷歌验证码有误，您还有"+google_limit+"次机会") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else if(google_limit<Constant.ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
			
			//成功
			try {
				FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw() ;
				fbankinfoWithdraw.setFbankNumber(account) ;
				fbankinfoWithdraw.setFbankType(openBankType) ;
				fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
				fbankinfoWithdraw.setFname(bankName) ;		
				fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
				fbankinfoWithdraw.setFaddress(last);
				fbankinfoWithdraw.setFothers(address);
				fbankinfoWithdraw.setFuser(fuser) ;
				this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
				

				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "提现银行卡信息设置成功") ;
				return jsonObject.toString() ;
			
			} catch (Exception e) {
				jsonObject.accumulate(ErrorCode , -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
			
	}
	
	
	
	/**
	 * USDT提现
	 * @param request
	 * @param tradePwd 交易密码
	 * @param withdrawBalance 提现数量
	 * @param phoneCode  手机验证码
	 * @param totpCode 谷歌验证码
	 * @param withdrawBlank 提现银行
	 * @return
	 * @throws Exception
	 */
	public String WithDrawCny(HttpServletRequest request) throws Exception {
		
		try {
			int withdrawBank = Integer.parseInt(request.getParameter("withdrawBank")) ;
			String tradePwd = request.getParameter("tradePwd") ;
			double withdrawBalance = Utils.getDouble(Double.parseDouble(request.getParameter("withdrawBalance")), 2) ;
			String phoneCode = request.getParameter("phoneCode") ;
			String totpCode = request.getParameter("totpCode");

			LOGGER.info(CLASS_NAME + " WithDrawCny 会员用户申请提现人民币，提现金额：{}", withdrawBalance);
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			//最大提现人民币
			double max_double = Double.parseDouble(this.frontConstantMapService.getString("maxwithdrawcny")) ;
			double min_double = Double.parseDouble(this.frontConstantMapService.getString("minwithdrawcny")) ;
			
			if(withdrawBalance<min_double){
				//提现金额不能小于10
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "提现金额不能小于：￥"+min_double) ;
				return jsonObject.toString() ;
			}
			
			if(withdrawBalance>max_double){
				//提现金额不能大于指定值
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "提现金额不能大于：￥"+max_double) ;
				return jsonObject.toString() ;
			}
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			String code=actionSecurityCheck(request, fuser, false, true, true, tradePwd, true, phoneCode, totpCode, MessageTypeEnum.USDT_TIXIAN, LogTypeEnum.User_USDT, "USDT提现", "value");
			if(!code.equals("ok")){
				return code;
			}
			
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid()) ;
			FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findFbankinfoWithdraw(withdrawBank) ;
			
			if(fbankinfoWithdraw == null || fbankinfoWithdraw.getFuser().getFid() != fuser.getFid()){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value,"提现账号不合法") ;
				return jsonObject.toString() ;
			}
			
			if(fwallet.getFtotal()<withdrawBalance){
				//资金不足
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value,"你的余额不足") ;
				return jsonObject.toString() ;
			}
			
			boolean withdraw = false ;
			try {
				withdraw = this.frontAccountService.updateWithdrawCNY(withdrawBalance, fuser,fbankinfoWithdraw) ;
			} catch (Exception e) {
				e.printStackTrace() ;
				this.frontValidateMapService.removeMessageMap(MessageTypeEnum.getEnumString(MessageTypeEnum.USDT_TIXIAN)) ;
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.USDT_TIXIAN);
			}
			
			if(withdraw){
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "提现请求成功，请耐心等待管理员处理") ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate(ErrorCode , -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
			
	}
	
	
	/**
	 * 获取虚拟币提现地址
	 * @param coinId 币种ID
	 * @return
	 * @throws Exception
	 */
	public String GetWithdrawBtcAddress(
			HttpServletRequest request
			) throws Exception {
		try {
			Integer coinId = Integer.parseInt(request.getParameter("coinId")) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(coinId) ;
			List<FvirtualaddressWithdraw> fvirtualaddressWithdraws = this.utilsService.list(0, 0, " where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and fuser.fid="+fuser.getFid()+" ", false, FvirtualaddressWithdraw.class) ;
			JSONArray jsonArray = new JSONArray() ;
			for (FvirtualaddressWithdraw fvirtualaddressWithdraw : fvirtualaddressWithdraws) {
				JSONObject item = new JSONObject() ;
				item.accumulate("id", fvirtualaddressWithdraw.getFid()) ;
				item.accumulate("remark", fvirtualaddressWithdraw.getFremark()) ;
				item.accumulate("address",  fvirtualaddressWithdraw.getFadderess()) ;
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("list", jsonArray) ;
			
			String xx = "where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and (ffee >0 or flevel=5)  order by flevel asc";
			List<Fwithdrawfees> withdrawFees = this.withdrawFeesService.list(0, 0, xx, false);
			
			jsonArray = new JSONArray() ;
			for (int i = 0; i < withdrawFees.size(); i++) {
				Fwithdrawfees fee = withdrawFees.get(i) ;
				JSONObject item = new JSONObject() ;
				item.accumulate("level", fee.getFlevel()) ;
				item.accumulate("fee", fee.getFfee()) ;
				
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("fee_list", jsonArray) ;
			
			return jsonObject.toString()  ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 设置虚拟币提现地址
	 * @param phoneCode 手机验证码
	 * @param symbol 币种类型
	 * @param withdrawAddr 虚拟币提币地址
	 * @param withdrawBtcPass 交易密码
	 * @param totpCode 谷歌验证码
	 * @param remark 备注
	 * @return
	 * @throws Exception
	 */
	public String SetWithdrawBtcAddr(
			HttpServletRequest request
			) throws Exception{
		
		try {
			String phoneCode = request.getParameter("phoneCode");
			int symbol  = Integer.parseInt(request.getParameter("symbol"));
			String withdrawAddr  = request.getParameter("withdrawAddr");
			String withdrawBtcPass = request.getParameter("withdrawBtcPass");
			String totpCode = request.getParameter("totpCode");
			String remark = HtmlUtils.htmlEscape(request.getParameter("remark"));
			
			withdrawAddr = HtmlUtils.htmlEscape(withdrawAddr.trim());
			remark = HtmlUtils.htmlEscape(remark.trim());
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			
			String code=actionSecurityCheck(request, fuser, false, false, true, withdrawBtcPass, true, phoneCode, totpCode, MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT,LogTypeEnum.User_BTC, "设置提币地址","value");
			if(!code.equals("ok")){
				return code;
			}
			
			if(remark.length() >100){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.user.remarklong")) ;
				return jsonObject.toString() ;
			}
			
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
			if(fvirtualcointype==null 
					|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
					|| !fvirtualcointype.isFIsWithDraw()){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.user.nocoin")) ;
				return jsonObject.toString() ;
			}
			
			//钱包核验地址，确保正确
			WalletMessage wmsg = new WalletMessage();
			wmsg.setIP(fvirtualcointype.getFip());
			wmsg.setPORT(fvirtualcointype.getFport());
			wmsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
			wmsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
			wmsg.setISERC20(false);
			wmsg.setCONTRACT("");
			wmsg.setDECIMALS(fvirtualcointype.getFdecimals());
			WalletUtil wallet =  WalletUtil.createWalletByClass(fvirtualcointype.getFclassPath(), wmsg);
			
			try{
				if(!wallet.validateAddress(withdrawAddr)){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "提现地址格式有误") ;
					return jsonObject.toString() ;
				}
			}catch(Exception e){
				e.printStackTrace();
				jsonObject.accumulate(Value, -1) ;
				jsonObject.accumulate(Value, "提现地址格式有误") ;
				return jsonObject.toString() ;
			}
			
			String ip = getIpAddr(request) ;
			
			FvirtualaddressWithdraw fvirtualaddressWithdraw = new FvirtualaddressWithdraw();
			fvirtualaddressWithdraw.setFadderess(withdrawAddr) ;
			fvirtualaddressWithdraw.setFcreateTime(Utils.getTimestamp());
			fvirtualaddressWithdraw.setFremark(remark);
			fvirtualaddressWithdraw.setFuser(fuser);
			fvirtualaddressWithdraw.setFvirtualcointype(fvirtualcointype);
			try {
				this.frontVirtualCoinService.updateFvirtualaddressWithdraw(fvirtualaddressWithdraw) ;
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "地址设置成功") ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "修改提币地址成功："+withdrawAddr);
			} catch (Exception e) {
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "修改提币地址失败");
			}finally{
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
			}
	 		
			return jsonObject.toString() ;
			
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
 		
	}
	
	
	/**虚拟币提现
	 * @author msb
	 * @param virtualaddres 提现地址
	 * @param withdrawAmount 提现数量
	 * @param tradePwd 交易密码
	 * @param phoneCode 手机验证码
	 * @param totpCode 谷歌验证码
	 * @param symbol 币种类型
	 * @param level 用户等级
	 * @return json
	 * @throws Exception
	 */
	public String WithdrawBtcSubmit(HttpServletRequest request) throws Exception{
		
		try {
			int virtualaddres = Integer.parseInt(request.getParameter("virtualaddres")) ;
			double withdrawAmount = Utils.getDouble(Double.parseDouble(request.getParameter("withdrawAmount")), 2) ;
			String tradePwd = request.getParameter("tradePwd").trim();
			String phoneCode= request.getParameter("phoneCode") ;
			String totpCode = request.getParameter("totpCode");
			int symbol = Integer.parseInt(request.getParameter("symbol"));
			int level = Integer.parseInt(request.getParameter("level"));
			
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币提现开始");
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 入参virtualaddres:{},withdrawAmount:{},symbol:{}", virtualaddres, withdrawAmount, symbol);
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			String scode=actionSecurityCheck(request,fuser,false,true,true,tradePwd,true,phoneCode,totpCode,MessageTypeEnum.VIRTUAL_TIXIAN,LogTypeEnum.User_BTC,"提币申请异常","msg");
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
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.illoperation")) ;
				return jsonObject.toString() ;
			}
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询用户虚拟币的钱包信息结束");
			FvirtualaddressWithdraw fvirtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(virtualaddres);
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询用户虚拟币地址结束");
			if(fvirtualaddressWithdraw == null
					|| fvirtualaddressWithdraw.getFuser().getFid() != fuser.getFid()
					|| fvirtualaddressWithdraw.getFvirtualcointype().getFid() != symbol){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.illoperation")) ;
				return jsonObject.toString() ;
			}
			
			
			//最大提现人民币
			double max_double = fvirtualcointype.getFmaxqty();
			double min_double = fvirtualcointype.getFminqty();
			
			if(withdrawAmount<min_double){
				//提现金额不能小于10
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "提现金额不能小于："+min_double) ;
				return jsonObject.toString() ;
			}
			
			if(withdrawAmount>max_double){
				//提现金额不能大于指定值
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "提现金额不能大于："+max_double) ;
				return jsonObject.toString() ;
			}
			
			//余额不足
			if(fvirtualwallet.getFtotal()<withdrawAmount){
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "余额不足") ;
				return jsonObject.toString() ;
			}
			
			int time = this.frontAccountService.getTodayVirtualCoinWithdrawTimes(fuser) ;
			if(time>=Constant.VirtualCoinWithdrawTimes){
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "每天最多提现"+Constant.VirtualCoinWithdrawTimes+"次，您已经超限") ;
				return jsonObject.toString() ;
			}
			
			String sql = "where flevel="+level+" and fvirtualcointype.fid="+symbol;
			List<Fwithdrawfees> alls = this.withdrawFeesService.list(0, 0, sql, false);
			if(alls == null || alls.size() ==0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "手续费异常") ;
			}
			double ffees = alls.get(0).getFfee();
			if(ffees ==0 && alls.get(0).getFlevel() != 5){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "手续费有误") ;
				return jsonObject.toString();
			}
			
			
			String filter = "where fadderess='"+fvirtualaddressWithdraw.getFadderess()+"'";
			int cc = this.adminService.getAllCount("Fvirtualaddress", filter);
			if(cc >0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "站内会员不允许互转") ;
				return jsonObject.toString() ;
			}
			
			
			String ip = getIpAddr(request) ;
			try{
				
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息开始");
				this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw, fvirtualcointype, fvirtualwallet, withdrawAmount, ffees, fuser) ;
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息结束");
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "提现请求成功，请耐心等待管理员处理") ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "提币申请成功："+withdrawAmount+fvirtualcointype.getfShortName());
			}catch(Exception e){
				LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 异常exception:{}", e.getMessage());
				e.printStackTrace() ;
				jsonObject.accumulate(ErrorCode , -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "提币申请失败："+withdrawAmount+fvirtualcointype.getfShortName());
			}finally{
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_TIXIAN);
			}
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币提现结束");
			return jsonObject.toString() ;
			
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	/**
	 * 获取虚拟币充值提现记录,type:1人民币充值，2人民币提现，3虚拟币充值，4虚拟币提现
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetAllRecords(
			HttpServletRequest request
			) throws Exception {
		try {
			Integer type = Integer.parseInt(request.getParameter("type")) ;  //type:1人民币充值，2人民币提现，3虚拟币充值，4虚拟币提现
			Integer currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
			Integer symbol = 0 ;
			try {
				symbol = Integer.parseInt(request.getParameter("symbol")) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}

			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			try {
				if(type == 1){//人民币充值
					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_IN+"\n");
					filter.append(" order by fid desc \n");
					List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*maxResult, maxResult, filter.toString(), true);
					int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fcapitaloperation fcapitaloperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						item.accumulate("bank", fcapitaloperation.getFremittanceType()) ;
						item.accumulate("amount", fcapitaloperation.getFamount()) ;
						item.accumulate("date", Utils.dateFormat(fcapitaloperation.getFcreateTime())) ;
						item.accumulate("status", fcapitaloperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				}else if(type ==2){//人民币提现

					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_OUT+"\n");
					filter.append(" order by fid desc \n");
					List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*maxResult, maxResult, filter.toString(), true);
					int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fcapitaloperation fcapitaloperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						item.accumulate("bank", fcapitaloperation.getfBank()) ;
						item.accumulate("amount", fcapitaloperation.getFamount()) ;
						item.accumulate("date", Utils.dateFormat(fcapitaloperation.getFcreateTime())) ;
						item.accumulate("status", fcapitaloperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				
				}else if(type ==3){
					//虚拟币充值
					Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and fvirtualcointype.fid="+fvirtualcointype.getFid()+" \n");
					filter.append("and ftype ="+VirtualCapitalOperationTypeEnum.COIN_IN+"\n");
					filter.append(" order by fid desc \n");
					
					System.out.println(filter);
					
					List<Fvirtualcaptualoperation> list = 
							this.utilsService.list((currentPage-1)*maxResult, maxResult,filter.toString(),true, Fvirtualcaptualoperation.class) ;
					int totalCount = this.utilsService.count(filter.toString(),Fvirtualcaptualoperation.class);
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						item.accumulate("bank", fvirtualcaptualoperation.getRecharge_virtual_address()) ;
						item.accumulate("amount", new BigDecimal(fvirtualcaptualoperation.getFamount()).setScale(6, BigDecimal.ROUND_DOWN)) ;
						item.accumulate("date", Utils.dateFormat(fvirtualcaptualoperation.getFcreateTime())) ;
						item.accumulate("status", fvirtualcaptualoperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				}else if(type ==4){

					//虚拟币提现
					Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and fvirtualcointype.fid="+fvirtualcointype.getFid()+" \n");
					filter.append("and ftype ="+VirtualCapitalOperationTypeEnum.COIN_OUT+"\n");
					filter.append(" order by fid desc \n");
					
					List<Fvirtualcaptualoperation> list = 
							this.utilsService.list((currentPage-1)*maxResult, maxResult,filter.toString(),true,Fvirtualcaptualoperation.class) ;
					int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						item.accumulate("bank", fvirtualcaptualoperation.getWithdraw_virtual_address()) ;
						item.accumulate("amount", new BigDecimal(fvirtualcaptualoperation.getFamount()).setScale(6, BigDecimal.ROUND_DOWN)) ;
						item.accumulate("date", Utils.dateFormat(fvirtualcaptualoperation.getFcreateTime())) ;
						item.accumulate("status", fvirtualcaptualoperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	/**
	 * 发送手机验证码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String SendMessageCode(HttpServletRequest request) throws Exception {
		try {
			int type = Integer.parseInt(request.getParameter("type"));  //发送短信验证类型
			String phone = request.getParameter("phone");   //手机号码
			String ip = getIpAddr(request) ;
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate(Result, true);
			boolean isLogin = this.isAppLogin(this.curLoginToken, false);
			
			if(type<=MessageTypeEnum.BEGIN || type>=MessageTypeEnum.END){
				jsonObject.accumulate(ErrorCode , -11) ;
				jsonObject.accumulate(Value, "短信类型错误") ;
				return  jsonObject.toString() ; 
			}
			
			if( (type==MessageTypeEnum.FIND_PASSWORD || type == MessageTypeEnum.REG_CODE  )){
				if(phone.matches(Constant.PhoneReg) == false ){
					jsonObject.accumulate(ErrorCode , -4) ;
					jsonObject.accumulate(Value, "手机号码格式错误") ;
					return  jsonObject.toString() ; 
				}
				
			}else if(isLogin == false ){
				jsonObject.accumulate(ErrorCode , -12) ;
				jsonObject.accumulate(Value, "请登录后继续操作") ;
				return  jsonObject.toString() ; 
			}			
			Fuser fuser = this.getAppFuser(this.curLoginToken);
			if (fuser != null && fuser.isFisTelephoneBind()) {
				phone = fuser.getFtelephone();
			}
			
			//注册，绑定手机
			if( ( type == MessageTypeEnum.REG_CODE ||type == MessageTypeEnum.BANGDING_MOBILE )){
				boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone);
				if (isPhoneExists) {
					jsonObject.accumulate(ErrorCode, -3);
					jsonObject.accumulate(Value, "手机号码已经被注册");
					return jsonObject.toString();
				}
			}
			//找回密码
			boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone);
			if(type==MessageTypeEnum.FIND_PASSWORD ){
				if (isPhoneExists == false ) {
					jsonObject.accumulate(ErrorCode, -13);
					jsonObject.accumulate(Value, "该账户不存在");
					return jsonObject.toString();
				}
			}
			
			if (phone.matches(Constant.PhoneReg)) {
				if (type == MessageTypeEnum.BANGDING_MOBILE) {
					if (isPhoneExists) {
						jsonObject.accumulate(ErrorCode, -3);
						jsonObject.accumulate(Value, "手机号码已经被注册");
						return jsonObject.toString();
					}
				} else {
					if (isLogin&&fuser.isFisTelephoneBind() == false) {
						jsonObject.accumulate(ErrorCode, -14);
						jsonObject.accumulate(Value, "该账号没有绑定手机号码");
						return jsonObject.toString();
					}
				}
				if(isLogin){
					MessageValidate messageValidate2=getMessageValidate("86", phone);
					Object[] params = new Object[]{messageValidate2.getCode()};
					String mas=getLocaleMessage(request,params,"smstpl.enum."+type);
					 frontValidateService.saveSendMessageHaslogin(messageValidate2,fuser,fuser.getFid(),mas, type,0) ;
					//super.SendMessage(request,fuser, fuser.getFid(), "86", phone, type,0);
				}else{
					MessageValidate messageValidate2=getMessageValidate("86", phone);
					Object[] params = new Object[]{messageValidate2.getCode()};
					String mas=getLocaleMessage(request,params,"smstpl.enum."+type);
					frontValidateService.saveSendMessageNologin(messageValidate2,getIpAddr(request),mas, type,0) ;
					//super.SendMessage(request,ip, "86", phone, type,0) ;
				}
				
				jsonObject.accumulate(ErrorCode, 0);
				jsonObject.accumulate(Value, "验证码发送成功");
				return jsonObject.toString();
			} else {
				jsonObject.accumulate(ErrorCode, -4);
				jsonObject.accumulate(Value, "手机号码格式错误");
				return jsonObject.toString();
			}
		} catch (Exception e) {
			return ApiConstant.getUnknownError(e);
		}
	}
	
	/**
	 * 发送邮箱验证码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String SendMailCode(HttpServletRequest request) throws Exception {
		try {
			String email = request.getParameter("email").toLowerCase() ;  //邮箱地址
			int type = Integer.parseInt(request.getParameter("type")) ;  //发送邮箱验证类型
			
			String ip = getIpAddr(request) ;
			//注册类型免登陆可以发送
			JSONObject jsonObject = new JSONObject() ;
			
			if(type<=SendMailTypeEnum.BEGIN||type>=SendMailTypeEnum.END){
				jsonObject.accumulate(ErrorCode, -15) ;
				jsonObject.accumulate(Value, "邮箱类型错误");
				return jsonObject.toString() ;
			}
			
			if(email.matches(Constant.EmailReg) == false ){
				jsonObject.accumulate(ErrorCode, -8) ;
				jsonObject.accumulate(Value, "邮箱格式错误");
				return jsonObject.toString() ;
			}
			
			boolean flag = this.frontUserService.isEmailExists(email) ;
			if(type == SendMailTypeEnum.FindPassword){
				if(flag == false ){
					jsonObject.accumulate(ErrorCode, -16) ;
					jsonObject.accumulate(Value, "邮箱不存在");
					return jsonObject.toString() ;
				}
			}
			
			if(type == SendMailTypeEnum.RegMail||type == SendMailTypeEnum.ValidateMail){
				if(flag == true ){
					jsonObject.accumulate(ErrorCode, -6) ;
					jsonObject.accumulate(Value, "邮箱已经存在");
					return jsonObject.toString() ;
				}
			}
			String msg=getLocaleMessage(request,null,"mail.title.reg");
			String lcal = RequestContextUtils.getLocale(request).toString();
			frontValidateService.saveSendMailNologin(msg, lcal, getIpAddr(request), email, type,0);
			//SendMail(request,getIpAddr(request), email, type,0) ;
			
			jsonObject.accumulate(ErrorCode, 0) ;
			jsonObject.accumulate(Value, "验证码发送成功");
			return jsonObject.toString() ;
			
		}catch(Exception e){
			return ApiConstant.getUnknownError(e);
		}
			
	}

	
	/**
	 * 找回登录密码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String FindLoginPassword(HttpServletRequest request) throws Exception {
		try {
			int type = Integer.parseInt(request.getParameter("type").trim());//1：phone 2：mail
			String email = request.getParameter("email").trim().toLowerCase();
			String password = request.getParameter("newpassword").trim();
			String code = request.getParameter(ErrorCode);
			String areaCode = "86" ;
			String ip = getIpAddr(request) ;
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate(Result, true);
			
			if(password == null || password.length()<6||password.length()>20){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "密码长度必须为6~20位") ;
				return jsonObject.toString() ;
			}
			
			if(type==1){
				//手机
				
				if(!email.matches(Constant.PhoneReg)){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "手机格式错误") ;
					return jsonObject.toString() ;
				}
				
				boolean flag1 = this.frontUserService.isTelephoneExists(email) ;
				if(flag1 == false ){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "手机不存在") ;
					return jsonObject.toString() ;
				}
				
				boolean mobilValidate = validateMessageCode(ip,areaCode,email, MessageTypeEnum.FIND_PASSWORD, code) ;
				if(!mobilValidate){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}
				
			}else{
				//邮箱注册
				boolean flag = this.frontUserService.isEmailExists(email) ;
				
				if(email.matches(Constant.EmailReg) == false ){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "邮箱格式错误") ;
					return jsonObject.toString() ;
				}
				
				if(flag == false ){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "邮箱不存在") ;
					return jsonObject.toString() ;
				}
				
				boolean mailValidate = validateMailCode(ip, email, SendMailTypeEnum.FindPassword, code);
				if(!mailValidate){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "邮箱验证码错误") ;
					return jsonObject.toString() ;
				}
			}

			boolean flag = false ;
			Fuser fuser = null ;
			
			fuser = this.frontUserService.findUserByProperty(type==1?"ftelephone":"femail", email).get(0) ; 
			
			if(fuser!=null ){
				fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt()));
				this.frontUserService.updateFuser(fuser);
				flag = true ;
			}
			if(flag == true ){
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "密码修改成功") ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}
			
		} catch (Exception e) {
			return ApiConstant.getUnknownError(e);
		}
	}
	
	/**
	 * 获取提现银行列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GeRechargeBankList(
			HttpServletRequest request
			) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;

			JSONArray jsonArray = new JSONArray() ;
			List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo();
			for (Systembankinfo systembankinfo : systembankinfos) {
				JSONObject item = new JSONObject() ;
				item.accumulate("id", systembankinfo.getFid()) ;
				item.accumulate("fbankName", systembankinfo.getFbankName()) ;
				item.accumulate("fownerName", systembankinfo.getFownerName()) ;
				item.accumulate("fbankAddress", systembankinfo.getFbankAddress()) ;
				item.accumulate("fbankNumber", systembankinfo.getFbankNumber()) ;
				jsonArray.add(item) ;
				
			}
			jsonObject.accumulate("list", jsonArray) ;
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 人民币充值
	 * @param money 人名币金额
	 * @param bankid 银行ID
	 * @return
	 * @throws Exception
	 */
	public String RechargeCny(HttpServletRequest request) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			double money = Utils.getDoubleUp(Double.parseDouble(request.getParameter("money")), 2) ;
			int bankid = Integer.parseInt(request.getParameter("bankid"));
			double minRecharge = Double.parseDouble(this.frontConstantMapService.get("minrechargecny").toString()) ;
			double maxRecharge = Double.parseDouble(this.frontConstantMapService.get("maxrechargecny").toString().trim()) ;
			LOGGER.info(CLASS_NAME + " usdtManual,配置最小充值金额为minRecharge:{}", minRecharge);
			LOGGER.info(CLASS_NAME + " usdtManual,配置最大充值金额为maxRecharge:{}", maxRecharge);
			if(money < minRecharge){
				//非法
				jsonObject.accumulate(ErrorCode, -1);
				Object[] params = new Object[]{minRecharge};
				jsonObject.accumulate(Value, getLocaleMessage(request,params,"json.account.minrecharge"));
				return jsonObject.toString();
			}
			
			if(money > maxRecharge){
				//非法
				jsonObject.accumulate(ErrorCode, -1);
				Object[] params = new Object[]{maxRecharge};
				jsonObject.accumulate(Value, getLocaleMessage(request,params,"json.account.maxrecharge"));
				return jsonObject.toString();
			}
			
			Systembankinfo systembankinfo = this.frontBankInfoService.findSystembankinfoById(bankid);
			if(systembankinfo==null|| systembankinfo.getFstatus()==SystemBankInfoEnum.ABNORMAL){
				LOGGER.info(CLASS_NAME + " alipayManual,查询银行账户信息为空或账户停用");
				//银行账号停用
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "银行帐户不存在");
				return jsonObject.toString();
			}
			Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
			fcapitaloperation.setFamount(money) ;
			fcapitaloperation.setSystembankinfo(systembankinfo) ;
			fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
			fcapitaloperation.setFtype(CapitalOperationTypeEnum.USDT_IN) ;
			fcapitaloperation.setFuser(this.getAppFuser(this.curLoginToken)) ;
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
			
		} catch (Exception e) {
			return ApiConstant.getUnknownError(e);
		}
	}
	
	/**
	 * 获取登录记录信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetLoginLogs(HttpServletRequest request) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int currentPage = 1 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}

			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			String filter = "where fkey2='"+fuser.getFloginName()+"' and ftype="+LogTypeEnum.User_LOGIN+" order by fid desc";

			List<Flog> logs = this.logService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage, filter, true);
			int total = this.adminService.getAllCount("Flog", filter);
			int totalPage = total/maxResult + ((total%maxResult)  ==0?0:1) ;

			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			JSONArray jsonArray = new JSONArray() ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			for (int i=0;i<logs.size();i++) {
				Flog log = logs.get(i);
				JSONObject item = new JSONObject();
				item.accumulate("createTime", sdf.format(log.getFcreateTime()));
				item.accumulate("ip", log.getFkey3());
				jsonArray.add(item);
			}
			jsonObject.accumulate("list", jsonArray);
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	   /**
		 * 安全设置记录
		 * @param currentPage 当前页数
		 * @return
		 * @throws Exception
		 */
		public String GetSettingLogs(HttpServletRequest request) throws Exception{
			try {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int currentPage = 1 ;
				try{
					currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
					currentPage = currentPage < 1?1:currentPage ;
				}catch (Exception e) {}

				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				
				String filter = "where fkey2='"+fuser.getFloginName()+"' and ftype <> "+LogTypeEnum.User_LOGIN+" order by fid desc";

				List<Flog> logs = this.logService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage, filter, true);
				int total = this.adminService.getAllCount("Flog", filter);
				int totalPage = total/maxResult + ((total%maxResult)  ==0?0:1) ;

				jsonObject.accumulate("totalPage", totalPage) ;
				jsonObject.accumulate("currentPage", currentPage) ;
				JSONArray jsonArray = new JSONArray() ;
				if(currentPage>totalPage){
					jsonObject.accumulate("list", jsonArray) ;
					return jsonObject.toString() ;
				}
				for (int i=0;i<logs.size();i++) {
					Flog log = logs.get(i);
					JSONObject item = new JSONObject();
					item.accumulate("createTime", sdf.format(log.getFcreateTime()));
					item.accumulate("type", log.getFtype_s());
					item.accumulate("ip", log.getFkey3());
					jsonArray.add(item);
				}
				jsonObject.accumulate("list", jsonArray);
				return jsonObject.toString() ;
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		/**
		 * 问题类型列表
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String GetQuestionTypeList(
				HttpServletRequest request
				) throws Exception {
			try {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;

				JSONArray jsonArray = new JSONArray() ;
				for (int i=QuestionTypeEnum.COIN_RECHARGE;i<=QuestionTypeEnum.OTHERS;i++) {
					JSONObject item = new JSONObject() ;
					item.accumulate("type", i) ;
					item.accumulate("name", QuestionTypeEnum.getEnumString(i)) ;
					jsonArray.add(item) ;
				}
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}

		
		/**
		 * 提交问题信息
		 * @param request
		 * @return
		 */
		public String PostQuestion(HttpServletRequest request){
			try {
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				String desc = request.getParameter("content") ;
				int type = Integer.parseInt(request.getParameter("type")) ;

				type = type<QuestionTypeEnum.COIN_RECHARGE?QuestionTypeEnum.COIN_RECHARGE:type ;
				type = type>QuestionTypeEnum.OTHERS?QuestionTypeEnum.OTHERS:type ;
						
				desc = HtmlUtils.htmlEscape(desc) ;
						
				Fquestion fquestion = new Fquestion() ;
				fquestion.setFuser(fuser) ;
				fquestion.setFcreateTime(Utils.getTimestamp()) ;
				fquestion.setFdesc(desc) ;
				fquestion.setFname(fuser.getFnickName()) ;
				fquestion.setFstatus(QuestionStatusEnum.NOT_SOLVED) ;
				fquestion.setFtelephone(fuser.getFtelephone()) ;
				fquestion.setFtype(type) ;

				this.frontQuestionService.save(fquestion) ;
				
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				jsonObject.accumulate(ErrorCode, 0);	
				return jsonObject.toString();
				
			} catch (Exception e) {
				return ApiConstant.getUnknownError(e);
			}
		}
		
	   /**
	    * 删除问题
	    * @param request
	    * @return
	    */
	   public String DelQuestion(HttpServletRequest request){
			try {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				int id = Integer.parseInt(request.getParameter("id")) ;
                Fquestion question = this.frontQuestionService.findById(id);
				if(question.getFstatus() == QuestionStatusEnum.SOLVED){
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "已解决的问题不允许删除");
					return jsonObject.toString();
				}
				if(question.getFuser().getFid() != fuser.getFid()){
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "非法操作");
					return jsonObject.toString();
				}

				this.frontQuestionService.delete(question);
				jsonObject.accumulate(Value, "删除成功");
				jsonObject.accumulate(ErrorCode, 0);	
				return jsonObject.toString();
			} catch (Exception e) {
				return ApiConstant.getUnknownError(e);
			}
		}
		
	    /**
	     * 获取问题列表
	     * @param request
	     * @return
	     */
		public String ListQuestions(HttpServletRequest request){
			try {
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				
				Map<String, Object> param = new HashMap<String, Object>() ;
				param.put("fuser.fid", fuser.getFid()) ;
				List<Fquestion> list = this.frontQuestionService.findByParam(param, 0, Constant.AppRecordPerPage, "fid desc") ;
				System.out.println("------xx-------"+fuser.getFid());
				System.out.println("----xx`1--------"+list);
				JSONArray jsonArray = new JSONArray() ;
				for (int i = 0; i < list.size(); i++) {
					JSONObject item = new JSONObject() ;
					Fquestion fquestion = list.get(i) ;
					item.accumulate("date", Utils.dateFormat(fquestion.getFcreateTime())) ;
					item.accumulate("ask", fquestion.getFdesc()) ;
					item.accumulate("type", fquestion.getFtype_s()) ;
					item.accumulate("id", fquestion.getFid()) ;
					item.accumulate("status", fquestion.getFstatus()) ;
					if(fquestion.getFstatus()==QuestionStatusEnum.NOT_SOLVED){
						item.accumulate("answer", null) ;
					}else{
						item.accumulate("answer", fquestion.getFanswer()) ;
					}
					jsonArray.add(item) ;
				}
				jsonObject.accumulate("list", jsonArray) ;
				
				return jsonObject.toString() ;
			} catch (Exception e) {
				return ApiConstant.getUnknownError(e);
			}
		}
		
		/**
		 * 安全信息绑定情况查询
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String getSecurity(HttpServletRequest request) throws Exception{
			JSONObject jsonObject = new JSONObject() ;
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			boolean isBindGoogle = fuser.getFgoogleBind() ;
			boolean isBindTelephone = fuser.isFisTelephoneBind() ;
			boolean isBindEmail = fuser.getFisMailValidate();
			boolean isTradePassword = false;
			boolean isLoginPassword = true;
			String googleTXT = "未绑定";
			String telNumberTXT = "未绑定";
			String emailTXT = "未绑定";
			String tradePaswordTXT = "未设置";
			String loginPaswordTXT = "******";
			if(fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
				isTradePassword = true;
				tradePaswordTXT = "******";
			}
	        if(isBindTelephone){
	        	telNumberTXT = fuser.getFtelephone().substring(0, fuser.getFtelephone().length()-5)+"****";
			}
	        if(isBindEmail){
	        	String[] args = fuser.getFemail().split("@");
	        	emailTXT = args[0].substring(0, args[0].length()-(args[0].length()>=3?3:1))+"****"+args[1];
			}
	        if(isBindGoogle){
	        	googleTXT = "已绑定";
	        }
	        
	        jsonObject.accumulate("googleTXT", googleTXT);
	        jsonObject.accumulate("isBindGoogle", isBindGoogle);
	        
	        jsonObject.accumulate("isBindTelephone", isBindTelephone);
	        jsonObject.accumulate("telNumberTXT", telNumberTXT);
	        
	        jsonObject.accumulate("isBindEmail", isBindEmail);
	        jsonObject.accumulate("emailTXT", emailTXT);
	        
	        jsonObject.accumulate("isTradePassword", isTradePassword);
	        jsonObject.accumulate("tradePaswordTXT", tradePaswordTXT);
	        
	        jsonObject.accumulate("isLoginPassword", isLoginPassword);
	        jsonObject.accumulate("loginPaswordTXT", loginPaswordTXT);
	        
	        return jsonObject.toString();
		}
	
		
		/**
		 * 绑定手机号码
		 * @param phone 手机号
		 * @param code  验证码
		 * @param totpCode 谷歌验证码
		 * @return
		 * @throws Exception
		 */
		public String bindPhone(
				HttpServletRequest request
				) throws Exception{
			try {
				String areaCode = "86" ;
				
				String phone = request.getParameter("phone") ;
				String code = request.getParameter("code") ;
				String totpCode = request.getParameter("totpCode");
				
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				
				if(!phone.matches("^\\d{10,14}$")){//手機格式不對
					jsonObject.accumulate(ErrorCode , 1) ;
					jsonObject.accumulate(Value, "手机格式错误") ;
					return jsonObject.toString() ;
				}
				
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				if(fuser.isFisTelephoneBind()){//已經綁定過手機了
					jsonObject.accumulate(ErrorCode , 1) ;
					jsonObject.accumulate(Value, "您的账号已经绑定手机") ;
					return jsonObject.toString() ;
				}
				
				String ip = getIpAddr(request) ;
				int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				if(tel_limit<=0){
					jsonObject.accumulate(ErrorCode , 1) ;
					jsonObject.accumulate(Value, "短信验证码错误次数超限，请稍后再试！") ;
					return jsonObject.toString() ;
				}
				if(google_limit<=0){
					jsonObject.accumulate(ErrorCode, 1) ;
					jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!");
					return jsonObject.toString() ;
				}
				
				if(fuser.getFgoogleBind()){
					boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
					if(!googleAuth){
						//谷歌驗證失敗
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
						jsonObject.accumulate(ErrorCode, 1) ;
						jsonObject.accumulate(Value, "GOOGLE验证码有误，您还有"+google_limit+"次机会");
						return jsonObject.toString() ;
					}else{
						this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					}
				}
				
				if(validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.BANGDING_MOBILE, code)){
					//判斷手機是否被綁定了
					List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
					if(fusers.size()>0){
						jsonObject.accumulate(ErrorCode , 1) ;
						jsonObject.accumulate(Value, "该手机号码已经绑定到其他账号了") ;
						return jsonObject.toString() ;
					}
					
					fuser.setFareaCode(areaCode) ;
					fuser.setFtelephone(phone) ;
					fuser.setFisTelephoneBind(true) ;
					fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
					this.frontUserService.updateFuser(fuser) ;
					
					//成功
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate(ErrorCode , 0) ;
					jsonObject.accumulate(Value, "绑定手机成功") ;
					return jsonObject.toString() ;
				}else{
					//手機驗證錯誤
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate(ErrorCode , 1) ;
					jsonObject.accumulate(Value, "验证码错误") ;
					return jsonObject.toString() ;
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		
		
		/**
		 * 修改密码（交易密码和登录密码）
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String changePassword(
				HttpServletRequest request
				) throws Exception {
			
			try {
				Integer type = Integer.parseInt(request.getParameter("type")) ;
				String password1 = request.getParameter("password1") ;
				String password2 = request.getParameter("password2") ;
				String vcode = request.getParameter("vcode") ;
				String totpCode = request.getParameter("totpCode");
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				
				if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
					jsonObject.accumulate(ErrorCode, 1) ;
					jsonObject.accumulate(Value, "请先绑定绑定谷歌认证或者手机") ;
					return jsonObject.toString() ;//需要绑定绑定谷歌或者电话才能修改密码
				}
				
				String ip = getIpAddr(request) ;
				if(fuser.getFgoogleBind()){
					int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					if(google_limit<=0){
						jsonObject.accumulate(ErrorCode, 1) ;
						jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!") ;
						return jsonObject.toString() ;
					}else{
						if(!GoogleAuth.auth(Long.parseLong(totpCode), fuser.getFgoogleAuthenticator())){
							jsonObject.accumulate(ErrorCode, 1) ;
							jsonObject.accumulate(Value, "GOOGLE验证码有误，您还有"+google_limit+"次机会") ;
							this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
							return jsonObject.toString() ;
						}else{
							this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
						}
					}
				}
				
				if(fuser.isFisTelephoneBind()){
					int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					if(mobil_limit<=0){
						jsonObject.accumulate(ErrorCode, 1) ;
						jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!") ;
						return jsonObject.toString() ;
					}else{
						boolean mobilValidate = false ;
						if(type == 1){
							mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CHANGE_LOGINPWD, vcode) ;
						}else{
							mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CHANGE_TRADEPWD, vcode) ;
						}
						if(!mobilValidate){
							jsonObject.accumulate(ErrorCode, 1) ;
							jsonObject.accumulate(Value, "手机验证码有误，您还有"+mobil_limit+"次机会") ;
							this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
							return jsonObject.toString() ;
						}else{
							this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
						}
					}
				}
				
				if(type == 1){
					//登录密码
					if(fuser.getFloginPassword().equals(Utils.MD5(password1,fuser.getSalt()))){
						fuser.setFloginPassword(Utils.MD5(password2,fuser.getSalt())) ;
						this.frontUserService.updateFuser(fuser) ;
						
						jsonObject.accumulate(ErrorCode , 0) ;
						jsonObject.accumulate(Value, "登录密码修改成功") ;
						return jsonObject.toString() ;
					}else{
						jsonObject.accumulate(ErrorCode , 1) ;
						jsonObject.accumulate(Value, "原始登录密码错误") ;
						return jsonObject.toString() ;
					}
				}else{
					//交易
					if(fuser.getFtradePassword()==null || Utils.MD5(password1,fuser.getSalt()).equals(fuser.getFtradePassword())){
						fuser.setFtradePassword(Utils.MD5(password2,fuser.getSalt())) ;
						this.frontUserService.updateFuser(fuser) ;
						
						jsonObject.accumulate(ErrorCode , 0) ;
						jsonObject.accumulate(Value, "交易密码修改成功") ;
						return jsonObject.toString() ;
					}else{
						jsonObject.accumulate(ErrorCode , 1) ;
						jsonObject.accumulate(Value, "原始交易密码错误") ;
						return jsonObject.toString() ;
					}
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		/**
		 * 绑定谷歌认证
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String googleAuth(
				HttpServletRequest request
				) throws Exception{
			try {
				JSONObject jsonObject = new JSONObject() ;
				
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				if(fuser.getFgoogleBind()){
					//已经绑定机器了，属于非法提交
					jsonObject.accumulate(ErrorCode, 1) ;
					jsonObject.accumulate(Value, "会员已绑定谷歌认证") ;
					return jsonObject.toString() ;
				}
				
				Map<String, String> map = GoogleAuth.genSecret(fuser.getFloginName()) ;
				String totpKey = map.get("secret") ;
				String qecode = map.get("url") ;
				
				fuser.setFgoogleAuthenticator(totpKey) ;
				fuser.setFgoogleurl(qecode) ;
				fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
				this.frontUserService.updateFUser(fuser,-1,null) ;
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
				String device_name = Constant.GoogleAuthName+":"+fuser.getFloginName();
				
				jsonObject.accumulate("device_name", device_name) ;
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate("totpKey", totpKey) ;
						
				return jsonObject.toString() ;
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		
		/**
		 * 获取谷歌认证密钥
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String getBindGoogle(HttpServletRequest request) throws Exception{
			try {
				JSONObject jsonObject = new JSONObject() ;
				String totpCode = request.getParameter("totpCode");
				String totpKey = request.getParameter("totpKey");
				
				String ip = getIpAddr(request) ;
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;

				boolean b_status = fuser.getFgoogleBind()==false
									&& totpKey.equals(fuser.getFgoogleAuthenticator())
									&& !totpKey.trim().equals("");
				
				if(!b_status){
					//非法提交
					jsonObject.accumulate(ErrorCode, 1) ;
					jsonObject.accumulate(Value, "您已绑定GOOGLE验证器，请勿重复操作") ;
					return jsonObject.toString() ;
				}
				
				int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				if(tel_limit<=0){
					jsonObject.accumulate(ErrorCode , 1) ;
					jsonObject.accumulate(Value, "短信验证码错误次数超限，请稍后再试！") ;
					return jsonObject.toString() ;
				}
				
				int limitedCount = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				if(limitedCount>0){
					boolean auth = GoogleAuth.auth(Long.parseLong(totpCode), fuser.getFgoogleAuthenticator()) ;
					if(auth){
						jsonObject.accumulate(ErrorCode, 0) ;//0成功，-1，错误
						jsonObject.accumulate(Value, "绑定成功") ;
						this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
						
						fuser.setFgoogleBind(true) ;
						fuser.setFgoogleValidate(false) ;
						this.frontUserService.updateFUser(fuser, LogTypeEnum.User_BIND_GOOGLE,ip) ;
						if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
							getSession(request).setAttribute("login_user", fuser) ;
						}
						return jsonObject.toString() ;
					}else{
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
						jsonObject.accumulate(ErrorCode, 1) ;
						jsonObject.accumulate(Value, "GOOGLE验证码有误，您还有"+limitedCount+"次机会") ;
						return jsonObject.toString() ;
					}
				}else{
					jsonObject.accumulate(ErrorCode, 1) ;
					jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!") ;
					return jsonObject.toString() ;
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
	
	/**
	 * 绑定邮箱
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String bindMail(HttpServletRequest request) throws Exception{
			try {
				JSONObject js = new JSONObject();
				String email = HtmlUtils.htmlEscape(request.getParameter("email"));
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				//邮箱注册
				boolean isExists = this.frontUserService.isEmailExists(email) ;
				if(isExists){
					js.accumulate(ErrorCode, 1);
					js.accumulate(Value, "邮箱地址已存在");
					return js.toString();
				}
				
				if(!email.matches(Constant.EmailReg)){
					js.accumulate(ErrorCode, 1);
					js.accumulate(Value, "邮箱格式不正确");
					return js.toString();
				}
				
				if(fuser.getFisMailValidate()){
					js.accumulate(ErrorCode, 1);
					js.accumulate(Value, "您的邮箱已经绑定成功");
					return js.toString();
				}
				
				boolean flag = false ;
				try {
					
					String lcal = RequestContextUtils.getLocale(request).toString();
					String msg=getLocaleMessage(request,null,"mail.title.bind");
					flag = this.frontUserService.saveValidateEmail(fuser,email,true,msg,lcal) ;
					//flag = this.frontUserService.saveValidateEmail(fuser,email,false,request) ;
				} catch (Exception e) {
					e.printStackTrace();
					js.accumulate(ErrorCode, 1);
					js.accumulate(Value, "网络异常");
					return js.toString();
				}
				
				if(flag){
					js.accumulate(ErrorCode, 0);
					js.accumulate(Value, "发送成功，请前往邮箱点击链接，进行绑定，如果未找到邮件，请在垃圾邮件中查找");
					return js.toString();
				}else{
					js.accumulate(ErrorCode, 1);
					js.accumulate(Value, "半小时内只能发送一次");
					return js.toString();
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
	
	
	public String GetScoreLogs(HttpServletRequest request) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int currentPage = 1 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}

			Fuser fuser = this.getAppFuser(this.curLoginToken) ;

			String filter = " where fuser.fid=? order by fid desc ";
			int total = this.utilsService.count(filter, FscoreRecord.class, fuser.getFid()) ;
			List<FscoreRecord> fscoreRecords = this.utilsService.list((currentPage-1)*maxResults, maxResults, filter, true, FscoreRecord.class,fuser.getFid()) ;
			int totalPage = total/maxResult + ((total%maxResult)  ==0?0:1) ;

			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			JSONArray jsonArray = new JSONArray() ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			for (int i=0;i<fscoreRecords.size();i++) {
				FscoreRecord scoreRecord = fscoreRecords.get(i);
				JSONObject item = new JSONObject();
				item.accumulate("createTime", sdf.format(scoreRecord.getFcreatetime()));
				item.accumulate("score",scoreRecord.getScore());
				item.accumulate("type",scoreRecord.getType_s());
				jsonArray.add(item);
			}
			jsonObject.accumulate("list", jsonArray);
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	

	
	public String getScoreRule(HttpServletRequest request) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			jsonObject.accumulate("vip", fuser.getFscore().getFlevel());
			jsonObject.accumulate("score", fuser.getFscore().getFscore());
	
			Map<Integer,Double> recordMap = new HashMap<Integer,Double>();
			String ss = " where fuser.fid=? and type >= ? order by fid desc ";
			List<FscoreRecord> fscoreRecords = this.utilsService.list(0, 0, ss, false, FscoreRecord.class,fuser.getFid(),ScoreRecordTypeEnum.SHIMINGRENZHENG) ;
			for (FscoreRecord fscoreRecord : fscoreRecords) {
				recordMap.put(fscoreRecord.getType(), fscoreRecord.getScore());
			}
			
			JSONArray scoreRuleDayArray = new JSONArray();
			JSONArray scoreRuleNewArray = new JSONArray();
			String sql = "order by type asc";
			List<FscoreSetting> scores = this.scoreSettingService.list(0, 0, sql, false);
			int i=0;
			for (FscoreSetting fscoreSetting : scores) {
				JSONObject js = new JSONObject() ;
				if(i <=3){
					js.accumulate("op", fscoreSetting.getType_s());
					if(i ==3){
						js.accumulate("score", "折合人民币×"+fscoreSetting.getScore());
					}else{
						js.accumulate("score", "+"+fscoreSetting.getScore());
					}
					js.accumulate("remark", fscoreSetting.getRemark());
					scoreRuleDayArray.add(js);
				}else{
					js.accumulate("op", fscoreSetting.getType_s());
					if(recordMap.containsKey(fscoreSetting.getType())){
						js.accumulate("isFinish", true);
					}else{
						js.accumulate("isFinish", false);
					}
					js.accumulate("score", "+"+fscoreSetting.getScore());
					js.accumulate("remark", fscoreSetting.getRemark());
					scoreRuleNewArray.add(js);
				}
				i++;
			}
			jsonObject.accumulate("scoreRuleDayArray", scoreRuleDayArray);
			jsonObject.accumulate("scoreRuleNewArray", scoreRuleNewArray);

			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	/**
	 * 获取用户vip等级和交易手续费比率
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getLevelRule(HttpServletRequest request) throws Exception {

		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			jsonObject.accumulate("vip", fuser.getFscore().getFlevel());
			jsonObject.accumulate("score", fuser.getFscore().getFscore());
			
			Map<Integer,String> wsMap = new HashMap<Integer,String>();
			String filter1 = "where fvirtualcointype.fid=2 order by flevel asc";
			List<Fwithdrawfees> withdrawFees = this.withdrawFeesService.list(0, 0, filter1, false);
			for (Fwithdrawfees fwithdrawfees : withdrawFees) {
				wsMap.put(fwithdrawfees.getFlevel(), fwithdrawfees.getFfee()*100+"%");
			}
			
			Map<Integer,String> feesMap = new HashMap<Integer,String>();
			String filter2 = "where ftrademapping.fid=1";
			List<Ffees> fees = this.feeService.list(0, 0, filter2, false);
			for (Ffees ffees : fees) {
				feesMap.put(ffees.getFlevel(), ffees.getFfee()*100+"%");
			}
			
			
			JSONArray rule = new JSONArray();
			String filter = "order by level asc";
			List<FlevelSetting> levels = this.levelSettingService.list(0, 0, filter, false);
			for (FlevelSetting flevelSetting : levels) {
				JSONObject js = new JSONObject() ;
				js.accumulate("vip", "VIP"+flevelSetting.getLevel());//等级
				if(flevelSetting.getLevel() !=6){//积分
					js.accumulate("score", flevelSetting.getScore2());
				}else{
					js.accumulate("score", ">="+flevelSetting.getScore());
				}
				js.accumulate("wsFee", wsMap.get(flevelSetting.getLevel()));
				js.accumulate("tradeFee", feesMap.get(flevelSetting.getLevel()));
				rule.add(js);
			}
			jsonObject.accumulate("rule", rule);

			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 删除会员银行卡信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String DeleteBankAddress(
			HttpServletRequest request
			) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			int fid = Integer.parseInt(request.getParameter("id"));
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			if(fuser.getFregType()==0){
				//注册类型为手机的账户，必须要绑定手机号码
				if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.bindinfo.ntp")) ;
					return jsonObject.toString() ;
				}
			}else{
				//对注册类型为邮箱的账户，必须要绑定邮箱地址
				if(null==fuser.getFemail() || fuser.getFemail().equals("")){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.bindinfo")+"(error code:nex)") ;
					return jsonObject.toString() ;
				}
			}
			
			FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(fid);
			if(fbankinfoWithdraw == null){
				jsonObject.accumulate(ErrorCode, 1) ;
				jsonObject.accumulate(Value, "记录不存在") ;
				return jsonObject.toString() ;
			}
			if(fuser.getFid() != fbankinfoWithdraw.getFuser().getFid()){
				jsonObject.accumulate(ErrorCode, 1) ;
				jsonObject.accumulate(Value, "非法操作") ;
				return jsonObject.toString() ;
			}
			//成功
			try {
				this.frontUserService.updateDelBankInfoWithdraw(fbankinfoWithdraw);
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "操作成功") ;
				this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_CNY, "删除银行卡成功");
			} catch (Exception e) {
				jsonObject.accumulate(ErrorCode, 1) ;
				jsonObject.accumulate(Value, "网络异常") ;
				this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_CNY, "删除银行卡失败");
			}
			
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	/**
	 * 删除虚拟币地址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String DetelCoinAddress(
			HttpServletRequest request
			) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			int fid = Integer.parseInt(request.getParameter("id"));
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			String code=actionSecurityCheck(request, fuser, false, false, false, "", false, "", "", MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT,LogTypeEnum.User_BTC, "删除提币地址成功","msg");
			if(!code.equals("ok")){
				return code;
			}
			
			FvirtualaddressWithdraw virtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(fid);
			if(virtualaddressWithdraw == null){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "记录不存在") ;
				return jsonObject.toString() ;
			}
			if(fuser.getFid() != virtualaddressWithdraw.getFuser().getFid()){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "非法操作") ;
				return jsonObject.toString() ;
			}
			//成功
			try {
				this.frontVirtualCoinService.updateDelFvirtualaddressWithdraw(virtualaddressWithdraw);
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "操作成功") ;
				this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_BTC, "删除提币地址成功："+virtualaddressWithdraw.getFadderess());
			} catch (Exception e) {
				jsonObject.accumulate(ErrorCode, 1) ;
				jsonObject.accumulate(Value, "网络异常") ;
				this.frontUserService.updateUserLog(fuser, getIpAddr(request), LogTypeEnum.User_BTC, "删除提币地址失败");
			}
			
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 新版绑定手机号码(或修改绑定手机)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String bindPhone2(
			HttpServletRequest request
			) throws Exception{
		try {
			
			Integer isUpdate = Integer.valueOf(request.getParameter("isUpdate")) ; // 0：绑定   1：修改
			String imgcode = request.getParameter("imgcode") ;  //图形验证码
			String areaCode = request.getParameter("areaCode") ; //手机区域号
			String phone = request.getParameter("phone") ;   //手机号
			String oldcode = request.getParameter("oldcode") ;  //旧验证码
			String newcode = request.getParameter("newcode") ;  //新验证码
			String totpCode = request.getParameter("totpCode"); //谷歌验证码
			
			JSONObject jsonObject = new JSONObject() ;
			areaCode = areaCode.replace("+", "");
			if(!phone.matches("^\\d{10,14}$")){//手機格式不對
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "手机格式错误");
				return jsonObject.toString() ;
			}
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			if(isUpdate ==0){
				if(fuser.isFisTelephoneBind()){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "您已绑定了手机号码");
					return jsonObject.toString() ;
				}
			}else{
				if(!fuser.isFisTelephoneBind()){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "您还未绑定手机号码");
					return jsonObject.toString() ;
				}
			}
			
			String ip = getIpAddr(request) ;
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(google_limit<=0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!");
				return jsonObject.toString() ;
			}
			if(tel_limit<=0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!");
				return jsonObject.toString() ;
			}
			
			if(fuser.getFgoogleBind()){
				boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
				if(!googleAuth){
					//谷歌驗證失敗
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					jsonObject.accumulate(ErrorCode, -1) ;
					Object[] params = new Object[]{google_limit};
					jsonObject.accumulate(Value, "GOOGLE验证码有误，您还有"+google_limit+"次机会");

					return jsonObject.toString() ;
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
			
			if(isUpdate ==1){
				if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.JIEBANG_MOBILE, oldcode)){
					//手機驗證錯誤
					 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate(ErrorCode, -1) ;
					Object[] params = new Object[]{tel_limit};
					jsonObject.accumulate(Value, "手机验证码有误，您还有"+tel_limit+"机会");

					return jsonObject.toString() ;
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
			
			if(validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.BANGDING_MOBILE, newcode)){
				//判斷手機是否被綁定了
				List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
				if(fusers.size()>0){//手機號碼已經被綁定了
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "手机号码已存在");
					return jsonObject.toString() ;
				}
				
				
				if(vcodeValidate(request, imgcode) == false ){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "请输入正确的图片验证码");
					return jsonObject.toString() ;
				}
				
				fuser.setFareaCode(areaCode) ;
				fuser.setFtelephone(phone) ;
				if(fuser.getFregType() == RegTypeEnum.TEL_VALUE){
					fuser.setFloginName(phone);
				}
				fuser.setFisTelephoneBind(true) ;
				fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
				try {
					this.frontUserService.updateFUser(fuser,LogTypeEnum.User_BIND_PHONE,ip) ;
					if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
						getSession(request).setAttribute("login_user", fuser) ;
					}
				} catch (Exception e) {
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "网络异常");
					return jsonObject.toString() ;
				}finally{
					//成功
					this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.BANGDING_MOBILE);
					this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.JIEBANG_MOBILE);
				}

				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "绑定成功");
				return jsonObject.toString() ;
			}else{
				//手機驗證錯誤
				 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate(ErrorCode, -1) ;

				Object[] params = new Object[]{tel_limit};
				jsonObject.accumulate(Value, "手机验证码有误，您还有"+tel_limit+"机会");

				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 新版修改密码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String changePassword2(HttpServletRequest request) throws Exception{
		//pwdType  0:登录密码       1：交易密码
		Integer pwdType = Integer.parseInt(request.getParameter("pwdType")) ;
		String newPwd = request.getParameter("newPwd") ;
		String reNewPwd = request.getParameter("reNewPwd") ;
		String originPwd = request.getParameter("originPwd") ;
		String phoneCode = request.getParameter("phoneCode") ;
		String totpCode = request.getParameter("totpCode");
		
		Fuser fuser = this.getAppFuser(this.curLoginToken) ;
		
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		
		if(fuser.getFregType()==0){
			//注册类型为手机的账户，必须要绑定手机号码
			if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "请绑定手机") ;
				return jsonObject.toString() ;
			}
		}else{
			//对注册类型为邮箱的账户，必须要绑定邮箱地址
			if(null==fuser.getFemail() || fuser.getFemail().equals("")){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "请绑定邮箱") ;
				return jsonObject.toString() ;
			}
		}
		
		if(!newPwd.equals(reNewPwd)){
			jsonObject.accumulate(ErrorCode, -3) ;
			jsonObject.accumulate(Value, "两次输入密码不一样") ;
			return jsonObject.toString() ;//两次输入密码不一样
		}
		
		
		if(pwdType==0){
			String newPass = Utils.MD5(newPwd,fuser.getSalt());
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0 && fuser.getFtradePassword().equals(newPass)){
				jsonObject.accumulate(ErrorCode, -6) ;
				jsonObject.accumulate(Value, "登陆密码不能与交易密码相同!") ;
				return jsonObject.toString() ;
			}
			//修改登陆密码
			if(!fuser.getFloginPassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
				jsonObject.accumulate(ErrorCode, -5) ;
				jsonObject.accumulate(Value, "原始登陆密码错误") ;
				return jsonObject.toString() ;//原始密码错误
			}
			
			String code=actionSecurityCheck(request, fuser, false, false, false, null, true, phoneCode, totpCode, MessageTypeEnum.CHANGE_LOGINPWD, LogTypeEnum.User_UPDATE_LOGIN_PWD, "修改密码异常", "value");		
			if(!code.equals("ok")){
				return code;
			}
			
		}else{
			String newPass = Utils.MD5(newPwd,fuser.getSalt());
			if(fuser.getFloginPassword().equals(newPass)){
				jsonObject.accumulate(ErrorCode, -6) ;
				jsonObject.accumulate(Value, "登陆密码不能与交易密码相同") ;
				return jsonObject.toString() ;
			}
			//修改交易密码
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
				if(!fuser.getFtradePassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
					jsonObject.accumulate(ErrorCode, -5) ;
					jsonObject.accumulate(Value, "原始交易密码错误") ;
					return jsonObject.toString() ;//原始密码错误
				}
			}
			String code=actionSecurityCheck(request, fuser, false, false, false, null, true, phoneCode, totpCode, MessageTypeEnum.CHANGE_TRADEPWD, LogTypeEnum.User_UPDATE_TRADE_PWD, "修改密码异常", "value");		
			if(!code.equals("ok")){
				return code;
			}
		}
			
		if(pwdType==0){
			//修改交易密码
			if(fuser.getFloginPassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
				jsonObject.accumulate(ErrorCode, -10) ;
				jsonObject.accumulate(Value, "新的登陆密码与原始密码相同，修改失败") ;
				return jsonObject.toString() ;
			}
		}else{
			//修改交易密码
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0
					&&fuser.getFtradePassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
				jsonObject.accumulate(ErrorCode, -10) ;
				jsonObject.accumulate(Value, "新的交易密码与原始密码相同，修改失败") ;
				return jsonObject.toString() ;
			}
		}
		
		String ip = getIpAddr(request) ;
		
		try {
			if(pwdType==0){
				//修改登陆密码
				fuser.setFloginPassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				this.frontUserService.updateFUser(fuser,LogTypeEnum.User_UPDATE_LOGIN_PWD,ip) ;
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
			}else{
				//修改交易密码
				int logType=0;
				if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
					logType = LogTypeEnum.User_UPDATE_TRADE_PWD;
				}else{
					logType = LogTypeEnum.User_SET_TRADE_PWD;
					
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
										
				}
				fuser.setFtradePassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				
				this.frontUserService.updateFUser(fuser,logType,ip) ;
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
			}
		} catch (Exception e) {
			jsonObject.accumulate(ErrorCode, -3) ;
			jsonObject.accumulate(Value, "网络异常") ;
			if(pwdType==0){
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_UPDATE_LOGIN_PWD, "密码操作失败");
			}else{
				this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_UPDATE_TRADE_PWD, "密码操作失败");
			}
			return jsonObject.toString() ;
		}finally{
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_LOGINPWD);
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_TRADEPWD);
		}
		
		jsonObject.accumulate(ErrorCode, 0) ;
		jsonObject.accumulate(Value, "操作成功") ;
		return jsonObject.toString() ;
	}
	
	
	/**
	 * 新版注册
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String UserRegister2(HttpServletRequest request) throws Exception {
		try{
			String areaCode = "86" ;
			String password = request.getParameter("password") ;   //登录密码
			String regName = request.getParameter("regName") ;  //登录名
			int regType = Integer.parseInt( request.getParameter("regType") ) ; //0手机、1邮箱
//			String vcode = request.getParameter("vcode") ;
			String ecode = request.getParameter("ecode") ;      //邮箱验证码
			String phoneCode = request.getParameter("phoneCode") ;   //手机验证码
			
			JSONObject jsonObject = new JSONObject() ;
			
			String phone = HtmlUtils.htmlEscape(regName);
			phoneCode = HtmlUtils.htmlEscape(phoneCode);
			String isOpenReg = frontConstantMapService.get("isOpenReg").toString().trim();
			if(!isOpenReg.equals("1")){
				jsonObject.accumulate(ErrorCode, -888) ;
				jsonObject.accumulate(Value, "暂停注册") ;
				return jsonObject.toString() ;
			}
			
			password = HtmlUtils.htmlEscape(password.trim());
			if(password == null || password.length() <6){
				jsonObject.accumulate(ErrorCode, -11) ;
				jsonObject.accumulate(Value, "登陆短信长度有误") ;
				return jsonObject.toString() ;
			}
			//邮箱
			if(regType==0){
				//手机注册
				
				boolean flag1 = this.frontUserService.isTelephoneExists(regName) ;
				if(flag1){
					jsonObject.accumulate(ErrorCode, -22) ;
					jsonObject.accumulate(Value, "手机号码已经被注册") ;
					return jsonObject.toString() ;
				}
				
				if(!phone.matches(Constant.PhoneReg)){
					jsonObject.accumulate(ErrorCode, -22) ;
					jsonObject.accumulate(Value, "手机格式错误") ;
					return jsonObject.toString() ;
				}
				
				boolean mobilValidate = validateMessageCode(getIpAddr(request),areaCode,phone, MessageTypeEnum.REG_CODE, phoneCode) ;
				if(!mobilValidate){
					jsonObject.accumulate(ErrorCode, -20) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}
				
			}else{
				//邮箱注册
				boolean flag = this.frontUserService.isEmailExists(regName) ;
				if(flag){
					jsonObject.accumulate(ErrorCode, -12) ;
					jsonObject.accumulate(Value, "邮箱已经存在") ;
					return jsonObject.toString() ;
				}
				
				boolean mailValidate = validateMailCode(getIpAddr(request), phone, SendMailTypeEnum.RegMail, ecode);
				if(!mailValidate){
					jsonObject.accumulate(ErrorCode, -10) ;
					jsonObject.accumulate(Value, "邮箱验证码错误") ;
					return jsonObject.toString() ;
				}
				
				if(!regName.matches(Constant.EmailReg)){
					jsonObject.accumulate(ErrorCode, -12) ;
					jsonObject.accumulate(Value, "邮箱格式错误") ;
					return jsonObject.toString() ;
				}
				
			}
			
			//推广
			Fuser intro = null ;
			
			try {
				String intro_user = request.getParameter("intro_user") ;
				if(intro_user!=null && !"".equals(intro_user.trim())){
					intro = this.frontUserService.findById(Integer.parseInt(intro_user.trim())) ;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(intro==null){
				String isMustIntrol = frontConstantMapService.get("isMustIntrol").toString().trim();
				if(isMustIntrol.equals("1")){
					jsonObject.accumulate(ErrorCode, -200) ;
					jsonObject.accumulate(Value, "请填写正确的邀请码") ;
					return jsonObject.toString();
				}
			}
			
			Fuser fuser = new Fuser() ;
			if(intro!=null){
				fuser.setfIntroUser_id(intro) ;
			}
			fuser.setFintrolUserNo(null);
			
			
			
			if(regType == 0){
				//手机注册
				fuser.setFregType(RegTypeEnum.TEL_VALUE);
				fuser.setFtelephone(phone);
				fuser.setFareaCode(areaCode);
				fuser.setFisTelephoneBind(true);
				
				fuser.setFnickName(phone) ;
				fuser.setFloginName(phone) ;
			}else{
				fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
				fuser.setFemail(regName) ;
				fuser.setFisMailValidate(true) ;
				fuser.setFnickName(regName.split("@")[0]) ;
				fuser.setFloginName(regName) ;
			}
			
			fuser.setSalt(Utils.getUUID());
			fuser.setFregisterTime(Utils.getTimestamp()) ;
			fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt())) ;
			fuser.setFtradePassword(null) ;
			String ip = getIpAddr(request) ;
			fuser.setFregIp(ip);
			fuser.setFlastLoginIp(ip);
			fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
			fuser.setFlastLoginTime(Utils.getTimestamp()) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			Fuser saveFlag = null ;
			try {
				saveFlag = this.frontUserService.saveRegister(fuser) ;
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(regType==0){
					String key1 = ip+"message_"+MessageTypeEnum.REG_CODE ;
					String key2 = ip+"_"+phone+"_"+MessageTypeEnum.REG_CODE ;
					this.frontValidateMapService.removeMessageMap(key1);
					this.frontValidateMapService.removeMessageMap(key2);
				}else{
					String key1 = ip+"mail_"+SendMailTypeEnum.RegMail ;
					String key2 = ip+"_"+phone+"_"+SendMailTypeEnum.RegMail ;
					this.frontValidateMapService.removeMailMap(key1);
					this.frontValidateMapService.removeMailMap(key2);
				}
			}
		
			
			if(saveFlag!=null){
				this.SetSession(saveFlag,request) ;

				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "注册成功") ;
				return jsonObject.toString() ;
			
			}else{
				jsonObject.accumulate(ErrorCode, -10000) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}
		
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 新版实名认证
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String ValidateIdentity2(
			HttpServletRequest request
			) throws Exception {
		try {
			
			String identityNo = request.getParameter("identityNo");  //证件号码
			Integer identityType = Integer.valueOf(request.getParameter("identityType"));   //证件类型
			String address = request.getParameter("address");   //证件地址（国家）
			String realName = request.getParameter("realName");   //真实姓名
			
			LOGGER.info(CLASS_NAME + " validateIdentity,identityNo:{},realName:{}", identityNo, realName);
			JSONObject js = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			realName = HtmlUtils.htmlEscape(realName);
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			if(fuser.getFpostRealValidate()){
				jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "请耐心等待审核!");
				return jsonObject.toString();
			}
			identityNo = identityNo.toLowerCase();
			//把中国身份证进行特别校验
			if (address.equals("86")){
				 String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
			                "3", "2" };  
			        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
			                "9", "10", "5", "8", "4", "2" };  
					if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
						jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证号码长度应该为15位或18位!");
						LOGGER.info(CLASS_NAME + " validateIdentity,身份证号码长度应该为15位或18位");
						return jsonObject.toString();
					}
					
					String Ai = "";
			        if (identityNo.length() == 18) {  
			            Ai = identityNo.substring(0, 17);  
			        } else if (identityNo.length() == 15) {  
			            Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);  
			        }
					LOGGER.info(CLASS_NAME + " validateIdentity,Ai:{}", Ai);
			        if (Utils.isNumeric(Ai) == false) {  
			            jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证号码有误!");
						return jsonObject.toString();
			        }
			        // ================ 出生年月是否有效 ================  
			        String strYear = Ai.substring(6, 10);// 年份  
			        String strMonth = Ai.substring(10, 12);// 月份  
			        String strDay = Ai.substring(12, 14);// 月份  
			        if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {  
			        	jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证号码有误!");
						return jsonObject.toString();
			        }  
			        GregorianCalendar gc = new GregorianCalendar();  
			        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
			        try {
			            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 60  
			                    || (gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) <18) {
			            	jsonObject.accumulate(ErrorCode, 1);
							jsonObject.accumulate(Value, "用户的年龄范围是18-60岁!");
							return jsonObject.toString();
			            }
			        } catch (NumberFormatException e) {
			            e.printStackTrace();
			        }
			        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			        	jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证号码有误!");
						return jsonObject.toString();
			        }
			        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			        	jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证号码有误!");
						return jsonObject.toString();
			        }  
			        // =====================(end)=====================  
			  
			        // ================ 地区码时候有效 ================  
			        Hashtable h = Utils.getAreaCode();
			        if (h.get(Ai.substring(0, 2)) == null) {
			        	jsonObject.accumulate(ErrorCode, 1);
						jsonObject.accumulate(Value, "身份证号码有误!");
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
			            	jsonObject.accumulate("code", 1);
							jsonObject.accumulate(Value, "身份证号码有误!");
							return jsonObject.toString();
			            }
			        } else {
			            return "";  
			        }
			}
			if (realName.trim().length() > 32) {
				jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "真实姓名不合法!");
				return jsonObject.toString();
			}
			
			String sql = "where fidentityNo='"+identityNo+"'";
			int count = this.adminService.getAllCount("Fuser", sql);
			LOGGER.info(CLASS_NAME + " validateIdentity,从数据库中查询身份证号，返回结果集count:{}", count);
			if(count >0){
				jsonObject.accumulate(ErrorCode, 1);
				jsonObject.accumulate(Value, "身份证号码已存在!");
				return jsonObject.toString();
			}
			
			//释放通过KYC认证，避免国外用户提交KYC1，未经验证的情况
			boolean isTrue =false;
			
			if(Constant.IS_APPKEY.equals("true") && address.equals("86")){
				IDCardVerifyUtil verifyUtil = new IDCardVerifyUtil();
				isTrue = verifyUtil.isRealPerson(realName, identityNo);
				LOGGER.info(CLASS_NAME + " validateIdentity,调verifyUtil.isRealPerson进行实名认证，返回结果isTrue:{}", isTrue);
				if(!isTrue){
					jsonObject.accumulate(ErrorCode, 1);
					jsonObject.accumulate(Value, "您的姓名与身份证号有误，请核对!");
					return jsonObject.toString();
				}
			}
			
			boolean bReward = true;
			//检测是否开启了AGON的活动
			boolean bAgon = false;
			String agonstatus = this.systemArgsService.getValue("agon");
			if(agonstatus.equals("1")){
				bAgon = true;
			}
			
			LOGGER.info(CLASS_NAME + " validateIdentity,实名认证通过");
			Fscore fscore = fuser.getFscore();
			Fuser fintrolUser = null;
			Fintrolinfo introlInfo = null;
			Fvirtualwallet fvirtualwallet = null;
			Fintrolinfo introlInfoIntro = null;
			Fvirtualwallet fvirtualwalletIntro = null;
			LOGGER.info(CLASS_NAME + " validateIdentity,从系统参数表查询实名认证配置auditSendCoin  begin");
			String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
			String[] introlSendCoin = this.systemArgsService.getValue("introlSendCoin").split("#");
			
			LOGGER.info(CLASS_NAME + " validateIdentity,从系统参数表查询实名认证配置auditSendCoin:{}", new Gson().toJson(auditSendCoin));
			int coinID = Integer.parseInt(auditSendCoin[0]);
			double coinQty = Double.valueOf(auditSendCoin[1]);
			int coinIDIntro = Integer.parseInt(introlSendCoin[0]);
			double coinQtyIntro = Double.valueOf(introlSendCoin[1]);
			//获取推荐用户信息，检测用户释放已经发放奖励，如果已经发放则不重复发放
			if(!fscore.isFissend() ){
				fscore.setFissend(true);
				bReward = true;
				//检测是否有推荐人
				if(fuser.getfIntroUser_id() != null){
					fintrolUser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid());
					fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
				}
			}else{
				bReward = false;
			}
			
			LOGGER.info(CLASS_NAME + " validateIdentity,coinQty:{}",coinQty);
			String agonmsg = "";
			fuser.setFidentityType(identityType) ;
			fuser.setFidentityNo(identityNo) ;
			fuser.setFrealName(realName) ;
			fuser.setFpostRealValidate(true) ;
			fuser.setFareaCode(address);
			fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
			if(Constant.IS_APPKEY.equals("true") && address.equals("86")){
				fuser.setFhasRealValidate(true);
				fuser.setFhasRealValidateTime(Utils.getTimestamp());
				fuser.setFisValid(true);
				//AGON活动期间不进行其他注册推荐奖励活动
				if(!bAgon){
					if(coinQty >0 && isTrue && bReward){
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coinID);
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(coinQty);
						introlInfo.setFuser(fuser);
						introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_REG);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						Object[] params = new Object[]{coinQty,fvirtualwallet.getFvirtualcointype().getFname()};
						introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
						
						//推荐奖励
						if (fintrolUser!=null && coinIDIntro>0){
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
					
					
				}else{
					//首先推荐人本身需要是参与agon活动的用户,通过KYC1直接获得50AGON
					if(bReward && isTrue && fuser.getFregfrom()!=null && fuser.getFregfrom().equals("agon")){
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), 67);
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+50);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(50d);
						introlInfo.setFuser(fuser);
						introlInfo.setFtype(IntrolInfoTypeEnum.INTROL_REG);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						introlInfo.setFtitle("参与AGON空投活动获得奖励50个AGON");
						agonmsg ="获得AGON空投奖励，请查收！";
						//处理AGON推荐
						if(fintrolUser!=null){
							double introRewardQty = 30d;
							int agoncount = this.utilsService.count(" where fregfrom='agon' and fpostRealValidate =1 and fhasRealValidate=1 and fIntroUser_id.fid="+fintrolUser.getFid(),Fuser.class);
							//按照推荐总数来进行奖励，推荐奖励需要先进行锁仓处理，待审核无误再释放
							//推荐人数<=5，每推荐一人，奖励30AGON
							//10>推荐人数>5，推荐一人，则奖励40AGON
							//推荐人数>10，推荐一人，奖励50AGON
							if(agoncount>10){
								introRewardQty = 50d;
							}else if(agoncount>5){
								introRewardQty = 40d;
							}else{
								introRewardQty = 30d;
							}
							fvirtualwalletIntro = this.frontUserService.findVirtualWalletByUser(fintrolUser.getFid(), 67);
							fvirtualwalletIntro.setFlocked(fvirtualwalletIntro.getFlocked()+introRewardQty);
							introlInfoIntro = new Fintrolinfo();
							introlInfoIntro.setFcreatetime(Utils.getTimestamp());
							introlInfoIntro.setFiscny(false);
							introlInfoIntro.setFqty(introRewardQty);
							introlInfoIntro.setFuser(fintrolUser);
							introlInfoIntro.setFtype(IntrolInfoTypeEnum.INTROL_INTROL);
							introlInfoIntro.setFname(fvirtualwalletIntro.getFvirtualcointype().getFname());
							introlInfoIntro.setFtitle("推荐用户参与AGON空投活动获得奖励"+introRewardQty+"个"+fvirtualwalletIntro.getFvirtualcointype().getFname());
						}
						
					}
				}
			}
			try {
				String ip = getIpAddr(request) ;
				LOGGER.info(CLASS_NAME + " validateIdentity,修改用户信息,实名认证成功");
				this.frontUserService.updateFUser(fuser, LogTypeEnum.User_CERT,ip) ;
				if(getSession(request)!=null && getSession(request).getAttribute("login_user")!=null){
					getSession(request).setAttribute("login_user", fuser) ;
				}
				if(Constant.IS_APPKEY.equals("true")){
				this.userService.updateObj(null, fscore, fintrolUser, fvirtualwallet, introlInfo, fvirtualwalletIntro,introlInfoIntro);
				LOGGER.info(CLASS_NAME + " validateIdentity,修改用户钱包信息成功，增加了奖励金额");
				}
				this.SetSession(fuser,request) ;
			} catch (Exception e) {
				js.accumulate(ErrorCode, 1);
				js.accumulate(Value, "身份证号码已存在!");
				return js.toString();
			}
			if(Constant.IS_APPKEY.equals("true") && address.equals("86")){
				js.accumulate(Value, "证件验证成功"+agonmsg);
			}else{
				js.accumulate(Value, "证件号码提交成功，请提交照片以完成身份认证");
			}
			js.accumulate(ErrorCode, 0);
			return js.toString();
		
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	/**
	 * 邮箱找回登录密码第一步
	 * @param request
	 * @param imgcode
	 * @param idcardno
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public String findPwdByEmailFst(
			HttpServletRequest request
			) throws Exception{
		
		String imgcode = request.getParameter("imgcode");  //图形验证码
		String idcardno = request.getParameter("idcardno");  //证件号码
		String email = request.getParameter("email");  //邮箱
		
		JSONObject jsonObject = new JSONObject() ;
		idcardno = idcardno.toLowerCase();
		if(vcodeValidate(request, imgcode) == false ){
			jsonObject.accumulate(ErrorCode, -1) ;
			jsonObject.accumulate(Value, "请输入正确的图片验证码") ;
			return jsonObject.toString() ;
		}
		
		if(!email.matches(Constant.EmailReg)){
			jsonObject.accumulate(ErrorCode, -1) ;
			jsonObject.accumulate(Value, "邮箱格式错误") ;
			return jsonObject.toString() ;
		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("femail", email) ;
		if(fusers.size()==1){
			Fuser fuser = fusers.get(0) ;
			if(fuser.getFisMailValidate()){
				//验证身份证号码
				if(fuser.getFhasRealValidate() == true ){
					if(idcardno.trim().equals(fuser.getFidentityNo()) == false ){
						jsonObject.accumulate(ErrorCode, -1) ;
						jsonObject.accumulate(Value, "身份证号码有误!") ;
						return jsonObject.toString() ;
					}
				}
				
				String ip = getIpAddr(request) ;
				Emailvalidate ev = this.frontValidateMapService.getMailMap(ip+"_"+SendMailTypeEnum.FindPassword) ;
				if(ev==null || Utils.getTimestamp().getTime()-ev.getFcreateTime().getTime()>5*60*1000L){
					boolean flag = false ;
					try {
						String lcal = RequestContextUtils.getLocale(request).toString();
						String mas=getLocaleMessage(request,null,"mail.title.findpass");
						flag = this.frontValidateService.saveSendFindPasswordMail(ip,fuser,email, lcal,mas) ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(flag){
						jsonObject.accumulate(ErrorCode, 0) ;
						jsonObject.accumulate(Value, "找回密码邮件已经发送，请及时查收") ;
						return jsonObject.toString() ;
					}else{
						jsonObject.accumulate(ErrorCode, -10000) ;
						jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
						return jsonObject.toString() ;
					}
				}else{
					jsonObject.accumulate(ErrorCode, -4) ;
					jsonObject.accumulate(Value, "请求过于频繁，请稍后再试") ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate(ErrorCode, -3) ;
				jsonObject.accumulate(Value, "该邮箱没有通过验证，不能用于找回密码") ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate(ErrorCode, -2) ;
			jsonObject.accumulate(Value, "用户名不存在") ;
			return jsonObject.toString() ;
		}
	}
	
	
	/**
	 * 手机找回登录密码第一步
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String findPwdByPhoneFst(
			HttpServletRequest request
			) throws Exception{
		
		String msgcode = request.getParameter("msgcode");  //手机验证码
		String idcardno = request.getParameter("idcardno");  //证件号码
		String phone = request.getParameter("phone");  //手机号码
		
		String areaCode = "86" ;
		idcardno = idcardno.toLowerCase();
		JSONObject jsonObject = new JSONObject() ;
		
		if(!phone.matches(Constant.PhoneReg)){
			jsonObject.accumulate(ErrorCode, -1) ;
			jsonObject.accumulate(Value, "手机格式错误") ;
			return jsonObject.toString() ;
		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
		
		if(fusers.size()==1){
			Fuser fuser = fusers.get(0) ;
			
			//短信验证码
			boolean validate = validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.FIND_PASSWORD, msgcode) ;
			if(validate == false ){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "短信验证码错误") ;
				return jsonObject.toString() ;
			}
			
			if(fuser.isFisTelephoneBind()){
				//验证身份证号码
				if(fuser.getFhasRealValidate() == true ){
					if(idcardno.trim().equals(fuser.getFidentityNo()) == false ){
						jsonObject.accumulate(ErrorCode, -1) ;
						jsonObject.accumulate(Value, "身份证号码有误!") ;
						return jsonObject.toString() ;
					}
				}
				
				//往session放数据
				MultipleValues values = new MultipleValues() ;
				values.setValue1(fuser.getFid()) ;
				values.setValue2(Utils.getTimestamp()) ;
				request.getSession().setAttribute("resetPasswordByPhone", values) ;
				
				this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.FIND_PASSWORD);
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "验证成功，将跳转到修改密码界面") ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate(ErrorCode, -3) ;
				jsonObject.accumulate(Value, "该手机没有通过验证，不能用于找回密码") ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate(ErrorCode, -2) ;
			jsonObject.accumulate(Value, "用户名不存在") ;
			return jsonObject.toString() ;
		}
	}
	
	
	/**
	 * 邮箱找回登录密码第二步
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String findPwdByEmailSec(
			HttpServletRequest request
			) throws Exception{
		
		String newPassword = request.getParameter("newPassword");  //新密码
		String newPassword2 = request.getParameter("newPassword2");  //重复新密码
		Integer fid = Integer.valueOf(request.getParameter("fid"));  //
		Integer ev_id = Integer.valueOf(request.getParameter("ev_id"));  //
		String newuuid = request.getParameter("newuuid");  //
		
		JSONObject jsonObject = new JSONObject() ;
		
		boolean flag = false ;
		try {
			flag = this.frontValidateService.canSendFindPwdMsg(fid, ev_id, newuuid) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!flag){
			jsonObject.accumulate(ErrorCode, -6) ;
			jsonObject.accumulate(Value, "重置密码页面已经失效") ;
			return jsonObject.toString() ;
		}
		
		//密码
		if(newPassword.length()<6){
			jsonObject.accumulate(ErrorCode, -2) ;
			jsonObject.accumulate(Value, "密码必须6~15位") ;
			return jsonObject.toString() ;
		}
		
		if(!newPassword.equals(newPassword2)){
			jsonObject.accumulate(ErrorCode, -3) ;
			jsonObject.accumulate(Value, "两次输入密码不一样") ;
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(fid) ;
		
		if(Utils.MD5(newPassword,fuser.getSalt()).equals(fuser.getFtradePassword())){
			jsonObject.accumulate(ErrorCode, -4) ;
			jsonObject.accumulate(Value, "登陆密码不能与交易密码相同!") ;
			return jsonObject.toString() ;
		}
		
		boolean updateFlag = false ;
		fuser.setFloginPassword(Utils.MD5(newPassword,fuser.getSalt())) ;
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFUser(fuser,LogTypeEnum.User_RESET_PWD,ip) ;
			
			updateFlag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(updateFlag){
			jsonObject.accumulate(ErrorCode, 0) ;
			jsonObject.accumulate(Value, "登录密码重置成功") ;
			return jsonObject.toString() ;
		}else{
			jsonObject.accumulate(ErrorCode, -10000) ;
			jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
			return jsonObject.toString() ;
		}
	}
	
	
	/**
	 * 手机找回登录密码第二步
	 * @param request
	 * @param newPassword
	 * @param newPassword2
	 * @return
	 * @throws Exception
	 */
	public String findPwdByPhoneSec(
			HttpServletRequest request
			) throws Exception{
		
		String newPassword = request.getParameter("newPassword");  //新密码
		String newPassword2 = request.getParameter("newPassword2");  //重复新密码
		
		JSONObject jsonObject = new JSONObject() ;
		
		boolean isValidate = false ;
		Fuser fuser = null ;
		Object resetPasswordByPhone = request.getSession().getAttribute("resetPasswordByPhone") ;
		if(resetPasswordByPhone!=null ){
			MultipleValues values = (MultipleValues)resetPasswordByPhone ;
			Integer fuserid = (Integer)values.getValue1() ;
			Timestamp time = (Timestamp)values.getValue2() ;
			
			if(Utils.timeMinus(Utils.getTimestamp(), time)<300){
				fuser = this.frontUserService.findById(fuserid) ;
				isValidate = true ;
			}
		}
		
		if(!isValidate){
			jsonObject.accumulate(ErrorCode, -6) ;
			jsonObject.accumulate(Value, "重置密码页面已经失效") ;
			return jsonObject.toString() ;
		}
		
		//密码
		if(newPassword.length()<6){
			jsonObject.accumulate(ErrorCode, -2) ;
			jsonObject.accumulate(Value, "密码必须6~15位") ;
			return jsonObject.toString() ;
		}
		
		if(!newPassword.equals(newPassword2)){
			jsonObject.accumulate(ErrorCode, -3) ;
			jsonObject.accumulate(Value, "两次输入密码不一样") ;
			return jsonObject.toString() ;
		}
		
		
		if(Utils.MD5(newPassword,fuser.getSalt()).equals(fuser.getFtradePassword())){
			jsonObject.accumulate(ErrorCode, -4) ;
			jsonObject.accumulate(Value, "登陆密码不能与交易密码相同!") ;
			return jsonObject.toString() ;
		}
		
		boolean updateFlag = false ;
		fuser.setFloginPassword(Utils.MD5(newPassword,fuser.getSalt())) ;
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFUser(fuser,LogTypeEnum.User_RESET_PWD,ip) ;
			
			updateFlag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(updateFlag){
			request.getSession().removeAttribute("resetPasswordByPhone") ;
			
			jsonObject.accumulate(ErrorCode, 0) ;
			jsonObject.accumulate(Value, "登录密码重置成功") ;
			return jsonObject.toString() ;
		}else{
			jsonObject.accumulate(ErrorCode, -10000) ;
			jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
			return jsonObject.toString() ;
		}
	}
	/**
	 * 
	 * usdt场外交易提交购买单
	 * @param request
	 * @param money 金额
	 * @param sbank 系统银行账号信息
	 * @return
	 * @throws Exception
	 */
	public String usdtManual(
			HttpServletRequest request		
			) throws Exception{
		double money=Double.parseDouble(request.getParameter("money"));
		int sbank=Integer.parseInt(request.getParameter("sbank"));
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
			jsonObject.accumulate(Value, getLocaleMessage(request,params,"json.account.minrecharge"));
			return jsonObject.toString();
		}
		
		if(money > maxRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			Object[] params = new Object[]{maxRecharge};
			jsonObject.accumulate(Value, getLocaleMessage(request,params,"json.account.maxrecharge"));
			return jsonObject.toString();
		}
		
		Systembankinfo systembankinfo = this.frontBankInfoService.findSystembankinfoById(sbank) ;
		LOGGER.info(CLASS_NAME + " usdtManual,查询银行账户信息systembankinfo:{}", systembankinfo);
		if(systembankinfo==null || systembankinfo.getFstatus()==SystemBankInfoEnum.ABNORMAL ){
			LOGGER.info(CLASS_NAME + " usdtManual,查询银行账户信息为空或账户停用");
			//银行账号停用
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.nobank"));
			return jsonObject.toString();
		}
		
		Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
		fcapitaloperation.setFamount(money) ;
		fcapitaloperation.setSystembankinfo(systembankinfo) ;
		fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
		fcapitaloperation.setFtype(CapitalOperationTypeEnum.USDT_IN) ;
		fcapitaloperation.setFuser(this.getAppFuser(this.curLoginToken)) ;
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
	
	
	/**
	 * usdt场外交易页面展示信息
	 * @param request
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	public String rechargeUsdt(
			HttpServletRequest request
		
			) throws Exception{		
		JSONObject jsonObject = new JSONObject() ;
		
		//人民币随机尾数
		int randomMoney = (new Random().nextInt(80)+11) ;
		//系统银行账号
		List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo() ;
		Systembankinfo bankInfo = systembankinfos.get(0);
	//	jsonObject.accumulate("randomMoney",randomMoney) ;
		
		JSONObject bankInfojson= new JSONObject() ;
		bankInfojson.accumulate("fid", bankInfo.getFid());
		bankInfojson.accumulate("fbankName", bankInfo.getFbankName());
		bankInfojson.accumulate("fownerName", bankInfo.getFownerName());
		bankInfojson.accumulate("fbankAddress", bankInfo.getFbankAddress());
		bankInfojson.accumulate("fbankNumber", bankInfo.getFbankNumber());
		jsonObject.accumulate("bankInfo",bankInfojson) ;
		
		//record
		Fuser fuser = this.getAppFuser(this.curLoginToken) ;
		
	
		//最小最大充值金额
		double minRecharge = Double.parseDouble(this.frontConstantMapService.get("minrechargecny").toString().trim()) ;
		double maxRecharge = Double.parseDouble(this.frontConstantMapService.get("maxrechargecny").toString().trim()) ;
		jsonObject.accumulate("minRecharge", minRecharge) ;
		jsonObject.accumulate("maxRecharge", maxRecharge) ;
		
		Map<String,String> bankTypes = new HashMap<String,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(String.valueOf(i).toString(),BankTypeEnum.getEnumString(i)) ;
			}			
		}
		jsonObject.accumulate("bankTypes", JSONObject.fromObject(bankTypes)) ;
		
		
		return jsonObject.toString() ;
	}
	
	/**
	 * usdt场外交易购买时第二步我已完成付款
	 * @param request
	 * @param bank 银行信息ID
	 * @param account 数量
	 * @param payee
	 * @param phone 电话号码
	 * @param desc 记录的id
	 * @return
	 * @throws Exception
	 */
	public String rechargeUsdtSubmit(
			HttpServletRequest request			
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		int desc=Integer.parseInt(request.getParameter("desc"));
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(desc) ;
		if(fcapitaloperation==null ||fcapitaloperation.getFuser().getFid() !=this.getAppFuser(this.curLoginToken).getFid() ){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.illoperation"));
			return jsonObject.toString();
		}
		
		if(fcapitaloperation.getFstatus() != CapitalOperationInStatus.NoGiven
				|| fcapitaloperation.getFtype() != CapitalOperationTypeEnum.USDT_IN){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.networkanomaly"));
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
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.networkanomaly"));
		}
		
		return jsonObject.toString();
	}
	
	
	
	
	/**
	 * 设置提现银行卡信息
	 * param account 银行卡号
     * param phoneCode 手机验证码
     * param totpCode 谷歌验证码
     * param openBankType 银行类型
     * param address 分行名称
     * param prov 银行所属省份
     * param city 银行所属城市
     * param dist 银行所属区局
     * param payeeAddr 开户姓名
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	public String updateOutAddress(
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		int openBankType=Integer.parseInt(request.getParameter("openBankType"));
		String account=request.getParameter("account");
		String phoneCode=request.getParameter("phoneCode");
		String totpCode=request.getParameter("totpCode");
		String address=request.getParameter("address");
		String prov=request.getParameter("prov");
		String city=request.getParameter("city");
		String dist=request.getParameter("dist");
		String payeeAddr=request.getParameter("payeeAddr");
		Fuser fuser = this.getAppFuser(this.curLoginToken) ;
		
		if(fuser.getFregType()==0){
			//注册类型为手机的账户，必须要绑定手机号码
			if(!fuser.isFisTelephoneBind() || null==fuser.getFtelephone() || fuser.getFtelephone().equals("")){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.bindinfo.ntp")) ;
				return jsonObject.toString() ;
			}
		}else{
			//对注册类型为邮箱的账户，必须要绑定邮箱地址
			if(null==fuser.getFemail() || fuser.getFemail().equals("")){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.bindinfo")+"(error code:nex)") ;
				return jsonObject.toString() ;
			}
		}
		
		String code=actionSecurityCheck(request, fuser, false, false, false, null, true, phoneCode, totpCode, MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, LogTypeEnum.User_CNY, "新增银行卡异常", Value);
		if(!code.equals("ok")){
			return code;
		}
		
		address = HtmlUtils.htmlEscape(address);
		account = HtmlUtils.htmlEscape(account);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		totpCode = HtmlUtils.htmlEscape(totpCode);
		prov = HtmlUtils.htmlEscape(prov);
		city = HtmlUtils.htmlEscape(city);
		dist = HtmlUtils.htmlEscape(dist);
		payeeAddr = fuser.getFrealName();
		
		String last = prov+city;
		if(!dist.equals("0")){
			last = last+dist;
		}
		
		if(account.length() < 10){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.bankaccill")) ;
			return jsonObject.toString();
		}
		
		if(address.length() > 300){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.addrtolong")) ;
			return jsonObject.toString();
		}
		
		if(last.length() > 50){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.illoperation")) ;
			return jsonObject.toString();
		}
		
		if(!fuser.getFrealName().equals(payeeAddr)){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, "银行卡账号名必须与您的实名认证姓名一致") ;
			return jsonObject.toString();
		}
		
		String bankName = BankTypeEnum.getEnumString(openBankType);
		if(bankName == null || bankName.trim().length() ==0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.bankmistake")) ;
			return jsonObject.toString();
		}
		
		int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fbankType="+openBankType+" and fbankNumber='"+account+"' and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
		if(count>0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.bankcardexist")) ;
			return jsonObject.toString();
		}
		
		String ip = getIpAddr(request) ;

		//成功
		try {
			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
			fbankinfoWithdraw.setFbankNumber(account) ;
			fbankinfoWithdraw.setFbankType(openBankType) ;
			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
			fbankinfoWithdraw.setFname(bankName) ;
			fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			fbankinfoWithdraw.setFaddress(last);
			fbankinfoWithdraw.setFothers(address);
			fbankinfoWithdraw.setFuser(fuser);
			this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.success")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_CNY, "新增银行卡成功");
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.networkanomaly")) ;
			this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_CNY, "新增银行卡失败");
		}finally{
			this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
		}
		
		return jsonObject.toString() ;
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
	public String withdrawUsdtSubmit(
			HttpServletRequest request
			
			) throws Exception{
		
		String tradePwd=request.getParameter("tradePwd");
		double withdrawBalance=Double.parseDouble(request.getParameter("withdrawBalance"));
		String phoneCode=request.getParameter("phoneCode");
		String totpCode=request.getParameter("totpCode");
		int withdrawBlank=Integer.parseInt(request.getParameter("withdrawBlank"));
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
			jsonObject.accumulate(Value, getLocaleMessage(request,params,"json.account.minwithdrawal"));
			return jsonObject.toString() ;
		}
		
		if(withdrawBalance>max_double){
			//提现金额不能大于指定值
			jsonObject.accumulate("code", -1) ;
			Object[] params = new Object[]{max_double};
			jsonObject.accumulate(Value, getLocaleMessage(request,params,"json.account.maxwithdrawal"));
			return jsonObject.toString() ;
		}
		
		
		Fuser fuser = this.getAppFuser(this.curLoginToken) ;
		
		String code=actionSecurityCheck(request, fuser, false, true, true, tradePwd, true, phoneCode, totpCode, MessageTypeEnum.USDT_TIXIAN, LogTypeEnum.User_USDT, "USDT生成提币码", Value);
		if(!code.equals("ok")){
			return code;
		}
		
		Fvirtualwallet fwallet = this.frontUserService.findUSDTWalletByUser(fuser.getFid());
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(withdrawBlank);
		if(fbankinfoWithdraw == null || fbankinfoWithdraw.getFuser().getFid() != fuser.getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.illwithdrawal")) ;
			return jsonObject.toString() ;
		}
		
		if(fwallet.getFtotal()<withdrawBalance){
			//资金不足
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.notenough")) ;
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
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.succash")) ;
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.networkanomaly")) ;
		}
		
		return jsonObject.toString() ;
	}
	
/**
 * 场外交易USDT提现
 * @param request
 * @param exchangecode 提币码
 * @return
 * @throws Exception
 */
	public String exchangeUsdtSubmit(
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		String exchangecode=request.getParameter("exchangecode");
		Fuser fuser = this.getAppFuser(this.curLoginToken) ;
		
		if(fuser.getFtradePassword()==null){
			//没有设置交易密码
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.settranpwd")) ;
			return jsonObject.toString() ;
		}
		
		if(!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()){
			//没有绑定谷歌或者手机
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.account.bindinfo")) ;
			return jsonObject.toString() ;
		}
		
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationByRemark(exchangecode) ;
		
		if(fcapitaloperation==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.exchange.noopera")) ;
			return jsonObject.toString() ;
		}
		if(fcapitaloperation.getFstatus()!=1){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value,getLocaleMessage(request,null,"json.exchange.repeatsubmit")) ;
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
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.succash")) ;
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.networkanomaly")) ;
		}
		
		return jsonObject.toString() ;
	}
	
	
/**
 * 取消订单
 * @param request
 *  @param id 订单id
 * @return
 * @throws Exception
 */
	public String cancelRechargeUsdtSubmit(
			HttpServletRequest request
			) throws Exception{
		int id=Integer.parseInt(request.getParameter("id"));
		Fuser fuser = this.frontUserService.findById(this.getAppFuser(this.curLoginToken).getFid()) ;
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
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate("code", 0) ;
		return jsonObject.toString() ;
	}
	
	
/**
 * 查询订单列表
 * @param currentPage
 * @param type  订单类型0  购买记录 1 兑换记录
 * @param operId订单id
 * @param request
 * @return
 * @throws Exception
 */
	public String recordUsdt(
			
			HttpServletRequest request
			) throws Exception{
		
	     int currentPage=Integer.parseInt(request.getParameter("currentPage"));
		int type=Integer.parseInt(request.getParameter("type"));
		int operId=0;
		if(request.getParameter("operId")!=null&&!"".equals(request.getParameter("operId"))&&request.getParameter("operId").trim().length()>0){
			 operId=Integer.parseInt(request.getParameter("operId"));
		}

		JSONObject jsonObject = new JSONObject() ;
		
		//type 0  购买记录
		//type 1 兑换记录
		if(type!=0){
			type=1;
		}
		
		Fuser fuser = this.frontUserService.findById(this.getAppFuser(this.curLoginToken).getFid()) ;
		Fcapitaloperation fcapitaloperation = null;
		//编号不为空，则尝试提取订单
		if (operId>0){
			fcapitaloperation = this.frontAccountService.findCapitalOperationById(operId) ;
		}
		JSONObject fcapitaloperationJson = new JSONObject() ;
		//如果获取到了订单，则需要根据订单类型来提取数据
		if (fcapitaloperation!=null){
			if (fcapitaloperation.getFtype()==CapitalOperationTypeEnum.USDT_IN){
				type=0;
			}else{
				type=1;
			}
			
			operId = fcapitaloperation.getFid();
			
			fcapitaloperationJson.accumulate("fid", fcapitaloperation.getFid());
			fcapitaloperationJson.accumulate("fremittanceType", fcapitaloperation.getFremittanceType());
			fcapitaloperationJson.accumulate("fcreateTime", fcapitaloperation.getFcreateTime());
			fcapitaloperationJson.accumulate("fstatus", fcapitaloperation.getFstatus());
			fcapitaloperationJson.accumulate("fAccount", fcapitaloperation.getfAccount());
			fcapitaloperationJson.accumulate("ffees", fcapitaloperation.getFfees());
			fcapitaloperationJson.accumulate("famount", fcapitaloperation.getFamount());
			fcapitaloperationJson.accumulate("fremark", fcapitaloperation.getFremark());
		}
		
		//獲得千12條交易記錄
		String param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.USDT_IN+" order by fid desc";
		if (type==1){
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.USDT_OUT+" order by fid desc";
		}
		List<Fcapitaloperation> fcapitaloperations = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param, true) ;
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", param.toString());
		String url = "/exchange/recordUsdt.html?type="+type+'&';
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		//如果订单为空，则获取用户的最新一条订单
		if (fcapitaloperation==null && fcapitaloperations.size()>0){
			fcapitaloperation = fcapitaloperations.get(0);
			operId = fcapitaloperation.getFid();
		}
		
		
		
		
		jsonObject.accumulate("pagin", pagin) ;
		jsonObject.accumulate("type", type) ;
		
		JSONArray fcapitaloperationsArray = new JSONArray() ;
		
		for(Fcapitaloperation fv:fcapitaloperations){
			JSONObject fcapitaloperationsJson = new JSONObject() ;
			fcapitaloperationsJson.accumulate("fid", fv.getFid());
			fcapitaloperationsJson.accumulate("fremittanceType", fv.getFremittanceType());
			fcapitaloperationsJson.accumulate("fcreateTime", fv.getFcreateTime());
			fcapitaloperationsJson.accumulate("fstatus", fv.getFstatus());
			fcapitaloperationsJson.accumulate("fAccount", fv.getfAccount());
			fcapitaloperationsJson.accumulate("ffees", fv.getFfees());
			fcapitaloperationsJson.accumulate("famount", fv.getFamount());
			fcapitaloperationsJson.accumulate("fremark", fv.getFremark());
			fcapitaloperationsArray.add(fcapitaloperationsJson);
		}
		
				
		jsonObject.accumulate("fcapitaloperations", fcapitaloperationsArray) ;
		jsonObject.accumulate("fcapitaloperation", fcapitaloperationJson) ;
		jsonObject.accumulate("operId", operId) ;
		//modelAndView.addObject("fuser",fuser) ;
		jsonObject.accumulate("currentPage",currentPage) ;
		jsonObject.accumulate("cur_page",currentPage) ;

		int totalPage = totalCount/maxResults +(totalCount%maxResults==0?0:1) ;
		jsonObject.accumulate("totalPage",totalPage) ;	
		jsonObject.accumulate("code", 0) ;
		
		return jsonObject.toString() ;
	}
	/**
	 * 场外交易USDT提现页面展示信息
	 * @param request
	 * @param currentPage 页数
	 * @return
	 * @throws Exception
	 */
	public String withdrawUsdt(
		
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.getAppFuser(this.curLoginToken) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> fbankinfoWithdraws =this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		JSONArray jsonarray=new JSONArray();
		for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
			int l = fbankinfoWithdraw.getFbankNumber().length();
			fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l-4, l));
						
			JSONObject fbankinfoWithdrawsjson = new JSONObject() ;
			fbankinfoWithdrawsjson.accumulate("fid", fbankinfoWithdraw.getFid());
			fbankinfoWithdrawsjson.accumulate("fname", fbankinfoWithdraw.getFname());
			fbankinfoWithdrawsjson.accumulate("fbankNumber", fbankinfoWithdraw.getFbankNumber());
			jsonarray.add(fbankinfoWithdrawsjson);
			
		}
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		jsonObject.accumulate("bankTypes", bankTypes) ;
		
		Fvirtualwallet fwallet = this.frontUserService.findUSDTWalletByUser(this.getAppFuser(this.curLoginToken).getFid());
		request.getSession().setAttribute("fwallet", fwallet) ;
		
		double fee = this.withdrawFeesService.findFfee(fwallet.getFvirtualcointype().getFid(),fuser.getFscore().getFlevel()).getFfee();
		jsonObject.accumulate("fee", fee) ;
		
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		jsonObject.accumulate("isBindGoogle", isBindGoogle) ;
		jsonObject.accumulate("isBindTelephone", isBindTelephone) ;
		
		
		jsonObject.accumulate("fbankinfoWithdraws",jsonarray) ;
		
		
		return jsonObject.toString() ;
	}
	
	
/**
 * 获取虚拟币充值页面信息
 * @param request
 * @param currentPage 页数
 * @param symbol 虚拟币ID
 * @return
 * @throws Exception
 */
	public String rechargeBtc(
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		/*int currentPage=Integer.parseInt(request.getParameter("currentPage"));*/
		int symbol=Integer.parseInt(request.getParameter("symbol"));
		Fuser fuser = this.getAppFuser(this.curLoginToken) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE 
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal 
				|| !fvirtualcointype.isFisrecharge()){
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <> "+CoinTypeEnum.FB_CNY_VALUE +" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				jsonObject.accumulate("code", -1) ;
				
				jsonObject.accumulate(Value, "该币种不支持充值") ;
				return jsonObject.toString() ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		Fvirtualaddress fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
		
		//最近十次充值记录
		/*String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_IN
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/rechargeBtc.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		jsonObject.accumulate("pagin", pagin) ;*/
		//modelAndView.addObject("fvirtualcaptualoperations",fvirtualcaptualoperations) ;
		jsonObject.accumulate("fvirtualcointype",JSONObject.fromObject(fvirtualcointype)) ;
		jsonObject.accumulate("symbol", symbol) ;
		
		JSONObject fvirtualaddressJson = new JSONObject() ;
		if(fvirtualaddress!=null){
			fvirtualaddressJson.accumulate("fid", fvirtualaddress.getFid()) ;
			fvirtualaddressJson.accumulate("fvirtualcointype", JSONObject.fromObject(fvirtualaddress.getFvirtualcointype())) ;
			fvirtualaddressJson.accumulate("fadderess", fvirtualaddress.getFadderess()) ;
		}
		
		jsonObject.accumulate("fvirtualaddress", fvirtualaddressJson) ;
		jsonObject.accumulate("code", 0) ;

		return jsonObject.toString() ;
	}
	
	
	//获取生成虚拟币提笔地址
		public String getVirtualAddress(
				HttpServletRequest request
				) throws Exception{
			JSONObject jsonObject = new JSONObject() ;
			int symbol=Integer.parseInt(request.getParameter("symbol"));
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
			int count = this.adminService.getAllCount("Fvirtualaddress", filter);
			if(count >0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.addressnogain")) ;
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
							jsonObject.accumulate("code", -1) ;
							jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.noaddress")) ;
							return jsonObject.toString() ;
						}
						
						addr = fpool.getFaddress() ;
						if(addr==null || addr.trim().equalsIgnoreCase("null") || addr.trim().equals("")){
							jsonObject.accumulate("code", -1) ;
							jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.noaddress")) ;
							return jsonObject.toString() ;
						}
						for (Fvirtualcointype fvcoin : coins) {
							Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
							fvirtualaddress.setFadderess(addr) ;
							fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
							fvirtualaddress.setFuser(fuser) ;
							fvirtualaddress.setFvirtualcointype(fvcoin) ;
							fpool.setFstatus(1) ;
							this.poolService.updateObj(fpool) ;
							this.virtualaddressService.saveObj(fvirtualaddress) ;
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
					if(fpool == null){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.noaddress")) ;
						return jsonObject.toString() ;
					}
					
					String address = fpool.getFaddress() ;
					if(address==null || address.trim().equalsIgnoreCase("null") || address.trim().equals("")){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.noaddress")) ;
						return jsonObject.toString() ;
					}
					
					Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
					fvirtualaddress.setFadderess(address) ;
					fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
					fvirtualaddress.setFuser(fuser) ;
					fvirtualaddress.setFvirtualcointype(coin) ;
					fpool.setFstatus(1) ;
					this.poolService.updateObj(fpool) ;
					this.virtualaddressService.saveObj(fvirtualaddress) ;
					jsonObject.accumulate("address", fvirtualaddress.getFadderess()) ;
				}
			}else{
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate(Value, getLocaleMessage(request,null,"json.account.nosupportrecharge")) ;
				return jsonObject.toString() ;
			}
			
			jsonObject.accumulate("code", 0) ;
			return jsonObject.toString();
		}
		
		
/**
 * 帮助中心和费率申明
 * @param request
 * @param id
 * @return
 * @throws Exception
 */
		public String aboutindex(HttpServletRequest request)
				throws Exception {
			String lcal = RequestContextUtils.getLocale(request).toString();

			int id=Integer.parseInt(request.getParameter("id"));
			JSONObject jsonobject = new JSONObject();
		//	String lcal = RequestContextUtils.getLocale(request).toString();
			Fabout fabout = this.frontOthersService.findFabout(id);
			if (fabout != null) {				
				jsonobject.accumulate("fabout", JSONObject.fromObject(fabout));
			}

			JSONArray abouts = new JSONArray();

			String helpkey = "helpType_CN";
			

			String[] args = this.frontConstantMapService.get(helpkey).toString()
					.split("#");
			for (int i = 0; i < args.length; i++) {
				JSONObject keyValues = new JSONObject();
				keyValues.accumulate("key",i);
				keyValues.accumulate("name",args[i]);
				String filter = "where ftype_cn='" + args[i] + "'";
				
				List<Fabout> curabout = this.aboutService.list(0, 0, filter, false);
				JSONArray jsoncurabout = new JSONArray();

					for (Fabout fabt : curabout) {	
						jsoncurabout.add(JSONObject.fromObject(fabt));
					}

				
				keyValues.accumulate("value",jsoncurabout);
				abouts.add(keyValues);
			}
			jsonobject.accumulate("abouts", abouts);
			jsonobject.accumulate("code", 0) ;
			
			return jsonobject.toString();
		}
		
		/**
		 * 站内消息列表展示
		 * @param request
		 * @param type 是否已读 1：未读，2：已读
		 * @param currentPage 页数
		 * @return
		 * @throws Exception
		 */
		public String usermessage(
				HttpServletRequest request
				) throws Exception {
			
			
			int type=Integer.parseInt(request.getParameter("type"));
			int currentPage=Integer.parseInt(request.getParameter("currentPage"));
			JSONObject jsonobject = new JSONObject();
			if(type != 1 && type !=2){
				type =1;
			}
			
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			int maxResult = Constant.AppRecordPerPage ;
			String filter = " where freceiver.fid="+fuser.getFid()+" and fstatus="+type+" order by fid desc" ;
			int total = this.adminService.getAllCount("Fmessage", filter);
			
			int totalPage = total / maxResult + (total % maxResult == 0 ? 0 :1) ;
			currentPage = currentPage <0?0:currentPage ;
			//currentPage = currentPage > totalPage?totalPage:currentPage ;
			
			List<Fmessage> messages = this.messageService.list((currentPage-1)*maxResult, maxResult, filter,true) ;
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/user/message.html?type="+type+"&") ;
			JSONArray messagesArray=new JSONArray();
			for(Fmessage fmessage:messages){
				JSONObject js = new JSONObject();
				js.accumulate("ftitle", fmessage.getFtitle()) ;
				js.accumulate("fcreateTime", fmessage.getFcreateTime()) ;
				js.accumulate("fid", fmessage.getFid()) ;
				js.accumulate("fcontent", fmessage.getFcontent()) ;
				messagesArray.add(js);
			}
			
             jsonobject.accumulate("messages", messagesArray) ;
	         jsonobject.accumulate("pagin", pagin) ;
	         jsonobject.accumulate("type", type) ;
	         jsonobject.accumulate("code", 0) ;
			return jsonobject.toString() ;
		}
		
		
		/**
		 * 阅读消息
		 * @param request
		 * @param id 消息id
		 * @return
		 * @throws Exception
		 */
		public String readMessage(
				HttpServletRequest request
				) throws Exception {
			JSONObject jsonObject = new JSONObject();
			int id=Integer.parseInt(request.getParameter("id"));
			int userid = this.getAppFuser(this.curLoginToken).getFid() ;
			
			Fmessage msg = this.messageService.findById(id);
			if(msg != null && msg.getFreceiver().getFid() == userid){
				if(msg.getFstatus() == MessageStatusEnum.NOREAD_VALUE){
					msg.setFstatus(MessageStatusEnum.READ_VALUE);
					this.messageService.updateObj(msg);
				}
			}
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("title", msg.getFtitle());
			jsonObject.accumulate("content", msg.getFcontent());
			return jsonObject.toString();
		}
		

		public String cancelWithdrawusdt(
				HttpServletRequest request
		
				) throws Exception{
			JSONObject jsonObject = new JSONObject();
			int id=Integer.parseInt(request.getParameter("id"));
			Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
			if(fcapitaloperation!=null
					&&fcapitaloperation.getFuser().getFid() ==this.getAppFuser(this.curLoginToken).getFid()
					&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.USDT_OUT
					&&fcapitaloperation.getFstatus()==CapitalOperationOutStatus.WaitForOperation){
				try {
					this.frontAccountService.updateCancelWithdrawUsdt(fcapitaloperation, this.frontUserService.findById(this.getAppFuser(this.curLoginToken).getFid())) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate(Value, "取消成功");
			return jsonObject.toString();
		}
		
		
		/**
		 * 移动端网页身份识别接口
		 * @param request
		 * @return
		 */
		public String faceValidateReq(HttpServletRequest request) {
			
			JSONObject jsonObject = new JSONObject() ;
			
			String userId = request.getParameter("userId");
			
			//获得一个用于实名验证的 token（token唯一且只能使用一次）
			String getTokenUrl = "https://api.megvii.com/faceid/lite/get_token";
			//用户完成或取消验证后网页跳转的目标URL。（回调方法为Post）
			String return_url = Constant.faceid_returnUrl;
			//用户完成验证、取消验证、或验证超时后，由FaceID服务器请求客户服务器的URL。（推荐为HTTPS页面，如果为HTTP则用户需要通过签名自行校验数据可信性，回调方法为Post）
			String notify_url = Constant.faceid_notifyUrl;
			//客户业务流水号，该号必须唯一。
//			String biz_no = String.valueOf(new Date().getTime());
			String biz_no = String.valueOf(userId);
			//本参数影响验证流程中是否存在身份证拍摄环节：如果为“1”，则可选择包含身份证拍摄；如果为“0”，验证流程中将没有身份证拍摄。
			String comparison_type = "1";
			//传递参数“0”，“1”，“2”或“3”，表示获取用户身份证信息的方法。
			//0：不拍摄身份证，而是通过 idcard_name / idcard_number 参数传入；
			//1：仅拍摄身份证人像面，可获取人像面所有信息；
			//2： 拍摄身份证人像面和身份证国徽面，可获取身份证所有信息；
			//3：不拍摄身份证，但会要求用户在界面上输入身份证号和姓名。
			String idcard_mode = "2";
			Fuser fuser = this.getAppFuser(this.curLoginToken) ;
			
			try {
				com.alibaba.fastjson.JSONObject jsonParam = new com.alibaba.fastjson.JSONObject();
				jsonParam.put("getTokenUrl", getTokenUrl);
				jsonParam.put("api_key", Constant.faceid_apiKey);
				jsonParam.put("api_secret", Constant.faceid_apiSecret);
				jsonParam.put("return_url", return_url);
				jsonParam.put("notify_url", notify_url);
				jsonParam.put("biz_no", biz_no);
				jsonParam.put("comparison_type", comparison_type);
				jsonParam.put("idcard_mode", idcard_mode);
				jsonParam.put("id_number", fuser.getFidentityNo());
	            
				String proxyServiceUrl = "https://faceid.powex.pro/user/faceValidate.html";            
	            String str = HttpClientUtils.sendPost(proxyServiceUrl, jsonParam);

				com.alibaba.fastjson.JSONObject jsonResult = com.alibaba.fastjson.JSONObject.parseObject(str);
	            
				//获取token
				String token = jsonResult.getString("token");
				if(StringUtils.isBlank(token)) {
					jsonObject.accumulate("code", "-10001");
					jsonObject.accumulate(Value, "调用API异常");
					return jsonObject.toString();
				}
				
				//保存userFaceID记录
				FuserFaceID userFaceId = new FuserFaceID();
				userFaceId.setUserId(Integer.valueOf(biz_no));
				userFaceId.setToken(token);
				userFaceId.setExpiredTime(Timestamp.valueOf(jsonResult.getString("expired_time")));
				userFaceId.setStatus(0);  // 0 
				userFaceId.setCreateTime(Utils.getTimestamp());
				userFaceId.setUpdateTime(Utils.getTimestamp());
				frontUserFaceIDService.addUserFaceID(userFaceId);
				
				//转跳到第三方身份验证页面
				String faceValidateUrl = "https://api.megvii.com/faceid/lite/do";
				
				jsonObject.accumulate("code", "0");
				jsonObject.accumulate("faceValidateUrl", faceValidateUrl+"?token="+token);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("调用API异常");
				jsonObject.accumulate("code", "-10001");
				jsonObject.accumulate(Value, "调用API异常");
				return jsonObject.toString();
			}
			
			return jsonObject.toString();
		}
		
		
		
		
		
		/**
		 * 验证交易密码
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String checkTradePwd(HttpServletRequest request) throws Exception{
			try {
				JSONObject jsonObject = new JSONObject() ;
				
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				
				String ip = getIpAddr(request) ;
				
				String tradePwd = request.getParameter("tradePwd").trim();
				
				if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
					jsonObject.accumulate(ErrorCode, -2) ;
					jsonObject.accumulate(Value,"请先设置交易密码") ;
					return jsonObject.toString() ;
				}
				
				int trade_limit = frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				if(trade_limit<=0){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!") ;
					this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC,"交易密码校验"+"：频繁密码验证");
					return jsonObject.toString() ;
				}
				
				try {
					if(!fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt()))){
						jsonObject.accumulate(ErrorCode, -3) ;
						Object[] params = new Object[]{trade_limit};
						jsonObject.accumulate(Value, "交易密码有误，您还有"+trade_limit+"次机会");
						frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
						return jsonObject.toString() ;
					}else if(trade_limit<Constant.ErrorCountLimit){
						frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObject.accumulate(ErrorCode, -3) ;
					Object[] params = new Object[]{trade_limit};
					jsonObject.accumulate(Value, "交易密码有误，您还有"+trade_limit+"次机会");
					frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					return jsonObject.toString() ; 
				}
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "交易密码验证成功") ;
				return jsonObject.toString() ;
				
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		

		/**
		 * 虚拟币划账
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String drawAccountsSubmit(HttpServletRequest request) throws Exception{
			try {
				//type  1:钱包划转到系统        2：系统划转到钱包
				int type = Integer.parseInt(request.getParameter("type")) ;
				double amount = Utils.getDouble(Double.parseDouble(request.getParameter("amount")), 6) ;
				String tradePwd = request.getParameter("tradePwd").trim();
				int coinType = Integer.parseInt(request.getParameter("coinType")) ;
				
				LOGGER.info(CLASS_NAME + " drawAccountsSubmit 虚拟币划账开始");
				LOGGER.info(CLASS_NAME + " drawAccountsSubmit 入参amount:{},type:{}", amount, type);
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				
				//转账金额小于等于0，则是作为非法操作
				if(amount<=0) {
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "非法操作") ;
					return jsonObject.toString() ;
				}
				
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;

				String scode=actionSecurityCheck(request,fuser,false,false,true,tradePwd,false,null,null,MessageTypeEnum.TRANSFER_CODE,LogTypeEnum.User_BTC,"划账异常","msg");
				if(!scode.equals("ok")) {
					return scode;
				}
				
				
				String drawaccountsCfg = frontConstantMapService.getString("drawaccounts");
				if(StringUtils.isBlank(drawaccountsCfg)) {
					jsonObject.accumulate(ErrorCode , -11) ;
					jsonObject.accumulate(Value, "系统参数未配置，请联系管理员") ;
					return jsonObject.toString() ;
				}
				
				String[] drawaccounts = drawaccountsCfg.split("#");
				int drawaccountsUserId = Integer.parseInt(drawaccounts[0]);
				String[] drawaccountsCoinId = drawaccounts[1].split(",");
				
				if(fuser.getFid()==drawaccountsUserId) {
					jsonObject.accumulate(ErrorCode , -12) ;
					jsonObject.accumulate(Value, "项目方不允许划转操作") ;
					return jsonObject.toString() ;
				}
				
				if(!Arrays.asList(drawaccountsCoinId).contains(String.valueOf(coinType))) {
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "非法操作") ;
					return jsonObject.toString() ;
				}
				
				Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findById(coinType);
				
				LOGGER.info(CLASS_NAME + " drawAccountsSubmit 根据symbol:{}查询虚拟币信息结束", fvirtualcointype.getfSymbol());
				if(fvirtualcointype==null 
						|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
						|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "非法操作") ;
					return jsonObject.toString() ;
				}
				
				Fvirtualwallet fvirtualwalletFrom = null ;
				Fvirtualwallet fvirtualwalletTo = null;
				if(1 == type) {           //钱包划转到系统 
					fvirtualwalletFrom = this.frontUserService.findVirtualWalletByUser(drawaccountsUserId, fvirtualcointype.getFid());
					
					if(null == fvirtualwalletFrom) {
						jsonObject.accumulate(ErrorCode, -13) ;
						jsonObject.accumulate(Value, "系统账户不存在，请联系管理员") ;
						return jsonObject.toString() ;
					}
					//余额不足
					if(fvirtualwalletFrom.getFtotal()<amount){
						jsonObject.accumulate(ErrorCode , -14) ;
						jsonObject.accumulate(Value, "系统账户余额不足") ;
						return jsonObject.toString() ;
					}
					
					fvirtualwalletTo = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
				} else if(2 == type) {          //系统划转到钱包
					
					if(fvirtualcointype.getFtype()==CoinTypeEnum.FB_USDT_VALUE){
						jsonObject.accumulate(ErrorCode, -1) ;
						jsonObject.accumulate(Value, "非法操作") ;
						return jsonObject.toString() ;
					}
					
					fvirtualwalletTo = this.frontUserService.findVirtualWalletByUser(drawaccountsUserId, fvirtualcointype.getFid());
					
					if(null == fvirtualwalletTo) {
						jsonObject.accumulate(ErrorCode, -13) ;
						jsonObject.accumulate(Value, "系统账户不存在，请联系管理员") ;
						return jsonObject.toString() ;
					}
					
					fvirtualwalletFrom = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
					//余额不足
					if(fvirtualwalletFrom.getFtotal()<amount){
						jsonObject.accumulate(ErrorCode , -15) ;
						jsonObject.accumulate(Value, "用户账户余额不足") ;
						return jsonObject.toString() ;
					}
					
				}
				
				
				String ip = getIpAddr(request) ;
				try{
					LOGGER.info(CLASS_NAME + " drawAccountsSubmit 保存划账流水，修改用户钱包信息开始");
					this.frontDrawAccountsService.updateDrawAccountsSubmit(fvirtualwalletFrom, fvirtualwalletTo, fvirtualcointype, amount, type) ;
					LOGGER.info(CLASS_NAME + " drawAccountsSubmit 保存划账流水，修改用户钱包信息结束");
					jsonObject.accumulate(ErrorCode , 0) ;
					jsonObject.accumulate(Value, "划账成功，请稍后查看您的账户信息") ;
					this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "划账成功："+amount+fvirtualcointype.getfShortName());
				
				}catch(Exception e){
					LOGGER.info(CLASS_NAME + " drawAccountsSubmit 划账异常exception:{}", e.getMessage());
					e.printStackTrace() ;
					jsonObject.accumulate(ErrorCode , -10000) ;
					jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
					this.frontUserService.updateUserLog(fuser, ip, LogTypeEnum.User_BTC, "提币申请失败："+amount+fvirtualcointype.getfShortName());
				}finally{
					this.frontValidateMapService.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.TRANSFER_CODE);
				}
				LOGGER.info(CLASS_NAME + " drawAccountsSubmit 虚拟币划账结束");
				return jsonObject.toString() ;
				
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		
		
		/**
		 * 获取用户指定币种的账户余额
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String GetAccountsByCoin(HttpServletRequest request) throws Exception{
			try {
				JSONObject jsonObject = new JSONObject() ;
				
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				
				int coinType = Integer.parseInt(request.getParameter("coinType")) ;
				
				String drawaccountsCfg = frontConstantMapService.getString("drawaccounts");
				if(StringUtils.isBlank(drawaccountsCfg)) {
					jsonObject.accumulate(ErrorCode , -2) ;
					jsonObject.accumulate(Value, "系统参数未配置，请联系管理员") ;
					return jsonObject.toString() ;
				}
				
				String[] drawaccounts = drawaccountsCfg.split("#");
				int drawaccountsUserId = Integer.parseInt(drawaccounts[0]);
				String[] drawaccountsCoinId = drawaccounts[1].split(",");
				
				if(!Arrays.asList(drawaccountsCoinId).contains(String.valueOf(coinType))) {
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "非法操作") ;
					return jsonObject.toString() ;
				}
				
				Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findById(coinType);
				
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coinType) ;
				
				jsonObject.accumulate("shortName", fvirtualcointype.getfShortName()) ;
				jsonObject.accumulate("available", new BigDecimal(fvirtualwallet.getFtotal()).setScale(2, BigDecimal.ROUND_DOWN)) ;
				jsonObject.accumulate("frozen", new BigDecimal(fvirtualwallet.getFfrozen()).setScale(2, BigDecimal.ROUND_DOWN)) ;
				jsonObject.accumulate("total", new BigDecimal(fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen()).setScale(2, BigDecimal.ROUND_DOWN)) ;
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "成功") ;
				return jsonObject.toString() ;
				
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		
		/**
		 * 获取系统配置划转的币种
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String GetDrawaccountsCoinlist(HttpServletRequest request) throws Exception{
			try {
				JSONObject jsonObject = new JSONObject() ;
				
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				
				String drawaccountsCfg = frontConstantMapService.getString("drawaccounts");
				if(StringUtils.isBlank(drawaccountsCfg)) {
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "系统参数未配置，请联系管理员") ;
					return jsonObject.toString() ;
				}
				
				String[] drawaccounts = drawaccountsCfg.split("#");
				int drawaccountsUserId = Integer.parseInt(drawaccounts[0]);
				String[] drawaccountsCoinId = drawaccounts[1].split(",");
				
				List<KeyValueVo> KeyValueVoList = new ArrayList<KeyValueVo>();
				for (int i = 0; i < drawaccountsCoinId.length; i++) {
					Fvirtualcointype cointype = this.frontVirtualCoinService.findById(Integer.valueOf(drawaccountsCoinId[i]));
					
					KeyValueVo keyValue = new KeyValueVo();
					keyValue.setKey(cointype.getFid().toString());
					keyValue.setValue(cointype.getfShortName());
					KeyValueVoList.add(keyValue);
				}
				
				jsonObject.accumulate("cointypeList", KeyValueVoList) ;
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "成功") ;
				return jsonObject.toString() ;
				
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		
		/**
		 * 获取最新成交记录
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String GetEntrustlogs(HttpServletRequest request) throws Exception {
			try{
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "获取最新成交记录") ;
				
				int symbol = Integer.parseInt(request.getParameter("symbol")) ;
				Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
				
				String entrustSuccessString = this.frontCacheService.getEntrustSuccessMap(ftrademapping.getFid());
				List<String> successEntrustsList = com.alibaba.fastjson.JSONObject.parseArray(entrustSuccessString, String.class);
				JSONArray data = new JSONArray() ;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
				
				if (successEntrustsList != null){
					for (int i = 0; i < successEntrustsList.size() && i < 20 ; i++) {
						JSONObject item = new JSONObject() ;
						String fentrustString = successEntrustsList.get(i);
						String[] fentrustParam = fentrustString.substring(1, fentrustString.length()-1).split(",");
						
						item.accumulate("time", sdf.format(new Timestamp(Long.valueOf(fentrustParam[0])))) ;
						item.accumulate("id", Integer.valueOf(fentrustParam[1])) ;
						item.accumulate("price", Utils.getDouble(Double.valueOf(fentrustParam[2]), ftrademapping.getFcount1())) ;
						item.accumulate("amount", Utils.getDouble(Double.valueOf(fentrustParam[3]), ftrademapping.getFcount2())) ;
						item.accumulate("en_type", Integer.valueOf(fentrustParam[4])==EntrustTypeEnum.BUY?"ask":"bid") ;
						item.accumulate("type", Integer.valueOf(fentrustParam[4])==EntrustTypeEnum.BUY?"买":"卖") ;
						data.add(item);
					}
				}
				jsonObject.accumulate("trades", data) ;
				return jsonObject.toString() ;
			}catch(Exception e){
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
		}
		
		
		/**
		 * 获取虚拟币地址(新)
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public String GetVirtualCoinAddress(HttpServletRequest request) throws Exception {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			try{
				Fuser fuser = this.getAppFuser(this.curLoginToken) ;
				int symbol = Integer.parseInt(request.getParameter("symbol"));
				String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
				
				List<Fvirtualaddress> virtualaddressList = virtualaddressService.list(0, 0, filter, false);
				Fvirtualaddress fvirtualaddress = null;
				if(null != virtualaddressList && virtualaddressList.size() > 0) {
					fvirtualaddress = virtualaddressList.get(0);
					jsonObject.accumulate("address", fvirtualaddress.getFadderess()) ;
				} else {
					Fvirtualcointype coin = this.virtualCoinService.findById(symbol);
					if(coin.isFisrecharge()){
						//代币和子资产都与主币共用地址,parentCid不为0的都是代币
						if (coin.getParentCid()>0){
							String filters = " where fstatus=1 and fisrecharge=1 and (parentCid=" + coin.getParentCid()
										   + " or fid=" + coin.getParentCid() +") and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
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
										jsonObject.accumulate(ErrorCode, -1) ;
										jsonObject.accumulate(Value, "地址库地址不足，请联系管理员") ;
										return jsonObject.toString() ;
									}else {
										addr = String.valueOf(fuser.getFid());
									}
								}else {
									addr = fpool.getFaddress() ;
									if(addr==null || addr.trim().equalsIgnoreCase("null") || addr.trim().equals("")){
										jsonObject.accumulate(ErrorCode, -1) ;
										jsonObject.accumulate(Value, "地址库地址不足，请联系管理员") ;
										return jsonObject.toString() ;
									}
								}
								
								for (Fvirtualcointype fvcoin : coins) {
									fvirtualaddress = new Fvirtualaddress() ;
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
									fvirtualaddress = new Fvirtualaddress() ;
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
									jsonObject.accumulate(ErrorCode, -1) ;
									jsonObject.accumulate(Value, "地址库地址不足，请联系管理员") ;
									return jsonObject.toString() ;
								}else {
									address = String.valueOf(fuser.getFid());
								}
							}else {
								address = fpool.getFaddress() ;
								if(address==null || address.trim().equalsIgnoreCase("null") || address.trim().equals("")){
									jsonObject.accumulate(ErrorCode, -1) ;
									jsonObject.accumulate(Value, "地址库地址不足，请联系管理员") ;
									return jsonObject.toString() ;
								}
							}
							
							fvirtualaddress = new Fvirtualaddress() ;
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
						jsonObject.accumulate(ErrorCode, -2) ;
						jsonObject.accumulate(Value, "该虚拟币不支持充值") ;
						return jsonObject.toString() ;
					}
				}
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "成功") ;
				return jsonObject.toString();
	 		}catch(Exception e){
	 			e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
	 		}
		}

}
