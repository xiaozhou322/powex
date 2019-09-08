package com.gt.dao;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Nper;

@Repository
public class NperDAO extends HibernateDaoSupport  {

	private static final Logger log = LoggerFactory.getLogger(NperDAO.class);
	
	public void save(Nper transientInstance) {
		log.debug("saving Nper instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Nper persistentInstance) {
		log.debug("deleting Nper instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void attachDirty(Nper instance) {
        log.debug("attaching dirty Nper instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
	
	public Nper findById(java.lang.Integer id) {
		log.debug("getting Nper instance with id: " + id);
		try {
			Nper instance = (Nper) getSession().get("com.gt.entity.Nper", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Nper> findByProperty(String propertyName, Object value) {
		log.debug("finding Nper instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Nper as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<Nper> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Nper> list = null;
		log.debug("finding Nper instance with filter");
		try {
			String queryString = "from Nper "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Nper name failed", re);
			throw re;
		}
		return list;
	}
	
	public int getAllCount(String tableName,String filter){
		String queryString = "select count(*) from " + tableName+" "+filter;
		Query queryObject = getSession().createQuery(queryString);
		return Integer.parseInt(queryObject.list().get(0).toString());
	}
	
	public int sendPrize(Integer nper_id){
		String queryString = "update t_nper set is_send_prize=1 where is_send_prize=0 and id=?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, nper_id);
		return queryObject.executeUpdate();
	}
	
	
	//selectType 0是=号，1是<>号
	public List<Nper> findByStatus(int status1,int status2,int selectType) {
		log.debug("finding all Nper instances");
		try {
			String eq = "=";
			if(selectType ==1){
				eq="<>";
			}
			String queryString = "from Nper where status "+eq+"? and status "+eq +"?" + "order by id desc ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, status1) ;
			queryObject.setParameter(1, status2) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	//selectType 0是=号，1是<>号
	public List<Nper> findByStatus(int status, int selectType) {
		log.debug("finding all Nper instances");
		try {
			String eq = "=";
			if(selectType ==1){
				eq="<>";
			}
			String queryString = "from Nper where status "+eq+"?" + "order by id desc ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, status) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}
