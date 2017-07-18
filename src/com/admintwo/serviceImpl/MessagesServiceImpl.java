package com.admintwo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admintwo.dao.MessagesDao;
import com.admintwo.model.Messages;
import com.admintwo.service.MessagesService;

@Component("MessagesServiceImpl")
public class MessagesServiceImpl implements MessagesService {

	@Autowired
	private MessagesDao messagesDao;

	@Override
	public int insertMessages(String message, int sort, String email) {
		int rows = messagesDao.insertMessages(message, sort, email);
		return rows;
	}

	@Override
	public List<Messages> getMessageByEmail(String email) {
		return messagesDao.getMessageByEmail(email);
	}

	@Override
	public boolean updateIsReadByIdAndEmail(int id, String email) {
		int rows = messagesDao.updateIsReadByIdAndEmail(id, email);
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateALlIsReadByEmail(String email) {
		return messagesDao.updateALlIsReadByEmail(email);
	}

}
