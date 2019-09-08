package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcOrderWalletRecord;

/**
 * 
 * @author jqy
 *
 */
@Repository
public class FotcOrderWalletRecordDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FotcOrderWalletRecordDAO.class);
	
	public Long getOrderCount(int fid) {
		log.debug("finding all FotcOrderWalletRecordCount instances");
		try {
			String hql = " select count(f.id) from FotcOrderWalletRecord f where f.fuser.fid=:fid ";
			Query query = getSession().createQuery(hql);
			query.setParameter("fid", fid);
			Long count = (Long)query.uniqueResult();
			return count;
		} catch (RuntimeException re) {
			log.error("find FotcOrderWalletRecordCount failed", re);
			throw re;
		}
	}
	
	public List<FotcOrderWalletRecord> findAllOrder(int fid, int pageSize, int pageIndex) {
		log.debug("finding all ForderWalletRecord instances");
		try {
			String hql = "from FotcOrderWalletRecord f where f.fotcAdvertisement.user.fid=:fid ";
			Query query = getSession().createQuery(hql);
			query.setParameter("fid", fid);
			query.setFirstResult((pageIndex-1)*pageSize);
			query.setMaxResults(pageSize);
			List<FotcOrderWalletRecord> list = query.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<FotcOrderWalletRecord> findAllOrder(Integer userId) {
		log.debug("finding all FotcOrderWalletRecord instances");
		try {
			String queryString = "from FotcOrderWalletRecord where fuser.fid=:userId";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("userId", userId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Integer save(FotcOrderWalletRecord obj) {
        log.debug("saving FotcOrderWalletRecord instance");
        Integer ret = 0;
        try {
        	ret=(Integer) getSession().save(obj);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            re.printStackTrace();
        }
        return ret;
    }
	
	public FotcOrderWalletRecord queryById(Integer id) {
		log.debug("queryById FotcOrderWalletRecord instance with id: " + id);
		try {
			FotcOrderWalletRecord instance = (FotcOrderWalletRecord) getSession().get(
					"com.gt.entity.FotcOrderWalletRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public void update(FotcOrderWalletRecord instance) {
		log.debug("attaching dirty FotcOrderWalletRecord instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<FotcOrderWalletRecord> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<FotcOrderWalletRecord> list = null;
		log.debug("finding FotcOrderWalletRecord instance with filter");
		try {
			String queryString = " from FotcOrderWalletRecord "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FotcOrderWalletRecord name failed", re);
			throw re;
		}
		return list;
	}

	public List<FotcOrderWalletRecord> findByProperty(String propertyName, Object value) {
		log.debug("finding FotcOrderWalletRecord instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FotcOrderWalletRecord as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	
	
}
