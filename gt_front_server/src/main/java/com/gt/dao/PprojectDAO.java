package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pdomain;
import com.gt.entity.Pproject;
import com.gt.entity.Psystemconfig;

/**
 * 项目表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PprojectDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PprojectDAO.class);
	
	public void save(Pproject instance) {
		log.debug("saving Pproject instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pproject findById(java.lang.Integer id) {
		log.debug("getting Pproject instance with id: " + id);
		try {
			Pproject instance = (Pproject) getSession().get(
					"com.gt.entity.Pproject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pproject instance) {
		log.debug("attaching dirty Pproject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	public List<Pproject> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pproject> list = null;
		log.debug("finding Pproject instance with filter");
		try {
			String queryString = "from Pproject "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Pproject name failed", re);
			throw re;
		}
		return list;
	}
	
	public Pproject findByProperty(String propertyName, Object value) {
		log.debug("finding Pproject instance with property: " + propertyName + ", value: " + value);
		Pproject project = null;
		try {
			String queryString = "from Pproject as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			
			List<Pproject> projectList = queryObject.list();
			if(null != projectList && projectList.size() > 0) {
				project = projectList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		return project;
	}
}
