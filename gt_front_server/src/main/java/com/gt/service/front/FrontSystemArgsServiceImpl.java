package com.gt.service.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FsystemargsDAO;
import com.gt.dao.FwebbaseinfoDAO;
import com.gt.entity.Fsystemargs;
import com.gt.entity.Fwebbaseinfo;

@Service("frontSystemArgsService")
public class FrontSystemArgsServiceImpl implements FrontSystemArgsService {

	@Autowired
	private FsystemargsDAO fsystemargsDAO ;
	@Autowired
	private FwebbaseinfoDAO fwebbaseinfoDAO ;
	
	public String getSystemArgs(String key){
		String value = null ;
		List<Fsystemargs> list = this.fsystemargsDAO.findByFkey(key) ;
		if(list.size()>0){
			value = list.get(0).getFvalue() ;
		}
		return value ;
	}
	
	public Map<String, Object> findAllMap(){
		Map<String, Object> map = new HashMap<String, Object>() ;
		List<Fsystemargs> list = this.fsystemargsDAO.findAll() ;
		for (Fsystemargs fsystemargs : list) {
			map.put(fsystemargs.getFkey(), fsystemargs.getFvalue()) ;
		}
		return map ;
	}
	
	public Fwebbaseinfo findFwebbaseinfoById(int id){
		return this.fwebbaseinfoDAO.findById(id) ;
	}
}
