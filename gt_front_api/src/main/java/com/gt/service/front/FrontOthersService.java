package com.gt.service.front;

import java.util.List;

import com.gt.entity.Fabout;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.entity.Fbankin;
import com.gt.entity.Fbanner;
import com.gt.entity.FbfscDataStatistics;
import com.gt.entity.Fentrustlog;
import com.gt.entity.FfeesConvert;
import com.gt.entity.Ffriendlink;
import com.gt.entity.Fperiod;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;

public interface FrontOthersService {
	
	public List<Ffriendlink> findFfriendLink(int type);
	
	public Fabout findFabout(int fid);
	
	public List<Fabout> findFaboutAll();
	
	public List<Farticle> findFarticle(int farticletype,int firstResult,int maxResult);
	
	public List<Fbanner> findFbanner(String fbannertype,int firstResult,int maxResult);
	
	public int findFarticleCount(int farticletype);
	
	public int findFarticle(int farticletype);
	
	public List<Ffriendlink> findFriendLink(int type,int firstResult,int maxResult);
	
	public Farticle findFarticleById(int id);
	
	public Farticletype findFarticleTypeById(int id);
	
	public List<Farticletype> findFarticleTypeAll();
	
	public List<Fperiod> findAllFperiod(long fromTime,int fvirtualCoinType);
	
	public void addFperiod(Fperiod fperiod);
	
	public void addFperiods(List<Fperiod> fperiods);
	
	public Fperiod getLastFperiod(Fvirtualcointype fvirtualcointype);
	
	public Fentrustlog getLastFpeFentrustlog(int fvirtualcointype);
	
	public List<Fbankin> findFbankin();
	
	public List<Farticle> findProjectFarticle(int projectId,int farticletype,int firstResult,int maxResult);

	public int findProjectFarticleCount(int fid, int id);
	
	
	/**
	 * 分页查询手续费兑换记录（USDT兑BFSC）
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FfeesConvert> queryFfeesConvertList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public FfeesConvert findFeesConvertById(java.lang.Integer id);
	
	/**
	 * 保存手续费兑换记录（USDT兑BFSC）
	 * @param instance
	 */
	public void saveFfeesConvert(FfeesConvert instance);
	
	/**
	 * 修改手续费兑换记录（USDT兑BFSC）
	 * @param instance
	 */
	public void updateFfeesConvert(FfeesConvert instance);
	
	
	/**
	 * 处理手续费兑换记录（USDT兑BFSC）
	 * @param instance
	 */
	public void updateFfeesConvertAndWallet(Fvirtualwallet usdtWallet,
			Fvirtualwallet bfscWallet, FfeesConvert feesConvert);
	
	/**
	 * 删除手续费兑换记录（USDT兑BFSC）
	 * @param instance
	 */
	public void deleteFfeesConvert(FfeesConvert instance);
	
	
	/**
	 * 保存BFSC统计数据
	 * @param instance
	 */
	public void saveFbfscDataStatistics(FbfscDataStatistics bfscData, FfeesConvert ffeesConvert);
	
	/**
	 * 根据条件查询BFSC统计数据
	 * @param filter
	 * @return
	 */
	public FbfscDataStatistics findFbfscDataStatisticsByParam(String filter);
	
	/**
	 * 分页查询BFSC统计数据
	 * @param firstResult
	 * @param maxResults
	 * @param filter
	 * @param isFY
	 * @return
	 */
	public List<FbfscDataStatistics> queryFbfscDataStatisticsList(int firstResult, int maxResults, String filter,boolean isFY);
	
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public FbfscDataStatistics findFbfscDataStatisticsById(java.lang.Integer id);
}
