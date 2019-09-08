package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FlevelSetting;

/**
 * A data access object (DAO) providing persistence and search support for
 * FlevelSetting entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.gt.entity.FlevelSetting
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FlevelSettingDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FlevelSettingDAO.class);
	// property constants
	public static final String VERSION = "version";
	public static final String LEVEL = "level";
	public static final String SCORE = "score";

	public void save(FlevelSetting transientInstance) {
		log.debug("saving FlevelSetting instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FlevelSetting persistentInstance) {
		log.debug("deleting FlevelSetting instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FlevelSetting findById(java.lang.Integer id) {
		log.debug("getting FlevelSetting instance with id: " + id);
		try {
			FlevelSetting instance = (FlevelSetting) getSession().get("com.gt.entity.FlevelSetting", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FlevelSetting> findByExample(FlevelSetting instance) {
		log.debug("finding FlevelSetting instance by example");
		try {
			List<FlevelSetting> results = (List<FlevelSetting>) getSession().createCriteria("com.gt.entity.FlevelSetting")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FlevelSetting instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from FlevelSetting as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<FlevelSetting> findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List<FlevelSetting> findByLevel(Object level) {
		return findByProperty(LEVEL, level);
	}

	public List<FlevelSetting> findByScore(Object score) {
		return findByProperty(SCORE, score);
	}

	public List findAll() {
		log.debug("finding all FlevelSetting instances");
		try {
			String queryString = "from FlevelSetting";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FlevelSetting merge(FlevelSetting detachedInstance) {
		log.debug("merging FlevelSetting instance");
		try {
			FlevelSetting result = (FlevelSetting) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FlevelSetting instance) {
		log.debug("attaching dirty FlevelSetting instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FlevelSetting instance) {
		log.debug("attaching clean FlevelSetting instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FlevelSetting> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FlevelSetting> list = null;
		log.debug("finding FlevelSetting instance with filter");
		try {
			String queryString = "from FlevelSetting "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FlevelSetting name failed", re);
			throw re;
		}
		return list;
	}
}