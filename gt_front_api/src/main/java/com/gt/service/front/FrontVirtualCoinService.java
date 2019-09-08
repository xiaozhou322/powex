package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwithdrawfees;

/**
 * 虚拟币操作接口
 * @author zhouyong
 *
 */
public interface FrontVirtualCoinService {
	
	/**
	 * 根据状态和虚拟币类型查询虚拟币列表
	 * @param status
	 * @param coinType
	 * @return
	 */
	public List<Fvirtualcointype> findFvirtualCoinType(int status,int coinType);
	public List<Fvirtualcointype> findByProperty(String propertyName, Object value);
	
	/**
	 * 根据id查询虚拟币信息
	 * @param id
	 * @return
	 */
	public Fvirtualcointype findFvirtualCoinById(int id);
	
	/**
	 * 根据状态和虚拟币类型查询虚拟币信息
	 * @return
	 */
	public Fvirtualcointype findFirstFirtualCoin();
	
	public Fvirtualcointype findFirstFirtualCoin_Wallet();
	
	/**
	 * 查询会员虚拟币钱包地址
	 * @param fuser
	 * @param fvirtualcointype
	 * @return
	 */
	public Fvirtualaddress findFvirtualaddress(Fuser fuser,Fvirtualcointype fvirtualcointype);
	
	/**
	 * 查询会员虚拟币钱包地址列表
	 * @param fvirtualcointype
	 * @param address
	 * @return
	 */
	public List<Fvirtualaddress> findFvirtualaddress(Fvirtualcointype fvirtualcointype,String address);
	
	/**
	 * 根据id查询虚拟钱包地址
	 * @param fid
	 * @return
	 */
	public FvirtualaddressWithdraw findFvirtualaddressWithdraw(int fid);
	
	/**
	 * 查询虚拟钱包提现地址列表
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FvirtualaddressWithdraw> findFvirtualaddressWithdraws(int firstResult, int maxResults,
			String filter,boolean isFY);
	
	/**
	 * 查询虚拟币资金流向数量
	 * @param fuser
	 * @param type
	 * @param status
	 * @param fvirtualcointypes
	 * @param order
	 * @return
	 */
	public int findFvirtualcaptualoperationCount(
			Fuser fuser,int type[],int status[],Fvirtualcointype[] fvirtualcointypes,String order);
	
	/**
	 * 查询虚拟币资金流向列表
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperations(int firstResult, int maxResults,String filter, boolean isFY);
	
	/**
	 * 根据条件查询虚拟币资金流向数量
	 * @param filter
	 * @return
	 */
	public int findFvirtualcaptualoperationsCount(String filter);
	
	/**
	 * 更新虚拟钱包提现地址
	 * @param fvirtualaddressWithdraw
	 */
	public void updateFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw);
	
	/**
	 * 删除虚拟钱包提现地址
	 * @param fvirtualaddressWithdraw
	 */
	public void updateDelFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw);
	
	/**
	 * 查询虚拟币手续费
	 * @param virtualCoinTypeId
	 * @param level
	 * @return
	 */
	public Fwithdrawfees findFfees(int virtualCoinTypeId,int level);
	
	/**
	 * 更新比特币提现
	 * @param fvirtualaddressWithdraw
	 * @param fvirtualcointype
	 * @param fvirtualwallet
	 * @param withdrawAmount
	 * @param ffees
	 * @param fuser
	 */
	public void updateWithdrawBtc(FvirtualaddressWithdraw fvirtualaddressWithdraw,Fvirtualcointype fvirtualcointype,Fvirtualwallet fvirtualwallet ,double withdrawAmount,double ffees,Fuser fuser);
	
	/**
	 * 更新转账
	 * @param fvirtualcointype
	 * @param fvirtualwallet
	 * @param withdrawAmount
	 * @param ffees
	 * @param fuser
	 * @param fuskr
	 */
	public void updateTransfer(Fvirtualcointype fvirtualcointype,Fvirtualwallet fvirtualwallet ,double withdrawAmount,double ffees,Fuser fuser,Fuser fuskr);
	
	/**
	 * 保存虚拟币资金流向记录
	 * @param fvirtualcaptualoperation
	 */
	public void addFvirtualcaptualoperation(Fvirtualcaptualoperation fvirtualcaptualoperation);
	
	/**
	 * 根据条件查询虚拟币资金流向列表
	 * @param key
	 * @param value
	 * @return
	 */
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperationByProperty(String key,Object value);
	
	/**
	 * 根据id查询虚拟币资金流向记录
	 * @param id
	 * @return
	 */
	public Fvirtualcaptualoperation findFvirtualcaptualoperationById(int id);
	
	/**
	 * 比特币自动充值并加币
	 * @param fvirtualcaptualoperation
	 * @throws Exception
	 */
	public void updateFvirtualcaptualoperationCoinIn(Fvirtualcaptualoperation fvirtualcaptualoperation);
	
	//比特币自动充值并加币
	public void updateFvirtualcaptualoperationCoinIn_ETP(Fvirtualcaptualoperation fvirtualcaptualoperation);
	
	/**
	 * 根据条件查询会员虚拟币钱包地址列表
	 * @param key
	 * @param value
	 * @return
	 */
	public List<Fvirtualaddress> findFvirtualaddressByProperty(String key,Object value);
	
	/**
	 * 判断虚拟币是否能提现
	 * @return
	 */
	public boolean isExistsCanWithdrawCoinType();
	
	/**
	 * 根据id获取虚拟币
	 * @return
	 */
	public Fvirtualcointype findById(int frenchCurrencyType);
	
	public List<Fvirtualcointype> list(int firstResult, int maxResults,
			String filter,boolean isFY);
}
