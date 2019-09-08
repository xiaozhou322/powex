package com.gt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcUserPaytype;

/**
 * 
 * @author jqy
 *
 */
@Repository
public class FotcUserPaytypeDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FotcUserPaytypeDAO.class);

	public List<FotcUserPaytype> findAllPayType(String key, Object value) {
		log.debug("finding all Fotcuserpaytype instances");
		try {
			String queryString = "from FotcUserPaytype where "
					+ key + "= ? and status = 1 ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public Integer save(FotcUserPaytype obj) {
        log.debug("saving FotcUserPaytype instance");
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
	
	public void update(FotcUserPaytype obj) {
        log.debug("saving FotcUserPaytype instance");
        try {
            getSession().saveOrUpdate(obj);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
	
	public FotcUserPaytype queryById(Integer id) {
		log.debug("queryById FotcUserPaytype instance with id: " + id);
		try {
			FotcUserPaytype instance = (FotcUserPaytype) getSession().get(
					"com.gt.entity.FotcUserPaytype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List<Integer> queryPayTypeList(Integer fid) {
		log.debug("queryById queryPayTypeList instance with id: " + fid);
		List<Integer> list = new ArrayList<Integer>();
		try {
			String queryString = "select payType from FotcUserPaytype where fuser.fid=:fid";
			Query query = getSession().createQuery(queryString);
			query.setParameter("fid", fid);
			list = (List<Integer>)query.list();
			log.debug("get successful");
			return list;
		} catch (RuntimeException re) {
			list = new ArrayList<Integer>();
			log.error("get failed", re);
			throw re;
		}
	}
	
	public boolean queryisBindPayType(Integer fid) {
		log.debug("queryById FotcUserPaytype instance with id: " + fid);
		boolean flag = false;
		try {
			String queryString = "select count(f.id) from FotcUserPaytype f where f.fuser.fid=:fid";
			Query query = getSession().createQuery(queryString);
			query.setParameter("fid", fid);
			Long count = (Long)query.uniqueResult();
			log.debug("get successful");
			if(count > 0) {
				flag = true;
			}
			return flag;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public FotcUserPaytype queryisBindType(Integer fid, Integer payType) {
		log.debug("queryById FotcUserPaytype instance with id: " + fid);
		FotcUserPaytype fotcuserpaytype = null;
		try {
			String queryString = " from FotcUserPaytype where 1=1 and payType=:payType and fuser.fid=:fid ";
			Query query = getSession().createQuery(queryString);
			query.setParameter("fid", fid);
			query.setParameter("payType", payType);
			List list = query.list();
			if(list.size()>0) {
				fotcuserpaytype = (FotcUserPaytype)list.get(0); 
			}
			log.debug("get successful");
			return fotcuserpaytype;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<FotcUserPaytype> findAllPayTypeById(int fid) {
		log.debug("finding all FotcUserPaytype instances");
		try {
			String queryString = "from FotcUserPaytype f where f.fuser.fid=:fid";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("fid", fid);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public List<FotcUserPaytype> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<FotcUserPaytype> list = null;
		log.debug("finding FotcUserPaytype instance with filter");
		try {
			String queryString = " from FotcUserPaytype "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FotcUserPaytype name failed", re);
			throw re;
		}
		return list;
	}
}
