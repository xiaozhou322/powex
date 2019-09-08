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
import com.gt.entity.Fentrustsnap;

/**
 	* A data access object (DAO) providing persistence and search support for Fasset entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.gt.entity.Fasset
  * @author MyEclipse Persistence Tools 
 */

@Repository
public class FentrustsnapDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(FentrustsnapDAO.class);
		//property constants
	public static final String VERSION = "version";
	public static final String FTOTAL = "ftotal";
	public static final String STATUS = "status";



    
    public void save(Fentrustsnap transientInstance) {
        log.debug("saving Fentrustsnap instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Fentrustsnap persistentInstance) {
        log.debug("deleting Fentrustsnap instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Fentrustsnap findById( java.lang.Integer id) {
        log.debug("getting Fentrustsnap instance with id: " + id);
        try {
        	Fentrustsnap instance = (Fentrustsnap) getSession()
                    .get("com.gt.entity.Fentrustsnap", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<Fentrustsnap> findByExample(Fentrustsnap instance) {
        log.debug("finding Fentrustsnap instance by example");
        try {
            List<Fentrustsnap> results = (List<Fentrustsnap>) getSession()
                    .createCriteria("com.gt.entity.Fentrustsnap")
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
      log.debug("finding Fentrustsnap instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Fentrustsnap as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List<Fentrustsnap> findByVersion(Object version
	) {
		return findByProperty(VERSION, version
		);
	}
	
	public List<Fentrustsnap> findByFtotal(Object ftotal
	) {
		return findByProperty(FTOTAL, ftotal
		);
	}
	
	public List<Fentrustsnap> findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all Fentrustsnap instances");
		try {
			String queryString = "from Fentrustsnap";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Fentrustsnap merge(Fentrustsnap detachedInstance) {
        log.debug("merging Fentrustsnap instance");
        try {
        	Fentrustsnap result = (Fentrustsnap) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Fentrustsnap instance) {
        log.debug("attaching dirty Fentrustsnap instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Fentrustsnap instance) {
        log.debug("attaching clean Fentrustsnap instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List<Fentrustsnap> getSnaptByDate(int first_result,int max_result,Date endDate){
		
		log.debug("getFentrustHistory all Fentrust instances");
		try {
			StringBuffer queryString = new StringBuffer("from Fentrustsnap where status=1 and ") ;
			
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
    
public List<Fentrustsnap> list(int first_result,int max_result,String filter, boolean isFY){
		
		log.debug("getFentrustHistory all Fentrustsnap instances");
		try {
			StringBuffer queryString = new StringBuffer("from Fentrustsnap ") ;
			queryString.append(filter) ;
			
			Query queryObject = getSession().createQuery(queryString.toString());
			if(isFY){
				queryObject.setFirstResult(first_result) ;
				queryObject.setMaxResults(max_result) ;
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
	}

	public List<Map> listIntro(Date calcday){
		
		List<Map> all = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(ifnull(famount,0)) total,fgtqty,ftotalamount,ftotalfee,fIntroUser,sum(ifnull(ffee,0)) fee,flastupdatetime from fentrustsnap \n");
		sql.append(" where fIntroUser is not null and DATE_FORMAT(flastupdatetime,'%Y-%m-%d') = ");
		sql.append(" '" + sdf.format(calcday) + "'");
		sql.append(" group by (fIntroUser) \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Object[] o = (Object[]) it.next();
			Map map = new HashMap();
			map.put("famount", o[0].toString());
			map.put("fgtqty", o[1].toString());
			map.put("ftotalamount", o[2].toString());
			map.put("ftotalfee", o[3].toString());
			map.put("fiuid", o[4].toString());
			map.put("ffee", o[5].toString());
			map.put("flastupdatetime", o[6].toString());
			all.add(map);
		}
		return all;
		
	}
}