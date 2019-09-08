package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.ConvertVirtualCoin;

@Repository
public class ConvertVirtualCoinDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(ConvertVirtualCoinDAO.class);

	public void save(ConvertVirtualCoin instance) {
		log.debug("saving ConvertVirtualCoin instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public ConvertVirtualCoin findById(java.lang.Integer id) {
		log.debug("getting ConvertVirtualCoin instance with id: " + id);
		try {
			ConvertVirtualCoin instance = (ConvertVirtualCoin) getSession().get(
					"com.gt.entity.ConvertVirtualCoin", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(ConvertVirtualCoin instance) {
		log.debug("attaching dirty ConvertVirtualCoin instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<ConvertVirtualCoin> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<ConvertVirtualCoin> list = null;
		log.debug("finding ConvertVirtualCoin instance with filter");
		try {
			String queryString = " from ConvertVirtualCoin "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by ConvertVirtualCoin name failed", re);
			throw re;
		}
		return list;
	}
	
}
