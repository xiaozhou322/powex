package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.PcwTotalStatistics;
import com.gt.entity.PdataStatistics;

/**
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Repository
public class PdataStatisticsDAO extends HibernateDaoSupport {

	private static final Logger log = LoggerFactory.getLogger(PdataStatisticsDAO.class);

	public void save(PdataStatistics instance) {
		log.debug("saving PdataStatistics instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public PdataStatistics findById(java.lang.Integer id) {
		log.debug("getting PchargeWithdrawStatistics instance with id: " + id);
		try {
			PdataStatistics instance = (PdataStatistics) getSession().get(
					"com.gt.entity.PdataStatistics", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(PdataStatistics instance) {
		log.debug("attaching dirty PdataStatistics instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<PdataStatistics> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<PdataStatistics> list = null;
		log.debug("finding PdataStatistics instance with filter");
		try {
			String queryString = " from PdataStatistics "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by PdataStatistics name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public PdataStatistics findPdataStatisticsByParam(String filter) {
		log.debug("finding PcwTotalStatistics instance with filter");
		PdataStatistics pdataStatistics = null;
		try {
			String queryString = "from PdataStatistics " + filter;
			Query queryObject = getSession().createQuery(queryString);
			
			List<PdataStatistics> pdataStatisticsList = queryObject.list();
			if(null != pdataStatisticsList && pdataStatisticsList.size() > 0) {
				pdataStatistics = pdataStatisticsList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		return pdataStatistics;
	}
}
