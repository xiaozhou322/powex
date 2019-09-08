package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdomain;
import com.gt.entity.Psystemconfig;
import com.gt.entity.Ptrademapping;

/**
 * 项目方系统配置表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PsystemconfigDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PsystemconfigDAO.class);
	
	public void save(Psystemconfig instance) {
		log.debug("saving Psystemconfig instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Psystemconfig findById(java.lang.Integer id) {
		log.debug("getting Psystemconfig instance with id: " + id);
		try {
			Psystemconfig instance = (Psystemconfig) getSession().get(
					"com.gt.entity.Psystemconfig", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Psystemconfig instance) {
		log.debug("attaching dirty Psystemconfig instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Psystemconfig> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Psystemconfig> list = null;
		log.debug("finding Psystemconfig instance with filter");
		try {
			String queryString = "from Psystemconfig "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Psystemconfig name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public Psystemconfig findByProjectId(String filter) {
		log.debug("getting Psystemconfig instance with filter: " + filter);
		
		Psystemconfig systemconfig = null;
		try {
			String queryString = " from Psystemconfig " + filter ;
			Query queryObject = getSession().createQuery(queryString);
			List<Psystemconfig> systemconfigList = queryObject.list();
			if(null != systemconfigList && systemconfigList.size() > 0) {
				systemconfig = systemconfigList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return systemconfig;
	}
	
	
	public List findAll(String filter) {
		log.debug("finding all Psystemconfig instances");
		try {
			String queryString = " from Psystemconfig " + filter ;
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}
