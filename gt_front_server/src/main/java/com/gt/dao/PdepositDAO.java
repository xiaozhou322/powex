package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pdeposit;

/**
 * 保证金表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PdepositDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PdepositDAO.class);
	
	public void save(Pdeposit instance) {
		log.debug("saving Pdeposit instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pdeposit findById(java.lang.Integer id) {
		log.debug("getting Pdeposit instance with id: " + id);
		try {
			Pdeposit instance = (Pdeposit) getSession().get(
					"com.gt.entity.Pdeposit", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pdeposit instance) {
		log.debug("attaching dirty Pdeposit instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pdeposit> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pdeposit> list = null;
		log.debug("finding Pdeposit instance with filter");
		try {
			String queryString = "from Pdeposit "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Pdeposit name failed", re);
			throw re;
		}
		return list;
	}
}
