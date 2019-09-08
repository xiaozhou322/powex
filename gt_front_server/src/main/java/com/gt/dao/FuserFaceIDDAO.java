package com.gt.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.FuserFaceID;

@Repository
public class FuserFaceIDDAO extends HibernateDaoSupport {
	
	private static final Logger log = LoggerFactory.getLogger(FuserFaceIDDAO.class);
	
	public void save(FuserFaceID transientInstance) {
		log.debug("saving FuserFaceID instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void attachDirty(FuserFaceID instance) {
		log.debug("attaching dirty FuserFaceID instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	public FuserFaceID findByUserId(Integer userId) {
		log.debug("finding FuserFaceID instance with userId ");
		try {
			String queryString = "from FuserFaceID as model where model.userId = ? order by model.id desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, userId);
			List<FuserFaceID> userFaceIDList = queryObject.list();
			if(userFaceIDList != null && userFaceIDList.size() > 0) {
				return userFaceIDList.get(0);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return null;
		
	}

}
