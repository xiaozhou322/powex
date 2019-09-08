package com.gt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;

@Repository
public class UtilsDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(UtilsDAO.class);
}
