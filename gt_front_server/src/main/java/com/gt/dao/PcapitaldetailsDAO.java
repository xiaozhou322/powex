package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pad;
import com.gt.entity.Pcapitaldetails;

/**
 * 资产明细
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Repository
public class PcapitaldetailsDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(PcapitaldetailsDAO.class);

	public void save(Pcapitaldetails instance) {
		log.debug("saving Pcapitaldetails instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pcapitaldetails findById(java.lang.Integer id) {
		log.debug("getting Pcapitaldetails instance with id: " + id);
		try {
			Pcapitaldetails instance = (Pcapitaldetails) getSession().get(
					"com.gt.entity.Pcapitaldetails", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pcapitaldetails instance) {
		log.debug("attaching dirty Pcapitaldetails instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pcapitaldetails> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pcapitaldetails> list = null;
		log.debug("finding Pcapitaldetails instance with filter");
		try {
			String queryString = "from Pcapitaldetails "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Pcapitaldetails name failed", re);
			throw re;
		}
		return list;
	}
}
