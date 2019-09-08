package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fabout;
import com.gt.entity.Pad;
import com.gt.entity.Pcointype;
import com.gt.entity.Ptrademapping;

/**
 * 项目方交易市场表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PtrademappingDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PtrademappingDAO.class);
	
	
	public void save(Ptrademapping instance) {
		log.debug("saving Ptrademapping instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Ptrademapping findById(java.lang.Integer id) {
		log.debug("getting Ptrademapping instance with id: " + id);
		try {
			Ptrademapping instance = (Ptrademapping) getSession().get(
					"com.gt.entity.Ptrademapping", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Ptrademapping instance) {
		log.debug("attaching dirty Ptrademapping instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Ptrademapping> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Ptrademapping> list = null;
		log.debug("finding Ptrademapping instance with filter");
		try {
			String queryString = "from Ptrademapping "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Ptrademapping name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public Ptrademapping findByTrademappingId(Integer trademappingId) {
		log.debug("finding Ptrademapping instance with property");
		Ptrademapping ptrademapping = null;
		try {
			String queryString = "from Ptrademapping where tradeMappingId =" + trademappingId ;
			Query queryObject = getSession().createQuery(queryString);
			List<Ptrademapping> trademappingList = queryObject.list();
			if(null != trademappingList && trademappingList.size() > 0) {
				ptrademapping = trademappingList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return ptrademapping;
	}
}
