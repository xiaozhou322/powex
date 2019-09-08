package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.PcwTotalStatistics;
import com.gt.entity.Pdomain;

/**
 * 
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Repository
public class PcwTotalStatisticsDAO extends HibernateDaoSupport {

	private static final Logger log = LoggerFactory.getLogger(PcwTotalStatisticsDAO.class);

	public void save(PcwTotalStatistics instance) {
		log.debug("saving PcwTotalStatistics instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public PcwTotalStatistics findById(java.lang.Integer id) {
		log.debug("getting PcwTotalStatistics instance with id: " + id);
		try {
			PcwTotalStatistics instance = (PcwTotalStatistics) getSession().get(
					"com.gt.entity.PcwTotalStatistics", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(PcwTotalStatistics instance) {
		log.debug("attaching dirty PcwTotalStatistics instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<PcwTotalStatistics> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<PcwTotalStatistics> list = null;
		log.debug("finding PcwTotalStatistics instance with filter");
		try {
			String queryString = "from PcwTotalStatistics "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by PcwTotalStatistics name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public PcwTotalStatistics findCwTotalStatisticsByParam(String filter) {
		log.debug("finding PcwTotalStatistics instance with filter");
		PcwTotalStatistics cwTotalStatistics = null;
		try {
			String queryString = "from PcwTotalStatistics " + filter;
			Query queryObject = getSession().createQuery(queryString);
			
			List<PcwTotalStatistics> cwTotalStatisticsList = queryObject.list();
			if(null != cwTotalStatisticsList && cwTotalStatisticsList.size() > 0) {
				cwTotalStatistics = cwTotalStatisticsList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		return cwTotalStatistics;
	}
}
