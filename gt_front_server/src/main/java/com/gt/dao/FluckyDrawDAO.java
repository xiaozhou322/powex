package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FluckyDrawModel;

/**
 * A data access object (DAO) providing persistence and search support for
 * FluckyDrawModel entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see ztmp.FluckyDrawModel
 * @author MyEclipse Persistence Tools
 */

@Repository
public class FluckyDrawDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FluckyDrawDAO.class);
	// property constants
	public static final String FTITLE = "ftitle";
	public static final String FCONTENT = "fcontent";

	public FluckyDrawModel insertLottery(FluckyDrawModel transientInstance) {
		log.debug("saving FluckyDrawModel instance");
		try {
			FluckyDrawModel instance = (FluckyDrawModel) getSession().save(transientInstance);
			return instance;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public FluckyDrawModel findById(java.lang.Integer id) {
		log.debug("getting FluckyDrawModel instance with id: " + id);
		try {
			FluckyDrawModel instance = (FluckyDrawModel) getSession().get("com.gt.entity.FluckyDrawModel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}



	public List findAll() {
		log.debug("finding all FluckyDrawModel instances");
		try {
			String queryString = "from FluckyDrawModel";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}



	public void attachDirty(FluckyDrawModel instance) {
		log.debug("attaching dirty FluckyDrawModel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<FluckyDrawModel> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FluckyDrawModel> list = null;
		log.debug("finding FluckyDrawModel instance with filter");
		try {
			String queryString = "from FluckyDrawModel "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FluckyDrawModel name failed", re);
			throw re;
		}
		return list;
	}
	
}