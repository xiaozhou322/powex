package com.gt.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Pprofitlogs;

/**
 * 收益记录表
 * @author raoshijing
 * @email 742434841@qq.com
 * @date 2018-10-31 15:30:02
 */
@Repository
public class PprofitlogsDAO extends HibernateDaoSupport {

	private static final Logger log = LoggerFactory.getLogger(PprofitlogsDAO.class);

	public void save(Pprofitlogs instance) {
		log.debug("saving Pprofitlogs instance");
		try {
			getSession().save(instance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public Pprofitlogs findById(java.lang.Integer id) {
		log.debug("getting Pprofitlogs instance with id: " + id);
		try {
			Pprofitlogs instance = (Pprofitlogs) getSession().get(
					"com.gt.entity.Pprofitlogs", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public void attachDirty(Pprofitlogs instance) {
		log.debug("attaching dirty Pprofitlogs instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Pprofitlogs> list(int firstResult, int maxResults, String filter,boolean isFY) {
		List<Pprofitlogs> list = null;
		log.debug("finding Pprofitlogs instance with filter");
		try {
			String queryString = "from Pprofitlogs "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by Profitlogs name failed", re);
			throw re;
		}
		return list;
	}
	
	//获取资产明细
	public List<Map> getAssetListByParams(int projectId){
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.trademapping_id,t1.cointype_id,t1.total_amount,t2.fTotal,t2.fFrozen from \n");
		sql.append("(select trademapping_id, cointype_id, SUM(amount) AS total_amount from p_profitlogs where project_id =" + projectId + " GROUP BY cointype_id)t1 \n");
		sql.append("left join (select fVi_fId,fTotal,fFrozen from fvirtualwallet where fuid = " + projectId +")t2 \n");
		sql.append("on t1.cointype_id = t2.fVi_fId \n");
		
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("tradeMappingId", o[0]);
			map.put("coinTypeId", o[1]);
			map.put("totalAmount", o[2]);
			map.put("fTotal", o[3]);
			map.put("fFrozen", o[4]);
			all.add(map);
		}
		return all;
	}
	
	public List<Map> getMonthServiceCharge(int status,int projectId){
		List<Map> all = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cointype_id,SUM(amount) FROM p_profitlogs WHERE project_id=" + projectId + " \n");
		if(status == 0 || status == 1){
			sql.append("and status = " + status + " \n");
		}
		sql.append("AND DATE_FORMAT(STR_TO_DATE(statistical_date,'%Y-%m-%d'),'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m')  \n");
		sql.append("GROUP BY cointype_id");
		
		SQLQuery queryObject = getSession().createSQLQuery(sql.toString());
		List allList = queryObject.list();
		Iterator it = allList.iterator();
		while(it.hasNext()) {
			Map map = new HashMap();
			Object[] o = (Object[]) it.next();
			map.put("coinTypeId", o[0]);
			map.put("totalAmount", o[1]);
			all.add(map);
		}
		return all;
	}
	
	public List<Integer> getAllProfitCoinType(String tableName,String fieldName,String filter){
		String queryString = "select DISTINCT(" + fieldName +") from " + tableName+" "+filter;
		Query queryObject = getSession().createQuery(queryString);
		return queryObject.list();
	}
}
