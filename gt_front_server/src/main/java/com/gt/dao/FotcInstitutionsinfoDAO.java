package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FotcInstitutionsinfo;

@Repository
public class FotcInstitutionsinfoDAO extends HibernateDaoSupport{
	private static final Logger log = LoggerFactory.getLogger(FotcInstitutionsinfoDAO.class);
	
	public FotcInstitutionsinfo findById(int id) {
		log.debug("getting Fotcinstitutionsinfo instance with id: " + id);
		try {
			FotcInstitutionsinfo instance = (FotcInstitutionsinfo) getSession().get(
					"com.gt.entity.FotcInstitutionsinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public void save(FotcInstitutionsinfo fotcinstitutionsinfo) {
		log.debug("saving FotcInstitutionsinfo instance");
        try {
            getSession().saveOrUpdate(fotcinstitutionsinfo);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
	}
	
	public List<FotcInstitutionsinfo> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<FotcInstitutionsinfo> list = null;
		log.debug("finding FotcInstitutionsinfo instance with filter");
		try {
			String queryString = "from FotcInstitutionsinfo "+filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if(isFY){
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find FotcInstitutionsinfo by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	
	public FotcInstitutionsinfo findByUserId(Integer userId) {
		log.debug("finding all Fotcorder instances");
		try {
			String queryString = "from FotcInstitutionsinfo where fuser.fid=:userId and institutions_status = 1";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("userId", userId);
			List<FotcInstitutionsinfo> list =  queryObject.list();
			if(list.size() > 0) {
				return list.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		return null;
	}
}
