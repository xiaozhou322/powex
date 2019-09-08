package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FapproCoinLogs;

/**
 * 币种兑换操作日志DAO
 * @author zhouyong
 *
 */
@Repository
public class FapproCoinLogsDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FapproCoinLogsDAO.class);

	public void save(FapproCoinLogs instance) {
		log.debug("saving FapproCoinLogs instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public FapproCoinLogs findById(java.lang.Integer id) {
		log.debug("getting FapproCoinLogs instance with id: " + id);
		try {
			FapproCoinLogs instance = (FapproCoinLogs) getSession().get(
					"com.gt.entity.FapproCoinLogs", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(FapproCoinLogs instance) {
		log.debug("attaching dirty FapproCoinLogs instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FapproCoinLogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FapproCoinLogs> list = null;
		log.debug("finding FapproCoinLogs instance with filter");
		try {
			String queryString = " from FapproCoinLogs "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FapproCoinLogs name failed", re);
			throw re;
		}
		return list;
	}
	
}
