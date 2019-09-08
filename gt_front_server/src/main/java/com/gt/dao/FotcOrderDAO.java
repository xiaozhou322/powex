package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcOrder;
import com.gt.util.DateHelper;

/**
 * 
 * @author jqy
 *
 */
@Repository
public class FotcOrderDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FotcOrderDAO.class);
	
	public Long getOrderCount(int fid) {
		log.debug("finding all FotcOrderCount instances");
		try {
			String hql = " select count(f.id) from FotcOrder f where f.fuser.fid=:fid ";
			Query query = getSession().createQuery(hql);
			query.setParameter("fid", fid);
			Long count = (Long)query.uniqueResult();
			return count;
		} catch (RuntimeException re) {
			log.error("find FotcOrder failed", re);
			throw re;
		}
	}
	
	public List<FotcOrder> findAllOrder(int fid, int pageSize, int pageIndex) {
		log.debug("finding all FotcOrder instances");
		try {
			String hql = "from FotcOrder f where f.fotcAdvertisement.user.fid=:fid ";
			Query query = getSession().createQuery(hql);
			query.setParameter("fid", fid);
			query.setFirstResult((pageIndex-1)*pageSize);
			query.setMaxResults(pageSize);
			List<FotcOrder> list = query.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findAll() {
		log.debug("finding all FotcOrder instances");
		try {
			String queryString = "from FotcOrder";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List<FotcOrder> findAllOrder(Integer userId) {
		log.debug("finding all FotcOrder instances");
		try {
			String queryString = "from FotcOrder where fuser.fid=:userId";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("userId", userId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Integer save(FotcOrder obj) {
        log.debug("saving FotcOrder instance");
        Integer ret = 0;
        try {
            ret = (Integer) getSession().save(obj);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
        return ret;
    }
	
	public FotcOrder queryById(Integer id) {
		log.debug("queryById FotcOrder instance with id: " + id);
		try {
			FotcOrder instance = (FotcOrder) getSession().get(
					"com.gt.entity.FotcOrder", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public void update(FotcOrder instance) {
		log.debug("attaching dirty FotcOrder instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<FotcOrder> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<FotcOrder> list = null;
		log.debug("finding FotcOrder instance with filter");
		try {
			String queryString = " from FotcOrder "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FotcOrder name failed", re);
			throw re;
		}
		return list;
	}

	public List<FotcOrder> findByProperty(String propertyName, Object value) {
		log.debug("finding FotcOrder instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FotcOrder as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List<FotcOrder> findByProperty1(String propertyName, Object value) {
		log.debug("finding FotcOrder instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FotcOrder as model where model."
					+ propertyName + "= ? and orderStatus in(101,102,103,104,105)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	
	public String queryAcceptAvgResptime(Integer fid) {
		log.debug("queryAcceptAvgResptime from  FotcOrder instances");
		try {
			String hql = " select AVG(o.respTime) from FotcOrder o  where o.orderStatus in (50, 60) and o.fotcAdvertisement.user.fid=:fid ";
			Query query = getSession().createQuery(hql);
			query.setParameter("fid", fid);
			Object seconds = query.uniqueResult();
			if(null == seconds) {
				return DateHelper.secondsConvertMinute(Math.round(0d));
			}
			return DateHelper.secondsConvertMinute(Math.round((Double)seconds));
		} catch (RuntimeException re) {
			log.error("find FotcOrderCount failed", re);
			throw re;
		}
	}
	
	
	public FotcOrder queryOrderByOrginOrderId(String orginOrderId) {
		log.debug("finding all FotcOrder instances");
		FotcOrder otcOrder = null;
		try {
			String hql = "from FotcOrder f where f.userOrderId=:userOrderId ";
			Query query = getSession().createQuery(hql);
			query.setParameter("userOrderId", orginOrderId);
			List<FotcOrder> list = query.list();
			if(null != list && list.size() > 0) {
				otcOrder = list.get(0);
			}
			return otcOrder;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}
