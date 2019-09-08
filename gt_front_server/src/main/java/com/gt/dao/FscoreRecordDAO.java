package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FscoreRecord;

/**
 * A data access object (DAO) providing persistence and search support for
 * FscoreRecord entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.gt.entity.FscoreRecord
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FscoreRecordDAO extends HibernateDaoSupport{
	private static final Logger log = LoggerFactory.getLogger(FscoreRecordDAO.class);
	// property constants
	public static final String TYPE = "type";
	public static final String SCORE = "score";
	public static final String REMARK = "remark";

	public void save(FscoreRecord transientInstance) {
		log.debug("saving FscoreRecord instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FscoreRecord persistentInstance) {
		log.debug("deleting FscoreRecord instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FscoreRecord findById(java.lang.Integer id) {
		log.debug("getting FscoreRecord instance with id: " + id);
		try {
			FscoreRecord instance = (FscoreRecord) getSession().get("com.gt.entity.FscoreRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FscoreRecord> findByExample(FscoreRecord instance) {
		log.debug("finding FscoreRecord instance by example");
		try {
			List<FscoreRecord> results = (List<FscoreRecord>) getSession().createCriteria("com.gt.entity.FscoreRecord")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FscoreRecord instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from FscoreRecord as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<FscoreRecord> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<FscoreRecord> findByScore(Object score) {
		return findByProperty(SCORE, score);
	}

	public List<FscoreRecord> findByRemark(Object remark) {
		return findByProperty(REMARK, remark);
	}

	public List findAll() {
		log.debug("finding all FscoreRecord instances");
		try {
			String queryString = "from FscoreRecord";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FscoreRecord merge(FscoreRecord detachedInstance) {
		log.debug("merging FscoreRecord instance");
		try {
			FscoreRecord result = (FscoreRecord) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FscoreRecord instance) {
		log.debug("attaching dirty FscoreRecord instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FscoreRecord instance) {
		log.debug("attaching clean FscoreRecord instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FscoreRecord> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<FscoreRecord> list = null;
		log.debug("finding FscoreRecord instance with filter");
		try {
			String queryString = "from FscoreRecord "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FscoreRecord name failed", re);
			throw re;
		}
		return list;
	}
}