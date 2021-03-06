package com.admintwo.dao;

import com.admintwo.model.Download;

public interface DownloadDao {

	// 查询是否下载过某资源
	public int getDownload(Download download);
	
	// 增加一条下载记录
	public int insertDownload(Download download);

}
