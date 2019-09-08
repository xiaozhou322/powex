package com.gt.dao;

// default package

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fvirtualpresalelog;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fvirtualpresalelog entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .Fvirtualpresalelog
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FvirtualpresalelogDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FvirtualpresalelogDAO.class);
	// property constants
	public static final String VERSION = "version";
	public static final String FQTY = "fqty";
	public static final String FSTATUS = "fstatus";
	public static final String FIS_SEND_MSG = "fisSendMsg";

	public void save(Fvirtualpresalelog transientInstance) {
		log.debug("saving Fvirtualpresalelog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fvirtualpresalelog persistentInstance) {
		log.debug("deleting Fvirtualpresalelog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fvirtualpresalelog findById(java.lang.Integer id) {
		log.debug("getting Fvirtualpresalelog instance with id: " + id);
		try {
			Fvirtualpresalelog instance = (Fvirtualpresalelog) getSession()
					.get("com.gt.entity.Fvirtualpresalelog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fvirtualpresalelog> findByExample(
			Fvirtualpresalelog instance) {
		log.debug("finding Fvirtualpresalelog instance by example");
		try {
			List<Fvirtualpresalelog> results = (List<Fvirtualpresalelog>) getSession()
					.createCriteria(
							"com.gt.entity.Fvirtualpresalelog")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fvirtualpresalelog instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fvirtualpresalelog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fvirtualpresalelog> findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List<Fvirtualpresalelog> findByFqty(Object fqty) {
		return findByProperty(FQTY, fqty);
	}

	public List<Fvirtualpresalelog> findByFstatus(Object fstatus) {
		return findByProperty(FSTATUS, fstatus);
	}

	public List<Fvirtualpresalelog> findByFisSendMsg(Object fisSendMsg) {
		return findByProperty(FIS_SEND_MSG, fisSendMsg);
	}

	public List findAll() {
		log.debug("finding all Fvirtualpresalelog instances");
		try {
			String queryString = "from Fvirtualpresalelog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fvirtualpresalelog merge(Fvirtualpresalelog detachedInstance) {
		log.debug("merging Fvirtualpresalelog instance");
		try {
			Fvirtualpresalelog result = (Fvirtualpresalelog) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fvirtualpresalelog instance) {
		log.debug("attaching dirty Fvirtualpresalelog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fvirtualpresalelog instance) {
		log.debug("attaching clean Fvirtualpresalelog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Fvirtualpresalelog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fvirtualpresalelog> list = null;
		log.debug("finding Fvirtualpresalelog instance with filter");
		try {
			String queryString = "from Fvirtualpresalelog "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fvirtualpresalelog by filter name failed", re);
			throw re;
		}
		return list;
	}
}