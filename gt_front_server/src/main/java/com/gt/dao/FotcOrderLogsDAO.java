package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcOrderLogs;

/**
 * otc订单操作日志DAO
 * @author zhouyong
 *
 */
@Repository
public class FotcOrderLogsDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FotcOrderLogsDAO.class);

	public void save(FotcOrderLogs instance) {
		log.debug("saving FotcOrderLogs instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public FotcOrderLogs findById(java.lang.Integer id) {
		log.debug("getting FotcOrderLogs instance with id: " + id);
		try {
			FotcOrderLogs instance = (FotcOrderLogs) getSession().get(
					"com.gt.entity.FotcOrderLogs", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(FotcOrderLogs instance) {
		log.debug("attaching dirty FotcOrderLogs instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FotcOrderLogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FotcOrderLogs> list = null;
		log.debug("finding FotcOrderLogs instance with filter");
		try {
			String queryString = " from FotcOrderLogs "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FotcOrderLogs name failed", re);
			throw re;
		}
		return list;
	}
	
}
