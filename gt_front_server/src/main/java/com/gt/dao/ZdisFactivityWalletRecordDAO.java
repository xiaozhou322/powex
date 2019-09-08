package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.ZdisFactivityWalletRecord;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fabout entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see ztmp.Fabout
 * @author MyEclipse Persistence Tools
 */

@Repository
public class ZdisFactivityWalletRecordDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(ZdisFactivityWalletRecordDAO.class);
	// property constants
	public static final String FTITLE = "ftitle";
	public static final String FCONTENT = "fcontent";

	public void save(ZdisFactivityWalletRecord transientInstance) {
		log.debug("saving ZdisFactivityWalletRecord instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ZdisFactivityWalletRecord persistentInstance) {
		log.debug("deleting ZdisFactivityWalletRecord instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ZdisFactivityWalletRecord findById(java.lang.Integer id) {
		log.debug("getting ZdisFactivityWalletRecord instance with id: " + id);
		try {
			ZdisFactivityWalletRecord instance = (ZdisFactivityWalletRecord) getSession().get("com.gt.entity.ZdisFactivityWalletRecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ZdisFactivityWalletRecord> findByExample(ZdisFactivityWalletRecord instance) {
		log.debug("finding ZdisFactivityWalletRecord instance by example");
		try {
			List<ZdisFactivityWalletRecord> results = (List<ZdisFactivityWalletRecord>) getSession()
					.createCriteria("com.gt.entity.ZdisFactivityWalletRecord").add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ZdisFactivityWalletRecord instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ZdisFactivityWalletRecord as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2) {
		log.debug("finding ZdisFactivityWalletRecord instance with property");
		try {
			String queryString = "from ZdisFactivityWalletRecord as model where model."
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
	
	
	public List<ZdisFactivityWalletRecord> findByFtitle(Object ftitle) {
		return findByProperty(FTITLE, ftitle);
	}

	public List<ZdisFactivityWalletRecord> findByFcontent(Object fcontent) {
		return findByProperty(FCONTENT, fcontent);
	}

	public List findAll() {
		log.debug("finding all ZdisFactivityWalletRecord instances");
		try {
			String queryString = "from ZdisFactivityWalletRecord";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ZdisFactivityWalletRecord merge(ZdisFactivityWalletRecord detachedInstance) {
		log.debug("merging ZdisFactivityWalletRecord instance");
		try {
			ZdisFactivityWalletRecord result = (ZdisFactivityWalletRecord) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ZdisFactivityWalletRecord instance) {
		log.debug("attaching dirty ZdisFactivityWalletRecord instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ZdisFactivityWalletRecord instance) {
		log.debug("attaching clean ZdisFactivityWalletRecord instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<ZdisFactivityWalletRecord> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<ZdisFactivityWalletRecord> list = null;
		log.debug("finding ZdisFactivityWalletRecord instance with filter");
		try {
			String queryString = "from ZdisFactivityWalletRecord "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by ZdisFactivityWalletRecord name failed", re);
			throw re;
		}
		return list;
	}
	
	public List getLogList(int id) {
		List<Map>  list = null;
		log.debug("finding ZdisFactivityWalletRecord instance with activityId");
		StringBuffer sb=new StringBuffer();
		try {
			sb.append(" select n.user_id,DATE_FORMAT(n.create_time,'%Y-%m-%d %H:%i:%s') as create_time,m.awards_name,m.fee_amount,l.fShortName from ( ");
			sb.append(" select * from "
					+ "lottery_log where periods_id in ( ");
			sb.append(" select a.id from "
					+ " lottery_periods a,( select id,this_round from factivity where id="+id+") b ");
			sb.append(" where a.activity_id=b.id and a.periods_num=b.this_round)) n,lottery_awards m,fvirtualcointype l ");
			sb.append(" where n.awards_id=m.id and m.coin_type=l.fid order by n.create_time desc ");
			 list=getFromSQL(sb.toString());		
		} catch (RuntimeException re) {
			log.error("find by ZdisFactivityWalletRecord name failed", re);
			throw re;
		}
		return list;
	}
}