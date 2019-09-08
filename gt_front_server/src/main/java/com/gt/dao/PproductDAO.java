package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pproduct;

/**
 * 项目方权证产品DAO
 * @author zhouyong
 *
 */
@Repository
public class PproductDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PproductDAO.class);
	
	public void save(Pproduct instance) {
		log.debug("saving Pproduct instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pproduct findById(java.lang.Integer id) {
		log.debug("getting Pproduct instance with id: " + id);
		try {
			Pproduct instance = (Pproduct) getSession().get(
					"com.gt.entity.Pproduct", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pproduct instance) {
		log.debug("attaching dirty Pproduct instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pproduct> list(int firstResult, int maxResults,String filter,boolean isFY) {
		List<Pproduct> list = null;
		log.debug("finding Pproduct instance with filter");
		try {
			String queryString = "from Pproduct "+filter;
			Query queryObject = getSession().createQuery(queryString);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public List findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2) {
		log.debug("finding Pproduct instance with property");
		try {
			String queryString = "from Pproduct as model where model."
					+ propertyName1 + "= ? and model."+propertyName2+"= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value1);
			queryObject.setParameter(1, value2);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public Pproduct findByCoinId(Integer coinId) {
		log.debug("finding Pproduct instance with property");
		Pproduct product = null;
		try {
			String queryString = "from Pproduct where coinId =" + coinId ;
			Query queryObject = getSession().createQuery(queryString);
			List<Pproduct> productList = queryObject.list();
			if(null != productList && productList.size() > 0) {
				product = productList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return product;
	}
}
