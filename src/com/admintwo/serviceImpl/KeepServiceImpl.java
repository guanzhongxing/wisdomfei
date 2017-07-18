package com.admintwo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admintwo.dao.KeepDao;
import com.admintwo.model.Keep;
import com.admintwo.service.KeepService;

@Component("KeepServiceImpl")
public class KeepServiceImpl implements KeepService {

	@Autowired
	private KeepDao keepDao;

	@Override
	public boolean insertKeep(Keep keep) {
		int rows = keepDao.insertKeep(keep);
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteKeep(Keep keep) {
		int rows = keepDao.deleteKeep(keep);
		if (rows == 1) {
			return true;
		}
		return false;
	}

}
