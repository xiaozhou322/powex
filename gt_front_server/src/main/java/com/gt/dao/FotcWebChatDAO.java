package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcWebChat;
@Repository
public class FotcWebChatDAO extends HibernateDaoSupport {

	private static final Logger log = LoggerFactory.getLogger(FotcWebChatDAO.class);
	
	public List<FotcWebChat> findAllFotcWebChat(String key, Object value) {
		log.debug("finding all FotcWebChat instances");
		try {
			String queryString = "from FotcWebChat where "
					+ key + "= ? ";
					
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Integer save(FotcWebChat obj) {
        log.debug("saving FotcWebChat instance");
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
	
	public void update(FotcWebChat obj) {
        log.debug("saving FotcWebChat instance");
        try {
            getSession().saveOrUpdate(obj);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
	
	public FotcWebChat queryById(Integer id) {
		log.debug("queryById FotcWebChat instance with id: " + id);
		try {
			FotcWebChat instance = (FotcWebChat) getSession().get(
					"com.gt.entity.FotcWebChat", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List<FotcWebChat> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<FotcWebChat> list = null;
		log.debug("finding FotcWebChat instance with filter");
		try {
			String queryString = " from FotcWebChat "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FotcWebChat name failed", re);
			throw re;
		}
		return list;
	}
	
}
