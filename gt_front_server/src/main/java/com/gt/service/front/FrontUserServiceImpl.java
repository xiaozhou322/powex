package com.gt.service.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.AuditStatusEnum;
import com.gt.Enum.BankInfoStatusEnum;
import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.LogTypeEnum;
import com.gt.Enum.SendMailTypeEnum;
import com.gt.Enum.ValidateMailStatusEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.dao.EmailvalidateDAO;
import com.gt.dao.FapiDAO;
import com.gt.dao.FbankinfoDAO;
import com.gt.dao.FbankinfoWithdrawDAO;
import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FlogDAO;
import com.gt.dao.FmessageDAO;
import com.gt.dao.FpoolDAO;
import com.gt.dao.FscoreDAO;
import com.gt.dao.FsystemargsDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.FusersettingDAO;
import com.gt.dao.FvalidateemailDAO;
import com.gt.dao.FvirtualaddressDAO;
import com.gt.dao.FvirtualaddressWithdrawDAO;
import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.PproductDAO;
import com.gt.dao.PsystemconfigDAO;
import com.gt.entity.CooperateOrgModel;
import com.gt.entity.Emailvalidate;
import com.gt.entity.Fapi;
import com.gt.entity.Fbankinfo;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fentrust;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Flog;
import com.gt.entity.Fmessage;
import com.gt.entity.Fscore;
import com.gt.entity.Fsystemargs;
import com.gt.entity.Fuser;
import com.gt.entity.Fusersetting;
import com.gt.entity.Fvalidateemail;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Pproduct;
import com.gt.entity.Psystemconfig;
import com.gt.util.Constant;
import com.gt.util.ConstantKeys;
import com.gt.util.ShareCodeUtil;
import com.gt.util.Utils;
import com.gt.util.redis.RedisCacheUtil;
import com.gt.util.redis.RedisKey;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Service("frontUserService")
public class FrontUserServiceImpl implements FrontUserService {
	private static final Logger log = LoggerFactory.getLogger(FrontUserServiceImpl.class);
	@Autowired
	private FuserDAO fuserDAO ;
	@Autowired
	private EmailvalidateDAO emailvalidateDAO ;
	@Autowired
	private FvalidateemailDAO validateemailsDAO ;
//	@Autowired
//	private TaskList taskList ;
	@Autowired
	private FbankinfoDAO fbankinfoDAO ;
	@Autowired
	private FscoreDAO fscoreDAO ;
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FvirtualaddressDAO fvirtualaddressDAO ;
	@Autowired
	private FvirtualaddressWithdrawDAO fvirtualaddressWithdrawDAO ;
	@Autowired
	private FbankinfoWithdrawDAO fbankinfoWithdrawDAO ;
	@Autowired
	private FsystemargsDAO fsystemargsDAO ;
	@Autowired
	private FapiDAO fapiDAO ;
	@Autowired
	private FrontValidateMapService frontValidateMapServiceImpl ;
	@Autowired
	private FlogDAO flogDAO ;
	@Autowired
	private FpoolDAO fpoolDAO ;
	@Autowired
	private FusersettingDAO fusersettingDAO ;
	@Autowired
	private FrontConstantMapService frontConstantMapServiceImpl ;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;
	@Autowired
	private FmessageDAO fmessageDAO;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	
	@Autowired
	private PproductDAO pproductDAO;
	
	public boolean nickValidated(String name) {
		boolean flag = false ;
		if(name!=null && !name.trim().equals("")){
			List<Fuser> list = fuserDAO.findByProperty("floginName", name) ;
			if(list.size()>0){
				flag = true ;
			}
		}
		return flag ;
	}
	
	public Fuser saveRegister(Fuser fuser) {
		try {
			this.fuserDAO.save(fuser) ;
			//设置邀请码
			fuser.setFuserNo(ShareCodeUtil.idToCode(fuser.getFid()));
			this.updateFuser(fuser);
			
			//用户基本设置信息表
			Fusersetting fusersetting = new Fusersetting() ;
			fusersetting.setFisAutoReturnToAccount(false) ;
			fusersetting.setFuser(fuser) ;
			fusersetting.setFticketQty(0d);
			fusersetting.setFsendDate(null);
			fusersetting.setFissend(false);
			this.fusersettingDAO.save(fusersetting) ;
			fuser.setFusersetting(fusersetting) ;
			
			List<Fvirtualcointype> fvirtualcointypes = (List)this.frontConstantMapServiceImpl.get("allCoins");
			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
				Fvirtualwallet fvirtualwallet = new Fvirtualwallet() ;
				fvirtualwallet.setFtotal(0F) ;
				fvirtualwallet.setFfrozen(0F) ;
				fvirtualwallet.setFvirtualcointype(fvirtualcointype) ;
				fvirtualwallet.setFuser(fuser) ;
				fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
				this.fvirtualwalletDAO.save(fvirtualwallet) ;
			}
//			//接受充值的虚拟地址
//			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
//				
//				if(!fvirtualcointype.isFIsWithDraw() && !fvirtualcointype.isFisrecharge()){
//					continue ;
//				}
//				
//				Fpool fpool = this.fpoolDAO.getOneFpool(fvirtualcointype) ;
//				String address = fpool.getFaddress() ;
//				Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
//				fvirtualaddress.setFadderess(address) ;
//				fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
//				fvirtualaddress.setFuser(fuser) ;
//				fvirtualaddress.setFvirtualcointype(fvirtualcointype) ;
//				if(address==null || address.trim().equalsIgnoreCase("null") || address.trim().equals("")){
//					throw new RuntimeException() ;
//				}
//				
//				fpool.setFstatus(1) ;
//				this.fpoolDAO.attachDirty(fpool) ;
//				
//				this.fvirtualaddressDAO.save(fvirtualaddress) ;
//			}
//			//对外提现的虚拟地址
//			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
//				FvirtualaddressWithdraw fvirtualaddressWithdraw = new FvirtualaddressWithdraw() ;
//				fvirtualaddressWithdraw.setFadderess(null) ;
//				fvirtualaddressWithdraw.setFcreateTime(Utils.getTimestamp()) ;
//				fvirtualaddressWithdraw.setFuser(fuser) ;
//				fvirtualaddressWithdraw.setFvirtualcointype(fvirtualcointype) ;
//				this.fvirtualaddressWithdrawDAO.save(fvirtualaddressWithdraw) ;
//			}
			
//			//充值的银行账号
//			Fbankinfo fbankinfo = new Fbankinfo() ;
//			fbankinfo.setFcreateTime(Utils.getTimestamp()) ;
//			fbankinfo.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
//			fbankinfo.setFuser(fuser) ;
//			this.fbankinfoDAO.save(fbankinfo) ;
			
//			//提现的银行账号
//			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw() ;
//			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
//			fbankinfoWithdraw.setFstatus(BankInfoWithdrawStatusEnum.NORMAL_VALUE) ;
//			fbankinfoWithdraw.setFuser(fuser) ;
//			this.fbankinfoWithdrawDAO.save(fbankinfoWithdraw) ;
			
			//积分
			Fscore fscore = new Fscore() ;
			fscore.setFlevel(1) ;
			fscore.setFscore(0) ;
			fscore.setFgroupqty(0);
			fscore.setFtreeqty(0);
			fscore.setFkillQty(0);
			fscore.setFissend(false);
			this.fscoreDAO.save(fscore) ;
			fuser.setFscore(fscore) ;
		} catch (Exception e) {
			e.printStackTrace() ;
			throw new RuntimeException() ;
		}
		
		return fuser ;
	}
	
	public Fuser updateCheckLogin(Fuser fuser,String ip,boolean ismail){
		Fuser flag = null ;
		try{
			Map<String, Object> map = new HashMap<String, Object>() ;
			map.put("floginName", fuser.getFloginName().toLowerCase()) ;
			map.put("floginPassword", Utils.MD5(fuser.getFloginPassword(),fuser.getSalt())) ;
			
			List<Fuser> list = this.fuserDAO.findByMap(map) ;
			if(list.size()>0){
				flag = list.get(0) ;
			}
//			if(ismail == false ){
//				map.put("ftelephone", fuser.getFemail().toLowerCase()) ;
//				map.put("floginPassword", Utils.MD5(fuser.getFloginPassword())) ;
//				
//				List<Fuser> list = this.fuserDAO.findByMap(map) ;
//				if(list.size()>0){
//					flag = list.get(0) ;
//				}
//			}else{
//				map.put("femail", fuser.getFemail().toLowerCase()) ;
//				map.put("floginPassword", Utils.MD5(fuser.getFloginPassword())) ;
//				
//				List<Fuser> list = this.fuserDAO.findByMap(map) ;
//				if(list.size()>0){
//					flag = list.get(0) ;
//				}
//
//			}
			
			if(flag!=null){
				Flog flog = new Flog() ;
				flog.setFcreateTime(Utils.getTimestamp()) ;
				flog.setFkey1(String.valueOf(flag.getFid())) ;
				flog.setFkey2(flag.getFloginName()) ;
				flog.setFkey3(ip) ;
				flog.setFtype(LogTypeEnum.User_LOGIN) ;
				this.flogDAO.save(flog) ;
				
				//更新登陆时间
				flag.setFlastLoginIp(ip);
				flag.setFlastLoginTime(Utils.getTimestamp());
				this.fuserDAO.attachDirty(flag);
			}
			
		}catch(Exception e){
			e.printStackTrace() ;
			fuser = null ;
			throw new RuntimeException() ;
		}
		return flag ;
	}
	
	
	//取到所有非人民币和USDT的虚拟币
	public LinkedHashMap<Fvirtualcointype, Fvirtualwallet> findVirtualWallet(int fuid){
		LinkedHashMap<Fvirtualcointype, Fvirtualwallet> map = new LinkedHashMap<Fvirtualcointype, Fvirtualwallet>();
		List<Fvirtualwallet> fvirtualwallets = this.fvirtualwalletDAO.find(fuid, VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_USDT_VALUE) ;
		
		//TODO 获取所有权证产品
		String filter = " where auditStatus = " + AuditStatusEnum.auditPass;
		
		List<Pproduct> productList = pproductDAO.list(0, 0, filter, false);
		
		for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
			Fvirtualcointype fct =fvirtualcointypeDAO.findById(fvirtualwallet.getFvirtualcointype().getFid());
			if(fct!=null){
				//此处判断该币种是否支持兑换
				for (Pproduct product : productList) {
					if(fct.getFid() == product.getConvertCointype().getFid() || 
							fct.getFid() == product.getCoinType().getFid()) {
						fct.setFcanConvert(true);
						fvirtualwallet.setFvirtualcointype(fct);
					}
				}
				map.put(fct, fvirtualwallet) ;
			}
		}
		if(map.isEmpty()) {
			return null;
		}
		return map ;
	}
	
	public Fvirtualwallet findVirtualWalletByUser(int fuid,int virtualCoinId){
		Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fuid, virtualCoinId) ;
		if (fvirtualwallet != null) {
			fvirtualwallet.getFuser().getFid();
		}
		return fvirtualwallet ;
	}
	
	//会员人民币钱包
	public Fvirtualwallet findWalletByUser(int fuid){
		Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findWallet(fuid) ;
		return fvirtualwallet ;
	}
	
	//会员USDT钱包
	public Fvirtualwallet findUSDTWalletByUser(int fuid){
		Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findUSDTWallet(fuid) ;
		return fvirtualwallet ;
	}
	
	public Fuser findById(int id){
		Fuser fuser = this.fuserDAO.findById(id) ;
		return fuser ;
	}
	
	public int findIntroUserCount(Fuser fuser){
		return this.fuserDAO.findByProperty("fIntroUser_id.fid", fuser.getFid()).size() ;
	}
	
	public Fbankinfo findUserBankInfo(int uid){
		Fbankinfo fbankinfo = this.fbankinfoDAO.findUserBankInfo(uid) ;
		return fbankinfo ;
	}
	
	public FbankinfoWithdraw findByIdWithBankInfos(int id){
		return this.fbankinfoWithdrawDAO.findById(id);
	}
	
	public boolean isEmailExists(String email) {
		boolean flag = false ;
		List<Fuser> list = this.fuserDAO.findByProperty("femail", email) ;
		flag = list.size()>0 ;
		return flag ;
	}
	
	public boolean isTelephoneExists(String telephone){
		boolean flag = false ;
		List<Fuser> list = this.fuserDAO.findByProperty("ftelephone", telephone) ;
		flag = list.size()>0 ;
		return flag ;
	}
	
	//安全设置修改48小时之内，不允许进行提币提现操作
	public boolean isSafeTime(Integer uid) throws Exception{
		int safecount = this.flogDAO.findSafeCount(String.valueOf(uid));
		if(safecount>0){
			return true;
		}else{
			return false;
		}
	}
	
	//用户日志记录操作
	public void updateUserLog(Fuser fuser,String ip,Integer logType,String description){
		if(logType >0){//操作记录。无记录填负数
			Flog flog = new Flog() ;
			flog.setFcreateTime(Utils.getTimestamp()) ;
			flog.setFkey1(String.valueOf(fuser.getFid())) ;
			flog.setFkey2(fuser.getFloginName()) ;
			flog.setFkey3(ip) ;
			flog.setFkey4(description);
			flog.setFtype(logType) ;
			//安全设置操作成功，都需要进行敏感操作冷却
			if(logType>1 && logType<=10){
				flog.setFkey5("SAFE") ;
			}
			this.flogDAO.save(flog) ;
		}
	}
	
	public void updateFUser(Fuser fuser,int logType,String ip){
		this.fuserDAO.attachDirty(fuser) ;
		if(logType >0){//操作记录。无记录填负数
			Flog flog = new Flog() ;
			flog.setFcreateTime(Utils.getTimestamp()) ;
			flog.setFkey1(String.valueOf(fuser.getFid())) ;
			flog.setFkey2(fuser.getFloginName()) ;
			flog.setFkey3(ip) ;
			flog.setFtype(logType) ;
			//安全设置操作成功，都需要进行敏感操作冷却
			if(logType>1 && logType<=10){
				flog.setFkey5("SAFE") ;
			}
			this.flogDAO.save(flog) ;
		}
	}
	
	public void updateFuser(Fuser fuser){
		this.fuserDAO.attachDirty(fuser) ;
	}
	
	public void updateFuser(Fscore fscore,Fvirtualwallet fvirtualwallet){
		try {
			this.fscoreDAO.attachDirty(fscore);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public void updateFuser(Fuser fuser,Fintrolinfo introlInfo,
			Fscore fintrolScore,Fscore fscore){
		try {
			if(fuser != null){
				this.fuserDAO.attachDirty(fuser) ;
			}
			if(introlInfo != null){
				this.introlinfoDAO.save(introlInfo);
			}
			if(fintrolScore != null){
				this.fscoreDAO.attachDirty(fintrolScore);
			}
			if(fscore != null){
				this.fscoreDAO.attachDirty(fscore);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	
	public List<Fuser> findUserByProperty(String key,Object value){
		return this.fuserDAO.findByProperty(key, value) ;
	}
	public int getMaxId(){
		return this.fuserDAO.getMaxId() ;
	}
	public void addBankInfo(Fbankinfo fbankinfo,Fuser fuser){
		try {
			Fbankinfo example = new Fbankinfo() ;
			example.setFuser(fuser) ;
			example.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			List<Fbankinfo> fbankinfos = this.fbankinfoDAO.findByExample(example) ;
			for (Fbankinfo fbankinfo2 : fbankinfos) {
				fbankinfo2.setFstatus(BankInfoStatusEnum.ABNORMAL_VALUE) ;
				this.fbankinfoDAO.attachDirty(fbankinfo2) ;
			}
			this.fbankinfoDAO.save(fbankinfo) ;
		} catch (Exception e) {
		     throw new RuntimeException();
		}
	}
	
	public void updateBankInfoWithdraw(FbankinfoWithdraw fbankinfoWithdraw){
		try {
			fbankinfoWithdrawDAO.save(fbankinfoWithdraw) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateDelBankInfoWithdraw(FbankinfoWithdraw fbankinfoWithdraw){
		try {
			fbankinfoWithdrawDAO.delete(fbankinfoWithdraw);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public boolean deleteAllUser() {
		List<Fuser> fusers = this.fuserDAO.findAll() ;
		for (Fuser fuser : fusers) {
			this.fuserDAO.delete(fuser) ;
		}
		return true ;
	}
	
	public String getSystemArgs(String key){
		String value = null ;
		List<Fsystemargs> list = this.fsystemargsDAO.findByFkey(key) ;
		if(list.size()>0){
			value = list.get(0).getFvalue() ;
		}
		return value ;
	}

	
	public Fapi addFapi(Fuser fuser,String label,boolean istrade,boolean iswithdraw,String ip){
		Fapi fapi = new Fapi() ;
		fapi.setFpartner(Utils.UUID()) ;
		fapi.setFsecret(Utils.randomString(36).toUpperCase()) ;
		fapi.setFuser(fuser) ;
		fapi.setLabel(label);
		fapi.setFcreatetime(Utils.getTimestamp());
		fapi.setFistrade(istrade);
		fapi.setFiswithdraw(iswithdraw);
		if(ip.length()>4){
			fapi.setFip(ip);
		}else{
			fapi.setFip("*");
		}
		this.fapiDAO.save(fapi) ;
		
		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.fuserDAO.attachDirty(fuser) ;
		
		return fapi ;
	}
	
	public void deleteFapi(Fapi fapi){
		this.fapiDAO.delete(fapi) ;
	}
	
	public Fapi findFapiById(int id){
		return this.fapiDAO.findById(id) ;
	}
	
	public List<FbankinfoWithdraw> findFbankinfoWithdrawByFuser(int firstResult, int maxResults, String filter,boolean isFY){
		return this.fbankinfoWithdrawDAO.list(firstResult, maxResults, filter, isFY);
	}
	public FbankinfoWithdraw findFbankinfoWithdraw(int fid){
		return this.fbankinfoWithdrawDAO.findById(fid) ; 
	}
	public boolean saveValidateEmail(Fuser fuser,String email,boolean force,String msg, String lcal) throws RuntimeException{
		boolean flag = false ;
		try {
			if(!force){
				//半小时内只能发送一次
				Emailvalidate ev = this.frontValidateMapServiceImpl.getMailMap(fuser.getFid()+"_"+SendMailTypeEnum.ValidateMail) ;
				if(ev!=null && Utils.getTimestamp().getTime()-ev.getFcreateTime().getTime()<30*60*1000L){
					flag = false ;
					return flag ;
				}
			}
			
			
			//发送激活邮件
			String UUID = Utils.UUID() ;
			//发送给用户的邮件
			Fvalidateemail validateemails = new Fvalidateemail() ;
			
//			String lcal = RequestContextUtils.getLocale(request).toString();
			String webnamekey = ConstantKeys.WEB_NAME;
			if(lcal.equals("zh_CN")){
				webnamekey=webnamekey+"_CN";
			}
			validateemails.setFtitle(this.getSystemArgs(webnamekey) + msg) ;
			
			String mailvalkey  = ConstantKeys.mailValidateContent;
			if(lcal.equals("zh_CN")){
				mailvalkey=mailvalkey+"_CN";
			}
			validateemails.setFcontent(
					this.getSystemArgs(mailvalkey)
					.replace("#firstLevelDomain#", this.getSystemArgs(ConstantKeys.firstLevelDomain))
					.replace("#url#", Constant.Domain+"validate/mail_validate.html?uid="+fuser.getFid()+"&&uuid="+UUID)
					.replace("#fulldomain#", this.getSystemArgs(ConstantKeys.fulldomain))
					.replace("#englishName#", this.getSystemArgs(ConstantKeys.englishName))) ;
			validateemails.setFcreateTime(Utils.getTimestamp()) ;
			validateemails.setFstatus(ValidateMailStatusEnum.Not_send) ;
			validateemails.setFuser(fuser) ;
			validateemails.setEmail(email);
			this.validateemailsDAO.save(validateemails) ;
			
			//验证并对应到用户的UUID
			Emailvalidate emailvalidate = new Emailvalidate() ;
			emailvalidate.setFcreateTime(Utils.getTimestamp()) ;
			emailvalidate.setFuser(fuser) ;
			emailvalidate.setFuuid(UUID) ;
			emailvalidate.setFmail(email);
			emailvalidate.setType(SendMailTypeEnum.ValidateMail) ;
			this.emailvalidateDAO.save(emailvalidate) ;
			
			//加入邮件队列
//			this.taskList.returnMailList(validateemails.getFid()) ;
			rabbitTemplate.convertAndSend("email.add", validateemails.getFid());
			
			this.frontValidateMapServiceImpl.putMailMap(fuser.getFid()+"_"+SendMailTypeEnum.ValidateMail, emailvalidate) ;
			
			flag = true ;
			return flag ;
		} catch (Exception e) {
			throw new RuntimeException() ;
		}
		
	}
	
	public void updateDisabledApi(Fuser fuser){
		try {
			Fapi fapi = fuser.getFapi() ;
			fuser.setFapi(null) ;
			this.fuserDAO.attachDirty(fuser) ;
			
			if(fapi!=null){
				this.fapiDAO.delete(fapi) ;
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public List<Fapi> findFapiByProperty(String key,Object value){
		return this.fapiDAO.findByProperty(key, value) ;
	}

	public Fuser findFuserByFapi(Fapi fapi){
		List<Fuser> fusers = this.fuserDAO.findByProperty("fapi.fid", fapi.getFid()) ;
		return fusers.get(0) ;
	}
	
	public Fuser findByQQlogin(String openId){
		List<Fuser> fusers = this.fuserDAO.findByProperty("qqlogin", openId) ;
		if(fusers.size()>0){
			return fusers.get(0) ;
		}else{
			return null ;
		}
	}
	
	public Fscore findFscoreById(int id){
		return this.fscoreDAO.findById(id) ;
	}
	
	public Fusersetting findFusersetting(int fid){
		return this.fusersettingDAO.findById(fid) ;
	}
	
	public void updateFusersetting(Fusersetting fusersetting){
		this.fusersettingDAO.attachDirty(fusersetting);
	}
	
	public void updateCleanScore(){
		this.fusersettingDAO.updateCleanScore() ;
	}
	
	public void updateSendLog(Fvirtualwallet fvirtualwallet,Fmessage message) {
		try {
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.fmessageDAO.save(message);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateShoppinglog(Fvirtualwallet fvirtualwallet,Fintrolinfo info) {
		try {
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.introlinfoDAO.save(info);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * 将OTC上线商户放入redis
	 * @param userId
	 */
	public void addOTCOnlineUserMap(Integer userId){
		
		String onlineUserStr = redisCacheUtil.getCacheString(RedisKey.getOTCOnlineUserList());
		List<Integer> onlineUserList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(onlineUserStr)) {
			JSONArray jsonArr = JSONArray.fromObject(onlineUserStr);
			
			if(jsonArr.size()>0){
				// JSONArray 转 java List
				onlineUserList = JSONArray.toList(jsonArr, new ArrayList<Integer>(), new JsonConfig());
				if(!onlineUserList.contains(userId)) {
					onlineUserList.add(userId);
				}
			} else {
				onlineUserList.add(userId);
			}
		} else {
			onlineUserList.add(userId);
		}
		
		redisCacheUtil.setCacheObject(RedisKey.getOTCOnlineUserList(), JSONArray.fromObject(onlineUserList).toString());
	}
	
	
	/**
	 * 将OTC下线商户移出redis
	 * @param userId
	 */
	public void removeOTCOnlineUserMap(Integer userId){
		
		String onlineUserStr = redisCacheUtil.getCacheString(RedisKey.getOTCOnlineUserList());
		List<Integer> onlineUserList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(onlineUserStr)) {
			JSONArray jsonArr = JSONArray.fromObject(onlineUserStr);
			
			if(jsonArr.size()>0){
				// JSONArray 转 java List
				onlineUserList = JSONArray.toList(jsonArr, new ArrayList<Integer>(), new JsonConfig());
				onlineUserList.remove(userId);
			}
		}
		
		redisCacheUtil.setCacheObject(RedisKey.getOTCOnlineUserList(), JSONArray.fromObject(onlineUserList).toString());
	}
	
	
	/**
	 * 从redis获取OTC上线用户
	 */
	public List<Integer> getOTCOnlineUserList(){
		String onlineUserStr = redisCacheUtil.getCacheString(RedisKey.getOTCOnlineUserList());
		
		List<Integer> onlineUserList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(onlineUserStr)) {
			JSONArray jsonArr = JSONArray.fromObject(onlineUserStr);
			
			if(jsonArr.size()>0){
				// JSONArray 转 java List
				onlineUserList = JSONArray.toList(jsonArr, new ArrayList<Integer>(), new JsonConfig());
			}
		}
		
		return onlineUserList;
	}
	
}
