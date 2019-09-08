package com.gt.dao;

import static org.hibernate.criterion.Example.create;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gt.dao.comm.HibernateDaoSupport;
import com.gt.entity.Fadmin;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fadmin entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.gt.entity.Fadmin
 * @author MyEclipse Persistence Tools
 */
@Repository
public class FadminDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FadminDAO.class);
	// property constants
	public static final String FNAME = "fname";
	public static final String FPASSWORD = "fpassword";
	public static final String FLEVEL = "flevel";
	public static final String FSTATUS = "fstatus";

	public void save(Fadmin transientInstance) {
		log.debug("saving Fadmin instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fadmin persistentInstance) {
		log.debug("deleting Fadmin instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fadmin findById(java.lang.Integer id) {
		log.debug("getting Fadmin instance with id: " + id);
		try {
			Fadmin instance = (Fadmin) getSession().get(
					"com.gt.entity.Fadmin", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fadmin> findByExample(Fadmin instance) {
		log.debug("finding Fadmin instance by example");
		try {
			List<Fadmin> results = (List<Fadmin>) getSession()
					.createCriteria("com.gt.entity.Fadmin")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fadmin instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fadmin as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fadmin> findByFname(Object fname) {
		return findByProperty(FNAME, fname);
	}

	public List<Fadmin> findByFpassword(Object fpassword) {
		return findByProperty(FPASSWORD, fpassword);
	}

	public List findAll() {
		log.debug("finding all Fadmin instances");
		try {
			String queryString = "from Fadmin";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fadmin merge(Fadmin detachedInstance) {
		log.debug("merging Fadmin instance");
		try {
			Fadmin result = (Fadmin) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fadmin instance) {
		log.debug("attaching dirty Fadmin instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fadmin instance) {
		log.debug("attaching clean Fadmin instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<Fadmin> list(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<Fadmin> list = null;
		log.debug("finding Fadmin instance with filter");
		try {
			String queryString = "from Fadmin " + filter;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setCacheable(true);
			if (isFY) {
				queryObject.setFirstResult(firstResult);
				queryObject.setMaxResults(maxResults);
			}
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find Fadmin by filter name failed", re);
			throw re;
		}
		return list;
	}
	
	public int getAllCount(String tableName,String filter){
		String queryString = "select count(*) from " + tableName+" "+filter;
		Query queryObject = getSession().createQuery(queryString);
		return Integer.parseInt(queryObject.list().get(0).toString());
	}
	
	public double getSQLValue(String filter){
		double total =0d;
		Query queryObject = getSession().createSQLQuery(filter);
		List list = queryObject.list();
		if(list != null && list.size() >0 && list.get(0) != null){
			total = Double.valueOf(list.get(0).toString());
		}
		return total;
	}
	
	
	public double getSQLValue2(String filter){
		double total =0d;
		Query queryObject = getSession().createQuery(filter);
		List list = queryObject.list();
		if(list != null && list.size() >0 && list.get(0) != null){
			total = Double.valueOf(list.get(0).toString());
		}
		return total;
	}
	
	public void updateSQL(String filter){
		Query queryObject = getSession().createSQLQuery(filter);
		queryObject.executeUpdate();
	}
	
	public Map<Integer,Object> getSQLValue1(String filter){
		Map<Integer,Object> map = new HashMap<Integer,Object>();
		Query queryObject = getSession().createSQLQuery(filter);
		List list = queryObject.list();
		if(list != null && list.size() >0 && list.get(0) != null){
			for(int i=0;i<list.size();i++){
				Object[] o = (Object[])list.get(i);
				map.put(Integer.parseInt(o[0].toString()),o[1]);
			}
		}
		return map;
	}

	public boolean backDatabase(String ip, String port, String dataBase,
			String name, String password,String dir,String fileName) {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBase
					+ "?useUnicode=true&characterEncoding=utf-8";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, name, password);
			Statement st = conn.createStatement();
			String tableName = "";
			String tableColumn = "";
			String allTable = "select table_name from information_schema.tables t where t.table_schema='"
					+ dataBase + "'";
			Query queryObject = getSession().createSQLQuery(allTable);
			List all = queryObject.list();
			Iterator it = all.iterator();
			File dirFile = new File(dir);		
			if(!dirFile.exists()){
				dirFile.mkdirs() ;
			}
			if(!dirFile.isDirectory()){
				return false ;
			}
			if(!dirFile.exists()){
				dirFile.mkdirs() ;
			}
			File file = new File(dir,fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			while (it.hasNext()) {
				tableName = it.next().toString();
				StringBuffer insertSQL = new StringBuffer();
				insertSQL.append("insert into " + dataBase + "." + tableName
						+ "(");
				tableColumn = "select COLUMN_NAME,DATA_TYPE from information_schema.COLUMNS where TABLE_NAME = '"
						+ tableName + "'";
				Query queryObject1 = getSession().createSQLQuery(tableColumn);
				List all1 = queryObject1.list();
				Iterator it1 = all1.iterator();
				StringBuffer selectSQL = new StringBuffer();
				selectSQL.append("select ");
				int count = 0;
				Map map = new HashMap();
				while (it1.hasNext()) {
					count = count + 1;
					Object[] column = (Object[]) it1.next();
					map.put(count, column[1].toString());
					if (it1.hasNext()) {
						insertSQL.append(column[0] + ",");
						selectSQL.append(column[0] + ",");
					} else {
						insertSQL.append(column[0]);
						selectSQL.append(column[0]);
					}
				}
				insertSQL.append(") values (");
				selectSQL.append(" from " + dataBase + "." + tableName);
				ResultSet rowSet = st.executeQuery(selectSQL.toString());
				String head = "//**************************备份table:" + tableName + "******************************\n";
				writer.write(head);
				while (rowSet.next()) {
					StringBuffer lastSQL = new StringBuffer();
					lastSQL.append(insertSQL.toString());
					for (int i = 1; i <= count; i++) {
						String type = map.get(i).toString();
						String value = "";
						if (type.equals("int") || type.equals("bigint")
								|| type.equals("decimal")
								|| type.equals("double")) {
							value = rowSet.getObject(i) + "";
						} else if (type.equals("varchar")) {
							value = "'" + rowSet.getObject(i) + "'";
						} else if (type.equals("datetime")) {
							value = "DATE_FORMAT('" + rowSet.getObject(i)
									+ "','%d %b %Y %T:%f')";
						} else {
							value = "'" + rowSet.getObject(i) + "'";
						}
						if (i == count) {
							lastSQL.append(value);
						} else {
							lastSQL.append(value + ",");
						}
					}
					lastSQL.append("); \n");
					writer.write(lastSQL.toString());
				}
				rowSet.close();
			}
			writer.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<Fadmin> login(Fadmin fadmin) {
		log.debug("finding all Fadmin instances");
		try {
			String queryString = "from Fadmin where fname=? and fpassword=? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, fadmin.getFname());
			queryObject.setParameter(1, fadmin.getFpassword());
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Double getAllSum(String tableName,String fieldName,String filter){
		String queryString = "select sum(" + fieldName +") from " + tableName+" "+filter;
		Query queryObject = getSession().createQuery(queryString);
		if (queryObject.list().size()>0 && queryObject.list().get(0)!=null){
			return Double.parseDouble(queryObject.list().get(0).toString());
		}else{
			return 0d;
		}
	}
}