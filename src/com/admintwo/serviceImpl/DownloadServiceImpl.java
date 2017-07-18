package com.admintwo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admintwo.dao.DownloadDao;
import com.admintwo.model.Download;
import com.admintwo.service.DownloadService;

@Component("DownloadServiceImpl")
public class DownloadServiceImpl implements DownloadService {

	@Autowired
	private DownloadDao DownloadDao;

	@Override
	public int getDownload(Download download) {
		int rows = DownloadDao.getDownload(download);
		return rows;
	}

	@Override
	public int insertDownload(Download download) {
		int rows = DownloadDao.insertDownload(download);
		return rows;
	}

}
