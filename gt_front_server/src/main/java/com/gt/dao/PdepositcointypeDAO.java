package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pdeposit;
import com.gt.entity.Pdepositcointype;

/**
 * 保证金币种表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PdepositcointypeDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PdepositcointypeDAO.class);

	public Pdepositcointype findById(java.lang.Integer id) {
		log.debug("getting Pdepositcointype instance with id: " + id);
		try {
			Pdepositcointype instance = (Pdepositcointype) getSession().get(
					"com.gt.entity.Pdepositcointype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

    public List<Pdepositcointype> findByProperty(String propertyName, Object value) {
        log.debug("finding Pdepositcointype instance with property: " + propertyName
              + ", value: " + value);
        try {
           String queryString = "from Pdepositcointype as model where model." 
           						+ propertyName + "= ?";
           Query queryObject = getSession().createQuery(queryString);
  		 queryObject.setParameter(0, value);
  		 return queryObject.list();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    
	public List<Pdepositcointype> findByParam(String filter) {
		List<Pdepositcointype> list = null;
		log.debug("finding Pdepositcointype instance with filter");
		try {
			String queryString = "from Pdepositcointype "+filter;
			Query queryObject = getSession().createQuery(queryString);
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public List<Pdepositcointype> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pdepositcointype> list = null;
		log.debug("finding Pdepositcointype instance with filter");
		try {
			String queryString = "from Pdepositcointype "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Pdepositcointype name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public void attachDirty(Pdepositcointype instance) {
		log.debug("attaching dirty Pdepositcointype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}
