package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositlogs;
import com.gt.entity.Pproject;

/**
 * 保证金操作记录表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PdepositlogsDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PdepositlogsDAO.class);
	
	public void save(Pdepositlogs instance) {
		log.debug("saving Pdeposit instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pdepositlogs findById(java.lang.Integer id) {
		log.debug("getting Pdepositlogs instance with id: " + id);
		try {
			Pdepositlogs instance = (Pdepositlogs) getSession().get(
					"com.gt.entity.Pdepositlogs", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pdepositlogs instance) {
		log.debug("attaching dirty Pdepositlogs instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pdepositlogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pdepositlogs> list = null;
		log.debug("finding Pdepositlogs instance with filter");
		try {
			String queryString = "from Pdepositlogs "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Pdepositlogs name failed", re);
			throw re;
		}
		return list;
	}
}
