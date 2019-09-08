package com.gt.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.Enum.EntrustTypeEnum;
import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.PuserProfitlogs;

/**
 * 用户挖矿收益记录DAO
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Repository
public class PuserProfitlogsDAO extends HibernateDaoSupport {

	private static final Logger log = LoggerFactory.getLogger(PuserProfitlogsDAO.class);

	public void save(PuserProfitlogs instance) {
		log.debug("saving PuserProfitlogs instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public PuserProfitlogs findById(java.lang.Integer id) {
		log.debug("getting PuserProfitlogs instance with id: " + id);
		try {
			PuserProfitlogs instance = (PuserProfitlogs) getSession().get(
					"com.gt.entity.PuserProfitlogs", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(PuserProfitlogs instance) {
		log.debug("attaching dirty PuserProfitlogs instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<PuserProfitlogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<PuserProfitlogs> list = null;
		log.debug("finding PuserProfitlogs instance with filter");
		try {
			String queryString = "from PuserProfitlogs "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by PuserProfitlogs name failed", re);
			throw re;
		}
		return list;
	}
	
	
	
	//获取用户买入的挖矿奖励
	public List<Map<String, Object>> getUserBuyProfitlogs(String yesterdayStr){
		List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.FUs_fId , a.ftrademapping , c.fid , \n");
		sql.append(" sum(a.ffees-a.fcommissionProfit-a.ffeesProfit) amount  \n");
		sql.append(" from fentrust a LEFT JOIN ftrademapping b on a.ftrademapping = b.fid  \n");
		sql.append(" LEFT JOIN fvirtualcointype c on b.fvirtualcointype2 = c.fid  \n");
		sql.append(" where a.fEntrustType = "+ EntrustTypeEnum.BUY +" \n");
		sql.append(" and a.fStatus = "+ EntrustStatusEnum.AllDeal +" \n");
		sql.append(" and DATE_FORMAT(a.flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' " +" \n");
		sql.append(" GROUP BY a.FUs_fId,a.ftrademapping,c.fid \n");
		
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("userId", o[0]);
			map.put("tradeMappingId", o[1]);
			map.put("coinTypeId", o[2]);
			map.put("amount", o[3]);
			all.add(map);
		}
		return all;
	}
	
	
	//获取用户卖出的挖矿奖励
	public List<Map<String, Object>> getUserSellProfitlogs(String yesterdayStr){
		List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.FUs_fId , a.ftrademapping , c.fid , \n");
		sql.append(" sum(a.ffees-a.fcommissionProfit-a.ffeesProfit) amount  \n");
		sql.append(" from fentrust a LEFT JOIN ftrademapping b on a.ftrademapping = b.fid  \n");
		sql.append(" LEFT JOIN fvirtualcointype c on b.fvirtualcointype1 = c.fid  \n");
		sql.append(" where a.fEntrustType = "+ EntrustTypeEnum.SELL +" \n");
		sql.append(" and a.fStatus = "+ EntrustStatusEnum.AllDeal +" \n");
		sql.append(" and DATE_FORMAT(a.flastUpdatTime,'%Y-%m-%d') = '" + yesterdayStr +"' " +" \n");
		sql.append(" GROUP BY a.FUs_fId,a.ftrademapping,c.fid \n");
		
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("userId", o[0]);
			map.put("tradeMappingId", o[1]);
			map.put("coinTypeId", o[2]);
			map.put("amount", o[3]);
			all.add(map);
		}
		return all;
	}
	
}
