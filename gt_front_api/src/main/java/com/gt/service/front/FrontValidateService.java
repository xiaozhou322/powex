package com.gt.service.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gt.comm.MessageValidate;
import com.gt.entity.Emailvalidate;
import com.gt.entity.Fuser;
import com.gt.entity.Fvalidateemail;
import com.gt.entity.Fvalidatemessage;

/**
 * 短信和邮件验证接口
 * @author zhouyong
 *
 */
public interface FrontValidateService {
	
	/**
	 * 用户注册邮件验证
	 * @param uid
	 * @param uuid
	 * @return
	 */
	public boolean updateMailValidate(int uid,String uuid);
	
//	public String getSystemArgs(String key);
	
	/**
	 * 发送短信验证码，已登录用户
	 * @param request
	 * @param fuser
	 * @param fuserid
	 * @param areaCode
	 * @param phone
	 * @param type
	 * @param lang
	 * @return
	 */
	public boolean saveSendMessageHaslogin(MessageValidate messageValidate2,Fuser fuser,int fuserid,
			String msg,int type,int lang);
	
	
	/**
	 * 发送短信验证码，未登录用户
	 * @param request
	 * @param ip
	 * @param areaCode
	 * @param phone
	 * @param type
	 * @param lang
	 * @return
	 */
	public boolean saveSendMessageNologin(MessageValidate messageValidate2,String ip,String msg,int type,int lang);
	
	
	/**
	 * 发送邮件验证码，未登录用户
	 * @param msg
	 * @param lcal
	 * @param ip
	 * @param mail
	 * @param type
	 * @param lang
	 * @return
	 */
	public boolean saveSendMailNologin(String msg,String lcal,String ip,String mail,int type,int lang);
	
	/**
	 * 发送激活邮件
	 * @param ip
	 * @param fuser
	 * @param email
	 * @param request
	 * @return
	 */
	public boolean saveSendFindPasswordMail(String ip,Fuser fuser,String email,String lcal, String msg);
	
	/**
	 * 找回密码邮件
	 * @param uid
	 * @param uuid
	 * @return
	 */
	public Emailvalidate updateFindPasswordMailValidate(int uid,String uuid);
	
	/**
	 * 是否可以重试
	 * @param ip
	 * @param type
	 * @return
	 */
	public int getLimitCount(String ip,int type);
	
	/**
	 * 记录一次错误记录
	 * @param ip
	 * @param type
	 */
	public void updateLimitCount(String ip,int type);
	
	/**
	 * 删除限制记录
	 * @param ip
	 * @param type
	 */
	public void deleteCountLimite(String ip,int type);
	
	/**
	 * 根据id查询短信验证信息
	 * @param id
	 * @return
	 */
	public Fvalidatemessage findFvalidateMessageById(int id);
	
	/**
	 * 根据条件查询短信验证信息
	 * @param key
	 * @param value
	 * @return
	 */
	public List<Fvalidatemessage> findFvalidateMessageByProperty(String key,Object value);
	
	/**
	 * 更新短信验证信息
	 * @param fvalidatemessage
	 */
	public void updateFvalidateMessage(Fvalidatemessage fvalidatemessage);
	
	
	/**
	 * 保存短信验证信息
	 * @param fvalidatemessage
	 */
	public void addFvalidateMessage(Fvalidatemessage fvalidatemessage);
	
	/**
	 * 保存邮箱验证信息
	 * @param fvalidateemail
	 */
	public void addFvalidateemail(Fvalidateemail fvalidateemail);
	
	/**
	 * 根据id查询邮箱验证信息
	 * @param id
	 * @return
	 */
	public Fvalidateemail findValidateMailsById(int id);
	
	/**
	 * 根据条件查询邮箱验证信息
	 * @param key
	 * @param value
	 * @return
	 */
	public List<Fvalidateemail> findValidateMailsByProperty(String key,Object value);
	
	/**
	 * 更新邮箱验证信息
	 * @param validateemails
	 */
	public void updateValidateMails(Fvalidateemail validateemails);
	
	/**
	 * 是否能发送找回密码邮件
	 * @param fid
	 * @param ev_id
	 * @param newuuid
	 * @return
	 */
	public boolean canSendFindPwdMsg(int fid, int ev_id, String newuuid);
	
}
