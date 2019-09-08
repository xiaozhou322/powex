package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Freleaselog;

/**
 	* A data access object (DAO) providing persistence and search support for Fasset entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.gt.entity.Fasset
  * @author MyEclipse Persistence Tools 
 */

@Repository
public class FreleaselogDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(FreleaselogDAO.class);
		//property constants
	public static final String FTYPE = "freleasetype";



    
    public void save(Freleaselog transientInstance) {
        log.debug("saving Freleaselog instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Freleaselog persistentInstance) {
        log.debug("deleting Freleaselog instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Freleaselog findById( java.lang.Integer id) {
        log.debug("getting Fassetgt instance with id: " + id);
        try {
        	Freleaselog instance = (Freleaselog) getSession()
                    .get("com.gt.entity.Freleaselog", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<Freleaselog> findByExample(Freleaselog instance) {
        log.debug("finding Freleaselog instance by example");
        try {
            List<Freleaselog> results = (List<Freleaselog>) getSession()
                    .createCriteria("com.gt.entity.Freleaselog")
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
      log.debug("finding Freleaselog instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Freleaselog as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	
	public List<Freleaselog> findByFtype(Object ftype
	) {
		return findByProperty(FTYPE, ftype
		);
	}
	

	public List findAll() {
		log.debug("finding all Freleaselog instances");
		try {
			String queryString = "from Freleaselog";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Freleaselog merge(Freleaselog detachedInstance) {
        log.debug("merging Freleaselog instance");
        try {
        	Freleaselog result = (Freleaselog) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Freleaselog instance) {
        log.debug("attaching dirty Freleaselog instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Freleaselog instance) {
        log.debug("attaching clean Freleaselog instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List<Freleaselog> getReleaseLogByDate(int first_result,int max_result,Date endDate){
		
		log.debug("getFentrustHistory all Freleaselog instances");
		try {
			StringBuffer queryString = new StringBuffer("from Freleaselog where status=1 and ") ;
			
			if(endDate!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
				queryString.append(" DATE_FORMAT(fcreatetime,'%Y-%m-%d') ='"+sdf.format(endDate)+"' and ") ;
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
    
public List<Freleaselog> list(int first_result,int max_result,String filter,boolean isFY){
		
		log.debug("get Freleaselog all Freleaselog instances");
		try {
			StringBuffer queryString = new StringBuffer("from Freleaselog ") ;
			queryString.append(filter) ;
			
			Query queryObject = getSession().createQuery(queryString.toString());
			if(isFY){
				queryObject.setFirstResult(first_result) ;
				queryObject.setMaxResults(max_result) ;
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			re.printStackTrace();
			throw re;
		}
		
	}
}