package com.gt.service.front;

import java.util.List;

import com.gt.entity.FotcWebChat;

public interface FotcWebChatService {

	public List<FotcWebChat> findAllFotcWebChat(String key, Object value);
	
	public Integer save(FotcWebChat obj);
	
	public void update(FotcWebChat obj);
	
	public FotcWebChat queryById(Integer id);
	
	public List<FotcWebChat> list(int firstResult, int maxResults, String filter,
			boolean isFY);
	
}
