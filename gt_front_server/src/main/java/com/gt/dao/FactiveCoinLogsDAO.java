package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FactiveCoinLogs;

/**
 * 币种激活流水DAO
 * @author zhouyong
 *
 */
@Repository
public class FactiveCoinLogsDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FactiveCoinLogsDAO.class);

	public Integer save(FactiveCoinLogs instance) {
		log.debug("saving FactiveCoinLogs instance");
		Integer ret = 0;
		try {
			ret = (Integer) getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return ret;
	}
	
	public FactiveCoinLogs findById(java.lang.Integer id) {
		log.debug("getting FactiveCoinLogs instance with id: " + id);
		try {
			FactiveCoinLogs instance = (FactiveCoinLogs) getSession().get(
					"com.gt.entity.FactiveCoinLogs", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(FactiveCoinLogs instance) {
		log.debug("attaching dirty FactiveCoinLogs instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FactiveCoinLogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FactiveCoinLogs> list = null;
		log.debug("finding FactiveCoinLogs instance with filter");
		try {
			String queryString = " from FactiveCoinLogs "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FactiveCoinLogs name failed", re);
			throw re;
		}
		return list;
	}
	
}
