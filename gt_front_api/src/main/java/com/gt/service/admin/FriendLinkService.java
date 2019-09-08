package com.gt.service.admin;

import java.util.List;

import com.gt.entity.Ffriendlink;

public interface FriendLinkService {

	public Ffriendlink findById(int id);

	public void saveObj(Ffriendlink obj);

	public void deleteObj(int id);

	public void updateObj(Ffriendlink obj);

	public List<Ffriendlink> findByProperty(String name, Object value);

	public List<Ffriendlink> findAll();

	public List<Ffriendlink> list(int firstResult, int maxResults,
			String filter,boolean isFY);

}