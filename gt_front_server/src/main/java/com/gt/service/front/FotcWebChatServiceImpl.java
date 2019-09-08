package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FotcWebChatDAO;
import com.gt.entity.FotcWebChat;
import com.gt.service.front.FotcWebChatService;

@Service("fotcWebChatService")
public class FotcWebChatServiceImpl implements FotcWebChatService{

	@Autowired
	private FotcWebChatDAO fotcWebChatDAO;
	
	@Override
	public List<FotcWebChat> findAllFotcWebChat(String key, Object value) {
		return fotcWebChatDAO.findAllFotcWebChat(key, value);
	}

	@Override
	public Integer save(FotcWebChat obj) {
		return fotcWebChatDAO.save(obj);
	}

	@Override
	public void update(FotcWebChat obj) {
		fotcWebChatDAO.update(obj);
		
	}

	@Override
	public FotcWebChat queryById(Integer id) {
		return fotcWebChatDAO.queryById(id);
	}

	@Override
	public List<FotcWebChat> list(int firstResult, int maxResults, String filter, boolean isFY) {
		return fotcWebChatDAO.list(firstResult, maxResults, filter, isFY);
	}

}
