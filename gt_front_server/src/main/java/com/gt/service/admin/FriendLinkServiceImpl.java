package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FfriendlinkDAO;
import com.gt.entity.Ffriendlink;

@Service("friendLinkService")
public class FriendLinkServiceImpl implements FriendLinkService {
	@Autowired
	private FfriendlinkDAO friendlinkDAO;

	public Ffriendlink findById(int id) {
		return this.friendlinkDAO.findById(id);
	}

	public void saveObj(Ffriendlink obj) {
		this.friendlinkDAO.save(obj);
	}

	public void deleteObj(int id) {
		Ffriendlink obj = this.friendlinkDAO.findById(id);
		this.friendlinkDAO.delete(obj);
	}

	public void updateObj(Ffriendlink obj) {
		this.friendlinkDAO.attachDirty(obj);
	}

	public List<Ffriendlink> findByProperty(String name, Object value) {
		return this.friendlinkDAO.findByProperty(name, value);
	}

	public List<Ffriendlink> findAll() {
		return this.friendlinkDAO.findAll();
	}

	public List<Ffriendlink> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.friendlinkDAO.list(firstResult, maxResults, filter,isFY);
	}

}