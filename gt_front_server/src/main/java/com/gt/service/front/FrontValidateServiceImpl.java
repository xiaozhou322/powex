package com.gt.service.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gt.Enum.CountLimitTypeEnum;
import com.gt.Enum.SendMailTypeEnum;
import com.gt.Enum.ValidateMailStatusEnum;
import com.gt.Enum.ValidateMessageStatusEnum;
import com.gt.comm.MessageValidate;
import com.gt.dao.EmailvalidateDAO;
import com.gt.dao.FcountlimitDAO;
import com.gt.dao.FsystemargsDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.FvalidateemailDAO;
import com.gt.dao.FvalidatemessageDAO;
import com.gt.entity.Emailvalidate;
import com.gt.entity.Fcountlimit;
import com.gt.entity.Fsystemargs;
import com.gt.entity.Fuser;
import com.gt.entity.Fvalidateemail;
import com.gt.entity.Fvalidatemessage;
import com.gt.util.Constant;
import com.gt.util.ConstantKeys;
import com.gt.util.Utils;

@Service("frontValidateService")
public class FrontValidateServiceImpl implements FrontValidateService {
	
	private static final Logger log = LoggerFactory.getLogger(FrontValidateServiceImpl.class);
	
	private static final String CLASS_NAME = FrontValidateServiceImpl.class.getSimpleName();
	
	@Autowired
	protected EmailvalidateDAO emailvalidateDAO ;
	@Autowired
	protected FvalidateemailDAO validateemailsDAO ;
	@Autowired
	private FuserDAO fuserDAO ;
	@Autowired
	private FcountlimitDAO fcountlimitDAO ;
	@Autowired
	private FvalidatemessageDAO fvalidatemessageDAO ;
//	@Autowired
//	private TaskList taskList ;
	@Autowired
	private FsystemargsDAO fsystemargsDAO ;
	@Autowired
	private FrontValidateMapService frontValidateMapServiceImpl ;
	@Autowired
	private FrontConstantMapService frontConstantMapServiceImpl ;
	@Autowired
	private FvalidateemailDAO fvalidateemailDAO ;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	
	//用户注册邮件验证
	public boolean updateMailValidate(int uid,String uuid){
		boolean flag = false ;
		try{
			Emailvalidate emailvalidate = this.emailvalidateDAO.findByUidUuid(uid, uuid,SendMailTypeEnum.ValidateMail) ;
			emailvalidate.setFvalidateTime(Utils.getTimestamp()) ;
			this.emailvalidateDAO.attachDirty(emailvalidate) ;
				
			Fuser fuser = emailvalidate.getFuser() ;
			if(!fuser.getFisMailValidate()){
				fuser.setFisMailValidate(true) ;
				fuser.setFemail(emailvalidate.getFmail());
				this.fuserDAO.attachDirty(fuser) ;
				flag = true ;
			}
			return flag ;
		}catch(Exception e){
			e.printStackTrace() ;
			throw new RuntimeException() ;
		}
	}
	
	public String getSystemArgs(String key){
		String value = null ;
		List<Fsystemargs> list = this.fsystemargsDAO.findByFkey(key) ;
		if(list.size()>0){
			value = list.get(0).getFvalue() ;
		}
		return value ;
	}
	
	
	//发送短信验证码，已登录用户
	public boolean saveSendMessageHaslogin(MessageValidate messageValidate2,Fuser fuser,int fuserid,String msg,int type,int lang){
		log.info(CLASS_NAME + " SendMessage 已登录用户发送验证码,fuserid:{},phone:{},type:{}", fuserid, messageValidate2.getPhone(), type);
		boolean canSend = true ;
		MessageValidate messageValidate = this.frontValidateMapServiceImpl.getMessageMap(fuserid+"_"+type) ;
		log.info(CLASS_NAME + " SendMessage 根据用户id+短信类型从validateMap查找是否存在,返回结果集messageValidate:{}", new Gson().toJson(messageValidate));
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			log.info(CLASS_NAME + " SendMessage 距离上一次操作时间,小于120s");
			canSend = false ;
		}
		
		if(canSend){
			/*MessageValidate messageValidate2 = new MessageValidate() ;
			messageValidate2.setAreaCode(areaCode) ;
			messageValidate2.setCode(Utils.randomInteger(6)) ;
			messageValidate2.setPhone(phone) ;
			messageValidate2.setCreateTime(Utils.getTimestamp()) ;*/
			this.frontValidateMapServiceImpl.putMessageMap(fuserid + "_" + type, messageValidate2) ;
			log.info(CLASS_NAME + " SendMessage 将验证信息存储到validateMap中，key:{},value:{}", fuserid+"_"+type, new Gson().toJson(messageValidate2));
			Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
//			fvalidatemessage.setFcontent(this.constantMap.getString(ConstantKeys.VALIDATE_MESSAGE_CONTENT).replace("#code#", messageValidate2.getCode())) ;
//			fvalidatemessage.setFcontent(messageValidate2.getCode()) ;
//			Object[] params = new Object[]{messageValidate2.getCode()};
			fvalidatemessage.setFcontent(msg) ;
			fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
			fvalidatemessage.setFphone(messageValidate2.getPhone()) ;
			fvalidatemessage.setFlang(lang);
			fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
			log.info(CLASS_NAME + " SendMessage 将短信发送信息存储到fvalidatemessage表,fvalidatemessage:{}", new Gson().toJson(fvalidatemessage));
			fvalidatemessage.setFuser(fuser) ;
			this.addFvalidateMessage(fvalidatemessage) ;

			log.info(CLASS_NAME + " SendMessage 将短信发送记录主键id存储到taskList,id:{}", fvalidatemessage.getFid());
			
//			this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
			//加入短信队列
			rabbitTemplate.convertAndSend("message.add", fvalidatemessage.getFid());
		}
		return canSend ;
	}
	
	
	//发送短信验证码，未登录用户
	public boolean saveSendMessageNologin(MessageValidate messageValidate2,String ip,String msg,int type,int lang){
		log.info(CLASS_NAME + "SendMessage 未登录用户发送短信验证码,参数ip:{},areaCode:{},phone:{},type:{}", ip, messageValidate2.getAreaCode(), messageValidate2.getPhone(), type);
		String key1 = ip+"_"+type ;
		String key2 = ip+"_"+messageValidate2.getPhone()+"_"+type ;
		log.info(CLASS_NAME + "SendMessage key1:{},key2:{}", key1,key2);
		//限制ip120秒发送
		MessageValidate messageValidate = this.frontValidateMapServiceImpl.getMessageMap(key1) ;
		log.info(CLASS_NAME + "SendMessage 根据ip地址+验证码类型，从数据库中查询限制信息,messageValidate:{}", new Gson().toJson(messageValidate));
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			log.info(CLASS_NAME + " SendMessage 验证码限制，此类型:{}距离上一次发送低于120s", key1);
			return false ;
		}
		
		
		messageValidate = this.frontValidateMapServiceImpl.getMessageMap(key2) ;
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			log.info(CLASS_NAME + " SendMessage 验证码限制，此类型:{}距离上一次发送低于120s", key2);
			return false ;
		}
		
		/*MessageValidate messageValidate2 = new MessageValidate() ;
		messageValidate2.setAreaCode(areaCode) ;
		messageValidate2.setCode(Utils.randomInteger(6)) ;
		messageValidate2.setPhone(phone) ;
		messageValidate2.setCreateTime(Utils.getTimestamp()) ;*/
		this.frontValidateMapServiceImpl.putMessageMap(key2, messageValidate2) ;
		this.frontValidateMapServiceImpl.putMessageMap(key1, messageValidate2) ;
		Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
//		fvalidatemessage.setFcontent(messageValidate2.getCode()) ;
//		Object[] params = new Object[]{messageValidate2.getCode()};
		fvalidatemessage.setFcontent(msg) ;
		fvalidatemessage.setFlang(lang);
		fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
		fvalidatemessage.setFphone(messageValidate2.getPhone()) ;
		fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
		this.addFvalidateMessage(fvalidatemessage) ;
		
//		this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
		//加入短信队列
		rabbitTemplate.convertAndSend("message.add", fvalidatemessage.getFid());
		return true ;
	}
	
	
	//发送邮件验证码，未登录用户
	public boolean saveSendMailNologin(String msg,String lcal,String ip,String mail,int type,int lang){
		String key1 = ip+"mail_"+type ;
		String key2 = ip+"_"+mail+"_"+type ;
		//限制ip120秒发送
		Emailvalidate mailValidate = this.frontValidateMapServiceImpl.getMailMap(key1) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		
		mailValidate = this.frontValidateMapServiceImpl.getMailMap(key2) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		Emailvalidate mailValidate2 = new Emailvalidate() ;
		mailValidate2.setCode(Utils.randomInteger(6)) ;
		mailValidate2.setFcreateTime(Utils.getTimestamp()) ;
		mailValidate2.setFmail(mail) ;
		
		this.frontValidateMapServiceImpl.putMailMap(key1, mailValidate2) ;
		this.frontValidateMapServiceImpl.putMailMap(key2, mailValidate2) ;
		
		Fvalidateemail fvalidateemail = new Fvalidateemail() ;
		fvalidateemail.setEmail(mail) ;
		fvalidateemail.setFlang(lang);
		String regmailContent = null ;
//		String lcal = RequestContextUtils.getLocale(request).toString();
		String regmailkey  = ConstantKeys.regmailContent;
		if(lcal.equals("zh_CN")){
			regmailkey=regmailkey+"_CN";
		}
		regmailContent = this.frontConstantMapServiceImpl.getString(regmailkey).replace("#code#", mailValidate2.getCode()) ;
		fvalidateemail.setFcontent(regmailContent) ;
		fvalidateemail.setFcreateTime(Utils.getTimestamp()) ;
		fvalidateemail.setFstatus(ValidateMailStatusEnum.Not_send) ;
		String webnamekey = ConstantKeys.WEB_NAME;
		if(lcal.equals("zh_CN")){
			webnamekey=webnamekey+"_CN";
		}
		fvalidateemail.setFtitle(this.frontConstantMapServiceImpl.getString(webnamekey)+msg);
		this.addFvalidateemail(fvalidateemail) ;
		
		//加入邮件队列
		rabbitTemplate.convertAndSend("email.add", fvalidateemail.getFid());
	//	this.taskList.returnMailList(fvalidateemail.getFid()) ;
		
		return true ;
	}
	
	
	public boolean saveSendFindPasswordMail(String ip,Fuser fuser,String email,String lcal, String msg){
		//发送激活邮件
		boolean flag = false ;
		try {
			String UUID = Utils.UUID() ;
			//发送给用户的邮件
			Fvalidateemail validateemails = new Fvalidateemail() ;
//			String lcal = RequestContextUtils.getLocale(request).toString();
			String webnamekey = ConstantKeys.WEB_NAME;
			if(lcal.equals("zh_CN")){
				webnamekey=webnamekey+"_CN";
			}
			validateemails.setFtitle(this.getSystemArgs(webnamekey)+msg) ;
			
			String findpasskey  = ConstantKeys.findPasswordMail;
			if(lcal.equals("zh_CN")){
				findpasskey=findpasskey+"_CN";
			}
			validateemails.setFcontent(
					this.getSystemArgs(findpasskey)
					.replace("#firstLevelDomain#", this.getSystemArgs(ConstantKeys.firstLevelDomain))
					.replace("#url#", Constant.Domain+"validate/resetPwd.html?uid="+fuser.getFid()+"&&uuid="+UUID)
					.replace("#fulldomain#", this.getSystemArgs(ConstantKeys.fulldomain))
					.replace("#englishName#", this.getSystemArgs(ConstantKeys.englishName))) ;
			validateemails.setFcreateTime(Utils.getTimestamp()) ;
			validateemails.setFstatus(ValidateMailStatusEnum.Not_send) ;
			validateemails.setFuser(fuser) ;
			this.validateemailsDAO.save(validateemails) ;
			
			//验证并对应到用户的UUID
			Emailvalidate emailvalidate = new Emailvalidate() ;
			emailvalidate.setFcreateTime(Utils.getTimestamp()) ;
			emailvalidate.setFmail(email) ;
			emailvalidate.setFuser(fuser) ;
			emailvalidate.setFuuid(UUID) ;
			emailvalidate.setType(SendMailTypeEnum.FindPassword) ;
			this.emailvalidateDAO.save(emailvalidate) ;
			
			//加入邮件队列
//			this.taskList.returnMailList(validateemails.getFid()) ;
			rabbitTemplate.convertAndSend("email.add", validateemails.getFid());
			
			this.frontValidateMapServiceImpl.putMailMap(ip+"_"+SendMailTypeEnum.FindPassword, emailvalidate) ;
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException() ;
		}
		return flag ;
		
	}
	
	//找回密码邮件
	public Emailvalidate updateFindPasswordMailValidate(int uid,String uuid) {
		Emailvalidate emailvalidate = null ;
		try{
			emailvalidate = this.emailvalidateDAO.findByUidUuid(uid, uuid,SendMailTypeEnum.FindPassword) ;
			
			if(emailvalidate==null || emailvalidate.getfNewUUid()!=null){
				return null ;
			}else{
				emailvalidate.setFvalidateTime(Utils.getTimestamp()) ;
				emailvalidate.setfNewUUid(Utils.UUID()) ;
				this.emailvalidateDAO.attachDirty(emailvalidate) ;
			}
			return emailvalidate ;
		}catch(Exception e){
			e.printStackTrace() ;
			throw new RuntimeException() ;
		}
	}
	
	//是否可以重试
	public int getLimitCount(String ip,int type){
		int maxLimit = Constant.ErrorCountLimit ;
		if(type==CountLimitTypeEnum.AdminLogin){
			maxLimit = Constant.ErrorCountAdminLimit ;
		}
		
		List<Fcountlimit> list = this.fcountlimitDAO.findByIpType(ip, type) ;
		if(list.size()==0){
			return maxLimit ;
		}else{
			Fcountlimit fcountlimit = list.get(0) ;
			if(Utils.getTimestamp().getTime()- fcountlimit.getFcreateTime().getTime()<2L*60*60*1000){
				return maxLimit - fcountlimit.getFcount() ;
			}else{
				return maxLimit ;
			}
		}
	}
	
	//记录一次错误记录
	public void updateLimitCount(String ip,int type){
		if(Constant.closeLimit){return;}
		List<Fcountlimit> list = this.fcountlimitDAO.findByIpType(ip, type) ;
		if(list.size()==0){
			Fcountlimit fcountlimit = new Fcountlimit() ;
			fcountlimit.setFcount(1) ;
			fcountlimit.setFcreateTime(Utils.getTimestamp()) ;
			fcountlimit.setFip(ip) ;
			fcountlimit.setFtype(type) ;
			this.fcountlimitDAO.save(fcountlimit) ;
		}else{
			Fcountlimit fcountlimit = list.get(0) ;
			if(Utils.getTimestamp().getTime()- fcountlimit.getFcreateTime().getTime()<2*60*60*1000L){
				fcountlimit.setFcount(fcountlimit.getFcount()+1) ;
				fcountlimit.setFcreateTime(Utils.getTimestamp()) ;
				this.fcountlimitDAO.attachDirty(fcountlimit) ;
			}else{
				fcountlimit.setFcount(1) ;
				fcountlimit.setFcreateTime(Utils.getTimestamp()) ;
				this.fcountlimitDAO.attachDirty(fcountlimit) ;
			}
		}
	}
	
	public void deleteCountLimite(String ip,int type){
		List<Fcountlimit> list = this.fcountlimitDAO.findByIpType(ip, type) ;
		for(int i=0;i<list.size();i++){
			this.fcountlimitDAO.delete(list.get(i)) ;
		}
	}
	
	public Fvalidatemessage findFvalidateMessageById(int id){
		return this.fvalidatemessageDAO.findById(id) ;
	}
	public List<Fvalidatemessage> findFvalidateMessageByProperty(String key,Object value){
		return this.fvalidatemessageDAO.findByProperty(key, value) ;
	}
	
	public void updateFvalidateMessage(Fvalidatemessage fvalidatemessage){
		this.fvalidatemessageDAO.attachDirty(fvalidatemessage) ;
	}
	
	public void addFvalidateMessage(Fvalidatemessage fvalidatemessage){
		this.fvalidatemessageDAO.save(fvalidatemessage) ;
	}
	
	public void addFvalidateemail(Fvalidateemail fvalidateemail){
		this.fvalidateemailDAO.save(fvalidateemail) ;
	}
	
	public Fvalidateemail findValidateMailsById(int id){
		Fvalidateemail fvalidateemail = this.validateemailsDAO.findById(id) ;
		return fvalidateemail ;
	}
	public List<Fvalidateemail> findValidateMailsByProperty(String key,Object value){
		return this.validateemailsDAO.findByProperty(key, value) ;
	}
	
	public void updateValidateMails(Fvalidateemail validateemails){
		this.validateemailsDAO.attachDirty(validateemails) ;
	}
	
	public boolean canSendFindPwdMsg(
			int fid,
			int ev_id,
			String newuuid
			){
		boolean flag = false ;
		List<Emailvalidate> emailvalidates = this.emailvalidateDAO.canSendFindPwdMsg(fid, ev_id, newuuid) ;
		if(emailvalidates.size()>0){
			Emailvalidate emailvalidate = emailvalidates.get(0) ;
			if(Utils.getTimestamp().getTime() - emailvalidate.getFvalidateTime().getTime()<15*60*1000L){
				flag = true ;
			}
		}
		return flag ;
	}
	
	
}
