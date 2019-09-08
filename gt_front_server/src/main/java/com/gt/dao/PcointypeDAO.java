package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pcointype;

/**
 * 项目方币种表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-25 09:56:34
 */
@Repository
public class PcointypeDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(PcointypeDAO.class);
	
	public void save(Pcointype instance) {
		log.debug("saving Pcointype instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pcointype findById(java.lang.Integer id) {
		log.debug("getting Pcointype instance with id: " + id);
		try {
			Pcointype instance = (Pcointype) getSession().get(
					"com.gt.entity.Pcointype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pcointype instance) {
		log.debug("attaching dirty Pcointype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pcointype> list(int firstResult, int maxResults,String filter,boolean isFY) {
		List<Pcointype> list = null;
		log.debug("finding Pcointype instance with filter");
		try {
			String queryString = "from Pcointype "+filter;
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
		log.debug("finding Pcointype instance with property");
		try {
			String queryString = "from Pcointype as model where model."
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
	
	public Pcointype findByCoinId(Integer coinId) {
		log.debug("finding Pcointype instance with property");
		Pcointype pcointype = null;
		try {
			String queryString = "from Pcointype where coinId =" + coinId ;
			Query queryObject = getSession().createQuery(queryString);
			List<Pcointype> coinTypeList = queryObject.list();
			if(null != coinTypeList && coinTypeList.size() > 0) {
				pcointype = coinTypeList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return pcointype;
	}
}
