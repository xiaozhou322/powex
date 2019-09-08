package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.Enum.EntrustStatusEnum;
import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fentrust;
import com.gt.entity.Fentrustlog;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fentrust entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.gt.entity.Fentrust
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FentrustDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FentrustDAO.class);
	// property constants
	public static final String FENTRUST_TYPE = "fentrustType";
	public static final String FPRIZE = "fprize";
	public static final String FAMOUNT = "famount";
	public static final String FCOUNT = "fcount";
	public static final String FSTATUS = "fstatus";

	public void save(Fentrust transientInstance) {
		log.debug("saving Fentrust instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fentrust persistentInstance) {
		log.debug("deleting Fentrust instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fentrust findById(java.lang.Integer id) {
		log.debug("getting Fentrust instance with id: " + id);
		try {
			Fentrust instance = (Fentrust) getSession()
					.get("com.gt.entity.Fentrust", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fentrust> findByExample(Fentrust instance) {
		log.debug("finding Fentrust instance by example");
		try {
			List<Fentrust> results = (List<Fentrust>) getSession()
					.createCriteria("com.gt.entity.Fentrust").add(create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fentrust instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fentrust as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fentrust> findByFentrustType(Object fentrustType) {
		return findByProperty(FENTRUST_TYPE, fentrustType);
	}

	public List<Fentrust> findByFprize(Object fprize) {
		return findByProperty(FPRIZE, fprize);
	}

	public List<Fentrust> findByFamount(Object famount) {
		return findByProperty(FAMOUNT, famount);
	}

	public List<Fentrust> findByFcount(Object fcount) {
		return findByProperty(FCOUNT, fcount);
	}

	public List<Fentrust> findByFstatus(Object fstatus) {
		return findByProperty(FSTATUS, fstatus);
	}

	public List findAll() {
		log.debug("finding all Fentrust instances");
		try {
			String queryString = "from Fentrust";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fentrust merge(Fentrust detachedInstance) {
		log.debug("merging Fentrust instance");
		try {
			Fentrust result = (Fentrust) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fentrust instance) {
		log.debug("attaching dirty Fentrust instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fentrust instance) {
		log.debug("attaching clean Fentrust instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Fentrustlog findLatestDeal(int coinTypeId){
		log.debug("findLatestSuccessDeal all Fentrust instances");
		try {
			String queryString = "from Fentrustlog where  ftrademapping.fid=? order by fid desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, coinTypeId) ;
			
			queryObject.setFirstResult(0) ;
			queryObject.setMaxResults(1) ;
			List<Fentrustlog> fentrustlogs = queryObject.list();
			if(fentrustlogs.size()>0){
				return fentrustlogs.get(0) ;
			}else{
				return null ;
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<Fentrust> findLatestSuccessDeal(int coinTypeId,int fentrustType,int count){
		log.debug("findLatestSuccessDeal all Fentrust instances");
		try {
			String queryString = "from Fentrust where fstatus=? and ftrademapping.fid=? and fentrustType=?  order by flastUpdatTime desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, EntrustStatusEnum.AllDeal) ;
			queryObject.setParameter(1, coinTypeId) ;
			queryObject.setParameter(2, fentrustType) ;
			
			queryObject.setFirstResult(0) ;
			queryObject.setMaxResults(count) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	
	public List<Fentrust> findAllGoingFentrust(int coinTypeId,int fentrustType,boolean isLimit){
		log.debug("findAllGoingFentrust all Fentrust instances");
		try {
			String queryString = "from Fentrust where (fstatus=? or fstatus=?)" +
					" and ftrademapping.fid=?" +
					" and fentrustType=? and fisLimit=?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, EntrustStatusEnum.Going) ;
			queryObject.setParameter(1, EntrustStatusEnum.PartDeal) ;
			queryObject.setParameter(2, coinTypeId) ;
			queryObject.setParameter(3, fentrustType) ;
			queryObject.setParameter(4, isLimit) ;
			
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List<Fentrust> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		List<Fentrust> list = null;
		log.debug("finding Fentrust instance with filter");
		try {
			String queryString = "from Fentrust " + filter;
			Query queryObject = getSession().createQuery(queryString);
			if (isFY) {
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fentrust by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public List<Fentrust> getFentrustHistory(
			int fuid,int fvirtualCoinTypeId,int[] entrust_type,
			int first_result,int max_result,String order,
			int entrust_status[]){
		
		log.debug("getFentrustHistory all Fentrust instances");
		try {
			StringBuffer queryString = new StringBuffer("from Fentrust where fuser.fid=? " +
					" and ftrademapping.fid=? and  ") ;
			
			if(entrust_type!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_type.length;i++) {
					if(i==0){
						queryString.append(" fentrustType=? ") ;
					}else{
						queryString.append(" or fentrustType=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			if(entrust_status!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_status.length;i++) {
					if(i==0){
						queryString.append(" fstatus=? ") ;
					}else{
						queryString.append(" or fstatus=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			queryString.append(" 1=1 ") ;
			
			if(order!=null){
				queryString.append(" order by "+order) ;
			}
			
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setParameter(0, fuid) ;
			queryObject.setParameter(1, fvirtualCoinTypeId) ;
			
			int index = 2 ;
			if(entrust_type!=null){
				for (int i = 0; i < entrust_type.length; i++) {
					queryObject.setParameter(index+i, entrust_type[i]) ;
				}
				index+=entrust_type.length ;
			}
			
			if(entrust_status!=null){
				for (int i=0;i<entrust_status.length;i++) {
					queryObject.setParameter(index+i, entrust_status[i]) ;
				}
			}

			queryObject.setFirstResult(first_result) ;
			queryObject.setMaxResults(max_result) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
	}
	
	public int getFentrustHistoryCount(
			int fuid,int fvirtualCoinTypeId,int[] entrust_type,
			int entrust_status[]){
		
		log.debug("getFentrustHistory all Fentrust instances");
		try {
			StringBuffer queryString = new StringBuffer("select count(fid) from Fentrust where fuser.fid=? " +
					" and ftrademapping.fid=? and  ") ;
			
			if(entrust_type!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_type.length;i++) {
					if(i==0){
						queryString.append(" fentrustType=? ") ;
					}else{
						queryString.append(" or fentrustType=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			if(entrust_status!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_status.length;i++) {
					if(i==0){
						queryString.append(" fstatus=? ") ;
					}else{
						queryString.append(" or fstatus=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			queryString.append(" 1=1 ") ;
			
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setParameter(0, fuid) ;
			queryObject.setParameter(1, fvirtualCoinTypeId) ;
			
			int index = 2 ;
			if(entrust_type!=null){
				for (int i = 0; i < entrust_type.length; i++) {
					queryObject.setParameter(index+i, entrust_type[i]) ;
				}
				index+=entrust_type.length ;
			}
			
			if(entrust_status!=null){
				for (int i=0;i<entrust_status.length;i++) {
					queryObject.setParameter(index+i, entrust_status[i]) ;
				}
			}
			
			long l = (Long)queryObject.list().get(0);
			return (int) l;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
	}
	
	public List<Fentrust> getFentrustHistory(
			int fuid,int fvirtualCoinTypeId,int[] entrust_type,
			int first_result,int max_result,String order,
			int entrust_status[],
			Date beginDate,Date endDate){
		
		log.debug("getFentrustHistory all Fentrust instances");
		try {
			StringBuffer queryString = new StringBuffer("from Fentrust where fuser.fid=? " +
					" and ftrademapping.fid=? and  ") ;
			
			if(beginDate!=null && endDate!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
				queryString.append(" fcreateTime>='"+sdf.format(beginDate)+"' and fcreateTime<='"+sdf.format(endDate)+"' and ") ;
			}
			
			if(entrust_type!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_type.length;i++) {
					if(i==0){
						queryString.append(" fentrustType=? ") ;
					}else{
						queryString.append(" or fentrustType=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			if(entrust_status!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_status.length;i++) {
					if(i==0){
						queryString.append(" fstatus=? ") ;
					}else{
						queryString.append(" or fstatus=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			queryString.append(" 1=1 ") ;
			
			if(order!=null){
				queryString.append(" order by "+order) ;
			}
			
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setParameter(0, fuid) ;
			queryObject.setParameter(1, fvirtualCoinTypeId) ;
			
			int index = 2 ;
			if(entrust_type!=null){
				for (int i = 0; i < entrust_type.length; i++) {
					queryObject.setParameter(index+i, entrust_type[i]) ;
				}
				index+=entrust_type.length ;
			}
			
			if(entrust_status!=null){
				for (int i=0;i<entrust_status.length;i++) {
					queryObject.setParameter(index+i, entrust_status[i]) ;
				}
			}

			queryObject.setFirstResult(first_result) ;
			queryObject.setMaxResults(max_result) ;
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
	}
	
	public int getFentrustHistoryCount(
			int fuid,int fvirtualCoinTypeId,int[] entrust_type,
			int entrust_status[],
			Date beginDate,Date endDate){
		
		log.debug("getFentrustHistory all Fentrust instances");
		try {
			StringBuffer queryString = new StringBuffer("select count(fid) from Fentrust where fuser.fid=? " +
					" and ftrademapping.fid=? and  ") ;
			
			if(beginDate!=null && endDate!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
				queryString.append(" fcreateTime>='"+sdf.format(beginDate)+"' and fcreateTime<='"+sdf.format(endDate)+"' and ") ;
			}
			
			if(entrust_type!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_type.length;i++) {
					if(i==0){
						queryString.append(" fentrustType=? ") ;
					}else{
						queryString.append(" or fentrustType=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			if(entrust_status!=null){
				queryString.append(" ( ") ;
				for (int i=0;i<entrust_status.length;i++) {
					if(i==0){
						queryString.append(" fstatus=? ") ;
					}else{
						queryString.append(" or fstatus=? ") ;
					}
				}
				queryString.append(" ) and ") ;
			}
			
			queryString.append(" 1=1 ") ;
			
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setParameter(0, fuid) ;
			queryObject.setParameter(1, fvirtualCoinTypeId) ;
			
			int index = 2 ;
			if(entrust_type!=null){
				for (int i = 0; i < entrust_type.length; i++) {
					queryObject.setParameter(index+i, entrust_type[i]) ;
				}
				index+=entrust_type.length ;
			}
			
			if(entrust_status!=null){
				for (int i=0;i<entrust_status.length;i++) {
					queryObject.setParameter(index+i, entrust_status[i]) ;
				}
			}
			
			long l = (Long)queryObject.list().get(0);
			return (int) l;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
	}
	
	public List<Map> getTotalQty(int fentrustType) {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(ifnull(a.fleftCount,0)) total,CONCAT(d.fName,'(',c.fName,')') from Fentrust a  \n");
		sql.append("left outer join ftrademapping b on a.ftrademapping=b.fId  \n");
		sql.append("LEFT OUTER JOIN fvirtualcointype c on b.fvirtualcointype1=c.fid  \n");
		sql.append("LEFT OUTER JOIN fvirtualcointype d on b.fvirtualcointype2=d.fid  \n");
		sql.append("where 1=1 and a.fstatus in(1,2) and a.fentrustType="+fentrustType+" \n");
		sql.append("group by d.fName,c.fName  \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("total", o[0]);
			map.put("fName", o[1]);
			all.add(map);
		}
		return all;
	}
	
	public List<Map> getMemberQty(String querytype,String queryday,int firstResult, int maxResults) {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(ifnull(a.fsuccessAmount,0)) total,b.fid,b.fRealName,b.fhasRealValidate,b.fhasImgValidate,ifnull(b.fIntroUser_id,0) introuser from Fentrust a \n");
		sql.append(" left outer join fuser b on a.fUs_fid=b.fId \n");
		sql.append(" where a.fStatus=3 and \n");
		if (querytype.equals("y")){
			sql.append(" DATE_FORMAT(a.flastUpdatTime,'%Y') = ");
		}else if (querytype.equals("m")){
			sql.append(" DATE_FORMAT(a.flastUpdatTime,'%Y-%m') = ");
		}else if (querytype.equals("w")){
			sql.append(" DATE_FORMAT(a.flastUpdatTime,'%Y-%u') = ");
		}else{
			sql.append(" DATE_FORMAT(a.flastUpdatTime,'%Y-%m-%d') = ");
		}
		sql.append(" '" + queryday + "'");
		sql.append(" group by (b.fid) \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Object[] o = (Object[]) it.next();
			Map map = new HashMap();
			map.put("total", o[0].toString());
			map.put("uid", o[1].toString());
			map.put("realname", o[2].toString());
			map.put("hasRealValidate", o[3].toString());
			map.put("hasImgValidate", o[4].toString());
			map.put("iuid", o[5].toString());
			all.add(map);
		}
		return all;
	}
	
	public List<Map> getTotalQty(int fentrustType,boolean isToday) {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(ifnull(a.ffees,0)-ifnull(a.fleftfees,0)) total,CONCAT(d.fName,'(',c.fName,')') from Fentrust a  \n");
		sql.append("left outer join ftrademapping b on a.ftrademapping=b.fId  \n");
		sql.append("LEFT OUTER JOIN fvirtualcointype c on b.fvirtualcointype1=c.fId  \n");
		sql.append("LEFT OUTER JOIN fvirtualcointype d on b.fvirtualcointype2=d.fId  \n");
		sql.append("where a.fstatus in(2,3,4) and a.fentrustType="+fentrustType+"  \n");
		if(isToday){
			sql.append("and DATE_FORMAT(a.fcreateTime,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d')  \n");
		}
		sql.append("group by d.fName,c.fName  \n");
		
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("total", o[0]);
			map.put("fName", o[1]);
			all.add(map);
		}
		return all;
	}
	
	public List<Map> getMemberMarket(int querytype,String queryday,int firstResult, int maxResults) {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(ifnull(a.fsuccessAmount,0)) total,b.fid,b.fRealName,b.fhasRealValidate,b.fhasImgValidate,ifnull(b.fIntroUser_id,0) introuser from Fentrust a \n");
		sql.append(" left outer join fuser b on a.fUs_fid=b.fId \n");
		sql.append(" where a.fStatus=3 and \n");
		sql.append(" DATE_FORMAT(a.flastUpdatTime,'%Y-%m-%d') = ");
		sql.append(" '" + queryday + "' and ");
		sql.append(" ftrademapping ="+querytype );
		sql.append(" group by (b.fid) order by total desc \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Object[] o = (Object[]) it.next();
			Map map = new HashMap();
			map.put("total", o[0].toString());
			map.put("uid", o[1].toString());
			map.put("realname", o[2].toString());
			map.put("hasRealValidate", o[3].toString());
			map.put("hasImgValidate", o[4].toString());
			map.put("iuid", o[5].toString());
			all.add(map);
		}
		return all;
	}
	
	public List<Map> getMemberMarket(int querytype,String queryday,String queryday1,int firstResult, int maxResults) {
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(ifnull(a.fsuccessAmount,0)) total,b.fid,b.fRealName,b.fhasRealValidate,b.fhasImgValidate,ifnull(b.fIntroUser_id,0) introuser from Fentrust a \n");
		sql.append(" left outer join fuser b on a.fUs_fid=b.fId \n");
		sql.append(" where a.fStatus=3 and \n");
		sql.append(" DATE_FORMAT(a.flastUpdatTime,'%Y-%m-%d') >= ");
		sql.append(" '" + queryday + "' and ");
		sql.append(" DATE_FORMAT(a.flastUpdatTime,'%Y-%m-%d') <= ");
		sql.append(" '" + queryday1 + "' and ");
		sql.append(" ftrademapping ="+querytype );
		sql.append(" group by (b.fid) order by total desc \n");
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Object[] o = (Object[]) it.next();
			Map map = new HashMap();
			map.put("total", o[0].toString());
			map.put("uid", o[1].toString());
			map.put("realname", o[2].toString());
			map.put("hasRealValidate", o[3].toString());
			map.put("hasImgValidate", o[4].toString());
			map.put("iuid", o[5].toString());
			all.add(map);
		}
		return all;
	}
}