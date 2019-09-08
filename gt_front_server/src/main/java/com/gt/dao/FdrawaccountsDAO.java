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

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fdrawaccounts;
import com.gt.entity.Pad;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdeposit;

/**
 * 划账记录DAO
 * @author zhouyong
 *
 */
@Repository
public class FdrawaccountsDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FdrawaccountsDAO.class);

	public void save(Fdrawaccounts instance) {
		log.debug("saving Fdrawaccounts instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Fdrawaccounts findById(java.lang.Integer id) {
		log.debug("getting Fdrawaccounts instance with id: " + id);
		try {
			Fdrawaccounts instance = (Fdrawaccounts) getSession().get(
					"com.gt.entity.Pad", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Fdrawaccounts instance) {
		log.debug("attaching dirty Fdrawaccounts instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fdrawaccounts> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Fdrawaccounts> list = null;
		log.debug("finding Fdrawaccounts instance with filter");
		try {
			String queryString = "from Fdrawaccounts "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Fdrawaccounts name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public List<Map<String, Object>> queryDrawaccountsStatisticsList(int firstResult, int maxResults, String filter,boolean isFY, int type) {
		List<Map<String, Object>> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		if(1 == type) {
			sql.append(" select t.fUserTo, a.fRealName, t.fCointype, c.fShortName,  \n");
			sql.append(" t.fType, sum(t.fAmount) totalAmount from fdrawaccounts t  \n");
			sql.append(" left join fuser a on a.fid = t.fUserTo  \n");
			sql.append(" left join fvirtualcointype c on c.fid = t.fCointype  \n");
			sql.append(filter);
			sql.append(" group by t.fUserTo \n");
		} else {
			sql.append(" select t.fUserFrom, a.fRealName, t.fCointype, c.fShortName,  \n");
			sql.append(" t.fType, sum(t.fAmount) totalAmount from fdrawaccounts t  \n");
			sql.append(" left join fuser a on a.fid = t.fUserFrom  \n");
			sql.append(" left join fvirtualcointype c on c.fid = t.fCointype  \n");
			sql.append(filter);
			sql.append(" group by t.fUserFrom \n");
		}
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		if(isFY){
			queryObject.setFirstResult(firstResult);
			queryObject.setMaxResults(maxResults);
		}
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Object[] o = (Object[]) it.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", o[0].toString());
			map.put("userName", o[1].toString());
			map.put("coinType", o[2].toString());
			map.put("coinShortName", o[3].toString());
			map.put("type", o[4].toString());
			map.put("totalAmount", o[5].toString());
			all.add(map);
		}
		
		return all;
	}
	
	
	
	public int getDrawaccountsStatisticsCount(String filter, int type){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from  \n");
		sql.append(" (select t.fUserFrom, t.fUserTo, t.fCointype, t.fType ,sum(t.fAmount)  from fdrawaccounts t  \n");
		sql.append(filter);
		if(1 == type) {
			sql.append(" group by t.fUserTo) v \n");
		} else {
			sql.append(" group by t.fUserFrom) v \n");
		}
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		return Integer.parseInt(queryObject.list().get(0).toString());
	}
}
