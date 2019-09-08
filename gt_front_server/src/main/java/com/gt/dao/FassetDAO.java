package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fasset;

/**
 	* A data access object (DAO) providing persistence and search support for Fasset entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.gt.entity.Fasset
  * @author MyEclipse Persistence Tools 
 */

@Repository
public class FassetDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(FassetDAO.class);
		//property constants
	public static final String VERSION = "version";
	public static final String FTOTAL = "ftotal";
	public static final String STATUS = "status";



    
    public void save(Fasset transientInstance) {
        log.debug("saving Fasset instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Fasset persistentInstance) {
        log.debug("deleting Fasset instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Fasset findById( java.lang.Integer id) {
        log.debug("getting Fasset instance with id: " + id);
        try {
            Fasset instance = (Fasset) getSession()
                    .get("com.gt.entity.Fasset", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<Fasset> findByExample(Fasset instance) {
        log.debug("finding Fasset instance by example");
        try {
            List<Fasset> results = (List<Fasset>) getSession()
                    .createCriteria("com.gt.entity.Fasset")
                    .add( create(instance) )
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Fasset instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Fasset as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List<Fasset> findByVersion(Object version
	) {
		return findByProperty(VERSION, version
		);
	}
	
	public List<Fasset> findByFtotal(Object ftotal
	) {
		return findByProperty(FTOTAL, ftotal
		);
	}
	
	public List<Fasset> findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all Fasset instances");
		try {
			String queryString = "from Fasset";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Fasset merge(Fasset detachedInstance) {
        log.debug("merging Fasset instance");
        try {
            Fasset result = (Fasset) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Fasset instance) {
        log.debug("attaching dirty Fasset instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Fasset instance) {
        log.debug("attaching clean Fasset instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}