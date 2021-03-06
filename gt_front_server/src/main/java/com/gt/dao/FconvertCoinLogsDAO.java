package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FconvertCoinLogs;

/**
 * 币种兑换操作日志DAO
 * @author zhouyong
 *
 */
@Repository
public class FconvertCoinLogsDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FconvertCoinLogsDAO.class);

	public void save(FconvertCoinLogs instance) {
		log.debug("saving FconvertCoinLogs instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public FconvertCoinLogs findById(java.lang.Integer id) {
		log.debug("getting FconvertCoinLogs instance with id: " + id);
		try {
			FconvertCoinLogs instance = (FconvertCoinLogs) getSession().get(
					"com.gt.entity.FconvertCoinLogs", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(FconvertCoinLogs instance) {
		log.debug("attaching dirty FconvertCoinLogs instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FconvertCoinLogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FconvertCoinLogs> list = null;
		log.debug("finding FconvertCoinLogs instance with filter");
		try {
			String queryString = " from FconvertCoinLogs "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FconvertCoinLogs name failed", re);
			throw re;
		}
		return list;
	}
	
}
