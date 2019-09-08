package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdomain;

/**
 * 项目方域名表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PdomainDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PdomainDAO.class);
	
	public void save(Pdomain instance) {
		log.debug("saving Pdomain instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pdomain findById(java.lang.Integer id) {
		log.debug("getting Pdomain instance with id: " + id);
		try {
			Pdomain instance = (Pdomain) getSession().get(
					"com.gt.entity.Pdomain", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List<Pdomain> list(int firstResult, int maxResults,String filter,boolean isFY) {
		List<Pdomain> list = null;
		log.debug("finding Pdomain instance with filter");
		try {
			String queryString = "from Pdomain "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public Pdomain findByProperty(String propertyName, Object value) {
		log.debug("finding Pdomain instance with property: " + propertyName + ", value: " + value);
		Pdomain pdomain = null;
		try {
			String queryString = "from Pdomain as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			
			List<Pdomain> pdomainList = queryObject.list();
			if(null != pdomainList && pdomainList.size() > 0) {
				pdomain = pdomainList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		return pdomain;
	}
	
	
	public void attachDirty(Pdomain instance) {
		log.debug("attaching dirty Pdomain instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Pdomain findByProjectId(java.lang.Integer projectId) {
		log.debug("getting Pdomain instance with projectId: " + projectId);
		
		Pdomain pdomain = null;
		try {
			String queryString = "from Pdomain where projectId.fid =" + projectId ;
			Query queryObject = getSession().createQuery(queryString);
			List<Pdomain> pdomainList = queryObject.list();
			if(null != pdomainList && pdomainList.size() > 0) {
				pdomain = pdomainList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return pdomain;
	}
}
