package com.admintwo.service;

import com.admintwo.model.Keep;

public interface KeepService {
	// 分享资源。1：成功，0：失败
	public boolean insertKeep(Keep keep);

	// 取消资源。1：成功，0：失败
	public boolean deleteKeep(Keep keep);
}
