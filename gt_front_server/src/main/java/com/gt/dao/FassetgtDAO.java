package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fasset;
import com.gt.entity.Fassetgt;
import com.gt.entity.Fentrust;

/**
 	* A data access object (DAO) providing persistence and search support for Fasset entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.gt.entity.Fasset
  * @author MyEclipse Persistence Tools 
 */

@Repository
public class FassetgtDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(FassetgtDAO.class);
		//property constants
	public static final String VERSION = "version";
	public static final String FTOTAL = "ftotal";
	public static final String STATUS = "status";



    
    public void save(Fassetgt transientInstance) {
        log.debug("saving Fassetgt instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Fassetgt persistentInstance) {
        log.debug("deleting Fassetgt instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Fassetgt findById( java.lang.Integer id) {
        log.debug("getting Fassetgt instance with id: " + id);
        try {
        	Fassetgt instance = (Fassetgt) getSession()
                    .get("com.gt.entity.Fassetgt", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<Fassetgt> findByExample(Fassetgt instance) {
        log.debug("finding Fassetgt instance by example");
        try {
            List<Fassetgt> results = (List<Fassetgt>) getSession()
                    .createCriteria("com.gt.entity.Fassetgt")
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
      log.debug("finding Fassetgt instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Fassetgt as model where model." 
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
		log.debug("finding all Fassetgt instances");
		try {
			String queryString = "from Fassetgt";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Fassetgt merge(Fassetgt detachedInstance) {
        log.debug("merging Fassetgt instance");
        try {
        	Fassetgt result = (Fassetgt) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Fassetgt instance) {
        log.debug("attaching dirty Fassetgt instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Fassetgt instance) {
        log.debug("attaching clean Fassetgt instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List<Fassetgt> getAssetGtByDate(int first_result,int max_result,Date endDate){
		
		log.debug("getFentrustHistory all Fassetgt instances");
		try {
			StringBuffer queryString = new StringBuffer("from Fassetgt where status=1 and ") ;
			
			if(endDate!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
				queryString.append(" DATE_FORMAT(flastUpdatTime,'%Y-%m-%d') ='"+sdf.format(endDate)+"' and ") ;
			}

			queryString.append(" 1=1 ") ;
			
			Query queryObject = getSession().createQuery(queryString.toString());

			queryObject.setFirstResult(first_result) ;
			queryObject.setMaxResults(max_result) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
	}
    
    public List<Fassetgt> list(int first_result,int max_result,String filter){
		
		log.debug("getFentrustHistory all Fassetgt instances");
		try {
			StringBuffer queryString = new StringBuffer("from Fassetgt ") ;
			queryString.append(filter) ;
			
			Query queryObject = getSession().createQuery(queryString.toString());

			queryObject.setFirstResult(first_result) ;
			queryObject.setMaxResults(max_result) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
	}


}