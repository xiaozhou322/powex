package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FsystemargsDAO;
import com.gt.entity.Fsystemargs;


@Service("systemArgsService")
public class SystemArgsServiceImpl implements SystemArgsService {
	@Autowired
	private FsystemargsDAO fsystemargsDAO;

	public Fsystemargs findById(int id) {
		return this.fsystemargsDAO.findById(id);
	}

	public void saveObj(Fsystemargs obj) {
		this.fsystemargsDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fsystemargs obj = this.fsystemargsDAO.findById(id);
		this.fsystemargsDAO.delete(obj);
	}

	public void updateObj(Fsystemargs obj) {
		this.fsystemargsDAO.attachDirty(obj);
	}

	public List<Fsystemargs> findByProperty(String name, Object value) {
		return this.fsystemargsDAO.findByProperty(name, value);
	}

	public List<Fsystemargs> findAll() {
		return this.fsystemargsDAO.findAll();
	}

	public List<Fsystemargs> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fsystemargsDAO.list(firstResult, maxResults, filter,isFY);
	}
	
	public String getValue(String key){
		String ret = "" ;
		List<Fsystemargs> fsystemargs = this.fsystemargsDAO.findByProperty("fkey", key) ;
		if(fsystemargs.size()>0){
			Fsystemargs fsystemargs2 = fsystemargs.get(0) ;
			ret = fsystemargs2.getFvalue() ;
		}
		
		return ret ;
	}
}