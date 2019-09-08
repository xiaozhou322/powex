package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pad;
import com.gt.entity.Pcointype;
import com.gt.entity.Pdeposit;

/**
 * 项目方公告表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:33
 */
@Repository
public class PadDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PadDAO.class);

	public void save(Pad instance) {
		log.debug("saving Pad instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pad findById(java.lang.Integer id) {
		log.debug("getting Pad instance with id: " + id);
		try {
			Pad instance = (Pad) getSession().get(
					"com.gt.entity.Pad", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pad instance) {
		log.debug("attaching dirty Pad instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pad> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pad> list = null;
		log.debug("finding Pad instance with filter");
		try {
			String queryString = "from Pad "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Pad name failed", re);
			throw re;
		}
		return list;
	}
	
}
