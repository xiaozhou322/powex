package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcBlacklist;

/**
 * otc黑名单用户DAO
 * @author zhouyong
 *
 */
@Repository
public class FotcBlacklistDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FotcBlacklistDAO.class);

	public void save(FotcBlacklist instance) {
		log.debug("saving FotcBlacklist instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public FotcBlacklist findById(java.lang.Integer id) {
		log.debug("getting FotcBlacklist instance with id: " + id);
		try {
			FotcBlacklist instance = (FotcBlacklist) getSession().get(
					"com.gt.entity.FotcBlacklist", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(FotcBlacklist instance) {
		log.debug("attaching dirty FotcBlacklist instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FotcBlacklist> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FotcBlacklist> list = null;
		log.debug("finding FotcBlacklist instance with filter");
		try {
			String queryString = " from FotcBlacklist "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FotcBlacklist name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public FotcBlacklist findByUserId(java.lang.Integer userId) {
		log.debug("getting FotcBlacklist instance with userId: " + userId);
		
		FotcBlacklist otcBlacklist = null;
		try {
			String queryString = "from FotcBlacklist where userId.fid =" + userId ;
			Query queryObject = getSession().createQuery(queryString);
			List<FotcBlacklist> fotcBlackList = queryObject.list();
			if(null != fotcBlackList && fotcBlackList.size() > 0) {
				otcBlacklist = fotcBlackList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return otcBlacklist;
	}
}
