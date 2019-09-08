package com.gt.service.front;

import java.util.LinkedHashMap;
import java.util.List;

import com.gt.entity.Fapi;
import com.gt.entity.Fbankinfo;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fintrolinfo;
import com.gt.entity.Fmessage;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fusersetting;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;

/**
 * 用户中心接口
 * @author zhouyong
 *
 */
public interface FrontUserService {
	
	
	public int getMaxId();
	/**
	 * 校验登录名
	 * @param name
	 * @return
	 */
	public boolean nickValidated(String name);
	
	/**
	 * 保存注册信息
	 * @param fuser
	 * @return
	 */
	public Fuser saveRegister(Fuser fuser);
	
	/**
	 * 更新登录信息
	 * @param fuser
	 * @param ip
	 * @param ismail
	 * @return
	 */
	public Fuser updateCheckLogin(Fuser fuser,String ip,boolean ismail);
	
	/**
	 * 查询所有非人民币和USDT的虚拟币
	 * @param fuid
	 * @return
	 */
	public LinkedHashMap<Fvirtualcointype, Fvirtualwallet> findVirtualWallet(int fuid);
	
	/**
	 * 根据用户id和币种查询虚拟钱包
	 * @param fuid
	 * @param virtualCoinId
	 * @return
	 */
	public Fvirtualwallet findVirtualWalletByUser(int fuid,int virtualCoinId);
	
	/**
	 * 根据UID找会员人民币钱包
	 * @param fuid
	 * @return
	 */
	public Fvirtualwallet findWalletByUser(int fuid);
	
	/**
	 * 根据UID找会员USDT钱包
	 * @param fuid
	 * @return
	 */
	public Fvirtualwallet findUSDTWalletByUser(int fuid);
	
	/**
	 * 根据UID查询会员
	 * @param id
	 * @return
	 */
	public Fuser findById(int id);
	
	public int findIntroUserCount(Fuser fuser);
	
	public Fbankinfo findUserBankInfo(int uid);
	
	/**
	 * 根据id查询提现银行信息
	 * @param id
	 * @return
	 */
	public FbankinfoWithdraw findByIdWithBankInfos(int id);
	
	/**
	 * 校验邮箱是否存在
	 * @param email
	 * @return
	 */
	public boolean isEmailExists(String email);
	
	/**
	 * 校验手机号是否存在
	 * @param telephone
	 * @return
	 */
	public boolean isTelephoneExists(String telephone);
	
	/**
	 * 更新用户信息及日志信息
	 * @param fuser
	 * @param session
	 * @param logType
	 * @param ip
	 */
	public void updateFUser(Fuser fuser,int logType,String ip);
	
	/**
	 * 更新用户信息
	 * @param fuser
	 */
	public void updateFuser(Fuser fuser);
	
	/**
	 * 更新用户积分及钱包
	 * @param fscore
	 * @param fvirtualwallet
	 */
	public void updateFuser(Fscore fscore,Fvirtualwallet fvirtualwallet);
	
	public void updateFuser(Fuser fuser,Fintrolinfo introlInfo,
			Fscore fintrolScore,Fscore fscore);
	
	/**
	 * 根据用户属性查询用户信息
	 * @param key
	 * @param value
	 * @return
	 */
	public List<Fuser> findUserByProperty(String key,Object value);
	
	/**
	 * 添加会员银行信息
	 * @param fbankinfo
	 * @param fuser
	 */
	public void addBankInfo(Fbankinfo fbankinfo,Fuser fuser);
	
	/**
	 * 保存会员提现银行信息
	 * @param fbankinfoWithdraw
	 */
	public void updateBankInfoWithdraw(FbankinfoWithdraw fbankinfoWithdraw);
	
	/**
	 * 删除会员提现银行信息
	 * @param fbankinfoWithdraw
	 */
	public void updateDelBankInfoWithdraw(FbankinfoWithdraw fbankinfoWithdraw);
	
	public boolean deleteAllUser();
	
	/**
	 * 根据key获取系统配置
	 * @param key
	 * @return
	 */
	public String getSystemArgs(String key);

	/**
	 * 保存会员API信息
	 * @param fuser
	 * @param label
	 * @param istrade
	 * @param iswithdraw
	 * @param ip
	 * @return
	 */
	public Fapi addFapi(Fuser fuser,String label,boolean istrade,boolean iswithdraw,String ip);
	
	/**
	 * 删除会员API
	 * @param fapi
	 */
	public void deleteFapi(Fapi fapi);
	
	/**
	 * 根据id查询会员API信息
	 * @param id
	 * @return
	 */
	public Fapi findFapiById(int id);
	
	/**
	 * 查询会员提现银行列表
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FbankinfoWithdraw> findFbankinfoWithdrawByFuser(int firstResult, int maxResults, String filter,boolean isFY);
	
	/**
	 * 根据id查询会员提现银行
	 * @param fid
	 * @return
	 */
	public FbankinfoWithdraw findFbankinfoWithdraw(int fid);
	
	/**
	 * 发送邮件
	 * @param fuser
	 * @param email
	 * @param force
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	public boolean saveValidateEmail(Fuser fuser,String email,boolean force,String msg, String lcal);
	
	
	public void updateDisabledApi(Fuser fuser);
	
	
	public List<Fapi> findFapiByProperty(String key,Object value);

	public Fuser findFuserByFapi(Fapi fapi);
	
	/**
	 * 根据QQ登录查询用户
	 * @param openId
	 * @return
	 */
	public Fuser findByQQlogin(String openId);
	
	public Fscore findFscoreById(int id);
	
	public Fusersetting findFusersetting(int fid);
	
	public void updateFusersetting(Fusersetting fusersetting);
	
	public void updateCleanScore();
	
	public void updateSendLog(Fvirtualwallet fvirtualwallet,Fmessage message);
	
	public void updateShoppinglog(Fvirtualwallet fvirtualwallet,Fintrolinfo info);
	
	
	//安全设置修改48小时之内，不允许进行提币提现操作
	public boolean isSafeTime(Integer uid) throws Exception;
		
	//用户日志记录操作
	public void updateUserLog(Fuser fuser,String ip,Integer logtype,String description);
	
	/**
	 * 将OTC上线商户放入redis
	 * @param userId
	 */
	public void addOTCOnlineUserMap(Integer userId);
	
	/**
	 * 将OTC下线商户移出redis
	 * @param userId
	 */
	public void removeOTCOnlineUserMap(Integer userId);
	
	
	/**
	 * 从redis获取OTC上线用户
	 */
	public List<Integer> getOTCOnlineUserList();
	
}
