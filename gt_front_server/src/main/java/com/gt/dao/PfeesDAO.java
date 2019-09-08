package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pfees;
import com.gt.entity.Pproject;

/**
 * 项目方手续费率表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PfeesDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PfeesDAO.class);
	
	public void save(Pfees instance) {
		log.debug("saving Pproject instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pfees findById(java.lang.Integer id) {
		log.debug("getting Pfees instance with id: " + id);
		try {
			Pfees instance = (Pfees) getSession().get(
					"com.gt.entity.Pfees", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pfees instance) {
		log.debug("attaching dirty Pfees instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pfees> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pfees> list = null;
		log.debug("finding Pfees instance with filter");
		try {
			String queryString = "from Pfees "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Pfees name failed", re);
			throw re;
		}
		return list;
	}
}
