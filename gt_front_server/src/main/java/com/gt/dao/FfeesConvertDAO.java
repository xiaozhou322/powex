package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FfeesConvert;

/**
 * 手续费USDT兑换BFSC操作日志表DAO
 * @author zhouyong
 *
 */
@Repository
public class FfeesConvertDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FfeesConvertDAO.class);

	public void save(FfeesConvert instance) {
		log.debug("saving FfeesConvert instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public FfeesConvert findById(java.lang.Integer id) {
		log.debug("getting FfeesConvert instance with id: " + id);
		try {
			FfeesConvert instance = (FfeesConvert) getSession().get(
					"com.gt.entity.FfeesConvert", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public void delete(FfeesConvert persistentInstance) {
		log.debug("deleting Flog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public void attachDirty(FfeesConvert instance) {
		log.debug("attaching dirty FfeesConvert instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<FfeesConvert> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<FfeesConvert> list = null;
		log.debug("finding FfeesConvert instance with filter");
		try {
			String queryString = " from FfeesConvert "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by FconvertCoinLogs name failed", re);
			throw re;
		}
		return list;
	}
	
}
