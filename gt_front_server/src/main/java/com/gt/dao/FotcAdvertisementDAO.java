package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcAdvertisement;

/**
 * A data access object (DAO) providing persistence and search support for
 * Farticle entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.gt.entity.Farticle
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FotcAdvertisementDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FotcAdvertisementDAO.class);

	public Long getAdvertisementCount(int fid) {
		log.debug("finding all FotcAdvertisementCount instances");
		try {
			String hql = " select count(f.id) from FotcAdvertisement f where f.user.fid=:fid ";
			Query query = getSession().createQuery(hql);
			query.setParameter("fid", fid);
			Long count = (Long)query.uniqueResult();
			return count;
		} catch (RuntimeException re) {
			log.error("find FotcAdvertisementCount failed", re);
			throw re;
		}
	}
	
	public List<FotcAdvertisement> findAllAdvertisement(int fid, int pageSize, int pageIndex) {
		log.debug("finding all Fotcorder instances");
		try {
			String hql = "from FotcAdvertisement f where f.user.fid=:fid";
			Query query = getSession().createQuery(hql);
			query.setParameter("fid", fid);
			query.setFirstResult((pageIndex-1)*pageSize);
			query.setMaxResults(pageSize);
			List<FotcAdvertisement> list = query.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public List<FotcAdvertisement> queryOtcAdvertisementByPage(FotcAdvertisement obj, int firstResult,int maxResult){
		log.debug("queryOtcAdvertisementByPage all fotcAdvertisement instances");
		try {
			String queryString = "from FotcAdvertisement where fvirtualcointype.fid=? and ad_type=? and repertory_count>=? and user.fid in ("+obj.getOnlineUserList()+") and status=0 order by id desc";
	         Query queryObject = getSession().createQuery(queryString);
	         queryObject.setCacheable(true) ;
	         queryObject.setParameter(0, obj.getFvirtualcointype().getFid()) ;
	         queryObject.setParameter(1, obj.getAd_type()) ;
	         queryObject.setParameter(2, obj.getRepertory_count()) ;
	         queryObject.setFirstResult(firstResult) ;
	         queryObject.setMaxResults(maxResult) ;
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	 
	
	public int queryOtcAdvertisementCount(FotcAdvertisement obj){
		log.debug("queryOtcAdvertisementCount all fotcAdvertisement instances");
		try {
			String queryString = "select count(*) from FotcAdvertisement where fvirtualcointype.fid=? and ad_type=? and repertory_count>=? and user.fid in ("+obj.getOnlineUserList()+") and status=0 order by id desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true) ;
			queryObject.setParameter(0, obj.getFvirtualcointype().getFid()) ;
	         queryObject.setParameter(1, obj.getAd_type()) ;
	         queryObject.setParameter(2, obj.getRepertory_count()) ;
			long l=(Long) queryObject.list().get(0) ;
			return (int) l ;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
    
    public void save(FotcAdvertisement transientInstance) {
        log.debug("saving Farticle instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    
    public FotcAdvertisement queryById(Integer id) {
		log.debug("queryById FotcAdvertisement instance with id: " + id);
		try {
			FotcAdvertisement instance = (FotcAdvertisement) getSession().get(
					"com.gt.entity.FotcAdvertisement", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
    
    
    public void update(FotcAdvertisement instance) {
		log.debug("attaching dirty FotcAdvertisement instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

    public void merge(FotcAdvertisement instance) {
 		log.debug("attaching dirty FotcAdvertisement instance");
 		try {
 			getSession().merge(instance);
 			log.debug("attach successful");
 		} catch (RuntimeException re) {
 			log.error("attach failed", re);
 			throw re;
 		}
 	}
    
	public List<FotcAdvertisement> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<FotcAdvertisement> list = null;
		log.debug("finding FotcAdvertisement instance with filter");
		try {
			String queryString = " from FotcAdvertisement "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FotcAdvertisement name failed", re);
			throw re;
		}
		return list;
	}
    
}