package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.LotteryPeriodsModel;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fabout entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see ztmp.Fabout
 * @author MyEclipse Persistence Tools
 */

@Repository
public class LotteryPeriodsDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(LotteryPeriodsDAO.class);
	// property constants
	public static final String FTITLE = "ftitle";
	public static final String FCONTENT = "fcontent";

	public void save(LotteryPeriodsModel transientInstance) {
		log.debug("saving LotteryPeriodsModel instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LotteryPeriodsModel persistentInstance) {
		log.debug("deleting LotteryPeriodsModel instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LotteryPeriodsModel findById(java.lang.Integer id) {
		log.debug("getting LotteryPeriodsModel instance with id: " + id);
		try {
			LotteryPeriodsModel instance = (LotteryPeriodsModel) getSession().get("com.gt.entity.LotteryPeriodsModel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<LotteryPeriodsModel> findByExample(LotteryPeriodsModel instance) {
		log.debug("finding LotteryPeriodsModel instance by example");
		try {
			List<LotteryPeriodsModel> results = (List<LotteryPeriodsModel>) getSession()
					.createCriteria("com.gt.entity.LotteryPeriodsModel").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding LotteryPeriodsModel instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from LotteryPeriodsModel as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2) {
		log.debug("finding LotteryPeriodsModel instance with property");
		try {
			String queryString = "from LotteryPeriodsModel as model where model."
					+ propertyName1 + "= ? and model."+propertyName2+"= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value1);
			queryObject.setParameter(1, value2);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<LotteryPeriodsModel> findByFtitle(Object ftitle) {
		return findByProperty(FTITLE, ftitle);
	}

	public List<LotteryPeriodsModel> findByFcontent(Object fcontent) {
		return findByProperty(FCONTENT, fcontent);
	}

	public List findAll() {
		log.debug("finding all LotteryPeriodsModel instances");
		try {
			String queryString = "from LotteryPeriodsModel";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LotteryPeriodsModel merge(LotteryPeriodsModel detachedInstance) {
		log.debug("merging LotteryPeriodsModel instance");
		try {
			LotteryPeriodsModel result = (LotteryPeriodsModel) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LotteryPeriodsModel instance) {
		log.debug("attaching dirty LotteryPeriodsModel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LotteryPeriodsModel instance) {
		log.debug("attaching clean LotteryPeriodsModel instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<LotteryPeriodsModel> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<LotteryPeriodsModel> list = null;
		log.debug("finding LotteryPeriodsModel instance with filter");
		try {
			String queryString = "from LotteryPeriodsModel "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by LotteryPeriodsModel name failed", re);
			throw re;
		}
		return list;
	}
	
}