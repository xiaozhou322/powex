package com.gt.service.admin;

import java.util.List;
import com.gt.entity.Freleaselog;

public interface ReleaseLogService {

	public Freleaselog findById(int id);

	public void saveObj(Freleaselog obj);

	public void deleteObj(int id);

	public void updateObj(Freleaselog obj);

	public List<Freleaselog> findByProperty(String name, Object value);

	public List<Freleaselog> findAll();
	
	public List<Freleaselog> list(int firstResult, int maxResults,String filter);
	
	public List<Freleaselog> simplelist(int firstResult, int maxResults,String filter);
}
