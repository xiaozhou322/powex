package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FbfscDataStatistics;

/**
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Repository
public class FbfscDataStatisticsDAO extends HibernateDaoSupport {

	private static final Logger log = LoggerFactory.getLogger(FbfscDataStatisticsDAO.class);

	public void save(FbfscDataStatistics instance) {
		log.debug("saving FbfscDataStatistics instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public FbfscDataStatistics findById(java.lang.Integer id) {
		log.debug("getting FbfscDataStatistics instance with id: " + id);
		try {
			FbfscDataStatistics instance = (FbfscDataStatistics) getSession().get(
					"com.gt.entity.FbfscDataStatistics", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(FbfscDataStatistics instance) {
		log.debug("attaching dirty FbfscDataStatistics instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FbfscDataStatistics> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FbfscDataStatistics> list = null;
		log.debug("finding FbfscDataStatistics instance with filter");
		try {
			String queryString = " from FbfscDataStatistics "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FbfscDataStatistics name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public FbfscDataStatistics findFbfscDataStatisticsByParam(String filter) {
		log.debug("finding FbfscDataStatistics instance with filter");
		FbfscDataStatistics bfscDataStatistics = null;
		try {
			String queryString = "from FbfscDataStatistics " + filter;
			Query queryObject = getSession().createQuery(queryString);
			
			List<FbfscDataStatistics> bfscDataStatisticsList = queryObject.list();
			if(null != bfscDataStatisticsList && bfscDataStatisticsList.size() > 0) {
				bfscDataStatistics = bfscDataStatisticsList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		return bfscDataStatistics;
	}
}
