package com.gt.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fconvertcointype;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fconvertcointype entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.gt.entity.Fconvertcointype
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FconvertcointypeDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FconvertcointypeDAO.class);
	
	// property constants
	public static final String FNAME = "fname";

	public void save(Fconvertcointype transientInstance) {
		log.debug("saving Fconvertcointype instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fconvertcointype persistentInstance) {
		log.debug("deleting Fconvertcointype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fconvertcointype findById(java.lang.Integer id) {
		log.debug("getting Fconvertcointype instance with id: " + id);
		try {
			Fconvertcointype instance = (Fconvertcointype) getSession().get(
					"com.gt.entity.Fconvertcointype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fconvertcointype instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Fconvertcointype as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fconvertcointype> findByFname(Object fname) {
		return findByProperty(FNAME, fname);
	}


	//selectType 0是=号，1是<>号
	public List findAll(int coinType,int selectType) {
		log.debug("finding all Fconvertcointype instances");
		try {
			String eq = "=";
			if(selectType ==1){
				eq="<>";
			}
			String queryString = "from Fconvertcointype where ftype"+eq+"?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, coinType) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	//selectType 0是=号，1是<>号
	public List findAll(int coinType1,int coinType2,int selectType) {
		log.debug("finding all Fconvertcointype instances");
		try {
			String eq = "=";
			if(selectType ==1){
				eq="<>";
			}
			String queryString = "from Fconvertcointype where ftype"+eq+"? and ftype"+eq +"?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, coinType1) ;
			queryObject.setParameter(1, coinType2) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAll() {
		log.debug("finding all Fconvertcointype instances");
		try {
			String queryString = "from Fconvertcointype ";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fconvertcointype merge(Fconvertcointype detachedInstance) {
		log.debug("merging Fconvertcointype instance");
		try {
			Fconvertcointype result = (Fconvertcointype) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fconvertcointype instance) {
		log.debug("attaching dirty Fconvertcointype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fconvertcointype instance) {
		log.debug("attaching clean Fconvertcointype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public boolean isTrue(String sql) {
		boolean flag = false;
		Query queryObject = getSession().createSQLQuery(sql);
		if(queryObject.list().size() > 0){
			flag = true;
		}
		return flag;
	}
	
	public List<Fconvertcointype> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fconvertcointype> list = null;
		log.debug("finding Fconvertcointype instance with filter");
		try {
			String queryString = "from Fconvertcointype "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fconvertcointype by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public List findAllNormal(int selectType) {
		log.debug("finding all Fconvertcointype instances");
		try {
			String queryString = "from Fconvertcointype where ftype <> 0 and fstatus=?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, selectType) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}