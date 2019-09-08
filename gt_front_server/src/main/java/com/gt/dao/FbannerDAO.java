package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fbanner;

/**
 	* A data access object (DAO) providing persistence and search support for Farticle entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.gt.entity.Farticle
  * @author MyEclipse Persistence Tools 
 */
@Repository
public class FbannerDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(FbannerDAO.class);
		//property constants
	public static final String FTITLE = "ftitle";



    
    public void save(Fbanner transientInstance) {
        log.debug("saving Fbanner instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Fbanner persistentInstance) {
        log.debug("deleting Fbanner instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Fbanner findById( java.lang.Integer id) {
        log.debug("getting Fbanner instance with id: " + id);
        try {
        	Fbanner instance = (Fbanner) getSession()
                    .get("com.gt.entity.Fbanner", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<Fbanner> findByExample(Fbanner instance) {
        log.debug("finding Farticle instance by example");
        try {
            List<Fbanner> results = (List<Fbanner>) getSession()
                    .createCriteria("com.gt.entity.Fbanner")
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
      log.debug("finding Farticle instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Fbanner as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List<Fbanner> findByFtitle(Object ftitle
	) {
		return findByProperty(FTITLE, ftitle
		);
	}
	

	public List findAll() {
		log.debug("finding all Farticle instances");
		try {
			String queryString = "from Fbanner";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Fbanner merge(Fbanner detachedInstance) {
        log.debug("merging Farticle instance");
        try {
        	Fbanner result = (Fbanner) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Fbanner instance) {
        log.debug("attaching dirty Farticle instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Fbanner instance) {
        log.debug("attaching clean Farticle instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
	public List<Fbanner> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fbanner> list = null;
		log.debug("finding Fbanner instance with filter");
		try {
			String queryString = "from Fbanner "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Farticle by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public List<Fbanner> findFbanner(String fbannertype,int firstResult,int maxResult){
		log.debug("findFarticle all Farticle instances");
		try {
			String queryString = "from Fbanner where ftype=? and fstatus=1 order by fisding desc,fLastModifyDate desc";
	         Query queryObject = getSession().createQuery(queryString);
	         queryObject.setCacheable(true) ;
	         queryObject.setParameter(0, fbannertype) ;
	         queryObject.setFirstResult(firstResult) ;
	         queryObject.setMaxResults(maxResult) ;
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public int findFbannerCount(String fbannertype){
		log.debug("findFarticleCount all Fbanner instances");
		try {
			String queryString = "select count(*) from Fbanner where ftype=? order by id desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			queryObject.setParameter(0, fbannertype) ;
			long l=(Long) queryObject.list().get(0) ;
			return (int) l ;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}