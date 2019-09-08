package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Lottery;
import com.gt.interceptor.AutoTableNameInterceptor;

@Repository
public class LotteryDAO extends HibernateDaoSupport  {

	private static final Logger log = LoggerFactory.getLogger(LotteryDAO.class);
	
	public void replaceTableName(String tableName){
		AutoTableNameInterceptor.setReplaceTableName("t_lottery", tableName);
	}
	
	public void createTable(String tableName){
		log.debug("saving Lottery instance");
		try {
			String queryString = "CREATE TABLE `"+tableName+"` ("
					+ "`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',"
					+ "`nper` varchar(11) DEFAULT NULL COMMENT '期数',"
					+ "`lottery_no` varchar(32) DEFAULT NULL COMMENT '奖票号码',"
					+ "`uid` int(11) DEFAULT NULL COMMENT '用户id',"
					+ "`createtime` datetime(3) DEFAULT NULL COMMENT '刮奖时间',"
					+ "`serial_no` varchar(128) DEFAULT NULL COMMENT '序列号',"
					+ "`block_link` varchar(128) DEFAULT NULL COMMENT '区块链接',"
					+ "PRIMARY KEY (`id`)"
					+ ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
			SQLQuery sqlQuery = getSession().createSQLQuery(queryString );
			sqlQuery.executeUpdate();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void save(Lottery transientInstance) {
		log.debug("saving Lottery instance");
		try {
			String queryString = "insert into t_lottery "
					+ "(block_link, createtime, lottery_no, nper, serial_no, uid)"
					+ " values (?, now(3), ?, ?, ?, ?)";
			SQLQuery sqlQuery = getSession().createSQLQuery(queryString );
			sqlQuery.setParameter(0, transientInstance.getBlock_link()) ;
			sqlQuery.setParameter(1, transientInstance.getLottery_no()) ;
			sqlQuery.setParameter(2, transientInstance.getNper()) ;
			sqlQuery.setParameter(3, transientInstance.getSerial_no()) ;
			sqlQuery.setParameter(4, transientInstance.getUid()) ;
			sqlQuery.executeUpdate();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Lottery persistentInstance) {
		log.debug("deleting Lottery instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void attachDirty(Lottery instance) {
        log.debug("attaching dirty Lottery instance");
        try {
            String queryString = "update t_lottery set block_link=? where id=?";
            SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
			sqlQuery.setParameter(0, instance.getBlock_link()) ;
			sqlQuery.setParameter(1, instance.getId()) ;
			sqlQuery.executeUpdate();
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
	
	public Lottery findById(java.lang.Integer id) {
		log.debug("getting Lottery instance with id: " + id);
		try {
			Lottery instance = (Lottery) getSession().get("com.gt.entity.Lottery", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Lottery instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Lottery as model where model."
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
		log.debug("finding Lottery instance with property");
		try {
			String queryString = "from Lottery as model where model."
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
	
	public List<Lottery> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Lottery> list = null;
		log.debug("finding Lottery instance with filter");
		try {
			String queryString = "from Lottery "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Lottery name failed", re);
			throw re;
		}
		return list;
	}
	
	public int getAllCount(String filter){
		String queryString = "select count(*) from Lottery "+filter;
		Query queryObject = getSession().createQuery(queryString);
		return Integer.parseInt(queryObject.list().get(0).toString());
	}
	
	public List<String> queryList(int firstResult, int maxResults, String hql,boolean isFY) {
		List<String> list = null;
		log.debug("finding Lottery instance with filter");
		try {
			String queryString = hql;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Lottery name failed", re);
			throw re;
		}
		return list;
	}
	
	public Long currentLotteryNumber(String nper){
		log.debug("finding Lottery instance with filter");
		try {
			StringBuffer queryString = new StringBuffer("select count(lottery_no) from Lottery where nper=?");
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setParameter(0, nper) ;
			return Long.valueOf(queryObject.list().get(0).toString());
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Long maxLotteryNo(String nper){
		log.debug("finding Lottery instance with filter");
		try {
			StringBuffer queryString = new StringBuffer("select max(lottery_no) from Lottery where nper=?");
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setParameter(0, nper) ;
			List list = queryObject.list();
			if(null != list && list.size()>0 && null != list.get(0)){
				return Long.valueOf(list.get(0).toString());
			}else{
				return 0L;
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
}
