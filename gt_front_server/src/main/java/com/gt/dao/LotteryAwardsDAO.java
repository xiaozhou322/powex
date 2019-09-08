package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.LotteryAwardsModel;

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
public class LotteryAwardsDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(LotteryAwardsDAO.class);
	// property constants
	public static final String FTITLE = "ftitle";
	public static final String FCONTENT = "fcontent";

	public void save(LotteryAwardsModel transientInstance) {
		log.debug("saving LotteryAwardsModel instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LotteryAwardsModel persistentInstance) {
		log.debug("deleting LotteryAwardsModel instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LotteryAwardsModel findById(java.lang.Integer id) {
		log.debug("getting LotteryAwardsModel instance with id: " + id);
		try {
			LotteryAwardsModel instance = (LotteryAwardsModel) getSession().get("com.gt.entity.LotteryAwardsModel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<LotteryAwardsModel> findByExample(LotteryAwardsModel instance) {
		log.debug("finding LotteryAwardsModel instance by example");
		try {
			List<LotteryAwardsModel> results = (List<LotteryAwardsModel>) getSession()
					.createCriteria("com.gt.entity.LotteryAwardsModel").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding LotteryAwardsModel instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from LotteryAwardsModel as model where model."
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
		log.debug("finding LotteryAwardsModel instance with property");
		try {
			String queryString = "from LotteryAwardsModel as model where model."
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
	public List<LotteryAwardsModel> findByFtitle(Object ftitle) {
		return findByProperty(FTITLE, ftitle);
	}

	public List<LotteryAwardsModel> findByFcontent(Object fcontent) {
		return findByProperty(FCONTENT, fcontent);
	}

	public List findAll() {
		log.debug("finding all LotteryAwardsModel instances");
		try {
			String queryString = "from LotteryAwardsModel";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LotteryAwardsModel merge(LotteryAwardsModel detachedInstance) {
		log.debug("merging LotteryAwardsModel instance");
		try {
			LotteryAwardsModel result = (LotteryAwardsModel) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LotteryAwardsModel instance) {
		log.debug("attaching dirty LotteryAwardsModel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LotteryAwardsModel instance) {
		log.debug("attaching clean LotteryAwardsModel instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<LotteryAwardsModel> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<LotteryAwardsModel> list = null;
		log.debug("finding LotteryAwardsModel instance with filter");
		try {
			String queryString = "from LotteryAwardsModel "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by LotteryAwardsModel name failed", re);
			throw re;
		}
		return list;
	}
	
}