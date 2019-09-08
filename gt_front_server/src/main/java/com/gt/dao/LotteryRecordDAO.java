package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.LotteryRecordModel;

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
public class LotteryRecordDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(LotteryRecordDAO.class);
	// property constants
	public static final String FTITLE = "ftitle";
	public static final String FCONTENT = "fcontent";

	public void save(LotteryRecordModel transientInstance) {
		log.debug("saving LotteryRecordModel instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LotteryRecordModel persistentInstance) {
		log.debug("deleting LotteryRecordModel instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LotteryRecordModel findById(java.lang.Integer id) {
		log.debug("getting LotteryRecordModel instance with id: " + id);
		try {
			LotteryRecordModel instance = (LotteryRecordModel) getSession().get("com.gt.entity.LotteryRecordModel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<LotteryRecordModel> findByExample(LotteryRecordModel instance) {
		log.debug("finding LotteryRecordModel instance by example");
		try {
			List<LotteryRecordModel> results = (List<LotteryRecordModel>) getSession()
					.createCriteria("com.gt.entity.LotteryRecordModel").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding LotteryRecordModel instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from LotteryRecordModel as model where model."
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
		log.debug("finding LotteryRecordModel instance with property");
		try {
			String queryString = "from LotteryRecordModel as model where model."
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
	
	public List<LotteryRecordModel> findByFtitle(Object ftitle) {
		return findByProperty(FTITLE, ftitle);
	}

	public List<LotteryRecordModel> findByFcontent(Object fcontent) {
		return findByProperty(FCONTENT, fcontent);
	}

	public List findAll() {
		log.debug("finding all LotteryRecordModel instances");
		try {
			String queryString = "from LotteryRecordModel";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LotteryRecordModel merge(LotteryRecordModel detachedInstance) {
		log.debug("merging LotteryRecordModel instance");
		try {
			LotteryRecordModel result = (LotteryRecordModel) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LotteryRecordModel instance) {
		log.debug("attaching dirty LotteryRecordModel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LotteryRecordModel instance) {
		log.debug("attaching clean LotteryRecordModel instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<LotteryRecordModel> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<LotteryRecordModel> list = null;
		log.debug("finding LotteryRecordModel instance with filter");
		try {
			String queryString = "from LotteryRecordModel "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by LotteryRecordModel name failed", re);
			throw re;
		}
		return list;
	}
	
}